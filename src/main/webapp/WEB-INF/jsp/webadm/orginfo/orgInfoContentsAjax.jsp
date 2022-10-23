<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : orgInfoRegist.jsp
  * @Description : 기관 정보 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<form:form commandName="orgContentsVO" name="orgContentsVO" action="./orgInfoRegistSave" class="form-horizontal" method="post" >
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
					<div class="form-group row" style="color:red">
								 ※ 기관소개정보는 평생학습포털 기관별 소개정보 컨텐츠로 노출되는 입력항목입니다.
							</div>
					<div class="form-group row">
	                    <label for="comnm">기관 소개</label>
	                    <div class="col-sm-12">
	                    	<form:textarea class="form-control" path="orgGuide" style="height:100px"/>
			   						<div><form:errors path="orgGuide" cssClass="error" /></div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="comnm"> 환불/감면/취소 안내</label>
	                    <div class="col-sm-12">
	                    	<form:textarea class="form-control" path="orgRetdcGuide" style="height:300px"/>
	   						<div><form:errors path="orgRetdcGuide" cssClass="error" /></div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="linkUrl" class="col-sm-2 col-form-label">대표이미지</label>
	                    <div class="col-sm-10">
	                    	<div class="input-group mb-3">
	                    		<form:hidden path="orgFileid" />
			                  	<input type="text" class="form-control" readonly id="orgFileNm" value="<c:out value="${orgContentsVO.orgFileOrigin}"/>">
			                  	<div class="input-group-append">
			                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="orgFileNm" data-inputid="orgFileid">파일변경</span>
			                  	</div>
			                </div>
			                <div id="orgFileidPreview" class="preview"><c:if test="${!empty orgContentsVO.orgFileName}"><img src="<tags:UploadFileUrl filePath="${orgContentsVO.orgFilePath}" fileName="${orgContentsVO.orgFileName }" originName="${orgContentsVO.orgFileOrigin }"/>" style="height:50px;cursor:pointer"/></c:if></div>
	   						<div><font color='red'>※ 권장하는 크기는 800ⅹ500 픽셀입니다.</font></div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label">네이버지도 URL</label>
	                    <div class="col-sm-10">
	                    	<form:input class="form-control" path="orgPlaceurl" maxlength="255"/>
	   						<div><form:errors path="orgPlaceurl" cssClass="error" /></div>
	                    </div>
	                </div>

	                 <div class="form-group row">
			                    <label for="comnm" class="col-sm-2 col-form-label"> 지하철 안내</label>
			                    <div class="col-sm-10">
			                    	<form:textarea class="form-control" path="orgSubwayGuide"/>
			   						<div><form:errors path="orgSubwayGuide" cssClass="error" /></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="comnm" class="col-sm-2 col-form-label"> 버스 안내</label>
			                    <div class="col-sm-10">
			                    	<form:textarea class="form-control" path="orgBusGuide"/>
			   						<div><form:errors path="orgBusGuide" cssClass="error" /></div>
			                    </div>
			                </div>
	                <div>
						<a id="orgInfoBtn" class="btn btn-primary btn-sm form-control <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_contents_save()">기관소개 저장</a>
	                </div>
 </form:form>
 <script>
 //에디터
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "orgRetdcGuide",
		sSkinURI: "<c:url value="${adminRoot}/resources/se/SmartEditor2SkinAjax.html"/>",
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});


 //클릭 이벤트
  $('.preview').find("img").click(function(){
	  window.open($(this).attr("src"));
  });
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
				url : './orgInfoFileUpload.json',
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'orgNo' : document.orgContentsVO.orgNo.value ,
							'comcd': document.orgContentsVO.comcd.value
						},
				dataType : 'json',
				error : function(res)
				{
					__ajaxErrorCallback__(res, function(){
						alert('업로드 처리 중 오류가 발생하였습니다.');
						$('body').hideLoading();						
					});
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
			        	alert("이미지가 변경되었습니다.");

			        	$('#'+inputnm).val(data.originFileName);
			        	$('#'+inputid).val(data.atchFileId);

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