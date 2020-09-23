package com.sinbal.spring.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginService {
	//아이디가 중복되는지 검사하는 추상 메소드
	public Map<String, Object> isExistId(String inputId);
	//쿠키 정보를 가져오는 추상 메소드
	public ModelAndView getCookie(HttpServletRequest request, ModelAndView mView);
	//로그인 처리를 위한 추상 메소드
	public void loginProcess(LoginDto dto, HttpServletRequest request,
			HttpServletResponse response, ModelAndView mView);
	//회원가입 요청을 처리하는 추상 메소드
	public void addUser(LoginDto dto, ModelAndView mView);
}
