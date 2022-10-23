<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  orgList.jsp
  * @Description : 공통코드목록 JSP
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
<validator:javascript formName="cmmnDetailCodeVO" staticJavascript="false" xhtml="true" cdata="false"/>

<script>
$(document).ready(function () {
	// 그룹 목록 커서 변경
	$('.tr-grp').css("cursor","pointer").on( "click", function() {
		if($('#orgNo').val() == ''){
			alert('기관을 먼저 선택해주세요.');
			return;
		}
		$('form[name=CcmCodeForm]').find('input[name=grpCd]').val($( this ).data('cd'));
		$('form[name=CcmCodeForm]').submit();
		return ;
	});

	// 기관 변경
	$('#orgNo').on( "change", function() {
		$('form[name=CcmCodeForm]').submit();
		return ;
	});
});
</script>
<c:if test="${grpInfo!=null}">

	<script>
	// 기관번호
	var orgNo  ="0";
	// 그룹코드
	var grpCd ="<c:out value='${grpInfo.grpCd}'/>";

	$(document).ready(function () {
		$('button.disabled').removeClass('disabled');

		orgNo = $('#orgNo').val();
	});

	// 공통코드 수정
	function fn_code_load(cd) {
		if(!orgNo || orgNo==0){
			alert("기관을 선택해주세요.");
			return ;
		}
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("기관별코드 수정");
		$.ajax({
		      url: './orgDetailAjax',
		      data : {'grpCd':grpCd, 'cd':cd, 'orgNo':orgNo, 'MODE':'UPDATE'},
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


	// 공통코드 수정
	function fn_code_add() {
		if(!orgNo || orgNo==0){
			alert("기관을 선택해주세요.");
			return ;
		}
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("기관별코드 등록");
		$.ajax({
		      url: './orgDetailAjax',
		      data : {'grpCd':grpCd, 'orgNo':orgNo,  'MODE':'INSERT'},
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
		var form = document.codeVO;

		if (updYn == "N") {
			alert("수정불가능한 코드 입니다.");
			return;
		}

		if (form.cd.value == "") {
			alert("상세코드를 입력해 주세요.");
			form.cd.focus();
			return;
		} else if (form.cdNm.value == "") {
				alert("상세코드명을 입력해 주세요.");
				form.cdNm.focus();
				return;
		} else if (form.sortOrder.value == "") {
			alert("순서를 입력해 주세요.");
			form.sortOrder.focus();
			return;
		} else if (isNaN(form.sortOrder.value)) {
			alert("순서는 숫자만 입력해 주세요.");
			form.sortOrder.focus();
			return;
		} else {
			var msg = "등록하시겠습니까?";
			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: './orgSave.json',
				      data : $('#codeVO').serialize(),
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
		var form = document.codeVO;

		if (delYn != "Y") {
			alert("삭제불가능한 코드 입니다.");
			return;
		}

		var msg = "삭제된 데이터는 복구되지 않습니다.\n정말 삭제하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './orgDelete.json',
			      data : $('#codeVO').serialize(),
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




	// 공통코드 순서 저장
	function fn_code_order(){
		var $form = $('<form/>');
		$('<input type="hidden" name="grpCd">').val(grpCd).appendTo($form);
		$('<input type="hidden" name="orgNo">').val(orgNo).appendTo($form);
		$.each($('.sublistSort').children(), function (idx, obj) {
			$('<input type="hidden" name="cd">').val($(this).data('cd')).appendTo($form);
			$('<input type="hidden" name="sortOrder">').val($(this).find('.input-sortOrder').val()).appendTo($form);
		});

		$.ajax({
		      url: './orgSort.json',
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




	// 사용여부 저장
	function fn_code_use(cd, useYn){
		var $form = $('<form/>');
		$('<input type="hidden" name="grpCd">').val(grpCd).appendTo($form);
		$('<input type="hidden" name="orgNo">').val(orgNo).appendTo($form);
		$('<input type="hidden" name="cd">').val(cd).appendTo($form);
		$('<input type="hidden" name="useYn">').val(useYn).appendTo($form);

		$.ajax({
		      url: './orgUse.json',
		      data : $form.serialize(),
		      type : "POST",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	 //alert(data.result.msg);
				         //top.location.reload();
			        }
		      }
		});
	}



	// 사용안함 코드만 조회
	function fn_show_use(){
		var yn = $('.check-showUse').prop("checked");
		$.each($('.sublistSort').children(), function (idx, obj) {
			if(yn){
				$(obj).hide();
				if($(this).find('.select-useYn').val()=='Y'){
					$(obj).show();
				}
			}
			else{
				$(obj).show();
			}
		});
	}


	$(document).ready(function () {
		fn_show_use();

		// 사용여부 변경
		$('.select-useYn').on( "change", function() {
			fn_code_use($(this).parent().parent().data('cd'), $(this).val());
			return ;
		});

		// 사용안함 코드만 조회
		$('.check-showUse').on( "click", function() {
			fn_show_use();
			return ;
		});
	});


	</script>
</c:if>

	<script>
	function fnSort(grpCd) {

		$('form[name=CcmCodeForm]').find('input[name=searchOrder]').val(grpCd);
		if('<c:out value="${searchVO.searchOrderDir}"/>'=='ASC'){
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("DESC");
		} else {
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("ASC");
		}
		$('form[name=CcmCodeForm]').submit();
	}
	</script>

</head>


<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>기관별 코드관리</h1>
          </div>
          <div class="col-sm-6 text-red">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>


<!-- Main content -->
<section class="content">
	<div class="container-fluid">



		<form name="CcmCodeForm" class="form-horizontal" action="./orgList" method="get" >
			<input type="hidden" name="grpCd" value="<c:out value="${searchVO.grpCd}"/>"/>
			<input type="hidden" name="searchOrder" value="<c:out value="${searchVO.searchOrder}"/>"/>
			<input type="hidden" name="searchOrderDir" value="<c:out value="${searchVO.searchOrderDir}"/>"/>
			<input type="hidden" name="pageIndex" value="<c:out value="${searchVO.pageIndex}"/>"/>
			<div class="card">
				<div class="card-body">
					<div class="row">
						<div class="col-4">
	                    	<hisco:OrgList defaultNo="${searchVO.orgNo}" id="orgNo" blankYn="N" defaultSelect="true"/>
	                    </div>
						<div class="col-2">
							<select name="searchCondition" title="검색영역" class="form-control">
								<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >그룹명</option><!-- 코드ID명 -->
								<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >그룹코드</option><!-- 코드ID -->
							</select>
						</div>
						<div class="col-4">
							<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-2">
							<button type="submit" class="btn btn-secondary">조회</button>
						</div>
					</div>
				</div>
			</div>
		</form>



		<div class="row">
			<div class="col-6">
			<c:if test="${grpInfo!=null}">
				<div class="row" style="margin-bottom:10px;">
					<div class="col-10 text-left">
						&nbsp;&nbsp;<b>그룹명 : <c:out value='${grpInfo.grpNm}'/></b>
						(그룹코드 : <c:out value='${grpInfo.grpCd}'/>)
					</div>
					<div class="col-2 text-right" style="visibility:hidden">
						<button type="button" class="btn  disabled">임시버튼</button>
					</div>
				</div>
			</c:if>
				<div class="card card-primary card-outline">

					<div class="card-body table-responsive p-0">
						<table class="table table-hover text-nowrap table-grp">
							<colgroup>
								<col style="width: 35%;">
								<col style="">
								<col style="width: 15%;">
								<col style="width: 15%;">
							</colgroup>
							<thead>
								<tr>
									<th class=" board_th_link">
										<a href="javascript:" onclick="fnSort('GRP_CD')">그룹코드
											<c:if test="${searchVO.searchOrder eq 'GRP_CD'}">
												<c:choose>
													<c:when test="${searchVO.searchOrderDir eq 'ASC'}">
														<i class="fas fa-sort-amount-down-alt"></i>
					                         		</c:when>
					                         		<c:otherwise>
														<i class="fas fa-sort-amount-down"></i>
					                         		</c:otherwise>
					                         	</c:choose>
											</c:if>
										</a>
									</th>
									<th class="">
										<a href="javascript:" onclick="fnSort('GRP_NM')">그룹명
											<c:if test="${searchVO.searchOrder eq 'GRP_NM'}">
												<c:choose>
													<c:when test="${searchVO.searchOrderDir eq 'ASC'}">
														<i class="fas fa-sort-amount-down-alt"></i>
					                         		</c:when>
					                         		<c:otherwise>
														<i class="fas fa-sort-amount-down"></i>
					                         		</c:otherwise>
					                         	</c:choose>
											</c:if>
										</a>
									</th>
									<th class="">수정 가능여부</th>
									<th class="">삭제 가능여부</th>
								</tr>
							</thead>
							<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">
								<tr>
									<td colspan="4">데이타가 없습니다</td>
								</tr>
					</c:if>


					<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
								<tr class="tr-grp <c:if test="${grpInfo!=null && grpInfo.grpCd==resultInfo.grpCd}">bg-gray</c:if>" data-cd="<c:out value='${resultInfo.grpCd}'/>">
									<td><c:out value='${resultInfo.grpCd}'/></td>
									<td><c:out value='${resultInfo.grpNm}'/></td>
									<td class="text-center">
										<c:choose>
											<c:when test="${resultInfo.updYn eq 'Y'}">
												O
			                         		</c:when>
			                         		<c:otherwise>
												X
			                         		</c:otherwise>
			                         	</c:choose>
									</td>
									<td class="text-center">
										<c:choose>
											<c:when test="${resultInfo.delYn eq 'Y'}">
												O
			                         		</c:when>
			                         		<c:otherwise>
												X
			                         		</c:otherwise>
			                         	</c:choose>
									</td>
								</tr>
					</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="card-footer p-0">
						<div class="pagination_2">
							<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./childList?pageIndex="/>
						</div>
					</div>
				</div>
			</div>

			<div class="col-6" id="subTop">
			<c:if test="${grpInfo!=null}">
				<div class="row" style="margin-bottom:10px;">
					<div class="col-2 text-left" >
					</div>
					<div class="col-10 text-right">
						<label><input type="checkbox" class="check-showUse" style="vertical-align:middle;" checked/> 사용 코드만 조회&nbsp;&nbsp;</label>
						<button type="button" class="btn btn-info disabled" onclick="fn_code_add()" data-toggle="modal" data-target="#modal-default">코드등록</button>
						<button type="button" class="btn btn-info disabled" onclick="fn_code_order()">목록 순서저장</button>
					</div>
				</div>
			</c:if>
				<div class="card card-primary card-outline">
					<div class="card-body table-responsive p-0">
						<table class="table table-hover text-nowrap">
							<colgroup>
								<col style="width: 25%;">
								<col style="">
								<col style="width: 20%;">
								<col style="width: 20%;">
							</colgroup>
							<thead>
								<tr>
									<th class="text-center">코드</th>
									<th class="text-center">코드명</th>
									<th class="text-center">정렬순서</th>
									<th class="text-center">사용여부</th>
								</tr>
							</thead>
							<tbody class="sublistSort">

			<c:choose>
				<c:when test="${grpInfo==null}">
								<tr>
									<td colspan="4" class="text-center">그룹을 선택해주세요.</td>
								</tr>
				</c:when>
				<c:otherwise>
					<c:if test="${fn:length(orgCdList) == 0}">
								<tr>
									<td colspan="4" class="text-center">데이타가 없습니다 <input type="hidden" name="select-useYn" class="select-useYn" value="Y"></td>
								</tr>
					</c:if>
					<c:forEach items="${orgCdList}" var="codeInfo" varStatus="status">
								<tr data-cd="<c:out value='${codeInfo.cd}'/>" data-order="<c:out value='${status.index+1}'/>" >
									<td class="text-center"><a href="javascript:" onclick="fn_code_load('<c:out value='${codeInfo.cd}'/>')"  data-toggle="modal" data-target="#modal-default"><c:out value='${codeInfo.cd}'/></a></td>
									<td class="text-center"><a href="javascript:" onclick="fn_code_load('<c:out value='${codeInfo.cd}'/>')"  data-toggle="modal" data-target="#modal-default"><c:out value='${codeInfo.cdNm}'/></a></td>
									<td class="text-center"><input type="number" class="input-sortOrder" value="<c:out value='${codeInfo.sortOrder}'/>" tabindex="99<c:out value='${status.index+1}'/>" style="width:60px; text-align:center"/></td>
									<td class="text-center">
										<select class="select-useYn" style="width:80px;" tabindex="9999<c:out value='${status.index+1}'/>" >
											<option value="Y" <c:if test="${codeInfo.useYn eq 'Y'}">selected</c:if>>사용</option>
											<option value="N" <c:if test="${codeInfo.useYn ne 'Y'}">selected</c:if>>사용안함</option>
										</select>
									</td>
								</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>


							</tbody>
						</table>
					</div>
				</div>
<% /*
			<c:if test="${grpInfo!=null}">
				<div class="text-right" style="margin-top:10px;">
					<button type="button" class="btn btn-info disabled" onclick="fn_code_up()">▲ 위로</button>
					<button type="button" class="btn btn-info disabled" onclick="fn_code_down()">▼ 아래로</button>
				</div>
			</c:if>
*/ %>
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
</section>

</body>