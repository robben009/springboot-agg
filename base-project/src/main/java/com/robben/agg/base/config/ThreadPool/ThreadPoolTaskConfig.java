package com.robben.agg.base.config.ThreadPool;

import com.robben.agg.base.common.Contants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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


}