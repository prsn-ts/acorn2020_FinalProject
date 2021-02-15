package com.sinbal.spring.shop.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.service.LoginService;
import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.product.dto.ProductReviewDto;
import com.sinbal.spring.product.service.ProductService;
import com.sinbal.spring.shop.dto.OrderDto;

@Controller
public class ShopController {

	@Autowired
	private ProductService productservice;
	@Autowired
	private LoginService loginservice;
	
	//String[] 배열을 primitive type의 int array로 바꾸는 메소드
	public static int[] convertStrings(String[] strings)
	{
	    int[] ret = new int[strings.length];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = Integer.parseInt(strings[i]);
	    }
	    return ret;
	}

	//ArrayList 동적 배열을 primitive type의 int array로 바꾸는 메소드
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}

	@RequestMapping("/shop/shop.do")
	public ModelAndView productList(HttpServletRequest request, 
			ModelAndView mView){
		
		productservice.productList(request);
		//  views/shop/shop/.jsp
		mView.setViewName("shop/shop");
		return mView;
		
	}
	
	//상세보기 페이지 보기 요청 처리
	@RequestMapping("/shop/detail.do")
	public ModelAndView detail(ModelAndView mView, HttpServletRequest request) {
		
		productservice.getProductData(mView, request);
		//  views/shop/shop/.jsp
		mView.setViewName("shop/detail");
		return mView;
	}
	
	//재고 개수 리턴 요청 처리
	@RequestMapping("/shop/checkQuantity.do")
	@ResponseBody
	public Map<String, Object> checkQuantity(@RequestParam int size, @RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbcount", productservice.getStockData(size, num).getSbcount());
		return result;
	}
	
	//신발 사이즈 항목의 개수 리턴 요청 처리
	@RequestMapping("/shop/checkSize.do")
	@ResponseBody
	public Map<String, Object> checkSize(@RequestParam int size, @RequestParam int num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sbsize", productservice.getSizeData(num));
		result.put("sbsize_price", productservice.getSbsizePrice(size, num)); //선택한 신발 사이즈에 해당하는 가격 가져오기
		return result;
	}
	
	//구매하기 버튼을 클릭했을 때 구매창 보기 요청 처리
	@RequestMapping("/shop/private/buy_form.do")
	public ModelAndView buyForm(ProductDto dto,ModelAndView mView,HttpServletRequest request) {
		//로그인된 회원의 정보를 가져온다
		loginservice.getLoginInfo(request, mView);
		//로그인 필터에서 넘어오는 경우도 생각해서 처리하기위해 num 변수 선언.
		Map<String, Object> requestPostData = new HashMap<String, Object>();
		
		if(request.getParameter("num") == null) {
			requestPostData = (Map<String, Object>) request.getSession().getAttribute("requestPostData");
			//session에 저장된 requestPostData 객체를 통해 글번호(num)로 인자값을 준다. 
			productservice.getData(mView, Integer.parseInt(requestPostData.get("num").toString()));
			int[] price_arr = convertIntegers((List<Integer>) requestPostData.get("price_arr"));
			int[] quantity_arr = convertIntegers((List<Integer>) requestPostData.get("quantity_arr"));
			int[] select_arr = (int[])requestPostData.get("selectedSize");
			for(int i=0; i<price_arr.length; i++) {
				System.out.printf("price_arr[%d] : %d \n", i, price_arr[i]);
			}
			for(int i=0; i<quantity_arr.length; i++) {
				System.out.printf("quantity_arr[%d] : %d \n", i, quantity_arr[i]);
			}
			for(int i=0; i<select_arr.length; i++) {
				System.out.printf("select_arr[%d] : %d \n", i, select_arr[i]);
			}
			//dto에 수량, 가격, 사이즈, 총각겨, 상품번호 등의 정보를 입력한다.
			dto.setPricearr(price_arr);
			dto.setCountarr(quantity_arr);
			dto.setSizearr(select_arr);
			dto.setTotalPrice((int)requestPostData.get("totalPrice"));
			dto.setNum((int)requestPostData.get("num"));
		}else {
			productservice.getData(mView, dto.getNum());
		}	
		productservice.buy(mView, dto);
		mView.setViewName("shop/private/buy_form");
		return mView;
	}
	
	//장바구니 버튼을 클릭했을 때 장바구니 페이지 요청 처리
	@RequestMapping("/shop/shopping_basket.do")
	public ModelAndView shoppingBasket(ProductDto dto,ModelAndView mView,
			HttpServletRequest request, HttpServletResponse response, @RequestParam String cookie) {
		System.out.println("cookie : "+ cookie);
		System.out.println("dto.getDetail_basket_button() : "+dto.getDetail_basket_button());
		//장바구니 관련 쿠키 값 가져오기(쿠키가 현재 있는 지 없는 지 먼저 가져와본다.
		Cookie[] cookies = request.getCookies();
		String cookie_fin = cookie;
		System.out.println("cookie_fin : "+cookie_fin);
		if(cookies != null){
			for(int i=0; i<cookies.length; i++){
				if(cookies[i].getName().equals("guest")){
					cookie_fin = cookies[i].getValue();
					System.out.println("guest 쿠키 존재함");
					mView.addObject("guest_cookie", cookie_fin);
					break; //찾는 것과 같은게 있을 때는 원하는 쿠키값을 대입 후 반복문을 빠져나온다.
				}else {
					System.out.println("guest 쿠키 존재하지않음");
					mView.addObject("guest_cookie", null);
				}
			}
		}
		//상세페이지에 있는 장바구니 버튼을 눌렀을 때만 ok라는 값이 날라오는데 그것에 해당하는 지 여부
		//현재 브라우저에 저장된 쿠키가 있으면 그 쿠키를 활용해 DB에 데이터 저장
		if(dto.getDetail_basket_button()!=null && cookie_fin!=null) { 
			cookie = productservice.saveToBasket(request, dto, response, cookie_fin); //장바구니에 상품들을 저장하는 메소드
		}
		//상세페이지에 있는 장바구니 버튼을 눌렀을 때만 ok라는 값이 날라오는데 그것에 해당하는 지 여부
		//현재 브라우저에 저장된 쿠키가 없으면 없는 대로 진행.
		else if(dto.getDetail_basket_button()!=null && cookie_fin==null) {
			cookie = productservice.saveToBasket(request, dto, response, cookie); //장바구니에 상품들을 저장하는 메소드
		}
		System.out.println("cookie 결과 : "+cookie);
		//view에 보여주기 위한 정보들을 다루는 메소드
		productservice.shoppingBasketInfo(dto, request, mView, cookie);
		mView.setViewName("shop/shopping_basket");
		return mView;
	}
	
//	//장바구니 버튼을 클릭했을 때 장바구니 페이지 요청 처리
//	@RequestMapping("/shop/shopping_basket1.do")
//	public ModelAndView shoppingBasket1(HttpServletRequest request, ModelAndView mView, @RequestParam String cookie) {
//		System.out.println("shopping_basket1_cookie : "+cookie);
//		//장바구니 관련 쿠키 값 가져오기
//		Cookie[] cookies = request.getCookies();
//		String cookie_fin = cookie;
//		System.out.println("cookie_fin : "+cookie_fin);
//		if(cookies != null){
//			for(int i=0; i<cookies.length; i++){
//				if(cookies[i].getName().equals("guest")){
//					cookie_fin = cookies[i].getValue();
//					System.out.println("guest 쿠키 존재함");
//					mView.addObject("guest_cookie", cookie_fin);
//					break; //찾는 것과 같은게 있을 때는 원하는 쿠키값을 대입 후 반복문을 빠져나온다.
//				}else {
//					System.out.println("guest 쿠키 존재하지않음");
//					mView.addObject("guest_cookie", null);
//				}
//			}
//		}
//		//cookie_fin가 가지고있는 쿠키값이 null이든 존재하든 shoppingBasketInfo 메소드 실행
//		productservice.shoppingBasketInfo(request, mView, cookie_fin);
//		mView.setViewName("shop/shopping_basket");
//		return mView;
//	}
	
	//신발 사이즈, 수량, 가격의 데이터를 가져오는 요청 처리
	@RequestMapping(value="/shop/size_quantity_price_ajax.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> size_quantity_price_ajax(HttpServletRequest request, ModelAndView mView, 
			@RequestParam String id, ProductDto dto) {
		//정보들을 저장하고 리턴할 Map 객체 하나 생성.
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		//로그인 여부를 알 수 있는 변수
		String loginId = (String)request.getSession().getAttribute("id");
		System.out.println("loginId : "+loginId);
		System.out.println("id : "+id);
		System.out.println("dto.getXid() : "+dto.getXid());
		if((!id.equals("")) && (loginId == null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이니가 없을 때
			System.out.println("쿠키값이 있고 로그인 안했을 때");
			dto.setId(id); //dto에 id(쿠키값)을 세팅
			mapAjax = productservice.shoppingBasketInfo_Ajax(request, mView, dto);
		}if((!id.equals("")) && (loginId != null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값이 있고 로그인도 했을 때");
			dto.setId(loginId); //dto에 id(로그인한 아이디값)을 세팅
			mapAjax = productservice.shoppingBasketInfo_Ajax(request, mView, dto);
		}if((id.equals("")) && (loginId != null)) { //쿠키값이 널이 이거나 빈 문자열이면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값은 없고 로그인은 했을 때");
			dto.setId(loginId); //dto에 id(로그인한 아이디값)을 세팅
			mapAjax = productservice.shoppingBasketInfo_Ajax(request, mView, dto);
		}
		if((id.equals("")) && (loginId == null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값이 없고 로그인도 안했을 때");
		}
		//mapAjax.put("isValid", isValid); //유효성 여부를 map 객체에 저장.
		return mapAjax;
	}
	
	//선택된 아이템 항목들을 삭제하는 요청 처리
	@RequestMapping(value="/shop/selected_delete_ajax.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selected_delete_ajax(HttpServletRequest request, ModelAndView mView, 
			@RequestParam String id, ProductDto dto) {
		//정보들을 저장하고 리턴할 Map 객체 하나 생성.
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		//로그인 여부를 알 수 있는 변수
		String loginId = (String)request.getSession().getAttribute("id");
		System.out.println("loginId : "+loginId);
		System.out.println("id : "+id);
		System.out.println("dto.getXid() : "+dto.getXid());
		//직렬화된 쿼리스트링(dto.getXid())을 문자열 배열로 전환.
		String[] checkItem_str = dto.getXid().split(",");
		//문자열 배열을 int 배열로 전환.
		int[] checkItem_int = convertStrings(checkItem_str);
		//dto.setCheckedItem 에 int 배열 세팅.
		dto.setCheckedItem(checkItem_int);
		
		if((!id.equals("")) && (loginId == null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이니가 없을 때
			System.out.println("쿠키값이 있고 로그인 안했을 때");
			dto.setId(id); //dto에 id(쿠키값)을 세팅
			mapAjax = productservice.selected_delete_Ajax(request, mView, dto);
		}if((!id.equals("")) && (loginId != null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값이 있고 로그인도 했을 때");
			dto.setId(loginId); //dto에 id(로그인한 아이디값)을 세팅
			mapAjax = productservice.selected_delete_Ajax(request, mView, dto);
		}if((id.equals("")) && (loginId != null)) { //쿠키값이 널이 이거나 빈 문자열이면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값은 없고 로그인은 했을 때");
			dto.setId(loginId); //dto에 id(로그인한 아이디값)을 세팅
			mapAjax = productservice.selected_delete_Ajax(request, mView, dto);
		}
		if((id.equals("")) && (loginId == null)) { //쿠키값이 널이 아니거나 빈 문자열이 아니면서 로그인한 아이디가 있을 때
			System.out.println("쿠키값이 없고 로그인도 안했을 때");
		}
		//mapAjax.put("isValid", isValid); //유효성 여부를 map 객체에 저장.
		return mapAjax;
	}
	
	//선택된 아이템 항목에 맞는 재고수량을 가져오는 요청 처리
	@RequestMapping(value="/shop/size_productnum_ajax.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, ProductDto> size_productnum_ajax(HttpServletRequest request, ProductDto dto,
			@RequestParam String num) {
		System.out.println("size_productnum_ajax/ num:"+num);
		System.out.println("size_productnum_ajax/ dto.getSelectedSize():"+dto.getSelectedSize());
		//DB에서 값을 조회할 수 있도록 문자열 형태의 값을 숫자 타입으로 변경,
		dto.setNum(Integer.parseInt(num));
		dto.setSbsize(Integer.parseInt(dto.getSelectedSize()));
		//선택했던 항목의 상품의 재고상태를 가져오는 메소드
		return productservice.getSelectedSbproductSub(dto);
	}
	
//	//basket_list 요청 처리
//	@RequestMapping(value="/basket_list.do", method=RequestMethod.GET)
//	public ModelAndView basket_list(HttpServletRequest request, ModelAndView mView, 
//			@RequestParam String cookie, ProductDto dto) {
//		System.out.println("basket_list에 들어옴?");
//		mView.setViewName("/shop/basket_list");
//		
//		return mView;
//	}
	
	@RequestMapping("/shop/private/buy.do")
	public ModelAndView order(ModelAndView mView ,OrderDto dto,HttpServletRequest request) {
		
		productservice.order(mView, dto, request);
		mView.setViewName("shop/private/buy");
		return mView;
	}
	//Detail.jsp 페이지 들어갔을 때 출력할 댓글 목록 페이징 ajax 요청 처리
	@RequestMapping("/shop/ajax_paging_list.do")
	@ResponseBody
	public Map<String, Object> ajaxPagingList(HttpServletRequest request, @RequestParam int num){
		
		return productservice.getPagingList(request, num);
	}
	
	//원글의 댓글 and 댓글의 댓글 추가하기 요청 처리
	@RequestMapping("/shop/private/comment_insert.do")
	public ModelAndView commentInsert(HttpServletRequest request,
			ModelAndView mView, @RequestParam int ref_group) {
		String id = (String)request.getSession().getAttribute("id");
		String content = request.getParameter("content");
		if(id != null && content != null && content != "") { //아이디가 존재하고 컨텐츠가 null or ""일때
			//새 댓글을 저장하고
			productservice.saveComment(request);
		}
		//보고있던 글 자세히 보기로 다시 리다일렉트 이동 시킨다.
		mView.setViewName("redirect:/shop/detail.do?num="+ref_group);
		return mView;
	}
	
	//댓글 수정 요청 처리
	@RequestMapping(value = "/shop/private/comment_update.do",
			method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> commentUpdate(ProductReviewDto dto){
		//댓글을 수정 반영하고
		productservice.updateComment(dto);
		Map<String, Object> map = new HashMap<>();
		map.put("num", dto.getNum());
		map.put("content", dto.getContent());
		return map;
	}
	
	//댓글 삭제 요청 처리
	@RequestMapping("/shop/private/comment_delete")
	public ModelAndView commentDelete(HttpServletRequest request,
			ModelAndView mView, @RequestParam int ref_group) {
		productservice.deleteComment(request);
		mView.setViewName("redirect:/shop/detail.do?num="+ref_group);
		return mView;
	}
	
	//메인홈페이지에서 주문내역보기 처리
	@RequestMapping("/order_list.do")
	public ModelAndView order_list(ModelAndView mView, HttpServletRequest request) {
		
		productservice.getorder_list(request, mView);
		mView.setViewName("order_list");
		return mView;
	}
}
