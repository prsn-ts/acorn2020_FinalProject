package com.sinbal.spring.shop.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.shop.dto.ShopDto;

public interface ShopService {
	//상품 번호에 맞는 상품 정보를 가져오는 추상 메소드
	public void getProductData(ModelAndView mView, int num);
	
	public void buy(ModelAndView mView, ProductDto dto);

}
