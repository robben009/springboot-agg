package com.robben.agg.base.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * 请求包装器（ContentCachingRequestWrapper）
 * 缓存请求体字节数据，允许多次读取。典型场景：记录请求日志后，控制器仍能正常解析请求体。
 */
@Slf4j
@Component
public class RequestLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 包装请求，缓存输入流
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 1024 * 1024);
        byte[] requestBody = wrappedRequest.getContentAsByteArray();

        // 记录请求日志（可在此处添加自定义逻辑）
        log.debug("Received request body: {}", new String(requestBody));

        // 传递包装后的请求，确保后续组件能重复读取
        filterChain.doFilter(wrappedRequest, response);
    }
}
