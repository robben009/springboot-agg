package com.robben.sentinelDemo.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * 用于区分controller提供的http接口的来源
 */
@Component
public class WebApiLimitFilter implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getHeader("source");
        return origin != null ? origin : "default";
    }
}

