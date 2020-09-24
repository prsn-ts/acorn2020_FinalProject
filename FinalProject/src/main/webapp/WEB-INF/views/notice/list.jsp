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
 .color:link { color: black; text-decoration: none;}
 .color:visited { color: black; text-decoration: none;}
 .color:hover { color: black; text-decoration: none;}
 .color{
  	text-align: right;
  }
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
	<div class="page-display">
		<ul class="pagination pagination-sm">
		<c:if test="${startPageNum ne 1 }">
			<li class="page-item"><a class="page-link" href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${encodedK }">Prev</a></li>
		</c:if>
		<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
			<c:choose>
				<c:when test="${i eq pageNum }">
					<li class="page-item active"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK }">${i }</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK }">${i }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${endPageNum lt totalPageCount }">
			<li class="page-item"><a class="page-link" href="list.do?pageNum=${endPageNum+1 }&condition=${condition }&keyword=${encodedK }">Next</a></li>
		</c:if>
		</ul>
		<div class="color">
			<c:if test="${id eq 'admin'}">
				<a class="color" href="private/insertform.do"><button type="button" class="btn btn-outline-dark btn-sm">공지 작성</button></a>
			</c:if>
		</div>		
	</div>
	<hr style="clear:left;"/>
	<form action="list.do" method="get">
		<label for="condition"></label>
		<select name="condition" id="condition">
			<option value="title_content" <c:if test="${condition eq 'title_content' }">selected</c:if>>제목+내용</option>
		</select>
		<input value="${keyword }" type="text" name="keyword" placeholder="검색어..."/>
		<button type="submit">검색</button>
	</form>	
</div>

</body>
</html>






