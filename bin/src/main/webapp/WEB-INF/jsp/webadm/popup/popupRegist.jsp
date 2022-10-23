<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : popupDetail.jsp
 * @Description : 팝업존 상세 화면
 * @Modification Information
 * @
 * @  수정일               수정자                   수정내용
 * @ -------       --------    ---------------------------
 * @ 2021.06.07    전영석                  최초 생성
 *
 *  @author 전영석
 *  @since 2021.06.07
 *  @version 1.0
 *  @see
 *
 */
%>
<head>
	<script type="text/javascript" src="../validator"></script>

	<validator:javascript formName="popupManageVO" staticJavascript="false" xhtml="true" cdata="false"/>

	<script type="text/javascript">

	/* ********************************************************
	 * 초기화
	 ******************************************************** */
	function fn_egov_init() {

		$('input.calendar').datepicker({
	    	autoclose: true,
	        calendarWeeks: false,
	        todayHighlight: true,
	        language: "kr",
	        dateFormat: "yy-mm-dd"
	    });

	}

	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {

		var varFrom = document.popupManageVO;

		if (!validatePopupManageVO(varFrom)) {
			return false;
		} else {
			var ntceBgndeYYYMMDD = document.getElementById('ntceBgndeYMD').value;
			var ntceEnddeYYYMMDD = document.getElementById('ntceEnddeYMD').value;

			if (ntceBgndeYYYMMDD != '') {
				varFrom.ntceBgnde.value = ntceBgndeYYYMMDD.replaceAll('-','') + fn_egov_SelectBoxValue('ntceBgndeHH') +  fn_egov_SelectBoxValue('ntceBgndeMM');
			} else {
				varFrom.ntceBgnde.value = '';
			}
			if (ntceEnddeYYYMMDD != '') {
				varFrom.ntceEndde.value = ntceEnddeYYYMMDD.replaceAll('-','') + fn_egov_SelectBoxValue('ntceEnddeHH') +  fn_egov_SelectBoxValue('ntceEnddeMM');
			} else {
				varFrom.ntceEndde.value = '';
			}

			var varSortOrder = $("#sortOrder").val();
			if ((varSortOrder === undefined) || (varSortOrder == null) || (varSortOrder == '')) {
				alert("정렬 순서는 필수 항목입니다.");
				$("#sortOrder").focus();
				return false;
			}

			var msg = "등록하시겠습니까?";
			if (varFrom.mode.value == "edit") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				varFrom.submit();
			}

		}
	}
	function fn_date_set() {
		let today = new Date();


		let hours = today.getHours(); // 시
		let minutes = today.getMinutes();  // 분
		let year = today.getFullYear(); // 년도
		let month = today.getMonth() + 1;  // 월

		let date = today.getDate();

		if (month < 10) {
			month ="0"+month;
		}
		if (date < 10) {
			date ="0"+date;
		}
		if (hours < 10) {
			hours ="0"+hours;
		}
		if (minutes < 10) {
			minutes ="0"+minutes;
		}

		$("#ntceBgndeYMD").val(year +"-"+ month +"-"+ date);
		$("#ntceBgndeHH").val(hours);
		$("#ntceBgndeMM").val(minutes);

		today.setDate(today.getDate()+7);

		year = today.getFullYear(); // 년도
		month = today.getMonth() + 1;  // 월
		date = today.getDate();

		if (month < 10) {
			month ="0"+month;
		}
		if (date < 10) {
			date ="0"+date;
		}

		$("#ntceEnddeYMD").val(year +"-"+ month +"-"+ date);
		$("#ntceEnddeHH").val(hours);
		$("#ntceEnddeMM").val(minutes);

	}
	function fn_space_remove(value) {
		$("#sortOrder").val(value.replace(/(\s*)/g, ""));
	}
	$(document).ready(function() {
		if ($("#ntceEnddeYMD").val() == "" ) {
			fn_date_set();
		}
		fn_egov_init();
	});
	</script>
</head>
<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>팝업존 관리</h1>
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

						<form:form commandName="popupManageVO" name="popupManageVO" action="./popupSave" class="form-horizontal" method="post" enctype="multipart/form-data">

							<input type="hidden" name="searchQuery" value="<c:out value="${searchQuery}"/>"/>
							<input type="hidden" name="mode" value="<c:out value="${mode}"/>"/>

							<form:hidden path="popupId"/>
							<form:hidden path="imageFileId"/>
							<form:hidden path="ntceBgnde"/>
							<form:hidden path="ntceEndde"/>

			                <div class="card-body ">
			                	<div class="form-group row">
			                    <label for="bannerNm" class="col-sm-2 col-form-label">* 제목</label>
			                    <div class="col-sm-10">
			                    	<form:input path="popupTitleNm" title="팝업창명" class="form-control " maxlength="255" />
			   						<div><form:errors path="popupTitleNm" cssClass="error" /></div>
			                    </div>
			                  </div>
<% /* DB 컬럼 추가 필요
			                  <div class="form-group row">
			                    <label for="linkUrl" class="col-sm-2 col-form-label">* 팝업위치</label>
			                    <div class="col-sm-10">
		<c:choose>
			<c:when test="${popupGbn==null || fn:length(popupGbn)<1}"><span class="text-red">코드그룹 [WEB_POPUP_GBN]의 코드를 등록해주세요.</span></c:when>
			<c:otherwise>

			                    	<form:select path="popupLocationcd" class="form-control ">
			                    		<option value="">-----선택해주세요------</option>
							<c:forEach items="${popupGbn}" var="gbn" varStatus="status">
										<form:option value="${gbn.cd}">${gbn.cdNm}</form:option>
							</c:forEach>

									</form:select>
			   						<div><form:errors path="popupLocationcd" cssClass="error" /></div>
			</c:otherwise>
		</c:choose>

			                    </div>
			                  </div>
*/ %>
			                  <div class="form-group row">
			                    <label for="linkUrl" class="col-sm-2 col-form-label">링크URL</label>
			                    <div class="col-sm-10">
			                    	<form:input path="fileUrl" title="URL" class="form-control " maxlength="255" />
			   						<div><form:errors path="fileUrl" cssClass="error" /></div>
			                    </div>
			                  </div>
			                  <div class="form-group row">
			                    <label for="reflctAt" class="col-sm-2 col-form-label">링크타겟</label>
			                    <div class="col-sm-10">
			                    	<label><form:radiobutton path="linkTarget" value="_blank" /> 새창</label>
								    &nbsp;&nbsp;&nbsp;
								    <label><form:radiobutton path="linkTarget" value="_self" /> 본창</label>
									<div><form:errors path="linkTarget" cssClass="error" /></div>
			                    </div>
			                  </div>
			                  <div class="form-group row">
			                    <label for="linkUrl" class="col-sm-2 col-form-label"> 이미지 선택</label>
			                    <div class="col-sm-10">
			                    	<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control"/>
			   						<div><form:errors path="imageFileId" cssClass="error" /></div>
			   						<div><font color='red'>※ 권장하는 크기는 788 ⅹ 427 픽셀입니다.</font></div>
			   						<c:if test="${!empty popupManageVO.imageFileNm }">
			   							<div>※ 현재 파일 : <c:out value="${popupManageVO.imageFileNm}" /></div>
										<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index == 0 }">
												<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" width="200"/></c:if>
										</c:forEach>
			   						</c:if>
			                    </div>
			                  </div>
			                  <div class="form-group row">
			                    <label for="ntceBgnde" class="col-sm-2 col-form-label">게시 시작일</label>
			                    <div class="col-sm-10">
			                      	<div style="display:flex">
				                      	<form:input path="ntceBgndeYMD" title="시작일" maxlength="10"  class="form-control calendar" style="width:140px;float:left" autocomplete="off"/>
										<div style="display:none;">
										<form:select path="ntceBgndeHH" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
											<form:options items="${ntceBgndeHH}" itemValue="code" itemLabel="codeNm"  />
										</form:select>

										<form:select path="ntceBgndeMM" cssClass="form-control" style="width:120px;float:left;margin-left:5px" autocomplete="off">
											<form:options items="${ntceBgndeMM}" itemValue="code" itemLabel="codeNm" />
										</form:select>
										</div>
									</div>
									<div>※ 날짜를 지정하지 않는 경우 게시 종료일까지 적용됩니다.</div>
			                    </div>
			                  </div>
			                  <div class="form-group row">
			                    <label for="ntceBgnde" class="col-sm-2 col-form-label">게시 종료일</label>
			                    <div class="col-sm-10">
									<div style="display:flex">

										<form:input path="ntceEnddeYMD" title="종료일" class="form-control calendar"  maxlength="10" style="width:140px;float:left" />
										<div style="display:none;">
										<form:select path="ntceEnddeHH" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
											<form:options items="${ntceBgndeHH}" itemValue="code" itemLabel="codeNm"  />
										</form:select>

										<form:select path="ntceEnddeMM" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
											<form:options items="${ntceBgndeMM}" itemValue="code" itemLabel="codeNm" />
										</form:select>
										</div>
									</div>

									<div class="help-block">※ 날짜를 지정하지 않는 경우 시작일부터  상시 게시 됩니다.</div>
			                    </div>
			                </div>

			                <div class="form-group row" style="display:none;">
			                    <label for="bannerDc" class="col-sm-2 col-form-label">* 팝업창위치</label>
			                    <div class="col-sm-10">

			                    	<div style="float:left">가로 : </div>
			                    	<form:input path="popupWlc" maxlength="10" class="form-control" cssStyle="width:100px; margin-left:5px;float:left" value="0" />
			                    	<div style="float:left;margin-left:15px;">세로 : </div>
			                    	<form:input path="popupHlc" maxlength="10" class="form-control" cssStyle="width:100px; margin-left:5px;float:left" value="0" />

			   						<div><form:errors path="popupWlc" cssClass="error"/></div>
			   						<div><form:errors path="popupHlc" cssClass="error"/></div>

			                    </div>
			                </div>

			                <div class="form-group row" style="display:none;">
			                    <label for="bannerDc" class="col-sm-2 col-form-label">* 팝업창사이즈 </label>
			                    <div class="col-sm-10">
			                    	<div style="float:left">Width: </div>
			                    	 <form:input path="popupWSize" maxlength="10" class="form-control"  cssStyle="width:100px; margin-left:5px;float:left" value="540" />
			                    	<div style="float:left;margin-left:15px;">Height :  </div>

			                    	<form:input path="popupHSize" maxlength="10" class="form-control"  cssStyle="width:100px; margin-left:5px;float:left"  value="470"/>
			   						<div><form:errors path="popupWSize" cssClass="error"/></div>
			   						<div><form:errors path="popupHSize" cssClass="error"/></div>

			                    </div>
			                </div>

			                <div class="form-group row">
			                    <label for="sortOrder" class="col-sm-2 col-form-label">정렬 순서 </label>
			                    <div class="col-sm-10">
									<form:input path="sortOrder" maxlength="10" class="form-control"  cssStyle="width:100px; margin-left:5px;float:left" onkeyup="fn_space_remove(this.value)"/>
			   						<div><form:errors path="sortOrder" cssClass="error"/></div>
			                    </div>
			                </div>

			                <div class="form-group row" style="display:none;">
			                    <label for="reflctAt" class="col-sm-2 col-form-label">* 그만보기 여부</label>
			                    <div class="col-sm-10">
			                    	<label><form:radiobutton path="stopVewAt" value="Y" checked="true" /> 사용함</label>
								    &nbsp;&nbsp;&nbsp;
								    <label><form:radiobutton path="stopVewAt" value="N" /> 사용안함</label>
									<div><form:errors path="stopVewAt" cssClass="error" /></div>
			                    </div>
			                </div>

							<div class="form-group row">
			                    <label for="reflctAt" class="col-sm-2 col-form-label">* 사용 여부</label>
			                    <div class="col-sm-10">
			                    	<label><form:radiobutton path="ntceAt" value="Y" checked="true" /> 사용함</label>
								    &nbsp;&nbsp;&nbsp;
								    <label><form:radiobutton path="ntceAt" value="N" /> 사용안함</label>
									<div><form:errors path="ntceAt" cssClass="error" /></div>
			                    </div>
			                </div>


			                </div>
			                <!-- /.card-body -->
			                <div class="card-footer">
			                  <button type="button" onclick="fn_save()" class="btn btn-info float-right">저장</button>
			                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
			                </div>
			                <!-- /.card-footer -->

						</form:form>

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>
</body>