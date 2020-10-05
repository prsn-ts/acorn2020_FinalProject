package com.sinbal.spring.login.dao;

import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginDao {
	//아이디가 존재하는 지 여부를 판단하는 추상 메소드
	public boolean isExist(String inputId);
	//비밀번호가 존재하는 지 여부를 판단하는 추상 메소드
	public boolean isExistPwd(String inputPwd, String id);
	//로그인된 정보를 가져오기 위한 추상 메소드
	public LoginDto getData(String id);
	//회원가입 처리를 수행하기 위한 추상 메소드
	public void insert(LoginDto dto, ModelAndView mView);
	//비밀번호 수정 요청 처리하는 추상 메소드
	public void updatePwd(LoginDto dto);
	//업데이트된 휴대폰 번호 및 이메일 정보를 DB에 저장하는 추상 메소드
	public void update(LoginDto dto);
	//탈퇴 처리하는 추상 메소드
	public void delete(String id);
	//회원가입시 계좌 테이블 수행하기위한 메소드
	public void insert_account(LoginDto dto, ModelAndView mView);
	
	public void addMoney(String id);
}
