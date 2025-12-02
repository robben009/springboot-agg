package com.robben.agg.base.controller;

import com.robben.agg.base.aspect.anno.OpenApiCatch;
import com.robben.agg.base.req.BenefitDetailReq;
import com.robben.agg.base.resp.BwpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "注解使用", description = "提供统一的拦截器做处理,对异常处理和增加返回traceId")
@RestController
@RequestMapping("/anno")
@OpenApiCatch
public class AnnoController {

    @Operation(summary = "测试切面")
    @PostMapping(value = "/use")
    public BwpResponse use(@RequestBody BenefitDetailReq req) {
        req.setCardCode("kkkkkkk");
        return BwpResponse.of(req);
    }


}
