package com.dog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dog.interfaces.FeignClientWithHystrix;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;

@RestController
public class FeignController {
	
	@Autowired
	private FeignClientWithHystrix client;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return client.hello();
	}
	
	@RequestMapping(value="/err", method=RequestMethod.GET)
	public String error() {
		String result = client.errorHello();
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
				.getInstance(HystrixCommandKey.Factory.asKey("FeignClientWithHystrix#errorHello()"));
		System.out.println("isOpen() = " + breaker.isOpen());
		return result;
	}
}
