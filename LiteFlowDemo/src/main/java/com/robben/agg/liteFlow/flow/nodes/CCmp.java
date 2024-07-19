package com.robben.agg.liteFlow.flow.nodes;

import com.alibaba.fastjson2.JSON;
import com.robben.agg.liteFlow.flow.contexts.StudentContext;
import com.robben.agg.liteFlow.flow.contexts.TeacherContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowFact;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent("CCmp")
public class CCmp{

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeType = NodeTypeEnum.COMMON)
    public void process(NodeComponent bindCmp, @LiteflowFact("studentContext") StudentContext studentContext,
                        @LiteflowFact("teacherContext") TeacherContext teacherContext){
        log.info("最终的学生信息={}", JSON.toJSONString(studentContext));
        log.info("最终的老师信息={}", JSON.toJSONString(teacherContext));
    }

}

