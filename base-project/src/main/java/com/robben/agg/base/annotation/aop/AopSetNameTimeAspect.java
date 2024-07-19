package com.robben.agg.base.annotation.aop;

import com.robben.annotation.aop.AopSetNameTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class AopSetNameTimeAspect {
    /**
     * Spring中使用@Pointcut注解来定义方法切入点
     * @Pointcut 用来定义切点，针对方法  @Aspect 用来定义切面，针对类
     * 后面的增强均是围绕此切入点来完成的
     * 此处仅配置被我们刚才定义的注解：AuthToken修饰的方法即可
     */
    @Pointcut("@annotation(aopSetNameTime)")
    public void doAop(AopSetNameTime aopSetNameTime) {
    }

//正常情况 @Around @Before 目标方法 @Around @AfterReturning @After;
//异常情况 @Around @Before 目标方法 @After @AfterThrowing;
//    try{
//        try{
//            //@Around
//            //@Before
//            method.invoke(..);
//            //@Around
//        }catch(){
//            throw.....;
//        }finally{
//            //@After
//        }
//        //@AfterReturning
//    }catch(){
//        //@AfterThrowing
//    }


    @Around("doAop(aopSetNameTime)")
    public Object t3(ProceedingJoinPoint pjp , AopSetNameTime aopSetNameTime) throws Throwable {
        log.info("1");
        Object result = pjp.proceed();
        log.info("2");
        return result;
    }


    @Before("doAop(aopSetNameTime)")
    public void t1(JoinPoint pjp , AopSetNameTime aopSetNameTime){
        //获取切面执行的方法
        String methodName = pjp.getSignature().getName();
        //获取切面的入参
        List<Object> args = Arrays.asList(pjp.getArgs());
        System.out.println("调用前连接点方法为：" + methodName + ",参数为：" + args);
    }

    @After("doAop(aopSetNameTime)")
    public void t2(JoinPoint pjp ,AopSetNameTime aopSetNameTime){
        System.out.println("AopExemple_@After");
    }

    @AfterReturning(returning="rvt", value="doAop(aopSetNameTime)")
    public String handleAop(JoinPoint pjp ,AopSetNameTime aopSetNameTime,Object rvt) {
        System.out.println("获取目标方法返回值:" + rvt.toString());
        return "注解处理类返回";
    }

}
