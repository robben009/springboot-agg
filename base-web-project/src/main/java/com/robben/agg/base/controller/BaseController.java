package com.robben.agg.base.controller;

import cn.hutool.core.util.RandomUtil;
import com.robben.agg.base.req.BenefitDetailReq;
import com.robben.agg.base.resp.BwpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "基础模拟")
@RestController
@RequestMapping("/base")
public class BaseController {

    @Operation(summary = "测试拦截器")
    @GetMapping(value = "/use")
    public String use() {
        return RandomUtil.randomString(9);
    }


}
