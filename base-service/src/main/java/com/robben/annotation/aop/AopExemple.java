package com.robben.annotation.aop;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

//此处为切面，
@Slf4j
@Component
@Aspect
public class AopExemple {

    //定义命名的切点 *表示返回任意参数 （..）表示入参为任意参数  涉及到所有的点为连接点
    @Pointcut("execution(* com.robben.controller.AopController.getUser1(..))")
    public void performance(){
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


    //定义通知
    @Before("performance()")   // 表演之前
    public void t1(){
        System.out.println("AopExemple_@Before");
    }

    @After("performance()")  // 表演之后
    public void t2(){
        System.out.println("AopExemple_@After");
    }

    @AfterReturning("performance()")  // 表演之后
    public void t3(){
        System.out.println("AopExemple_@AfterReturning");
    }

    @AfterThrowing("performance()")   // 表演失败之后
    public void t4(){
        System.out.println("AopExemple_@AfterThrowing");
    }

    @Around("performance()")  // 环绕通知方法
    public void t5(ProceedingJoinPoint pjp) throws Throwable {
        try {
            System.out.println("AopExemple_@Around_start");
            pjp.proceed();
            System.out.println("AopExemple_@Around_end");
        } catch (Throwable e){
            System.out.println("AopExemple_@Around_end_error");
        }

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest req = sra.getRequest();

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        String url = req.getRequestURL().toString();
        String method = req.getMethod();
        String queryString = req.getQueryString();

        Object[] args = pjp.getArgs();

        String params = "";
        //获取请求参数集合并进行遍历拼接
        if(args.length>0){
            if("POST".equals(method)){
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSON.toJSONString(map);
            }else if("GET".equals(method)){
                params = queryString;
            }
        }

        String sId = req.getSession().getId();
        log.info("{} #############################################",sId);
        log.info("{} 请求开始===地址:{}",sId,url);
        log.info("{} 请求开始===类型:{}",sId,method);
        log.info("{} 请求开始===参数:{}",sId,params);
        log.info("{} 请求结束===返回值:{}",sId,result);
    }


    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }


}
