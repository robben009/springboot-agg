package com.mq.producer;

import cn.hutool.core.date.SystemClock;
import com.mq.config.RocketMqContants;
import com.mq.entity.UserVo;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * 1:如果发送不了,注意云机器的防火墙和安全组、conf/broker.conf 是否设置了brokerIP1=xxx.xxx.xxx.xxx（服务器公网地址）
 * 2:启动注意修改rocket runserver.sh和runbroker的jvm参数
 * namesrv启动方式：
 *  nohup sh bin/mqnamesrv &
 * broker启动方式：
 *  nohup sh bin/mqbroker -n xxx.xxx.xxx.xxx（服务器公网地址）:9876 -c conf/broker.conf autoCreateTopicEnable=true &
 * 3:安装控制台的UI
 *  https://github.com/apache/rocketmq-externals  编译运行rocket-console
 *
 * Date: 2021/7/3 19:46
 */
@Component
public class Producer1 {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    

    public void send(){
        long time = SystemClock.now()/1000;
        UserVo userVo = new UserVo("robben" + time, 18);

//        //普通发送
//        rocketMQTemplate.convertAndSend(RocketMqContants.Topic, userVo);
//
//        //发送同步消息
//        SendResult sendResult = rocketMQTemplate.syncSend(RocketMqContants.Topic, userVo);
//
//        //异步发送消息-100秒
//        rocketMQTemplate.asyncSend(RocketMqContants.Topic, userVo, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println("异步发送成功" + userVo);
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println("异步发送失败"+ userVo);
//            }
//        },100*1000);
//
//        //单向消息--不用回执(日志类)
//        rocketMQTemplate.sendOneWay(RocketMqContants.Topic,userVo);
//
//        /**
//         * 参考文档: https://www.cnblogs.com/starcrm/p/13061971.html
//         * private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
//         * 延时消息(指定等级)
//         */
//        rocketMQTemplate.syncSend(RocketMqContants.Topic, MessageBuilder.withPayload(userVo).build(),2000,2);

        //顺序消费
        rocketMQTemplate.syncSendOrderly(RocketMqContants.Topic,new UserVo("robben" + time, 16),userVo.getName());


        System.out.println("~~~~~~~~~~~~~~~~~~~~~producer1~~~~~发送消息完毕~~~~共6条~~~~~~~~~~~~~~~~");
    }

}
