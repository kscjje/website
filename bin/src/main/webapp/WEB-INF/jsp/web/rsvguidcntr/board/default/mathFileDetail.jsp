<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
/**
 * @Class Name : mathPlayDetail.jsp
 * @Description : 수학아 놀자 상세 JSP
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2021.04.14    전영석          최초 생성
 *
 *  @author 전영석
 *  @since 2021.04.14
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
		<h3 class="tit">
			<c:out value="${fn:replace(SELECTED_MENU_OBJ.menuNm, '[[[', '')}" />			
		</h3>
		<div class="board-view">
			<div class="head" style="text-align:left;">
				<h4 <c:if test="${resultInfo.noticeAt eq 'Y' }">class="ico_nt"</c:if>><c:out value="${result.nttSj}"/></h4>
				<!-- 목록상단공지일 경우, 제목에 아이콘 추가 클리스 ico_nt -->
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
				<div class="info">
					<dl>
						<dt>
							<c:if test="${not empty result.atchFileId}">
								<c:import url="/web/common/upload/includeUploadDetail" >
						       		<c:param name="file_group_id" value="${result.atchFileId}" />
						       		<c:param name="show_type" value="user" />
					        	</c:import>
							</c:if>			
						</dt>
					</dl>				
				</div>				
			</div>
			<!-- //head -->
			<div class="body">
				<div class="cont">
					<p><c:out value="${fn:replace(result.nttCn, crlf, '<br/>')}" escapeXml="false" /></p>
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
				
			</div>
			<!-- //body -->
			<ul class="btn_area">
			<c:if test="${nextArticle.prevNttId > 0 }">
				<li class="b_prev"><a href="./detail?nttId=<c:out value="${nextArticle.prevNttId}"/><c:out value="${searchQuery}"/>"><span class="b_btn">이전글</span><span>PREV<br>이전글</span></a></li>
			</c:if>
			<c:if test="${nextArticle.nextNttId > 0 }">
				<li class="b_next"><a href="./detail?nttId=<c:out value="${nextArticle.nextNttId}"/><c:out value="${searchQuery}"/>"><span>NEXT<br>다음글</span><span class="b_btn">다음글</span></a></li>
			</c:if>
				<li class="list_btn"><a href="./list<c:out value="${listParam}"/>" class="btn_ty_m_c1">목록보기</a></li>
			</ul>
			<!-- //btn_area -->

		</div>
		<!-- //board-view -->
	</div>
</div>

<script>

	$(".file_tit").css("text-align", "left");
	$(".btn_ty_s_c2.btn").css("left", "250px");
	

</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
