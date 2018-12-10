package com.fotron.draw.core;

import com.alibaba.fastjson.JSONObject;
import com.fotron.draw.bean.resp.SessionResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: niuhuan
 * @createDate: 2018/11/6
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Component
@Slf4j
public class LoginComponent {
    @Resource
    private RestTemplate restTemplate;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.secret}")
    private String secret;

    private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public SessionResp getUserInfo(String code) {
        String response = restTemplate.getForObject(String.format(SESSION_URL, appId, secret, code), String.class);
        SessionResp resp = JSONObject.parseObject(response, SessionResp.class);
        if (resp != null && resp.getOpenid() != null) {
            return resp;
        }
        return null;
    }

}

