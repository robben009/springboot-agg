package com.robben.agg.nacosDemoServer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Nds2Application {

    public static void main(String[] args) {
        SpringApplication.run(Nds2Application.class, args);
    }

}