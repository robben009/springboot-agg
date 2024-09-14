package com.robben.agg.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocalCacheService {


    @Cacheable(value = "userCache",cacheManager = "caffeineCacheManager",key = "#p0")
    public String getCacheValue(String s) {
        log.info("本地缓存没走");

        if(s.equals("1")){
            return null;
        }

        return s;
    }




}
