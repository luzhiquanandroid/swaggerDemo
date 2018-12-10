package com.fotron.draw.controller;


import com.fotron.draw.bean.req.BubbleReq;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.resp.ReceiveResp;
import com.fotron.draw.service.ReceiveService;
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

/**
 * @author luzhiquan
 * @createTime 2018/11/30 15:51
 * @description
 */
@RequestMapping("api/receive")
@RestController
public class ReceiveController extends BaseController {
    @Resource
    private ReceiveService receiveService;

    /**
     * 得到领取的信息
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("getInfo")
    public ApiResponse getInfo(@RequestBody @Validated ApiRequest<UserReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        ReceiveResp receive = this.receiveService.getReceiveInfo(data.getData());
        result.put("data", receive);
        return result;
    }


    /**
     * 领取2小时钻石
     *
     * @return
     */
    @PostMapping("receive")
    @SignVerification
    public ApiResponse receive(@RequestBody @Validated ApiRequest<UserReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        this.receiveService.getReceive(data.getData());
        return result;
    }

    /**
     * 戳气泡获取钻石
     *
     * @return
     */
    @PostMapping("bubble")
    @SignVerification
    public ApiResponse bubble(@RequestBody @Validated ApiRequest<BubbleReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        this.receiveService.bubble(data.getData());
        return result;
    }
}