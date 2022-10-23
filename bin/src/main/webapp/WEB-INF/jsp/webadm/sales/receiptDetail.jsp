<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
		.sort {font-size:16px; cursor:pointer}
		.sort.active {color:red}
	</style>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>영수증거래내역</h1>
	          </div>
	          <div class="col-sm-6" style="text-align:right">
	          		<c:set var="backURL" value="${referer }"/>
	          		<c:if test="${empty referer}"><c:set var="backURL" value="javascript:history.go(-1);"/></c:if>
					<a class="btn btn-secondary btn-sm btn-flat" href="${backURL}">이전화면</a>
	          </div>
	        </div>
	      </div>
	</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">

		<div class="card card-primary card-outline">
       	  <div class="card-body">
	 	 	<div class="col-12 p-0">
				<table class="table table-bordered text-nowrap">
					<colgroup>
					<col style="width: 20%;">
					<col style="width: 30%;">
					<col style="width: 20%">
					<col style="width: *">
					</colgroup>

					<tbody>
						<tr>
							<th>영수증번호</td>
							<td >${detailVO.receiptNo}</td>
							<th>주문번호(oid)</td>
							<td >${detailVO.oid}</td>
					</tr>
					<tr>
						<th>결제일시</td>
						<td><fmt:formatDate value="${detailVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
						<th>거래금액</td>
						<td><fmt:formatNumber value="${detailVO.payAmt}" pattern="#,###"/> 원</td>
					</tr>
					<tr>
						<th>결제회원번호</td>
						<td>${detailVO.memNo}</td>
						<th>결제회원명</td>
						<td>${detailVO.memNm}</td>
					</tr>

					</tbody>
			</table>
	        </div>
	       </div>
 	   	</div>


<div class="row">
	<div class="col-12">
		<h5 style="color:black">상품 결제내역</h5>
	</div>
</div>
       <div class="row">

          <div class="col-12">
            <div class="card card-primary card-outline" id="divCard">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap">
							<caption style="display:none"></caption>

							<thead>
								<tr>
									<th>No.</th>
									<th>거래일자</th>
									<th>예약번호</th>
									<th>기관명</th>
									<th>상품명</th>
									<th>단가</th>
									<th>수량</th>
									<th>할인사유</th>
									<th>할인금액</th>
									<th>결제수단</th>
									<th>결제금액</th>
									<th>승인번호</th>
									<th>OID</th>
									<th>TID</th>
									<th>MID</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty list}">
									<tr><td colSpan="17">데이터가 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
									<% int i =1; %>
										<c:forEach items="${list}" var="item" varStatus="status">
										<c:if test="${item.slipType eq '1' }">
											<tr>
												<td><%=i++ %></td>
												<td>${item.payDate}</td>
												<td><a href="../edcrsvn/edcRsvnInfoDetail?edcRsvnNo=${item.rsvnNo}">${item.rsvnNo }</a></td>
												<td>${item.orgNm}</td>

												<td>${item.itemNm}</td>
												<td style="text-align:right"><fmt:formatNumber value="${item.costAmt}" type="number" groupingUsed="true"/></td>
												<td style="text-align:right">${item.salnum}</td>
												<td>${item.dcTypeNm}</td>
												<td style="text-align:right;color:red;"><fmt:formatNumber value="${item.dcAmt}" type="number" groupingUsed="true"/></td>

												<td>${item.payMethodNm}</td>
												<td style="text-align:right;<c:if test="${item.slipType ne '1'}">color:red;</c:if>"><fmt:formatNumber value="${item.payAmt}" type="number" groupingUsed="true"/></td>
												<td>${item.appNo}</td>
												<td>${item.oid}</td>
												<td>${item.tid}</td>
												<td>${item.mid}</td>
											</tr>
										</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
              </div>
          </div>
        </div>
      </div>

      <div class="row">
	<div class="col-12">
		<h5 style="color:black">상품 취소내역</h5>
	</div>
</div>
       <div class="row">

          <div class="col-12">
            <div class="card card-primary card-outline" id="divCard">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap">
							<caption style="display:none"></caption>

							<thead>
								<tr>
									<th>No.</th>
									<th>환불일자</th>
									<th>예약번호</th>
									<th>기관명</th>
									<th>상품명</th>
									<th>결제방법</th>
									<th>결제금액</th>
									<th>수량</th>
									<th>환불방법</th>
									<th>환불금액</th>
									<th>승인번호</th>
									<th>환불은행명</th>
									<th>환불계좌</th>
									<th>예금주</th>
									<th>OID</th>
									<th>TID</th>
									<th>MID</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${empty refundList}">
									<tr><td colSpan="17">취소내역이 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${refundList}" var="item" varStatus="status">
											<tr>
												<td>${status.index+1 }</td>
												<td>${item.cancelDate}</td>
												<td><a href="../edcrsvn/edcRsvnInfoDetail?edcRsvnNo=${item.rsvnNo}">${item.rsvnNo}</a></td>
												<td>${item.orgNm}</td>
												<td>${item.edcPrgmnm}</td>
												<td>${item.methodNm}</td>
												<td  style="text-align:right"><fmt:formatNumber value="${item.payAmt}" type="number" groupingUsed="true"/></td>
												<td  style="text-align:right">${item.memCnt}</td>
												<td>${item.payPath}</td>
												<td style="color:red;text-align:right"><fmt:formatNumber value="${item.returnAmt}" type="number" groupingUsed="true"/></td>
												<td>${item.appNo}</td>
												<td>${item.retBankNm}</td>
												<td>${item.retAcountNum}</td>
												<td>${item.retDpstrNm}</td>
												<td>${item.oid}</td>
												<td>${item.tid}</td>
												<td>${item.mid}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
              </div>
          </div>
        </div>
      </div>


     </div>
</section>
</body>