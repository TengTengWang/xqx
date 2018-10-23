package com.xqx.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xqx.monitor.bean.MonitorConf;
import com.xqx.monitor.handler.CollectDruidHandler;
import com.xqx.monitor.handler.CollectHystrixHandler;
import com.xqx.monitor.handler.CollectZipkinHandler;

@RestController
public class RController {

	@Autowired
	private StringRedisTemplate template;
	@Autowired
	private CollectZipkinHandler zipkinHandler;
	@Autowired
	private CollectDruidHandler druidHandler;
	@Autowired
	private CollectHystrixHandler hystrixHandler;
	@Autowired
	private MonitorConf monitorIp;

	// http://localhost:10093/test
	@RequestMapping("/test")
	public String testBean() {
		return monitorIp.toString();
	}

	// http://localhost:10093/hello/v1
	@RequestMapping("/hello/{val}")
	public String redisHello(@PathVariable String val) {
		template.opsForValue().append("aa", val);
		String str = template.opsForValue().get("aa");
		System.out.println(str);
		return str;
	}

	// http://localhost:10093/hello
	@RequestMapping("/hello")
	public String exe() {
		zipkinHandler.execute("");
		druidHandler.execute("");
		hystrixHandler.execute("");
		return "ok";
	}

	// http://localhost:10093/helloz
	@RequestMapping("/helloz")
	public String exez() {
		zipkinHandler.execute("");
		return "ok";
	}

	// http://localhost:10093/hellod
	@RequestMapping("/hellod")
	public String exed() {
		druidHandler.execute("");
		return "ok";
	}

	// http://localhost:10093/helloh
	@RequestMapping("/helloh")
	public String exeh() {
		hystrixHandler.execute("");
		return "ok";
	}
}
