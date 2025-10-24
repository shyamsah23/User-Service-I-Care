package com.iCare.User_Service.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileFeignInterceptor {

    @Value("${secret.header.key}")
    private String secretKeyForHeader;

    @Bean
    public RequestInterceptor userToProfileRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("X-Secret-Key", secretKeyForHeader);
            }
        };
    }
}
