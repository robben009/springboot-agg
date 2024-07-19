package com.robben.agg.base.annotation.validParam.anno;

import com.robben.agg.base.enums.CommonErrorCodeEnum;
import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public  @interface MyNotNull {

    CommonErrorCodeEnum codeEnum();

}
