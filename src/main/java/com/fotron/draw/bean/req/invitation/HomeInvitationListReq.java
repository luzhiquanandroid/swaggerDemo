package com.fotron.draw.bean.req.invitation;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:首页邀请好友的列表请求类
 */
@Data
public class HomeInvitationListReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

}
