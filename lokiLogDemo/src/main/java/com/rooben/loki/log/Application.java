package com.rooben.loki.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        //下面语句使得日志输出使用异步处理，减小输出日志对性能的影响
        SpringApplication.run(Application.class, args);

        while (true){
            Thread.sleep(3000);
            log.info("当前日志={}",System.currentTimeMillis());
        }

    }


}
