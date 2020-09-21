package com.sinbal.spring;




import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomeController {
	
	
	

	@RequestMapping("/home")
	public String home(HttpSession session) {
		
		return "home";
	}
	
}
