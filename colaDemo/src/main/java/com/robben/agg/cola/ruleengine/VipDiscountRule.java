package com.robben.agg.cola.ruleengine;

import java.math.BigDecimal;

public class VipDiscountRule implements Rule{
    @Override
    public boolean evaluate(DiscountContext context) {
        return "VIP".equals(context.getUserLevel())
                && context.getOrderAmount().compareTo(BigDecimal.valueOf(100)) >= 0;
    }

    @Override
    public void execute(DiscountContext context) {
        context.setDiscountRate(BigDecimal.valueOf(0.9));
    }
}
