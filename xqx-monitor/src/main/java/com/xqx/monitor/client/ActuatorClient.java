package com.xqx.monitor.client;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.xqx.base.gson.GsonUtil;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.pojo.ActuatorBean;
import com.xqx.monitor.pojo.ActuatorDO;
import com.xqx.monitor.pojo.ActuatorBean.MyMetrics;

public class ActuatorClient {
	private static String[] methods = { "/health", "/metrics/system.cpu.count", "/metrics/system.cpu.usage",
			"/metrics/jvm.memory.max", "/metrics/jvm.memory.used", "/metrics/jvm.threads.live", "/metrics/process.cpu.usage" };

	@Test
	public void actuatorApiParse() throws IOException {
		// http://localhost:10051/actuator/metrics/system.cpu.usage
		ActuatorDO actuator = new ActuatorDO();
		for (int i = 0; i < methods.length; i++) {
			String url = "http://localhost:10051/actuator" + methods[i];
			sendHttp(url, null, i, actuator);
		}
		System.out.println(actuator);
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
					e.printStackTrace();
				}
			}
		}
		throw new IOException("获取数据失败");
	}
}
