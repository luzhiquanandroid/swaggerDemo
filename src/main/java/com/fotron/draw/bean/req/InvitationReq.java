package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 20:29
 * @description 助力话费宝箱请求参数
 */
@Data
public class InvitationReq {
    /**
     * 记录id
     */
    @NotNull(message = "记录id")
    private Integer recordId;

    @NotEmpty(message = "用户id不能为空")
    private String userId;
    /**
     * 红包类型
     */
    @NotNull(message = "类型不能为空")
    private Integer type;
}