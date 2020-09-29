package com.sinbal.spring.product.dao;

import java.util.List;

import com.sinbal.spring.product.dto.ProductDto;

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
	public List<ProductDto> getList();
	//상품한개의  데이터 가져오기
	public List<ProductDto> getData(int num);
	
	public void productupdate(ProductDto dto);
	public void productupdate_sub(ProductDto dto);
}
