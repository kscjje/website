<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function fn_file_download(atchFileId , fileSn) {
    window.location.href="<c:url value="/web"/>/common/file/download?atchFileId="+atchFileId+"&fileSn=" + fileSn;
}
</script>
<c:choose>
	<c:when test="${param.show_type eq 'user' }">
		<c:if test="${fn:length(fileList) > 0 }">
		<div class="file-list">
			<!-- <span class="file_tit">첨부파일</span> -->
			<ul>
			<c:forEach var="fileItem" items="${fileList}" varStatus="status">
				<li><a href="javascript:fn_file_download('<c:out value="${fileItem.fileGrpinnb}"/>','<c:out value="${fileItem.fileSn}"/>');" class="file"><c:out value="${fileItem.orginFileName}"/> <span>(<tags:FileSize fileSize="${fileItem.fileSize}"/>)</span></a><a href="javascript:fn_file_download('<c:out value="${fileItem.fileGrpinnb}"/>','<c:out value="${fileItem.fileSn}"/>');" class="btn_ty_s_c2 btn">다운로드</a></li>
				<c:if test="${param.show_image eq 'Y' }">
					<c:if test="${fn:toUpperCase(fileItem.fileExtsn) eq 'JPG' || fn:toUpperCase(fileItem.fileExtsn) eq 'PNG' ||  fn:toUpperCase(fileItem.fileExtsn) eq 'GIF' || fn:toUpperCase(fileItem.fileExtsn) eq 'BMP'}">
					<script>
					$(document).ready(function () {
						var img = $('<img />').attr('src','<c:url value="/web"/>/common/file/view?atchFileId=<c:out value="${fileItem.fileGrpinnb}"/>&fileSn=<c:out value="${fileItem.fileSn}"/>').css('max-width','100%');
						$('.cont-atch-img').append(img).append("<br/><br/>").css('margin-bottom','30px');
					});
					</script>
					</c:if>
				</c:if>
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