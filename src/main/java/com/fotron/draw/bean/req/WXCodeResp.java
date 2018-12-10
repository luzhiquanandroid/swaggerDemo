package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/12/4
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class WXCodeResp {

    @NotEmpty(message = "friend不能为空")
    private String friend;

    @NotNull(message = "refId不能为空")
    private Integer refId;

}
