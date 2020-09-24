<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>회원정보 수정 폼</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<!-- angularjs 로딩 -->
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<style>
	.updateAndCancel {
		display: flex; 
		justify-content: space-between;
		margin-top: 20px;
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 15px;
	}
</style>
<script>
	var myApp=angular.module("myApp", []);
	
	myApp.controller("update_form_Ctrl", function($scope, $http){
		//email 데이터 가져오기
		var email_all = "${dto.email }";
		
		// dto에 email 데이터 분리.
		function splitEmail(email_all) {
		    var emails = email_all;
		    emails = emails.split("@");
		    $scope.email = emails[0];
		    $scope.email_second = emails[1];
		}
		
		//페이지 로딩 시에 분리된 email 데이터 얻어내기
		splitEmail(email_all);
		
		//페이지 로딩 시에 휴대폰 번호 값 세팅.
		$scope.phone_num = "${dto.phone_num }";
		
		//페이지 로딩 되었을 때 보여줄 select 요소 지정 
		$scope.email_select = "직접 입력";
		
		//select 요소가 바뀌었을 때 호출될 함수
		$scope.select_change = function(){
			$scope.email_second = $scope.email_select;
			if($scope.email_second == "직접 입력"){
				$scope.email_second="";
			}
		};
		
		//휴대폰 입력창에 keyup 이벤트가 일어났을 때 호출될 함수 등록
		$scope.phone_num_input=function(e){
			console.log($scope.phone_num_input.length);
			console.log(e.target.value);
			console.log($scope);
			//입력받은 값을 저장.
			$scope.phone_num_comp = e.target.value;
		};
		
		//이메일 입력창(email)에 keyup 이벤트가 일어났을 때 호출될 함수 등록
		$scope.email_input=function(e){
			console.log($scope.email_input.length);
			console.log(e.target.value);
			console.log($scope);
			//입력받은 값을 저장.
			$scope.email_comp = e.target.value;
		};
		
		//이메일 입력창(email_second)에 keyup 이벤트가 일어났을 때 호출될 함수 등록
		$scope.email_second_input=function(e){
			console.log($scope.email_second_input.length);
			console.log(e.target.value);
			console.log($scope);
			//입력받은 값을 저장.
			$scope.email_second_comp = e.target.value;
		};
	});
</script>
<body data-ng-controller="update_form_Ctrl">
	<jsp:include page="../../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container" style="width:460px; margin: 0 auto; margin-top: 100px;">
		<div style="text-align:center;">
			<h2><img style="width:1.5em;height:1.5em;margin-right:15px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="logo" />회원정보 변경</h2>
		</div>
		<div class="panel-body">
		    <form name="user" class="user" action="update.do" method="post">
				<div class="form-group">
					<label for="id">아이디</label>
					<input type="text" class="form-control form-control-user" id="id" name="id" value="${dto.id}" disabled/>
				</div>
				<div class="form-group">
					<label for="phone_num">휴대폰 번호</label>
					<input type="text" data-ng-model="phone_num" data-ng-required="true" name="phone_num" class="form-control" id="phone_num"
						data-ng-class="{'is-invalid': (user.phone_num.$invalid && user.phone_num.$dirty && phone_num_comp.length!=0), 'is-valid': user.phone_num.$valid && phone_num_comp.length!=0}"
						data-ng-keyup="phone_num_input($event)"
						data-ng-pattern="/^01(?:0|1|[6-9])-\d{3,4}-\d{4}$/" />
				</div>
				<label for="email_all">이메일</label>
				<div class="form-row">
					<div class="col">
						<input type="text" data-ng-model="email" data-ng-required="true" class="form-control mb-2" id="email" name="email"
							data-ng-class="{'is-invalid': (user.email.$invalid && user.email.$dirty && email_comp.length!=0), 'is-valid': user.email.$valid && email_comp.length!=0}"
							data-ng-keyup="email_input($event)"
							data-ng-pattern="/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*$/i"/>
					</div>
					<div class="col">
						<div class="input-group">
							<div class="input-group-prepend">
								<div class="input-group-text">@</div>
							</div>
							<input type="text" data-ng-model="email_second" data-ng-bind="email_second" data-ng-required="true" class="form-control" id="email_second" name="email_second" placeholder="" value=""
								data-ng-class="{'is-invalid': (user.email_second.$invalid && user.email_second.$dirty && email_second_comp.length!=0), 'is-valid': user.email_second.$valid && (email_second_comp.length!=0 || email_select!=0)}"
								data-ng-keyup="email_second_input($event)"
								data-ng-pattern="/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.+[a-zA-Z]{2,3}$/i"/>
						</div>
					</div>
					<div class="col">
						<select class="custom-select mr-sm-2" id="email_select" data-ng-required="true" data-ng-model="email_select" data-ng-change="select_change()">
					       <option selected>직접 입력</option>
					       <option value="naver.com">naver.com</option>
					       <option value="hanmail.net">hanmail.net</option>
					       <option value="gmail.com">gmail.com</option>
					       <option value="nate.com">nate.com</option>
					       <option value="hotmail.com">hotmail.com</option>
					    </select>
					</div>
				</div>
				<div class="updateAndCancel">
					<button type="reset" class="btn btn-danger btn-block" 
						onclick="window.location.href='${pageContext.request.contextPath}/home.do'">취소</button>
					<button id="submitBtn" type="submit" style="margin:0;"
						data-ng-disabled="user.$invalid" class="btn btn-primary btn-block">수정확인</button>
			    </div>
			</form>
	    </div>
	</div>
</body>
</html>