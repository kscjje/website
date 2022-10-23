<%
 /**
  * @Class Name : articleDetail.jsp
  * @Description : 게시판 상세 JSP
  * @Modification Information
  * @
  * @  수정일            수정자           수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.28   전영석         최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.28
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/top.jsp"%>

<script type="text/javascript">
/* ********************************************************
 * 삭제처리
 ******************************************************** */
 function fn_article_delete(nttId) {
		var msg = "삭제하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './delete',
			      data : {'nttId' : nttId},
			      type : "POST",
			      dataType : "json",
			      beforeSend : function(xhr) {
	                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	              },
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);
					         window.location.replace("./list<c:out value="${listParam}"/>");
				         }
			      },
			      error: function() {
			    	  alert("Server Error");
			      }
			 });
		}
}
</script>

<div class="sub_cont" id="container">
	
	<div class="inner">
		
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		
		<div class="board-view">
			
			<div class="head" style="text-align:left;">
				
				<h4 <c:if test="${resultInfo.noticeAt eq 'Y'}">class="ico_nt"</c:if>>
					<c:if test="${resultInfo.noticeAt ne 'Y'}">
						<c:choose>
							<c:when test="${empty replyData}"><span class="st s1">답변접수</span></c:when><c:otherwise><span class="st s2">답변완료</span></c:otherwise>
						</c:choose>
					</c:if>
					<c:out value="${result.nttSj}"/>
				</h4>				
				<!-- 목록상단공지일 경우, 제목에 아이콘 추가 클리스 ico_nt -->
				
				<div class="info">
					<dl>
						<dt>작dd성자</dt>
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
			
			</div>
			
		</div>
		
	</div>
	
</div>