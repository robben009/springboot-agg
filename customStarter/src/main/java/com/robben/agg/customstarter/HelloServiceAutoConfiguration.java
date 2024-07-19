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
@EnableConfigurationProperties(HelloProperties.class) // 让配置类生效
@ConditionalOnClass(HelloServiceAutoConfiguration.class) // 前置配置
public class HelloServiceAutoConfiguration {

    @Autowired
    HelloProperties helloProperties;

    @Bean
    @ConditionalOnMissingBean({HelloService.class})
    public HelloService helloService() {
        HelloService helloService = new HelloService();
        helloService.setName(helloProperties.getName());
        helloService.setMsg(helloProperties.getMsg());
        System.out.println("创建了HelloService");

        if(helloProperties.getName() == null || helloProperties.getMsg() == null){
            return null;
        }

        return helloService;
    }

}

