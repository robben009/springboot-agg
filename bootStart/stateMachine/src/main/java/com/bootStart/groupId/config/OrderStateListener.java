package com.bootStart.groupId.config;

import com.bootStart.groupId.constant.OrderStatusChangeEventEnum;
import com.bootStart.groupId.constant.OrderStatusEnum;
import com.bootStart.groupId.generator.domain.TbOrder;
import com.bootStart.groupId.generator.mapper.TbOrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;


@Component
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStateListener {
    @Resource
    private TbOrderMapper orderMapper;
    @Resource
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> orderStateMachine;


    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public void payTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        try {
            log.info("支付，监听状态机信息：{}",  message.getHeaders());
            //更新订单
            order.setStatus(OrderStatusEnum.WAIT_DELIVER.getKey());
            orderMapper.updateById(order);

            boolean flag = Math.random() * 10 < 5 ? true : false;
            if(!flag){
                throw new RuntimeException("执行业务异常");
            }

            //模拟处理成功
            orderStateMachine.getExtendedState().getVariables().put(order.getId(),1);
        } catch (RuntimeException e) {
            log.error("payTransition 出现异常：{}",e);
            //模拟处理失败
            orderStateMachine.getExtendedState().getVariables().put(order.getId(),0);
            throw e;

        }
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public void deliverTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("发货，监听状态机信息：{}",  message.getHeaders());
        //更新订单
        order.setStatus(OrderStatusEnum.WAIT_RECEIVE.getKey());
        orderMapper.updateById(order);
        //TODO 其他业务
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public void receiveTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("确认收货，监听状态机信息：{}",  message.getHeaders());
        //更新订单
        order.setStatus(OrderStatusEnum.FINISH.getKey());
        orderMapper.updateById(order);
        //TODO 其他业务
    }
}
