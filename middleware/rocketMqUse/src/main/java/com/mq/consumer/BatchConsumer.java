package com.mq.consumer;

import com.mq.entity.UserVo;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/7/3 21:46
 */
//@Service
//@RocketMQMessageListener(topic = "topic17",consumerGroup = "batchcg3",selectorExpression = "*")
//public class BatchConsumer implements RocketMQListener<UserVo> {
//
//
//    @Override
//    public void onMessage(UserVo list) {
////        for (Message vo : list) {
//            System.out.println("消费了消息:");
////        }
//
//    }
//
//}
