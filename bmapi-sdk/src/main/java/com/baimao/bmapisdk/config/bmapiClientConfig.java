package com.baimao.bmapisdk.config;

import com.baimao.bmapisdk.client.UserApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author baimao
 * @title bmapiClientConfig
 */
@Configuration
@ComponentScan
@Data
@ConfigurationProperties("bmapi.client")
public class bmapiClientConfig {

    private String accessKey;


    private String accessSecret;

    @Bean
    public UserApiClient apiClient() {
        return new UserApiClient(accessKey, accessSecret);
    }

}
