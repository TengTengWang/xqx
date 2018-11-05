package com.xqx.monitor.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.xqx.base.util.HttpClientUtils;

@RestController
public class ReceiveCatAlertController {
	private Logger logger = LoggerFactory.getLogger(ReceiveCatAlertController.class);

	// http://localhost:10093/cat/alert
	@RequestMapping("/cat/alert")
	public String testBean(@RequestParam(required = false) String type, @RequestParam(required = false) String value,
			@RequestParam(required = false) String key, @RequestParam(required = false) String re,
			@RequestParam(required = false) String to) {

		logger.debug("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);
		logger.info("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);
		logger.error("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);

		JsonObject jo = new JsonObject();
		jo.addProperty("successCode", 200);
		jo.addProperty("code", 200);
		return jo.toString();
	}

	@RequestMapping("/actuator")
	public String greet() throws IOException {
		String encoding = Base64Utils.encodeToString(StringUtils.getBytesUtf8("taifu:passw0rd"));

		System.out.println(encoding);
		HttpClientUtils client = HttpClientUtils.getInstance();
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", "Basic " + encoding);
		client.setHeader(header);
		String sendHttpGet = client.sendHttpGet("http://localhost:8081/actuator/health");
		String sendHttpGet2 = client.sendHttpGet("http://localhost:8081/actuator/health");
		System.out.println(sendHttpGet2);
		return sendHttpGet;
	}
}
