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
        
<div class="carousel border border-warning">
<div class="dropdown">
  <button class="btn dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    신발
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <button class="dropdown-item" type="button">스니커즈</button>
    <button class="dropdown-item" type="button">런닝화</button>
    <button class="dropdown-item" type="button">워커</button>
  </div>
  </div>
  
  <br><br><br><br><br><br>
  
   <div class="dropdown">
    <button class="btn dropdown-toggle" type="button" id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    가격
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu3">
    <button class="dropdown-item" type="button">50000원 이하</button>
    <button class="dropdown-item" type="button">50000원 ~ 100000원</button>
    <button class="dropdown-item" type="button">100000원 ~ 150000원</button>
  </div>
  
</div>
  
   <br><br><br><br><br><br>
  
  <div class="dropdown">
    <button class="btn dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    사이즈
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <button class="dropdown-item" type="button">230</button>
    <button class="dropdown-item" type="button">240</button>
    <button class="dropdown-item" type="button">250</button>
    <button class="dropdown-item" type="button">260</button>
    <button class="dropdown-item" type="button">270</button>
    <button class="dropdown-item" type="button">280</button>
    
  </div>
   </div>
   
   <br><br><br><br><br><br><br><br><br>

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
                  <a href="#">${tmp.productname }</a>
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