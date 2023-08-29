package com.robben.service.redisMsg;

import com.robben.config.RedisConfig.RedisMQChannels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//redis的订阅与发布测试案例，通过接口的实现来区分不同的信道的处理
@Slf4j
@Component
public class RedisMQConfig2HandlerImpl2 implements RedisMQConfig2Handler {

    @Override
    public void handleMessage(String msg) {
        log.info("RedisMQConfig2HandlerImpl2_收到消息为：{}",msg);
    }

    @Override
    public String redisChannel() {
        return RedisMQChannels.redisChannelTest2;
    }
}
