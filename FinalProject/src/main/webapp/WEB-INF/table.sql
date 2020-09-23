-- 자신이 생성한 테이블과 시퀀스  추가하기

-- 공지 사항 테이블
CREATE TABLE sbnotice(
	num NUMBER PRIMARY KEY,
	writer VARCHAR2(100) NOT NULL,
	title VARCHAR2(100) NOT NULL,
	content CLOB,
	viewCount NUMBER, --조회수
	regdate DATE
);

CREATE SEQUENCE sbnotice_seq;

-- 사용자(회원) 정보를 저장할 테이블
CREATE TABLE sblogin(
	id VARCHAR2(100) PRIMARY KEY,
	pwd VARCHAR2(100) NOT NULL,
	email VARCHAR2(100) NOT NULL,
	phone_num VARCHAR2(100) NOT NULL,
	buy_consent VARCHAR2(50) NOT NULL, -- 구매 이용약관 동의
	private_consent VARCHAR2(50) NOT NULL, -- 개인정보 수집 동의
	trust_consent VARCHAR2(50) NOT NULL, -- 개인정보 처리위탁동의
	profile VARCHAR2(100), -- 프로필 이미지 경로를 저장할 칼럼
	regdate DATE -- 가입일 관련

CREATE TABLE sbproduct(
	num NUMBER PRIMARY KEY,
	kind VARCHAR2(100),
	productName VARCHAR2(100),
	content CLOB,
	quantity NUMBER,
	price NUMBER,
	regdate DATE
);

CREATE SEQUENCE sbproduct_seq;

INSERT INTO sbproduct
(num, kind, productname, content, quantity, price, regdate)
VALUES(sbproduct_seq.NEXTVAL, #{kind }, #{productname }, #{content }, #{quantity }, #{price }, SYSDATE);
