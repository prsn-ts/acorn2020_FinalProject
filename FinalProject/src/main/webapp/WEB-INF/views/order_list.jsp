<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<title>주문 목록보기</title>
	  <!-- Bootstrap core CSS -->
	  
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
    width: 500px;
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
	
	.send-status{
		font-size : 25px;
		color : rgb(164, 233, 157);
		
		
	}
	.send-money{
		font-size : 20px;
		color :black;
		
	}

	
</style>
</head>
<body>
	
<jsp:include page="include/header.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>


<div class="container">
		<p class="fontst"><img style="width:2.5em ;height=2.5em; margin-right:10px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />주문내역</p>
	
	
		<table class="type09" style="margin-left: auto; margin-right: auto;">
		<thead>
			<tr>
				<th width="10%">주문번호</th>				
				<th width="45%"colspan="2" >주문 상세정보</th>
				<th width="15%">금액</th>
				<th width="15%">주문일자</th>
				<th width="15%">상태<th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach var="tmp" items="${list }">
			<tr>
				<td>${tmp.num }</td>
				<td>
				<img  style="width :150px; height:150px"src="${pageContext.request.contextPath }${tmp.profile}" alt="profile" />
				</td>
				<td class="send-info"><br /><strong style="font-size: 18px; hover color:red">${tmp.productname}</strong>
				 <br /> 상품 정보 : ${tmp.sboption}
				 <br /><span style="font-size: 12px ;color:gray">(배송지 : ${tmp.addr })</span> 
				
				<td class="send-money"><strong><br /><br />${tmp.totalPrice } 원</strong></td>
				<td class="send-money"><br /><br />${tmp.orderdate }</td>
				<td class="send-money"><br /><br /><strong style="color : rgb(164, 233, 157);">배송준비중</strong></td>
			</tr>

		</c:forEach>
		</tbody>
	</table>
	
	
	

</div>


<jsp:include page="include/footer.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>


</body>
</html>