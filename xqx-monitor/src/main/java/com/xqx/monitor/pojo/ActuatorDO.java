package com.xqx.monitor.pojo;

public class ActuatorDO {
	private String address;
	/** 磁盘总空间(byte) */
	private long DiskSpaceTotal;
	/** 磁盘剩余空间(byte) */
	private long DiskSpaceFree;
	/** 磁盘使用空间临界值，若接近此值则硬盘不可靠(byte) */
	private long DiskSpaceThreshold;
	/** Java虚拟机可用的处理器数量 */
	private int systemCpuCount;
	/** cpu使用率0-1 */
	private double systemCpuUsage;
	/** jvm最大内存 (bytes) */
	private long jvmMemoryMax;
	/** jvm已用内存 (bytes) */
	private long jvmMemoryUsed;
	/** jvm可用内存 (bytes) */
	private long jvmMemoryFree;
	/** 线程数量 */
	private int jvmThreadsLive;
	/** The "recent cpu usage" for the Java Virtual Machine process */
	private double processCpuUsage;

//	/** The maximum file descriptor count */
//	private int processFilesMax;
//	/**
//	 * The sum of the number of runnable entities queued to available processors and
//	 * the number of runnable entities running on the available processors averaged
//	 * over a period of time
//	 */
//	private double systemLoadAverage1m;
//	private int httpServerRequests;
//	/** Number of info level events that made it to the logs */
//	private int logbackEvents;
//	/** The uptime of the Java virtual machine */
//	private double processUptime;
//	/** The open file descriptor count */
//	private int processFilesOpen;
//	/** Start time of the process since Unix epoch.(s) */
//	private int processStartTime;
//
//	/**
//	 * Count of positive increases in the size of the old generation memory pool
//	 * before GC to after GC
//	 */
//	private long jvmGcMemoryPromoted;
//	/** Max size of old generation memory pool */
//	private long jvmGcMaxDataSize;
//	/** Time spent in GC pause */
//	private int jvmGcPauseCount;
//	/** Time spent in GC pause */
//	private double jvmGcPauseTime;
//	/**
//	 * The amount of memory in bytes that is committed for the Java virtual machine
//	 * to use
//	 */
//	private int jvmMemoryCommitted;
//	/**
//	 * An estimate of the memory that the Java virtual machine is using for this
//	 * buffer pool
//	 */
//	private long jvmBufferMemoryUsed;
//	/** The current number of live daemon threads */
//	private int jvmThreadsDaemon;
//	/**
//	 * Incremented for an increase in the size of the young generation memory pool
//	 * after one GC to before the next
//	 */
//	private long jvmGcMemoryAllocated;
//	/**
//	 * The peak live thread count since the Java virtual machine started or peak was
//	 * reset
//	 */
//	private int jvmThreadsPeak;
//	/**
//	 * The number of classes that are currently loaded in the Java virtual machine
//	 */
//	private int jvmClassesLoaded;
//	/**
//	 * The total number of classes unloaded since the Java virtual machine has
//	 * started execution
//	 */
//	private int jvmClassesUnloaded;
//	/** Size of old generation memory pool after a full GC(byte) */
//	private long jvmGcLiveDataSize;
//	/** An estimate of the number of buffers in the pool */
//	private int jvmBufferCount;
//	/** An estimate of the total capacity of the buffers in this pool */
//	private int jvmBufferTotalCapacity;
//	private int tomcatCacheAccess;
//	private int tomcatCacheHit;
//	private int tomcatGlobalError;
//	private int tomcatGlobalReceived;
//	private int tomcatGlobalRequestMax;
//	private int tomcatGlobalRequest;
//	private int tomcatGlobalSent;
//	private int tomcatServletError;
//	private int tomcatServletRequestMax;
//	private int tomcatServletRequestCount;
//	private double tomcatServletRequestTotleTime;
//	private int tomcatSessionsActiveCurrent;
//	private int tomcatSessionsAliveMax;
//	private int tomcatSessionsCctiveMax;
//	private int tomcatSessionsCreated;
//	private int tomcatSessionsExpired;
//	private int tomcatSessionsRejected;
//	private int tomcatThreadsBusy;
//	private int tomcatThreadsCurrent;
//	private int tomcatThreadsConfigMax;

	public long getDiskSpaceTotal() {
		return DiskSpaceTotal;
	}

	public void setDiskSpaceTotal(long diskSpaceTotal) {
		DiskSpaceTotal = diskSpaceTotal;
	}

	public long getDiskSpaceFree() {
		return DiskSpaceFree;
	}

	public void setDiskSpaceFree(long diskSpaceFree) {
		DiskSpaceFree = diskSpaceFree;
	}

	public long getDiskSpaceThreshold() {
		return DiskSpaceThreshold;
	}

	public void setDiskSpaceThreshold(long diskSpaceThreshold) {
		DiskSpaceThreshold = diskSpaceThreshold;
	}

	public int getSystemCpuCount() {
		return systemCpuCount;
	}

	public void setSystemCpuCount(int systemCpuCount) {
		this.systemCpuCount = systemCpuCount;
	}

	public double getSystemCpuUsage() {
		return systemCpuUsage;
	}

	public void setSystemCpuUsage(double systemCpuUsage) {
		this.systemCpuUsage = systemCpuUsage;
	}

	public long getJvmMemoryMax() {
		return jvmMemoryMax;
	}

	public void setJvmMemoryMax(long jvmMemoryMax) {
		this.jvmMemoryMax = jvmMemoryMax;
	}

	public long getJvmMemoryUsed() {
		return jvmMemoryUsed;
	}

	public void setJvmMemoryUsed(long jvmMemoryUsed) {
		this.jvmMemoryUsed = jvmMemoryUsed;
	}

	public long getJvmMemoryFree() {
		return jvmMemoryFree;
	}

	public void setJvmMemoryFree(long jvmMemoryFree) {
		this.jvmMemoryFree = jvmMemoryFree;
	}

	public int getJvmThreadsLive() {
		return jvmThreadsLive;
	}

	public void setJvmThreadsLive(int jvmThreadsLive) {
		this.jvmThreadsLive = jvmThreadsLive;
	}

	public double getProcessCpuUsage() {
		return processCpuUsage;
	}

	public void setProcessCpuUsage(double processCpuUsage) {
		this.processCpuUsage = processCpuUsage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ActuatorDO [address=" + address + ", DiskSpaceTotal=" + DiskSpaceTotal + ", DiskSpaceFree="
				+ DiskSpaceFree + ", DiskSpaceThreshold=" + DiskSpaceThreshold + ", systemCpuCount=" + systemCpuCount
				+ ", systemCpuUsage=" + systemCpuUsage + ", jvmMemoryMax=" + jvmMemoryMax + ", jvmMemoryUsed="
				+ jvmMemoryUsed + ", jvmMemoryFree=" + jvmMemoryFree + ", jvmThreadsLive=" + jvmThreadsLive
				+ ", processCpuUsage=" + processCpuUsage + "]";
	}
	
}