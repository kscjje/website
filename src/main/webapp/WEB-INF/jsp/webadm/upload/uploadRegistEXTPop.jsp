<%
 /**
  * @Class Name : uploadRegistEXT.jsp
  * @Description : 운영관리 (관람) 프로그램 전용  파일업로드
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.14    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.14
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script type="text/javascript" src="<c:url value="/webadm/resources/js/EgovMultiFile.js"/>"></script>
<script type='text/javascript'>
	var fileUploadCnt = 5;
	var fileAllowExt  = "<c:out value="${uploadAllowExt}"/>";
	var fileMaxSize = 10;

	function fn_file_init() {

		/*********************************************************
		 * 파일첨부.
		 * ******************************************************** */
		var maxFileNum = fileUploadCnt;
		var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
		multi_selector.addElement(document.getElementById('egovComFileUploader'));

	}
	function setThumbnail(event) {
		var flag = true;

		$.each(event.target.files , function(index,file) {
			 var fileSize = file.size;
			 var maxSize = fileMaxSize * 1024 * 1024;
			 if (fileSize > maxSize) {
					alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
					 flag = false;
		            return;
				}

  		 	if (fileAllowExt.indexOf(file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase()) < 0) {
                alert("업로드가 불가능한 파일입니다. 파일 확장자를 확인해 주세요.");
                flag = false;
                return false;
            } else {
           	 	$('#imgFileList').html("<li>"+file.name + " (" + formatBytes(file.size)+")</li>");
           	 	$('#image_container').html("");
            }
  	 	});
		if (flag) {
			var reader = new FileReader();
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.style.width = "100%";
				document.querySelector("div#image_container").appendChild(img);
			};
			reader.readAsDataURL(event.target.files[0]);
		}

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
	    if (bytes === 0) return '0 Bytes';
		if (!decimals) decimals = 2;

	    const k = 1024;
	    const dm = decimals < 0 ? 0 : decimals;
	    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

	    const i = Math.floor(Math.log(bytes) / Math.log(k));

	    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
	}

	function fn_save() {
		if (confirm("저장하시겠습니까?")) {
				document.fileForm.submit();
		}
	}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-12">
            <h1>관람 <span style="font-size:1.2rem">> <c:out value="${programInfo.title }" /></span> </h1>
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<section class="content" >
      <div class="container-fluid">
		<div class="row">
			<div class="col-sm-6">
      		<div class="card card-primary card-outline">
      			<div class="card-header">
                <h3 class="card-title">관람계획서</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
<form name="fileForm" action="./uploadSave" class="form-horizontal" method="post"  enctype="multipart/form-data">
<input type="hidden" name="fileDeleteList" id="fileDeleteList" />
<input type="hidden" name="uploadType" id="uploadType" value="<c:out value="${param.uploadType }" />"/>
<input type="hidden" name="uniqueId" id="uniqueId" value="<c:out value="${param.uniqueId }" />"/>
<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
				<div class="btn btn-default btn-file file_input_div">
		           <i class="fas fa-paperclip"></i> 파일 첨부
		           <input type="file" name="file_1"  id="egovComFileUploader" multiple class="file_input_hidden" >
		         </div>
		         <ul id="egovComFileList" class="pt-1">

		         </ul>
		         <div class="pl-2"><label>현재 파일</label></div>
				<ul >
		<c:forEach items="${planFileList}" var="item" varStatus="status">
					<li><span><a href="/web/common/file/download?atchFileId=<c:out value="${item.fileGrpinnb}"/>&fileSn=<c:out value="${item.fileSn}"/>" ><c:out value="${item.orginFileName}"/></a> (<tags:FileSize fileSize="${item.fileSize}"/>)
					<a href="javascript:;" onclick="fn_file_delete(this,'${item.fileSn}')" style="margin-left:5px" class="badge bg-danger">X</a></span></li>
		</c:forEach>
				</ul>
				<div class="help-block">* 파일당 용량은 10MB 를 넘을 수 없습니다.</div>
				<div class="help-block">* 첨부 가능 확장자 : <c:out value="${uploadAllowExt}"/></div>

		</div>
	</div><!-- card -->
</div><!-- col -->
<div class="col-sm-6">
	<div class="card card-primary card-outline">
			<div class="card-header">
                <h3 class="card-title">대표 이미지</h3>
              </div>
              <!-- /.card-header -->

              <div class="card-body">
            	<div class="btn btn-default btn-file file_input_div">
		           <i class="fas fa-paperclip"></i> 이미지 첨부
		           <input type="file" name="imgFileUpload" id="imgFileUpload" accept="image/*" class="file_input_hidden" onchange="setThumbnail(event);">
		         </div>
		         <ul id="imgFileList" class="pt-1">
<c:forEach items="${imgFileList}" var="item" varStatus="status">
					<li><span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span></li>
</c:forEach>
		         </ul>

		         <div id="image_container">
<c:forEach items="${imgFileList}" var="item" varStatus="status">
					<img src="/web/common/file/view?atchFileId=<c:out value="${item.fileGrpinnb}"/>&fileSn=<c:out value="${item.fileSn}"/>"  width="100%"/>
</c:forEach>
		         </div>
           	</div>
           </div><!-- card -->
         </div><!-- col -->
	</div><!-- row -->
</div>
</section>
</form>
	<div class="text-center">
		<button type="button" onclick="fn_save()" class="btn btn-primary">저장</button>
	</div>
<script>
fn_file_init() ;
</script>
