package com.fotron.draw.bean.req;

import lombok.Data;

@Data
public class TelRechargeReq {

	/**
	 * 手机号
	 */
	private String phoneNum;
	
	/**
	 * 金额（元）
	 */
	private Integer amount;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 调用方
	 */
	private String subChannel;
	
	/**
	 * 调用方回调地址
	 */
	private String callbackUrl;
}
