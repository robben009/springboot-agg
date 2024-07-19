package com.robben.agg.base.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class LogCostFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)arg0;
        System.out.println("自定义过滤器filter2触发,拦截url:"+request.getRequestURI());
        arg2.doFilter(arg0,arg1);
    }


}
