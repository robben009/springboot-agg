package com.robben.agg.base.event;

import com.robben.agg.base.dao.entity.UserInfoEntity;
import org.springframework.context.ApplicationEvent;

public class ContentSmartEvent extends ApplicationEvent {

    private UserInfoEntity userInfoEntity;

    public ContentSmartEvent(Object source, UserInfoEntity UserInfoEntity) {
        super(source);
        this.userInfoEntity = UserInfoEntity;
    }

    public UserInfoEntity getUserVo() {
        return userInfoEntity;
    }

    public void setUserVo(UserInfoEntity UserInfoEntity) {
        this.userInfoEntity = UserInfoEntity;
    }
}
