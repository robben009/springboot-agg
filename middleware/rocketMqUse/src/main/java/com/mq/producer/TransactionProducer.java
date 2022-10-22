package com.mq.producer;

import cn.hutool.core.date.SystemClock;
import com.mq.config.RocketMqContants;
import com.mq.entity.UserVo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class TransactionProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    

    public void send(){
        long time = SystemClock.now()/1000;
        UserVo userVo = new UserVo("robben" + time, 18);

        Message message = MessageBuilder.withPayload(userVo).build();
        // myTransactionGroup要和@RocketMQTransactionListener(txProducerGroup = "myTransactionGroup")定义的一致
        rocketMQTemplate.sendMessageInTransaction("myTransactionGroup",RocketMqContants.Topic,message, null);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~TransactionProducer~~~~~发送消息完毕~~~~~~~~~~~~~~~~~~~");
    }

}
