package com.fotron.draw.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author yutong
 */
@Configuration
public class RestTemplateConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            org.springframework.http.client.SimpleClientHttpRequestFactory requestFactory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
            //连接超时
            requestFactory.setConnectTimeout(20000);
            //数据读取超时
            requestFactory.setReadTimeout(20000);
            return requestFactory;
        } catch (Exception e) {
            logger.error("初始化http连接池失败", e);
        }
        return null;
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getInterceptors().add(new HeaderRequestInterceptor());
        return restTemplate;
    }
}
