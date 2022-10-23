<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="now" value="<%=new java.util.Date()%>" />


<head>
	

</head>


<body>
	<div class="sub_visual">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>	
	</div>
	<div id="content" class="sub_content">
		<!-- tab_link -->
		<% /* @ include file="/WEB-INF/templates/web/base/tab_menu.jsp" */ %>

		<div class="sub_top type02">
			<h3>강사 PR (상세)</h3>
		</div>	
		
		<div class="board-view">
			<div class="head">
				<ul>
				<c:if test="${fn:length(result.item2)>0}"><li>#<c:out value="${result.item2}"/></li></c:if>
				<c:if test="${fn:length(result.item3)>0}"><li>#<c:out value="${result.item3}"/></li></c:if>
				<c:if test="${fn:length(result.item4)>0}"><li>#<c:out value="${result.item4}"/></li></c:if>
				<c:if test="${fn:length(result.item5)>0}"><li>#<c:out value="${result.item5}"/></li></c:if>
				</ul>
				<h4><c:out value="${result.nttSj}"/></h4>
			</div>
			<!-- //head -->
			<div class="body">
				<div class="body_head">
					<div>
					<span><c:out value="${result.frstRegisterNm}"/></span>
					<em><c:out value="${result.item1}"/></em>
				</div>
					<div>
						<span>등록일시 : <fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
						<em>조회 : <c:out value='${result.inqireCo}'/></em>
					</div>
				</div>
				<div class="cont">
			<c:choose>
				<c:when test="${not empty result.atchFileId}">
				<img src="<c:url value="${webDir}"/>/common/file/download?atchFileId=<c:out value="${result.atchFileId}"/>&fileSn=<c:out value="${result.atchImg}"/>" alt="학습동아리 <c:out value="${result.nttSj}"/> 이미지" onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img.jpg'"  style="display: block;">
				</c:when>
				<c:otherwise>
				<img src="<c:url value="${webDir}"/>/resources/images/data/no_img.jpg" alt="이미지" style="display: block;">
				</c:otherwise>
			</c:choose>
					<br>
					<p><c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" /></p>
				</div>
				<!-- //cont -->
			</div>
		</div>
		<!-- //board-view -->
		<div class="table">
			<div class="title_box">
				<p class="t_title">강좌소개</p>
				<span></span>
			</div>
			<table>
				<caption>강좌신청 정보 표입니다. -강좌명, 실주소, 주관기관, 접수마감, 교육기간 , 요일시간, 정원수, 교육장소, 강좌분야, 교육대상, 강좌회차, 수강료</caption>
				<colgroup>
					<col style="width:10.5%">
					<col style="width:89.5%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">강사</th>
						<td><c:out value="${result.frstRegisterNm}"/></td>
					</tr>
					<tr>
						<th scope="row" class="vtop">기획제안</th>
						<td><c:out value="${fn:replace(result.item6 , crlf , '<br/>')}" escapeXml="false" /></td>
					</tr>
					<tr>
						<th scope="row">준비물</th>
						<td><c:out value="${fn:replace(result.item7 , crlf , '<br/>')}" escapeXml="false" /></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="btn_area">
			<a href="javascript:history.back()" class="btn_s1_c0">목록</a>
			
			<c:if test="${instrPoolVO!=null && fn:length(instrPoolVO.memNo)>0 }">
				<a href="<c:url value="${webDir}/teacher/poolDetail?detailNo=${instrPoolVO.memNo }"/>" class="btn_s1_c1">강사 프로필 이동</a>
			</c:if>
			
			<c:if test="${user.id eq result.ntcrId }">
			<a href="./modify?nttId=<c:out value="${result.nttId}"/>" class="btn_s1_c1">수정</a>
			</c:if>
		</div>		
	</div>
</body>