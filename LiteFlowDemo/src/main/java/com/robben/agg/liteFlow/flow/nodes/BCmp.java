package com.robben.agg.liteFlow.flow.nodes;

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
@LiteflowComponent("BCmp")
public class BCmp{

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeType = NodeTypeEnum.COMMON)
    public void process(NodeComponent bindCmp, @LiteflowFact("studentContext") StudentContext studentContext,
                        @LiteflowFact("teacherContext") TeacherContext teacherContext){

        Integer age = studentContext.getAge();
        studentContext.setAge(age + 1);
        studentContext.setName(studentContext.getName() + "BCmp");

        teacherContext.setCompany(teacherContext.getCompany() + "BCmp");
        teacherContext.setAddress(teacherContext.getAddress() + "BCmp");
    }
}

