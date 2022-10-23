<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
	</style>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>수강신청 상세</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

	<!-- Main content -->
	<section class="content">

      <div class="container-fluid">
		
		<div class="card card-primary card-outline">
       	  <div class="card-body">
	 	 	<div class="col-12">
	              <!-- /.card-header -->
                   <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 20%;">
						<col style="width: 30%;">
						<col style="width: 20%">
						<col style="width: *">
					</colgroup>
					
	                <tbody>
						<tr>
							<th>회원명</td>
							<td>${rsvnInfo.edcRsvnCustnm}</td>
							<th>ID</td>
							<td>${rsvnInfo.id}</td>
						</tr>
						<tr>
							<th>성별</td>
							<td>${rsvnInfo.edcRsvnGender}</td>
							<th>핸드폰번호</td>
							<td><hisco:HpPrint hp="${rsvnInfo.edcRsvnMoblphon}"/></td>
						</tr>
						<tr>
							<th>생년월일</td>
							<td><hisco:DateUtil datestr="${rsvnInfo.edcRsvnBirthdate}" format="yyyy-MM-dd"/>${rsvnInfo.birthSecNm}</td>
							<th>가입일시</td>
							<td>
								<c:if test="${not empty rsvnInfo.joinDate}">
								<hisco:DateUtil datestr="${rsvnInfo.joinDate}" format="yyyy-MM-dd HH:mm:ss"/>
								</c:if>
							</td>
						</tr>
	                </tbody>
	              </table>
	              
	              <!-- /.card-header -->
                   <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 20%;">
						<col style="width: 30%;">
						<col style="width: 20%">
						<col style="width: *">
					</colgroup>
					
	                <tbody>
						<tr>
							<th>신청프로그램명</td>
							<td colspan="3">${rsvnInfo.edcPrgmnm}</td>
						</tr>
						<tr>
							<th>모집차수</td>
							<td>${rsvnInfo.edcRsvnsetSeq}</td>
							<th>모집차수명</td>
							<td>${rsvnInfo.edcRsvnsetNm}</td>
						</tr>
						<tr>
							<th>접수기간</td>
							<td><hisco:DateUtil datestr="${rsvnInfo.edcRsvnSdate}" format="yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcRsvnEdate}" format="yyyy-MM-dd"/></td>
							<th>접수방법</td>
							<td>${rsvnInfo.edcProgmTypeNm}(${rsvnInfo.edcRsvnAccssrdNm})</td>
						</tr>
						<tr>
							<th>교육기간</td>
							<td><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy-MM-dd"/></td>
							<th>수업요일 / 시간</td>
							<td>${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> - <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/></td>
						</tr>
						<tr>
							<th>신청상태</td>
							<td>${rsvnInfo.edcStatNm}</td>
							<th>강사명</td>
							<td>${rsvnInfo.instrctrName}</td>
						</tr>
						
						<%-- <tr>
							<th>수강료</td>
							<td>
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
								<fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/> 원
								</c:when>
								<c:otherwise>무료</c:otherwise>
							</c:choose>
							</td>
							<th>비회원 접수여부</td>
							<td>비회원 접수<c:choose><c:when test="${detailVO.rsvnNonmebyn eq 'N'}">불가</c:when><c:otherwise>가능</c:otherwise></c:choose></td>
						</tr> --%>
						<%-- <tr>
							<th>총신청인원/모집인원</td>
							<td>${detailVO.statApplyCnt}명 / ${detailVO.edcPncpa}명</td>
							<th><c:if test="${detailVO.edcRsvnRectype eq '1002' }">대기정원</c:if></td>
							<td><c:if test="${detailVO.edcRsvnRectype eq '1002' }"><fmt:formatNumber value="${detailVO.edcEndwaitCapa}" pattern="#,###"/></c:if></td>
						</tr>
						<c:if test="${detailVO.edcCapaDvdyn eq 'Y' }">
						<tr>
							<th>온라인/방문 인원/정원</td>
							<td>온라인: ${detailVO.statApplyOnCnt}명 / ${detailVO.edcOncapa}명, 방문: ${detailVO.statApplyOffCnt}명 / ${detailVO.edcOffcapa}명</td>
							<th></td>
							<td></td>
						</tr>
						</c:if> --%>
	                </tbody>
	              </table>
	        
	        	  <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 20%;">
						<col style="width: 30%;">
						<col style="width: 20%">
						<col style="width: *">
					</colgroup>
					
	                <tbody>
						<tr>
							<th rowspan="2">결제방법</td>
							<td><c:if test="${not empty paySummary.onoffPayGbnNm}">${paySummary.onoffPayGbnNm}<c:if test="${not empty paySummary.payMethodNm}"> (${paySummary.payMethodNm})</c:if></c:if></td>
							<th>수강료</td>
							<td><fmt:formatNumber value='${rsvnInfo.edcProgmCost}'/> 원</td>
						</tr>
						<tr>
							<td>
							<c:choose>
								<c:when test="${paySummary.onoffPayGbnNm eq '온라인결제' and paySummary.payMethod eq 'VBANK'}">
									${paySummary.vbankName}&nbsp;${paySummary.vbankAccountNo}
								</c:when>
								<%-- <c:when test="${paySummary.onoffPayGbnNm eq '온라인결제' and paySummary.payMethod eq 'CARD'}">
									${paySummary.financeNm}&nbsp;${paySummary.cardNo}
								</c:when> --%>
								<c:otherwise>${paySummary.financeNm}</c:otherwise>
							</c:choose>
							</td>
							<th>할인금액</td>
							<td>
							<c:choose>
								<c:when test="${rsvnInfo.edcDcamt > 0}">
									<font color="red">-<fmt:formatNumber value='${rsvnInfo.edcDcamt}'/></font> 원 (${rsvnInfo.edcReasondcNm}&nbsp;<fmt:formatNumber value='${(rsvnInfo.edcDcamt/rsvnInfo.edcProgmCost)*100}'/>%)
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<th>결제마감일시</td>
							<td>
								<c:if test="${not empty rsvnInfo.edcPaywaitEnddatetime}">
								<font color="red"><fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern="yyyy-MM-dd HH:mm"/> 분까지</font>
								</c:if>
							</td>
							<th>결제예상금액</td>
							<td><fmt:formatNumber value='${rsvnInfo.edcTotamt}'/> 원</td>
						</tr>
	                </tbody>
	              </table>
	          <div class="col-12">
	 	 		<h6>[결제완료정보]</h6>
	 	 	  </div>
	 	 	  <div class="col-12">
	              <!-- /.card-header -->
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
							<td>${paySummary.receiptNo}</td>
							<th>결제수단</td>
							<td><c:if test="${not empty paySummary.payMethodNm}">${paySummary.payMethodNm}<c:if test="${not empty paySummary.financeNm and paySummary.financeNm ne '현금'}">(${paySummary.financeNm})</c:if></c:if></td>
						</tr>
						<tr>
							<th>결제일시</td>
							<td><c:if test="${not empty paySummary.payDate}"><hisco:DateUtil datestr="${paySummary.payDate}${paySummary.payTime}" format="yyyy년 MM월 dd일 HH:mm:ss"/></c:if></td>
							<th>승인번호</td>
							<td>${paySummary.appNo}</td>
						</tr>
						<tr>
							<th>주문번호(OID)</td>
							<td>${paySummary.oid}</td>
							<th>결제금액</td>
							<td><c:if test="${paySummary.payAmt > 0}"><font color="blue"><fmt:formatNumber value='${paySummary.payAmt}'/></font> 원</c:if></td>
						</tr>
						
						<tr>
							<th rowspan="2">결제취소일시</td>
							<td>
								<c:if test="${not empty paySummary.cancelDtime}">
									<hisco:DateUtil datestr="${paySummary.cancelDtime}" format="yyyy년 MM월 dd일 HH:mm:ss"/>
								</c:if>
							</td>
							<th>취소금액</td>
							<td>
								<c:if test="${not empty paySummary.cancelDtime}">
									<font color="red">-<fmt:formatNumber value='${paySummary.cancelAmt}'/></font> 원
								</c:if>
							</td>
						</tr>
						<tr>
							<td></td>
							<th>환불계좌</td>
							<td>${paySummary.retBankNm}&nbsp;${paySummary.retAcountNum}&nbsp;${paySummary.retDpstrNm}</td>
						</tr>
	                </tbody>
	              </table>
	        </div>
	       </div>
	       
 	   	</div>
	
 	   <div class="row" style="margin-bottom:5px;">
			 <div class="col-12" style="text-align:center">
			 	<button type="button" class="btn btn-secondary btn-sm btn-flat" onclick="javascript:history.go(-1);">이전화면</button>
			</div>
       </div>

       
     </div>
   </section>
</body>