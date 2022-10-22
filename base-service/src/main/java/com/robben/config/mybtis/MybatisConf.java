//package com.robben.config.mybtis;
//
//import com.baomidou.mybatisplus.core.injector.AbstractMethod;
//import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
//import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
///**
// * @author hjz
// * @version 1.0
// * @date 2022/10/22 20:45
// */
//
//@Configuration
//public class MybatisConf {
//
//    @Bean
//    public EasySqlInjector easySqlInjector() {
//        return new EasySqlInjector();
//    }
//
//    class EasySqlInjector extends DefaultSqlInjector {
//        @Override
//        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
//            List<AbstractMethod> methodList=super.getMethodList(mapperClass);
//            methodList.add(new InsertBatchSomeColumn());
//            return methodList;
//        }
//    }
//
//}
