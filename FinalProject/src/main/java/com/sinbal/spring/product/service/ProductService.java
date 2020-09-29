package com.sinbal.spring.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;

public interface ProductService {
	public void insert(ProductDto dto);
	public Map<String, Object> saveProfileImage(HttpServletRequest request,
			MultipartFile mFile);
	public void getList(ModelAndView mView);
	
	public void insert_sub(ProductDto dto);
}
