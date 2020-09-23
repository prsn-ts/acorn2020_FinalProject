package com.sinbal.spring.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

	@RequestMapping("/shop/shop.do")
	public ModelAndView shoplist(ModelAndView mView){
		
		
		//  views/shop/shop/.jsp
		mView.setViewName("shop/shop");
		return mView;
	}
}
