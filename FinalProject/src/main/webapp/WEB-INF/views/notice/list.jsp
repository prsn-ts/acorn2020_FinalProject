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
  table.type09 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
	}
	table.type09 thead th {
    	padding: 10px;
    	font-weight: bold;
    	vertical-align: top;
    	color: #369;
    	border-bottom: 3px solid #036;
	}
	table.type09 tbody th {
    	width: 150px;
    	padding: 10px;
    	font-weight: bold;
    	vertical-align: top;
    	border-bottom: 1px solid #ccc;
    	background: #f3f6f7;
}
	table.type09 td {
    	padding: 10px;
    	vertical-align: top;
    	border-bottom: 1px solid #ccc;
	}
	.fontst{
		font-size: 30px;
	}
	.pagination {
   		justify-content: center;
	}
	.my.pagination > .active > a, 
	.my.pagination > .active > span, 
	.my.pagination > .active > a:hover, 
	.my.pagination > .active > span:hover, 
	.my.pagination > .active > a:focus, 
	.my.pagination > .active > span:focus {
  		background: #343a40;
 	 	border-color: #343a40;
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
	<p class="fontst"><img style="width:2.5em ;height=2.5em; margin-right:10px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />공지사항</p>
	<table class="type09" style="margin-left: auto; margin-right: auto;">
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
				<td class="design"><a class="color" href="detail.do?num=${tmp.num }&condition=${condition }&keyword=${encodedK }">${tmp.title }</a></td>
				<td>${tmp.viewCount }</td>
				<td>${tmp.regdate }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br />
	<div class="page-display">
		<ul class="pagination pagination-sm pagination my">
		<c:if test="${startPageNum ne 1 }">
			<li class="page-item"><a class="page-link color" href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${encodedK }">Prev</a></li>
		</c:if>
		<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
			<c:choose>
				<c:when test="${i eq pageNum }">
					<li class="page-item active"><a class="page-link color" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK }">${i }</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link color" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK }">${i }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${endPageNum lt totalPageCount }">
			<li class="page-item"><a class="page-link color" href="list.do?pageNum=${endPageNum+1 }&condition=${condition }&keyword=${encodedK }">Next</a></li>
		</c:if>	
		</ul>
		<div class="color">
			<c:if test="${id eq 'admin'}">
				<a class="color" href="private/insertform.do"><button type="button" class="btn btn-outline-dark btn-sm">공지 작성</button></a>
			</c:if>
		</div>	
	</div>
	<hr style="clear:left;"/>
	<form class="form-inline" action="list.do" method="get">
		<label for="condition"></label>
		<select class="form-control form-control-sm" name="condition" id="condition">
			<option value="title_content" <c:if test="${condition eq 'title_content' }">selected</c:if>>제목+내용</option>
		</select>
		<input class="form-control form-control-sm" value="${keyword }" type="search" name="keyword" placeholder="입력.."/>
		<button type="submit" class="btn btn-outline-dark btn-sm">Serch</button>
	</form>	
</div>
<br />
<br />
<br />


<jsp:include page="../include/footer.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>

</body>
</html>






