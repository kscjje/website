<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : contentsRegi.jsp
  * @Description : 컨텐츠 관리 등록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.22    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.22
  *  @version 1.0
  *  @see
  *
  */
%>

<head>
	<style>
		.txt_file{
			display:flex;
			align-content:flex-start;
			flex-wrap:wrap;
			overflow:auto;
			padding:0px;
		}
		.txt_file li {width:25% ; border:1px solid #ccc !important; margin:5px !important;padding:5px !important;font-size:12px;max-width:250px;list-style:none !important}
		.txt_file li span{white-space: normal !important;}
		.imgArea{width:100%;max-height:100px;overflow:hidden}
		.imgArea img{width:100%}
	</style>
	<script type="text/javascript">

	$(document).ready(function() {
		$('#uploadPopBtn').click(function(){
			var atachFileId = $('#fileId2').val();
			window.open("./uploadRegistPopup?atachFileId=" +atachFileId +"&contentsSeq=${contentsVO.contentsSeq}" , "uploadPopup" , "width=500,height=700,scrollbars=yes") ;
		})
	});



	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	 function fn_save() {

			var varFrom = document.contentsVO;

			var varMode = $($("input[name='mode']")).val();

			/*
			var varCtgCd = $($("input[name='contentsSubject']")).val();
			if ((varCtgCd === undefined) || (varCtgCd == null) || (varCtgCd == '')) {
				alert("컨텐츠 제목은 비울 수 없습니다.");
				$("input[name='contentsSubject']").focus();
				return;
			}
			*/

			top.modules_board__oEditors.getById["cnts"].exec("UPDATE_CONTENTS_FIELD", []);
			
			var varContentsMain = $($("textarea[name='cnts']")).val();
			if ((varContentsMain === undefined) || (varContentsMain == null) || (varContentsMain == '')) {
				alert("컨텐츠 내용은 비울 수 없습니다.");
				$("textarea[name='contentsMain']").focus();
				return;
			}

			var msg = "등록하시겠습니까?";
			var url = './contentsInsert.json';
			if (varFrom.mode.value == "update") {
				msg = "수정하시겠습니까?";
				url = './contentsUpdate.json';
			}

			if ((varMode === undefined) || (varMode == null) || (varMode == '')) {
				$($("input[name='mode']")).val("insert");
			}

			if (confirm(msg)) {
				$.ajax({
				      url: url,
				      type : "POST",
				      data : $('#contentsVO').serialize(),
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         window.location.replace("./contentsList");
					         }
				      }
				});
			}
		}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	             <h1>컨텐츠 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
		       <div class="row">
		          <div class="col-12">
		            <div class="card card-primary card-outline">
		              <!-- /.card-header -->
<form:form commandName="contentsVO" name="contentsVO" action="./contentsSavePost" class="form-horizontal" method="post">
<input type="hidden" name="searchQuery" value="<c:out  value='${searchQuery}'/>"/>
<input type="hidden" name="mode"        value="<c:out  value='${mode}'/>"/>
<form:hidden path="menuNo"/>
<form:hidden path="contentsSeq"/>
<form:hidden path="fileId"/>
<form:hidden path="fileId2"/>
						<div class="card-body  table-responsive p-0">
			                <table class="table table-bordered text-nowrap">
				               	<colgroup>
									<col style="width:5%;">
									<col style="width:45%;">
									<col style="width:5%;">
									<col style="width:45%;">
								</colgroup>

									<tbody>

										<tr>
											<th><label class="col-sm-2 col-form-label"> 고유번호</label></th>
											<td  class="left">
												<c:choose>
													<c:when test="${mode eq 'insert' }">
														<label class="col-sm-2 col-form-label"> 자동 생성됩니다.</label>
													</c:when>
													<c:otherwise>
														<label class="col-sm-2 col-form-label"> ${contentsVO.contentsSeq}</label>
													</c:otherwise>
												</c:choose>
											</td>
											<th><label for="cntsType" class="col-sm-2 col-form-label"> * 컨텐츠 속성</label></th>
											<td class="left">
						                    	<form:select path="cntsType" class="form-control " style="width:100px;float:left">
						                    		<form:option value="1001"> HTML</form:option>
						                    		<form:option value="2001"> TEXT</form:option>
						                    	</form:select>
											</td>
										</tr>
										<tr>
											<th><label for="contentsGbn" class="col-sm-2 col-form-label"> 연결된 메뉴</label></th>
											<td colspan="3" class="left">
												<c:choose>
													<c:when test="${contentsVO.menuNo>0}">
														<label class="col-form-label">${contentsVO.menuNm} (${contentsVO.menuNo})</label>
													</c:when>
													<c:otherwise>
														<label class="col-form-label">없음</label>
													</c:otherwise>
												</c:choose>

											</td>

										</tr>
										<tr>
											<th>
												<label for="title" class="col-sm-2 col-form-label"> 컨텐츠 제목</label>
											</th>
											<td class="left" colspan="3">
						                    	<form:input path="title" title="컨텐츠 제목" class="form-control " maxlength="255" />
											</td>
										</tr>
										<tr>
											<th><label for="cnts" class="col-sm-2 col-form-label">* 컨텐츠 내용</label></th>
											<td colspan="3" class="left">
											<c:set var="content_height" scope="request" value="40em"/>
											<c:set var="content_nm" scope="request" value="cnts"/>
											<jsp:include page="/WEB-INF/jsp/modules/seditor/inc.seditor.content.jsp"/>
											<% /*
												<form:textarea path="cnts" title="컨텐츠 내용" class="form-control " rows="20" cols="200" />
											*/ %>
											</td>
										</tr>
										<tr>
											<th><label for="RegiUFile01" class="col-sm-2 col-form-label">파일 첨부</label></th>
											<td class="left" colspan="3">

												<div class="form-group">
								                  <div class="btn btn-default btn-file fileupload_process" data-inputid="fileId">
								                    <i class="fas fa-paperclip"></i> Attachment
								                  </div>
								                </div>

								                <div class="file_list">
													<ul id="egovComImgList" class="txt_file">
<c:forEach items="${fileList}" var="item" varStatus="status">
					<li id="image_container_${item.fileSn}">
						<span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span>
						<input type="hidden" id="fileurl_${item.fileSn}" value="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" />
						<br/>
						<a href='javascript:;' onclick="fn_file_delete(this , '${item.fileGrpinnb}' ,'${item.fileSn}')">[삭제]</a> <a href='javascript:;' onclick="fn_url_copy('${item.fileSn}')">[URL]</a>
						<c:set var="fileExtsn" value="${fn:toLowerCase(item.fileExtsn) }" />
						<c:if test="${fileExtsn eq 'png' or fileExtsn eq 'jpg' or fileExtsn eq 'gif' or fileExtsn eq 'jpge'}">
							<div class="imgArea">
								<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>"/>
							</div>
						</c:if>

					</li>
</c:forEach>
														</ul>

		         								</div>

											</td>
										</tr>
<c:if test="${fn:indexOf(contentsVO.menuUrl,'orgintro') >0 }">
										<tr>
											<th><label for="RegiUFile01" class="col-sm-2 col-form-label">강좌신청안내 이미지</label></th>
											<td class="left" colspan="3">

												<div class="form-group">
								                  <div id="uploadPopBtn" class="btn btn-default btn-file" data-inputid="fileId" >
								                    <i class="fas fa-paperclip"></i> 이미지 업로드
								                  </div>
								                </div>

								                <div class="file_list">
													<ul id="egovComImgList2" class="txt_file">
<c:forEach items="${fileList2}" var="item" varStatus="status">
					<li id="image_container2_${item.fileSn}">
						<span><c:out value="${item.orginFileName}"/> (<tags:FileSize fileSize="${item.fileSize}"/>)</span>
						<input type="hidden" id="fileurl_0${item.fileSn}" value="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" />
						<br/>
						<a href='javascript:;' onclick="fn_file_delete(this , '${item.fileGrpinnb}' ,'${item.fileSn}')">[삭제]</a> <a href='javascript:;' onclick="fn_url_copy('0${item.fileSn}')">[URL]</a>
						<div class="imgArea">
							<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>"/>
						</div>
					</li>
</c:forEach>
														</ul>

		         								</div>

											</td>
										</tr>
</c:if>
										<tr>
											<th><label for="edcGuideComment" class="col-sm-2 col-form-label">* 사용 여부</label></th>
											<td colspan="3" class="left">
							                    <div class="col-sm-10">
							                    	<label class="col-sm-2 col-form-label"><form:radiobutton path="useYn" value="Y"/> 사용함</label>
												    &nbsp;&nbsp;&nbsp;
												    <label class="col-sm-2 col-form-label"><form:radiobutton path="useYn" value="N"/> 사용안함</label>
							                    </div>
											</td>
										</tr>
<c:if test="${!empty contentsVO.reguser }">
										<tr>
											<th>등록일</th>
											<td class="left"><c:out value="${contentsVO.reguser}"/> / <fmt:formatDate value="${contentsVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
											<th>수정일</th>
											<td class="left"><c:if test="${!empty contentsVO.moduser }"><c:out value="${contentsVO.moduser}"/> / <fmt:formatDate value="${contentsVO.moddate}" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
										</tr>
</c:if>
									</tbody>

								</table>
		              	</div>

		                <div class="card-footer">
		                  <a href="javascript:fn_save();" class="btn btn-info float-right">저장</a>
		                  <button type="button" class="btn btn-default " onclick="history.back(); return false;">취소</button>
		                </div>

</form:form>

					</div>
				</div>
	        </div>
	       </div>
	</section>

<script>
//업로드 가능 확장자
var UPLOAD_EXT = 'JPG, JPEG, PNG, GIF, HWP, DOC, DOCX, XLS, XLSX, PDF, TXT, PPT, PPTX, ZIP';
var IMG_EXT = 'JPG, JPEG, PNG, GIF,jpg,jpeg,png,gif';
var UPLOAD_EXTS = UPLOAD_EXT.split(',');

 //클릭 이벤트
  $('.preview').find("img").click(function(){
	  window.open($(this).attr("src"));
  });
//파일 업로드 처리
	$('.fileupload_process').click(function(e){
		$('#image-file-upload').remove();

		var last_flag = 0;
		var form = $('<form id="image-file-upload" style="display: block; width: 1px; height: 0px; overflow: hidden;" method="post" enctype="multipart/form-data"><input type="file" name="file" value="" /><input type="submit" value="업로드" /></form>');

		var inputid = $(this).data("inputid");
		var fileid = $('#'+inputid).val();

		$(document.body).append(form);

		// 파일 업로드 버튼 클릭 처리
		form.find('[type="file"]').change(function(e){
			var val = $(this).val();
			if(val == ''){
				alert('파일을 선택해 주세요.');
				return;
			}

			// 확장자 체크
			var ext = $.trim(val.indexOf('.') > -1 ? val.substring(val.lastIndexOf('.') + 1).toUpperCase() : '');
			if(ext == ''){
				alert('확장자가 없는 파일은 선택하실 수 없습니다.');
				return false;
			}else{
				var check = false;
				for(var i=0; i<UPLOAD_EXTS.length; i++)
				{
					if($.trim(UPLOAD_EXTS[i]) == ext)
					{
						check = true;
						break;
					}
				}
				if(!check){
					alert(ext + ' 확장자는 첨부하실 수 없습니다.\n* 첨부 가능 확장자 : '+ UPLOAD_EXT);
					return false;
				}
			}

			$('body').showLoading();

			// 폼 전송 처리
			form.ajaxSubmit({
				type : 'POST',
				url : './fileUpload.json',
				data : { 'inputid' : inputid ,
						 'atchFileId' : fileid
						},
				dataType : 'json',
				error : function()
				{
					alert('업로드 처리 중 오류가 발생하였습니다.');
					$('body').hideLoading();
				},
				beforeSend : function(xhr)
				{
					 xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success: function(data, statusText, xhr, $form)
				{
					form.empty().remove();
					//console.log(data);

					$('body').hideLoading();

					if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	alert("업로드되었습니다.");

			        	var obj = $('<li></li>');
			        	var html = "<span>"+ data.originFileName + " (" + formatBytes(data.fileSize)+")</span><br/>";
			        	html += "<input type=\"hidden\" id=\"fileurl_"+data.fileSn+"\" value=\""+data.realFilePath+"\" />";
			        	html += "<a href='javascript:;' onclick=\"fn_file_delete(this , '"+data.atchFileId+"' ,'"+data.fileSn+"')\">[삭제]</a> <a href='javascript:;' onclick=\"fn_url_copy('"+data.fileSn+"')\">[URL]</a>";
			        	html += "<div class=\"imgArea\"></div>";
			        	obj.html(html);

			        	if(IMG_EXT.indexOf(ext) >=0){
			        		var img = document.createElement("img");
							img.setAttribute("src", data.realFilePath);

							obj.find(".imgArea").append(img);
			        	}else{

			        	}

			        	$('#'+inputid).val(data.atchFileId);
			        	$('#egovComImgList').append(obj);
			        }

				}
			});
		}).click();
	});

	function formatBytes(bytes, decimals) {
	    if (bytes === 0) return '0 Bytes';
		if(!decimals) decimals = 2;

	    const k = 1024;
	    const dm = decimals < 0 ? 0 : decimals;
	    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

	    const i = Math.floor(Math.log(bytes) / Math.log(k));

	    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
	}

	function fn_file_delete(obj ,fileId, fileSn) {
		//DB 삭제 처리
		if(confirm("바로 삭제됩니다.\n삭제하시겠습니까?")){
			$.ajax({
			      url: './fileDelete.json',
			      type : "POST",
			      data : {'atchFileId' : fileId , 'fileSn' : fileSn},
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 $(obj).parent().remove();
				        }
			      }
			});
		}
	}

	function fn_url_copy(fileSn){
		var val = $('#fileurl_'+fileSn).val();

		window.prompt("아래 주소를 사용하세요.", val);
	}
 </script>
</body>