package com.robben.agg.cola.stateMachine;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.alibaba.fastjson2.JSON;
import com.robben.agg.cola.stateMachine.dto.OrderContext;
import com.robben.agg.cola.stateMachine.handle.OrderAbstractHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SelfStateMachineConfig {
    public static final String MACHINE_ID = "testMachine";

    @Bean
    private StateMachine<OrderStatusEnum, OrderEventEnum, OrderContext> init(){
        StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder = StateMachineBuilderFactory.create();

        // 定义状态转换事件、条件和动作
        builder.externalTransition()
                .from(OrderStatusEnum.STATE1)
                .to(OrderStatusEnum.STATE2)
                .on(OrderEventEnum.EVENT1)
                .when(this::checkCondition)
                .perform(OrderAbstractHandlerFactory.getOrderStateMachineHandler(OrderEventEnum.EVENT1));

        builder.externalTransitions()
                .fromAmong(OrderStatusEnum.STATE2, OrderStatusEnum.STATE3)
                .to(OrderStatusEnum.STATE4)
                .on(OrderEventEnum.EVENT2)
                .perform(OrderAbstractHandlerFactory.getOrderStateMachineHandler(OrderEventEnum.EVENT2));

        builder.setFailCallback((sourceMyStateEnum, myEventEnum, myContext) -> {
            log.warn("出错了,{}", JSON.toJSONString(myContext));
            throw new BizException("error");
        });

        return builder.build(MACHINE_ID);
    }

    //这个也可以抽离出去作为独立的类来处理
    private boolean checkCondition(OrderContext orderContext) {
        return true;
    }


}
