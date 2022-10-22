package com.robben.annotation.validParam.self;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CheckParams {

    Class<?> beanClass();

    /**
     * 指定方法不需要返回结果，如果校验失败可以抛出 CheckParamsException 异常
     * @return
     */
    String methodName();
}

