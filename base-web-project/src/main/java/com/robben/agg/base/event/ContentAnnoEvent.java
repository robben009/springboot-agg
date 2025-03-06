package com.robben.agg.base.event;

import com.robben.agg.base.dao.entity.UserInfo;
import org.springframework.context.ApplicationEvent;

public class ContentAnnoEvent extends ApplicationEvent {

    private UserInfo u;

    public ContentAnnoEvent(Object source, UserInfo u) {
        super(source);
        this.u = u;
    }

    public UserInfo getUserVo() {
        return u;
    }

    public void setUserVo(UserInfo UserInfo) {
        this.u = UserInfo;
    }
}
