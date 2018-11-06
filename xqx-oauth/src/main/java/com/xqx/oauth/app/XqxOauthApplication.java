package com.xqx.oauth.app;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * 此注解中包含了
 * @SpringBootApplication
 * @EnableDiscoveryClient   表明开启服务发现
 * @EnableCircuitBreaker    表明开启Hystrix
 */
@SpringCloudApplication
@EnableDiscoveryClient // Eureka客户端
@EnableApolloConfig
@EnableCaching// 开启缓存，需要显示的指定
@ComponentScan(basePackages = {"com.xqx.oauth.app","com.xqx.base"})
public class XqxOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(XqxOauthApplication.class, args);
	}
	
	@Bean
    @LoadBalanced
    RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
        //获取RestTemplate默认配置好的所有转换器
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //默认的MappingJackson2HttpMessageConverter在第7个 先把它移除掉
        messageConverters.remove(6);
        //添加上GSON的转换器
        messageConverters.add(6, new GsonHttpMessageConverter());

        return restTemplate;
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
