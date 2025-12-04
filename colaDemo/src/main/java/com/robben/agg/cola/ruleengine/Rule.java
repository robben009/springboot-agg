package com.robben.agg.cola.ruleengine;

public interface Rule {

    boolean evaluate(DiscountContext context);

    void execute(DiscountContext context);

}
