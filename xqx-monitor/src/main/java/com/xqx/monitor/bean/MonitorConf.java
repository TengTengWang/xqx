package com.xqx.monitor.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

@Component
public class MonitorConf {

    @ApolloConfig("monitorConf")
    private Config monitorConf;
    
	@Value("${monitor.zipkin.address}")
	private List<String> zipkinAddresses;
	
	@Value("${monitor.hystrix.addres}")
	private List<String> hystrixAddresses;
	
	@Value("${monitor.druid.address}")
	private List<String> druidAddresses;

	@Value("${monitor.zipkin.longTime}")
	private int zipkinLongTime;

	public List<String> getZipkinAddresses() {
		return zipkinAddresses;
	}

	public void setZipkinAddresses(List<String> zipkinAddresses) {
		this.zipkinAddresses = zipkinAddresses;
	}

	public List<String> getHystrixAddresses() {
		return hystrixAddresses;
	}

	public void setHystrixAddresses(List<String> hystrixAddresses) {
		this.hystrixAddresses = hystrixAddresses;
	}

	public List<String> getDruidAddresses() {
		return druidAddresses;
	}

	public void setDruidAddresses(List<String> druidAddresses) {
		this.druidAddresses = druidAddresses;
	}

	@Override
	public String toString() {
		return "MonitorConf [zipkinAddresses=" + zipkinAddresses + ", hystrixAddresses=" + hystrixAddresses
				+ ", druidAddresses=" + druidAddresses + "]";
	}

}
