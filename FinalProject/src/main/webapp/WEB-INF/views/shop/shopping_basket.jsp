<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<!-- 글씨체 관련 -->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<!-- angularjs 로딩 -->
<!-- 
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular-cookies.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/2.0.0-beta.9/Rx.min.js"></script>
 -->
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-cookies/1.8.2/angular-cookies.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular-route.min.js"></script>
<style>
	.shopping_basket {
		margin-top: 1rem;
	}
	hr {
		width: 100%;
		padding-right: 0;
		padding-left: 0;
		margin-right: 0;
		margin-left: 0;
	}
	.product_list {
		margin-top: 0.5rem;
		position: relative;
		color: blue;
		font-weight: bold;
	}
	/* 장바구니 화면 크기 설정 */
	@media (min-width: 0px) { /* 최소 가로길이가 0px 일때(최소 가로길이가 0px부터 미디어쿼리 적용) */
		.container {
		  max-width: 1080px; /* 최대 가로길이가 1080px까지만 적용  */
		}
	}
	/* 상품 보여지는 화면 크기 설정 */
	@media (min-width: 0px) { /* 최소 가로길이가 0px 일때(최소 가로길이가 0px부터 미디어쿼리 적용) */
		.cart_part {
		  min-width: 1080px; /* 최소 가로길이가 1080px 이상 적용  */
		}
	}
	
	/* 상품 목록 개수 관련 */
	.buycount_deco {
	    position: absolute;
		display: inline-block;
	    background-color: #E6F1FD;
	    border-radius: 50%;
	    width: 24px;
	    height: 24px;
	    top: 1px;
	    left: 82.5px;
	    z-index: -1;
	}
	.cart_part {
		background-color: #d8d8d8;
	}
	/* 동적으로 생성한 요소 관련 css */
    .cart_select_delete {
	    position: sticky;
	    background-color: #d8d8d8;
	    padding-top: 7px;
	    z-index: 1;
    }
	.cart_select_delete label {
		margin-left: 6px;
		margin-right: 10px;
		color: gray;
		cursor: pointer;
	}
	.cart_select_delete button.selected_del {
		border: none;
	    background: none;
	    cursor: pointer;
	    margin-left: 10px;
	    padding: 0;
	    color: gray;
	}
	.cart_select_delete li {
		position: relative;
		display: inline;
		list-style: none;
	}
	.cart_select_delete li::before {
		content: "";
	    position: absolute;
	    background: #c8c8c8;
	    width: 1px;
	    height: 13px;
	    top: 5px;
	    left: 0;
	}
	.cart_body {
		display:flex; 
		width: 100%;
		padding-bottom: 31px;
	}
	.cart_body_right {
		flex-grow:1;
		background-color: #d8d8d8;
		width: 230px;
	}
	.cart_body_right_dl {
		background-color: white;
		padding:15px;
		position: sticky;
		margin-bottom: 0;
	}
	.cart_body_right_dl dt{
		height:30px;
		border-bottom:1px solid black;
		margin-bottom: 20px;
	}
	/* 장바구니 상품 왼쪽 부분 */
	.cart_body_left {
		flex-grow:5;
		background-color: white;
		padding:15px;
	}
	.cart_body_left_dl dt {
		height:30px;
		border-bottom:1px solid black;
		margin-bottom: 25px;
	}
	.each_product {
		clear:both;
		position: relative;
	}
	.each_product_checkbox_image_all {
		float:left;
	}
	.each_product_checkbox {
		display:inline-block;
	}
	.each_product_image {
		display:inline-block;
	}
	.each_product_info_all {
		display: inline-block;
		line-height: 40px;
		position: relative;
	}
	.each_product_info_productName {
		width: 250px;
	    word-break: break-all;
	    line-height: 1.2rem;
	    height: 40px;
	}
	.each_product_info_size {
		height: 40px;
    	display: table;
    	line-height: 1.2rem;
	}
	.each_product_info_size span{
		margin-right: 6rem;
	    display: table-cell;
	    vertical-align: bottom;
	}
    .each_product_info_quantity {
    	position: absolute;
	    width: 110px;
	    top: 0;
	    left: 265px;
    }
    .each_product_info_quantity .first_a {
    	color:#000000;
    	display: inline-block;
	    width: 31px;
	    height: 31px;
	    vertical-align: text-top;
    }
    .each_product_info_quantity .first_i {
    	vertical-align: text-bottom;
    }
    .each_product_info_quantity input {
    	width: 35px;
	    height: 28px;
	    vertical-align: bottom;
	    text-align: center;
	    margin: 0 5px 0 2px;
    }
    .each_product_info_quantity .second_a {
    	color:#000000;
    	display: inline-block;
	    width: 31px;
	    height: 31px;
	    vertical-align: bottom;
    }
    .each_product_info_quantity .second_i {
    	vertical-align: text-bottom;
    }
    .each_product_info_price {
    	position: absolute;
	    top: 0;
	    width: 90px;
	    left: 450px;
	    text-align: right;
    }
    .each_product_delete {
    	display: inline-block;
	    position: absolute;
	    right: 0;
	    transform: translateY(3px);
    }
</style>
<script>
	var myApp=angular.module("myApp", ['ngCookies','ngRoute']);
	
	myApp.controller("shoppingBasket_Ctrl", function($scope, $http, $cookies, $compile, $filter){
		console.log($scope); 
		
		$scope.cookie = $cookies.get("guest"); 
		
		//장바구니 리스트
		$scope.basket_list = {};
		//장바구니 리스트가 있는 지 없는 지 판별하는 변수
		$scope.isValidBasket = false;
		//전체 가격을 저장할 변수
		$scope.product_all_price = 0;
		//전체 수량을 저장할 변수
		$scope.quantity_all = 0;
		//상품 수를 저장할 변수
		$scope.buycount = Number("${buycount}");
		
		/*
		//초기값 설정.
		$scope.product_all_price = ${totalPrice };
		$scope.product_price_230 = ${product_price_230};
		$scope.product_price_240 = ${product_price_240};
		$scope.product_price_250 = ${product_price_250};
		$scope.product_price_260 = ${product_price_260};
		$scope.product_price_270 = ${product_price_270};
		$scope.product_price_280 = ${product_price_280};
		
		$scope.quantity_all = ${totalQuantity };
		$scope.quantity_230 = ${quantity_230};
		$scope.quantity_240 = ${quantity_240};
		$scope.quantity_250 = ${quantity_250};
		$scope.quantity_260 = ${quantity_260};
		$scope.quantity_270 = ${quantity_270};
		$scope.quantity_280 = ${quantity_280};
		
		//상품 고유의 가격을 저장하기 위한 변수
		$scope.product_init_price = ${initPrice };
		//선택된 사이즈의 개수를 저장할 변수
		$scope.productInfo_index = ${buycount };
		//상품 이름을 저장할 변수
		$scope.product_name = "${productname }";
		//상품 종류를 저장할 변수
		$scope.kind = "${kind }";
		//문자열화 돼있는 배열을 객체화 시키기 위해 JSON.parse 실행하여 배열 객체로 저장.
		//$scope.select_size = JSON.parse(${selectedSize });
		*/
		
		$scope.form_submit = function() {
			//체크박스가 선택되어있는지 확인할 변수
			let isCheckCount = 0;
			for(let i=0; i<$scope.basket_list.length; i++){ //전체 상품 리스트를 반복
				if(eval("$scope.isCheckedBox_"+i+" === false")){ //체크해제된 박스가 있을 경우
					++isCheckCount; //체크해제된 상품이 있을 경우 카운트를 하나 올린다.
				}
			}
			if(isCheckCount === $scope.basket_list.length){ //선택한 상품이 아예 없을 경우
				alert("상품을 한개 이상 선택해주세요!");
				return; //함수를 그냥 종료시킨다.
			}
			else{//하나라도 선택한 상품이 있을 경우
				//버튼을 클릭했을 때 폼안에 이쓴 버튼을 통해 폼을 전송하도록 하기.
				document.querySelector(".form_submit_button").click();
			}
		}
		
		//페이지 로딩 되었을 때 초기 체크박스 설정. 
		$scope.isChecked = true;
		//전체동의 체크박스를 체크했을 때 호출될 함수
		$scope.all_select_changed = function(){
			console.log($scope);
			if($scope.isChecked == false){//전체선택이 비활성화일 때
				$scope.isChecked = true;
				for(let i=0; i<$scope.basket_list.length; i++){
					if(eval("$scope.isCheckedBox_"+i+" === false")){ //각 항목에 체크표시가 비활성화 되있을 경우
						eval("$scope.isCheckedBox_"+i+" = true");
						eval("$scope.basket_list_"+i+"_check = true");
						//임시 변수에 저장된 값을 선택한 아이템 항목들 변수에 저장한다.
						eval("$scope.basket_list_quantity_"+i+" = $scope.basket_list_quantity_temp_"+i);
						//임시 변수에 저장된 값을 선택한 아이템 항목들 변수에 저장한다.
						eval("$scope.basket_list_price_"+i+" = $scope.basket_list_price_temp_"+i);
						//특정 상품을 선택했을 때 체크한 상품의 다른 사이즈 개수만큼 상품 수를 증가시킨다.
						$scope.buycount = $scope.buycount + 1;
					}
				}
				//전체가격을 계산하기전에 0으로 초기화시킨다.
				$scope.product_all_price = 0;
				//전체수량을 계산하기전에 0으로 초기화시킨다.
				$scope.quantity_all = 0;
				for(let i=0; i<$scope.basket_list.length; i++){
					//전체 상품의 총 가격을 구한다.
					eval("$scope.product_all_price = $scope.product_all_price + $scope.basket_list_price_"+i);
					//전체 상품의 총 수량을 구한다.
					eval("$scope.quantity_all = $scope.quantity_all + $scope.basket_list_quantity_"+i);
				}
			}
			else{//전체선택이 활성화일 때
				$scope.isChecked = false;
				for(let i=0; i<$scope.basket_list.length; i++){
					if(eval("$scope.isCheckedBox_"+i+" === true")){ //각 항목에 체크표시가 활성화 되있을 경우
						eval("$scope.isCheckedBox_"+i+" = false");
						eval("$scope.basket_list_"+i+"_check = false");
						//선택해제한 아이템 항목들의 수량을 0으로 초기화하기전에 임시 변수에 현재값을 보관해둔다.
						eval("$scope.basket_list_quantity_temp_"+i+" = $scope.basket_list_quantity_"+i);
						//선택해제한 아이템 항목들의 가격을 0으로 초기화하기전에 임시 변수에 현재값을 보관해둔다.
						eval("$scope.basket_list_price_temp_"+i+" = $scope.basket_list_price_"+i);
						//선택해제한 아이템 항목들의 수량을 0원으로 초기화.
						eval("$scope.basket_list_quantity_"+i+" = 0");
						//선택해제한 아이템 항목들의 가격을 0원으로 초기화.
						eval("$scope.basket_list_price_"+i+" = 0");
						//특정 상품을 선택했을 때 체크한 상품의 다른 사이즈 개수만큼 상품 수를 감소시킨다.
						$scope.buycount = $scope.buycount - 1;
					}
				}
				//전체가격을 계산하기전에 0으로 초기화시킨다.
				$scope.product_all_price = 0;
				//전체수량을 계산하기전에 0으로 초기화시킨다.
				$scope.quantity_all = 0;
				for(let i=0; i<$scope.basket_list.length; i++){
					//전체 상품의 총 가격을 구한다.
					eval("$scope.product_all_price = $scope.product_all_price + $scope.basket_list_price_"+i);
					//전체 상품의 총 수량을 구한다.
					eval("$scope.quantity_all = $scope.quantity_all + $scope.basket_list_quantity_"+i);
				}
			}
		};
		
		$scope.quantity_minus=function(e){
			console.log($scope);
			//특정 마이너스 버튼을 구분하기위해 설정.
			$scope.minus_button = e.target.id;
			console.log($scope.minus_button);
			$http({
				method: 'POST',
				url:"${pageContext.request.contextPath}/shop/size_quantity_price_ajax.do",
				data: $.param({id:$scope.cookie}),
			    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			}).then(function(success){
				console.log(success);
				//scope 영역에 장바구니 리스트를 저장.
				$scope.basket_list = success.data.basket_list;
				
				//누른 마이너스 버튼의 리스트의 순서값을 가져온다.
				let minusButtonStrArr = $scope.minus_button.split('_');
				$scope.order_list = Number(minusButtonStrArr[minusButtonStrArr.length-1]);
				console.log($scope.order_list);
				
				if(eval("$scope.basket_list_quantity_"+$scope.order_list+" > 1")) { //선택한 상품의 수량이 1보다 클 경우
					//줄인 수량을 특정 모델에 결과를 반영한다.
					eval("$scope.basket_list_quantity_"+$scope.order_list+"--;");
					//특정 상품의 특정 사이즈의 각격을 구한다.
					eval("$scope.basket_list_price_"+$scope.order_list+"=$scope.basket_list_quantity_"+$scope.order_list+"*$scope.basket_list[$scope.order_list].initPrice");
					$scope.quantity_all--; //전체 수량을 구한다.
					$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
					for(let i=0; i<$scope.basket_list.length; i++){//전체 가격을 구하기위한 반복문
						eval("$scope.product_all_price=$scope.product_all_price+$scope.basket_list_price_"+i);
					}
				}
			},function (error){

			});
		};
		
		$scope.quantity_plus=function(e){
			console.log($scope);
			//특정 플러스 버튼을 구분하기위해 설정.
			$scope.plus_button = e.target.id;
			console.log($scope.plus_button);
			$http({
				method: 'POST',
				url:"${pageContext.request.contextPath}/shop/size_quantity_price_ajax.do",
				data: $.param({id:$scope.cookie}),
			    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			}).then(function(success){
				console.log(success);
				//scope 영역에 장바구니 리스트를 저장.
				$scope.basket_list = success.data.basket_list;
				
			},function (error){

			});

			/*
				$scope.order_list 변수에 특정 상품의 항목을 가리키는 값을 가지고 있고 이 값을 활용해 특정 상품의 model의 값을
				동적으로 바꾸기위함이다.
				개수를 추가할 때마다 특정 상품을 가리키는 값을 가져와야할 때 $scope.order_list를 통해 접근해야하므로
				특정 상품의 개수를 추가버튼을 통해 늘릴 때마다 $scope.order_list에 특정 상품을 식별하기위한 값을 저장해야 그 값을 활용할 수 있다.
				(쉽게말하면 $scope.order_list의 안에있는 값에 의존하기 때문이다.)
			*/
			//누른 마이너스 버튼의 리스트의 순서값을 가져온다.
			let plusButtonStrArr = $scope.plus_button.split('_');
			$scope.order_list = Number(plusButtonStrArr[plusButtonStrArr.length-1]);
			console.log($scope.order_list);
			
			//파라미터로 넘길 값을 설정해준다.
			let order_list = String($scope.basket_list[$scope.plus_button.substr($scope.plus_button.length-1, 1)].num);
			let selectedSize = $scope.basket_list[$scope.plus_button.substr($scope.plus_button.length-1, 1)].selectedSize;
			
			$http({
				method: 'POST',
				url:"${pageContext.request.contextPath}/shop/size_productnum_ajax.do",
				data: $.param({num:order_list, selectedSize:selectedSize}),
			    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			}).then(function(success){
				console.log(success);
				//scope 영역에 장바구니 리스트를 저장.
				$scope.selectedSbproductSub = success.data.selectedSbproductSub;
				
				if(eval("$scope.basket_list_quantity_"+$scope.order_list+" < $scope.selectedSbproductSub.sbcount")) { //선택한 상품의 수량이 재고 수량보다 작을 경우
					if(eval("$scope.isCheckedBox_"+$scope.order_list+" === true")){ //선택한 체크박스가 활성화일 때만 수량 추가 로직을 실행하기위한 조건문
						//줄인 수량을 특정 모델에 결과를 반영한다.
						eval("$scope.basket_list_quantity_"+$scope.order_list+"++;");
						//특정 상품의 특정 사이즈의 각격을 구한다.
						eval("$scope.basket_list_price_"+$scope.order_list+"=$scope.basket_list_quantity_"+$scope.order_list+"*$scope.basket_list["+$scope.order_list+"].initPrice");
						$scope.quantity_all++; //전체 수량을 구한다.
						$scope.product_all_price = 0; //총 가격 계산을 하기전에 0으로 초기화해놓는다.
						for(let i=0; i<$scope.basket_list.length; i++){//전체 가격을 구하기위한 반복문
							eval("$scope.product_all_price=$scope.product_all_price+$scope.basket_list_price_"+i);
						}
					}else{//상품을 선택하지 않고 수량을 추가하려했을 때
						alert("해당 상품을 먼저 선택해야합니다!");
					}
				}else { //재고 수량보다 요구 수량이 많거나 같은 경우는 수량을 올리지 않고 알림창을 띄운다.
					alert("재고 수량이 부족합니다");
				}
				
			},function (error){

			});
			
		};
		
		//사이즈 박스 X표시 눌렀을 때 실행할 함수.
		$scope.product_info_delete=function(e){
			let isSeletedDelete = confirm("해당 상품을 삭제하시겠습니까?");
			if(isSeletedDelete){
				console.log($scope);
				//특정 사이즈를 구분하기위해 설정.
				$scope.delete_X_id = e.target.parentNode.parentNode.parentNode.id;
				//누른 마이너스 버튼의 문자열을 _ 단위로 나눠서 배열에 저장한다.
				let delete_X_id_arr = e.target.parentNode.parentNode.parentNode.id.split('_');
				//$scope.delete_X_id에 저장된 문자열 중에 리스트의 순서값을 저장하는 문자열을 뺀다.
				$scope.delete_X_number = delete_X_id_arr[delete_X_id_arr.length-1];
				
				$http({
					method: 'POST',
					url:"${pageContext.request.contextPath}/shop/size_quantity_price_ajax.do",
					data: $.param({id:$scope.cookie, Xid:$scope.delete_X_number}),
				    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
				}).then(function(success){
					console.log(success);
					//scope 영역에 장바구니 리스트를 저장.
					$scope.basket_list = success.data.basket_list;
					
				},function (error){
	
				});
				
				//form의 action 값을 페이지 요청할 곳으로 바꾼다.
				$("form").attr('action', '${pageContext.request.contextPath}/shop/shopping_basket.do?cookie='+$scope.cookie);
				//버튼을 눌렀을 때 폼을 강제 전송한다.
				$("form").submit();
			}
		};
		
		//선택된 항목들을 삭제하는 메소드
		$scope.selected_delete = function(e) {
			let isSeletedDelete = confirm("선택한 상품들을 삭제하시겠습니까?");
			if(isSeletedDelete){
				//checked 속성이 활성화된 항목들을 담을 배열 선언
				let checkedItem = [];
				
				for(let i=0; i<$scope.basket_list.length; i++){
					let inputElem = document.querySelector("#each_product_"+(i+1)+" .each_product_checkbox_image_all .each_product_checkbox input");
					if($(inputElem).attr('checked') == undefined){
						continue;
					}else if($(inputElem).attr('checked') == 'checked'){
						checkedItem.push(i+1); //체크된 체크박스들을 배열에 담는다.
						console.log(checkedItem[i]);
					}
				}
				if(checkedItem.length !== 0){ //배열이 비어있지 않을 때
					$http({
						method: 'POST',
						url:"${pageContext.request.contextPath}/shop/selected_delete_ajax.do",
						data: $.param({id:$scope.cookie, Xid:""+checkedItem}),
					    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
					}).then(function(success){
						console.log(success);
						//scope 영역에 장바구니 리스트를 저장.
						$scope.basket_list = success.data.basket_list;
						
					},function (error){
	
					});
					//form의 action 값을 페이지 요청할 곳으로 바꾼다.
					$("form").attr('action', '${pageContext.request.contextPath}/shop/shopping_basket.do?cookie='+$scope.cookie);
					//버튼을 눌렀을 때 폼을 강제 전송한다.
					$("form").submit();
				}
			}
		}
		
		//한 체크박스를 선택할 시 수행되는 메소드
		$scope.isSelectedCheckbox = function(e) {
			//선택한 상품들의 체크박스가 활성화 되어있는지 여부
			let isCheck = true;
			
			//누른 마이너스 버튼의 리스트의 순서값을 가져온다.
			let selectedItemOrderArr = e.target.parentNode.parentNode.parentNode.id.split('_');
			let selectedItemOrder = Number(selectedItemOrderArr[selectedItemOrderArr.length-1]);
			console.log($scope);
			if(eval("$scope.isCheckedBox_"+selectedItemOrder+" == false")){
				eval("$scope.isCheckedBox_"+selectedItemOrder+" = true");
				for(let i=0; i<$scope.basket_list.length; i++){
					if($scope.basket_list[selectedItemOrder].productname === $scope.basket_list[i].productname){
						eval("$scope.isCheckedBox_"+i+" = true");
						eval("$scope.basket_list_"+i+"_check = $scope.isCheckedBox_"+i);
						//임시 변수에 저장된 값을 선택한 아이템 항목들 변수에 저장한다.
						eval("$scope.basket_list_quantity_"+i+" = $scope.basket_list_quantity_temp_"+i);
						//임시 변수에 저장된 값을 선택한 아이템 항목들 변수에 저장한다.
						eval("$scope.basket_list_price_"+i+" = $scope.basket_list_price_temp_"+i);
						//특정 상품을 선택했을 때 체크한 상품의 다른 사이즈 개수만큼 상품 수를 증가시킨다.
						$scope.buycount = $scope.buycount + 1;
					}
				}
				//전체가격을 계산하기전에 0으로 초기화시킨다.
				$scope.product_all_price = 0;
				//전체수량을 계산하기전에 0으로 초기화시킨다.
				$scope.quantity_all = 0;
				for(let i=0; i<$scope.basket_list.length; i++){ //전체 항목이 체크되어있는지 검사하기위한 반복문
					if(eval("$scope.isCheckedBox_"+i+" === false")){ //반복하는 인덱스에 해당하는 체크박스가 하나라도 비활성화일 때
						isCheck = false; //비활성화인 체크박스가 있다는 것을 판단
					}
					//전체 상품의 총 가격을 구한다.
					eval("$scope.product_all_price = $scope.product_all_price + $scope.basket_list_price_"+i);
					//전체 상품의 총 수량을 구한다.
					eval("$scope.quantity_all = $scope.quantity_all + $scope.basket_list_quantity_"+i);
				}
				if(isCheck === true) { //체크박스 모두가 활성화일 때
					$scope.isChecked = true; //전체 선택 체크박스를 활성화.
				}
			}
			else{
				//한개의 체크박스라도 선택되어있지 않을 경우 전체선택 체크박스 상태를 false로 만든다.
				$scope.isChecked = false;
				eval("$scope.isCheckedBox_"+selectedItemOrder+" = false");
				for(let i=0; i<$scope.basket_list.length; i++){
					if($scope.basket_list[selectedItemOrder].productname === $scope.basket_list[i].productname){
						eval("$scope.isCheckedBox_"+i+" = false");
						eval("$scope.basket_list_"+i+"_check = $scope.isCheckedBox_"+i);
						//선택해제한 아이템 항목들의 수량을 0으로 초기화하기전에 임시 변수에 현재값을 보관해둔다.
						eval("$scope.basket_list_quantity_temp_"+i+" = $scope.basket_list_quantity_"+i);
						//선택해제한 아이템 항목들의 가격을 0으로 초기화하기전에 임시 변수에 현재값을 보관해둔다.
						eval("$scope.basket_list_price_temp_"+i+" = $scope.basket_list_price_"+i);
						//선택해제한 아이템 항목들의 수량을 0원으로 초기화.
						eval("$scope.basket_list_quantity_"+i+" = 0");
						//선택해제한 아이템 항목들의 가격을 0원으로 초기화.
						eval("$scope.basket_list_price_"+i+" = 0");
						//특정 상품을 선택했을 때 체크한 상품의 다른 사이즈 개수만큼 상품 수를 감소시킨다.
						$scope.buycount = $scope.buycount - 1;
					}
				}
				//전체가격을 계산하기전에 0으로 초기화시킨다.
				$scope.product_all_price = 0;
				//전체수량을 계산하기전에 0으로 초기화시킨다.
				$scope.quantity_all = 0;
				for(let i=0; i<$scope.basket_list.length; i++){
					//전체 상품의 총 가격을 구한다.
					eval("$scope.product_all_price = $scope.product_all_price + $scope.basket_list_price_"+i);
					//전체 상품의 총 수량을 구한다.
					eval("$scope.quantity_all = $scope.quantity_all + $scope.basket_list_quantity_"+i);
				}
			}
		}
		$http({
			method: 'POST',
			url:"${pageContext.request.contextPath}/shop/size_quantity_price_ajax.do",
			data: $.param({id:$scope.cookie}),
		    headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
		}).then(function(success){
			console.log(success);
			//scope 영역에 장바구니 리스트를 저장.
			$scope.basket_list = success.data.basket_list;
			//scope 영역에 장바구니 리스트가 넘어오는 지 안 넘어오는지 판단해줄 변수 저장.
			$scope.isValidBasket = success.data.isValid;
			
			//객체의 키값의 길이가 0이 아니라면 객체안에 값이 들어있다고  판단.(키값의 길이가 0 이상이라면 빈 객체가 아님)
			//이 조건을 붙인 이유는 반복문 조건에 $scope.basket_list.length의 .length가 객체가 비어있을 때 length를 참조하지 못해 오류가 났었다.
			if(Object.keys(success.data).length !== 0) { 
				//basket_list_check 관련 배열 객체 만들기
				$scope.basket_list_check = [];
				//전체 가격과 전체 수량을 scope 영역에 저장한다.
				for(let i=0; i<$scope.basket_list.length; i++){
					//DB에서 넘어온 전체 가격을 저장한다.
					$scope.product_all_price = $scope.product_all_price + ($scope.basket_list[i].initPrice*$scope.basket_list[i].selectedQuantity);
					//DB에서 넘어온 전체 수량을 저장한다.
					$scope.quantity_all = $scope.quantity_all + $scope.basket_list[i].selectedQuantity;
					
					$scope.basket_list_check.push("basket_list_"+(i+1)+"_check");
				}
			}
			
			if($scope.isValidBasket == true){ //장바구니 관련 ajax 요청이 잘 왔을 때만 동적 요소 생성하기.
				let cart_part = document.querySelector(".cart_part");
				let cart_select_delete = $compile("<div class='cart_select_delete'></div>")($scope);
				//angular.element() 로 만든 것이 아닌 cart_part 변수는 앞에 앵귤러 요소라는 의미로 angular.element(cart_part) 여기에 append 해야 추가된다.
				angular.element(cart_part).append(cart_select_delete); 
				cart_select_delete.append($compile("<input type='checkbox' id='select_all' data-ng-model='all_select' data-ng-checked='isChecked' data-ng-click='all_select_changed()'><label for='select_all' href='#'>전체선택</label>")($scope));
				cart_select_delete.append($compile("<li><button class='selected_del' data-ng-click='selected_delete($event)'>선택삭제</button></li>")($scope));
				angular.element(cart_part).append($compile("<div class='cart_body'></div>")($scope));
				//상품이 실질적으로 표시될 부분(cart_body)
				let cart_body = angular.element(document.querySelector(".cart_body"));
				cart_body.append($compile("<div class='cart_body_left'></div>")($scope));
				cart_body.append($compile("<div class='cart_body_right'></div>")($scope));
				//상품이 왼쪽에 표시되는 부분
				let cart_body_left = angular.element(document.querySelector(".cart_body_left"));
				cart_body_left.append($compile("<dl class='cart_body_left_dl'></dl>")($scope));
				let cart_body_left_dl = angular.element(document.querySelector(".cart_body_left_dl"));
				cart_body_left_dl.append($compile("<dt>상품 목록 보기</dt>")($scope));
				cart_body_left_dl.append($compile("<dd class='cart_body_left_dd'></dd>")($scope));
				let cart_body_left_dd = angular.element(document.querySelector(".cart_body_left_dd"));
				cart_body_left_dd.append($compile("<div class='cart_item_info'></div>")($scope));
				let cart_item_info = angular.element(document.querySelector(".cart_item_info"));
				let cart_form = angular.element("<form action='private/buy_form.do 'name='product' method='post' novalidate>");
				cart_item_info.append(cart_form);
				
				/*
					<div class='each_product_info_quantity'>
						<a href='' class='first_a' data-ng-click='quantity_minus($event)' data-ng-model='basket_list_quantity_minus_"+i+"'><i class='fa fa-minus-circle fa-2x first_i' id='basket_list_quantity_minus_"+i+"' aria-hidden='true'></i></a>
						<input class='quantity_input' type='text' data-ng-model='basket_list_quantity_"+i+"' value='{{basket_list_quantity_"+i+"}}' name='countarr' disabled/>
						<a href='' class='second_a' data-ng-click='quantity_plus($event)' data-ng-model='basket_list_quantity_plus_"+i+"'><i class='fa fa-plus-circle fa-2x second_i' id='basket_list_quantity_plus_"+i+"' aria-hidden='true'></i></a>
					</div>
					<div class='each_product_info_price'>
						<span data-ng-model='basket_list_price_"+i+"' value='{{basket_list_price_"+i+" | number:0}}'>{{basket_list_price_"+i+" | number:0}}원</span>
					</div>
				*/
				

				/*
				//data-ng-repeat='tmp in basket_list'
				let each_product = $compile(
						`<div class='each_product' id='each_product_{{$index+1}}' data-ng-repeat='tmp in basket_list'>
							<div data-ng-if='$index == 0'>
								<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
									<div class='each_product_checkbox'>
										<input type='checkbox' data-ng-checked='isChecked' value='basket_list_{{$index}}_check'>
									</div>
									<div class='each_product_image'>
										<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
									</div>
								</div>
								<div class='each_product_info_all'>
									<div class='each_product_info_productName'>
										<span class='cart_item_name'>{{tmp.kind}} - {{tmp.productname}}</span>
									</div>
									<div class='each_product_info_size'>
										<span>사이즈 - {{tmp.selectedSize}}</span>
									</div>
									<div class='each_product_info_quantity'>
										<a href='' class='first_a' data-ng-click='quantity_minus($event)' data-ng-model='basket_list_quantity_minus'><i class='fa fa-minus-circle fa-2x first_i' id='basket_list_quantity_minus_{{$index}}' aria-hidden='true'></i></a>
										<input class='quantity_input' type='text' data-ng-model='basket_list_quantity' value='basket_list_quantity_{{$index}}' name='countarr' disabled/>
										<a href='' class='second_a' data-ng-click='quantity_plus($event)' data-ng-model='basket_list_quantity_plus'><i class='fa fa-plus-circle fa-2x second_i' id='basket_list_quantity_plus_{{$index}}' aria-hidden='true'></i></a>
									</div>
								</div>
							</div>
							<div data-ng-if='$index > 0'>
								<div data-ng-if='basket_list[$index-1].productname != basket_list[$index].productname' style='margin-top: 30px;'>
									<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
										<div class='each_product_checkbox'>
											<input type='checkbox' data-ng-checked='isChecked' value='basket_list_{{$index}}_check'>
										</div>
										<div class='each_product_image'>
											<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
										</div>
									</div>
									<div class='each_product_info_all'>
										<div class='each_product_info_productName'>
											<span class='cart_item_name'>{{tmp.kind}} - {{tmp.productname}}</span>
										</div>
										<div class='each_product_info_size'>
											<span>사이즈 - {{tmp.selectedSize}}</span>
										</div>
										
									</div>
								</div>
								<div data-ng-if='basket_list[$index-1].productname == basket_list[$index].productname'>
									<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
										<div class='each_product_checkbox'>
											<input type='checkbox' style='visibility: hidden;' data-ng-checked='isChecked' value='basket_list_{{$index}}_check'>
										</div>
										<div class='each_product_image' style='width:80px;'>
											<img style='width: 80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
										</div>
									</div>
									<div class='each_product_info_all'>
										<div class='each_product_info_productName' style='display:none;'>
											<span class='cart_item_name'>{{tmp.kind}} - {{tmp.productname}}</span>
										</div>
										<div class='each_product_info_size'>
											<span>사이즈 - {{tmp.selectedSize}}</span>
										</div>
										
									</div>
								</div>
							</div>
						</div>`)($scope);
				cart_form.append(each_product);
				*/
				//리스트 개수만큼 반복하기위한 반복문
				for(let i=0; i<$scope.basket_list.length; i++){
					//상품의 이름을 저장할 변수
					let productname = $scope.basket_list[i].productname;
					
					let each_product = angular.element("<div class='each_product' id='each_product_"+(i+1)+"'></div>");
					let each_product_checkbox_image_all = angular.element("<div class='each_product_checkbox_image_all'></div>");
					let each_product_checkbox = angular.element("<div class='each_product_checkbox'></div>");
					let each_product_image = angular.element("<div class='each_product_image'></div>");
					each_product_checkbox_image_all.append(each_product_checkbox);
					each_product_checkbox_image_all.append(each_product_image);
					
					let each_product_info_productName = angular.element("<div class='each_product_info_productName'></div>");
					if(i == 0) {//처음 반복할 때 이미지, 체크박스 넣기
						//each_product_image.append(angular.element(~~어쩌구저쩌구));
						//이런식의 동적으로 요소를 추가할 때 $compile를 사용하지 않고 angular.elemnt()는 data-ng-check가 동작하지 않는다
						each_product_checkbox.append($compile("<input type='checkbox' href='#' id='input_checkbox_"+(i+1)+"' data-ng-click='isSelectedCheckbox($event)' data-ng-checked='isCheckedBox_"+i+"' data-ng-model='basket_list_"+i+"_check' value='basket_list_"+i+"_check'>")($scope));
						//처음 페이지 로딩될 때 checkbox 체크 상태를 true로 설정하기
						eval("$scope.isCheckedBox_"+i+" = true");
						each_product_image.append($compile("<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }"+$scope.basket_list[i].profile+"' alt=''>")($scope));
						each_product.append(each_product_checkbox_image_all);
						//상품 종류와 상품명을 보여준다.↓
						each_product_info_productName.append($compile("<span class='cart_item_name'>"+$scope.basket_list[i].kind+" - "+$scope.basket_list[i].productname+"</span>")($scope));
					}
					if(i > 0){// 맨 처음이 아니면서 두번째부터 조건을 검사하는데
						if($scope.basket_list[i-1].productname != productname){//현재 인덱스의 상품 이름과 전의 인덱스의 상품 이름과 다른 지 여부
							//다르면 이미지, 체크박스를 넣어준다.
							each_product.append(angular.element("<div style='margin-top: 30px;'>"));
							//each_product_image.append(angular.element(~~어쩌구저쩌구));
							//이런식의 동적으로 요소를 추가할 때 $compile를 사용하지 않고 angular.elemnt()는 data-ng-check가 동작하지 않는다
							each_product_checkbox.append($compile("<input type='checkbox' id='input_checkbox_"+(i+1)+"' data-ng-click='isSelectedCheckbox($event)' data-ng-checked='isCheckedBox_"+i+"' data-ng-model='basket_list_"+i+"_check' value='basket_list_"+i+"_check'>")($scope));
							//처음 페이지 로딩될 때 checkbox 체크 상태를 true로 설정하기
							eval("$scope.isCheckedBox_"+i+" = true");
							each_product_image.append($compile("<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }"+$scope.basket_list[i].profile+"' alt=''>")($scope));
							each_product.append(each_product_checkbox_image_all);
							//상품 종류와 상품명을 보여준다.↓
							each_product_info_productName.append($compile("<span class='cart_item_name'>"+$scope.basket_list[i].kind+" - "+$scope.basket_list[i].productname+"</span>")($scope));
						}
						else if($scope.basket_list[i-1].productname == productname){//현재 인덱스의 상품 이름과 전의 인덱스의 상품 이름과 같은 지 여부
							//같으면 사진 이미지와 체크박스를 넣지 않고 사진 크키만큼 빈 공간을 준다.
							//체크박스는 언제든 있어야한다. 다만 상품당 하나만 보이면 되기 때문에 여기서 생성된 체크박스는 안보이도록 가린다.
							each_product_checkbox.append($compile("<input type='checkbox' id='input_checkbox_"+(i+1)+"' style='visibility: hidden;' data-ng-checked='isCheckedBox_"+i+"' data-ng-model='basket_list_"+i+"_check' value='basket_list_"+i+"_check'>")($scope));
							//처음 페이지 로딩될 때 checkbox 체크 상태를 true로 설정하기
							eval("$scope.isCheckedBox_"+i+" = true");
							each_product_image.css('width','80px');
							each_product.append(each_product_checkbox_image_all);
							//each_product_info_productName 객체의 style 효과인 display:none; 을 추가한다.
							each_product_info_productName.css('display','none');
						}
					}
					let each_product_info_all = angular.element("<div class='each_product_info_all'></div>");
					each_product_info_all.append(each_product_info_productName);
					let each_product_info_size = angular.element("<div class='each_product_info_size'></div>");
					each_product_info_size.append(angular.element("<span>사이즈 - "+$scope.basket_list[i].selectedSize+"</span>"));
					each_product_info_all.append(each_product_info_size);
					let each_product_info_quantity = angular.element("<div class='each_product_info_quantity'></div>");
					each_product_info_quantity.append($compile("<a href='' class='first_a' data-ng-click='quantity_minus($event)' data-ng-model='basket_list_quantity_minus_"+i+"'><i class='fa fa-minus-circle fa-2x first_i' id='basket_list_quantity_minus_"+i+"' aria-hidden='true'></i></a>")($scope));
					//$scope.running_230_quantity_0 = 5; -> 이런식의 코드를 각 사이즈에 초기수량에 맞게 eval 함수를 통해 동적으로 코딩. 
					eval("$scope.basket_list_quantity_"+i+"="+$scope.basket_list[i].selectedQuantity);
					//$scope.basket_list_quantity_"+i+"의 수량을 임시로 저장할 변수 선언.
					eval("$scope.basket_list_quantity_temp_"+i+"="+$scope.basket_list[i].selectedQuantity);
					each_product_info_quantity.append($compile("<input class='quantity_input' type='text' data-ng-model='basket_list_quantity_"+i+"' value='{{basket_list_quantity_"+i+"}}' name='countarr' disabled/>")($scope));
					each_product_info_quantity.append($compile("<a href='' class='second_a' data-ng-click='quantity_plus($event)' data-ng-model='basket_list_quantity_plus_"+i+"'><i class='fa fa-plus-circle fa-2x second_i' id='basket_list_quantity_plus_"+i+"' aria-hidden='true'></i></a>")($scope));
					each_product_info_all.append(each_product_info_quantity);
					
					let each_product_info_price = angular.element("<div class='each_product_info_price'></div>");
					//$scope.running_240_price_0 = 30000; //-> 이런 느낌으로 eval 함수를 통해 문자열과 변수들을 결합하여 동적으로 모델에 가격을 저장한다. 
					eval("$scope.basket_list_price_"+i+"="+$scope.basket_list[i].selectedPrice);
					//$scope.basket_list_quantity_"+i+"의 가격을 임시로 저장할 변수 선언.
					eval("$scope.basket_list_price_temp_"+i+"="+$scope.basket_list[i].selectedPrice);
					//$filter를 이용해 html 코드에 가격이 세자리 수씩 콤마로 찍히도록 필터 기능을 적용함.
					each_product_info_price.append($compile("<span data-ng-model='basket_list_price_"+i+"' value='{{basket_list_price_"+i+"}}'>{{basket_list_price_"+i+" | number:0}}원</span>")($scope));
					each_product_info_all.append(each_product_info_price);
					each_product.append(each_product_info_all);
					
					let each_product_delete = angular.element("<div class='each_product_delete'></div>");
					each_product_delete.append($compile("<a href='' data-ng-click='product_info_delete($event)' style='color:#000000;'><i class='fa fa-times fa-2x ' id='basket_list_delete_X_"+i+"' aria-hidden='true' style='text-align:right; margin-left:10px;'></i></a>")($scope));
					each_product.append(each_product_delete);
					
					cart_form.append(each_product);
				}
				//전체 수량과 전체 가격을 hidden으로 form에 추가한다.
				cart_form.append($compile("<input type='hidden' name='totalPrice' value='{{product_all_price}}' />")($scope));
				cart_form.append($compile("<input type='hidden' name='buycount' value='${buycount}'/>")($scope));
				cart_form.append("<button class='form_submit_button' type='submit' style='display:none;'>구매하기</button>");
				
				//상품의 총 가격을 보여주는 오른쪽 부분
				let cart_body_right = angular.element(document.querySelector(".cart_body_right"));
				cart_body_right.css("margin-left", "1.5rem");
				let cart_body_right_dl = angular.element("<dl class='cart_body_right_dl'></dl>");
				cart_body_right.append(cart_body_right_dl);
				cart_body_right_dl.append($compile("<dt>전체 합계</dt>")($scope));
				cart_body_right_dl.append($compile("<dd class='cart_body_right_dd'></dd>")($scope));
				let cart_body_right_dd = angular.element(document.querySelector(".cart_body_right_dd"));
				cart_body_right_dd.append($compile("<ul class='order_list' style='list-style:none; padding-left:0; margin-bottom: 10px; padding-bottom:10px; border-bottom: 1px solid black;'></ul>")($scope));
				let order_list = angular.element(document.querySelector(".order_list"));
				order_list.append($compile(
						`<li class='order_buycount' style='display:flex; justify-content: space-between; margin-bottom: 10px;'>
							<div>상품 수</div>
							<span>{{buycount}}개</span>
						 </li>`)($scope));
				order_list.append($compile(
						`<li class='order_amount' style='display:flex; justify-content: space-between; margin-bottom: 10px;'>
							<div>상품 금액</div>
							<span><span data-ng-bind='product_all_price | number:0'></span>원</span>
						 </li>`)($scope));
				cart_body_right_dl.append($compile(
						`<div class='cart_body_right_totalPrice' style='display:flex; justify-content: space-between; align-items: center; margin: 10px 0;'>
							<Strong>전체 주문금액</Strong>
							<span><span style='font-weight: bold; font-size: 25px;' data-ng-bind='product_all_price | number:0'></span>원</span>
						 </div>`)($scope));
				cart_body_right_dl.append($compile(
						`<button data-ng-click='form_submit();' class='cart_body_right_buy btn btn-primary btn-lg' style='width:100%; background: #006FD8;'>
							구매하기
						</button>`)($scope));
			}
		},function (error){

		});
		
		//가격에 3자리 수마다 ,(콤마)를 붙일 수 있는 정규식을 이용한 함수
		function numberWithCommas(x) {
		    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	});
	/*
	myApp.directive("addelements", function($compile, $http) {
		return function(scope, element, attrs) {
			angular.element(document).ready(function(e) {
				if(scope.isValidBasket == true){ //장바구니 관련 ajax 요청이 잘 왔을 때만 동적 요소 생성하기.
					element.append($compile("<div class='cart_select_delete'></div>")(scope));
					let cart_select_delete = angular.element(document.querySelector(".cart_select_delete"));
					cart_select_delete.append($compile("<input type='checkbox' id='select_all'><label for='select_all' href='#'>전체선택</label>")(scope));
					cart_select_delete.append($compile("<li><button class='selected_del' href='#'>선택삭제</button></li>")(scope));
					cart_select_delete.append($compile("<div class='cart_body' style='display:flex; width: 100%; height: 500px;'></div>")(scope));
					let cart_body = angular.element(document.querySelector(".cart_body"));
					cart_body.append($compile("<div class='cart_body_left' style='flex-grow:2;background-color: white; height:500px;'></div>")(scope));
					cart_body.append($compile("<div class='cart_body_right' style='flex-grow:1;background-color: yellow; height:500px;'></div>")(scope));
					
					let cart_body_left = angular.element(document.querySelector(".cart_body_left"));
					cart_body_left.append($compile("<dl class='cart_body_left_dl'></dl>")(scope));
					let cart_body_left_dl = angular.element(document.querySelector(".cart_body_left_dl"));
					cart_body_left_dl.append($compile("<dt></dt>")(scope));
					cart_body_left_dl.append($compile("<dd class='cart_body_left_dd'></dd>")(scope));
					let cart_body_left_dd = angular.element(document.querySelector(".cart_body_left_dd"));
					cart_body_left_dd.append($compile("<div class='cart_item_info'></div>")(scope));
					let cart_item_info = angular.element(document.querySelector(".cart_item_info"));
				}
			});
		};
	});
	
	//라우터를 사용하기 위한 설정(페이지를 전체 로딩이 아닌 부분 업데이트만 쉽게 할 수 있도록 도와주는 라우터 기능)
	myApp.config(function($routeProvider, $locationProvider){ //앵귤러에서 제공하는 객체는 "$" 기호가 붙는다.
		$locationProvider.hashPrefix(''); // add configuration
		console.log($routeProvider);
		$routeProvider
		.when("/basket_list", {templateUrl:"basket_list.jsp"}) //이 템플릿(userListCtrl) url에서 사용할 컨트롤러 등록하기
		//.otherwise({redirectTo:"/home"}) //위에 두개의 요청(/home, /user_list)가 아니라면 /home 경로로 리다일렉트 요청(이동)을 하라는 뜻.
	});
	*/
</script>
<script type="text/ng-template" id="basket_list.jsp">
    <div class='cart_select_delete'>
		<input type='checkbox' id='select_all' data-ng-model='all_select' data-ng-checked='isChecked' data-ng-click='all_select_changed()'>
		<label for='select_all' href='#'>전체선택</label>
		<li><button class='selected_del' href='#'>선택삭제</button></li>
	</div>
	<div class='cart_body'>
		<div class='cart_body_left'>
			<dl class='cart_body_left_dl'>
				<dt>상품 목록 보기</dt>
				<dd class='cart_body_left_dd'>
					<div class='cart_item_info'>
						<form action='private/buy_form.do 'name='product' method='post' novalidate>
							<div class='each_product' id='each_product_' data-ng-repeat="(key,val) in basket_list">
								<div data-ng-if='$index == 0'>
									<div data-ng-repeat='(k,v) in val'>
										<div data-ng-if="k == 'basketIndex'">
											<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
												<div class='each_product_checkbox'>
													<input type='checkbox' data-ng-checked='isChecked' data-ng-model="basket_list_check" value='basket_list_{{$index}}_check'>
												</div>
												<div class='each_product_image'>
													<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div data-ng-if='$index > 0'>
									{{basket_list[$index].basketIndex}}
									<div data-ng-if='basket_list[$index-1].productname != basket_list[$index].productname'>
										<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
											<div class='each_product_checkbox'>
												<input type='checkbox' data-ng-checked='isChecked' data-ng-model='basket_list_check' value='basket_list_{{$index}}_check'>
											</div>
											<div class='each_product_image'>
												<img style='width: 80px; height:80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
											</div>
										</div>
									</div>
									<div data-ng-if='basket_list[$index-1].productname == basket_list[$index].productname'>
										<div class='each_product_checkbox_image_all' data-ng-model='each_product_checkbox_image_all'>
											<div class='each_product_checkbox'>
												<input type='checkbox' style='visibility: hidden;' data-ng-checked='isChecked' data-ng-model='basket_list_check' value='basket_list_{{$index}}_check'>
											</div>
											<div class='each_product_image'>
												<img style='width: 80px;' class='card-img-top' src='${pageContext.request.contextPath }${productDto.profile}' alt=''>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</dd>
			</dl>
		</div>
		<div class='cart_body_right'></div>
	</div>
</script>
</head>
<body data-ng-controller="shoppingBasket_Ctrl">
	<%-- 
	<c:forEach var="tmp" items="${list }">
		상품 이름 : ${tmp.productname }<br>
		상품 사이즈 : ${tmp.sbsize }<br>
		상품 수량 : ${tmp.sbcount }<br>
	</c:forEach>
	 --%>
	<jsp:include page="../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container">
		<h1 class="shopping_basket"><strong>장바구니</strong></h1>
	</div>
	<hr />
	<div class="container">
		<h5 class="product_list"><a>상품목록<a style='margin-left: 7px; font-size:1rem;'>${buycount }</a><span class="buycount_deco"></span></a></h5>
	</div>
	<hr style="margin-bottom: 0;" />
	<div class="cart_part container" addelements>
		<div data-ng-view></div>
	</div>
	
	<jsp:include page="../include/footer.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
<script>
	document.addEventListener('scroll', function() {//스크롤 이벤트가 발생했을 때
		let header_height = document.querySelector( '#header-div' ).offsetHeight;
		let cart_select_delete_height = document.querySelector( '.cart_select_delete' ).offsetHeight;
		document.querySelector( '.cart_select_delete' ).style["top"] = header_height+"px";
		document.querySelector( '.cart_body_right_dl' ).style["top"] = (header_height+cart_select_delete_height)+"px";
	});
</script>
</body>
</html>