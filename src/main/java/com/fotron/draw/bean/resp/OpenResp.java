package com.fotron.draw.bean.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 19:40
 * @description 开抽话费返回值
 */
@Data
public class OpenResp {
    /**
     * 开出话费金额
     */
    private BigDecimal mount;
    /**
     * 开出幸运奖钻石
     */
    private Integer diamondNum;
}