<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>상세보기 페이지</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<!-- angularjs 로딩 -->
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<style>
	.first_container {
		display:flex;
		margin-top: 100px; 
	}
	.left_side {
		width: 450px; height: 450px;
		padding:20px;
		margin-right:15px;
		/* border: 1px solid blue; */
		flex: 1 1 auto;
	}
	.right_side {
		width: 450px; height: 450px;
		padding:20px;
		/* border: 1px solid blue; */
		flex: 1 1 auto;
		/* position: relative; */
	}
	/*
	.right_side .right_side_children {
		position: absolute;
		width: 90%;
	    top: 50%;
	    left: 50%;
	    transform: translate(-50%, -50%);
	    -webkit-transform: translate(-50%, -50%);
	    -moz-transform: translate(-50%, -50%);
	    -o-transform: translate(-50%, -50%);
	}
	*/
	.updateAndCancel {
		display: flex; 
		justify-content: space-between;
		margin: 20px;
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 15px;
	}
	#product_info {
		display:flex; 
		flex-direction:column-reverse;
	}
	.product_info {
		margin-top:15px; 
		border: 1px solid #E5E5E5; 
		background-color: #F9F9FA;
	}
	.product_info .kindAndName {
		 padding:20px 15px;
	}
	.product_info .quantity {
		 padding:10px 15px;
	}
	.product_info .quantity input {
		 width:28px; height:28px; vertical-align: text-bottom;
		 text-align:center;
	}
	.quantity a:hover i {
		color: blue;
	}
	
</style>
<script>
	var myApp=angular.module("myApp", []);

	myApp.controller("detail_Ctrl", function($scope, $http){
		
		//초기값 설정.
		$scope.quantity = 1;
		$scope.size = "nosize";
		$scope.product_info_230 = false;
		$scope.product_info_240 = false;
		$scope.product_info_250 = false;
		$scope.product_info_260 = false;
		$scope.product_info_270 = false;
		$scope.product_info_280 = false;
		

		$scope.quantity_plus=function(e){
			//특정 사이즈를 구분하기위해 설정.
			$scope.size = e.target.id;
			//재고가 DB에 존재하는 지 여부를 ajax 요청 처리
			$http({
				method: 'POST',
				url:"${pageContext.request.contextPath}/shop/checkQuantity.do",
				data: $.param({size:$scope.size, num:${productDto.num}}),
			    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			}).success(function(data){
				switch ($scope.size) {
				  case "230":
					if($scope.quantity_230 < data.sbcount){
						$scope.quantity_230 = $scope.quantity_230+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "240":
					if($scope.quantity_240 < data.sbcount){
							$scope.quantity_240 = $scope.quantity_240+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "250":
					if($scope.quantity_250 < data.sbcount){
							$scope.quantity_250 = $scope.quantity_250+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "260":
					if($scope.quantity_260 < data.sbcount){
							$scope.quantity_260 = $scope.quantity_260+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
					break;
				  case "270":
					if($scope.quantity_270 < data.sbcount){
							$scope.quantity_270 = $scope.quantity_270+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  default:
					if($scope.quantity_280 < data.sbcount){
							$scope.quantity_280 = $scope.quantity_280+1;
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				}
			});
		};
		$scope.quantity_minus=function(e){
			//특정 사이즈를 구분하기위해 설정.
			$scope.size = e.target.id;
			switch ($scope.size) {
			  case "230":
				if($scope.quantity_230 != 1){
					$scope.quantity_230 = $scope.quantity_230-1;
				}
			    break;
			  case "240":
				if($scope.quantity_240 != 1){
					$scope.quantity_240 = $scope.quantity_240-1;
				}
			    break;
			  case "250":
			    if($scope.quantity_250 != 1){
					$scope.quantity_250 = $scope.quantity_250-1;
				}
			    break;
			  case "260":
			  	if($scope.quantity_260 != 1){
					$scope.quantity_260 = $scope.quantity_260-1;
				}
				break;
			  case "270":
			  	if($scope.quantity_270 != 1){
					$scope.quantity_270 = $scope.quantity_270-1;
				}
			    break;
			  default:
			  	if($scope.quantity_280 != 1){
					$scope.quantity_280 = $scope.quantity_280-1;
				}
			}
		};
		
		//사이즈가 변경되었을 때 호출될 함수
		$scope.size_change=function(){
			switch ($scope.size) {
			  case "230":
				//사이즈 변경 했을 때 초기화 설정.
				$scope.quantity_230 = 1;
			    break;
			  case "240":
				$scope.quantity_240 = 1;
			    break;
			  case "250":
				$scope.quantity_250 = 1;
			    break;
			  case "260":
				$scope.quantity_260 = 1;
				break;
			  case "270":
				$scope.quantity_270 = 1;
			    break;
			  default:
				$scope.quantity_280 = 1;
			}
			
			if($scope.size != "nosize"){
				switch ($scope.size) {
				  case "230":
					$scope.product_info_230 = true;
				    break;
				  case "240":
					$scope.product_info_240 = true;
				    break;
				  case "250":
					$scope.product_info_250 = true;
				    break;
				  case "260":
					$scope.product_info_260 = true;
					break;
				  case "270":
					$scope.product_info_270 = true;
				    break;
				  default:
					$scope.product_info_280 = true;
				}
			}
		};
		
		//사이즈 박스 X표시 눌렀을 때 실행할 함수.
		$scope.product_info_delete=function(e){
			var tempStr = null;
			//x버튼이 포함된 요소(product_info)의 참조값을 가져온다.
			for(var i=0; i<$scope.dinamic_el[0].children.length; i++){ //동적으로 생성되는 요소만큼 반복문을 돈다.
				tempStr = $scope.dinamic_el[0].children[i].innerText; //동적으로 생성되는 product_info 클래스 요소의 내용.
				var product_info_el = tempStr.substr(tempStr.length-3, 3); //원하는 숫자값만 추출에서 product_info_el 변수에 대입.(결과적으로 사이즈 값이 들어있다.)
				if(product_info_el == e.target.id){ //누른 x버튼과 해당하는 사이즈 박스와 값이 일치한다면
					var delete_el = $scope.dinamic_el[0].children[i];	//제거하고자 하는 엘리먼트
					var dinamic_el = $scope.dinamic_el[0];	// 그 엘리먼트의 부모 객체
					dinamic_el.removeChild(delete_el); //그 요소를 삭제한다.
					for(var i=0; i<$scope.productInfo_duplicate_check.length; i++){ //선택된 사이즈가 저장된 배열의 길이만큼 반복.(요소를 동적으로 제거하기위한 것.)
						if($scope.productInfo_duplicate_check[i] == product_info_el){ //제거하고자 하는 엘리먼트의 문자열과 배열안에 문자열이 같으면
							$scope.productInfo_duplicate_check.splice(i, 1); // 같은 문자열이 들은 배열의 인덱스를 삭제한다.
							$scope.productInfo_index--; //선택한 사이즈의 개수를 알려주는 index 값을 하나 줄인다.(삭제했기 때문)
							$("#size").find("option:eq(0)").prop("selected", true); //스크립트로 추가된 요소는 prop 으로 제어가능.(x버튼 눌렀을 때 "사이즈 선택" 옵션이 보이도록, change 이벤트 적용을 위해)
						}
					}
				}
			}
		};
	});
	
	myApp.directive("addelements", function($compile, $http) {
		
		return function(scope, element, attrs) {
			//선택한 사이즈 항목의 개수만큼 동적으로 요소 추가를 관리할 변수 선언.
			scope.productInfo_index=0;
			//사이즈 중복으로 눌렀는지 검사하는 변수.
			scope.productInfo_duplicate_check = [];
			element.bind("change", function(e) {
				//선택할 수 있는 신발 사이즈 항목의 개수를 ajax 요청 처리
				$http({
					method: 'POST',
					url:"${pageContext.request.contextPath}/shop/checkSize.do",
					data: $.param({num:${productDto.num}}),
				    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
				}).success(function(data){
					if(scope.productInfo_index < data.sbsize){
						for(var i=0; i<data.sbsize; i++){
							if(scope.size == "nosize"){ //사이즈 선택 항목을 눌렀을 대
								return; //요소 생성을 막는다.
							}
							if(scope.productInfo_duplicate_check[i] == scope.size){
								alert("동일한 상품의 옵션을 선택하셨습니다.");
								return; //요소 생성을 막는다.
							}else{//배열안에 값과 선택된 사이즈의 값이 같지 않을 때
								if(typeof scope.productInfo_duplicate_check[i] == 'undefined'){ //배열안에 값이 존재하지 않으면
									scope.productInfo_duplicate_check[i]=scope.size; //선택된 사이즈의 값을 넣어준다.
									break;
								}else{ //배열안에 값이 존재할 경우
									continue;
								}
							}
						}
						var e1 = angular.element(document.querySelector("#product_info"));
						scope.dinamic_el = e1; // 요소의 참조값 저장.
						e1.append($compile("<div class=product_info data-ng-model=product_info_"+scope.size+"></div>")(scope));
						var product_info = angular.element(e1[0].children[scope.productInfo_index]);
						product_info.append($compile("<div class='kindAndName'>${productDto.kind } - <strong>${productDto.productname }</strong><br />"+scope.size+"</div>")(scope));
						product_info.append($compile("<hr style='margin:0 0 5px 0;'/>")(scope));
						product_info.append($compile("<div class='quantity' style='display:flex; justify-content:space-between;'></div>")(scope));
						var quantity = angular.element(product_info[0].children[2]);
						quantity.append($compile("<div></div>")(scope));
						var quantity_first_chlid = angular.element(quantity[0].children[0]);
						quantity_first_chlid.append($compile("<a href='' data-ng-click='quantity_minus($event)' style='color:#000000;'><i class='fa fa-minus-circle fa-2x' id='"+scope.size+"' aria-hidden='true' style='margin-right:5px;'></i></a>")(scope));
						quantity_first_chlid.append($compile("<input type='text' data-ng-model='quantity_"+scope.size+"' value='quantity_"+scope.size+"'/>")(scope));
						quantity_first_chlid.append($compile("<a href='' data-ng-click='quantity_plus($event)' style='color:#000000;'><i class='fa fa-plus-circle fa-2x' id='"+scope.size+"' aria-hidden='true' style='margin-left:5px;'></i></a>")(scope));
						quantity.append($compile("<a href='' data-ng-click='product_info_delete($event)' style='color:#000000;'><i class='fa fa-times fa-2x ' id="+scope.size+" aria-hidden='true' style='text-align:right;'></i></a>")(scope));
						
						scope.productInfo_index++; //생성 후 1증가 시킴.
					}else{
						//사이즈 선택 항목을 눌렀을 때는 뜨지 않도록 처리.
						if(scope.size != "nosize"){
							alert("동일한 상품의 옵션을 선택하셨습니다.");
						}
					}
				});
			});
		};
	});
</script>
<body data-ng-controller="detail_Ctrl">
	<jsp:include page="../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container first_container">
		<img class="card-img-top left_side" src="${pageContext.request.contextPath }${productDto.profile}" alt="">
		<div class="right_side">
			<div class="right_side_children">
				<div style="text-align:center;">
					<h2 style="margin-bottom:1rem;"><img style="width:1.5em;height:1.5em;margin-right:15px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="logo" />상품 정보</h2>
				</div>
				<form action="" name="product" method="post">
				    <table class="table" style="margin-right:20px;">
						<tr>
							<th>상품명</th>
							<td>${productDto.productname }</td>
						</tr>
						<tr>
							<th>상품가격</th>
							<td>${productDto.price }</td>
						</tr>
					</table>
					<select class="custom-select mr-sm-2" id="size" data-ng-required="true" data-ng-model="size" data-ng-change="size_change($event)" addelements>
						<option selected value="nosize" data-ng-model="nosize">사이즈 선택</option>
						<c:forEach var="tmp" items="${productDto_sub }">
							<option value="${tmp.sbsize }">${tmp.sbsize } - 남은 수량(${tmp.sbcount })</option>
						</c:forEach>
				    </select>
					<div id="product_info"></div>
				    <!-- 
				    <div class="product_info" data-ng-model="product_info_230" data-ng-if="product_info_230">
				    	<div class="kindAndName">
				    		${productDto.kind } - <strong>${productDto.productname }</strong>
				    	</div>
				    	<hr style="margin:0 0 5px 0;"/>
				    	<div class="quantity">
						    <a href="" data-ng-click="quantity_minus()" style="color:#000000;"><i class="fa fa-minus-circle fa-2x" aria-hidden="true"></i></a>
						    <input type="text" data-ng-model="quantity_230" value="quantity"/>
						    <a href="" data-ng-click="quantity_plus()" style="color:#000000;"><i class="fa fa-plus-circle fa-2x" aria-hidden="true"></i></a>
					    </div>
				    </div>
				    -->
					<div class="updateAndCancel">
						<button type="reset" class="btn btn-success btn-block" 
							onclick="window.location.href='${pageContext.request.contextPath}/home.do'">장바구니</button>
						<button id="submitBtn" type="submit" style="margin: 0 0 0 20px;"
							data-ng-disabled="user.$invalid" class="btn btn-primary btn-block">구매하기</button>
				    </div>
				    <button type="button" onclick="location.href='productupdate.do?num=${param.num }'">수정</button>
					<button type="button"onclick="confirm('삭제하시겠습니까?');location.href='productdelete.do?num=${param.num }'">삭제</button>
				</form>
			</div>
	    </div>
	</div>
</body>
</html>