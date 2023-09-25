package com.bootStart.groupId;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bootStart.groupId.constant.OrderStatusChangeEventEnum;
import com.bootStart.groupId.constant.OrderStatusEnum;
import com.bootStart.groupId.generator.domain.TbOrder;
import com.bootStart.groupId.generator.mapper.TbOrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {
    @Resource
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> orderStateMachine;
    @Resource
    private StateMachinePersister<OrderStatusEnum, OrderStatusChangeEventEnum, String> stateMachineMemPersister;
    @Resource
    private TbOrderMapper tbOrderMapper;
    /**
     * 创建订单
     *
     * @param tbOrder
     * @return
     */
    public TbOrder create(TbOrder tbOrder) {
        tbOrder.setStatus(OrderStatusEnum.WAIT_PAYMENT.getKey());
        tbOrderMapper.insert(tbOrder);
        return tbOrder;
    }
    /**
     * 对订单进行支付
     *
     * @param id
     * @return
     */
    public TbOrder pay(Long id) {
        TbOrder tbOrder = tbOrderMapper.selectById(id);
        log.info("线程名称：{},尝试支付，订单号：{}" ,Thread.currentThread().getName() , id);
        if (!sendEvent(OrderStatusChangeEventEnum.PAYED, tbOrder)) {
            log.error("线程名称：{},支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), tbOrder);
            throw new RuntimeException("支付失败, 订单状态异常");
        }
        return tbOrder;
    }
    /**
     * 对订单进行发货
     *
     * @param id
     * @return
     */
    public TbOrder deliver(Long id) {
        TbOrder tbOrder = tbOrderMapper.selectById(id);
        log.info("线程名称：{},尝试发货，订单号：{}" ,Thread.currentThread().getName() , id);
        if (!sendEvent(OrderStatusChangeEventEnum.DELIVERY, tbOrder)) {
            log.error("线程名称：{},发货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), tbOrder);
            throw new RuntimeException("发货失败, 订单状态异常");
        }
        return tbOrder;
    }
    /**
     * 对订单进行确认收货
     *
     * @param id
     * @return
     */
    public TbOrder receive(Long id) {
        TbOrder tbOrder = tbOrderMapper.selectById(id);
        log.info("线程名称：{},尝试收货，订单号：{}" ,Thread.currentThread().getName() , id);
        if (!sendEvent(OrderStatusChangeEventEnum.RECEIVED, tbOrder)) {
            log.error("线程名称：{},收货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), tbOrder);
            throw new RuntimeException("收货失败, 订单状态异常");
        }
        return tbOrder;
    }


    /**
     * 发送订单状态转换事件
     * synchronized修饰保证这个方法是线程安全的
     *
     * @param changeEvent
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(OrderStatusChangeEventEnum changeEvent, TbOrder order) {
        boolean result = false;
        try {
            //启动状态机
            orderStateMachine.start();
            //尝试恢复状态机状态
            stateMachineMemPersister.restore(orderStateMachine, String.valueOf(order.getId()));
            Message message = MessageBuilder.withPayload(changeEvent).setHeader("order", order).build();
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            stateMachineMemPersister.persist(orderStateMachine, String.valueOf(order.getId()));
        } catch (Exception e) {
            log.error("订单操作失败:{}", e);
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}