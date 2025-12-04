package com.robben.agg.cola.ruleengine;

import java.math.BigDecimal;

public class NormalUserDiscountRule implements Rule{
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
