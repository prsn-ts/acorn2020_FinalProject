<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html data-ng-app="myApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>

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
	// IE에서 opener관련 오류가 발생하는 경우, window에 이름을 명시해줍니다.
	window.name="jusoPopup";
	
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("../../addr/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}


function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		
		document.form.roadAddrPart1.value = roadAddrPart1;
		document.form.addrDetail.value = addrDetail;
		document.form.zipNo.value = zipNo;

}


</script>
<script>

var myApp=angular.module("myApp", []);

myApp.controller("insert_Ctrl", function($scope, $http){
	//angularjs  가 초기화 될때 최초 한번만 호출된다.
	//휴대폰 입력창에 keyup 이벤트가 일어났을 때 호출될 함수 등록
	$scope.phone_num_input=function(e){

		//입력받은 값을 저장.
		$scope.phone_num_comp = e.target.value;
	};
	
})
</script>
<title>주소 입력 샘플</title>
</head>
<body data-ng-controller="insert_Ctrl">
<jsp:include page="../../include/header.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>

<form action="${pageContext.request.contextPath }/shop/private/buy.do" name="form" id="form" method="post" class="form">
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
			<th>상품번호</th>
			<input type="hidden" name="productnum" value="${sbdto.num }" />
			<td>${sbdto.num }</td>
		</tr>		
		<tr>
			<th>ID</th>
			<input type="hidden" name="id" value="${dto.id }" />
			<td>${dto.id }</td>
		</tr>	
		<tr>
			<th>받는사람</th>
			<td><input type="text"  name="name"
			ng-model="name" required
			/></td>
		</tr>				
		<tr>
			<th rowspan="3">주소</th>
			
			<td><input type="text"  style="" id="zipNo" required name="zipNo"   />
			<input type="button" onClick="goPopup();" value="우편번호"/>
			</td>
		</tr>
		<tr>
			
			<td><input type="text"  style="width:300px;" id="roadAddrPart1"  name="roadAddrPart1" required /></td>
		</tr>
		<tr>
			
			<td><input type="text"  style="width:300px;" id="addrDetail"  name="addrDetail" required    /></td>
		</tr>
		<tr>
			<th rowspan="2">연락처</th>
			<td><input type="text"  required name="phone_num"  id="phone_num" class="form-control"
			ng-model="phone_num" 	
			data-ng-class="{'is-invalid': (form.phone_num.$dirty&&form.phone_num.$invalid &&  phone_num_comp.length!=0)}"
			data-ng-keyup="phone_num_input($event)"
			data-ng-pattern="/^01(?:0|1|[6-9])-\d{3,4}-\d{4}$/"/></td>
			
			
		</tr>
		<tr><td><small class="text-muted">휴대폰 번호는 010-1111-2222 형식으로 적어주세요.</small></td>	</tr>
		<tr>
			<th>배송시 요청사항</th>
			<td>
			  <div class="form-group">
			    <select class="form-control" name="sendrequest" model="selectpost">
			      <option value="배송시 요청사항(선택)">배송시 요청사항(선택)</option>
			      <option value="부재시, 전화 또는 문자주세요.">부재시, 전화 또는 문자주세요.</option>
			      <option value="배송전, 연락바랍니다.">배송전, 연락바랍니다.</option>
			      <option value="부재시, 경비실에맡겨주세요.">부재시, 경비실에 맡겨주세요.</option>

			    </select>
			  </div>			
			
			</td> 

		</tr>						
	</table>
	</div>
	

	<div class="col-lg-5">
		<c:forEach var="i" begin="0" end="${fn:length(sbsize)-1 }" step="1">
		<div class="card"style="margin-bottom: 10px;" >
		  <div class="card-body">
		  <div class="row">
		  <div class="col-4">
		  <img class="profile"src="${pageContext.request.contextPath }${list[0].profile}" alt="profile" />
		  </div>
		  <div class="col-8">
		  	<h5 class="card-title">${list[0].productname }</h5>
		    <h5 class="card-subtitle mb-2 text-muted">size: ${sbsize[i] } / ${sbcount[i] } 개 </h6>
		    <h4 class="card-text"> ${sbprice[i] }</h3>
		  </div>
		  </div>
		  </div>
		</div>				
		<input type="hidden" name="sizearr" value="${sbsize[i]}" />
		<input type="hidden" name="countarr" value="${sbcount[i] }" />
		</c:forEach>
		<div class="card"style="margin-bottom: 10px;text-align:center;" >
		  <div class="card-body">
		    <h3 class="card-title">최종결제금액</h5>
			<input type="hidden" name="totalPrice" value="${sbdto.totalPrice }" />
		    <h2 class="card-text"> ${sbdto.totalPrice } 원</h3>
		      </div>
		</div>							
		<button data-ng-disabled="form.$invalid" class="btn btn-primary btn-block"type="submit">결제하기</button>
		
	</div> <!-- div col-lg-5 -->

	</div>


</div>
</form>

<br />

<jsp:include page="../../include/footer.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>
</body>
</html>