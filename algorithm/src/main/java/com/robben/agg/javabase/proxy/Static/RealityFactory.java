package com.robben.agg.javabase.proxy.Static;

public class RealityFactory implements Serve {
    @Override
    public void service(float price) {
        System.out.println("根据您的要求，本工厂为您定制衣服，价格是：" + price);
    }
}
