package com.robben.agg.base.service.redisMsg;

public interface RedisMQConfig2Handler {

    void handleMessage(String msg);

    String redisChannel();

}
