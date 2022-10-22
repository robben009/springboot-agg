package com.robben.controller;

import com.alibaba.fastjson.JSON;
import com.robben.model.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "nacosProvider2提供接口")
@RestController
@RequestMapping("/nprovider")
public class ProviderController {

    @Value("${server.port}")
    private String serverPort;

    @ApiOperation(value = "提供对外服务2", notes = "测试提供对外服务2")
    @PostMapping(value = "/myHandle")
    public StudentVo myHandle(@RequestBody StudentVo studentVo) {
        log.info("np2获取了参数:{}", JSON.toJSONString(studentVo));

        studentVo.setName("provider2:" + serverPort);
        studentVo.setAge(studentVo.getAge() + 2);
        return studentVo;
    }

    @ApiOperation(value = "网关调用", notes = "网关调用服务")
    @GetMapping(value = "/gateWayGetData")
    public StudentVo gateWayGetData() {
        log.info("nacos-provider2网关调用！！！！！");
        StudentVo studentVo = new StudentVo();
        studentVo.setName("服务网关路由到我了");
        studentVo.setAge(studentVo.getAge() + 1);
        studentVo.setId(999);
        return studentVo;
    }


}