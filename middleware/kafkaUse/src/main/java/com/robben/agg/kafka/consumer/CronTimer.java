package com.robben.agg.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/31 14:26
 */
@Slf4j
@EnableScheduling
@Component
public class CronTimer {
    /**
     * @KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean，
     * 而是会被注册在KafkaListenerEndpointRegistry中，
     * 而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
     **/
//    @Autowired
//    private KafkaListenerEndpointRegistry registry;
//
//    @Autowired
//    private ConsumerFactory consumerFactory;
//
//    // 监听器容器工厂(设置禁止KafkaListener自启动)
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory delayContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
//        container.setConsumerFactory(consumerFactory);
//        //禁止KafkaListener自启动
//        container.setAutoStartup(false);
//        log.info("kafka监听器不在工作!");
//        return container;
//    }
//
//    @KafkaListener(id="timingConsumer",groupId = KakfaConstant.Consumer_user1,topicPartitions = {
//            @TopicPartition(topic = KakfaConstant.topic1,
//                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))}
//            ,containerFactory = "delayContainerFactory"
//    )
//    public void handle(String message) {
//        log.info("Consumer_user1开始处理消息:{}",message );
//    }
//
//    // 定时启动监听器
//    @Scheduled(cron = "0 11 15 * * ? ")
//    public void startListener() {
//        log.info("启动监听器...");
//        // "timingConsumer"是@KafkaListener注解后面设置的监听器ID,标识这个监听器
//        if (!registry.getListenerContainer("timingConsumer").isRunning()) {
//            registry.getListenerContainer("timingConsumer").start();
//        }
//    }
//
//    // 定时停止监听器
//    @Scheduled(cron = "0 12 15 * * ? ")
//    public void shutDownListener() {
//        log.info("关闭监听器...");
//        registry.getListenerContainer("timingConsumer").pause();
//    }

}