<%
 /**
  * @Class Name : vertical.jsp
  * @Description : 관람 현황판(세로)
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
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SPOWISE CMS 현황판</title>
  <!-- Tell the browser to be responsive to screen width -->
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="<%=strContext%>/images/layout/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="<%=strContext%>/images/layout/apple-touch-icon.png">
  <link rel="stylesheet" href="<%=strContext%>/stat/css/style.css">
<script src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>

	<!-- Datepicker by JYS -->
	<script src="/webadm/resources/plugins/jquery-ui/jquery-ui.js"></script>
	<link rel="stylesheet" href="/webadm/resources/plugins/jquery-ui/jquery-ui.css">

</head>
<body class="ver">
	<section class="status_ver_ui">
		<div class="wrapper">
			<div class="st_head">
				<h1 class="logo"><img src="<%=strContext%>/stat/images/logo_ver.png" alt="노원수학문화관로고"></h1>
				<span class="title">유료 관람 매표현황</span>
				<span class="m_date"></span>
				<span class="s_date"></span><input type="image" id="dateToday" src="<%=strContext%>/images/main/calendar00.png" style="width:3px; height:3px;">   
			</div>
			<!-- //st_head -->
			<div class="st_list">
				<div class="st top mb30">
					<div class="table t1_2" id="PART_0011">
						<div class="tit">꿈아띠체험관</div>
						<div class="cont">
							<div class="th">
								<span class="time">시간</span>
								<span class="seat">잔여좌석</span>
							</div>
							<div class="td">
							</div>
						</div>
					</div>
					<!-- //꿈아띠체험관 -->
				</div>
				<div class="st">
					<div class="table t2" id="PART_0009">
						<div class="tit">창의나래관</div>
						<div class="cont">
							<div class="th">
								<span class="time">시간</span>
								<span class="seat">잔여좌석</span>
							</div>
							<div class="td">
							</div>
						</div>
					</div>
					<!-- //창의나래관 -->
				</div>
				<div class="st">
					<div class="table t3" id="PART_0013">
						<!-- 
						<div class="tit">자기부상열차</div>
						<div class="cont">
							<div class="th">
								<span class="time">시간</span>
								<span class="seat">잔여좌석</span>
							</div>
							<div class="td">
							</div>
						</div>
						-->
					</div>
					<!-- //자기부상열차 -->
				</div>
				<div class="st">
					<div class="table t4" id="PART_0012">
						<div class="tit">천체관</div>
						<div class="cont">
							<div class="th">
								<span class="time">시간</span>
								<span class="seat">잔여좌석</span>
							</div>
							<div class="td">
							</div>
						</div>
					</div>
					<!-- //천체관 -->
				</div>
			</div>
			<!-- //st_list -->
			<!--
			<div class="tel">

				<i><img src="<%=strContext%>/images/layout/ico_tel.png" alt="전화아이콘"></i> <span>노원수학문화관 매표소 042-601-7732</span>

			</div>
			 -->
		</div>
		<!-- //wrapper -->
	</section>
</body>
</html>
	<script>
	
	/* 
	$(document).ready(function() {
		fn_load_schedule();

		setInterval(function() {
			fn_load_schedule();
		},60000)//setinterval

	});
	*/
	
	var gvarToday;
	var gvarNextday;
	
	$(document).ready(function() {
		
		fn_load_schedule();

		setInterval(function() {

			console.log("--------------------------------S");
			console.log(gvarToday);
			console.log(gvarNextday);
			console.log("--------------------------------E");
			
			if ((gvarNextday === undefined) || (gvarNextday == '') || (gvarNextday == null)) {
				fn_load_schedule();
			} else {
				if (gvarToday == gvarNextday) {
					fn_load_schedule();	
				}				
			}
			
		},60000)//setinterval

	});	

	function fn_load_schedule() {

		$.ajax({
			url :  './dataLoadAjax',
			method : 'GET',
			dataType : 'json',
			error : function(request, error, jqXHR) {
				alert("일정 불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {

				console.log("--------------fn_load_schedule: " + data.result.hhmm);

				gvarToday = data.result.mmdd;
				console.log(gvarToday);				
				
				$('.st_list').find('.table .cont .td').remove();

				var weekNm = "";
				if (data.result.week == "1") {
					weekNm = "일요일";
				} else if (data.result.week == "2") {
						weekNm = "월요일";
				} else if (data.result.week == "3") {
					weekNm = "화요일";
				} else if (data.result.week == "4") {
					weekNm = "수요일";
				} else if (data.result.week == "5") {
					weekNm = "목요일";
				} else if (data.result.week == "6") {
					weekNm = "금요일";
				} else if (data.result.week == "7") {
					weekNm = "토요일";
				}
				var mDate = data.result.mmdd.substring(0,2) + "월" +  data.result.mmdd.substring(2,4) + "일";
				var sDate = weekNm + '</br>' +  data.result.hhmm;
				$('.m_date').html(mDate);
				$('.s_date').html(sDate);
				$.each(data.result.timeList , function(index,item) {
					var part = item.partCd;
					var listObj = $('#PART_' + part).find(".cont");

					if (item.excepCnt > 0) {

					} else {
						var html = "<div class=\"td\">";
						var time = item.sTime.substring(0,2) + ":" + item.sTime.substring(2,4);

						html += "<span class=\"time\">"+ item.timeName + " " + time+"</span>";

						if (item.reserveAbleYn == "N") {
							html += "<span class=\"seat end\">종료</span>";
						} else if (item.totCapa <= item.reserveCnt ) {
							html += "<span class=\"seat out\">매진</span>";
						} else {
							html += "<span class=\"seat\">" + (item.totCapa-item.reserveCnt)+ "석</span>";
						}

						html += "</div>";
						listObj.append(html);
					}
				});
			}
		});
	}

	function fn_load_schedule_selDate(yyyymmdd) {

		console.log(yyyymmdd);
		
		$.ajax({
			url :  './dataLoadSelDateAjax',
			method : 'GET',
			data: {"yyyymmdd": yyyymmdd},
			dataType : 'json',
			error : function(request, error, jqXHR) {
				alert("일정 불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {

				console.log("--------------fn_load_schedule_selDate: " + data.result.hhmm);

				gvarNextday = data.result.mmdd;
				console.log(gvarNextday);						
				
				$('.st_list').find('.table .cont .td').remove();

				var weekNm = "";
				if (data.result.week == "1") {
					weekNm = "일요일";
				} else if (data.result.week == "2") {
						weekNm = "월요일";
				} else if (data.result.week == "3") {
					weekNm = "화요일";
				} else if (data.result.week == "4") {
					weekNm = "수요일";
				} else if (data.result.week == "5") {
					weekNm = "목요일";
				} else if (data.result.week == "6") {
					weekNm = "금요일";
				} else if (data.result.week == "7") {
					weekNm = "토요일";
				}
				var mDate = data.result.mmdd.substring(0,2) + "월" +  data.result.mmdd.substring(2,4) + "일";
				var sDate = weekNm + '</br>' +  data.result.hhmm;
				$('.m_date').html(mDate);
				$('.s_date').html(sDate);
				$.each(data.result.timeList , function(index,item) {
					var part = item.partCd;
					var listObj = $('#PART_' + part).find(".cont");

					if (item.excepCnt > 0) {

					} else {
						var html = "<div class=\"td\">";
						var time = item.sTime.substring(0,2) + ":" + item.sTime.substring(2,4);

						html += "<span class=\"time\">"+ item.timeName + " " + time+"</span>";

						if (item.reserveAbleYn == "N") {
							html += "<span class=\"seat end\">종료</span>";
						} else if (item.totCapa <= item.reserveCnt ) {
							html += "<span class=\"seat out\">매진</span>";
						} else {
							html += "<span class=\"seat\">" + (item.totCapa-item.reserveCnt)+ "석</span>";
						}

						html += "</div>";
						listObj.append(html);
					}
				});
			}
		});
	}
	
    $( "#dateToday").datepicker({
    	dateFormat: 'yymmdd',
    	onSelect: function (dateText, inst) {

    		var varMMDD = dateText.substring(4,8);
    		
    		console.log("varMMDD = ");
    		console.log(varMMDD);    		
    		
    		if (gvarToday == varMMDD) {
    			gvarNextday = varMMDD;
    			fn_load_schedule();
    		} else {
    			fn_load_schedule_selDate(dateText);	
    		}
    		
    	}
    }); 	
	
	</script>