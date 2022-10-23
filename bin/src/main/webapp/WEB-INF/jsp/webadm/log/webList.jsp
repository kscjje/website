<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : webList.jsp
  * @Description : 웹 접속 카운터  목록 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.17    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.17
  *  @version 1.0
  *  @see
  *
  */
%>

<head>
	<script>
		$(document).ready(function() {
			$('input.calendar').datepicker({
		    	autoclose: true,
		        calendarWeeks: false,
		        todayHighlight: true,
		        language: "kr",
		    	format: "yyyy-mm-dd"
		    });

			$('#searchCondition').change(function(){
				$('#searchdaily').hide();
				$('#searchmonthly').hide();

				$('#search'+$(this).val()).show();
			})
		});

		function fn_show_sub(menuNo) {
			$('.child_' + menuNo).toggle();
		}
		function excelDown() {
	    	var $cform = $('#searchForm').clone(true);
			$cform.attr('id', 'excelDownloadForm');
			$cform.attr('name', 'excelDownloadForm');
			$cform.attr('method', 'get');
			$cform.attr('action', "./webListExcel");

			$('body').append($cform);
			$('#excelDownloadForm').submit();
			$('#excelDownloadForm').remove();
		}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>메뉴별 접속 통계</h1>
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
				<form name="searchForm" id="searchForm" method="get">
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
							<div class="col-2">
		              			<select name="searchCondition" id="searchCondition" title="검색"  class="form-control">
									<option value="monthly"  <c:if test="${paramMap.searchCondition eq 'monthly'}">selected</c:if> >월별 보기</option>
									<option value="daily"  <c:if test="${paramMap.searchCondition eq 'daily'}">selected</c:if> >일별 보기</option>
								</select>
	 						</div>
							<div class="col-5 " id="searchdaily" <c:if test="${paramMap.searchCondition eq 'monthly'}">style="display:none"</c:if>>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
			                        <input id="startYmd" name="startYmd" class="form-control calendar" type="text" value="<c:out value="${paramMap.startYmd}"/>" autocomplete="off"/>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                      <input id="endYmd" name="endYmd" class="form-control calendar" type="text" value="<c:out value="${paramMap.endYmd}"/>" autocomplete="off"/>
			                    </div>

							</div>
							<div class="col-5" id="searchmonthly" <c:if test="${paramMap.searchCondition ne 'monthly'}">style="display:none"</c:if>>
								<div class="input-group date" style="float:left;width:110px"  data-target-input="nearest" >
									<select id="startYear" name="startYear" title="년도" class="form-control">
				             			${hisco:makeYearOptions("년도", 5, paramMap.startYear)}
									</select>
									&nbsp;
			                    </div>
								<div class="input-group date" style="float:left;width:95px"  data-target-input="nearest">
									<select id="startMonth" name="startMonth" title="월"  class="form-control">
				             			${hisco:makeMonthOptions("월", paramMap.startMonth)}
									</select>
									&nbsp; ~ &nbsp;
			                    </div>

			                    <div class="input-group date" style="float:left;width:110px"  data-target-input="nearest" >
									<select id="endYear" name="endYear" title="년도" class="form-control">
				             			${hisco:makeYearOptions("년도", 5, paramMap.endYear)}
									</select>
									&nbsp;
			                    </div>
								<div class="input-group date" style="float:left;width:95px"  data-target-input="nearest">
									<select id="endMonth" name="endMonth" title="월"  class="form-control">
				             			${hisco:makeMonthOptions("월", paramMap.endMonth)}
									</select>
			                    </div>
							</div>
							<div class="col-5">
								<button type="submit" class="btn btn-secondary" style="margin-left:20px">조회</button>
								<button type="button" class="btn btn-info" style="margin-left:10px" onclick="excelDown()">엑셀다운로드</button>
							</div>
	 					</div>
	 				</div>
	 			</form>
	 		</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table  text-nowrap">
		               	<colgroup>
							<col style="width: 10%;">
							<col style="width: 10%">
							<col style="width: *;">
						</colgroup>
		                  <thead>
		                    <tr>
								<th>메뉴명</th>
								<th>날짜</th>
								<th></th>
		                    </tr>
		                  </thead>
		                  <tbody>

							<c:if test="${fn:length(menuList) == 0}">
								<tr>
									<td colspan="4">데이타가 없습니다</td>
								</tr>
							</c:if>

							<c:set var="upperMenuId" value="" />

							<c:forEach items="${menuList}" var="item" varStatus="status">

								<c:if test="${item.upperMenuId ne upperMenuId}">
									<c:set var="upperMenuId" value="${item.upperMenuId}" />
								</c:if>

								<c:if test="${item.upperMenuId == 0 or !empty item.menuUrl}">

									<tr <c:if test="${item.upperMenuId > 0 }"> style="display:none" class="child_${item.upperMenuId}"</c:if>>
										<td >
										<c:choose>
										 	<c:when test="${item.upperMenuId > 0 }"><span style="margin-left:20px"><i class="fa fa-fw  fa-caret-right"></i><c:out value="${item.menuNm}"/></span></c:when>
										 	<c:when test="${ !empty item.menuUrl}"><b><i class="fa fa-fw fa-arrow-circle-down"></i> <c:out value="${item.menuNm}"/></b></c:when>
										 	<c:otherwise><b><i class="fa fa-fw fa-arrow-circle-down"></i> <a href="javascript:fn_show_sub(${item.menuNo })"><c:out value="${item.menuNm}"/></a></b>
										 	</c:otherwise>
										</c:choose>
										</td>
										<td class="text-left p-0" colspan="2">
											<table class="table p-0">
						                		<colgroup>
													<col style="width: 15%">
													<col style="width: *;">
												</colgroup>
							                  <tbody>
													<c:set var="color" value="${fn:split('bg-maroon,bg-gray,bg-yellow,bg-green,bg-indigo,bg-danger,bg-navy,bg-purple,bg-fuchsia,bg-orange,bg-lime,bg-teal,bg-blue,bg-info',',') }" />
													<c:set var="idxNo" value="${status.index%14}" />
													<c:forEach items="${item.logCount}" var="cItem" varStatus="status">
									                    <tr>
									                      <td>
									                      	${cItem.ymd}
									                      </td>
									                      <td class="text-left p-0">
									                      	 <div class="progress-group">
										                      	<span class="text-red">${cItem.cnt}</span>
										                        <div class="progress">
										                        <c:set var="percent" value="0" />
										                        <c:choose>
										                        	<c:when test="${maxCnt > 0 &&  cItem.cnt > 0}"> <c:set var="percent" value="${cItem.cnt*100.0/maxCnt*1.0}" /></c:when>
										                        </c:choose>
										                          <div class="progress-bar ${color[idxNo]}" style="width:${percent}%"/> </div>
										                        </div>
										                   	</div>
									                      </td>
									                    </tr>
													</c:forEach>
							                  </tbody>
					            			</table>

										</td>
									</tr>

								</c:if>

							</c:forEach>

	            		</tbody>
	          		</table>
	      		</div>
	       		<!-- /.card-body -->

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>

</body>
