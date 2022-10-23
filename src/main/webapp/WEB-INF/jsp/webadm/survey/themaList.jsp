<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<script>

	$(document).ready(function () {
		$('input.calendar').datepicker({
	    	autoclose: true,
	        calendarWeeks: false,
	        todayHighlight: true,
	        language: "kr",
	        dateFormat: "yy-mm-dd"
	    });

		$( "#searchVO").find('[name=orgNm]').on( "change", function() {
			$('#searchVO').submit();
		});

		$( ".select-openYn" ).on( "change", function() {
			fn_change_field($(this),'openYn');
		});

		$( ".select-useYn" ).on( "change", function() {
			fn_change_field($(this),'useYn');
		});


	});




	function fn_change_field(obj, field) {
		var yn = $(obj).val();
		var qestnarId = $(obj).parent().parent().data('id');
		var qestnarStdno = $(obj).parent().parent().data('no');

		var $form = $('<form/>');
		$('<input type="hidden" name="qestnarId">').val(qestnarId).appendTo($form);
		$('<input type="hidden" name="qestnarStdno">').val(qestnarStdno).appendTo($form);
		$('<input type="hidden" name="'+field+'">').val(yn).appendTo($form);

		var msg = "공개여부가 변경 되었습니다.";
		$.ajax({
		      url: './themaStdrmngFieldUpdate.json',
		      data : $form.serialize(),
		      type : "POST",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			       } else {
			        	 alert(msg);
			        	 top.location.reload();
			       }
		      }
		});
	}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>설문조사 주제 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card">
				<form:form commandName="searchVO" name="searchVO" method="get">
					 <div class="card-body">
			 	 	 	<div class="row">
			 	 	 		<div class="col-2 text-center">
			 	 	 			<label>기관명</label>
			 	 	 		</div>
			 	 	 		<div class="col-4">
			 	 	 			<hisco:OrgList defaultNo="${searchVO.orgNm}" id="orgNm"/>
			 	 	 		</div>
			 	 	 	</div>
			 	 	 	<div class="row" style="margin-top:10px;">
			 	 	 		<div class="col-2 text-center">
			 	 	 			<label>등록기간</label>
			 	 	 		</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="searchStartDts" value='<c:out value="${searchVO.searchStartDts}"/>' title="시작일" class="form-control calendar" autocomplete="off" placeholder="시작일">
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="searchEndDts" value='<c:out value="${searchVO.searchEndDts}"/>' title="종료일" class="form-control calendar" autocomplete="off" placeholder="종료일" >
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
							<div class="col-2">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='${searchVO.searchKeyword}'  maxlength="155" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-secondary">조회</button>

			              	</div>
	 					</div>
	 				</div>
	 			</form:form>
	 	   </div>
	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
					<div class="card-header">
						<div class="row">
							<div class="col-12 text-right">
								<a href="./themaAdd" class="btn btn-info" title="등록버튼">등록</a><!-- 등록 -->
							</div>
						</div>
					</div>
	              <div class="card-body ">
		               <table class="table table-bordered">
		               	<colgroup>
							<col style="width: 80px;">
							<col style="width: 100px;">
							<col style="width: 80px">
							<col style="width: 130px">
							<col style="">
							<col style="width: 120px;">
							<col style="width: 120px;">
							<col style="width: 150px;">
							<col style="width: 120px;">
							<col style="width: 120px;">
							<col style="width: 100px;">
							<col style="width: 100px;">
							<col style="width: 100px;">
						</colgroup>
		                  <thead>
		                    <tr class="text-center">
		                      	<th>번호</th><!-- 번호 -->
								<th>등록일</th>
								<th>설문번호</th>
								<th>등록기관명</th>
								<th>설문명</th>
								<th>설문항목수</th>
								<th>설문기간설정</th>
								<th>오픈일시</th>
		                      	<th>설문시작일</th>
								<th>설문종료일</th>
								<th>참가자수</th>
								<th>공개여부</th>
								<th>삭제</th>
		                    </tr>
		                  </thead>
		                  <tbody>
					<c:if test="${fn:length(surveyList) == 0}">
						<tr>
							<td colspan="13" align="center">데이타가 없습니다</td>
						</tr>
					</c:if>
						<c:forEach items="${surveyList}" var="item" varStatus="status">
		                    <tr <c:if test="${item.activeYn eq 'Y'}">class="text-success"</c:if> data-id="<c:out value="${item.qestnarId}"/>" data-no="<c:out value="${item.qestnarStdno }"/>">
		                      	<td class="text-center">${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
								<td class="text-center"><c:out value="${item.regdate}"/></td>
								<td class="text-center"><c:out value="${item.qestnarId}"/></td>
								<td class="text-center"><c:out value="${item.orgNm}"/></td>
								<td class="left"><a href="./themaEdit?qestnarId=${item.qestnarId}${commandMap.query}" >${item.qestnarName}</a></td>
								<td class="text-center">${item.questionCnt}</td>
					<c:choose>
					 	<c:when test="${item.qestnarOpertype eq '1001'}">
					 			<td class="text-center">상시운영</td>
					 	</c:when>
					 	<c:otherwise>
					 			<td class="text-center">기간설정</td>
					 	</c:otherwise>
					 </c:choose>
								<td class="text-center">
									${fn:substring(item.qestnarOpenDate,0,4)}-${fn:substring(item.qestnarOpenDate,4,6)}-${fn:substring(item.qestnarOpenDate,6,8)}
									&nbsp;
									${fn:substring(item.qestnarOpenTime,0,2) }:${fn:substring(item.qestnarOpenTime,2,4) }
								</td>
								<td class="text-center">
					<c:if test="${item.qestnarOpersdate!=null && fn:length(item.qestnarOpersdate)==8}">
									${fn:substring(item.qestnarOpersdate,0,4)}-${fn:substring(item.qestnarOpersdate,4,6)}-${fn:substring(item.qestnarOpersdate,6,8)}
					</c:if>
								</td>
								<td class="text-center">
					<c:if test="${item.qestnarOperedate!=null && fn:length(item.qestnarOperedate)==8}">
									${fn:substring(item.qestnarOperedate,0,4)}-${fn:substring(item.qestnarOperedate,4,6)}-${fn:substring(item.qestnarOperedate,6,8)}
					</c:if>
								</td>
								<td class="text-center">
					<c:if test="${item.submitCnt>0}">
									<a href="./result?qestnarId=${item.qestnarId }&qestnarStdno=${item.qestnarStdno}" class="btn btn-secondary btn-xs">${item.submitCnt }명 보기</a>
					</c:if>
								</td>
								<td class="text-center">
									<select name="openYn" class="form-control-sm select-openYn">
										<option value="Y" <c:if test="${item.openYn eq 'Y'}">selected</c:if>>공개</option>
										<option value="N" <c:if test="${item.openYn eq 'N'}">selected</c:if>>비공개</option>
									</select>
								</td>
								<td class="text-center">
									<select name="useYn" class="form-control-sm select-useYn">
										<option value="Y" <c:if test="${item.useYn eq 'Y'}">selected</c:if>>정상</option>
										<option value="N" <c:if test="${item.useYn eq 'N'}">selected</c:if>>삭제</option>
									</select>
								</td>
						<% /*
								<td class="text-center">
							  		<a class="btn btn-info btn-xs" href="./editQuestion/${item.qestnarId}">[설문지]</a>
								</td>
						*/ %>
		                    </tr>
						</c:forEach>
		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./themaList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>