package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class DiamondListReq {

    @NotEmpty(message = "用户id不能为空")
    private String userId;
    @NotNull(message = "当前页currentPage不能为空")
    private Integer currentPage;
    @NotNull(message = "每页pageSize不能为空")
    private Integer pageSize;

}
