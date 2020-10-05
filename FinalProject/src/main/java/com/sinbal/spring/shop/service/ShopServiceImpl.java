package com.sinbal.spring.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.shop.dao.ShopDao;
import com.sinbal.spring.shop.dto.ShopDto;

public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;
	
	//상품 번호에 맞는 상품 정보를 가져오는 추상 메소드
	@Override
	public void getProductData(ModelAndView mView, int num) {
		//상품정보를 가져온다.
		ShopDto result = shopDao.getData(num);
	}
}
