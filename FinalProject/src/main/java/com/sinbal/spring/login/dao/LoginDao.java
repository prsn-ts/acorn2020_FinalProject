package com.sinbal.spring.login.dao;

import com.sinbal.spring.login.dto.LoginDto;

public interface LoginDao {
	//로그인된 정보를 가져오기 위한 추상 메소드
	public LoginDto getData(String id);
}
