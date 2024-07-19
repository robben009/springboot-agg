package com.robben.agg.base.controller;

import com.alibaba.fastjson2.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.robben.common.ResponseEntityDto;
import com.robben.common.UnifiedReply;
import com.robben.service.CacheService;
import com.robben.utils.RedisUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reqTest")
public class ReqController extends UnifiedReply {

    @Autowired
    private CacheService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Configuration configuration;

    //

    /**
     * @RequestParam 可以接受get请求,请求方式是form-data(下面填写对应的key和value)、x-www-form-urlencoded请求只能在url中添加参数也能接受
     * @RequestBody 是接受post请求 可以转换为对象
     * @param name
     * @param addresss
     * @return
     */
    @GetMapping(value = "/use")
    public String use(@RequestParam String name, @RequestParam String addresss){
        log.info("name:{},addresss:{}",name,addresss);
        return "success";
    }

    @GetMapping(value = "/use2")
    public String use2(@RequestParam("name") String name, @RequestParam("addresss") String addresss){
        log.info("name:{},addresss:{}",name,addresss);
        return "success";
    }


}
