package com.xqx.monitor.common;

public class StaticParam {
	/** 1000 * 60 = 1分钟，首次拉取1分钟之前数据 */
	public static long collectZipkinIntervalTime = 1000 * 60;
	
	/** 1000 * 60 = 1分钟 */
	public static long collectDruidIntervalTime = 1000 * 60;
	
	/** 1000 = 1秒，统计前一秒的数据，流方式 */
	public static long collectHystrixStreamIntervalTime = 1000;
	

	/** 100ms  sql最慢执行时间最大值 */
	public static long druidMaxTimespan = 50;
}
