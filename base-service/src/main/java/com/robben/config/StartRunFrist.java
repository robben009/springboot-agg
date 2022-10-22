package com.robben.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *开机启动
 */

@Slf4j
@Component
@Order(value = 1)
public class StartRunFrist implements ApplicationRunner {
    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${server.servlet.context-path}")
    private String projectName;

    @Override
    public void run(ApplicationArguments args){
        //清除所有rabbitmq队列及其里面的消息
        log.info("~~~~~~~~~~~~~~~~~~~springBoot_startSuccess!!!~~~~~~~~~~~~~~~~~~~");
        log.info("~~~~~~~~~~~~~~~~~~~environment:{}~~~~~~~~~~~~~~~~~~~", environment);
        log.info("~~~~~~~~~~~~~~~~~~~projectName:{}~~~~~~~~~~~~~~~~~~~", projectName);
    }

}
