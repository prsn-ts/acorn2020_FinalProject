package com.sinbal.spring.product.service;

import java.io.File;
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
import com.sinbal.spring.product.dao.ProductDao;
import com.sinbal.spring.product.dto.ProductDto;
import com.sinbal.spring.shop.dao.OrderDao;
import com.sinbal.spring.shop.dto.OrderDto;
import com.sinbal.spring.shop.dto.ShopDto;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	
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
	public void getList(ModelAndView mView) {
		
		
		mView.addObject("list",productDao.getList());
		
		
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
	public void getProductData(ModelAndView mView, int num) {
		//상품 정보를 가져온다.
		ProductDto dto = productDao.getData2(num);
		//신발 사이즈, 신발 수량 정보를 가져온다.
		List<ProductDto> dto_sub = productDao.getSubData(num);
		//mView에 담는다.
		mView.addObject("productDto", dto);
		mView.addObject("productDto_sub", dto_sub);
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
}
