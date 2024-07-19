package com.robben.agg.redisExtend.controller;

import cn.hutool.core.date.SystemClock;
import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Arrays;


@Slf4j
@Tag(name = "redis使用")
@RestController
@RequestMapping("/json")
public class JsonController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JReJSON jReJSON;
    @Autowired
    private Jedis jedis;

    @Operation(summary = "redisJSON的使用")
    @GetMapping(value = "/useRedisJSON")
    public int useRedisJSON(){
        long stime = SystemClock.now();

        jedis.set("kkkk","asdfasdf");

        String testJsonObjectName = "robben";
        jReJSON.set(testJsonObjectName,new Object());
        jReJSON.set(testJsonObjectName,21,new Path(".age"));
        jReJSON.set(testJsonObjectName,"zhangsan",new Path(".name"));
        jReJSON.set(testJsonObjectName,"hello",new Path(".msg"));
        jReJSON.set(testJsonObjectName, Arrays.asList(9,8,7),new Path(".arr"));
        Object result = jReJSON.get(testJsonObjectName);
        log.info("目前的对象:{}",result.toString());

        jReJSON.set(testJsonObjectName,"lisi",new Path(".name"));
        result = jReJSON.get(testJsonObjectName);
        log.info("设置 name=lisi,结果:{}",result.toString());

        jReJSON.arrAppend(testJsonObjectName, new Path(".arr"), 21);
        result = jReJSON.get(testJsonObjectName);
        log.info("在数组追加一个值：21,结果:{}",result.toString());
        result = jReJSON.get(testJsonObjectName);
        log.info("在数组追加一个值：21,结果:{}",result.toString());
        log.info("总耗时:{}", SystemClock.now() - stime);
        return 0;
    }


//    @Operation(summary = "redisJSON的使用2")
//    @GetMapping(value = "/useRedisJSON2")
//    public int useRedisJSON2(){
//        long stime = SystemClock.now();
//        for (int i = 0; i < 1000; i++) {
//            redisTemplate.opsForValue().set("lll","123fff" + i);
//        }
//        log.info("test1:{}ms",SystemClock.now() - stime);
//
//
//        String testJsonObjectName = "robben2";
//        long stime2 = SystemClock.now();
//        for (int i = 0; i < 1000; i++) {
//            jReJSON.set(testJsonObjectName,"zhangsan"+i,new Path(".name"));
//        }
//        log.info("test2:{}ms",SystemClock.now() - stime2);
//
//        jReJSON.set(testJsonObjectName,"zhangsanasdfasdfasdf",new Path(".name"));
//
//        return 0;
//    }



}
