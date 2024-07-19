package com.robben.agg.rocketMq.consumer;

import com.robben.agg.rocketMq.constants.RocketMqContants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMqContants.Topic, consumerGroup = RocketMqContants.consumerGroup)
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到RocketMQ消息[topic={}] ======> {}", RocketMqContants.Topic, s);
    }

}
