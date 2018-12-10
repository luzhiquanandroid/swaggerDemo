package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.DetailReq;
import com.fotron.draw.bean.req.HelpPhoneReq;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.Invitation;
import com.fotron.draw.entity.InvitationJoin;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.InvitationMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.PersonalCenterService;
import com.fotron.draw.utils.Constant;
import com.fotrontimes.core.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 21:54
 * @description
 */
@Service
@Slf4j
public class PersonalCenterServiceImpl implements PersonalCenterService {
    @Resource
    private InvitationMapper invitationMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map list(HelpPhoneReq helpPhoneReq) {
        Map map = new HashMap();
        User user = this.userMapper.selectByUserId(helpPhoneReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        PageHelper.startPage(helpPhoneReq.getCurrentPage(), helpPhoneReq.getPageSize());
        List<Invitation> invitations = this.invitationMapper.selectList(helpPhoneReq.getUserId(), helpPhoneReq.getType());
        map.put("data", invitations);
        map.put("totalCount", invitations.size());
        return map;
    }

    @Override
    public Invitation detail(DetailReq detailReq) {
        //修改状态
        // 如果人数大于三人/六人  状态未已经完成
        // 如果人数小于三人/六人  截止大于当前日期 未已过期
        User user = this.userMapper.selectByUserId(detailReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Invitation invitation = this.invitationMapper.selectById(detailReq.getId());
        if (invitation != null) {
            if (detailReq.getType() == 1) {
                //助力话费 三人
                if ((invitation.getStatus() == 0 || invitation.getStatus() == 1) && invitation.getExpireTime().compareTo(new Date()) <= 0) {
                    invitation.setStatus(Constant.INVITATION_STATUS.HELP_OUT_TIME);
                } else if (invitation.getStatus() == Constant.INVITATION_STATUS.HELP_RECEIVED) {
                    invitation.setStatus(Constant.INVITATION_STATUS.HELP_RECEIVED);
                }
            } else if (detailReq.getType() == 2) {
                //助力宝箱 六人
                if (invitation.getStatus() == Constant.INVITATION_STATUS.HELP_RECEIVED) {
                    invitation.setStatus(Constant.INVITATION_STATUS.HELP_RECEIVED);
                } else {
                    if ((invitation.getStatus() == 0 || invitation.getStatus() == 1) && invitation.getExpireTime().compareTo(new Date()) <= 0) {
                        invitation.setStatus(Constant.INVITATION_STATUS.HELP_OUT_TIME);
                    }
                }
            }
            if (invitation.getExpireTime().getTime() - System.currentTimeMillis() > 0) {
                invitation.setRemainingTime((invitation.getExpireTime().getTime() -  System.currentTimeMillis()) / 1000);
            } else {
                invitation.setRemainingTime(0L);
            }
            this.invitationMapper.updateByPrimaryKeySelective(invitation);

            List<InvitationJoin> invitatiionJoinList = invitation.getInvitationJoinList();
            invitatiionJoinList.forEach(invitationJoin -> {
                User userJoin = this.userMapper.selectByUserId(invitationJoin.getJoinUserId());
                if (userJoin != null) {
                    invitationJoin.setNickName(userJoin.getNickname());
                    invitationJoin.setHeadImage(userJoin.getHeadImage());
                }
            });

        }
        return invitation;
    }


}