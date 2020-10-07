package com.sinbal.spring.shop.dto;

public class OrderDto {
	private int num; //주문번호
	private int productnum; //상품번호
	private String id; //주문자id
	private String name; //받는사람
	private String zipNo;  //우편번호
	private String roadAddrPart1; //도로명주소
	private String addrDetail; //상세주소
	private String addr; //우편번호 +도로명주소+상세주소  합한 주소
	private String phone_num; //연락처
	private String sendrequest; //배송시 요청사항
	private int sbsize;
	private int sbcount;
	private int[] sizearr; //사이즈를 받아낼 배열
	private int[] countarr; //수량을 받아낼 배열
	private int[] pricearr; //가격을 받아낼 배열
	private int totalPrice; //총 가격
	private String orderdate; //등록일자
	private String sboption;// 신발 사이즈 개수 전부더해주는필드
	private String productname; //상품명을 받을필드
	private String profile; //상품 프로필을 받을 필드
	
	public OrderDto() {}

	public OrderDto(int num, int productnum, String id, String name, String zipNo, String roadAddrPart1,
			String addrDetail, String addr, String phone_num, String sendrequest, int sbsize, int sbcount,
			int[] sizearr, int[] countarr, int[] pricearr, int totalPrice, String orderdate, String sboption,
			String productname, String profile) {
		super();
		this.num = num;
		this.productnum = productnum;
		this.id = id;
		this.name = name;
		this.zipNo = zipNo;
		this.roadAddrPart1 = roadAddrPart1;
		this.addrDetail = addrDetail;
		this.addr = addr;
		this.phone_num = phone_num;
		this.sendrequest = sendrequest;
		this.sbsize = sbsize;
		this.sbcount = sbcount;
		this.sizearr = sizearr;
		this.countarr = countarr;
		this.pricearr = pricearr;
		this.totalPrice = totalPrice;
		this.orderdate = orderdate;
		this.sboption = sboption;
		this.productname = productname;
		this.profile = profile;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getProductnum() {
		return productnum;
	}

	public void setProductnum(int productnum) {
		this.productnum = productnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipNo() {
		return zipNo;
	}

	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}

	public String getRoadAddrPart1() {
		return roadAddrPart1;
	}

	public void setRoadAddrPart1(String roadAddrPart1) {
		this.roadAddrPart1 = roadAddrPart1;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getSendrequest() {
		return sendrequest;
	}

	public void setSendrequest(String sendrequest) {
		this.sendrequest = sendrequest;
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

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getSboption() {
		return sboption;
	}

	public void setSboption(String sboption) {
		this.sboption = sboption;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}




	
}
