<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script type="text/javascript" src="<c:url value="${webDir}"/>/resoources/js/jquery.form.js"></script>
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
			window.open("<c:url value="${webDir}"/>/contents/uploadRegistPopup?atachFileId=" +atachFileId +"&contentsSeq=${contentsVO.contentsSeq}" , "uploadPopup" , "width=500,height=700,scrollbars=yes") ;
		})
	});



	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	 function fn_save() {

			var varFrom = document.contentsVO;
			var varContentsMain = $($("textarea[name='cnts']")).val();
			if ((varContentsMain === undefined) || (varContentsMain == null) || (varContentsMain == '')) {
				alert("컨텐츠 내용은 비울 수 없습니다.");
				$("textarea[name='contentsMain']").focus();
				return;
			}

			if (confirm("수정하시겠습니까?")) {
				$.ajax({
				      url: '../contentsUpdate.json',
				      type : "POST",
				      data : $('#contentsVO').serialize(),
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						        // window.location.replace("./contentsList");
					         }
				      }
				}).fail(function(){
					 alert("에러 발생. 관리자에게 문의 주세요.");
				});
			}
		}
	</script>
</head>
<body>
	<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}"/>">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>

	<div id="content" class="sub_content">
		<div class="sub_top type02">
			<h3>컨텐츠 관리</h3>
		</div>

		<div class="table">
<form:form commandName="contentsVO" name="contentsVO" action="./contentsSavePost"  method="post">
<form:hidden path="menuNo"/>
<form:hidden path="contentsSeq"/>
<form:hidden path="fileId"/>
<form:hidden path="fileId2"/>
						<div class="card-body  table-responsive p-0">
			                <table class="table table-bordered text-nowrap">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:40%;">
									<col style="width:10%;">
									<col style="width:40%;">
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
												<form:textarea path="cnts" title="컨텐츠 내용" class="form-control " rows="20" cols="200" />
											</td>
										</tr>
										<tr>
											<th><label for="RegiUFile01" class="col-sm-2 col-form-label">파일 첨부</label></th>
											<td class="left" colspan="3">

												<div class="form-group">
								                  <button type="button" class="btn_s3_c2_midium btn_file02 fileupload_process"  data-inputid="fileId">파일선택</button>
								                </div>

								                <div class="p-2">
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
								                  <button id="uploadPopBtn" type="button" class="btn_s3_c2_midium btn_file02"  data-inputid="fileId">이미지 업로드</button>
								                </div>

								                <div >
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
												<div class="w_box dblock">
								                   <div class="radio">
														<span>
															<form:radiobutton path="useYn" value="Y"/>
															<label for="useYn1">사용함</label>
														</span>
														<span>
															<form:radiobutton path="useYn" value="N"/>
															<label for="useYn2">사용안함</label>
														</span>
													</div>
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
		              	<div class="btn_area">
								<a href="javascript:fn_save();" class="btn_s1_c1">저장</a>
								<a href="<c:url value="${contentsVO.menuUrl }"/>" class="btn_s1_c0">메뉴로 돌아가기</a>
						</div>
						<!--// btn_area  -->
</form:form>
		<!--// table  -->
		</div>
	</div>

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

			// 폼 전송 처리
			form.ajaxSubmit({
				type : 'POST',
				method : 'POST',
				url : '../fileUpload.json',
				data : { 'inputid' : inputid ,
						 'atchFileId' : fileid
						},
				dataType : 'json',
				error : function()
				{
					alert('업로드 처리 중 오류가 발생하였습니다.');
				},
				beforeSend : function(xhr)
				{
					 xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success: function(data, statusText, xhr, $form)
				{
					form.empty().remove();
					//console.log(data);

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
			      url: '../fileDelete.json',
			      type : "POST",
			      data : {'atchFileId' : fileId , 'fileSn' : fileSn},
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 $(obj).parent().remove();
				        }
			      }
			}).fail(function(){
				 alert("에러 발생. 관리자에게 문의 주세요.");
			});
		}
	}

	function fn_url_copy(fileSn){
		var val = $('#fileurl_'+fileSn).val();

		window.prompt("아래 주소를 사용하세요.", val);
	}

 </script>
</body>