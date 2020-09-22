package com.sinbal.spring.login.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;
import com.sinbal.spring.login.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	//로그인 폼 요청 처리
	@RequestMapping("/login/login_form")
	public ModelAndView loginform(HttpServletRequest request, ModelAndView mView) {
		// url 파라미터가 넘어오는지 읽어와 보기 
		String url=request.getParameter("url");
		if(url==null){//목적지 정보가 없다면
			String cPath=request.getContextPath();
			url=cPath+"/home.do"; //로그인후 인덱스 페이지로 가도록 하기 위해 
		}
		//쿠키에 저장된 정보를 가져오기 위함.
		mView = loginService.getCookie(request, mView);
		//url 파라미터가 있는 경우 request 에 담는다.
		request.setAttribute("url", url);
		//view 페이지로 이동한다.
		mView.setViewName("login/login_form");
		return mView;
	}
	
	//로그인 요청 처리
	@RequestMapping("/login/login")
	public ModelAndView login(LoginDto dto, HttpServletRequest request,
			HttpServletResponse response, ModelAndView mView) {
		//로그인 후 가야하는 목적지 정보
		String url = request.getParameter("url");
		//목적지 정보도 미리 인코딩 해 놓는다.
		String encodedUrl = URLEncoder.encode(url);

		//로그인 처리 후 가야할 url 정보를 ModelAndView 객체에 담는다.
		mView.addObject("url", url);
		mView.addObject("encodedUrl", encodedUrl);
		
		//로그인 처리하는 메소드
		loginService.loginProcess(dto, request, response, mView);
		
		//로그인 처리 후 view 페이지로 이동한다.
		mView.setViewName("login/login");
		return mView;
	}
	
	//회원 가입 폼 요청 처리
	@RequestMapping("/login/signup_form")
	public ModelAndView signupForm(ModelAndView mView) {
		
		mView.setViewName("login/signup_form");
		return mView;
	}
	
	//로그아웃 폼 요청처리
	@RequestMapping("/login/logout.do")
	public ModelAndView logout(ModelAndView mView,HttpServletRequest request) {
		
		request.getSession().invalidate();
		mView.setViewName("redirect:/home.do");
		return mView;
	}
}
