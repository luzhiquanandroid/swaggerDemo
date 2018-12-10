package com.fotron.draw.service.impl;

import com.fotrontimes.utils.verification.annotation.SignVerification;
import com.fotron.draw.bean.req.PhoneCallbackReq;
import com.fotron.draw.bean.req.RechargePhoneReq;
import com.fotron.draw.bean.req.RechargeRecordReq;
import com.fotron.draw.bean.req.TelRechargeReq;
import com.fotron.draw.bean.resp.FalsehoodListResp;
import com.fotron.draw.bean.resp.TelRechargeResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.FalsehoodUser;
import com.fotron.draw.entity.RechargeRecord;
import com.fotron.draw.entity.TelephoneFare;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.FalsehoodUserMapper;
import com.fotron.draw.mapper.RechargeRecordMapper;
import com.fotron.draw.mapper.TelephoneFareMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.PhoneService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.OrderCodeCreateUtil;
import com.fotrontimes.core.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Value("${phone.recharge}")
    private String phoneUrl;
    @Value("${phone.callbackUrl}")
    private String callbackUrl;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private FalsehoodUserMapper falsehoodUserMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private TelephoneFareMapper telephoneFareMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void recharge(RechargePhoneReq data) {
        Date now = new Date();
        User user = this.userMapper.selectByUserId(data.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        int fare = data.getAmount().compareTo(user.getTelephoneFare());
        if (fare>0) {
            log.error("余额不足");
            throw new BusinessException(MyErrorCode.AMOUNT_NOT_ENOUGH);
        }
        Example example = new Example(RechargeRecord.class);
        example.createCriteria().andEqualTo("userId", data.getUserId())
                .andEqualTo("status", 0);
        List<RechargeRecord> rechargeRecords = this.rechargeRecordMapper.selectByCondition(example);
        if (rechargeRecords.size() > 0) {
            log.error("重复充值提交:{}", user.getUserId());
            throw new BusinessException(MyErrorCode.REPEAT_OPERATE);
        }
        //增加充值记录
        Example example1 = new Example(RechargeRecord.class);
        example1.createCriteria().andEqualTo("userId", data.getUserId());
        int count = this.rechargeRecordMapper.selectCountByCondition(example1);
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setAmount(data.getAmount().intValue());
        rechargeRecord.setStatus(0);
        rechargeRecord.setUpdateTime(now);
        rechargeRecord.setCreateTime(now);
        rechargeRecord.setUserId(user.getUserId());
        rechargeRecord.setOrderId(OrderCodeCreateUtil.getOrderId(user.getId() + "", count));
        rechargeRecord.setPhone(data.getPhone());
        this.rechargeRecordMapper.insertSelective(rechargeRecord);
        //调用充值话费接口
        TelRechargeReq phoneReq = new TelRechargeReq();
        phoneReq.setAmount(data.getAmount().intValue());
        phoneReq.setOrderId(rechargeRecord.getOrderId());
        phoneReq.setPhoneNum(data.getPhone());
        phoneReq.setSubChannel("1");
        phoneReq.setCallbackUrl(this.callbackUrl);
        TelRechargeResp telRechargeResp = this.restTemplate.postForObject(this.phoneUrl, phoneReq, TelRechargeResp.class);
        log.info("话费充值接口返回:{}", telRechargeResp);
        if (telRechargeResp != null && !"SUCCESS".equals(telRechargeResp.getResultCode())) {
            //调用失败直接修改订单状态为失败
            int row = this.rechargeRecordMapper.updateStatus(rechargeRecord.getId(), Constant.NO);
            if (row != 1) {
                log.error("修改话费订单状态失败");
                throw new BusinessException(MyErrorCode.REPEAT_OPERATE);
            }
        }
        //调用成功扣除话费
        BigDecimal newRechargeRecord = user.getTelephoneFare().subtract(new BigDecimal(rechargeRecord.getAmount() + ""));
        int rows = this.userMapper.updateTelephoneFareById(user.getUserId(), newRechargeRecord, user.getTelephoneFare(),new Date());
        if (rows != 1) {
            log.error("话费充值失败更新资产失败用户id:{}", user.getId());
            throw new BusinessException(MyErrorCode.UPDATE_AMOUNT_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void callback(PhoneCallbackReq data) {
        RechargeRecord rechargeRecord = this.rechargeRecordMapper.selectByOrderId(data.getOrderId());
        if (rechargeRecord != null) {
            rechargeRecord.setStatus(data.getStatus());
            int row = this.rechargeRecordMapper.updateStatus(rechargeRecord.getId(), data.getStatus());
            if (row != 1) {
                log.error("话费充值回调了多次");
                throw new BusinessException(MyErrorCode.REPEAT_OPERATE);
            }
            User user = this.userMapper.selectByUserId(rechargeRecord.getUserId());
            if (data.getStatus() == Constant.NO) {
                //充值失败返还消耗的话费
                BigDecimal newRechargeRecord = user.getTelephoneFare().add(new BigDecimal(rechargeRecord.getAmount() + ""));
                int rows = this.userMapper.updateTelephoneFareById(user.getUserId(), newRechargeRecord, user.getTelephoneFare(),new Date());
                if (rows != 1) {
                    log.error("话费充值失败更新资产失败用户id:{}", user.getId());
                    throw new BusinessException(MyErrorCode.UPDATE_AMOUNT_FAIL);
                }
            }else{
                //增加消耗话费记录
                TelephoneFare telephoneFare = new TelephoneFare();
                telephoneFare.setCreateTime(new Date());
                telephoneFare.setUpdateTime(new Date());
                telephoneFare.setRecordId(rechargeRecord.getId());
                telephoneFare.setTelephoneFare(new BigDecimal(rechargeRecord.getAmount()));
                telephoneFare.setType(Constant.TWO);
                telephoneFare.setUserId(user.getUserId());
                this.telephoneFareMapper.insertSelective(telephoneFare);
            }



        }

    }

    @Override
    public List<FalsehoodListResp> falsehood() {
        List<FalsehoodUser> list = this.falsehoodUserMapper.selectByRanod20();
        List<FalsehoodListResp> result = new ArrayList<>();
        for (FalsehoodUser falsehoodUser : list) {
            FalsehoodListResp resp = new FalsehoodListResp();
            BeanUtils.copyProperties(falsehoodUser, resp);
            int count = com.xiaoleilu.hutool.util.RandomUtil.randomInt(1, 3);
            if (count == 1) {
                resp.setAmount(1);
            } else if (count == 2) {
                resp.setAmount(5);
            } else if (count == 3) {
                resp.setAmount(10);
            }
            result.add(resp);
        }
        return result;
    }

    @Override
    public Map list(RechargeRecordReq rechargeRecordReq) {
        Map map = new HashMap();
        User user = this.userMapper.selectByUserId(rechargeRecordReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        PageHelper.startPage(rechargeRecordReq.getCurrentPage(), rechargeRecordReq.getPageSize());
        List<RechargeRecord> rechargeRecordList = this.rechargeRecordMapper.selectByUserIdList(user.getUserId());
        map.put("data", rechargeRecordList);
        map.put("totalCount", rechargeRecordList.size());
        return map;
    }
}
