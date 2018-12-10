package com.fotron.draw.service;

import com.fotron.draw.bean.req.PhoneCallbackReq;
import com.fotron.draw.bean.req.RechargePhoneReq;
import com.fotron.draw.bean.req.RechargeRecordReq;
import com.fotron.draw.bean.resp.FalsehoodListResp;
import com.fotron.draw.entity.RechargeRecord;

import java.util.List;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public interface PhoneService {

    /**
     * 充值话费
     *
     * @param data
     */
    void recharge(RechargePhoneReq data);

    /**
     * 话费充值后回调
     *
     * @param data
     */
    void callback(PhoneCallbackReq data);

    /**
     * 随机抽取20个用户
     *
     * @return
     */
    List<FalsehoodListResp> falsehood();

    Map list(RechargeRecordReq rechargeRecordReq);
}
