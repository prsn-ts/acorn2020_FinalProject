package com.sinbal.spring.login.dao;

import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginDao {
	//아이디가 존재하는 지 여부를 판단하는 추상 메소드
	public boolean isExist(String inputId);
	//로그인된 정보를 가져오기 위한 추상 메소드
	public LoginDto getData(String id);
	//회원가입 처리를 수행하기 위한 추상 메소드
	public void insert(LoginDto dto, ModelAndView mView);
}
