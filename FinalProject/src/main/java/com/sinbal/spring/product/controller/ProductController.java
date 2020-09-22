package com.sinbal.spring.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/product/insertform")
	public String insertform() {
		
		return "product/insertform";
	}
	
	@RequestMapping("/product/insert") 
	public ModelAndView getList(ProductDto dto, ModelAndView mView) {
		productService.insert(dto);
		mView.setViewName("redirect:/home.do");
		return mView;
	}
}
