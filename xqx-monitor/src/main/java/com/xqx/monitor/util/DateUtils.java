package com.xqx.monitor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String toString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
}
