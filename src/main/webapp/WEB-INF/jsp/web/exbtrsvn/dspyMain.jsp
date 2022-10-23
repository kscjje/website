<%
 /**
  * @Class Name : dspyMain.jsp
  * @Description : 관람 메인 화면 JSP
  * @Modification Information
  * @
  * @  수정일               수정자              수정내용
  * @ -------       --------    ---------------------------
  * @ 2021.05.19    전영석             최초 생성
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<script>
	
	/* 일정 담을 변수 */
	var SCHEDULE;
	
	//캘린더 사전처리
	var date = new Date();
	var year = date.getFullYear();
	var mon = date.getMonth() + 1;
	var today = date.getDate();
	
	var calDate = "";
	var calYear ;
	var calMon ;
	
	$(document).ready(function() {
		
		calDate = $(".head").find("strong").html();
		calYear = calDate.split(".")[0];
		calMon = calDate.split(".")[1]; 
	
		var todate = year + (mon < 10 ? "0" : "")  +  mon  + (today < 10 ? "0" : "") + today;
		
		<c:choose>
			<c:when test="${!empty ymd  and fn:length(ymd) == 8}">
				fn_exbt_base('<c:out value="${ymd}"/>');
			</c:when>
			<c:otherwise>
				fn_exbt_base(todate);
			</c:otherwise>
		</c:choose>
	
	
		//프로그램 일정 로드
		fn_loadSchedules("${calMap.calTitle}".replace(".",""));
		
	});
	
	
	function moveCalendar(val , currMonth,currYear) {
		
		var url = '../calendarAjax';
		
		$.ajax({
			url : url,
			data : {
				val :val,
				currMonth : currMonth,
				currYear : currYear 
	 		},
			method : 'GET',
			dataType : 'html',
			error : function(request, error, jqXHR) {
				//alert("일정 불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				$('.calendar').html(data);
				$('#CAL_'+$('#ymd').val()).find("a").addClass("on");
	
			}
		});
		
	}
	
	function calendarScheduleCheck() {
		
		var todate = year + (mon < 10 ? "0" : "")  +  mon  + (today < 10 ? "0" : "") + today;
	
		for(var i = 0; i < SCHEDULE.length; i++) {
			
			var t = $('#CAL_'+SCHEDULE[i].ymd);
		    var text = SCHEDULE[i].scheType;
	
		    var cntCheck = SCHEDULE[i].chkData.split("|");
		    var timeCnt =  parseInt(cntCheck[0]);
		    var capaCnt = parseInt(cntCheck[1]);
		    var appCnt = parseInt(cntCheck[2]);
	
		  	if (text == "00") { // 휴일
				t.find("dd").addClass('hol').text('휴관');
				t.prop("disabled","disabled");
				t.css("cursor","default");
		  	} else if (timeCnt < 1) { //시간표 등록이 안되어 있음
	
			} else if (text == "10") { // 예약가능
				if ( capaCnt < 1) {
					t.find("dd").addClass('impos').text('예약불가'); // 정원없음
				} else if (appCnt > 0) {
					t.find("dd").addClass('pos').text('예약가능');
				} else {
					//정원마감
					t.find("dd").addClass('impos').text('예약불가');
				}
	
				t.find("a").attr("href" , "javascript:fn_exbt_base('"+SCHEDULE[i].ymd+"');");
			}
		}
	
		$("#calendarDiv td").each(function(i, e) {
			
			var d = $(this).find("dt").text();
			
			if (parseInt(d)<10) {d = '0' + d};
	
			if (calYear < year ||  (calYear == year && calMon < mon) ) { //같은해, 이전달들인 경우 마감 처리)
				//지난날짜
			} else if (calYear == year && calMon == mon && today > d) { //같은해, 같은달, 오늘포함 이전날짜인 경우 마감 처리)
				//지난날짜
			} else if (calYear == year && calMon == mon && today == d) { //오늘
					$(this).find("a").addClass("today");
					$(this).find("dd").removeClass('impos');
					$(this).find("dd").removeClass('pos').text("오늘");
					$(this).find("a").attr("href" , "javascript:fn_exbt_base('"+todate+"');");
			} else {
	
			}
	
		});
	}
	
	/*
	 * fn_reciptOver 캘린더 마감처리
	 */
	function fn_receiptOver(t , txt) {
		if (txt) {
			t.find("dd").text(txt);
		}
		t.css("cursor","default");
		t.find("a").addClass("off");
		t.prop("disabled","disabled");
	}
	
	
	/* 일정 불러오기 */
	function fn_loadSchedules(m) {
		var url = '../calendarCheckAjax';
		$.ajax({
			url : url,
			data : {
				partCd  : '<c:out value="${partInfo.uniqueId}"/>',
				month : m 
			},
			method : 'GET',
			dataType : 'json',
			error : function(request, error, jqXHR) {
				alert("일정 불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				
				console.log(data);
				
				SCHEDULE = data.schedule;
				calendarScheduleCheck();
			}
		});
	}
	
	function fn_exbt_base(ymd) {
		
		var url = '../exbtBaseListAjax';
		
		$('#ymd').val(ymd);
	
		$('#calendarDiv').find("td").find("a.on").removeClass("on").removeAttr("title");
		$('#CAL_'+ymd).find("a").addClass("on").attr("title" , "날짜 선택됨");
		$.ajax({
			url : url,
			data : {
				partCd  : '<c:out value="${partInfo.uniqueId}"/>',
				ymd : ymd,
				exbtSeq : $('#exbtSeq').val(),
				exbtType : $('#exbtType').val() 
			},
			method : 'GET',
			dataType : 'html',
			error : function(request, error, jqXHR) {
				alert("관람유형 불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				$('.cate_wrap2').html(data);
			}
		});
	}
	
	function fn_exbtdetail_show() {
		
		var url = '../exbtDetailAjax';
		
		$.ajax({
			url : url,
			data : $('#sendForm').serialize(),
			method : 'GET',
			cache: false,
			dataType : 'html',
			error : function(request, error, jqXHR) {
				alert("상세 내용 불러오기 실패");
				$('.event_cont').html('');
				$('.event_cont').hideLoading();
			},
			success : function(data, textStatus, jqXHR) {
				$('.event_cont').html(data);
				$('.event_cont').hideLoading();
	
			}
		});
	}
	
</script>

<div class="sub_cont" id="container">
	<h3 class="tit"><c:out value="${partInfo.title}"/></h3>
	<div class="exh_top mb70" style="background-image:url('<%=strContext%>/images/dspy/${partInfo.uniqueId}_bg.png'),url('<%=strContext%>/images/sub/bg_top_planet.png')">
		<div class="inner">
			<div class="exh_wrap">
				<!-- visual -->
				<div class="visual">
					<c:choose>
						<c:when test="${empty fileList}">
							<div class="swiper-container">
								<ul class="swiper-wrapper">
									<li class="swiper-slide item01" ></li>
								</ul>
							</div>
						</c:when>
						<c:otherwise>
							<!-- swiper-container -->
							<div class="swiper-container">
								<!-- mc_notice_list -->
								<ul class="swiper-wrapper">
									<c:forEach var="item" items="${fileList}" varStatus="status">
										<li class="swiper-slide">
											<p class="pc"><img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName}" originName="${item.orginFileName}"/>" alt="${fn:replace(item.orginFileName, '.jpg', '')}"></p>
											<p class="m"><img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName}" originName="${item.orginFileName}"/>" alt="${fn:replace(item.orginFileName, '.jpg', '')}"></p>
										</li>
									</c:forEach>
								</ul>
								<!-- //visual_list -->
							</div>
							<!-- //swiper-container-->
							<c:if test="${fn:length(fileList) > 1}">
							<div class="swiper-controls">
								<button class="play-stop">메인배너 자동플레이/일시정지</button>
								<div class="swiper-pagination"></div>
							</div>
							</c:if>
							<!-- //control -->
						</c:otherwise>
		
					</c:choose>
				</div>
				<!-- //visual -->
				<div class="bo_txt">
					<dl>
						<!-- <dt>반구형 돔 화면의 ‘국내 최초’ 「3D 천체 투영관」</dt> -->
						<dt><c:out value="${partInfo.title}"/></dt>
						<dd>
						<c:out value="${fn:replace(partInfo.partGuide , crlf , '<br/>') }" escapeXml="false" />
						</dd>
					</dl>
				</div>
				<!-- //bo_txt -->
			</div>
			<!-- //exh_top -->
		</div>
		<!-- //inner -->
	</div>
	
	<!-- //exh_top -->
	<div class="inner">
		<h4 class="th4 mb5">상품 예약하기</h4>
		<p class="desc2 mb40">희망하는 관람 일자, 프로그램 종류를 선택해주세요.</p>
		<div class="exh_box">
			<div class="calendar">
				<div class="head">
					<a href="javascript:moveCalendar('2' , '<c:out value="${calMap.currMonth }"/>','<c:out value="${calMap.currYear }"/>')" class="prev">이전 달</a>
					<strong><c:out value="${calMap.calTitle }"/></strong>
					<a href="javascript:moveCalendar('1' , '<c:out value="${calMap.currMonth }"/>','<c:out value="${calMap.currYear }"/>')" class="next">다음 달</a>
				</div>
				<!-- //head-->
				<div class="divi">
					<ul>
						<li><i class="pos"></i>예약 가능</li>
						<li><i class="impos"></i>예약 불가능</li>
						<!-- <li><i class="hol"></i>휴관</li> -->
	
						<!--
	
							공통 class:
							pos - 예약가능
							impos - 예약불가능
							hol - 휴관
						-->
					</ul>
				</div>
				<!-- //divi -->
				<table>
					<caption>월,화,수,목,금,토,일 순으로 나열된 달력 표입니다.</caption>
					<colgroup>
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
						<col style="width:14.28571428571429%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">일</th>
							<th scope="col">월</th>
							<th scope="col">화</th>
							<th scope="col">수</th>
							<th scope="col">목</th>
							<th scope="col">금</th>
							<th scope="col">토</th>
						</tr>
					</thead>
					<tbody id="calendarDiv">
						<c:out value="${calMap.calStr}" escapeXml="false" />
					</tbody>
				</table>
				<!-- //table-->
			</div>
			<!-- //calendar -->
			<div class="cate_wrap2">
				<!-- 관람유형 -->
			</div>
			<!-- //cate_m -->
		</div>
		<!-- //exh_box -->
		<div class="event_cont" >
			<!-- 관람 상세 시간표 -->
		</div>
		<!-- //event_cont -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<form id="sendForm" name="sendForm" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="exbtType" id="exbtType" value="" />
	<input type="hidden" name="exbtSeq" id="exbtSeq" value="${exbtSeq}" />
	<input type="hidden" name="ymd" id="ymd" value="" />
</form>
