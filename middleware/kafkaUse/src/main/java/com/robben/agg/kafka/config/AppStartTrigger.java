package com.robben.agg.kafka.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

/**
 * 开机启动
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AppStartTrigger implements ApplicationRunner {
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Override
    public void run(ApplicationArguments args) {
        //本地关闭kafka消息消费,防止本地启动后自动监听消息
//        registry.getListenerContainer("topicId").stop();
        log.info("关闭了topicId消费监听");
    }

}
