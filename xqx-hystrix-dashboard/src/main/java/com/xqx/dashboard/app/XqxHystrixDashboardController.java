package com.xqx.dashboard.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class XqxHystrixDashboardController {
	
	@RequestMapping(value="/hystrix/myhystrix")
	public String goMyhystrix() {
		return "/my-hystrix.html";
	}
	@RequestMapping(value="/myhystrix")
	public String goMyhystrix2() {
		return "my-hystrix.html";
	}
}
