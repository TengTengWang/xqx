package com.xqx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private StringRedisTemplate template;

	@Test
	// 直接使用redisTemplate存取字符串
	public void setAndGet() {
		template.opsForValue().append("aa", "1");
		System.out.println(template.opsForValue().get("aa"));
	}
}
