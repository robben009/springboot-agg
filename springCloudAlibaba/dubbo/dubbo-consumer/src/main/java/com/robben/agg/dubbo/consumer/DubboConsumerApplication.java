package com.robben.agg.dubbo.consumer;

import com.robben.agg.dubbo.api.DubboDemoService;
import com.robben.agg.dubbo.api.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableDubbo
@SpringBootApplication
@RestController
public class DubboConsumerApplication {
    @DubboReference
    private DubboDemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

    @PostMapping("/test")
    public StudentVo test() {
        StudentVo vo = new StudentVo();
        vo.setAge(1);
        vo.setName("kkkkkk");
        StudentVo s = demoService.sayHello(vo);
        return s;
    }

}