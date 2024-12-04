package com.robben.agg.cola.service.impl;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.fastjson2.JSON;
import com.robben.agg.cola.stateMachine.dto.OrderContext;
import com.robben.agg.cola.stateMachine.dto.OrderEventRecord;
import com.robben.agg.cola.service.OrderStatusService;
import com.robben.agg.cola.stateMachine.OrderEventEnum;
import com.robben.agg.cola.stateMachine.OrderStatusEnum;
import com.robben.agg.cola.stateMachine.SelfStateMachineConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    /**
     * Transactional 可以保证在订单状态流转的时候,保证事务
     */
    @Override
    @Transactional
    public OrderStatusEnum modifyMyStatus(OrderContext orderContext) {
        StateMachine<OrderStatusEnum, OrderEventEnum, OrderContext> stateMachine = StateMachineFactory.get(SelfStateMachineConfig.MACHINE_ID);
        //校验
        OrderStatusEnum fromStatusEnum = checkEventParam(orderContext);
        //事件记录初始化
        OrderEventRecord orderEventRecord = initEventRecord(orderContext, fromStatusEnum);

        try {
            //状态机执行状态的流转
            OrderStatusEnum toStatusEnum = stateMachine.fireEvent(fromStatusEnum, orderContext.getEventEnum(), orderContext);

            //如果状态相等,则表明状态没有改变则认为是变更失败了
            if (!Objects.equals(fromStatusEnum, toStatusEnum)) {
                orderEventRecord.setEventResult(true);
            }

            orderEventRecord.setToStatus(toStatusEnum.getCode());
            return toStatusEnum;
        } catch (BizException e) {
            //结果
            orderEventRecord.setToStatus(fromStatusEnum.getCode());
            log.error("订单状态流转失败,myContext={}", JSON.toJSONString(orderContext), e);
            throw e;
        } finally {
            saveMyEventRecord(orderEventRecord);
        }
    }


    private OrderEventRecord initEventRecord(OrderContext orderContext, OrderStatusEnum fromStatusEnum) {
        OrderEventRecord eventRecord = new OrderEventRecord();
        eventRecord.setOrderId(orderContext.getOrderId());
        eventRecord.setFromStatus(fromStatusEnum.getCode());
        eventRecord.setEventCode(orderContext.getEventEnum().getDesc());
        eventRecord.setEventResult(false);
        //补充调用者的信息
        eventRecord.setCreateBy(orderContext.getCallUserId());
        eventRecord.setCreateDate(new Date());
        return eventRecord;
    }


    @Override
    @Async
    public void saveMyEventRecord(OrderEventRecord orderEventRecord) {
        //存储事件变更的数据放在数据库,方便查询
    }


    private OrderStatusEnum checkEventParam(OrderContext orderContext) {
        if (StringUtils.isAnyBlank(orderContext.getOrderId(), orderContext.getCallUserId())) {
            throw new BizException("信息为空");
        }
        if (orderContext.getEventEnum() == null) {
            throw new BizException("状态变更失败 未指定事件");
        }

        OrderStatusEnum fromStatusEnum = OrderStatusEnum.getEnumByCode(orderContext.getNowStatus());
        if (fromStatusEnum == null) {
            throw new BizException("状态变更失败 原状态有误");
        }
        return fromStatusEnum;
    }


}