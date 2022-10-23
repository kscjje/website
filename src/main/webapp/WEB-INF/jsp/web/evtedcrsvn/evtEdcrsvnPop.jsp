<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/headerOther.jsp"%>

<%
 /**
  * @Class Name : evtrsvnPop.jsp
  * @Description : 예약증 팝업 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.06.08    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.08
  *  @version 1.0
  *  @see
  *
  */
%>

<script>

	$(document).ready(function() {
		$('body').addClass('w_pop')
	});
	
	window.addEventListener("beforeprint", function(event) {
		$('#printBtn').css('display','none')
		$('.modal-title').css('display','none')
	})
	
	window.addEventListener("afterprint", function(event) {
		$('#printBtn').css('display','inline')
		$('.modal-title').css('display','inline')
	})

</script>

<div class="w_wrap">
	<div class="modal-dialog" id="container" style="max-width:100%;padding: 0;">
		<div class="modal-title">
			<h4>상세 예약정보</h4>
			<a href="javascript:window.close();" class="pop_close">닫기</a>
		</div>
		<div class="modal-content">
			<div class="modal-body" style="padding-top:100px;">
				<div class="modal-txt">
				<p class="stit">예약 프로그램</p>
				<div class="table2 responsive-type mb50">
						<table>
							<caption>출력용 예약증</caption>
							<colgroup>
								<col style="width:30%">
								<col style="width:70%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="vtop">제목</th>
									<td class="b"><c:out value="${resultVO.evtName}"/></td>
								</tr>
								<tr>
									<th scope="row" class="vtop">예약번호</th>
									<td class="b"><c:out value="${resultVO.evtRsvnno}"/></td>
								</tr>
								<tr>
									<th scope="row" class="vtop">예약 인원</th>
									<td class="b">
									
										<%-- <c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
											<c:if test="${item.evtRsvnItemcd ne prevItemCd }">
												<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
											 	<c:out value="${item.evtItemNm }"/> : <c:out value="${item.evtRsvnItemcnt }"/>명&nbsp;&nbsp;&nbsp;
											</c:if>
										</c:forEach> --%>
										
										<c:out value="${resultVO.evtVeingnmpr}"/> 명
									
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">단체예약 일시</th>
									<td class="b">
										<fmt:parseDate var="dateStr1" value="${resultVO.evtVeingdate}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateStr1}" pattern="yyyy년 MM월 dd일" />
										&nbsp;&nbsp;<c:out value="${fn:substring(resultVO.evtStime,0,2)} : ${fn:substring(resultVO.evtStime,2,4)}" /> ~ <c:out value="${fn:substring(resultVO.evtEtime,0,2)} : ${fn:substring(resultVO.evtEtime,2,4)}" />
									</td>
								</tr>
							</tbody>
						</table>
						<!-- //mod_table -->
					</div>
					<!-- //table -->
					<p class="stit">예약자 정보</p>
					<div class="table2 responsive-type mb50">
						<table>
							<caption>출력용 예약증2</caption>
							<colgroup>
								<col style="width:30%">
								<col style="width:70%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="vtop">예약자 이름</th>
									<td class="b"><c:out value="${resultVO.evtRsvnCustnm }"/></td>
								</tr>
								<tr>
									<th scope="row" class="vtop">휴대전화번호</th>
									<td class="b">
										<tags:HpDecrypt param="${resultVO.evtRsvnMoblphon}"/>
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">이메일</th>
									<td class="b">
										<tags:EmailDecrypt param="${resultVO.evtEmail}"/>
									</td>
								</tr>
									<c:if test="${resultVO.evtPersnGbn eq '2001' }">
										<tr>
											<th scope="row" class="vtop">단체명</th>
											<td class="b"><c:out value="${resultVO.evtRsvnGrpnm}"/></td>
										</tr>
										<tr>
											<th scope="row" class="vtop">인솔자 명</th>
											<td class="b"><c:out value="${resultVO.evtVisitCustnm}"/></td>
										</tr>
										<tr>
											<th scope="row" class="vtop">인솔자 연락처</th>
											<td class="b">
												<tags:HpDecrypt param="${resultVO.evtVisitMoblphon}"/>
											</td>
										</tr>
									</c:if>
							</tbody>
						</table>
						<!-- //mod_table -->
					</div>
					<!-- //table -->
				</div>
				<!-- //modal-txt -->
			</div>
			<!-- //modal-body -->
		</div>
		<!-- //modal-content -->
		<div class="btn_area">
			<button class="btn_ty_m_c1" id="printBtn" onclick="window.print()">예약증 출력</button>
		</div>
	</div>
	<!-- //modal-dialog -->
</div>
<!-- //팝업 -->