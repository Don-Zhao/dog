package com.dog.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dog.model.Member;
import com.dog.service.MemberService;

@RestController
public class MemberController {
	
//	@Autowired
//	private RestTemplate restTemplate;
//	
//	@RequestMapping(value="/get", method=RequestMethod.GET
//			, produces=MediaType.APPLICATION_JSON_VALUE)
//	public String findMember() {
//		String result = restTemplate.getForObject("http://king/member/2", String.class);
//		return result;
//	}
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
	public Member findMember(@PathVariable int id) {
		return memberService.getMember(id);
	}
	
	@RequestMapping(value="/cache/{id}", method=RequestMethod.GET)
	public Member cacheMember(@PathVariable int id) {
		for (int i = 0; i < 5; i++) {
			memberService.cacheMember(id);
		}
		return new Member();
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public Member removeMember(@PathVariable Integer id) {
		memberService.getCache(id);
		memberService.getCache(id);
		
		memberService.cacheRemove(id);
		System.out.println("$$$$$$$$$$$$$$$$$$$$");
		memberService.getCache(id);
		return new Member();
	}
	
	@RequestMapping(value="/collapse", method=RequestMethod.GET)
	public String collapse() throws InterruptedException, ExecutionException {
		System.out.println("collapse");
		Future<Member> m1 = memberService.getOneMember(1);
		Future<Member> m2 = memberService.getOneMember(2);
		Future<Member> m3 = memberService.getOneMember(3);
		System.out.println("collapse....");
		Member p1 = m1.get();
		Member p2 = m2.get();
		Member p3 = m3.get();
		
		System.out.println(p1.getName());
		System.out.println(p2.getName());
		System.out.println(p3.getName());
		System.out.println("collapse++++");
		return "";
	}
}
