package com.xqx.monitor.hystrix;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

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

				int b = -1;
				// 128K缓存
				byte[] buff = new byte[1024 * 128];
				while ((b = is.read(buff)) != -1) {
					String data = new String(buff, 0, b);
					System.out.println(data);
				}
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
}
