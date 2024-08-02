package com.robben.agg.dutychain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractHandler {
    /**
     * 下一关用当前抽象类来接收
     */
    protected AbstractHandler selfHandle;

    public void setSelfHandle(AbstractHandler selfHandle) {
        this.selfHandle = selfHandle;
    }

    public abstract int handler(Order order);



}
