package com.robben.annotation.validParam.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
@Aspect
@Component
public class AuthTokenAspect {
    /**
     * Spring中使用@Pointcut注解来定义方法切入点
     * @Pointcut 用来定义切点，针对方法  @Aspect 用来定义切面，针对类
     * 后面的增强均是围绕此切入点来完成的
     * 此处仅配置被我们刚才定义的注解：AuthToken修饰的方法即可
     */
    @Pointcut("@annotation(authToken)")
    public void doAuthToken(AuthToken authToken) {
    }



//
//    /**
//     * 此处我使用环绕增强，在方法执行之前或者执行之后均会执行。
//     */
//    @Around("doAuthToken(authToken)")
//    public Object deBefore(ProceedingJoinPoint pjp, AuthToken authToken) throws Throwable {
//        log.info("---------方法执行之前-------------");
//        // 执行原方法，并记录返回值。
//        Object proceed = pjp.proceed();
//        log.info("---------方法执行之后-------------");
//        return proceed;
//    }


    /**
     * 此处我使用环绕增强，在方法执行之前或者执行之后均会执行。
     */
    @Around("doAuthToken(authToken)")
    public Object deBefore(ProceedingJoinPoint pjp, AuthToken authToken) throws Throwable {
        //获取当前请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return pjp.proceed();
        }

        HttpServletRequest request = attributes.getRequest();

        // 获取访问该方法所需的role_name信息
        String[] roleList = authToken.roleName();
        // 需要验证身份
        String role = request.getHeader("roleName");
        for (String str : roleList) {
            /**
             * 此处str由于是用role_name中取值，则str必定不为空
             * 而从请求头中获取的role有可能为空，则此处调用str的equals方法
             * 当然可以直接在获得请求头后进行验证是是否不为空。
             */
            if (str.equals(role)) {
                // 身份匹配成功,执行原方法并返回即可。
                return pjp.proceed();
            }
        }
        log.info("权限检验失败：{}",roleList);
        return "权限校验失败，不具有指定的身份";
    }

}
