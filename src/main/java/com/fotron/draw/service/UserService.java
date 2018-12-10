package com.fotron.draw.service;

import com.fotron.draw.bean.req.DiamondListReq;
import com.fotron.draw.bean.req.invitation.NickNameReq;
import com.fotron.draw.bean.resp.DiamondListResp;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.WxLoginReq;
import com.fotron.draw.bean.resp.UserInfoResp;
import com.fotron.draw.bean.resp.UserResp;
import com.fotrontimes.core.web.ApiRequest;

import java.util.List;
import java.util.Map;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:32
 * @description
 */
public interface UserService {
    /**
     * 授权存储用户
     *
     * @param data
     * @return
     */
    UserInfoResp save(ApiRequest<WxLoginReq> data);

    /**
     * 获取用户基本信息
     *
     * @param userReq
     * @return
     */
    UserResp getUserInfo(UserReq userReq);


}
