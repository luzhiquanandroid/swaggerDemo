package com.fotron.draw.controller;

import com.alibaba.fastjson.JSONObject;
import com.fotron.draw.bean.req.WXCodeResp;
import com.fotron.draw.bean.req.WxLoginReq;
import com.fotron.draw.bean.resp.UserInfoResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.core.config.WxComponent;
import com.fotron.draw.service.UserService;
import com.fotron.draw.utils.IdWorker;
import com.fotron.draw.utils.QiniuUtil;
import com.fotron.draw.utils.RedisUtil;
import com.fotrontimes.core.exception.BusinessException;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author: yutong
 * @createDate: 2018/6/27
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@RestController
@RequestMapping("/api/wx")
@Slf4j
public class WxController extends BaseController {


    @Resource
    private UserService userService;
    @Resource
    private WxComponent wxComponent;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private QiniuUtil qiniuUtil;

    /**
     * 登录/授权
     *
     * @param request
     * @param br
     * @return
     */
    @PostMapping("/login")
    public ApiResponse loginV1(@RequestBody @Valid ApiRequest<WxLoginReq> request, BindingResult br) {
        checkParameters(br);
        ApiResponse response = ApiResponse.buildSuccess();
        UserInfoResp result = userService.save(request);
        if (result == null) {
            log.error("授权code失效");
            throw new BusinessException(MyErrorCode.WX_CODE_INVALID);
        }
        response.put("userId", result.getUserId());
        response.put("flag", result.getFlag());
        return response;
    }


    @PostMapping("/wxaCode")
    public ApiResponse getWxaCode(@RequestBody @Valid ApiRequest<WXCodeResp> request, BindingResult br) {
        checkParameters(br);
        ApiResponse response = ApiResponse.buildSuccess();
        String codeUrl = this.redisUtil.get(request.getData().getFriend() + request.getData().getRefId());
        if (codeUrl != null) {
            response.put("data", codeUrl);
        } else {
            String tokenAccess = this.wxComponent.getTokenAccess();
            byte[] s;
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + tokenAccess;
            JSONObject params = new JSONObject();
            // 新增source=3表明好友来源于小程序码
            params.put("scene", request.getData().getRefId());
            params.put("page", "pages/index/xiangYaoqing/xiangYaoqing");
            params.put("width", 280);
            s = restTemplate.postForObject(url, params.toJSONString(), byte[].class);
            codeUrl = qiniuUtil.uploadWxaCodeImg(s, IdWorker.getUid());
            //存入redis
            redisUtil.setEx(request.getData().getFriend() + request.getData().getRefId(), codeUrl, 2, TimeUnit.HOURS);
            response.put("data", codeUrl);
        }

        return response;

    }

}
