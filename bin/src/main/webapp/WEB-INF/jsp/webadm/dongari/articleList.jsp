<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : articleList.jsp
  * @Description : 게시판 게시물  목록 화면
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

		$( ".s-ntceStat" ).on( "change", function() {
			$('#articleForm').find('input[name=ntceStat]').val($(this).val());
			$('#articleForm').submit();
		});

		$( ".checkAll" ).on( "click", function() {
			$('.check-nttId').prop("checked", $(this).prop("checked"));
		});
	});


	function chgNtceStat(nttId, ntceStat){

		var $form = $('<form/>');
		if(!nttId){
			if($('.check-nttId:checked').length<1){
				alert("변경할 동아리를 선택해주세요.");
				return ;
			}
			$.each($('.check-nttId:checked'), function (k, v) {
				$('<input type="hidden" name="nttId">').val($(v).val()).appendTo($form);
			});
		}
		else{
			$('<input type="hidden" name="nttId">').val(nttId).appendTo($form);
		}

		$('<input type="hidden" name="ntceStat">').val(ntceStat).appendTo($form);

		var msg = "승인처리 되었습니다.";
		if(ntceStat=="3"){
			msg = "반려처리 되었습니다.";
		}
		else if(ntceStat=="1"){
			msg = "취소처리 되었습니다.";
		}

		if (confirm("체크한 승인대기를 모두 승인처리 하시겠습니까")) {
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
<% /*
		function fn_article_delete(nttId) {
			if (confirm("삭제하시겠습니까?\n삭제하시면 데이타는 남아 있고 사용자 페이지에서는 노출되지 않습니다.")) {
				$.ajax({
				      url: './delete.json',
				      data : {'nttId' : nttId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         top.location.reload();
					         }
				      },
				      error: function() {
				    	  alert("Server Error");
				      }
				 });

			}

		}
*/ %>
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1><small>학습동아리></small>
					<c:choose>
						<c:when test="${type eq 'open' }">
							학습동아리개설현황
						</c:when>
						<c:otherwise>
							학습동아리승인처리
						</c:otherwise>
					</c:choose>
	            </h1>
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
	<form name="articleForm" id="articleForm" action="./list" method="get" >
		<input type="hidden" name="ntceStat" value="<c:out value="${searchVO.ntceStat}"/>"/>
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
							<div class="col-2 text-center">
								<label>개설신청일</label>
							</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="searchBgnDe" value='<c:out value="${searchVO.searchBgnDe}"/>' title="신청일 시작일" class="form-control calendar" autocomplete="off" placeholder="시작일">
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
							<div class="col-2">
								<div class="input-group mb-3">
                  					<input type="text" name="searchEndDe" value='<c:out value="${searchVO.searchEndDe}"/>' title="신청일 종료일" class="form-control calendar" autocomplete="off" placeholder="종료일" >
                  					<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                				</div>
							</div>
			 	 	 	</div>
			 	 	 	<br>
			 	 	 	<div class="row">
	<c:if test="${!empty ctgList }">
							<div class="col-2">
									<select name="searchCtg" title="카테고리 선택"  class="form-control">
										<option value=""  >::모집상태::</option>
	<c:forEach items="${ctgList}" var="item" varStatus="status">
										<option value="<c:out value="${item.ctgId }"/>" <c:if test="${searchVO.searchCtg eq item.ctgId }">selected</c:if> ><c:out value="${item.ctgNm }"/></option>
	</c:forEach>
									</select>

							</div>
	</c:if>
			              	<div class="col-2">
		              			<select name="searchCnd" title="검색영역"  class="form-control">
									<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >동아리명</option>
									<option value="2"  <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >신청자</option>
								</select>
							</div>
							<div class="col-3">
									<input class="form-control" name="searchWrd" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-secondary">조회</button>
			              	</div>
	<c:if test="${empty ctgList }">
							<div class="col-2"></div>
	</c:if>
	 					</div>
	 				</div>
	 </form>
	 			</div>

			<div class="row" style="margin-bottom:5px;">
				 <div class="col-2">
					<select name="temp" title="승인상태 선택"  class="form-control s-ntceStat">
						<option value="" <c:if test="${searchVO.ntceStat eq '' }">selected</c:if> >::승인상태::</option>
						<option value="1" <c:if test="${searchVO.ntceStat eq '1' }">selected</c:if> >승인대기</option>
						<option value="2" <c:if test="${searchVO.ntceStat eq '2' }">selected</c:if> >승인</option>
						<option value="3" <c:if test="${searchVO.ntceStat eq '3' }">selected</c:if> >반려</option>
					</select>
				 </div>
				<div class="col-10" style="text-align:right">
					<button type="button" class="btn btn-secondary" onclick="chgNtceStat('','2')">일괄 승인</button>
				</div>
			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary ">

	              <div class="card-body table-responsive p-0">


		               <table class="table table-bordered ">
		               	<colgroup>
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 10%;">
							<col style="width: 10%;">
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 15%;">
						</colgroup>
		                  <thead>
		                    <tr class="text-center">
		                      	<th><input type="checkbox" class="checkAll"/></th>
		                      	<th>번호</th><!-- 번호 -->
								<th>신청일</th>
								<th>신청자</th>
								<th class="board_th_link">동아리명 </th>
		                      	<th>승인상태</th>
								<th>승인처리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
	<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="7" class="text-center">데이타가 없습니다</td>
						</tr>
	</c:if>
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		                    <tr class="text-center">
		                    	<th>
				<c:choose>
					<c:when test="${resultInfo.ntceStat eq '1'}">
		                    		<input type="checkbox" name="nttId" value="<c:out value="${resultInfo.nttId}"/>" class="check-nttId"/>
		            </c:when>
		        </c:choose>
		                    	</th>
		                      	<td>
				<c:choose>
					<c:when test="${resultInfo.useAt eq 'Y' }">
									<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
					</c:when>
					<c:otherwise>
									<span class="text-red">삭제</span>
					</c:otherwise>
				</c:choose>
		                      	</td>
								<td><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></td>
								<td><c:out value='${resultInfo.frstRegisterNm}'/><% /*(<c:out value='${resultInfo.item1}'/>)*/ %></td>
								<td>
									<c:choose>
										<c:when test="${resultInfo.useAt eq 'Y' }">
											<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></a>
										</c:when>
										<c:otherwise>
											<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>" class="text-red"><strike><i><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></i></strike></a>
										</c:otherwise>
									</c:choose>
								</td>
		                      	<td>
									<c:choose>
										<c:when test="${resultInfo.ntceStat eq '1'}">
													<span class="text-green">승인대기</span>
										</c:when>
										<c:when test="${resultInfo.ntceStat eq '2'}">
													<span class="text-blue">승인</span>
										</c:when>
										<c:when test="${resultInfo.ntceStat eq '3'}">
													<span class="text-red">반려</span>
										</c:when>
										<c:otherwise>

										</c:otherwise>
									</c:choose>
								</td>

								<td>
									<c:choose>
										<c:when test="${resultInfo.ntceStat eq '1'}">
											<button type="button" class="btn btn-default btn-xs text-blue" onclick="chgNtceStat('<c:out value="${resultInfo.nttId}"/>','2')">승인</button>
											&nbsp;
											<button type="button" class="btn btn-default btn-xs text-red" onclick="chgNtceStat('<c:out value="${resultInfo.nttId}"/>','3')">반려</button>
										</c:when>
										<c:when test="${resultInfo.ntceStat eq '2'}">
											<button type="button" class="btn btn-default btn-xs text-green" onclick="chgNtceStat('<c:out value="${resultInfo.nttId}"/>','1')">승인 취소</button>
										</c:when>
										<c:when test="${resultInfo.ntceStat eq '3'}">
											<button type="button" class="btn btn-default btn-xs text-green" onclick="chgNtceStat('<c:out value="${resultInfo.nttId}"/>','1')">반려 취소</button>
										</c:when>
										<c:otherwise>

										</c:otherwise>
									</c:choose>


<% /*
<c:if test="${resultInfo.useAt eq 'Y' }">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn bg-primary btn-xs" href="./modify?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn bg-primary btn-xs disabled">수정</a>
		</c:otherwise>
	</c:choose>
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" onclick="fn_article_delete(<c:out value="${resultInfo.nttId}"/>)" class="btn btn-secondary btn-xs" >삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary btn-xs disabled" >삭제</button>
		</c:otherwise>
	</c:choose>
</c:if>
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
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
