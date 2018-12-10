package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.InvitationReq;
import com.fotron.draw.bean.req.ReceiveReq;
import com.fotron.draw.bean.req.invitation.HomeInvitationListReq;
import com.fotron.draw.bean.resp.HomeInvitationLazyListResp;
import com.fotron.draw.bean.resp.HomeInvitationListResp;
import com.fotron.draw.bean.resp.InvitationResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.Invitation;
import com.fotron.draw.entity.InvitationJoin;
import com.fotron.draw.entity.TelephoneFare;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.AllRecordMapper;
import com.fotron.draw.mapper.InvitationJoinMapper;
import com.fotron.draw.mapper.InvitationMapper;
import com.fotron.draw.mapper.TelephoneFareMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.InvitationService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.SaveAllRecord;
import com.fotron.draw.utils.TimeUtils;
import com.fotrontimes.core.exception.BusinessException;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 20:30
 * @description
 */
@Service
@Slf4j
public class InvitationServiceImpl implements InvitationService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private AllRecordMapper allRecordMapper;

    @Resource
    private InvitationMapper invitationMapper;

    @Resource
    private TelephoneFareMapper telephoneFareMapper;
    @Resource
    private SaveAllRecord saveAllRecord;

    @Resource
    private InvitationJoinMapper invitationJoinMapper;
    @Value("${defaultImage}")
    private String defaultImage;


    /**
     * 拆开1，2助力红包，宝箱
     *
     * @param invitationReq
     */
    @Override
    public InvitationResp invitation(InvitationReq invitationReq) {
        //查询id是否存在 根据类型进行开出的奖  加入邀请表  修改所有记录表的is_open状态，更新时间
        //1:助力话费 2:宝箱
        Date nowDate = new Date();
        User user = this.userMapper.selectByUserId(invitationReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        int type = invitationReq.getType();
        Invitation invitation = new Invitation();
        invitation.setCreateTime(nowDate);
        invitation.setOpenTime(nowDate);
        invitation.setStatus(Constant.INVITATION_STATUS.INVITION_ING);
        invitation.setUserId(invitationReq.getUserId());
        invitation.setExpireTime(DateUtil.offsetHour(nowDate, 2));
        invitation.setType(type);
        int rows = this.invitationMapper.insertSelective(invitation);
        if (rows > 0) {
            //修改记录开状态
            this.saveAllRecord.saveRecord(invitationReq.getRecordId(), Constant.LUCK_IS_OPEN.OPEN);
        }
        InvitationResp invitationResp = new InvitationResp();
        invitationResp.setRefID(invitation.getId());
        if (invitation.getExpireTime().getTime() - System.currentTimeMillis() > 0) {
            invitationResp.setRemainingTime((invitation.getExpireTime().getTime() - System.currentTimeMillis()) / 1000);
        } else {
            invitationResp.setRemainingTime(0L);
        }
        return invitationResp;
    }

    /**
     * 领取1，2助力红包，宝箱
     *
     * @param receiveReq
     */
    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void receive(ReceiveReq receiveReq) {
        //1:助力话费 2:宝箱
        Date nowDate = new Date();
        User user = this.userMapper.selectByUserId(receiveReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Invitation invitation = this.invitationMapper.selectById(receiveReq.getRecordId());
        int type = receiveReq.getType();
        if (invitation != null) {
            TelephoneFare telephoneFare = new TelephoneFare();
            if (type == Constant.LUCK_TYPE.ONE && invitation.getInvitationNum() == Constant.INVITATION_NUMBER.THREE_NUM
                    && invitation.getStatus() != Constant.INVITATION_STATUS.HELP_RECEIVED) {
                //3个人 并且未被领取
                telephoneFare.setUserId(receiveReq.getUserId());
                telephoneFare.setRecordId(receiveReq.getRecordId());
                telephoneFare.setCreateTime(nowDate);
                telephoneFare.setUpdateTime(nowDate);
                telephoneFare.setType(Constant.TELPHONE_FARE_TYPE.HELP_PHONE);
                //设置多少金额
                telephoneFare.setTelephoneFare(Constant.INVITATION_MONEY.ONE);
                this.telephoneFareMapper.insertSelective(telephoneFare);
                //进行话费金额的添加
                BigDecimal beforeTelphoneFare = user.getTelephoneFare();
                BigDecimal afterTelphoneFare = beforeTelphoneFare.add(Constant.INVITATION_MONEY.ONE);
                if (afterTelphoneFare.compareTo(Constant.INVITATION_MONEY.NINETY_NINE) > 0) {
                    afterTelphoneFare = Constant.INVITATION_MONEY.NINETY_NINE;
                    //当计算话费超过了99元，抛出异常提醒用户，先提现在进行领取
                    //throw new BusinessException(MyErrorCode.PHONE_MORE_NINTYNINE);
                }
                user.setTelephoneFare(afterTelphoneFare);

                int i = this.userMapper.updateTelephoneFareById(receiveReq.getUserId(), afterTelphoneFare, beforeTelphoneFare, new Date());
                if (i != 1) {
                    throw new BusinessException(MyErrorCode.RECEIVED_ERROR);
                }
                //领取成功
                invitation.setStatus(Constant.INVITATION_STATUS.HELP_RECEIVED);
                this.invitationMapper.updateByPrimaryKeySelective(invitation);
            } else if (type == Constant.LUCK_TYPE.TWO && invitation.getInvitationNum() == Constant.INVITATION_NUMBER.SIX_NUM
                    && invitation.getStatus() != Constant.INVITATION_STATUS.HELP_RECEIVED) {
                //6个人 并且未被领取
                telephoneFare.setUserId(receiveReq.getUserId());
                telephoneFare.setCreateTime(nowDate);
                telephoneFare.setRecordId(receiveReq.getRecordId());
                telephoneFare.setUpdateTime(nowDate);
                telephoneFare.setType(Constant.TELPHONE_FARE_TYPE.HELP_TREASURE);
                //设置多少金额
                telephoneFare.setTelephoneFare(Constant.INVITATION_MONEY.THREE);
                this.telephoneFareMapper.insertSelective(telephoneFare);

                //进行话费金额的添加
                BigDecimal beforeTelphoneFare = user.getTelephoneFare();
                BigDecimal afterTelphoneFare = beforeTelphoneFare.add(Constant.INVITATION_MONEY.THREE);
                if (afterTelphoneFare.compareTo(Constant.INVITATION_MONEY.NINETY_NINE) > 0) {
                    afterTelphoneFare = Constant.INVITATION_MONEY.NINETY_NINE;
                    //当计算话费超过了99元，抛出异常提醒用户，先提现在进行领取
                    //throw new BusinessException(MyErrorCode.PHONE_MORE_NINTYNINE);
                }
                user.setTelephoneFare(afterTelphoneFare);
                int i = this.userMapper.updateTelephoneFareById(receiveReq.getUserId(), afterTelphoneFare, beforeTelphoneFare, new Date());
                if (i != 1) {
                    throw new BusinessException(MyErrorCode.RECEIVED_ERROR);
                }
                //领取成功
                invitation.setStatus(Constant.INVITATION_STATUS.HELP_RECEIVED);
                this.invitationMapper.updateByPrimaryKeySelective(invitation);

            } else {
                throw new BusinessException(MyErrorCode.RECEIVED);
            }
        }
    }

    @Override
    public List<HomeInvitationListResp> homeList(HomeInvitationListReq data) {
        List<HomeInvitationListResp> result = new ArrayList<>();
        Date now = new Date();
        Example example = new Example(InvitationJoin.class);
        example.createCriteria().andEqualTo("userId", data.getUserId())
                .andGreaterThanOrEqualTo("createTime", DateUtil.beginOfDay(now))
                .andLessThanOrEqualTo("createTime", DateUtil.endOfDay(now))
                .andEqualTo("type", Constant.TWO);
        example.setOrderByClause("id desc limit 10");
        List<InvitationJoin> invitationJoins = this.invitationJoinMapper.selectByCondition(example);
        for (InvitationJoin invitationJoin : invitationJoins) {
            HomeInvitationListResp resp = new HomeInvitationListResp();
            User user = this.userMapper.selectByUserId(invitationJoin.getJoinUserId());
            if (user != null) {
                BeanUtils.copyProperties(user, resp);
                resp.setHeadImage("".equals(user.getHeadImage()) ? this.defaultImage : user.getHeadImage());
                result.add(resp);
            }
        }
        return result;
    }

    @Override
    public List<HomeInvitationLazyListResp> homeLazyList(HomeInvitationListReq data) {
        List<HomeInvitationLazyListResp> result = new ArrayList<>();
        Date now = new Date();
        //当天时间往前 偏移一天
        DateTime dateTime = DateUtil.offsetDay(now, -1);
        Example example = new Example(InvitationJoin.class);
        example.createCriteria().andEqualTo("userId", data.getUserId())
                .andGreaterThanOrEqualTo("createTime", DateUtil.beginOfDay(dateTime))
                .andLessThanOrEqualTo("createTime", DateUtil.endOfDay(dateTime))
                .andEqualTo("type", Constant.TWO);
        example.setOrderByClause("id desc limit 10");
        List<InvitationJoin> invitationJoinList = this.invitationJoinMapper.selectByCondition(example);
        for (InvitationJoin invitationJoin : invitationJoinList) {
            HomeInvitationLazyListResp resp = new HomeInvitationLazyListResp();
            User user = this.userMapper.selectByUserId(invitationJoin.getJoinUserId());
            //如何最后一次登录时间  和当前时间不相同 就判定未是偷懒用户
            if (user != null && !user.getLastLoginTime().equals(TimeUtils.getDateFromDateTime(now))) {
                BeanUtils.copyProperties(user, resp);
                resp.setHeadImage("".equals(user.getHeadImage()) ? this.defaultImage : user.getHeadImage());
                resp.setNickname("".equals(user.getNickname()) ? "" : user.getNickname());
                result.add(resp);
            }
        }
        return result;
    }
}