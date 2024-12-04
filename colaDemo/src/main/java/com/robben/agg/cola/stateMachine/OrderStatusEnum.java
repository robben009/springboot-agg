package com.robben.agg.cola.stateMachine;

import com.github.xiaoymin.knife4j.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    STATE1("状态1","aaaaaaa"),
    STATE2("状态2","bbbbbbbb"),
    STATE3("状态2","cccccccc"),
    STATE4("状态2","ddddddd")
    ;

    private String code;
    private String desc;

    OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static OrderStatusEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }

        for (OrderStatusEnum statusEnum : OrderStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }

        return null;
    }

}
