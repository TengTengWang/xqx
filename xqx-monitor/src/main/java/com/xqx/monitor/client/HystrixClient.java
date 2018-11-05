package com.xqx.monitor.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.google.gson.Gson;
import com.xqx.monitor.pojo.HystrixServiceStatusBean;
import com.xqx.monitor.pojo.HystrixThreadPoolBean;

public class HystrixClient {
	@Test
	public void parse() {

		// 从配置文件读取
		String proxyUrl = "http://localhost:10092/turbine.stream?delay=1000";

		HttpGet httpget = null;
		InputStream is = null;
		try {
			httpget = new HttpGet(proxyUrl);

			HttpClient client = HttpClients.createDefault();
			HttpResponse httpResponse = client.execute(httpget);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				is = httpResponse.getEntity().getContent();

				/*
				 * int b = -1; // 128K缓存 byte[] buff = new byte[1024 * 128]; while ((b =
				 * is.read(buff)) != -1) { String data = new String(buff, 0, b);
				 * System.out.println("----------------"); System.out.println(data);
				 * System.out.println("================"); if(b==10) {
				 * System.out.println("*************************"); } }
				 */
				/*
				 * OutputStream os = new FileOutputStream("/Users/taifu/a.txt"); int b = -1;
				 * while ((b = is.read()) != -1) { try { os.write(b); // flush buffer on line
				 * feed if (b == 10 ) { os.flush(); System.out.println("=============="); }
				 * }catch (Exception e) { e.printStackTrace(); } } os.close();
				 */

				OutputStream os = new MyByteArrayOutputStream();
				int b = -1;
				while ((b = is.read()) != -1) {
					try {
						os.write(b);
						// flush buffer on line feed
						if (b == 10) {
							String data = os.toString();
							// data:
							// {"rollingCountFallbackSuccess":0,"rollingCountFallbackFailure":0,"propertyValue_circuitBreakerRequestVolumeThreshold":20,"propertyValue_circuitBreakerForceOpen":false,"propertyValue_metricsRollingStatisticalWindowInMilliseconds":10000,"latencyTotal_mean":0,"rollingMaxConcurrentExecutionCount":0,"type":"HystrixCommand","rollingCountResponsesFromCache":0,"rollingCountBadRequests":0,"rollingCountTimeout":0,"propertyValue_executionIsolationStrategy":"SEMAPHORE","rollingCountFailure":0,"rollingCountExceptionsThrown":0,"rollingCountFallbackMissing":0,"threadPool":"RibbonCommand","latencyExecute_mean":0,"isCircuitBreakerOpen":false,"errorCount":0,"rollingCountSemaphoreRejected":0,"group":"RibbonCommand","latencyTotal":{"0":0,"99":0,"100":0,"25":0,"90":0,"50":0,"95":0,"99.5":0,"75":0},"requestCount":0,"rollingCountCollapsedRequests":0,"rollingCountShortCircuited":0,"propertyValue_circuitBreakerSleepWindowInMilliseconds":5000,"latencyExecute":{"0":0,"99":0,"100":0,"25":0,"90":0,"50":0,"95":0,"99.5":0,"75":0},"rollingCountEmit":0,"currentConcurrentExecutionCount":0,"propertyValue_executionIsolationSemaphoreMaxConcurrentRequests":100,"errorPercentage":0,"rollingCountThreadPoolRejected":0,"propertyValue_circuitBreakerEnabled":true,"propertyValue_executionIsolationThreadInterruptOnTimeout":true,"propertyValue_requestCacheEnabled":true,"rollingCountFallbackRejection":0,"propertyValue_requestLogEnabled":true,"rollingCountFallbackEmit":0,"rollingCountSuccess":0,"propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests":10,"propertyValue_circuitBreakerErrorThresholdPercentage":50,"propertyValue_circuitBreakerForceClosed":false,"name":"xqx-oauth-v1","reportingHosts":1,"propertyValue_executionIsolationThreadPoolKeyOverride":"null","propertyValue_executionIsolationThreadTimeoutInMilliseconds":3000,"propertyValue_executionTimeoutInMilliseconds":3000}
							if (data.startsWith("data: {")) {
//								data = data.substring("data: ".length());
								parseHystrixData(data);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				os.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (httpget != null) {
				try {
					httpget.abort();
				} catch (Exception ex) {
					// ignore errors on close
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
					// ignore errors on close
				}
			}
		}

	}
//	{
//	    "rollingCountFallbackSuccess": 10,
//	    "rollingCountFallbackFailure": 0,
//	    "propertyValue_circuitBreakerRequestVolumeThreshold": 20,
//	    "propertyValue_circuitBreakerForceOpen": false,
//	    "propertyValue_metricsRollingStatisticalWindowInMilliseconds": 10000,
//	    "latencyTotal_mean": 143,
//	    "rollingMaxConcurrentExecutionCount": 1,
//	    "type": "HystrixCommand",
//	    "rollingCountResponsesFromCache": 0,
//	    "rollingCountBadRequests": 0,//Bad Request Count
//	    "rollingCountTimeout": 2,// 请求超时次数
//	    "propertyValue_executionIsolationStrategy": "THREAD",
//	    "rollingCountFailure": 0,// 请求失败次数
//	    "rollingCountExceptionsThrown": 0,
//	    "rollingCountFallbackMissing": 0,
//	    "threadPool": "TokenService",
//	    "latencyExecute_mean": 143,
//	    "isCircuitBreakerOpen": false,
//	    "errorCount": 2,// 错误次数(什么错误？)
//	    "rollingCountSemaphoreRejected": 0,// 信号拒绝次数
//	    "group": "TokenService",
//	    "latencyTotal": {
//	        "0": 5,
//	        "99": 3007,
//	        "100": 3007,
//	        "25": 6,
//	        "90": 29,
//	        "50": 8,
//	        "95": 1128,
//	        "99.5": 3007,
//	        "75": 14
//	    },
//	    "requestCount": 8,
//	    "rollingCountCollapsedRequests": 0,
//	    "rollingCountShortCircuited": 8,// 熔断次数
//	    "propertyValue_circuitBreakerSleepWindowInMilliseconds": 5000,
//	    "latencyExecute": {
//	        "0": 5,
//	        "99": 3007,
//	        "100": 3007,
//	        "25": 6,
//	        "90": 29,
//	        "50": 8,
//	        "95": 1128,
//	        "99.5": 3007,
//	        "75": 14
//	    },
//	    "rollingCountEmit": 0,
//	    "currentConcurrentExecutionCount": 0,
//	    "propertyValue_executionIsolationSemaphoreMaxConcurrentRequests": 10,
//	    "errorPercentage": 25,//错误率 Error Percentage [(Timed-out + Threadpool Rejected + Failure) / Total]
//	    "rollingCountThreadPoolRejected": 0,
//	    "propertyValue_executionIsolationThreadInterruptOnTimeout": true,
//	    "propertyValue_circuitBreakerEnabled": true,
//	    "propertyValue_circuitBreakerFoForceOpen": false,
//	    "propertyValue_requestCacheEnabled": true,
//	    "rollingCountFallbackRejection": 0,
//	    "propertyValue_requestLogEnabled": true,
//	    "rollingCountFallbackEmit": 0,
//	    "rollingCountSuccess": 6,// 成功请求的次数
//	    "propertyValue_fallbackIsolationSemaphoreMaxConcurrentRequests": 10,
//	    "propertyValue_circuitBreakerErrorThresholdPercentage": 50,
//	    "propertyValue_circuitBreakerForceClosed": false,
//	    "name": "verifyToken",
//	    "reportingHosts": 1,
//	    "propertyValue_executionIsolationThreadPoolKeyOverride": "null",
//	    "propertyValue_executionIsolationThreadTimeoutInMilliseconds": 3000,
//	    "propertyValue_executionTimeoutInMilliseconds": 3000
//	}

	private void parseHystrixData(String data) {
		if(data.contains("\"type\":\"meta\"")) {
			return;
		}
		if(data.contains("\"type\":\"HystrixThreadPool\"")) {
			data = data.substring("data: ".length());
			Gson gson = new Gson();
			HystrixThreadPoolBean bean = gson.fromJson(data, HystrixThreadPoolBean.class);
			System.out.println(bean);
			return;
		}
		if(data.contains("\"type\":\"HystrixCommand\"")) {
			data = data.substring("data: ".length());
			Gson gson = new Gson();
			HystrixServiceStatusBean bean = gson.fromJson(data, HystrixServiceStatusBean.class);
			System.out.println(bean);
			return;
		}
	}
}
