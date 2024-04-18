package com.hjz.big.kafkastream.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MyConsumer {
    @KafkaListener(topics = TopicContants.inputTopic)
    public void processMessage(String content) {
        log.info("inputTopic......" + content);
    }

    @KafkaListener(topics = TopicContants.outputTopic)
    public void processMessage2(String content) {
        log.info("outputTopic......" + content);
    }

    @KafkaListener(topics = TopicContants.inputTopic2)
    public void processMessage3(String content) {
        log.info("inputTopic2......" + content);
    }

    @KafkaListener(topics = TopicContants.outputTopic2)
    public void processMessage4(String content) {
        log.info("outputTopic2......" + content);
    }


}

