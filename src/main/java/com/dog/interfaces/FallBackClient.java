package com.dog.interfaces;

import org.springframework.stereotype.Component;

@Component
public class FallBackClient implements FeignClientWithHystrix {

	@Override
	public String hello() {
		return "出错了，执行回退方法";
	}

	@Override
	public String errorHello() {
		return "超时了，这里执行的是回退方法";
	}

}
