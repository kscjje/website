<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="now" value="<%=new java.util.Date()%>" />

<head>
	<script type="text/javascript" src="../validator"></script>
	<script type="text/javascript" src="../resources/js/EgovMultiFile.js"></script>
	<validator:javascript formName="themaVO" staticJavascript="false" xhtml="true" cdata="false"/>

	<script type="text/javascript">

	var questionTypeOptions = new Array();
	var questionTypeList = ${questionTypeListJson};
	questionTypeOptions.push("<option value=''>= 선택 =</option>");
	$.each(questionTypeList, function(index, item) {
		questionTypeOptions.push("<option value='" + item.cd + "'>" + item.cdNm + "</option>");
	});

	var themaVOJson = ${themaVOJson};

	$(document).ready(function() {
		$('input.calendar').datepicker({
	    	autoclose: true,
	        calendarWeeks: false,
	        todayHighlight: true,
	        language: "kr",
	        dateFormat: "yy-mm-dd"
	    });


		<c:if test="${questionList!=null  && fn:length(questionList)>0}">
		$('.question-no').hide();
		</c:if>

		nextTrId = $('.question-row').length;
	})


	function fn_question_type(obj) {
		var type = $(obj).val();

		$(obj).parent().parent().find(".questionEmpty").hide();
		$(obj).parent().parent().find(".questionItemBtn").hide();
		$(obj).parent().parent().find(".questionItemShow").hide();
		$(obj).parent().parent().find(".qestnsDesc").hide();
		switch (type) {
			case '0000':
				$(obj).parent().parent().find(".qestnsDesc").show();
				break;
			case '1001':
			case '2001':
				$(obj).parent().parent().find(".questionItemBtn").show();
				$(obj).parent().parent().find(".questionItemShow").show();
				break;
			case '3001':
				break;
			default:
				$(obj).parent().parent().find(".questionEmpty").show();
				break;
		}
	}

	var selectQestnsType = "";
	function fn_question_add_item(obj){

		$('.modal-qid').val($(obj).parent().parent().find("span.q-idx").text());
		$('.items-body').children().remove(); // 항목순번 삭제

		$('.p-question').text($(obj).parent().parent().find("input[name=qestnsName]").val())
		selectQestnsType = $(obj).parent().parent().find("select[name=qestnsType]").val();

		var items = [];	//

		if($(obj).parent().parent().find(".questionItemShow>span").length>0){
			$($(obj).parent().parent().find(".questionItemShow>span")).each(function(i,v) {

				var item = {};
				item['nm'] = $(v).find('.questionItemNm').text();
				item['score'] = $(v).find('.questionItemScore').text();
				var nm = $(v).find('.questionItemNm').text();

				var score = '0';
				if(selectQestnsType=='1001'){
					var score = $(v).find('.questionItemScore').text();
				}

				$('.items-body').append(fn_modal_item_add(i+1,nm,score ));
			});
		}
		else{
			$('.items-body').append(fn_modal_item_add("1","","" ));
		}

		if(selectQestnsType=='1001'){
			$('.layer-item-score').show();
		} else if(selectQestnsType=='2001'){
			$('.layer-item-score').hide();
		}

		fn_item_sort();
		$('#modal-item').modal('show');

	}

	function fn_item_add() {
		$('.items-body').append(fn_modal_item_add("","",""));
		if(selectQestnsType=='1001'){
			$('.layer-item-score').show();
		} else if(selectQestnsType=='2001'){
			$('.layer-item-score').hide();
		}
		fn_item_sort();
	}

	function fn_item_sort() {
		$($('.items-body').children()).each(function(i,v) {
			$(v).find('.item-order').val(i+1);
		});
	}

	function fn_item_save() {
		var qestnsSeq = $('.modal-qid').val();
		$('.question-'+qestnsSeq).find('.questionItemShow').html("");
		var qestnsType = $('.question-'+qestnsSeq).find("select[name=qestnsType]").val();
		$($('.items-body').children()).each(function(i,v) {
			var questionItemList = $('<span class="questionItemList" style="margin-right:20px;">').html("○&nbsp;");
			$('<span class="questionItemNm"/>').html($(v).find('.item-nm').val()).appendTo(questionItemList);
			if(qestnsType=='1001'){
				$('<span class="questionItemScore"/>').html("("+$(v).find('.item-score').val()+")").appendTo(questionItemList);
			} else {
				$('<span class="questionItemScore" style="display:none"/>').html("(0)").appendTo(questionItemList);
			}
			$('.question-'+qestnsSeq).find('.questionItemShow').append(questionItemList);
		});
		$('#modal-item').modal('hide');


	}

	function fn_modal_item_add(order, nm, score, qestnsType) {
		var tr = $('<tr/>').addClass('text-center');
		$('<td/>').append($('<input type="text" class="item-order text-center" style="width:98%; background:#ddd;" placeholder="순서자동" readonly/>').val(order)).appendTo(tr);
		$('<td/>').append($('<input type="text" class="item-nm" style="width:98%;" placeholder="" />').val(nm)).appendTo(tr);
		$('<td class="layer-item-score"/>').append($('<input type="text" class="item-score text-center" style="width:98%;" placeholder="" />').val(score)).appendTo(tr);
		$('<td/>').append($('<button type="button" class="btn btn-secondary btn-xs btn-item" onclick="fn_question_item_delete(this)">삭제</button>')).appendTo(tr);
		return tr;
	}

	function fn_question_item_delete(obj) {
		$(obj).parent().parent().remove();
		fn_item_sort();
	}




	var nextTrId = 0;
	function fnNextTrId() {
		nextTrId++;
		return nextTrId;
	}

	function fn_question_add() {
		$('.question-no').hide();

		var trCnt = fnNextTrId();
		var tr = '';
		tr += '<tr class="question-row text-center question-'+trCnt+'">';
		tr += '	<td><span class="q-idx">'+trCnt+'</span><input type="hidden" name="qestnsSeq" value="" /></td>';
		tr += '<td><input type="text" name="qestnsName" value="" style="width:98%;" placeholder="질문내용을 입력해주세요."/></td>';
		tr += '<td>';
		tr += '<select name="qestnsType" style="width:98%;"  onchange="fn_question_type(this)">';
		tr += questionTypeOptions.join("");
		tr += '</select>';
		tr += '</td>';
		tr += '<td class="text-left">';
		tr += '<span class="questionEmpty">질문유형을 선택해주세요.</span>';
		tr += '<button type="button" class="btn btn-secondary btn-xs questionItemBtn" onclick="fn_question_add_item(this)" style="margin-right:20px; display:none">+</button>';
		tr += '<span class="questionItemShow" style="display:none"></span>';
		tr += '<input type="text" class="qestnsDesc" value="" style="width:98%; display:none;" placeholder="50자 내외로 입력하세요."/>';
		tr += '</td>';
		tr += '<td>';
		tr += '<button type="button" class="btn btn-secondary btn-xs" onclick="fn_question_delete(this)">삭제</button>';
		tr += '</td>';
		tr += '</tr>';

		$('#serveyQuestionTable > tbody').append(tr);
	}

	function fn_question_delete(obj) {
		$('#themaVO').append($("<input type='hidden' name='delQestnsSeq'/>").val($(obj).parent().parent().find("input[name=qestnsSeq]").val()));
		$(obj).parent().parent().remove();
		var trCnt = $('.question-row').length;
		if(trCnt<1){
			$('.question-no').show();
		}
	}

	function fn_save() {
	<c:choose>
		<c:when test="${possible ne 'Y' }">
		alert("설문 참여자있어 수정이 불가능 합니다.");
		
		</c:when>
		<c:otherwise>
		if(!$('#orgNo').val()){
			alert("등록기관명을 선택해주세요."); $('#orgNo').focus(); return ;
		}
		if(!$('#qestnarName').val()){
			alert("설문명을 입력해주세요."); $('#qestnarName').focus(); return ;
		}

		var filePath = $.trim($('[type="file"]').val());
		if (!$("#orginFileName").html() && !filePath) {
			alert("설문대표이미지를 선택해 주세요.");
			return;
		}

		if(!$('#qestnarOpertype').val()){
			alert("설문 기간설정을 선택해주세요."); $('#qestnarOpertype').focus(); return ;
		}

		if($('#qestnarOpertype').val()=='2001'){
			if(!$('#qestnarOpersdate').val()){
				alert("설문기간을 입력해주세요."); $('#qestnarOpersdate').focus(); return ;
			}
			if(!$('#qestnarOperedate').val()){
				alert("설문기간을 입력해주세요."); $('#qestnarOperedate').focus(); return ;
			}
		}
		else{
			if($('#openYn').val()=='Y'){
				if(!$('#qestnarOpenDate').val()){
					alert("설문 오픈일시를 입력해주세요."); $('#qestnarOpenDate').focus(); return ;
				}
			}
		}

		$('input.clear').remove(); // history.back 되었을때 중복으로 입력 방지
		$('.question-row').each(function(i,v) {
			var id = "item_" + i;
			var qestnsType = $(v).find('select[name=qestnsType]').val();
			if(qestnsType=='0000'){
				$('#themaVO').append($('<input name="'+id+'_nm" type="hidden" class="clear"/>').val($(v).find('.qestnsDesc').val()));
				$('#themaVO').append($('<input name="'+id+'_score" type="hidden" class="clear"/>').val(0));
			}
			else if(qestnsType=='3001'){
			}
			else{
				$(v).find('.questionItemList').each(function(ii,vv) {
					$('#themaVO').append($('<input name="'+id+'_nm" type="hidden" class="clear"/>').val($(vv).find('.questionItemNm').text()));
					$('#themaVO').append($('<input name="'+id+'_score" type="hidden" class="clear"/>').val($(vv).find('.questionItemScore').text()));
				});
			}
		});

		var msg = "등록하시겠습니까?";
		if ($.trim($("#qestnarId").val()) != "0") {
			msg = "수정하시겠습니까?";
		}
		if (confirm(msg)) {
			$("#themaVO").submit();
		}		
		
		</c:otherwise>
	</c:choose>
	}
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
<form:form commandName="themaVO" name="themaVO" action="./themaSave" method="post" enctype="multipart/form-data">
<form:hidden path="comcd"/>
<form:hidden path="qestnarId"/>
<form:hidden path="qestnarImgfilnb"/>
<form:hidden path="useYn"/>
	                <table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
							<c:if test="${themaVO.qestnarId > 0}">
							<tr>
								<th>설문 고유번호</th>
								<td class="left"><c:out value="${themaVO.qestnarId}"/></td>
								<td colspan="2"></td>
							</tr>
							</c:if>
							<tr>
								<th>등록기관명<span class="pilsu">*</span></th>
								<td class="left">
									<hisco:OrgList defaultNo="${themaVO.orgNo}" id="orgNo"/>
									<div><form:errors path="orgNo" cssClass="error" /></div>
								</td>
								<td colspan="2"></td>
							<% /*
								<th>설문주제등록일자</th>
								<td class="left">
			                      	<form:input path="qestnarDate" maxlength="10" cssClass="form-control calendar" style="width:140px;float:left" autocomplete="off" readonly="true"/>
				                    <div><form:errors path="qestnarDate" cssClass="error" /></div>
								</td>
								*/ %>
							</tr>
							<tr>
								<th>설문명<span class="pilsu">*</span></th>
								<td class="left">
									<form:input path="qestnarName" maxlength="200" cssClass="form-control"/>
									<div><form:errors path="qestnarName" cssClass="error" /></div>
								</td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<th>설문대표이미지<span class="pilsu">*</span></th>
								<td class="left"  colspan="3">
									<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control"/>
			   						<div><form:errors path="qestnarImgfilnb" cssClass="error" /></div>
			   						<div><font color='red'>※ 권장하는 크기는 540ⅹ470 픽셀입니다.</font></div>
			   						<c:if test="${!empty themaVO.orginFileName }">
			   							<div id="orginFileName">※ 현재 파일 : ${themaVO.orginFileName}</div>
			   						</c:if>
								</td>
							</tr>
							<tr>
								<th>설문 안내설명</th>
								<td class="left" colspan="3">
									<form:textarea path="qestnarGuide" cssClass="form-control" style="height:150px" maxlength="100" placeholder="100자 내외로 입력하세요."/>
									<div><form:errors path="qestnarGuide" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th>설문 기간설정<span class="pilsu">*</span></th>
								<td class="left">
									<select id="qestnarOpertype" name="qestnarOpertype" class="form-control" style="width:150px">
				              			<option value="">::::선택::::</option>
				              			<option value="1001" <c:if test="${themaVO.stdrmng.qestnarOpertype eq '1001'}">selected</c:if>>상시운영</option>
				              			<option value="2001" <c:if test="${themaVO.stdrmng.qestnarOpertype eq '2001'}">selected</c:if>>기간설정</option>
			              			</select>
								</td>
								<th>설문기간</th>
								<td class="left">
									<div class="input-group date  " style="float:left;width:45%" data-target-input="nearest">
			                        	<input id="qestnarOpersdate" name="qestnarOpersdate" class="form-control calendar" type="text" value="<c:out value="${themaVO.stdrmng.qestnarOpersdate}"/>" autocomplete="off">
			                        	<div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    	</div>
			                    	<div class="input-group text-center  " style="float:left;width:5%">
			                    	~
			                    	</div>
									<div class="input-group date  " style="float:left;width:45%" data-target-input="nearest">
			                        	<input id="qestnarOperedate" name="qestnarOperedate" class="form-control calendar" type="text" value="<c:out value="${themaVO.stdrmng.qestnarOperedate}"/>" autocomplete="off">
			                        	<div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    	</div>
								</td>
							</tr>
							<tr>
								<th>설문 오픈일시</th>
								<td class="left">
									<div class="input-group date  " style="float:left;width:45%" data-target-input="nearest">
			                        	<input id="qestnarOpenDate" name="qestnarOpenDate" class="form-control calendar" type="text" value="<c:out value="${themaVO.stdrmng.qestnarOpenDate}"/>" autocomplete="off">
			                        	<div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    	</div>

									<select id="qestnarOpenTimeHH" name="qestnarOpenTimeHH" class="form-control" style="width:120px;float:left;margin-left:5px">
				              			<option value="">::선택::</option>
				              			<c:forEach items="${qestnarOpenTimeHH}" var="item" varStatus="status">
				              			<option value="${item.code }" <c:if test="${themaVO.stdrmng.qestnarOpenTimeHH eq item.code}">selected</c:if>>${item.codeNm }</option>
				              			</c:forEach>
			              			</select>
									<select id="qestnarOpenTimeMM" name="qestnarOpenTimeMM" class="form-control" style="width:120px;float:left;margin-left:5px">
				              			<option value="">::선택::</option>
				              			<c:forEach items="${qestnarOpenTimeMM}" var="item" varStatus="status">
				              			<option value="${item.code }" <c:if test="${themaVO.stdrmng.qestnarOpenTimeHH eq item.code}">selected</c:if>>${item.codeNm }</option>
				              			</c:forEach>
			              			</select>
								</td>
								<th>공개여부</th>
								<td class="left">
									<select id="openYn" name="openYn" class="form-control" style="width:120px">
				              			<option value="Y" <c:if test="${themaVO.stdrmng.openYn eq 'Y'}">selected</c:if>>공개</option>
				              			<option value="N" <c:if test="${themaVO.stdrmng.openYn ne 'Y'}">selected</c:if>>비공개</option>
			              			</select>
								</td>
							</tr>

<% /*
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

							</tr>

							<c:if test="${themaVO.qestnarId > 0}">
							<tr>
								<th>등록자/일시</th>
								<td class="left">
									${themaVO.reguser}/${themaVO.regdate}
								</td>
								<th>수정자/일시</th>
								<td class="left">
									${themaVO.moduser}/${themaVO.moddate}
								</td>
							</tr>
							</c:if>
*/ %>
							<tr>
								<th class="">질문
								</th>
								<td colspan="3">
									<table id="serveyQuestionTable" style="width:100%" class="table table-bordered sm-table">
						               	<colgroup>
											<col style="width:50px">
											<col style="width:200px">
											<col style="width:150px">
											<col style="">
											<col style="width:70px">
										</colgroup>
										<thead class="bg-lightblue">
											<tr class="text-white">
												<td>No.</td>
												<td>질문내용</td>
												<td>질문유형</td>
												<td>질문선택항목</td>
												<td><button type="button" class="btn btn-default btn-xs btn-item" onclick="fn_question_add('')">추가</button></td>
											</tr>
										</thead>
										<tbody>
											<tr class="question-no">
												<td colspan="5" class="text-center">질문을 추가해주세요.</td>
											</tr>
								<c:if test="${questionList!=null  && fn:length(questionList)>0}">
									<c:forEach items="${questionList}" var="q" varStatus="status">

											<tr class="question-row text-center question-<c:out value="${status.index+1}"/>">
												<td><span class="q-idx"><c:out value="${status.index+1}"/></span><input type="hidden" name="qestnsSeq" value="<c:out value="${q.qestnsSeq }"/>" /></td>
										 			<td><input type="text" name="qestnsName" value="<c:out value="${q.qestnsName }"/>" style="width:98%;" placeholder="질문내용을 입력해주세요."/></td>
												<td>
													<select name="qestnsType" style="width:98%;" onchange="fn_question_type(this)">
														<option value="">= 선택 =</option>
													<c:forEach items="${questionTypeList}" var="qt" varStatus="statust">
														<option value="${qt.cd }" <c:if test="${q.qestnsType eq qt.cd }">selected</c:if> >${qt.cdNm }</option>
													</c:forEach>
													</select>
												</td>
												<td class="text-left">
													<span class="questionEmpty" style="display:none;">질문유형을 선택해주세요.</span>
										<c:choose>
										 	<c:when test="${q.qestnsType eq '0000'}">
													<button type="button" class="btn btn-secondary btn-xs questionItemBtn" onclick="fn_question_add_item(this)" style="margin-right:20px; display:none;">+</button>
										 			<span class="questionItemShow"></span>
												<c:choose>
													<c:when test="${fn:length(q.itemList)>0}">
										 				<input type="text" class="qestnsDesc" value="<c:out value="${q.itemList[0].qestnarItemnm }"/>" style="width:98%;" placeholder="50자 내외로 입력하세요."/>
										 			</c:when>
										 			<c:otherwise>
										 				<input type="text" class="qestnsDesc" value="" style="width:98%;" placeholder="50자 내외로 입력하세요."/>
										 			</c:otherwise>
										 		</c:choose>
										 	</c:when>
										 	<c:when test="${q.qestnsType eq '3001'}">
													<button type="button" class="btn btn-secondary btn-xs questionItemBtn" onclick="fn_question_add_item(this)" style="margin-right:20px; display:none;">+</button>
										 			<span class="questionItemShow"></span>
										 			<input type="text" class="qestnsDesc" value="" style="width:98%; display:none;" placeholder="50자 내외로 입력하세요."/>
										 	</c:when>
										 	<c:otherwise>
													<button type="button" class="btn btn-secondary btn-xs questionItemBtn" onclick="fn_question_add_item(this)" style="margin-right:20px; ">+</button>
										 			<span class="questionItemShow">
										 			<c:forEach items="${q.itemList}" var="qi" varStatus="status">
										 				<span class="questionItemList" style="margin-right:20px;">
										 					○
										 					<span class="questionItemNm"><c:out value="${qi.qestnarItemnm }"/></span>
										 					<c:if test="${q.qestnsType eq '1001' }">
										 					(<span class="questionItemScore">${qi.qestnarScore }</span>)
										 					</c:if>
										 				</span>
										 			</c:forEach>
										 			</span>
										 			<input type="text" class="qestnsDesc" value="" style="width:98%; display:none;" placeholder="50자 내외로 입력하세요."/>
										 	</c:otherwise>
										 </c:choose>
										 		</td>
												<td>
													<button type="button" class="btn btn-secondary btn-xs btn-item" onclick="fn_question_delete(this)">삭제</button>
												</td>
											</tr>
									</c:forEach>
								</c:if>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
</form:form>
					<div class="card-footer">
	                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
	                  <c:choose>
	                  	<c:when test="${themaVO.qestnarId eq '0' }">
	                  		<button type="button" class="btn btn-info float-right" onclick="fn_save()">등록하기</button>
	                  	</c:when>
	                  	<c:otherwise>
	                  		<button type="button" class="btn btn-info float-right" onclick="fn_save()">수정하기</button>
	                  	</c:otherwise>
	                  </c:choose>
	                </div>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
        </div>

      </div> <!-- /.container-fluid -->
	</section>


	<!-- 모달 박스 -->
	<div class="modal fade" id="modal-item" >
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">선택항목(점수) 등록수정</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				</div>
				<div class="modal-body">
					<div>
						<span class="p-question">질문명</span>
						<input type="hidden" class="modal-qid"/>
						<span class="float-right">
							<button type="button" class="btn btn-secondary btn-xs" onclick="fn_item_add()">+ 항목추가</button>
						</span>
					</div>

	                <table class="table table-bordered text-nowrap sm-table">
		               	<colgroup>
							<col style="width:15%;">
							<col style="">
							<col style="width:20%;"  class="layer-item-score">
							<col style="width:15%;">
						</colgroup>

						<thead class="bg-lightblue">
							<tr class="text-white text-center">
								<td>항목순번</td>
								<td>설문항목명</td>
								<td class="layer-item-score">배점</td>
								<td>삭제</td>
							</tr>
						</thead>
						<tbody class="items-body">
							<tr class="text-center">
								<td><input type="text" class="item-order text-center" value="1" style="width:98%; background:#ddd;" placeholder="순서자동" readonly/></td>
								<td><input type="text" class="item-nm" value="" style="width:98%;" placeholder=""/></td>
								<td class="layer-item-score"><input type="text" class="item-score text-center" value="" style="width:98%;" placeholder=""/></td>
								<td><button type="button" class="btn btn-secondary btn-xs btn-item" onclick="fn_question_item_delete(this)">삭제</button></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer justify-content-between">
					<button type="button" class="btn btn-default " data-dismiss="modal">취소</button>
					<div>
						<button type="button" class="btn btn-primary btn-save" onclick="fn_item_save()">적용</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>