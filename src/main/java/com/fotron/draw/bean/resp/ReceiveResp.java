package com.fotron.draw.bean.resp;

import lombok.Data;

/**
 * @author luzhiquan
 * @createTime 2018/11/30 17:31
 * @description 领取的返回信息
 */
@Data
public class ReceiveResp {
    /**
     * 状态
     */
    private Integer status;
    /**
     * 剩余时间
     */
    private Long  remainingTime;
}