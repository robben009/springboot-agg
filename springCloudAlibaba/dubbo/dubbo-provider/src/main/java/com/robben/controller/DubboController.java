package com.robben.controller;

import com.robben.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/config")
public class DubboController {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/get")
    public String get() {
        return "mynacosConfigValue";
    }

}