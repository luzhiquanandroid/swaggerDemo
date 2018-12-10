package com.fotron.draw.controller;


import com.fotron.draw.bean.req.PhoneCallbackReq;
import com.fotron.draw.bean.req.RechargePhoneReq;
import com.fotron.draw.bean.req.RechargeRecordReq;
import com.fotron.draw.bean.resp.FalsehoodListResp;
import com.fotron.draw.service.PhoneService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotrontimes.utils.verification.annotation.SignVerification;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:话费充值
 */
@RequestMapping("/api/phone")
@RestController
public class PhoneController extends BaseController {

    @Resource
    private PhoneService phoneService;

    /**
     * 充值话费//调用步数宝的充值接口
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("/recharge")
    @SignVerification
    public ApiResponse list(@RequestBody @Validated ApiRequest<RechargePhoneReq> data, BindingResult check) {
        checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        phoneService.recharge(data.getData());
        return result;
    }

    /**
     * 充值话费回调
     *
     * @param status
     * @param orderId
     * @return
     */
    @GetMapping("/callback")
    public ApiResponse callback(@NotNull(message = "状态不能为空") @RequestParam("status") Integer status, @NotEmpty(message = "订单id不能为空") @RequestParam("orderId") String orderId) {
        ApiResponse result = ApiResponse.buildSuccess();
        PhoneCallbackReq req = new PhoneCallbackReq();
        req.setOrderId(orderId);
        req.setStatus(status);

        this.phoneService.callback(req);

        return result;
    }

    /**
     * 谎言
     *
     * @return
     */
    @GetMapping("/falsehood")
    public ApiResponse falsehood() {
        ApiResponse result = ApiResponse.buildSuccess();

        List<FalsehoodListResp> list = phoneService.falsehood();
        result.put("data", list);
        return result;
    }

    @PostMapping("/record")
    public ApiResponse recordList(@RequestBody @Validated ApiRequest<RechargeRecordReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = super.createSuccessResult();
        try {
            Map resp = this.phoneService.list(data.getData());
            result.put("data", resp.get("data"));
            result.put("totalCount", resp.get("totalCount"));
        } catch (Exception e) {
            handleException(e, result);
        }
        return result;
    }

}
