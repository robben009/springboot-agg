package com.robben.agg.nettyserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class NettyServerApp {
    /**
     * @param args
     */
    public static void main(String[] args) {
       SpringApplication.run(NettyServerApp.class);
    }
}
