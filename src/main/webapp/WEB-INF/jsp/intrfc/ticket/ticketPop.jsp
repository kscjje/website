<%
 /**
  * @Class Name : ticketPop.jsp
  * @Description : 모바일 티켓
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.28    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.28
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script>
$(document).ready(function() {
	$('body').addClass('w_pop')
});

</script>
<div class="w_wrap">
	<div class="modal-dialog" id="container" style="max-width:100%;padding: 0;">
		<div id="header-wrap" class="fix">
			<div id="header" class="header-wrap">
				<h1><a >로고</a></h1>
				<!-- <a href="javascript:window.close();" class="pop_close">닫기</a> -->
			</div>
		</div>

		<c:choose>
			<c:when test="${ ticket.apptype eq '20' }">
				<div class="modal-content">
					<div class="modal-body" style="padding-top:70px;">
						<div class="modal-txt">
						<div class="bar_top mb10">
							<p class="tit"><c:if test="${ticket.gubun eq 'EXBT' }">${ticket.partNm } </c:if>${ticket.title }</p>
							<span class="time_tit">시간</span>
							<span class="time_desc">${fn:substring(ticket.sTime,0,2) }시 ${fn:substring(ticket.sTime,2,4) }분 ~ ${fn:substring(ticket.eTime,0,2) }시 ${fn:substring(ticket.eTime,2,4) }분</span>
						</div>
						<div class="bar_top mb30">
							<c:choose>
								<c:when test="${empty ticket.ticketNo}">
									<div class="nobar"><i><img src="<%=strContext%>/images/mobile/ico_not.png" alt="알림아이콘"></i>티켓이 발행되지 않는<br><c:choose><c:when test="${ticket.gubun eq 'EXBT' }">관람입니다.</c:when><c:otherwise>강연/행사/영화입니다.</c:otherwise></c:choose></div>
								</c:when>
								<c:otherwise>
									<img src="/web/common/file/barcode/${ticket.ticketNo }" alt="바코드이미지" >
								</c:otherwise>
							</c:choose>
						</div>
<c:if test="${!empty ticket.ticketDesc }">
						<div class="ex_txt mb15">
							<p class="ex_tit"><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 유의사항 안내</p>
							${fn:replace(ticket.ticketDesc , cslf , '<br/>')}
						</div>
</c:if>
						<!-- //bar_top -->
						<div class="table2 responsive-type mb50">
								<table>
									<caption>티켓정보</caption>
									<colgroup>
										<col style="width:30%">
										<col style="width:70%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="vtop">제목</th>
											<td class="b"><c:out value="${ticket.title}"/></td>
										</tr>
										<tr>
											<th scope="row" class="vtop">예약번호</th>
											<td class="b"><c:out value="${ticket.rsvnNo }"/></td>
										</tr>
										<tr>
											<th scope="row" class="vtop">일시</th>
											<td class="b"><fmt:parseDate var="extYmd" value="${ticket.ymd}" pattern="yyyyMMdd"/>
											<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
											&nbsp;&nbsp;${fn:substring(ticket.sTime,0,2) }시 ${fn:substring(ticket.sTime,2,4) }분 ~ ${fn:substring(ticket.eTime,0,2) }시 ${fn:substring(ticket.eTime,2,4) }분
											</td>
										</tr>

										<tr>
											<th scope="row" class="vtop">총 인원</th>
											<td >
<c:forEach items="${ticket.itemList}" var="item" varStatus="s">
										<c:choose>
											<c:when test="${ticket.gubun eq 'EXBT' }">
												<ul class="num_cont">
												<c:if test="${item.itemCd ne prevItemCd}">
													<c:set var="prevItemCd" value="${item.itemCd}" />
													<li>
														<dl>
															<dt><c:out value="${item.itemNm}"/>&nbsp;<c:out value="${item.itemCnt}"/> 명</dt>
														</dl>
													</li>
												</c:if>
												</ul>
												</c:when>
												<c:otherwise>
													<ul class="num_cont">
														<c:if test="${item.evtRsvnItemcd ne prevItemCd}">
															<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
															<li>
																<dl>
																	<dt><c:out value="${item.evtItemNm}"/>&nbsp;<c:out value="${item.evtRsvnItemcnt}"/> 명</dt>
																</dl>
															</li>
														</c:if>
													</ul>
												</c:otherwise>
											</c:choose>
</c:forEach>
											</td>
										</tr>

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
			</c:when>
			<c:otherwise>
			<div class="bar_top">
				<div class="nobar" style="padding : 70px"><i><img src="<%=strContext%>/images/mobile/ico_not.png" alt="알림아이콘"></i>
				유효하지 않은 티켓입니다.</div>
			</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- //modal-dialog -->
</div>
<!-- //팝업 -->