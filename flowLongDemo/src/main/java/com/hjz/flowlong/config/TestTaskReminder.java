package com.hjz.flowlong.config;



import com.aizuda.bpm.engine.TaskReminder;
import com.aizuda.bpm.engine.assist.DateUtils;
import com.aizuda.bpm.engine.core.FlowLongContext;
import com.aizuda.bpm.engine.entity.FlwTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 注入自定义任务提醒处理类
 */
@Slf4j
@Component
public class TestTaskReminder implements TaskReminder {

    @Override
    public Date remind(FlowLongContext context, Long instanceId, FlwTask currentTask) {
        log.info("测试提醒，instanceId = {}", instanceId);

        // 一天后继续提醒，直到用户处理完
        return DateUtils.toDate(DateUtils.now().plusDays(1));
    }
}
