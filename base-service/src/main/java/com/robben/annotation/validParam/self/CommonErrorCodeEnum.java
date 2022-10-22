package com.robben.annotation.validParam.self;

/**
 * 系统异常错误码
 */
public enum CommonErrorCodeEnum {
    P_Common_ParamMissing("P_Common_ParamMissing", "参数缺失"),

    P_Common_ComParamMissing("P_Common_ComParamMissing", "commonParam参数缺失,不能为空"),
    P_Common_UserInfoCanNotEmpty("P_Common_UserInfoCanNotEmpty", "用户信息不能为空"),
    P_Common_OemCanNotEmpty("P_Common_OemCanNotEmpty", "Oem信息不能为空"),
    P_Common_BrandCodeNotEmpty("P_Common_BrandCodeNotEmpty", "BrandCode不能为空"),
    P_Common_BizCodeCanNotEmpty("P_Common_BizCodeCanNotEmpty", "bizCode不能为空"),
    P_Common_SourceTypeCanNotEmpty("P_Common_SourceTypeCanNotEmpty", "sourceType不能为空"),
    P_Common_DeviceIdCanNotEmpty("P_Common_DeviceIdCanNotEmpty", "设备编码不能为空"),

    P_Common_format_error("P_Common_format_error", "参数结构不合法,无法转换成对象"),
    P_Page_ParamNull("P_Page_ParamNull", "分页数为空"),
    P_Page_ParamError("P_Page_ParamError", "分页数不合法"),

    B_System_InterfaceNotExist("B_System_InterfaceNotExist", "接口不存在"),
    B_System_RepeatSubmit("B_System_RepeatSubmit", "重复提交"),
    B_System_TokenExpire("B_System_TokenExpire", "token过期"),
    B_System_AccountInfoNotFound("B_System_AccountInfoNotFound", "未查到用户信息"),

    S_System_error("S_System_error", "系统异常"),


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
