package com.robben.agg.javaBase.singleton;

/**
 * Description： 除了枚举其他几种都会反射破坏了单例
 * Author: robben
 * Date: 2021/4/16 13:43
 */
public enum EnumClass {
    a(1,"a"),
    b(2,"b");

    private int code;
    private String desc;

    EnumClass(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
