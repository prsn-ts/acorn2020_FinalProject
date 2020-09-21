<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<jsp:include page="../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
  
	<div class="container" style="width:460px; margin: 0 auto;">
		<h2>로그인</h2>
		<div class="panel-body">
		    <form class="user" action="login.do" method="post">
				<input type="hidden" name="url" value="${url }">
				<div class="form-group">
				  <input value="${savedId }" type="text" name="id" class="form-control" id="id" placeholder="아이디">
				  <span id="idMsg" style="display:none;color:#FF0000;">아이디를 입력해주세요.</span>
				</div>
				<div class="form-group">
				  <input value="${savedPwd }" type="password" name="pwd" class="form-control" id="pwd" placeholder="비밀번호">
				  <span id="pwMsg" style="display:none;color:#FF0000;">비밀번호를 입력해주세요.</span>
				</div>
				<button type="submit" class="btn-primary btn-lg btn-block">로그인</button>
				<div class="form-group">
				    <input type="checkbox" name="isSave" value="yes" ${savedChecked }>
				    <span>아이디 비밀번호 저장</span>
				</div>
		    </form>
		    <div class="etc_line"></div>
		    <div class="join_btn">
		        <a href="/user/join.html">회원가입</a>
		        <a href="/user/find_passwd.html">비밀번호 찾기</a>
		        <a href="javascript:doGuest()">비회원주문</a>
		        <a href="javascript:doGuesOrder()">비회원주문조회 </a>
		    </div>
		    <div id="guestOrder" style="display:none;margin-top:5px" class="join_b_order">
		        <form name="form_guest_order" id="form_guest_order" method="post"  action="?" onSubmit="return doGuestOrderSubmit()">
		            <div class="form-group">
		                <input type="text" name="OrderNm" id="OrderNm" class="form-control border_none" placeholder="주문자명">
		            </div>
		            <div class="form-group">
		                <input type="text" name="OrderNo" id="OrderNo" class="form-control" placeholder="주문번호">
		            </div>
		            <button type="submit" class="btn btn-default btn-lg btn-block mag_t_10">주문조회</button>
		            <div class="info">주문번호와 비밀번호를 잊으신 경우,<br><a href="mailto:help@10000recipe.com">[☞고객센터]</a>로 문의해주세요.</div>
		        </form>
		    </div>
			    <button type="button" onClick="location.href='https://www.10000recipe.com/user/login_fb.html?q_path=' +encodeURIComponent('') " class="btn btn-primary btn-lg btn-block join_f"><span>페이스북으로 로그인</span></button>
			    <button type="button" onClick="loginWithKakao()" class="btn btn-primary btn-lg btn-block join_k"><span>카카오톡으로 로그인</span></button>
			    <button type="button" onClick="loginWithNaver()" class="btn btn-primary btn-lg btn-block join_n"><span>네이버로 로그인</span></button>
			    <button type="button" onClick="loginWithGoogle()" class="btn btn-primary btn-lg btn-block join_g" id="gooleSigninButton"><span>구글로 로그인</span></button>
	    </div>
	</div>
  
	<jsp:include page="../include/footer.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
</body>
</html>