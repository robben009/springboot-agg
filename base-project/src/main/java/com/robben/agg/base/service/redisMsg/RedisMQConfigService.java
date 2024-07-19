package com.robben.service.redisMsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMQConfigService {
    /**
     * 接收消息的方法1
     **/
    public void receiveMessage(String message){
        log.info("RedisMQConfigService_receiveMessage接收到的消息：{}",message);
    }

    /**
     * 接收消息的方法2
     **/
    public void receiveMessage2(String message){
        log.info("RedisMQConfigService_receiveMessage2接收到的消息：{}",message);
    }

}
