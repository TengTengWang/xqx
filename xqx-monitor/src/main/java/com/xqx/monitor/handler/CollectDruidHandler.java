package com.xqx.monitor.handler;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.google.gson.Gson;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.bean.DruidSqlBean;
import com.xqx.monitor.bean.DruidSqlBean.SqlContent;
import com.xqx.monitor.bean.DruidUriBean;
import com.xqx.monitor.bean.DruidUriBean.UriContent;
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
			DruidSqlBean sqlData = null;
			DruidUriBean uriData = null;
			try {
				// 获取sql监控数据，解析sql监控数据，并埋点
				sqlData = getSqlData(druidAddress);
				parseForSqlData(sqlData);

				// 获取uri监控数据，解析uri监控数据，并埋点
				uriData = getUriData(druidAddress);
				parseForUriData(uriData);

				// TODO 清空druid监控缓存
//				resetDruid(druidAddress);
				
				// 拉取druid api成功则记录
				Cat.logEvent("monitorPull", "pullDruidApiSuccess", Event.SUCCESS, druidAddress);
			} catch (Exception e) {
				logger.error("访问druid服务" + druidAddress + "失败", e);
			}
		}
		return ReturnT.SUCCESS;
	}

	private DruidSqlBean getSqlData(String druidAddress) throws IOException {
		// druid sql监控数据地址
		// http://localhost:10051/druid/sql.json?orderBy=MaxTimespan&orderType=desc&page=1&perPageCount=1000000&
		String druidServerUrl = "http://" + druidAddress + "/druid/sql.json";
		druidServerUrl += "?orderBy=MaxTimespan&orderType=desc&page=1&perPageCount=1000000";
		logger.debug("开始拉取 druid sql数据：{}", druidServerUrl);

		HttpClientUtils client = HttpClientUtils.getInstance();
		String result = client.sendHttpGet(druidServerUrl);
		// 解析数据，记录CAT
		Gson gson = new Gson();
		DruidSqlBean bean = gson.fromJson(result, DruidSqlBean.class);
		bean.setAddress(druidAddress);
		bean.setAddressFull(druidServerUrl);
		return bean;
	}

	private DruidUriBean getUriData(String druidAddress) throws IOException {
		// TODO druid url监控数据地址
		// http://localhost:10051/druid/weburi.json?orderBy=URI&orderType=desc&page=1&perPageCount=1000000
		String druidServerUrl = "http://" + druidAddress + "/druid/weburi.json";
		druidServerUrl += "?orderBy=URI&orderType=desc&page=1&perPageCount=1000000";
		logger.debug("开始拉取 druid uri数据：{}", druidServerUrl);

		HttpClientUtils client = HttpClientUtils.getInstance();
		String result = client.sendHttpGet(druidServerUrl);
		// 解析数据，记录CAT
		Gson gson = new Gson();
		DruidUriBean bean = gson.fromJson(result, DruidUriBean.class);
		bean.setAddress(druidAddress);
		bean.setAddressFull(druidServerUrl);
		return bean;
	}

	private void parseForSqlData(DruidSqlBean bean) {
		if (bean.getResultCode() == 1) {
			List<SqlContent> content = bean.getContent();
			if (content == null) {
				logger.debug("druid拉取到0个sql数据:{}", bean.getAddress());
				return;
			}
			logger.debug("druid拉取到{}个sql数据:{}", content.size(), bean.getAddress());
			for (SqlContent c : content) {
				if (c.getMaxTimespan() > monitorConf.getDruidSqlMaxTime()) {
					// 最慢执行时间>100ms则记录
					Cat.logEvent("DruidMaxTimespan", bean.getAddress(), Event.SUCCESS,
							"executeTime=" + c.getMaxTimespan() + "ms," + c.getSQL());
				}

				if (c.getErrorCount() > 0) {
					// sql执行错误次数>0
					Cat.logEvent("DruidSqlErrorCount", bean.getAddress(), Event.SUCCESS,
							"sqlErrorCount=" + c.getErrorCount());
				}
			}
		} else {

		}
	}

	private void parseForUriData(DruidUriBean bean) {
		if (bean.getResultCode() == 1) {
			List<UriContent> content = bean.getUriContent();
			if (content == null) {
				logger.debug("druid拉取到0个uri数据:{}", bean.getAddress());
				return;
			}
			logger.debug("druid拉取到{}个uri数据:{}", content.size(), bean.getAddress());
			for (UriContent c : content) {

				if (c.getRequestTimeMillisMax() > monitorConf.getZipkinLongTime()) {
					// 慢请求则埋点记录
					Cat.logEvent("DruidRequestTimeMax", bean.getAddress(), Event.SUCCESS,
							"requestTime=" + c.getRequestTimeMillisMax() + "ms," + c.getURI());
				}
			}
		} else {

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
