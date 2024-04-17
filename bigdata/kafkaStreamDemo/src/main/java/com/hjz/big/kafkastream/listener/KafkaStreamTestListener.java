package com.hjz.big.kafkastream.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Configuration
@Slf4j
public class KafkaStreamTestListener {

    @Bean
    public KStream<String,String> kStream(StreamsBuilder streamsBuilder){
        //创建kstream对象，同时指定从那个topic中接收消息
        KStream<String, String> inputStream = streamsBuilder.stream(TopicContants.inputTopic);

        inputStream
                //根据value进行聚合分组
                .groupBy((key,value)->value)
                //聚合计算时间间隔
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                //对相同的key做聚合
                .reduce((value1, value2) -> value1, Materialized.as("storeName"))
                .toStream()
                //发送消息
                .to(TopicContants.outputTopic);

        return inputStream;
    }

}
