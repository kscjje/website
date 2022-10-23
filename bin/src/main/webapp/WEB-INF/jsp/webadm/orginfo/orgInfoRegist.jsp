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
<head>
<script type="text/javascript" src="<c:url value="${adminRoot}/resources/se/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="${adminRoot}/validator"/>"></script>
<validator:javascript formName="orgInfoVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script>
	var orgTypeList; // 기관 유형 담는 객체
	var oEditors = [];
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {
		var form = document.orgInfoVO;
		if (!validateOrgInfoVO(form)) {
			return ;

		} else {
			var msg = "등록하시겠습니까?";
			if (confirm(msg)) {
				oEditors.getById["orgContents.orgClassmngGuide"].exec("UPDATE_CONTENTS_FIELD", []);
				oEditors.getById["orgContents.orgRetdcGuide"].exec("UPDATE_CONTENTS_FIELD", []);
				form.submit();
			}
		}
	}

	$(document).ready(function(){
		var obj = document.getElementById('orgOptinfo.webPaymentMethod1');
		$(obj).click(function(){
			if($(this).prop("checked")){
				document.getElementById('orgOptinfo.webPaymentMethod2').disabled = true;
				document.getElementById('orgOptinfo.webPaymentMethod3').disabled = true;
				document.getElementById('orgOptinfo.webPaymentMethod4').disabled = true;
			}else{
				document.getElementById('orgOptinfo.webPaymentMethod2').disabled = false;
				document.getElementById('orgOptinfo.webPaymentMethod3').disabled = false;
				document.getElementById('orgOptinfo.webPaymentMethod4').disabled = false;
			}
		});
		
		$(".numberOnly").on("keyup", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		 });

		$(".guide-link").on("click", function(){
			$("#help-modal-1").modal("show");
		});

		$('.dcReason').click(function(){
			var checked = $(this).prop("checked");
			$(this).parent().parent().find(".dcRate").prop("disabled" , !checked);
		})

		//에디터
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "orgContents.orgClassmngGuide",
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

		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "orgContents.orgRetdcGuide",
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


		$.ajax({
		      url: '../code/SM_ORG_MTYPE/list.json',
		      type : "GET",
		      cache : false,
		      async : true,
		      success: function(data) {
		    	  //목록을 담는다
		    	  orgTypeList = data.list;
		    	  fn_print_orgtype($('#orgLtype').val() , '${orgInfoVO.orgMtype}');
		      },
		      error : function(res){
		    	  __ajaxErrorCallback__(res, "기관유형 가져오기 오류\n" + res.responseText);
		      }

		});

		$('#orgLtype').change(function(){
			fn_print_orgtype($(this).val() , '');
		});
	});

	function fn_print_orgtype(parentCd , defaultVal){
		$('#orgMtype').html('');

		var cnt=0;
		for(var i = 0 ; i<orgTypeList.length ; i++){
			var item = orgTypeList[i];
			if(item.item1 == parentCd){
				if($('#orgMtype').html() == ""){
					$('#orgMtype').append("<option value=''>선택</option>");
				}

				var selected = "";
				if(item.cd == defaultVal) selected = " selected";

				$('#orgMtype').append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
				cnt++;
			}

		}
		if(cnt < 1){
			$('#orgMtype').append("<option value=''>없음</option>");
		}
	}
</script>
</head>
<body>
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
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>기관 정보 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
<form:form commandName="orgInfoVO" name="orgInfoVO" action="./orgInfoRegistSave" class="form-horizontal" method="post" enctype="multipart/form-data">
<form:hidden path="comcd" />
       <div class="row">
          <div class="col-md-6">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">기본 정보</h3>
              </div>

              <!-- /.card-header -->
              <div class="card-body ">
                  <div class="form-group row">
	                    <label for="orgNm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관명</label>
	                    <div class="col-sm-10">
	                    	<form:input path="orgNm" title="기관명" class="form-control " maxlength="50" />
	   						<div><form:errors path="orgNm" cssClass="error" /></div>
	                    </div>
                  </div>
                  <!--
                  <div class="form-group row">
                    <label for="areaCd" class="col-sm-2 col-form-label">상위기관 선택</label>
                    <div class="col-sm-10">
                    	<form:select path="parentOrgNo" class="form-control">
                    	 	<option value="0">:::: 없음 ::::</option>
<c:forEach items="${upOrgList}" var="item" varStatus="status">
                    	 	<form:option value="${item.orgNo }" label="${item.orgNm }" />
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="areaCd" cssClass="error" /></div>
                    </div>
                  </div>
                   -->
                  <div class="form-group row">
                    <label for="areaCd" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 지역</label>
                    <div class="col-sm-10">
                    	<form:select path="areaCd" class="form-control">
                    	 	<option value="">::::선택::::</option>

<c:forEach items="${areaList}" var="item" varStatus="status">
                    	 	<form:option value="${item.areaCd }" label="${item.areaNm }" />
     <c:forEach items="${item.subAreaList}" var="item2" varStatus="status">
     						<form:option value="${item2.areaCd }" label="${item.areaNm } > ${item2.areaNm }" />
     </c:forEach>
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="areaCd" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgKind" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관분류</label>
                    <div class="col-sm-10">
                    	<form:select path="orgKind" class="form-control">
                    		<form:option value="1001" label="산하기관" />
                    		<form:option value="2001" label="유관기관" />
                    	</form:select>
   						<div><form:errors path="orgKind" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgLtype" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관유형(대)</label>
                    <div class="col-sm-10">
                    	<form:select path="orgLtype" class="form-control">
                    		<form:options items="${typeList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
                    	</form:select>
   						<div><form:errors path="orgLtype" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgMtype" class="col-sm-2 col-form-label">기관유형(중)</label>
                    <div class="col-sm-10">
                    <!-- ajax 으로 호출함 -->
                    	<form:select path="orgMtype" class="form-control">
                    	</form:select>
                    	<div>※ 유관기관만 적용하세요</div>
   						<div><form:errors path="orgMtype" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 대표전화</label>
                    <div class="col-sm-10">
                    	<form:input path="orgTel" title="대표전화" class="form-control " maxlength="14" />
   						<div><form:errors path="orgTel" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 주소</label>
                    <div class="col-sm-10">
                    	<form:input path="addr" title="주소" class="form-control " maxlength="250" />
   						<div><form:errors path="addr" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">E-MAIL</label>
                    <div class="col-sm-10">
                    	<form:input path="email" title="이메일" class="form-control " maxlength="50" />
   						<div><form:errors path="email" cssClass="error" /></div>
                    </div>
                  </div>
                   <div class="form-group row">
                    <label for="fax" class="col-sm-2 col-form-label">팩스번호</label>
                    <div class="col-sm-10">
                    	<form:input path="fax" title="팩스번호" class="form-control " maxlength="14" />
   						<div><form:errors path="fax" cssClass="error" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">대표자명</label>
                    <div class="col-sm-10">
                    	<form:input path="bossNm" title="대표자명" class="form-control " maxlength="15" />
   						<div><form:errors path="bossNm" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">사업자번호</label>
                    <div class="col-sm-10">
                    	<form:input path="bizNo" title="사업자번호" class="form-control " maxlength="20" />
   						<div><form:errors path="bizNo" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="uptae" class="col-sm-2 col-form-label">업태</label>
                    <div class="col-sm-10">
                    	<form:input path="uptae" title="업태" class="form-control " maxlength="20" />
   						<div><form:errors path="uptae" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="jongmok" class="col-sm-2 col-form-label">종목</label>
                    <div class="col-sm-10">
                    	<form:input path="jongmok" title="종목" class="form-control " maxlength="20" />
   						<div><form:errors path="jongmok" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgUrl" class="col-sm-2 col-form-label">홈페이지 URL</label>
                    <div class="col-sm-10">
                    	<form:input path="orgUrl" title="홈페이지 URL" class="form-control " maxlength="200" />
   						<div><form:errors path="orgUrl" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="charger" class="col-sm-2 col-form-label">담당자명</label>
                    <div class="col-sm-10">
                    	<form:input path="charger" title="담당자명" class="form-control " maxlength="20" />
   						<div><form:errors path="charger" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="chargertel" class="col-sm-2 col-form-label">담당자전화</label>
                    <div class="col-sm-10">
                    	<form:input path="chargertel" title="담당자전화" class="form-control " maxlength="14" />
   						<div><form:errors path="chargertel" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgCntsactiveyn" class="col-sm-2 col-form-label">기관소개노출</label>
                    <div class="col-sm-10 pt-2">
                    	<label><form:radiobutton path="orgCntsactiveyn" value="Y" /> 노출</label>
   						<label><form:radiobutton path="orgCntsactiveyn" value="N" /> 비노출</label>
                    </div>

                  </div>
                  <div class="form-group row">
                    <label for="sortOrder" class="col-sm-2 col-form-label">정렬순서 <i class="far fa-question-circle guide-link" style="cursor:pointer" title="정렬순서 도움말" help-id="001"></i></label>
                    <div class="col-sm-10">
                    	<form:input path="sortOrder" title="정렬순서" class="form-control " maxlength="5" />
   						<div><form:errors path="sortOrder" cssClass="error" /></div>
   						<div style="color:red">※ 숫자가 낮을 수록 상단에 정렬됩니다.</div>
                    </div>
                  </div>

                </div>
                <!-- /.card-body -->

            <!-- /.card -->
          </div>

        </div>
         <div class="col-md-6">
         	<div class="card card-primary card-outline card-outline-tabs">
              <div class="card-header p-0 border-bottom-0">
                <ul class="nav nav-tabs" id="tablist">
                  <li class="nav-item">
                    <a class="nav-link active" href="#ORG_INFO_CONTENTS_TAB" role="tab" data-toggle="pill" aria-controls="ORG_INFO_CONTENTS_TAB" aria-selected="true" >기관소개</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link"   href="#ORG_INFO_OPT_TAB" role="tab" data-toggle="pill" aria-controls="ORG_INFO_OPT_TAB" aria-selected="false">기관 운영환경 설정</a>
                  </li>
                   <li class="nav-item">
                    <a class="nav-link"   href="#ORG_INFO_DC_TAB" role="tab" data-toggle="pill" aria-controls="ORG_INFO_DC_TAB" aria-selected="false">수강료 감면기준 설정</a>
                  </li>
                </ul>
             </div>
              <!-- /.card-header -->
                <div class="card-body ">
                	<div class="tab-content" id="custom-tabs-one-tabContent">
                		<div class="tab-pane fade  active show" id="ORG_INFO_CONTENTS_TAB" role="tabpanel">
							<div class="form-group row" style="color:red">
								 ※ 기관소개정보는 평생학습포털 기관별 소개정보 컨텐츠로 노출되는 입력항목입니다.
							</div>

							<div class="form-group row">
			                    <label for="orgNm"> 기관 소개</label>
			                    <div class="col-sm-12">
			                    	<form:textarea class="form-control" path="orgContents.orgGuide" style="height:100px"/>
			   						<div><form:errors path="orgContents.orgGuide" cssClass="error" /></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="orgNm"> 환불/감면/취소 안내</label>
			                    <div class="col-sm-12">
			                    	<form:textarea class="form-control" path="orgContents.orgRetdcGuide" style="height:300px"/>
			   						<div><form:errors path="orgContents.orgRetdcGuide" cssClass="error" /></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="linkUrl" class="col-sm-2 col-form-label"> 대표이미지</label>
			                    <div class="col-sm-10">
			                    	<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control" accept="image/*"/>
			   						<div><form:errors path="orgContents.orgFileid" cssClass="error" /></div>
			   						<div><font color='red'>※ 권장하는 크기는 800ⅹ500 픽셀입니다.</font></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="orgNm" class="col-sm-2 col-form-label">네이버지도 URL</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgContents.orgPlaceurl" maxlength="255"/>
			   						<div><form:errors path="orgContents.orgPlaceurl" cssClass="error" /></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="orgNm" class="col-sm-2 col-form-label"> 지하철 안내</label>
			                    <div class="col-sm-10">
			                    	<form:textarea class="form-control" path="orgContents.orgSubwayGuide"/>
			   						<div><form:errors path="orgContents.orgSubwayGuide" cssClass="error" /></div>
			                    </div>
			                </div>
			                <div class="form-group row">
			                    <label for="orgNm" class="col-sm-2 col-form-label"> 버스 안내</label>
			                    <div class="col-sm-10">
			                    	<form:textarea class="form-control" path="orgContents.orgBusGuide"/>
			   						<div><form:errors path="orgContents.orgBusGuide" cssClass="error" /></div>
			                    </div>
			                </div>

                  		</div>

                  		<div class="tab-pane fade" id="ORG_INFO_DC_TAB" role="tabpanel">
								<table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="50%">
			                  				<col width="25%">
			                  				<col width="25%">
			                  			</colgroup>
									<thead>
										<tr>
											<th>사유</th>
											<th>할인/할증</th>
											<th>할인/할증률(%)</th>
										</tr>
									</thead>
									<tbody>
<c:forEach items="${orgInfoVO.orgDcList}" var="item" varStatus="status">
							         <tr>
											<td>
												<form:checkbox path="orgDcList[${status.index }].dcYn" value="${item.dcReasonCd }" class="dcReason"/>
												<label for="orgDcList${status.index }.dcYn1" class="form-check-label">&nbsp;<c:out value="${item.dcNm }"/></label>
											</td>
											<td>
												<c:out value="${item.dcType eq '10'?'할인':'할증' }"/>
											</td>
											<td><form:input path="orgDcList[${status.index }].dcRate" style="width:70px" disabled="true" class="dcRate"/> %</td>
									</tr>
</c:forEach>

		                        	</tbody>
		                        </table>
                  		</div>

                  		<div class="tab-pane fade" id="ORG_INFO_OPT_TAB" role="tabpanel">
			                <div class="form-group row">
			                    <label for="smsProvider" class="col-sm-2 col-form-label">회원연령<br/>계산방법</label>
			                    <div class="col-sm-10" >
			                    	 <form:select path="orgOptinfo.ageAppgbn" class="form-control">
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
				                         <form:select path="orgOptinfo.paymentUpdownUnit" class="form-control">
			    							<form:options items="${updownUnitList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
				                    	 </form:select>
				                      </div>
				                      <div class="input-group-append">
				                        <form:select path="orgOptinfo.paymentUpdown" class="form-control">
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
				                         <form:select path="orgOptinfo.refundUpdownUnit" class="form-control">
			    							<form:options items="${updownUnitList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
				                    	 </form:select>
				                      </div>
				                      <div class="input-group-append">
				                        <form:select path="orgOptinfo.refundUpdown" class="form-control">
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
				                    	<input type="file" name="file_4" id="egovfile_32" title="이미지" class="form-control" accept="image/*"/>
				   						<div><form:errors path="orgOptinfo.orgStampimgFfinnb" cssClass="error" /></div>
				   						<div><font color='red'>※ 권장하는 크기는 540ⅹ470 픽셀입니다.</font></div>
				                    </div>
				              </div>

				              <div class="form-group row">
			                    <label for="smsUseyn" class="col-sm-2 col-form-label"> 온라인 결제방법</label>
			                    <div class="col-sm-10" style="padding-top:5px">
									<div class="form-check" style="display:inline-block">
										<form:checkbox path="orgOptinfo.webPaymentMethod" cssClass="form-check-input" value="0"/>
			                           <label class="form-check-label" for="orgOptinfo.webPaymentMethod1">결제 미적용</label>
			                        </div>
			                    </div>
			                </div>

				              <div class="form-group row">
			                    <label for="smsUseyn" class="col-sm-2 col-form-label"> 온라인 결제 수단</label>
			                    <div class="col-sm-10" style="padding-top:5px">
									<div class="form-check" style="display:inline-block">
										<form:checkbox path="orgOptinfo.webPaymentMethod" cssClass="form-check-input" value="1"/>
			                           <label class="form-check-label" for="orgOptinfo.webPaymentMethod2">카드결제</label>
			                        </div>
			                        <div class="form-check"  style="display:inline-block">
			                          	<form:checkbox path="orgOptinfo.webPaymentMethod" cssClass="form-check-input" value="2"/>
			                           	<label class="form-check-label" for="orgOptinfo.webPaymentMethod3">실시간 계좌이체</label>
			                        </div>
			                        <div class="form-check"  style="display:inline-block">
			                          	<form:checkbox path="orgOptinfo.webPaymentMethod" cssClass="form-check-input" value="3"/>
			                           	<label class="form-check-label" for="orgOptinfo.webPaymentMethod4">가상계좌(무통장입금)</label>
			                        </div>
			                    </div>
			                </div>

			              	<div class="form-group row">
			                    <label for="orgNm" class="col-sm-2 col-form-label">온라인결제-상점 ID</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.webpaymentid" maxlength="20"/>
			   						<div><form:errors path="orgOptinfo.webpaymentid" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="orgNm" class="col-sm-2 col-form-label">온라인결제-패스워드</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.webpaymentpwd" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.webpaymentpwd" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="webpayapiKey" class="col-sm-2 col-form-label">온라인결제- mertKey</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.webpayapiKey" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.webpayapiKey" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="areaPayId" class="col-sm-2 col-form-label">지역화폐-연동 ID</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.areaPayId" maxlength="20"/>
			   						<div><form:errors path="orgOptinfo.areaPayId" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="arePayPwd" class="col-sm-2 col-form-label">지역화폐-연동 PW</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.areaPayPwd" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.areaPayPwd" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="arePayapiKey" class="col-sm-2 col-form-label">지역화폐-연동 Key</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.areaPayapiKey" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.areaPayapiKey" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="arePayapiKey" class="col-sm-2 col-form-label">교육신청알림 담당자연락처</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control numberOnly" path="orgOptinfo.edcPrnmAlarmHpno" maxlength="14" placeHolder="- 제외하고 숫자만 입력해 주세요"/>
			   						<div><form:errors path="orgOptinfo.edcPrnmAlarmHpno" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="orgPayBanknm" class="col-sm-2 col-form-label">무통장 입금은행명</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.orgPayBanknm" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.orgPayBanknm" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="orgPayAccno" class="col-sm-2 col-form-label">무통장 입금계좌번호</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.orgPayAccno" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.orgPayAccno" cssClass="error" /></div>
			                    </div>
			              	</div>
			              	<div class="form-group row">
			                    <label for="orgPayAccname" class="col-sm-2 col-form-label">무통장 입금예금주명</label>
			                    <div class="col-sm-10">
			                    	<form:input class="form-control" path="orgOptinfo.orgPayAccname" maxlength="100"/>
			   						<div><form:errors path="orgOptinfo.orgPayAccname" cssClass="error" /></div>
			                    </div>
			              	</div>
			              </div>
			        </div>


              </div>

           </div>
        </div>
      </div>
       <div class="row">
          <div class="col-md-12">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">강좌신청 안내</h3>
              </div>

              <!-- /.card-header -->
              <div class="card-body ">
              		 <div class="form-group row">
              			<div class="col-sm-2 col-form-label"><label>강좌신청 안내</label>
              				<br/>(기관유형1 남양주시청+평생시민대학의 각 대학별만 적용)
              				<br/><br/>*나머지 컨텐츠는 관리자ID로 사용자 화면에서 수정가능
              			</div>
			         	<div class="col-sm-10" >
	                    	<form:textarea path="orgContents.orgClassmngGuide" title="강좌소개" class="form-control" style="height:500px" />
	   						<div><form:errors path="orgContents.orgClassmngGuide" cssClass="error" /></div>
			            </div>
			          </div>
              </div>
               <div class="card-footer">
               	<div class="row">
               			<div class="col-sm-2"></div>
                   		<div class="col-sm-4">
							<a class="btn btn-secondary btn-sm form-control" href="javascript:history.back()">취소</a>
						</div>
						<div class="col-sm-4">
			              	<c:choose>
			              		<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
			              			 <button type="button" onclick="fn_save()"  class="btn btn-info form-control">기관 추가하기</button>
			              		</c:when>
			              		<c:otherwise>
			              			 <button type="button" class="btn btn-info form-control disabled">기관 추가하기</button>
			              		</c:otherwise>
			              	</c:choose>
			            </div>
			            <div class="col-sm-2"></div>
			      </div>

             </div>
              <!-- /.card-footer -->
         </div>
		</div>
     </div>
</form:form>
     </div>

     <div class="modal fade" id="help-modal-1" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">정렬순서  도움말</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <p class="guide-contents" style="line-height:1.8">
~~~~~~[산하기관]~~~~~~~<br/>
남양주시청 : 01~20<br/>
자치회관 : 21~50<br/>
평생시민대학 : 51~60<br/>
마을배움터 : 61<br/>
동네배움터 : 71~99<br/>
~~~~~~[유관기관]~~~~~~~<br/>
도서관 : 101~199<br/>
평생학습관 : 201~299<br/>
관내대학교 : 301~399<br/>
박물관&미술관 : 401~499<br/>
문화예술체육시설 : 501~599<br/>
대상특화교육시설 : 601~699<br/>
복지관 : 701~799<br/>
시민사회단체 : 801~899<br/>
기타시설 : 901~999<br/>
그외시설A : 1001~1099<br/>
그외시설B : 1101~1199<br/>
그외시설C : 1201~129<br/>

              </p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
</section>
</body>