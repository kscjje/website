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
<body>
<form:form commandName="orgOptinfoVO" name="orgOptinfoVO" action="./orgInfoRegistSave" class="form-horizontal" method="post" >
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
		                <div class="form-group row">
		                    <label for="smsProvider" class="col-sm-2 col-form-label">회원연령<br/>계산방법</label>
		                    <div class="col-sm-10" >
		                    	 <form:select path="ageAppgbn" class="form-control">
	    							<form:options items="${ageList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
		                    	 </form:select>
		                    </div>
		                </div>
		                <!--
		                <div class="form-group row">
		                    <label for="comTopctgcd" class="col-sm-2 col-form-label">수납금액-절상절사</label>
		                    <div class="col-sm-10" >
		                    	<div class="input-group">
			                      <div class="custom-file col-sm-6">
			                         <form:select path="paymentUpdownUnit" class="form-control">
		    							<form:options items="${updownUnitList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
			                    	 </form:select>
			                      </div>
			                      <div class="input-group-append">
			                        <form:select path="paymentUpdown" class="form-control">
		    							<form:options items="${updownList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
			                    	 </form:select>
			                      </div>
			                    </div>
			                   </div>
		                  </div>

		                 <div class="form-group row">
		                    <label for="refundUpdownUnit" class="col-sm-2 col-form-label">환불금액-절상절사</label>
		                    <div class="col-sm-10" >
		                    	<div class="input-group">
			                      <div class="custom-file col-sm-6">
			                         <form:select path="refundUpdownUnit" class="form-control">
		    							<form:options items="${updownUnitList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
			                    	 </form:select>
			                      </div>
			                      <div class="input-group-append">
			                        <form:select path="refundUpdown" class="form-control">
		    							<form:options items="${updownList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
			                    	 </form:select>
			                      </div>
			                    </div>
			                   </div>
		                  </div>
		                   -->

						  <div class="form-group row">
			                    <label for="linkUrl" class="col-sm-2 col-form-label">직인 이미지</label>
			                    <div class="col-sm-10">
			                    	<div class="input-group mb-3">
			                    		<form:hidden path="orgStampimgFfinnb" />
					                  	<input type="text" class="form-control" readonly id="orgStampimgFfinnbNm" value="<c:out value="${orgOptinfoVO.stampFileOrigin}"/>">
					                  	<div class="input-group-append">
					                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="orgStampimgFfinnbNm" data-inputid="orgStampimgFfinnb">파일변경</span>
					                  	</div>
					                </div>
					                <div id="orgStampimgFfinnbPreview" class="preview"><c:if test="${!empty orgOptinfoVO.stampFileName}"><img src="<tags:UploadFileUrl filePath="${orgOptinfoVO.stampFilePath}" fileName="${orgOptinfoVO.stampFileName }" originName="${orgOptinfoVO.stampFileOrigin }"/>" style="height:50px;cursor:pointer"/></c:if></div>
			   						<div><font color='red'>※ 권장하는 크기는 540ⅹ470 픽셀입니다.</font></div>
			                    </div>
		                 </div>

			              <div class="form-group row">
			                    <label for="webPaymentMethod" class="col-sm-2 col-form-label"> 온라인 결제방법</label>
			                    <div class="col-sm-10" style="padding-top:5px">
									<div class="form-check" style="display:inline-block">
										<form:checkbox path="webPaymentMethod" cssClass="form-check-input" value="0"/>
			                           <label class="form-check-label" for="webPaymentMethod1">결제 미적용</label>
			                        </div>
			                    </div>
			                </div>

				              <div class="form-group row">
			                    <label for="smsUseyn" class="col-sm-2 col-form-label"> 온라인 결제 수단</label>
			                    <div class="col-sm-10" style="padding-top:5px">
									<div class="form-check" style="display:inline-block">
										<form:checkbox path="webPaymentMethod" cssClass="form-check-input" value="1"/>
			                           <label class="form-check-label" for="webPaymentMethod2">카드결제</label>
			                        </div>
			                        <div class="form-check"  style="display:inline-block">
			                          	<form:checkbox path="webPaymentMethod" cssClass="form-check-input" value="2"/>
			                           	<label class="form-check-label" for="webPaymentMethod3">실시간 계좌이체</label>
			                        </div>
			                        <div class="form-check"  style="display:inline-block">
			                          	<form:checkbox path="webPaymentMethod" cssClass="form-check-input" value="3"/>
			                           	<label class="form-check-label" for="webPaymentMethod4">가상계좌(무통장입금)</label>
			                        </div>
			                    </div>
			                </div>

		              	<div class="form-group row">
		                    <label for="webpaymentid" class="col-sm-2 col-form-label">온라인결제-상점 ID</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="webpaymentid" maxlength="20"/>
		   						<div><form:errors path="webpaymentid" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
		                    <label for="webpaymentpwd" class="col-sm-2 col-form-label">온라인결제- 패스워드</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="webpaymentpwd" maxlength="100"/>
		   						<div><form:errors path="webpaymentpwd" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
		                    <label for="webpayapiKey" class="col-sm-2 col-form-label">온라인결제-mertKey</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="webpayapiKey" maxlength="100"/>
		   						<div><form:errors path="webpayapiKey" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
		                    <label for="areaPayId" class="col-sm-2 col-form-label">지역화폐-연동 ID</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="areaPayId" maxlength="20"/>
		   						<div><form:errors path="areaPayId" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
		                    <label for="arePayPwd" class="col-sm-2 col-form-label">지역화폐-연동 PW</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="areaPayPwd" maxlength="100"/>
		   						<div><form:errors path="areaPayPwd" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
		                    <label for="arePayapiKey" class="col-sm-2 col-form-label">지역화폐-연동 Key</label>
		                    <div class="col-sm-10">
		                    	<form:input class="form-control" path="areaPayapiKey" maxlength="100"/>
		   						<div><form:errors path="areaPayapiKey" cssClass="error" /></div>
		                    </div>
		              	</div>
		              	<div class="form-group row">
	                    <label for="arePayapiKey" class="col-sm-2 col-form-label">교육신청알림 담당자연락처</label>
	                    <div class="col-sm-10">
	                    	<form:input class="form-control numberOnly" path="edcPrnmAlarmHpno" maxlength="14" placeHolder="- 제외하고 숫자만 입력해 주세요"/>
	   						<div><form:errors path="edcPrnmAlarmHpno" cssClass="error" /></div>
	                    </div>
	              	</div>
	              	<div class="form-group row">
	                    <label for="orgPayBanknm" class="col-sm-2 col-form-label">무통장 입금은행명</label>
	                    <div class="col-sm-10">
	                    	<form:input class="form-control" path="orgPayBanknm" maxlength="100"/>
	   						<div><form:errors path="orgPayBanknm" cssClass="error" /></div>
	                    </div>
	              	</div>
	              	<div class="form-group row">
	                    <label for="orgPayAccno" class="col-sm-2 col-form-label">무통장 입금계좌번호</label>
	                    <div class="col-sm-10">
	                    	<form:input class="form-control" path="orgPayAccno" maxlength="100"/>
	   						<div><form:errors path="orgPayAccno" cssClass="error" /></div>
	                    </div>
	              	</div>
	              	<div class="form-group row">
	                    <label for="orgPayAccname" class="col-sm-2 col-form-label">무통장 입금예금주명</label>
	                    <div class="col-sm-10">
	                    	<form:input class="form-control" path="orgPayAccname" maxlength="100"/>
	   						<div><form:errors path="orgPayAccname" cssClass="error" /></div>
	                    </div>
	              	</div>

	               </div>
	                <div>
						<a id="orgInfoBtn" class="btn btn-primary btn-sm form-control <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_optinfo_save()">운영환경 설정 저장</a>
	                </div>
</form:form>
<script>
 	var webPaymentMethod = "${orgOptinfoVO.webPaymentMethod}";
 	var arrList = webPaymentMethod.split(",");
 	var obj = document.getElementById('webPaymentMethod1');

 	if(arrList.length > 0){
 		for(var i=0 ; i<arrList.length ; i++){
 			var no = $.trim(arrList[i]);
 			if(no != ""){
 				$('input[name=webPaymentMethod]').eq(no).prop("checked" , true);
 			}
 		}
 	}

 	if($(obj).prop("checked")){
 		document.getElementById('webPaymentMethod2').disabled = true;
		document.getElementById('webPaymentMethod3').disabled = true;
		document.getElementById('webPaymentMethod4').disabled = true;
 	}


	$(obj).click(function(){
		if($(this).prop("checked")){
			document.getElementById('webPaymentMethod2').disabled = true;
			document.getElementById('webPaymentMethod3').disabled = true;
			document.getElementById('webPaymentMethod4').disabled = true;
		}else{
			document.getElementById('webPaymentMethod2').disabled = false;
			document.getElementById('webPaymentMethod3').disabled = false;
			document.getElementById('webPaymentMethod4').disabled = false;
		}
	});

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
				url : './orgInfoFileUpload.json',
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'orgNo' : document.orgOptinfoVO.orgNo.value ,
							'comcd': document.orgOptinfoVO.comcd.value
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

  function fn_optinfo_save(){
		if(confirm("수정하시겠습니까?")){
			$.ajax({
			      url: './orgInfoOptinfoSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#orgOptinfoVO').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_optinfo_load();
				        	alert(data.result.msg);
				        }
			      }
			});
		}
	}
  
  
$(document).ready(function(){
	$(".numberOnly").on("keyup", function() {
	    $(this).val($(this).val().replace(/[^0-9]/g,""));
	 });
});
 </script>
 </body>