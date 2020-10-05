package com.sinbal.spring.notice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.notice.dto.NoticeDto;
import com.sinbal.spring.notice.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/notice/list")
	public ModelAndView getList(HttpServletRequest request, 
			ModelAndView mView) {
		noticeService.getList(request);
		mView.setViewName("notice/list");
		return mView;
	}
	
	@RequestMapping("/notice/detail")
	public ModelAndView detail(HttpServletRequest request,
			ModelAndView mView) {
		noticeService.getDetail(request);
		mView.setViewName("notice/detail");
		return mView;
	}
	
	@RequestMapping("/notice/private/insertform")
	public ModelAndView insertForm(ModelAndView mView) {
		
		mView.setViewName("notice/insertform");
		return mView;
	}
	
	@RequestMapping(value = "/notice/private/insert", method=RequestMethod.POST)
	public ModelAndView insert(NoticeDto dto, ModelAndView mView, HttpSession session) {
		//dto 에 글 작성자 담기 
		String id=(String)session.getAttribute("id");
		dto.setWriter(id);
		noticeService.saveContent(dto);
		mView.setViewName("notice/insert");
		return mView;
	}
	
	@RequestMapping("/notice/private/updateform")
	public ModelAndView updateform(HttpServletRequest request,
			ModelAndView mView) {
		noticeService.getDetail(request);
		mView.setViewName("notice/updateform");
		return mView;
	}
	@RequestMapping(value="/notice/private/update", method=RequestMethod.POST)
	public ModelAndView update(NoticeDto dto, ModelAndView mView) {
		noticeService.updateContent(dto);
		mView.setViewName("notice/update");
		return mView;
	}
	@RequestMapping("/notice/private/delete")
	public ModelAndView delete(int num, HttpServletRequest request,
			ModelAndView mView) {
		noticeService.deleteContent(num, request);
		mView.setViewName("redirect:/notice/list.do");
		return mView;
	}
	
}
