package com.xqx.zuul.app;

import java.util.List;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@EnableZuulProxy    // 注解开启Zuul的API网关功能
@EnableApolloConfig({"application","filterConfig"})     // 开启Apollo配置中心功能
@SpringCloudApplication     // 此注解中包含了@SpringBootApplication和@EnableDiscoveryClient
@EnableCaching
@ComponentScan(value ="com.xqx")
public class XqxGatewayApplication {

	public static void main(String[] args) {
        new SpringApplicationBuilder(XqxGatewayApplication.class).run(args);
    }

	/**
	 * 远程访问模板注册
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// 获取RestTemplate默认配置好的所有转换器
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		// 默认的MappingJackson2HttpMessageConverter在第7个 先把它移除掉
		messageConverters.remove(6);
		// 添加上GSON的转换器
		messageConverters.add(6, new GsonHttpMessageConverter());

		return restTemplate;
	}

    /**
     * 使用正则表达式的方式自定义服务于路由的映射
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }
    
    @Bean
	public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
		HystrixMetricsStreamServlet hystrixServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(hystrixServlet,"/hystrix.stream");
		registrationBean.setLoadOnStartup(1);
		registrationBean.setName(HystrixMetricsStreamServlet.class.getName());
		return registrationBean; 
	}
    
}
