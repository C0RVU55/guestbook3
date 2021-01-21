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

//html코드를 받았을 때 코드 정리하기 : 들여쓰기, 태그 속 속성 정리 등

@Controller
@RequestMapping(value="/guest")
public class GuestController {
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) { // <-- dispatcher servlet한테 하는 소리라고 보면 됨
		System.out.println("list");
		
		GuestDao gDao = new GuestDao();
		// List<GuestVo> guestList = new ArrayList<GuestVo>(); 
		// --> dao에서 어차피 new하고 vo를 채워넣기 때문에 또 메모리에 올릴 필요없음. 
		// 여기서 선언한 List는 dao의 List주소를 가리키게 돼서 여기에서 올린 메모리는 안 쓰게 됨.
		List<GuestVo> guestList = gDao.getList();
		
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
	public String deleteForm() { //여기에서 안 쓸 거니까 @RequestParam("no") int no 안 꺼내도 됨. 삭제폼가서 바로 파라미터 꺼내쓰면 됨.
		System.out.println("deleteForm");
		
		return "deleteForm";
	}
	
	//@PathVariable는 파라미터가 아니고 컨트롤러에 갖고 있는 값이기 때문에 model 어트리뷰트해야됨. jsp에서 requestScope.no 또는 no로 꺼내 씀.
	
	//삭제
	@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("password") String pw, @RequestParam("no") int no) {
		System.out.println("delete");
		
		GuestDao gDao = new GuestDao();
		int count = gDao.contentDelete(no, pw);
		
		//비밀번호 틀렸을 경우 no를 어떻게 넘겨야 될지 모르겠음. --> 아래처럼 가능+로그인세션에서 썼던 것처럼 확인용 파라미터 추가
		//아님 모델에 넣어도 됨
		if(count == 0) { 
			return "redirect:/guest/dform?result=0&no="+no;
		}
		
		return "redirect:/guest/list";
	}
}
