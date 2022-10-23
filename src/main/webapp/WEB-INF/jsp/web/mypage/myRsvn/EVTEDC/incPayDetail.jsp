<%
 /**
  * @Class Name : incpayDetail.jsp
  * @Description : 결제 상세(EVT)
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------       --------    ---------------------------
  * @ 2021.07.15    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.07.15
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
	<ul class="his_list mb20">
		<li>
			<!-- 결제 가격 안내 -->
			<div class="his_tit">
				<span><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></span>
				<span><c:out value="${resultVO.evtRsvnno}"/></span>
				<dl>
					<dt><c:out value="${resultVO.evtName}"/></dt>
					<dd><fmt:parseDate var="extYmd" value="${resultVO.evtVeingdate}" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;${fn:substring(resultVO.evtStime,0,2)}:${fn:substring(resultVO.evtStime,2,4)} ~ ${fn:substring(resultVO.evtEtime,0,2)}:${fn:substring(resultVO.evtEtime,2,4)}</dd>
				</dl>
			</div>

			<a href="#" onclick="fnPayGuideOnClick(this); return false;"><div class="his_stit">결제 가격 안내</div></a>
			
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
<c:set var="prevItemCd" value="" />
<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
<c:if test="${item.evtRsvnItemcd ne prevItemCd}">
<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
					<li>
						<div class="c_l"><c:out value="${item.evtItemNm}"/>(<fmt:formatNumber value="${item.evtCost}"/>원)&nbsp;&nbsp;<c:out value="${item.evtRsvnItemcnt}"/>명</div>
						<div class="c_r">
							<span><fmt:formatNumber value="${item.evtSalamt}"/>원</span>
						</div>
					</li>
</c:if>
</c:forEach>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //결제 가격 안내 -->
<c:set var="discCnt" value="0" />
<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
<c:if test="${!empty item.evtDcType}"><c:set var="discCnt" value="${discCnt+1}" /></c:if>
</c:forEach>
<c:if test="${discCnt >0 }">
			<!-- 쿠폰 적용 내역 -->
			<div class="his_stit">할인 적용 내역</div>
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
<c:if test="${!empty item.evtDcType}">
<li>
	<div class="c_l">
		<c:choose>
		 	<c:when test="${!empty item.cpnName}"><c:out value="${item.cpnName}"/></c:when>
		 	<c:when test="${!empty item.dcName}"><c:out value="${item.dcName}"/></c:when>
		 	<c:when test="${!empty item.dcTypeNm}"><c:out value="${item.dcTypeNm}"/></c:when>
		</c:choose>
		(${item.evtDcRate}%)&nbsp;&nbsp;<c:out value="${item.evtItemNm}"/>
	</div>
	<div class="c_r">
		<span>- <fmt:formatNumber value="${item.evtDcamt}"/>원</span>
	</div>
</li>
</c:if>
</c:forEach>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //쿠폰 적용 내역 -->
</c:if>

			<div class="calc his_calc">
				<div class="cal1">
					<dl>
						<dt>총 금액</dt>
						<dd><fmt:formatNumber value="${resultVO.evtRsvnSaleamt}"/>원</dd>
					</dl>
					<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
					<dl>
						<dt>할인 금액</dt>
						<dd><fmt:formatNumber value="${resultVO.evtRsvnSaleamt-resultVO.evtRsvnPayamt}"/>원</dd>
					</dl>
				</div>
				<div class="cal2">
					<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
					<dl>
						<dt>최종 결제 금액</dt>
						<dd class="blu"><fmt:formatNumber value="${resultVO.evtRsvnPayamt}"/>원</dd>
					</dl>
				</div>
			</div>
			<div class="btm_box">
				<c:choose>
					<c:when test="${fn:indexOf(resultVO.evtRsvnApptype , '3') == 0}">
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
					<c:when test="${resultVO.evtRsvnApptype eq '20'}">
						<c:choose>
							<c:when test="${resultVO.evtOnoffpyntype eq '20' }">
								<span class="st2 s2">취소 불가능</span>
								<span class="txt"> 오프라인에서 결제한 내역은 온라인취소가 불가능합니다.</span>
							</c:when>
							<c:when test="${resultVO.ticketChkCnt >0 }">
								<span class="st2 s2">취소 불가능</span>
								<span class="txt"> 티켓검표가 완료되어 취소가 불가능합니다.</span>
							</c:when>
							<c:when test="${appGbn eq '2' or resultVO.rfndRule.validYn eq 'Y'  or resultVO.rfndRule.cancelAbleYn eq 'Y'}">
							<a href="javascript:fn_cancel_info(${resultVO.evtRsvnIdx})"><span class="st2 s1">취소 하기</span></a>
							<span><tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="EVT"/></span>
									<input type="hidden" name="rsvnIdxChk" value="${resultVO.evtRsvnIdx}"/>

									<c:if test="${resultVO.rfndRate >=100 }">
										<c:set var="cancelAbleCnt" value="${cancelAbleCnt + 1}" />
									</c:if>
							</c:when>
							<c:otherwise>
									<span class="st2 s2">취소 불가능</span>
									<span class="txt"> <tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="EVT"/></span>
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