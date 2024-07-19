package com.robben.annotation.aop.DeclareParents;

import org.springframework.stereotype.Component;

@Component
public class FemaleAnimal implements Animal {
    @Override
    public void eat() {
        System.out.println("我是个吃货");
    }
}
