package com.robben.agg.base.config.RedisConfig;

import org.springframework.stereotype.Component;

@Component
public class RedisMQChannels {

    /**
     * 测试redis发布订阅的频道
     */
    public final static String redisChannelTest1 = "redisChannel_1";

    public final static String redisChannelTest2 = "redisChannel_2";

}
