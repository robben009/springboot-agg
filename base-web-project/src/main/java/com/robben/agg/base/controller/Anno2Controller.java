package com.robben.agg.base.controller;

import com.robben.agg.base.req.BenefitDetailReq;
import com.robben.agg.base.resp.BwpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "注解使用2", description = "提供统一拦截处理HttpInputMessage、RequestBodyAdvice")
@RestController
@RequestMapping("/anno2")
public class Anno2Controller {

    @Operation(summary = "测试拦截器")
    @PostMapping(value = "/use")
    public BwpResponse use(@RequestBody BenefitDetailReq req) {
        return BwpResponse.of(req);
    }


}
