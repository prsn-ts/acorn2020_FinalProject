package com.sinbal.spring.notice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinbal.spring.notice.dto.NoticeDto;

public interface NoticeService {
	public void getList(HttpServletRequest request);
	public void getDetail(HttpServletRequest request);
	public void saveContent(NoticeDto dto);
	public void updateContent(NoticeDto dto);
	public void deleteContent(int num, HttpServletRequest request);

}
