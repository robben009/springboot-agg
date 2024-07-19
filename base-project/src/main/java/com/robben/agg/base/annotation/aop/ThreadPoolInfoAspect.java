package com.robben.annotation.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class ThreadPoolInfoAspect {
//    @Qualifier("authTaskExecutor")
//    @Autowired
//    private ThreadPoolTaskExecutor e;
//    /**
//     * Spring中使用@Pointcut注解来定义方法切入点
//     * @Pointcut 用来定义切点，针对方法  @Aspect 用来定义切面，针对类
//     * 后面的增强均是围绕此切入点来完成的
//     * 此处仅配置被我们刚才定义的注解：AuthToken修饰的方法即可
//     */
//    @Pointcut("@annotation(async)")
//    public void doAsync(Async async) {
//    }
//
//
//    /**
//     * 此处我使用环绕增强，在方法执行之前或者执行之后均会执行。
//     */
//    @Before("doAsync(async)")
//    public void deBefore(JoinPoint j, Async async) {
//        log.info("~~~~~~~~~~~~~~~~");
//        log.info("拦截目标:{}",j.getTarget().getClass().getName());
//        log.info("当前ActiveCount:{}",e.getThreadPoolExecutor().getActiveCount());
//        log.info("当前PoolSize:{}",e.getThreadPoolExecutor().getPoolSize());
//        log.info("当前queueSize:{}",e.getThreadPoolExecutor().getQueue().size());
//        log.info("~~~~~~~~~~~~~~~~");
//    }


}
