package com.robben.agg.base.example.proxy.Static;

public class Test{
    public static void main(String[] args) {
        Serve realityFactory = new RealityFactory();	//定义被代理类工厂
        Serve purchasing = new Purchasing(realityFactory);	//定义代理类我自己，并将被代理类注入
        purchasing.service(20.0f);	//执行代理类的service方法,增强了被代理类作用
    }
}
