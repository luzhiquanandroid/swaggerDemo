package com.fotron.draw.service;

import com.fotron.draw.bean.req.BubbleReq;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.resp.ReceiveResp;

/**
 * @author luzhiquan
 * @createTime 2018/11/30 15:39
 * @description
 */
public interface ReceiveService {
    /**
     * 根据用户 获取领取
     *
     * @param userReq
     * @return
     */
    ReceiveResp getReceiveInfo(UserReq userReq);

    /**
     * 根据用户  领取钻石
     *
     * @param userReq
     */
    void getReceive(UserReq userReq);

    /**
     * 戳汽包领取钻石
     *
     * @param data
     */
    void bubble(BubbleReq data);
}
