package com.xqx.monitor.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.bean.MonitorConf;

import zipkin2.Span;

@Service
public class ZipkinHandlerService {
	private Logger logger = LoggerFactory.getLogger(ZipkinHandlerService.class);
	@Autowired
	private MonitorConf monitorIp;

	/**
	 * @param endTs    结束时间 ms
	 * @param limit    返回最大条数
	 * @param lookback 从结束时间起的追溯时间 ms
	 * @return
	 * @throws IOException 请求失败
	 */
	public List<List<Span>> getZipkin(Long endTs, int limit, Long lookback) throws IOException {

		List<String> zipkinAddresses = monitorIp.getZipkinAddresses();
		for (String zipkinAddress : zipkinAddresses) {
			try {
				// http://9.186.52.168:9411/zipkin/api/v2/traces?endTs=1539755220000&limit=1000&lookback=521991552
				String zipkinServerUrl = "http://" + zipkinAddress + "/zipkin/api/v2/traces";
				zipkinServerUrl += "?endTs=" + endTs + "&limit=" + limit + "&lookback=" + lookback;

				HttpClientUtils client = HttpClientUtils.getInstance();
				String result = client.sendHttpGet(zipkinServerUrl);

				Gson gson = new Gson();
				List<List<Span>> zipkinSpan = gson.fromJson(result, new TypeToken<List<List<Span>>>() {
				}.getType());
				return zipkinSpan;
			} catch (Exception e) {
				logger.error("访问zipkin服务" + zipkinAddress + "失败", e);
			}
		}
		throw new IOException("访问所有zipkin服务失败");
	}
}
