package com.robben.agg.base.event;

import com.robben.agg.base.entity.UserInfoEntity;
import org.springframework.context.ApplicationEvent;

public class ContentAnnoEvent extends ApplicationEvent {

    private UserInfoEntity u;

    public ContentAnnoEvent(Object source, UserInfoEntity u) {
        super(source);
        this.u = u;
    }

    public UserInfoEntity getUserVo() {
        return u;
    }

    public void setUserVo(UserInfoEntity UserInfoEntity) {
        this.u = UserInfoEntity;
    }
}
