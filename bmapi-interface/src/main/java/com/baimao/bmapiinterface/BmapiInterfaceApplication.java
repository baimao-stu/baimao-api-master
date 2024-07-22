package com.baimao.bmapiinterface;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baimao.bmapiinterface.mapper")
public class BmapiInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmapiInterfaceApplication.class, args);
	}

}
