<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : salesRefundList.jsp
	 * @Description : 환불취소현황 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.12.31     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2022.01.05
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<style type="text/css">
.sort {font-size:16px; cursor:pointer}
.sort.active {color:red}
</style>
<script type="text/javascript" src="../resources/js/sales/common.js"></script>
<script>
function fn_excelDown() {
	var $cform = $('#searchVO').clone(true);

	$cform.attr('id', 'excelDownloadForm');
	$cform.attr('name', 'excelDownloadForm');
	$cform.attr('method', 'get');
	$cform.attr('action', "./refundListExcel");

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
            <h1>환불취소현황</h1>
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
      			<form:form commandName="searchVO" name="searchVO"  method="get" >
      			<form:hidden path="searchOrder" />
				<form:hidden path="searchOrderDir" />
      			<form:hidden path="itemCtgd" />
      			<div class="card card-primary card-outline">
	      			<div class="card-body search-body">
	      				<div class="row">
	      					<div class="col-3">
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="1">1일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="7">7일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="15">15일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="30">30일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="90">90일</button>
	      						<button type="button" class="btn btn-outline-secondary now-month-btn">당월</button>
	      					</div>
							<div class="col-4">
									<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
				                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off" placeHolder="거래기간(시작일자)"/>
				                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
				                    </div>

									<div style="width:10%;float:left;text-align:center">~</div>
									<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
				                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off" placeHolder="거래기간(종료일자)"/>
				                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
				                    </div>
							</div>
	      				</div>
	      				<hr/>
	      				<div class="row">
	      					<div class="col-3">
								<form:select path="orgKind" title="기관유형(선택)"  class="form-control">
									<%-- <form:option value=""  >기관유형(선택)</form:option> --%>
									${hisco:makeOptions("SM_ORG_LTYPE", "기관유형(선택)", searchVO.orgKind)}
								</form:select>
	      					</div>
	      					<div class="col-3">
	      						<form:select path="searchOrgNo" title="기관(선택)"  class="form-control">
	      							<form:option value=""  >기관(선택)</form:option>
	      							<c:forEach items="${orgList}" var="item" varStatus="status">
	      								<form:option value="${item.orgNo}" data-orgkind="${item.orgLtype}">${item.orgNm}</form:option>
	      							</c:forEach>
	      						</form:select>
	      					</div>
	      					<div class="col-3">
		      					<div class="input-group">
		      						<form:select path="refundMethod" title="환불방법"  class="form-control">
		      							<form:option value="" >환불방법(전체)</form:option>
		      							<form:option value="CARD">거래취소</form:option>
		      							<form:option value="PART">부분취소</form:option>
		      							<form:option value="BANK">계좌환불</form:option>
		      							<form:option value="CASH">현금환불</form:option>
		      						</form:select>
	 							</div>
	      					</div>
	      				</div>
	      			</div>
      			</div>
	  			<div class="row mb-3 justify-content-center">
			    	<div class="col-2">
			    		<button type="submit" class="btn btn-primary btn-block">조회</button>
			    	</div>
			    	<div class="col-2">
			    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
			    	</div>
			    </div>
		 	   <div class="row mb-2">
					 <div class="col-2">
						<div class="input-group input-group-sm"">
							<form:select path="pageSize" title="글수"  cssClass="form-control">
								<form:option value="5">5개씩 보기</form:option>
								<form:option value="10">10개씩 보기</form:option>
								<form:option value="20">20개씩 보기</form:option>
								<form:option value="50">50개씩 보기</form:option>
								<form:option value="100">100개씩 보기</form:option>
							</form:select>
						</div>
					 </div>
					 <div class="col-2 pt-1">
					 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
					 </div>
					 <div class="col-8" style="text-align:right">
						<button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button>
					</div>
		       </div>
      			</form:form>
      		</div>
      	</div>
      	<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-body table-responsive p-0">
						<table class="table table-bordered table-hover text-nowrap">
							<caption style="display:none"></caption>
			               	<colgroup>
								<col style="width:5%;"/>
								<col style="width:5%"/>
								<col style="width:10"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 회원명까지 30% -->
								<col style="width:5%;"/>
								<col style="width:10%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 결제금액까지 30% -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 환불금액까지 20% -->
								<col style="width:10%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 20% -->
							</colgroup>
							<thead>
								<tr>
									<th>No.</th>
									<th>환불일자<span id="refDt" class="sort">▼</span></th>
									<th>기관명<span id="orgNm" class="sort">▼</span></th>
									<th>회원번호</th>
									<th>회원명<span id="memNm" class="sort">▼</span></th>
									<th>휴대폰번호<span id="hpNo" class="sort">▼</span></th>
									<th>강좌명<span id="edcPrgmnm" class="sort">▼</span></th>
									<th>인원</th>
									<th>결제방법<span id="payMtd" class="sort">▼</span></th>
									<th>결제금액<span id="payAmt" class="sort">▼</span></th>
									<th>환불방법<span id="refMtd" class="sort">▼</span></th>
									<th>환불사유</th>
									<th>공제금액<span id="useAmt" class="sort">▼</span></th>
									<th>환불금액<span id="refAmt" class="sort">▼</span></th>
									<th>환불계좌<span id="refAcc" class="sort">▼</span></th>
									<th>환불은행명</th>
									<th>환불계좌명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td>[계]</td>
									<td>${total.totCnt}건</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.payAmt}" type="number" groupingUsed="true"/></td>
									<td></td>
									<td></td>
									<td></td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.returnAmt}" type="number" groupingUsed="true"/></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<c:choose>
									<c:when test="${empty list}">
									<tr><td colSpan="17">데이터가 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${list}" var="item" varStatus="status">
										<tr>
											<td>${item.rnum}</td>
											<td>${item.cancelDate}</td>
											<td>${item.orgNm}</td>
											<td>${item.memNo}</td>
											<td>${item.memNm}</td>
											<td>${item.hpNo}</td>
											<td>${item.edcPrgmnm}</td>
											<td  style="text-align:right">${item.memCnt}</td>
											<td>${item.methodNm}</td>
											<td  style="text-align:right"><fmt:formatNumber value="${item.payAmt}" type="number" groupingUsed="true"/></td>
											<td>${item.payPath}</td>
											<td>${item.retReason}</td>
											<td  style="text-align:right"><fmt:formatNumber value="${item.useAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.returnAmt}" type="number" groupingUsed="true"/></td>
											<td>${item.retDpstrNm}</td>
											<td>${item.retBankNm}</td>
											<td>${item.retAcountNum}</td>
										</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- /.card-body -->
		            <div class="card-footer p-0">
		              	<div class="pagination_2">
							<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./refundList?pageIndex="/>
						</div>
		            </div>
		            <!-- /.card -->
				</div>
			</div>
		</div>
   </div>
</section>
</body>