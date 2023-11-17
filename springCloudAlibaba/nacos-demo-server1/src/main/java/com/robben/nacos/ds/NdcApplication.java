package com.robben.nacos.ds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NdcApplication {

    public static void main(String[] args) {
        SpringApplication.run(NdcApplication.class, args);
    }

}