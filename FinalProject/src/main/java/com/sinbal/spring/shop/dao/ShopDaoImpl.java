package com.sinbal.spring.shop.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinbal.spring.shop.dto.ShopDto;

public class ShopDaoImpl implements ShopDao{
	
	@Autowired
	private SqlSession session;
	
	//상품정보를 가져오는 메소드
	@Override
	public ShopDto getData(int num) {
		
		return session.selectOne("shop.getData", num);
	}

}
