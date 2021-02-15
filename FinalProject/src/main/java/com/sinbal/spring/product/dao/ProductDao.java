package com.sinbal.spring.product.dao;

import java.util.List;
import java.util.Map;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dto.OrderDto;

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
	public List<ProductDto> getList(ProductDto dto);
	//상품한개의  데이터 가져오기
	public List<ProductDto> getData(int num);
	
	public void productupdate(ProductDto dto);
	public void productupdate_sub(ProductDto dto);
	
	//상품 정보를 가져오는 추상 메소드
	public ProductDto getData2(int num);
	//신발 사이즈, 신발 수량 정보를 가져오는 추상 메소드
	public List<ProductDto> getSubData(int num);
	//특정 사이즈의 재고 개수를 리턴하는 추상 메소드
	public ProductDto getStockData(int size, int num);
	//선택할 수 있는 신발 사이즈 항목의 개수를 리턴하는 추상 메소드
	public int getSizeData(int num);
	public List<ProductDto> productList(ProductDto dto);
	public List<ProductDto> homeList(ProductDto dto);
	public int getCount(ProductDto dto);
	//상품  인기 정보를 리스트를 가져오는 메소드
	
	public List<ProductDto> favoritelist();
	
	//장바구니에 상품을 저장하는 추상 메소드
	public void saveToBasket_230(ProductDto dto);
	public void saveToBasket_240(ProductDto dto);
	public void saveToBasket_250(ProductDto dto);
	public void saveToBasket_260(ProductDto dto);
	public void saveToBasket_270(ProductDto dto);
	public void saveToBasket_280(ProductDto dto);
	//특정 상품 번호에 맞는 리스트를 가져오기 위한 추상 메소드
	public List<ProductDto> select_230(ProductDto dto);
	public List<ProductDto> select_240(ProductDto dto);
	public List<ProductDto> select_250(ProductDto dto);
	public List<ProductDto> select_260(ProductDto dto);
	public List<ProductDto> select_270(ProductDto dto);
	public List<ProductDto> select_280(ProductDto dto);
	//특정 상품 번호에 맞는 리스트를 제거하기 위한 추상 메소드
	public List<ProductDto> delete_230(ProductDto dto);
	public List<ProductDto> delete_240(ProductDto dto);
	public List<ProductDto> delete_250(ProductDto dto);
	public List<ProductDto> delete_260(ProductDto dto);
	public List<ProductDto> delete_270(ProductDto dto);
	public List<ProductDto> delete_280(ProductDto dto);
	//장바구니 DB에 저장된 내용을 가져오는 추상 메소드
	public List<ProductDto> savedBasketInfo(ProductDto dto);
//	//장바구니 DB에 저장된 내용을 (Id 없이) 가져오는 추상 메소드
//	public List<ProductDto> shoppingBasketInfo_non_Id();
	//장바구니 관련 DB에 저장된 아이디 리스트를 검색하는 추상 메소드(현재 쿠키 아이디값과 DB에 저장된 아이디값이 같은 지 비교하기위한 검색)
	public List<String> selectedId();
	//현재 쿠키(아이디)값에 존재하지 않는 장바구니 관련 DB에 저장된 아이디를 삭제하는 추상 메소드
	public void selectedId_remove(String id);
	//장바구니 테이블을 정렬하기위한 추상 메소드
	public List<ProductDto> getAlignedBasket(ProductDto dto);
	//물품 삭제하는 X버튼을 누른 특정 상품의 특정 사이즈를 제외한 장바구니에 있는 것을 검색해오는 추상 메소드
	public int getIdentityNumBasket(ProductDto dto);
	//identitynum에 해당하는 물품을 삭제하는 추상 메소드
	public void deleteIdentityNumAtBasket(ProductDto dto);
	//선택했던 체크박스의 장바구니 관련 항목을 삭제하는 추상 메소드
	public void selectedCheckBoxItemRemove(ProductDto dto);
	//선택된 아이템 항목에 맞는 재고수량을 가져오는 추상 메소드
	public ProductDto getSelectedSbproductSub(ProductDto dto);
}
