<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>

</head>
<body>

<jsp:include page="include/header.jsp">
	<jsp:param value="info" name="thisPage"/>
</jsp:include>





		<div class="container">
			<div class="pb-4"></div>
			<div class="row_all p-3" style="border: 1px solid #dfdfdf; background-color: #f2f2f2;">
			

			
			    <!-- Blog Post -->
			    <div class="card mb-4">
					<div class="card-body">
					
						<h3 class="card-title text-center" >
						<img style="width:4em ;height=2.5em; margin-right:10px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />BARSIN 소개</h3>
						<hr>
						<p class="card-body h5 text-center">조장: 이세희 <br><br> 조원: 이재근, 김세영, 백운용, 김영대 </p>
					</div>
			    </div>
				
			    <div class="card mb-4">
			        <div class="card-body">
			    		  <br/>
			        	  <div>
			        	  	
			        	  	<ol class="h4">
			        	  		<h3>1. 여행이나 오랜 보행에도 OK!</h3><br>
			        	  		<div style="list-style: none;"><img src="${pageContext.request.contextPath }/resources/images/info1.png" align="right"
			        	  		style="width:400px; height: 400px; border-radius: 15px 15px 15px 15px;"></div>
			        	  		<h5>오솔라이트 인솔 및 경량의 파일론창으로 여행이나<br><br>
			        	  		 오랜 보행에도 편안하게 착화할수 있습니다<br><br>
			        	  		 최대한 인체 공학적 설게를 바탕으로<br><br>
			        	  		 발에 무리를 주지 않아서 오랜시간<br><br>
			        	  		 걸을수 있도록 설계 되었습니다</h5>
			        	
			        	  		<br><br><br><br><br><br><br><br><br><br><br><br>
			        	  		
			        	  		<h3><p class="text-right">2. 내구성 갑! 이런 하이 퀄리티 봤어?</p></h3><br>
			        	  		<div style="list-style: none;"><img src="${pageContext.request.contextPath }/resources/images/info2.png" align="left"
			        	  		style="width:400px; height: 400px; border-radius: 15px 15px 15px 15px;"></div>
			        	  		<h5><p class="text-right"> 천연소가죽에 푹신한 쿠션감을 더했으며 견고한  <br><br>
			        	  		내구성까지 같췄습니다. 따른 가죽들과는 다르게<br><br>
			        	  		좀더 고급스러운 느낌을 주며 얼룩을 쉽게 지울수 있고<br><br>
			        	  		세탁하기도 편해서 많은 인기를 받고 있습니다 
			        	  		</p></h5>
			        	  	
			        	  		<br><br><br><br><br><br><br><br><br><br><br><br>
			        	  		
			        	  		<h3>3. 계절 & 연령대 모두 아우르는 신발</h3><br>
			        	  		 <div style="list-style: none;"><img src="${pageContext.request.contextPath }/resources/images/info3.png" align="right"
			        	  		style="width:400px; height: 400px; border-radius: 15px 15px 15px 15px;"></div>
			        	  		<h5>통기성이 좋은 소재로 봄,여름,가을,겨울 사계절은 물론, <br><br>
								모든 연력대를 아우르는 클래식한 디자인 입니다<br><br>
								남자가 신어도 여자가 신어도 누구나 무난하게<br><br>
								<h5>신을수 있으며 유행을 따르지 않는 디자인을<br><br>
								추구해 와 적용 시켰습니다</h5>
								
			        	  	

			        	  		

			        	  	</ol>
			        	  </div>
			        	  </div>
			        	  </div>
			        	  </div>
			        	  </div>
			       





<jsp:include page="include/footer.jsp">
	<jsp:param value="info" name="thisPage"/>
</jsp:include>

</body>
</html>