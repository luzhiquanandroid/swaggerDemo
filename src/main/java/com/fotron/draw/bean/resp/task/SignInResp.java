package com.fotron.draw.bean.resp.task;

import lombok.Data;

/**
 * @author luzhiquan
 * @createTime 2018/12/1 16:46
 * @description 签到画布
 */
@Data
public class SignInResp {

    private Integer days;

    private boolean isSignIn;

    private Integer diamond;
}