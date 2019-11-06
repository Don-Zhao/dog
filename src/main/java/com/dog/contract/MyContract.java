package com.dog.contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.MethodMetadata;
import feign.Request.HttpMethod;

public class MyContract extends SpringMvcContract {

	@Override
	protected void processAnnotationOnMethod(MethodMetadata data, Annotation annotation, Method method) {
		super.processAnnotationOnMethod(data, annotation, method);
		if (MyUrl.class.isAnnotation()) {
			MyUrl myUrl = method.getAnnotation(MyUrl.class);
			if (myUrl != null) {
				String url = myUrl.url();
				HttpMethod urlMethod = myUrl.method();
				
				data.template().method(urlMethod);
				data.template().uri(url);
			}
		}
	}
}
