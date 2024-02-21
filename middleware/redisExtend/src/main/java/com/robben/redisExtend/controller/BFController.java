package com.robben.redisExtend.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "redis使用")
@RestController
@RequestMapping("/bf")
public class BFController {

    @Autowired
    private RedissonClient redissonClient;

    @Operation(summary = "布隆过滤器的使用")
    @GetMapping(value = "/userBF")
    public int userBF(){
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("phoneList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L,0.03);

        //将号码10086插入到布隆过滤器中
        bloomFilter.add("10086");
        //判断下面号码是否在布隆过滤器中
        System.out.println(bloomFilter.contains("123456"));//false
        System.out.println(bloomFilter.contains("10086"));//true


//        BloomFilter<String> bloomFilter2 = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),100000,0.01);
//        bloomFilter.put("10086");
//        System.out.println(bloomFilter.mightContain("123456"));
//        System.out.println(bloomFilter.mightContain("10086"))


        return 0;
    }





}
