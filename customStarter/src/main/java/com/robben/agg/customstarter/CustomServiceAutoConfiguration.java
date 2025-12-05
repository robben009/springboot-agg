package com.robben.agg.customstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/6/18 11:47
 */
@Configuration
@EnableConfigurationProperties(CustomProperties.class) // 让配置类生效
@ConditionalOnClass(CustomServiceAutoConfiguration.class) // 前置配置
public class CustomServiceAutoConfiguration {

    @Autowired
    CustomProperties customProperties;

    @Bean
    @ConditionalOnMissingBean({CustomService.class})
    public CustomService helloService() {
        CustomService customService = new CustomService();
        customService.setName(customProperties.getName());
        customService.setMsg(customProperties.getMsg());
        System.out.println("创建了CustomService");

        if(customProperties.getName() == null || customProperties.getMsg() == null){
            return null;
        }

        return customService;
    }

}

