package com.robben.agg.base.aspect.anno.handle;

import cn.hutool.core.date.SystemClock;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import com.alibaba.fastjson2.JSON;
import com.robben.agg.base.resp.BbResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 拦截注解的请求类或者方法
 * 1: 对异常做统一处理
 * 2: 增加请求traceId (需要做单独的处理)
 */
@Slf4j
@Aspect
@Component
public class OpenApiCatchAspect {

    @Pointcut("(@within(com.robben.agg.base.aspect.anno.OpenApiCatch) " +
            "|| @annotation(com.robben.agg.base.aspect.anno.OpenApiCatch)) && execution(public * *(..))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long startTime = SystemClock.now();

        Object response = null;
        try {
//            initTraceId();
            response = joinPoint.proceed();
        } catch (Throwable e) {
            response = handleException(e);
        } finally {
            response = addTraceId(response);
            logResponse(startTime, response, joinPoint);
        }

        return response;
    }


//    private void initTraceId() {
//        if (MDC.get(TraceIdFilter.TraceId) != null) {
//            RpcContext.getContext().setAttachment(TraceIdFilter.TraceId, MDC.get(TraceIdFilter.TraceId));
//            return;
//        }
//
//        //如果日志上下文和dubbo服务上下文都没有的话,那就自己产生一个放入两者上下文。
//        String traceId = "self" + SystemClock.now() + RandomUtil.randomString(5);
//        RpcContext.getContext().setAttachment(TraceIdFilter.TraceId, traceId);
//        MDC.put(TraceIdFilter.TraceId, traceId);
//    }

    //特定返回类型,返回增加traceId
    private Object addTraceId(Object response) {
        if (response instanceof BbResponse) {
            BbResponse r = (BbResponse) response;
//            r.setTraceId(MDC.get(TraceIdFilter.TraceId));
            return r;
        }

        return response;
    }

    private Object handleException(Throwable e) {
        String errCode;
        String errMessage;

        if (e instanceof BizException) {
            BizException b = (BizException) e;
            errCode = b.getErrCode();
            errMessage = b.getMessage();
        } else if (e instanceof SysException) {
            SysException s = (SysException) e;
            errCode = s.getErrCode();
            errMessage = s.getMessage();
        } else {
            errCode = "-1";
            errMessage = "系统异常";
        }

        log.error("开放接口拦截器异常 errCode={},errMessage={}", errCode, errMessage, e);
        return BbResponse.buildFailure(errCode, errMessage);
    }


    private void logResponse(long startTime, Object response, ProceedingJoinPoint joinPoint) {
        long costTime = SystemClock.now() - startTime;
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info("开放接口请求方法:{},请求参数:{},返回:{},用时:{}ms",
                ms.getDeclaringType().getSimpleName() + " " + ms.getMethod().getName(), JSON.toJSONString(args), JSON.toJSONString(response), costTime);

    }


}
