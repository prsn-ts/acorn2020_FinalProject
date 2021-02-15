package com.sinbal.spring.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.dto.ProductReviewDto;
import com.sinbal.spring.shop.dto.OrderDto;

public interface ProductService {
	public void insert(ProductDto dto, HttpServletRequest request);
	public Map<String, Object> saveProfileImage(HttpServletRequest request,
			MultipartFile mFile);
	public void getList(ModelAndView mView ,ProductDto dto);
	public void insert_sub(ProductDto dto);
	//상품명이 중복되는지 검사하는 추상 메소드
	public Map<String, Object> isExistproductname(String inputproductname);
	public void productList(HttpServletRequest request);
	public void homeList(HttpServletRequest request);
	public void productdelete(HttpServletRequest request, int num);
	public void getData(ModelAndView mView ,int num);
	
	//신발 수정 서비스
	public void productupdate(ModelAndView mView, ProductDto dto ,HttpServletRequest request);

	//상품 번호에 맞는 상품 정보를 가져오는 추상 메소드
	public void getProductData(ModelAndView mView, HttpServletRequest request);
	//특정 사이즈의 재고 개수를 리턴하는 추상 메소드
	public ProductDto getStockData(int size, int num);
	//선택할 수 있는 신발 사이즈 항목의 개수를 리턴하는 추상 메소드
	public int getSizeData(int num);
	//특정 신발 사이즈에 해당하는 가격을 가져오는 추상 메소드
	public Map<String, Object> getSbsizePrice(int size, int num);
	
	//상품을 결제하는 메소드
	public void order(ModelAndView mView,OrderDto dto, HttpServletRequest request);
	
	public void buy(ModelAndView mView ,ProductDto dto);
	//새로운 댓글을 저장하는 추상 메소드
	public void saveComment(HttpServletRequest request); //댓글 저장
	//자신의 댓글을 수정하는 추상 메소드
	public void updateComment(ProductReviewDto dto); //댓글 수정
	//자신의 댓글을 삭제하는 추상 메소드
	public void deleteComment(HttpServletRequest request); //댓글 삭제
	
	//댓글 목록 페이징 ajax 요청 처리 추상 메소드
	public Map<String, Object>getPagingList(HttpServletRequest request, int num);
	
	//주문내역 리스트로불러오는 메소드
	public void getorder_list(HttpServletRequest request, ModelAndView mView);
	
	//인기상품리스트
	public void likelist(ModelAndView mView);
	
	//장바구니에 상품을 저장하는 추상 메소드
	public String saveToBasket(HttpServletRequest request, ProductDto dto, HttpServletResponse response, String cookie);
	//장바구니 DB에 저장된 내용을 가져오는 추상 메소드
	public ModelAndView shoppingBasketInfo(ProductDto dto, HttpServletRequest request, ModelAndView mView, @RequestParam String id);
	//장바구니 DB에 저장된 내용을 가져오는 추상 메소드(ajax 요청)
	public Map<String, Object> shoppingBasketInfo_Ajax(HttpServletRequest request, ModelAndView mView, ProductDto dto);
	//선택된 아이템 항목들을 삭제하는 추상 메소드(ajax 요청)
	public Map<String, Object> selected_delete_Ajax(HttpServletRequest request, ModelAndView mView, ProductDto dto);
	//선택된 아이템 항목에 맞는 재고수량을 가져오는 추상 메소드
	public Map<String, ProductDto> getSelectedSbproductSub(ProductDto dto);
}
