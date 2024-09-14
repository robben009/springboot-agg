package com.robben.agg.javaBase.proxy.cglib;


import com.robben.agg.javaBase.proxy.JdkDong.LiuDeHua;
import com.robben.agg.javaBase.proxy.JdkDong.Star;

public class Test {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Star star2 = (Star)proxy.CreatProxyedObj(LiuDeHua.class);

        System.out.println(star2.dance("lalal"));
    }

}
