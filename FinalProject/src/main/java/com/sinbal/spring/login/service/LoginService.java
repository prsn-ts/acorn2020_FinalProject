package com.sinbal.spring.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginService {
	//로그인 정보를 가져오는 추상 메소드
	public void getLoginInfo(HttpServletRequest request, ModelAndView mView);
	//아이디가 중복되는지 검사하는 추상 메소드
	public Map<String, Object> isExistId(String inputId);
	//비밀번호가 중복되는지 검사하는 추상 메소드
	public Map<String, Object> isExistPwd(String inputPwd, HttpSession session);
	//쿠키 정보를 가져오는 추상 메소드
	public ModelAndView getCookie(HttpServletRequest request, ModelAndView mView);
	//로그인 처리를 위한 추상 메소드
	public void loginProcess(LoginDto dto, HttpServletRequest request,
			HttpServletResponse response, ModelAndView mView);
	//회원가입 요청을 처리하는 추상 메소드
	public void addUser(LoginDto dto, ModelAndView mView);
	//비밀번호 수정 요청을 처리하는 추상 메소드
	public void updateUserPwd(LoginDto dto, HttpServletRequest request, ModelAndView mView);
	//회원정보 수정 요청 처리를 위한 추상 메소드
	public void updateUser(LoginDto dto, HttpServletRequest request, ModelAndView mView);
	//회원 탈퇴 요청 관련 추상 메소드
	public void deleteUser(HttpSession session);
	
	// 돈충전 하는 서비스
	public void addMoney(HttpServletRequest request);
	// ajax를 통한 회원 돈과 포인트를 보여주는서비스
	public Map<String,Object> getaccount(HttpServletRequest request);
}
