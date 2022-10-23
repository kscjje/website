<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
<script>
	$(document).ready(function(){

	});

</script>
			<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}" />">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top type02">
					<a href="javascript:history.back()">뒤로가기 버튼</a>
					<h3>강사 프로필 (상세)</h3>
				</div>
				<!--//top -->
				<div class="detail_box">
					<div class="img_box">
						<p>
						<c:choose>
							<c:when test="${empty imgFile}"><img src="${requestScope.resourceContext}/images/data/img_teacher07.jpg" alt="대표이미지 없음"></c:when>
							<c:otherwise>
								<img src="<tags:UploadFileUrl filePath="${imgFile.filePath}" fileName="${imgFile.fileName }" originName="${imgFile.orginFileName }"/>"/>
							</c:otherwise>
						</c:choose>
						</p>
					</div>
					<!--가로값 700px 설정시 scroll클래스 추가  -->
					<div class="table t_flex">
						<div class="title_area">
							<div>
								<p>${detailVO.memNm }</p>
								<span><c:out value="${detailVO.lectrField }"/></span>
							</div>
							<span class="t_right">강사번호 : <c:out value="${detailVO.memNo }"/></span>
						</div>
						<table>
							<caption>강좌프로필 상세입니다. -강좌설명, 자격증,이력사항,강의계획서,이메일,강사명,전화,강의계획서</caption>
							<colgroup class="pc">
								<col style="width:150px">
								<col style="width:285px">
								<col style="width:150px">
								<col style="width:285px">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="h_figure">강사소개 & 강좌설명</th>
									<td colspan="3"><c:out value="${fn:replace(detailVO.selfintrcn , crlf , '<br/>')}" escapeXml="false"/></td>
								</tr>
								<tr>
									<th scope="row" class="h_figure">자격증 & 이력사항</th>
									<td colspan="3"><c:out value="${fn:replace(detailVO.licenseEtc , crlf , '<br/>')}" escapeXml="false"/></td>

								</tr>
							<c:if test="${!empty planFile}">
								<tr>
									<th scope="row">강의계획서</th>
									<td colspan="3"><a href="<tags:UploadFileUrl filePath="${planFile.filePath}" fileName="${planFile.fileName }" originName="${planFile.orginFileName }"/>" class="down_txt"><c:out value="${planFile.orginFileName }"/></a><a href="<tags:UploadFileUrl filePath="${planFile.filePath}" fileName="${planFile.fileName }" originName="${planFile.orginFileName }"/>" class="btn_down">다운로드</a></td>
								</tr>
							</c:if>
								<tr>
									<th scope="row">이메일</th>
									<td >
										<c:choose>
											<c:when test="${empty detailVO.email}">-</c:when>
											<c:otherwise><c:out value="${detailVO.email }"/></c:otherwise>
										</c:choose>
									</td>
									<th scope="row">연락처</th>
									<td >
										<c:choose>
											<c:when test="${empty detailVO.hp}">-</c:when>
											<c:when test="${detailVO.eduinfoOpenyn eq 'Y' }"><c:out value="${detailVO.hp }"/></c:when>
											<c:otherwise>비공개</c:otherwise>
										</c:choose>
									</td>
									</tr>
									<tr>
										<th scope="row">개인홈피/블로그</th>
										<td colspan="3" ><c:out value="${detailVO.proflEtc }" /></td>
									</tr>
							</tbody>
						</table>
					</div>
					<!--// table  -->
				</div>
				<!--// detail_box  -->

				<div class="btn_area">
					<a href="javascript:history.back()" class="btn_s1_c0">목록  이동</a>
					<c:if test="${detailVO.memNo eq commandMap.userInfo.uniqId }"><a href="../mypage/teacher/modify" class="btn_s1_c1">수정 하기</a></c:if>
				</div>
				<!--// btn_area   -->
			</div>
</body>