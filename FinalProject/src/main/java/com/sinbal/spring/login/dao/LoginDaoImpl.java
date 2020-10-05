package com.sinbal.spring.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
	
	////비밀번호가 존재하는 지 여부를 판단하는 메소드
	@Override
	public boolean isExistPwd(String inputPwd, String id) {
		//입력한 아이디가 존재하는지 id 를 select 해 본다.
		String encodedPwd = session.selectOne("login.isExistPwd", id);
		
		//DB에서 가져온 비밀번호를 복호화한다.
		//Bcrypt 클래스의 static 메소드로 DB에 저장된 암호화된 비밀번호와 입력한 비밀번호의 일치 여부를 검사한다.
		boolean isValid = BCrypt.checkpw(inputPwd, encodedPwd);
		
		if(isValid) {
			return true;
		}else {
			return false;
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
	
	//비밀번호를 수정하기 위한 메소드
	@Override
	public void updatePwd(LoginDto dto) {
		session.update("login.updatePwd", dto);
	}
	
	//업데이트된 휴대폰 번호 및 이메일 정보를 DB에 저장하는 메소드
	@Override
	public void update(LoginDto dto) {
		session.update("login.update", dto);
	}
	
	//탈퇴 처리하는 메소드
	@Override
	public void delete(String id) {
		session.delete("login.delete", id);
	}

	@Override
	public void insert_account(LoginDto dto, ModelAndView mView) {
		session.insert("login.insert_account",dto);
		
	}

	@Override
	public void addMoney(String id) {
		session.update("login.addMoney", id);
		
	}
}
