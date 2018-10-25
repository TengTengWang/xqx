package com.xqx.monitor.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.google.gson.Gson;
import com.xqx.monitor.bean.HystrixServiceStatusBean;
import com.xqx.monitor.bean.MonitorConf;
import com.xqx.monitor.client.MyByteArrayOutputStream;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 统计hystrix指标：
 * <ul>
 * 1. 错误百分比 2. 熔断次数 3. 错误次数 4. 超时次数
 * </ul>
 * 若hystrix监听参数delay=1000，则表示从当前到前一秒钟所监听到的。 而CAT需持续1分钟才能报警，那么1分钟错误次数超多多少报警呢？
 * 
 * 
 * Event告警-->添加告警-->[项目:xqx-monitor]、[Type:HystrixCommand]、[监控项:执行次数]-->[持续分钟:N]-->[规则:最大值]、[阈值:错误、超时、熔断次数]
 * 
 * 对如下几点进行埋点： 1.错误百分比到达 2.访问错误次数到达 3.访问熔断次数到达 4.访问超时次数到达
 */
@JobHandler(value = "collectHystrixHandler")
@Component
public class CollectHystrixHandler extends IJobHandler {
	private Logger logger = LoggerFactory.getLogger(CollectHystrixHandler.class);

	@Autowired
	private MonitorConf monitorConf;

	@Override
	public ReturnT<String> execute(String param) {
		List<String> hystrixAddresses = monitorConf.getHystrixAddresses();
		for (String hystrixAddress : hystrixAddresses) {
			try {
				getHystrixData(hystrixAddress);
				return ReturnT.SUCCESS;
			} catch (Exception e) {
				logger.error("统计hystrix出错", e);
				// 统计出错次数
				Transaction t = Cat.newTransaction("hystrixException", hystrixAddress);
				t.setStatus(e);
				t.complete();
			}
		}
		return ReturnT.SUCCESS;
	}

	private void getHystrixData(String hystrixAddress) throws Exception {

		// 从配置文件读取druid api地址
		String proxyUrl = "http://" + hystrixAddress + "/turbine.stream?delay="
				+ monitorConf.getHystrixStreamSpanTime();

		Transaction t = Cat.newTransaction("monitorHystrix", "hystrix");
		// http 请求读取流
		HttpGet httpget = null;
		InputStream is = null;
		try {
			httpget = new HttpGet(proxyUrl);
			HttpClient client = HttpClients.createDefault();
			HttpResponse httpResponse = client.execute(httpget);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				Cat.logEvent("monitorPull", "pullHystrixSuccess", Event.SUCCESS, hystrixAddress);
				logger.debug("hytrix拉取到数据");
				is = httpResponse.getEntity().getContent();
				OutputStream os = new MyByteArrayOutputStream();
				int b = -1;
				long startTime = System.currentTimeMillis();
				while ((b = is.read()) != -1) {
					if (System.currentTimeMillis() - startTime > monitorConf.getHystrixStreamContinueTime()) {
						break;
					}
					os.write(b);
					// flush buffer on line feed
					if (b == 10) {
						String data = os.toString();
						if (data.startsWith("data: {")) {
							parseHystrixData(data);
						}
					}
				}
				os.close();
			}
			t.setStatus(Transaction.SUCCESS);
		} finally {
			t.complete();
			if (httpget != null) {
				try {
					httpget.abort();
				} catch (Exception ex) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}

		}
	}

	private void parseHystrixData(String data) {
		if (data.contains("\"type\":\"meta\"")) {
			return;
		} else if (data.contains("\"type\":\"HystrixThreadPool\"")) {
			// TODO 线程池监控
			// 此为线程池状态监控
//			Transaction t = Cat.newTransaction("hystrix", "HystrixThreadPool");
//			try {
//				logger.debug(data);
//				data = data.substring("data: ".length());
//				Gson gson = new Gson();
//				HystrixThreadPoolBean bean = gson.fromJson(data, HystrixThreadPoolBean.class);
//			} finally {
//				t.complete();
//			}
		} else if (data.contains("\"type\":\"HystrixCommand\"")) {
			try {
				data = data.substring("data: ".length());
				Gson gson = new Gson();
				HystrixServiceStatusBean bean = gson.fromJson(data, HystrixServiceStatusBean.class);
				// TODO 写入CAT逻辑
				/**
				 * 1. 若总的错误百分比到达5则这次写入CAT中 <br/>
				 * 2. 若出错次数1分钟达10次 <br/>
				 * 3. 若熔断次数1分钟达10次 <br/>
				 * 4. 若超时次数1分钟达10次 <br/>
				 */
				// 错误百分比
				int errorPercentage = bean.getErrorPercentage();
				if (errorPercentage > monitorConf.getHystrixErrorPercentage()) {
					// as: HystrixCommandErrorPercentage:verifyToken
					Cat.logEvent("HystrixCommandErrorPercentage", bean.getName());
					// 统计每次出现的平均值（若1分钟平均值大于5则告警）
					// as: HystrixCommandErrorPercentage:10
					Cat.logMetricForDuration("HystrixCommandErrorPercentage", errorPercentage);
					logger.debug("{} HystrixCommandErrorPercentage:{}", bean.getName(), errorPercentage);
				}
				// 熔断、短路次数
				int rollingCountShortCircuited = bean.getRollingCountShortCircuited();
				if (rollingCountShortCircuited > 0) {
					Cat.logEvent("HystrixCommandShortCircuited", bean.getName(), Event.SUCCESS,
							"count=" + rollingCountShortCircuited);
					// 统计这一分钟内出现的次数
					Cat.logMetricForCount("HystrixCommandShortCircuitedCount", rollingCountShortCircuited);
					logger.debug("{} HystrixCommandShortCircuitedCount:{}", bean.getName(), rollingCountShortCircuited);
				}
				// 错误次数
				int errorCount = bean.getErrorCount();
				if (errorCount > 0) {
					Cat.logEvent("HystrixCommandError", bean.getName(), Event.SUCCESS, "count=" + errorCount);
					Cat.logMetricForCount("HystrixCommandErrorCount", errorCount);
					logger.debug("{} HystrixCommandError:{}", bean.getName(), errorCount);
				}
				// 超时次数
				int rollingCountTimeout = bean.getRollingCountTimeout();
				if (rollingCountTimeout > 0) {
					Cat.logEvent("HystrixCommandTimeout", bean.getName(), Event.SUCCESS,
							"count=" + rollingCountTimeout);
					Cat.logMetricForCount("HystrixCommandTimeoutCount", rollingCountTimeout);
					logger.debug("{} HystrixCommandTimeout:{}", bean.getName(), rollingCountTimeout);
				}
			} catch (Exception e) {
				logger.error("解析hytrix数据出错", e);
				// 此为具体每个微服务监控
				Transaction t = Cat.newTransaction("HystrixCommandException", e.getClass().getName());
				t.setStatus(e);
				t.complete();
			}
		}
	}
}
