package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.SubReserveMsgReq;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.SubReserveMsg;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.SubReserveMsgMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.SubReserveMsgService;
import com.fotron.draw.utils.RedisTool;
import com.fotrontimes.core.exception.BusinessException;
import com.xiaoleilu.hutool.date.DateException;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: niuhuan
 * @createDate: 2018/12/1
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Service
@Slf4j
public class SubReserveMsgServiceImpl implements SubReserveMsgService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTool redisTool;
    @Resource
    private SubReserveMsgMapper subReserveMsgMapper;

    @Override
    public void report(SubReserveMsgReq req) {
        User user = userMapper.selectByUserId(req.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        SubReserveMsg newReq = new SubReserveMsg();
        BeanUtils.copyProperties(req, newReq);
        newReq.setUserId(user.getId());
        newReq.setOpenId(user.getOpenId());
        String lockKey = "lock:reserve:" + req.getUserId();
        String requestId = UUID.randomUUID().toString();
        try {
            if (redisTool.tryGetDistributedLock(lockKey, requestId)) {
                /**
                 * 3是赚好运 5:赚好运的cs
                 */
                this.shangbao(user, req, 3, 5);
            } else {
                log.error("并发错误，无法获取到分布式锁");
            }
        } catch (DateException e) {
            log.error("时间格式不正确", e);
        } finally {
            redisTool.releaseDistributedLock(lockKey, requestId);
        }
    }

    private void shangbao(User user, SubReserveMsgReq req, Integer boxType, Integer cs) {
        Date now = new Date();
        String date = DateUtil.format(DateUtil.offsetDay(now, 1), "yyyy-MM-dd HH");
        Date reserveTime = DateUtil.parse(date, "yyyy-MM-dd HH");
        Example condition = new Example(SubReserveMsg.class);
        condition.createCriteria().andEqualTo("userId", user.getId())
                .andEqualTo("reserveType", req.getRemindType() == 2 ? 3 : req.getRemindType())
                .andGreaterThan("createTime", DateUtil.beginOfDay(now))
                .andEqualTo("boxType", boxType);
        condition.setOrderByClause(" create_time desc");
        List<SubReserveMsg> exists = subReserveMsgMapper.selectByCondition(condition);
        if (exists.isEmpty()) {
            SubReserveMsg reserveMsg = new SubReserveMsg();
            reserveMsg.setUserId(user.getId());
            reserveMsg.setOpenId(user.getOpenId());
            reserveMsg.setFormId(req.getFormId());
            reserveMsg.setReserveType(req.getRemindType() == 2 ? 3 : req.getRemindType());
            reserveMsg.setSendCount(StringUtils.isEmpty(req.getFormId()) ? 0 : 1);
            reserveMsg.setReserveTime(reserveTime);
            reserveMsg.setCreateTime(now);
            reserveMsg.setBoxType(boxType);
            //有效7天formid
            reserveMsg.setExpireTime(DateUtil.offsetDay(now, 7));
            subReserveMsgMapper.insertSelective(reserveMsg);
        } else {
            SubReserveMsg newest = exists.get(0);
            if (reserveTime.compareTo(newest.getReserveTime()) != 0) {
                SubReserveMsg modify = new SubReserveMsg();
                modify.setId(newest.getId());
                modify.setReserveTime(reserveTime);
                modify.setSendCount(StringUtils.isEmpty(req.getFormId()) ? 0 : 1);
                modify.setFormId(req.getFormId());
                modify.setExpireTime(DateUtil.offsetDay(now, 7));
                subReserveMsgMapper.updateByPrimaryKeySelective(modify);
            }
        }

    }
}
