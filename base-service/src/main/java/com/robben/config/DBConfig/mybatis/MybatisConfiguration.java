package com.robben.config.DBConfig.mybatis;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/4/30 14:07
 */

import com.robben.interceptor.mybtis.MybatisPlusLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis配置
 */
@Configuration
public class MybatisConfiguration {


    @Bean
    public MybatisPlusLogInterceptor logInterceptor() {
        MybatisPlusLogInterceptor interceptor = new MybatisPlusLogInterceptor();
        Properties properties = new Properties();
        // 设置是否显示数据库执行语句的执行时间
        properties.setProperty(MybatisPlusLogInterceptor.PROPERTIES_KEY_ENABLE_EXECUTOR_TIME, MybatisPlusLogInterceptor.ENABLE_EXECUTOR_TIME);
        interceptor.setProperties(properties);
        return interceptor;
    }

}