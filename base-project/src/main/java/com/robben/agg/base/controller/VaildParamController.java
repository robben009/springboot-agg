package com.robben.agg.base.controller;

import com.robben.annotation.validParam.auth.AuthToken;
import com.robben.annotation.validParam.anno.ValidGroup;
import com.robben.model.ValidVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "入参校验")
@RestController
@RequestMapping("/anno")
public class VaildParamController {
    /**
     * 需要角色校验，加上注解，并且写入两个角色，本文演示两个角色有一个即可访问，当然写一个可以。
     * 注：若想两个角色同时具有，修改后文的逻辑判断即可。
     * 若需要更复杂的逻辑操作，推荐使用Spring Security框架。
     */
    @Operation(summary = "注解权限校验-有")
    @GetMapping("needAdmin")
    @AuthToken(roleName = {"admin", "admin2"})
    public String admin(Integer id, String name, Integer age) {
        return "只有管理员角色访问成功啦!!!";
    }

    //以下是spring自带的一些入参权限检验
    @Operation(summary = "RequestBody校验")
    @PostMapping("/valid/test1")
    public String test1(@Validated @RequestBody ValidVo validVO){
        return "success";
    }

    @Operation(summary = "Form校验")
    @PostMapping(value = "/valid/test2")
    public String test2(@Validated ValidVo validVO){
        return "success";
    }

    @Operation(summary = "单参数-邮箱校验")
    @PostMapping(value = "/valid/test3")
    public String test3(@Email @RequestParam String email){
        return "success";
    }


    @Operation(summary = "RequestBody校验-分组")
    @PostMapping("/valid/test4")
    public String test4(@Validated(ValidGroup.noParam.class) @RequestBody ValidVo validVO){
        return "success";
    }


}
