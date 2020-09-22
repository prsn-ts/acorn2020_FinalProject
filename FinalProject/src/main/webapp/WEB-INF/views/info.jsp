<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회사소개</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>

</head>
<body>


<jsp:include page="include/header.jsp">
	<jsp:param value="info" name="thisPage"/>
</jsp:include>

<h1>	인포페이지입니다.</h1>


<jsp:include page="include/footer.jsp">
	<jsp:param value="info" name="thisPage"/>
</jsp:include>



</body>
</html>