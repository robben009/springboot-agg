package com.robben.agg.kafka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Tag(name = "生产者发送")
@Slf4j
@RestController
public class KafkaController {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private KafkaAdminClient kafkaAdminClient;


    //"若是不存在的topic不会自动创建,需要在kafka服务器上的的server.properties增加配置,kafka新版本是默认可以创建topic的" +
    // "num.partitions=3\n" +
    // "auto.create.topics.enable=true\n" +
    // "default.replication.factor=3\n"
    @Operation(summary = "手动创建topic")
    @GetMapping("/createTopic")
    public String createTopic(String topicName) {
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
        CreateTopicsResult result = kafkaAdminClient.createTopics(Arrays.asList(newTopic));

        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        return result.toString();
    }


    @Operation(summary = "简单发送")
    @GetMapping("/send")
    public String send(String topicName, String msg) {
        CompletableFuture send = kafkaTemplate.send(topicName, msg);
        return "success";
    }


    @Operation(summary = "简单发送2")
    @GetMapping("/send2")
    public String send2(String topicName, String msg) {
        //kafka允许为每条消息设置一个key，一旦消息被定义了 Key，那么就可以保证同一个 Key 的所有消息都进入到相同的分区，
        // 这种策略属于自定义策略的一种，被称作"按消息key保存策略"，或Key-ordering 策略。
        kafkaTemplate.send(topicName, "key11", msg);
        return "success";
    }


    @Operation(summary = "发送有回执")
    @GetMapping("/sendAck")
    public String sendAck(String topicName, String msg) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, msg);

        //成功的处理
        future.thenAccept(result -> {
            log.info("Kafka发送消息成功：Topic={},msg={}", topicName, result.toString());
        });

        //发送失败的处理
        future.exceptionally(throwable -> {
            log.error("Kafka发送消息失败：Topic={},errMsg={}", topicName, throwable.getMessage());
            return null;
        });

        return "success";
    }


    /**
     * 参考：https://developer.aliyun.com/article/897204
     * 开启配置项需要增加：前缀transaction-id-prefix: tx_ 、retries大于0、acks: all才行
     * 执行过程中所有的发送操作要么全部成功，要么全部失败
     * @param topicName
     * @param msg
     * @return
     */
    @Operation(summary = "事务正常发送")
    @GetMapping("/transactionSend")
    @Transactional
    public String transactionSend(String topicName, String msg) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName, msg);
            operations.send(topicName, msg);
            operations.send(topicName, msg);
            operations.send(topicName, msg);
            return "lalalal";
        });

        return "success";
    }

    @Operation(summary = "事务异常发送-模板方法")
    @GetMapping("/transactionSend2")
    public void transactionSend2(String topicName, String msg) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName, msg);
            throw new RuntimeException("fail");
        });
    }

    //注解方式如果是同时操作数据库的可能会失效,参考https://blog.csdn.net/feg545/article/details/113742434
    @Operation(summary = "事务异常发送-注解")
    @GetMapping("/transactionSend3")
    @Transactional
    public void transactionSend3(String topicName, String msg) {
        kafkaTemplate.send(topicName, msg);
        throw new RuntimeException("fail");
    }

}
