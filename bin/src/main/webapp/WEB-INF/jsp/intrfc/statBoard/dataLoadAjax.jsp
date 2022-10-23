<%
/**
 * @Class Name : statBoardDetail.jsp
 * @Description : 관람 현황판
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
		<div class="th">
			<span class="time">시간</span>
			<span class="seat">잔여좌석</span>
		</div>
		<c:choose>
			<c:when test="${empty list }">
				<div class="td">
				 <span class="seat out">--</span>
				 <span class="seat out">--</span>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="v" varStatus="s">
					<c:set value="${v.totCapa - v.reserveCnt }" var="rmCnt"/>
					<div class="td">
						<span class="time"><c:out value="${v.timeName }"/>&nbsp;&nbsp;<c:out value="${fn:substring(v.sTime,0,2) }"/>:<c:out value="${fn:substring(v.sTime,2,4) }"/></span>
						<c:choose>
							<c:when test="${ rmCnt eq 0 }">
								<span class="seat out">매진</span>
							</c:when>
							<c:when test="${ v.reserveAbleYn eq 'N' }">
								<span class="seat end">--</span>
							</c:when>
							<c:when test="${ rmCnt ne 0 }">
								<span class="seat"><c:out value="${rmCnt }"/></span>
							</c:when>
						</c:choose>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>