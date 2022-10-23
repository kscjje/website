<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="table table-bordered text-nowrap">
<input type="hidden" id="orgTel" value="${detailVO.orgTel}"/>
		<colgroup>
		<col style="width: 20%;">
		<col style="width: 30%;">
		<col style="width: 20%">
		<col style="width: *">
		</colgroup>

		            <tbody>
			<tr>
				<th>기관명</td>
				<td colspan="3">${detailVO.orgName}</td>
		</tr>
		<tr>
			<th>신청프로그램명</td>
			<td colspan="3">${detailVO.edcPrgmnm}</td>
		</tr>
		<tr>
			<th>모집차수</td>
			<td>${detailVO.edcRsvnsetSeq}</td>
			<th>모집차수명</td>
			<td>${detailVO.edcRsvnsetNm}</td>
		</tr>
		<tr>
			<th>접수기간</td>
			<td><hisco:DateUtil datestr="${detailVO.edcRsvnSdate}${detailVO.edcRsvnStime}" format="yyyy-MM-dd HH:mm"/> ~ <hisco:DateUtil datestr="${detailVO.edcRsvnEdate}${detailVO.edcRsvnEtime}" format="yyyy-MM-dd HH:mm"/></td>
			<th>접수방법</td>
			<td>${detailVO.edcRsvnRectypeNm}(${detailVO.edcRsvnAccssrdNm})</td>
		</tr>
		<tr>
			<th>교육기간</td>
			<td>${detailVO.edcPeriod}</td>
			<th>수업요일/시간</td>
			<td>${detailVO.edcDaygbnNm}/${detailVO.edcTime}</td>
		</tr>
		<tr>
			<th>수강료</td>
			<td>
			<c:choose>
				<c:when test="${detailVO.saleAmt > 0}">
				<fmt:formatNumber value="${detailVO.saleAmt}" pattern="#,###"/> 원
				</c:when>
				<c:otherwise>무료</c:otherwise>
			</c:choose>
			</td>
			<th>강사명</td>
			<td>${detailVO.instrctrName}</td>
		</tr>
		<tr>
			<th>총신청인원/모집인원</td>
			<td>${detailVO.statApplyCnt}명 <c:if test="${detailVO.edcCapaDvdyn ne 'Y' }">(온라인: ${detailVO.statApplyOnCnt}명 , 방문: ${detailVO.statApplyOffCnt}명)</c:if>/ ${detailVO.edcPncpa}명</td>
			<th>대기정원</td>
			<td><c:choose><c:when test="${detailVO.edcRsvnRectype eq '1002' }"><fmt:formatNumber value="${detailVO.edcEndwaitCapa}" pattern="#,###"/></c:when><c:otherwise>-</c:otherwise></c:choose></td>
		</tr>
		<c:if test="${detailVO.edcCapaDvdyn eq 'Y' }">
		<tr>
			<th>온라인/방문 인원/정원</td>
			<td>온라인: ${detailVO.statApplyOnCnt}명 / ${detailVO.edcOncapa}명, 방문: ${detailVO.statApplyOffCnt}명 / ${detailVO.edcOffcapa}명</td>
			<th></td>
			<td></td>
		</tr>
		</c:if>
		</tbody>
</table>