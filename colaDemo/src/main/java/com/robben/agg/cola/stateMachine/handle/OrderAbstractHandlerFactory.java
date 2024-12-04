package com.robben.agg.cola.stateMachine.handle;

import com.robben.agg.cola.stateMachine.OrderEventEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class OrderAbstractHandlerFactory {
    private static final Map<OrderEventEnum, OrderAbstractHandler> orderHandlerMap = new HashMap<>();

    //依据不同的事件类型获取对应的处理
    public static OrderAbstractHandler getOrderStateMachineHandler(OrderEventEnum orderEventEnum) {
        OrderAbstractHandler stateMachineHandler = orderHandlerMap.get(orderEventEnum);
        if (stateMachineHandler == null) {
            log.error("未找到对应的处理器={}", orderEventEnum.getDesc());
            return null;
        }
        return stateMachineHandler;
    }

    //注册
    public static void registerHandler(OrderEventEnum orderEventEnum, OrderAbstractHandler handler) {
        if (orderEventEnum == null) {
            return;
        }

        orderHandlerMap.put(orderEventEnum, handler);
    }
}
