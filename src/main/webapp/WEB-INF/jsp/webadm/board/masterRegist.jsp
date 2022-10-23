<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  @Class Name : masterRegist.jsp
  @Description : 게시판 마스터 등록 화면
  @Modification Information
  @
  @  수정일         수정자                   수정내용
  @ -------    --------    ---------------------------
  @ 2020.07.21    진수진          최초 생성
  *
   @author 진수진
   @since 2020.07.21
   @version 1.0
   @see
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
		#boardMasterVO label {font-weight:normal;}
		.border-0 th, .border-0 td {border:0 !important;}
	</style>
	<script type="text/javascript" src="../validator"></script>
	<validator:javascript formName="boardMasterVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<script type="text/javascript">
	/********************************************************
	 저장처리화면
	 ********************************************************/
	function fn_egov_updt_bbs(form, bbsId) {
		if (!validateBoardMasterVO(form)) {
			return false;
		} else {

			var validateForm = document.getElementById("boardMasterVO");


			if (validateForm.fileAtchPosblAt.value == 'Y' && validateForm.atchPosblFileNumber.value == '0') {
				alert('첨부가능파일숫자를 선택하세요.');
				return;
			}

			if (validateForm.fileAtchPosblAt.value == 'Y' && validateForm.atchPosblFileExt.value == '') {
				alert('첨부가능한 파일 확장자를 입력해 주세요.');
				validateForm.atchPosblFileExt.focus();
				return;
			}

			var msg = "등록하시겠습니까>";
			<c:if test="${mode eq 'edit' }">
				msg = "수정하시겠습니까?";
				if($('#boardMasterVO').find('input[name=delCtgId]').length>0){
					msg = "카테고리 등록되어 있는 게시물의 카테고리도 삭제 됩니다. 삭제하시겠습니까?"
				}
			</c:if>
			if (confirm(msg)) {
				form.submit();
			}
		}
	}
	function fn_id_check() {
		var f = document.boardMasterVO;

		if ($('#bbsId').val() == "") {
			alert("게시판 ID를 입력해 주시기 바랍니다.");
			f.bbsId.focus();
		} else if (!validateEnglish(f)) {
			return;
		} else {
			$.ajax({
			      url: './masterIdCheckAjax',
			      data : $('#boardMasterVO').serialize(),
			      type : "POST",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 $('#divModalResult').text("이미 사용중인 게시판 ID 입니다. 다른 게시판 ID 를 입력해 주세요.").removeClass("text-blue").addClass("text-red");
				        } else {
				        	$('#confirmBbsId').val(data.result.msg);
				        	$('#bbsId').val(data.result.msg);
				        	$('#divModalResult').text("사용 하실 수 있는 아이디 입니다.").addClass("text-blue").removeClass("text-red");
				         }
			      }
			 });
		}
	}


	// 카테고리 삭제
	function fn_ctg_delete(obj) {
		$('#boardMasterVO').append($("<input type='hidden' name='delCtgId'/>").val($(obj).parent().parent().find("input[name=ctgId]").val()));
		$(obj).parent().parent().remove();
		var trCnt = $('.ctg-row').length;
		if(trCnt<1){
			$('.ctg-no').show();
		}
	}
	function fn_ctg_add() {
		$('.ctg-no').hide();
		var trCnt = $('.ctg-row').length + 1;
		var tr = '';
		tr += '<tr class="ctg-row">';
		tr += '	<td><input type="text" name="ctgId" value="" style="width:98%; background:#ddd;" readonly  placeholder="자동생성"/></td>';
		tr += '<td><input type="text" name="ctgNm" value="" style="width:98%;" placeholder="카테고리명 입력"/></td>';
		tr += '<td><input type="text" name="ctgOrder" value="'+trCnt+'" style="width:98%;" placeholder="숫자만 입력"/></td>';
		tr += '<td>';
		tr += '<select name="ctgYn" style="width:98%;">';
		tr += '<option value="Y" selected>Y</option>';
		tr += '<option value="N" >N</option>';
		tr += '</select>';
		tr += '</td>';
		tr += '<td><input type="text" name="ctgRegDt" value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />" style="width:98%;"/></td>';
		tr += '<td>';
		tr += '<button type="button" class="btn btn-secondary btn-xs" onclick="fn_ctg_delete(this)">삭제</button>';
		tr += '</td>';
		tr += '</tr>';

		$('#boardCtgTable > tbody').append(tr);
	}

	function fn_item_delete(obj) {
		$('#boardMasterVO').append($("<input type='hidden' name='delItemId'/>").val($(obj).parent().parent().find("input[name=itemId]").val()));
		$(obj).parent().parent().remove();
		var trCnt = $('.item-row').length;
		if(trCnt<1){
			$('.item-no').show();
		}
	}

	function fn_item_add() {
		$('.item-no').hide();
		var trCnt = $('.item-row').length + 1;
		var tr = '';
		tr += '<tr class="item-row">';
		tr += '	<td><input type="text" name="itemId" value="" style="width:98%; background:#ddd;" readonly  placeholder="자동생성"/></td>';
		tr += '<td><input type="text" name="itemEnId" value="" style="width:98%;background:#ddd;"/></td>';
		tr += '<td>';
		tr += '<select name="itemType" style="width:98%;">';
		tr += '<option value="1001" selected>일반정보</option>';
		tr += '<option value="2001" >컨텐츠정보</option>';
		tr += '</select>';
		tr += '</td>';
		tr += '<td><input type="text" name="itemNm" value="" style="width:98%;" placeholder="항목명 입력"/></td>';
		tr += '<td><input type="text" name="itemOrder" value="'+trCnt+'" style="width:98%;" placeholder="숫자만 입력"/></td>';
		tr += '<td>';
		tr += '<button type="button" class="btn btn-secondary btn-xs" onclick="fn_item_delete(this)">삭제</button>';
		tr += '</td>';
		tr += '</tr>';

		$('#boardItemTable > tbody').append(tr);
	}


	$(document).ready(function() {
	<c:if test="${ctgList!=null  && fn:length(ctgList)>0}">
		$('.ctg-no').hide();
	</c:if>
	<c:if test="${itemList!=null  && fn:length(itemList)>0}">
		$('.item-no').hide();
	</c:if>
	});

	</script>

<c:if test="${boardMasterVO.bbsId eq 'dongari' || boardMasterVO.bbsId eq 'teacherpr' }">
	<script>
	$(document).ready(function() {
		$('.btn-ctg').remove();
		$('.btn-item').remove();
	});
	</script>
</c:if>
</head>

<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>게시판 관리</h1>
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


	<form:form commandName="boardMasterVO" name="boardMasterVO" action="./masterSave" class="form-horizontal" method="post" onSubmit="fn_egov_updt_bbs(this); return false;">
	<input type="hidden" name="searchQuery" value="${searchQuery }"/>
	<input name="mode" type="hidden" value="<c:out value='${mode }'/>">
	<input type="hidden" name="confirmBbsId" id="confirmBbsId"  value="">
	                <div class="card-body">
	                	<table class="table border-0" style="">
			               	<colgroup>
								<col style="width:15%">
								<col style="width:35%">
								<col style="width:15%">
								<col style="width:35%">
							</colgroup>
							<tbody>
								<tr>
									<th class="text-right">게시판ID <b class="text-red">*</b></th>
									<td colspan="3">
									 <c:choose>
									 	<c:when test="${mode eq 'edit' }">
									 		<form:input path="bbsId" title="게시판 ID" class="form-control" maxlength="20" readonly="true"/>
									 	</c:when>
									 	<c:otherwise>
										 	<div class="input-group">
										 		<form:input path="bbsId" title="게시판 ID" class="form-control" maxlength="20" />
										 		<span class="input-group-append">
										        	<button type="button" class="btn btn-info btn-flat" onclick="fn_id_check()">중복체크</button>
										        </span>
										   	</div>
										    <div class="help-block">※ 영문으로 20자 이하로 입력해 주세요.</div>
									 	</c:otherwise>
									 </c:choose>
									   		<div id="divModalResult"><form:errors path="bbsId" cssClass="error text-red" /></div>
									</td>
								</tr>
								<tr>
									<th class="text-right">게시판명 <b class="text-red">*</b></th>
									<td colspan="3">
										<form:input path="bbsNm" title="${title} ${inputTxt }" class="form-control" maxlength="70" />
										<div><form:errors path="bbsNm" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th class="text-right">게시판소개내용</th>
									<td colspan="3">
										<form:textarea path="bbsIntrcn" title="${title} ${inputTxt}" class="form-control" style="height:100px"/>
										<div><form:errors path="bbsIntrcn" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th class="text-right">게시판 유형 <b class="text-red">*</b></th>
									<td>
										<form:select path="bbsTyCode" title="${title} ${inputTxt}" cssClass="form-control">
										<c:if test="${fn:length(bbsTyCode)>1}">
											<form:option value='' label="--선택하세요--" />
										</c:if>
											<form:options items="${bbsTyCode}" itemValue="code" itemLabel="codeNm" />
										</form:select>
										<div><form:errors path="bbsTyCode" cssClass="error" /></div>
									</td>
									<th class="text-right">게시판 템플릿 <b class="text-red">*</b></th>
									<td>
										<form:select path="tmplatId" title="게시판 템플릿" cssClass="form-control">
											<form:option value='' label="--선택하세요--" />
											<c:forEach items="${templateList}" var="tmp" >
												<form:option value='${tmp.tmplatId}' label="${tmp.tmplatNm} (${tmp.tmplatSeCodeNm })" />
											</c:forEach>
										</form:select>
										<div><form:errors path="tmplatId" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th class="text-right">게시판 사용여부 <b class="text-red">*</b></th>
									<td>
										<label><form:radiobutton path="useAt" value="Y" /> 사용함</label>
										&nbsp;&nbsp;&nbsp;
										<label><form:radiobutton path="useAt" value="N" /> 사용안함</label>
										<div><form:errors path="useAt" cssClass="error" /></div>
									</td>

									<th class="text-right">첨부파일 기능 <b class="text-red">*</b></th>
									<td>
										<label><form:radiobutton path="fileAtchPosblAt" value="Y" /> 사용함</label>
										&nbsp;&nbsp;&nbsp;
										<label><form:radiobutton path="fileAtchPosblAt" value="N" /> 사용안함</label>
										<div><form:errors path="fileAtchPosblAt" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th class="text-right">첨부파일 갯수 <b class="text-red">*</b></th>
									<td>
										<form:select path="atchPosblFileNumber" title="${title} ${inputTxt }" cssClass="form-control">
										<form:option value="0" selected="selected">없음</form:option>
										<form:option value='1'>1</form:option>
										<form:option value='2'>2</form:option>
										<form:option value='3'>3</form:option>
										<form:option value='4'>4</form:option>
										<form:option value='5'>5</form:option>
										</form:select>
										<div><form:errors path="atchPosblFileNumber" cssClass="error" /></div>
									</td>
									<th class="text-right">첨부파일 확장자 <b class="text-red">*</b></th>
									<td>
										<form:input path="atchPosblFileExt" title="첨부파일 확장자" class="form-control" maxlength="70" />
										<div><form:errors path="atchPosblFileExt" cssClass="error" /></div>
										<div>첨부 가능한 확장자를   쉼표(,) 로 연결하여 소문자로 입력해 주세요. </div>


										<form:hidden path="replyPosblAt" value="N" />
										<form:hidden path="commentAt" value="N" />
										<form:hidden path="stsfdgAt" value="N" />
 									</td>
								</tr>
								<tr>
									<th class="text-right">담당자 표기 사항 <b class="text-red">*</b></th>
									<td>
										<label><form:checkbox path="managerAt" value="Y" /> 담당자 표시 사용</label>
									</td>
									<th class="text-right">URL 링크 사용여부 <b class="text-red">*</b></th>
									<td>
										<label><form:radiobutton path="urlLnikyn" value="Y" /> 사용함</label>
										&nbsp;&nbsp;&nbsp;
										<label><form:radiobutton path="urlLnikyn" value="N" /> 사용안함</label>
										<div><form:errors path="urlLnikyn" cssClass="error" /></div>
									</td>
								</tr>
								<!--
								<tr>
									<th class="text-right">답변기능 <b class="text-red">*</b></th>
									<td>
										<label><form:radiobutton path="replyPosblAt" value="Y" /> 사용함</label>
										&nbsp;&nbsp;&nbsp;
										<label><form:radiobutton path="replyPosblAt" value="N" /> 사용안함</label>
										<div><form:errors path="replyPosblAt" cssClass="error" /></div>
									</td>
									<th class="text-right">담당자 표기 사항 <b class="text-red">*</b></th>
									<td>
										<label><form:checkbox path="managerAt" value="Y" /> 담당자 표시 사용</label>
									</td>
								</tr>
								<tr>
									<th class="text-right">댓글 여부 <b class="text-red">*</b></th>
									<td>
										<label><form:checkbox path="commentAt" value="Y" /> 댓글 사용</label>
									</td>
									<th class="text-right">만족도 체크 여부 <b class="text-red">*</b></th>
									<td>
										<label><form:checkbox path="stsfdgAt" value="Y" /> 만족도 사용</label>
									</td>
								</tr>
								 -->
								<tr>
									<th class="text-right">추가입력항목<br>(1열 자동 구성)
										<br/> <button type="button" class="btn btn-primary btn-xs btn-item<c:if test="${commandMap.selectedMenu.updYn eq 'N' }"> disabled</c:if>" <c:if test="${commandMap.selectedMenu.updYn ne 'N' }">onclick="fn_item_add('')"</c:if>>추가</button>
									</th>
									<td colspan="3">
										<table id="boardItemTable" style="width:100%" class="table table-bordered sm-table">
							               	<colgroup>
												<col style="width:17%">
												<col style="width:17%">
												<col style="width:17%">
												<col style="">
												<col style="width:15%">
												<col style="width:11%">
											</colgroup>
											<thead class="bg-lightblue">
												<tr>
													<th>ID</th>
													<th>항목영문고유ID</th>
													<th>항목유형</th>
													<!--
													게시판추가 항목타입유형을 분류하는 시스템 분류코드
													1001 : 일반정보(최대  21,000자)
													2001 : 컨텐츠정보(최대 1,600,000 만자)
													-->
													<th>항목명</th>
													<th>순서</th>
													<th>관리</th>
												</tr>
											</thead>
											<tbody>
												<tr class="item-no">
													<td colspan="5" class="text-center">추가입력항목을 추가해주세요.</td>
												</tr>
									<c:if test="${itemList!=null  && fn:length(itemList)>0}">
										<c:forEach items="${itemList}" var="item" varStatus="status">
											<% /* bbsItemEnid 가 BBSITEM 으로 시작하지 않으면 고정 */ %>

												<tr class="item-row">
											 <c:choose>
											 	<c:when test="${fn:indexOf(item.bbsItemEnid,'BBSITEM')>=0 }">
													<td><input type="text" name="itemId" value="<c:out value="${item.bbsEtcseq }"/>" style="width:98%; background:#ddd;" readonly  placeholder="자동생성"/></td>
													<td><input type="text" name="itemEnId" value="<c:out value="${item.bbsItemEnid }"/>" style="width:98%;background:#ddd;" readonly  placeholder="자동생성"/></td>
													<td>
														<select name="itemType" style="width:98%;">
															<option value="1001" <c:if test="${item.bbsItemType eq '1001' }">selected</c:if> >일반정보</option>
															<option value="2001" <c:if test="${item.bbsItemType eq '2001' }">selected</c:if> >컨텐츠정보</option>
														</select>
													</td>
													<td><input type="text" name="itemNm" value="<c:out value="${item.bbsItemNm }"/>" style="width:98%;" placeholder="항목명 입력"/></td>
													<td><input type="text" name="itemOrder" value="<c:out value="${item.bbsItemSort }"/>" style="width:98%;" placeholder="숫자만 입력"/></td>
													<td>
														<button type="button" class="btn btn-secondary btn-xs btn-item" onclick="fn_item_delete(this)">삭제</button>
													</td>
											 	</c:when>
											 	<c:otherwise>
													<td><input type="text" name="itemId" value="<c:out value="${item.bbsEtcseq }"/>" style="width:98%; background:#ddd;" readonly  placeholder="자동생성"/></td>
													<td><input type="text" name="itemEnId" value="<c:out value="${item.bbsItemEnid }"/>" style="width:98%;background:#ddd;" readonly  placeholder="자동생성"/></td>
													<td>
														고정항목
														<input type="hidden" name="itemType" value="1001"/>
													</td>
													<td><input type="text" name="itemNm" value="<c:out value="${item.bbsItemNm }"/>" style="width:98%; background:#ddd;"  placeholder="항목명 입력"/></td>
													<td><input type="text" name="itemOrder" value="<c:out value="${item.bbsItemSort }"/>" style="width:98%; background:#ddd;" readonly placeholder="숫자만 입력"/></td>
													<td>
														고정항목
													</td>
											 	</c:otherwise>
											 </c:choose>
												</tr>
										</c:forEach>
									</c:if>
											</tbody>
										</table>
									</td>
								</tr>

								<tr>
									<th class="text-right">카테고리
										<br/> <button type="button" class="btn btn-primary btn-xs btn-ctg <c:if test="${commandMap.selectedMenu.updYn eq 'N' }"> disabled</c:if>" <c:if test="${commandMap.selectedMenu.updYn ne 'N' }">onclick="fn_ctg_add('')"</c:if>>추가</button>
									</th>
									<td colspan="3">
										<table id="boardCtgTable" style="width:100%" class="table table-bordered sm-table">
							               	<colgroup>
												<col style="">
												<col style="">
												<col style="width:13%">
												<col style="width:13%">
												<col style="width:16%">
												<col style="width:12%">
											</colgroup>
											<thead>
												<thead class="bg-lightblue">
													<th>ID</th>
													<th>카테고리명</th>
													<th>순서</th>
													<th>사용</th>
													<th>등록일</th>
													<th>관리</th>
												</tr>
											</thead>
											<tbody>
												<tr class="ctg-no">
													<td colspan="6" class="text-center" >카테고리를 추가해주세요.</td>
												</tr>
									<c:if test="${ctgList!=null  && fn:length(ctgList)>0}">
										<c:forEach items="${ctgList}" var="ctg" varStatus="status">
												<tr class="ctg-row">
													<td><input type="text" name="ctgId" value="<c:out value="${ctg.ctgId }"/>" style="width:98%; background:#ddd;" readonly  placeholder="자동생성"/></td>
													<td><input type="text" name="ctgNm" value="<c:out value="${ctg.ctgNm }"/>" style="width:98%;" placeholder="카테고리명 입력"/></td>
													<td><input type="text" name="ctgOrder" value="<c:out value="${ctg.ctgSort }"/>" style="width:98%;" placeholder="숫자만 입력"/></td>
													<td>
														<select name="ctgYn" style="width:98%;">
															<option value="Y" <c:if test="${ctg.useAt eq 'Y' }">selected</c:if> >Y</option>
															<option value="N" <c:if test="${ctg.useAt eq 'N' }">selected</c:if> >N</option>
														</select>
													</td>
													<td><input type="text" name="ctgRegDt" value="<c:out value="${ctg.frstRegisterPnttm }"/>" style="width:98%;"/></td>
													<td>
														<button type="button" class="btn btn-secondary btn-xs btn-ctg" onclick="fn_ctg_delete(this)">삭제</button>
													</td>
												</tr>
										</c:forEach>
									</c:if>



											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<th class="text-right">등록자/일시</th>
									<td>
							 <c:choose>
							 	<c:when test="${fn:length(boardMasterVO.frstRegisterId) > 0}">
										<c:out value="${boardMasterVO.frstRegisterId}"/> / <c:out value="${boardMasterVO.frstRegisterPnttm}"/>
								</c:when>
								<c:otherwise>
										<c:out value="${adminVO.id}" /> / <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
								</c:otherwise>
							</c:choose>
									</td>
									<th class="text-right">수정자/일시</th>
									<td>
							 <c:choose>
							 	<c:when test="${fn:length(boardMasterVO.lastUpdusrId) > 0}">
										<c:out value="${boardMasterVO.lastUpdusrId}"/> / <c:out value="${boardMasterVO.lastUpdusrPnttm}"/>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
									</td>
								</tr>
							</tbody>
						</table>


	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="submit" class="btn btn-info float-right">저장</button>
	                  <button type="button" class="btn btn-default " onclick="history.back();">목록</button>
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


	<!-- 모달 박스 -->
	<div class="modal fade" id="modal-default" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Default Modal</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<p>One fine body…</p>
				</div>
				<div class="modal-footer justify-content-between">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="fn_save()">저장</button>
				</div>
			</div>
		</div>
	</div>
</body>