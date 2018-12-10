package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author luzhiquan
 * @createTime 2018/11/21 14:34
 * @description 获取用户信息，请求参数
 */
@Data
public class UserReq {
    @NotEmpty(message = "用户userId不能为空")
    private String userId;
}