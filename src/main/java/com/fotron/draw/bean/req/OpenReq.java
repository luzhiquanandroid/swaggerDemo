package com.fotron.draw.bean.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 18:43
 * @description 开红包参数
 */
@Data
@ApiModel
public class OpenReq {
    /**
     * 记录id
     */
    @NotNull(message = "记录id不能为空")
    @ApiModelProperty(value = "recordId",required = true)
    private Integer recordId;

    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(value = "userId",required = true)
    private String userId;
    /**
     * 红包类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "type",required = true)
    private Integer type;

}