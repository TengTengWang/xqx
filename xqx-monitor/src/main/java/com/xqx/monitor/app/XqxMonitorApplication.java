package com.xqx.monitor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringCloudApplication
@EnableDiscoveryClient // Eureka客户端
@EnableApolloConfig({"application","monitorConf"})
@ComponentScan(basePackages = {"com.xqx","com.xxl"})
public class XqxMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(XqxMonitorApplication.class, args);
	}
}
