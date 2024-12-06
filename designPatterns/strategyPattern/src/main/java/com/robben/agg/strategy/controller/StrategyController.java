package com.robben.agg.strategy.controller;

import com.robben.agg.strategy.service.CommandFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "测试")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class StrategyController {
    private final CommandFactory commandFactory;

    @Operation(summary = "方法一")
    @GetMapping("/method1")
    public Integer getById() {
        return commandFactory.calculate("add", 1, 2);
    }


}