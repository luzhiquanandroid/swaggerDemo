package com.fotron.draw.service;

import com.fotron.draw.bean.req.InvitationReq;
import com.fotron.draw.bean.req.ReceiveReq;
import com.fotron.draw.bean.req.invitation.HomeInvitationListReq;
import com.fotron.draw.bean.resp.HomeInvitationLazyListResp;
import com.fotron.draw.bean.resp.HomeInvitationListResp;
import com.fotron.draw.bean.resp.InvitationResp;

import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 20:27
 * @description 邀请
 */
public interface InvitationService {
    InvitationResp invitation(InvitationReq invitationReq);

    void receive(ReceiveReq receiveReq);

    /**
     * 查询首页邀请好友列表
     *
     * @param data
     * @return
     */
    List<HomeInvitationListResp> homeList(HomeInvitationListReq data);

    /**
     * 查询偷懒好友列表
     * @param data
     * @return
     */
    List<HomeInvitationLazyListResp> homeLazyList(HomeInvitationListReq data);
}
