package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.BubbleReq;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.resp.ReceiveResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.Receive;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.ReceiveMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.ReceiveService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.DiamondUtils;
import com.fotrontimes.core.exception.BusinessException;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.fotron.draw.utils.Constant.DIAMOND_TYPE.UP;
import static com.fotron.draw.utils.Constant.DIAMOND_UPORDOWN.DIAMOND_RECEIVE;

/**
 * @author luzhiquan
 * @createTime 2018/11/30 15:41
 * @description
 */
@Service
public class ReceiveServiceImpl implements ReceiveService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ReceiveMapper receiveMapper;

    @Resource
    private DiamondUtils diamondUtils;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public ReceiveResp getReceiveInfo(UserReq userReq) {
        ReceiveResp receiveResp = new ReceiveResp();
        Date nowDate = new Date();
        User user = this.userMapper.selectByUserId(userReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Receive receive = this.receiveMapper.selectByUserId(user.getUserId());
        if (receive == null) {
            receive = new Receive();
            receive.setUserId(userReq.getUserId());
            receive.setCreateTime(nowDate);
            receive.setExpireTime(DateUtil.offsetHour(nowDate, 2));
            receive.setUpdateTime(nowDate);
            receive.setStatus(Constant.RECEIVE.NOT_CAN);
            this.receiveMapper.insertSelective(receive);
        }
        if (receive.getExpireTime().getTime() - System.currentTimeMillis() > 0) {
            receiveResp.setRemainingTime((receive.getExpireTime().getTime() - System.currentTimeMillis()) / 1000);
            receiveResp.setStatus(Constant.RECEIVE.NOT_CAN);
            receive.setStatus(Constant.RECEIVE.NOT_CAN);
        } else {
            receiveResp.setRemainingTime(0L);
            receiveResp.setStatus(Constant.RECEIVE.CAN);
            receive.setStatus(Constant.RECEIVE.CAN);
        }
        this.receiveMapper.updateByPrimaryKeySelective(receive);
        return receiveResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void getReceive(UserReq userReq) {
        Date nowDate = new Date();
        User user = this.userMapper.selectByUserId(userReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Receive receive = this.receiveMapper.selectByUserId(user.getUserId());
        if (receive == null) {
            throw new BusinessException(MyErrorCode.RECEIVED_GET_ERROR);
        }
        if (receive.getStatus() != Constant.RECEIVE.CAN) {
            throw new BusinessException(MyErrorCode.RECEIVED_NOT_CAN_ERROR);
        }
        receive.setUpdateTime(nowDate);
        //设置偏移时间2小时
        receive.setExpireTime(DateUtil.offsetHour(nowDate, 2));
        receive.setStatus(Constant.RECEIVE.NOT_CAN);
        int i = this.receiveMapper.updateByPrimaryKeySelective(receive);
        if (i <= 0) {
            throw new BusinessException(MyErrorCode.RECEIVED_ERROR);
        }
        //存入钻石记录
        this.diamondUtils.updateDiamond(user, Constant.FIVE, UP, DIAMOND_RECEIVE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void bubble(BubbleReq data) {
        User user = this.userMapper.selectByUserId(data.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Receive receive = this.receiveMapper.selectByUserIdAndRecId(data.getUserId(), data.getReceiveId());
        if (receive == null) {
            throw new BusinessException(MyErrorCode.BUBBLE_IS_NULL);
        }
        if (receive.getStatus() == Constant.THREE) {
            throw new BusinessException(MyErrorCode.BUBBLE_REPEAT_RECEIVE);
        }
        receive.setStatus(Constant.THREE);
        this.receiveMapper.updateByPrimaryKeySelective(receive);
        //存入钻石记录
        this.diamondUtils.updateDiamond(user, Constant.ELEVEN, UP, receive.getDiamond());
    }
}
