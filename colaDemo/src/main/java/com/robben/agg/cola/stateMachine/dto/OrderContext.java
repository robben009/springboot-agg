package com.robben.agg.cola.stateMachine.dto;

import com.robben.agg.cola.stateMachine.OrderEventEnum;
import lombok.Data;

@Data
public class OrderContext {


    public String orderId;

    /**
     * 当前状态
     */
    public String nowStatus;

    //对应的时间
    private OrderEventEnum eventEnum;

    /**
     * 调用者ID
     */
    private String callUserId;

    /**
     * 扩展数据
     */
    private Object extData;


}
