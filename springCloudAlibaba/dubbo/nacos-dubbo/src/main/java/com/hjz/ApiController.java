package com.hjz;

import com.robben.DubboDemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/apiTest")
public class ApiController {
    @DubboReference
    private DubboDemoService dubboDemoService;


    @GetMapping("/a")
    public String checkParam() {
        log.info("开始启请求");
        return dubboDemoService.sayHello(null);
    }


}
