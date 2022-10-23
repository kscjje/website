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
<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
<script type="text/javascript" src="../resources/js/EgovMultiFile.js"></script>
<script type='text/javascript'>
	var fileUploadCnt = 5;
	var fileAllowExt  = "<c:out value="${uploadAllowExt}"/>";
	var fileMaxSize = 10;

	function fn_file_init() {

		/*********************************************************
		 * 파일첨부.
		 * ******************************************************** */
		var maxFileNum = fileUploadCnt;

		var multi_selector = new MultiSelector(document.getElementById('egovComImgList'), maxFileNum);
		multi_selector.addElement(document.getElementById('egovComFileUploader'));


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
					<h3 style="margin-top:20px;margin-left:20px">강좌신청안내 이미지  업로드</h3>

          		</div>
<form name="fileForm" action="./uploadRegisSave" class="form-horizontal" method="post"  enctype="multipart/form-data">
<input type="hidden" name="fileDeleteList" id="fileDeleteList" />
<input type="hidden" name="fileGrpId" id="fileGrpId" value="${fileId }"/>
<input type="hidden" name="contentsSeq" id="contentsSeq" value="<c:out value="${param.contentsSeq}"/>"/>
<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
				<div class="modal-body">
					<div class="modal-txt">

				<div class="btn btn-default btn-file file_input_div">
		           <i class="fas fa-paperclip"></i> 이미지 첨부
		           <input type="file" name="file_1"  id="egovComFileUploader" accept="image/*"  class="file_input_hidden" >
		         </div>

		         <div class="file_list p-2">
					<ul id="egovComImgList" class="txt_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
					<li class="img" data-filesn="${item.fileSn}"><span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span>
					<i  onclick="fn_file_delete(this,'${item.fileSn}')"></i>
					<br>
					<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" height="150"/>

					</li>
</c:forEach>
					</ul>

		         </ul>


						<!-- //file_list -->
						<p class="f16">※ 파일 추가/삭제 후 저장하기 버튼을 클릭하셔야 적용 됩니다.</p>
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


opener.$('#fileId2').val('${fileId}');
<c:if test="${!empty fileId}">
opener.$('#egovComImgList2').html("");

$.each($('#egovComImgList').find("li") , function(){
	var obj = $('<li></li>');
	var html = "<span>"+ $(this).find("span").html() +"</span><br/>";

	var filesn = $(this).data("filesn");
	var realFilePath = $(this).find("img").attr("src");
	var atchFileId = '${fileId}';

	html += "<input type=\"hidden\" id=\"fileurl_0"+ filesn +"\" value=\""+realFilePath+"\" />";
	html += "<a href='javascript:;' onclick=\"fn_file_delete(this , '"+atchFileId+"' ,'"+ filesn+"')\">[삭제]</a> <a href='javascript:;' onclick=\"fn_url_copy('0"+filesn+"')\">[URL]</a>";
	html += "<div class=\"imgArea\"></div>";
	obj.html(html);


	var img = document.createElement("img");
	img.setAttribute("src",realFilePath);

	obj.find(".imgArea").append(img);

	opener.$('#egovComImgList2').append(obj);
});
</c:if>
</script>