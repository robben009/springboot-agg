package com.robben.agg.dutychain.method2_spring;

import com.robben.agg.commonDto.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderHandleChainService {
    @Autowired
    private List<OrderHandleService> orderHandleServiceList;

    public OrderVo execute(OrderVo context) {
        for (OrderHandleService handleIntercept : orderHandleServiceList) {
            context = handleIntercept.handle(context);
        }
        return context;
    }

}
