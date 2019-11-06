package com.dog.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dog.contract.MyUrl;
import com.dog.model.User;

import feign.Request.HttpMethod;

@FeignClient("king")
public interface KingClient {
	
	@RequestMapping(method=RequestMethod.GET, value="/hello")
	public String hello();
	
	@RequestMapping(value="/find/{id}", method=RequestMethod.GET)
	public User get(@PathVariable("id") int id);
	
	@MyUrl(url="/hello", method=HttpMethod.GET)
	public String myHello();
}
