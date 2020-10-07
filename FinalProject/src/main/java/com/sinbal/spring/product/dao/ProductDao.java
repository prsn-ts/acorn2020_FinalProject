package com.sinbal.spring.product.dao;

import java.util.List;
import java.util.Map;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dto.OrderDto;

public interface ProductDao {
	public void insert(ProductDto dto);
	//사이즈랑 수량 인설트
	public void insert_sub(ProductDto dto);
	//num 시퀀스값 얻어오기
	public int getnum();
	//상품명 존재하는지 여부를 판단
	public boolean isExist(String inputproductname);
	//상품 삭제 
	public void productdelete(int num);
	
	//상품리스트
	public List<ProductDto> getList(ProductDto dto);
	//상품한개의  데이터 가져오기
	public List<ProductDto> getData(int num);
	
	public void productupdate(ProductDto dto);
	public void productupdate_sub(ProductDto dto);
	
	//상품 정보를 가져오는 추상 메소드
	public ProductDto getData2(int num);
	//신발 사이즈, 신발 수량 정보를 가져오는 추상 메소드
	public List<ProductDto> getSubData(int num);
	//특정 사이즈의 재고 개수를 리턴하는 추상 메소드
	public ProductDto getStockData(int size, int num);
	//선택할 수 있는 신발 사이즈 항목의 개수를 리턴하는 추상 메소드
	public int getSizeData(int num);
	public List<ProductDto> productList(ProductDto dto);
	public int getCount(ProductDto dto);
	
	
}
