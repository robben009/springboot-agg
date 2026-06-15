package com.hjz.flowlong.config;

import com.aizuda.bpm.engine.entity.FlwTaskActor;
import com.aizuda.bpm.spring.event.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
public class FLTaskEventListnerConfig {
    /**
     * 异步任务事件监听处理
     * <p>
     * application.yml 开启  flowlong.eventing.task = true
     * </p>
     */
    @EventListener
    public void onTaskEvent(TaskEvent taskEvent) {
        log.info("=== 流程任务事件 ===");
        log.info("事件类型: {}", taskEvent.getEventType().name());
        log.info("任务信息: id={}, instanceId={}, taskName={}, taskKey={}, taskType={}",
                taskEvent.getFlwTask().getId(),
                taskEvent.getFlwTask().getInstanceId(),
                taskEvent.getFlwTask().getTaskName(),
                taskEvent.getFlwTask().getTaskKey(),
                taskEvent.getFlwTask().getTaskType());
        log.info("创建人: id={}, name={}, tenantId={}",
                taskEvent.getFlowCreator().getCreateId(),
                taskEvent.getFlowCreator().getCreateBy(),
                taskEvent.getFlowCreator().getTenantId());
        if (taskEvent.getTaskActors() != null && !taskEvent.getTaskActors().isEmpty()) {
            for (FlwTaskActor actor : taskEvent.getTaskActors()) {
                log.info("参与者: actorId={}, actorName={}, actorType={}, weight={}",
                        actor.getActorId(),
                        actor.getActorName(),
                        actor.getActorType(),
                        actor.getWeight());
            }
        }
        if (taskEvent.getNodeModel() != null) {
            log.info("节点模型: key={}, name={}, type={}",
                    taskEvent.getNodeModel().getNodeKey(),
                    taskEvent.getNodeModel().getNodeName(),
                    taskEvent.getNodeModel().getType());
        }
    }
}
