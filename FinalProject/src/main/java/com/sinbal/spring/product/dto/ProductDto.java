package com.sinbal.spring.product.dto;

import org.springframework.stereotype.Component;

@Component
public class ProductDto {
	private String id;
	private int num;
	private int subNum;
	private String productname;
	private String kind;
	private String content;
	private int price;
	private String regdate;
	private String profile;
	private String profile2;
	private int sbsize;
	private int sbcount;
	private int[] sizearr; //사이즈를 받아낼 배열
	private int[] countarr; //수량을 받아낼 배열
	private int[] pricearr; //가격을 받아낼 배열
	private String[] selected_Size; //선택된 사이즈를 담은 배열(문자열 배열 타입, 장바구니 페이지 관련)
	private int totalPrice; //총 가격
	private int totalQuantity; //총 수량(장바구니 페이지 관련)
	private int startRowNum;
	private int endRowNum;
	private String search;
	private String arr;
	private String kindSelect;
	private String keyword;
	private int buycount;
	
	//장바구니 관련 필드들
	private int identityNum; //장바구니 관련 메소드의 각 행들을 구분하기위한 프라이머리 키 필드
	private String oneSize; //특정 한 사이즈를 구해 mapper에서 사용할 필드
	private int initPrice;
	private String selectedSize;
	private int selectedQuantity;
	private int selectedPrice;
	private int product_price_230;
	private int product_price_240;
	private int product_price_250;
	private int product_price_260;
	private int product_price_270;
	private int product_price_280;
	private int quantity_230;
	private int quantity_240;
	private int quantity_250;
	private int quantity_260;
	private int quantity_270;
	private int quantity_280;
	private String saveTime;
	private String detail_basket_button;
	private String Xid; //X표시를 누른 버튼의 아이디를 저장할 필드
	private String basket_list_check; //동적으로 생성된 체크박스에서 사용할 필드
	private int basketIndex; //장바구니 항목의 순서를 저장한 배열의 인덱스
	private int[] checkedItem; //선택된 아이템 항목들을 삭제하기위한 값을 담은 int배열
	private int startRnum; //선택된 아이템 항목들을 삭제하기위한 시작값
	private int endRnum; //선택된 아이템 항목들을 삭제하기위한 끝값
	
	//디폴트 생성자 
	public ProductDto() {}

	public ProductDto(String id, int num, int subNum, String productname, String kind, String content, int price,
			String regdate, String profile, String profile2, int sbsize, int sbcount, int[] sizearr, int[] countarr,
			int[] pricearr, String[] selected_Size, int totalPrice, int totalQuantity, int startRowNum, int endRowNum,
			String search, String arr, String kindSelect, String keyword, int buycount, int identityNum, String oneSize,
			int initPrice, String selectedSize, int selectedQuantity, int selectedPrice, int product_price_230,
			int product_price_240, int product_price_250, int product_price_260, int product_price_270,
			int product_price_280, int quantity_230, int quantity_240, int quantity_250, int quantity_260,
			int quantity_270, int quantity_280, String saveTime, String detail_basket_button, String xid,
			String basket_list_check, int basketIndex, int[] checkedItem, int startRnum, int endRnum) {
		super();
		this.id = id;
		this.num = num;
		this.subNum = subNum;
		this.productname = productname;
		this.kind = kind;
		this.content = content;
		this.price = price;
		this.regdate = regdate;
		this.profile = profile;
		this.profile2 = profile2;
		this.sbsize = sbsize;
		this.sbcount = sbcount;
		this.sizearr = sizearr;
		this.countarr = countarr;
		this.pricearr = pricearr;
		this.selected_Size = selected_Size;
		this.totalPrice = totalPrice;
		this.totalQuantity = totalQuantity;
		this.startRowNum = startRowNum;
		this.endRowNum = endRowNum;
		this.search = search;
		this.arr = arr;
		this.kindSelect = kindSelect;
		this.keyword = keyword;
		this.buycount = buycount;
		this.identityNum = identityNum;
		this.oneSize = oneSize;
		this.initPrice = initPrice;
		this.selectedSize = selectedSize;
		this.selectedQuantity = selectedQuantity;
		this.selectedPrice = selectedPrice;
		this.product_price_230 = product_price_230;
		this.product_price_240 = product_price_240;
		this.product_price_250 = product_price_250;
		this.product_price_260 = product_price_260;
		this.product_price_270 = product_price_270;
		this.product_price_280 = product_price_280;
		this.quantity_230 = quantity_230;
		this.quantity_240 = quantity_240;
		this.quantity_250 = quantity_250;
		this.quantity_260 = quantity_260;
		this.quantity_270 = quantity_270;
		this.quantity_280 = quantity_280;
		this.saveTime = saveTime;
		this.detail_basket_button = detail_basket_button;
		Xid = xid;
		this.basket_list_check = basket_list_check;
		this.basketIndex = basketIndex;
		this.checkedItem = checkedItem;
		this.startRnum = startRnum;
		this.endRnum = endRnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSubNum() {
		return subNum;
	}

	public void setSubNum(int subNum) {
		this.subNum = subNum;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getProfile2() {
		return profile2;
	}

	public void setProfile2(String profile2) {
		this.profile2 = profile2;
	}

	public int getSbsize() {
		return sbsize;
	}

	public void setSbsize(int sbsize) {
		this.sbsize = sbsize;
	}

	public int getSbcount() {
		return sbcount;
	}

	public void setSbcount(int sbcount) {
		this.sbcount = sbcount;
	}

	public int[] getSizearr() {
		return sizearr;
	}

	public void setSizearr(int[] sizearr) {
		this.sizearr = sizearr;
	}

	public int[] getCountarr() {
		return countarr;
	}

	public void setCountarr(int[] countarr) {
		this.countarr = countarr;
	}

	public int[] getPricearr() {
		return pricearr;
	}

	public void setPricearr(int[] pricearr) {
		this.pricearr = pricearr;
	}

	public String[] getSelected_Size() {
		return selected_Size;
	}

	public void setSelected_Size(String[] selected_Size) {
		this.selected_Size = selected_Size;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getArr() {
		return arr;
	}

	public void setArr(String arr) {
		this.arr = arr;
	}

	public String getKindSelect() {
		return kindSelect;
	}

	public void setKindSelect(String kindSelect) {
		this.kindSelect = kindSelect;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getBuycount() {
		return buycount;
	}

	public void setBuycount(int buycount) {
		this.buycount = buycount;
	}

	public int getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(int identityNum) {
		this.identityNum = identityNum;
	}

	public String getOneSize() {
		return oneSize;
	}

	public void setOneSize(String oneSize) {
		this.oneSize = oneSize;
	}

	public int getInitPrice() {
		return initPrice;
	}

	public void setInitPrice(int initPrice) {
		this.initPrice = initPrice;
	}

	public String getSelectedSize() {
		return selectedSize;
	}

	public void setSelectedSize(String selectedSize) {
		this.selectedSize = selectedSize;
	}

	public int getSelectedQuantity() {
		return selectedQuantity;
	}

	public void setSelectedQuantity(int selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}

	public int getSelectedPrice() {
		return selectedPrice;
	}

	public void setSelectedPrice(int selectedPrice) {
		this.selectedPrice = selectedPrice;
	}

	public int getProduct_price_230() {
		return product_price_230;
	}

	public void setProduct_price_230(int product_price_230) {
		this.product_price_230 = product_price_230;
	}

	public int getProduct_price_240() {
		return product_price_240;
	}

	public void setProduct_price_240(int product_price_240) {
		this.product_price_240 = product_price_240;
	}

	public int getProduct_price_250() {
		return product_price_250;
	}

	public void setProduct_price_250(int product_price_250) {
		this.product_price_250 = product_price_250;
	}

	public int getProduct_price_260() {
		return product_price_260;
	}

	public void setProduct_price_260(int product_price_260) {
		this.product_price_260 = product_price_260;
	}

	public int getProduct_price_270() {
		return product_price_270;
	}

	public void setProduct_price_270(int product_price_270) {
		this.product_price_270 = product_price_270;
	}

	public int getProduct_price_280() {
		return product_price_280;
	}

	public void setProduct_price_280(int product_price_280) {
		this.product_price_280 = product_price_280;
	}

	public int getQuantity_230() {
		return quantity_230;
	}

	public void setQuantity_230(int quantity_230) {
		this.quantity_230 = quantity_230;
	}

	public int getQuantity_240() {
		return quantity_240;
	}

	public void setQuantity_240(int quantity_240) {
		this.quantity_240 = quantity_240;
	}

	public int getQuantity_250() {
		return quantity_250;
	}

	public void setQuantity_250(int quantity_250) {
		this.quantity_250 = quantity_250;
	}

	public int getQuantity_260() {
		return quantity_260;
	}

	public void setQuantity_260(int quantity_260) {
		this.quantity_260 = quantity_260;
	}

	public int getQuantity_270() {
		return quantity_270;
	}

	public void setQuantity_270(int quantity_270) {
		this.quantity_270 = quantity_270;
	}

	public int getQuantity_280() {
		return quantity_280;
	}

	public void setQuantity_280(int quantity_280) {
		this.quantity_280 = quantity_280;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public String getDetail_basket_button() {
		return detail_basket_button;
	}

	public void setDetail_basket_button(String detail_basket_button) {
		this.detail_basket_button = detail_basket_button;
	}

	public String getXid() {
		return Xid;
	}

	public void setXid(String xid) {
		Xid = xid;
	}

	public String getBasket_list_check() {
		return basket_list_check;
	}

	public void setBasket_list_check(String basket_list_check) {
		this.basket_list_check = basket_list_check;
	}

	public int getBasketIndex() {
		return basketIndex;
	}

	public void setBasketIndex(int basketIndex) {
		this.basketIndex = basketIndex;
	}

	public int[] getCheckedItem() {
		return checkedItem;
	}

	public void setCheckedItem(int[] checkedItem) {
		this.checkedItem = checkedItem;
	}

	public int getStartRnum() {
		return startRnum;
	}

	public void setStartRnum(int startRnum) {
		this.startRnum = startRnum;
	}

	public int getEndRnum() {
		return endRnum;
	}

	public void setEndRnum(int endRnum) {
		this.endRnum = endRnum;
	}

	
		
}
