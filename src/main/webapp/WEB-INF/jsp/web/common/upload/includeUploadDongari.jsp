<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<script type='text/javascript'>

	var fileUploadCnt = <c:out value="${param.file_length}"/>;
	var fileAllowExt  = "<c:out value="${param.file_ext}"/>";
	var fileMaxSize = 10;

	function fn_file_init() {
		console.log("fn_file_init::");
		/*********************************************************
		 * 파일첨부.
		 * ******************************************************** */
		var maxFileNum = fileUploadCnt;
		var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
		multi_selector.addElement(document.getElementById('egovComFileUploader'));
	}

	function fn_file_delete(obj , fileSn) {
		console.log("fn_file_delete::");
		var fileDeleteList = $('#fileDeleteList').val();
		if (fileDeleteList != '') {
			fileDeleteList += ',';
		}
		fileDeleteList += fileSn;
		$('#fileDeleteList').val(fileDeleteList);

		$(obj).parent().parent().remove();
	}

	function formatBytes(bytes, decimals) {
		console.log("formatBytes::");
	    if (bytes === 0) return '0 Bytes';
		if (!decimals) decimals = 2;

	    const k = 1024;
	    const dm = decimals < 0 ? 0 : decimals;
	    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

	    const i = Math.floor(Math.log(bytes) / Math.log(k));

	    // 이전 동아리 이미지는 삭제 동아리
	    $('.dongari-img').remove();
	    $('.file_add').remove();
	    var fileDeleteList = '<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index > 0 }">,</c:if><c:out value="${item.fileSn}"/></c:forEach>';
		$('#fileDeleteList').val(fileDeleteList);

	    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
	}

</script>

<c:set var="fileNm" value=""/>

<div class="w_box b_area">
	<div class="inputbox w100">
		<input type="file" name="file_1"  id="egovComFileUploader" multiple class="file02" >
		<label for="egovComFileUploader" class="hidden"></label>
		<input type="text" placeholder="" id="fileText" class="upload-name02" value="<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index > 0 }">,</c:if><c:out value="${item.orginFileName}"/></c:forEach>" readonly>
	</div>
	<button type="button" class="btn_s3_c2 btn_file02">파일선택</button>
	<input type="hidden" name="fileDeleteList" id="fileDeleteList" />
	<div id="egovComFileList" style="display:none;"></div>
</div>
	<c:if test="${param.show_image eq 'Y' }">
	<c:forEach items="${fileList}" var="item" varStatus="status">
		<c:if test="${fn:toUpperCase(item.fileExtsn) eq 'JPG' || fn:toUpperCase(item.fileExtsn) eq 'PNG' ||  fn:toUpperCase(item.fileExtsn) eq 'GIF' || fn:toUpperCase(item.fileExtsn) eq 'BMP'}">
		<img src="<c:url value="/web"/>/common/file/view?atchFileId=<c:out value="${item.fileGrpinnb}"/>&fileSn=<c:out value="${item.fileSn}"/>" alt="학습동아리 <c:out value="${item.orginFileName}"/> 이미지" onError="this.src='<c:url value="/web"/>/resources/images/data/no_img02.jpg'" style="max-width:100px; margin-top:10px;" class="dongari-img">
		<br>
		</c:if>
	</c:forEach>
	</c:if>

	<% /*
	<div class="help-block">* 첨부 파일 최대 <c:out value="${param.file_length}"/>개, 파일당 용량은 10MB 를 넘을 수 없습니다.</div>
	<div class="help-block">* 첨부 가능 확장자 : <c:out value="${param.file_ext}"/></div>
	*/ %>
	<script>
	fn_file_init() ;
	</script>
