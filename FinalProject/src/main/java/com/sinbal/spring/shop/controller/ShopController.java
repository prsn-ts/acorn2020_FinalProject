package com.sinbal.spring.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.service.LoginService;
import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.dto.ProductReviewDto;
import com.sinbal.spring.product.service.ProductService;
import com.sinbal.spring.shop.dto.OrderDto;

@Controller
public class ShopController {

	@Autowired
	private ProductService productservice;
	@Autowired
	private LoginService loginservice;

	

	@RequestMapping("/shop/shop.do")
	public ModelAndView productList(HttpServletRequest request, 
			ModelAndView mView){
		
		productservice.productList(request);
		//  views/shop/shop/.jsp
		mView.setViewName("shop/shop");
		return mView;
		
	}
	
	//상세보기 페이지 보기 요청 처리
	@RequestMapping("/shop/detail.do")
	public ModelAndView detail(ModelAndView mView, HttpServletRequest request) {
		
		productservice.getProductData(mView, request);
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
	//Detail.jsp 페이지 들어갔을 때 출력할 댓글 목록 페이징 ajax 요청 처리
	@RequestMapping("/shop/ajax_paging_list.do")
	@ResponseBody
	public Map<String, Object> ajaxPagingList(HttpServletRequest request, @RequestParam int num){
		
		return productservice.getPagingList(request, num);
	}
	
	//원글의 댓글 and 댓글의 댓글 추가하기 요청 처리
	@RequestMapping("/shop/private/comment_insert.do")
	public ModelAndView commentInsert(HttpServletRequest request,
			ModelAndView mView, @RequestParam int ref_group) {
		String id = (String)request.getSession().getAttribute("id");
		String content = request.getParameter("content");
		if(id != null && content != null && content != "") { //아이디가 존재하고 컨텐츠가 null or ""일때
			//새 댓글을 저장하고
			productservice.saveComment(request);
		}
		//보고있던 글 자세히 보기로 다시 리다일렉트 이동 시킨다.
		mView.setViewName("redirect:/shop/detail.do?num="+ref_group);
		return mView;
	}
	
	//댓글 수정 요청 처리
	@RequestMapping(value = "/shop/private/comment_update.do",
			method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> commentUpdate(ProductReviewDto dto){
		//댓글을 수정 반영하고
		productservice.updateComment(dto);
		Map<String, Object> map = new HashMap<>();
		map.put("num", dto.getNum());
		map.put("content", dto.getContent());
		return map;
	}
	
	//댓글 삭제 요청 처리
	@RequestMapping("/shop/private/comment_delete")
	public ModelAndView commentDelete(HttpServletRequest request,
			ModelAndView mView, @RequestParam int ref_group) {
		productservice.deleteComment(request);
		mView.setViewName("redirect:/shop/detail.do?num="+ref_group);
		return mView;
	}
	
	//메인홈페이지에서 주문내역보기 처리
	@RequestMapping("/order_list.do")
	public ModelAndView order_list(ModelAndView mView, HttpServletRequest request) {
		
		productservice.getorder_list(request, mView);
		mView.setViewName("order_list");
		return mView;
	}
}
