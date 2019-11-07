package com.dog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dog.model.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

@Service
//@DefaultProperties(defaultFallback="getMemberFallback")
public class MemberService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 这里面对hystrix中配置参数，只对该方法起作用。如果想要修改所有的hystrix参数，可以使用DefaultProperties
	@HystrixCommand(fallbackMethod="getMemberFallback", 
				groupKey="MemberGroup", 
				commandKey="MemberCommon",
				commandProperties= {
						@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
				},
				threadPoolProperties= {
						@HystrixProperty(name="coreSize", value="2")
				})
	public Member getMember(int id) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Member member = restTemplate.getForObject("http://king/member/" + id, Member.class);
		return member;
	}
	
	public Member getMemberFallback(int id) {
		Member member = new Member();
		member.setId(id);
		member.setName("回退方法");
		member.setUrl("调用失败，这是回退方法");
		
		return member;
	}
	
	@CacheResult
	@HystrixCommand
	public Member cacheMember(Integer id) {
		System.out.println("cacheMember use");
		Member member = restTemplate.getForObject("http://king/member/" + id, Member.class);
		
		return member;
	}
	
	/**
	 * 删除缓存
	 * @param id
	 */
	@CacheRemove(commandKey = "cacheKey")
	@HystrixCommand
	public void cacheRemove(Integer id) {
		System.out.println("remove cache key");
	}
	
	@CacheResult
	@HystrixCommand(commandKey = "cacheKey")
	public String getCache(Integer id) {
		System.out.println("getCache");
		return null;
	}
	
	/**
	 *     合并请求，减少与服务器交互的次数
	 *  实际执行的方法是getMembers
	 * @param id
	 * @return
	 */
	@HystrixCollapser(batchMethod="getMembers", collapserProperties= {
			// 收集1秒内的请求
			@HystrixProperty(name="timerDelayInMilliseconds", value="1000")
	})
	public Future<Member> getOneMember(Integer id) {
		System.out.println("get member method invoke");
		return null;
	}
	
	@HystrixCommand
	public List<Member> getMembers(List<Integer> ids) {
		System.out.println("getMembers");
		List<Member> members = new ArrayList<Member>();
		for (Integer id : ids) {
			members.add(new Member(1, "zhaojiahong1"));
			System.out.println("id=" + id);
		}
		
		return members;
	}
}
