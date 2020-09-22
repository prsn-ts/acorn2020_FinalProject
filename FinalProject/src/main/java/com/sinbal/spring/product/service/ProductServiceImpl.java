package com.sinbal.spring.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinbal.spring.product.dao.ProductDao;
import com.sinbal.spring.product.dto.ProductDto;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	
	@Override
	public void insert(ProductDto dto) {
		productDao.insert(dto);
	}

}
