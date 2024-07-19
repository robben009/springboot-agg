package com.robben.agg.liteFlow.controller;

import com.robben.agg.liteFlow.flow.contexts.StudentContext;
import com.robben.agg.liteFlow.flow.contexts.TeacherContext;
import com.robben.agg.liteFlow.flow.initParam.FlowInitParam;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "测试")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class FController {
    private final FlowExecutor flowExecutor;

    @Operation(summary = "测试流程")
    @PostMapping("testFlow")
    public String TestFlow(@RequestBody FlowInitParam flowInitParam) {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", flowInitParam, StudentContext.class, TeacherContext.class);
        return response.getExecuteStepStrWithTime();
    }


}
