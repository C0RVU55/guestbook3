package com.javaex.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/guest")
public class GuestController {
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		GuestDao gDao = new GuestDao();
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		guestList = gDao.getList();
		
		//모델 어트리뷰트
		model.addAttribute("gList", guestList);
		
		return "addList";
	}
	
	/*
	//등록1 --> @RequestParam
	@RequestMapping(value="/add", method= {RequestMethod.GET, RequestMethod.POST})
	public String add(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("content") String content) {
		System.out.println("add");
		
		GuestVo gVo = new GuestVo(name, password, content);
		GuestDao gDao = new GuestDao();
		gDao.contentAdd(gVo);
		
		return "redirect:/guest/list";
	}
	*/
	
	//등록2 --> @ModelAttribute
	@RequestMapping(value="/add", method= {RequestMethod.GET, RequestMethod.POST})
	public String add(@ModelAttribute GuestVo gVo) {
		System.out.println("add");
		
		GuestDao gDao = new GuestDao();
		gDao.contentAdd(gVo);
		
		return "redirect:/guest/list";
	}
	
	//삭제폼 
	//no=? 파라미터로 하니까 No mapping found for HTTP request with URI [/guestbook3/guest/dform&no=5] in DispatcherServlet with name 'spring' 오류남
	//또 dform?no=5가 아니라 dform&no=5로 써서 계속 오류남. 그래서 @PathVariable로 했는데 이거는 파라미터 저장이 안 됨.
	@RequestMapping(value="/dform", method= {RequestMethod.GET, RequestMethod.POST})
	public String deleteForm(@RequestParam("no") int no) {
		System.out.println("deleteForm");
		
		return "deleteForm";
	}
	
	//삭제
	@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("password") String pw, @RequestParam("no") int no) {
		System.out.println("delete");
		
		GuestDao gDao = new GuestDao();
		int count = gDao.contentDelete(no, pw);
		
		if(count == 0) { //비밀번호 틀렸을 경우 no를 어떻게 넘겨야 될지 모르겠음.
			return "redirect:/guest/dform";
		}
		
		return "redirect:/guest/list";
	}
}
