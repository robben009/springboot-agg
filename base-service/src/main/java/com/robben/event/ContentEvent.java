package com.robben.event;

import org.springframework.context.ApplicationEvent;

public class ContentEvent extends ApplicationEvent {

    /**
     * 其中的source为发送的内容
     * @param source
     */
    public ContentEvent(Object source) {
        super(source);
    }

}
