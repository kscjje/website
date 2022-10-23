<%
 /**
  * @Class Name : myRsvnCancelList.jsp
  * @Description : 결제 취소 목록 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.10    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.10
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<script>
	$(document).ready(function() {
		$('.m_table2 table tr th.pay i').click(function() {
			$(this).siblings('.bubble').fadeToggle();
		});//컬럼 버튼
	});
	function fn_move_detail(oid , gubun , pageNum) {
		if (pageNum == "") {
			pageNum = "1";
		} else if (isNaN(pageNum)) {
			pageNum = "1";
		}
		window.location.href = "./myRsvnDetail?orderId="+oid+"&gubun="+gubun+"&pageIndex=" + pageNum;
	}
</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">나의 예약내역</h3>
		<ul class="b_tab t3">
				<li><!-- 활성화 class="on"-->
					<a href="./myRsvnList?gubun=<c:out value="${param.gubun }"/>">
						<dl>
							<dt>결제 대기상태</dt>
							<dd>${reserveCount.exbtData.waitingCnt+reserveCount.edcData.waitingCnt+reserveCount.evtData.waitingCnt }</dd>
						</dl>
					</a>
				</li>
				<li >
					<a href="./myRsvnCompList?gubun=<c:out value="${param.gubun }"/>">
						<dl>
							<dt>예약 및 결제 완료</dt>
							<dd>${reserveCount.exbtData.compCnt+reserveCount.edcData.compCnt+reserveCount.evtData.compCnt }</dd>
						</dl>
					</a>
				</li>
				<li class="on">
					<a href="./myRsvnCancelList?gubun=<c:out value="${param.gubun }"/>">
						<dl>
							<dt>취소 및 환불</dt>
							<dd>${reserveCount.exbtData.cancelCnt+reserveCount.edcData.cancelCnt+reserveCount.evtData.cancelCnt }</dd>
						</dl>
					</a>
				</li>
			</ul>
		<!-- //b_tab -->
			<ul class="tab tab4 mb35">
				<li <c:if test="${gubun eq 'EXBT' }">class="on"</c:if>><a href="./myRsvnCancelList?gubun=EXBT">관람<em> ${reserveCount.exbtData.cancelCnt }</em></a></li><!-- 현재위치 class: on 추가 -->
				<li <c:if test="${gubun eq 'EDC' }">class="on"</c:if>><a href="./myRsvnCancelList?gubun=EDC">교육예약<em>${reserveCount.edcData.cancelCnt}</em></a></li>
				<li <c:if test="${gubun eq 'EVT' }">class="on"</c:if>><a href="./myRsvnCancelList?gubun=EVT">강연/행사/영화<em>${reserveCount.evtData.cancelCnt }</em></a></li>
			</ul>
			<!-- tab -->
		<c:import url="/web/mypage/myRsvn/incCompleteList" ></c:import>
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->