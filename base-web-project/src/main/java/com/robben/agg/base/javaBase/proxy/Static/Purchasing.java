package com.robben.agg.base.javaBase.proxy.Static;

public class Purchasing implements Serve {
    private Serve realityFactory;

    public Purchasing(Serve realityFactory) {
        this.realityFactory= realityFactory;    //注入一个被代理类
    }

    @Override
    public void service(float price) {
        System.out.println("根据市场调研寻找到合适的工厂");	//前置增强
        realityFactory.service(price);    //工厂只负责制作衣服
        System.out.println("工厂制作完毕，我负责帮您邮寄到家");	//后置增强
    }
}
