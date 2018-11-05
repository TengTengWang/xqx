package com.xqx.monitor.pojo;

public class HystrixThreadPoolBean {

	private int currentCorePoolSize;
	private int currentLargestPoolSize;
	private int propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	private int currentActiveCount;
	private int currentMaximumPoolSize;
	private int currentQueueSize;
	private String type;
	private int currentTaskCount;
	private int currentCompletedTaskCount;
	private int rollingMaxActiveThreads;
	private int rollingCountCommandRejections;
	private String name;
	private int reportingHosts;
	private int currentPoolSize;
	private int propertyValue_queueSizeRejectionThreshold;
	private int rollingCountThreadsExecuted;

	public void setCurrentCorePoolSize(int currentCorePoolSize) {
		this.currentCorePoolSize = currentCorePoolSize;
	}

	public int getCurrentCorePoolSize() {
		return currentCorePoolSize;
	}

	public void setCurrentLargestPoolSize(int currentLargestPoolSize) {
		this.currentLargestPoolSize = currentLargestPoolSize;
	}

	public int getCurrentLargestPoolSize() {
		return currentLargestPoolSize;
	}

	public void setPropertyValue_metricsRollingStatisticalWindowInMilliseconds(
			int propertyValue_metricsRollingStatisticalWindowInMilliseconds) {
		this.propertyValue_metricsRollingStatisticalWindowInMilliseconds = propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	}

	public int getPropertyValue_metricsRollingStatisticalWindowInMilliseconds() {
		return propertyValue_metricsRollingStatisticalWindowInMilliseconds;
	}

	public void setCurrentActiveCount(int currentActiveCount) {
		this.currentActiveCount = currentActiveCount;
	}

	public int getCurrentActiveCount() {
		return currentActiveCount;
	}

	public void setCurrentMaximumPoolSize(int currentMaximumPoolSize) {
		this.currentMaximumPoolSize = currentMaximumPoolSize;
	}

	public int getCurrentMaximumPoolSize() {
		return currentMaximumPoolSize;
	}

	public void setCurrentQueueSize(int currentQueueSize) {
		this.currentQueueSize = currentQueueSize;
	}

	public int getCurrentQueueSize() {
		return currentQueueSize;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setCurrentTaskCount(int currentTaskCount) {
		this.currentTaskCount = currentTaskCount;
	}

	public int getCurrentTaskCount() {
		return currentTaskCount;
	}

	public void setCurrentCompletedTaskCount(int currentCompletedTaskCount) {
		this.currentCompletedTaskCount = currentCompletedTaskCount;
	}

	public int getCurrentCompletedTaskCount() {
		return currentCompletedTaskCount;
	}

	public void setRollingMaxActiveThreads(int rollingMaxActiveThreads) {
		this.rollingMaxActiveThreads = rollingMaxActiveThreads;
	}

	public int getRollingMaxActiveThreads() {
		return rollingMaxActiveThreads;
	}

	public void setRollingCountCommandRejections(int rollingCountCommandRejections) {
		this.rollingCountCommandRejections = rollingCountCommandRejections;
	}

	public int getRollingCountCommandRejections() {
		return rollingCountCommandRejections;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setReportingHosts(int reportingHosts) {
		this.reportingHosts = reportingHosts;
	}

	public int getReportingHosts() {
		return reportingHosts;
	}

	public void setCurrentPoolSize(int currentPoolSize) {
		this.currentPoolSize = currentPoolSize;
	}

	public int getCurrentPoolSize() {
		return currentPoolSize;
	}

	public void setPropertyValue_queueSizeRejectionThreshold(int propertyValue_queueSizeRejectionThreshold) {
		this.propertyValue_queueSizeRejectionThreshold = propertyValue_queueSizeRejectionThreshold;
	}

	public int getPropertyValue_queueSizeRejectionThreshold() {
		return propertyValue_queueSizeRejectionThreshold;
	}

	public void setRollingCountThreadsExecuted(int rollingCountThreadsExecuted) {
		this.rollingCountThreadsExecuted = rollingCountThreadsExecuted;
	}

	public int getRollingCountThreadsExecuted() {
		return rollingCountThreadsExecuted;
	}

	@Override
	public String toString() {
		return "HystrixThreadPoolBean [currentCorePoolSize=" + currentCorePoolSize + ", currentLargestPoolSize="
				+ currentLargestPoolSize + ", propertyValue_metricsRollingStatisticalWindowInMilliseconds="
				+ propertyValue_metricsRollingStatisticalWindowInMilliseconds + ", currentActiveCount="
				+ currentActiveCount + ", currentMaximumPoolSize=" + currentMaximumPoolSize + ", currentQueueSize="
				+ currentQueueSize + ", type=" + type + ", currentTaskCount=" + currentTaskCount
				+ ", currentCompletedTaskCount=" + currentCompletedTaskCount + ", rollingMaxActiveThreads="
				+ rollingMaxActiveThreads + ", rollingCountCommandRejections=" + rollingCountCommandRejections
				+ ", name=" + name + ", reportingHosts=" + reportingHosts + ", currentPoolSize=" + currentPoolSize
				+ ", propertyValue_queueSizeRejectionThreshold=" + propertyValue_queueSizeRejectionThreshold
				+ ", rollingCountThreadsExecuted=" + rollingCountThreadsExecuted + "]";
	}

}
