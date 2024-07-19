package com.robben.agg.xxljob.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoJobHandler {

    @XxlJob(value = "testJob")
    public ReturnT<String> lalala(String param) throws Exception {
        log.info("startAllFlinkTask begin");

        log.info("startAllFlinkTask end");
        return ReturnT.SUCCESS;
    }

}
