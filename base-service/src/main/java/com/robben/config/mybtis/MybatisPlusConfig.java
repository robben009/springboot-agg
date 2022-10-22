package com.robben.config.mybtis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author hjz
 * @version 1.0
 * @date 2022/10/22 20:45
 */

@Configuration
public class MybatisPlusConfig {

//    @Bean
//    public MybatisPlusLogInterceptor logInterceptor() {
//        MybatisPlusLogInterceptor interceptor = new MybatisPlusLogInterceptor();
//        Properties properties = new Properties();
//        // 设置是否显示数据库执行语句的执行时间
//        properties.setProperty(MybatisPlusLogInterceptor.PROPERTIES_KEY_ENABLE_EXECUTOR_TIME, MybatisPlusLogInterceptor.ENABLE_EXECUTOR_TIME);
//        interceptor.setProperties(properties);
//        return interceptor;
//    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
