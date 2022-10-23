<%
 /**
  * @Class Name : incpayDetail.jsp
  * @Description : 결제 상세(EDC)
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.15    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.15
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
				<span><c:out value="${resultVO.edcRsvnno}"/></span>
				<dl>
					<dt>
							<div class="title_wrap">
							<c:if test="${resultVO.edcOnlineyn eq 'Y' }">
								<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
							</c:if>
							<c:out value='${resultVO.edcPrgmnm}' />
							</div>
					</dt>
					<dd>
						${resultVO.eduPeriod }
					</dd>
				</dl>
			</div>
			<a href="#" onclick="fnPayGuideOnClick(this); return false;"><div class="his_stit">결제 가격 안내</div></a>
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
					<li>
						<div class="c_l"><c:out value="${resultVO.edcPrgmnm}"/></div>
						<div class="c_r">
							<c:set var="totAmt" value="${resultVO.edcProgmCost}" />
							<span><fmt:formatNumber value="${totAmt}"/>원</span>
						</div>
					</li>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //결제 가격 안내 -->
<c:choose>
	<c:when test="${resultVO.edcDcamt > 0 }">
			<!-- 쿠폰 적용 내역 -->
			<div class="his_stit">할인 적용 내역</div>
			<!-- his_cont -->
			<div class="his_cont">
				<ul>
					<li>
						<div class="c_l">
							<c:choose>
							 	<c:when test="${!empty resultVO.cpnName}"><c:out value="${resultVO.cpnName}"/></c:when>
							 	<c:when test="${!empty resultVO.dcName}"><c:out value="${resultVO.dcName}"/></c:when>
							 	<c:when test="${!empty resultVO.dcTypeNm}"><c:out value="${resultVO.dcTypeNm}"/></c:when>
							</c:choose>
							<c:choose><c:when test="${resultVO.cpnDcrate > 0}">(${resultVO.cpnDcrate}%)</c:when><c:when test="${resultVO.dcRate>0 }">(${resultVO.dcRate}%)</c:when></c:choose><c:set var="discCnt" value="${discCnt+1}" />
						</div>
						<div class="c_l">
							<span>- <fmt:formatNumber value="${resultVO.edcDcamt}"/>원</span>
						</div>
					</li>
				</ul>
			</div>
			<!-- //his_cont -->
			<!-- //쿠폰 적용 내역 -->
	</c:when>
</c:choose>
			<div class="calc his_calc">
				<div class="cal1">
					<dl>
						<dt>총 금액</dt>
						<dd><fmt:formatNumber value="${totAmt}"/>원</dd>
					</dl>
					<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
					<dl>
						<dt>할인 금액</dt>
						<dd><fmt:formatNumber value="${resultVO.edcDcamt}"/>원</dd>
					</dl>
				</div>
				<div class="cal2">
					<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
					<dl>
						<dt>최종 결제 금액</dt>
						<dd class="blu"><fmt:formatNumber value="${resultVO.edcTotamt}"/>원</dd>
					</dl>
				</div>
			</div>
		</li>
		<div class="btm_box">
				<c:choose>
					<c:when test="${fn:indexOf(resultVO.edcStat , '3') == 0}">
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
					<c:when test="${resultVO.edcStat eq '20'}">
						<c:choose>
							<c:when test="${resultVO.edcOnoffpyntype eq '20' }">
								<span class="st2 s2">취소 불가능</span>
								<span class="txt"> 오프라인에서 결제한 내역은 온라인취소가 불가능합니다.</span>
							</c:when>
							<c:when test="${appGbn eq '2' or resultVO.rfndRule.validYn eq 'Y' or resultVO.rfndRule.cancelAbleYn eq 'Y'}">
							<a href="javascript:fn_cancel_info(${resultVO.edcRsvnReqid})"><span class="st2 s1">취소 하기</span></a>
							<span><tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="EDC"/></span>
									<input type="hidden" name="rsvnIdxChk" value="${resultVO.edcRsvnReqid}"/>
									<c:if test="${resultVO.rfndRate >=100 }">
										<c:set var="cancelAbleCnt" value="${cancelAbleCnt + 1}" />
									</c:if>
							</c:when>
							<c:otherwise>
									<span class="st2 s2">취소 불가능</span>
									<span class="txt"> <tags:RfndRuleText ruleObj="${resultVO.rfndRule}" gubun="EDC"/></span>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
			</div>
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