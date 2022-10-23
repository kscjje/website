<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : salesSettleDetail.jsp
	 * @Description : 수입금정산상세내역 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.12.31     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.12.31
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
<script type="text/javascript" src="../resources/js/sales/help.js"></script>
<script type="text/javascript" src="../resources/js/sales/common.js"></script>
<script>
function fn_excelDown() {
	var $cform = $('#searchVO').clone(true);

	$cform.attr('id', 'excelDownloadForm');
	$cform.attr('name', 'excelDownloadForm');
	$cform.attr('method', 'get');
	$cform.attr('action', "./settleDetailExcel");

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
            <h1>수입금정산상세내역</h1>
          </div>
          <div class="col-sm-6" style="text-align:right">
	          		<a class="btn btn-secondary btn-sm btn-flat" href="javascript:history.back()">이전화면</a>
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
	      					<div class="col-3">
		      					<form:input cssClass="form-control" path="itemNm" placeHolder="프로그램명을 입력하세요."/>
	      					</div>
	      					<div class="col-3">

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
			               <colgroup>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:10%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 헤더 35% -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 수입내역 25% -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 당일환불 25% -->
								<col style="width:5%;"/>
								<!-- 합계 5% -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 전거래 환불 10%  -->
							</colgroup>
							<thead>
								<tr>
									<th rowSpan="2">No.</th>
									<th rowSpan="2">기관명 <span id="orgNm" class="sort">▼</span></th>
									<th rowSpan="2">프로그램명 <span id="prgNm" class="sort">▼</span></th>
									<th rowSpan="2">결제일자 <span id="payDt" class="sort">▼</span></th>
									<th rowSpan="2">회원번호 <span id="memNo" class="sort">▼</span></th>
									<th rowSpan="2">회원명 <span id="memNm" class="sort">▼</span></th>
									<th colSpan="5">수입내역 <i class="far fa-question-circle guide-link" style="cursor:pointer" title="수입내역 도움말" help-id="001"></i></th>
									<th colSpan="5">당일환불 <i class="far fa-question-circle guide-link" style="cursor:pointer" title="당일환불 도움말" help-id="004"></i></th>
									<th rowSpan="2">합계 <i class="far fa-question-circle guide-link" style="cursor:pointer" title="합계 도움말" help-id="005"></i></th>
									<th colSpan="2">전거래 환불 <i class="far fa-question-circle guide-link" style="cursor:pointer" title="전거래일 환불 도움말" help-id="003"></i></th>
								</tr>
								<tr>
									<th>인원(수량)</th>
									<th>현금</th>
									<th>카드</th>
									<th>실시간이체</th>
									<th>가상계좌</th>
									<th>인원(수량)</th>
									<th>현금</th>
									<th>카드</th>
									<th>실시간이체</th>
									<th>가상계좌</th>
									<th>인원(수량)</th>
									<th>환불금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td>[계]</td>
									<td style="text-align:right">${total.cnt}</td>
									<td style="text-align:right"><fmt:formatNumber value="${total.cashAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.cardAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.bankAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.vbankAmt}" type="number" groupingUsed="true"/></td>
									<td style="color:red;text-align:right">${total.nretCnt}</td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.nretCashAmt}" type="number" groupingUsed="true"/></td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.nretCardAmt}" type="number" groupingUsed="true"/></td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.nretBankAmt}" type="number" groupingUsed="true"/></td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.nretVbankAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.totAmt}" type="number" groupingUsed="true"/></td>
									<td style="color:red;text-align:right">${total.retCnt}</td>
									<td style="color:red;text-align:right"><fmt:formatNumber value="${total.returnAmt}" type="number" groupingUsed="true"/></td>
								</tr>
								<c:choose>
									<c:when test="${empty list}">
									<tr><td colSpan="20">데이터가 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${list}" var="item" varStatus="status">
										<tr>
											<td>${item.rnum}</td>
											<td>${item.comNm}</td>
											<td>${item.itemNm}</td>
											<td>${item.payDate}</td>
											<td>${item.memNo}</td>
											<td>${item.memNm}</td>
											<td style="text-align:right">${item.cnt}</td>
											<td style="text-align:right"><fmt:formatNumber value="${item.cashAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.cardAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.bankAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.vbankAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right">${item.nretCnt}</td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.nretCashAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.nretCardAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.nretBankAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.nretVbankAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.totAmt}" type="number" groupingUsed="true"/></td>
											<td style="color:red;text-align:right">${item.retCnt}</td>
											<td style="color:red;text-align:right"><fmt:formatNumber value="${item.returnAmt}" type="number" groupingUsed="true"/></td>
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
							<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./settleDetail?pageIndex="/>
						</div>
		            </div>
		            <!-- /.card -->
				</div>
			</div>
		</div>
	</div>

      <!-- 프로그램 분류 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">분야 선택하기</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
      <!-- 도움말 -->
     <div class="modal fade" id="help-modal-1" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">도움말(<span class="guide-title"></span>)</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <p class="guide-contents" style="line-height:1.8"></p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
</section>
</body>