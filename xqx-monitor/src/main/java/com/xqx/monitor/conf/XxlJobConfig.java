package com.xqx.monitor.conf;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 * 
 * @ComponentScan 自动扫描该包下的handler
 */
@Configuration
@ComponentScan(basePackages = "com.xqx.monitor.handler")
public class XxlJobConfig {
	private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

	@Value("${xxl.job.admin.addresses}")
	private String adminAddresses;

	@Value("${xxl.job.executor.appname}")
	private String appName;

	@Value("${xxl.job.executor.ip}")
	private String ip;

	@Value("${xxl.job.executor.port}")
	private int port;

	@Value("${xxl.job.accessToken}")
	private String accessToken;

	@Value("${xxl.job.executor.logpath}")
	private String logPath;

	@Value("${xxl.job.executor.logretentiondays}")
	private int logRetentionDays;

	@Bean(initMethod = "start", destroyMethod = "destroy")
	public XxlJobExecutor xxlJobExecutor() {
		System.out.println("init");
		logger.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
		xxlJobExecutor.setAdminAddresses(adminAddresses);
		xxlJobExecutor.setAppName(appName);
		xxlJobExecutor.setIp(ip);
		xxlJobExecutor.setPort(port);
		xxlJobExecutor.setAccessToken(accessToken);
		xxlJobExecutor.setLogPath(logPath);
		xxlJobExecutor.setLogRetentionDays(logRetentionDays);

		return xxlJobExecutor;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getAdminAddresses() {
		return adminAddresses;
	}

	public String getAppName() {
		return appName;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getLogPath() {
		return logPath;
	}

	public int getLogRetentionDays() {
		return logRetentionDays;
	}
	
}