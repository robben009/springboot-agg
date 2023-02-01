package com.robben.controller;

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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "注解使用")
@RestController
@RequestMapping("/anno")
public class AnnoController extends UnifiedReply {

    @Autowired
    private CacheService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Configuration configuration;

    @Operation(summary = "postConstruct使用")
    @GetMapping(value = "/use")
    public ResponseEntityDto use(){
        String a = "{\n" +
                "      \"name\" : \"john\",\n" +
                "      \"gender\" : \"male\"\n" +
                "   }";
        JSONObject d = JSONObject.parseObject(a);

        ReadContext ctx = JsonPath.using(configuration).parse(d);
//        ReadContext ctx = JsonPath.parse(d);
        String gender0 = ctx.read("$.name");
        System.out.println(gender0);
        String gender1 = ctx.read("$.name1");
        System.out.println(gender1);

        return buildSuccesResp();
    }



}
