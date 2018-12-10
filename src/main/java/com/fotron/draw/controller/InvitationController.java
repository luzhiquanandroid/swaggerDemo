package com.fotron.draw.controller;


import com.fotron.draw.bean.req.InvitationReq;
import com.fotron.draw.bean.req.ReceiveReq;
import com.fotron.draw.bean.req.invitation.HomeInvitationListReq;
import com.fotron.draw.bean.resp.HomeInvitationLazyListResp;
import com.fotron.draw.bean.resp.HomeInvitationListResp;
import com.fotron.draw.bean.resp.InvitationResp;
import com.fotron.draw.service.InvitationService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotrontimes.utils.verification.annotation.SignVerification;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 20:51
 * @description
 */
@RequestMapping("api/invitation")
@RestController
public class InvitationController extends BaseController {
    @Resource
    private InvitationService invitationService;

    /**
     * 点击开1，2助力话费和宝箱
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("open")
    public ApiResponse open(@RequestBody @Validated ApiRequest<InvitationReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        InvitationResp invitation = this.invitationService.invitation(data.getData());
        result.put("data", invitation);
        return result;
    }


    /**
     * 领取
     *
     * @return
     */
    @PostMapping("receive")
    @SignVerification
    public ApiResponse receive(@RequestBody @Validated ApiRequest<ReceiveReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        this.invitationService.receive(data.getData());
        return result;
    }

    /**
     * 首页邀请好友的列表
     *
     * @return
     */
    @PostMapping("homeList")
    public ApiResponse homeList(@RequestBody @Validated ApiRequest<HomeInvitationListReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        List<HomeInvitationListResp> list = this.invitationService.homeList(data.getData());
        result.put("data", list);
        return result;
    }

    /**
     * 偷懒好友列表
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("homeLazyList")
    public ApiResponse homeLazyList(@RequestBody @Validated ApiRequest<HomeInvitationListReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        List<HomeInvitationLazyListResp> homeInvitationLazyListResps = this.invitationService.homeLazyList(data.getData());
        result.put("data", homeInvitationLazyListResps);
        return result;
    }


}