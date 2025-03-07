package com.robben.agg.base.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BenefitDetailReq implements Serializable {

    private String rightsInstanceCode;

    private String cardCode;

    private String verifyType;


}
