package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:充值话费回调请求参数
 */
@Data
public class PhoneCallbackReq {
    @NotEmpty(message = "订单id不能为空")
    private String orderId;
    @NotNull(message = "状态不能为空")
    private Integer status;
}
