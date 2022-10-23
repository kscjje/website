<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnResult.jsp
  * @Description : 예약 결과 JSP
  * @Modification Information
  * @
  * @  수정일              수정자           수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.18    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.18
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
	
	
	});
	
	function fn_pop_rsvnCerti() {
		window.open('','rsvnPop','width=818, height=1177, resizable=yes, scrollbars=yes, status=0, titlebar=0, toolbar=0, left=100, top=50')
		$('#popFrm').submit()
	}
	
	function fn_direct_pay() {
		$('#evtrsvnMstVO').submit();
	}
	
</script>

<c:set var="amount"     value="0" />
<c:set var="totAmount"  value="0" />
<c:set var="prevItemCd" value="0" />

<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
	<c:if test="${prevItemCd ne item.evtRsvnItemcd}">
		<c:set var="amount"     value="${amount + item.evtSalamt}" />
		<c:set var="totAmount"  value="${totAmount + item.evtTotamt}" />
		<c:set var="discAmount" value="${discAmount + item.evtDcamt}" />
		<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
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
						<dd>강연/행사/영화 선택</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>인원입력 및 동의</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>3</dt>
						<dd>예약자 정보 입력</dd>
					</dl>
				</li>
				<li class="on">
					<!-- 현재 on -->
					<dl>
						<dt>4</dt>
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
						<dd><c:choose><c:when test="${amount > 1 }">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
					</dl>
				</li>
			</ul>
			-->
			<!-- //step -->
			<form:form commandName="evtrsvnMstVO" name="evtrsvnMstVO" id="popFrm" action="./evtrsvnPop" target="rsvnPop" method="post">
				<input type="hidden" name="evtRsvnIdx" value="${resultVO.evtRsvnIdx }"/>
			</form:form>
			<!-- signup2 -->
			<div class="signup2">
			<c:choose>
				<c:when test="${resultVO.evtRsvnApptype eq '20' }">
				<!-- 무료 -->
				<!-- <h4 class="h4 mb40">예약이 완료 되었습니다.</h4> -->
				<p class="desc desc3 mb35">강연/행사/영화 예약이 완료 되었습니다.</p>
			</c:when>
			<c:otherwise>
					<c:choose>
						<c:when test="${resultVO.evtPersnGbn eq '2001'}">
							<p class="desc desc3 mb5">강연/행사/영화 예약이 완료 되었습니다.</p>
							<h4 class="h4 mb40">관람 시작 전 까지  결제 가능하며<br>당일날 현장에서도 결제할 수 있습니다.</h4>
						</c:when>
						<c:otherwise>
							<h4 class="h4 mb40"><span class="red"><fmt:formatDate value="${resultVO.evtPaywaitEnddatetime}" pattern="yyyy년 MM월 dd일 HH시 mm분"/></span> 까지 결제 하셔야<br><span class="red">예약이 완료</span>됩니다.</h4>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
				<c:choose>
					<c:when test="${amount > 1 }">
					<!-- calc -->
					<div class="calc">
						<div class="cal1">
							<dl>
								<dt>총 금액</dt>
								<dd><fmt:formatNumber value="${amount}"/>원</dd>
							</dl>
							<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
							<dl>
								<dt>할인 예상 금액</dt>
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

						<form:form commandName="evtrsvnMstVO"  action="/web/mypage/myRsvn/myRsvnPay" method="post">
							<input type="hidden" name="rsvnIdx" value="${evtrsvnMstVO.evtRsvnIdx }"/>
							<input type="hidden" name="gubun" value="EVT"/>
							<!-- btn_area -->
							<ul class="btn_area mb70">
								<li><a href="javascript:fn_direct_pay()" class="btn_ty_m_c1">바로 결제</a></li>
							</ul>
						</form:form>
						
					</c:when>
					<c:otherwise>

					</c:otherwise>
				</c:choose>

				<!-- //btn_area -->
				<!-- <p class="stit">예약 정보 확인</p> -->
				<!-- table -->
				<div class="table2 responsive-type mb50">
				
					<table>
						<caption>예약 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:25%">
							<col style="width:75%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">강연/행사/영화 제목</th>
								<td class="b">
									<c:out value="${resultVO.evtName}"/>
									<!-- 강연행사 타이틀 강연행사 타이틀 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약번호</th>
								<td class="b">
									<c:out value="${resultVO.evtRsvnno}"/>
									<!-- r000000000000 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약자</th>
								<td class="b">
									<c:out value="${resultVO.evtRsvnCustnm}"/>
									<!-- 홍길동 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">단체, 학교, 기관 명</th>
								<td class="b">
									<c:out value="${resultVO.evtRsvnGrpnm}"/>
									<!-- 00학교 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">예약자 연락처</th>
								<td class="b">
									<tags:HpDecrypt param="${resultVO.evtRsvnMoblphon}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">방문 인솔자명</th>
								<td class="b">
									<c:out value="${resultVO.evtVisitCustnm}"/>
									<!-- 홍길순 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">방문 인솔자 연락처</th>
								<td class="b">
									<tags:HpDecrypt param="${resultVO.evtVisitMoblphon}"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">참가인원</th>
								<td class="b">
									<c:out value="${resultVO.evtVeingnmpr}"/> 명
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
								<th scope="row" class="vtop">일시</th>
								<td class="b">
									<fmt:parseDate var="dateStr1" value="${resultVO.evtVeingdate}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateStr1}" pattern="yyyy년 MM월 dd일" />
									&nbsp;&nbsp;<c:out value="${fn:substring(resultVO.evtStime,0,2)}:${fn:substring(resultVO.evtStime,2,4)}" /> ~ <c:out value="${fn:substring(resultVO.evtEtime,0,2)}:${fn:substring(resultVO.evtEtime,2,4)}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인원수</th>
								<td class="b">
									<c:set var="prevItemCd" value="" />
									<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
									<c:if test="${item.evtRsvnItemcnt > 0 && item.evtRsvnItemcd ne prevItemCd }"><c:out value="${item.evtItemNm }"/> : <c:out value="${item.evtRsvnItemcnt }"/>명&nbsp;&nbsp;&nbsp;</c:if>
										<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">이메일</th>
								<td><c:out value="${resultVO.evtEmail}"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				
			<c:if test="${amount < 1 or resultVO.evtPersnGbn eq '2001'}">
				<div class="btn_area">
					<!-- <button class="btn_ty_m_c1" onclick="fn_pop_rsvnCerti()">예약증 출력</button> -->
					
					<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EVT" class="btn_ty_m3_c1">예약현황보기</a></li>
					<li><a href="./evtrsvnList" class="btn_ty_m3_c5">메인화면으로</a></li>					
					
				</div>
			</c:if>
			
				<!-- //table -->
			</div>
			<!-- //ex_txt -->
			<!-- //signup2 -->
			
		</div>
		<!-- //join_wrap -->
		<ul class="btn_area long" style="display:none;">  
			<li><a href="./evtrsvnList" class="btn_ty_m_c1">강연/행사/영화 목록</a></li>
			<c:choose>
				<c:when test="${amount > 1 }">
					<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EVT" class="btn_ty_m3_c5">나의 예약내역</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/web/mypage/myRsvn/myRsvnCompList?gubun=EVT" class="btn_ty_m3_c5">나의 예약내역</a></li>
				</c:otherwise>
			</c:choose>

		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>