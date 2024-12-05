package com.robben.agg.dutychain.method2_spring;

import com.robben.agg.commonDto.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
public class OrderHandleServiceImpl2 implements OrderHandleService {
    @Override
    public OrderVo handle(OrderVo context) {
        log.info("订单Handle2");
        return null;
    }

}
