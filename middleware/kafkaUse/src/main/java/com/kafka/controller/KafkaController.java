package com.kafka.controller;

import com.alibaba.fastjson2.JSON;
import com.kafka.constant.KakfaConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@Api(tags = "生产者发送")
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
    @ApiOperation("手动创建topic")
    @GetMapping("/createTopic")
    public String createTopic(@ApiParam(name = "topicName") String topicName) {
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
        CreateTopicsResult result = kafkaAdminClient.createTopics(Arrays.asList(newTopic));

        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        return result.toString();
    }


    @ApiOperation(value = "简单发送")
    @GetMapping("/send")
    public String send(@ApiParam(name = "topicName") String topicName,
                       @ApiParam(name = "msg") String msg) {
        kafkaTemplate.send(topicName, msg);
        return "success";
    }


    @ApiOperation("简单发送2")
    @GetMapping("/send2")
    public String send2(@ApiParam(name = "topicName") String topicName,
                        @ApiParam(name = "msg") String msg) {

        //kafka允许为每条消息设置一个key，一旦消息被定义了 Key，那么就可以保证同一个 Key 的所有消息都进入到相同的分区，
        // 这种策略属于自定义策略的一种，被称作"按消息key保存策略"，或Key-ordering 策略。
        kafkaTemplate.send(topicName,"key11", msg);
        return "success";
    }


    @ApiOperation("发送有回执")
    @GetMapping("/sendAck")
    public String sendAck(@ApiParam(name = "topicName") String topicName,
                          @ApiParam(name = "msg") String msg) {

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.error("Kafka发送消息失败：Topic={},errMsg={}",topicName , throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                //成功的处理
                log.info("Kafka发送消息成功：Topic={},msg={}", topicName, result.toString());
            }
        });

        return "success";
    }


    /**
     * 参考：https://developer.aliyun.com/article/897204
     * 开启配置项需要增加：前缀transaction-id-prefix: tx_ 、retries大于0、acks: all才行
     * @param topicName
     * @param msg
     * @return
     */
    @ApiOperation("事务正常发送")
    @GetMapping("/transactionSend")
    @Transactional
    public String transactionSend(@ApiParam(name = "topicName") String topicName,
                                  @ApiParam(name = "msg") String msg) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName,msg);
            return "lalalal";
        });

        return "success";
    }

    @ApiOperation("事务异常发送-模板方法")
    @GetMapping("/transactionSend2")
    public void transactionSend2(@ApiParam(name = "topicName") String topicName,
                                 @ApiParam(name = "msg") String msg){
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName,msg);
            throw new RuntimeException("fail");
        });
    }

    //注解方式如果是同时操作数据库的可能会失效,参考https://blog.csdn.net/feg545/article/details/113742434
    @ApiOperation("事务异常发送-注解")
    @GetMapping("/transactionSend3")
    @Transactional
    public void transactionSend3(@ApiParam(name = "topicName") String topicName,
                                 @ApiParam(name = "msg") String msg){
        kafkaTemplate.send(topicName, msg);
        throw new RuntimeException("fail");
    }

}
