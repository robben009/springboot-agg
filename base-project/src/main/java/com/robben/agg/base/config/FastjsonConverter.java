//package com.robben.agg.base.config;
//
///**
// * @author hjz
// * @version 1.0
// * @date 2022/6/8 11:06
// */
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.StringHttpMessageConverter;
//
//import java.nio.charset.StandardCharsets;
//
//
//@Configuration
//public class FastjsonConverter {
//
//    @Bean
//    public HttpMessageConverters customConverters() {
//        // 定义一个转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        // WriteMapNullValue把空的值的key也返回  需要其他的序列化规则按照格式设置即可
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
//        // 处理中文乱码问题
//        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
//        // 在转换器中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
//        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
//        // 将转换器添加到converters中
//        return new HttpMessageConverters(stringConverter, fastConverter);
//    }
//}
