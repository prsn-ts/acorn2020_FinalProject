package com.sinbal.spring.product.dao;

import java.util.List;

import com.sinbal.spring.product.dto.ProductReviewDto;

public interface ProductReviewDao {
	//댓글 목록 얻어오기
	public List<ProductReviewDto> getList(ProductReviewDto dto);
	//댓글 삭제
	public void delete(int num);
	//댓글 추가
	public void insert(ProductReviewDto dto);
	//추가할 댓글의 글번호를 리턴하는 메소드
	public int getSequence(); //insert하는 시점에 원글의 대한 댓글(대댓글아님)에 num 칼럼과, comment_group 칼럼에 동일한 값을 집어넣기위함.(생성한 시퀀스 값을 가져와서 쓴다.)
	//댓글 수정
	public void update(ProductReviewDto dto);
	//댓글 하나의 정보를 리턴하는 메소드
	public ProductReviewDto getData(int num);
	//전체 댓글의 개수를 리턴하는 추상 메소드
	public int getCount(int ref_group);
}
