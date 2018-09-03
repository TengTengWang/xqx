package com.xqx.zuul.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableZuulProxy    // 注解开启Zuul的API网关功能
@EnableApolloConfig({"application","filterConfig"})     // 开启Apollo配置中心功能
@SpringCloudApplication     // 此注解中包含了@SpringBootApplication和@EnableDiscoveryClient
@ComponentScan(value ="com.xqx")
public class XqxGatewayApplication {

	public static void main(String[] args) {
        new SpringApplicationBuilder(XqxGatewayApplication.class).run(args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 使用正则表达式的方式自定义服务于路由的映射
     * @return

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }
     */
}
