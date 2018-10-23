package com.xqx.monitor.bean;

import java.util.Map;

public class HystrixServiceStatusBean {

	/** 请求服务名称 */
	private String name;
	/** 错误百分比 */
	private int errorPercentage;
	/** 成功次数 */
	private int rollingCountSuccess;
	/** 短路次数 */
	private int rollingCountShortCircuited;
	/** 坏请求次数 */
	private int rollingCountBadRequests;
	/** 请求失败次数 */
	private int rollingCountFailure;
	/** 信号拒绝次数 */
	private int rollingCountSemaphoreRejected;
	/**   */
	private int rollingCountThreadPoolRejected;
	/** 请求超时次数 */
	private int rollingCountTimeout;
	/** 报告主机个数 */
	private int reportingHosts;
	/** 延迟执行平均值 */
	private int latencyExecute_mean;
	/** 大部分情况下执行时间 */
	private Map<String, Integer> latencyExecute;
	private Map<String, Integer> latencyTotal;

	/** 执行超时时间属性ms */
	private int propertyValue_executionTimeoutInMilliseconds;

	private int rollingCountFallbackSuccess;
	private int rollingCountFallbackFailure;
	private int propertyValue_circuitBreakerRequestVolumeThreshold;
	private boolean propertyValue_circuitBreakerForceOpen;
	private int propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	private int latencyTotal_mean;
	private int rollingMaxConcurrentExecutionCount;
	private String type;
	private int rollingCountResponsesFromCache;
	private String propertyValue_executionIsolationStrategy;
	private int rollingCountExceptionsThrown;
	private int rollingCountFallbackMissing;
	private String threadPool;
	private boolean isCircuitBreakerOpen;
	private int errorCount;
	private String group;
	private int requestCount;
	private int rollingCountCollapsedRequests;
	private int propertyValue_circuitBreakerSleepWindowInMilliseconds;
	private int rollingCountEmit;
	private int currentConcurrentExecutionCount;
	private int propertyValue_executionIsolationSemaphoreMaxConcurrentRequests;
	private boolean propertyValue_circuitBreakerEnabled;
	private boolean propertyValue_executionIsolationThreadInterruptOnTimeout;
	private boolean propertyValue_requestCacheEnabled;
	private int rollingCountFallbackRejection;
	private boolean propertyValue_requestLogEnabled;
	private int rollingCountFallbackEmit;
	private int propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests;
	private int propertyValue_circuitBreakerErrorThresholdPercentage;
	private boolean propertyValue_circuitBreakerForceClosed;
	private String propertyValue_executionIsolationThreadPoolKeyOverride;
	private int propertyValue_executionIsolationThreadTimeoutInMilliseconds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getErrorPercentage() {
		return errorPercentage;
	}

	public void setErrorPercentage(int errorPercentage) {
		this.errorPercentage = errorPercentage;
	}

	public int getRollingCountSuccess() {
		return rollingCountSuccess;
	}

	public void setRollingCountSuccess(int rollingCountSuccess) {
		this.rollingCountSuccess = rollingCountSuccess;
	}

	public int getRollingCountShortCircuited() {
		return rollingCountShortCircuited;
	}

	public void setRollingCountShortCircuited(int rollingCountShortCircuited) {
		this.rollingCountShortCircuited = rollingCountShortCircuited;
	}

	public int getRollingCountBadRequests() {
		return rollingCountBadRequests;
	}

	public void setRollingCountBadRequests(int rollingCountBadRequests) {
		this.rollingCountBadRequests = rollingCountBadRequests;
	}

	public int getRollingCountFailure() {
		return rollingCountFailure;
	}

	public void setRollingCountFailure(int rollingCountFailure) {
		this.rollingCountFailure = rollingCountFailure;
	}

	public int getRollingCountSemaphoreRejected() {
		return rollingCountSemaphoreRejected;
	}

	public void setRollingCountSemaphoreRejected(int rollingCountSemaphoreRejected) {
		this.rollingCountSemaphoreRejected = rollingCountSemaphoreRejected;
	}

	public int getRollingCountThreadPoolRejected() {
		return rollingCountThreadPoolRejected;
	}

	public void setRollingCountThreadPoolRejected(int rollingCountThreadPoolRejected) {
		this.rollingCountThreadPoolRejected = rollingCountThreadPoolRejected;
	}

	public int getRollingCountTimeout() {
		return rollingCountTimeout;
	}

	public void setRollingCountTimeout(int rollingCountTimeout) {
		this.rollingCountTimeout = rollingCountTimeout;
	}

	public int getReportingHosts() {
		return reportingHosts;
	}

	public void setReportingHosts(int reportingHosts) {
		this.reportingHosts = reportingHosts;
	}

	public int getLatencyExecute_mean() {
		return latencyExecute_mean;
	}

	public void setLatencyExecute_mean(int latencyExecute_mean) {
		this.latencyExecute_mean = latencyExecute_mean;
	}

	public Map<String, Integer> getLatencyExecute() {
		return latencyExecute;
	}

	public void setLatencyExecute(Map<String, Integer> latencyExecute) {
		this.latencyExecute = latencyExecute;
	}

	public Map<String, Integer> getLatencyTotal() {
		return latencyTotal;
	}

	public void setLatencyTotal(Map<String, Integer> latencyTotal) {
		this.latencyTotal = latencyTotal;
	}

	public int getPropertyValue_executionTimeoutInMilliseconds() {
		return propertyValue_executionTimeoutInMilliseconds;
	}

	public void setPropertyValue_executionTimeoutInMilliseconds(int propertyValue_executionTimeoutInMilliseconds) {
		this.propertyValue_executionTimeoutInMilliseconds = propertyValue_executionTimeoutInMilliseconds;
	}

	public int getRollingCountFallbackSuccess() {
		return rollingCountFallbackSuccess;
	}

	public void setRollingCountFallbackSuccess(int rollingCountFallbackSuccess) {
		this.rollingCountFallbackSuccess = rollingCountFallbackSuccess;
	}

	public int getRollingCountFallbackFailure() {
		return rollingCountFallbackFailure;
	}

	public void setRollingCountFallbackFailure(int rollingCountFallbackFailure) {
		this.rollingCountFallbackFailure = rollingCountFallbackFailure;
	}

	public int getPropertyValue_circuitBreakerRequestVolumeThreshold() {
		return propertyValue_circuitBreakerRequestVolumeThreshold;
	}

	public void setPropertyValue_circuitBreakerRequestVolumeThreshold(
			int propertyValue_circuitBreakerRequestVolumeThreshold) {
		this.propertyValue_circuitBreakerRequestVolumeThreshold = propertyValue_circuitBreakerRequestVolumeThreshold;
	}

	public boolean isPropertyValue_circuitBreakerForceOpen() {
		return propertyValue_circuitBreakerForceOpen;
	}

	public void setPropertyValue_circuitBreakerForceOpen(boolean propertyValue_circuitBreakerForceOpen) {
		this.propertyValue_circuitBreakerForceOpen = propertyValue_circuitBreakerForceOpen;
	}

	public int getPropertyValue_metricsRollingStatisticalWindowInMilliseconds() {
		return propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	}

	public void setPropertyValue_metricsRollingStatisticalWindowInMilliseconds(
			int propertyValue_metricsRollingStatisticalWindowInMilliseconds) {
		this.propertyValue_metricsRollingStatisticalWindowInMilliseconds = propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	}

	public int getLatencyTotal_mean() {
		return latencyTotal_mean;
	}

	public void setLatencyTotal_mean(int latencyTotal_mean) {
		this.latencyTotal_mean = latencyTotal_mean;
	}

	public int getRollingMaxConcurrentExecutionCount() {
		return rollingMaxConcurrentExecutionCount;
	}

	public void setRollingMaxConcurrentExecutionCount(int rollingMaxConcurrentExecutionCount) {
		this.rollingMaxConcurrentExecutionCount = rollingMaxConcurrentExecutionCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRollingCountResponsesFromCache() {
		return rollingCountResponsesFromCache;
	}

	public void setRollingCountResponsesFromCache(int rollingCountResponsesFromCache) {
		this.rollingCountResponsesFromCache = rollingCountResponsesFromCache;
	}

	public String getPropertyValue_executionIsolationStrategy() {
		return propertyValue_executionIsolationStrategy;
	}

	public void setPropertyValue_executionIsolationStrategy(String propertyValue_executionIsolationStrategy) {
		this.propertyValue_executionIsolationStrategy = propertyValue_executionIsolationStrategy;
	}

	public int getRollingCountExceptionsThrown() {
		return rollingCountExceptionsThrown;
	}

	public void setRollingCountExceptionsThrown(int rollingCountExceptionsThrown) {
		this.rollingCountExceptionsThrown = rollingCountExceptionsThrown;
	}

	public int getRollingCountFallbackMissing() {
		return rollingCountFallbackMissing;
	}

	public void setRollingCountFallbackMissing(int rollingCountFallbackMissing) {
		this.rollingCountFallbackMissing = rollingCountFallbackMissing;
	}

	public String getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(String threadPool) {
		this.threadPool = threadPool;
	}

	public boolean isCircuitBreakerOpen() {
		return isCircuitBreakerOpen;
	}

	public void setCircuitBreakerOpen(boolean isCircuitBreakerOpen) {
		this.isCircuitBreakerOpen = isCircuitBreakerOpen;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public int getRollingCountCollapsedRequests() {
		return rollingCountCollapsedRequests;
	}

	public void setRollingCountCollapsedRequests(int rollingCountCollapsedRequests) {
		this.rollingCountCollapsedRequests = rollingCountCollapsedRequests;
	}

	public int getPropertyValue_circuitBreakerSleepWindowInMilliseconds() {
		return propertyValue_circuitBreakerSleepWindowInMilliseconds;
	}

	public void setPropertyValue_circuitBreakerSleepWindowInMilliseconds(
			int propertyValue_circuitBreakerSleepWindowInMilliseconds) {
		this.propertyValue_circuitBreakerSleepWindowInMilliseconds = propertyValue_circuitBreakerSleepWindowInMilliseconds;
	}

	public int getRollingCountEmit() {
		return rollingCountEmit;
	}

	public void setRollingCountEmit(int rollingCountEmit) {
		this.rollingCountEmit = rollingCountEmit;
	}

	public int getCurrentConcurrentExecutionCount() {
		return currentConcurrentExecutionCount;
	}

	public void setCurrentConcurrentExecutionCount(int currentConcurrentExecutionCount) {
		this.currentConcurrentExecutionCount = currentConcurrentExecutionCount;
	}

	public int getPropertyValue_executionIsolationSemaphoreMaxConcurrentRequests() {
		return propertyValue_executionIsolationSemaphoreMaxConcurrentRequests;
	}

	public void setPropertyValue_executionIsolationSemaphoreMaxConcurrentRequests(
			int propertyValue_executionIsolationSemaphoreMaxConcurrentRequests) {
		this.propertyValue_executionIsolationSemaphoreMaxConcurrentRequests = propertyValue_executionIsolationSemaphoreMaxConcurrentRequests;
	}

	public boolean isPropertyValue_circuitBreakerEnabled() {
		return propertyValue_circuitBreakerEnabled;
	}

	public void setPropertyValue_circuitBreakerEnabled(boolean propertyValue_circuitBreakerEnabled) {
		this.propertyValue_circuitBreakerEnabled = propertyValue_circuitBreakerEnabled;
	}

	public boolean isPropertyValue_executionIsolationThreadInterruptOnTimeout() {
		return propertyValue_executionIsolationThreadInterruptOnTimeout;
	}

	public void setPropertyValue_executionIsolationThreadInterruptOnTimeout(
			boolean propertyValue_executionIsolationThreadInterruptOnTimeout) {
		this.propertyValue_executionIsolationThreadInterruptOnTimeout = propertyValue_executionIsolationThreadInterruptOnTimeout;
	}

	public boolean isPropertyValue_requestCacheEnabled() {
		return propertyValue_requestCacheEnabled;
	}

	public void setPropertyValue_requestCacheEnabled(boolean propertyValue_requestCacheEnabled) {
		this.propertyValue_requestCacheEnabled = propertyValue_requestCacheEnabled;
	}

	public int getRollingCountFallbackRejection() {
		return rollingCountFallbackRejection;
	}

	public void setRollingCountFallbackRejection(int rollingCountFallbackRejection) {
		this.rollingCountFallbackRejection = rollingCountFallbackRejection;
	}

	public boolean isPropertyValue_requestLogEnabled() {
		return propertyValue_requestLogEnabled;
	}

	public void setPropertyValue_requestLogEnabled(boolean propertyValue_requestLogEnabled) {
		this.propertyValue_requestLogEnabled = propertyValue_requestLogEnabled;
	}

	public int getRollingCountFallbackEmit() {
		return rollingCountFallbackEmit;
	}

	public void setRollingCountFallbackEmit(int rollingCountFallbackEmit) {
		this.rollingCountFallbackEmit = rollingCountFallbackEmit;
	}

	public int getPropertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests() {
		return propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests;
	}

	public void setPropertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests(
			int propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests) {
		this.propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests = propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests;
	}

	public int getPropertyValue_circuitBreakerErrorThresholdPercentage() {
		return propertyValue_circuitBreakerErrorThresholdPercentage;
	}

	public void setPropertyValue_circuitBreakerErrorThresholdPercentage(
			int propertyValue_circuitBreakerErrorThresholdPercentage) {
		this.propertyValue_circuitBreakerErrorThresholdPercentage = propertyValue_circuitBreakerErrorThresholdPercentage;
	}

	public boolean isPropertyValue_circuitBreakerForceClosed() {
		return propertyValue_circuitBreakerForceClosed;
	}

	public void setPropertyValue_circuitBreakerForceClosed(boolean propertyValue_circuitBreakerForceClosed) {
		this.propertyValue_circuitBreakerForceClosed = propertyValue_circuitBreakerForceClosed;
	}

	public String getPropertyValue_executionIsolationThreadPoolKeyOverride() {
		return propertyValue_executionIsolationThreadPoolKeyOverride;
	}

	public void setPropertyValue_executionIsolationThreadPoolKeyOverride(
			String propertyValue_executionIsolationThreadPoolKeyOverride) {
		this.propertyValue_executionIsolationThreadPoolKeyOverride = propertyValue_executionIsolationThreadPoolKeyOverride;
	}

	public int getPropertyValue_executionIsolationThreadTimeoutInMilliseconds() {
		return propertyValue_executionIsolationThreadTimeoutInMilliseconds;
	}

	public void setPropertyValue_executionIsolationThreadTimeoutInMilliseconds(
			int propertyValue_executionIsolationThreadTimeoutInMilliseconds) {
		this.propertyValue_executionIsolationThreadTimeoutInMilliseconds = propertyValue_executionIsolationThreadTimeoutInMilliseconds;
	}

	@Override
	public String toString() {
		return "HystrixServiceStatusBean [name=" + name + ", errorPercentage=" + errorPercentage
				+ ", rollingCountSuccess=" + rollingCountSuccess + ", rollingCountShortCircuited="
				+ rollingCountShortCircuited + ", rollingCountBadRequests=" + rollingCountBadRequests
				+ ", rollingCountFailure=" + rollingCountFailure + ", rollingCountSemaphoreRejected="
				+ rollingCountSemaphoreRejected + ", rollingCountThreadPoolRejected=" + rollingCountThreadPoolRejected
				+ ", rollingCountTimeout=" + rollingCountTimeout + ", reportingHosts=" + reportingHosts
				+ ", latencyExecute_mean=" + latencyExecute_mean + ", latencyExecute=" + latencyExecute
				+ ", latencyTotal=" + latencyTotal + ", propertyValue_executionTimeoutInMilliseconds="
				+ propertyValue_executionTimeoutInMilliseconds + ", rollingCountFallbackSuccess="
				+ rollingCountFallbackSuccess + ", rollingCountFallbackFailure=" + rollingCountFallbackFailure
				+ ", propertyValue_circuitBreakerRequestVolumeThreshold="
				+ propertyValue_circuitBreakerRequestVolumeThreshold + ", propertyValue_circuitBreakerForceOpen="
				+ propertyValue_circuitBreakerForceOpen
				+ ", propertyValue_metricsRollingStatisticalWindowInMilliseconds="
				+ propertyValue_metricsRollingStatisticalWindowInMilliseconds + ", latencyTotal_mean="
				+ latencyTotal_mean + ", rollingMaxConcurrentExecutionCount=" + rollingMaxConcurrentExecutionCount
				+ ", type=" + type + ", rollingCountResponsesFromCache=" + rollingCountResponsesFromCache
				+ ", propertyValue_executionIsolationStrategy=" + propertyValue_executionIsolationStrategy
				+ ", rollingCountExceptionsThrown=" + rollingCountExceptionsThrown + ", rollingCountFallbackMissing="
				+ rollingCountFallbackMissing + ", threadPool=" + threadPool + ", isCircuitBreakerOpen="
				+ isCircuitBreakerOpen + ", errorCount=" + errorCount + ", group=" + group + ", requestCount="
				+ requestCount + ", rollingCountCollapsedRequests=" + rollingCountCollapsedRequests
				+ ", propertyValue_circuitBreakerSleepWindowInMilliseconds="
				+ propertyValue_circuitBreakerSleepWindowInMilliseconds + ", rollingCountEmit=" + rollingCountEmit
				+ ", currentConcurrentExecutionCount=" + currentConcurrentExecutionCount
				+ ", propertyValue_executionIsolationSemaphoreMaxConcurrentRequests="
				+ propertyValue_executionIsolationSemaphoreMaxConcurrentRequests
				+ ", propertyValue_circuitBreakerEnabled=" + propertyValue_circuitBreakerEnabled
				+ ", propertyValue_executionIsolationThreadInterruptOnTimeout="
				+ propertyValue_executionIsolationThreadInterruptOnTimeout + ", propertyValue_requestCacheEnabled="
				+ propertyValue_requestCacheEnabled + ", rollingCountFallbackRejection=" + rollingCountFallbackRejection
				+ ", propertyValue_requestLogEnabled=" + propertyValue_requestLogEnabled + ", rollingCountFallbackEmit="
				+ rollingCountFallbackEmit + ", propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests="
				+ propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests
				+ ", propertyValue_circuitBreakerErrorThresholdPercentage="
				+ propertyValue_circuitBreakerErrorThresholdPercentage + ", propertyValue_circuitBreakerForceClosed="
				+ propertyValue_circuitBreakerForceClosed + ", propertyValue_executionIsolationThreadPoolKeyOverride="
				+ propertyValue_executionIsolationThreadPoolKeyOverride
				+ ", propertyValue_executionIsolationThreadTimeoutInMilliseconds="
				+ propertyValue_executionIsolationThreadTimeoutInMilliseconds + "]";
	}

}
