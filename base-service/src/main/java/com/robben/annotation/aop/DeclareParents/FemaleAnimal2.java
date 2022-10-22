package com.robben.annotation.aop.DeclareParents;

import org.springframework.stereotype.Component;

@Component
public class FemaleAnimal2 implements Animal {
    @Override
    public void eat() {
        System.out.println("我是个吃货2");
    }
}
