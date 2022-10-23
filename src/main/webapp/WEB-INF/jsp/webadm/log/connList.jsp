<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : connList.jsp
  * @Description : 시스템 접속 통계
  * @Modification Information
  * @
  * @  수정일           수정자                 수정내용
  * @ -------     --------    ---------------------------
  * @ 2021.04.08  전영석                최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.08
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
		        dateFormat: "yy-mm-dd"
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
			$cform.attr('action', "./connListExcel");

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
	            <h1>웹방문자통계</h1>
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
				<form name="searchForm" id="searchForm" method="get" >
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
		</div>
	</section>

<section class="content">
	      <div class="container-fluid">
	       	<div class="card card-primary card-outline">
	       		<div class="row">

	          	<div class="col-12">
		               <table class="table  text-nowrap">
		               	<colgroup>
							<col style="width: 10%;">
							<col style="width: 10%">
							<col style="width: 35%;">
							<col style="width: 10%">
							<col style="width: 35%;">
						</colgroup>
		                  <thead>
		                    <tr>
								<th rowspan="2"><c:choose><c:when test="${paramMap.searchCondition  eq 'monthly'}">년월</c:when><c:otherwise>일자</c:otherwise></c:choose></th>
								<th colspan="2">회원</th>
								<th colspan="2">비회원</th>
		                    </tr>
		                    <tr>
								<th>접속수</th>
								<th>비율</th>
								<th>접속수</th>
								<th>비율</th>
		                    </tr>
		                  </thead>
		                  <tbody>

								<c:if test="${fn:length(dbResultList) == 0}">
									<tr>
										<td colspan="4">데이타가 없습니다</td>
									</tr>
								</c:if>

								<c:forEach items="${dbResultList}" var="item" varStatus="status">
									<c:set var="totalA" value="${totalA + item.memCount}"/>
									<c:set var="totalB" value="${totalB + item.nonCount}"/>
								</c:forEach>

								<c:forEach items="${dbResultList}" var="item" varStatus="status">
										<tr>
											<td>
												<c:out value="${item.occrrncDe}"/>
											</td>
											<td>
												<fmt:formatNumber type="number" maxFractionDigits="3" value="${item.memCount}" />
											</td>
											<td class="text-left p-0">
						                      	 <div class="progress-group">
							                        <c:set var="percent" value="0" />
							                        <c:choose>
							                        	<c:when test="${totalA > 0 }">
							                        		<c:set var="percent" value="${item.memCount * 100.0 / totalA * 1.0}" />
							                        	</c:when>
							                        </c:choose>
							                      	<span class="text-red">${percent} %</span>
							                        <div class="progress">
								                        <div class="progress-bar bg-maroon" style="width:${percent}%"/> </div>
							                        </div>
							                   	</div>
											</td>
											<td>
												<fmt:formatNumber type="number" maxFractionDigits="3" value="${item.nonCount}" />
											</td>
											<td class="text-left p-0">
						                      	 <div class="progress-group">
							                        <c:set var="percent" value="0" />
							                        <c:choose>
							                        	<c:when test="${totalB > 0 }">
							                        		<c:set var="percent" value="${item.nonCount * 100.0 / totalB * 1.0}" />
							                        	</c:when>
							                        </c:choose>
							                      	<span class="text-red">${percent} %</span>
							                        <div class="progress">
								                        <div class="progress-bar bg-gray" style="width:${percent}%"/> </div>
							                        </div>
							                   	</div>

											</td>
										</tr>
								</c:forEach>

		                  </tbody>
		                </table>

	              </div>
	              <!-- /.card-body -->


	        </div>
	      </div>

	     </div>
	</section>

</body>
