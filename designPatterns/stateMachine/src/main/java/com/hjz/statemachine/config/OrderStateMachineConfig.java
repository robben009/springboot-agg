package com.hjz.statemachine.config;

import com.hjz.statemachine.constant.OrderStatusChangeEventEnum;
import com.hjz.statemachine.constant.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine(name = OrderStateMachineConfig.orderStateMachine)
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderStatusChangeEventEnum> {

    public static final String orderStateMachine = "orderStateMachine";

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> states) throws Exception {
        states.withStates()
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }


    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderStatusChangeEventEnum> transitions) throws Exception {
        transitions
                //支付事件:待支付-》待发货
                .withExternal().source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.WAIT_DELIVER).event(OrderStatusChangeEventEnum.PAYED)
                .and()
                //发货事件:待发货-》待收货
                .withExternal().source(OrderStatusEnum.WAIT_DELIVER).target(OrderStatusEnum.WAIT_RECEIVE).event(OrderStatusChangeEventEnum.DELIVERY)
                .and()
                //收货事件:待收货-》已完成
                .withExternal().source(OrderStatusEnum.WAIT_RECEIVE).target(OrderStatusEnum.FINISH).event(OrderStatusChangeEventEnum.RECEIVED);
    }
}
