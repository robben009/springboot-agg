package com.robben.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocalCacheService {


    @Cacheable(value = "userCache",cacheManager = "caffeineCacheManager",key = "#p0")
    public String getCacheValue(String s,String s1) {
        System.out.println("本地缓存没走");
        return s;
    }




}
