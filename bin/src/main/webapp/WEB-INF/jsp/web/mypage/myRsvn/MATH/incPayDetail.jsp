<%
 /**
  * @Class Name : incpayDetail.jsp
  * @Description : 결제 상세(MATH)
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.16    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.16
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/top.jsp"%>
<p class="stit">총 <em class="pink">${fn:length(list) }</em>건 결제정보</p>
<c:set var="cancelAbleCnt" value="0" />
<c:set var="totalCnt" value="${fn:length(list) }" />
<c:forEach items="${list}" var="resultVO" varStatus="status">
	<!-- his_list -->
	<ul class="his_list mb20">
		<li>
			<!-- 결제 가격 안내 -->
			<div class="his_tit">
				<span><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></span>
				<span><c:out value="${resultVO.rsvnNo}"/></span>
				<dl>
					<dt><c:out value="${resultVO.partNm}"/>
						<c:choose>
							<c:when test="${resultVO.mathType eq '1001' }">관람</c:when>
							<c:otherwise><c:out value="${resultVO.mathName }"/></c:otherwise>
						</c:choose>
					</dt>
					<dd><fmt:parseDate var="extYmd" value="${resultVO.ymd}" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;${fn:substring(resultVO.mathStime,0,2)}:${fn:substring(resultVO.mathStime,2,4)} ~ ${fn:substring(resultVO.mathEtime,0,2)}:${fn:substring(resultVO.mathEtime,2,4)}
					</dd>
				</dl>

			</div>

			<a href="#" onclick="fnPayGuideOnClick(this); return false;"><div class="his_stit">결제 가격 안내</div></a>
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
<c:set var="prevItemCd" value="" />
<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
<c:if test="${item.itemCd ne prevItemCd}">
<c:set var="prevItemCd" value="${item.itemCd}" />
					<li>
						<div class="c_l"><c:out value="${item.itemNm}"/>(<fmt:formatNumber value="${item.price}"/>원)&nbsp;&nbsp;<c:out value="${item.itemCnt}"/>명</div>
						<div class="c_r">
							<span><fmt:formatNumber value="${item.amount}"/>원</span>
						</div>
					</li>
</c:if>
</c:forEach>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //결제 가격 안내 -->
			<!-- 쿠폰 적용 내역 -->
			<div class="his_stit">할인 적용 내역</div>
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
<c:set var="discCnt" value="0" />
<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
<c:if test="${!empty item.dcType}">
<li>
	<div class="c_l">
		<c:choose>
		 	<c:when test="${!empty item.cpnName}"><c:out value="${item.cpnName}"/></c:when>
		 	<c:when test="${!empty item.dcName}"> <c:out value="${item.dcName}"/></c:when>
		 	<c:when test="${!empty item.dcTypeNm}"><c:out value="${item.dcTypeNm}"/></c:when>
		</c:choose>
		(${item.dcRate}%)&nbsp;&nbsp;<c:out value="${item.itemNm}"/><c:set var="discCnt" value="${discCnt+1}" />
	</div>
	<div class="c_r">
		<span>- <fmt:formatNumber value="${item.dcAmount}"/>원</span>
	</div>
</li>
</c:if>
</c:forEach>
<c:if test="${discCnt == 0}">
<li>
	<div class="c_l">할인 적용 내역이 없습니다.</div>
</li>
</c:if>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //쿠폰 적용 내역 -->
			<div class="calc his_calc">
				<div class="cal1">
					<dl>
						<dt>총 금액</dt>
						<dd><fmt:formatNumber value="${resultVO.saleamt}"/>원</dd>
					</dl>
					<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
					<dl>
						<dt>할인 금액</dt>
						<dd><fmt:formatNumber value="${resultVO.saleamt-resultVO.payamt}"/>원</dd>
					</dl>
				</div>
				<div class="cal2">
					<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
					<dl>
						<dt>최종 결제 금액</dt>
						<dd class="blu"><fmt:formatNumber value="${resultVO.payamt}"/>원</dd>
					</dl>
				</div>
			</div>
			<div class="btm_box">
				<c:choose>
					<c:when test="${fn:indexOf(resultVO.apptype , '3') == 0}">
						 <span class="st2 s4">취소 완료</span>
								<span class="txt">결제취소가 완료되었습니다.</span>
								<ul class="p_dt">
									<li>
										<dl>
											<dt>취소일시</dt>
											<dd class="b red"><fmt:formatDate value="${resultVO.moddate}" pattern="yyyy.MM.dd HH:mm"/></dd>
										</dl>
									</li>
									<li>
										<dl>
											<dt>환불금액</dt>
											<dd><fmt:formatNumber value="${resultVO.cancelAmt}"/>원</dd>
										</dl>
									</li>
									<li>
										<dl>
											<dt>공제금액</dt>
											<dd><fmt:formatNumber value="${resultVO.breakAmt}"/>원</dd>
										</dl>
									</li>
								</ul>
					</c:when>
					<c:when test="${resultVO.apptype eq '20'}">
						<c:choose>
							<c:when test="${resultVO.onoffPaytype eq '20' }">
								<span class="st2 s2">취소 불가능</span>
								<span class="txt"> 오프라인에서 결제한 내역은 온라인취소가 불가능합니다.</span>
							</c:when>
							<c:when test="${resultVO.ticketChkCnt >0 }">
								<span class="st2 s2">취소 불가능</span>
								<span class="txt"> 티켓검표가 완료되어 취소가 불가능합니다.</span>
							</c:when>
							<c:when test="${appGbn eq '2' or resultVO.rfndRule.validYn eq 'Y' or resultVO.rfndRule.cancelAbleYn eq 'Y'}">
							<a href="javascript:fn_cancel_info(${resultVO.rsvnIdx})"><span class="st2 s1">취소 하기</span></a>
							<span class="txt"><tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="MATH"/></span>
									<input type="hidden" name="rsvnIdxChk" value="${resultVO.rsvnIdx}"/>
									<c:if test="${resultVO.rfndRate >=100 }">
										<c:set var="cancelAbleCnt" value="${cancelAbleCnt + 1}" />
									</c:if>
							</c:when>
							<c:otherwise>
									<span class="st2 s2">취소 불가능</span>
									<span class="txt"> <tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="MATH"/></span>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
			</div>
		</li>
	</ul>
</c:forEach>
<script>

	$('.his_stit').click(function() {
		$(this).toggleClass('on');
		$(this).next('.his_cont').stop().slideToggle();
	}); //제목클릭이벤트
	totalCnt = ${totalCnt};
	cancelAbleCnt = ${cancelAbleCnt};
	$('.sub_cont').show();

	function fnPayGuideOnClick(parmThis) {
		$(".his_stit").toggleClass('on');
		$(parmThis).next('.his_cont').stop().slideToggle();
		return false;	
	}		
	
</script>