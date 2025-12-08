package com.robben.agg.base.javaBase.singleton;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/4/16 11:54
 */
public class Lazy2 {
    private Lazy2() {
    }

    private volatile static Lazy2 lazy;

    public static Lazy2 getLazy() {
        if(lazy == null){
            synchronized (Lazy2.class){
                if(lazy == null){
                    lazy = new Lazy2();
                }
            }
        }
        return lazy;
    }
}


/**
 * 从CPU的角度来看，instance = new Instance()可以分为分为几个步骤：
 * 分配对象内存空间
 * 执行构造方法，对象初始化
 * instance指向分配的内存地址
 */
