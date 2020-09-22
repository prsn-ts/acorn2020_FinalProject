package com.sinbal.spring;




import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
	
	
	

	@RequestMapping("/home.do")
	public String home(HttpSession session) {
		
		return "home";
	}
	
	
	// info.do라는 요청이오면
	@RequestMapping("/info.do")
	public String info() {
		
		//   info.jsp페이지를 보여주겠다.
		return "info";
	}
	
	
	

	
}
