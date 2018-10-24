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

	/** zipkin服务器地址：127.0.0.1:9411 */
	@Value("${monitor.zipkin.address}")
	private List<String> zipkinAddresses;

	/** hystrix服务器地址：127.0.0.1:10092 */
	@Value("${monitor.hystrix.address}")
	private List<String> hystrixAddresses;

	/** druid客户端地址：127.0.0.1:10051 */
	@Value("${monitor.druid.address}")
	private List<String> druidAddresses;

	/** 若请求大于此时间，则埋点为慢请求,默认3000ms */
	@Value("${monitor.zipkin.longTime:3000}")
	private int zipkinLongTime;

	/** 首次拉取1分钟之前数据，下次拉取该redis上次记录时间到当前时间数据，默认1分钟 */
	@Value("${monitor.zipkin.start.spanTime:60000}")
	private long zipkinStartSpanTime;

	/** 每次拉取到超过该值，则埋点为慢sql查询，默认3秒 */
	@Value("${monitor.druid.sql.maxTime:3000}")
	private long druidSqlMaxTime;

	/** 请求hystrix服务器，获取前一秒的统计数据，流方式，默认1秒 */
	@Value("${monitor.hystrix.spanTime:1000}")
	private long hystrixStreamSpanTime;

	/** 请求hystrix流服务器持续时间，默认5秒 */
	@Value("${monitor.hystrix.continueTime:5000}")
	private long hystrixStreamContinueTime;

	/** hystrix统计错误百分比，超过此值则埋点，默认10。1分钟内该值均值超过N则报警 */
	@Value("${monitor.hystrix.errorPercentage:10}")
	private int hystrixErrorPercentage;

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

	public Config getMonitorConf() {
		return monitorConf;
	}

	public void setMonitorConf(Config monitorConf) {
		this.monitorConf = monitorConf;
	}

	public int getZipkinLongTime() {
		return zipkinLongTime;
	}

	public void setZipkinLongTime(int zipkinLongTime) {
		this.zipkinLongTime = zipkinLongTime;
	}

	public long getZipkinStartSpanTime() {
		return zipkinStartSpanTime;
	}

	public void setZipkinStartSpanTime(long zipkinStartSpanTime) {
		this.zipkinStartSpanTime = zipkinStartSpanTime;
	}

	public long getDruidSqlMaxTime() {
		return druidSqlMaxTime;
	}

	public void setDruidSqlMaxTime(long druidSqlMaxTime) {
		this.druidSqlMaxTime = druidSqlMaxTime;
	}

	public long getHystrixStreamSpanTime() {
		return hystrixStreamSpanTime;
	}

	public void setHystrixStreamSpanTime(long hystrixStreamSpanTime) {
		this.hystrixStreamSpanTime = hystrixStreamSpanTime;
	}

	public long getHystrixStreamContinueTime() {
		return hystrixStreamContinueTime;
	}

	public void setHystrixStreamContinueTime(long hystrixStreamContinueTime) {
		this.hystrixStreamContinueTime = hystrixStreamContinueTime;
	}

	public int getHystrixErrorPercentage() {
		return hystrixErrorPercentage;
	}

	public void setHystrixErrorPercentage(int hystrixErrorPercentage) {
		this.hystrixErrorPercentage = hystrixErrorPercentage;
	}

	@Override
	public String toString() {
		return "MonitorConf [monitorConf=" + monitorConf + ", zipkinAddresses=" + zipkinAddresses
				+ ", hystrixAddresses=" + hystrixAddresses + ", druidAddresses=" + druidAddresses + ", zipkinLongTime="
				+ zipkinLongTime + ", zipkinStartSpanTime=" + zipkinStartSpanTime + ", druidSqlMaxTime="
				+ druidSqlMaxTime + ", hystrixStreamSpanTime=" + hystrixStreamSpanTime + ", hystrixStreamContinueTime="
				+ hystrixStreamContinueTime + ", hystrixErrorPercentage=" + hystrixErrorPercentage + "]";
	}

}
