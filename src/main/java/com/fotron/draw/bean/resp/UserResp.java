package com.fotron.draw.bean.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luzhiquan
 * @createTime 2018/11/21 14:33
 * @description 用户话费金额，钻石总数量
 */
@Data
public class UserResp {
    private BigDecimal allMount;
    private Long allDiamond;

    private String nickName = "";
    private String headImage;
    private Integer taskTotal = 0;

    private int level = 1;
    /**
     * 到升级 已获取了多少钻石
     */
    private int diamondCount = 0;
}