<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
	if(${isSuccess} == true){
		
		alert("결제가 완료되었습니다.");
		location.href="${pageContext.request.contextPath }/shop/shop.do";
	}
	if(${isSuccess} ==false){
		alert("계좌 금액이 부족합니다 금액을 충전해주세요");
		location.href="${pageContext.request.contextPath }/login/private/info.do";
	}

</script>
</body>
</html>