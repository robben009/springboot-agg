package com.robben.agg.cola;

import com.robben.agg.cola.ruleengine.OrderAppService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest(classes = ColaApplication.class)
public class TestVipDiscount {
    @Resource
    private OrderAppService orderAppService;

    @Test
    public void testVipDiscount() {
        OrderAppService service = new OrderAppService();
        BigDecimal finalPrice = orderAppService.calculateFinalPrice("VIP", BigDecimal.valueOf(120));
        System.out.println(finalPrice); // 输出 108.0 (120 * 0.9)
    }

}
