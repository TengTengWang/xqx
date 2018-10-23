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

/**
 * 统计druid指标： <br/>
 * 1. sql执行速度，若sql执行时间超过3000ms则写入CAT <br/>
 * 2. sql执行是否失败，若错误则写入CAT <br/>
 * 统计从当前时间起，至N分钟之前数据
 * 
 * CAT:监控配置
 * Event告警-->添加告警-->[项目:xqx-monitor]、[Type:zipkinUrl]、[监控项:执行次数]-->[持续分钟:N]-->[规则:最大值]、[阈值:错误、超时次数]
 * 
 */
@JobHandler(value = "collectDruidHandler")
@Component
public class CollectDruidHandler extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CollectDruidHandler.class);

	@Autowired
	private MonitorConf monitorConf;

	@Override
	public ReturnT<String> execute(String param) {

		List<String> druidAddresses = monitorConf.getDruidAddresses();
		for (String druidAddress : druidAddresses) {
			try {
				// druid数据地址
				// http://localhost:10051/druid/sql.json?orderBy=MaxTimespan&orderType=desc&page=1&perPageCount=1000000&
				String druidServerUrl = "http://" + druidAddress + "/druid/sql.json";
				druidServerUrl += "?orderBy=MaxTimespan&orderType=desc&page=1&perPageCount=1000000";

				HttpClientUtils client = HttpClientUtils.getInstance();
				String result = client.sendHttpGet(druidServerUrl);
				// 解析数据，记录CAT
				Gson gson = new Gson();
				DruidSqlBean bean = gson.fromJson(result, DruidSqlBean.class);
				bean.setAddress(druidAddress);
				catOpt(bean);
				// TODO 清空druid监控缓存
				// resetDruid(druidAddress);
			} catch (Exception e) {
				logger.error("访问druid服务" + druidAddress + "失败", e);
			}
		}
		return ReturnT.SUCCESS;
	}

	private void catOpt(DruidSqlBean bean) {
		if (bean.getResultCode() == 1) {
			List<Content> content = bean.getContent();
			logger.debug("druid拉取到{}个数据", content.size());
			for (Content c : content) {
				if (c.getMaxTimespan() > druidMaxTimespan) {
					// System.out.println("-----------------more than 100ms");
					// 最慢执行时间>100ms则记录
					Cat.logEvent("DruidMaxTimespan", bean.getAddress());
				}
				if (c.getErrorCount() > 0) {
					// System.out.println("-----------------error count");
					// sql执行错误次数>0
					Cat.logEvent("DruidSqlErrorCount", bean.getAddress());
				}
			}
		}
	}

	private void resetDruid(String druidAddress) {
		try {
			String url = "http://" + druidAddress + "/druid/reset-all.json";
			HttpClientUtils client = HttpClientUtils.getInstance();
			client.sendHttpGet(url);
		} catch (IOException e) {
		}
	}
}
