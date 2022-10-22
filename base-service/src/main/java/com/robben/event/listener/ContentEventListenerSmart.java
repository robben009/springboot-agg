package com.robben.event.listener;

import com.alibaba.fastjson.JSON;
import com.robben.controller.EventController;
import com.robben.event.ContentSmartEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentEventListenerSmart implements SmartApplicationListener {

    //如果实现支持该事件类型,那么返回true
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
//        return false; //原方法
        return ContentSmartEvent.class == aClass;
    }

    //指的是谁来发布事件的类(示例中式EventController来发布的)
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
//        return false;
        return sourceType == EventController.class;
    }


    //执行的序列(数值越小越优先)
    @Override
    public int getOrder() {
        return 1;
    }

    //监听以后执行的方法
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ContentSmartEvent sEvent = (ContentSmartEvent)event;
        log.info("ContentEventListenerSmart收到消息{}", JSON.toJSONString(sEvent.getUserVo()));
    }


}
