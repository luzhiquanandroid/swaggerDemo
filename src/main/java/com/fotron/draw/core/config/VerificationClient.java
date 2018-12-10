package com.fotron.draw.core.config;

import com.fotrontimes.utils.verification.client.SimpleVerificationClient;
import com.fotrontimes.utils.verification.config.VerificationInfo;

public class VerificationClient {
    public static <T> SimpleVerificationClient<T> build(String appid, String appSecret) {
        VerificationInfo verificationInfo = new VerificationInfo();
        verificationInfo.setAppId(appid);
        verificationInfo.setAppSecret(appSecret);
        return new SimpleVerificationClient<>(verificationInfo);
    }
}
