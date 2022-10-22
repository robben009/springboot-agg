package com.robben.event;

import com.robben.entity.UserInfoEntity;
import org.springframework.context.ApplicationEvent;

public class ContentAnnoEvent extends ApplicationEvent {

    private UserInfoEntity UserInfoEntity;

    public ContentAnnoEvent(Object source, UserInfoEntity UserInfoEntity) {
        super(source);
        this.UserInfoEntity = UserInfoEntity;
    }

    public UserInfoEntity getUserVo() {
        return UserInfoEntity;
    }

    public void setUserVo(UserInfoEntity UserInfoEntity) {
        this.UserInfoEntity = UserInfoEntity;
    }
}
