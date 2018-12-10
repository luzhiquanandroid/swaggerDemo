package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: niuhuan
 * @createDate: 2018/11/20
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class GetDiamondReq {
    @NotEmpty(message = "unionId不能为空")
    private String unionId;
}
