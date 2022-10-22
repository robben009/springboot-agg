package com.mq.consumer;

import com.mq.config.RocketMqContants;
import com.mq.entity.UserVo;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/7/3 21:46
 */
//@Service
//@RocketMQMessageListener(topic = RocketMqContants.Topic,consumerGroup = RocketMqContants.consumerGroup,
//        selectorType = SelectorType.TAG ,selectorExpression = "*",
//        messageModel = MessageModel.CLUSTERING) //广播模式和集群模式-负载均衡
//public class consumer1 implements RocketMQListener<UserVo> {
//
//    @Override
//    public void onMessage(UserVo userVo) {
//        System.out.println("消费了消息:" + userVo.toString());
//    }
//
//}
