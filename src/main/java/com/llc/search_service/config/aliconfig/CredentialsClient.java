package com.llc.search_service.config.aliconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aliyun.credentials.Client;
import com.aliyun.credentials.models.Config;

@Configuration
public class CredentialsClient {
    private final AliConfig aliConfig;

    public CredentialsClient(AliConfig aliConfig) {
        this.aliConfig = aliConfig;
    }
    @Bean
    public Client getClientInstance() {
        Config config = new Config();
        config.setType("access_key");
        config.setAccessKeyId(aliConfig.getAccessKeyId());
        config.setAccessKeySecret(aliConfig.getAccessKeyId());
        return new Client(config);
    }
}
