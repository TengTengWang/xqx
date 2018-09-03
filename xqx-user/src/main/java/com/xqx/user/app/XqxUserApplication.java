package com.xqx.user.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableDiscoveryClient // Eureka客户端
@SpringBootApplication
@EnableApolloConfig
@ComponentScan(basePackages = "com.xqx")
public class XqxUserApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(XqxUserApplication.class).web(true).run(args);
	}
}
