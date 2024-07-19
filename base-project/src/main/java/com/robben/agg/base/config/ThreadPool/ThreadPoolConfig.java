package com.robben.agg.base.config.ThreadPool;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程池配置
 */
@Slf4j
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

    /**
     * cpu 核心数量
     */
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池配置
     * @return  scheduledTaskExecutor
     */
    @Bean("myTaskExecutor")
    @Override
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 配置核心线程池数量
        taskExecutor.setCorePoolSize(cpuNum);
        // 配置最大线程池数量
        taskExecutor.setMaxPoolSize(cpuNum*2);
        /// 线程池所使用的缓冲队列
        taskExecutor.setQueueCapacity(100);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        taskExecutor.setAwaitTerminationSeconds(60);
        // 空闲线程存活时间
        taskExecutor.setKeepAliveSeconds(60);
        // 等待任务在关机时完成--表明等待所有线程执行完
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池名称前缀
        taskExecutor.setThreadNamePrefix("myTaskExecutor-pool-");
        // 线程池拒绝策略
        taskExecutor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler("myTaskExecutor"));
        // 线程池初始化
        taskExecutor.initialize();
        log.info("myTaskExecutor线程池初始化......");
        return taskExecutor;
    }


    //自定义线程池拒绝策略
    class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        private String param;
        public CustomRejectedExecutionHandler(String param) {
            this.param = param;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor t) {
            log.warn("{}-线程池任务被拒绝,直接丢弃,当前线程池状态 activeCount={},queueSize={}",param
                    ,t == null ? "null" : t.getActiveCount()
                    ,t == null ? "null" : t.getQueue().size());
            //直接丢弃任务
        }
    }


    /**
     * 重写捕获异常类
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     */
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
            String paramsStr = Joiner.on(", ").join(params);
            log.info("线程池自定义异常,MethodName={},ExceptionMessage={},params={}",method.getName(),throwable.getMessage(),paramsStr);
            throwable.printStackTrace();
        }
    }

}