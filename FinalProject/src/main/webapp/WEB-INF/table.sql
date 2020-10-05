-- 자신이 생성한 테이블과 시퀀스  추가하기

--주문테이블
CREATE TABLE sborder
(
    num            NUMBER           NOT NULL, 
    productnum     NUMBER           NOT NULL, 
    id             VARCHAR2(100)    NOT NULL, 
    name           VARCHAR2(100)    NOT NULL, 
    addr           VARCHAR2(100)    NOT NULL, 
    phone_num      VARCHAR2(100)    NOT NULL, 
    sendrequest    VARCHAR2(100)    NULL, 
    totalprice     NUMBER           NOT NULL, 
    orderdate      DATE             NOT NULL, 
    sboption       CLOB             NOT NULL, 
    CONSTRAINT SBORDER_PK PRIMARY KEY (num)
);

주문테이블 시퀀스

CREATE SEQUENCE sborder_seq;

--계좌테이블
CREATE TABLE sblogin_account
(
    id       VARCHAR2(100)    NOT NULL, 
    money    NUMBER           NOT NULL, 
    point    NUMBER           NOT NULL
);

ALTER TABLE sblogin_account
    ADD CONSTRAINT FK_sblogin_account_id_sblogin_ FOREIGN KEY (id)
        REFERENCES sblogin (id) on delete cascade;

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

CREATE TABLE sbproduct
(
    num            NUMBER            NOT NULL, 
    productName    VARCHAR2(100)     NOT NULL, 
    kind           VARCHAR2(100)     NOT NULL, 
    content        CLOB              NULL, 
    price          NUMBER            NOT NULL, 
    regdate        DATE              NOT NULL, 
    profile        NVARCHAR2(100)    NULL, 
    profile2       NVARCHAR2(100)    NULL, 
    CONSTRAINT SBPRODUCT_PK PRIMARY KEY (num)
);
--시퀀스 생성
CREATE SEQUENCE sbproduct_seq;
--productname 에 유니크키 설정
alter table sbproduct add constraint primarykey22 unique (productname);
--상품 추가정보 테이블
CREATE TABLE sbproduct_sub
(
    sbsize     NUMBER    NOT NULL, 
    sbcount    NUMBER    NOT NULL, 
    num        NUMBER    NOT NULL
);
--상품 추가정보 FOREIGN KEY 설정
ALTER TABLE sbproduct_sub
    ADD CONSTRAINT FK_sbproduct_sub_num_sbproduct FOREIGN KEY (num)
        REFERENCES sbproduct (num) on delete cascade;
        

