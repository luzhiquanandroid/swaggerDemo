package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 21:07
 * @description 领取助力和宝箱参数
 */
@Data
public class ReceiveReq {

    /**
     * 记录id
     */
    @NotNull(message = "记录id")
    private Integer recordId;

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;
    /**
     * 类型
     */
    @NotNull(message = "类型type不能为空")
    private Integer type;
}