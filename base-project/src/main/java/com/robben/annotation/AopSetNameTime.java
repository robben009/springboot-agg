package com.robben.annotation;

import java.lang.annotation.*;


//权限认证的注解定义
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopSetNameTime {
    String name() default "";
}
