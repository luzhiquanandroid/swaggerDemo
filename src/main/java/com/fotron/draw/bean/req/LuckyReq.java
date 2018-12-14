package com.fotron.draw.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:28
 * @description 抽奖参数
 */
@Data
@ApiModel(value = "抽奖请求体")
public class LuckyReq {
    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(value = "用户ID",required = true)
    private String userId;
}