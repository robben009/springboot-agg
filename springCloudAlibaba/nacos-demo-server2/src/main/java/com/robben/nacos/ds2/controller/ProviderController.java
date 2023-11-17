package com.robben.nacos.ds2.controller;

import com.robben.nacos.ds2.model.Student2Vo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "nacosServer1对外接口")
@RestController
@RequestMapping("/nacosServer1")
@RefreshScope
public class ProviderController {

    @Value("${testValue2}")
    private String testValue2;


    @Operation(summary = "获取配置项的值")
    @PostMapping(value = "/getConfigValue2")
    public String getConfigValue2() {
        return testValue2;
    }

    @Operation(summary = "提供对外服务", description = "测试提供对外服务")
    @GetMapping(value = "/testServer")
    public Student2Vo testServer() {
        Student2Vo Student2Vo = new Student2Vo();
        Student2Vo.setName("provideraaaaaaaaaaaa");
        Student2Vo.setAge(Student2Vo.getAge() + 1);
        return Student2Vo;
    }


    @Operation(summary = "网关调用", description = "网关调用服务")
    @GetMapping(value = "/gateWayGetData")
    public Student2Vo gateWayGetData() {
        log.info("nacos-provider网关调用！！！！！");
        Student2Vo Student2Vo = new Student2Vo();
        Student2Vo.setName("服务网关路由到我了");
        Student2Vo.setAge(Student2Vo.getAge() + 1);
        Student2Vo.setId(999);
        return Student2Vo;
    }


}
