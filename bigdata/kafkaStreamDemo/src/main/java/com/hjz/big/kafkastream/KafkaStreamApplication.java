package com.hjz.big.kafkastream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import scala.collection.script.Start;


@SpringBootApplication
@EnableKafkaStreams
public class KafkaStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(Start.class,args);
    }
}
