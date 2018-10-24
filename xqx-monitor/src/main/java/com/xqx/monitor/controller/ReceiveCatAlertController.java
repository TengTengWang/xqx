package com.xqx.monitor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class ReceiveCatAlertController {
	private Logger logger = LoggerFactory.getLogger(ReceiveCatAlertController.class);

	// http://localhost:10093/cat/alert
	@RequestMapping("/cat/alert")
	public String testBean(@RequestParam(required = false) String type, @RequestParam(required = false) String value,
			@RequestParam(required = false) String key, @RequestParam(required = false) String re,
			@RequestParam(required = false) String to) {

		logger.debug("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);
		logger.info ("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);
		logger.error("接收到告警数据:type={}, value={}, key={}, re={}, to={}", type, value, key, re, to);

		JsonObject jo = new JsonObject();
		jo.addProperty("successCode", 200);
		jo.addProperty("code", 200);
		return jo.toString();
	}
}
