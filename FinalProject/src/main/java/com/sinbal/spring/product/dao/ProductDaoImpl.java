package com.sinbal.spring.product.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.shop.dto.ShopDto;

@Repository
public class ProductDaoImpl implements ProductDao{
	@Autowired
	private SqlSession session;
	
	@Override
	public void insert(ProductDto dto) {
		session.insert("product.addProduct",dto);
	}

	@Override
	public List<ProductDto> getList(ProductDto dto) {
		return session.selectList("product.getList",dto);
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
	
	//상품 정보를 가져오는 메소드
	@Override
	public ProductDto getData2(int num) {
		
		return session.selectOne("product.getData2", num);
	}
	
	//신발 사이즈, 신발 수량 정보를 가져오는 메소드
	@Override
	public List<ProductDto> getSubData(int num) {
		
		return session.selectList("product.getSubData", num);
	}

	//특정 사이즈의 재고 개수를 리턴하는 메소드
	@Override
	public ProductDto getStockData(int size, int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", size);
		map.put("num", num);
		return session.selectOne("product.getStockData", map);
	}

	//선택할 수 있는 신발 사이즈 항목의 개수를 리턴하는 메소드
	@Override
	public int getSizeData(int num) {
		return session.selectOne("product.getSizeData", num);
	}
	
	@Override
	public List<ProductDto> productList(ProductDto dto) {
		return session.selectList("product.productList", dto);
	}
	
	@Override
	public int getCount(ProductDto dto) {

		return session.selectOne("product.getCount", dto);
	}


}

