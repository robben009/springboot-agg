package com.robben.agg.base.javaBase.singleton;

import java.lang.reflect.Constructor;

/**
 * Description： 饿汉单例
 * Author: robben
 * Date: 2021/4/16 11:52
 */
public class Hungry {
    private Hungry() {
    }

    private static Hungry hungry = new Hungry();

    public static Hungry getLazy(){
        return hungry;
    }

    //通过反射可以破坏单例
    public static void main(String[] args) throws Exception {
        Hungry a = Hungry.getLazy();

        Constructor<Hungry> d = Hungry.class.getDeclaredConstructor();
        d.setAccessible(true);
        Hungry b = d.newInstance();

        System.out.println(a == b);
    }
}
