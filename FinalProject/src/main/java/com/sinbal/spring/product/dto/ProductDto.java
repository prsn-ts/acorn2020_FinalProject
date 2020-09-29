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
	private String[] sizearr;
	private String[] countarr;
	//디폴트 생성자 
	public ProductDto() {}
	public ProductDto(int num, String productname, String kind, String content, int price, String regdate,
			String profile, String profile2, int sbsize, int sbcount, String[] sizearr, String[] countarr) {
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
	public String[] getSizearr() {
		return sizearr;
	}
	public void setSizearr(String[] sizearr) {
		this.sizearr = sizearr;
	}
	public String[] getCountarr() {
		return countarr;
	}
	public void setCountarr(String[] countarr) {
		this.countarr = countarr;
	}



}
