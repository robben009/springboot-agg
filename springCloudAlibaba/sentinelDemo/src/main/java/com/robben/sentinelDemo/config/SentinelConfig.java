//package com.robben.sentinelDemo.config;
//
//import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
//import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
//import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.TypeReference;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//
//@Configuration
//public class SentinelConfig {
//    @Value("${spring.cloud.nacos.config.server-addr}")
//    private String nacosServerHost;
//
//    @Bean
//    public SentinelResourceAspect sentinelResourceAspect() {
//        return new SentinelResourceAspect();
//    }
//
//    @PostConstruct
//    private void initRules() {
//        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(nacosServerHost, "DEFAULT_GROUP", "sentinelDemo-flowRules",
//                source -> JSON.parseObject(source, new TypeReference<>() {
//                }));
//        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
//    }
//
//}
