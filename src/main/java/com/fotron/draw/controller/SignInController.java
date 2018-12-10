package com.fotron.draw.controller;

import com.fotrontimes.utils.verification.annotation.SignVerification;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.SignInReq;
import com.fotron.draw.bean.resp.task.SignInResp;
import com.fotron.draw.service.SignInService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 签到相关
 */
@RequestMapping("api/sign")
@RestController
public class SignInController extends BaseController {
    @Resource
    private SignInService signInService;

    /**
     * 签到画布
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("signInList")
    public ApiResponse signInList(@RequestBody @Validated ApiRequest<UserReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        List<SignInResp> signInResps = this.signInService.signInList(data.getData());
        result.put("data", signInResps);
        return result;
    }


    /**
     * 签到
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("signIn")
    @SignVerification
    public ApiResponse signIn(@RequestBody @Validated ApiRequest<SignInReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        List<SignInResp> signInResps = this.signInService.signIn(data.getData());
        result.put("data", signInResps);
        return result;
    }
}
