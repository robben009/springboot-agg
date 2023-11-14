package com.robben.controller;

import com.alibaba.fastjson2.JSON;
import com.robben.model.StudentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "nacosProvider2提供接口")
@RestController
@RequestMapping("/nprovider")
public class ProviderController {


    @Operation(summary = "提供对外服务2", description = "测试提供对外服务2description")
    @PostMapping(value = "/myHandle")
    public StudentVo myHandle(@RequestBody StudentVo studentVo) {
        log.info("np2获取了参数:{}", JSON.toJSONString(studentVo));

        studentVo.setName("provider2222222222");
        studentVo.setAge(studentVo.getAge() + 2);
        return studentVo;
    }

    @Operation(summary = "网关调用", description = "网关调用服务description")
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