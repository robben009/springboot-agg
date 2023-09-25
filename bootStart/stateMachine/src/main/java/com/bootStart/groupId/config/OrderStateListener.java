package com.bootStart.groupId.config;

import com.bootStart.groupId.constant.OrderStatusChangeEventEnum;
import com.bootStart.groupId.constant.OrderStatusEnum;
import com.bootStart.groupId.generator.domain.TbOrder;
import com.bootStart.groupId.generator.mapper.TbOrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;


@Component
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListener {
    @Resource
    private TbOrderMapper orderMapper;

    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public void payTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("支付，状态机反馈信息：{}",  message.getHeaders().toString());
        //更新订单
        order.setStatus(OrderStatusEnum.WAIT_DELIVER.getKey());
        orderMapper.updateById(order);
        //TODO 其他业务
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public void deliverTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("发货，状态机反馈信息：{}",  message.getHeaders().toString());
        //更新订单
        order.setStatus(OrderStatusEnum.WAIT_RECEIVE.getKey());
        orderMapper.updateById(order);
        //TODO 其他业务
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public void receiveTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("确认收货，状态机反馈信息：{}",  message.getHeaders().toString());
        //更新订单
        order.setStatus(OrderStatusEnum.FINISH.getKey());
        orderMapper.updateById(order);
        //TODO 其他业务
    }
}
