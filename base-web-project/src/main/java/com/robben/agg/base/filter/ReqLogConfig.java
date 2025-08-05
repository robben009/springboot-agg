package com.robben.agg.base.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * 用于记录请求日志，springboot自带的拦截器
 * 使用时需要注意修改日志级别 logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
 */
@Configuration
public class ReqLogConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true); // 包含查询参数
        filter.setIncludePayload(true);     // 包含请求体
        filter.setMaxPayloadLength(1024);   // 限制请求体日志长度（避免大字段溢出）
        filter.setAfterMessagePrefix("[REQUEST DATA] ");  //日志的前缀
        return filter;
    }

}
