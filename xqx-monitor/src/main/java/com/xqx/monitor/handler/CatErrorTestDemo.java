package com.xqx.monitor.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
@JobHandler(value = "catErrorTestDemo")
@Component
public class CatErrorTestDemo extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CatErrorTestDemo.class);

	@Override
	public ReturnT<String> execute(String param) {
		logger.info("exe");
		
		return ReturnT.SUCCESS;
	}


}
