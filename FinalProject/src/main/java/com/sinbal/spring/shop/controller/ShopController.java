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
	public Map<String, Object> checkSize(@RequestParam int size, @RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbsize", productservice.getSizeData(num));
		result.put("sbsize_price", productservice.getSbsizePrice(size, num)); //선택한 신발 사이즈에 해당하는 가격 가져오기
		return result;
	}
	
	//구매하기 버튼을 클릭했을 때 구매창 보기 요청 처리
	@RequestMapping("/shop/private/buy_form(테스트용).do")
	public String buyForm(ProductDto dto) {
		int[] sbsize= dto.getSizearr();
		int[] sbcount= dto.getCountarr();
		int[] sbprice= dto.getPricearr();
		int totalPrice = dto.getTotalPrice();
		
		System.out.println("sbsize[0] : "+sbsize[0]);
		System.out.println("sbsize[1] : "+sbsize[1]);
		System.out.println("sbsize[2] : "+sbsize[2]);
		System.out.println("sbsize[3] : "+sbsize[3]);
		System.out.println("sbsize[4] : "+sbsize[4]);
		System.out.println("sbsize[5] : "+sbsize[5]);
		
		System.out.println("sbcount[0]_230 : "+sbcount[0]);
		System.out.println("sbcount[1]_240 : "+sbcount[1]);
		System.out.println("sbcount[2]_250 : "+sbcount[2]);
		System.out.println("sbcount[3]_260 : "+sbcount[3]);
		System.out.println("sbcount[4]_270 : "+sbcount[4]);
		System.out.println("sbcount[5]_280 : "+sbcount[5]);
		
		System.out.println("sbprice[0]_230 : "+sbprice[0]);
		System.out.println("sbprice[1]_240 : "+sbprice[1]);
		System.out.println("sbprice[2]_250 : "+sbprice[2]);
		System.out.println("sbprice[3]_260 : "+sbprice[3]);
		System.out.println("sbprice[4]_270 : "+sbprice[4]);
		System.out.println("sbprice[5]_280 : "+sbprice[5]);
		
		System.out.println("totalPrice : "+totalPrice);
		
		return "shop/private/buy_form(테스트용)";
	}
}
