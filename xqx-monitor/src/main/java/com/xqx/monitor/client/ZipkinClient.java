package com.xqx.monitor.client;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xqx.base.util.HttpClientUtils;

import zipkin2.Span;

public class ZipkinClient {
	@Test
	public void zipkinApiParse() throws IOException {
		// http://9.186.52.168:9411/zipkin/api/v2/traces?endTs=1539755220000&limit=1000&lookback=521991552
		// 结束时间 ms
		long endTs = System.currentTimeMillis();
		// 返回最大条数
		int limit = 10;
		// 从结束时间起的追溯时间 ms 1小时
		long lookback = 3600 * 1000 * 1;
		String zipkinServerUrl = "http://9.186.52.168:9411/zipkin/api/v2/traces";
		zipkinServerUrl += "?endTs=" + endTs + "&limit=" + limit + "&lookback=" + lookback;
		HttpClientUtils client = HttpClientUtils.getInstance();
		String result = client.sendHttpGet(zipkinServerUrl);
		System.out.println(result);
		Gson gson = new Gson();
		List<List<Span>> zipkinSpan = gson.fromJson(result, new TypeToken<List<List<Span>>>() {
		}.getType());

		for (List<Span> spans : zipkinSpan) {
			for (Span span : spans) {
				System.out.println(span.localEndpoint().serviceName() + "\t" + span.name() + "\t" + span.duration()
						+ "\t" + span.kind());
			}
		}
	}
	/* 以下为API返回结果
	 * xqx-gateway-->xqx-oauth-v1-->/xqx-user/getAllUser
	 *     ||
	 *     ||
	 *     \/
	 *   hystrix
[
    [
        {
            "traceId": "bfee57a5d72faef7",
            "id": "bfee57a5d72faef7",
            "kind": "SERVER",
            "name": "get",
            "timestamp": 1539239486369139,
            "duration": 94232,
            "localEndpoint": {
                "serviceName": "xqx-gateway"
            },
            "remoteEndpoint": {
                "ipv6": "::1"
            },
			"tags": {
				"error": "Filter threw Exception",
				"http.method": "GET",
				"http.path": "/xqx-user/getAllUser",
				"http.status_code": "500",
				"mvc.controller.class": "BasicErrorController",
				"mvc.controller.method": "error"
			}
        },
        {
            "traceId": "bfee57a5d72faef7",
            "parentId": "bfee57a5d72faef7",
            "id": "df871ab7be6079f5",
            "name": "hystrix",
            "timestamp": 1539239486371353,
            "localEndpoint": {
                "serviceName": "xqx-gateway"
            }
        },
        {
            "traceId": "bfee57a5d72faef7",
            "parentId": "bfee57a5d72faef7",
            "id": "093f1a579f8c17f0",
            "kind": "CLIENT",
            "name": "get",
            "timestamp": 1539239486392254,
            "duration": 31246,
            "localEndpoint": {
                "serviceName": "xqx-gateway"
            },
            "tags": {
                "http.method": "GET",
                "http.path": "/getAllUser"
            }
        },
        {
            "traceId": "bfee57a5d72faef7",
            "parentId": "bfee57a5d72faef7",
            "id": "093f1a579f8c17f0",
            "kind": "SERVER",
            "name": "get",
            "timestamp": 1539239486396085,
            "duration": 27728,
            "localEndpoint": {
                "serviceName": "xqx-user"
            },
            "remoteEndpoint": {
                "ipv6": "::1"
            },
            "tags": {
                "http.method": "GET",
                "http.path": "/getAllUser",
                "mvc.controller.class": "UserController",
                "mvc.controller.method": "getAllUser"
            },
            "shared": true
        },
        {
            "traceId": "bfee57a5d72faef7",
            "parentId": "df871ab7be6079f5",
            "id": "67188d6964e599b2",
            "kind": "CLIENT",
            "name": "post",
            "timestamp": 1539239486372116,
            "duration": 9413,
            "localEndpoint": {
                "serviceName": "xqx-gateway"
            },
            "tags": {
                "http.method": "POST",
                "http.path": "/verifyToken"
            }
        },
        {
            "traceId": "bfee57a5d72faef7",
            "parentId": "df871ab7be6079f5",
            "id": "67188d6964e599b2",
            "kind": "SERVER",
            "name": "post",
            "timestamp": 1539239486376099,
            "duration": 4457,
            "localEndpoint": {
                "serviceName": "xqx-oauth-v1"
            },
            "remoteEndpoint": {
                "ipv4": "9.125.64.251"
            },
            "tags": {
                "http.method": "POST",
                "http.path": "/verifyToken",
                "mvc.controller.class": "TokenController",
                "mvc.controller.method": "verifyToken"
            },
            "shared": true
        }
    ]
]
	 */
}
