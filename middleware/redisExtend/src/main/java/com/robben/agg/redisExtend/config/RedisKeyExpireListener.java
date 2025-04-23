package com.robben.agg.redisExtend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyExpireListener implements MessageListener {

    // 监听过期事件
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString(); // 获取过期的 key
        // 注意：message.getChannel() 获取到的是 __keyevent@<db>__:expired
        // message.getBody() 获取到的才是 key 的 byte[] 形式
        log.info("接收到Rediskey过期事件,Key={}, Channel={}", expiredKey, new String(message.getChannel()));
    }


}

