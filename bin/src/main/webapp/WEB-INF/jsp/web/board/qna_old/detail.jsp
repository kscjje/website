<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

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

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

	<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
			<div class="board-view">
				<div class="head">
				
					<h4><c:if test="${resultInfo.noticeAt eq 'Y' }"><span class="st s1">답변접수</span></c:if><c:out value="${result.nttSj}"/></h4>
					
					<!--  답변완료일경우 
					<span class="st s2">답변완료</span>
					-->
					
					<div class="info">
						<dl>
							<dt>작성자</dt>
							<dd><c:out value="${result.frstRegisterNm}"/></dd>
						</dl>
						<dl>
							<dt>등록일</dt>
							<dd><fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></dd>
						</dl>
					</div>
				</div>
				<!-- //head -->
				<div class="body">
					<div class="cont">
						<p><c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" /></p>
					</div>
					
					<c:if test="${boardMasterVO.managerAt == 'Y'}">
						<div class="contact">
							<ul>
								<li>
									<dl>
										<dt>담당부서</dt>
										<dd><c:out value="${result.managerDept}"/></dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>문의 전화번호</dt>
										<dd><c:out value="${result.managerTel}"/></dd>
									</dl>
								</li>
							</ul>
						</div>
					</c:if>					
					
					<!-- //cont -->
					
					<c:if test="${not empty result.atchFileId}">
					
						<div class="file-list">
							<!-- <span class="file_tit">첨부파일</span> -->
							<ul>
							
								<c:import url="/web/common/upload/includeUploadDetail" >
						       		<c:param name="file_group_id" value="${result.atchFileId}" />
						       		<c:param name="show_type" value="user" />
					        	</c:import>							
							
								<!-- 
								<li><a href="#" class="file">2020년 과학공방 여름방학 교육과정 일정표.hwp <span>(190KB)</span></a><a href="#" class="btn_ty_s_c2 btn">다운로드</a></li>
								<li><a href="#" class="file">2020년 과학공방 여름방학 교육과정 일정표.hwp <span>(190KB)</span></a><a href="#" class="btn_ty_s_c2 btn">다운로드</a></li>
								-->
								
							</ul>
						</div>
						
					</c:if>
					
					<!-- //flie-list-->
				</div>
			<!-- //board-view -->
			</div>
			<!-- //board-view -->
			
			<c:if test="${!empty replyData}">
			
				<div class="board-view answer">
					<div class="head">
						<h4><c:out value="${replyData.nttSj}"/></h4>
						<div class="info">
							<dl>
								<dt>작성자</dt>
								<dd><c:out value="${replyData.frstRegisterNm}"/></dd>
							</dl>
							<dl>
								<dt>등록일</dt>
								<dd><fmt:formatDate value="${replyData.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></dd>
							</dl>
						</div>
					</div>
					<!-- //head -->
					<div class="body">
						<div class="cont">
							<p>
								<c:set var="replayMain" value="${fn:replace(replyData.nttCn, '<script>' , '')}" />
								<c:out value="${fn:replace(replayMain, crlf, '<br/>')}" escapeXml="false" />									
							</p>
						</div>
						<div class="file-list">
							<!-- <span class="file_tit">첨부파일</span> -->
							<ul>
							
							<c:if test="${not empty replyData.atchFileId}">
								<c:import url="/web/common/upload/includeUploadDetail" >
						       		<c:param name="file_group_id" value="${replyData.atchFileId}" />
						       		<c:param name="show_type" value="user" />
					        	</c:import>
							</c:if>								
							
							<!-- 							
							<li><a href="#" class="file">2020년 과학공방 여름방학 교육과정 일정표.hwp <span>(190KB)</span></a><a href="#" class="btn_ty_s_c2 btn">다운로드</a></li>
							<li><a href="#" class="file">2020년 과학공방 여름방학 교육과정 일정표.hwp <span>(190KB)</span></a><a href="#" class="btn_ty_s_c2 btn">다운로드</a></li>
							-->
							
							</ul>
						</div>
						<!-- //flie-list-->
					</div>
					<!-- //body -->
				</div>
				
			</c:if>
			
			<!-- //board-view answer -->
			<ul class="btn_area">
				<li><a href="./list" class="btn_ty_m_c1">목록</a></li>
				
				<c:if test="${commandMap.userInfo.id eq result.frstRegisterId}">
				
					<c:if test="${empty replyData}">
						<li><a href="./updt?nttId=${param.nttId}" class="btn_ty_m_c3">수정</a></li>
						<li><a href="#" onclick="fn_delete_article(); return false;" class="btn_ty_m_c4">삭제</a></li>
					</c:if>
					
				</c:if>
				
			</ul>
			<!-- //btn_area -->
		</div>
		<!-- //inner -->
	</div>
	<!-- //sub_cont -->

<script>

	function fn_delete_article() {
			
		if (confirm("삭제하시겠습니까?")) {
			
			$.ajax({
				
				url : "./delete",
				method : 'POST',
				data : {
					nttId : "${param.nttId}" 
				},
				dataType : 'json',
			    beforeSend : function(xhr) {
			    	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
			    },												
				error : function() {
					alert('삭제 오류가 발생하였습니다.');
				},
				
				success : function(data) {
					top.location.href="/web/board/qna/list";
				}
				
			});			
			
			
		}

	}

</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>