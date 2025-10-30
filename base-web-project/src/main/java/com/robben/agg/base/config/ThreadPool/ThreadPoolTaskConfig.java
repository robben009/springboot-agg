package com.robben.agg.base.config.ThreadPool;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.robben.agg.base.contants.Contants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {
    //核心线程数(歌认线程数)
    private static final int corePoolSize = 5;
    //最大线程数
    private static final int maxPoolSize = 10;
    //允许线程空闲时间(默认为秒)
    private static final int keepAliveTime = 10;
    //缓冲队列数量
    private static final int queueCapacity = 200;
    //线程池名称的前缀
    private static final String threadNamePrefix = "AsyncService-";

    //配和ttlAsyncExecutor 来一起使用，会把主线程的变量复制到子线程中，当子线程完成任务后会恢复主线程的值(期间如果子线程改了这个变量的值，也不会影响主线程一开始赋的值)
    public static final TransmittableThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();


    //bean的名称,制认为首字母小写的方法名
    @Bean(Contants.MY_EXECUTOR)
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutorPlus executor = new ThreadPoolTaskExecutorPlus();   //新增子类主要是增加了日志
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        //线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        executor.initialize();
        return executor;
    }



    @Bean("ttlAsyncExecutor")
    public Executor asyncExecutor() {
        // 创建一个普通线程池，比如固定线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 使用 TransmittableThreadLocal 包装线程池，保证TTL能传递
        ExecutorService ttlExecutor = TtlExecutors.getTtlExecutorService(executorService);

        return ttlExecutor;
    }

}