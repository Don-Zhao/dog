package com.dog.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="king", fallback=FallBackClient.class)
public interface FeignClientWithHystrix {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String hello();
	
	@RequestMapping(value="/err", method=RequestMethod.GET)
	public String errorHello();
}
