package com.robben.agg.rocketMq.controller;

import com.alibaba.fastjson.JSON;
import com.robben.agg.rocketMq.constants.RocketMqContants;
import com.robben.agg.rocketMq.entity.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Tag(name = "rocketMqUse")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mq")
public class MqController {
    private final RocketMQTemplate rocketMQTemplate;

    @Operation(summary = "发送字符串")
    @PostMapping(value = "/send")
    public String send(@RequestBody UserVo userVo) {
        //发送普通字符串
        rocketMQTemplate.convertAndSend(RocketMqContants.Topic, "hello world");

        //同步发送有回执
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMqContants.Topic, JSON.toJSONString(userVo));

        //单向消息--不用回执(日志类)
        rocketMQTemplate.sendOneWay(RocketMqContants.Topic, userVo);
        return sendResult.toString();
    }


    //实测发现发送字符串和对象,接收方用string都能接受到
    @Operation(summary = "发送对象")
    @PostMapping(value = "/send2")
    public String send2(@RequestBody UserVo userVo) {
        //发送有回执
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMqContants.Topic2, userVo);
        return sendResult.toString();

    }


    @Operation(summary = "异步发送消息")
    @PostMapping(value = "/send3")
    public String send3(@RequestBody UserVo userVo) {
        rocketMQTemplate.asyncSend(RocketMqContants.Topic2, userVo, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步发送成功 {}", sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步发送失败 {}", JSON.toJSONString(userVo));
            }
        }, 3 * 1000);

        return "1";
    }


    @Operation(summary = "发送延迟消息")
    @PostMapping(value = "/send4")
    public String send4(@RequestBody UserVo userVo) {
        /**
         * 参考文档: https://www.cnblogs.com/starcrm/p/13061971.html
         * private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
         * 延时消息(指定等级)
         * 查看官网 https://github.com/apache/rocketmq-clients/blob/master/java/client/src/main/java/org/apache/rocketmq/client/java/example/ProducerDelayMessageExample.java
         * 可以使用原生message,自定义任意时长
         */
        SendResult sendResult = rocketMQTemplate.syncSend(RocketMqContants.Topic2, MessageBuilder.withPayload(userVo).build(), 2000, 2);
        return sendResult.toString();
    }


    @Operation(summary = "发送顺序消息")
    @PostMapping(value = "/send5")
    public String send5(@RequestBody UserVo userVo) {
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(RocketMqContants.Topic, userVo, String.valueOf(userVo.getAge()));
        return sendResult.toString();
    }


    @Operation(summary = "发送事务消息")
    @PostMapping(value = "/send6")
    public String send6(@RequestBody UserVo userVo) {
        Message message = MessageBuilder
                .withPayload(userVo)
                .setHeader("age", userVo.getAge())
                .build();
        TransactionSendResult r = rocketMQTemplate.sendMessageInTransaction(RocketMqContants.Topic2, message, null);
        return r.toString();
    }


    @Operation(summary = "批量发送消息")
    @PostMapping(value = "/send7")
    public String send7(@RequestBody UserVo userVo) {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserVo newUserVo = userVo;
            newUserVo.setAge(userVo.getAge() + i);

            Message message = MessageBuilder.withPayload(newUserVo).build();
            list.add(message);
        }

        SendResult sendResult = rocketMQTemplate.syncSend(RocketMqContants.Topic2, list, 1000);
        return sendResult.toString();
    }

}
