package com.xqx.monitor.handler;

import static com.xqx.monitor.common.StaticParam.collectZipkinIntervalTime;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.xqx.monitor.service.ZipkinHandlerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import zipkin2.Span;

/**
 * 统计zipkin指标： <br/>
 * 1. 请求用时，若执行时间超过3000ms则写入CAT <br/>
 * 2. 请求是否失败，若报错则写入CAT <br/>
 * 统计从当前时间起，至N分钟之前数据
 * 
 * CAT:监控配置
 * Event告警-->添加告警-->[项目:xqx-monitor]、[Type:zipkinUrl]、[监控项:执行次数]-->[持续分钟:N]-->[规则:最大值]、[阈值:错误、超时次数]
 * 
 * 对如下几点进行埋点：
 * 1.访问出错
 * 2.访问超时
 */
@JobHandler(value = "collectZipkinHandler")
@Component
public class CollectZipkinHandler extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CollectZipkinHandler.class);

	@Autowired
	private ZipkinHandlerService zipkinHandlerService;

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
			preTime = collectZipkinIntervalTime;
		} else {
			preTime = Long.parseLong(preTimeStr);
		}
		Long interval = now - preTime;
		try {
			List<List<Span>> zipkinSpan = zipkinHandlerService.getZipkin(now, 10000, interval);
			if (zipkinSpan == null) {
				return SUCCESS;
			}
			logger.debug("zipkin拉取到{}秒之前{}个数据", interval / 1000, zipkinSpan.size());

			for (List<Span> spans : zipkinSpan) {
				for (Span span : spans) {
					String parentId = span.parentId();
					// 判断是否为客户端请求地址
					if (StringUtils.isBlank(parentId)) {
						// TODO 写入CAT逻辑
						/*
						 * 1、若报错则写入 2、若执行时间超过3000ms则写入
						 */
						// 执行错误
						if (span.tags() != null && span.tags().containsKey("error")) {
							// TODO del
//							System.out.println("----------------zipkin 数据：error");
							Cat.logEvent("zipkinUrlRequest", span.localEndpoint().serviceName() + "_error", "500",
									null);
						} else {
							// 若执行时间超过3000ms则写入
							if (span.duration() > 30) {
								// TODO del
//								System.out.println("----------------zipkin 数据：timeout");
								Cat.logEvent("zipkinUrlRequest", span.localEndpoint().serviceName() + "_timeout");
							}
						}
					}
				}
			}
			// 拉取zipkin api成功则记录
			Cat.logEvent("zipkinPull", "pullZipkinApiSuccess");
		} catch (Exception e) {
			logger.error("拉起zipkin失败", e);
			// 记录拉取失败则记录，所有地址都请求了zipkin数据，但是无结果
			Cat.logEvent("zipkinPull", "pullZipkinApiFail");
		}
		return ReturnT.SUCCESS;
	}

}
