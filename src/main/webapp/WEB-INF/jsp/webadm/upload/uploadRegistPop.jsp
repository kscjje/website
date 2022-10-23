<%
 /**
  * @Class Name : uploadRegistPop.jsp
  * @Description : 운영관리  프로그램 전용  파일업로드
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
<link rel="stylesheet" type="text/css" href="/webadm/resources/css/style.css">
<script type="text/javascript" src="/webadm/resources/js/EgovMultiFile.js"></script>
<script type='text/javascript'>
	var fileUploadCnt = 5;
	var fileAllowExt  = "<c:out value="${uploadAllowExt}"/>";
	var fileMaxSize = 10;

	function fn_file_init() {

		/*********************************************************
		 * 파일첨부.
		 * ******************************************************** */
		var maxFileNum = fileUploadCnt;
		<c:choose>
		<c:when test="${ param.uploadField eq 'PART_IMG_FINNB'}">
			var multi_selector = new MultiSelector(document.getElementById('egovComImgList'), maxFileNum);
			multi_selector.addElement(document.getElementById('egovComFileUploader'));
			</c:when>
			<c:otherwise>
			if (document.getElementById('egovComFileList')) {
				var multi_selector = new MultiSelector(document.getElementById('egovComFileList'), maxFileNum);
				multi_selector.addElement(document.getElementById('egovComFileUploader'));

			}
			</c:otherwise>
		</c:choose>

	}
	function setThumbnail(event) {
		var flag = true;

		$.each(event.target.files , function(index,file) {
			 var fileSize = file.size;
			 var maxSize = fileMaxSize * 1024 * 1024;
			 /*
			 if (fileSize > maxSize) {
					alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
					 flag = false;
		            return;
				}
*/
  		 	if (fileAllowExt.indexOf(file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase()) < 0) {
                alert("업로드가 불가능한 파일입니다. 파일 확장자를 확인해 주세요.");
                flag = false;
                return false;
            } else {
           	 	$('#imgFileList').html("<li>"+file.name + " (" + formatBytes(file.size)+")</li>");
           	 	$('#image_container li').remove();
            }
  	 	});
		if (flag) {
			$('#image_container').append("<li></li>");

			var reader = new FileReader();
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.style.height = "100px";
				document.querySelector("ul#image_container li").appendChild(img);
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

		$(obj).parent().remove();
	}

	function fn_img_delete(obj , fileSn) {
		var fileDeleteList = $('#fileDeleteList').val();
		if (fileDeleteList != '') {
			fileDeleteList += ',';
		}
		fileDeleteList += fileSn;
		$('#fileDeleteList').val(fileDeleteList);

		$(obj).parent().remove();

		$('#image_container_'+fileSn).remove();
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
<style>
.btn-default{min-width: 150px; height: 50px;padding: 0 16px;font-weight: 500;line-height: 50px; border-radius: 5px; background:#222;border:1px solid #222;color:#fff;font-size:16px}
.btn-default:hover{min-width: 150px; height: 50px;padding: 0 16px;font-weight: 500;line-height: 50px; border-radius: 5px; background:#222;border:1px solid #222;color:#fff;font-size:16px}

</style>

	<div class="w_wrap">
		<!-- 새창팝업일시 : class="w_wrap" -->
				<div class="modal-title">
					<h3 style="margin-top:20px">
<c:choose>
    <c:when test="${ param.uploadType eq 'PSP'}">사업장</c:when>
    <c:when test="${param.uploadType eq 'EXT'}">관람</c:when>
    <c:when test="${param.uploadType eq 'EVT'}">강연/행사/영화</c:when>
    <c:when test="${param.uploadType eq 'EDC'}">교육</c:when>
    <c:when test="${param.uploadType eq 'QES'}">설문조사</c:when>
</c:choose>  업로드
					</h3>
					<h5>
				   <c:choose><c:when test="${param.uploadType eq 'QES'}"><c:out value="${programInfo.qestnarName }" /></c:when><c:otherwise><c:out value="${programInfo.title }" /></c:otherwise></c:choose>

					</h5>

          		</div>
<form name="fileForm" action="./uploadSave" class="form-horizontal" method="post"  enctype="multipart/form-data">
<input type="hidden" name="fileDeleteList" id="fileDeleteList" />
<input type="hidden" name="uploadType" id="uploadType" value="<c:out value="${param.uploadType }" />"/>
<input type="hidden" name="uniqueId" id="uniqueId" value="<c:out value="${param.uniqueId }" />"/>
<input type="hidden" name="uploadField" id="uploadField" value="<c:out value="${param.uploadField }" />"/>
<input type="hidden" name="fileGrpId" id="fileGrpId" value="${fileId }"/>
<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
				<div class="modal-body">
					<div class="modal-txt">
						<!-- ex_txt -->
						<div class="ex_txt">
							<!--
							<p class="ex_tit"><i><img src="../resources/images/template/ico_notice.png" alt="유의사항아이콘"></i> 파일첨부 시 유의사항</p>
							<p style="margin-bottom:0px">- 첨부 가능 확장자 : <c:out value="${uploadAllowExt}"/></p>
							-->
<c:choose>
	<c:when test="${param.uploadField eq 'PART_IMG_FINNB'}"><p style="margin-bottom:0px">- 1개 이상 첨부 가능합니다.</p></c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_THMB_') > 0}"><p style="margin-bottom:0px">- 1개만 첨부 가능합니다.</p></c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_INTRIMG_') > 0}"><p style="margin-bottom:0px">- 1개만 첨부 가능합니다.</p></c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_PLAN_') > 0}"><p style="margin-bottom:0px">- 1개 이상 첨부 가능합니다.</p></c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_IMG_') > 0}"><p style="margin-bottom:0px">- 1개만 첨부 가능합니다.</p></c:when>
	<c:when test="${fn:indexOf(param.uploadField , 'IMG') >= 0}"><p style="margin-bottom:0px">- 1개만 첨부 가능합니다.</p></c:when>
</c:choose>
						</div>
						<!-- //ex_txt -->
						<h4>
<c:choose>
	<c:when test="${param.uploadField eq 'PART_IMG_FINNB'}">사업장 소개이미지</c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_THMB_') > 0}">썸네일 이미지</c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_INTRIMG_') > 0}">소개 이미지</c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_PLAN_') > 0}">계획서 파일</c:when>
	<c:when test="${fn:indexOf(param.uploadField , '_IMG_') > 0}">대표 이미지</c:when>
	<c:when test="${fn:indexOf(param.uploadField , 'IMG') >= 0}">대표 이미지</c:when>
</c:choose>

						</h4>
<c:choose>
	<c:when test="${param.uploadField eq 'PART_IMG_FINNB'}">
				<div class="btn btn-default btn-file file_input_div">
		           <i class="fas fa-paperclip"></i> 이미지 첨부
		           <input type="file" name="file_1"  id="egovComFileUploader" accept="image/*"  class="file_input_hidden" >
		         </div>

		         <div class="file_list p-2">
					<ul id="egovComImgList" class="txt_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
					<li class="img"><span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span>
					<i  onclick="fn_file_delete(this,'${item.fileSn}')"></i>
					<br>
					<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" height="100"/>

					</li>
</c:forEach>
					</ul>

		         </ul>
	</c:when>
	<c:when test="${ fn:indexOf(param.uploadField , '_PLAN_') > 0}">
				<div class="btn btn-default btn-file file_input_div">
		           <i class="fas fa-paperclip"></i> 파일 첨부
		           <input type="file" name="file_1"  id="egovComFileUploader" multiple class="file_input_hidden" >
		         </div>
		         <div class="file_list p-2" >
			         <ul id="egovComFileList"  class="txt_file">

			         </ul>

			          <div class="pl-2"><label>※ 현재 파일</label></div>
					<ul  class="txt_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
						<li><span><a href="/web/common/file/download?atchFileId=<c:out value="${item.fileGrpinnb}"/>&fileSn=<c:out value="${item.fileSn}"/>" ><c:out value="${item.orginFileName}"/></a> (<tags:FileSize fileSize="${item.fileSize}"/>)
							<i onclick="fn_file_delete(this,'${item.fileSn}')"></i></span>
						</li>
</c:forEach>
					</ul>
				</div>
	</c:when>
	<c:otherwise>
				<div class="btn btn-default btn-file file_input_div ">
		           <i class="fas fa-paperclip"></i> 이미지 첨부
		           <input type="file" name="imgFileUpload" id="imgFileUpload" accept="image/*" class="file_input_hidden" onchange="setThumbnail(event);">
		         </div>
		         <div class="file_list p-2">
					<ul  id="imgFileList" class="txt_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
					<li class="img"><span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span>
					<i  onclick="fn_img_delete(this,'${item.fileSn}')"></i>
					</li>
</c:forEach>
					</ul>
					<ul id="image_container"  class="img_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
					<li id="image_container_${item.fileSn}">
						<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>"  height="100"/>

					</li>
</c:forEach>
					</ul>
				</div>


	</c:otherwise>
</c:choose>

						<!-- //file_list -->
						<p class="f16">※ 파일 저장 후에 자동으로 창이 닫히지 않습니다.</p>
					</div>
					<!-- //modal-txt -->
					<div class="btn_area">
						<a href="javascript:fn_save()" class="btn_ty_m2_c6">저장하기</a>
					</div>
					<!-- //btn_area -->
				</div>

</form>

<script>
fn_file_init() ;
</script>