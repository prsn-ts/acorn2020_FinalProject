package com.sinbal.spring.product.dao;

import java.util.List;

import com.sinbal.spring.product.dto.ProductDto;

public interface ProductDao {
	public void insert(ProductDto dto);
	//사이즈랑 수량 인설트
	public void insert_sub(ProductDto dto);
	
	//num값 얻어오기
	public int getnum();
	
	public List<ProductDto> getList();
}
