package com.robben.event.listener;

import com.alibaba.fastjson.JSON;
import com.robben.event.ContentAnnoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentEventListenerAnno2 {

    //监听以后执行的方法
    @EventListener
    @Async
    public void onApplicationEvent(ContentAnnoEvent event) {
        log.info("ContentEventListenerAnno2收到信息--:{},event_thread:{}",JSON.toJSONString(event.getUserVo()),Thread.currentThread().getName());
    }


}
