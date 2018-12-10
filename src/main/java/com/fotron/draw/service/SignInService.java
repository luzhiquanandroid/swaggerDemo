package com.fotron.draw.service;

import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.SignInReq;
import com.fotron.draw.bean.resp.task.SignInResp;

import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/12/01 15:27
 * @description 签到
 */
public interface SignInService {
    /**
     * 签到
     *
     * @param signInReq
     */
    List<SignInResp> signIn(SignInReq signInReq);

    /**
     * 显示画布
     */
    List<SignInResp> signInList(UserReq userReq);
}
