package com.robben.agg.base.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.robben.agg.base.resp.ResponseEntityDto;
import com.robben.agg.base.service.LocalCacheService;
import com.robben.agg.base.utils.guava.GuavaCacheUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "本地缓存")
@RestController
@RequestMapping("/localCache")
public class LocalCacheController extends UnifiedReply {
    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Autowired
    private LocalCacheService localCacheService;


    @Operation(summary = "guava本地缓存使用")
    @GetMapping(value = "/guavaCacheUse")
    public String guavaCacheUse(){
        GuavaCacheUtils.setKey("aaa","bbb");

        String one = GuavaCacheUtils.getKey("aaa");
        System.out.println(one);

        ThreadUtil.sleep(3000);

        String two = GuavaCacheUtils.getKey("aaa");
        if(two == null){
            System.out.println(1);
        }
        System.out.println(two + "11");

        return "1";
    }


    @Operation(summary = "caffeine本地缓存使用")
    @GetMapping(value = "/caffeineCacheUse")
    public ResponseEntityDto<Boolean> caffeineCacheUse(@RequestParam String s){
        return buildSuccesResp(localCacheService.getCacheValue(s));
    }


    @Operation(summary = "caffeine本地缓存使用2")
    @GetMapping(value = "/caffeineCacheUse2")
    public ResponseEntityDto<Boolean> caffeineCacheUse2(@RequestParam String s){
        localCacheService.getCacheValue(s);
        localCacheService.getCacheValue(s);

        //获取当前的缓存服务
        Collection<String> cacheNames = caffeineCacheManager.getCacheNames();
        System.out.println(JSON.toJSONString(cacheNames));

        //可以在此打断点直接查看Cache中缓存的值
        Cache cache = caffeineCacheManager.getCache("userCache");
        Map<String, Object> stringObjectMap = cacheToMap(cache);
        System.out.println(JSON.toJSONString(stringObjectMap));

        return buildSuccesResp();
    }


    //通过方法获取所有缓存的key和value
    public static Map<String, Object> cacheToMap(Cache cache) {
        Object obj = cache.getNativeCache();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            log.error("ObjToMap exp", e);
            return null;
        }

        log.info("变动1");
        log.info("变动4");

        // 获取Cache.map中的cache
        return (Map<String, Object>) map.get("cache");
    }



}
