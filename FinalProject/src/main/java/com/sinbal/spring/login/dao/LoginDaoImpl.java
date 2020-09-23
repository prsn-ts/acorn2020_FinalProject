package com.sinbal.spring.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;

@Repository
public class LoginDaoImpl implements LoginDao{
	@Autowired
	private SqlSession session;
	
	//아이디가 존재하는 지 여부를 판단하는 메소드
	@Override
	public boolean isExist(String inputId) {
		//입력한 아이디가 존재하는지 id 를 select 해 본다.
		String id = session.selectOne("login.isExist", inputId);
		
		if(id==null) {
			return false;
		}else {
			return true;
		}
	}
	
	//로그인된 정보를 가져오는 메소드
	@Override
	public LoginDto getData(String id) {
		return session.selectOne("login.getData", id);
	}
	
	//회원가입 처리를 수행하기 위한 메소드
	@Override
	public void insert(LoginDto dto, ModelAndView mView) {
		Integer result = session.insert("login.insert", dto);
		if(result <= 0) {//회원가입이 실패한 경우
			boolean isSuccess = false;
			mView.addObject("isSuccess", isSuccess);
		}else if(result > 0) {//회원가입이 성공한 경우
			boolean isSuccess = true;
			mView.addObject("isSuccess", isSuccess);
		}
	}
}
