package com.robben.event.listener;

import com.alibaba.fastjson.JSON;
import com.robben.event.ContentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentEventListener3 implements ApplicationListener<ContentEvent> {

    @Override
    public void onApplicationEvent(ContentEvent event) {
        log.info("ContentEventListener3收到信息--:{},event_thread:{}",JSON.toJSONString(event.getSource()),Thread.currentThread().getName());
    }
}
