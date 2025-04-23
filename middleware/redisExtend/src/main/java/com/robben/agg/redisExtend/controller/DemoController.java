package com.robben.agg.redisExtend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@Tag(name = "key过期")
@RestController
@RequestMapping("/key")
public class DemoController {
    @Autowired
    private RedisTemplate redisTemplate;


    @Operation(summary = "设置一个过期的key")
    @GetMapping(value = "/setkey")
    public int setkey(){
        redisTemplate.opsForValue().set("asdfasdf","kkkkkkk",10, TimeUnit.SECONDS);
        return 0;
    }



}
