package com.sinbal.spring.product.dto;

public class ProductDto {
	private int num;
	private String kind;
	private String productname;
	private String content;
	private float quantity;
	private float price;
	private String regdate;
	//디폴트 생성자 
	public ProductDto() {}
	public ProductDto(int num, String kind, String productname, String content, float quantity, float price,
			String regdate) {
		super();
		this.num = num;
		this.kind = kind;
		this.productname = productname;
		this.content = content;
		this.quantity = quantity;
		this.price = price;
		this.regdate = regdate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	
	
}
