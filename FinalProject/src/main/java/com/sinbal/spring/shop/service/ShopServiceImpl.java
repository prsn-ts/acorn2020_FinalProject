package com.sinbal.spring.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dao.ShopDao;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.shop.dto.ShopDto;


public class ShopServiceImpl implements ShopService{
	
	
	
	//현재 여기있는거 아무것도 안쓰는중
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private ShopDao shopDao;
	
	//상품 번호에 맞는 상품 정보를 가져오는 추상 메소드
	@Override
	public void getProductData(ModelAndView mView, int num) {
		//상품정보를 가져온다.
		ShopDto result = shopDao.getData(num);
	}

	@Override
	public void buy(ModelAndView mView, ProductDto dto) {
		int[] sbsize= dto.getSizearr();
		int[] sbcount= dto.getCountarr();
		int[] sbprice= dto.getPricearr();
		int totalPrice = dto.getTotalPrice();
		
		mView.addObject("sbsize",sbsize);
		mView.addObject("sbcount",sbcount);
		mView.addObject("sbprice",sbprice);
		mView.addObject("sbdto",dto);
		
	}
}
