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
	//디폴트 생성자 
	public ProductDto() {}
	public ProductDto(int num, String productname, String kind, String content, int price, String regdate,
			String profile, String profile2, int sbsize, int sbcount, int[] sizearr, int[] countarr, int[] pricearr,
			int totalPrice) {
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
}
