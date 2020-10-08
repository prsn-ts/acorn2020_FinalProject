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

</head>
<body>

	
<jsp:include page="../include/header.jsp">
	<jsp:param value="shop" name="thisPage"/>
</jsp:include>



  <!-- Page Content  카테고리-->
  <div class="">

    <div class="row">

      <div class="col-lg-3">

        <h1 class="my-4">신발목록</h1>
			<p>스니커즈</p>
			<div class="list-group" id="buttonList">
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button>
			</div>
			<p>런닝화</p>
			<div class="list-group" id="buttonList">
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button>
			</div>
			<p>워커</p>
			<div class="list-group" id="buttonList">
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=priceHighArr"><button class="list-group-item list-group-item-action">가격 높은 순</button></a>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=priceLowArr"><button type="button" class="list-group-item list-group-item-action">가격 낮은 순</button>
				<a href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker&arr=buyHighArr"><button type="button" class="list-group-item list-group-item-action">인기순</button>
			</div>						

      </div>
      <!-- /.col-lg-3  상품목록-->

      <div class="col-lg-9" style="margin-top: 100px">


        <div class="row">
		<c:forEach var="tmp" items="${list }">
		<div class="col-lg-4 col-md-6 mb-4">
            <div class="card h-100">
              <a href="#"><img class="card-img-top" src="${pageContext.request.contextPath }${tmp.profile}" alt=""></a>
              <div class="card-body">
                <h4 class="card-title">
                  <a href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}">${tmp.productname }</a>
                </h4>
                <h6>${tmp.price }</h6>
                <p class="card-text">심플 이즈 베스트 단화!</p>
              </div>
              <div class="card-footer">
                <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
              </div>
            </div>
          </div>
		
		</c:forEach>
 	        



        </div>
        <!-- /.row -->
 		<div class="page-display">
			<ul class="pagination pagination-sm">
			<c:if test="${startPageNum ne 1 }">
				<li class="page-item"><a class="page-link" href="shop.do?pageNum=${startPageNum-1 }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }">Prev</a></li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
				<c:choose>
					<c:when test="${i eq pageNum }">
						<li class="page-item active"><a class="page-link" href="shop.do?pageNum=${i }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }">${i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="shop.do?pageNum=${i }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount }">
				<li class="page-item"><a class="page-link" href="shop.do?pageNum=${endPageNum+1 }&search=${encodedK }&arr=${arr }&kindSelect=${kindSelect }">Next</a></li>
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