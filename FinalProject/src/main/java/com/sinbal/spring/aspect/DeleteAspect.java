package com.sinbal.spring.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.sinbal.spring.exception.NotDeleteException;
import com.sinbal.spring.notice.dao.NoticeDao;
import com.sinbal.spring.notice.dto.NoticeDto;

@Aspect
@Component
public class DeleteAspect {
	@Autowired
	private NoticeDao noticeDao;
	
	@Around("execution(void com.sinbal.spring.notice.service.*.deleteContent(..))")
	public void checkCafeDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		//메소드에 전달된 인자값을 저장할 지역 변수 
		int num=0;
		HttpServletRequest request=null;
		
		Object[] args=joinPoint.getArgs();
		for(Object tmp:args) {
			if(tmp instanceof Integer) { //전달된 인자중에서 정수(int) 찾기
				num=(int)tmp;
			}
			if(tmp instanceof HttpServletRequest) {//HttpServletRequest 찾기
				request=(HttpServletRequest)tmp;
			}
		}
		//삭제할 글 정보를 얻어온다.
		NoticeDto noticeDto=noticeDao.getData(num);
		//세션에 저장된 아이디를 읽어온다(로그인된 아이디)
		String id=(String)request.getSession().getAttribute("id");
		if(!id.equals(noticeDto.getWriter())) {
			throw new NotDeleteException("삭제 권한이 없습니다.");
		}
		//메소드 정상 수행하기 
		joinPoint.proceed();		
	}
}