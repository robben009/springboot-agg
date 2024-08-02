package com.robben.agg.base.contants;

public enum BbResultEnum {

    SUCCESS("20000", "请求成功"),
    PARAMS_ERROR("1001", "请求参数有误!"),
    NO_TOKEN("444", "请重新登录系统!"),
    TOKEN_INVALID("444", "请重新登录!"),
    NO_POWER("1004", "没有权限!"),

    NO_USER("1005", "用户名/密码错误!"),
    USERNAME_REPEAT("1006", "该用户名已经存在!"),
    ROLENAME_REPEAT("1007", "该角色名已经存在!"),
    USER_STATENO("1008", "该用户已经被禁用!"),
    ROLE_NO_POWER("1009", "该角色没有权限!"),
    NO_ROLE("1010", "该角色不存在!"),

    POWERLIST_NULL("1011", "权限列表为空!"),
    CODE_ERROR("1012", "验证码错误,请重试!"),
    RESOURCE_REPEAT("1013", "该资源已存在!"),
    CONFIG_REPEAT("1014", "该配置项已存在!"),
    USER_STATE_DISABLE("1015", "该账号密码输入错误次数过多,账号被锁定!"),

    NO_USERID("1005", "该用户ID为空!"),
    IP_STATE_DISABLE("1015", "该IP登陆输入错误次数过多,账号被锁定!"),
    GETRESOURCES_ERROR("1016", "获取没有的权限,请求参数错误"),

    UNKNOWN_ERROR("40000", "服务器繁忙!");

    private String code;
    private String message;

    BbResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}