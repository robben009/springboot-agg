package com.robben.event.listener;

import com.alibaba.fastjson2.JSON;
import com.robben.event.ContentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentEventListener2 implements ApplicationListener<ContentEvent> {

    @Override
    public void onApplicationEvent(ContentEvent event) {
        log.info("ContentEventListener2收到信息--:{},event_thread:{}", JSON.toJSONString(event.getSource()),Thread.currentThread().getName());
    }
}
