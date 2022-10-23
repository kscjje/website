<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<script type='text/javascript'>

	var fileUploadCnt = <c:out value="${param.file_length}"/>;
	var fileAllowExt  = "<c:out value="${param.file_ext}"/>";
	var fileMaxSize = 10;

	function fn_file_init() {

		/*********************************************************
		 * 파일첨부.
		 * ******************************************************** */
		var maxFileNum = fileUploadCnt;
		var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
		multi_selector.addElement(document.getElementById('egovComFileUploader'));
	}

	function fn_file_delete(obj , fileSn) {
		
		var fileDeleteList = $('#fileDeleteList').val();
		
		if (fileDeleteList != '') {
			fileDeleteList += ',';
		}
		
		fileDeleteList += fileSn;
		$('#fileDeleteList').val(fileDeleteList);

		$(obj).parent().parent().remove();
	}

	function formatBytes(bytes, decimals) {
		
	    if (bytes === 0) {
			return '0 Bytes';
	    }
	    
		if (!decimals) {
			decimals = 2;
		}

	    const k = 1024;
	    const dm = decimals < 0 ? 0 : decimals;
	    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

	    const i = Math.floor(Math.log(bytes) / Math.log(k));

	    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
	}
	
</script>

	<div>
		<div class="btn btn-default btn-file file_input_div">
           <i class="fas fa-paperclip"></i> Attachment
           <input type="file" name="file_1"  id="egovComFileUploader" multiple class="file_input_hidden" >
         </div>
		<ul id="egovComFileList" class="pt-1">
			<c:forEach items="${fileList}" var="item" varStatus="status">
				<li><span><c:out value="${item.orginFileName}"/><a href="javascript:;" onclick="fn_file_delete(this,'${item.fileSn}')" style="margin-left:5px" class="badge bg-danger">X</a></span></li>
			</c:forEach>
		</ul>
		<input type="hidden" name="fileDeleteList" id="fileDeleteList" />
	</div>
	<div class="help-block">* 첨부 파일 최대 <c:out value="${param.file_length}"/>개, 파일당 용량은 10MB 를 넘을 수 없습니다.</div>
	<div class="help-block">* 첨부 가능 확장자 : <c:out value="${param.file_ext}"/></div>
	
	<script>
		fn_file_init() ;
	</script>
