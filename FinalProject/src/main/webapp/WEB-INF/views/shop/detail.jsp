<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>상세보기 페이지</title>
<!-- 글씨체 관련 -->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
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
		width: 450px;
		padding:20px;
		margin-right:15px;
		/* border: 1px solid blue; */
		flex: 1 1 auto;
	}
	.right_side {
		width: 450px;
		padding:20px;
		/* border: 1px solid blue; */
		flex: 1 1 auto;
	}
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
	.product_info .quantity input.quantity_input {
		width:35px; height:28px; vertical-align: text-bottom;
		text-align:center;
	}
	.product_info .quantity input {
		width:90px; height: 30px;
		text-align:center;
	}
	.quantity a:hover i {
		color: blue;
	}
	.price {
		vertical-align:text-bottom;
		margin-right:5px;
	}
	.all_price {
		text-align:right;
		margin-top: 20px;
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 20px;
	}
	.all_price span {
		margin-right: 60px;
	}
	.statusBar {
		width: auto;
	    height: 60px;
	    background-color: #A9B3BC;
		text-align:center;
	}
	.statusBar .background {
		width: fit-content;
		border-radius: 30px;
		height: 40px;
		background-color: #595959;
		padding:0 20px;
		transform: translateY(10px);
		margin: 0 auto;
	}
	.statusBar .background span {
		color: #ffffff;
	    line-height: 40px;
	}
	/* 
		댓글 관련 css
	*/
	/* ul 요소의 기본 스타일 제거 */
	.comments ul{
		padding: 0;
		margin: 0;
		list-style-type: none;
	}
	.comments dt{
		margin-top: 5px;
	}
	.comments dd{
		margin-top: 7px;
	}
	.comment-form textarea, .comment-form button{
		float: left;
	}
	.comments li{
		clear: left;
	}
	.comments ul li{
		border-bottom: 1px solid #E0E0E0;
		margin-top: 10px;
	}
	.comment-form textarea{
		width: 85%;
		height: 100px;
	}
	.comment-form button{
		margin-left:2%;
		width: 13%;
		height: 100px;
	}
	/* 댓글에 댓글을 다는 폼과 수정폼은 일단 숨긴다. */
	.comments .comment-form{
		display: none;
	}
	/* .reply_icon 을 li 요소를 기준으로 배치 하기 */
	.comments li{
		position: relative;
	}
	.comments .reply-icon{
		position: absolute;
		left: 1em;
		color: red;
	}
	pre {
	  display: block;
	  padding: 9.5px;
	  margin: 0 0 10px;
	  font-size: 13px;
	  line-height: 1.42857143;
	  color: #333333;
	  word-break: break-all;
	  word-wrap: break-word;
	  background-color: #f5f5f5;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	}
	/* 글 내용중에 이미지가 있으면 최대 폭을 100%로 제한하기 */
	.contents img{
		max-width: 100%;
	}
	.productReviewLetter {
		margin: 50px 0 10px 0;
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 20px;
	}
	.pagination {
         justify-content: center;
    }
</style>
<script>
	var myApp=angular.module("myApp", []);

	myApp.controller("detail_Ctrl", function($scope, $http){
		
		console.log($scope);
		
		//초기값 설정.
		$scope.size = "nosize";
		//선택안할 시 처음 가격을 0원으로 설정
		$scope.product_price_230 = 0;
		$scope.product_price_240 = 0;
		$scope.product_price_250 = 0;
		$scope.product_price_260 = 0;
		$scope.product_price_270 = 0;
		$scope.product_price_280 = 0;
		$scope.product_all_price = 0;
		$scope.product_init_price = ${productDto.price }; //상품 가격을 나타냄.
		
		//전체 수량
		$scope.quantity_all = 0; 
		//각 사이즈별 수량
		$scope.quantity_230 = 0; 
		$scope.quantity_240 = 0;
		$scope.quantity_250 = 0;
		$scope.quantity_260 = 0;
		$scope.quantity_270 = 0;
		$scope.quantity_280 = 0;
		
		//요소가 추가되었는지 아닌지 판별하는 변수
		$scope.isAddElement = false;
		
		$scope.product_info_230 = false;
		$scope.product_info_240 = false;
		$scope.product_info_250 = false;
		$scope.product_info_260 = false;
		$scope.product_info_270 = false;
		$scope.product_info_280 = false;
		

		$scope.quantity_plus=function(e){
			console.log($scope);
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
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_230); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_230 = $scope.quantity_230+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_230); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_230 = $scope.product_init_price*$scope.quantity_230; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "240":
					if($scope.quantity_240 < data.sbcount){
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_240); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_240 = $scope.quantity_240+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_240); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_240 = $scope.product_init_price*$scope.quantity_240; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "250":
					if($scope.quantity_250 < data.sbcount){
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_250); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_250 = $scope.quantity_250+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_250); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_250 = $scope.product_init_price*$scope.quantity_250; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "260":
					if($scope.quantity_260 < data.sbcount){
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_260); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_260 = $scope.quantity_260+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_260); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_260 = $scope.product_init_price*$scope.quantity_260; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
					break;
				  case "270":
					if($scope.quantity_270 < data.sbcount){
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_270); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_270 = $scope.quantity_270+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_270); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_270 = $scope.product_init_price*$scope.quantity_270; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
				    break;
				  case "280":
					if($scope.quantity_280 < data.sbcount){
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_280); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
						$scope.quantity_280 = $scope.quantity_280+1; //수량을 올린다
						//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_280); //수량을 올렸을 때 총 가격을 계산한다.
						$scope.product_price_280 = $scope.product_init_price*$scope.quantity_280; //수량에 맞는 사이즈의 가격을 계산한다.
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
					}else{
						alert("재고가 부족한 관계로 더 이상 살 수 없습니다.");
					}
					break;
				  default :
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			});
		};
		$scope.quantity_minus=function(e){
			console.log($scope);
			//특정 사이즈를 구분하기위해 설정.
			$scope.size = e.target.id;
			switch ($scope.size) {
			  case "230":
				if($scope.quantity_230 != 1){
					$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
					//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_230); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_230 = $scope.quantity_230-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_230); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_230 = $scope.product_init_price*$scope.quantity_230; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			    break;
			  case "240":
				if($scope.quantity_240 != 1){
					$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
					//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_240); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_240 = $scope.quantity_240-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_240); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_240 = $scope.product_init_price*$scope.quantity_240; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			    break;
			  case "250":
			    if($scope.quantity_250 != 1){
			    	$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
			    	//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_250); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_250 = $scope.quantity_250-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_250); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_250 = $scope.product_init_price*$scope.quantity_250; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			    break;
			  case "260":
			  	if($scope.quantity_260 != 1){
			  		$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
			  		//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_260); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_260 = $scope.quantity_260-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_260); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_260 = $scope.product_init_price*$scope.quantity_260; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
				break;
			  case "270":
			  	if($scope.quantity_270 != 1){
			  		$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
			  		//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_270); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_270 = $scope.quantity_270-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_270); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_270 = $scope.product_init_price*$scope.quantity_270; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			    break;
			  case "280":
			  	if($scope.quantity_280 != 1){
			  		$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
			  		//$scope.product_all_price = $scope.product_all_price - ($scope.product_init_price*$scope.quantity_280); //원래 상품가격의 수량 값을 곱한 것을 전체가격에서 뺀다. 
					$scope.quantity_280 = $scope.quantity_280-1; //수량을 내린다.
					//$scope.product_all_price = $scope.product_all_price + ($scope.product_init_price*$scope.quantity_280); //수량을 올렸을 때 총 가격을 계산한다.
					$scope.product_price_280 = $scope.product_init_price*$scope.quantity_280; //수량에 맞는 사이즈의 가격을 계산한다.
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				}
			  	break;
			  default :
					$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
					$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			}
		};
		
		//사이즈가 변경되었을 때 호출될 함수
		$scope.size_change=function(){
			$scope.product.selectBox.$valid = false;
			console.log("체인지이벤트!1");
			//사이즈 변경 했을 때 초기화 설정.
			switch ($scope.size) {
			  case "230":
				$scope.quantity_230 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
				$scope.product_price_230 = ${productDto.price };
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				break;
			  case "240":
				$scope.quantity_240 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
				$scope.product_price_240 = ${productDto.price };
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			    break;
			  case "250":
				$scope.quantity_250 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
				$scope.product_price_250 = ${productDto.price };
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			    break;
			  case "260":
				$scope.quantity_260 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
				$scope.product_price_260 = ${productDto.price };
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
				break;
			  case "270":
				$scope.quantity_270 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
				$scope.product_price_270 = ${productDto.price };
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			    break;
			  case "280":
				$scope.quantity_280 = 1;
				$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280;
			  	$scope.product_price_280 = ${productDto.price };
			  	$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			  	break;
			  default :
				$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
				$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
			}

			console.log($scope);
			
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
				  case "280":
					$scope.product_info_280 = true;
					break;
				}
			}
		};
		
		//사이즈 박스 X표시 눌렀을 때 실행할 함수.
		$scope.product_info_delete=function(e){
			var tempStr = null;
			//x버튼이 포함된 요소(product_info)의 참조값을 가져온다.
			for(var i=0; i<$scope.dinamic_el[0].children.length; i++){ //동적으로 생성되는 요소만큼 반복문을 돈다.
				tempStr = $scope.dinamic_el[0].children[i].innerText; //동적으로 생성되는 product_info 클래스 요소의 내용.
				var product_info_el = tempStr.substr(tempStr.length-5, 3); //원하는 숫자값만 추출에서 product_info_el 변수에 대입.(결과적으로 사이즈 값이 들어있다.)
				if(product_info_el == e.target.id){ //누른 x버튼과 해당하는 사이즈 박스와 값이 일치한다면
					var delete_el = $scope.dinamic_el[0].children[i];	//제거하고자 하는 엘리먼트
					var dinamic_el = $scope.dinamic_el[0];	// 그 엘리먼트의 부모 객체
					dinamic_el.removeChild(delete_el); //그 요소를 삭제한다.
					for(var i=0; i<$scope.productInfo_duplicate_check.length; i++){ //선택된 사이즈가 저장된 배열의 길이만큼 반복.(요소를 동적으로 제거하기위한 것.)
						if($scope.productInfo_duplicate_check[i] == product_info_el){ //제거하고자 하는 엘리먼트의 문자열과 배열안에 문자열이 같으면
							$scope.productInfo_duplicate_check.splice(i, 1); // 같은 문자열이 들은 배열의 인덱스를 삭제한다.
							$scope.productInfo_index--; //선택한 사이즈의 개수를 알려주는 index 값을 하나 줄인다.(삭제했기 때문)
							$("#size").find("option:eq(0)").prop("selected", true); //스크립트로 추가된 요소는 prop 으로 제어가능.(x버튼 눌렀을 때 "사이즈 선택" 옵션이 보이도록, change 이벤트 적용을 위해)
							$scope.size="nosize"; //선택했던 사이즈 박스가 제거됐을 때 size 값을 기본(nosize)로 설정.
							//개별 사이즈 수량 및 가격을 먼저 계산한다.
							eval("$scope.quantity_".concat(product_info_el)+"="+"0"); //제거했을 때 수량을 0개로
							eval("$scope.product_price_".concat(product_info_el)+"="+"0"); //제거했을 때 가격을 0원으로
							//전체 사이즈 수량 및 가격을 후에 게산한다.
							$scope.quantity_all = $scope.quantity_230+$scope.quantity_240+$scope.quantity_250+$scope.quantity_260+$scope.quantity_270+$scope.quantity_280; //전체 수량을 재산정.
							$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
							$scope.product_all_price = $scope.product_init_price*$scope.quantity_all; //전체 가격을 구한다.
							console.log($scope);
							console.log($scope.productInfo_duplicate_check);
							console.log($scope.productInfo_index);
							if($scope.productInfo_index == 0){ //신발 사이즈 항목이 없을 때
								$scope.product.selectBox.$valid = true; //구매하기 버튼을 비활성화
							}
						}
					}
				}
			}
		};
		/*
		//댓글 or 대댓글을 등록했을 때 ajax 요청 처리
		$scope.comment_reg=function(){
			//선택할 수 있는 신발 사이즈 항목의 개수를 ajax 요청 처리
			$http({
				method: 'POST',
				url:"${pageContext.request.contextPath}/shop/private/comment_insert",
			    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			}).success(function(data){
				
			});
		}
		*/
		 
		//댓글 페이징 처리를 위한 ajax 요청 처리.
		
		
		//페이징 처리 UI를 만들지 여부
		$scope.isMakePaging=false;
		
		//ajax 로 카페 글 목록을 요청
		$http({
			url:"${pageContext.request.contextPath}/shop/ajax_paging_list.do",
			method:"get",
			params:{"num":${param.num}},
		}).success(function(data){
			//data => {"commentList":[{},{},{}...], "paging":{}}
			$scope.commentList=data.commentList;
			//페이징 처리에 필요한 값을 모델로 관리하기
			$scope.paging=data.paging;
			var pageNums = [];
			for(var i=data.paging.startPageNum;i<=data.paging.endPageNum;i++) {
				pageNums.push(i);
			}
			$scope.pageNums = pageNums;
			//서버로 부터 데이터를 받아왔을 때 페이징 처리 UI가 만들어 질 수 있도록 true로 변경.
			$scope.isMakePaging=true;
		});
		$scope.getPage=function(num){
			//페이징 처리 UI를 만들지 여부
			$scope.isMakePaging=false;
			
			//ajax 로 카페 글 목록을 요청
			$http({
				url:"${pageContext.request.contextPath}/shop/ajax_paging_list.do",
				method:"get",
				params:{num:${param.num}, pageNum:num}
			}).success(function(data){
				//data => {"commentList":[{},{},{}...], "paging":{}}
				$scope.commentList=data.commentList;
				//페이징 처리에 필요한 값을 모델로 관리하기
				$scope.paging=data.paging;
				var pageNums = [];
				for(var i=data.paging.startPageNum;i<=data.paging.endPageNum;i++) {
					pageNums.push(i);
				}
				$scope.pageNums = pageNums;
				//서버로 부터 데이터를 받아왔을 때 페이징 처리 UI가 만들어 질 수 있도록 true로 변경.
				$scope.isMakePaging=true;
				
				console.log($scope);
			});
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
					data: $.param({size:scope.size, num:${productDto.num}}),
				    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
				}).success(function(data){
					scope.productInfo_priceData = data.sbsize_price;
					if(scope.productInfo_index < data.sbsize){
						for(var i=0; i<data.sbsize; i++){
							if(scope.size == "nosize"){ //사이즈 선택 항목을 눌렀을 대
								return; //요소 생성을 막는다.
								console.log($scope);
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
						quantity_first_chlid.append($compile("<input class='quantity_input' type='text' data-ng-model='quantity_"+scope.size+"' value='{{quantity_"+scope.size+"}}' name='countarr' disabled/>")(scope));
						quantity_first_chlid.append($compile("<input type='hidden' value='{{quantity_"+scope.size+"}}' name='countarr' />")(scope));
						quantity_first_chlid.append($compile("<a href='' data-ng-click='quantity_plus($event)' style='color:#000000;'><i class='fa fa-plus-circle fa-2x' id='"+scope.size+"' aria-hidden='true' style='margin-left:5px;'></i></a>")(scope));
						quantity_first_chlid.append($compile("<input type='hidden' name='sizearr' value='"+scope.size+"'/>")(scope));
						quantity.append($compile("<div></div>")(scope));
						var quantity_second_chlid = angular.element(quantity[0].children[1]);
						quantity_second_chlid.append($compile("<input type='text' name='price' data-ng-model='product_price_"+scope.size+"' value='product_price_"+scope.size+"' class=price disabled />원<a href='' data-ng-click='product_info_delete($event)' style='color:#000000;'><i class='fa fa-times fa-2x ' id="+scope.size+" aria-hidden='true' style='text-align:right; margin-left:10px;'></i></a>")(scope));
						quantity_second_chlid.append($compile("<input type='hidden' name='pricearr' value='{{product_price_"+scope.size+"}}'/>")(scope));
						
						scope.productInfo_index++; //생성 후 1증가 시킴.
					}else{
						//사이즈 선택 항목을 다 추가하고 눌렀을 때는 뜨지 않도록 처리.
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
		<div class="left_side">
			<img class="card-img-top" src="${pageContext.request.contextPath }${productDto.profile}" alt="">
		</div>
		<div class="right_side">
			<div class="right_side_children">
				<div style="text-align:center;">
					<h2 style="margin-bottom:1rem;"><img style="width:1.5em;height:1.5em;margin-right:15px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="logo" />상품 정보</h2>
				</div>
				<form action="private/buy_form.do" name="product" method="post" novalidate>
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
					<select class="custom-select mr-sm-2" name="selectBox" id="size" data-ng-required="true" data-ng-model="size" data-ng-change="size_change($event)" addelements>
						<option selected value="nosize">사이즈 선택</option>
						<c:forEach var="tmp" items="${productDto_sub }">
							<c:if test="${tmp.sbcount ne 0}">
								<option value="${tmp.sbsize }">${tmp.sbsize } - 남은 수량(${tmp.sbcount })</option>
							</c:if>
							<c:if test="${tmp.sbcount eq 0}">
								<option value="${tmp.sbsize }" disabled>${tmp.sbsize } - 남은 수량(${tmp.sbcount }) --품절</option>
							</c:if>
						</c:forEach>
				    </select>
					<div id="product_info"></div>
					<div class="all_price" data-ng-if="product_all_price != 0">
						<span>총 상품금액 : </span>{{product_all_price}} 원
						<input type="hidden" name="totalPrice" value="{{product_all_price}}" />
					</div>
					<input type="hidden" name="num" value="${param.num }" />
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
				    <c:if test="${not empty id}">
						<div class="updateAndCancel">
							<button type="reset" class="btn btn-success btn-block" 
								onclick="window.location.href='${pageContext.request.contextPath}/home.do'">장바구니</button>
							<button id="submitBtn" type="submit" style="margin: 0 0 0 20px;"
								data-ng-disabled="product.selectBox.$valid" class="btn btn-primary btn-block" onclick="buy_submit()">구매하기</button>
					    </div>
				    </c:if>
				    <c:if test="${empty id}">
				    	<div class="updateAndCancel">
							<button type="reset" class="btn btn-success btn-block" 
								onclick="window.location.href='${pageContext.request.contextPath}/home.do'">장바구니</button>
							<button id="submitBtn" type="submit" style="margin: 0 0 0 20px;"
								class="btn btn-primary btn-block" onclick="buy_submit()" disabled>구매하기</button>
					    </div>
					    <p style="color:red; font-size: 20px; text-align:center;">로그인 후 구매가 가능합니다!!</p>
				    </c:if>
				    <c:if test="${id eq 'admin'}">
				    	<button type="button" onclick="location.href='private/productupdate.do?num=${param.num }'">수정</button>
						<button type="button" onclick="deleteConfirm()">삭제</button>
				    </c:if>
				</form>
			</div>
	    </div>
	</div>
	<div class="statusBar">
		<div class="background">
			<span>상품 정보 보기</span>
		</div>
	</div>
	<div class="container second_container">
		<!-- 상세 정보 내용 -->
	    <div class="detail_info">
	    	${productDto.content }
	    </div>
	    <!-- 댓글(상품평) 내용  -->
	    <div class="comment_info">
	    	<hr />
	    	<div class="first_comment">
				<!-- 원글에 댓글을 작성하는 form -->
				<form class="comment-form insert-form" action="private/comment_insert.do?ref_group=${productDto.num }" method="post">
					<!-- 원글의 글번호가 ref_group 번호가 된다. -->
					<input type="hidden" name="ref_group" value="${productDto.num }"/>
					<!-- 원글의 작성자가 댓글의 수신자가 된다.(상품 글에 대한 댓글의 수신자는 없기 때문에 파라미터값으로 넘기지 않는다) 
					<input type="hidden" name="target_id" value="${dto.writer }"/>
					-->
					<textarea name="content"><c:if test="${empty id }">로그인이 필요합니다</c:if></textarea>
					<button class="btn btn-info" type="submit">등록</button>
				</form>
			</div>
			<!-- 위에 float:left 에 영향을 받지 않게 하기 위해 -->
			<div class="clearfix"></div>
			
			<div class="productReviewLetter">상품평</div>
			<hr style="border: 1px solid #888; margin-top:0;" />
			
			<!-- ajax 댓글 목록
			<div class="comments">
				<ul>
					<div data-ng-repeat="tmp in commentList">
						<li data-ng-if="tmp.deleted == 'no '">
							<li>삭제된 댓글 입니다.</li>
						</li>
						<li id="comment{{tmp.num}}" data-ng-style="myStyle" >
							asdfasdf
						</li>
					</div>
			 -->
					
			<!-- 댓글 목록 -->
			<div class="comments">
				<ul>
					<c:forEach var="tmp" items="${commentList }">
						<c:choose>
							<c:when test="${tmp.deleted eq 'yes' }">
								<li>삭제된 댓글 입니다.</li>
							</c:when>
							<c:otherwise>
								<!-- 대댓글의 경우는 들여쓰기가 된 것처럼 보이도록 설정 -->
								<!-- 특정 li요소를 찾아 수정(javascript로 조작)하기 위해 id 지정 -->
								<li id="comment${tmp.num }" <c:if test="${tmp.num ne tmp.comment_group }">style="padding-left:50px;"</c:if>>
									<c:if test="${tmp.num ne tmp.comment_group }"><svg class="reply-icon" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-return-right" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
				  						<path fill-rule="evenodd" d="M10.146 5.646a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L12.793 9l-2.647-2.646a.5.5 0 0 1 0-.708z"/>
				  						<path fill-rule="evenodd" d="M3 2.5a.5.5 0 0 0-.5.5v4A2.5 2.5 0 0 0 5 9.5h8.5a.5.5 0 0 0 0-1H5A1.5 1.5 0 0 1 3.5 7V3a.5.5 0 0 0-.5-.5z"/></svg>
									</c:if>
									<dl>
										<dt>
											<span>${tmp.writer }</span>
											<c:if test="${tmp.num ne tmp.comment_group }">
												@<i>${tmp.target_id }</i>
											</c:if>
											<span style="margin-left:10px;">${tmp.regdate }</span>
											<a data-num="${tmp.num }" href="javascript:" class="reply-link" style="text-decoration:none;">답글</a>
											<c:if test="${tmp.writer eq id }">
												| <a data-num="${tmp.num }" href="javascript:" class="comment-update-link" style="text-decoration:none;">수정</a>
												| <a data-num="${tmp.num }" href="javascript:deleteWriting()" class="comment-delete-link" style="text-decoration:none;">삭제</a>
											</c:if>
										</dt>
										<dd>
											<pre>${tmp.content }</pre> <!-- tab, 띄어쓰기, 개행 등등을 해석해주는 pre 요소 -->
										</dd>
									</dl>
									<!-- 댓글의 댓글(대댓글) 작성 폼 -->
									<form class="comment-form re-insert-form" 
										action="private/comment_insert.do" method="post">
										<input type="hidden" name="ref_group"
											value="${productDto.num }"/>
										<input type="hidden" name="target_id"
											value="${tmp.writer }"/>
										<input type="hidden" name="comment_group"
											value="${tmp.comment_group }"/>
										<textarea name="content"></textarea>
										<button class="btn btn-info" type="submit">등록</button>
									</form>
									<!-- 로그인된 아이디와 댓글의 작성자가 같으면 수정 폼 출력 -->
									<c:if test="${tmp.writer eq id }">
										<form class="comment-form update-form" 
											action="private/comment_update.do" method="post">
											<input type="hidden" name="num" value="${tmp.num }"/>
											<textarea name="content">${tmp.content }</textarea>
											<button class="btn btn-info" type="submit">수정</button>
										</form>
									</c:if>
								</li>
								<!-- 위에 float:left 에 영향을 받지 않게 하기 위해 -->
								<div style="clear:both;"></div>						
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div><!-- /.comments -->
	    </div>
	    
	    <!-- 
	    <div class="page-display" data-ng-if="isMakePaging">
			<ul class="pagination pagination-sm">
				<li data-ng-if="paging.startPageNum != 1" 
					class="page-item">
					<a data-ng-click="getPage(paging.startPageNum-1)" 
						class="page-link" href="#/{{paging.startPageNum-1}}">Prev</a>
				</li>
				<li data-ng-repeat="tmp in pageNums" 
						class="page-item"
						data-ng-class="{active: paging.pageNum == tmp }">
					<a data-ng-click="getPage(tmp)"
						class="page-link" href="#/{{tmp}}">{{tmp}}</a>
				</li>
				<li data-ng-if="paging.endPageNum < paging.totalPageCount"
					class="page-item">
					<a data-ng-click="getPage(paging.endPageNum+1)" 
						class="page-link" href="#/{{paging.endPageNum+1}}">Next</a>
				</li>
			</ul>
		</div>
		 -->
	    <div class="page-display paging1" style="margin-top:">
			<ul class="pagination pagination-sm">
			<c:if test="${startPageNum ne 1 }">
				<li class="page-item"><a class="page-link" href="detail.do?num=${productDto.num }&pageNum=${startPageNum-1 }">Prev</a></li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
				<c:choose>
					<c:when test="${i eq pageNum }">
						<li class="page-item active"><a class="page-link" href="detail.do?num=${productDto.num }&pageNum=${i }">${i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="detail.do?num=${productDto.num }&pageNum=${i }">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount }">
				<li class="page-item"><a class="page-link" href="detail.do?num=${productDto.num }&pageNum=${endPageNum+1 }">Next</a></li>
			</c:if>
			</ul>	
		</div>
	</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery.form.min.js"></script>
<script>
	function deleteConfirm(){
		let isDelete=confirm("상품을 삭제하시겠습니까?");
		if(isDelete){
		    location.href="productdelete.do?num=${param.num }";
		}
	}
	function buy_submit(){
		let isBuy=confirm("상품을 구매하시겠습니까?");
		if(isBuy){
		    location.href="private/buy_form.do?num=${param.num }";
		}
	}
	//새로 동적으로 생기는 폼들은 ajaxForm()이 먹히지 않는다 따라서 폼이 제출될 때 ajax로 처리하기위해서는 ajaxSubmit() 함수를 이용해야한다고 한다.
	$(document).on("submit", ".update-form", function(){
		//이벤트가 일어난 폼을 ajax로 전송되도록 하고 
		$(this).ajaxSubmit(function(data){
			console.log(data); //결과 확인용
			//수정이 일어난 댓글의 li 요소를 선택해서 원하는 작업(수정완료 후 수정폼을 숨기기위한 작업)을 한다.
			var selector="#comment"+data.num; //"#comment6" 형식의 선택자 구성
			
			//댓글 수정 폼을 안보이게 한다. 
			$(selector).find(".update-form").slideUp();
			//pre 요소에 출력된 내용 수정하기
			$(selector).find("pre").text(data.content);
			//수정완료 후 "수정" 글자가 보이도록 한다.
			$(".comment-update-link").text("수정");//수정으로 바꾼다.
		});
		//폼 전송을 막아준다.
		return false;
	});
	
	//답글 달기 링크를 클릭했을때 실행할 함수 등록
	$(document).on("click", ".reply-link", function(){
		//로그인 여부
		var isLogin=${not empty id};
		if(isLogin == false){
			alert("로그인 페이지로 이동합니다.");
			location.href="${pageContext.request.contextPath }/login/login_form.do?"+
					"url=${pageContext.request.contextPath }/shop/detail.do?num=${productDto.num}";
		}
		
		var selector="#comment"+$(this).attr("data-num");
		$(selector)
		.find(".re-insert-form")
		.slideToggle();
		
		if($(this).text()=="답글"){//링크 text를 답글일때 클릭하면 
			$(this).text("취소");//취소로 바꾸고 
		}else{//취소일때 클릭하면 
			$(this).text("답글");//답글로 바꾼다.
		}
	});
	//댓글 수정 링크를 눌렀을때 호출되는 함수 등록
	$(document).on("click",".comment-update-link", function(){
		/*
			click 이벤트가 일어난 댓글 수정 링크에 저장된 data-num 속성의 값을
			읽어와서 id 선택자를 구성한다.
		*/
		var selector="#comment"+$(this).attr("data-num");
		//구성된 id 선택자를 이용해서 원하는 li 요소에서 .update-form 을 찾아서 동작하기
		$(selector)
		.find(".update-form")
		.slideToggle();
		
		if($(this).text()=="수정"){//링크 text를 수정일때 클릭하면 
			$(this).text("취소");//취소로 바꾸고 
		}else{//취소일때 클릭하면 
			$(this).text("수정");//수정으로 바꾼다.
		}
	});
	//자신의 댓글 삭제버튼 눌렀을 때 실행할 함수.
	$(document).on("click", ".comment-delete-link", function(){
		//삭제할 글번호 
		var num=$(this).attr("data-num");
		var isDelete=confirm("댓글을 삭제 하시겠습니까?");
		if(isDelete){
			location.href="${pageContext.request.contextPath }"+
			"/shop/private/comment_delete.do?num="+num+"&ref_group=${productDto.num}";
		}
	});
</script>
</body>
</html>