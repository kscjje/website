<%
 /**
  * @Class Name : myTicketInfoAjax.jsp
  * @Description : 쿠폰 상세 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.18    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.18
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
		<div class="modal-body ty">
			<div class="m_barcode">
				<div class="mc_top">
					<dl>
						<dt><c:out value="${dtl.cpnName}"/></dt>
						<dd><c:out value="${dtl.cpnDcrate}"/>%할인권</dd>
					</dl>
					<p>
						<fmt:parseDate var="dateStr1" value="${dtl.cpnStdt }" pattern="yyyyMMdd" />
						<fmt:parseDate var="dateStr2" value="${dtl.cpnEddt }" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
						<c:out value="${fn:substring(dtl.cpnEdtime,0,2) }"/> : <c:out value="${fn:substring(dtl.cpnEdtime,2,4) }"/>
					</p>
					<dl>
						<dt>
						<c:choose>
							<c:when test="${dtl.cpnUseyn eq 'N' and dtl.endtime eq 'U' }">
								사용가능
							</c:when>
							<c:when test="${dtl.cpnUseyn eq 'Y'}">
								사용완료
							</c:when>
							<c:when test="${dtl.endtime eq 'E' }">
								기한만료
							</c:when>
						</c:choose>
						</dt>
					</dl>
				</div>
				<div class="mc_btm">
					<img src="/web/common/file/barcode/${dtl.cpnUid }" alt="바코드이미지">
				</div>
			</div>
			<!-- //m_barcode -->
			<dl class="m_txt">
				<dt>※ 쿠폰으로 할인받는 방법 </dt>
				<dd><strong>관람 / 강연,행사,영화 / 교육 프로그램 예약 시</strong> 보유하고 있는 쿠폰을 가지고 할인 받으실 수 있습니다.</dd>
			</dl>
			<!-- //m_txt -->
		</div>
<script>
</script>