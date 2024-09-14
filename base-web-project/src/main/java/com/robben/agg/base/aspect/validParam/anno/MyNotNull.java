package com.robben.agg.base.aspect.validParam.anno;

import com.robben.agg.base.contants.CommonErrorCodeEnum;
import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public  @interface MyNotNull {

    CommonErrorCodeEnum codeEnum();

}
