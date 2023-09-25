package com.bootStart.groupId;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bootStart.groupId.constant.OrderStatusChangeEventEnum;
import com.bootStart.groupId.constant.OrderStatusEnum;
import com.bootStart.groupId.generator.domain.TbOrder;
import com.bootStart.groupId.generator.mapper.TbOrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {
    @Resource
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> orderStateMachine;
    @Resource
    private StateMachinePersister<OrderStatusEnum, OrderStatusChangeEventEnum, String> stateMachineRedisPersister;
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
        boolean sendResult = false;
        try {
            //启动状态机
            orderStateMachine.start();
            //尝试恢复状态机状态
            stateMachineRedisPersister.restore(orderStateMachine, String.valueOf(order.getId()));
            Message message = MessageBuilder.withPayload(changeEvent).setHeader("order", order).build();
            sendResult = orderStateMachine.sendEvent(message);

            //获取到监听的结果信息
            Integer handleReuslt = (Integer) orderStateMachine.getExtendedState().getVariables().get(order.getId());
            //操作完成之后,删除本次对应的key信息
            orderStateMachine.getExtendedState().getVariables().remove(order.getId());
            //如果事务执行成功，则持久化状态机(注意存储的key为order.getId())
            if(Objects.equals(1,Integer.valueOf(handleReuslt))){
                //持久化状态机状态
                stateMachineRedisPersister.persist(orderStateMachine, String.valueOf(order.getId()));
            }else {
                //订单执行业务异常
                log.info("订单执行业务异常 {}",changeEvent);
            }

        } catch (Exception e) {
            log.error("订单操作失败:{}", e);
        } finally {
            orderStateMachine.stop();
        }
        return sendResult;
    }


}