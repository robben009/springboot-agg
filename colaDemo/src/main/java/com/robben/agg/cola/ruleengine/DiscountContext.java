package com.robben.agg.cola.ruleengine;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountContext {

    private String userLevel;      // 用户等级：VIP, NORMAL
    private BigDecimal orderAmount;
    private BigDecimal discountRate = BigDecimal.ONE; // 默认无折扣

}
