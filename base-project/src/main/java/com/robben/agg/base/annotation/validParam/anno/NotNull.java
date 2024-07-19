package com.robben.annotation.validParam.anno;


import com.robben.enums.CommonErrorCodeEnum;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public  @interface NotNull {

    CommonErrorCodeEnum codeEnum();

}
