package com.sinbal.spring.login.dto;

public class LoginDto {
	private String id;
	private String pwd;
	private String email; // @(골뱅이)전까지의 글자
	private String email_second; // @(골뱅이) 후의 클자
	private String email_all; //이메일 앞쪽과 뒤쪽을 합친 것을 저장할 필드
	private String phone_num;
	private String buy_consent; //구매 이용약관 동의
	private String private_consent; //개인정보 수집 동의
	private String trust_consent; //개인정보 처리위탁동의
	private String profile; //프로필 이미지 경로를 저장할 필드
	private String regdate;
	
	public LoginDto() {}

	public LoginDto(String id, String pwd, String email, String email_second, String email_all, String phone_num,
			String buy_consent, String private_consent, String trust_consent, String profile, String regdate) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.email = email;
		this.email_second = email_second;
		this.email_all = email_all;
		this.phone_num = phone_num;
		this.buy_consent = buy_consent;
		this.private_consent = private_consent;
		this.trust_consent = trust_consent;
		this.profile = profile;
		this.regdate = regdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_second() {
		return email_second;
	}

	public void setEmail_second(String email_second) {
		this.email_second = email_second;
	}

	public String getEmail_all() {
		return email_all;
	}

	public void setEmail_all(String email_all) {
		this.email_all = email_all;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getBuy_consent() {
		return buy_consent;
	}

	public void setBuy_consent(String buy_consent) {
		this.buy_consent = buy_consent;
	}

	public String getPrivate_consent() {
		return private_consent;
	}

	public void setPrivate_consent(String private_consent) {
		this.private_consent = private_consent;
	}

	public String getTrust_consent() {
		return trust_consent;
	}

	public void setTrust_consent(String trust_consent) {
		this.trust_consent = trust_consent;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}	