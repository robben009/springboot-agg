package com.robben.agg.liteFlow.flow.nodes;

import com.alibaba.fastjson2.JSON;
import com.robben.agg.liteFlow.flow.contexts.StudentContext;
import com.robben.agg.liteFlow.flow.contexts.TeacherContext;
import com.robben.agg.liteFlow.flow.initParam.FlowInitParam;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowFact;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent("ACmp")
public class ACmp{

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeType = NodeTypeEnum.COMMON)
    public void process(NodeComponent bindCmp, @LiteflowFact("studentContext") StudentContext studentContext,
                        @LiteflowFact("teacherContext") TeacherContext teacherContext){
        FlowInitParam flowInitParam = bindCmp.getRequestData();
        log.info("获取流程初始化入参={}", JSON.toJSONString(flowInitParam));

        //依据流程入参,构建上下文
        studentContext.setAge(flowInitParam.getId());
        studentContext.setName("张三" + flowInitParam.getId());

        teacherContext.setCompany("清华大学" + flowInitParam.getId());
        teacherContext.setAddress("五道口" + flowInitParam.getId());
    }

}

