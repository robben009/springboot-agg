package com.robben.agg.base.aspect.aop.DeclareParents;

import org.springframework.stereotype.Component;

@Component
public class Women implements Person {
    @Override
    public void likePerson() {
        System.out.println("我是女生");
    }
}
