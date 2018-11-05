package com.xqx.monitor.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.xqx.base.gson.GsonUtil;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.conf.MonitorConf;
import com.xqx.monitor.pojo.ActuatorBean;
import com.xqx.monitor.pojo.ActuatorBean.MyMetrics;
import com.xqx.monitor.pojo.ActuatorDO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 统计机器性能指标
 */
@JobHandler(value = "collectActuatorHandler")
@Component
public class CollectActuatorHandler extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CollectActuatorHandler.class);

	private static String[] methods = { "/health", "/metrics/system.cpu.count", "/metrics/system.cpu.usage",
			"/metrics/jvm.memory.max", "/metrics/jvm.memory.used", "/metrics/jvm.threads.live",
			"/metrics/process.cpu.usage" };

	@Autowired
	private MonitorConf monitorConf;

	@Override
	public ReturnT<String> execute(String param) {
		Transaction t = Cat.newTransaction("monitorActuator", "actuator");
		try {
			List<String> actuatorAddresses = monitorConf.getActuatorAddresses();
			for (String actuatorAddress : actuatorAddresses) {
				try {
					// http://localhost:10051/actuator/metrics/system.cpu.usage
					ActuatorDO actuator = new ActuatorDO();
					for (int i = 0; i < methods.length; i++) {
						String url = "http://" + actuatorAddress + "/actuator" + methods[i];
						sendHttp(url, null, i, actuator);
					}
					actuator.setAddress(actuatorAddress);
					parseActuator4cat(actuator);
					// TODO save to es
				} catch (Exception e) {
					logger.error("访问druid服务" + actuatorAddress + "失败", e);
				}
			}
			t.setStatus(Transaction.SUCCESS);
		} finally {
			t.complete();
		}
		return ReturnT.SUCCESS;
	}

	private void parseActuator4cat(ActuatorDO actuator) {
		if (actuator.getSystemCpuUsage() > monitorConf.getActuatorSystemCpuUsage() || monitorConf.getActuatorSystemCpuUsage() == 0) {
			Cat.logMetricForDuration(actuator.getAddress() + " actuatorSystemCpuUsage", Math.round(actuator.getSystemCpuUsage() * 100));
			Cat.logEvent("actuatorSystemCpuUsage", actuator.getAddress(), Event.SUCCESS, actuator.getSystemCpuUsage() + "");
		}
		if (actuator.getDiskSpaceFree() < monitorConf.getActuatorDiskSpaceFree() || monitorConf.getActuatorDiskSpaceFree() == 0) {
			Cat.logMetricForDuration(actuator.getAddress() + " actuatorDiskSpaceFree", actuator.getDiskSpaceFree());
			Cat.logEvent("actuatorDiskSpaceFree", actuator.getAddress(), Event.SUCCESS, actuator.getDiskSpaceFree() + "");
		}
		if (actuator.getJvmMemoryFree() < monitorConf.getActuatorJvmMemoryFree() || monitorConf.getActuatorJvmMemoryFree() == 0) {
			Cat.logMetricForDuration(actuator.getAddress() + " actuatorJvmMemoryFree", actuator.getJvmMemoryFree());
			Cat.logEvent("actuatorJvmMemoryFree", actuator.getAddress(), Event.SUCCESS, actuator.getJvmMemoryFree() + "");
		}
	}

	private void sendHttp(String url, Map<String, String> header, int index, ActuatorDO actuator) throws IOException {
		// 有可能数值会返回NaN的情况，所以重试
		HttpClientUtils client = HttpClientUtils.getInstance();
		for (int i = 0; i < 3; i++) {
			try {
				String resp = client.sendHttpGet(url);
				if (index == 0) {
					ActuatorBean health = GsonUtil.fromJson(resp, ActuatorBean.class);
					actuator.setDiskSpaceFree(health.getDetails().getDiskSpace().getDetails().getFree());
					actuator.setDiskSpaceThreshold(health.getDetails().getDiskSpace().getDetails().getThreshold());
					actuator.setDiskSpaceTotal(health.getDetails().getDiskSpace().getDetails().getTotal());
					return;
				}
				MyMetrics metrics = GsonUtil.fromJson(resp, MyMetrics.class);
				String valStr = metrics.getMeasurements().get(0).getValue();
				Double value = Double.parseDouble(valStr);
				switch (index) {
				case 1:
					actuator.setSystemCpuCount((int) Math.round(value));
					break;
				case 2:
					actuator.setSystemCpuUsage(value);
					break;
				case 3:
					actuator.setJvmMemoryMax(Math.round(value));
					break;
				case 4:
					actuator.setJvmMemoryUsed(Math.round(value));
					break;
				case 5:
					actuator.setJvmThreadsLive((int) Math.round(value));
					break;
				case 6:
					actuator.setProcessCpuUsage(value);
					break;
				}
				return;
			} catch (Exception e) {
				if (i == 2) {
					logger.error("访问actuator失败", e);
				}
			}
		}
		throw new IOException("获取数据失败");
	}
}
