<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : articleRegist.jsp
  * @Description : 게시판 게시물  등록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>
<%
	egovframework.com.cmm.LoginVO user = (egovframework.com.cmm.LoginVO) egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser();
%>
<c:set var="adminVO" value="<%=user%>" />
<c:set var="now" value="<%=new java.util.Date()%>" />

<head>
	<style>
		#articleVO label {font-weight:normal;}
		.border-0 th, .border-0 td {border:0 !important;}
	</style>

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
	        dateFormat: "yy-mm-dd"
	    });
		// 첫 입력란에 포커스
		document.getElementById("articleVO").nttSj.focus();

	}


	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_regist_article() {

		var form = document.articleVO;

		var varNttSj = $("#nttSj").val().trim() || '';
		if(varNttSj == '') {
			alert('제목 정보는 비울 수 없습니다.');
			$("#nttSj").focus();
			return false;
		}

		top.modules_board__oEditors.getById["nttCn"].exec("UPDATE_CONTENTS_FIELD", []);

		var varNttCn = $("#nttCn").val().trim() || '';

		if(varNttCn == '') {
			alert('내용 정보는 비울 수 없습니다.');
			return false;
		}


		/*
		var varNttCn = $("iframe").contents().find("iframe").contents().find('.se2_inputarea').text().trim() || '';
		if(varNttCn == '') {
			alert('내용 정보는 비울 수 없습니다.');
			$("iframe").contents().find("iframe").contents().find('.se2_inputarea').focus();
			return false;
		}
		*/

		/* if (!validateArticleVO(form)) {
			return false;
		} else { */

		var validateForm = document.getElementById("articleVO");

		//비밀글,익명글 사용안함으로 처리
		<c:if test="${boardMasterVO.bbsTyCode eq '000'}">
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

		</c:if>

		 <c:if test="${!empty ctgList }">
		 	if ($('#ctgId').val() == "" && $("#nttNo").val() != "0" && $("#nttNo").val() != "1") {
		 		alert("카테고리를 선택해 주세요.");
		 		$('#ctgId').focus();
		 		return;
		 	}
		 </c:if>

		var varUseAt = $('input:radio[name=useAt]').is(":checked");
		
		if(varUseAt == '') {
			alert('사용여부를 체크 해주세요.');
			$("#useAt").focus();
			return false;
		}
			//게시기간
			/*
			var ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			var ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde == '' && ntceEndde != '') {
				validateForm.ntceBgnde.value = '1900-01-01';
			}
			if (ntceBgnde != '' && ntceEndde == '') {
				validateForm.ntceEndde.value = '9999-12-31';
			}
			if (ntceBgnde == '' && ntceEndde == '') {
				validateForm.ntceBgnde.value = '1900-01-01';
				validateForm.ntceEndde.value = '9999-12-31';
			}

			ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde > ntceEndde) {
				alert("기간 설정 오류");
				return false;
			}
			*/
			var msg = "등록하시겠습니까?";
			if (form.nttId.value != "" && form.nttId.value != "0") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				form.submit();
			}

		/* } */
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
						<input type="hidden" name="searchQuery"  value="${searchQuery}"/>
						<input type="hidden" name="bbsTyCode"    value="<c:out value='${boardMasterVO.bbsTyCode}'/>" />
						<input type="hidden" name="replyPosblAt" value="<c:out value='${boardMasterVO.replyPosblAt}'/>" />
						<input type="hidden" name="fileAtchPosblAt"   value="<c:out value='${boardMasterVO.fileAtchPosblAt}'/>" />
						<input type="hidden" id="atchPosblFileNumber" name="atchPosblFileNumber" value="<c:out value='${boardMasterVO.atchPosblFileNumber}'/>" />
						<input type="hidden" name="atchPosblFileSize" value="<c:out value='${boardMasterVO.atchPosblFileSize}'/>" />
						<input type="hidden" name="tmplatId" value="<c:out value='${boardMasterVO.tmplatId}'/>" />
						<input type="hidden" name="blogId"   value="<c:out value='${searchVO.blogId}'/>" />
						<input type="hidden" name="blogAt"   value="<c:out value='${articleVO.blogAt}'/>"/>
						<input type="hidden" name="nttId"    value="<c:out value='${articleVO.nttId}'/>"/>
						<input type="hidden" name="nttNo"    value="<c:out value='${articleVO.nttNo}'/>"/>


		                <div class="card-body ">
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
								<c:if test="${!empty ctgList && articleVO.nttNo < 2 }">
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
								<c:if test="${!empty ctgList && articleVO.nttNo < 2 && articleVO.bbsId ne 'freeboard' && articleVO.bbsId ne 'qna'}">
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
								</c:if>
									<!--
									<tr>
										<th class="text-right">최상위 공지 <b class="text-red">*</b></th>
										<td>
											<label><form:checkbox path="noticeAt" value="Y"  /> 최상위 공지 여부</label>
										</td>
										<th class="text-right">비밀글 <b class="text-red">*</b></th>
										<td>
											<label><form:checkbox path="secretAt" value="Y"  /> 비밀글 여부</label>
										</td>
									</tr>
									<tr>
										<th class="text-right">익명등록  <b class="text-red">*</b></th>
										<td>
											<label><form:checkbox path="anonymousAt" value="Y"  /> 익명등록 여부</label>
										</td>
								<c:choose>
									<c:when test="${boardMasterVO.commentAt eq 'Y'}">
										<th class="text-right">댓글사용 <b class="text-red">*</b></th>
										<td>
											<label><form:checkbox path="commentAt" value="Y"  />댓글사용 여부</label>
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="2"></td>
									</c:otherwise>
								</c:choose>

									</tr>
									 -->
									<tr>
										<th class="text-right">게시글 내용 <b class="text-red">*</b></th>
										<td colspan="3" >
										<% /* inc.seditor.content.jsp 여기 안에 있음
											<form:textarea path="nttCn" title="${title} ${inputTxt}" class="form-control" style="height:400px"/>
											<div>
												<form:errors path="nttCn" cssClass="input-error"/>
												<form:hidden path="content_type"/>
												<form:errors path="content_type" cssClass="input-error"/>
											</div>
										*/ %>
											<form:hidden path="content_type"/>
											<form:errors path="content_type" cssClass="input-error"/>
											<c:set var="content_height" scope="request" value="40em"/>
											<c:set var="content_nm" scope="request" value="nttCn"/>
											<div style="width:80%">
												<jsp:include page="/WEB-INF/jsp/modules/seditor/inc.seditor.content.jsp"/>
											</div>
										</td>
									</tr>
							<c:if test="${boardMasterVO.managerAt == 'Y'}">
									<tr>
										<th class="text-right">담당 부서  <b class="text-red">*</b></th>
										<td>
				                    		<form:input path="managerDept" title="담당부서" class="form-control " maxlength="50" />
				   							<div><form:errors path="managerDept" cssClass="error" /></div>
										</td>
										<th class="text-right">담당자 연락처 <b class="text-red">*</b></th>
										<td>
				                    		<form:input path="managerTel" title="담당 연락처" class="form-control " maxlength="20" />
				   							<div><form:errors path="managerTel" cssClass="error" /></div>
										</td>
									</tr>
							</c:if>
							<c:if test="${boardMasterVO.urlLnikyn == 'Y'}">
									<tr>
										<th class="text-right">연관링크</th>
										<td colspan="3">
				                    		<form:input path="linkUrl" title="연관링크" class="form-control " maxlength="1000" />
				   							<div><form:errors path="linkUrl" cssClass="error" /></div>
										</td>
									</tr>
							</c:if>

							<c:if test="${itemList!=null  && fn:length(itemList)>0}">
								<c:forEach items="${itemList}" var="item" varStatus="status">
										<tr>
											<th class="text-right"><c:out value="${item.bbsItemNm }"/></th>
											<td colspan="3">
									<c:choose>
										<c:when test="${item.bbsItemType eq '2001'}">
											<c:choose>
												<c:when test="${itemInfoList!=null  && fn:length(itemInfoList)>0}">
												<c:set var="existTextarea" value="N" />
													<c:forEach items="${itemInfoList}" var="itemInfo" varStatus="status">
														<c:if test="${item.bbsEtcseq eq itemInfo.bbsEtcseq}">
															<textarea name="<c:out value="${item.bbsItemEnid }"/>" class="form-control "><c:out value="${itemInfo.bbsItemLvalue }"/></textarea>
															<c:set var="existTextarea" value="Y" />
														</c:if>
													</c:forEach>
													<c:if test="${existTextarea eq 'N'}">
														<textarea name="<c:out value="${item.bbsItemEnid }"/>" class="form-control "></textarea>
													</c:if>
												</c:when>
												<c:otherwise>
												<textarea name="<c:out value="${item.bbsItemEnid }"/>" class="form-control "></textarea>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${itemInfoList!=null  && fn:length(itemInfoList)>0}">
												<c:set var="existTextarea" value="N" />
													<c:forEach items="${itemInfoList}" var="itemInfo" varStatus="status">
														<c:if test="${item.bbsEtcseq eq itemInfo.bbsEtcseq}">
															<input type="text" name="<c:out value="${item.bbsItemEnid }"/>" class="form-control " maxlength="50" value="<c:out value="${itemInfo.bbsItemLvalue }"/>"/>
															<c:set var="existTextarea" value="Y" />
														</c:if>
													</c:forEach>
													<c:if test="${existTextarea eq 'N'}">
														<input type="text" name="<c:out value="${item.bbsItemEnid }"/>" class="form-control " maxlength="50" />
													</c:if>
												</c:when>
												<c:otherwise>
													<input type="text" name="<c:out value="${item.bbsItemEnid }"/>" class="form-control " maxlength="50" />
												</c:otherwise>
											</c:choose>

										</c:otherwise>
									</c:choose>
												<c:if test="${item.bbsItemEnid eq 'EDC_PROGRAM' }">
													<p>※ 내부기관이라면 강좌상세의 "뒷번호" 입력하시고, 외부기관이면 연결 할 "URL"을 입력하세요.</p>
												</c:if>

											</td>
										</tr>
								</c:forEach>
							</c:if>

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
									<tr>
										<th class="text-right">사용여부 <b class="text-red">*</b></th>
										<td colspan="3">
		                   					<label><form:radiobutton path="useAt" value="Y" /> 사용함</label>
						    				&nbsp;&nbsp;&nbsp;
						    				<label><form:radiobutton path="useAt" value="N" /> 사용안함</label>
											<div><form:errors path="useAt" cssClass="error" /></div>
										</td>
									</tr>
									<tr>
										<th class="text-right">등록자/일시</th>
										<td>
								 <c:choose>
								 	<c:when test="${fn:length(articleVO.frstRegisterId) > 0}">
											<c:out value="${articleVO.frstRegisterId}"/> / <c:out value="${articleVO.frstRegisterPnttm}"/>
									</c:when>
									<c:otherwise>
											<c:out value="${adminVO.id}" /> / <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
									</c:otherwise>
								</c:choose>
										</td>
										<th class="text-right">수정자/일시</th>
										<td>
								 <c:choose>
								 	<c:when test="${fn:length(articleVO.lastUpdusrId) > 0}">
											<c:out value="${articleVO.lastUpdusrId}"/> / <c:out value="${articleVO.lastUpdtPnttm}"/>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
										</td>
									</tr>
								</tbody>
							</table>

		                </div>


		                <div class="card-footer">
		                  <button type="button" onclick="fn_egov_regist_article()" class="btn btn-info float-right">저장</button>
		                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
		                </div>

					</form:form>

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>
</body>
