package com.xqx.monitor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class ReceiveCatAlertController {

	// http://localhost:10093/cat/alert
	@RequestMapping("/cat/alert")
	public String testBean(@RequestParam(required = false) String type, @RequestParam(required = false) String value,
			@RequestParam(required = false) String key, @RequestParam(required = false) String re,
			@RequestParam(required = false) String to) {

		System.out.println("type="+type
				+ "\n" +"value=" + value
				+ "\n" +"key=" + key
				+ "\n" +"re=" + re
				+ "\n" +"to=" + to
				+ "\n");
		
		JsonObject jo = new JsonObject();
		jo.addProperty("successCode", 200);
		jo.addProperty("code", 200);
		return jo.toString();
	}
}
