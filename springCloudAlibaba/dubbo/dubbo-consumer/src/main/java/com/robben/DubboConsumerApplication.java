package com.robben;

import com.robben.DubboDemoService;
import com.robben.vo.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableDubbo
@SpringBootApplication
public class DubboConsumerApplication {

    @DubboReference
    private DubboDemoService demoService;


    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        StudentVo vo = new StudentVo();
        vo.setAge(1);
        vo.setName("kkkkkk");
        return args -> log.info(demoService.sayHello(vo));
    }

}