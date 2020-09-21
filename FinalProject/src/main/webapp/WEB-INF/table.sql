-- 자신이 생성한 테이블과 시퀀스  추가하기
CREATE TABLE sbnotice(
	num NUMBER PRIMARY KEY,
	writer VARCHAR2(100) NOT NULL,
	title VARCHAR2(100) NOT NULL,
	content CLOB,
	viewCount NUMBER, --조회수
	regdate DATE
);

CREATE SEQUENCE sbnotice_seq;
