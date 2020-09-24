package com.sinbal.spring.product.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinbal.spring.product.dto.ProductDto;

@Repository
public class ProductDaoImpl implements ProductDao{
	@Autowired
	private SqlSession session;
	
	@Override
	public void insert(ProductDto dto) {
		session.insert("product.addProduct",dto);
	}

	@Override
	public List<ProductDto> getList() {
		
		return session.selectList("product.getList");
	}
}
