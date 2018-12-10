package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/21 10:17
 * @description
 */
@Data
public class DetailReq {
    @NotNull(message = "唯一id不能为空")
    private Integer id;
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