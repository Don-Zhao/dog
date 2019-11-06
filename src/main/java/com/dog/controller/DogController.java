package com.dog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dog.interfaces.KingClient;
import com.dog.model.User;

@RestController
public class DogController {

	@Autowired
	private KingClient kingClient;
	
	@RequestMapping("/say")
	public String say() {
		return kingClient.hello();
	}
	
	@RequestMapping("/say2")
	public String say2() {
		return kingClient.myHello();
	}
	
	@RequestMapping(value="/find", produces=MediaType.APPLICATION_JSON_VALUE)
	public User get() {
		return kingClient.get(5);
	}
}
