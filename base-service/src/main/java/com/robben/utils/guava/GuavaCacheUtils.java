package com.robben.utils.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author hjz
 * @version 1.0
 * @date 2022/6/24 10:16
 */
@Slf4j
public class GuavaCacheUtils {
    public static final String TOKEN_PREFIX = "token_";
    /**
     *过期时间12小时
     * @info 2019-10-17 14:28 liuxi
     * 使用了LRU算法
     */
    private static LoadingCache localcache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(2, TimeUnit.HOURS).build(new CacheLoader() {
        // 如果 get取值的时候没有值，默认调用这个方法返回值.由于LoadingCache不能缓存值为null 所以用字符串代替
        @Override
        public Object load(Object o) throws Exception {
            return "null";
        }
    });

    public static void setKey(String key, String value) {
        localcache.put(key, value);
    }


    public static String getKey(String key) {
        if(StringUtils.isEmpty(key)){
            return null;
        }

        String value = null;
        try {
            value = (String) localcache.get(key);
            if ("null".equals(value)) {
                value = null;
            }
        } catch (ExecutionException e) {
            log.error("本地缓存获取失败:",e);
            e.printStackTrace();
        }
        return value;
    }


}
