package com.robben.agg.cola.stateMachine.handle;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import org.springframework.beans.factory.InitializingBean;

/**
 * 之索引实现InitializingBean，是为了在每个handle实现类中能够在初始化bean完成后自动填充到处理器的map集合中,
 * 方便OrderAbstractHandler的map中获取到bean
 * @param <S>
 * @param <E>
 * @param <C>
 */
public abstract class OrderAbstractHandler<S, E, C> implements Condition<C>, Action<S, E, C>, InitializingBean {
}
