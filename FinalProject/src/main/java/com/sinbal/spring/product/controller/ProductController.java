package com.sinbal.spring.product.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	
	//원래는 request , mview 두개같이쓸필요가없지만 로직을 변경하기귀찮아서 그냥이렇게씀
	@RequestMapping("/product/insert") 
	public ModelAndView insert(ProductDto dto, ModelAndView mView ,HttpServletRequest request) {
		productService.insert(dto,request);

		
		mView.setViewName("product/insert");
		return mView;
	}
	//상품명이 존재하는 지 여부에 대한 요청 처리
	@RequestMapping("/product/checkproductname.do")
	@ResponseBody
	public Map<String, Object> checkproductname(@RequestParam String inputproductname){
		//service 가 리턴해주는 Map 객체를 리턴한다.
		return productService.isExistproductname(inputproductname);
	}

	
	// ajax 프로필 사진 업로드 요청 처리
	@RequestMapping("/product/profile_upload")
	@ResponseBody
	public Map<String, Object> profile_upload
				(HttpServletRequest request,@RequestParam MultipartFile image){
		//service 객체를 이용해서 이미지를 upload 폴더에 저장하고 Map 을 리턴 받는다.
		Map<String, Object> map=productService.saveProfileImage(request, image);
		//{"imageSrc":"/upload/xxx.jpg"} 형식의 JSON 문자열을 출력하기 위해
		//Map 을 @ResponseBody 로 리턴해준다. 
		return map;
	}	
	
	//상품 삭제 부분
	@RequestMapping("/shop/productdelete.do")
	public ModelAndView productdelete(ModelAndView mView, int num) {
		
		productService.productdelete(num);
		mView.setViewName("redirect:/shop/shop.do");
		return mView;
	}
	//회원정보 폼 페이지
	@RequestMapping("/shop/private/productupdate.do")
	public ModelAndView productupdate(ModelAndView mView, int num) {
		
		productService.getData(mView, num);
		mView.setViewName("shop/private/productupdate");
		return mView;
		
	}
	//회원정보 수정 응답페이지
	@RequestMapping("/shop/private/productupdate2.do")
	public ModelAndView productupdate2(ModelAndView mView, ProductDto dto, HttpServletRequest request) {
		
		productService.productupdate(mView, dto, request);
		mView.setViewName("shop/private/productupdate2");
		return mView;
		
	}
}
