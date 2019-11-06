package com.dog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dog.contract.MyContract;

@Configuration
public class DogConfig {
	@Bean
	public MyContract feignContract() {
		return new MyContract();
	}
}
