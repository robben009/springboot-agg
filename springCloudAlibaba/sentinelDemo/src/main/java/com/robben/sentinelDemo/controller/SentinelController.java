package com.robben.sentinelDemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "sentinel的api")
@RestController
@RequestMapping("/sentinelController")
public class SentinelController {

    //测速控制台的话,需要放到服务上的才能测试看的
    @Operation(summary = "静态配置-测试限流")
    @SentinelResource(value = "getAppValue", blockHandler = "exceptionBlockHandler", fallback = "exceptionFallbackHandler")
    @PostMapping(value = "/getAppValue")
    public String getAppValue() {
        return "getAppValueValue";
    }


    //动态配置规则
    @Operation(summary = "动态配置规则-测试限流")
    @SentinelResource(value = "getAppValue2", blockHandler = "exceptionBlockHandler", fallback = "exceptionFallbackHandler")
    @PostMapping(value = "/getAppValue2")
    public String getAppValue2() {
        return "getAppValueValue2";
    }


    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String exceptionFallbackHandler() {
        return "helloFallback~~~~~~~~~~~~";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionBlockHandler(BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "exceptionHandler~~~~~~~~~";
    }


}
