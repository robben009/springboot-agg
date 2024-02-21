package com.mq.consumer;

import com.alibaba.fastjson2.JSON;
import com.mq.constants.RocketMqContants;
import com.mq.entity.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMqContants.Topic2, consumerGroup = RocketMqContants.consumerGroup2)
public class ConsumerService2 implements RocketMQListener<UserVo> {

    @Override
    public void onMessage(UserVo s) {
        log.info("接收到RocketMQ消息[topic={}] ======> {}", RocketMqContants.Topic2, JSON.toJSONString(s));
    }

}
