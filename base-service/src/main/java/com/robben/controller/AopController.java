package com.robben.controller;

import com.robben.annotation.aop.DeclareParents.Animal;
import com.robben.annotation.aop.DeclareParents.Person;
import com.robben.annotation.aop.DeclareParents.Women;
import com.robben.service.AopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Aop测试")
@RestController
@RequestMapping("/aop")
public class AopController {
    @Autowired
    private Women women;
    @Autowired
    private AopService aopService;

    @ApiOperation(value = "测试springAop使用",notes = "测试springAop使用")
    @PostMapping(value = "/getUser1")
    public String getUser1(String name){
        log.info("getUser1_name:{}",name);
        return name;
    }


    @ApiOperation(value = "测试springAop使用2")
    @PostMapping(value = "/getUser2")
    public String getUser2(String name){
        return aopService.getValue(name);
    }


    @ApiOperation(value = "测试@DeclareParents使用")
    @RequestMapping(value = "/getUser3",method = RequestMethod.POST)
    public String getUser3(String name){
        log.info("getUser3_name:{}",name);

        Person person = women;
        person.likePerson();
        Animal animal = (Animal)person;
        animal.eat();

        women.likePerson();
        Animal animal2 = (Animal)women;
        animal2.eat();

        return name;
    }


}
