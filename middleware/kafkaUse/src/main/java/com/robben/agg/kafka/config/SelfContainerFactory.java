package com.robben.agg.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/30 17:34
 * desc: 筛选出需要的信息再交由KafkaListener处理，不需要的消息则过滤掉。
 * 返回true的时候消息将会被抛弃，返回false时，消息能正常抵达监听容器。
 */

@Component
public class SelfContainerFactory {
    @Autowired
    private ConsumerFactory consumerFactory;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    // 消息过滤器
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            //返回true的时候消息将会被抛弃，返回false时，消息能正常抵达监听容器。
            if (!"qq".equals(consumerRecord.value().toString())) {
                return false;
            }
            //返回true消息则被过滤
            return true;
        });
        return factory;
    }

    /**
     * 自定义的监听配置
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> singleMsgConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 30);
        factory.setConsumerFactory( new DefaultKafkaConsumerFactory<>(config));
        factory.setBatchListener(false);
        return factory;
    }


}
