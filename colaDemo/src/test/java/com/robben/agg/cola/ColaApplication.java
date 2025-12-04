package com.robben.agg.cola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.robben.agg.cola","com.alibaba.cola"})
public class ColaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColaApplication.class, args);
    }
}
