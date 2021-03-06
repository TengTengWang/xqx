package com.xqx.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 此注解中包含了
 * 
 * @SpringCloudApplication 表明程序启动入口，包含如下
 *                         <ul>
 *                         <li>@SpringBootApplication 表明该类为程序启动类</li>
 *                         <li>@EnableDiscoveryClient 表明开启服务发现，Eureka</li>
 *                         <li>@EnableCircuitBreaker 表明开启Hystrix</li>
 *                         </ul>
 * @EnableApolloConfig 表明程序应用apollo提供配置
 * @EnableCaching 表明开启缓存，需要显示的指定
 * @ComponentScan 配置spring扫描包的路径
 */
@SpringCloudApplication
@EnableApolloConfig
@EnableCaching
@ComponentScan(basePackages = { "com.xqx", "com.xxl" })
public class XqxJonHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(XqxJonHandlerApplication.class, args);
	}
}
