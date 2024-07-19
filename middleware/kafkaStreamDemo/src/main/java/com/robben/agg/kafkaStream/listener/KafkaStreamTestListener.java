package com.robben.agg.kafkaStream.listener;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Configuration
@Slf4j
public class KafkaStreamTestListener {

    /**
     * 直接依据消息中的某个字段做为分组聚合的依据，发送消息时可以直接发送字符串
     */
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        //创建kstream对象，同时指定从那个topic中接收消息
        KStream<String, String> inputStream = streamsBuilder.stream(TopicContants.inputTopic);

        inputStream
                .map((key, value) -> {
                    log.info("消息流转中key:{},value:{}", key, value);
                    JSONObject json = JSONObject.parseObject(value);
                    String uniqueFieldValue = json.getString("name"); // 根据你的JSON结构替换为正确的键
                    return KeyValue.pair(uniqueFieldValue, value);
                })
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .reduce((value1, value2) -> value2, Materialized.as("deduplicated-store"))
                .toStream()

                //下面两种方式都可以转发到其他topic中
//                .map((key, value) -> new KeyValue<>(key.key(), value))
//                .to(TopicContants.outputTopic)
                .to(TopicContants.outputTopic, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.String()))
        ;

        return inputStream;
    }


    /**
     * 直接依据消息中的key作为分组依据,然后去重发送到其他topic中.
     * 注意，要求发送消息的时候要注意key的赋值，不能直接发送一个消息字符串，需要携带一个key
     * @param streamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStream2(StreamsBuilder streamsBuilder) {
        //创建kstream对象，同时指定从那个topic中接收消息
        KStream<String, String> inputStream = streamsBuilder.stream(TopicContants.inputTopic2);

        inputStream
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .reduce((value1, value2) -> value2, Materialized.as("deduplicated-store2"))
                .toStream()
                .to(TopicContants.outputTopic2, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.String()))
        ;

        return inputStream;
    }



}
