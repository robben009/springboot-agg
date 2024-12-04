package com.robben.agg.cola.stateMachine;

import lombok.Getter;

@Getter
public enum OrderEventEnum {
    EVENT1("事件1", "aaaaaaa"),
    EVENT2("事件2", "bbbbbb"),
    EVENT3("事件3", "ccccccc")
    ;

    private String code;
    private String desc;

    OrderEventEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}