package com.robben.agg.stateMachine.anno;

import com.robben.agg.stateMachine.constant.OrderStatusChangeEventEnum;
import com.robben.agg.stateMachine.constant.OrderStatusEnum;
import com.robben.agg.stateMachine.dao.domain.TbOrder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
public class SmHanleAspect {
    @Resource
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> orderStateMachine;


    //拦截 LogHistory注解
    @Pointcut("@annotation(com.robben.agg.stateMachine.anno.SmHandle)")
    private void logResultPointCut() {
    }


    @Around("logResultPointCut()")
    public Object logResultAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取参数
        Object[] args = pjp.getArgs();
        log.info("参数args:{}", args);
        Message message = (Message) args[0];
        TbOrder tbOrder = (TbOrder) message.getHeaders().get("order");

        //获取方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        // 获取注解的key
        SmHandle smHandle = method.getAnnotation(SmHandle.class);
        String key = smHandle.key();

        Object returnVal;
        try {
            //执行方法
            returnVal = pjp.proceed();
            //如果业务执行正常，则保存信息
            //成功 则为1
            orderStateMachine.getExtendedState().getVariables().put(key + tbOrder.getId(), 1);
        } catch (Throwable e) {
            log.error("e:{}", e.getMessage());
            //如果业务执行异常，则保存信息
            //将异常信息变量信息中，失败则为0
            orderStateMachine.getExtendedState().getVariables().put(key + tbOrder.getId(), 0);
            throw e;
        }
        return returnVal;
    }

}
