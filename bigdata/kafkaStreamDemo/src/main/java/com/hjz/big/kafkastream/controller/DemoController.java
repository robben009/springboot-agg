package com.hjz.big.kafkastream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${inputTopic}")
    private String inputTopic;


    @GetMapping("/addData")
    public String addData() {
        int count = 20;
        for (int i = 0; i < count; i++) {
            if (i / 2 == 0) {
                kafkaTemplate.send(inputTopic, "aaa", "aaa" + i);
            } else {
                kafkaTemplate.send(inputTopic, "bbb", "bbb" + i);
            }
        }
        return "ok";
    }


}

