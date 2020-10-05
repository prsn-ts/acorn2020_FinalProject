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

    <!-- Three columns of text below the carousel -->
    <div class="row">
      <div class="col-lg-4">
        <svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>
        <h2>Heading</h2>
        <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>
        <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
      <div class="col-lg-4">
        <svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>
        <h2>Heading</h2>
        <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>
        <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
      <div class="col-lg-4">
        <svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140"><title>Placeholder</title><rect width="100%" height="100%" fill="#777"></rect><text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>
        <h2>Heading</h2>
        <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
        <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
      </div><!-- /.col-lg-4 -->
    </div><!-- /.row -->


    <!-- START THE FEATURETTES -->
    
    

    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-5 text-center" style="margin-top: 200px">
        <h1 class="featurette-heading">STYLE PICK</h1>
        <a href="shop/shop.do" class="lead"><button type="button" class="btn btn-dark btn-lg" >구매하기 -></button></a>
      </div>
      <div class="col-md-5">
        <img src="${pageContext.request.contextPath }/resources/images/a4.png" width="700" height="500" >
      </div>
    </div>


    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-3 order-md-9 text-center" style="margin-top: 200px">
        <h1 class="featurette-heading">Sneakers</h1>
        <a href="info.do" class="lead"><button type="button" class="btn btn-dark btn-lg"><-자세히 보기</button></a>
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