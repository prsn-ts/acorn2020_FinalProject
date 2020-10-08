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

--주문테이블 시퀀스

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
	
-- 댓글(상품평) 저장하는 테이블
CREATE TABLE sbproduct_review(
	num NUMBER PRIMARY KEY, -- 댓글의 글 번호
	writer VARCHAR2(100), -- 댓글 작성자의 아이디
	content VARCHAR2(500), -- 댓글 내용
	target_id VARCHAR2(100), -- 댓글의 대상자 아이디(누구에게 댓글을 달았는지 확인여부)
	ref_group NUMBER, -- 하나의 글(ex. 글 번호가 80번이라 가정하면)에서 파생된(작성된) 모든 댓글은 80번 글 번호에 관련된 댓글이다 라는 것을 그룹으로 묶어서 명시.(80번이 아닌 글들의 댓글을 가져오면 안되기 때문에 해당 글(여기선 80번)의 댓글을 가져오기위해서 그룹으로 관리하기 위함) 
	comment_group NUMBER, -- 하나의 댓글(ex. 글 번호가 12번이라 가정하면)에 대한 대댓글 들의 글 번호를 12번으로 지정해서 글에 달았던 최초 댓글과 그 아래 대댓글을 하나의 그룹으로 묶어서 같이 표현해야할 때 사용하기위해 사용하는 칼럼(그룹으로 관리하기 위함)
	deleted CHAR(3) DEFAULT 'no', -- 댓글이 삭제된지 여부(삭제된 경우 deleted 칼럼에 'yes' 문자열을 넣는다)
	regdate DATE
);
-- 댓글(상품평) 관련 시퀀스 
CREATE SEQUENCE sbproduct_review_seq;

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
        

