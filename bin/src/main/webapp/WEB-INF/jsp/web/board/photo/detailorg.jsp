<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : detail.jsp
  * @Description : 상세 JSP
  * @Modification Information
  * @
  * @  수정일           수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.29  전영석                 최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.29
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<style>
		.btn_ty_s_c2 {display:none !important;}
	</style>
</head>

<body>
			<div class="sub_visual">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top type02">
					<a href="javascript:history.back()">뒤로가기 버튼</a>
					<h3><c:out value="${boardMasterVO.bbsNm }"/></h3>
				</div>
				<div class="board-view">
					<div class="head">
	<c:if test="${fn:length(result.tag0001)>0 || fn:length(result.tag0002)>0 || fn:length(result.tag0003)>0 || fn:length(result.tag0004)>0 || fn:length(result.tag0005)>0 }">
						<ul>
							<c:if test="${fn:length(result.tag0001)>0 }"><li>#<c:out value="${result.tag0001}"/></li></c:if>
							<c:if test="${fn:length(result.tag0002)>0 }"><li>#<c:out value="${result.tag0002}"/></li></c:if>
							<c:if test="${fn:length(result.tag0003)>0 }"><li>#<c:out value="${result.tag0003}"/></li></c:if>
							<c:if test="${fn:length(result.tag0004)>0 }"><li>#<c:out value="${result.tag0004}"/></li></c:if>
							<c:if test="${fn:length(result.tag0005)>0 }"><li>#<c:out value="${result.tag0005}"/></li></c:if>
						</ul>
	</c:if>
						<h4>
							<c:if test="${result.noticeAt eq 'Y' }"><a class="btn_s2_c1" href="javascript:">공지</a></c:if>
							<c:if test="${(!empty ctgList) && fn:length(result.ctgNm)>0}">
								<em class="st1">[<c:out value="${result.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${result.nttSj}"/>
						</h4>

					</div>
					<!-- //head -->
					<div class="body">
						<div class="body_head">
				 <c:choose>
				 	<c:when test="${fn:length(result.source)>0 }">
						<span>출처 : <c:out value="${result.source}"/></span>
				 	</c:when>
				 	<c:when test="${fn:length(result.media)>0 }">
						<span>언론사 : <c:out value="${result.media}"/></span>
				 	</c:when>
				 	<c:otherwise>
				 		<span>등록자명 : <c:out value="${result.frstRegisterNm}"/></span>
				 	</c:otherwise>
				 </c:choose>
							<div>
								<span>등록일시 : <fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
								<em>조회 : <c:out value="${result.inqireCo}"/></em>
							</div>
						</div>

					<% /* 첨부 파일 */ %> <% /* show_image : 본문에 첨부파일 이미지 보여주기 여부 */ %>
					<c:if test="${not empty result.atchFileId}">
						<c:import url="${webDir}/common/upload/includeUploadDetail" >
				       		<c:param name="file_group_id" value="${result.atchFileId}" />
				       		<c:param name="show_type" value="user" />
				       		<c:param name="show_image" value="Y" />
			        	</c:import>
					</c:if>

						<div class="cont">
							<div class="cont-atch-img"></div>
							<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />

							<c:if test="${fn:length(result.surveyNo)>0 }">
							<div class="btn_area" style="max-width:100%; text-align: center;">
								<c:choose>
									<c:when test="${fn:startsWith(result.surveyNo,'http')}">
						                <a href="<c:out value='${result.surveyNo}'/>" class="btn_s1_c1" target="_blank">신청하러 가기</a>
									</c:when>
									<c:otherwise>
										<a href="../../survey/form?qestnarId=<c:out value="${result.surveyNo}"/>" class="btn_s1_c1">신청하러 가기</a>
									</c:otherwise>
								</c:choose>
							</div>
							</c:if>

<c:if test="${programVO!=null}">
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
											<td><c:out value="${programVO.instrctrName }"/></td>
										</tr>
										<tr>
											<th scope="row">추천대상</th>
											<td><c:out value="${programVO.edcTargetAgegbnNm }"/></td>
										</tr>
										<tr>
											<th scope="row">수강료</th>
											<td>
												<c:choose>
													<c:when test="${programVO.salamt > 0}">
														<fmt:formatNumber value="${programVO.salamt}" pattern="#,###"/>원
													</c:when>
													<c:otherwise>무료</c:otherwise>
												</c:choose>
												(
												<hisco:DateUtil datestr="${programVO.edcSdate}" format="yy.MM.dd"/> ~ <hisco:DateUtil datestr="${programVO.edcEdate}" format="yy.MM.dd"/>
												, <c:out value="${programVO.edcClcnt}"/>회
												)
											</td>
										</tr>
									</tbody>
								</table>
							</div>
	</c:if>

						</div>

					<c:if test="${boardMasterVO.managerAt == 'Y'}">
						<div class="file-list">
							<span class="file_tit">담당부서</span>
							<ul><li><c:out value="${result.managerDept}"/></li></ul>
						</div>
						<div class="file-list">
							<span class="file_tit">문의 전화번호</span>
							<ul><li><c:out value="${result.managerTel}"/></li></ul>
						</div>
					</c:if>
						<!-- //cont -->

	<c:if test="${itemInfoList!=null  && fn:length(itemInfoList)>0}">
		<c:forEach items="${itemInfoList}" var="itemInfo" varStatus="status">
			<% /* bbsItemEnid 가 BBSITEM 으로 시작하지 않으면 고정항목 제외  */ %>
		 	<c:if test="${fn:indexOf(itemInfo.bbsItemEnid,'BBSITEM')>=0 }">
						<div class="file-list">
							<span class="file_tit"><c:out value="${itemInfo.bbsItemNm}"/></span>
							<ul>
								<li><c:out value="${fn:replace(itemInfo.bbsItemLvalue , crlf , '<br/>')}" escapeXml="false" /></li>
							</ul>
						</div>
			</c:if>
		</c:forEach>
	</c:if>


					</div>
				</div>




<c:if test="${nextBoardVo!=null ||  prevBoardVo!=null}">
				<div class="pa_text">
	<c:if test="${prevBoardVo!=null}">
					<a href="./detail?nttId=<c:out value="${prevBoardVo.nttId}"/><c:out value="${searchQuery}"/>">
						<dl>
							<dt>이전글</dt>
							<dd>
								<p>
							<c:if test="${(!empty ctgList) && fn:length(prevBoardVo.ctgNm)>0}">
								<em class="st1">[<c:out value="${prevBoardVo.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${prevBoardVo.nttSj}"/>
								</p>
								<span><fmt:formatDate value="${prevBoardVo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
							</dd>
						</dl>
					</a>
	</c:if>
	<c:if test="${nextBoardVo!=null}">
					<a href="./detail?nttId=<c:out value="${nextBoardVo.nttId}"/><c:out value="${searchQuery}"/>">
						<dl>
							<dt>다음글</dt>
							<dd>
								<p>
							<c:if test="${(!empty ctgList) && fn:length(nextBoardVo.ctgNm)>0}">
								<em class="st1">[<c:out value="${nextBoardVo.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${nextBoardVo.nttSj}"/>
								</p>
								<span><fmt:formatDate value="${nextBoardVo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
							</dd>
						</dl>
					</a>
	</c:if>
				</div>
</c:if>
				<!-- //board-view -->
				<div class="btn_area">
					<a href="./list?<c:out value="${searchQuery}"/>" class="btn_s1_c0">목록</a>
					<c:if test="${fn:length(result.edcProgram)>0 }">
						<c:choose>
							<c:when test="${fn:startsWith(result.edcProgram,'http')}">
				                <a href="<c:out value='${result.edcProgram}'/>" class="btn_s1_c1" target="_blank">해당내용 상세보기</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="/web/edc/program/${result.edcProgram}"/>" class="btn_s1_c1">해당내용 상세보기</a>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${fn:length(result.urlLink)>0 }">
						<c:choose>
							<c:when test="${fn:startsWith(result.urlLink,'http')}">
				                <a href="<c:out value="${result.urlLink}"/>" class="btn_s1_c1" target="_blank">해당 기사 보기</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="${result.urlLink}"/>" class="btn_s1_c1">해당 기사 보기</a>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${boardMasterVO.urlLnikyn eq 'Y' && fn:length(result.linkUrl)>0 }">
						<c:choose>
							<c:when test="${fn:startsWith(result.linkUrl,'http')}">
				                <a href="<c:out value="${result.linkUrl}"/>" class="btn_s1_c1" target="_blank">해당내용 상세보기</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="${result.linkUrl}"/>" class="btn_s1_c1">해당내용 상세보기</a>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<!--// btn_area  -->
			</div>
			<!--// sub_content  -->

</body>