<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/notice/list.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<style>
 .color:visited { color: black; text-decoration: none;}
 .color:hover { color: black; text-decoration: none;}
  .jb-th-1 {
    width: 600px;
  }
</style>
</head>
<body>

<jsp:include page="../include/header.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>

<!-- Page Content -->
<div class="container">
	<br />
	<h4><strong>공지사항</strong></h4>
	<br />
	<table class="table table-striped table-sm">
		<thead>
			<tr>				
				<th class="jb-th-1">제목</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="tmp" items="${list }">
			<tr>
				<td><a class="color" href="detail.do?num=${tmp.num }&condition=${condition }&keyword=${encodedK }">${tmp.title }</a></td>
				<td>${tmp.viewCount }</td>
				<td>${tmp.regdate }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<a class="color" href="private/insertform.do">새글 작성</a>
</div>

</body>
</html>






