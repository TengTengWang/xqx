package com.xqx.user.data.app;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableDiscoveryClient // Eureka客户端
@SpringCloudApplication
@EnableApolloConfig
@EnableCaching// 开启缓存，需要显示的指定
@ComponentScan(basePackages = "com.xqx.user.data")
public class XqxUserDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(XqxUserDataApplication.class, args);
	}
}
