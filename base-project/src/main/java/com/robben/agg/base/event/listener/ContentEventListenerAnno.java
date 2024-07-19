package com.robben.event.listener;

import com.alibaba.fastjson2.JSON;
import com.robben.event.ContentAnnoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentEventListenerAnno {

    //监听以后执行的方法(基于注解的监听)
    @EventListener
    //添加@Async表示异步来执行,否则为同步
    @Async
    public void onApplicationEvent(ContentAnnoEvent event) {
        log.info("ContentEventListenerAnno收到信息--:{},event_thread:{}",JSON.toJSONString(event.getUserVo()),Thread.currentThread().getName());
    }

    @EventListener
    //添加@Async表示异步来执行,否则为同步
    @Async
    public void onApplicationEvent2(ContentAnnoEvent event) {
        log.info("ContentEventListenerAnno_onApplicationEvent2收到信息--:{},event_thread:{}",JSON.toJSONString(event.getUserVo())
                ,Thread.currentThread().getName());
    }

}
