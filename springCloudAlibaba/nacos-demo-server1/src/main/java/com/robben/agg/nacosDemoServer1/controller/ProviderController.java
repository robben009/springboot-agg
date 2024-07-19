package com.robben.agg.nacosDemoServer1.controller;

import com.robben.agg.nacosDemoServer1.model.StudentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "nacosServer1对外接口")
@RestController
@RequestMapping("/nacosServer1")
@RefreshScope
public class ProviderController {
    @Value("${testValue}")
    private String testValue;
    @Value("${testValueExt}")
    private String testValueExt;


    @Operation(summary = "获取配置项的值")
    @PostMapping(value = "/getConfigValue")
    public String getConfigValue() {
        return testValue;
    }

    @Operation(summary = "获取额外配置项的值")
    @PostMapping(value = "/getExtConfigValue")
    public String getExtConfigValue() {
        return testValueExt;
    }

    @Operation(summary = "提供对外服务", description = "测试提供对外服务")
    @GetMapping(value = "/testServer")
    public StudentVo testServer() {
        StudentVo studentVo = new StudentVo();
        studentVo.setName("provideraaaaaaaaaaaa");
        studentVo.setAge(studentVo.getAge() + 1);
        return studentVo;
    }


    @Operation(summary = "提供对外服务", description = "测试提供对外服务")
    @PostMapping(value = "/myHandle")
    public StudentVo myHandle(@RequestBody StudentVo studentVo) {
        studentVo.setName("provideraaaaaaaaaaaa");
        studentVo.setAge(studentVo.getAge() + 1);
        return studentVo;
    }

    @Operation(summary = "网关调用", description = "网关调用服务")
    @GetMapping(value = "/gateWayGetData")
    public StudentVo gateWayGetData() {
        log.info("nacos-provider网关调用！！！！！");
        StudentVo studentVo = new StudentVo();
        studentVo.setName("服务网关路由到我了");
        studentVo.setAge(studentVo.getAge() + 1);
        studentVo.setId(999);
        return studentVo;
    }


}
