package com.llc.search_service.config.aliconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// 配置文件前缀
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class AliConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
