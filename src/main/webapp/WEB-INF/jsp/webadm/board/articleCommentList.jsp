<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : commentListAjax.jsp
  * @Description : 댓글 목록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.27    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.27
  *  @version 1.0
  *  @see
  *
  */
%>

<c:if test="${fn:length(commentList) == 0}">
					<div class="post ml-3 mr-3">
                      <!--
                      <div class="user-block">
                        <span class="description"><i class="fa fa-fw fa-user"></i> 작성자명 | Shared publicly - 7:45 PM today</span>
                      </div>
                       -->
                      <p>등록된 댓글이 없습니다.</p>
                    </div>
</c:if>
<c:forEach var="result" items="${commentList}" varStatus="status">
		<div class="post  ml-3 mr-3">
                      <div class="user-block ">
                        <span class="description ml-0"><i class="fa fa-fw fa-user"></i> <strong><c:out value="${result.wrterNm}" /> (<c:out value="${result.wrterId}" />)</strong> | <c:out value="${result.frstRegisterPnttm}" />&nbsp;&nbsp;
 <c:choose>
	<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
                        <a class="btn btn-outline-info btn-xs m1-2" href="javascript:fn_egov_comment_delete(${result.commentNo})"  title="삭제버튼">삭제</a></span>
    </c:when>
    <c:otherwise>
                       <a class="btn btn-outline-info btn-xs m1-2 disabled" title="삭제버튼">삭제</a></span>
    </c:otherwise>
</c:choose>
                      </div>
                      <p ><c:out value="${fn:replace(result.commentCn , crlf , '<br/>')}" escapeXml="false" /></p>
         </div>

</c:forEach>
<c:choose>
	<c:when test="${commandMap.selectedMenu.insYn eq 'Y'}">
		<div class="post clearfix ml-3 mr-3">
			<form id="articleCommentVO" name="articleCommentVO" class="form-horizontal">
				<input type="hidden" name="nttId" value="<c:out value='${boardVO.nttId}'/>"/>
				<input type="hidden" name="commentNo" value=""/>
				<c:if test="${fn:length(commentList) == 0}">
					<div class="input-group input-group-sm  mb-3">
						<textarea id="commentCn" name="commentCn" class="form-control form-control-sm" placeholder="댓글 쓰기"></textarea>
						<div class="input-group-append">
							<button type="button" onclick="fn_egov_comment_insert()" class="btn btn-primary">저장</button>
						</div>
					</div>
				</c:if>
			</form>
		</div>
	</c:when>
</c:choose>