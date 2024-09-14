package com.robben.agg.base.contants;

/**
 * 系统异常错误码
 */
public enum CommonErrorCodeEnum {
    P_Common_ParamMissing("P_Common_ParamMissing", "参数缺失"),


    ;

    private String value;

    private String name;

    CommonErrorCodeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static CommonErrorCodeEnum getByValue(String value) {
        for (CommonErrorCodeEnum priceStatus : CommonErrorCodeEnum.values()) {
            if (priceStatus.value.equals(value)) {
                return priceStatus;
            }
        }
        return null;
    }

}
