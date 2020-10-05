<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 보기</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<style>
	.updateAndDelete {
		display: flex; 
		justify-content: space-between;
		padding:10px; 
		font-family: 'Nanum Gothic', sans-serif;
		font-size: 15px;
	}
</style>
</head>
<body>
	<jsp:include page="../../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
	<div class="container" style="width:460px; margin: 0 auto; margin-top: 100px;">
		<div style="text-align:center;">
			<h2><img style="width:1.5em;height:1.5em;margin-right:15px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="logo" />회원 정보</h2>
		</div>
		<div class="panel-body">
		    <table class="table" style="margin-bottom:0;">
				<tr>
					<th><label for="id">아이디</label></th>
					<td colspan="2"><input type="text" id="id" value="${dto.id }" disabled /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td colspan="2"><a href="pwd_updateform.do">수정하기</a></td>
				</tr>
				<tr>
					<th>휴대폰 번호</th>
					<td colspan="2">${dto.phone_num }</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td colspan="2">${dto.email }</td>
				</tr>
				<tr>
					<th>보유 Money</th>
					<td id="mone">${dto.money} 원  </td>
					<td><a id="addacount" href="addMoney.do">십만원충전</a></td>
				</tr>	
				<tr>
					<th>보유 Point</th>
					<td colspan="2">${dto.point } point</td>
				</tr>							
				<tr>
					<th>가입일자</th>
					<td colspan="2">${dto.regdate }</td>
				</tr>
			</table>
			<hr style="margin-top:0;">
			<div class="updateAndDelete">
				<div>
					<a href="update_form.do">개인정보 수정</a>
				</div>
				<a href="javascript:deleteConfirm()">탈퇴</a>
			</div>
	    </div>
	</div>
<script>
	//탈퇴 버튼 누를 시 실행되는 함수
	function deleteConfirm(){
		var isDelete=confirm("${id } 회원님 탈퇴 하시겠습니까?");
		if(isDelete){
			location.href="delete.do";
		}
	}
	
	$("#addacount").on("click",function(){
		
		$.ajax({
			method:"get",
			url:"addMoney.do",
			 // data:{msg:msg} 도 가능
			success:function(data){
				$("#mone").text(data.money+"원");
				alert("십만원 충전완료");
				
				
			}
			
			
		});
		
		return false;
	})
</script>
</body>
</html>