<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : bannerRegist.jsp
  * @Description : 배너  등록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.30    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.30
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<script type="text/javascript" src="../validator"></script>
	<validator:javascript formName="banner" staticJavascript="false" xhtml="true" cdata="false"/>
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
		var form = document.banner;

		if (!validateBanner(form)) {
			return false;
	<c:if test="${empty banner.bannerImage }">
		} else if (form.file_1.value == '') {
	        alert("배너이미지는 필수 입력값입니다.");/* 배너이미지는 필수 입력값입니다. */
	        return false;
	</c:if>
		} else {
			var ntceBgndeYYYMMDD = document.getElementById('ntceBgndeYMD').value;
			var ntceEnddeYYYMMDD = document.getElementById('ntceEnddeYMD').value;

			if (ntceBgndeYYYMMDD != '') {
				form.ntceBgnde.value = ntceBgndeYYYMMDD.replaceAll('-','') + fn_egov_SelectBoxValue('ntceBgndeHH') +  fn_egov_SelectBoxValue('ntceBgndeMM');
			} else {
				form.ntceBgnde.value = '';
			}
			if (ntceEnddeYYYMMDD != '') {
				form.ntceEndde.value = ntceEnddeYYYMMDD.replaceAll('-','') + fn_egov_SelectBoxValue('ntceEnddeHH') +  fn_egov_SelectBoxValue('ntceEnddeMM');
			} else {
				form.ntceEndde.value = '';
			}

			top.modules_board__oEditors.getById["linkContent"].exec("UPDATE_CONTENTS_FIELD", []);

			var msg = "등록하시겠습니까?";
			if (form.mode.value == "edit") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				form.submit();
			}

		}
	}
	$(document).ready(function() {
		fn_egov_init();
	});

	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>기업홍보 배너존 관리</h1>
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
	<form:form commandName="banner" name="banner" action="./bannerSave" class="form-horizontal" method="post" enctype="multipart/form-data">
	<input type="hidden" name="searchQuery" value="<c:out value="${searchQuery }"/>"/>
	<input type="hidden" name="mode" value="<c:out value="${mode }"/>"/>
	<input type="hidden" name="isOrg" value="Y"/>
	<form:hidden path="bannerId"/>
	<form:hidden path="bannerImageFile"/>
	<form:hidden path="ntceBgnde"/>
	<form:hidden path="ntceEndde"/>
	                <div class="card-body ">
	                	<div class="form-group row">
	                    <label for="bannerNm" class="col-sm-2 col-form-label">* 배너명</label>
	                    <div class="col-sm-10">
	                    	<form:input path="bannerNm" title="배너명" class="form-control " maxlength="30" />
	   						<div><form:errors path="bannerNm" cssClass="error" /></div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="linkUrl" class="col-sm-2 col-form-label">* 배너위치</label>
	                    <div class="col-sm-10">
<c:choose>
	<c:when test="${bannerGbn==null || fn:length(bannerGbn)<1}"><span class="text-red">코드그룹 [WEB_BANNER_GBN]의 코드를 등록해주세요.</span></c:when>
	<c:otherwise>

	                    	<form:select path="bannerGbn" class="form-control ">
					<c:forEach items="${bannerGbn}" var="gbn" varStatus="status">
								<form:option value="${gbn.cd}">${gbn.cdNm}</form:option>
					</c:forEach>

							</form:select>
	   						<div><form:errors path="bannerGbn" cssClass="error" /></div>
	</c:otherwise>
</c:choose>

	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="linkUrl" class="col-sm-2 col-form-label">* 링크 타겟</label>
	                    <div class="col-sm-10">
	                    	<form:select path="linkTarget" class="form-control ">
	 							<form:option value="_self">본창</form:option>
								<form:option value="_blank">새창</form:option>
							</form:select>
	   						<div><form:errors path="linkTarget" cssClass="error" /></div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="linkUrl" class="col-sm-2 col-form-label">링크URL</label>
	                    <div class="col-sm-10">
	                    	<form:input path="linkUrl" title="제목" class="form-control " maxlength="255" />
	   						<div><form:errors path="linkUrl" cssClass="error" /></div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="ntceBgnde" class="col-sm-2 col-form-label">게시 시작일</label>
	                    <div class="col-sm-10">
	                      	<div style="display:flex">
		                      	<form:input path="ntceBgndeYMD" title="시작일" maxlength="10"  class="form-control calendar" style="width:140px;float:left" autocomplete="off"/>

								<form:select path="ntceBgndeHH" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
									<form:options items="${ntceBgndeHH}" itemValue="code" itemLabel="codeNm"  />
								</form:select>

								<form:select path="ntceBgndeMM" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
									<form:options items="${ntceBgndeMM}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</div>
							<div>※ 날짜를 지정하지 않는 경우 게시 종료일까지 적용됩니다.</div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="ntceBgnde" class="col-sm-2 col-form-label">게시 종료일</label>
	                    <div class="col-sm-10">
							<div style="display:flex">

								<form:input path="ntceEnddeYMD" title="종료일" class="form-control calendar"  maxlength="10" style="width:140px;float:left" autocomplete="off" />
								<form:select path="ntceEnddeHH" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
									<form:options items="${ntceBgndeHH}" itemValue="code" itemLabel="codeNm"  />
								</form:select>

								<form:select path="ntceEnddeMM" cssClass="form-control" style="width:120px;float:left;margin-left:5px">
									<form:options items="${ntceBgndeMM}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</div>

							<div class="help-block">※ 날짜를 지정하지 않는 경우 시작일부터  상시 게시 됩니다.</div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="linkUrl" class="col-sm-2 col-form-label">* 이미지 선택</label>
	                    <div class="col-sm-10">
	                    	<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control"/>
	   						<div><form:errors path="bannerImage" cssClass="error" /></div>
	   						<c:if test="${!empty banner.bannerImage }"><div>※ 현재 파일 : <c:out value="${banner.bannerImage }" /></div></c:if>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="bannerDc" class="col-sm-2 col-form-label"> 메인노출 텍스트(선택)</label>
	                    <div class="col-sm-10">
	                    	<form:textarea path="bannerDc" title="텍스트" class="form-control" style="height:150px" maxlength="100" />
	   						<div><form:errors path="bannerDc" cssClass="error" /></div>
	                    </div>
	                  </div>
	 				  <div class="form-group row">
	                    <label for="linkType" class="col-sm-2 col-form-label">* 배너클릭 연결방식</label>
	                    <div class="col-sm-10">
	                    	<label><input type="radio" name="linkType" value="1" <c:if test="${ banner.linkType ne '2'}">checked</c:if> /> 다이렉트 URL이동</label>
						    &nbsp;&nbsp;&nbsp;
	                    	<label><input type="radio" name="linkType" value="2" <c:if test="${ banner.linkType eq '2'}">checked</c:if> /> 배너존 상세이동</label>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="linkContent" class="col-sm-2 col-form-label">배너존 상세텍스트</label>
	                    <div class="col-sm-10">
							<c:set var="content_height" scope="request" value="40em"/>
							<c:set var="content_nm" scope="request" value="linkContent"/>
							<jsp:include page="/WEB-INF/jsp/modules/seditor/inc.seditor.content.jsp"/>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="bannerDc" class="col-sm-2 col-form-label">* 정렬순서</label>
	                    <div class="col-sm-10">
	                    	<form:input path="sortOrdr" title="정렬순서" class="form-control " maxlength="5" />
	   						<div><form:errors path="sortOrdr" cssClass="error" /></div>
	                    </div>
	                  </div>


	 					<div class="form-group row">
	                    <label for="reflctAt" class="col-sm-2 col-form-label">* 사용여부</label>
	                    <div class="col-sm-10">
	                    	<label><form:radiobutton path="reflctAt" value="Y" /> 사용함</label>
						    &nbsp;&nbsp;&nbsp;
						    <label><form:radiobutton path="reflctAt" value="N" /> 사용안함</label>
							<div><form:errors path="reflctAt" cssClass="error" /></div>
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