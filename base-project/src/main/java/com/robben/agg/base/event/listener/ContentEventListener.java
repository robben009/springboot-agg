package com.robben.agg.base.event.listener;

import com.alibaba.fastjson2.JSON;
import com.robben.event.ContentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 引入了ApplicationListener采用了ApplicationEvent做为入参,这个方法一有时间就会监听，不会区分事件类型
 * 可以在else后面打印日志查看
 */
@Slf4j
@Component
public class ContentEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContentEvent){
            log.info("ContentEventListener收到信息--:{},event_thread:{}", JSON.toJSONString(event.getSource()),Thread.currentThread().getName());
        }
    }

}
