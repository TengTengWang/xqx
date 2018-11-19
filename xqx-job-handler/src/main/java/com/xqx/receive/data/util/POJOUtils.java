package com.xqx.receive.data.util;

import com.xqx.job.user.pojo.ReceiveDO;
import com.xqx.job.user.pojo.ReceiveDTO;

public class POJOUtils {

	public static ReceiveDO toReceiveDO(ReceiveDTO r) {
		if (r == null) {
			return null;
		}
		return new ReceiveDO(r.getStatus(), r.getData());
	}
}
