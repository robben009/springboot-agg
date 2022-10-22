package com.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/31 15:16
 */
@Slf4j
@Configuration
public class KafkaConf {

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    //不行估计是配置文件没有加载完全
    @Bean
    public KafkaAdminClient kafkaAdminClient(){
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, server);

        return (KafkaAdminClient) KafkaAdminClient.create(props);
    }

}
