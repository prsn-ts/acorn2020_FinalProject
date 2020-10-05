package com.sinbal.spring.product.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinbal.spring.product.dto.ProductReviewDto;

@Repository
public class ProductReviewDaoImpl implements ProductReviewDao{
	@Autowired
	private SqlSession session;
	//댓글 목록 얻어오는 메소드
	@Override
	public List<ProductReviewDto> getList(ProductReviewDto dto) {
		
		return session.selectList("productreview.getList", dto);
	}

	@Override
	public void delete(int num) {
		session.update("productreview.delete", num);
	}

	@Override
	public void insert(ProductReviewDto dto) {
		session.insert("productreview.insert", dto);
	}

	@Override
	public int getSequence() {
		
		return session.selectOne("productreview.getSequence");
	}

	@Override
	public void update(ProductReviewDto dto) {
		session.update("productreview.update", dto);
	}

	@Override
	public ProductReviewDto getData(int num) {
		
		return session.selectOne("productreview.getData", num);
	}
	//전체 댓글의 개수를 가져오는 메소드
	@Override
	public int getCount(int ref_group) {
		
		return session.selectOne("productreview.getCount", ref_group);
	}
}
