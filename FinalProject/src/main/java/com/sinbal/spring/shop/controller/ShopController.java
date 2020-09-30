package com.sinbal.spring.shop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.service.ProductService;
import com.sinbal.spring.shop.service.ShopService;

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
	
	//상세보기 페이지 보기 요청 처리
	@RequestMapping("/shop/detail.do")
	public ModelAndView detail(ModelAndView mView, @RequestParam int num) {
		
		productservice.getProductData(mView, num);
		//  views/shop/shop/.jsp
		mView.setViewName("shop/detail");
		return mView;
	}
	
	//재고 개수 리턴 요청 처리
	@RequestMapping("/shop/checkQuantity.do")
	@ResponseBody
	public Map<String, Object> checkQuantity(@RequestParam int size, @RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbcount", productservice.getStockData(size, num).getSbcount());
		return result;
	}
	
	//신발 사이즈 항목의 개수 리턴 요청 처리
	@RequestMapping("/shop/checkSize.do")
	@ResponseBody
	public Map<String, Object> checkSize(@RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbsize", productservice.getSizeData(num));
		return result;
	}
}
