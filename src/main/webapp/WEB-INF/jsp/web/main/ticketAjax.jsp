<%
 /**
  * @Class Name : ticketAjax.jsp
  * @Description : 오늘의 티켓 화면 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.07    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.07
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
<script type="text/javascript">
$(document).ready(function() {
	$('body').addClass('on');
	$('.btn_ticket').hide();
	$('.ticket').addClass('open');
	$('.dim').fadeIn().addClass('on');


	$('.ticket .close').click(function() {
		$('body').removeClass('on');
		$('.btn_ticket').show();
		$('.ticket').removeClass('open');
		$('.dim').fadeOut().removeClass('on');
	});
	$('.barcode_wrap .close').click(function() {
		$('#ticket_detail').removeClass('open');
	});

	var ticketSlide = new Swiper('.ticket .swiper-container', {
		slidesPerView: 1,
		pagination: {
			el: '.ticket .swiper-pagination',
			clickable: true
		},
		speed: 500,
		loop: false,

	});


});
function fn_ticket_detail(idkey) {
	var obj = $('#T' + idkey);
	$('#ticket_detail').find(".bar_top .tit").text(obj.find(".bar_top .tit").text());
	$('#ticket_detail').find(".bar_top .time_desc").text(obj.find(".bar_top .time_desc").text());
	$('#ticket_detail').find(".bar_btm .t_barcode_l").find("img").attr("src" , obj.find(".bar_top img").attr("src"));
	$('#ticket_detail').find(".bar_btm .num_wrap").find("ul").html(obj.find(".bar_btm").find("ul").html())
	$('#ticket_detail').find(".bar_btm .num_wrap .num_tit").find("dl dd").html(obj.find(".bar_btm .num_tit").find("dl dd").html());
	$('#ticket_detail').find(".g_wrap .g_txt").find("span").html(obj.find(".g_wrap .g_txt").find("span").html());
	$('.barcode_wrap').addClass('open');

}
function fn_ticket_msg(idx) {
	var html = $('#noticeTicket_'+idx).html();
	$('#noticeTicket').find(".modal-txt").html(html);

	$('#noticeTicket').modal();
}
</script>
<!-- ticket -->
<c:if test="${fn:length(ticketList) > 0 }">
<div class="ticket">
	<button type="button" class="close">닫기</button>
	<!-- swiper-container -->
	<div class="swiper-container">
				<!-- swiper-wrapper -->
				<ul class="swiper-wrapper">
<c:forEach items="${ticketList }" var="item" varStatus="s">
					<li class="swiper-slide">
						<!-- ticket_cont -->
						<div class="ticket_cont">
							<!-- ticket_box -->
							<div class="ticket_box<c:if test="${item.chkCnt >0 }"> done</c:if>" id="T${item.gubun}_${item.rsvnIdx }">
								<div class="bar_top">
									<p class="tit"><c:if test="${item.gubun eq 'EXBT' }">${item.partNm } </c:if>${item.title }</p>
									<span class="time_tit">시간</span>
									<span class="time_desc">${fn:substring(item.sTime,0,2) }시 ${fn:substring(item.sTime,2,4) }분 ~ ${fn:substring(item.eTime,0,2) }시 ${fn:substring(item.eTime,2,4) }분</span>
									<c:choose>
										<c:when test="${empty item.ticketNo}">
											<div class="nobar"><i><img src="<%=strContext%>/images/mobile/ico_not.png" alt="알림아이콘"></i>티켓이 발행되지 않는<br><c:choose><c:when test="${item.gubun eq 'EXBT' }">관람입니다.</c:when><c:otherwise>강연/행사/영화입니다.</c:otherwise></c:choose></div>
										</c:when>
										<c:otherwise>
											<img src="/web/common/file/barcode/${item.ticketNo }" alt="바코드이미지1"  class="t_barcode" onclick="fn_ticket_detail('${item.gubun}_${item.rsvnIdx }')">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="t_notice">
<c:if test="${!empty item.ticketDesc }">
									<a href="javascript:fn_ticket_msg(${s.index })"><i><img src="<%=strContext%>/images/mobile/ico_not2.png" alt="안내아이콘"></i>유의사항 안내 보기</a>
									<div style="display:none" id="noticeTicket_${s.index }">
										${fn:replace(item.ticketDesc , cslf , '<br/>')}
									</div>
</c:if>
								</div>
								<!-- //bar_top -->
								<div class="g_wrap">
									<div class="g_txt">
										<span><c:out value="${item.noticeTxt }"/></span>
									</div>
								</div>
								<!-- //g_wrap -->
								<div class="bar_btm">
									<div class="num_tit">
										<dl>
											<dt>관람입장인원</dt>
											<dd><span><c:out value="${item.visitNum }"/></span> 명</dd>
										</dl>
									</div>
<c:choose>
	<c:when test="${item.gubun eq 'EXBT' }">
									<ul class="num_cont">
		<c:forEach items="${item.itemList}" var="subItem" varStatus="s">
			<c:if test="${subItem.itemCd ne prevItemCd}">
			<c:set var="prevItemCd" value="${subItem.itemCd}" />
										<li>
											<dl>
												<dt><c:out value="${subItem.itemNm}"/></dt>
												<dd><c:out value="${subItem.itemCnt}"/> 명</dd>
											</dl>
										</li>
			</c:if>
		</c:forEach>
									</ul>
									<a href="/web/mypage/myRsvn/myRsvnCompList?gubun=EXBT" class="btn_mypage">마이페이지 자세히 보기</a>
	</c:when>
	<c:otherwise>
									<ul class="num_cont">
		<c:forEach items="${item.itemList}" var="subItem" varStatus="s">
			<c:if test="${subItem.evtRsvnItemcd ne prevItemCd}">
			<c:set var="prevItemCd" value="${subItem.evtRsvnItemcd}" />
										<li>
											<dl>
												<dt><c:out value="${subItem.evtItemNm}"/></dt>
												<dd><c:out value="${subItem.evtRsvnItemcnt}"/> 명</dd>
											</dl>
										</li>
			</c:if>
		</c:forEach>
									</ul>
									<a href="/web/mypage/myRsvn/myRsvnCompList?gubun=EVT" class="btn_mypage">마이페이지 자세히 보기</a>
	</c:otherwise>
</c:choose>
								</div>
								<!-- //bar_btm -->
							</div>
							<!-- //ticket_box -->
						</div>
						<!-- //ticket_cont -->
					</li>
</c:forEach>
				</ul>
				<!-- //swiper-wrapper -->
				<div class="swiper-pgw">
					<div class="swiper-pagination"></div>
				</div>
</div>

</div>
<!-- //ticket -->
<!-- barcode_wrap -->
<div class="barcode_wrap" id="ticket_detail">
		<button type="button" class="close">닫기</button>
		<div class="bar_top">
			<p class="tit">천체관 명품음악회 특별전천체관 명품음악회 특별전</p>
			<span class="time_tit">시간</span>
			<span class="time_desc">10시 30분 ~11시 30분</span>
		</div>
		<!-- //bar_top -->
		<div class="bar_btm">
			<span class="t_barcode_l"><img src="<%=strContext%>/images/mobile/barcode01.png" alt="바코드이미지"></span>
			<!-- //t_barcode_l -->

			<div class="num_wrap">
				<ul class="num_cont">
					<li>
						<dl>
							<dt>어른/노인</dt>
							<dd>000 명</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>어린이/청소년</dt>
							<dd>000 명</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>유아</dt>
							<dd>000 명</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>영아</dt>
							<dd>000 명</dd>
						</dl>
					</li>
				</ul>
				<!-- //num_cont -->
				<div class="num_tit">
					<dl>
						<dt>관람입장인원</dt>
						<dd><span>000</span> 명</dd>
					</dl>
				</div>
				<!-- //num_tit -->
			</div>
		</div>
		<!-- //bar_btm -->
		<div class="g_wrap">
			<div class="g_txt">
				<span>관람시간 5분전까지 입장해 주세요. 관람시간 5분전까지 입장해 주세요. 관람시간 5분전까지 입장해 주세요.</span>
			</div>
		</div>
		<!-- //g_wrap -->
</div>
<!-- //barcode_wrap-->
<div class="dim"></div>
</c:if>