<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : exbtBaseListAjax.jsp
  * @Description :관람  유형JSP
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

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<c:set var="reserveAbleCnt" value="0" />
<c:set var="firstExbtType" value="" />
<c:set var="firstExbtSeq" value="" />

<c:forEach var="item" items="${baseList}" varStatus="status">
	<c:if test="${item.reserveAbleYn eq 'Y' and item.holdCnt < 1 and item.timeCnt > 0}">
		<c:set var="reserveAbleCnt" value="${reserveAbleCnt+1 }" />
		<c:if test="${empty firstExbtSeq or (!empty param.exbtSeq  and item.exbtSeq eq param.exbtSeq)}">
			<c:set var="firstExbtType" value="${item.exbtType}" />
			<c:set var="firstExbtSeq" value="${item.exbtSeq}" />
		</c:if>
	</c:if>
</c:forEach>

<div class="cate_top">
	<span class="tt">선택하신 관람일자</span>
	<span class="dt">
		<fmt:parseDate var="extYmd" value="${param.ymd}" pattern="yyyyMMdd"/>
		<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
	</span>
</div>
<c:choose>
	<c:when test="${holidayCheck > 0 }">
		<div class="cate_m m_guide">
			<dl>
				<dt><img src="<%=strContext%>/images/sub/ico_info_b.png" alt="안내아이콘"></dt>
				<dd>휴관일 입니다.<br/>다른 날짜를 선택해 주세요.</dd>
			</dl>
		</div>
		<script>
			$('.event_cont').html("");
		</script>
	</c:when>
	<c:when test="${empty baseList or reserveAbleCnt < 1}">
<!--  관람 유형이 없는 경우 -->
		
		<!-- 
		<div class="cate_m m_guide">
			<dl>
				<dt><img src="<%=strContext%>/images/sub/ico_info_b.png" alt="안내아이콘"></dt>
				<dd>예약 가능한 상품이 없습니다.<br/>다른 날짜를 선택해 주세요.</dd>
			</dl>
		</div>
		-->
		
		<div class="empty">

			<div>
				<p><img src="/web/resources/site/images/warning.png"></p>
				<p>예약가능한 상품이 없습니다.</p>
				<p>다른 날짜를 선택해 주세요.</p>
			</div>

		</div>		
		
		<script>
			$('.event_cont').html("");
		</script>
		
	</c:when>
	<c:otherwise>

<!--  관람 유형이 등록되어 있을경우 -->
<div class="cate_m">

	<div>
	
		<span class="on">관람유형을 선택하세요</span>
		
		<ul id="CATE_TAB_01" class="cate_tab active">
			<c:set var="prevExbtType" value="" />
			<c:forEach var="item" items="${baseList}" varStatus="status">
				<c:if test="${item.exbtType ne  prevExbtType and item.reserveAbleYn eq 'Y' and item.holdCnt < 1 and item.timeCnt > 0}">
					<li id="TYPE_<c:out value="${item.exbtType}"/>"><a href="javascript:;" onclick="fn_type_select('<c:out value="${item.exbtType}"/>','<c:out value="${item.exbtSeq}"/>')" class="btn_ty_m5_c2"><c:out value="${item.exbtTypeNm }"/></a></li>
					<c:set var="prevExbtType" value="${item.exbtType}" />
				</c:if>
			</c:forEach>
		</ul>
		
	</div>
	
	<div style="display:none" id="secondTab">
	<span class="on">상세 선택</span>
	<ul id="CATE_TAB_02" class="cate_tab w100 active">
	<c:forEach var="item" items="${baseList}" varStatus="status">
		<c:set var="targetNm" value="" />
		<c:choose>
			<c:when test="${item.rsvnNonmebyn eq 'Y' }"><c:set var="targetNm" value="" /></c:when>
			<c:when test="${item.rsvnStdmembyn eq 'Y' }"><c:set var="targetNm" value="[회원대상] " /></c:when>
			<c:when test="${item.rsvnSpeclmembyn eq 'Y' and item.rsvnAnualmembyn eq 'Y' }"><c:set var="targetNm" value="[유료,특별회원 대상] " /></c:when>
			<c:when test="${item.rsvnSpeclmembyn eq 'Y'}"><c:set var="targetNm" value="[특별회원 대상] " /></c:when>
			<c:when test="${item.rsvnAnualmembyn eq 'Y' }"><c:set var="targetNm" value="[유료회원 대상] " /></c:when>
		</c:choose>
		<c:if test="${item.reserveAbleYn eq 'Y' and item.holdCnt < 1 and item.timeCnt > 0}">
			<li class="typecd_<c:out value="${item.exbtType}"/>" style="display:none"><a href="javascript:;" id="detailBtn_${item.exbtSeq}" onclick="fn_exbtbase_select('<c:out value="${item.exbtSeq}"/>' , this)" class="btn_ty_m5_c2">${targetNm}<c:out value="${item.exbtName}"/></a></li>
		</c:if>
	</c:forEach>
	</ul>
	</div>
</div>

<script>

	function fn_type_select(val, exbtSeq) {

		////$('.event_cont').showLoading();

		$('#CATE_TAB_01').find('li').removeClass("on");
		$('#CATE_TAB_01').find('li > a').addClass("btn_ty_m5_c2").removeClass("btn_ty_m5_c1");

		var obj = $('#TYPE_' + val);
		obj.addClass("on");
		obj.find("a").removeClass("btn_ty_m5_c2").addClass("btn_ty_m5_c1");

		$('#CATE_TAB_02').find('li').hide();
		$('#CATE_TAB_02').find('.typecd_'+val).show();

		if (val == '1001') {
			$('#secondTab').hide();
		} else {
			////$('#secondTab').show();
			$('#secondTab').hide();
		}
		var subObj = $('#CATE_TAB_02').find('.typecd_'+val).find('a');

		$('#exbtType').val(val);
		fn_exbtbase_select(exbtSeq , subObj , false);

		$(document).attr("title", "회차 선택 - 관람관예약 | 노원수학문화관");
		
	}

	function fn_exbtbase_select(exbtSeq , obj , focusFlag) {
		
		$('#CATE_TAB_02').find('li').removeClass("on");
		$('#CATE_TAB_02').find('li > a').addClass("btn_ty_m5_c2").removeClass("btn_ty_m5_c1");

		if (obj) {
			$('#detailBtn_' + exbtSeq).parent().addClass("on"); 
			$('#detailBtn_' + exbtSeq).removeClass("btn_ty_m5_c2").addClass("btn_ty_m5_c1");
		}

		$('#exbtSeq').val(exbtSeq);
		fn_exbtdetail_show(focusFlag); // 하단 상세 페이지로 포커스 여부 
	}

	<c:if test="${!empty firstExbtType}">fn_type_select('<c:out value="${firstExbtType}"/>','<c:out value="${firstExbtSeq}"/>');</c:if> 


</script>

	</c:otherwise>
</c:choose>