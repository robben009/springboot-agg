package com.bootStart.groupId.config;

import com.bootStart.groupId.anno.SmHandle;
import com.bootStart.groupId.constant.ComConstant;
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
@WithStateMachine(name = OrderStateMachineConfig.orderStateMachine)
@Slf4j
public class OrderStateListener {
    @Resource
    private TbOrderMapper orderMapper;

    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    @SmHandle(key = ComConstant.tbOrderKey_pay)
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
        } catch (RuntimeException e) {
            log.error("payTransition 出现异常：{}",e);
            throw e;
        }
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    @SmHandle(key = ComConstant.tbOrderKey_deliver)
    public void deliverTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("发货，监听状态机信息：{}",  message.getHeaders());
        //更新订单
        order.setStatus(OrderStatusEnum.WAIT_RECEIVE.getKey());
        orderMapper.updateById(order);
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    @SmHandle(key = ComConstant.tbOrderKey_receive)
    public void receiveTransition(Message<OrderStatusChangeEventEnum> message) {
        TbOrder order = (TbOrder) message.getHeaders().get("order");
        log.info("确认收货，监听状态机信息：{}",  message.getHeaders());
        //更新订单
        order.setStatus(OrderStatusEnum.FINISH.getKey());
        orderMapper.updateById(order);
    }

}
