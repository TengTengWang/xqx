package com.xqx.job.user.hanlder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xqx.job.user.pojo.ReceiveDO;
import com.xqx.job.user.pojo.ReceiveDTO;
import com.xqx.receive.data.dao.IReceiveRepository;
import com.xqx.receive.data.util.POJOUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 接收黑名单数据
 */
@Component
@JobHandler(value = "ReceiveBlacklistHandler")
public class ReceiveBlacklistHandler extends IJobHandler {
	public static List<ReceiveDTO> dataCache = new ArrayList<>();

	@Autowired
	private IReceiveRepository receiveRepostory;

	/**
	 * @param param json格式入参，如：{"accountName":"BankOfChina", "money", "100", "id",
	 *              2}
	 */
	@Override
	public ReturnT<String> execute(String param) {
		if (StringUtils.isBlank(param)) {
			XxlJobLogger.log("接收参数为空");
			return SUCCESS;
		}
		System.out.println("接收："+param);
		ReceiveDTO receive = new ReceiveDTO(param);
		receiveRepostory.save(POJOUtils.toReceiveDO(receive));
		List<ReceiveDO> receiveDOList = receiveRepostory.findAll();
		System.out.println(receiveDOList);
		
		return SUCCESS;
	}
}