<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<script>

	function fn_file_download(atchFileId , fileSn) {
	    window.location.href="/web/common/file/download?atchFileId="+atchFileId+"&fileSn=" + fileSn;
	}
	
</script>

<c:choose>
	<c:when test="${param.show_type eq 'user' }">
		<c:if test="${fn:length(fileList) > 0 }">
		<div class="file-list">
			<span class="file_tit">첨부파일</span>
			<ul>
				<c:forEach var="fileItem" items="${fileList}" varStatus="status">
					<li><a href="javascript:fn_file_download('<c:out value="${fileItem.fileGrpinnb}"/>','<c:out value="${fileItem.fileSn}"/>');" class="file"><c:out value="${fileItem.orginFileName}"/> <span>(<tags:FileSize fileSize="${fileItem.fileSize}"/>)</span></a><a href="javascript:fn_file_download('<c:out value="${fileItem.fileGrpinnb}"/>','<c:out value="${fileItem.fileSn}"/>');" class="btn_ty_s_c2 btn">다운로드</a></li>
				</c:forEach>
			</ul>
		</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="fileItem" items="${fileList}" varStatus="status">
			<div  style="line-height:25px"><i class="fa fa-save"></i> <a href="javascript:fn_file_download('<c:out value="${fileItem.fileGrpinnb}"/>','<c:out value="${fileItem.fileSn}"/>');"><c:out value="${fileItem.orginFileName}"/></a></div>
		</c:forEach>
	</c:otherwise>
</c:choose>