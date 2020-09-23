<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sinbar.css" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.bundle.min.js"></script>
<style>
	/* textarea 의 크기가 SmartEditor 의 크기가 된다. */
	#content{
		display: none;
		width: 100%;
		height: 350px;
	}
	
		#profileImage{
		width: 200px;
		height: 200px;
		border: 1px solid #cecece;
		
		cursor: pointer;
	}
</style>
</head>
<body>
	<!-- header -->
	<jsp:include page="../include/header.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>


<div class="container" style="">

	<form action="${pageContext.request.contextPath}/product/insert.do" method="post">
				<input type="hidden" name="profile" id="profile" 
				value="${dto.profile }"/>
		<div class="form-row">
			<div class="form-group col-md-3">
				<label for="kind">카테고리</label>
				<select name="kind" class="form-control">
			        <option value="sneakers">스니커즈</option>
			        <option value="running">런닝화</option>
			        <option value="walker">워커</option>
		      </select>
			</div>
			<div class="form-group col-md-9">
				<label for="productname">상품명</label>
				<input class="form-control" type="text" placeholder="상품명"name="productname"/>
			</div>			
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
				<label for="price">가격</label>
				<input class="form-control" type="text" placeholder="숫자만 입력"name="price"/>
			</div>
		</div>
		<h5>대표 사진 등록</h3>
		<a href="javascript:" id="profileLink">
				<svg id="profileImage"  width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
							<path fill-rule="evenodd" d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
				</svg>
		</a>
		<div class="form-group">
			<label for="content">상세정보</label>
			<textarea class="form-control" name="content" id="content" cols="30" rows="10"></textarea>
		</div>

			


		<button class="btn btn-outline-primary"  type="submit" onclick="submitContents(this);">등록</button>
			<button class="btn btn-outline-warning" type="reset" >취소</button>
	</form>
	
	<form action="profile_upload.do" method="post" 
		enctype="multipart/form-data" id="profileForm">
		<input type="file" name="image" 
			accept=".jpg, .jpeg, .png, .JPG, .JPEG" id="image"/>
	</form>	

</div>


<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath }/SmartEditor/js/HuskyEZCreator.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/clickMarker.js"></script>
<script>
	var oEditors = [];
	
	//추가 글꼴 목록
	//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
	
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "${pageContext.request.contextPath}/SmartEditor/SmartEditor2Skin.html",	
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});
	
	function pasteHTML() {
		var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
		oEditors.getById["content"].exec("PASTE_HTML", [sHTML]);
	}
	
	function showHTML() {
		var sHTML = oEditors.getById["content"].getIR();
		alert(sHTML);
	}
		
	function submitContents(elClickedObj) {
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
		
		// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("content").value를 이용해서 처리하면 됩니다.
		
		try {
			elClickedObj.form.submit();
		} catch(e) {}
	}
	
	function setDefaultFont() {
		var sDefaultFont = '궁서';
		var nFontSize = 24;
		oEditors.getById["content"].setDefaultFont(sDefaultFont, nFontSize);
	}
	
	
	//  사진업로드 관한 javascript
	//프로필 링크를 클릭했을때 실행할 함수 등록 
	$("#profileLink").on("click", function(){
		//input type="file" 을 강제 클릭한다.
		$("#image").click();
	});
	
	//이미지를 선택했을때 실행할 함수 등록
	$("#image").on("change", function(){
		//폼을 강제 제출한다.
		$("#profileForm").submit();
	});
	
	//폼이 ajax 로 제출될수 있도록 플러그인을 동작 시킨다.
	$("#profileForm").ajaxForm(function(data){
		//기존 프로필 이미지 요소를 제거 한다.
		$("#profileImage").remove();
		//새로 img 요소를 만들어서 #profileLink 에 추가한다.
		$("<img/>")
		.attr("id", "profileImage")
		.attr("src", "${pageContext.request.contextPath }"+data.imageSrc)
		.appendTo("#profileLink");
		
		//회원정보 수정폼 전송될때 같이 전송 되도록한다.
		$("#profile").val(data.imageSrc);// input type="hidden" 의 value값
	});	
</script>



	<!-- footer -->
	<jsp:include page="../include/footer.jsp">
		<jsp:param value="index" name="thisPage"/>
	</jsp:include>
	
</body>
</html>