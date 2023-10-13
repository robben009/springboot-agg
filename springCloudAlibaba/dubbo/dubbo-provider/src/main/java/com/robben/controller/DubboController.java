package com.robben.controller;

import com.robben.DubboDemoService;
import com.robben.vo.StudentVo;
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
    private DubboDemoService dubboDemoService;

    @GetMapping(value = "/get")
    public String get() {
        StudentVo studentVo = new StudentVo();
        studentVo.setName("aaaaa");
        studentVo.setAge(18);
        return dubboDemoService.sayHello(studentVo);
    }

}