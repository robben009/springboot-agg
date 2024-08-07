package com.robben.agg.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.robben.agg.dubbo.api.DubboDemoService;
import com.robben.agg.sentinel.tool.Tools;
import com.robben.agg.dubbo.api.StudentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "sentinel的api")
@RestController
@RequestMapping("/sentinelController")
public class SentinelController {
    @DubboReference(version = "1.0.0",timeout = 5000)
    private DubboDemoService demoService;


    @Operation(summary = "测试web限流")
    @SentinelResource(value = "api", blockHandler = "exceptionBlockHandler", fallback = "exceptionFallbackHandler")
    @PostMapping(value = "/getAppValue")
    public String getAppValue(@RequestBody String req) {
        return "测试web限流";
    }

    @Operation(summary = "测试web限流-默认方法")
    @SentinelResource(value = "api", blockHandlerClass = Tools.class, blockHandler = "webHandle")
    @PostMapping(value = "/getAppValue2")
    public String getAppValue2(@RequestBody String req) {
        return "测试web限流-默认方法";
    }


    @Operation(summary = "测试dubbo限流-默认方法")
    @PostMapping(value = "/getAppValue3")
    public StudentVo getAppValue3(@RequestBody String req) {
        return demoService.sayHello(new StudentVo(1,req,null));
    }


    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String exceptionFallbackHandler(@RequestBody String req) {
        return "helloFallback~~~~~~~~~~~~";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionBlockHandler(@RequestBody String req, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "exceptionHandler~~~~~~~~~";
    }


}
