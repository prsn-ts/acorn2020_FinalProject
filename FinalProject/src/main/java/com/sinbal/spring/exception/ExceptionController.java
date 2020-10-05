package com.sinbal.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(NotDeleteException.class)
	public ModelAndView notDelete(NotDeleteException nde) {
		//해당 오류가 발생했을때 원하는 작업을 한후 
		
		//view page 로 forward 이동해서 예외 정보를 응답한다. 
		ModelAndView mView=new ModelAndView();
		//exception  이라는 키값으로 예외 객체를 담고 
		mView.addObject("exception", nde);
		// /WEB-INF/views/error/info.jsp 페이지로 forward 이동 
		mView.setViewName("error/info");
		return mView;
	}
}
