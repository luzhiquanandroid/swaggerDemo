package com.fotron.draw.bean.req;

import lombok.Data;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class PhoneReq {
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 金额
     */
    private Integer amount;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 调用方 6
     */
    private String subChannel;
    /**
     * 回调地址
     */
    private String callbackUrl;
}
