package com.robben.agg.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // 配置 RestTemplate Bean，全局复用
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}