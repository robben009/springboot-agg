package com.hjz.flowlong.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.aizuda.bpm.engine.FlowLongEngine;
import com.aizuda.bpm.engine.TaskService;
import com.aizuda.bpm.engine.core.FlowCreator;
import com.aizuda.bpm.engine.entity.FlwHisTask;
import com.aizuda.bpm.engine.entity.FlwHisTaskActor;
import com.aizuda.bpm.engine.entity.FlwInstance;
import com.aizuda.bpm.engine.entity.FlwProcess;
import com.aizuda.bpm.engine.entity.FlwTask;
import com.aizuda.bpm.mybatisplus.mapper.FlwInstanceMapper;
import com.aizuda.bpm.mybatisplus.mapper.FlwProcessMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hjz.flowlong.model.ApprovalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "flow")
@RestController
@RequestMapping("/process")
@AllArgsConstructor
@Slf4j
public class TestController {
    private FlowLongEngine flowLongEngine;
    private FlwInstanceMapper flwInstanceMapper;
    private FlwProcessMapper flwProcessMapper;

    private static FlowCreator userCreator = FlowCreator.of("user001", "员工001");
    private static FlowCreator manageCreator = FlowCreator.of("manage001", "主管001");
    private static FlowCreator bossCreator = FlowCreator.of("boos001", "经理001");


    @Operation(summary = "启动流程实例")
    @GetMapping("/instance-start")
    public FlwInstance instanceStart() {
        Map<String, Object> args = new HashMap<>();
        args.put("day", 8);
        args.put("assignee", "test001");
        return flowLongEngine.startInstanceByProcessKey("process", null, userCreator, args).get();
    }


    @Operation(summary = "查看当前任务")
    @GetMapping("/getTask")
    public FlwTask getTask(@RequestParam Long taskId) {
        FlwTask task = flowLongEngine.queryService().getTask(taskId);
        return task;
    }

    /**
     * 审批接口（同意/驳回）
     * 入参：taskId（任务ID）、userId（用户ID）、userName（用户名）、action（approve/reject）、remark（备注）
     */
    @Operation(summary = "审批接口（同意/驳回）")
    @PostMapping("/approval")
    public Map<String, Object> approval(@RequestBody ApprovalRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            FlowCreator flowCreator = FlowCreator.of(request.getUserId(), request.getUserName());
            FlwTask flwTask = flowLongEngine.queryService().getTask(request.getTaskId());
            if (flwTask == null) {
                result.put("success", false);
                result.put("message", "任务不存在，taskId=" + request.getTaskId());
                return result;
            }

            if ("approve".equalsIgnoreCase(request.getAction())) {
                Map<String, Object> args = new HashMap<>();
                if (request.getRemark() != null) {
                    args.put("remark", request.getRemark());
                }
                boolean success = flowLongEngine.executeTask(request.getTaskId(), flowCreator, args);
                log.info("用户 {}({}) 同意任务 {}，结果: {}", request.getUserName(), request.getUserId(), flwTask.getTaskName(), success);
                result.put("success", success);
                result.put("message", success ? "审批通过" : "审批失败");
            } else if ("reject".equalsIgnoreCase(request.getAction())) {
                TaskService taskService = flowLongEngine.taskService();
                Map<String, Object> args = new HashMap<>();
                if (request.getRemark() != null) {
                    args.put("remark", request.getRemark());
                }

                Optional<List<FlwTask>> rejectedTasks = taskService.rejectTask(flwTask, flowCreator, args);
                boolean success = rejectedTasks.isPresent();
                log.info("用户 {}({}) 驳回任务 {}，结果: {}", request.getUserName(), request.getUserId(), flwTask.getTaskName(), success);
                result.put("success", success);
                result.put("message", success ? "驳回成功" : "驳回失败");
            } else {
                result.put("success", false);
                result.put("message", "不支持的操作: " + request.getAction());
            }
        } catch (Exception e) {
            log.error("审批异常", e);
            result.put("success", false);
            result.put("message", "审批异常: " + e.getMessage());
        }
        return result;
    }

    /**
     * 查询流程实例当前活跃任务
     */
    @GetMapping("/active-tasks")
    public Map<String, Object> getActiveTasks(@RequestParam Long instanceId) {
        Map<String, Object> result = new HashMap<>();
        List<FlwTask> tasks = flowLongEngine.queryService().getTasksByInstanceId(instanceId);
        result.put("success", true);
        result.put("instanceId", instanceId);
        result.put("tasks", tasks);
        return result;
    }

    /**
     * 查询流程实例历史任务
     */
    @Operation(summary = "查询流程实例历史任务")
    @GetMapping("/history-tasks")
    public Map<String, Object> getHistoryTasks(@RequestParam Long instanceId) {
        Map<String, Object> result = new HashMap<>();
        Optional<List<FlwHisTask>> hisTasksOpt = flowLongEngine.queryService().getHisTasksByInstanceId(instanceId);
        List<FlwHisTask> hisTasks = hisTasksOpt.orElse(Collections.emptyList());
        List<Map<String, Object>> taskDetails = new ArrayList<>();
        for (FlwHisTask hisTask : hisTasks) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("taskId", hisTask.getId());
            detail.put("taskName", hisTask.getTaskName());
            detail.put("taskKey", hisTask.getTaskKey());
            detail.put("taskType", hisTask.getTaskType());
            detail.put("taskState", hisTask.getTaskState());
            detail.put("createBy", hisTask.getCreateBy());
            detail.put("createId", hisTask.getCreateId());
            detail.put("createTime", hisTask.getCreateTime());
            detail.put("finishTime", hisTask.getFinishTime());
            detail.put("duration", hisTask.getDuration());
            detail.put("assignor", hisTask.getAssignor());
            detail.put("remark", hisTask.getVariable());
            List<FlwHisTaskActor> actors = flowLongEngine.queryService().getHisTaskActorsByTaskId(hisTask.getId());
            List<Map<String, Object>> actorList = new ArrayList<>();
            for (FlwHisTaskActor actor : actors) {
                Map<String, Object> actorInfo = new HashMap<>();
                actorInfo.put("actorId", actor.getActorId());
                actorInfo.put("actorName", actor.getActorName());
                actorInfo.put("actorType", actor.getActorType());
                actorList.add(actorInfo);
            }
            detail.put("actors", actorList);
            taskDetails.add(detail);
        }
        result.put("success", true);
        result.put("instanceId", instanceId);
        result.put("tasks", taskDetails);
        return result;
    }

    /**
     * 查询正在运行的流程实例列表
     */
    @Operation(summary = "查询正在运行的流程实例")
    @GetMapping("/running-instances")
    public Map<String, Object> getRunningInstances() {
        StpUtil.login(10001);

        Map<String, Object> result = new HashMap<>();
        List<FlwInstance> instances = flwInstanceMapper.selectList(Wrappers.<FlwInstance>lambdaQuery());
        result.put("success", true);
        result.put("count", instances.size());
        result.put("instances", instances);
        return result;
    }

    /**
     * 查询已部署的流程列表
     */
    @Operation(summary = "查询已部署的流程")
    @GetMapping("/deployed-processes")
    public Map<String, Object> getDeployedProcesses() {
        Map<String, Object> result = new HashMap<>();
        List<FlwProcess> processes = flwProcessMapper.selectList(
                Wrappers.<FlwProcess>lambdaQuery().orderByDesc(FlwProcess::getCreateTime)
        );
        List<Map<String, Object>> processList = new ArrayList<>();
        for (FlwProcess process : processes) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("processId", process.getId());
            detail.put("processKey", process.getProcessKey());
            detail.put("processName", process.getProcessName());
            detail.put("processVersion", process.getProcessVersion());
            detail.put("processType", process.getProcessType());
            detail.put("createBy", process.getCreateBy());
            detail.put("createTime", process.getCreateTime());
            processList.add(detail);
        }
        result.put("success", true);
        result.put("count", processList.size());
        result.put("processes", processList);
        return result;
    }




}
