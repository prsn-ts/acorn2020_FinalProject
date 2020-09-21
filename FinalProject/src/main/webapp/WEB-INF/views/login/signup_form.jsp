<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<title>회원가입</title>
<!-- 글씨체 관련 -->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<!-- angularjs 로딩 -->
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<style>
	.form-group {
		margin-bottom: 0.5rem;
	}
	.info_consent .info_part {
		padding:10px; 
		border:1px solid #F2F2F2;
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 14px;
	}
	.info_part {
		display: flex; 
		justify-content: space-between;
	}
	.info_part #svg {
		transform: rotateZ(180deg);
	}
</style>
<script>
	var myApp=angular.module("myApp", []);
	
	myApp.controller("signup_Ctrl", function($scope){
		//페이지 로딩 되었을 때 보여줄 select 요소 지정 
		$scope.email_select = "직접 입력";
		
		//select 요소가 바뀌었을 때 호출될 함수
		$scope.select_change = function(){
			$scope.email_second = $scope.email_select;
			if($scope.email_second == "직접 입력"){
				$scope.email_second="";
			}
		};
		//페이지 로딩 되었을 때 초기 체크박스 설정. 
		$scope.isChecked = false;
		//전체동의 체크박스를 체크했을 때 호출될 함수
		$scope.all_consent_changed = function(){
			if($scope.isChecked == false){
				$scope.isChecked = true;
			}
			else{
				$scope.isChecked = false;
			}
		};
		
		//비밀번호 입력창 값을 비교하는 데 쓰기위한 $scope 영역에 pwd 선언.
		$scope.pwd_comp="";
		
		//비밀번호 입력창에 keyup 이벤트가 일어났을 때 호출될 함수 등록
		$scope.pwd_input = function(e){
			console.log($scope);
			console.log($scope.user.pwd.$valid);
			$scope.pwd_comp= e.target.value;
		};
		
		//비밀번호 입력창에 keyup 이벤트가 일어났을 때 호출될 함수 등록
		$scope.pwd_confirm_input = function(e){
			console.log($scope.pwd_comp.length);
			console.log($scope.user.pwd_confirm.length);
			console.log($scope);
			$scope.user.pwd_confirm = e.target.value;
		};
	});
</script>
</head>
<body data-ng-controller="signup_Ctrl">
	<jsp:include page="../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
  
	<div class="container" style="width:460px; margin: 0 auto; margin-top: 100px;">
		<h2>회원가입</h2>
		<div class="panel-body">
		    <form name="user" class="user" action="login.do" method="post">
				<input type="hidden" name="url" value="${url }">
				<div class="form-group">
				  <input data-ng-model="id" type="text" name="id" data-ng-required="true" class="form-control" id="id" placeholder="아이디(필수)"
				  	data-ng-class="{'is-invalid': user.id.$invalid && user.id.$dirty, 'is-valid': user.id.$valid}"
				  	data-ng-minlength="5"
					data-ng-maxlength="20"
					data-ng-pattern="/^[A-Za-z0-9]+$/" />
					<small id="passwordHelpBlock" class="form-text text-muted">영문 대.소문자, 숫자만 최소 5자리 ~ 최대 20자리까지 입력해주세요.</small>
					<div class="invalid-feedback">아이디를 다시 입력해주세요.</div>
				</div>
				<div class="form-group">
					<input data-ng-model="nick" data-ng-required="true" type="text" name="nick" id="nick" class="form-control" placeholder="닉네임(선택)"
						data-ng-class="{'is-invalid': user.nick.$invalid && user.nick.$dirty, 'is-valid': user.nick.$valid}"
						data-ng-minlength="3"
						data-ng-maxlength="10"
						data-ng-pattern="/^[가-힇]+$/" />
					<small id="passwordHelpBlock" class="form-text text-muted">한글로, 3~10글자 이내로 입력해주세요.</small>
					<div class="invalid-feedback">닉네임을 다시 입력해주세요.</div>
				</div>
				<div class="form-group">
					<input data-ng-model="pwd" data-ng-required="true" type="password" name="pwd" class="form-control" id="pwd" placeholder="비밀번호(필수)"
						data-ng-class="{'is-invalid': (user.pwd.$invalid && user.pwd.$dirty && pwd_comp.length!=0), 'is-valid': user.pwd.$valid && pwd_comp.length!=0}"
						data-ng-keyup="pwd_input($event)"
						data-ng-minlength="8"
						data-ng-maxlength="25"
						data-ng-pattern="/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/" />
					<small id="passwordHelpInline" class="text-muted">
						비밀번호는 숫자+영문자+특수문자 조합으로 8자리 이상 써야합니다.
					</small>
				</div>
				<div class="form-group">
				  <input data-ng-model="pwd_confirm" data-ng-required="true" type="password" name="pwd_confirm" class="form-control" id="pwd_confirm" placeholder="비밀번호 확인(필수)"
				  	data-ng-keyup="pwd_confirm_input($event)"
				  	data-ng-class="{'is-invalid': (user.pwd_confirm != pwd_comp) && (user.pwd_confirm.length!=0 && pwd_comp.length!=0), 'is-valid': (user.pwd_confirm == pwd_comp) && (user.pwd_confirm.length!=0 && pwd_comp.length!=0)}" />
				  <span id="pwCfMsg" style="display:none;color:#FF0000;">비밀번호를 입력해주세요.</span>
				</div>
				<div class="form-group">
				  <input type="text" name="phone_num" class="form-control" id="phone_num" placeholder="휴대폰 번호(필수)">
				  <span id="pNumMsg" style="display:none;color:#FF0000;">휴대폰 번호를 입력해주세요.</span>
				</div>
				<div class="form-row">
					<div class="col">
						<input type="text" class="form-control mb-2" id="email" name="email" placeholder="이메일(선택)">
					</div>
					<div class="col">
						<div class="input-group">
							<div class="input-group-prepend">
								<div class="input-group-text">@</div>
							</div>
							<input type="text" class="form-control" id="email_second" name="email_second" data-ng-model="email_second" placeholder="">
						</div>
					</div>
					<div class="col">
						<select class="custom-select mr-sm-2" id="email_second" data-ng-model="email_select" data-ng-change="select_change()">
					       <option selected>직접 입력</option>
					       <option value="naver.com">naver.com</option>
					       <option value="hanmail.net">hanmail.net</option>
					       <option value="gmail.com">gmail.com</option>
					       <option value="nate.com">nate.com</option>
					       <option value="hotmail.com">hotmail.com</option>
					    </select>
					</div>
				</div>
				<div class="info_consent" style="margin: 10px 0;">
					<div class="info_part" style="background-color:#F4F4F4;">
						<div>
							<input style="vertical-align: middle" type="checkbox"
							 name="all_consent" id="all_consent" value="" data-ng-click="all_consent_changed()">
							<span>전체동의(약관 및 개인 정보 수집 동의 등)</span>
						</div>
					</div>
					<div class="info_part">
						<div>
							<input style="vertical-align: middle" type="checkbox" 
							name="buy_consent" id="buy_consent" value="" data-ng-checked="isChecked">
							<span>(필수)구매이용약관동의</span>
						</div>
						<a href="">자세히 보기</a>
					</div>
					<div class="info_part">
						<div>
							<input style="vertical-align: middle" type="checkbox"
							 name="private_consent" id="private_consent" value="" data-ng-checked="isChecked">
							<span>(필수)개인정보 수집 이용동의</span>
						</div>
						<a href="">자세히 보기</a>
					</div>
					<div class="info_part">
						<div>
							<input style="vertical-align: middle" type="checkbox"
							 name="trust_consent" id="trust_consent" value="" data-ng-checked="isChecked">
							<span>(필수)개인정보 처리위탁동의(구매 및 배송 관련)</span>
						</div>
						<a href="">자세히 보기</a>
					</div>
				</div>
				<button style="margin-bottom: 10px;" type="submit" class="btn-primary btn-lg btn-block">회원가입</button>
		    </form>
	    </div>
	</div>

	<jsp:include page="../include/footer.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
</body>
</html>