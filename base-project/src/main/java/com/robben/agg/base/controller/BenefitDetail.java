package com.robben.controller;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BenefitDetail {
    /**
     * 权益实例code
     */
    private String rightsInstanceCode;
    private String outerEntityId;

    /**
     * 卡号
     */
    private String chargeCardCode;

    /**
     * 0: 满减券
     * 1: 折扣券
     * 3: 充电额度卡
     * 6：充电金额卡
     */
    private String verifyType;

    /**
     * 实际抵扣的电量
     */
    private BigDecimal verifyQuantity;

    /**
     * 抵扣的金额
     */
    private BigDecimal verifyAmount;

    private Integer state;
    private String rightsTempleCode;

    /**
     * 权益名称
     */
    private String rightsName;

    private String targetPlatformCode;

    //单位分
    private BigDecimal reduceFeeCent;

}
