package com.xqx.monitor.handler;

import static com.xqx.monitor.common.StaticParam.druidMaxTimespan;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.google.gson.Gson;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.bean.DruidSqlBean;
import com.xqx.monitor.bean.DruidSqlBean.Content;
import com.xqx.monitor.bean.MonitorConf;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
@JobHandler(value = "catErrorTestDemo")
@Component
public class CatErrorTestDemo extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CatErrorTestDemo.class);

	@Override
	public ReturnT<String> execute(String param) {
		
		
		return ReturnT.SUCCESS;
	}


}
