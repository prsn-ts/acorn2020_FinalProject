<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	//장바구니 관련 쿠키 값 가져오기
	Cookie[] cookies = request.getCookies();
	String cookie = null;
	if(cookies != null){
		for(int i=0; i<cookies.length; i++){
			if(cookies[i].getName().equals("guest")){
				cookie = cookies[i].getValue();
			}
		}
	}
%>     
  <!-- Navigation -->
<div id="header-div"class="sticky-top">
	 <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
  <a class="navbar-brand" href="${pageContext.request.contextPath }/home.do"><img style="width:2.5em ;height=2.5em; margin-right:10px;"src="${pageContext.request.contextPath }/resources/images/dlah.png" alt="" />BARSIN</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
    <c:choose>
    <c:when test="${param.thisPage eq 'info' }">
       <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath }/info.do">회사소개 <span class="sr-only">(current)</span></a>
      </li>    	
    </c:when>
    <c:otherwise>
       <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath }/info.do">회사소개 <span class="sr-only">(current)</span></a>
      </li>
    </c:otherwise>
    </c:choose>
	
	<c:choose>
		<c:when test="${param.thisPage eq 'list' }">
	      <li class="nav-item active">
	        <a class="nav-link" href="${pageContext.request.contextPath }/notice/list.do">BARSIN공지</a>
	      </li>		
		</c:when>
		<c:otherwise>
	      <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath }/notice/list.do">BARSIN공지</a>
	      </li>		
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${param.thisPage eq 'shop' }">
	      <li class="nav-item dropdown active">
	        <a class="nav-link dropdown-toggle " href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          	상품목록
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	        	<a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do">전체목록</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers">스니커즈</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running">런닝화</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker">워커</a>
	        </div>
	      </li>		
		</c:when>
		<c:otherwise>
	      <li class="nav-item dropdown">
	        <a class="nav-link dropdown-toggle " href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          	상품목록
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	        	<a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do">전체목록</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=sneakers">스니커즈</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=running">런닝화</a>
	          <a class="dropdown-item" href="${pageContext.request.contextPath }/shop/shop.do?kindSelect=walker">워커</a>
	        </div>
	      </li>		
		</c:otherwise>
	</c:choose>	
	<c:if test="${id eq 'admin' }">
		   <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath }/product/insertform.do">상품등록</a>
	      </li>			
	</c:if>
	
    </ul>
        <form action="${pageContext.request.contextPath }/shop/shop.do" method="get" class="form-inline my-2 my-lg-0">
      	<input class="form-control mr-sm-2"  type="search" placeholder="상품명..." aria-label="Search" name="search">
      	<button class="btn btn-outline-light my-2 my-sm-0" type="submit">
      		<svg width="1.1em" height="1.1em" viewBox="0 0 16 16" class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
			  	<path fill-rule="evenodd" d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"/>
			  	<path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"/>
			</svg>
		</button>
    </form>
<c:choose>
	<c:when test="${empty id }">
		<div id="login">
		<a class="icon-svg"href="${pageContext.request.contextPath }/login/login_form.do">로그인</a>
		<a class="icon-svg"href="${pageContext.request.contextPath }/login/signup_form.do">회원가입</a>
		</div> 	
	</c:when>
	<c:otherwise>
		<div id="login">
		<a class="icon-svg"href="${pageContext.request.contextPath }/login/private/info.do">${id }님 </a>
		<a class="icon-svg"href="${pageContext.request.contextPath }/login/logout.do">로그아웃</a>
		</div>		
	</c:otherwise>
</c:choose>


   
   <a title="나의 쇼핑정보"class="icon-svg" href="${pageContext.request.contextPath }/order_list.do"><svg width="2.5em" height="2.5em" viewBox="0 0 16 16" class="bi bi-person-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
</svg></a>    
   <a title="장바구니  보러가기"class="icon-svg" href="${pageContext.request.contextPath }/shop/shopping_basket.do?cookie=<%=cookie %>"><input type="hidden" name="detail_basket_button" value="no"/><svg width="2.5em" height="2.5em" viewBox="0 0 16 16" class="bi bi-cart4" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
</svg></a>
  </div>


</nav>

</div>