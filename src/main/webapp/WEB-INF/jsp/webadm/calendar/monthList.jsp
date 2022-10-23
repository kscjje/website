<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  parentList.jsp
  * @Description : 공통코드그룹목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27  이윤호            최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.27
  *  @version 1.0
  *  @see
  *
  */
%>
<head>

<script type="text/javascript" src="../validator"></script>
<script>
$(document).ready(function () {
	$( ".datepicker" ).datepicker({
		"dateFormat":'yy.mm.dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});
});

//등록
function fn_add(calDate) {
	$('#modal-default').find(".modal-body").html("");
	$('#modal-default').find(".modal-title").html("기준 월력 등록");
	$.ajax({
	      url: './monthListFromAjax',
	      data : {'calDate':calDate,  'MODE':'INSERT'},
	      type : "GET",
	      dataType : "html",
	      success: function(data) {
	    	  if(data.indexOf("login_area") > 0 ){
	    		  __ajaxHtmlCallback__();
	    	  	}else{
	         		$('#modal-default').find(".modal-body").html(data);
	    	  	}
	      }
	    });
}

//수정
function fn_mod(calDate, orgNo, gubun, dateType) {
	$('#modal-default').find(".modal-body").html("");
	$('#modal-default').find(".modal-title").html("기준 월력 등록");
	$.ajax({
	      url: './monthListFromAjax',
	      data : {'calDate':calDate, 'orgNo':orgNo, 'gubun':gubun, 'dateType':dateType,  'MODE':'UPDATE'},
	      type : "GET",
	      dataType : "html",
	      success: function(data) {
	    	  if(data.indexOf("login_area") > 0 ){
	    		  __ajaxHtmlCallback__();
	    	  	}else{
	         		$('#modal-default').find(".modal-body").html(data);
	    	  	}
	      }
	    });
}


function fn_save() {
	if (!$('#calendarInfoVO').find('#calDate').val()) {
		alert("날짜를 입력해 주세요.");
		return;
	} else if (!$('#calendarInfoVO').find('#dateType').val()) {
			alert("휴일구분을 선택해 주세요.");
			return;
	} else if (!$('#calendarInfoVO').find('#calendarEtc').val()) {
		alert("비고를 입력해 주세요.");
		$('#calendarInfoVO').find('#calendarEtc').focus();
		return;
	} else {
		var msg = "월력 정보를 입력하시겠습니까?";
		if ($('#calendarInfoVO').find('input[name=MODE]').val() == "UPDATE") {
			msg = "월력 정보를 수정하시겠습니까?";
		}
		if (confirm(msg)) {
			$.ajax({
			      url: './monthListFormSave.json',
			      data : $('#calendarInfoVO').serialize(),
			      type : "POST",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);
					         top.location.reload();
				         }
			      }
			});

		}
	}
}


function fn_delete() {
	var form = document.calendarInfoVO;

	var msg = "삭제된 데이터는 복구되지 않습니다.\n정말 삭제하시겠습니까?";
	if (confirm(msg)) {
		$.ajax({
		      url: './monthListFormDelete.json',
		      data : $('#calendarInfoVO').serialize(),
		      type : "POST",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	 alert(data.result.msg);
				         top.location.reload();
			         }
		      }
		});

	}
}

function fn_delete_all() {
	var $form = $('<form/>');
	$('<input type="hidden" name="yyyy">').val('<c:out value="${fn:substring(ym,0,4) }"/>').appendTo($form);
	$('<input type="hidden" name="orgNo">').val(orgNo).appendTo($form);

	var msg = "해당연도(<c:out value="${fn:substring(ym,0,4) }"/>)에 입력되어있는 모든 일정을 정말 삭제하시겠습니까? 삭제된 데이터는 복구되지 않습니다.";
	if (confirm(msg)) {
		$.ajax({
		      url: './monthListFormDeleteAll.json',
		      data : $form.serialize(),
		      type : "POST",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	 alert(data.result.msg);
				         top.location.reload();
			         }
		      }
		});

	}
}
//일괄등록 저장
function fn_save_group() {
	if (!$('#groupVO').find('#sdate').val()) {
		alert("시작일을 입력해 주세요.");
		return;
	} else if (!$('#groupVO').find('#edate').val()) {
		alert("종료일을 입력해 주세요.");
		return;
	} else if ($('#groupVO').find('input[name=week]:checked').length<1) {
		alert("요일을 선택해 주세요.");
		return;
	} else if (!$('#groupVO').find('#dateType').val()) {
		alert("휴일구분을 선택해 주세요.");
		return;
	} else if (!$('#groupVO').find('#calendarEtc').val()) {
		alert("비고를 입력해 주세요.");
		$('#groupVO').find('#calendarEtc').focus();
		return;
	} else {
		var msg = "월력 정보를 일괄 입력하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './monthListGroupSave.json',
			      data : $('#groupVO').serialize(),
			      type : "POST",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);
					         top.location.reload();
				         }
			      }
			});

		}
	}
}
</script>

</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>기준 월력 관리</h1>
          </div>
          <div class="col-sm-6 text-red">
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
					<div class="modal-header justify-content-between">
						<a href="?ym=<c:out value="${prev}"/>" class="btn btn-default "><i class="fas fa-chevron-left"></i></a>
						<h3><c:out value="${fn:substring(ym,0,4) }"/>.<c:out value="${fn:substring(ym,4,6) }"/></h3>
						<a href="?ym=<c:out value="${next}"/>" class="btn btn-default"><i class="fas fa-chevron-right"></i></a>
					</div>


					<div class="card-body  p-0">
						<table class="table table-bordered">
							<colgroup>
								<col style="width: 14.285%;">
								<col style="width: 14.285%;">
								<col style="width: 14.285%;">
								<col style="width: 14.285%;">
								<col style="width: 14.285%;">
								<col style="width: 14.285%;">
								<col style="">
							</colgroup>
							<thead>
								<tr>
								<c:set var="tdColor" value=""/>
								<c:forEach items="${weekNames}" var="weekName" varStatus="status">
									<c:choose>
										<c:when test="${status.index==0}">
											<c:set var="tdColor" value="text-red"/>
										</c:when>
										<c:when test="${status.index==6}">
											<c:set var="tdColor" value="text-blue"/>
										</c:when>
										<c:otherwise>
											<c:set var="tdColor" value=""/>
										</c:otherwise>
									</c:choose>
									<th class="text-center <c:out value="${tdColor }"/>"><c:out value="${weekName }"/></th>
								</c:forEach>
								</tr>
							</thead>
							<tbody class="ov">
							<c:forEach items="${cal}" var="week" varStatus="weeks">
								<tr>
								<c:forEach items="${week}" var="day" varStatus="days">
									<c:choose>
										<c:when test="${days.index==0}">
											<c:set var="tdColor" value="text-red"/>
										</c:when>
										<c:when test="${days.index==6}">
											<c:set var="tdColor" value="text-blue"/>
										</c:when>
										<c:otherwise>
											<c:set var="tdColor" value="text-dark"/>
										</c:otherwise>
									</c:choose>
									<c:if test="${day.ym ne ym}">
										<c:set var="tdColor" value="text-muted"/>
									</c:if>

									<c:choose>
										<c:when test="${day.schedule != null && fn:length(day.schedule)>0}">
									<td >
										<div class="text-right">
											<c:if test="${day.day eq today}">
												<span class="badge bg-secondary">today</span>&nbsp;&nbsp;
											</c:if>
											<a href="javascript:" onclick="fn_add('<c:out value="${day.day}"/>')" class="<c:out value="${tdColor }"/>" data-toggle="modal" data-target="#modal-default"><c:out value="${day.dd}"/></a>
										</div>
										<br/>
										<div>
											<c:if test="${day.schedule != null && fn:length(day.schedule)>0}">
												<c:forEach items="${day.schedule}" var="s" varStatus="ss">
													<c:if test="${ss.index>0}"><br></c:if>
													<c:choose>
														<c:when test="${s.dateType eq '02'}">
															<a href="javascript:" onclick="fn_mod('<c:out value="${s.calDate}"/>','<c:out value="${s.orgNo}"/>', '<c:out value="${s.gubun}"/>', '<c:out value="${s.dateType}"/>')" class="" data-toggle="modal" data-target="#modal-default"><c:out value="${s.calendarEtc}"/></a>
														</c:when>
														<c:otherwise>
															<a href="javascript:" onclick="fn_mod('<c:out value="${s.calDate}"/>','<c:out value="${s.orgNo}"/>', '<c:out value="${s.gubun}"/>', '<c:out value="${s.dateType}"/>')" class="" data-toggle="modal" data-target="#modal-default">휴관일(<c:out value="${s.calendarEtc}"/>)</a>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:if>
										</div>
									</td>
										</c:when>
										<c:otherwise>
									<td onclick="fn_add('<c:out value="${day.day}"/>')" data-toggle="modal" data-target="#modal-default" style="cursor:pointer;">
										<div class="text-right">
											<c:if test="${day.day eq today}">
												<span class="badge bg-secondary">today</span>&nbsp;&nbsp;
											</c:if>
											<a href="javascript:" class="<c:out value="${tdColor }"/>"><c:out value="${day.dd}"/></a>
										</div>
										<br/>
									</td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>

					<div class="modal-footer text-right">
							<a href="javascript:" onClick="fn_delete_all()" class="btn btn-danger">모두 삭제</a>
							<a href="javascript:"  class="btn btn-secondary"  data-toggle="modal" data-target="#modal-default-group">일괄입력</a>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- 모달 박스 -->
	<div class="modal fade" id="modal-default" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Default Modal</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				</div>
				<div class="modal-body">
					<p>One fine body…</p>
				</div>
				<div class="modal-footer justify-content-between">
					<button type="button" class="btn btn-default " data-dismiss="modal">취소</button>
					<div>
						<button type="button" class="btn btn-danger btn-delete" onclick="fn_delete()">삭제</button>
						<button type="button" class="btn btn-primary btn-save" onclick="fn_save()">저장</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 모달 박스 -->
	<div class="modal fade" id="modal-default-group" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">일괄입력</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				</div>
				<div class="modal-body">
<form id="groupVO" name="groupVO" method="post" >
	<div class="row">
		<table id="modaltable" class="table table-bordered">
			<colgroup>
				<col width="25%">
				<col width="*">
			</colgroup>
			<tr>
				<th>날짜</th>
				<td>
					<input name="sdate" id="sdate" class="form-control datepicker" style="width:45%; display:inline-block; " placeholder="시작일"/>
					~
					<input name="edate" id="edate" class="form-control datepicker" style="width:45%; display:inline-block; " placeholder="종료일"/>
				</td>
			</tr>
			<tr>
				<th>요일</th>
				<td>
					<c:set var="tdColor" value=""/>
					<c:set var="weekTitle" value=""/>
					<c:forEach items="${weekNames}" var="weekName" varStatus="status">
						<c:choose>
							<c:when test="${status.index==0}"><c:set var="tdColor" value="text-red"/></c:when>
							<c:when test="${status.index==6}"><c:set var="tdColor" value="text-blue"/></c:when>
							<c:otherwise><c:set var="tdColor" value=""/></c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${weekName eq 'Sun'}"><c:set var="weekTitle" value="일"/></c:when>
							<c:when test="${weekName eq 'Mon'}"><c:set var="weekTitle" value="월"/></c:when>
							<c:when test="${weekName eq 'The'}"><c:set var="weekTitle" value="화"/></c:when>
							<c:when test="${weekName eq 'Wed'}"><c:set var="weekTitle" value="수"/></c:when>
							<c:when test="${weekName eq 'Thu'}"><c:set var="weekTitle" value="목"/></c:when>
							<c:when test="${weekName eq 'Fri'}"><c:set var="weekTitle" value="금"/></c:when>
							<c:when test="${weekName eq 'Sat'}"><c:set var="weekTitle" value="토"/></c:when>
						</c:choose>
						<label class="<c:out value="${tdColor }"/>">
							<input type="checkbox" name="week" value="<c:out value="${status.index+1 }"/>"/> <c:out value="${weekTitle }"/>&nbsp;&nbsp;
						</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>휴일구분(*)</th>
				<td>
                   	 <select name="dateType" id="dateType" class="form-control">
                   	 <c:forEach items="${dateTypeList}" var="dateItem" varStatus="status">
                   	 		<option value="${dateItem.cd }">${dateItem.cdNm }</option>
  						</c:forEach>
                   	 </select>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td>
					<input class="form-control" name="calendarEtc" id="calendarEtc" maxlength="20"/>
				</td>
			</tr>
		</table>
	</div>
 </form>
				</div>
				<div class="modal-footer justify-content-between">
					<button type="button" class="btn btn-default " data-dismiss="modal">취소</button>
					<div>
						<button type="button" class="btn btn-primary btn-save" onclick="fn_save_group()">저장</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


</body>