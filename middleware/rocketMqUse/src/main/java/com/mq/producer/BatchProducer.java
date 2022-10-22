package com.mq.producer;

import cn.hutool.core.date.SystemClock;
import com.mq.entity.UserVo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class BatchProducer{

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(){
        long time = SystemClock.now()/1000;
        UserVo userVo = new UserVo("robben" + time, 18);

        //发送批量消息
        List<Message> list = new ArrayList<>();

        // 构建 Spring Messaging 定义的 Message 消息
        list.add(MessageBuilder.withPayload(userVo).build());
        list.add(MessageBuilder.withPayload(userVo).build());

        rocketMQTemplate.syncSend("topic1",list,1000);

        System.out.println("~~~~~~~~~~~~~~~~~~BatchProducer~~~发送消息完毕2~~~~~~~~~~~~~~~~~~~~");
    }

}
