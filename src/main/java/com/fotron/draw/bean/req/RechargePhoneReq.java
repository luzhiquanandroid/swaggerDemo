package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:充值话费请求类
 */
@Data
public class RechargePhoneReq {
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "用户id不能为空")
    private String userId;
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

}
