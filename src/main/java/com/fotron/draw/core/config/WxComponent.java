package com.fotron.draw.core.config;

import com.fotron.draw.bean.resp.AccessTokenResp;
import com.fotron.draw.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: yutong
 * @createDate: 2018/6/27
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Component
@Slf4j
public class WxComponent {
    @Resource
    private RestTemplate restTemplate;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.secret}")
    private String secret;


    private static final String TOKEN_ACCESS_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";


    @Resource
    private RedisUtil redisUtil;


    private final String TOKEN_KEY = "wx:token:key_luck";
    public String getTokenAccess() {
        String accessToken = redisUtil.get(this.TOKEN_KEY);
        if (StringUtils.isEmpty(accessToken)) {
            AccessTokenResp response = restTemplate.getForObject(String.format(TOKEN_ACCESS_URL, appId, secret), AccessTokenResp.class);
            accessToken = response.getAccessToken();
            redisUtil.setEx(TOKEN_KEY, accessToken,100, TimeUnit.MINUTES);
        }
        return accessToken;
    }


}
