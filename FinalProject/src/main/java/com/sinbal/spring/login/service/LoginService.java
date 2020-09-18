package com.sinbal.spring.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginService {
	//쿠키 정보를 가져오는 추상 메소드
	public ModelAndView getCookie(HttpServletRequest request, ModelAndView mView);
	//로그인 처리를 위한 추상 메소드
	public void loginProcess(LoginDto dto, HttpServletRequest request,
			HttpServletResponse response, ModelAndView mView);
}
