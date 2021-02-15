package com.sinbal.spring.product.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.login.dao.LoginDao;
import com.sinbal.spring.exception.NotDeleteException;
import com.sinbal.spring.product.dao.ProductDao;
import com.sinbal.spring.product.dao.ProductReviewDao;
import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dao.OrderDao;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.product.dto.ProductReviewDto;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductReviewDao productReviewDao;
	
	//한 페이지에 나타낼 댓글 의 갯수
	final int PAGE_ROW_COUNT=12;
	//하단 디스플레이 페이지 갯수
	final int PAGE_DISPLAY_COUNT=5;
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private LoginDao loginDao;
	
	@Transactional
	@Override
	public void insert(ProductDto dto ,HttpServletRequest request) {
		productDao.insert(dto);
		int num=productDao.getnum();
		String[] sbsize= request.getParameterValues("sizearr");
		String[] sbcount= request.getParameterValues("sbcount");
		for(int i=0; i<sbsize.length;i++) {
			dto.setSbsize(Integer.parseInt(sbsize[i]));
			dto.setSbcount(Integer.parseInt(sbcount[i]));
			dto.setNum(num);
			productDao.insert_sub(dto);
		}
	}
	@Override
	public Map<String, Object> saveProfileImage(HttpServletRequest request, MultipartFile mFile) {
		//원본 파일명
		String orgFileName=mFile.getOriginalFilename();
		// webapp/upload 폴더 까지의 실제 경로(서버의 파일시스템 상에서의 경로)
		String realPath=request.getServletContext().getRealPath("/upload");
		//저장할 파일의 상세 경로
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File upload=new File(filePath);
		if(!upload.exists()) {//만일 디렉토리가 존재하지 않으면 
			upload.mkdir(); //만들어 준다.
		}
		//저장할 파일 명을 구성한다.
		String saveFileName=
				System.currentTimeMillis()+orgFileName;
		try {
			//upload 폴더에 파일을 저장한다.
			mFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//Map 에 업로드된 이미지 파일의 경로를 담아서 리턴한다
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("imageSrc","/upload/"+saveFileName);
		
		return map;
	}
	@Override
	public void getList(ModelAndView mView , ProductDto dto) {
		
		mView.addObject("list",productDao.getList(dto));
	}
	//신발 사이즈와 수량을 추가
	@Override
	public void insert_sub(ProductDto dto) {

	}
	@Override
	public Map<String, Object> isExistproductname(String inputproductname) {
		boolean isExist = productDao.isExist(inputproductname);
		//아이디가 존재하는 지 여부를 Map 에 담아서 리턴해준다.
		Map<String, Object> map = new HashMap<>();
		map.put("isExist", isExist);
		return map;
		
	}
	@Override
	public void productdelete(HttpServletRequest request, int num) {
		//세션에 저장된 로그인된 아이디
		String id=(String)request.getSession().getAttribute("id");
		//관리자 계정의 정보를 담는다.
		String admin = "admin";
		//관리자 계정으로 삭제하지 않은 경우 예외 발생
		if(!admin.equals(id)) {
			throw new NotDeleteException("남의 상품은 삭제할 수 없습니다!");
		}
		productDao.productdelete(num);
	}
	@Override
	public void getData(ModelAndView mView ,int num) {
		List<ProductDto> list=productDao.getData(num);
		mView.addObject("list",list);
	}
	
	@Transactional
	@Override
	public void productupdate(ModelAndView mView, ProductDto dto ,HttpServletRequest request) {
		productDao.productupdate(dto);
		String[] sbsize= request.getParameterValues("sizearr");
		String[] sbcount= request.getParameterValues("sbcount");
		
		for(int i=0; i<sbsize.length;i++) {
			dto.setSbsize(Integer.parseInt(sbsize[i]));
			dto.setSbcount(Integer.parseInt(sbcount[i]));
			productDao.productupdate_sub(dto);
		}
		
	}
	//상품 번호에 맞는 상품 정보를 가져오는 추상 메소드
	@Override
	public void getProductData(ModelAndView mView, HttpServletRequest request) {
		//파라미터로 전달되는 글번호
		int num = Integer.parseInt(request.getParameter("num"));
		//상품 정보를 가져온다.
		ProductDto dto = productDao.getData2(num);
		//신발 사이즈, 신발 수량 정보를 가져온다.
		List<ProductDto> dto_sub = productDao.getSubData(num);
		//mView에 담는다.
		mView.addObject("productDto", dto);
		mView.addObject("productDto_sub", dto_sub);
		
		/* 
			댓글 관련 로직 
		*/
		
		//전체 댓글 의 갯수를 읽어온다.
		int totalRow=productReviewDao.getCount(num);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호(만일 pageNum이 넘어오지 않으면 가장 마지막 페이지)
		String strPageNum = request.getParameter("pageNum");
		//만일 페이지 번호가 넘어온다면
		if(strPageNum!=null) {
			//넘어온 페이지에 해당하는 댓글 목록을 보여주도록 한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		// CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum 을 담는다.
		ProductReviewDto productReviewDto=new ProductReviewDto();
		productReviewDto.setStartRowNum(startRowNum);
		productReviewDto.setEndRowNum(endRowNum);
		//ref_group 번호도 담는다.
		productReviewDto.setRef_group(num);
		
		//원글의 글번호를 이용해서 댓글 목록을 얻어온다.
		List<ProductReviewDto> commentList = productReviewDao.getList(productReviewDto);
		
		//request 에 담아준다.
		request.setAttribute("commentList", commentList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPageCount", totalPageCount);
	}
	//특정 사이즈의 재고 개수를 리턴하는 메소드
	@Override
	public ProductDto getStockData(int size, int num) {
		//재고 개수를 가져온다.
		ProductDto dto = productDao.getStockData(size, num);
		return dto;
	}
	//선택할 수 있는 신발 사이즈 항목의 개수를 리턴하는 메소드
	@Override
	public int getSizeData(int num) {
		//재고 개수를 가져온다.
		int number = productDao.getSizeData(num);
		return number;
	}
	//선택한 신발 사이즈에 해당하는 가격을 가져오는 추상 메소드
	@Override
	public Map<String, Object> getSbsizePrice(int size, int num) {
		//특정 상품번호에 대한 정보를 가져온다.
		List<ProductDto> list_dto = productDao.getSubData(num);
		//특정 상품번호의 특정 신발 사이즈에 대한 정보를 가져온다.
		ProductDto dto = productDao.getStockData(size, num);
		//선택한 신발 사이즈에 대한 총 가격을 구한다.
		//총 가격을 저장할 변수 선언
		int totalPrice = 0;
		for(int i=0; i<list_dto.size(); i++) {
			totalPrice = totalPrice + list_dto.get(i).getPrice();
		}
		//특정 신발 사이즈에 맞는 가격을 가져온다.
		int price = dto.getPrice();
		//Map 객체에 정보를 담는다.
		Map<String, Object> priceInfo = new HashMap<>();
		priceInfo.put("totalPrice", totalPrice);
		priceInfo.put("price", price);
		return priceInfo;
	}
	
	@Transactional
	@Override
	public void order(ModelAndView mView, OrderDto dto ,HttpServletRequest request) {
		
		int[]sizearr =dto.getSizearr();
		int[]countarr=dto.getCountarr();
		
		int buycount = 0;
		
		//총금액을 가져오ㄴ다
		int totalPrice =dto.getTotalPrice();
		//사용자의 id를 검색한다
		String id=(String)request.getSession().getAttribute("id");
		//사용자의 계좌 정보를 검색한다.
		int money =loginDao.getData(id).getMoney();
		
		//사용자의 머니가 총금액보다 높을떄만  주문을 하도록 처리
		if(money>=totalPrice) {
			String sboption="";
			for(int i=0;i<sizearr.length;i++) {
				sboption= sboption+"size :"+sizearr[i]+","+countarr[i]+"개";	
				buycount = buycount + countarr[i];
			}
			String addr=dto.getZipNo()+dto.getRoadAddrPart1()+dto.getAddrDetail();
			dto.setSboption(sboption);
			dto.setAddr(addr);
			
			//상품을 주문하는 dao
			orderDao.order(dto);
			//가지고있는 계좌에서 금액을 감소시킨다.
			orderDao.minus_money(dto);
			
			OrderDto dto2= new OrderDto();
			dto2.setNum(dto.getProductnum());
			dto2.setBuycount(buycount);
			System.out.println("바이카운트"+buycount);
			//상품의 buycount를 증가시킨다
			orderDao.buycount(dto2);
			//상품의 재고를 감소시킨다
			for(int i=0;i<sizearr.length;i++) {
				dto.setSbsize(sizearr[i]);
				dto.setSbcount(countarr[i]);
				orderDao.minus_count(dto);	
			}
			//포인트를 적립시킨다.
			orderDao.addpoint(dto);
			//결제가 성공적으로 완료되었다는처리보내기
			mView.addObject("isSuccess",true);
			
		}
		else {
			//결제가 실패했다는 처리보내기
			mView.addObject("isSuccess",false);
		}
	}
	@Override
	public void buy(ModelAndView mView, ProductDto dto) {
		int[] sbsize= dto.getSizearr();
		int[] sbcount= dto.getCountarr();
		int[] sbprice= dto.getPricearr();
		
//		int buycount = dto.getBuycount();
//		int totalQuantity = dto.getTotalQuantity();
		int totalPrice = dto.getTotalPrice();
//		int initPrice = dto.getInitPrice();
//		String kind = dto.getKind();
//		String productname = dto.getProductname();
//		int product_price_230 = dto.getProduct_price_230();
//		int product_price_240 = dto.getProduct_price_240();
//		int product_price_250 = dto.getProduct_price_250();
//		int product_price_260 = dto.getProduct_price_260();
//		int product_price_270 = dto.getProduct_price_270();
//		int product_price_280 = dto.getProduct_price_280();
//		int quantity_230 = dto.getQuantity_230();
//		int quantity_240 = dto.getQuantity_240();
//		int quantity_250 = dto.getQuantity_250();
//		int quantity_260 = dto.getQuantity_260();
//		int quantity_270 = dto.getQuantity_270();
//		int quantity_280 = dto.getQuantity_280();
		
//		String[] selectedSizeArr = dto.getSelected_Size();
//		
//		//선택한 사이즈들의 배열 객체를 얻기위해서 먼저 문자열로 바꾼다.(자바스크립트 부분에서 객체화할 예정)
//		ObjectMapper mapper = new ObjectMapper();
//		String selectedSize;
//		try {
//			selectedSize = mapper.writeValueAsString( selectedSizeArr );
//			mView.addObject( "selectedSize", selectedSize );
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		mView.addObject("sbsize",sbsize);
		mView.addObject("sbcount",sbcount);
		mView.addObject("sbprice",sbprice);
//		mView.addObject("buycount",buycount);
//		mView.addObject("totalQuantity",totalQuantity);
		mView.addObject("totalPrice",totalPrice);
//		mView.addObject("initPrice", initPrice);
//		mView.addObject("kind", kind);
//		mView.addObject("productname", productname);
		mView.addObject("sbdto",dto);
//		
//		mView.addObject("product_price_230", product_price_230);
//		mView.addObject("product_price_240", product_price_240);
//		mView.addObject("product_price_250", product_price_250);
//		mView.addObject("product_price_260", product_price_260);
//		mView.addObject("product_price_270", product_price_270);
//		mView.addObject("product_price_280", product_price_280);
//		
//		mView.addObject("quantity_230", quantity_230);
//		mView.addObject("quantity_240", quantity_240);
//		mView.addObject("quantity_250", quantity_250);
//		mView.addObject("quantity_260", quantity_260);
//		mView.addObject("quantity_270", quantity_270);
//		mView.addObject("quantity_280", quantity_280);
	}
	//작성된 댓글 내용을 DB에 저장하는 메소드
	@Override
	public void saveComment(HttpServletRequest request) {
		//댓글 작성자
		String writer = (String)request.getSession().getAttribute("id");
		//폼 전송되는 댓글의 정보 얻어내기
		int ref_group = Integer.parseInt(request.getParameter("ref_group"));
		String target_id = request.getParameter("target_id");
		String content = request.getParameter("content");
		/*
		 *  원글의 댓글은 comment_group 번호가 전송이 안되고
		 *  댓글의 댓글은 comment_group 번호가 전송이 된다.
		 *  따라서 null 여부를 조사하면 원글의 댓글인지 댓글의 댓글인지 판단할 수 있다.
		 */
		String comment_group = request.getParameter("comment_group");
		int seq = productReviewDao.getSequence();
		
		//저장할 댓글 정보를 dto에 담기
		ProductReviewDto dto = new ProductReviewDto();
		dto.setNum(seq);
		dto.setWriter(writer);
		dto.setTarget_id(target_id);
		dto.setContent(content);
		dto.setRef_group(ref_group);
		if(comment_group==null) {//원글의 댓글인 경우
			//댓글의 글번호가 comment_group 번호가 된다.
			dto.setComment_group(seq);
		}else {//댓글의 댓글인 경우
			//폼 전송된 comment_group 번호를 숫자로 바꿔서 dto 에 넣어준다.
			dto.setComment_group(Integer.parseInt(comment_group));
		}
		//댓글 정보를 DB 에 저장한다.
		productReviewDao.insert(dto);
	}
	//자신의 댓글을 수정하는 메소드
	@Override
	public void updateComment(ProductReviewDto dto) {
		productReviewDao.update(dto);
	}
	//자신의 댓글을 삭제하는 메소드
	@Override
	public void deleteComment(HttpServletRequest request) {
		//GET 방식 파라미터로 전달되는 삭제할 댓글 번호
		int num = Integer.parseInt(request.getParameter("num"));
		//세션에 저장된 로그인된 아이디
		String id=(String)request.getSession().getAttribute("id");
		//댓글의 정보를 얻어와서 댓글의 작성자와 같은지 비교 한다.
		String writer = productReviewDao.getData(num).getWriter();
		if(!writer.equals(id)) {
			throw new NotDeleteException("남의 댓글을 삭제할 수 없습니다.");
		}
		productReviewDao.delete(num);
	}
	//댓글 목록 페이징 ajax 요청 처리 메소드
	@Override
	public Map<String, Object> getPagingList(HttpServletRequest request, int num) {
		/* 
			댓글 관련 로직 
		*/
		
		//전체 댓글 의 갯수를 읽어온다.
		int totalRow=productReviewDao.getCount(num);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호(만일 pageNum이 넘어오지 않으면 가장 마지막 페이지)
		String strPageNum = request.getParameter("pageNum");
		//만일 페이지 번호가 넘어온다면
		if(strPageNum!=null) {
			//넘어온 페이지에 해당하는 댓글 목록을 보여주도록 한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		// CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum 을 담는다.
		ProductReviewDto productReviewDto=new ProductReviewDto();
		productReviewDto.setStartRowNum(startRowNum);
		productReviewDto.setEndRowNum(endRowNum);
		//ref_group 번호도 담는다.
		productReviewDto.setRef_group(num);
		
		//원글의 글번호를 이용해서 댓글 목록을 얻어온다.
		List<ProductReviewDto> commentList = productReviewDao.getList(productReviewDto);
		
		//상품 정보를 가져온다.
		ProductDto dto = productDao.getData2(num);
		
		//글목록과 페이징 처리에 관련된 값을 담을 Map 객체 생성
		Map<String, Object> map=new HashMap<>();
		//글목록을 전체 Map 에 담아준다. 
		map.put("commentList", commentList);
		//상품 정보를 전체 Map 에 담아준다.
		map.put("productDto", dto);
		
		//페이징 처리에 필요한 값을 Map 에 담아서 
		Map<String, Integer> paging=new HashMap<>();
		paging.put("startPageNum", startPageNum);
		paging.put("endPageNum", endPageNum);
		paging.put("pageNum", pageNum);
		paging.put("totalPageCount", totalPageCount);
		//전체 Map 에 담아준다. 
		map.put("paging", paging);
		return map;
	}
	@Override
	public void getorder_list(HttpServletRequest request ,ModelAndView mView) {
		String id =(String)request.getSession().getAttribute("id");
		
		//한 페이지에 나타낼 댓글 의 갯수
		final int PAGE_ROW_COUNT=5;
		//하단 디스플레이 페이지 갯수
		final int PAGE_DISPLAY_COUNT=5;
		
		//전체 댓글 의 갯수를 읽어온다.
		int totalRow=orderDao.getCount(id);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호(만일 pageNum이 넘어오지 않으면 가장 마지막 페이지)
		String strPageNum = request.getParameter("pageNum");
		//만일 페이지 번호가 넘어온다면
		if(strPageNum!=null) {
			//넘어온 페이지에 해당하는 댓글 목록을 보여주도록 한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		// CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum 을 담는다.
		OrderDto orderDto=new OrderDto();
		orderDto.setStartRowNum(startRowNum);
		orderDto.setEndRowNum(endRowNum);
		//주문자의 아이디를 담는다.
		orderDto.setId(id);
		
		//request 에 담아준다.
		request.setAttribute("list",orderDao.order_list(orderDto));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPageCount", totalPageCount);
		
	}
	
	@Override
    public void productList(HttpServletRequest request) {
		 String search = request.getParameter("search");
		 String kindSelect = request.getParameter("kindSelect");
		 String arr = request.getParameter("arr");
		 String keyword = request.getParameter("keyword");
		 
		 if(search==null) {
		    search="";
		 }
		 if(kindSelect==null) {
		    kindSelect="";
		 }
		 if(arr==null) {
		    arr="";
		 }
		 if(keyword==null) {
		    keyword="";
		 }
		 
		 System.out.println(search);
		 System.out.println(kindSelect);
		 System.out.println(arr);
		 
		 String encodedK=URLEncoder.encode(search);
		 
		 
		 //보여줄 페이지의 번호
		 int pageNum=1;
		 //보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.   
		 String strPageNum=request.getParameter("pageNum");
		 if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
		//페이지 번호를 설정한다.
		    pageNum=Integer.parseInt(strPageNum);
		 }
		 //보여줄 페이지 데이터의 시작 ResultSet row 번호
		 int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		 //보여줄 페이지 데이터의 끝 ResultSet row 번호
		 int endRowNum=pageNum*PAGE_ROW_COUNT;
		 
		 //startRowNum, endRowNum 을 담을 Dto 객체 생성
		 ProductDto dto=new ProductDto();
		 dto.setStartRowNum(startRowNum);
		 dto.setEndRowNum(endRowNum);
		 dto.setSearch(search);
		 
		 if(!search.equals("")) {
		    dto.setSearch(search);
		 }
		 if(!kindSelect.equals("")) {
		    dto.setKindSelect(kindSelect);
		 }
		 if(!arr.equals("")) {
		    dto.setArr(arr);
		 }
		 if(!keyword.equals("")) {
		    dto.setKeyword(keyword);
		 }
		 
		 //파일 목록 얻어오기
		 List<ProductDto> list=productDao.productList(dto);
		 //전체 row 의 갯수 
		 int totalRow=productDao.getCount(dto);
		 
		 //전체 페이지의 갯수 구하기
		 int totalPageCount=
		       (int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		 //시작 페이지 번호
		 int startPageNum=
		    1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		 //끝 페이지 번호
		 int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		 //끝 페이지 번호가 잘못된 값이라면 
		 if(totalPageCount < endPageNum){
		    endPageNum=totalPageCount; //보정해준다. 
		 }
		 
		 //EL 에서 사용할 값을 미리 request 에 담아두기
		 request.setAttribute("list", list);
		 request.setAttribute("startPageNum", startPageNum);
		 request.setAttribute("endPageNum", endPageNum);
		 request.setAttribute("pageNum", pageNum);
		 request.setAttribute("totalPageCount", totalPageCount);
		 request.setAttribute("search", search);
		 request.setAttribute("encodedK", encodedK);
		 request.setAttribute("kindSelect", kindSelect);
		 request.setAttribute("arr", arr);
		 request.setAttribute("keyword", keyword);
    }
	
	@Override
	public void homeList(HttpServletRequest request) {
		ProductDto dto = new ProductDto();
		List<ProductDto> list=productDao.homeList(dto);
		request.setAttribute("list", list);
		
	}
	@Override
	public void likelist(ModelAndView mView) {
	  List<ProductDto> likelist=productDao.favoritelist();
	  mView.addObject("likelist" ,likelist);
		
	}
	//장바구니에 상품을 저장하는 메소드
	@Override
	public String saveToBasket(HttpServletRequest request, ProductDto dto,
			HttpServletResponse response, String cookie) {
		//int[] 배열을 String[] 배열로 바꾸기 위한 sizeArr 배열 생성
		String[] sizeArr = Arrays.stream(dto.getSizearr()).mapToObj(String::valueOf).toArray(String[]::new);
		System.out.println(Arrays.toString(sizeArr));
		
		//로그인한 상태로 장바구니에 상품을 담았는 지 비로그인 상태로 장바구니에 상품을 담았는 지 아이디 확인
		String id = (String)request.getSession().getAttribute("id");
		
		//비로그인 상태이면서 쿠키 값이 없을 때
		if(cookie.equals("") && id==null) {
			System.out.println("쿠키값도 없고 로그인도 안한 경우");
			//비회원으로 장바구니에 상품을 담을 시에 각 사용자들을 구분하기위해 타임스탬프를 적용해 특정 문자열 생성.
			String guest = "guest_"+System.currentTimeMillis();
			
			//클라이언트 사이드에서 쿠키값을 읽어서 사용할 수 있도록 쿠키값을 저장.
			Cookie guest_cook=new Cookie("guest", guest);
			guest_cook.setMaxAge(60*30);//30분 동안 쿠키 지정
			guest_cook.setPath("/spring/"); //어느 경로에서든 쿠키에 접근할 수 있도록 프로젝트 이름으로 범위 설정.
			response.addCookie(guest_cook);
			
			//쿠키를 만들고나서 그 값을 cookie 변수에 넣어
			//shopcontroller에 /shop/shopping_basket.do에 shoppingBasketInfo 메소드의 cookie 인자로 쓰기위함.
			cookie = guest; 
			
			//비로그인 상태로 장바구니에 상품을 담았을 경우에
			//손님이라는 의미로 guest로 아이디 설정 및 장바구니 담은 시간을 정확히 계산하기위해 타임스탬프 이용.
			dto.setId(guest);
		}if(cookie.equals("") && id!=null) { //쿠키는 없고 로그인한 아이디가 있을 경우 아이디값으로 dto 세팅.
			System.out.println("쿠키값는 없고 로그인은 한 경우");
			dto.setId(id);
		}if(!cookie.equals("") && id==null) { //쿠키는 있는데 아이디는 없는 경우 기존 쿠키를 넣는다.
			System.out.println("쿠키가 있고 로그인한 아이디는 없는 경우");
			dto.setId(cookie);
		}if(!cookie.equals("") && id!=null){//쿠키와 아이디 둘다 있는 경우
			System.out.println("쿠키, 로그인한 아이디 둘다 있는 경우");
			dto.setId(id);
		}
		
//		//사라진 쿠키 정보는 DB에서 지울 수 있도록 처리
//		Cookie[] cookies = request.getCookies();
//		List<String> idList = productDao.selectedId();
//		for(Cookie cook : cookies) {
//			String cook_str = cook.toString();
//			for(int i=0; i<idList.size(); i++) {
//				if(!cook_str.equals(idList.get(i))) {//현재 존재하는 쿠키(아이디)값과 DB에 저장된 아이디값이 같지 않을 경우
//					productDao.selectedId_remove(idList.get(i)); //장바구니 관련 DB에서 해당 아이디 관련 정보 삭제
//				}
//			}
//		}
		
		//특정 상품 번호에 맞는 리스트를 가져오기 위한 메소드 호출
		dto.setOneSize("230");
		List<ProductDto> list_230 = productDao.select_230(dto);
		dto.setOneSize("240");
		List<ProductDto> list_240 = productDao.select_240(dto);
		dto.setOneSize("250");
		List<ProductDto> list_250 = productDao.select_250(dto);
		dto.setOneSize("260");
		List<ProductDto> list_260 = productDao.select_260(dto);
		dto.setOneSize("270");
		List<ProductDto> list_270 = productDao.select_270(dto);
		dto.setOneSize("280");
		List<ProductDto> list_280 = productDao.select_280(dto);
		for(int i=0; i<sizeArr.length; i++) {
			if(sizeArr[i].equals("230") && list_230.isEmpty()) {
				dto.setOneSize("230");
				productDao.saveToBasket_230(dto);
			}
			else if(sizeArr[i].equals("230") && !list_230.isEmpty()) {
				dto.setOneSize("230");
				productDao.delete_230(dto);
				productDao.saveToBasket_230(dto);
			}
			if(sizeArr[i].equals("240") && list_240.isEmpty()) {
				dto.setOneSize("240");
				productDao.saveToBasket_240(dto);
			}
			else if(sizeArr[i].equals("240") && !list_240.isEmpty()) {
				dto.setOneSize("240");
				productDao.delete_240(dto);
				productDao.saveToBasket_240(dto);
			}
			if(sizeArr[i].equals("250") && list_250.isEmpty()) {
				dto.setOneSize("250");
				productDao.saveToBasket_250(dto);
			}
			else if(sizeArr[i].equals("250") && !list_250.isEmpty()) {
				dto.setOneSize("250");
				productDao.delete_250(dto);
				productDao.saveToBasket_250(dto);
			}
			if(sizeArr[i].equals("260") && list_260.isEmpty()) {
				dto.setOneSize("260");
				productDao.saveToBasket_260(dto);
			}
			else if(sizeArr[i].equals("260") && !list_260.isEmpty()) {
				dto.setOneSize("260");
				productDao.delete_260(dto);
				productDao.saveToBasket_260(dto);
			}
			if(sizeArr[i].equals("270") && list_270.isEmpty()) {
				dto.setOneSize("270");
				productDao.saveToBasket_270(dto);
			}
			else if(sizeArr[i].equals("270") && !list_270.isEmpty()) {
				dto.setOneSize("270");
				productDao.delete_270(dto);
				productDao.saveToBasket_270(dto);
			}
			if(sizeArr[i].equals("280") && list_280.isEmpty()) {
				dto.setOneSize("280");
				productDao.saveToBasket_280(dto);
			}
			else if(sizeArr[i].equals("280") && !list_280.isEmpty()) {
				dto.setOneSize("280");
				productDao.delete_280(dto);
				productDao.saveToBasket_280(dto);
			}
		}
		return cookie;
	}
	//장바구니 DB에 저장된 내용을 가져오는 메소드
	@Override
	public ModelAndView shoppingBasketInfo(ProductDto dto, HttpServletRequest request, ModelAndView mView, String id) {
		String loginId = (String)request.getSession().getAttribute("id");
		System.out.println("shoppingBasketInfo_loginId : "+loginId);
		System.out.println("shoppingBasketInfo_id : "+id);
		
		//현재 시간에서 1일 전 내용과 아이디(또는 쿠키)를 이용해 필요한 장바구니 관련 DB select 하기
		Calendar cal = Calendar.getInstance(); //날짜 계산 관련 객체 생성(cal 변수에는 객체 생성한 시점의 시간이 저장되어있다.)
		String a_day_ago = null; //shoppingBasketInfo 메소드가 실행되는 시점에 생성되는 현재 시간의 내용을 저장할 변수
		cal.add(cal.DAY_OF_MONTH, -1); //하루 전의 날짜를 계산하는 부분
		a_day_ago = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " ";
		
		//DB 검색한 데이터들을 저장하기위한 리스트
		List<ProductDto> basket_list = new ArrayList<ProductDto>();
		if(id == null && loginId != null) {//guest 이름의 쿠키값이 넘어온게 없으면서 로그인한 아이디가 있을 때
			//쿠키 값이 없을 때는 로그인한 아이디값을 대입
			id = (String)request.getSession().getAttribute("id");
			dto.setSaveTime(a_day_ago); //현재 시간의 1일 전 날짜를 문자열로 대입한다.
			dto.setId(id); //쿠키값이 있는 경우 쿠키값으로 아이디를 세팅.
			basket_list = productDao.savedBasketInfo(dto);
		}
		if(id != null && loginId == null) {//쿠키 값이 있을 때
			
			dto.setSaveTime(a_day_ago); //현재 시간의 1일 전 날짜를 문자열로 대입한다.
			dto.setId(id); //쿠키값이 있는 경우 쿠키값으로 아이디를 세팅.
			basket_list = productDao.savedBasketInfo(dto);
		}
		if(id != null && loginId != null) { //쿠키값과 로그인한 아이디가 둘다 있는 경우
			//둘다 있을 때는 로그인한 아이디값을 대입
			id = (String)request.getSession().getAttribute("id");
			dto.setSaveTime(a_day_ago); //현재 시간의 1일 전 날짜를 문자열로 대입한다.
			dto.setId(id); //쿠키값이 있는 경우 쿠키값으로 아이디를 세팅.
			basket_list = productDao.savedBasketInfo(dto);
		}
//		//id(쿠키값) 없고 loginId(로그인 관련) 값이 없을 경우
//		basket_list = productDao.shoppingBasketInfo_non_Id();
//		
		for(int i=0; i<basket_list.size(); i++) {
			System.out.println(basket_list.get(i).getKind());
			System.out.println(basket_list.get(i).getProductname());
			System.out.println(basket_list.get(i).getSelectedSize());
			System.out.println(basket_list.get(i).getSelectedQuantity());
			System.out.println(basket_list.get(i).getSelectedPrice());
			System.out.println(basket_list.get(i).getSaveTime());
		}
		//비교할 배열 선언 및 값 대입
//		List<String> compare_list = new ArrayList<String>();
//		compare_list.add("230");
//		compare_list.add("240");
//		compare_list.add("250");
//		compare_list.add("260");
//		compare_list.add("270");
//		compare_list.add("280");
		
//		//각 수량, 사이즈, 가격에 맞는 데이터를 저장할 배열 선언 
		List<String> sizeList = new ArrayList<String>();
//		List<Integer> quantityList = new ArrayList<Integer>();
//		List<Integer> priceList = new ArrayList<Integer>();
//		//리스트를 저장할 map 객체 선언
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		//데이터 저장하는 반복문
		for(int i=0; i<basket_list.size(); i++) {
			sizeList.add(basket_list.get(i).getSelectedSize());
		}
//		for(int i=0; i<basket_list.size(); i++) {
//			sizeList.add(basket_list.get(i).getSelectedSize());
//			quantityList.add(basket_list.get(i).getSelectedQuantity());
//			priceList.add(basket_list.get(i).getSelectedPrice());
//			
//			//DB에 저장된 값 응답 페이지에서 쓸 수 있도록 mView에 할당
//			if(basket_list.get(i).getSelectedSize().equals("230")) {
//				mView.addObject("product_price_230", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_230", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("240")) {
//				mView.addObject("product_price_240", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_240", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("250")) {
//				mView.addObject("product_price_250", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_250", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("260")) {
//				mView.addObject("product_price_260", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_260", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("270")) {
//				mView.addObject("product_price_270", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_270", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("280")) {
//				mView.addObject("product_price_280", basket_list.get(i).getSelectedPrice());
//				mView.addObject("quantity_280", basket_list.get(i).getSelectedQuantity());
//			}
//		}
//		mView.addObject("sbsize", sizeList);
		mView.addObject("buycount", sizeList.size());
		if(sizeList.isEmpty()) {//만약 sizeList 배열안에 값이 존재하지 않으면
			mView.addObject("buycount", 0); //0개로 처리
		}
//		mView.addObject("kind", basket_list.get(0).getKind());
//		mView.addObject("productname", basket_list.get(0).getProductname());
//		mView.addObject("totalQuantity", basket_list.get(0).getTotalQuantity());
//		mView.addObject("totalPrice",basket_list.get(0).getTotalPrice());
//		mView.addObject("initPrice", basket_list.get(0).getInitPrice());
		
//		//장바구니 관련 DB에 저장되지 않은 데이터들을 알아내기위한 compare_list 배열의 정리.
//		for(int i=0; i<basket_list.size(); i++) {
//			for(int j=0; j<compare_list.size(); j++) {
//				if(basket_list.get(i).getSelectedSize().equals(compare_list.get(j))) {
//					compare_list.remove(j);
//				};
//			}
//		}
//		//DB에 저장되지 않았던 신발 사이즈의 수량,가격을 숫자 0으로 변환.
//		for(int i=0; i<compare_list.size(); i++) {
//			mView.addObject("product_price_"+compare_list.get(i), 0);
//			mView.addObject("quantity_"+compare_list.get(i), 0);
//			mView.addObject("totalQuantity", 0);
//			mView.addObject("totalPrice", 0);
//			mView.addObject("initPrice", 0);
//		}
		
//		listMap.put("sizeList", sizeList);
//		listMap.put("quantityList", quantityList);
//		listMap.put("priceList", priceList);
		return mView;
	}
	//장바구니 DB에 저장된 내용을 가져오는 메소드(ajax 요청)
	@Override
	public Map<String, Object> shoppingBasketInfo_Ajax(HttpServletRequest request, 
			ModelAndView mView, ProductDto dto) {
		
		//현재 시간에서 1일 전 내용과 아이디(또는 쿠키)를 이용해 필요한 장바구니 관련 DB select 하기
		Calendar cal = Calendar.getInstance(); //날짜 계산 관련 객체 생성(cal 변수에는 객체 생성한 시점의 시간이 저장되어있다.)
		String a_day_ago = null; //shoppingBasketInfo 메소드가 실행되는 시점에 생성되는 현재 시간의 내용을 저장할 변수
		cal.add(cal.DAY_OF_MONTH, -1); //하루 전의 날짜를 계산하는 부분
		a_day_ago = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " ";
		System.out.println(a_day_ago);
		//현재 시간에서 1일 전 계산한 값을 dto에 넣어준다.
		dto.setSaveTime(a_day_ago);
		
		//리스트를 저장할 map 객체 선언
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		//현재 시간으로 부터 1일 전까지의 장바구니 관련 쿠키 내용들을 가져오는 메소드(정렱 X)
		List<ProductDto> basket_list = productDao.savedBasketInfo(dto);
		
		//리스트를 그냥 가져오지않고 정렬해서 가져오기위한 sql문 실행(정렬 O)
		List<ProductDto> alignedBasketList = productDao.getAlignedBasket(dto);
		
		if(alignedBasketList.isEmpty()) {
			System.out.println("alignedBasketList가 존재하지 않습니다");
		}
		
		//장바구니 리스트 전체를 map 객체에 넣기
		listMap.put("basket_list", alignedBasketList);
		
		//특정 상품의 특정 사이즈의 X표시를 눌렀을 때(안 눌렀다면 null 값이 들어있음)
		if(dto.getXid() != null) {
			//x버튼을 누른 특정 상품의 특정 사이즈를 제외한 결과를 가져오기위함.
			int identityNum = productDao.getIdentityNumBasket(dto);
			//결과를 dto에 세팅한다.
			dto.setIdentityNum(identityNum);
			//identityNum에 해당하는 물품을 장바구니 관련 테이블에서 삭제한다.
			productDao.deleteIdentityNumAtBasket(dto);
			
			//특정 물품의 특정 사이즈 삭제 후 정렬된 테이블 가져오기
			List<ProductDto> excludedList = productDao.getAlignedBasket(dto);
			
			for(int i=0; i<excludedList.size(); i++) {
				System.out.println(excludedList.get(i).getProductname());
			}
			
			//장바구니의 특정 상품의 특정 사이즈를 제외한 리스트 전체를 map 객체에 넣기
			listMap.put("basket_list", excludedList);
		}
		
		//DB에서 장바구니 관련 정보를 가져올 수 있는 지 없는지 판별할 변수
		boolean isValid = false;
		
		//만약 리스트가 존재하지 않을 경우
		if(alignedBasketList.size() == 0) {
			isValid = false; //장바구니에 접근 가능하다는 의미로 true
			listMap.put("isValid", isValid); 
		}
		else if(alignedBasketList.size() > 0){
			isValid = true; //장바구니에 접근 불가능하다는 의미로 false
			listMap.put("isValid", isValid); 
		}
		
//		//비교할 배열 선언 및 값 대입
//		List<String> compare_list = new ArrayList<String>();
//		compare_list.add("230");
//		compare_list.add("240");
//		compare_list.add("250");
//		compare_list.add("260");
//		compare_list.add("270");
//		compare_list.add("280");
//		
//		//각 수량, 사이즈, 가격에 맞는 데이터를 저장할 배열 선언 
//		List<String> sizeList = new ArrayList<String>();
//		List<Integer> quantityList = new ArrayList<Integer>();
//		List<Integer> priceList = new ArrayList<Integer>();
//		
//		//데이터 저장하는 반복문
//		for(int i=0; i<basket_list.size(); i++) {
//			sizeList.add(basket_list.get(i).getSelectedSize());
//			quantityList.add(basket_list.get(i).getSelectedQuantity());
//			priceList.add(basket_list.get(i).getSelectedPrice());
//			
//			//DB에 저장된 값 응답 페이지에서 쓸 수 있도록 mView에 할당
//			if(basket_list.get(i).getSelectedSize().equals("230")) {
//				listMap.put("product_price_230", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_230", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("240")) {
//				listMap.put("product_price_240", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_240", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("250")) {
//				listMap.put("product_price_250", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_250", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("260")) {
//				listMap.put("product_price_260", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_260", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("270")) {
//				listMap.put("product_price_270", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_270", basket_list.get(i).getSelectedQuantity());
//			}
//			else if(basket_list.get(i).getSelectedSize().equals("280")) {
//				listMap.put("product_price_280", basket_list.get(i).getSelectedPrice());
//				listMap.put("quantity_280", basket_list.get(i).getSelectedQuantity());
//			}
//			listMap.put("kind", basket_list.get(i).getKind());
//			listMap.put("productname", basket_list.get(i).getProductname());
//			listMap.put("totalQuantity", basket_list.get(i).getTotalQuantity());
//			listMap.put("totalPrice", basket_list.get(i).getTotalPrice());
//			listMap.put("initPrice", basket_list.get(i).getInitPrice());
//		}
//		listMap.put("buycount", sizeList.size());
//		
//		//장바구니 관련 DB에 저장되지 않은 데이터들을 알아내기위한 compare_list 배열의 정리.
//		for(int i=0; i<basket_list.size(); i++) {
//			for(int j=0; j<compare_list.size(); j++) {
//				if(basket_list.get(i).getSelectedSize().equals(compare_list.get(j))) {
//					compare_list.remove(j);
//				};
//			}
//		}
//		//DB에 저장되지 않았던 신발 사이즈의 수량,가격을 숫자 0으로 변환.
//		for(int i=0; i<compare_list.size(); i++) {
//			listMap.put("product_price_"+compare_list.get(i), 0);
//			listMap.put("quantity_"+compare_list.get(i), 0);
//		}
//		
//		listMap.put("sizeList", sizeList);
//		listMap.put("quantityList", quantityList);
//		listMap.put("priceList", priceList);
		return listMap;
	}
	
	//장바구니 DB에 저장된 내용을 가져오는 메소드(ajax 요청)
	@Override
	public Map<String, Object> selected_delete_Ajax(HttpServletRequest request, 
			ModelAndView mView, ProductDto dto) {
		
		//현재 시간에서 1일 전 내용과 아이디(또는 쿠키)를 이용해 필요한 장바구니 관련 DB select 하기
		Calendar cal = Calendar.getInstance(); //날짜 계산 관련 객체 생성(cal 변수에는 객체 생성한 시점의 시간이 저장되어있다.)
		String a_day_ago = null; //shoppingBasketInfo 메소드가 실행되는 시점에 생성되는 현재 시간의 내용을 저장할 변수
		cal.add(cal.DAY_OF_MONTH, -1); //하루 전의 날짜를 계산하는 부분
		a_day_ago = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " ";
		System.out.println(a_day_ago);
		//현재 시간에서 1일 전 계산한 값을 dto에 넣어준다.
		dto.setSaveTime(a_day_ago);
		
		//리스트를 저장할 map 객체 선언
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		//현재 시간으로 부터 1일 전까지의 장바구니 관련 쿠키 내용들을 가져오는 메소드(정렱 X)
		List<ProductDto> basket_list = productDao.savedBasketInfo(dto);
		
		//리스트를 그냥 가져오지않고 정렬해서 가져오기위한 sql문 실행(정렬 O)
		List<ProductDto> alignedBasketList = productDao.getAlignedBasket(dto);
		
		if(alignedBasketList.isEmpty()) {
			System.out.println("alignedBasketList가 존재하지 않습니다");
		}
		
		//장바구니 리스트 전체를 map 객체에 넣기
		listMap.put("basket_list", alignedBasketList);
		
		//특정 상품의 특정 사이즈의 X표시를 눌렀을 때(안 눌렀다면 null 값이 들어있음)
		if(dto.getXid() != null) {
			for(int i=0; i<dto.getCheckedItem().length; i++) {
				if(i == 0) {
					//선택된 아이템 항목들을 삭제하기위한 시작값 설정
					dto.setStartRnum(dto.getCheckedItem()[i]);
					System.out.println(dto.getCheckedItem()[i]);
				}
				if(i == dto.getCheckedItem().length-1) {
					//선택된 아이템 항목들을 삭제하기위한 끝값 설정
					dto.setEndRnum(dto.getCheckedItem()[i]);
					System.out.println(dto.getCheckedItem()[i]);
				}
			}
			//선택했던 체크박스의 장바구니 관련 항목을 삭제한다.
			productDao.selectedCheckBoxItemRemove(dto);
			
			//특정 물품의 특정 사이즈 삭제 후 정렬된 테이블 가져오기
			List<ProductDto> excludedList = productDao.getAlignedBasket(dto);
			
			for(int i=0; i<excludedList.size(); i++) {
				System.out.println(excludedList.get(i).getProductname());
			}
			
			//장바구니의 특정 상품의 특정 사이즈를 제외한 리스트 전체를 map 객체에 넣기
			listMap.put("basket_list", excludedList);
		}
		
		//DB에서 장바구니 관련 정보를 가져올 수 있는 지 없는지 판별할 변수
		boolean isValid = false;
		
		//만약 리스트가 존재하지 않을 경우
		if(alignedBasketList.size() == 0) {
			isValid = false; //장바구니에 접근 가능하다는 의미로 true
			listMap.put("isValid", isValid); 
		}
		else if(alignedBasketList.size() > 0){
			isValid = true; //장바구니에 접근 불가능하다는 의미로 false
			listMap.put("isValid", isValid); 
		}
		
//			//비교할 배열 선언 및 값 대입
//			List<String> compare_list = new ArrayList<String>();
//			compare_list.add("230");
//			compare_list.add("240");
//			compare_list.add("250");
//			compare_list.add("260");
//			compare_list.add("270");
//			compare_list.add("280");
//			
//			//각 수량, 사이즈, 가격에 맞는 데이터를 저장할 배열 선언 
//			List<String> sizeList = new ArrayList<String>();
//			List<Integer> quantityList = new ArrayList<Integer>();
//			List<Integer> priceList = new ArrayList<Integer>();
//			
//			//데이터 저장하는 반복문
//			for(int i=0; i<basket_list.size(); i++) {
//				sizeList.add(basket_list.get(i).getSelectedSize());
//				quantityList.add(basket_list.get(i).getSelectedQuantity());
//				priceList.add(basket_list.get(i).getSelectedPrice());
//				
//				//DB에 저장된 값 응답 페이지에서 쓸 수 있도록 mView에 할당
//				if(basket_list.get(i).getSelectedSize().equals("230")) {
//					listMap.put("product_price_230", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_230", basket_list.get(i).getSelectedQuantity());
//				}
//				else if(basket_list.get(i).getSelectedSize().equals("240")) {
//					listMap.put("product_price_240", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_240", basket_list.get(i).getSelectedQuantity());
//				}
//				else if(basket_list.get(i).getSelectedSize().equals("250")) {
//					listMap.put("product_price_250", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_250", basket_list.get(i).getSelectedQuantity());
//				}
//				else if(basket_list.get(i).getSelectedSize().equals("260")) {
//					listMap.put("product_price_260", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_260", basket_list.get(i).getSelectedQuantity());
//				}
//				else if(basket_list.get(i).getSelectedSize().equals("270")) {
//					listMap.put("product_price_270", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_270", basket_list.get(i).getSelectedQuantity());
//				}
//				else if(basket_list.get(i).getSelectedSize().equals("280")) {
//					listMap.put("product_price_280", basket_list.get(i).getSelectedPrice());
//					listMap.put("quantity_280", basket_list.get(i).getSelectedQuantity());
//				}
//				listMap.put("kind", basket_list.get(i).getKind());
//				listMap.put("productname", basket_list.get(i).getProductname());
//				listMap.put("totalQuantity", basket_list.get(i).getTotalQuantity());
//				listMap.put("totalPrice", basket_list.get(i).getTotalPrice());
//				listMap.put("initPrice", basket_list.get(i).getInitPrice());
//			}
//			listMap.put("buycount", sizeList.size());
//			
//			//장바구니 관련 DB에 저장되지 않은 데이터들을 알아내기위한 compare_list 배열의 정리.
//			for(int i=0; i<basket_list.size(); i++) {
//				for(int j=0; j<compare_list.size(); j++) {
//					if(basket_list.get(i).getSelectedSize().equals(compare_list.get(j))) {
//						compare_list.remove(j);
//					};
//				}
//			}
//			//DB에 저장되지 않았던 신발 사이즈의 수량,가격을 숫자 0으로 변환.
//			for(int i=0; i<compare_list.size(); i++) {
//				listMap.put("product_price_"+compare_list.get(i), 0);
//				listMap.put("quantity_"+compare_list.get(i), 0);
//			}
//			
//			listMap.put("sizeList", sizeList);
//			listMap.put("quantityList", quantityList);
//			listMap.put("priceList", priceList);
		return listMap;
	}
	//선택된 아이템 항목에 맞는 재고수량을 가져오는 메소드
	@Override
	public Map<String, ProductDto> getSelectedSbproductSub(ProductDto dto) {
		System.out.println("getSelectedSbproductSub dto.getNum() : "+dto.getNum());
		System.out.println("getSelectedSbproductSub dto.getNum() : "+dto.getSelectedSize());
		Map<String, ProductDto> resultMap = new HashMap<String, ProductDto>();
		ProductDto selectedSbproductSub = productDao.getSelectedSbproductSub(dto);
		resultMap.put("selectedSbproductSub", selectedSbproductSub);
		return resultMap;
	}
	
//	@Override
//	public void cookie_related(ProductDto dto, ModelAndView mView,
//			HttpServletResponse response) {
//		List<ProductDto> list=productDao.getData(dto.getNum());
//		mView.addObject("list",list);
//		
//		int[] sbsize= dto.getSizearr();
//		int[] sbcount= dto.getCountarr();
//		int[] sbprice= dto.getPricearr();
//		
//		int buycount = dto.getBuycount();
//		int totalQuantity = dto.getTotalQuantity();
//		int totalPrice = dto.getTotalPrice();
//		int initPrice = dto.getInitPrice();
//		int product_price_230 = dto.getProduct_price_230();
//		int product_price_240 = dto.getProduct_price_240();
//		int product_price_250 = dto.getProduct_price_250();
//		int product_price_260 = dto.getProduct_price_260();
//		int product_price_270 = dto.getProduct_price_270();
//		int product_price_280 = dto.getProduct_price_280();
//		int quantity_230 = dto.getQuantity_230();
//		int quantity_240 = dto.getQuantity_240();
//		int quantity_250 = dto.getQuantity_250();
//		int quantity_260 = dto.getQuantity_260();
//		int quantity_270 = dto.getQuantity_270();
//		int quantity_280 = dto.getQuantity_280();
//		
//		Cookie buycount_cook=new Cookie("buycount", Integer.toString(buycount));
//		buycount_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(buycount_cook);
//		Cookie totalQuantity_cook=new Cookie("totalQuantity", Integer.toString(totalQuantity));
//		totalQuantity_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(totalQuantity_cook);
//		Cookie totalPrice_cook=new Cookie("totalPrice", Integer.toString(totalPrice));
//		totalPrice_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(totalPrice_cook);
//		Cookie initPrice_cook=new Cookie("initPrice", Integer.toString(initPrice));
//		initPrice_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(initPrice_cook);
//		Cookie product_price_230_cook=new Cookie("product_price_230", Integer.toString(product_price_230));
//		product_price_230_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_230_cook);
//		Cookie product_price_240_cook=new Cookie("product_price_240", Integer.toString(product_price_240));
//		product_price_240_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_240_cook);
//		Cookie product_price_250_cook=new Cookie("product_price_250", Integer.toString(product_price_250));
//		product_price_250_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_250_cook);
//		Cookie product_price_260_cook=new Cookie("product_price_260", Integer.toString(product_price_260));
//		product_price_260_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_260_cook);
//		Cookie product_price_270_cook=new Cookie("product_price_270", Integer.toString(product_price_270));
//		product_price_270_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_270_cook);
//		Cookie product_price_280_cook=new Cookie("product_price_280", Integer.toString(product_price_280));
//		product_price_280_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(product_price_280_cook);
//		
//		Cookie quantity_230_cook=new Cookie("quantity_230", Integer.toString(quantity_230));
//		quantity_230_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_230_cook);
//		Cookie quantity_240_cook=new Cookie("quantity_240", Integer.toString(quantity_240));
//		quantity_240_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_240_cook);
//		Cookie quantity_250_cook=new Cookie("quantity_250", Integer.toString(quantity_250));
//		quantity_250_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_250_cook);
//		Cookie quantity_260_cook=new Cookie("quantity_260", Integer.toString(quantity_260));
//		quantity_260_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_260_cook);
//		Cookie quantity_270_cook=new Cookie("quantity_270", Integer.toString(quantity_270));
//		quantity_270_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_270_cook);
//		Cookie quantity_280_cook=new Cookie("quantity_280", Integer.toString(quantity_280));
//		quantity_230_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//		response.addCookie(quantity_230_cook);
//		
//		String[] selectedSizeArr = dto.getSelectedSize();
//		
//		//선택한 사이즈들의 배열 객체를 얻기위해서 먼저 문자열로 바꾼다.(자바스크립트 부분에서 객체화할 예정)
//		ObjectMapper mapper = new ObjectMapper();
//		String selectedSize;
//		try {
//			selectedSize = mapper.writeValueAsString( selectedSizeArr );
////			mView.addObject( "selectedSize", selectedSize );
//			Cookie selectedSize_cook=new Cookie("selectedSize", selectedSize);
//			selectedSize_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//			response.addCookie(selectedSize_cook);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////		mView.addObject("sbsize",sbsize);
////		mView.addObject("sbcount",sbcount);
////		mView.addObject("sbprice",sbprice);
////		mView.addObject("buycount",buycount);
////		mView.addObject("totalQuantity",totalQuantity);
////		mView.addObject("totalPrice",totalPrice);
////		mView.addObject("initPrice", initPrice);
////		mView.addObject("sbdto",dto);
////		
////		mView.addObject("product_price_230", product_price_230);
////		mView.addObject("product_price_240", product_price_240);
////		mView.addObject("product_price_250", product_price_250);
////		mView.addObject("product_price_260", product_price_260);
////		mView.addObject("product_price_270", product_price_270);
////		mView.addObject("product_price_280", product_price_280);
////		
////		mView.addObject("quantity_230", quantity_230);
////		mView.addObject("quantity_240", quantity_240);
////		mView.addObject("quantity_250", quantity_250);
////		mView.addObject("quantity_260", quantity_260);
////		mView.addObject("quantity_270", quantity_270);
////		mView.addObject("quantity_280", quantity_280);
//		
//		//int[] 배열을 String[] 배열로 바꾸기 위한 sizeArr 배열 생성
//		String[] sizeArr = Arrays.stream(dto.getSizearr()).mapToObj(String::valueOf).toArray(String[]::new);
//		System.out.println(Arrays.toString(sizeArr));
//		for(int i=0; i<sizeArr.length; i++) {
//			if(sizeArr[i].equals("230")) {
//				Cookie size_230_cook=new Cookie("size_230", "230");
//				size_230_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_230_cook);
//			}
//			else if(sizeArr[i].equals("240")) {
//				Cookie size_240_cook=new Cookie("size_240", "240");
//				size_240_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_240_cook);
//			}
//			else if(sizeArr[i].equals("250")) {
//				Cookie size_250_cook=new Cookie("size_250", "250");
//				size_250_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_250_cook);
//			}
//			else if(sizeArr[i].equals("260")) {
//				Cookie size_260_cook=new Cookie("size_260", "260");
//				size_260_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_260_cook);
//			}
//			else if(sizeArr[i].equals("270")) {
//				Cookie size_270_cook=new Cookie("size_270", "270");
//				size_270_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_270_cook);
//			}
//			else if(sizeArr[i].equals("280")) {
//				Cookie size_280_cook=new Cookie("size_280", "280");
//				size_280_cook.setMaxAge(60*30);//30분 동안 쿠키 지정 
//				response.addCookie(size_280_cook);
//			}
//		}
//		System.out.println("cookie_related 함수 실행됨");
//	}
//	//장바구니에 저장된 쿠키 읽어오는 메소드
//	@Override
//	public ModelAndView basketcookie_read(ModelAndView mView, HttpServletRequest request) {
//		//쿠키에 저장된 것을 담을 변수
//		int buycount = 0;
//		int totalQuantity = 0;
//		int totalPrice = 0;
//		int initPrice = 0;
//		int product_price_230 = 0;
//		int product_price_240 = 0;
//		int product_price_250 = 0;
//		int product_price_260 = 0;
//		int product_price_270 = 0;
//		int product_price_280 = 0;
//		int quantity_230 = 0;
//		int quantity_240 = 0;
//		int quantity_250 = 0;
//		int quantity_260 = 0;
//		int quantity_270 = 0;
//		int quantity_280 = 0;
//		String selectedSize = null;
//		String size_230 = null;
//		String size_240 = null;
//		String size_250 = null;
//		String size_260 = null;
//		String size_270 = null;
//		String size_280 = null;
//		
//		//쿠키에 저장된 값을 위의 변수에 저장하는 코드를 작성해 보세요.
//		Cookie[] cooks=request.getCookies();
//		if(cooks!=null){
//			//반복문 돌면서 쿠키객체를 하나씩 참조해서 
//			for(Cookie tmp: cooks){
//				//저장된 키값을 읽어온다.
//				String key=tmp.getName();
//				//만일 키값이 savedId 라면 
//				if(key.equals("buycount")){
//					//쿠키 value 값을 savedId 라는 지역변수에 저장
//					buycount=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("totalQuantity")){
//					totalQuantity=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("totalPrice")){
//					totalPrice=Integer.parseInt(tmp.getValue());
//				}				
//				if(key.equals("initPrice")){
//					initPrice=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_230")){
//					product_price_230=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_240")){
//					product_price_240=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_250")){
//					product_price_250=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_260")){
//					product_price_260=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_270")){
//					product_price_270=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("product_price_280")){
//					product_price_280=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_230")){
//					quantity_230=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_240")){
//					quantity_240=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_250")){
//					quantity_250=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_260")){
//					quantity_260=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_270")){
//					quantity_270=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("quantity_280")){
//					quantity_280=Integer.parseInt(tmp.getValue());
//				}
//				if(key.equals("selectedSize")){
//					selectedSize=tmp.getValue();
//				}
//				if(key.equals("size_230")){
//					size_230=tmp.getValue();
//				}
//				if(key.equals("size_240")){
//					size_240=tmp.getValue();
//				}
//				if(key.equals("size_250")){
//					size_250=tmp.getValue();
//				}
//				if(key.equals("size_260")){
//					size_260=tmp.getValue();
//				}
//				if(key.equals("size_270")){
//					size_270=tmp.getValue();
//				}
//				if(key.equals("size_280")){
//					size_280=tmp.getValue();
//				}
//			}
//		}
//		
//		//쿠키 정보를 ModelAndView 객체에 저장.
//		mView.addObject("buycount", buycount);
//		mView.addObject("totalQuantity", totalQuantity);
//		mView.addObject("totalPrice", totalPrice);
//		mView.addObject("initPrice", initPrice);
//		mView.addObject("product_price_230", product_price_230);
//		mView.addObject("product_price_240", product_price_240);
//		mView.addObject("product_price_250", product_price_250);
//		mView.addObject("product_price_260", product_price_260);
//		mView.addObject("product_price_270", product_price_270);
//		mView.addObject("product_price_280", product_price_280);
//		mView.addObject("quantity_230", quantity_230);
//		mView.addObject("quantity_240", quantity_240);
//		mView.addObject("quantity_250", quantity_250);
//		mView.addObject("quantity_260", quantity_260);
//		mView.addObject("quantity_270", quantity_270);
//		mView.addObject("quantity_280", quantity_280);
//		mView.addObject("selectedSize", selectedSize);
//		mView.addObject("size_230", size_230);
//		mView.addObject("size_240", size_240);
//		mView.addObject("size_250", size_250);
//		mView.addObject("size_260", size_260);
//		mView.addObject("size_270", size_270);
//		mView.addObject("size_280", size_280);
//		
//		System.out.println("basketcookie_read 함수 실행됨");
//		
//		return mView;
//	}
}
