package com.robben.agg.base.annotation.validParam.auth;

import java.lang.annotation.*;


//权限认证的注解定义
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthToken {
    /**
     * 访问所需的身份，默认为空，为登录即可访问，可以多个定义
     *
     * @return
     * @data 2018年12月19日
     * @version v1.0.0.0
     */
    String[] roleName() default "";

}
