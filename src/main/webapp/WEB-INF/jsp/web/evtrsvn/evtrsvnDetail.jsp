<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%/**
 * @Class Name : evtrsvnDetail.jsp
 * @Description : 강연/행사/영화 상세
 * @Modification Information
 * @
 * @  수정일              수정자           수정내용
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


<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

			<form:form commandName="EvtStdmngVO" name="EvtStdmngVO" id="EvtStdmngVO" method="get" action='/web/evtrsvn/evtrsvnStep1'>
				<input type="hidden" name="evtTimeseq"    id="evtTimeseq" />
				<input type="hidden" name="evtTimestdseq" id="evtTimestdseq" />
				<input type="hidden" name="evtNo"         id="evtNo"    value="${vo.evtNo}" />
				<input type="hidden" name="evtTime"       id="evtTime" />
				<input type="hidden" name="evtPartcd"     value="${vo.evtPartcd}" />
				<input type="hidden" name="evtPersnGbn"   id="evtPersnGbn" />
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
			</form:form>
	
			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">강연/행사/영화 예약</h3>
					<div class="event_box ty mb45">
					
						<div class="left">
							<div class="e_img">
							
								<c:set var="onlyOrginExtNm" value=".${vo.evtIntrimgFinnbOnlyOrginExtrn}" />
								<img src="<tags:UploadFileUrl filePath='${vo.evtIntrimgFinnbFilePath}' fileName='${vo.evtIntrimgFinnbFileName}' originName='${vo.evtIntrimgFinnbOrginFileName}' />" alt="${fn:replace(vo.evtIntrimgFinnbOrginFileName, onlyOrginExtNm, '')}" />							

							</div>
							<!-- //e_img -->
						</div>
						
						<div class="e_cont">
							<div class="e_tit">
								<c:out value="${vo.evtName}" />
							</div>
							<ul class="e_dt">
								<li>
									<dl>
										<dt>장소</dt>
										<dd>
											<c:out value="${vo.evtPlacenm}" />
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>기간</dt>
										<dd>

											<fmt:parseDate var="dateStr1" value="${vo.evtUseSdate}" pattern="yyyyMMdd" />
											<fmt:parseDate var="dateStr2" value="${vo.evtUseEdate}" pattern="yyyyMMdd" />	
											
											<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />											
											
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>대상</dt>
										<dd>
											<c:out value="${vo.evtTargetage}"/>												
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>시간</dt>
										<dd>
											<c:out value="${vo.evtTimeinfo}"/>												
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>분류</dt>
										<dd>
											<c:out value="${vo.evtGbnNm}"/>
										</dd>
									</dl>
								</li>
								<!-- 
								<li>
									<dl>
										<dt>관람연령가</dt>
										<dd>
											<c:out value="${vo.evtTargetage}"/>
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>시간</dt>
										<dd>
											<c:out value="${vo.evtTimeinfo}"/>
										</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>러닝타임</dt>
										<dd>
											<c:out value="${vo.evtRuningTime}"/>
										</dd>
									</dl>
								</li>
								-->
							</ul>
							<div class="e_price">
								<span class="p_tit">관람비용</span>
								<!-- <span class="p_price"><em>0</em> 원</span> -->
								<span class="p_price"><em>무료</em></span>
							</div>
						</div>
						<!-- //e_cont -->
					</div>
					<!-- //event_box -->
					<div class="event_cont">
						<div class="detail left">
							<ul class="tab_ty wid">
								<li class="on"><a href="#none">공연설명</a></li><!-- 현재위치 class: on -->
							</ul>
							<!-- //tab_ty -->
							<div class="d_cont">
								<p>
									<c:out value="${fn:replace(vo.evtIntrcn, crlf , '<br/>') }" escapeXml="false" />
								</p>
								<p class="d_img">
									<img src="<tags:UploadFileUrl filePath='${vo.evtIntrimgFinnbFilePath}' fileName='${vo.evtIntrimgFinnbFileName}' originName='${vo.evtIntrimgFinnbOrginFileName}' />" alt="${fn:replace(vo.evtIntrimgFinnbOrginFileName, onlyOrginExtNm, '')}" />								
								</p>
							</div>
							<div class="d_more_btn">
								<span>강연/행사/영화 설명 더보기</span>
							</div>
						</div>
						<!-- //detail -->
						<div class="calendar right">
							<div class="head">
								
								<a class="prev" href="javascript:moveCalendar('2' , '<c:out value="${calMap.currMonth}"/>','<c:out value="${calMap.currYear}"/>')">이전 달</a>
								<strong id="calTitle"><c:out value="${calMap.calTitle }" /></strong>
								<a class="next" href="javascript:moveCalendar('1' , '<c:out value="${calMap.currMonth}"/>','<c:out value="${calMap.currYear}"/>')">다음 달</a>
								
							</div>
							<!-- //head-->
							<div class="divi">
								<ul>
									<li><i class="pos"></i>예약 가능일</li>
									<li><i class="impos"></i>예약 불가일</li>
									<!-- <li><i class="hol"></i>휴관일</li> -->
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
						
					</div>
					<!-- //event_cont -->

					<div class="event_cont">
					
						<div id="timeListDIV">
			
						</div>					
					
					</div>
					
					<!-- //event_cont -->
					
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
<script>

	//캘린더 사전처리
	var date = new Date();
	var year = date.getFullYear();
	var mon = date.getMonth() + 1;
	var today = date.getDate();
	var currDate = String(year)+String(mon).lpad(2,'0')+String(today);

	var calDate = "";
	var calYear;
	var calMon;
	var  selectedDate = "";

	$(document).ready(function() {
		
		calDate = $(".head").find("strong").html();
		calYear = calDate.split(".")[0];
		calMon = calDate.split(".")[1];

		$('.d_more_btn').click(function() {
			
			$(this).toggleClass('on');
			if ($(this).hasClass('on')) {
				$(this).find('span').text('강연/행사/영화설명 닫기');
			} else {
				$(this).find('span').text('강연/행사/영화설명 더보기');
			}
			
			$('.d_cont').toggleClass('open');
		})
		
		//일정 로드
		var todate = year + (mon < 10?"0":"")  +  mon  + (today < 10?"0":"") + today;

		if (todate < '${vo.evtUseSdate }') {
			todate =  '${vo.evtUseSdate }';
		}

		fn_loadSchedules("${calMap.calTitle}".replace("." , ""));
		fn_show_prgm(todate);
		
	});

	function moveCalendar(val, currMonth, currYear) {
		
		var url = './calendarAjax';
		
		$.ajax({
			url : url,
			data : {
				val :val,
				currMonth : currMonth,
				currYear : currYear
	 		},
			method : 'GET',
			cache : false,
			dataType : 'html',
			success : function(data, textStatus, jqXHR) {
				$('.calendar').html(data);
			},
			error : function(request, error, jqXHR) {
				//alert("일정 불러오기 실패");
			}
		});
		
	}//end of movCalendar

	/* 달력표시 불러오기 */
	function fn_loadSchedules(m) {
		
		console.log("call fn_loadSchedules()");
				
		$.ajax({
			url : './evtrsvnSelectAjax',
			data : {
				evtNo : $('#evtNo').val(),
				month : m
			},
			method : 'GET',
			dataType : 'json',
			cache : false,
			success : function(data, textStatus, jqXHR) {
				
				console.log(data);
				
				fn_set_calendar(data);
			},
			error : function(request, error, jqXHR) {
				alert("일정 불러오기 실패");
			}
		});

	}

	//일정 불러오기
	function fn_show_prgm(val) {

		console.log("call fn_show_prgm()");
		
		var url = './evtShowPrgmAjax'; 
		var ymd = val;
		
		var dow = new Date(ymd.toDate('-')).getDay();
		dow = dow + 1;
		
		var no = $('#evtNo').val();

		selectedDate = val;

		$('#calendarDiv').find("td").find("a.on").removeClass("on").removeAttr("title");
		$('#CAL_'+selectedDate+'').find("a").addClass("on").attr("title" , "날짜 선택됨");

		$.ajax({
			url : url,
			data : {
				evtNo : no,
				evtTime : ymd,
				evtUsedays : dow
	 		},
			method : 'GET',
			dataType : 'html',
			cache : false,
			success : function(data, textStatus, jqXHR) {
				
				//console.log(data);
				
				$('#timeListDIV').html(data);
			},
			error : function(request, error, jqXHR) {
				//alert("일정 불러오기 실패");
			}
		});
		
	}//end of fn_show

	//캘린더 세팅
	function fn_set_calendar(data) {
		
		var scList = data.scheList;

		$('#CAL_'+selectedDate+'').find("a").addClass("on").attr("title" , "날짜 선택됨");

		// 휴관일 세팅
		for(var i = 0; i < scList.length; i++) {
			
			var t = $('#CAL_'+scList[i].calDate);
		    var text = scList[i].dateType;
		    var cntCheck = scList[i].cntChk.split("|");
		    var timeCnt =  parseInt(cntCheck[0]);
		    var capaCnt = parseInt(cntCheck[1]);
		    var appCnt = parseInt(cntCheck[2]);


			if (text == "00" ) { // 휴일
				t.find("dd").addClass('hol').text('휴관');
				t.prop("disabled","disabled");
			} else if (timeCnt < 1) { //시간표 등록이 안되어 있음

			} else {
				if (text == "30" || capaCnt < 1) {
					//예약가능 기간이 아님
					t.find("dd").addClass('impos').text('예약불가');
				} else if (appCnt > 0) {
					t.find("dd").addClass('pos').text('예약가능');
				} else {
					//정원마감
					t.find("dd").addClass('impos').text('예약불가');
				}

				t.find("a").attr("href" , "javascript:fn_show_prgm('"+scList[i].calDate+"');");
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
					$(this).find("dd").removeClass('pos').text("오늘");
			} else {

			}

		});
	}

	function fn_receiptOver(t , txt) {
		if (txt) {
			t.find("dd").text(txt)
		}
		t.find("a").addClass("off");
		t.prop("disabled","disabled");
		t.css("cursor","default");
		t.find("dd").removeClass('pos');
	}

	function fn_rsv_evt(type, dt ,stdSeq,seq) {
		
		/* 
		if (type == "10") {
			if (("${vo.evtSexdstn}" == "1001" || "${vo.evtSexdstn}" == "2001")) {
				if ("${vo.evtSexdstn}" == "1001" && "${userInfo.gender}" == "2") {
					alert("신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					return;
				} else if ("${vo.evtSexdstn}" == "2001" && "${userInfo.gender}" == "1" ) {
					alert("신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					return;
				}
			}
		}
		*/

		var msg = "";
		var rsv = false;
		var frm = $('#EvtStdmngVO');
		var paramAdd = "";

		$('#evtTime').val(dt);
		$('#evtPersnGbn').val(type);
		$('#evtTimeseq').val(seq);
		$('#evtTimestdseq').val(stdSeq);

		var url = frm.attr('action') +'?'+ frm.serialize();
		
		
		//console.log(url)
		<c:choose>
			<c:when test="${(!empty userInfo and !userInfo.isMember and vo.evtNonmebyn eq 'Y') or (userInfo.isMember and vo.evtStdmembyn eq 'Y') or (userInfo.yearYn eq 'Y' and vo.evtAnualmembyn eq 'Y') or (userInfo.specialYn eq 'Y' and vo.evtSpeclmembyn eq 'Y')}">
				// 예약가능
				window.location.href = url;
			</c:when>
			<c:when test="${empty userInfo and vo.evtNonmebyn eq 'Y'}">
				msg = "회원 로그인 후 예약하실 수 있습니다.";
			</c:when>
			<c:when test="${(empty userInfo or !userInfo.isMember) and vo.evtStdmembyn eq 'Y'}">
				msg = "회원 로그인 후 예약하실 수 있습니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${vo.evtAnualmembyn eq 'Y' and vo.evtSpeclmembyn eq 'Y'}">
				//연간회원 예약가능
				msg = "유료회원 또는 특별회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${vo.evtSpeclmembyn eq 'Y'}">
				//특별회원 예약가능
				msg = "특별회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
			<c:when test="${vo.evtAnualmembyn eq 'Y'}">
				//연간회원 예약가능
				msg = "유료회원만 예약가능합니다.";
				paramAdd = "&member_yn=Y";
			</c:when>
		</c:choose>
		
		<c:if test="${empty userInfo}" >
			if (msg != "") {
					if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
						url += paramAdd;
						window.location.href = "/web/member/login?returnURL=" + encodeURIComponent(url);
					}
			}
		</c:if>
	
		<c:if test="${!empty userInfo}">
			if (msg != "") {
				alert(msg);
			}
		</c:if>
	}
</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>