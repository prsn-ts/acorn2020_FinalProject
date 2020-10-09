<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/shop/shop.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<style>
	 .color:hover {
	  	text-decoration: none;
	 }
	 .pagination {
   		justify-content: center;
	}
</style>
</head>
<body>

	
<jsp:include page="../include/header.jsp">
	<jsp:param value="shop" name="thisPage"/>
</jsp:include>



  <!-- Page Content  카테고리-->
  <div class="">

    <div class="row">

      <div class="col-md-2" style="margin-top:25px; margin-right:50px">

        <h3 class="my-4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상품목록</h3>
        <hr />
        <div class="p-3 mb-2 bg-info text-white" style="font-size:20px; font-weight:bold;">
				&nbsp;&nbsp;&nbsp;스니커즈
			</div>
			<div class="list-group" id="buttonList">
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button></a>
			</div>
			<br />
			<div class="p-3 mb-2 bg-info text-white" style="font-size:20px; font-weight:bold;">
				&nbsp;&nbsp;&nbsp;런닝화
			</div>
			<div class="list-group" id="buttonList">
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button></a>
			</div>
			<br />
			<div class="p-3 mb-2 bg-info text-white" style="font-size:20px; font-weight:bold;">
				&nbsp;&nbsp;&nbsp;워커
			</div>
			<div class="list-group" id="buttonList">
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button></a>
				<a class="color" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button></a>
			</div>						

      </div>
      <!-- /.col-lg-3  상품목록-->

	<div class="col-lg-9" ">
		<div class="container" style="margin-top: 50px;">
         <form action="${pageContext.request.contextPath }/shop/shop.do" method="get">
            <div class="form-group row">
               <div class="col-sm-7">
                    <c:choose>
                    	<c:when test="${kindSelect eq '' }"><h4>전체 검색</h4></c:when>
                     	<c:when test="${kindSelect eq 'sneakers'}"><h4>스니커즈 검색</h4></c:when>
                    	<c:when test="${kindSelect eq 'running'}"><h4>런닝화 검색</h4></c:when>
                    	<c:when test="${kindSelect eq 'walker'}"><h4>워커 검색</h4></c:when>
                    </c:choose>
                     
                     <input  class="form-control"  type="text" name="keyword" placeholder="상품명...">
                     <input type="hidden" name="kindSelect" value ="${ kindSelect}" />
                  </div>
                  <button class="btn btn-praimary" type="submit">
                     <svg width="1.1em" height="1.1em" viewBox="0 0 16 16" class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                       <path fill-rule="evenodd" d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"/>
                       <path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"/>
                  </svg>
                  </button>
               </div>
          </form>
	</div>

        <div class="row" style="margin-top: 50px;">
		<c:forEach var="tmp" items="${list }">
		<div class="col-sm-3" >
            <div class="card h-100">
            		<a href="#"><img class="card-img-top embed-responsive-item" src="${pageContext.request.contextPath }${tmp.profile}" alt="" ></a>
              <div class="card-body">
                <h4 class="card-title">
                  <a class="color" href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}">${tmp.productname }</a>
                </h4>
                <h6>${tmp.price }원</h6>
              </div>
              <div class="card-footer">
                <p class="card-text">${tmp.regdate } <span style="color:gray; font-size: 12px; margin-left: 10px;">(누적판매량  ${tmp.buycount })</span> </p>
              </div>
		</div>
          </div>
		
		</c:forEach>
 	        



        </div>
        <!-- /.row -->
        <br />
 		<div class="page-display">
			<ul class="pagination pagination">
			<c:if test="${startPageNum ne 1 }">
				<li class="page-item"><a class="page-link" href="shop.do?pageNum=${startPageNum-1 }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }&keyword="${keyword }"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
				<c:choose>
					<c:when test="${i eq pageNum }">
						<li class="page-item active"><a class="page-link" href="shop.do?pageNum=${i }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }&keyword="${keyword }">${i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="shop.do?pageNum=${i }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }&keyword="${keyword }">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount }">
				<li class="page-item"><a class="page-link" href="shop.do?pageNum=${endPageNum+1 }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }&keyword="${keyword }"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
			</ul>	
		</div>
      </div>
      <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->

  </div>
  
  
<jsp:include page="../include/footer.jsp">
	<jsp:param value="shop" name="thisPage"/>
</jsp:include>
  
</body>
</html>