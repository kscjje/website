<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrctrRegi.jsp
	 * @Description : 강사관리 등록 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.05
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript" src="../validator"></script>
<%-- <validator:javascript formName="instrctrVO" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<script type="text/javascript">
var fn_save = function() {
	var form = jQuery("#instrctrVO")[0];

	if(form.nameKor.value == ""){
		alert("강사명을 입력해 주세요.");
		form.nameKor.focus();
		return;
	}

	if($('input[name=genderGbn]:checked').length < 1){
		alert("성별을 선택해 주세요.");
		form.genderGbn[0].focus();
		return;
	}

	if(form.email.value == ""){
		alert("이메일 주소를 입력해 주세요.");
		form.email.focus();
		return;
	}

	if(form.hpNo.value == ""){
		alert("핸드폰번호를 입력해 주세요.");
		form.hpNo.focus();
		return;
	}

	var msg = "등록하시겠습니까?";
	<c:if test="${instrctrVO.instrctrNo >0 }">
		msg = "수정하시겠습니까?";
	</c:if>
	if(confirm(msg)){
		form.submit();
	}

};

function fn_delete() {
	
	if (confirm("해당 팝업의 정보를 삭제하시겠습니까?")) {
		$.ajax({
		      url: './'+$('#instrctrNo').val(),
		      data : {
		    	  'instrctrNo' : $('#instrctrNo').val(),
		    	  'comcd' : $('#comcd').val()
		    	  },
		      type : "POST",
		      success: function(data) {
		    	   if (JSON.parse(data).result == false) {
			        	 alert(JSON.parse(data).comment);
			        } else {
			        	 alert(JSON.parse(data).comment);
			        	 window.location.replace("./list");
			         }
		      },error : function (data,request,status,error) {
		    	    alert('잠시 후 다시 시도해주세요.');
		    	    return false;
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
            <h1><c:choose><c:when test="${instrctrVO.instrctrNo >0 }">교육강사 상세</c:when><c:otherwise>교육강사 등록</c:otherwise></c:choose></h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<section class="content">
<div class="container-fluid">
 <c:if test="${!empty errorMsg}">
		<div class="row">
          <div class="col-md-12">
      		<div class="ccallout callout-danger">
              <h5><i class="fas fa-info"></i> ERROR:</h5>
             ${errorMsg }
            </div>
         </div>
       </div>
</c:if>
<form:form commandName="instrctrVO" name="instrctrVO" class="form-horizontal" method="post" action="${formAction}" enctype="multipart/form-data">
	<form:hidden path="instrctrNo" />
	<form:hidden path="comcd" />
	<input type="hidden" name="searchQuery" value="${searchQuery }"/>
	<div class="row">
		<div class="col-12">
			<div class="card card-primary card-outline">
              <!-- /.card-header -->
              <div class="card-body">
              		<div><h5 style="color:black">[개인 정보]</h5> </div>
              		<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th><span class="pilsu">*</span> 기관선택</th>
							<td class="left">
								<c:choose>
									<c:when test="${instrctrVO.instrctrNo >0 }">
										<form:hidden path="orgNo" />
										${instrctrVO.orgNm }
									</c:when>
									<c:otherwise>
										<hisco:OrgList defaultNo="${instrctrVO.orgNo}" id="orgNo" />
									</c:otherwise>
								</c:choose>
							</td>
							<th><span class="pilsu">*</span>  강사명</th>
							<td class="left">
								<form:input path="nameKor" title="강사명(한글)" class="form-control " maxlength="50" />
   								<div><form:errors path="nameKor" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 성별</th>
							<td class="left">
								<div class="col-sm-10">
			                      <div class="icheck-primary d-inline">
			                      	<form:radiobutton path="genderGbn" value="M"/>
			                        <label for="genderGbn1">남자</label>
			                      </div>
			                      <div class="icheck-primary d-inline">
			                        <form:radiobutton path="genderGbn" value="F"/>
			                        <label for="genderGbn2">
			                        	여자
			                        </label>
			                      </div>
				                  <div><form:errors path="genderGbn" cssClass="error" /></div>
								</div>
							</td>
							<th><span class="pilsu">*</span>  이메일</th>
							<td class="left">
								<form:input path="email" title="이메일" class="form-control " maxlength="100" />
   								<div><form:errors path="email" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 휴대폰번호</th>
							<td class="left">
								<form:input path="hpNo" title="핸드폰번호" class="form-control " maxlength="20" />
   								<div><form:errors path="hpNo" cssClass="error" /></div>
							</td>
							<th>자택 전화번호</th>
							<td class="left">
								<form:input path="homeTel" title="자택 전화번호" class="form-control " maxlength="20" />
							</td>
						</tr>
						<tr>
							<th>직장 전화번호</th>
							<td class="left">
								<form:input path="officeTel" title="사무실 전화번호" class="form-control " maxlength="20" />
							</td>
							<th>소속</th>
							<td class="left">
								<form:input path="psitnOrgname" title="소속" class="form-control " maxlength="50" />
							</td>
						</tr>
						<tr>
							<th>최종학력</th>
							<td class="left">
								<form:input path="lastAcdmcr" title="최종학력" class="form-control " maxlength="20" />
   								<div><form:errors path="lastAcdmcr" cssClass="error" /></div>
							</td>
							<th>최종학교명</th>
							<td class="left">
								<form:input path="lastShname" title="최종학교명" class="form-control " maxlength="20" />
   								<div><form:errors path="lastShname" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>입금은행명</th>
							<td class="left">
								<form:input path="bankNm" title="입금은행" class="form-control " maxlength="20" />
							</td>
							<th>예금주명</th>
							<td class="left">
								<form:input path="bankAccNm" title="예금주명" class="form-control " maxlength="20" />
							</td>
						</tr>
						<tr>
							<th>입금계좌번호</th>
							<td class="left">
								<form:input path="bankAccNo" title="입금은행계좌번호" class="form-control " maxlength="20" />
							</td>
							<th>사용여부</th>
							<td class="left">
								<form:select path="useyn" cssClass="txt">
									<form:option value="Y"  label=" 사용함"/>
									<form:option value="N"  label=" 사용안함"/>
								</form:select>
		   						<div><form:errors path="useyn" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>강사 사진</th>
	                   		<td>
<c:choose>
	<c:when test="${instrctrVO.instrctrNo >0 }">
				                <div class="input-group mb-3">
		                    		<form:hidden path="instrctrImgid" />
				                  	<input type="text" class="form-control" readonly id="imgOrigin" value="<c:out value="${instrctrVO.imgOrigin}"/>">
				                  	<div class="input-group-append">
				                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="imgOrigin" data-inputid="instrctrImgid">파일변경</span>
				                  	</div>
				                </div>
				                <div id="instrctrImgidPreview" class="preview"><c:if test="${!empty instrctrVO.imgOrigin}"><img src="<tags:UploadFileUrl filePath="${instrctrVO.imgPath}" fileName="${instrctrVO.imgFilenm }" originName="${instrctrVO.imgOrigin }"/>" style="height:50px;cursor:pointer"/></c:if></div>
	</c:when>
	<c:otherwise>
								<input type="file" name="file_1" title="강사이미지" class="form-control" accept="image/*" />
	</c:otherwise>
</c:choose>

	                    	</td>
	                    	<td></td>
	                    	<td></td>
						</tr>
						<tr>
							<th>자기소개</th>
							<td colspan="3">
								<form:textarea path="selfintrcn" title="자기소개" class="form-control " maxlength="1000" rows="4"/>
   								<div><form:errors path="selfintrcn" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>경력사항</th>
							<td colspan="3">
								<form:textarea path="careerInfo" title="경력사항" class="form-control " maxlength="1000" rows="4"/>
   								<div><form:errors path="careerInfo" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>기타사항</th>
							<td colspan="3">
								<form:textarea path="etc" title=" 기타사항" class="form-control " maxlength="1000" rows="4"/>
							</td>
						</tr>
<c:if test="${instrctrVO.instrctrNo >0 }">
						<tr>
							<th>등록일</th>
							<td class="left">${instrctrVO.reguser} | <fmt:formatDate value="${instrctrVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
							<th>수정일</th>
							<td class="left">${instrctrVO.moduser} | <fmt:formatDate value="${instrctrVO.moddate}" pattern="yyyy.MM.dd HH:mm"/></td>
						</tr>
</c:if>
					</tbody>
				</table>
<c:if test="${instrctrVO.instrctrNo >0 }">
				<div><h5 style="color:black" class="pt-5">[최근 강좌 정보]</h5> </div>
              		<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
<c:if test="${empty edcList }">
							<tr>
								<td colspan="4">강좌 정보가 없습니다.</td>
							</tr>
</c:if>
<c:forEach items="${edcList}" var="resultInfo" varStatus="status">
							<tr>
								<th>최근(${status.index+1 }) 강좌명</th>
								<td><c:out value="${resultInfo.edcPrgmnm}"/></td>
								<th>최근(${status.index+1 }) 강좌기간</th>
								<td><hisco:DateUtil datestr="${resultInfo.edcSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${resultInfo.edcEdate}" format="yyyy.MM.dd"/></td>
							</tr>
</c:forEach>
						</tbody>
					</table>
</c:if>
              </div>
              <div class="card-footer">
              		<a href="./list${searchQuery }" class="btn btn-default">목록</a>
 <c:choose>
	<c:when test="${instrctrVO.instrctrNo >0 }">
					<c:choose>
                		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
                			 <button type="button" onclick="fn_delete()"  class="btn btn-danger float-right">삭제</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-danger float-right disabled">삭제</button>
                		</c:otherwise>
                	</c:choose>

					<c:choose>
                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
                			 <button type="button" onclick="fn_save()"  class="btn btn-info float-right mr-2">저장</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-info float-right disabled mr-2">저장</button>
                		</c:otherwise>
                	</c:choose>
	</c:when>
	<c:otherwise>
                	<c:choose>
                		<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
                			 <button type="button" onclick="fn_save()"  class="btn btn-info float-right">저장</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-info float-right disabled">저장</button>
                		</c:otherwise>
                	</c:choose>
    </c:otherwise>
</c:choose>
               </div>

			</div>
		</div>
	</div>
</form:form>
</div>
</section>
<c:if test="${instrctrVO.instrctrNo >0 }">
<script>
	//업로드 가능 확장자
	var UPLOAD_EXT = 'JPG, JPEG, PNG, GIF, HWP, DOC, DOCX, XLS, XLSX, PDF, TXT, PPT, PPTX, ZIP';
	var UPLOAD_EXTS = UPLOAD_EXT.split(',');

 //클릭 이벤트
  $('.preview').find("img").click(function(){
	  window.open($(this).attr("src"));
  })
//파일 업로드 처리
	$('.fileupload_process').click(function(e){
		$('#image-file-upload').remove();

		var last_flag = 0;
		var form = $('<form id="image-file-upload" style="display: block; width: 1px; height: 0px; overflow: hidden;" method="post" enctype="multipart/form-data"><input type="file" name="file" value="" /><input type="submit" value="업로드" /></form>');
		var inputnm = $(this).data("inputnm");
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
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'instrctrNo' : document.instrctrVO.instrctrNo.value ,
							'comcd': document.instrctrVO.comcd.value
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
					console.log(data);

					$('body').hideLoading();

					if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	$('#'+inputnm).val(data.originFileName);
			        	$('#'+inputid).val(data.atchFileId);

		        		alert("이미지가 변경되었습니다.");
		        		var img = document.createElement("img");
						img.setAttribute("src", data.realFilePath);
						img.style.height = "50px";
						img.style.cursor = "pointer";

						img.onclick = function(){
							window.open(data.realFilePath);
						}

						$('#'+inputid+'Preview').html("");
						$('#'+inputid+'Preview').append(img);
			        }

				}
			});
		}).click();
	});
 </script>
</c:if>
</body>