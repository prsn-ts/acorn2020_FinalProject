<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<title>BARSIN 공식온라인 STORE</title>
	  <!-- Bootstrap core CSS -->
	  
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>

<style>
 .center{
	display:block; 
    margin:auto;
 }
 .color:link { color: black; text-decoration: none;}
 .color:visited { color: black; text-decoration: none;}
 .color:hover { color: black; text-decoration: none;}
</style>
</head>
<body>
	
<jsp:include page="include/header.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>


 <!-- Page Content -->
<div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="${pageContext.request.contextPath }/resources/images/shoes2.png" class="d-block w-100 " height="500px" alt="...">
      <div style="text-align: right"  class="carousel-caption d-none d-md-block">
        <h5>새로운 차원의 디자인과 최상의 편안함</h5>
        <p>지금 가장 먼저 경험해 보세요</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath }/resources/images/a1.png" class="d-block w-100" height="500px" alt="...">
      <div style="text-align: right" class="carousel-caption d-none d-md-block">
        <h5>새로운 컬러, 새로운 디자인</h5>
        <p>새로운 모습으로 지금 공개 합니다</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath }/resources/images/p2.png" class="d-block w-100" height="500px" alt="...">
      <div style="text-align: right" class="carousel-caption d-none d-md-block">
        <h5>학생들에게 인기 많은디자인</h5>
        <p>입학하는 자녀에게 신발 선물을 해보세요</p>
      </div>
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
<div class="container marketing">
   <p class="fontst"><img style="width:2.5em ;height=2.5em; margin-right:10px; text-align: center;"src="${pageContext.request.contextPath }/resources/images/dlah.png" /><strong>Best Shoes</strong></p>
    <hr class="featurette-divider">	
    <!-- Three columns of text below the carousel -->
    <div class="row">
    	<c:forEach var="tmp" items="${likelist}" begin="0" end="2" step="1">
      		<div class="col-lg-4">
				<a href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}"><img class="center" style="padding:2px; width:220px; height: 220px; border-radius: 70%; border:solid 1px; border-color: #343a40;" src="${pageContext.request.contextPath }${tmp.profile }" /></a>
        		<br />
        		<h4><a class="color" href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}">${tmp.productname}</a></h4>
        		<h6>${tmp.kind }</h6>
      		</div><!-- /.col-lg-4 -->
      	</c:forEach>
	</div><!-- /.row -->
	<br />
	<p class="fontst"><img style="width:2.5em ;height=2.5em; margin-right:10px; text-align: center;"src="${pageContext.request.contextPath }/resources/images/dlah.png" /><strong>New Shoes</strong></p>
    <hr class="featurette-divider">	
    <!-- Three columns of text below the carousel -->
    <div class="row">
    	<c:forEach var="tmp" items="${list}" begin="0" end="2" step="1">
      		<div class="col-lg-4">
        		<a href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}"><img class="center" style=" padding:2px; width:220px; height: 220px; border-radius: 70%; border:solid 1px; border-color: #343a40;" src="${pageContext.request.contextPath }${tmp.profile }" /></a>
        		<br />
        		<h4><a class="color" href="${pageContext.request.contextPath }/shop/detail.do?num=${tmp.num}">${tmp.productname}</a></h4>
        		<h6>${tmp.kind }</h6>
      		</div><!-- /.col-lg-4 -->
      	</c:forEach>
	</div><!-- /.row -->
	<br />

    <!-- START THE FEATURETTES -->
    
    

    <hr class="featurette-divider">
	<br />
    <div class="row featurette">
      <div class="col-md-5 text-center" style="margin-top: 200px">
        <h1 class="featurette-heading">STYLE PICK</h1>
        <a href="shop/shop.do" class="lead"><button type="button" class="btn btn-secondary btn-lg" >상품목록 -></button></a>
      </div>
      <div class="col-md-5">
        <img src="${pageContext.request.contextPath }/resources/images/a4.png" width="700" height="500" >
      </div>
    </div>


    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-3 order-md-9 text-center" style="margin-top: 200px">
        <h1 class="featurette-heading">Sneakers</h1>
        <a href="info.do" class="lead"><button type="button" class="btn btn-secondary btn-lg"><-자세히 보기</button></a>
      </div>
      <div class="col-md-9 order-md-1">
        <img src="${pageContext.request.contextPath }/resources/images/a3.png" width="700" height="500" >
      </div>
    </div>

    <hr class="featurette-divider">

    <!-- /END THE FEATURETTES -->

  </div>
  
 





 


 



<jsp:include page="include/footer.jsp">
	<jsp:param value="index" name="thisPage"/>
</jsp:include>


</body>
</html>