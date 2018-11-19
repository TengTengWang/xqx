package com.xqx.data.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	@Test
	public void mainTest() {
		try {
			String host = "r-2zef340bc2798464.redis.rds.aliyuncs.com";// 控制台显示访问地址
			int port = 6379;
			Jedis jedis = new Jedis(host, port);
			// 鉴权信息
			jedis.auth("4Ynk2BwmB48oSWiO");// password
			String key = "redis";
			String value = "aliyun-redis";
			// select db默认为0
			jedis.select(1);
			// set一个key
			jedis.set(key, value);
			System.out.println("Set Key " + key + " Value: " + value);
			// get 设置进去的key
			String getvalue = jedis.get(key);
			System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
