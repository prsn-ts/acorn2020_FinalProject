package com.sinbal.spring.shop.dao;

import org.springframework.web.bind.annotation.RequestParam;

import com.sinbal.spring.shop.dto.ShopDto;

public interface ShopDao {
	//상품정보를 가져오는 추상 메소드
	public ShopDto getData(int num);
}
