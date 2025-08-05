package com.robben.agg.base.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Base64;

/**
 * 响应包装器过滤器
 * 缓存响应输出流，支持在响应提交前修改内容（如添加签名、动态拼接数据）。
 */

@Component
public class ResponseSignFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 包装响应，缓存输出流
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // 执行后续处理（控制器逻辑）
        filterChain.doFilter(request, wrappedResponse);

        // 响应后处理：添加签名
        byte[] responseBody = wrappedResponse.getContentAsByteArray();
        String signature = generateSignature(responseBody);
        wrappedResponse.setHeader("X-Response-Signature", signature);

        // 必须调用此方法将缓存内容写入原始响应
        wrappedResponse.copyBodyToResponse();
    }

    private String generateSignature(byte[] body) {
        // 自定义签名逻辑
        return Base64.getEncoder().encodeToString(body);
    }
}
