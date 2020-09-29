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

	@Override
	public void insert_sub(ProductDto dto) {
		session.insert("product.insert_sub",dto);
		
	}

	@Override
	public int getnum() {
		
		return session.selectOne("product.getnum");
	}

	@Override
	public boolean isExist(String inputproductname) {
		//입력한 아이디가 존재하는지 id 를 select 해 본다.
		String productname = session.selectOne("product.isExist", inputproductname);
		
		if(productname==null) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public void productdelete(int num) {
		session.delete("product.productdelete", num);
		
	}

	@Override
	public List<ProductDto> getData(int num) {
		
		return session.selectList("product.getData",num);
	}

	@Override
	public void productupdate(ProductDto dto) {
		session.update("product.update",dto);
		
	}

	@Override
	public void productupdate_sub(ProductDto dto) {
		session.update("product.updatesub",dto);
		
	}
}
