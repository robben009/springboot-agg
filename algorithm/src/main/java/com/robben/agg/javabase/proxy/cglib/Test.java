package com.robben.agg.javabase.proxy.cglib;


import com.robben.agg.javabase.proxy.JdkDong.LiuDeHua;
import com.robben.agg.javabase.proxy.JdkDong.Star;

public class Test {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Star star2 = (Star)proxy.CreatProxyedObj(LiuDeHua.class);

        System.out.println(star2.dance("lalal"));
    }

}
