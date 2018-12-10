package com.fotron.draw.bean.req.task;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/12/1 13:52
 * @description 签到请求
 */
@Data
public class SignInReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

}