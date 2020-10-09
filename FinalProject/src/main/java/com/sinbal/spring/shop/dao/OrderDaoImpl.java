package com.sinbal.spring.shop.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinbal.spring.shop.dto.OrderDto;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SqlSession session;
	@Override
	public void order(OrderDto dto) {
		session.insert("product.order", dto);
		
	}
	@Override
	public void addpoint(OrderDto dto) {
		session.update("product.addpoint",dto);
		
	}
	@Override
	public void minus_money(OrderDto dto) {
		session.update("product.minus_money",dto);
		
	}
	@Override
	public void minus_count(OrderDto dto) {
		session.update("product.minus_count",dto);
		
	}
	@Override
	public void buycount(OrderDto dto) {
		session.update("product.buycount",dto);
		
	}
	
	
	//주문 내역 페이징 관련 부분
	
	@Override
	public List<OrderDto> order_list(OrderDto orderDto) {
		 
		return session.selectList("product.order_list",orderDto);
	}
	//전체 주문 내역 개수를 가져오는 메소드
	@Override
	public int getCount(String id) {
		return session.selectOne("order.getCount", id);
	}
}
