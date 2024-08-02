package com.robben.agg.cola;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.robben.agg.cola","com.alibaba.cola"})
public class ColaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColaApplication.class, args);
    }

}
