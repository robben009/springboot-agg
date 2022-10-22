package com.robben.annotation.validParam.self;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public  @interface NotNull {

    CommonErrorCodeEnum codeEnum();

}
