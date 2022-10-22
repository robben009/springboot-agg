package com.robben.controller;

import com.robben.entity.UserInfoEntity;
import com.robben.event.ContentAnnoEvent;
import com.robben.event.ContentEvent;
import com.robben.event.ContentSmartEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * spring的事件机制,采用的观察者模式，但是最新的一般都会用MQ了
 */
@Slf4j
@Api(tags = "spring事件机制")
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private ApplicationContext applicationContext;


    @ApiOperation(value = "ApplicationListener的监听")
    @GetMapping("/sendEvent")
    public String sendEvent(String s){
        log.info("sendEvent_request_start!!!");

        applicationContext.publishEvent(new ContentEvent("这是一个ContentEvent消息-"+s));
        return "success";
    }


    //按照顺序来执行的事件
    @ApiOperation(value = "SmartApplicationListener的监听")
    @PostMapping(value = "/sendSmartEvent")
    public String sendSmartEvent(String context){
        log.info("sendSmartEvent_request:{}",context);

        UserInfoEntity vo = new UserInfoEntity();
        vo.setId(1l);
        vo.setName("aaa");

        applicationContext.publishEvent(new ContentSmartEvent(this,vo));
        return context;
    }


    //基于注解的事件发布(建议使用此种方法)
    @ApiOperation(value = "AnnoApplicationListener的监听")
    @RequestMapping(value = "/sendAnnoEvent",method = RequestMethod.POST)
    public String sendAnnoEvent(String context){
        log.info("sendAnnoEvent_request:{}",context);

        UserInfoEntity vo = new UserInfoEntity();
        vo.setId(2L);
        vo.setName("bbb");

        applicationContext.publishEvent(new ContentAnnoEvent(this,vo));
        return context;
    }

}
