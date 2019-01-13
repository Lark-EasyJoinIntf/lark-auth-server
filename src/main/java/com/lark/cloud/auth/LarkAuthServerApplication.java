package com.lark.cloud.auth;

import com.lark.cloud.user.client.SysUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {SysUserClient.class})
public class LarkAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LarkAuthServerApplication.class, args);
	}

}

