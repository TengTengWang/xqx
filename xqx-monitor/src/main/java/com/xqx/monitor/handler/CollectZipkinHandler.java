package com.xqx.monitor.handler;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.bean.MonitorConf;
import com.xqx.monitor.bean.ZipkinSpanBean;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 统计zipkin指标： <br/>
 * 1. 请求用时，若执行时间超过3000ms则写入CAT <br/>
 * 2. 请求是否失败，若报错则写入CAT <br/>
 * 统计从当前时间起，至N分钟之前数据
 * 
 * CAT:监控配置
 * Event告警-->添加告警-->[项目:xqx-monitor]、[Type:zipkinUrl]、[监控项:执行次数]-->[持续分钟:N]-->[规则:最大值]、[阈值:错误、超时次数]
 * 
 * 对如下几点进行埋点： 1.访问出错 2.访问超时
 */
@JobHandler(value = "collectZipkinHandler")
@Component
public class CollectZipkinHandler extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CollectZipkinHandler.class);

	@Autowired
	private MonitorConf monitorConf;
	@Autowired
	private StringRedisTemplate template;

	@Override
	public ReturnT<String> execute(String param) {
		// 计算上次拉取时间到现在的间隔
		Long now = System.currentTimeMillis();
		Long preTime = null;
		String preTimeStr = null;
		if (template != null) {
			preTimeStr = template.opsForValue().get("collectZipkinTime");
			template.opsForValue().set("collectZipkinTime", now + "");
		}
		if (StringUtils.isBlank(preTimeStr)) {
			preTime = monitorConf.getZipkinStartSpanTime();
		} else {
			preTime = Long.parseLong(preTimeStr);
		}
		Long interval = now - preTime;
		try {
			// 获取数据
			List<List<ZipkinSpanBean>> zipkinSpan = getZipkin(now, 10000, interval);

			// 解析数据，并埋点
			parseData(zipkinSpan, interval);
		} catch (Exception e) {
			logger.error("拉起zipkin失败", e);
			// 记录拉取失败则记录，所有地址都请求了zipkin数据，但是无结果
			Cat.logEvent("zipkinPull", "pullZipkinApiFail");
		}
		return ReturnT.SUCCESS;
	}

	/**
	 * @param endTs    结束时间 ms
	 * @param limit    返回最大条数
	 * @param lookback 从结束时间起的追溯时间 ms
	 * @return
	 * @throws IOException 请求失败
	 */
	private List<List<ZipkinSpanBean>> getZipkin(Long endTs, int limit, Long lookback) throws IOException {

		List<String> zipkinAddresses = monitorConf.getZipkinAddresses();
		for (String zipkinAddress : zipkinAddresses) {
			try {
				// http://9.186.52.168:9411/zipkin/api/v2/traces?endTs=1539755220000&limit=1000&lookback=521991552
				String zipkinServerUrl = "http://" + zipkinAddress + "/zipkin/api/v2/traces";
				zipkinServerUrl += "?endTs=" + endTs + "&limit=" + limit + "&lookback=" + lookback;

				HttpClientUtils client = HttpClientUtils.getInstance();
				String result = client.sendHttpGet(zipkinServerUrl);

				Gson gson = new Gson();
				List<List<ZipkinSpanBean>> zipkinSpan = gson.fromJson(result,
						new TypeToken<List<List<ZipkinSpanBean>>>() {
						}.getType());
				return zipkinSpan;
			} catch (Exception e) {
				logger.error("访问zipkin服务" + zipkinAddress + "失败", e);
			}
		}
		throw new IOException("访问所有zipkin服务失败");
	}

	private void parseData(List<List<ZipkinSpanBean>> zipkinSpan, long interval) {

		// 拉取zipkin api成功则记录
		Cat.logEvent("monitorPull", "pullZipkinApiSuccess");
		if (zipkinSpan == null) {
			return;
		}
		logger.debug("zipkin拉取到{}秒之前{}个数据", interval / 1000, zipkinSpan.size());

		for (List<ZipkinSpanBean> spans : zipkinSpan) {
			for (ZipkinSpanBean span : spans) {
				String parentId = span.getParentId();
				// 判断是否为客户端请求地址
				if (StringUtils.isBlank(parentId)) {
					// TODO 写入CAT逻辑
					/*
					 * 1、若报错则写入 2、若执行时间超过3000ms则写入
					 */
					// 执行错误
					if (span.getTags() != null && span.getTags().containsKey("error")) {
						Cat.logEvent("zipkinUrlRequest", span.getLocalEndpoint().serviceName() + "_error", "500", null);
						logger.debug(span.getLocalEndpoint().serviceName() + ", " + span.getTags().get("error"));
					} else {
						// 若执行时间超过3000ms则写入
						if (span.getDuration() > monitorConf.getZipkinLongTime()) {
							Cat.logEvent("zipkinUrlRequest", span.getLocalEndpoint().serviceName() + "_timeout");
							logger.debug("{}执行时间{}", span.getLocalEndpoint().serviceName(), span.getDuration());
						}
					}
				}
			}
		}
	}
}
