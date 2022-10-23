<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  popupList.jsp
  * @Description : 팝업존 관리 JSP
  * @Modification Information
  * @
  * @  수정일               수정자              수정내용
  * @ -------       --------    ---------------------------
  * @ 2021.06.04    전영석             최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.04
  *  @version 1.0
  *  @see
  *
  */
%>
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

		$( ".s-ntceAt" ).on( "change", function() {
			$('#searchForm').find('input[name=ntceAt]').val($(this).val());
			$('#searchForm').submit();
		});

		$( ".select-yn" ).on( "change", function() {
			fn_change_row_ntceAt($(this));
		});


	});



	function fn_delete(popupId) {
		if (confirm("해당 팝업의 정보를 삭제하시겠습니까?")) {
			$.ajax({
			      url: './popupDelete.json',
			      data : {'popupId' : popupId},
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


	function fn_change_row_ntceAt(obj) {
		var yn = $(obj).val();
		var popupId = $(obj).parent().parent().data('id');

		if(yn=="Y"){
			$(obj).parent().parent().find('.dummy-order').hide();
			$(obj).parent().parent().find('.input-order').show();
		}
		else{
			$(obj).parent().parent().find('.input-order').hide();
			$(obj).parent().parent().find('.dummy-order').show();
		}

		// 실시간 저장필요시 활성화
		// chgNtceAt(popupId);
	}

	function chgNtceAt(popupId){
		var $form = $('<form/>');
		if(!popupId){
			if($('.popup-row').length<1){
				alert("변경할 항목이 없습니다.");
				return ;
			}
			$.each($('.popup-row'), function (k, v) {
				$('<input type="hidden" name="popupId">').val($(v).data('id')).appendTo($form);
				$('<input type="hidden" name="ntceAt">').val($(v).find('.select-yn').val()).appendTo($form);
				$('<input type="hidden" name="sortOrder">').val($(v).find('.input-order').val()).appendTo($form);
			});
		}
		else{
			$('<input type="hidden" name="popupId">').val(popupId).appendTo($form);
			$('<input type="hidden" name="ntceAt">').val($('.popup-row-'+popupId).find('.select-yn').val()).appendTo($form);
			$('<input type="hidden" name="sortOrder">').val($('.popup-row-'+popupId).find('.input-order').val()).appendTo($form);
		}


		var msg = "처리 되었습니다.";

		if (confirm("정렬순을 일괄처리 하시겠습니까")) {
			$.ajax({
			      url: './statUpdate.json',
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
	}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>팝업존 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card card-primary ">
	<form name="searchForm" id="searchForm"  method="get" >
		<input type="hidden" name="ntceAt" value="<c:out value="${searchVO.ntceAt}"/>"/>
<% /*
					<div class="card-header p-0 border-bottom-0">
						<ul class="nav nav-tabs" id="custom-tabs-four-tab" role="tablist">

							<li class="nav-item">
								<a class="nav-link <c:if test="${empty searchVO.searchGubun}">active</c:if>"  href="javascript:fn_gubun_change('')" role="tab" aria-controls="custom-tabs-four-home" aria-selected="true">전체</a>
							</li>

	<c:forEach items="${popupGbn}" var="gbn" varStatus="status">
							<li class="nav-item">
								<a class="nav-link <c:if test="${searchVO.searchGubun eq gbn.cd}">active</c:if>" href="javascript:fn_gubun_change('${gbn.cd}')" role="tab">${gbn.cdNm }</a>
							</li>
	 </c:forEach>
						</ul>
					</div>
*/ %>
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-2">
		              			<select name="searchCondition" title="검색영역"  class="form-control">
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >이용기간</option>
									<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >등록일</option>
								</select>
							</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="ntceBgndeYMD" value='<c:out value="${searchVO.ntceBgndeYMD}"/>' title="시작일" class="form-control calendar" autocomplete="off" placeholder="시작일">
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="ntceEnddeYMD" value='<c:out value="${searchVO.ntceEnddeYMD}"/>' title="종료일" class="form-control calendar" autocomplete="off" placeholder="종료일" >
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
			 	 	 	</div>
			 	 	 	<div class="row">
			 	 	 		<div class="col-2 text-center">
								<label>팝업명</label>
							</div>
							<div class="col-4">
									<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-secondary">조회</button>
			              	</div>

	 					</div>
	 				</div>
	 </form>
	 			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->
						<div class="card-header">
						<div class="row">
							<div class=" col-2 ">
								<select name="temp" title="구분"  class="form-control s-ntceAt">
									<option value="" <c:if test="${searchVO.ntceAt eq '' }">selected</c:if> >구분(전체)</option>
									<option value="Y" <c:if test="${searchVO.ntceAt eq 'Y' }">selected</c:if> >사용만보기</option>
								</select>
							</div>

							<div class="col-10 text-right">
								<button type="button" class="btn btn-secondary" onclick="chgNtceAt()">정렬순 적용</button>
								<a href="./popupRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="팝업존 등록">등록</a><!-- 등록 -->
							</div>
						</div>
					</div>

	              <div class="card-body ">
		               <table class="table table-bordered">
		               	<colgroup>
							<col style="width: 5%;">
							<col style="width: 10%;">
							<col style="width: 15%;">
							<col style="width: *">
							<col style="width: 8%;">
							<col style="width: 8%;">
							<col style="width: 10%;">
						</colgroup>
		                  <thead>
		                    <tr class="text-center">
		                      	<th>번호</th><!-- 번호 -->
								<th>등록일</th>
								<th>이용기간</th>
								<th>팝업명</th>
								<th>사용여부</th>
								<th>정렬순서</th>
								<th>삭제</th>
		                    </tr>
		                  </thead>
		                  <tbody>
							<c:if test="${fn:length(resultList) == 0}">
								<tr class="text-center">
									<td colspan="7">데이타가 없습니다</td>
								</tr>
							</c:if>
								<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
				                    <tr class="text-center popup-row popup-row-<c:out value="${resultInfo.popupId}"/>" data-id="<c:out value="${resultInfo.popupId}"/>">
				                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
										<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd"/></td>
										<td>
											<c:choose>
												<c:when test="${empty resultInfo.ntceBgnde and empty resultInfo.ntceEndde}">상시</c:when>
												<c:otherwise><c:out value="${resultInfo.ntceBgnde}"/> ~ <c:out value="${resultInfo.ntceEndde}"/></c:otherwise>
											</c:choose>
										</td>
										<td class="text-left">
										<% /* 제목 클릭시 수정으로 변경
											<a href="./popupDetail?popupId=<c:out value="${resultInfo.popupId}"/><c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.popupTitleNm}'/></a>
											*/ %>
											<a href="./popupUpdt?popupId=<c:out value="${resultInfo.popupId}"/><c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.popupTitleNm}'/></a>
										</td>

										<td>
											<select name="ntceAt" class="form-control-sm select-yn">
												<option value="Y" <c:if test="${resultInfo.openYn eq 'Y'}">selected</c:if>>Y</option>
												<option value="N" <c:if test="${resultInfo.openYn eq 'N'}">selected</c:if>>N</option>
											</select>
										</td>
										<td>
											<input class="form-control-sm text-center dummy-order" value="-" disabled style="width:50px; <c:if test="${resultInfo.openYn eq 'Y'}">display:none;</c:if>"/>
											<input class="form-control-sm text-center input-order" value="<c:out value='${resultInfo.sortOrder}'/>" style="width:50px; <c:if test="${resultInfo.openYn ne 'Y'}">display:none;</c:if>"/>
										</td>
										<td>
											<a class="btn bg-danger btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="javascript:" onclick="fn_delete('<c:out value="${resultInfo.popupId}"/>')">삭제</a>
										<% /* 제목 클릭시 수정으로 변경
										  <a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./popupUpdt?popupId=<c:out value="${resultInfo.popupId}"/><c:out value="${commandMap.query}"/>">수정</a>
										  */ %>
										</td>
				                    </tr>
								</c:forEach>
		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./popupList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>