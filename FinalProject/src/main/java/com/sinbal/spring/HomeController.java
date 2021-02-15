package com.sinbal.spring;




import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.service.ProductService;
import com.sinbal.spring.shop.dao.ShopDao;



@Controller
public class HomeController {
	
	@Autowired
	private ProductService productservice;
	

	

	@RequestMapping("/home.do")
	public ModelAndView home(HttpServletRequest request,
			ModelAndView mView) {
		productservice.likelist(mView);
		
		productservice.productList(request);
		mView.setViewName("home");
		return mView;
	}
	
	// info.do라는 요청이오면
	@RequestMapping("/info.do")
	public String info() {
		
		//   info.jsp페이지를 보여주겠다.
		return "info";
	}
	

}