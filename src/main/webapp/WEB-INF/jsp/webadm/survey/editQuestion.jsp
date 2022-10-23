<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	<script type="text/javascript">
	var questionTypeOptions = new Array();
	var nextTrId = 1;
	var MAX_QUESTION_CNT = 10;
	var qestnarId = "${qestnarId}"; //테마아이디
	var questionList = ${questionList}; //db질의한 질문목록

	var questionTypeList = ${questionTypeList};
	$.each(questionTypeList, function(index, item) {
		if (item.cd != "0000") {
			questionTypeOptions.push("<option value='" + item.cd + "'>" + item.cdNm + "</option>");
		}
	});

	var remarkTr = new Array();
	remarkTr.push('<tr class="remark" data-trid="XTRID">');
	remarkTr.push('  <td class="left" colspan="4">');
	remarkTr.push('    <input type="text" id="qestnsName" name="qestnsName" maxlength="200" title="설명" placeholder="설명을 입력하세요" class="form-control"/>');
	remarkTr.push('  </td>');
	remarkTr.push('  <td align="center">');
	remarkTr.push('    <button type="button" class="btn btn-default" onclick="javascript:fnDeleteRemark(this);">설명행 삭제</button>');
	remarkTr.push('  </td>');
	remarkTr.push('</tr>');

	var questionTr = new Array();
	questionTr.push('<tr class="question" data-trid="XTRID">');
	questionTr.push('  <td id="no">XQUESTIONNO</td>');
	questionTr.push('  <td class="left">');
	questionTr.push('    <input type="text" id="qestnsName" name="qestnsName" maxlength="200" title="질문" placeholder="질문을 입력하세요" class="form-control"/>');
	questionTr.push('  </td>');
	questionTr.push('  <td class="left">');
	questionTr.push('    <select id="qestnsType" name="qestnsType" onfocus="javascript:fnFocusQuestionType(this);" onchange="javascript:fnSelectQuestionType(this);" class="form-control">');
	questionTr.push(questionTypeOptions.join(""));
	questionTr.push('    </select>');
	questionTr.push('  </td>');
	questionTr.push('  <td class="left">');
	questionTr.push('    <input type="text" id="qestnarScore" value="" maxlength="3" title="배점" onkeydown="javascript:fnSetNumber(this);" class="form-control"/>');
	questionTr.push('  </td>');
	questionTr.push('  <td align="center">');
	questionTr.push('    <button type="button" class="btn btn-default" onclick="javascript:fnDeleteQuestion(this);">질문 삭제</button>');
	questionTr.push('  </td>');
	questionTr.push('</tr>');

	var itemTr = new Array();
	itemTr.push('<tr class="item" data-parent-trid="XTRID">');
	itemTr.push('  <td>└──</td>');
	itemTr.push('  <td class="left" colspan="4">');
	itemTr.push('    <input type="text" id="questionItem1" name="questionItem1" maxlength="150" placeholder="항목을 입력하세요" class="form-control" style="width:400px"/>');
	itemTr.push('    <input type="text" id="questionItem2" name="questionItem2" maxlength="150" placeholder="항목을 입력하세요" class="form-control" style="width:400px"/>');
	itemTr.push('    <input type="text" id="questionItem3" name="questionItem3" maxlength="150" placeholder="항목을 입력하세요" class="form-control" style="width:400px"/>');
	itemTr.push('    <input type="text" id="questionItem4" name="questionItem4" maxlength="150" placeholder="항목을 입력하세요" class="form-control" style="display:none;width:400px"/>');
	itemTr.push('    <input type="text" id="questionItem5" name="questionItem4" maxlength="150" placeholder="항목을 입력하세요" class="form-control" style="display:none;width:400px"/>');
	itemTr.push('  </td>');
	itemTr.push('</tr>');

	$(document).ready(function() {

		//질문, 질문항목이 있을 경우 동적으로 html을 생성한다.
		<c:if test="${fn:length(questionList) > 2}"><%//json data로 빈값일경우 []%>
		var qNo = 1;
		$.each(questionList, function(idx, question) {
			if (question.qestnsType == "0000") {//설명그룹(remark)
				var remarkTrHtml = remarkTr.join("");
				remarkTrHtml = remarkTrHtml.replace("XTRID", question.qestnsSeq);
				_addRemark(remarkTrHtml);
				$("#qestnsName", $("#questionTable > tbody > tr.remark:last")).val(question.qestnsName);
			} else {//질문
				var questionTrHtml = questionTr.join("");
				questionTrHtml = questionTrHtml.replace("XTRID", question.qestnsSeq);
				questionTrHtml = questionTrHtml.replace("XQUESTIONNO", qNo++);
				_addQuestion(questionTrHtml);

				var lastTr = $("#questionTable > tbody > tr.question:last");
				$("#qestnsName", lastTr).val(question.qestnsName);
				$("#qestnsType", lastTr).val(question.qestnsType);
				if (question.qestnsType == "1001") {//배점
					$("#qestnarScore", lastTr).val(question.qestnarScore);
				} else {
					$("#qestnarScore", lastTr).attr("readonly", true);
				}

				if (question.itemList.length > 0) {//x선다형일경우, 항목목록 출력
					var itemTrHtml = itemTr.join("");
					itemTrHtml = itemTrHtml.replace("XTRID", question.qestnsSeq);
					_addItem(itemTrHtml);

					var lastTr = $("#questionTable > tbody > tr.item:last");
					if (question.qestnsType == "2003") { //5지선다
						$("#questionItem4", lastTr).show();
						$("#questionItem5", lastTr).show();
					}
					if (question.qestnsType == "2002") { //4지선다
						$("#questionItem4", lastTr).show();
					}

					$.each(question.itemList, function(ix, item) {
						$("#questionItem" + (ix+1), lastTr).val(item.qestnarItemnm);
					});
				}
			}
		});
		</c:if>

		nextTrId = parseInt($("#questionTable > tbody > tr:not(.item):last").data("trid"));

		$("#btnSave").click(function() {
			var msg = "등록하시겠습니까?";
			/* if ($.trim($("#qestnarId").val()) != "0") {
				msg = "수정하시겠습니까?";
			} */

			var check = true;

			//input값 체크
			$("input[type=text]").each(function(idx, inputText) {
				if ($(inputText).is(":visible") && !$(inputText).attr("readonly")) {
					if (!$.trim($(inputText).val())) {
						if ($(inputText).attr("title") == "배점") {
							alert("배점을 입력하세요");
						} else {
							alert($(inputText).attr("placeholder"));
						}
						$(inputText).focus();
						check = false;
						return false;
					}
				}
			});
			if (!check) return;

			//배점입력일 경우 총합 100점체크
			var total = 0;
			$("input[type=text]").each(function(idx, inputText) {
				if ($(inputText).is(":visible") && !$(inputText).attr("readonly")) {
					if ($(inputText).attr("title") == "배점") {
						total += parseInt($(inputText).val());
					}
				}
			});
			if (total != 100) {
				alert("배점의 총합은 100 이어야 합니다");
			}

			if (confirm(msg)) {
				var questionList = new Array();
				var allItemList = new Array();
				var qestnsSeq = 1;
				$("#questionTable > tbody > tr").each(function(idx, item) {
					if ($(item).hasClass("remark") || $(item).hasClass("question")) {
						var question = new Object();
						qestnsSeq = $(item).data("trid");
						question.qestnarId = qestnarId;
						question.qestnsSeq = qestnsSeq;
						question.qestnsName = $("#qestnsName", $(item)).val();
						question.qestnsType = "0000"; //remark
						question.qestnarScore = 0;

						if (!$(item).hasClass("remark")) {
							question.qestnsType = $("#qestnsType option:selected", $(item)).val();
							var score = $("#qestnarScore", $(item)).val();
							if (!score) score = 0;
							question.qestnarScore = parseInt(score);
						}

						questionList.push(question);
					} else { //item
						for (var i=1; i<=5; i++) { //최대 5지선다.
							if ($("#questionItem" + i, $(item)).is(":visible")) { //보여지는 것만 저장하도록
								var questionItem = $("#questionItem" + i, $(item)).val();
								//console.log(i + "item = " + questionItem);
								if (typeof questionItem != "undefined") {
									var qitem = new Object();
									qitem.qestnarId = qestnarId;
									qitem.qestnsSeq = qestnsSeq;
									qitem.qestnarItemseq = i;
									qitem.qestnarItemnm = questionItem;
									allItemList.push(qitem);
								}
							}
						}
					}
				});

				$.each(questionList, function(i, question) {
					var itemList = new Array();
					$.each(allItemList, function(j, qitem) {
						if (question.qestnarId == qitem.qestnarId && question.qestnsSeq == qitem.qestnsSeq) {
							itemList.push(qitem);
						}
					});
					if (itemList.length > 0) {
						question.itemList = itemList;
					}
				});
				//console.log(JSON.stringify(questionList));

				$.ajax({
					url : "<c:url value='/webadm/survey/saveQuestion.json'/>",
					type : 'POST',
					data : JSON.stringify(questionList),
					contentType : "application/json; charset=UTF-8",
					success : function(data) {
						if (data.result == "SUCCESS") {
							alert("정상적으로 저장하였습니다.");
						} else {
							alert("저장에 실패하였습니다.");
						}
					},
					error : function(res) {
						__ajaxErrorCallback__(res, "오류가 발생하였습니다.\n다시 확인해 주시기 바랍니다.");
					}
				});
			}
		});
	})

	function fnAddRemark() {
		var trCnt = $("#questionTable tr").length;
		//console.log("trCnt = " + trCnt);

		if ($("#questionTable > tbody > tr:last").hasClass("remark")) {
			alert("연속해서 설명을 입력할 수 없습니다.");
			return;
		}

		remarkTrHtml = remarkTr.join("");
		remarkTrHtml = remarkTrHtml.replace("XTRID", fnNextTrId());

		_addRemark(remarkTrHtml);
	}

	function _addRemark(remarkTrHtml) {
		$("#questionTable > tbody:last").append(remarkTrHtml);
	}

	function fnNextTrId() {
		nextTrId++;
		return nextTrId;
	}

	function fnAddQuestion() {
		var nextQuestionNo = $("#questionTable tr td#no").length + 1;
		var trCnt = $("#questionTable > tbody > tr").length;
		var remarkCnt = $("#questionTable > tbody > tr.remark").length;
		var questionCnt = trCnt - remarkCnt;

		if (questionCnt >= MAX_QUESTION_CNT) {
			alert("입력할 수 있는 최대 질문은 " + MAX_QUESTION_CNT + "건 입니다.");
			return;
		}

		questionTrHtml = questionTr.join("");
		questionTrHtml = questionTrHtml.replace("XTRID", fnNextTrId());
		questionTrHtml = questionTrHtml.replace("XQUESTIONNO", nextQuestionNo);

		_addQuestion(questionTrHtml);
	}

	function _addQuestion(questionTrHtml) {
		$("#questionTable > tbody:last").append(questionTrHtml);
	}

	var _curType = "";
	function fnFocusQuestionType(obj) {
		_curType = $(obj).val();
	}

	function fnSelectQuestionType(obj) {
		var trid = $(obj).parent().parent().data("trid");
		var selType = $(obj).val();

		//선다형일경우 항목추가 아닌경우 항목삭제
		if ("2001,2002,2003".indexOf(selType) > -1) { //3~5선다형
			fnAddItem(trid, selType);
		} else {
			fnDeleteItem(trid);
		}

		//별점항목 제어
		if ("1001" == selType) {//별점.점수입력
			$("#qestnarScore", $(obj).parent().parent()).attr("readonly", false).val("").focus();
		} else {
			$("#qestnarScore", $(obj).parent().parent()).attr("readonly", true).val("");
		}

		//번호조정
		fnRefreshNo();
	}

	function fnAddItem(trid, selType) {
		itemTrHtml = itemTr.join("");
		itemTrHtml = itemTrHtml.replace("XTRID", trid);

		if ("2001,2002,2003".indexOf(_curType) > -1) { //3~5선다형
			//변경
			_adjustQuestionItem(trid, selType);
		} else {
			fnDeleteItem(trid);

			//추가
			$("#questionTable > tbody > tr").each(function() {
				if ($(this).data("trid") == trid) {
					$(this).after(itemTrHtml);

					_adjustQuestionItem(trid, selType);
					return false;
				}
			});
		}
	}

	function _adjustQuestionItem(trid, selType) {
		$("#questionTable > tbody > tr.item").each(function() {
			if ($(this).data("parent-trid") == trid) {
				if (selType == "2001") { //3지선다
					$("#questionItem4", $(this)).hide();
					$("#questionItem5", $(this)).hide();
				} else if (selType == "2002") { //4지선다
					$("#questionItem4", $(this)).show();
					$("#questionItem5", $(this)).hide();
				} else if (selType == "2003") { //5지선다
					$("#questionItem4", $(this)).show();
					$("#questionItem5", $(this)).show();
				}
				return false;
			}
		});
	}

	function _addItem(itemTrHtml) {
		$("#questionTable > tbody:last").append(itemTrHtml);
	}

	function fnDeleteRemark(obj) {
		var trLength = $("#questionTable > tbody > tr").length;
		if (trLength == 1) {
			alert("삭제할 수 없는 설명행입니다.");
			return;
		}
		$(obj).parent().parent().remove();
	}

	function fnDeleteQuestion(obj) {
		fnDeleteItem($(obj).parent().parent().data("trid"));
		$(obj).parent().parent().remove();
		//번호조정
		fnRefreshNo();
	}

	function fnDeleteItem(trid) {
		//하위항목 삭제
		$("#questionTable > tbody > tr").each(function() {
			if ($(this).data("parent-trid") == trid) {
				$(this).after().remove();
				return false;
			}
		});
	}

	function fnRefreshNo() {
		var no = 1;
		$("#questionTable > tbody > tr").each(function() {
			if ($("#no", $(this)).text()) {
				$("#no", $(this)).text(no);
				no++;
			}
		});
	}

	function fnSetNumber(obj) {
		$(obj).number(true);
		if (!$(obj).val()) $(obj).val(0);
	}
	</script>
</head>
<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>설문지 내용현황 <small> > ${themaVO.qestnarName}</small></h1>
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
              <div class="card-body  table-responsive p-0">
					<input type="hidden" name="qestnarId" id="qestnarId" value="${qestnarId}"/>
	                <table id="questionTable" class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:10%;">
							<col style="width:45%;">
							<col style="width:15%;">
							<col style="width:15%;">
							<col style="width:15%;">
						</colgroup>
						<thead align="center">
		                    <tr bgcolor="#EEEEEE">
		                      	<th>번호</th><!-- 번호 -->
		                      	<th>질문명</th>
								<th>답변유형</th>
								<th>배점</th>
								<th>삭제</th>
		                    </tr>
		                </thead>
						<tbody>
							<c:if test="${fn:length(questionList) <= 2}">
							<tr class="remark" data-trid="1">
								<td class="left" colspan="4">
									<input type="text" id="qestnsName" name="qestnsName" maxlength="200" title="설명" placeholder="설명을 입력하세요." class="form-control"/>
								</td>
								<td align="center">
									<button type="button" class="btn btn-default" onclick="javascript:fnDeleteRemark(this);">설명행 삭제</button>
								</td>
							</tr>
							</c:if>
						</tbody>
					</table>

					<table class="table table-bordered text-nowrap">
		               	<tbody>
							<tr align="center" bgcolor="#AAAAAA">
								<td>
									<button type="button" onclick="javascript:fnAddRemark();" class="btn btn-default"><strong>설명 행추가</strong></button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" onclick="javascript:fnAddQuestion();" class="btn btn-default"><strong>질문 행추가</strong></button>
								</td>
							</tr>
						</tbody>
					</table>

					<br/>
					<c:if test="${not empty questionVO}">
					<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:20%;">
							<col style="width:30%;">
							<col style="width:20%;">
							<col style="width:30%;">
						</colgroup>
						<tbody>
							<tr>
								<th>등록자/일시</th>
								<td class="left">${themaVO.reguser}|${themaVO.regdate}</td>
								<th>수정자/일시</th>
								<td class="left">${questionVO.reguser}|${questionVO.regdate}</td>
							</tr>
						</tbody>
					</table>
					</c:if>

					<div class="card-footer">
	                  <button type="button" class="btn btn-default" onclick="history.back();">취소</button>
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