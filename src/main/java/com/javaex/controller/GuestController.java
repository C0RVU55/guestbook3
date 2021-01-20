package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/guest")
public class GuestController {
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list() {
		System.out.println("list");
		
		
		
		return "addlist";
	}
	
	//등록
	
	//삭제폼
	
	//삭제
}
