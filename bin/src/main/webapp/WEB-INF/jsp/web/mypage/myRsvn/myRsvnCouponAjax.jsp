<%
 /**
  * @Class Name : myRsvnCouponAjax.jsp
  * @Description : 쿠폰 선택 페이지
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
<div class="table2 responsive-type">
<c:choose>
	<c:when test="${couponForm.appStatus eq '11'}">
<form:form commandName="couponForm" name="couponForm" id="couponForm" action="./myRsvnPay" method="post">
<form:hidden path="gubun" />
<form:hidden path="programCd" />
<form:hidden path="partCd" />
<form:hidden path="rsvnIdxOne" />
<form:hidden path="ymd" />
<form:hidden path="target" />
<form:hidden path="itemCd" />
<form:hidden path="memberGbn" />
	<table>
		<caption>할인 정보를 선택하는 표입니다.</caption>
		<colgroup>
			<col style="width:25%">
			<col style="width:75%">
		</colgroup>
		<tbody>

<c:if test="${!empty discList }">
			<tr>
				<td class="bb0"> <!-- 20.08.27 클래스 추가 -->
					이벤트 할인 선택
				</td>
				<td>
<c:forEach items="${discList}" var="item" varStatus="s">
					<div class="w100 <c:if test="${s.index > 0 }">mt10</c:if>">
						<label><input type="radio" class="radio" name="eventReasonCd" value="${item.eventReasoncd }|${item.eventDcid}"  <c:if test="${item.useYn eq 'Y' }">checked</c:if>/><em></em><span>${item.eventDcname } (${item.eventDcRate}%)</span></label>
					</div>
</c:forEach>
				</td>
			</tr>
</c:if>
<c:if test="${!empty annualData }">
<c:if test="${ annualData.limitQty == 0}"><input type="hidden" name="annualLimitYn"  value="N"/></c:if>
			<tr>
				<td class="bb0"><c:choose><c:when test="${couponForm.memberGbn eq '1' }">유료회원 할인</c:when><c:otherwise>특별회원 할인</c:otherwise></c:choose></td>
				<td>
					<c:choose>
						<c:when test="${annualData.limitCnt > 0 and annualData.limitCnt <= annualData.applyCnt }">잔여 할인 횟수가 없습니다.</c:when>
						<c:otherwise>
							<div class="w100">
								<input type="radio" class="radio" name="eventReasonCd" id="reasonCd_ann" value="ANN|${annualData.eventReasoncd}" <c:if test="${annualData.useYn eq 'Y' }">checked</c:if>/><em></em>
								<label for="reasonCd_ann"><c:choose><c:when test="${couponForm.memberGbn eq '1' }">유료회원 할인</c:when><c:otherwise>특별회원 할인</c:otherwise></c:choose>(${annualData.eventDcRate}%)</label>
							</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
</c:if>
<c:if test="${!empty couponList}">
			<tr>
				<td class="bb0">쿠폰 할인</td>
				<td>
					<c:forEach items="${couponList}" var="item" varStatus="s">
						<div class="w100 <c:if test="${s.index > 0 }">mt10</c:if>">
							<c:choose>
								<c:when test="${couponForm.gubun eq 'EDC'}">
									<input type="radio" class="radio" name="eventReasonCd" id="couponId_${s.index}" value="CPN|<c:out value="${item.cpnUid}" />"><em></em>
								</c:when>
								<c:otherwise>
									<input type="checkbox" class="checkbox" name="couponIds" id="couponId_${s.index}" value="<c:out value="${item.cpnUid}" />">
								</c:otherwise>
							</c:choose>
							<label for="couponId_${s.index}"><c:out value="${item.cpnName}" /> (${item.cpnDcrate}%)</label>
						</div>
					</c:forEach>
				</td>
			</tr>
</c:if>

<c:if test="${empty discList and empty couponList and empty annualData}">
			<tr>
				<td colspan="2">할인사유 선택 목록이 없습니다.</td>
			</tr>

</c:if>
		</tbody>
	</table>
	<!-- //table -->
	<p class="ex mt10">※ 할인사유는 중복되지 않습니다.</p>
</form:form>
</div>
</c:when>
	<c:otherwise>
			결제진행 상태가 대기인 경우에 할인 사유를 선택하실 수 있습니다. <br>현재 결제진행 상태를 다시 확인해 주세요.
	</c:otherwise>
</c:choose>

<script>
	if ($('input[name=eventReasonCd]').length > 0 && $('input[name=eventReasonCd]:checked').length < 1 ) {
<c:if test="${param.gubun ne 'EDC'}">
		$('input[name=eventReasonCd]').eq(0).prop("checked" , true);
</c:if>
	}

</script>