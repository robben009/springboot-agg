package com.robben.agg.base.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 响应包装器过滤器
 * 缓存响应输出流，支持在响应提交前修改内容（如添加签名、动态拼接数据）。
 */

@Component
public class ResponseSignFilter extends OncePerRequestFilter {
    // 实际项目中，密钥会放在配置文件中，前后端保持一致
    private static final String SECRET_KEY = "your-16bit-secret-key-1234";


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
        try {
            // 1. 拼接响应体和密钥（核心：密钥防止黑客伪造签名）
            String content = new String(body, StandardCharsets.UTF_8) + SECRET_KEY;
            // 2. 用 SHA256 哈希算法生成签名（不可逆，更安全）
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            // 3. 把哈希结果转成 16 进制字符串（方便传输和对比）
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("签名生成失败", e);
        }
    }
}
