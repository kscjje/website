<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : exbtDetailAjax.jsp
  * @Description :관람  상세 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.19    전영석               최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.19
  *  @version 1.0
  *  @see
  *
  */
%>

<div class="search_wrap">
		<fieldset>
			<span class="total"><strong>
				<c:choose>
					<c:when test="${baseDataVO.exbtType eq '1001'}"><c:out value="${baseDataVO.exbtPartNm}"/> 관람</c:when>
					<c:otherwise><c:out value="${baseDataVO.exbtName}"/></c:otherwise>
				</c:choose>
			</strong>
<fmt:parseDate var="extYmd" value="${param.ymd}" pattern="yyyyMMdd"/>
<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
<c:choose>
	<c:when test="${dayWeekNm eq '1'}">(일)</c:when>
	<c:when test="${dayWeekNm eq '2'}">(월)</c:when>
	<c:when test="${dayWeekNm eq '3'}">(화)</c:when>
	<c:when test="${dayWeekNm eq '4'}">(수)</c:when>
	<c:when test="${dayWeekNm eq '5'}">(목)</c:when>
	<c:when test="${dayWeekNm eq '6'}">(금)</c:when>
	<c:when test="${dayWeekNm eq '7'}">(토)</c:when>
	<c:when test="${dayWeekNm eq '8'}">(공휴일)</c:when>
</c:choose>
			</span>
		</fieldset>
</div>

<c:choose>
	<c:when test="${empty baseDataVO }">
			<div style="height:200px;text-align:center">
				프로그램 정보가 없습니다.
			</div>
	</c:when>
	<c:otherwise>
	
<%-- <div class="noti_box">
	<div class="noti_img">
	<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index == 0 }">
		<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" alt="${baseDataVO.exbtName }(상세내용은 하단 참조)"/></c:if>
	</c:forEach>
	</div>
	<div class="noti_txt">
		<p><c:out value="${fn:replace(baseDataVO.exbtIntrcn , crlf , '</p><p>')}" escapeXml="false" /></p>
	</div>
</div> --%>

<c:choose>
	<c:when test="${baseDataVO.holdCnt > 0 }">
		<div style="height:200px;text-align:center">
			휴관일 입니다.
		</div>
	</c:when>
	<c:when test="${empty  timeList or baseDataVO.reserveAbleYn ne 'Y'}">
		<div style="height:200px;text-align:center">
			등록된 시간표가 없습니다.
		</div>
	</c:when>
	<c:otherwise>
		<ul class="prog_list four">
		<c:forEach items="${timeList}" var="item" varStatus="status">
				<li>
					<div class="p_top">
						<span><c:out value="${item.timeName }"/> ${fn:substring(item.sTime,0,2) }:${fn:substring(item.sTime,2,4) } ~ ${fn:substring(item.eTime,0,2) }:${fn:substring(item.eTime,2,4) }</span>
					</div>
					<!-- //p_top -->
					<div class="p_cont">
						<c:out value="${item.timeTitle }"/>
					</div>
					<!-- //p_cont -->
					<div class="seat">
						<span class="st_tit">잔여정원</span>
						<span class="st_num"><em><fmt:formatNumber value="${(item.totCapa-item.reserveCnt)<0?0:(item.totCapa-item.reserveCnt)}"/></em> <b>/</b> <fmt:formatNumber value="${item.totCapa}"/>석</span>
					</div>
					<!-- //seat -->
					<c:set var="btnType" value=""/>
					<c:set var="validYN" value="N"/>
					
					<c:choose>
						<c:when test="${baseDataVO.reserveAbleYn eq 'Y' and item.reserveAbleYn eq 'Y' and item.totCapa > item.reserveCnt and baseDataVO.todayYn eq 'Y'}">
						
							<c:if test="${item.personyn ne 'Y'}"><c:set var="btnType" value=" one"/></c:if>
							<c:if test="${item.groupyn ne 'Y'}"><c:set var="btnType" value=" one"/></c:if>
							<ul class="p_btn${btnType}">
								<c:if test="${item.personyn eq 'Y' }"><li><a href="javascript:fn_reserve_action('10','<c:out value="${item.timeStdSeq }"/>','<c:out value="${item.exbtTimeseq }"/>')" class="btn_ty_c6">개인 예약</a></li><c:set var="validYN" value="Y"/></c:if>
								<c:if test="${item.groupyn eq 'Y'}">
									<c:choose>
										<c:when test="${baseDataVO.grpMinCnt > 0 and baseDataVO.grpMinCnt <= (item.totCapa-item.reserveCnt) }">
											<li><a href="javascript:fn_reserve_action('20','<c:out value="${item.timeStdSeq }"/>','<c:out value="${item.exbtTimeseq }"/>')" class="btn_ty_c3">단체 예약</a></li><c:set var="validYN" value="Y"/>
										</c:when>
										<c:otherwise><li><a  class="btn_ty_c4">단체마감</a></li><c:set var="validYN" value="Y"/></c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${validYN eq 'N'}"><li><a  class="btn_ty_c4">예약마감</a></li></c:if>
							</ul>
						</c:when>
						<c:when test="${ baseDataVO.reserveAbleYn eq 'Y' and item.reserveAbleYn eq 'Y' and baseDataVO.todayYn eq 'Y'}">
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
	<!-- //prog_list -->

	<script>
	function fn_reserve_action(target , stdseq , timeseq ) {
		
		var msg = "";
		var limitCnt = <c:out value="${baseDataVO.rsvnLimitCnt}"/>;
		var myCnt = <c:out value="${limitCount}"/>;
		var limitMethod = '<c:out value="${baseDataVO.rsvnLimitMethod}"/>';
		var paramAdd = "";

		var url =  "<c:out value="${userRoot}"/>/exbtrsvn/reserveStep1?target="+target+"&exbtSeq=<c:out value="${baseDataVO.exbtSeq}"/>&stdseq="+stdseq+"&timeseq="+timeseq+"&ymd=<c:out value='${param.ymd}'/>";
		
		console.log("call fn_reserve_action()");
		console.log(url);
		
		<c:choose>
			<c:when test="${(!empty userInfo and !userInfo.isMember and baseDataVO.rsvnNonmebyn eq 'Y') or (userInfo.isMember and baseDataVO.rsvnStdmembyn eq 'Y') or (userInfo.yearYn eq 'Y' and baseDataVO.rsvnAnualmembyn eq 'Y') or (userInfo.specialYn eq 'Y' and baseDataVO.rsvnSpeclmembyn eq 'Y')}">
				// 예약가능
				if (limitCnt > 0 && myCnt >= limitCnt) {
					if (limitMethod == '20') {
						alert("예약 가능 횟수 가 초과되었습니다.\n(하루 최대 "+limitCnt+"회)");
					} else if (limitMethod == '21') {
						alert("예약 가능 횟수 가 초과되었습니다.\n(한달 최대 "+limitCnt+"회)");
					} else if (limitMethod == '22') {
						alert("예약 가능 횟수 가 초과되었습니다.\n(1년 최대 "+limitCnt+"회)");
					}
				} else {
					//window.location.href =url;
					window.location.href =url;
				}
			</c:when>
			<c:when test="${empty userInfo and baseDataVO.rsvnNonmebyn eq 'Y'}">
				///msg = "회원 로그인 또는 비회원 본인인증 후 예약하실 수 있습니다.";
				msg = "회원 로그인 후 예약하실 수 있습니다.";
			</c:when>
			<c:when test="${(empty userInfo or !userInfo.isMember) and baseDataVO.rsvnStdmembyn eq 'Y'}">
				msg = "회원 로그인 후 예약하실 수 있습니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${baseDataVO.rsvnAnualmembyn eq 'Y' and baseDataVO.rsvnSpeclmembyn eq 'Y'}">
				//연간회원 예약가능
				msg = "유료회원 또는 특별회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${baseDataVO.rsvnSpeclmembyn eq 'Y'}">
				//특별회원 예약가능
				msg = "특별회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${baseDataVO.rsvnAnualmembyn eq 'Y'}">
				//연간회원 예약가능
				msg = "유료회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
		</c:choose>
		<c:if test="${empty userInfo}" >
			if (msg != "") {
				if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
					window.location.href = "<c:out value="${userRoot}"/>/member/login?returnURL=" + encodeURIComponent(url) + paramAdd;
				}
			}
		</c:if>
		<c:if test="${!empty userInfo}" >
		if (msg != "") {
			alert(msg);
		}
		</c:if>
	}
	</script>
</c:otherwise>

</c:choose>