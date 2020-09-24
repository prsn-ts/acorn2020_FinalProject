<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정 폼</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
</head>

<body>
	<jsp:include page="../../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>

	<div class="container" style="width:460px; margin: 0 auto; margin-top: 100px;">
		<div style="text-align:center;">
			<h2><img style="width:1.5em;height:1.5em;margin-right:15px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="logo" />비밀번호 변경</h2>
		</div>
		<div style="margin-top:20px;">
			<form class="user" action="pwd_update.do" method="post" id="myForm">
				<div class="form-group">
					<label for="pwd">기존 비밀번호</label>
					<input type="password" class="form-control form-control-user" name="pwd" id="pwd" placeholder="Password"/>
				</div>
				<div class="form-group">
					<label for="newPwd">새 비밀번호</label>
					<input type="password" class="pw form-control form-control-user" name="newPwd" id="newPwd" placeholder="New Password"/>
				</div>
				<div class="form-group">
					<label for="newPwd2">새 비밀번호 확인</label>
					<input type="password" class="pw form-control form-control-user" name="newPwd2" id="newPwd2" placeholder="New Password"/>
					<span id="checkPassword"></span>
				</div>
				<button type="submit" class="btn btn-primary btn-user btn-block">수정하기</button>
			</form>
		</div>
	</div>
	
<script>
	//비밀번호 유효성 정규표현식(숫자+영문자+특수문자 조합으로 8자리 이상)
	var reg_pwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
	//기존 비밀번호를 읽어온다.
	var pwd=$("#pwd").val();
	//입력한 새로운 비밀번호 2개를 읽어와서 
	var pwd1=$("#newPwd").val();
	var pwd2=$("#newPwd2").val();

	//기존 비밀번호를 눌렀을 때 이벤트 처리
	$("#pwd").keyup(function() {
		//입력한 문자열을 읽어와서 변수에 담기
		pwd=$(this).val();
		$.ajax({
			method:"post",
			url:"pwd_check.do",
			data:"pwd="+pwd, // data:{msg:msg} 도 가능
			success:function(data){
				//data 는 {"isSuccess":true} 인 object 이다.
				if(data.isExist && pwd.length != 0){
					$("#pwd").addClass("is-valid").removeClass("is-invalid");
				}else if(!data.isExist && pwd.length != 0){
					$("#pwd").addClass("is-invalid").removeClass("is-valid");
				}else if(!data.isExist && pwd.length == 0){
					$("#pwd").removeClass("is-valid is-invalid");
				}
			}
		});
	});
	
	//새로운 비밀번호(newPwd)를 눌렀을 때 이벤트 처리
	$("#newPwd").keyup(function() {
		pwd1=$(this).val();
		//비밀번호가 유효하지 않은 경우
		if(!(reg_pwd.test(pwd1))){
			$("#newPwd").addClass("is-invalid").removeClass("is-valid");
		}else{
			$("#newPwd").addClass("is-valid").removeClass("is-invalid");
		} 
		if(!(reg_pwd.test(pwd1)) && pwd1.length == 0){
			$("#newPwd").removeClass("is-valid is-invalid");
		}
	});
	
	//새로운 비밀번호(newPwd2)를 눌렀을 때 이벤트 처리
	$("#newPwd2").keyup(function() {
		pwd2=$(this).val();
		//비밀번호가 유효하지 않은 경우
		if(!(reg_pwd.test(pwd2))){
			$("#newPwd2").addClass("is-invalid").removeClass("is-valid");
		}else{
			$("#newPwd2").addClass("is-valid").removeClass("is-invalid");
		}
		if(!(reg_pwd.test(pwd2)) && pwd2.length == 0){
			$("#newPwd2").removeClass("is-valid is-invalid");
		}
	});

	$('.pw').focusout(function () {
	    pwd1 = $("#newPwd").val();
	    pwd2 = $("#newPwd2").val();
	
	  //비밀번호 입력칸이 비어있지 않고 비밀번호 확인칸이 비어있을 때
        if ((pwd1 != '' && pwd2 == '') || (pwd1 == '' && pwd2 != '')) {
        	$("#checkPassword").text("비밀번호, 비밀번호 확인칸 모두 입력해주세요.").removeClass("text-success").css({'color':'#d92742','font-weight':'bold'});
        } else if (pwd1 != "" || pwd2 != "") {
            if (pwd1 == pwd2) {		
                $("#checkPassword").text("비밀번호가 일치합니다.").addClass("text-success");
            } else {
            	$("#checkPassword").text("비밀번호가 일치하지 않습니다.").removeClass("text-success").css({'color':'#d92742','font-weight':'bold'});
            }
        }
      	//비밀번호 입력칸과 비밀번호 확인칸에 아무것도 입력하지않고 뗐을 시 중복확인 문자 사라지게 하기
		if(pwd1.length == 0 && pwd2.length == 0){
			$("#checkPassword").text("");
			//form 안에 있는 일반 버튼을 눌러도 폼이 전송 되기 때문에 폼 전송을 막아준다.
			return false;
		}
	});
	
	//id 가 myForm  인 곳에 submit 이벤트가 일어 났을때 실행할 함수 등록 
	$("#myForm").on("submit", function(){
		//비밀번호 입력칸과 비밀번호 확인칸이 일치하지 않을 때
        if(pwd1 != pwd2){
        	//알림을 띄우고
        	alert("비밀번호, 비밀번호 확인이 일치하지 않습니다.");
        	//폼 제출을 막는다.
        	return false;
        }else if(pwd1 == pwd2){ //비밀번호 입력칸과 비밀번호 확인칸이 일치할 때
            //비밀번호가 유효하지 않은 경우
            if(!(reg_pwd.test(pwd1) && reg_pwd.test(pwd2))){
            	//알림을 띄우고
                alert('영문자+숫자+특수문자 조합으로 8자리 이상 적어주세요.');
            	//포커스 잡아주고
                $('#newPwd').val("").focus();
                $('#newPwd2').val("");
                $("#newPwd").removeClass("is-valid is-invalid");
    			$("#newPwd2").removeClass("is-valid is-invalid");
             	//폼 제출을 막는다.
                return false;
            } 
        }
		//새로운 비밀번호를 입력하지 않으면
		if(pwd1.length == 0 || pwd2.length == 0){
			//알림을 띄우고 
			alert("새로운 비밀번호를 정확히 입력해주세요.");
			//비밀번호 입력란을 초기화 하고 포커스도 주고 
			$("#newPwd").val("").focus();
			$("#newPwd2").val("");
			$("#newPwd").removeClass("is-valid is-invalid");
			$("#newPwd2").removeClass("is-valid is-invalid");
			//폼전송을 막는다. 
			return false;
		}
		//기존 비밀번호와 새로운 비밀번호가 같으면
		if((pwd === pwd1) && (pwd === pwd2) && (pwd1 === pwd2)){
			//알림을 띄우고 
			alert("기존 비밀번호와 다른 새로운 비밀번호를 입력해주세요.");
			//비밀번호 입력란을 초기화 하고 포커스도 주고 
			$("#newPwd").val("").focus();
			$("#newPwd2").val("");
			$("#newPwd").removeClass("is-valid is-invalid");
			$("#newPwd2").removeClass("is-valid is-invalid");
			//폼전송을 막는다. 
			return false;
		}
	});
</script>
</body>
</html>