package com.sinbal.spring.notice.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinbal.spring.notice.dao.NoticeDao;
import com.sinbal.spring.notice.dto.NoticeDto;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
	//한 페이지에 나타낼 row 의 갯수
	final int PAGE_ROW_COUNT=10;
	//하단 디스플레이 페이지 갯수
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public void getList(HttpServletRequest request) {
		
		int pageNum=1;
		
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){
			
			pageNum=Integer.parseInt(strPageNum);
		}
		
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		
		int endRowNum=pageNum*PAGE_ROW_COUNT;

		String keyword=request.getParameter("keyword"); //검색 키워드
		String condition=request.getParameter("condition"); //검색 조건
		if(keyword==null){
			keyword="";  
			condition="";
		}
		
		String encodedK=URLEncoder.encode(keyword);
		
		NoticeDto dto=new NoticeDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){ 
			if(condition.equals("title_content")){
				
				dto.setTitle(keyword);
				dto.setContent(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
		
		List<NoticeDto> list=noticeDao.getList(dto);
		
		int totalRow=noticeDao.getCount(dto);
		
		
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount;  
		}
		
		request.setAttribute("list", list);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);				
	}

	@Override
	public void getDetail(HttpServletRequest request) {
		//파라미터로 전달되는 글번호 
		int num=Integer.parseInt(request.getParameter("num"));
		String keyword=request.getParameter("keyword"); //검색 키워드
		String condition=request.getParameter("condition"); //검색 조건
		if(keyword==null){//전달된 키워드가 없다면 
			keyword=""; //빈 문자열을 넣어준다. 
			condition="";
		}
		//인코딩된 키워드를 미리 만들어 둔다. 
		String encodedK=URLEncoder.encode(keyword);
				
		//글번호와 검색 키워드를 담을 NoticeDto 객체 생성
		NoticeDto dto=new NoticeDto();
		dto.setNum(num);//글번호 담기 
				
		if(!keyword.equals("")){ //만일 키워드가 넘어온다면 
			if(condition.equals("title_content")){
				 
				dto.setTitle(keyword);
				dto.setContent(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
		//자세히 보여줄 글 정보 
		NoticeDto resultDto=noticeDao.getData(dto);
				
		//view 페이지에서 필요한 내용 HttpServletRequest 에 담기
		request.setAttribute("dto", resultDto);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);
			
		//글 조회수 올리기
		noticeDao.addViewCount(num);
				
	}
	@Override
	public void saveContent(NoticeDto dto) {
		
		noticeDao.insert(dto);
		
	}

	@Override
	public void updateContent(NoticeDto dto) {
		
		noticeDao.update(dto);
		
	}

	@Override
	public void deleteContent(int num, HttpServletRequest request) {
		
		noticeDao.delete(num);
		
	}

}
