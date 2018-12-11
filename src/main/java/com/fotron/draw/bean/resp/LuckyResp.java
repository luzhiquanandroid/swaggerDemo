package com.fotron.draw.bean.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:31
 * @description 抽奖的返回值
 */
@Data
@ApiModel(value = "开奖返回值")
public class LuckyResp {
    /**
     * 抽奖类型
     */
    @ApiModelProperty(value = "抽奖类型")
    private Integer type;
    /**
     * 抽奖存入的id
     */
    @ApiModelProperty(value = "抽奖记录id")
    private Integer id;

    /**
     * 抽到钻石数量
     */
    @ApiModelProperty(value = "抽奖的钻石数量，没有返回null")
    private Integer diamondNum;
}