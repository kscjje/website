<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : salesReceiptList.jsp
	 * @Description : 수입금전표현황 JSP
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
	$cform.attr('action', "./receiptListExcel");

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
            <h1>수입금전표현황</h1>
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
	      				<div class="row">
	      					<div class="col-3">
	      						<form:input path="receiptNo" cssClass="form-control" placeHolder="영수증번호 13자리 숫자를 입력하세요." maxlength="13"/>
	      					</div>
	      					<div class="col-3">
	      						<form:input path="memNm" cssClass="form-control" placeHolder="회원명" maxlength="20"/>
	      					</div>
	      				</div>
	      				<hr/>
	      				<div class="row">
	      					<div class="col-3">
								<form:select path="orgKind" title="기관유형(선택)"  class="form-control">
									<form:option value=""  >기관유형(선택)</form:option>
									${hisco:makeOptions("SM_ORG_LTYPE", "EMPTY", searchVO.orgKind)}
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
								<col style="width:10%"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:10%;"/>
								<col style="width:10%;"/>
								<!-- 55% 상품명까지 -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 20% 감면사유까지-->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 25% OID까지-->
							</colgroup>
							<thead>
								<tr>
									<th>No.</th>
									<th>거래일자<span id="payDate" class="sort">▼</span></th>
									<th>기관명<span id="orgNm" class="sort">▼</span></th>
									<th>영수증번호</th>
									<th>회원번호</th>
									<th>회원명<span id="memNm" class="sort">▼</span></th>
									<th>강좌명<span id="edcPrgmnm" class="sort">▼</span></th>
									<th>상품명<span id="itemNm" class="sort">▼</span></th>
									<th>수량</th>
									<th>단가</th>
									<th>감면금액</th>
									<th>감면사유</th>
									<th>결제수단<span id="payType" class="sort">▼</span></th>
									<th>카드사/은행<span id="methodNm" class="sort">▼</span></th>
									<th>수입금액<span id="payAmt" class="sort">▼</span></th>
									<th>수납자명</th>
									<th>OID</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty list}">
									<tr><td colSpan="17">데이터가 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${list}" var="item" varStatus="status">
										<tr>
											<td>${item.rnum}</td>
											<td>${item.payDate}</td>
											<td>${item.orgNm}</td>
											<td><a href="./receiptDetail?receiptNo=${item.receiptNo}">${item.receiptNo}</a></td>
									<c:choose>
										<c:when test="${item.memNo eq '비회원'}">
											<td>${item.memNo}</td>
											<td>${item.memNm}</td>
										</c:when>
										<c:otherwise>
											<td><a href="../member/userUpdt?memNo=${item.memNo}">${item.memNo}</a></td>
											<td><a href="../member/userUpdt?memNo=${item.memNo}">${item.memNm}</a></td>
										</c:otherwise>
									</c:choose>
											<td>${item.edcPrgmnm}</td>
											<td>${item.itemNm}</td>
											<td style="text-align:right">${item.salnum}</td>
											<td style="text-align:right"><fmt:formatNumber value="${item.costAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right;color:red;"><fmt:formatNumber value="${item.dcAmt}" type="number" groupingUsed="true"/></td>
											<td>${item.dcTypeNm}</td>
											<td>${item.payMethodNm}</td>
											<td>${item.payLocNm}</td>
											<td style="text-align:right;<c:if test="${item.slipType ne '1'}">color:red;</c:if>"><fmt:formatNumber value="${item.payAmt}" type="number" groupingUsed="true"/></td>
											<td>${item.cashier}</td>
											<td>${item.oid}</td>
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
							<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./receiptList?pageIndex="/>
						</div>
		            </div>
		            <!-- /.card -->
				</div>
			</div>
		</div>
   </div>
</section>
</body>