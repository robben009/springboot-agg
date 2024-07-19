package com.robben.agg.base.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorRegistration implements WebMvcConfigurer {

    @Autowired
    private InterceptorDemo interceptorDemo;

    //这个方法是用来配置静态资源(Html,CSS,js)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

    }


    //这个方法用来注册拦截器，写好的拦截器需要通过这里注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //表示拦截所有的请求路径
        //addPathPatterns("/**")
        //表示除了登录与注册之外
        //excludePathPatterns("/login","/register")

        registry.addInterceptor(interceptorDemo).addPathPatterns("/test/getUser3");

    }


}
