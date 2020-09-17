<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	  <!-- Bootstrap core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
</head>
<body>

  <!-- Navigation -->
 <nav class="navbar navbar-expand navbar-dark bg-dark fixed-top">
   <div class="container">
     <a class="navbar-brand" href="#">home</a>
     <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
       <span class="navbar-toggler-icon"></span>
     </button>
     <div class="collapse navbar-collapse" id="navbarResponsive">
       <ul class="navbar-nav ml-auto">
         <li class="nav-item active">
           <a class="nav-link" href="#">Home
             <span class="sr-only">(current)</span>
           </a>
         </li>
         <li class="nav-item">
           <a class="nav-link" href="#">About</a>
         </li>
         <li class="nav-item">
           <a class="nav-link" href="#">Services</a>
         </li>
         <li class="nav-item">
           <a class="nav-link" href="#">Contact</a>
         </li>
       </ul>
     </div>
   </div>
 </nav>

 <!-- Page Content -->
<div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="http://placehold.it/900x350" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5>첫번째 신발사진</h5>
        <p>스니커즈로 부탁해</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="http://placehold.it/900x350" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5>두번쨰 신발사진</h5>
        <p>런닝화로 부탁해</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="http://placehold.it/900x350" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5>세번째 신발사진</h5>
        <p>워커로 부탁해</p>
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






 


 <!-- Footer -->
 <footer class="py-5 bg-dark">
   <div class="container">
     <p class="m-0 text-center text-white">Copyright &copy; Your Website 2020</p>
   </div>
   <!-- /.container -->
 </footer>






</body>
</html>