package com.robben.agg.cola.stateMachine.handle;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import org.springframework.beans.factory.InitializingBean;


public abstract class OrderAbstractHandler<S, E, C> implements Condition<C>, Action<S, E, C>, InitializingBean {
}
