package com.robben.agg.base.event;

import com.robben.agg.base.dao.entity.UserInfo;
import org.springframework.context.ApplicationEvent;

public class ContentSmartEvent extends ApplicationEvent {

    private UserInfo userInfo;

    public ContentSmartEvent(Object source, UserInfo UserInfo) {
        super(source);
        this.userInfo = UserInfo;
    }

    public UserInfo getUserVo() {
        return userInfo;
    }

    public void setUserVo(UserInfo UserInfo) {
        this.userInfo = UserInfo;
    }
}
