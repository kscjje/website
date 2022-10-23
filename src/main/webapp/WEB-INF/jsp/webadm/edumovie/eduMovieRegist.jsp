<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<script>
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {
		var form = document.EduMovieVO;

		if (form.orgNo.value == "") {
			alert("기관을 선택해 주세요.");
			form.orgNo.focus();
			return;
		}

		if (form.title.value == "") {
			alert("프로그램명을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}
		
		if (form.url.value == "") {
			alert("동영상 URL을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}
		
		if (form.playTime.value == "") {
			alert("동영상 시간을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}
		
		if (form.completionPermitTime.value == "") {
			alert("수료허용시간을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}

		if(form.orgMovieLecNo.value == 0){
			if (confirm("새로운 교육프로그램을 등록하시겠습니까?")) {
				$("#EduMovieVO").attr("action", "./eduMovieRegistSave");
				form.submit();
			}
		} else{
			if (confirm("동영상강좌 정보를 수정하시겠습니까?")) {
				$("#EduMovieVO").attr("action", "./eduMovieUpdtSave");
				form.submit();
			}
		}

		
	}

	function fn_movie_delete(){
		var form = document.EduMovieVO;
		
		if(confirm("신청자가 있을 경우 삭제가 불가능합니다.\n삭제하시겠습니까?")){
			$("#EduMovieVO").attr("action", "./eduMovieDelete");
			form.submit();
		}
	}

	function fn_program_intro(){
		$.ajax({
		      url: './edcProgramIntroSave.json',
		      type : "POST",
		      data : $('#edcProgramVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}

	function fn_program_notice(){
		$.ajax({
		      url: './edcProgramNoticeSave.json',
		      type : "POST",
		      data : $('#edcProgramVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}
	
	function fn_program_rsvn(){
		var msg = "";
		if($('#edcRsvnsetSeq').val() == ""){
			msg = "신규 모집정보를 생성하시겠습니까?";
		}

		if(!fn_rsvn_set_chk()){
			return;
		}else{
			$('body').showLoading();

			if(msg == "" || confirm(msg)){

				var data = $('#edcProgramVO').serialize();
				if ($('input[name=edcCapaDvdyn]').prop("disabled") || !$('input[name=edcCapaDvdyn]').prop("checked")) {
					data += '&edcCapaDvdyn=N';
				}

				$.ajax({
				      url: './edcProgramRsvnSave.json',
				      type : "POST",
				      data : data,
				      success: function(data) {

				    	  $('body').hideLoading();

				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	fn_change_rsvn($('#edcRsvnsetSeq').val());
					        	 alert(data.result.msg);
					        }
				      },
				      error : function(res){
				    	  	__ajaxErrorCallback__(res, function(){
								$('body').hideLoading();
								alert("서버 에러");
				    	  	});
				      }
				});
			}
		}
	}

	function fn_find_category(){
		$.ajax({
		      url: '../comctgr/comCtgrListAjax.json',
		      type : "GET",
		      dataType : "json",
		      cache : false,
		      success: function(data) {
		        console.log(data);

		        var LIST = data.result;
		        $('#CTG_TABLE').find("tbody").html("");

		        if(LIST){
		        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
		        }
		      }
		    });
	}

	$(document).ready(function(){
		

	});

	function fn_change_rsvn(val){
		$('#custom-tabs-one-profile').showLoading();
		$.ajax({
		      url: './edcProgramDetailAjax',
		      type : "GET",
		      data : {'comcd': $('#comcd').val() , 'edcPrgmNo':$('#edcPrgmNo').val() , 'edcRsvnsetSeq':val},
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#custom-tabs-one-profile').html(data);
						$('#custom-tabs-one-profile').hideLoading();
		    	  	}
		      },
		      error : function(res){
		    	  	__ajaxErrorCallback__(res, function(){
						$('#custom-tabs-one-profile').hideLoading();
						alert("서버 에러");
		    	  	});
		      }
		});
	}

</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>교육프로그램 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
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
<form:form commandName="EduMovieVO" name="EduMovieVO" class="form-horizontal" method="post" enctype="multipart/form-data">
<form:hidden path="comcd" />
<form:hidden path="reguser" />
<form:hidden path="orgMovieLecNo" />
       <div class="row">
          <div class="col-9">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">기본 정보</h3>
              </div>

              <!-- /.card-header -->
              <div class="card-body ">
                  <div class="form-group row">
	                    <div class="col-sm-12">
							<label for="useYn" class="col-sm-2 col-form-label"><span class="pilsu">*</span>사용여부</label>
	                        <div class="form-check" style="display:inline-block;margin-left:10px">
	                        <c:choose>
								<c:when test="${eduMovieVO.orgMovieLecNo >0 }">
									<input type="radio" id="useYn1" name="useYn" cssClass="form-check-input" value="Y" <c:if test="${eduMovieVO.useYn == 'Y'}">checked</c:if> />
								</c:when>
								<c:otherwise>
									<input type="radio" id="useYn1" name="useYn" cssClass="form-check-input" value="Y" checked />
								</c:otherwise>
							</c:choose>
	                          
	                          <label for="useYn1" class="form-check-label">사용</label>
	                        </div>
	                        <div class="form-check" style="display:inline-block;margin-left:20px">
	                          <input type="radio" id="useYn2" name="useYn" cssClass="form-check-input" value="N" <c:if test="${eduMovieVO.useYn == 'N'}">checked</c:if>/>
	                          <label for="useYn2" class="form-check-label">사용안함</label>
	                        </div>
 	                    </div>
	               </div>

                  <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 프로그램 운영기관</label>
	                    <div class="col-sm-10">
	                    	<c:choose>
								<c:when test="${eduMovieVO.orgMovieLecNo >0 }">
									<form:hidden path="orgNo" value="${eduMovieVO.orgNo }"/>
									${eduMovieVO.orgNm }
								</c:when>
								<c:otherwise>
									<hisco:OrgList defaultNo="${eduMovieVO.orgNo}" id="orgNo" />
								</c:otherwise>
							</c:choose>
	   						<div><form:errors path="orgNo" cssClass="error" /></div>
	                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 프로그램명</label>
                    <div class="col-sm-10">
                    	<form:input path="title" title="프로그램명" class="form-control " maxlength="100" value="${eduMovieVO.title}"/>
   						<div><form:errors path="title" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
	                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 동영상 URL</label>
	                    <div class="col-sm-10">
                    	<form:input path="url" title="동영상 URL" class="form-control " maxlength="100" value="${eduMovieVO.url}"/>
   						<div><form:errors path="url" cssClass="error" /></div>
                    </div>
	              </div>
					<div class="form-group row">
						<label for="linkUrl" class="col-sm-2 col-form-label">썸네일 이미지</label>
						<div class="col-sm-10">
							<c:choose>
								<c:when test="${eduMovieVO.orgMovieLecNo >0 }">
								<div class="input-group">
									<input type="file" name="file_1" id="egovfile_0" title="썸네일 이미지" class="form-control" style="width:80px;"/>
									<input type="text" class="form-control" readonly id="thumbImg" value="${eduMovieVO.thumbOrgimg}">
								</div>
								<div id="edcImgFileidPreview" class="preview"><c:if test="${!empty eduMovieVO.thumbImg}"><img src="<tags:UploadFileUrl filePath="${eduMovieVO.thumbPath}" fileName="${eduMovieVO.thumbImg }" originName="${eduMovieVO.thumbOrgimg }"/>" style="height:50px;cursor:pointer"/></c:if></div>
 								</c:when>
								<c:otherwise>
									<input type="file" name="file_1" id="egovfile_0" title="썸네일 이미지" class="form-control"/>
								</c:otherwise>
							</c:choose>
							<div><font color='red'>※ 권장하는 크기는 470ⅹ620 픽셀입니다.</font></div>
						</div>
					</div>
	              <div class="form-group row">
	                    <label for="playTime" class="col-sm-2 col-form-label"><span class="pilsu">*</span>동영상시간</label>
	                    <div class="col-sm-10">
	                    	<form:input path="playTime" title="동영상시간" class="form-control " maxlength="100" value="${eduMovieVO.playTime}"/>
	   						<div><form:errors path="playTime" cssClass="error" /></div>
	                    </div>
	              </div>
	              <div class="form-group row">
	                    <label for="completionPermitTime" class="col-sm-2 col-form-label"><span class="pilsu">*</span>수료허용시간</label>
	                    <div class="col-sm-10">
	                    	<form:input path="completionPermitTime" title="수료허용시간" class="form-control " maxlength="100" value="${eduMovieVO.completionPermitTime}"/>
	   						<div><form:errors path="completionPermitTime" cssClass="error" /></div>
	                    </div>
	              </div>
	              
	            
        <!-- /.card-footer -->
          </div>
	          <div class="card-footer">
				<a href="./eduMovieList${searchQuery }" class="btn btn-default">목록</a>
				 <c:choose>
					<c:when test="${eduMovieVO.orgMovieLecNo >0 }">
						<c:choose>
	                		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
	                			 <button type="button" onclick="fn_movie_delete()"  class="btn btn-danger float-right">삭제</button>
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
</form:form>
     </div>

     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">분야 선택하기</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <div class="modal fade" id="modal-teacher" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">강사 선택</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <div style="padding-bottom:10px;">
              		강사명 검색  : <input type="text" name="searchTeacher" id="searchTeacher" value="" />
              </div>



              <table class="table table-sm table-bordered" id="TCR_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>



</section>

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
				url : './edcProgramUpload.json',
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'edcPrgmNo' : document.edcProgramVO.edcPrgmNo.value ,
							'comcd': document.edcProgramVO.comcd.value
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

			        	if(inputid == "edcPlanFileid"){
			        		//강의계획서
			        		alert("강의계획서가 변경되었습니다.");
			        		$('#'+inputid+'Preview').html("<a href='"+data.realFilePath+"'><i class=\"fas fa-download\"></i> 다운로드</a>");
			        	}else{
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
				}
			});
		}).click();
	});
 </script>
</body>