package com.sinbal.spring.shop.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.service.LoginService;
import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.service.ProductService;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.shop.service.ShopService;

@Controller
public class ShopController {

	@Autowired
	private ProductService productservice;
	@Autowired
	private LoginService loginservice;

	
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
	public Map<String, Object> checkSize(@RequestParam int size, @RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbsize", productservice.getSizeData(num));
		result.put("sbsize_price", productservice.getSbsizePrice(size, num)); //선택한 신발 사이즈에 해당하는 가격 가져오기
		return result;
	}
	
	
	
	//구매하기 버튼을 클릭했을 때 구매창 보기 요청 처리
	@RequestMapping("/shop/private/buy_form.do")
	public ModelAndView buyForm(ProductDto dto,ModelAndView mView,HttpServletRequest request) {
		
			productservice.getData(mView, dto.getNum());		
			loginservice.getLoginInfo(request, mView);
			productservice.buy(mView, dto);
		

		mView.setViewName("shop/private/buy_form");
		return mView;
	}
	
	@RequestMapping("/shop/private/buy.do")
	public ModelAndView order(ModelAndView mView ,OrderDto dto,HttpServletRequest request) {
		
		productservice.order(mView, dto, request);
		mView.setViewName("shop/private/buy");
		return mView;
	}
}
