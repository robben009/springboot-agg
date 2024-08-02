package com.robben.agg.base.controller;

import com.robben.agg.base.aspect.validParam.anno.ValidGroup;
import com.robben.agg.base.model.ValidVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 使用spring的校验全局异常拦截来处理
 */
@Slf4j
@Tag(name = "入参校验")
@RestController
@RequestMapping("/anno")
public class VaildParamController {


    //以下是spring自带的一些入参权限检验
    @Operation(summary = "RequestBody校验")
    @PostMapping("/valid/test1")
    public String test1(@Validated @RequestBody ValidVo validVO) {
        return "success";
    }

    @Operation(summary = "Form校验")
    @PostMapping(value = "/valid/test2")
    public String test2(@Validated ValidVo validVO) {
        return "success";
    }

    @Operation(summary = "单参数-邮箱校验")
    @PostMapping(value = "/valid/test3")
    public String test3(@Email @RequestParam String email) {
        return "success";
    }


    @Operation(summary = "RequestBody校验-分组")
    @PostMapping("/valid/test4")
    public String test4(@Validated(ValidGroup.noParam.class) @RequestBody ValidVo validVO) {
        return "success";
    }


}
