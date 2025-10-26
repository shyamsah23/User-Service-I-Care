package com.iCare.User_Service.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProfileFeignInterceptor {

    @Value("${secret.header.key}")
    private String secretKeyForHeader;

    @Bean
    public RequestInterceptor userToProfileRequestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("X-Secret-Key", secretKeyForHeader);
    }
}
