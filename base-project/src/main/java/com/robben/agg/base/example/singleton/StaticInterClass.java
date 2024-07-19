package com.robben.agg.base.example.singleton;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/4/16 13:39
 */
public class StaticInterClass {
    private StaticInterClass() {
    }

    private static class Sc{
        private static final StaticInterClass instance = new StaticInterClass();
    }

    public static final StaticInterClass getSc(){
        return Sc.instance;
    }


}
