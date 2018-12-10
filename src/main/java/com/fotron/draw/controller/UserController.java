package com.fotron.draw.controller;


import com.fotron.draw.bean.req.DiamondListReq;
import com.fotron.draw.bean.req.InvitationReq;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.invitation.NickNameReq;
import com.fotron.draw.bean.resp.DiamondListResp;
import com.fotron.draw.bean.resp.UserResp;
import com.fotron.draw.entity.Invitation;
import com.fotron.draw.service.UserService;
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
import java.util.Map;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
@RequestMapping("api/user")
@RestController
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    @PostMapping("getUserInfo")
    public ApiResponse getUserInfo(@RequestBody @Validated ApiRequest<UserReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = super.createSuccessResult();
        UserResp userInfo = this.userService.getUserInfo(data.getData());
        result.put("data", userInfo);
        return result;
    }


}