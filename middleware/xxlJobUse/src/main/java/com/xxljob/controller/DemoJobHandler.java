package com.xxljob.controller;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;


@Component
public class DemoJobHandler {

	@XxlJob(value = "lalala")
	public ReturnT<String> lalala(String param) throws Exception {
		XxlJobHelper.log("startAllFlinkTask begin");

		XxlJobHelper.log("startAllFlinkTask end");
		return ReturnT.SUCCESS;
	}

}
