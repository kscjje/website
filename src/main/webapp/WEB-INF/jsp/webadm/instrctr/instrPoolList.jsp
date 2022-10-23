<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrPoolList.jsp
	 * @Description : 강사Pool 리스트 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.09
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<script type="text/javascript">
<!--
	$(document).ready(function(){

		$( ".datepicker" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

		$('#chkall').click(function(){
			$('input[name=checkedMem]').prop("checked" , $(this).prop('checked'));
		})
	});

	function fn_set_checked(){
		var cnt = $('input[name=checkedMem]:checked').length;
		if(cnt > 0){
			jQuery.ajax({
				url : "./updateStateChecked.json",
				type: "POST",
				data: $('#listForm').serialize(),
				success: function(data) {
					if (data.result.code == "ERROR") {
						alert(data.result.msg);
					} else {
						alert(data.result.msg);
						window.location.reload();
					}
				}
			});
		}else{
			alert("승인처리할 데이타를 1개 이상 선택하세요.");
		}
	}

	function fn_change_stat(memNo ,newState){
		var stateNm = "승인완료 상태로 변경하시겠습니까?";
		if(newState == "1001"){
			stateNm = "현재 상태를 취소 하시겠습니까?"
		} else if(newState == "2001"){
			stateNm = "승인반려 상태로 변경 하시겠습니까?"
		}

		if(confirm(stateNm)){
			jQuery.ajax({
				url : "./updateState/"+memNo+".json",
				type: "POST",
				data: {memNo : memNo , state : newState},
				success: function(data) {
					if (data.result.code == "ERROR") {
						alert(data.result.msg);
					} else {
						alert(data.result.msg);

						var row = $('#ROW_' + memNo);
						var newText = "승인대기";


						if(newState == '1001'){
							row.find('button').eq(0).show();
							row.find('button').eq(1).show();

							row.find('button').eq(2).hide();
						}else{
							row.find('button').eq(0).hide();
							row.find('button').eq(1).hide();

							var text = (newState=='2001'?'반려취소':'승인취소');
							newText =  (newState=='2001'?'승인반려':'승인완료');

							row.find('button').eq(2).text(text).show();

						}

						$('#ROWST_' + memNo).text(newText);

					}
				}
			});
		}
	}
//-->
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>강사은행 현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       	<div class="card card-primary card-outline">
<form:form commandName="searchVO" name="searchVO" method="get">
		 	 	<div class="card-body">
		 	 	 	<div class="row">
 		              	<div class="col-1">
	              			<select name="searchCondition" title="검색"  class="form-control">
								<option value="name"  <c:if test="${searchVO.searchCondition eq 'name'}">selected="selected"</c:if> >이름</option>
								<option value="field"  <c:if test="${searchVO.searchCondition eq 'field'}">selected="selected"</c:if> >과목</option>
							</select>
 						</div>
 						<div class="col-3">
 								<input class="form-control" name="searchKeyword" type="text" value="<c:out value="${searchVO.searchKeyword}"/>" size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
 						</div>
						<div class="col-2">
								<form:select path="searchStat" title="승인상태" class="form-control">
									<form:option value="">승인상태(전체)</form:option>
                    				<form:options items="${stateList}" itemLabel="cdNm" itemValue="cd" />
                    			</form:select>
 		              	</div>
 		              	<div class="col-1" style="text-align:right">신청일</div>
 		              	<div class="col-4">
								<div class="input-group date" style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>
 						<div class="col-1">
 							<button type="submit" class="btn btn-secondary">조회</button>
 							<!--
							<a href="./regist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록">강사 등록</a>
		              	 	-->
		              	</div>
 					</div>
 				</div>
</form:form>
 			</div>

		<div class="row mb-2">
			 <div class="col-4 pt-1">
			 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
			 </div>
			 <div class="col-8" style="text-align:right">
				<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
					<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_set_checked()" >일괄승인</button>
				</c:if>
			</div>
       </div>

       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->
<form id="listForm">
              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 7%;">
						<col style="width: 7%;">
						<col style="width: 15%;">
						<col style="width: 10%;">
						<col style="width: *;">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
	                  <thead>
	                    <tr>
	                    	<th><input type="checkbox" id="chkall" name="chkall" value="Y"/></th>
	                      	<th>번호</th><!-- 번호 -->
	                      	<th>신청일시</th>
	                      	<th>신청자 이름</th>
							<th>강의과목</th>
							<th>승인상태</th>
							<th>승인처리</th>
	                    </tr>
	                  </thead>
	                  <tbody>
						<c:if test="${fn:length(list) == 0}">
							<tr>
								<td colspan="6">데이타가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${list}" var="resultInfo" varStatus="status">
		                   <tr>
		                   		<th><input type="checkbox" name="checkedMem" value="${resultInfo.memNo}"/></th>
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
		                      	<td><fmt:formatDate value="${resultInfo.regdate }" pattern="yyyy.MM.dd HH:mm"/></td>
								<td><a href="./detail?detailNo=${resultInfo.memNo}<c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.memNm}'/></a></td>
								<td><c:out value="${resultInfo.lectrField}"/></td>
								<td id="ROWST_${resultInfo.memNo}"><c:out value="${resultInfo.stateNm}"/></td>
								<td id="ROW_${resultInfo.memNo}">
									<button type="button" class="btn btn-default btn-xs text-green" onclick="fn_change_stat('${resultInfo.memNo}','3001')" style="<c:if test="${resultInfo.state ne '1001'}">display:none</c:if>">승인완료</button>

									<button type="button" class="btn btn-default btn-xs text-red" onclick="fn_change_stat('${resultInfo.memNo}','2001')" style="<c:if test="${resultInfo.state ne '1001'}">display:none</c:if>">승인반려</button>

									<button type="button" class="btn btn-default btn-xs text-gray" onclick="fn_change_stat('${resultInfo.memNo}','1001')" style="<c:if test="${resultInfo.state eq '1001'}">display:none</c:if>">${resultInfo.state eq '2001'?'반려취소':'승인취소'}</button>
								</td>

		                    </tr>
						</c:forEach>
	                  </tbody>
	                </table>
              </div>
 </form>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
				</div>
            </div>

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
</section>