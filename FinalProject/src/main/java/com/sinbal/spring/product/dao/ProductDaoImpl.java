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
	public List<ProductDto> homeList(ProductDto dto) {
		return session.selectList("product.homeList",dto);
	}
	
	@Override
	public int getCount(ProductDto dto) {

		return session.selectOne("product.getCount", dto);
	}
	@Override
	public List<ProductDto> favoritelist() {
		return session.selectList("product.favoritelist");
	}
	
	@Override
	//장바구니에 상품을 저장하는 메소드
	public void saveToBasket_230(ProductDto dto) {
		session.insert("product.saveToBasket_230", dto);
	}
	@Override
	public void saveToBasket_240(ProductDto dto) {
		session.insert("product.saveToBasket_240", dto);
	}
	@Override
	public void saveToBasket_250(ProductDto dto) {
		session.insert("product.saveToBasket_250", dto);
	}
	@Override
	public void saveToBasket_260(ProductDto dto) {
		session.insert("product.saveToBasket_260", dto);
	}
	@Override
	public void saveToBasket_270(ProductDto dto) {
		session.insert("product.saveToBasket_270", dto);
	}

	@Override
	public void saveToBasket_280(ProductDto dto) {
		session.insert("product.saveToBasket_280", dto);
	}
	//장바구니 DB에 저장된 내용을 가져오는 메소드
	@Override
	public List<ProductDto> savedBasketInfo(ProductDto dto) {
		List<ProductDto> basket_list = session.selectList("product.savedBasketInfo", dto);
		return basket_list;
	}
	//특정 상품 번호에 맞는 리스트를 가져오기 위한 추상 메소드
	@Override
	public List<ProductDto> select_230(ProductDto dto) {
		return session.selectList("product.select_230", dto);
	}
	@Override
	public List<ProductDto> select_240(ProductDto dto) {
		return session.selectList("product.select_240", dto);
	}
	@Override
	public List<ProductDto> select_250(ProductDto dto) {
		return session.selectList("product.select_250", dto);
	}
	@Override
	public List<ProductDto> select_260(ProductDto dto) {
		return session.selectList("product.select_260", dto);
	}
	@Override
	public List<ProductDto> select_270(ProductDto dto) {
		return session.selectList("product.select_270", dto);
	}
	@Override
	public List<ProductDto> select_280(ProductDto dto) {
		return session.selectList("product.select_280", dto);
	}
	//특정 상품 번호에 맞는 리스트를 제거하기 위한 추상 메소드
	@Override
	public List<ProductDto> delete_230(ProductDto dto) {
		return session.selectList("product.delete_230", dto);
	}@Override
	public List<ProductDto> delete_240(ProductDto dto) {
		return session.selectList("product.delete_240", dto);
	}@Override
	public List<ProductDto> delete_250(ProductDto dto) {
		return session.selectList("product.delete_250", dto);
	}@Override
	public List<ProductDto> delete_260(ProductDto dto) {
		return session.selectList("product.delete_260", dto);
	}@Override
	public List<ProductDto> delete_270(ProductDto dto) {
		return session.selectList("product.delete_270", dto);
	}@Override
	public List<ProductDto> delete_280(ProductDto dto) {
		return session.selectList("product.delete_280", dto);
	}
	//장바구니 관련 DB에 저장된 아이디 리스트를 검색하는 추상 메소드(현재 쿠키 아이디값과 DB에 저장된 아이디값이 같은 지 비교하기위한 검색)
	@Override
	public List<String> selectedId() {
		return session.selectList("product.selectedId");
	}
	//현재 쿠키(아이디)값에 존재하지 않는 장바구니 관련 DB에 저장된 아이디를 삭제하는 추상 메소드
	@Override
	public void selectedId_remove(String id) {
		session.delete("product.selectedId_remove", id);
	}
//	//장바구니 DB에 저장된 내용을 (Id 없이) 가져오는 메소드
//	@Override
//	public List<ProductDto> shoppingBasketInfo_non_Id() {
//		return session.selectList("product.selectedAllBasket");
//	}
	//장바구니 테이블을 정렬하기위한 추상 메소드
	@Override
	public List<ProductDto> getAlignedBasket(ProductDto dto) {
		return session.selectList("product.getAlignedBasket", dto);
	}
	//물품 삭제하는 X버튼을 누른 특정 상품의 특정 사이즈의 identitynum(table의 칼럼)을 검색해오는 메소드
	@Override
	public int getIdentityNumBasket(ProductDto dto) {
		return session.selectOne("product.getIdentityNumBasket", dto);
	}
	//identitynum에 해당하는 물품을 삭제하는 메소드
	@Override
	public void deleteIdentityNumAtBasket(ProductDto dto) {
		session.delete("product.deleteIdentityNumAtBasket", dto);
	}
	//선택했던 체크박스의 장바구니 관련 항목을 삭제하는 메소드
	@Override
	public void selectedCheckBoxItemRemove(ProductDto dto) {
		session.delete("product.selectedCheckBoxItemRemove", dto);
	}
	//선택된 아이템 항목에 맞는 재고수량을 가져오는 메소드
	@Override
	public ProductDto getSelectedSbproductSub(ProductDto dto) {
		return session.selectOne("product.getSelectedSbproductSub", dto);
	}
}

