package com.robben.agg.rocketMq;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description：
 * 文档: https://rocketmq.apache.org/docs/quick-start/
 * Author: robben
 * Date: 2021/7/3 17:20
 */
@SpringBootApplication
@ImportAutoConfiguration({RocketMQAutoConfiguration.class})
public class MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }


}
