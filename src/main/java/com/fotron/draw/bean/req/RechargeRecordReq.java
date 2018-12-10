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
public class RechargeRecordReq {
    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotNull(message = "当前页currentPage不能为空")
    private Integer currentPage;
    @NotNull(message = "每页pageSize不能为空")
    private Integer pageSize;
}