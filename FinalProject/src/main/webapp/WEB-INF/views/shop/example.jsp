<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<p>${param.num }</p>


<button type="button" onclick="location.href='productupdate.do?num=${num }'">수정</button>
<button type="button"onclick="confirm('삭제하시겠습니까?');location.href='productdelete.do?num=${num }'">삭제</button>
</body>
</html>