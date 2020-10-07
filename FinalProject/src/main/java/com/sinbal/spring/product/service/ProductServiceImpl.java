package com.sinbal.spring.product.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.sinbal.spring.shop.dto.ShopDto;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductReviewDao productReviewDao;
	
	//한 페이지에 나타낼 댓글 의 갯수
	final int PAGE_ROW_COUNT=9;
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
	public void productdelete(int num) {
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
			}
			String addr=dto.getZipNo()+dto.getRoadAddrPart1()+dto.getAddrDetail();
			dto.setSboption(sboption);
			dto.setAddr(addr);
			
			//상품을 주문하는 dao
			orderDao.order(dto);
			//가지고있는 계좌에서 금액을 감소시킨다.
			orderDao.minus_money(dto);
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
		int totalPrice = dto.getTotalPrice();
		
		mView.addObject("sbsize",sbsize);
		mView.addObject("sbcount",sbcount);
		mView.addObject("sbprice",sbprice);
		mView.addObject("sbdto",dto);
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
		
		//글목록과 페이징 처리에 관련된 값을 담을 Map 객체 생성
		Map<String, Object> map=new HashMap<>();
		//글목록을 전체 Map 에 담아준다. 
		map.put("commentList", commentList);
		
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
		
		mView.addObject("list",orderDao.order_list(id));
		
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
	}
}
