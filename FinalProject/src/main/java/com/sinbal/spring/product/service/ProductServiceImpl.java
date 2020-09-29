package com.sinbal.spring.product.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sinbal.spring.product.dao.ProductDao;
import com.sinbal.spring.product.dto.ProductDto;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	
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
}
