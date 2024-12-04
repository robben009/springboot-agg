package com.robben.agg.cola.service;

import com.robben.agg.cola.stateMachine.OrderStatusEnum;
import com.robben.agg.cola.stateMachine.dto.OrderContext;
import com.robben.agg.cola.stateMachine.dto.OrderEventRecord;

public interface OrderStatusService {

    OrderStatusEnum modifyMyStatus(OrderContext orderContext);

    void saveMyEventRecord(OrderEventRecord orderEventRecord);

}
