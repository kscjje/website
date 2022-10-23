<%
 /**
  * @Class Name : cancelDetailAjax.jsp
  * @Description :취소  대상  확인
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.24    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.24
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="modal-txt">
<form:form commandName="cancelVO" name="cancelVO" id="cancelVO" method="post">
<form:hidden path="gubun" />
<form:hidden path="orderId" value="${payInfo.oid}" />
<input type="hidden" name="pComcd" value="${payInfo.pComcd }" />
<input type="hidden" name="pType" value="${payInfo.pType }" />
<input type="hidden" name="cardCd" value="${payInfo.cardCd }" />
<c:forEach items="${cancelVO.rsvnIdx}" var="idx" ><input type="hidden" name="rsvnIdx" value="${idx }"/></c:forEach>
	<p class="stit">예약프로그램 및 결제정보</p>
	<!-- table -->
	<div class="m_table mb50">
		<table>
			<caption>예약프로그램 및 결제정보 표입니다.</caption>
			<thead>
				<tr>
					<th class="cor">제목</th>
					<th class="date2">일시</th>
					<th class="price">결제금액</th>
					<th class="price">환불가능금액</th>
				</tr>
			</thead>
			<tbody>
<c:set var="rsvnNo" value="" />
<c:set var="totalAmt" value="0" />
<c:set var="cancelAmt" value="0" />
<c:set var="notCancelCnt" value="0" />
<c:forEach items="${list}" var="item" varStatus="s">
	<c:set var="payamt" value="0" />
	<c:choose>
		<c:when test="${param.gubun eq 'EXBT' }">
				<tr>
					<td>
						<span  class="b"><c:out value="${item.partNm}"/>
							<c:choose>
								<c:when test="${item.exbtType eq '1001' }"><c:out value="${item.exbtTypeNm}"/></c:when>
								<c:otherwise><c:out value="${item.exbtName }"/></c:otherwise>
							</c:choose>
						</span>
					</td>
					<td  class="date2">
						<fmt:parseDate var="extYmd" value="${item.ymd}" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
						<br class="pc"/>( ${fn:substring(item.exbtStime,0,2) }:${fn:substring(item.exbtStime,2,4) } ~ ${fn:substring(item.exbtEtime,0,2) }:${fn:substring(item.exbtEtime,2,4) } )
					</td>
					<td class="price"><fmt:formatNumber value="${item.payamt}"/>원
						<c:set var="totalAmt" value="${totalAmt + item.payamt }" />
						<c:set var="rsvnNo" value="${rsvnNo}${(s.index>0)?',':''}${item.rsvnNo}" />
					</td>
					<td>
						<c:choose>
							<c:when test="${item.ticketChkCnt < 1 and item.apptype eq '20' and (item.rfndRule.validYn eq 'Y' or item.rfndRule.cancelAbleYn eq 'Y') }">
								<span class="b red"><fmt:formatNumber value="${item.cancelAmt}"/>원</span><br class="pc"/> (${item.rfndRate }%)
								<c:set var="cancelAmt" value="${cancelAmt + item.cancelAmt }" />
							</c:when>
							<c:otherwise><p class="red">환불불가</p><c:set var="notCancelCnt" value="${notCancelCnt+1 }" /></c:otherwise>
						</c:choose>
					</td>
				</tr>
		</c:when>
		<c:when test="${param.gubun eq 'EVT' }">
				<tr>
					<td>
						<span  class="b"><c:out value="${item.evtName }"/></span>
					</td>
					<td  class="date2">
						<fmt:parseDate var="extYmd" value="${item.evtVeingdate}" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
						<br class="pc"/>( ${fn:substring(item.evtStime,0,2) }:${fn:substring(item.evtStime,2,4) } ~ ${fn:substring(item.evtEtime,0,2) }:${fn:substring(item.evtEtime,2,4) } )
					</td>
					<td class="price"><fmt:formatNumber value="${item.evtRsvnPayamt}"/>원
						<c:set var="totalAmt" value="${totalAmt + item.evtRsvnPayamt }" />
						<c:set var="rsvnNo" value="${rsvnNo}${(s.index>0)?',':''}${item.evtRsvnno}" />
					</td>
					<td>
						<c:choose>
							<c:when test="${item.ticketChkCnt < 1 and item.evtRsvnApptype eq '20' and (item.rfndRule.validYn eq 'Y' or item.rfndRule.cancelAbleYn eq 'Y') }">
								<span class="b red"><fmt:formatNumber value="${item.cancelAmt}"/>원</span><br class="pc"/> (${item.rfndRate }%)
								<c:set var="cancelAmt" value="${cancelAmt + item.cancelAmt }" />
							</c:when>
							<c:otherwise><p class="red">환불불가</p><c:set var="notCancelCnt" value="${notCancelCnt+1 }" /></c:otherwise>
						</c:choose>
					</td>
				</tr>
		</c:when>
		<c:when test="${param.gubun eq 'EDC' }">
				<tr>
					<td>
						<span  class="b"><c:out value="${item.edcPrgmnm }"/></span>
					</td>
					<td class="date2">
							<fmt:parseDate var="extYmd" value="${item.edcReqSdate}" pattern="yyyyMMdd"/>
							<fmt:parseDate var="extYmd2" value="${item.edcReqEdate}" pattern="yyyyMMdd"/>
							<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${extYmd2}" pattern="yyyy.MM.dd"/>
					</td>
					<td class="price"><fmt:formatNumber value="${item.edcTotamt}"/>원
						<c:set var="totalAmt" value="${totalAmt + item.edcTotamt }" />
						<c:set var="rsvnNo" value="${rsvnNo}${(s.index>0)?',':''}${item.edcRsvnno}" />
					</td>
					<td>
						<c:choose>
							<c:when test="${item.edcStat eq '20' and (item.rfndRule.validYn eq 'Y' or item.rfndRule.cancelAbleYn eq 'Y') }">
								<span class="b red"><fmt:formatNumber value="${item.cancelAmt}"/>원</span><br class="pc"/> (${item.rfndRate }%)
								<c:set var="cancelAmt" value="${cancelAmt + item.cancelAmt }" />
							</c:when>
							<c:otherwise><p class="red">환불불가</p><c:set var="notCancelCnt" value="${notCancelCnt+1 }" /></c:otherwise>
						</c:choose>
					</td>
				</tr>
		</c:when>
	</c:choose>
</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- //table -->
	<!-- //예약프로그램 및 결제정보 -->

	<p class="stit">결제 취소 정보</p>
	<div class="table2 responsive-type mb50">
	<table>
		<caption>결제 취소 정보를 확인하는 표입니다.</caption>
		<colgroup>
			<col style="width:20%">
			<col style="width:80%">
		</colgroup>
		<tbody>
			<tr>
				<th scope="row" class="vtop">결제 방법</th>
				<td class="b">
					<c:choose><c:when test="${payInfo.pType eq 'CARD'}">신용카드</c:when><c:when test="${payInfo.pType eq 'BANK'}">계좌이체</c:when></c:choose> (${payInfo.methodNm })
				</td>
			</tr>
			<tr>
				<th scope="row" class="vtop">총 결제금액</th>
				<td class="b"><fmt:formatNumber value="${totalAmt}"/>원 </td>
			</tr>
<c:if test="${notCancelCnt == 0 and totalAmt > cancelAmt}">
			<tr>
				<th scope="row" class="vtop">공제금액</th>
				<td class="b red"> - <fmt:formatNumber value="${totalAmt-cancelAmt}"/>원 </td>
			</tr>
</c:if>
			<tr>
				<th scope="row" class="vtop">환불 가능 금액</th>
				<td class="b red">
					<fmt:formatNumber value="${cancelAmt}"/>원
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="cancelCd">사유 선택<br>(필수)</label></th>
				<td>
					<div class="w360">
						<select name="cancelCd"  id="cancelCd">
<c:forEach items="${reasonList}" var="item" varStatus="s">
							<option value="${item.cd }">${item.cdNm }</option>
</c:forEach>
						</select>
					</div>
				</td>
			</tr>

		</tbody>
	</table>
	</div>
<form:hidden path="cancelAmt" value="${cancelAmt}" />
<form:hidden path="rsvnNo" value="${rsvnNo}" />
<input type="hidden" name="notCancelCnt" id="notCancelCnt" value="${notCancelCnt }" />
</form:form>
</div>
<c:if test="${notCancelCnt > 0 }">
<script>
alert("환불 불가 상태인 예약건이 있습니다.\n새로 고침 하셔서 환불 가능상태를 다시 확인해 주세요.");
</script>
</c:if>