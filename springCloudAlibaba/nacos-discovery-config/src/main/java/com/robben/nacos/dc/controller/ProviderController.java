package com.robben.nacos.dc.controller;

import com.robben.nacos.dc.model.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "对外接口")
@RestController
@RequestMapping("/nprovider")
@RefreshScope
public class ProviderController {

    @Value("${aaaa}")
    private String testValue;


    @Operation(summary = "获取配置项的值")
    @PostMapping(value = "/getConfigValue")
    public String getConfigValue() {
        return testValue;
    }

    @Operation(summary = "提供对外服务", notes = "测试提供对外服务")
    @PostMapping(value = "/myHandle")
    public StudentVo myHandle(@RequestBody StudentVo studentVo) {
        studentVo.setName("provider:" + testValue);
        studentVo.setAge(studentVo.getAge() + 1);
        return studentVo;
    }

    @Operation(summary = "网关调用", notes = "网关调用服务")
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
