package com.sinbal.spring.product.dto;

public class ProductDto {
	private int num;
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
	private int totalPrice; //총 가격
	private int startRowNum;
	private int endRowNum;
	private String search;
	private String arr;
	private String kindSelect;
	private String keyword;
	
	//디폴트 생성자 
	public ProductDto() {}

	public ProductDto(int num, String productname, String kind, String content, int price, String regdate,
			String profile, String profile2, int sbsize, int sbcount, int[] sizearr, int[] countarr, int[] pricearr,
			int totalPrice, int startRowNum, int endRowNum, String search, String arr, String kindSelect,
			String keyword) {
		super();
		this.num = num;
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
		this.totalPrice = totalPrice;
		this.startRowNum = startRowNum;
		this.endRowNum = endRowNum;
		this.search = search;
		this.arr = arr;
		this.kindSelect = kindSelect;
		this.keyword = keyword;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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


}
