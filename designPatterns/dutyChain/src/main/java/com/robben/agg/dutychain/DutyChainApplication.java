package com.robben.agg.dutychain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.robben.agg"})
public class DutyChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DutyChainApplication.class, args);
    }

}
