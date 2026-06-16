package com.hjz.flowlong.config;

import com.aizuda.bpm.engine.FlowLongEngine;
import com.aizuda.bpm.engine.FlowLongScheduler;
import com.aizuda.bpm.engine.TaskReminder;
import com.aizuda.bpm.engine.scheduling.JobLock;
import com.aizuda.bpm.spring.autoconfigure.FlowLongProperties;
import com.aizuda.bpm.spring.event.TaskEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class FLSchedulerConfig {

    private final FlowLongEngine flowLongEngine;
    private final FlowLongProperties properties;
    private final JobLock jobLock;
    private FlowLongScheduler flowLongScheduler;

    @Bean
    @ConditionalOnBean(TaskReminder.class)
    @ConditionalOnMissingBean
    public FlowLongScheduler createFlowLongScheduler() {
        FlowLongScheduler scheduler = new FlowLongScheduler() {};
        scheduler.setFlowLongEngine(flowLongEngine);
        scheduler.setRemindParam(properties.getRemind());
        scheduler.setJobLock(jobLock);
        this.flowLongScheduler = scheduler;
        return scheduler;
    }

    /**
     * 定时任务提醒
     */
    @Scheduled(cron = "${flowlong.remind.cron:*/5 * * * * ?}")
    public void scheduledRemind() {
        if (flowLongScheduler != null) {
            flowLongScheduler.remind();
        }
    }


}
