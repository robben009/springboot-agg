package com.robben.agg.cola.ruleengine;

import com.robben.agg.cola.ruleengine.dto.DiscountContext;

public interface RuleApi {

    boolean evaluate(DiscountContext context);

    void execute(DiscountContext context);

}
