package com.hjz.big.kafkastream.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hjz.big.kafkastream.listener.TopicContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafkaStream")
public class DemoController {
    @Autowired
    private KafkaTemplate kafkaTemplate;


    @GetMapping("/addData")
    public String addData() {
        int count = 20;
        for (int i = 0; i < count; i++) {
            JSONObject data = new JSONObject();
            data.put("age", i);

            if (i % 2 == 0) {
                data.put("name", "aaa");
            } else {
                data.put("name", "bbb");
            }

            kafkaTemplate.send(TopicContants.inputTopic, data.toJSONString());
        }

        return "ok";
    }


    @GetMapping("/addData2")
    public String addData2() {
        int count = 20;
        for (int i = 0; i < count; i++) {
            JSONObject data = new JSONObject();
            data.put("age", i);

            if (i % 2 == 0) {
                data.put("name", "aaa");
            } else {
                data.put("name", "bbb");
            }

            kafkaTemplate.send(TopicContants.inputTopic2, data.getString("name"), data.toJSONString());
        }

        return "ok";
    }
}

