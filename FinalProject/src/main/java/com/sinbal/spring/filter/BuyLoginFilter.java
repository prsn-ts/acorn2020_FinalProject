package com.sinbal.spring.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinbal.spring.product.dto.ProductDto;

@WebFilter({"/shop/private/buy_form.do"})
public class BuyLoginFilter implements Filter{
	
	//String[] 배열을 primitive type의 int array로 바꾸는 메소드
	public static int[] convertIntegers(String[] strings)
	{
	    int[] ret = new int[strings.length];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = Integer.parseInt(strings[i]);
	    }
	    return ret;
	}
	//String[] 배열을 primitive type의 int array로 바꾸는 메소드
		public static int[] convertArrToIntegers(ArrayList<Integer> integers)
		{
		    int[] ret = new int[integers.size()];
		    for (int i=0; i < ret.length; i++)
		    {
		        ret[i] = integers.get(i);
		    }
		    return ret;
		}
	// primitive type의 int array를 ArrayList로 바꾸는 메소드
	public static ArrayList<Integer> convertArrIntegers(int[] intarr)
	{
	    Set<Integer> set = new HashSet<Integer>();
		for(int i : intarr)
			set.add(i); //hashset 객체에 저장되는 데이터는 중복없이 들어간다.
		
		//중복없이 저장된 hashset 객체의 정보를 ArrayList 객체를 생성하면서 생성자로 넣어준다.
	    ArrayList<Integer> overlapNoArr = new ArrayList<>(set);
	    Collections.sort(overlapNoArr); //정렬을 담당.

	    //중복제거, 정렬된 배열을 반환한다.  
	    return overlapNoArr; 
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//클라이언트가 로그인 했는지 여부를 확인한다.
		//부모 type 을  자식 type  으로 casting  후 
		HttpServletRequest req=(HttpServletRequest)request;
		//HttpSession 객체의 참조값을 얻어낸다 
		HttpSession session=req.getSession();
		//로그인된 아이디가 있는지 얻어와본다.
		String id=(String)session.getAttribute("id");
		if(id != null) {//로그인된 상태
			System.out.println("로그인 한 상태로 요청했던 페이지 갔다옴.(값 확인) : "+req.getSession().getAttribute("requestPostData"));
			//요청의 흐름 계속 진행 시키기
			chain.doFilter(request, response);
			
		}else {//로그인이 안된 상태 
			String body = null;
	        StringBuilder stringBuilder = new StringBuilder();
	        BufferedReader bufferedReader = null;
	 
	        try {
	            InputStream inputStream = request.getInputStream();
	            if (inputStream != null) {
	                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	                char[] charBuffer = new char[128];
	                int bytesRead = -1;
	                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                    stringBuilder.append(charBuffer, 0, bytesRead);
	                    System.out.println("stringBuilder : "+stringBuilder);
	                }
	            } else {
	                stringBuilder.append("");
	            }
	        } catch (IOException ex) {
	            throw ex;
	        } finally {
	            if (bufferedReader != null) {
	                try {
	                    bufferedReader.close();
	                } catch (IOException ex) {
	                    throw ex;
	                }
	            }
	        }
	 
	        body = stringBuilder.toString();
	        //requestbody 데이터를 문자열 배열에 넣기위해 선언
	        String[] requestBody_cont = null;
	        //&문자를 제외한 문자열들을 배열에 순차적으로 대입.
	        requestBody_cont = body.split("&");
	        
	        //post 데이터를 저장할 map 객체선언
	        Map<String, Object> requestPostData = new HashMap<String, Object>();
	        
	        //선택한 사이즈를 넣을 문자열 배열 선언.
	        String[] selectedSize = null;
	        int[] selectedSize_int_arr = null;
	        
	        //수량, 가격들을 저장할 배열 선언
	        List<Integer> price_arr = new ArrayList<Integer>();
	        List<Integer> quantity_arr = new ArrayList<Integer>();
	        
	        String[] test_str = {"apple", "banana", "pineapple", "grape", "tomato" };
	        for(int i=0; i<test_str.length; i++) {
	        	System.out.println("test_str : "+test_str[i]);
	        }
	        
	        //requestBody_cont 배열에서 적합한 데이터를 얻기위한 반복문
	        int price_230 = 0;
	        int price_240 = 0;
	        int price_250 = 0;
	        int price_260 = 0;
	        int price_270 = 0;
	        int price_280 = 0;
	        int quantity_230 = 0;
	        int quantity_240 = 0;
	        int quantity_250 = 0;
	        int quantity_260 = 0;
	        int quantity_270 = 0;
	        int quantity_280 = 0;
	        int totalPrice = 0;
	        int num = 0;
	        int buycount = 0;
	        int totalQuantity = 0;
	        String productname = null;
	        String kind = null;
	        String profile = null;
	        int initPrice = 0;
	        //선택된 사이즈들의 반복 횟수를 저장할 변수
	        int selectedSize_int=0;
	        
	        System.out.println("selectedSize for문 전 : "+selectedSize);
	        for(int i=0; i<requestBody_cont.length; i++) {
	        	System.out.printf("requestBody_cont[%d] : %s \n", i, requestBody_cont[i]);
	        	if(requestBody_cont[i].contains("profile")) {
	        		profile = URLDecoder.decode(requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length()));
	        		requestPostData.put("profile", profile);
	        	}
	        	if(requestBody_cont[i].contains("selectedSize")) {//사이즈를 string 배열로 변환하기 위한 작업.
	        		//문자열 배열을 숫자 배열로 convertIntegers 메소드를 이용해 변경 후 selectedSize_int_arr에 저장.
	        		selectedSize_int_arr = convertIntegers(URLDecoder.decode(requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length())).replace("[", "").replace("]", "").replace("\"", "").split(","));
	        		System.out.println("selectedSize_int_arr 정렬 전 : "+selectedSize_int_arr);
	        		for(int j=0; j<selectedSize_int_arr.length; j++) {
	        			System.out.println("selectedSize_int_arr 정렬 전 : "+selectedSize_int_arr[j]);
	        		}
//	        		//수치가 작은 것부터 재배치(중복제거 X)
//        			for(int j=0; j<selectedSize_int_arr.length; j++) { 
//        				int standardNum = j; //비교할 기준에 해당하는 값을 저장하고 관리.
//        				for(int k=1; k<selectedSize_int_arr.length; k++) {
//        					if((selectedSize_int_arr[standardNum] > selectedSize_int_arr[k]) && (k < selectedSize_int_arr.length) && (k > standardNum)) {
//        						int tmp = 0; //임시 변수
//        						tmp = selectedSize_int_arr[k];
//        						selectedSize_int_arr[k] = selectedSize_int_arr[standardNum];
//        						selectedSize_int_arr[standardNum] = tmp;
//		        			}
//        				}
//        			}
	        		//중복 제거, 정렬하기위한 ArrayList 선언
        			ArrayList<Integer> sortedArrayList = convertArrIntegers(selectedSize_int_arr);
        			//중복 제거 및 정렬이 완료된 primitive 타입의 int 배열
        			int[] sortedIntArr = convertArrToIntegers(sortedArrayList);
        			for(int j=0; j<sortedIntArr.length; j++) {
	        			System.out.println("sortedIntArr 정렬, 중복제거 후 : "+sortedIntArr[j]);
	        		}
	        		requestPostData.put("selectedSize", sortedIntArr);
	        	}
	        	if(requestBody_cont[i].contains("product_price_230")) {
	        		String price_230_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		for(int j=0; j<selectedSize_int_arr.length; j++) {
	        			if(!price_230_str.equals("0") && selectedSize_int_arr[j]==230) {
		        			price_230 = Integer.parseInt(price_230_str);
			        		price_arr.add(price_230); //값이 있을 때만 가격 배열에 추가
		        		}
	        		}
	        		requestPostData.put("price_230", price_230);
	        	}
	        	if(requestBody_cont[i].contains("product_price_240")) {
	        		String price_240_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		for(int j=0; j<selectedSize_int_arr.length; j++) {
	        			if(!price_240_str.equals("0") && selectedSize_int_arr[j]==240) {
		        			price_240 = Integer.parseInt(price_240_str);
			        		price_arr.add(price_240); //값이 있을 때만 가격 배열에 추가
		        		}
	        		}
	        		requestPostData.put("price_240", price_240);
	        	}
	        	if(requestBody_cont[i].contains("product_price_250")) {
	        		String price_250_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		for(int j=0; j<selectedSize_int_arr.length; j++) {
	        			if(!price_250_str.equals("0") && selectedSize_int_arr[j]==250) {
	        				price_250 = Integer.parseInt(price_250_str);
			        		price_arr.add(price_250); //값이 있을 때만 가격 배열에 추가
		        		}
	        		}
	        		requestPostData.put("price_250", price_250);
	        	}
	        	if(requestBody_cont[i].contains("product_price_260")) {
	        		String price_260_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!price_260_str.equals("0")) {
	        			price_260 = Integer.parseInt(price_260_str);
		        		price_arr.add(price_260); //값이 있을 때만 가격 배열에 추가
	        		}
	        		requestPostData.put("price_260", price_260);
	        	}
	        	if(requestBody_cont[i].contains("product_price_270")) {
	        		String price_270_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!price_270_str.equals("0")) {
	        			price_270 = Integer.parseInt(price_270_str);
		        		price_arr.add(price_270); //값이 있을 때만 가격 배열에 추가
	        		}
	        		requestPostData.put("price_270", price_270);
	        	}
	        	if(requestBody_cont[i].contains("product_price_280")) {
	        		String price_280_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!price_280_str.equals("0")) {
	        			price_280 = Integer.parseInt(price_280_str);
	        			price_arr.add(price_280); //값이 있을 때만 가격 배열에 추가
	        		}
        			requestPostData.put("price_280", price_280);
	        	}
	        	if(requestBody_cont[i].contains("quantity_230")) {
	        		String quantity_230_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_230_str.equals("0")) {
	        			quantity_230 = Integer.parseInt(quantity_230_str);
	        			quantity_arr.add(quantity_230); //값이 있을 때만 수량 배열에 추가
	        		}
        			requestPostData.put("quantity_230", quantity_230);
	        	}
	        	if(requestBody_cont[i].contains("quantity_240")) {
	        		String quantity_240_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_240_str.equals("0")) {
	        			quantity_240 = Integer.parseInt(quantity_240_str);
		        		quantity_arr.add(quantity_240); //값이 있을 때만 수량 배열에 추가
	        		}
	        		requestPostData.put("quantity_240", quantity_240);
	        	}
	        	if(requestBody_cont[i].contains("quantity_250")) {
	        		String quantity_250_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_250_str.equals("0")) {
	        			quantity_250 = Integer.parseInt(quantity_250_str);
		        		quantity_arr.add(quantity_250); //값이 있을 때만 수량 배열에 추가
	        		}
	        		requestPostData.put("quantity_250", quantity_250);
	        	}
	        	if(requestBody_cont[i].contains("quantity_260")) {
	        		String quantity_260_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_260_str.equals("0")) {
	        			quantity_260 = Integer.parseInt(quantity_260_str);
		        		quantity_arr.add(quantity_260); //값이 있을 때만 수량 배열에 추가
	        		}
	        		requestPostData.put("quantity_260", quantity_260);
	        	}
	        	if(requestBody_cont[i].contains("quantity_270")) {
	        		String quantity_270_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_270_str.equals("0")) {
	        			quantity_270 = Integer.parseInt(quantity_270_str);
		        		quantity_arr.add(quantity_270); //값이 있을 때만 수량 배열에 추가
	        		}
	        		requestPostData.put("quantity_270", quantity_270);
	        	}
	        	if(requestBody_cont[i].contains("quantity_280")) {
	        		String quantity_280_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
        			if(!quantity_280_str.equals("0")) {
	        			quantity_280 = Integer.parseInt(quantity_280_str);
		        		quantity_arr.add(quantity_280); //값이 있을 때만 수량 배열에 추가
	        		}
	        		requestPostData.put("quantity_280", quantity_280);
	        	}
	        	if(requestBody_cont[i].contains("totalPrice")) {
	        		String totalPrice_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		if(!totalPrice_str.equals("0")) {
	        			totalPrice = Integer.parseInt(totalPrice_str);
	        		}
	        		requestPostData.put("totalPrice", totalPrice);
	        	}
	        	if(requestBody_cont[i].contains("num")) {
	        		String num_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		if(!num_str.equals("0")) {
	        			num = Integer.parseInt(num_str);
	        		}
	        		requestPostData.put("num", num);
	        	}
	        	if(requestBody_cont[i].contains("buycount")) {
	        		String buycount_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		if(!buycount_str.equals("0")) {
	        			buycount = Integer.parseInt(buycount_str);
	        		}
	        		requestPostData.put("buycount", buycount);
	        	}
	        	if(requestBody_cont[i].contains("totalQuantity")) {
	        		String totalQuantity_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		if(!totalQuantity_str.equals("0")) {
	        			totalQuantity = Integer.parseInt(totalQuantity_str);
	        		}
	        		requestPostData.put("totalQuantity", totalQuantity);
	        	}
	        	if(requestBody_cont[i].contains("productname")) {
	        		productname = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		requestPostData.put("productname", productname);
	        	}
	        	if(requestBody_cont[i].contains("kind")) {
	        		kind = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		requestPostData.put("kind", kind);
	        	}
	        	if(requestBody_cont[i].contains("initPrice")) {
	        		String initPrice_str = requestBody_cont[i].substring(requestBody_cont[i].lastIndexOf("=")+1, requestBody_cont[i].length());
	        		if(!initPrice_str.equals("0")) {
	        			initPrice = Integer.parseInt(initPrice_str);
	        		}
	        		requestPostData.put("initPrice", initPrice);
	        	}
	        }
	        
	        //수량, 가격 배열을 map 객체에 포함시키기
	        requestPostData.put("price_arr", price_arr);
	        requestPostData.put("quantity_arr", quantity_arr);
	        
	        //request 하는 요청 쪽에 requestPostData map을 저장.
	        req.getSession().setAttribute("requestPostData", requestPostData);
	        
	        System.out.println(price_230);
	        System.out.println(price_240);
	        System.out.println(price_250);
	        System.out.println(price_260);
	        System.out.println(price_270);
	        System.out.println(price_280);
	        System.out.println(quantity_230);
	        System.out.println(quantity_240);
	        System.out.println(quantity_250);
	        System.out.println(quantity_260);
	        System.out.println(quantity_270);
	        System.out.println(quantity_280);
	        System.out.println(totalPrice);
	        System.out.println(num);
	        System.out.println(buycount);
	        System.out.println(totalQuantity);
	        System.out.println(productname);
	        System.out.println(kind);
	        System.out.println(profile);
	        System.out.println(initPrice);
	        
//	        req.setAttribute("requestBody", body);

			/*
			 * 로그인 페이지로 강제로 리다일렉트 했다면 로그인 성공 후에 원래 가려던 
			 * 목적지로 다시 보내야 하고 
			 * GET 방식 전송되는 파라미터가 있다면 파라미터 정보도 같이 가지고 
			 * 다녀야 한다. 
			 */
			//원래 가려던 url 정보 읽어오기
			String url=req.getRequestURI();
			System.out.println("url : "+url);
			//GET 방식 전송 파라미터를 query string 으로 얻어오기
			String query=req.getQueryString();
			//인코딩을 한다.
			String encodedUrl=null;
			if(query==null) { //전송 파라미터가 없다면
				encodedUrl=URLEncoder.encode(url);
			}else {
				encodedUrl=URLEncoder.encode(url+"?"+query);
				
			}
			//로그인 폼으로 리다일렉트 이동하라고 응답
			HttpServletResponse res=(HttpServletResponse)response;
			String cPath=req.getContextPath();
			
			res.sendRedirect(cPath+"/login/login_form.do?url="+encodedUrl);
				
			System.out.println("로그인 하지않은 상태로 요청했던 페이지 갔다옴.");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
