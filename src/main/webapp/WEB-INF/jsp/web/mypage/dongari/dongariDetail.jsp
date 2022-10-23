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
		<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>

		<div class="sub_top type01">
			<h3>마이페이지 > 학습동아리</h3>
		</div>		
		
		<!--//top -->
		<div class="detail_box">
			<div class="img_box">
				<p class="h539">
			<c:choose>
				<c:when test="${not empty result.atchFileId}">
				<img src="<c:url value=""/>/web/common/file/download?atchFileId=<c:out value="${result.atchFileId}"/>&fileSn=<c:out value="${result.atchImg}"/>" alt="학습동아리 <c:out value="${result.nttSj}"/> 이미지" onError="this.src='<c:url value=""/>/web/resources/images/data/no_img02.jpg'">
				</c:when>
				<c:otherwise>
				<img src="<c:url value=""/>/web/resources/images/data/no_img02.jpg" alt="이미지" >
				</c:otherwise>
			</c:choose>
				</p>
			</div>
			<div class="table t_flex">
				<table>
					<caption>학습동아리 상세입니다. -모임주소, 모임주기,요일/시간,지역,회원조건,모임장,문의전화,이메일,동아리 홈피,동아리소개</caption>
					<colgroup class="pc">
						<col style="width:150px">
						<col style="width:285px">
						<col style="width:150px">
						<col style="width:285px">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">동아리명</th>
							<td colspan="3">
								<button type="button" class="btn_s2_c2" ><c:out value="${result.ctgNm}"/></button>
								<c:out value="${result.nttSj}"/>
							</td>
						</tr>
						<tr>
							<th scope="row">모임주소</th>
							<td colspan="3"><c:out value="${result.item5}"/></td>
						</tr>
						<tr>
							<th scope="row">모임주기</th>
							<td><c:out value="${result.item7}"/></td>
							<th scope="row">요일/시간</th>
							<td><c:out value="${result.item8}"/></td>
						</tr>
						<tr>
							<th scope="row">지역</th>
							<td>
								<c:if test="${!empty areaList}">
									<c:forEach items="${areaList}" var="item" varStatus="status">
										<c:if test="${item.areaCd eq result.item6}">
											<c:out value="${item.areaName }"/>		
										</c:if>
									</c:forEach>
								</c:if>								
							</td>
							<th scope="row">회원조건</th>
							<td><c:out value="${result.item9}"/></td>
						</tr>
						<tr>
							<th scope="row">모임장</th>
							<td><c:out value="${result.item1}"/></td>
							<th scope="row">문의전화</th>
							<td><c:out value="${result.item2}"/></td>
						</tr>
						<tr>
							<th scope="row">이메일</th>
							<td><c:out value="${result.item3}"/>@<c:out value="${result.item4}"/></td>
							<th scope="row">동아리 홈피</th>
							<td><a href="<c:out value="${result.item10}" />" target="_blank"><c:out value="${result.item10}" /></a></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">동아리소개</th>
							<td colspan="3" >
								<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />
								<% /*
								<textarea placeholder=""><c:out value="${result.nttCn}"/></textarea>
								*/ %>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--// table  -->
		</div>
		<!--// detail_box  -->

		<div class="btn_area">
			<a href="javascript:history.back()" class="btn_s1_c0">목록 이동</a>
			<c:if test="${user.id eq result.ntcrId }">
				<c:choose>
					<c:when test="${result.ntceStat eq '1' or result.ntceStat eq '3'}">
								<a href="./modify?nttId=<c:out value="${result.nttId}"/>" class="btn_s1_c1">수정</a>
					</c:when>
					<c:otherwise>
								
					</c:otherwise>
				</c:choose>				
			
			</c:if>
		</div>		
	</div>
</body>