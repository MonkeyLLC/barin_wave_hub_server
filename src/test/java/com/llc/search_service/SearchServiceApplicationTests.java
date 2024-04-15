package com.llc.search_service;

import com.aliyun.credentials.Client;
import com.aliyun.credentials.models.Config;
import com.llc.search_service.config.aliconfig.AliConfig;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchServiceApplicationTests {

	@Resource
	private AliConfig aliConfig;

	@Test
	void contextLoads() {
		Config config = new Config();
		config.setType("access_key");
		// String getenv = System.getenv();
		config.setAccessKeyId(aliConfig.getAccessKeyId());
		config.setAccessKeySecret(aliConfig.getAccessKeyId());
		Client client = new Client(config);
		System.out.println(client);

	}

}
