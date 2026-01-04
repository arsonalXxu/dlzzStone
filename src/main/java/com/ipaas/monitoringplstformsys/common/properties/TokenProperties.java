package com.ipaas.monitoringplstformsys.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "deipaas.token")
@Configuration
public class TokenProperties {

    /**
     * jwt 刷新token有效时间 3600000
     */
    private long refreshTtlMillis = 14 * 24 * 60 * 60 * 1000;

    /**
     * token有效时间
     */
    private long ttlMillis = 2 * 60 * 60 * 1000;

}

