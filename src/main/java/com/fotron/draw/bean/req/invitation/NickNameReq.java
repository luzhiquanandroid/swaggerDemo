package com.fotron.draw.bean.req.invitation;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:通过邀请id 获取userid  在获取昵称
 */
@Data
public class NickNameReq {
    @NotNull(message = "邀请id不能为空")
    private Integer refId;

}
