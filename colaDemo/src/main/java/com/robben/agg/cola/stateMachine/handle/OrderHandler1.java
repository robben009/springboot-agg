package com.robben.agg.cola.stateMachine.handle;

import com.robben.agg.cola.stateMachine.OrderStatusEnum;
import com.robben.agg.cola.stateMachine.dto.OrderContext;
import com.robben.agg.cola.stateMachine.OrderEventEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderHandler1 extends OrderAbstractHandler<OrderStatusEnum, OrderEventEnum, OrderContext> {

    @Override
    public void execute(OrderStatusEnum from, OrderStatusEnum to, OrderEventEnum event, OrderContext context) {
        log.info("事件 MyEventEnum.EVENT1 触发了");

        //额外参数可以转换成请求参数或者其他封转的参数
        Object extData = context.getExtData();

        //执行业务逻辑

    }

    //此处可以再次校验这个发生的前提条件,如果不需要则可以在抽象类中删除此继承
    @Override
    public boolean isSatisfied(OrderContext context) {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        OrderAbstractHandlerFactory.registerHandler(OrderEventEnum.EVENT1, this);
    }


}
