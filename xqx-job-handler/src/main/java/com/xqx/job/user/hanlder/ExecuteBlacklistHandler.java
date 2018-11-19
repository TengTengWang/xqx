package com.xqx.job.user.hanlder;

import java.util.Iterator;

import org.springframework.stereotype.Component;

import com.xqx.base.util.HttpClientUtils;
import com.xqx.job.user.pojo.ReceiveDTO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@Component
@JobHandler(value = "ExecuteBlacklistHandler")
public class ExecuteBlacklistHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		Iterator<ReceiveDTO> iterator = ReceiveBlacklistHandler.dataCache.iterator();
		while (iterator.hasNext()) {
			ReceiveDTO receivePO = iterator.next();
			if (receivePO.getStatus().equals("0")) {
				continue;
			} else {
				XxlJobLogger.log("开始执行任务" + receivePO.getData());
				// TODO 项目B的地址
				String url = "http://localhost:8091/accounts/finish" + receivePO.getData();
				HttpClientUtils client = HttpClientUtils.getInstance();
				String resp = client.sendHttpGet(url);
				if ("ok".equals(resp)) {
					receivePO.setStatus("0");
				} else {
					receivePO.setStatus("1");
				}
			}
		}
		return SUCCESS;
	}
}
