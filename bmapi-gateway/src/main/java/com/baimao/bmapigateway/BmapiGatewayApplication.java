package com.baimao.bmapigateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
@ComponentScan("com.baimao")
public class BmapiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmapiGatewayApplication.class, args);
    }

}
