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
	// 기관 변경
	$('#orgNo').on( "change", function() {
		if($(this).val() == ""){
			alert("기관선택은 필수 입니다.");
		}else{
			$('form[name=orgForm]').submit();
		}
	});
});

// 기관번호
var orgNo ="<c:out value='${searchVO.orgNo}'/>";

//등록
function fn_add(calDate) {
	$('#modal-default').find(".modal-body").html("");
	$('#modal-default').find(".modal-title").html("기관별 월력 등록");
	$.ajax({
	      url: './orgMonthListFromAjax',
	      data : {'calDate':calDate, 'orgNo':orgNo,  'MODE':'INSERT'},
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
	$('#modal-default').find(".modal-title").html("기관별 월력 수정");
	$.ajax({
	      url: './orgMonthListFromAjax',
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
			      url: './orgMonthListFormSave.json',
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
		      url: './orgMonthListFormDelete.json',
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
	if(orgNo == "" || orgNo == "0"){
		alert("기관을 먼저 선택해 주세요.");
		document.searchVO.orgNo.focus();
		return;
	}

	var $form = $('<form/>');
	$('<input type="hidden" name="yyyy">').val('<c:out value="${fn:substring(ym,0,4) }"/>').appendTo($form);
	$('<input type="hidden" name="orgNo">').val(orgNo).appendTo($form);

	var msg = "해당연도(<c:out value="${fn:substring(ym,0,4) }"/>)에 입력되어있는 모든 일정을 정말 삭제하시겠습니까? 삭제된 데이터는 복구되지 않습니다.";
	if (confirm(msg)) {
		$.ajax({
		      url: './orgMonthListFormDeleteAll.json',
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


//기관별 월력 불러오기
function fn_load_copy() {
	$('#modal-default').find(".modal-body").html("");
	$('#modal-default').find(".modal-title").html("월력 관리");

	$.ajax({
	      url: './orgMonthListCopyAjax',
	      data : {'calDate':'<c:out value="${fn:substring(ym,0,4) }"/>', 'orgNo':orgNo},
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



//복사
function fn_copy() {
	if(!$('#yyyyCopy').val()){
		alert("연도를 선택해 주세요.");
		return ;
	}

	var $form = $('<form/>');
	$('<input type="hidden" name="yyyy">').val($('#yyyyCopy').val()).appendTo($form);
	$('<input type="hidden" name="orgNo">').val(orgNo).appendTo($form);

	$.ajax({
	      url: './orgMonthListCopySave.json',
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


//일괄등록
function fn_add_group() {
	$('#modal-default').find(".modal-body").html("");
	$('#modal-default').find(".modal-title").html("기관별 월력 등록");
	$.ajax({
	      url: './orgMonthListGroupAjax',
	      data : {'orgNo':orgNo},
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
			      url: './orgMonthListGroupSave.json',
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
            <h1>기관별 월력 관리</h1>
          </div>
          <div class="col-sm-6 text-red">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>


<!-- Main content -->
<section class="content">
	<div class="container-fluid">

		<form name="orgForm" class="form-horizontal" action="./orgMonthList" method="get" >
			<div class="card-body">
				<div class="row">
					<div class="col-4 text-right">
						<label for="orgNo" class="col-form-label">기관을 선택해주세요.</label>
					</div>
					<div class="col-4">
						<hisco:OrgList defaultNo="${searchVO.orgNo}" id="orgNo" blankYn="Y" />
					</div>
				</div>
			</div>
		</form>

		<div class="row">
			<div class="col-12">
				<div class="card card-primary card-outline">
					<div class="modal-header justify-content-between">
						<a href="?ym=<c:out value="${prev}"/>&orgNo=<c:out value="${searchVO.orgNo }"/>" class="btn btn-default "><i class="fas fa-chevron-left"></i></a>
						<h3><c:out value="${fn:substring(ym,0,4) }"/>.<c:out value="${fn:substring(ym,4,6) }"/></h3>
						<a href="?ym=<c:out value="${next}"/>&orgNo=<c:out value="${searchVO.orgNo }"/>" class="btn btn-default"><i class="fas fa-chevron-right"></i></a>
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
									<td>
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
													<c:set var="aColor" value=""/>

													<c:if test="${ss.index>0}"><br></c:if>
													<c:choose>
														<c:when test="${s.dateType eq '02'}">
															<a href="javascript:" onclick="fn_mod('<c:out value="${s.calDate}"/>','<c:out value="${s.orgNo}"/>', '<c:out value="${s.gubun}"/>', '<c:out value="${s.dateType}"/>')" class="<c:out value="${aColor}"/>" data-toggle="modal" data-target="#modal-default"><c:out value="${s.calendarEtc}"/></a>
														</c:when>
														<c:otherwise>
															<a href="javascript:" onclick="fn_mod('<c:out value="${s.calDate}"/>','<c:out value="${s.orgNo}"/>', '<c:out value="${s.gubun}"/>', '<c:out value="${s.dateType}"/>')" class="<c:out value="${aColor}"/>" data-toggle="modal" data-target="#modal-default">휴관일(<c:out value="${s.calendarEtc}"/>)</a>
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
					<c:choose>
						<c:when test="${searchVO.orgNo > 0 }">
							<a href="javascript:" onClick="fn_delete_all()" class="btn btn-danger">모두 삭제</a>
							<a href="javascript:" onClick="fn_add_group()" class="btn btn-secondary"  data-toggle="modal" data-target="#modal-default">일괄입력</a>
							<a href="javascript:" onClick="fn_load_copy()" class="btn btn-secondary"  data-toggle="modal" data-target="#modal-default">기준월력 불러오기</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:" onClick="alert('기관을 먼저 선택해 주세요.')" class="btn btn-danger">모두 삭제</a>
							<a href="javascript:" onClick="alert('기관을 먼저 선택해 주세요.')" class="btn btn-secondary">일괄입력</a>
							<a href="javascript:" onClick="alert('기관을 먼저 선택해 주세요.')" class="btn btn-secondary">기준월력 불러오기</a>
						</c:otherwise>
					</c:choose>

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
					<button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
					<div>
						<button type="button" class="btn btn-danger btn-delete" onclick="fn_delete()" style="display:none;">삭제</button>
						<button type="button" class="btn btn-primary btn-save" onclick="fn_save()" style="display:none;">저장</button>
						<button type="button" class="btn btn-primary btn-copy" onclick="fn_copy()" style="display:none;">저장</button>
						<button type="button" class="btn btn-primary btn-save-group" onclick="fn_save_group()" style="display:none;">저장</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

</body>
