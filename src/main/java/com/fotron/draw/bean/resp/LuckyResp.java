package com.fotron.draw.bean.resp;

import lombok.Data;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:31
 * @description 抽奖的返回值
 */
@Data
public class LuckyResp {
    /**
     * 抽奖类型
     */
    private Integer type;
    /**
     * 抽奖存入的id
     */
    private Integer id;

    /**
     * 抽到钻石数量
     */
    private Integer diamondNum;
}