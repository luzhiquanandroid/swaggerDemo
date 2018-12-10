package com.fotron.draw.core.config;

import com.fotrontimes.utils.verification.config.SimpleVerificationConfiguration;
import com.fotrontimes.utils.verification.config.VerificationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/******************************************
 * @author: 吕鹏伟 (codecooker@outlook.com)
 * @createDate:2018/7/26
 * @company: (C) Copyright codecooker.cn 2018 
 * @since: JDK 1.8 
 * @Description: 看类名, 你懂的
 ******************************************/
@Configuration
public class AuthConfiguration implements SimpleVerificationConfiguration {
    @Value("${draw.appId}")
    private String appId;
    @Value("${draw.appSecret}")
    private String appSecret;

    @Override
    public List<VerificationInfo> loadConfigurations() {
        return null;
    }

    @Override
    public VerificationInfo configuration(String s) {
        VerificationInfo info = new VerificationInfo();
        info.setAppId(appId);
        info.setAppSecret(appSecret);

        return info;
    }


}
