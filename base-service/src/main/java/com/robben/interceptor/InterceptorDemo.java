package com.robben.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorDemo implements HandlerInterceptor {

    //在接口访问前访问
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,Object handler)
            throws Exception{

        resp.sendError(0,"没权限");
        return false;
    }

    //当preHandle返回true且在方法执行完成之后执行，DispatcherServlet进行试图的渲染之前
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler,
                      @Nullable ModelAndView mav) throws Exception{

    }

    //当preHandle返回true且在方法执行完成之后执行，DispatcherServlet进行试图的渲染之前
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler,
                                @Nullable Exception ex) throws Exception{

    }

}
