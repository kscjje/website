<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	<script type="text/javascript" src="<c:url value="/webadm/validator"/>"></script>
	<script type="text/javascript" src="<c:url value="/webadm/resources/js/EgovMultiFile.js"/>"></script>
	<validator:javascript formName="surveyMstVO" staticJavascript="false" xhtml="true" cdata="false"/>

	<script type="text/javascript">
	$(document).ready(function() {

		$("#btnSave").click(function() {
			var form = document.surveyMstVO;
			if (!validateSurveyMstVO(form)) return;

			var filePath = $.trim($('[type="file"]').val());
			if (!$("#orginFileName").html() && !filePath) {
				alert("파일을 선택해 주세요.");
				return;
			}

			var msg = "등록하시겠습니까?";
			if ($.trim($("#qestnarId").val()) != "0") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				$("#surveyMstVO").submit();
			}

		});

		$('input.calendar').datepicker({
	    	/* autoclose: true,
	        calendarWeeks: false,
	        todayHighlight: true,
	        language: "kr", */
	        minDate: 0,
	    	dateFormat: "yy-mm-dd"
	    });

		<%/* $("#qestnarName").val("주제명");
		$("#qestnarGuide").val("설문안내설명설문안내설명설문안내설명설문안내설명설문안내설명설문안내설명설문안내설명");
		$("#orgNo").val(2).prop("selected", true);
		$("input[type=radio][value='Y']").prop("checked", true);
		$("#qestnarDate").val("2021-11-07");
		$("#qestnarGbn option:eq(1)").prop("selected", true);
		$("#qestnarTargetEdc").val("강좌를 넣어야하나?");
		$("#qestnarName").val("주제명"); */%>
	})
	</script>
</head>
<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>설문조사 주제 등록</h1>
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

       <div class="row">
          <div class="col-12">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->
              <div class="card-body  table-responsive p-0">
<form:form commandName="surveyMstVO" name="surveyMstVO" action="./saveThema" method="post" enctype="multipart/form-data">
<form:hidden path="comcd"/>
<form:hidden path="qestnarId"/>
<form:hidden path="qestnarImgfilnb"/>
	                <table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
							<tr>
								<th>설문 주제명<span class="pilsu">*</span></th>
								<td class="left" colspan="3">
									<form:input path="qestnarName" maxlength="200" cssClass="form-control"/>
									<div><form:errors path="qestnarName" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>이용기관(유형)<span class="pilsu">*</span></th>
								<td class="left">
									<hisco:OrgList defaultNo="${surveyMstVO.orgNo}" id="orgNo"/>
									<div><form:errors path="orgNo" cssClass="error" /></div>
								</td>
								<th>설문주제등록일자</th>
								<td class="left">
			                      	<form:input path="qestnarDate" maxlength="10" cssClass="form-control calendar" style="width:140px;float:left" autocomplete="off" readonly="true"/>
				                    <div><form:errors path="qestnarDate" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>설문대상<span class="pilsu">*</span></th>
								<td class="left">
									<form:select path="qestnarTarget" title="" class="form-control">
			                    		<option value="">::::선택::::</option>
			                    	</form:select>
									<div><form:errors path="qestnarTarget" cssClass="error" /></div>
								</td>
								<th>구분</th>
								<td class="left">
									<select id="qestnarGbn" name="qestnarGbn" class="form-control">
				              			<option value="">::::선택::::</option>
				              			${hisco:makeOptions("SM_QESTNAR_GBN", "EMPTY", surveyMstVO.qestnarGbn)}
			              			</select>
				                    <div><form:errors path="qestnarGbn" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>설문관련 [강좌]<span class="pilsu">*</span></th>
								<td class="left" colspan="3">
									<form:input path="qestnarTargetEdc" cssClass="form-control"/>
									<div><form:errors path="qestnarTargetEdc" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>설문 안내설명<span class="pilsu">*</span></th>
								<td class="left" colspan="3">
									<form:textarea path="qestnarGuide" cssClass="form-control" style="height:150px" maxlength="2000" />
									<div><form:errors path="qestnarGuide" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>사용여부<span class="pilsu">*</span></th>
								<td class="left">
									<div class="form-group clearfix">
			                      		<div class="icheck-primary d-inline">
											<form:radiobutton path="useYn" value="N"/>
					                        <label for="useYn1">미사용</label>
					                    </div>
					                    <div class="icheck-primary d-inline">
					                        <form:radiobutton path="useYn" value="Y"/>
					                        <label for="useYn2">사용</label>
					                    </div>
					                </div>
									<div><form:errors path="useYn" cssClass="error" /></div>
								</td>
								<th>이미지 첨부<span class="pilsu">*</span></th>
								<td class="left">
									<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control"/>
			   						<div><form:errors path="qestnarImgfilnb" cssClass="error" /></div>
			   						<div><font color='red'>※ 권장하는 크기는 540ⅹ470 픽셀입니다.</font></div>
			   						<c:if test="${!empty surveyMstVO.orginFileName }">
			   							<div id="orginFileName">※ 현재 파일 : ${surveyMstVO.orginFileName}</div>
			   						</c:if>
								</td>
							</tr>
							<c:if test="${surveyMstVO.qestnarId > 0}">
							<tr>
								<th>등록자/일시</th>
								<td class="left">
									${surveyMstVO.reguser}/${surveyMstVO.regdate}
								</td>
								<th>수정자/일시</th>
								<td class="left">
									${surveyMstVO.moduser}/${surveyMstVO.moddate}
								</td>
							</tr>
							</c:if>
						</tbody>
					</table>
</form:form>
					<div class="card-footer">
	                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
	                  <button type="button" id="btnSave" class="btn btn-info float-right">저장</button>
	                </div>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
        </div>

      </div> <!-- /.container-fluid -->
	</section>
</body>