<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>


<style>
	.profile{
		width:100px;
		height:100px;
		
	}
</style>
<script language="javascript">
// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("팝업API 호출 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function goPopup(){
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}


function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		
		document.form.roadAddrPart1.value = roadAddrPart1;
		document.form.addrDetail.value = addrDetail;
		document.form.zipNo.value = zipNo;

}

</script>
<title>주소 입력 샘플</title>
</head>
<body>
<jsp:include page="include/header.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>

<form action="hello.do" name="form" id="form" method="post">
<div class="container">
	<h3>상품 구매 페이지 입니다.</h3>
	<div class="row">
	<div class="col-lg-7">	
	<h4>배송지선택</h4>
	<br />
	<table class="table">
		<colgroup>
            <col width="30%" />
            <col width="*" />
		</colgroup>
		<tr>
			<th>보내는사람</th>
			<td><input type="text" /></td>
		</tr>			
		<tr>
			<th rowspan="3">주소</th>
			<td><input type="text"  style="" id="zipNo"  name="zipNo" />
			<input type="button" onClick="goPopup();" value="우편번호"/>
			</td>
		</tr>
		<tr>
			<td><input type="text"  style="width:300px;" id="roadAddrPart1"  name="roadAddrPart1" /></td>
		</tr>
		<tr>
			<td><input type="text"  style="width:300px;" id="addrDetail"  name="addrDetail" /></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td><input type="text" /></td>
		</tr>
		<tr>
			<th>배송시 요청사항</th>
			<td>
			  <div class="form-group">
			    <select class="form-control" name="talk">
			      <option>배송시 요청사항(선택)</option>
			      <option>부재시, 전화 또는 문자주세요.</option>
			      <option>배송전, 연락바랍니다.</option>
			      <option>부재시, 전화 또는 문자 주세요.</option>
			      <option>부재시, 경비실에 맡겨주세요.</option>
			      <option>직접입력</option>
			    </select>
			  </div>			
			
			</td> 

		</tr>						
	</table>
	</div>
	

	<div class="col-lg-5">
		<div class="card"style="margin-bottom: 10px;" >
		  <div class="card-body">
		  <div class="row">
		  <div class="col-4">
		  <img class="profile"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />
		  </div>
		  <div class="col-8">
		  	<h5 class="card-title">삼선슬리퍼</h5>
		    <h5 class="card-subtitle mb-2 text-muted">size: 255</h6>
		    <h4 class="card-text"> 8000원 /2개</h3>
		  </div>
		  </div>
		  </div>
		</div>	
		<div class="card"style="margin-bottom: 10px;" >
		  <div class="card-body">
		  <div class="row">
		  <div class="col-4">
		  <img class="profile"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />
		  </div>
		  <div class="col-8">
		  	<h5 class="card-title">삼선슬리퍼</h5>
		    <h5 class="card-subtitle mb-2 text-muted">size: 255</h6>
		    <h4 class="card-text"> 8000원 /2개</h3>
		  </div>
		  </div>
		  </div>
		</div>		
		
		<div class="card"style="margin-bottom: 10px;text-align:center;" >
		  <div class="card-body">
		    <h3 class="card-title">최종결제금액</h5>

		    <h2 class="card-text"> 30000 원</h3>
		      </div>
		</div>							
		<button class="btn btn-primary btn-block"type="submit">결제하기</button>
		
	</div> <!-- div col-lg-5 -->

	</div>


</div>
</form>

<br />

<jsp:include page="include/footer.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>
</body>
</html>