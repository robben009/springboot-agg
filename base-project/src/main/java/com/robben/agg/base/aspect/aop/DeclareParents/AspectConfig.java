package com.robben.agg.base.aspect.aop.DeclareParents;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectConfig {
    //"+"表示Men的所有子类(2.0以后版本+不能用了)；defaultImpl 表示默认需要添加的新的类
    @DeclareParents(value = "com.robben.agg.base.aspect.aop.DeclareParents.Women", defaultImpl = FemaleAnimal2.class)
    public Animal animal;

}
