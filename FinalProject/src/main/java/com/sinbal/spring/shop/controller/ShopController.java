package com.sinbal.spring.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.service.ProductService;

@Controller
public class ShopController {

	@Autowired
	private ProductService productservice;
	
	@RequestMapping("/shop/shop.do")
	public ModelAndView shoplist(ModelAndView mView){
		
		productservice.getList(mView);
		//  views/shop/shop/.jsp
		mView.setViewName("shop/shop");
		return mView;
		
	}
}
