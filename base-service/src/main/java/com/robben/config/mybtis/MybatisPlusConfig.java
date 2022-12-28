package com.robben.config.mybtis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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


//    @Bean
//    public EasySqlInjector easySqlInjector() {
//        return new EasySqlInjector();
//    }
//
//    class EasySqlInjector  extends DefaultSqlInjector {
//        @Override
//        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
//            List<AbstractMethod> methodList = super.getMethodList(mapperClass);
//            methodList.add(new InsertBatchSomeColumn()); // 添加InsertBatchSomeColumn方法
//            return methodList;
//        }
//    }

}
