package com.sinbal.spring.shop.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinbal.spring.shop.dto.OrderDto;

public interface OrderDao {
	public void order(OrderDto dto) ;//상품을 주문하는 메소드
	public void addpoint(OrderDto dto); //포인트를 적립하는메소드
	public void minus_money(OrderDto dto);//계좌금액을 감소시키는메소드
	public void minus_count(OrderDto dto); // 상품재고를 감소시키는 메소드
	public List<OrderDto> order_list(String id); // 주문했던 상품목록을 볼수있는 메소드
	
}
