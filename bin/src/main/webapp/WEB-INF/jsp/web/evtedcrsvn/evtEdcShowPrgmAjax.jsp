<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtShowPrgmAjax.jsp
  * @Description : 회차 목록 JSP
  * @Modification Information
  * @
  * @  수정일               수정자          수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.17    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.17
  *  @version 1.0
  *  @see
  *
  */
%>

	<div class="search_wrap">
		<fieldset>
				<span class="total"><strong>
					<fmt:parseDate var="extYmd" value="${param.evtTime}" pattern="yyyyMMdd"/>
					<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
					<c:choose>
						<c:when test="${dayWeek eq '1'}">(일)</c:when>
						<c:when test="${dayWeek eq '2'}">(월)</c:when>
						<c:when test="${dayWeek eq '3'}">(화)</c:when>
						<c:when test="${dayWeek eq '4'}">(수)</c:when>
						<c:when test="${dayWeek eq '5'}">(목)</c:when>
						<c:when test="${dayWeek eq '6'}">(금)</c:when>
						<c:when test="${dayWeek eq '7'}">(토)</c:when>
						<c:when test="${dayWeek eq '8'}">(공휴일)</c:when>
					</c:choose>
					</strong>
				</span>
			</fieldset>
	</div>
	<!-- //search_wrap -->
	
		<c:choose>
			<c:when test="${programVO.holdCnt > 0}">
				<div>휴관일 입니다.</div>
			</c:when>
			<c:when test="${empty timeList}">
				<div>예약 가능한 시간표가 없습니다.</div>
			</c:when>
			<c:otherwise>
				
				<ul class="prog_list four">
			
					<c:forEach items="${timeList}" var="item" varStatus="status">
						<li>
							<div class="p_top">
								<input type='hidden' name='seq' id='seq_${status.index}' value='<c:out value="${item.evtTimeSeq}"/>' />
								<span><c:out value="${item.timeName}"/>&nbsp;${fn:substring(item.sTime,0,2)}:${fn:substring(item.sTime,2,4)} ~ ${fn:substring(item.eTime,0,2)}:${fn:substring(item.eTime,2,4)}</span>
							</div>
							<!-- //p_top -->
							<div class="p_cont">
				
							</div>
							<!-- //p_cont -->
							<div class="seat">
								<span class="st_tit">잔여팀</span>
								<span class="st_num">
									<%-- <em><fmt:formatNumber value="${(item.totCapa-item.rsvCnt)>0?(item.totCapa-item.rsvCnt):0}"/></em> <b>/</b> <fmt:formatNumber value="${item.totCapa}"/>팀 --%>
									<em><fmt:formatNumber value="${(item.grpOrgCnt-item.grpRsvCnt) > 0 ? (item.grpOrgCnt-item.grpRsvCnt):0}"/></em> <b>/</b> <c:out value="${item.grpOrgCnt}"/> 팀
								</span>
							</div>
							<!-- //seat -->
							<c:choose>
								<c:when test="${programVO.rsvAbleYn eq 'W'}">
									<ul class="p_btn one">
										<li><a  class="btn_ty_c8">접수준비</a></li>
									</ul>
								</c:when>
				                <%--<c:when test="${programVO.rsvAbleYn eq 'Y' and item.rsvAbleYn eq 'Y' and item.totCapa > item.rsvCnt}"> 원본 --%>
								<c:when test="${item.rsvAbleYn eq 'Y' and item.grpOrgCnt > item.grpRsvCnt}">
									<c:set var="btnType" value=""/>
									<c:if test="${programVO.evtOrgMincnt < 1}"><c:set var="btnType" value=" one"/></c:if>
									<ul class="p_btn${btnType}">
									
										<%-- <li><a href="javascript:fn_rsv_evt('10','<c:out value="${param.evtTime}"/>','<c:out value="${item.evtTimestdSeq}"/>','<c:out value="${item.evtTimeseq}"/>')" class="btn_ty_c6">가족</a></li> --%>
										<li><a href="javascript:fn_rsv_evt('20','<c:out value="${param.evtTime}"/>','<c:out value="${item.evtTimestdSeq}"/>','<c:out value="${item.evtTimeseq}"/>')" class="btn_ty_c3">단체</a></li>
										
										<%-- <c:if test="${programVO.evtOrgMincnt > 0}">
											<c:choose>
												<c:when test="${programVO.evtOrgMincnt <= (item.totCapa-item.rsvCnt)}">
													<li><a href="javascript:fn_rsv_evt('20','<c:out value="${param.evtTime}"/>','<c:out value="${item.evtTimestdSeq}"/>','<c:out value="${item.evtTimeseq}"/>')" class="btn_ty_c3">단체</a></li>
												</c:when>
												<c:otherwise>
													<li><a  class="btn_ty_c4">단체마감</a></li>
												</c:otherwise>
											</c:choose>
										</c:if> --%>
										
									</ul>
								</c:when>
								<c:when test="${programVO.rsvAbleYn eq 'Y' and item.rsvAbleYn eq 'Y'}">
									<ul class="p_btn one">
										<li><a  class="btn_ty_c4">예약마감</a></li>
									</ul>
								</c:when>
								<c:otherwise>
									<ul class="p_btn one">
										<li><a  class="btn_ty_c4">예약종료</a></li>
									</ul>
								</c:otherwise>
							</c:choose>
						</li>
					</c:forEach>
			
				</ul>
			
			</c:otherwise>
		</c:choose>