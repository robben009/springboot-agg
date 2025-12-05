package com.robben.agg.cola.ruleengine;


import com.robben.agg.cola.ruleengine.dto.DiscountContext;
import com.robben.agg.cola.ruleengine.impl.NormalUserDiscountRuleApi;
import com.robben.agg.cola.ruleengine.impl.VipDiscountRuleApi;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderAppService {

    public BigDecimal calculateFinalPrice(String userLevel, BigDecimal orderAmount) {
        DiscountContext context = new DiscountContext();
        context.setUserLevel(userLevel);
        context.setOrderAmount(orderAmount);

        // 构建规则引擎（实际中可通过 Spring 注入规则列表）
        RuleEngine engine = new RuleEngine()
                .addRule(new VipDiscountRuleApi())
                .addRule(new NormalUserDiscountRuleApi());

        engine.fire(context);

        return orderAmount.multiply(context.getDiscountRate());
    }

}
