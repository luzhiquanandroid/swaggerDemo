package com.fotron.draw.bean.req;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: yutong
 * @createDate: 2018/7/5
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class SubReserveMsgReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotEmpty(message = "formId不能为空")
    private String formId;



    @NotNull
    @Range(min = 1, max = 2)
    private Integer remindType;
    /**
     * 3  赚好运
     */
     private Integer boxType;
}
