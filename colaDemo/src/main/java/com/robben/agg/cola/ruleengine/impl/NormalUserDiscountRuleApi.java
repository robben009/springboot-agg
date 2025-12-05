package com.robben.agg.cola.ruleengine.impl;

import com.robben.agg.cola.ruleengine.dto.DiscountContext;
import com.robben.agg.cola.ruleengine.RuleApi;

import java.math.BigDecimal;

public class NormalUserDiscountRuleApi implements RuleApi {
    @Override
    public boolean evaluate(DiscountContext context) {
        return "NORMAL".equals(context.getUserLevel())
                && context.getOrderAmount().compareTo(BigDecimal.valueOf(500)) >= 0;
    }

    @Override
    public void execute(DiscountContext context) {
        context.setDiscountRate(BigDecimal.valueOf(0.95));
    }
}
