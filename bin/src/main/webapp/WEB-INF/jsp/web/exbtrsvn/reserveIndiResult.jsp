<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : reserveResult.jsp
  * @Description : 예약 결과 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.01    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.01
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

$(document).ready(function() {
	$(document).attr("title", "예약 완료 - 관람예약 | 노원수학문화관");
});

function fn_direct_pay() {
	$('#rsvnMasterVO').submit();
}
</script>
<c:set var="amount" value="0" />
<c:set var="totAmount" value="0" />
<c:set var="discAmount" value="0" />
<c:set var="prevItemCd" value="0" />
<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
	<c:if test="${prevItemCd ne item.itemCd}">
		<c:set var="amount" value="${amount + item.amount }" />
		<c:set var="totAmount" value="${totAmount + item.totAmount }" />
		<c:set var="discAmount" value="${discAmount + item.dcAmount }" />
		<c:set var="prevItemCd" value="${item.itemCd }" />
	</c:if>
</c:forEach>

<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		<div class="join_wrap bor">
			<!-- step -->
			
			<ul class="step step3">
				<li>
					<dl>
						<dt>1</dt>
						<dd>일정 선택</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>안내사항 동의 및 여부</dd>
					</dl>
				</li>
				<li class="on">
					<!-- 현재 on -->
					<dl>
						<dt>3</dt>
						<dd>예약 완료 안내</dd>
					</dl>
				</li>
			</ul>			
			
			<!-- 
			<ul class="step step4">
				<li>
					<dl>
						<dt>1</dt>
						<dd>회차 선택</dd>
					</dl>
				</li>
				<li >
					<dl>
						<dt>2</dt>
						<dd>안내 및 주문</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>3</dt>
						<dd>정보 입력</dd>
					</dl>
				</li>
				<li class="on">
					<dl>
						<dt>4</dt>
						<dd><c:choose><c:when test="${amount < 1}">예약완료</c:when><c:otherwise>결제대기</c:otherwise></c:choose></dd>
					</dl>
				</li>
			</ul>
			-->
			<!-- //step -->
			<!-- signup2 -->
			<div class="signup2">
<c:choose>
	<c:when test="${amount < 1}">
			<!-- 무료 -->
			<!-- <h4 class="h4 mb40">예약이 완료 되었습니다.</h4> -->
			<p class="desc desc3 mb35">관람 예약이 완료되었습니다.</p>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${resultVO.target eq '2001' }">
				<p class="desc desc3 mb5">관람 예약이 완료되었습니다.</p>
				<h4 class="h4 mb40">관람 시작 전 까지  결제 가능하며<br> 당일날 현장에서도 결제할 수 있습니다.</h4>
			</c:when>
			<c:otherwise>
				<h4 class="h4 mb40"><span class="red"><fmt:formatDate value="${resultVO.paywaitEnddt}" pattern="yyyy년 MM월 dd일 HH시 mm분"/></span> 까지 결제 하셔야<br/><span class="red">예약이 완료</span>됩니다.</h4>
			</c:otherwise>
		</c:choose>
				<!-- calc -->
				<div class="calc">
					<div class="cal1">
						<dl>
							<dt>총 금액</dt>
							<dd><fmt:formatNumber value="${amount}"/>원</dd>
						</dl>
						<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
						<dl>
							<dt>할인 금액</dt>
							<dd><fmt:formatNumber value="${discAmount}"/>원</dd>
						</dl>
					</div>
					<div class="cal2">
						<span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span>
						<dl>
							<dt>결제 예정 금액</dt>
							<dd class="pink"><fmt:formatNumber value="${totAmount}"/>원</dd>
						</dl>
					</div>
				</div>
				<!-- //calc -->
<form:form commandName="rsvnMasterVO" name="rsvnMasterVO" id="rsvnMasterVO" action="/web/mypage/myRsvn/myRsvnPay" method="post">
<form:hidden path="rsvnIdx"/>
<input type="hidden" name="gubun" value="EXBT"/>
				<!-- btn_area -->
				<ul class="btn_area mb70">
					<li><a href="javascript:fn_direct_pay()" class="btn_ty_m_c1">바로 결제</a></li>
				</ul>
</form:form>
				<!-- //btn_area -->
	</c:otherwise>
</c:choose>
				<!-- <p class="stit">예약 정보 확인</p> -->
				<!-- table -->
				<div class="table2 responsive-type">
				
					<table>
						<caption>예약 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">예약번호</th>
								<td class="b">
									<c:out value="${resultVO.rsvnNo}"/>
									<!-- r000000000000 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">관람일정</th>
								<td class="b">
									<fmt:parseDate var="extYmd" value="${resultVO.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(resultVO.exbtStime,0,2) }:${fn:substring(resultVO.exbtStime,2,4) } ~ ${fn:substring(resultVO.exbtEtime,0,2) }:${fn:substring(resultVO.exbtEtime,2,4) }								
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약자 성명</th>
								<td class="b">
									<c:out value="${resultVO.custNm }"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">참가인원</th>
								<td class="b">
									<c:out value="${resultVO.visitnum}"/> 명
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">휴대폰</th>
								<td class="b">
									<tags:HpDecrypt param="${resultVO.exbtHp}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">이메일</th>
								<td class="b">
									<tags:EmailDecrypt param="${resultVO.exbtEmail}"/>
								</td>
							</tr>
						</tbody>
					</table>					
				
					<table style="display:none;">
						<caption>예약 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">관람 제목</th>
								<td class="b">
									<c:out value="${resultVO.partNm }"/>
									<c:choose>
										<c:when test="${resultVO.exbtType eq '1001' }">관람</c:when>
										<c:otherwise><c:out value="${resultVO.exbtName }"/></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">일시</th>
								<td class="b">
									<fmt:parseDate var="extYmd" value="${resultVO.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(resultVO.exbtStime,0,2) }:${fn:substring(resultVO.exbtStime,2,4) } ~ ${fn:substring(resultVO.exbtEtime,0,2) }:${fn:substring(resultVO.exbtEtime,2,4) }
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인원수</th>
								<td class="b">
								<c:set var="prevItemCd" value="" />
								<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
									<c:if test="${item.itemCnt > 0 && item.itemCd ne prevItemCd }"> <c:out value="${item.itemNm }"/> : <c:out value="${item.itemCnt }"/>명&nbsp;&nbsp;&nbsp;</c:if>
									<c:set var="prevItemCd" value="${item.itemCd}" />
								</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약자 이름</th>
								<td ><c:out value="${resultVO.custNm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약자 연락처</th>
								<td>
									<tags:HpDecrypt param="${resultVO.exbtHp}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">이메일</th>
								<td>
									<tags:EmailDecrypt param="${resultVO.exbtEmail}"/>
								</td>
							</tr>
<c:choose>
	<c:when test="${resultVO.target eq '2001' }">
							<tr>
								<th scope="row" class="vtop">단체명</th>
								<td ><c:out value="${resultVO.grpnm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">방문 인솔자명</th>
								<td ><c:out value="${resultVO.visitcustNm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">방문 인솔자 연락처</th>
								<td ><tags:HpTelShow hpval="${resultVO.visitcustHpno}"/></td>
							</tr>
	</c:when>
</c:choose>

						</tbody>
					</table>
				</div>
				<!-- //table -->
				<div class="ex_txt" style="display:none;">
					<p class="ex_tit"><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 예약 유의사항</p>
					<p>- 무료관람은 예약신청과 함께 예약이 확정되며 유료관람은 결제가 되어야 예약이 확정됩니다.</p>
					<p>- 유료관람 예약은 예약 신청 후 결제 마감 시간이 있을 수 있음으로 시간 확인 후 결제해 주시기 바랍니다.</p>
				</div>
			</div>
			<!-- //ex_txt -->
			<!-- //signup2 -->
		</div>
		
		<ul class="btn_area long">
			<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EXBT" class="btn_ty_m3_c1">예약현황보기</a></li>
			<li><a href="./exbtrsvnList" class="btn_ty_m3_c5">메인화면으로</a></li>
		</ul>		
		
		<!-- //join_wrap -->
		<ul class="btn_area long" style="display:none;">
			<li><a href="./dspy/<c:out value="${resultVO.exbtPartcd }"/>?ymd=<c:out value="${resultVO.ymd}"/>" class="btn_ty_m_c1">추가 예약</a></li>
			<c:choose>
				<c:when test="${amount > 1 }">
					<li><a href="/web/mypage/myRsvn/myRsvnList" class="btn_ty_m3_c5">나의 예약내역</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/web/mypage/myRsvn/myRsvnCompList" class="btn_ty_m3_c5">나의 예약내역</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>