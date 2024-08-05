package com.robben.agg.cola.stateMachine;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.alibaba.fastjson2.JSON;
import com.robben.agg.cola.common.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SelfStateMachineConfig {
    private final UserService userService;
    private static final String MACHINE_ID = "testMachine";

    @Bean
    private StateMachine<State, Event, Context> init(){
        StateMachineBuilder<State, Event, Context> builder = StateMachineBuilderFactory.create();

        // 定义状态转换事件、条件和动作
        builder.externalTransition()
                .from(State.STATE1)
                .to(State.STATE2)
                .on(Event.EVENT1)
                .when(this::checkCondition)
                .perform((from, to, event, context) -> userService.updateName(context.getMessage()));

        builder.externalTransitions()
                .fromAmong(State.STATE1, State.STATE2, State.STATE3)
                .to(State.STATE4)
                .on(Event.EVENT4)
                .perform((from, to, event, context) -> userService.updateName(context.getMessage()));

        builder.externalTransition()
                .from(State.STATE4)
                .to(State.STATE1)
                .on(Event.EVENT2)
                .perform((from, to, event, context) -> userService.updateName(context.getMessage()));

        builder.internalTransition()
                .within(State.STATE2)
                .on(Event.EVENT3)
                .perform((from, to, event, context) -> userService.updateName(context.getMessage()));

        return builder.build(MACHINE_ID);
    }


    private boolean checkCondition(Context context) {
        if("ok".equals(context.getMessage())){
            return true;
        }
        return false;
    }


}
