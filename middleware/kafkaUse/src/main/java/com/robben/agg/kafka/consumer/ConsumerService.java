package com.robben.agg.kafka.consumer;

import com.alibaba.fastjson2.JSON;
import com.robben.agg.kafka.constant.KakfaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ConsumerService {

    //以下的方式都可以来接受请求
    @KafkaListener(topics = { KakfaConstant.topic1 },groupId = KakfaConstant.Consumer_user1)
    public void handle(String message) {
        log.info("Consumer_user1开始处理消息:{}",message );
    }


    //批量消费(需要配置spring.kafka.listener.type=batch,spring.kafka.consumer.max-poll-records==5批量数,注意入参是list)
    @KafkaListener(topics = { KakfaConstant.topic1 },groupId = KakfaConstant.Consumer_user1)
    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
        log.info("批量消费一次size:{}",records.size());
        for (ConsumerRecord<?, ?> record : records) {
            log.info("消费的值:{}",record.value());
        }
    }
    //批量消费的入参是个集合,也可一直这样写
//    public void onMessage3(List<String> recorsd) {
//        log.info(">>>批量消费一次，records.size()="+recorsd.size());
//        for (String record : recorsd) {
//            log.info("消费的值:{}",record);
//        }
//    }


    //批量消费多个topic和partitions
    /**
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
    */
    @KafkaListener(groupId = "felix-group",topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = { "0" }),
            @TopicPartition(topic = "topic2", partitions = "0",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
    })
//    @KafkaListener(groupId = KakfaConstant.Consumer_user1,topicPartitions = {
//            @TopicPartition(topic = KakfaConstant.topic1,
//                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))
//    })
    public void onMessage2(List<ConsumerRecord<?, ?>> records) {
        log.info("批量消费一次size:{}",records.size());
        for (ConsumerRecord<?, ?> record : records) {
            log.info("消费的值:{}",record.value());
        }
    }


    //增加了一个消息过滤器
    @KafkaListener(topics = { KakfaConstant.topic1 },groupId = KakfaConstant.Consumer_user1,containerFactory = "filterContainerFactory")
    public void handle2(String message) {
        log.info("Consumer_user1开始处理消息:{}",message );
    }


   //ConsumerRecord接收消息
    @KafkaListener(topics = { KakfaConstant.topic1 },groupId = KakfaConstant.Consumer_user2)
    public void handle(ConsumerRecord<String, String> record) {
        log.info("Consumer_user2开始处理消息:{}",record );
    }

    @KafkaListener(topics = KakfaConstant.topic1,groupId = KakfaConstant.Consumer_user3)
    public void consumer(ConsumerRecord consumerRecord){
        Optional<Object> kafkaMassage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMassage.isPresent()){
            Object o = kafkaMassage.get();
            log.info("Consumer_user3开始处理消息:{}",o.toString());
        }
    }

    //消息转发(由此验证批量并不是等到够个数才转发,是等到就处理,但每次处理的量是固定的)
    @KafkaListener(topics = KakfaConstant.topic1,groupId = KakfaConstant.Consumer_user1)
    @SendTo(KakfaConstant.topic2)
    public String onMessage7(List<String> recordList) {
        log.info("监听消息为:{}", JSON.toJSONString(recordList));
        return JSON.toJSONString(recordList);
    }

}
