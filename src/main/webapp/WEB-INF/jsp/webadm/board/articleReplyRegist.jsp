<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : articleReplyRegist.jsp
  * @Description : 게시판 게시물 답변  등록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.27    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.27
  *  @version 1.0
  *  @see
  *
  */
%>

<head>
	<script type="text/javascript" src="../../../validator"></script>
	<script type="text/javascript" src="../../../resources/js/EgovMultiFile.js"></script>
	<validator:javascript formName="articleVO" staticJavascript="false" xhtml="true" cdata="false"/>
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
	    	format: "yyyy-mm-dd"
	    });
		// 첫 입력란에 포커스
		document.getElementById("articleVO").nttSj.focus();

	}


	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_regist_article() {
		var form = document.articleVO;

		if (!validateArticleVO(form)) {
			return false;
		} else {

			var validateForm = document.getElementById("articleVO");

			//비밀글은 제목 진하게 할 수 없음.
			//비밀글은 익명게시 불가.
			//비밀글은 공지게시 불가.
			if (validateForm.secretAt.checked) {
				if (validateForm.sjBoldAt.checked) {
					alert("비밀글은 제목을 진하게 할 수 없습니다.");
					return;
				}
				if (validateForm.anonymousAt.checked) {
					alert("비밀글은 익명으로 등록할 수 없습니다.");
					return;
				}
				if (validateForm.noticeAt.checked) {
					alert("비밀글은 공지로 등록할 수 없습니다.");
					return;
				}
			}
			//익명글은 공지게시 불가.
			if (validateForm.anonymousAt.checked) {
				if (validateForm.noticeAt.checked) {
					alert("익명글은 공지로 등록할 수 없습니다.");
					return;
				}
			}

			//게시기간
			var ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			var ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde == '' && ntceEndde != '') {
				//validateForm.ntceBgnde.value = '1900-01-01';
			}
			if (ntceBgnde != '' && ntceEndde == '') {
				//validateForm.ntceEndde.value = '9999-12-31';
			}
			if (ntceBgnde == '' && ntceEndde == '') {
				//validateForm.ntceBgnde.value = '1900-01-01';
				//validateForm.ntceEndde.value = '9999-12-31';
			}
	/*
			ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde > ntceEndde) {
				alert("게시기간 오류");
				return false;
			}
	*/
			var msg = "등록하시겠습니까?";
			if (confirm(msg)) {
				form.submit();
			}

		}
	}

	$(document).ready(function() {
		fn_egov_init();
	})
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1><small>게시판 관리 ></small> <c:out value="${boardMasterVO.bbsNm}"/></h1>
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

	<form:form commandName="articleVO" name="articleVO" action="./save" class="form-horizontal" method="post"  enctype="multipart/form-data">
	<input type="hidden" name="searchQuery" value="${searchQuery }"/>
	<input type="hidden" name="bbsTyCode" value="<c:out value='${boardMasterVO.bbsTyCode}'/>" />
	<input type="hidden" name="replyPosblAt" value="<c:out value='${boardMasterVO.replyPosblAt}'/>" />
	<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${boardMasterVO.fileAtchPosblAt}'/>" />
	<input type="hidden" id="atchPosblFileNumber" name="atchPosblFileNumber" value="<c:out value='${boardMasterVO.atchPosblFileNumber}'/>" />
	<input type="hidden" name="atchPosblFileSize" value="<c:out value='${boardMasterVO.atchPosblFileSize}'/>" />
	<input type="hidden" name="tmplatId" value="<c:out value='${boardMasterVO.tmplatId}'/>" />
	<input type="hidden" name="blogId" value="<c:out value='${searchVO.blogId}'/>" />
	<input type="hidden" name="blogAt" value="<c:out value='${articleVO.blogAt}'/>"/>
	<input type="hidden" name="parnts" value="<c:out value='${articleVO.nttId}'/>" />
	<input type="hidden" name="sortOrdr" value="<c:out value='${articleVO.sortOrdr}'/>" />
	<input type="hidden" name="noticeAt" value="<c:out value='${articleVO.noticeAt}'/>" />
	<input type="hidden" name="replyLc" value="<c:out value='${articleVO.replyLc+1}'/>" />
	                <div class="card-body">

		                	<table class="table border-0" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>
									<tr>
										<th class="text-right">제목 <b class="text-red">*</b></th>
										<td colspan="3">
					                    	<form:input path="nttSj" title="${title} ${inputTxt}" class="form-control " maxlength="70" style="width:80%; display:inline-block"/>
					                    	&nbsp;&nbsp;<label><form:checkbox path="sjBoldAt" value="Y"/> bold</label>
					   						<div><form:errors path="nttSj" cssClass="error" /></div>
										</td>
									</tr>
								<c:if test="${!empty ctgList}">
									<tr>
										<th class="text-right">카테고리 <b class="text-red">*</b></th>
										<td colspan="3">
											<form:select path="ctgId" title="카테고리 선택"  class="form-control" style="width:80%; display:inline-block">
												<form:option value="">::카테고리 선택::</form:option>
												<c:forEach items="${ctgList}" var="item" varStatus="status">
													<form:option value="${item.ctgId}"><c:out value="${item.ctgNm}"/></form:option>
												</c:forEach>
											</form:select>
										</td>
									</tr>
								</c:if>

									<tr>
										<th class="text-right">게시기간 <b class="text-red">*</b></th>
										<td colspan="3">
											<form:input path="ntceBgnde" title="${title} ${inputTxt}" maxlength="10"  class="form-control calendar" style="width:140px;float:left" autocomplete="off" />
											<div style="float:left">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</div>

											<form:input path="ntceEndde" title="${title} ${inputTxt}" class="form-control calendar"  maxlength="10" style="width:140px;float:left" autocomplete="off"/>
											<div><form:errors path="ntceBgnde" cssClass="error" /></div>
											<div><form:errors path="ntceEndde" cssClass="error" /></div>
										</td>
									</tr>
									<tr>
										<th class="text-right">비밀글 <b class="text-red">*</b></th>
										<td >
				                    		<form:checkbox path="secretAt" value="Y"  class="form-check-input"/>
					                        <label class="form-check-label" for="secretAt1">비밀글 여부</label>
										</td>
										<th class="text-right">익명등록 <b class="text-red">*</b></th>
										<td >
				                    		<form:checkbox path="anonymousAt" value="Y"  class="form-check-input"/>
					                        <label class="form-check-label" for="anonymousAt1">익명등록 여부</label>
										</td>
									</tr>
																											<tr>
										<th class="text-right">게시글 내용 <b class="text-red">*</b></th>
										<td colspan="3">
					                      	<form:textarea path="nttCn" title="${title} ${inputTxt}" class="form-control" style="height:500px"/>
											<div><form:errors path="nttCn" cssClass="error" /></div>
										</td>
									</tr>

							<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
									<tr>
										<th class="text-right">첨부파일 <b class="text-red">*</b></th>
										<td colspan="3">

									<c:import url="/web/common/upload/includeUploadRegist" >
							       		<c:param name="file_length" value="${boardMasterVO.atchPosblFileNumber}" />
							       		<c:param name="file_ext" value="${boardMasterVO.atchPosblFileExt}" />
							       		<c:param name="file_group_id" value="${articleVO.atchFileId}" />
						        	</c:import>
										</td>
									</tr>
							</c:if>
								</tbody>
							</table>
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="button" onclick="fn_egov_regist_article()" class="btn btn-info float-right">저장</button>
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
