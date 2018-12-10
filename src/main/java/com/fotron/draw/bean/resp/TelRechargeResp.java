package com.fotron.draw.bean.resp;

import lombok.Data;

@Data
public class TelRechargeResp {
	
	/**
	 * 返回码   FAILED:调用失败   SUCCESS：调用成功    （非充值成功）
	 */
	private String resultCode;
	
	/**
	 * 返回描述
	 */
	private String resultMsg;

}
