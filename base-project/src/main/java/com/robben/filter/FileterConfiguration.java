package com.robben.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/10/12 14:38
 */
@Configuration
public class FileterConfiguration {

    @Bean
    public FilterRegistrationBean<LogCostFilter> RegistTest1(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean<LogCostFilter> bean = new FilterRegistrationBean<LogCostFilter>();
        bean.setFilter(new LogCostFilter());//注册自定义过滤器
        bean.setName("flilter1");//过滤器名称
        bean.addUrlPatterns("/anno/*");//过滤注解的请求
//        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);//优先级，最顶级
        return bean;
    }
    @Bean
    public FilterRegistrationBean<LogCostFilter2> RegistTest2(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean<LogCostFilter2> bean = new FilterRegistrationBean<LogCostFilter2>();
        bean.setFilter(new LogCostFilter2());//注册自定义过滤器
        bean.setName("flilter2");//过滤器名称
        bean.addUrlPatterns("/test/*");//过滤所有路径
        bean.setOrder(6);//优先级，越低越优先
        return bean;
    }

}
