package com.hjz.statemachine.constant;

public enum OrderStatusEnum {
    // 待支付，待发货，待收货，已完成
    WAIT_PAYMENT(1, "待支付"),
    WAIT_DELIVER(2, "待发货"),
    WAIT_RECEIVE(3, "待收货"),
    FINISH(4, "已完成");

    private Integer key;
    private String desc;

    OrderStatusEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }
    public String getDesc() {
        return desc;
    }

    public static OrderStatusEnum getByKey(Integer key) {
        for (OrderStatusEnum e : values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        throw new RuntimeException("enum not exists.");
    }

}