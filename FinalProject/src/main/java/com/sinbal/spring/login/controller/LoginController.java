package com.sinbal.spring.login.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dto.LoginDto;
import com.sinbal.spring.login.service.LoginService;
import com.sinbal.spring.product.dto.ProductDto;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	//로그인 폼 요청 처리
	@RequestMapping("/login/login_form")
	public ModelAndView loginform(HttpServletRequest request, ModelAndView mView) {
		// url 파라미터가 넘어오는지 읽어와 보기 
		String url=request.getParameter("url");
		if(url==null){//목적지 정보가 없다면
			String cPath=request.getContextPath();
			url=cPath+"/home.do"; //로그인후 인덱스 페이지로 가도록 하기 위해 
		}
		//쿠키에 저장된 정보를 가져오기 위함.
		mView = loginService.getCookie(request, mView);
		//url 파라미터가 있는 경우 request 에 담는다.
		request.setAttribute("url", url);
		//view 페이지로 이동한다.
		mView.setViewName("login/login_form");
		return mView;
	}
	
	//로그인 요청 처리
	@RequestMapping("/login/login")
	public ModelAndView login(LoginDto dto, HttpServletRequest request,
			HttpServletResponse response, ModelAndView mView) {
		//로그인 후 가야하는 목적지 정보
		String url = request.getParameter("url");
		//목적지 정보도 미리 인코딩 해 놓는다.
		String encodedUrl = URLEncoder.encode(url);

		//로그인 처리 후 가야할 url 정보를 ModelAndView 객체에 담는다.
		mView.addObject("url", url);
		mView.addObject("encodedUrl", encodedUrl);
		//로그인 처리하는 메소드
		loginService.loginProcess(dto, request, response, mView);
		
		//로그인 처리 후 view 페이지로 이동한다.
		mView.setViewName("login/login");
		return mView;
	}
	
	//회원 가입 폼 요청 처리
	@RequestMapping("/login/signup_form")
	public ModelAndView signupForm(ModelAndView mView) {
		
		mView.setViewName("login/signup_form");
		return mView;
	}
	
	//회원 가입 요청 처리
	@RequestMapping("/login/signup")
	public ModelAndView signup(LoginDto dto, ModelAndView mView) {
		//loginService 객체를 이용해서 사용자 정보를 추가한다.
		loginService.addUser(dto, mView);
		//view 페이지로 이동해서 응답하기.
		mView.setViewName("login/signup");
		return mView;
	}
	
	//로그아웃 폼 요청처리
	@RequestMapping("/login/logout.do")
	public ModelAndView logout(ModelAndView mView,HttpServletRequest request) {
		
		request.getSession().invalidate();
		mView.setViewName("redirect:/home.do");
		return mView;
	}
	
	//아이디가 존재하는 지 여부에 대한 요청 처리
	@RequestMapping("/login/checkid")
	@ResponseBody
	public Map<String, Object> checkid(@RequestParam String inputId){
		//service 가 리턴해주는 Map 객체를 리턴한다.
		return loginService.isExistId(inputId);
	}
	
	//회원 정보 보기 요청 처리
	@RequestMapping("/login/private/info.do")
	public ModelAndView info(HttpServletRequest request, ModelAndView mView) {
		//로그인된 정보를 가져온다.
		loginService.getLoginInfo(request, mView);
		//ModelAndView 객체에 view 페이지 정보를 담는다.
		mView.setViewName("login/private/info");
		//view 페이지로 forward 이동한다.
		return mView;
	}
	
	//입력한 비밀번호가 기존 비밀번호와 맞는 지 체크하는 요청 처리
	@RequestMapping("/login/private/pwd_check.do")
	@ResponseBody
	public Map<String, Object> pwdCheck(@RequestParam String pwd, HttpSession session){
		//service 가 리턴해주는 Map 객체를 리턴한다.
		return loginService.isExistPwd(pwd, session);
	}
	
	//비밀번호 수정 폼 요청 처리
	@RequestMapping("/login/private/pwd_updateform")
	public String pwdUpdate() {
		
		//view 페이지로 이동하기.
		return "login/private/pwd_updateform";
	}
	
	//비밀번호 수정 요청 처리
	@RequestMapping("/login/private/pwd_update")
	public ModelAndView pwdUpdate(ModelAndView mView,
			LoginDto dto, HttpServletRequest request) {
		//loginService 객체를 이용해서 비밀번호를 수정한다.
		loginService.updateUserPwd(dto, request, mView);
		
		//view 페이지 정보를 담고
		mView.setViewName("login/private/pwd_update");
		//이동한다.
		return mView;
	}
	
	//회원정보 수정 폼 요청 처리
	@RequestMapping("/login/private/update_form.do")
	public ModelAndView updateForm(HttpServletRequest request,
			ModelAndView mView) {
		//로그인된 정보를 가져온다.
		loginService.getLoginInfo(request, mView);
		//ModelAndView 객체에 view 페이지 정보를 담는다.
		mView.setViewName("login/private/update_form");
		//view 페이지로 이동한다.
		return mView;
	}
	
	//회원정보 수정 요청 처리
	@RequestMapping("/login/private/update.do")
	public ModelAndView updateForm(LoginDto dto, HttpServletRequest request,
			ModelAndView mView) {
		//로그인된 정보를 가져온다.
		loginService.updateUser(dto, request, mView);
		//ModelAndView 객체에 view 페이지 정보를 담는다.
		mView.setViewName("login/private/update");
		//view 페이지로 이동한다.
		return mView;
	}
	
	//회원 탈퇴 요청 처리
	@RequestMapping("/login/private/delete.do")
	public ModelAndView delete(HttpServletRequest request,
			ModelAndView mView) {
		//아이디 정보를 읽어내서 mView에 대입.
		mView.addObject("id", request.getSession().getAttribute("id"));
		//서비스를 이용해서 사용자 정보를 삭제하고
		loginService.deleteUser(request.getSession());
		//view 페이지로 forward 이동해서 응답
		mView.setViewName("login/private/delete");
		return mView;
	}
	
	//Ajax 를통한 포인트 중전 
	@RequestMapping("/login/private/addMoney.do")
	@ResponseBody
	public Map<String, Object> addMey(HttpServletRequest request){
		loginService.addMoney(request);
		return loginService.getaccount(request);
	}
}
