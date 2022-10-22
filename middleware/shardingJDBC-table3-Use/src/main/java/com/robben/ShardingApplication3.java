package com.robben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注意如果开启了健康检查,则有可能需要关闭db的健康检查不然可能报错
 *  management.health.db.enabled=false
 */

@SpringBootApplication
public class ShardingApplication3 {
    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication3.class, args);
    }
}
