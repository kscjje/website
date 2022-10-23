<%
	 /**
	  * @Class Name : dashboardMain.jsp
	  * @Description : DashBOARD 페이지
	  * @Modification Information
	  * @
	  * @  수정일         수정자         수정내용
	  * @ -------       --------    ---------------------------
	  * @ 2020.10.18    전영석         최초 생성
	  *
	  *  @author 전영석
	  *  @since 2020.10.18
	  *  @version 1.0
	  *
	  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<!doctype html>
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=1920">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<title>SPOWISE CMS</title>
	
	<link rel="shortcut icon" href="<%=strContext%>/images/layout/favicon.ico" type="image/x-icon">		
	<link rel="stylesheet"    href="<%=strContext%>/css/jquery-ui.css">
	<link rel="stylesheet"    href="<%=strContext%>/css/style.css">
	
	<script type="text/javascript" src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>
	<script type="text/javascript" src="<%=strContext%>/js/jquery-ui.min.js"></script>
	
	<!-- 
	<script type="text/javascript" src="<%=strContext%>/js/front_ui.js"></script>
	-->
	
    <!-- google charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    
    <!-- <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['corechart']}]}"> --> 
	
	<script src="<%=strContext%>/js/common_nsm_dashboard.js"></script>
	
</head>

<body>
	<section class="dashboard_ui">
	
		<div class="header">
			<div class="wrapper">
				<h1><img src="<%=strContext%>/images/dashboard/logo.png" alt="노원수학문화관통합관리자로고" onclick="fn_Refresh(); return false;"></h1>
				<div class="side">
					<dl class="date_txt">
						<!-- 
						<dt>오후 <em>15:04</em></dt>
						<dd>8월 18일 현재, 목요일</dd>
						-->
						<dt>${resultToday[0].daynight} <em>${resultToday[0].hhmi}</em></dt>
						<dd>${resultToday[0].mm}월 ${resultToday[0].dd}일 현재, ${resultToday[0].weekName}</dd>						
					</dl>
					<div class="date_wrap">
						<div class="btn_date"><i><img src="<%=strContext%>/images/dashboard/ico_calendar.png" alt="일자변경달력아이콘"></i> 일자 변경
						</div>
						<div class="d_bubble" style="display: none;">
							<!-- 안보이게 시작할경우 style제거 -->
							<div class="tri"><img src="<%=strContext%>/images/dashboard/tri.png" alt="삼각형"></div>
							<div class="b_head">
								<p class="tit">조회 일자 변경</p>
								<button type="button" class="btn_close">닫기</button>
							</div>
							<div class="btn_list">
								<button type="button" class="btn on" id="retrieveToday">오늘</button>
								<button type="button" class="btn"    id="retrieveYesterday">전일</button>
							</div>
							<div class="sel_date">
								<label for="date" class="hidden">일자변경달력보기</label>
								<input type="text" class="select_date2" value="${resultToday[0].yyyymmdd}">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	
		<!-- 
		<div class="header">
			<div class="wrapper">
				<h1><img src="<%=strContext%>/images/dashboard/logo.png" alt="노원수학문화관통합관리자로고"></h1>
				<div class="side">
					<dl class="date_txt">
						<dt>오후 <em>15:04</em></dt>
						<dd>8월 18일 현재, 목요일</dd>
					</dl>
					<div class="date_wrap">
						<div class="btn_date"><i><img src="<%=strContext%>/images/dashboard/ico_calendar.png" alt="일자변경달력아이콘"></i> 일자 변경
						</div>
						<div class="d_bubble" style="display: block;">
							<div class="tri"><img src="<%=strContext%>/images/dashboard/tri.png" alt="삼각형"></div>
							<div class="b_head">
								<p class="tit">조회 일자 변경</p>
								<button type="button" class="btn_close">닫기</button>
							</div>
							<div class="btn_list">
								<button type="button" class="btn on">오늘</button>
								<button type="button" class="btn">전일</button>
							</div>
							<div class="sel_date">
								<label for="date" class="hidden">일자변경달력보기</label>
								<input type="text" class="select_date2" value="2019.08.18">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		-->
		
		<!-- //header -->
		<div class="wrapper">
		
			<ul class="list total" id="dispGroup01"></ul>				
		
			<ul class="list total" id="dispGroup02"></ul>		
			
			<ul class="list total" id="dispGroup03"></ul>				
		
			<ul class="list total" id="dispGroup04"></ul>			
		
			<!-- 
			<ul class="list total" id="dispGroup4" style="top:0px;"></ul>				
		
			<ul class="list total" id="dispGroup3" style="top:0px;"></ul>		
		
			<ul class="list total" id="dispGroup1" style="top:-100px;"></ul>
			
			<ul class="list total" id="dispGroup2" style="top:-100px;"></ul>
			-->	
							
			<!-- //list total -->
		</div>
		<!-- //wrapper -->
	</section>
	
 	<form id="dashboardForm" name="dashboardForm">

		<input id="HHMI"             type="hidden" name="hhmi"           value="${resultToday[0].hhmi}" />
		<input id="MM"               type="hidden" name="mm"             value="${resultToday[0].mm}" />
		<input id="DD"               type="hidden" name="dd"             value="${resultToday[0].dd}" />
		<input id="WEEK_NAME"        type="hidden" name="weekName"       value="${resultToday[0].weekName}" />
		<input id="DAYNIGHT"         type="hidden" name="daynight"       value="${resultToday[0].daynight}" />
		<input id="YYYYMMDD"         type="hidden" name="yyyymmdd"       value="${resultToday[0].yyyymmdd}" />
		<input id="YYYYMMDD2"        type="hidden" name="yyyymmdd2"      value="${resultToday[0].yyyymmdd2}" />
		<input id="YYYYMMDD3"        type="hidden" name="yyyymmdd3"      value="${resultToday[0].yyyymmdd3}" />
		<input id="YYYYMMDD4"        type="hidden" name="yyyymmdd4"      value="${resultToday[0].yyyymmdd4}" />
		<input id="YYYYMM_M6_SDATE"  type="hidden" name="yyyymmM6Sdate"  value="${resultToday[0].yyyymmM6Sdate}" />		
		<input id="YYYYMM_M6_SDATE2" type="hidden" name="yyyymmM6Sdate2" value="${resultToday[0].yyyymmM6Sdate2}" />
		<input id="YYYYMM_M6_SDATE3" type="hidden" name="yyyymmM6Sdate3" value="${resultToday[0].yyyymmM6Sdate3}" />
		<input id="YYYYMM_M1_SDATE1" type="hidden" name="yyyymmM1Sdate1" value="${resultToday[0].yyyymmM1Sdate1}" />
		<input id="YYYYMM_M1_SDATE2" type="hidden" name="yyyymmM1Sdate2" value="${resultToday[0].yyyymmM1Sdate2}" />
		
		<input id="YYYYMM_M12_SDATE"  type="hidden" name="yyyymmM12Sdate"  value="${resultToday[0].yyyymmM12Sdate}" />		
		<input id="YYYYMM_M12_SDATE2" type="hidden" name="yyyymmM12Sdate2" value="${resultToday[0].yyyymmM12Sdate2}" />
		<input id="YYYYMM_M12_SDATE3" type="hidden" name="yyyymmM12Sdate3" value="${resultToday[0].yyyymmM12Sdate3}" />		
		
		<input id="YYYYMM_M12" type="hidden" name="yyyymmM12" value="${resultToday[0].yyyymmM12}" />
		<input id="YYYYMM_M11" type="hidden" name="yyyymmM11" value="${resultToday[0].yyyymmM11}" />
		<input id="YYYYMM_M10" type="hidden" name="yyyymmM10" value="${resultToday[0].yyyymmM10}" />
		<input id="YYYYMM_M09" type="hidden" name="yyyymmM09" value="${resultToday[0].yyyymmM09}" />
		<input id="YYYYMM_M08" type="hidden" name="yyyymmM08" value="${resultToday[0].yyyymmM08}" />
		<input id="YYYYMM_M07" type="hidden" name="yyyymmM07" value="${resultToday[0].yyyymmM07}" />
		<input id="YYYYMM_M06" type="hidden" name="yyyymmM06" value="${resultToday[0].yyyymmM06}" />
		<input id="YYYYMM_M05" type="hidden" name="yyyymmM05" value="${resultToday[0].yyyymmM05}" />
		<input id="YYYYMM_M04" type="hidden" name="yyyymmM04" value="${resultToday[0].yyyymmM04}" />
		<input id="YYYYMM_M03" type="hidden" name="yyyymmM03" value="${resultToday[0].yyyymmM03}" />
		<input id="YYYYMM_M02" type="hidden" name="yyyymmM02" value="${resultToday[0].yyyymmM02}" />
		<input id="YYYYMM_M01" type="hidden" name="yyyymmM01" value="${resultToday[0].yyyymmM01}" />
				
	</form>			
	
	<script>
	
		//회원수
		var gvarGP1Contents = "<li style='width:500px;height:400px;'> " + 
		" <h2>총 회원수</h2> " + 
		" <div class='chart chart01'> " + 
		" <div style='width:500px;height:310px;margin:0 auto'> " + 
		" 	<ul class='chart chart01' id='chart_member'></ul> " + 
		" 	<div class='chart_txt'>  " + 
		" 		<dl> " + 
		" 			<dt>총</dt> " + 
		" 			<dd id='chart_member_title_1'>(명)</dd> " + 
		" 		</dl> " + 
		" 	</div> " + 
		" </div> " + 
		" </div> " + 
		" </li> " + 
		" <li style='width:69%;height:400px;'> " + 
		" <div class='right' style='width:100%;'> " + 
		" <h2 class='left'>최근 1년 신규 회원 증감 추이</h2> " + 
		" <div class='chart chart02'> " + 
		" 	<div style='width:100%;height:446px;margin:auto'> " + 
		" 		<div id='chart_stt4'></div> " + 
		" 	</div> " + 
		" </div> " + 
		" </div> " + 
		" </li>";
		
		//매출
		var gvarGP2Contents = "<li style='width:500px;height:400px;'> " + 
		" <h2 id='chart_stt3_title_1'>매출</h2> " +
		" <div class='chart chart01'> " +
		" <div style='width:500px;height:310px;margin:0 auto'> " +
		" 	<ul id='chart_stt3'></ul> " +
		" 	<div class='chart_txt'>  " +
		" 		<dl> " +
		" 			<dt>총</dt> " +
		" 			<dd id='chart_stt3_title_2'>(명)</dd> " +
		" 		</dl> " +
		" 	</div> " +
		" </div> " +
		" </div> " +
		" </li> " +
		" <li style='width:69%;height:400px;'> " +
		" <div class='right' style='width:100%;'> " +
		" <h2 class='left' id='chart_stt6_title_1'>매출 추이</h2> " +
		" <div class='chart chart02'> " +
		" 	<div style='width:100%;height:446px;'> " +
		" 		<div id='chart_stt6'></div> " +
		" 	</div> " +
		" </div> " +
		" </div> " +
		" </li>";
		
		//방문객
		var gvarGP3Contents = "<li style='width:500px;height:400px;'>" + 
		" <h2 id='chart_stt2_title_1'>방문객</h2>" +
		" <div class='chart chart01'>" +
		" <div style='width:500px;height:310px;margin:0 auto'>" +
		" 	<ul id='chart_stt2'></ul>" +
		" 	<div class='chart_txt'> " +
		" 		<dl>" +
		" 			<dt>총</dt>" +
		" 			<dd id='chart_stt2_title_2'>(명)</dd>" +
		" 		</dl>" +
		" 	</div>" +
		" </div>" +
		" </div>" +
		" </li>" +
		" <li style='width:69%;height:400px;'>" +
		" <div class='right' style='width:100%;'>" +
		" <h2 class='left' id='chart_stt5_title_1'>방문객</h2>" +
		" <div class='chart chart02'>" +
		" 	<div style='width:100%;height:446px;margin:auto'>" +
		" 		<div id='chart_stt5'></div>" +
		" 	</div>" +
		" </div>" +
		" </div>" +
		" </li>";
		
		//관람객
		var gvarGP4Contents = "<li style='width:500px;height:400px;'>" + 
		" <h2 id='chart_stt22_title_1'>관람객</h2>" +
		" <div class='chart chart01'>" +
		" <div style='width:500px;height:310px;margin:0 auto'>" +
		" 	<ul id='chart_stt22'></ul>" +
		" 	<div class='chart_txt'> " +
		" 		<dl>" +
		" 			<dt>총</dt>" +
		" 			<dd id='chart_stt22_title_2'>(명)</dd>" +
		" 		</dl>" +
		" 	</div>" +
		" </div>" +
		" </div>" +
		" </li>" +
		" <li style='width:69%;height:400px;'>" +
		" <div class='right' style='width:100%;'>" +
		" <h2 class='left' id='chart_stt52_title_1'>관람객</h2>" +
		" <div class='chart chart02'>" +
		" 	<div style='width:100%;height:446px;margin:auto'>" +
		" 		<div id='chart_stt52'></div>" +
		" 	</div>" +
		" </div>" +
		" </div>" +
		" </li>";
	
		var gvarJobMode     = "";
		var gvarToggleValue = "T";
		var varInternal2Timer; 
		
		$('.btn_list .btn').click(function() {
			$('.btn_list .btn').removeClass('on');
			$(this).toggleClass('on');
		}); //일자변경안버튼
		
		$('.btn_date').click(function() {
			$(this).siblings('.d_bubble').fadeToggle(300);
		}); //일자변경창
		
		$('.btn_close').click(function() {
			$('.d_bubble').fadeOut(300);
		}); //닫기버튼
		
   		$(document).ready(function() {
 			
 			google.charts.load('current', {packages :['corechart', 'bar']});
 			
 		 	var varInternalTimer = setInterval(function() {
 		
 		 		clearInterval(varInternalTimer);
 	
 		 		$("#dispGroup01").empty();
 		 		$("#dispGroup02").empty();
 		 		$("#dispGroup03").empty();
 		 		$("#dispGroup04").empty();
 		 		
 		 		/* 
 				$("#dispGroup3").empty();
 				$("#dispGroup4").empty();
 				*/
 		 		
 				$("#dateTitle_stt4_01").empty();
 				$("#dateTitle_stt4_01").append("${resultToday[0].yyyymmM1Sdate3}" + "<br>~" + "${resultToday[0].yyyymmdd}");	 		
 		 		
 				$("#dateTitle_stt2_01").empty();
 				$("#dateTitle_stt2_01").append("${resultToday[0].yyyymmdd}");
 				
 				$("#dateTitle_stt22_01").empty();
 				$("#dateTitle_stt22_01").append("${resultToday[0].yyyymmdd}"); 				
 				
 				$("#dateTitle_stt3_01").empty();
 				$("#dateTitle_stt3_01").append("${resultToday[0].yyyymmdd}");				
 				
 				$("#dateTitle_stt5_01").empty();
 				$("#dateTitle_stt5_01").append("${resultToday[0].yyyymmdd}");
 				
 				$("#dateTitle_stt52_01").empty();
 				$("#dateTitle_stt52_01").append("${resultToday[0].yyyymmdd}"); 				
 				
 				$("#dateTitle_stt6_01").empty();
 				$("#dateTitle_stt6_01").append("${resultToday[0].yyyymmM1Sdate3.substring(0, 7)}" + "~" + "${resultToday[0].yyyymmdd.substring(0, 7)}");	     					

				$("#dispGroup01").append(gvarGP4Contents);
				fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback);  // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
				fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람별 관람객
				
				
				$("#dispGroup02").append(gvarGP3Contents);
 			  	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);    // PIE :: 기준 총 방문객 (yyyymmdd2 -- yyyymmdd)
 			  	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람별 방문객 				
 				
 				/* 
				fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback);  // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
 			  	fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람별 관람객

 			  	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);    // PIE :: 기준 총 방문객 (yyyymmdd2 -- yyyymmdd)
 			  	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람별 방문객
 			  	*/
 			  	
 			  	/* 
 			  	fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);  // PIE :: 현 총 회원수
 			  	fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);  // 회원 증감 추이
 			  	
 			  	fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);  // 기준 총 매출 (yyyymmdd2 -- yyyymmdd)
 			  	fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);  // 매출 추이
 			  	*/
 			  	
 			  	gvarToggleValue = "T";

 			}, 500);	

			varInternal2Timer = setInterval(function() {
				
				if ((gvarToggleValue === '') || (gvarToggleValue === 'T') || (gvarToggleValue === undefined)) {
					gvarToggleValue = 'B';
				} else {
					gvarToggleValue = 'T';
				}
				
		    	gvarJobMode = "Today";
		    	fn_ajaxDashboardOnly("./selectToday", $("#dashboardForm"), fn_selectToday);  
				
			}, 60000 * 1); 		 	
			//}, 300000 * 1);
   			//}, 10000 * 1);
 		 	
 		});	              
        
	    //총 인원수          
     	var chartDrawStt1 = {
 			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt1 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt1() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '일반구분');
   		          	data.addColumn('number' , '명');
   		 
   		          	//console.log("data = ");
   		          	//console.log(data);
   		          	
   		          	var dataRow = [];

	          		dataRow = ['무료회원', dbData.RESULT.resultMember[0].freeCnt];	
   		          	data.addRow(dataRow);

   		          	dataRow = ['유료회원', dbData.RESULT.resultMember[0].yearCnt];
   		          	data.addRow(dataRow);
   		          	
   		          	/* 
   		          	dataRow = ['특별회원', dbData.RESULT.resultMember[0].spePrn];
   		          	data.addRow(dataRow);
   		          	*/
   		 
   		          	//var varAllMember = dbData.RESULT.resultMember[0].freePrn + dbData.RESULT.resultMember[0].yearPrn + dbData.RESULT.resultMember[0].spePrn;  
   		          	var varAllMember = dbData.RESULT.resultMember[0].freeCnt + dbData.RESULT.resultMember[0].yearCnt;
   		          	
	                var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'in',
	                       width: '430',   		                	   
	                       height: '330',
	                       pieHole: 0.6,
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       legend: {
	                           position: 'bottom',
	                           alignment: 'center'
	                       },
	                       chartArea: { 
	                           left: 0, 
	                           top: 0, 
	                           width: '430', 
	                           height: '290' 
	                       } 
	                       /* ,
	                       tooltip: {
	                           trigger:'none'
	                       } */
	                };
   		            
   		            var chart = new google.visualization.PieChart(document.getElementById('chart_member'));

   		            chart.draw(data, options);	 
   		            
   		         	//$("#chart_member_overlay").empty();
   		         	//$("#chart_member_overlay").append(varAllMember + "(명)");
   		         	
   		       		$("#chart_member_title_1").empty();
   		         	$("#chart_member_title_1").append(varAllMember + " (명)");   		         	
   		         
   		            /* 
   		            $("#ul_chart_member").empty();
   		            $("#ul_chart_member").append("<li class='d1'>"+ "무료회원" +"</li>");
   		            $("#ul_chart_member").append("<li class='d2'>"+ "유료회원" +"</li>");	            
   		            $("#ul_chart_member").append("<li class='d4'>"+ "특별회원" +"</li>");
   		            */
   		            
   		        }
   			        
   				google.charts.setOnLoadCallback(drawDashboardStt1);
     			 
     		}
     	}	              
	    
    	var chartDrawStt2 = {
   			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt2 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt2() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '사업장구분');
   		          	data.addColumn('number' , '명');
   		 
   		          	var dataRow = [];

   		          	var varStt2AllSum = 0;
   		          	
   	           		for(var i in dbData.RESULT.resultStt2) {
   	           			
   	           			//console.log(dbData.RESULT.resultStt2[i].grpName);
   	           			
   	           			/* 
   	           			dataRow = [dbData.RESULT.resultStt4[i].joinDate, dbData.RESULT.resultStt4[i].member0000, dbData.RESULT.resultStt4[i].member9001, dbData.RESULT.resultStt4[i].memberplus];
   	           			
   	           			data.addRow(dataRow);
   	           			
   	           			varmember0000All = varmember0000All + dbData.RESULT.resultStt4[i].member0000;
   	           			varmember9001All = varmember9001All + dbData.RESULT.resultStt4[i].member9001;
   	           			varmemberplusAll = varmemberplusAll + dbData.RESULT.resultStt4[i].memberplus;
   	           			*/
   	           			
   	   		          	dataRow = [dbData.RESULT.resultStt2[i].grpName, dbData.RESULT.resultStt2[i].ioQty];
   	   		          	data.addRow(dataRow);
   	           			
   	   		      		varStt2AllSum = varStt2AllSum + dbData.RESULT.resultStt2[i].ioQty; 
   	   		          	
   	           		}
   	           		
   	           		if (varStt2AllSum <= 0) {
   	   		          	dataRow = ["방문객없음", 10];
   	   		          	data.addRow(dataRow);   	           			
   	           		}
   	           		
   		          	////dataRow = ["전체", varStt2AllSum];
   		          	////data.addRow(dataRow);   	           		
   	           		
		         	$("#chart_stt2_title_1").empty();
		         	//$("#chart_stt2_title_1").append("${resultToday[0].yyyymmdd} 총방문객");
		         	$("#chart_stt2_title_1").append("금일 총방문객");
   	           		
   		         	$("#chart_stt2_title_2").empty();
   		         	$("#chart_stt2_title_2").append(fn_numberWithCommas(varStt2AllSum) + " (명)");   		          	
   		          	
   		          	/*
   					$("#ul_chart_stt2").empty();
   		          	
   					if (dbData.RESULT.resultStt2[0].grp00001Sum >= 1) {
   	   		          	dataRow = ['과학기술관', dbData.RESULT.resultStt2[0].grp00001Sum];
   	   		          	data.addRow(dataRow);
   	   		          	
   	   		      		$("#ul_chart_stt2").append("<li class='d1'>" + "과학기술관"   + "</li>");
   					}

   					if (dbData.RESULT.resultStt2[0].grp00002Sum >= 1) {
   						
   	   		          	dataRow = ['자연사관', dbData.RESULT.resultStt2[0].grp00002Sum];
   	   		          	data.addRow(dataRow);

   	   					$("#ul_chart_stt2").append("<li class='d2'>" + "자연사관" + "</li>");   						
   					}
	          	
   		          	if (dbData.RESULT.resultStt2[0].grp00003Sum >= 1) {
   		          		
   	   		          	dataRow = ['정문', dbData.RESULT.resultStt2[0].grp00003Sum];
   	   		          	data.addRow(dataRow);

   	   		          	$("#ul_chart_stt2").append("<li class='d3'>" + "정문" + "</li>");   		          		
   		          	}
  		          	
   		          	if (dbData.RESULT.resultStt2[0].grp00004Sum >= 1) {
   	   		          	dataRow = ['역사의광장', dbData.RESULT.resultStt2[0].grp00004Sum];
   	   		          	data.addRow(dataRow);

   	   		          	$("#ul_chart_stt2").append("<li class='d4'>" + "역사의광장" + "</li>");   		          		
   		          	}

   		          	if (dbData.RESULT.resultStt2[0].grp00005Sum >= 1) {
   	   		          	dataRow = ['창의나래관', dbData.RESULT.resultStt2[0].grp00005Sum];
   	   		          	data.addRow(dataRow);

   	   		          	$("#ul_chart_stt2").append("<li class='d5'>" + "창의나래관" + "</li>");   		          		
   		          	}

   		          	if (dbData.RESULT.resultStt2[0].grp00006Sum >= 1) {
   	   		          	dataRow = ['종합안내센터', dbData.RESULT.resultStt2[0].grp00006Sum];
   	   		          	data.addRow(dataRow);

   	   		          	$("#ul_chart_stt2").append("<li class='d6'>" + "종합안내센터" + "</li>");   		          		
   		          	}   		          	

					if (dbData.RESULT.resultStt2[0].grp00007Sum >= 1) {
	   		          	dataRow = ['측문', dbData.RESULT.resultStt2[0].grp00007Sum];
	   		          	data.addRow(dataRow);

	   		          	$("#ul_chart_stt2").append("<li class='d7'>" + "측문" + "</li>");						
					}

					if (dbData.RESULT.resultStt2[0].grp00008Sum >= 1) {
	   		          	dataRow = ['북문', dbData.RESULT.resultStt2[0].grp00008Sum];
	   		          	data.addRow(dataRow);

	   		          	$("#ul_chart_stt2").append("<li class='d8'>" + "북문" + "</li>");						
					}
   		          	*/
   		          	
   		          	//console.log("Stt2 data = ");
   		          	//console.log(data);		          	
   		          	
	              	var options = {     
                	   //theme :'maximized',
                	   titlePosition: 'in',
                	   axisTitlesPosition: 'in',
                       width: '430',   		                	   
                       height: '330',
                       pieHole: 0.6,
                       showLables: 'true',
                       //pieSliceText: 'value',
                       pieSliceTextStyle: {
                           color: 'white',
                           fontSize:16
                       },
                       legend: {
                           position: 'bottom',
                           alignment: 'center'
                       },
                       chartArea: { 
                           left: 0, 
                           top: 0, 
                           width: '430', 
                           height: '290' 
                       } 
                       /* ,
                       tooltip: {
                           trigger:'none'
                       } */
                   	};
   		         	 	        
   		            var chart2 = new google.visualization.PieChart(document.getElementById('chart_stt2'));

   		            chart2.draw(data, options);	 	        
   		            
   		        }
   			        
   				google.charts.setOnLoadCallback(drawDashboardStt2);
   			 
   			}
   		}	     	
     	
    	var chartDrawStt22 = {
  			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt22 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt22() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '사업장구분');
   		          	data.addColumn('number' , '명');
   		 
   		          	var dataRow = [];

   					////$("#ul_chart_stt22").empty();
   		          	
   					if ((dbData.RESULT.resultStt22[0].exbt + dbData.RESULT.resultStt22[0].edc + dbData.RESULT.resultStt22[0].evt) <= 0) {
   						
   						dataRow = ['관람객없음', 10];
   	   		          	data.addRow(dataRow);   						
   					
   					} else {
   					
   	   		          	dataRow = ['관람', dbData.RESULT.resultStt22[0].exbt];
   	   		          	data.addRow(dataRow);

   	   					////$("#ul_chart_stt22").append("<li class='d1'>" + "관람"   + "</li>");
   	   		          	
   	   		          	dataRow = ['교육', dbData.RESULT.resultStt22[0].edc];
   	   		          	data.addRow(dataRow);

   	   					////$("#ul_chart_stt22").append("<li class='d2'>" + "교육" + "</li>");	          	
   	   		          	
   	   		          	dataRow = ['강연/행사/영화', dbData.RESULT.resultStt22[0].evt];
   	   		          	data.addRow(dataRow);

   	   		          	////$("#ul_chart_stt22").append("<li class='d3'>" + "강연/행사/영화" + "</li>");   						
   						
   					}
   		          	
		         	$("#chart_stt22_title_1").empty();
		         	//$("#chart_stt22_title_1").append("${resultToday[0].yyyymmdd} 총관람객");   		          	
		         	$("#chart_stt22_title_1").append("금일 총관람객");
   		          	
   		         	$("#chart_stt22_title_2").empty();
   		         	$("#chart_stt22_title_2").append((dbData.RESULT.resultStt22[0].exbt + dbData.RESULT.resultStt22[0].edc + dbData.RESULT.resultStt22[0].evt) + "(명)");   		          	
   		          	
   		          	//console.log("Stt22 data = ");
   		          	//console.log(data);		          	
   		          	
   		          	/* 
   		            var options = {
   		                    //title: ''
   		                    legend: {position: 'none'}
   		            };	
   		          	*/
   		          	
	               var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'in',
	                       width: '430',   		                	   
	                       height: '330',
	                       pieHole: 0.6,
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       legend: {
	                           position: 'bottom',
	                           alignment: 'center'
	                       },
	                       chartArea: { 
	                           left: 0, 
	                           top: 0, 
	                           width: '430', 
	                           height: '290' 
	                       } 
	                       /* ,
	                       tooltip: {
	                           trigger:'none'
	                       } */
	                   };  		          	
   		         	 	        
   		            var chart22 = new google.visualization.PieChart(document.getElementById('chart_stt22'));

   		            chart22.draw(data, options);	 	        
   		            
   		        }
   			        
   				google.charts.setOnLoadCallback(drawDashboardStt22);
   			 
   			}
   		}	      	
     	
    	var chartDrawStt3 = {
 			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt3 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt3() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '사업장구분');
   		          	data.addColumn('number' , '원');
   		 
   		          	var dataRow = [];

   		          	var varStt3AllSum = 0;
   		          	
   		         	for(var i in dbData.RESULT.resultStt3) {
   	   		          	dataRow = [dbData.RESULT.resultStt3[i].cdNm, dbData.RESULT.resultStt3[i].salamt];
   	   		          	data.addRow(dataRow);
   	   		          	
   	   		      		varStt3AllSum = varStt3AllSum + dbData.RESULT.resultStt3[i].salamt; 
   	   		          	
   		         	}

   		         	if (varStt3AllSum <= 0) {
   	   		          	dataRow = ["매출없음", 10];
   	   		          	data.addRow(dataRow);   		         		
   		         	}
   		         	
		         	$("#chart_stt3_title_1").empty();
		         	//$("#chart_stt3_title_1").append("${resultToday[0].yyyymmdd} 기준 총매출");   		         
		         	$("#chart_stt3_title_1").append("금일 총매출");
   		         
   		         	$("#chart_stt3_title_2").empty();
   		         	$("#chart_stt3_title_2").append(fn_numberWithCommas(varStt3AllSum) + " (원)");
   		         	
   		          	/* 
   					$("#ul_chart_stt3").empty();
   		          	
   		          	dataRow = ['관람', dbData.RESULT.resultStt3[0].exbt];
   		          	data.addRow(dataRow);

   					$("#ul_chart_stt3").append("<li class='d1'>" + "관람"   + "</li>");
   		          	
   		          	dataRow = ['교육', dbData.RESULT.resultStt3[0].edc];
   		          	data.addRow(dataRow);

   					$("#ul_chart_stt3").append("<li class='d2'>" + "교육" + "</li>");	          	
   		          	
   		          	dataRow = ['강연/행사/영화', dbData.RESULT.resultStt3[0].evt];
   		          	data.addRow(dataRow);

   		          	$("#ul_chart_stt3").append("<li class='d3'>" + "강연/행사/영화" + "</li>");
   		          	*/
   		          	
  		          //console.log("Stt3 data = ");
  		          //console.log(data);		          	
  		          	
	               var options = {     
                	   //theme :'maximized',
                	   titlePosition: 'in',
                	   axisTitlesPosition: 'in',
                       width: '430',   		                	   
                       height: '330',
                       pieHole: 0.6,
                       showLables: 'true',
                       //pieSliceText: 'value',
                       pieSliceTextStyle: {
                           color: 'white',
                           fontSize:16
                       },
                       legend: {
                           position: 'bottom',
                           alignment: 'center'
                       },
                       chartArea: { 
                           left: 0, 
                           top: 0, 
                           width: '430', 
                           height: '290' 
                       } 
                       /* ,
                       tooltip: {
                           trigger:'none'
                       } */
                    };         
   		         	 	        
   		            var chart3 = new google.visualization.PieChart(document.getElementById('chart_stt3'));

   		            chart3.draw(data, options);	 	        
   		            
   		        }
   				google.charts.setOnLoadCallback(drawDashboardStt3);
   			}
   		}	     	
     	
    	var chartDrawStt4 = {
 			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt4 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt4() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '가입월');
   		          	data.addColumn('number' , '신규(무료회원)');
   		          	data.addColumn('number' , '신규(유료회원)');
   		          		 
   		          	var dataRow = [];

   		          	var varmember0000All = 0;
   		          	var varmember9001All = 0;
   		          	var varmemberplusAll = 0;
   		          	
   		          	//01 개월
   		          	var varThisMonth00 = dbData.RESULT.resultStt4[0].month01;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt01, dbData.RESULT.resultStt4[0].yearCnt01];
   	           		data.addRow(dataRow);   		          	
   	           		
   		          	//02 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month02;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt02, dbData.RESULT.resultStt4[0].yearCnt02];
   	           		data.addRow(dataRow);   	           		
   		          	
   		          	//03 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month03;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt03, dbData.RESULT.resultStt4[0].yearCnt03];
   	           		data.addRow(dataRow);      	           		
   		          	
   		          	//04 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month04;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt04, dbData.RESULT.resultStt4[0].yearCnt04];
   	           		data.addRow(dataRow);   
   	           		
   		          	//05 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month05;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt05, dbData.RESULT.resultStt4[0].yearCnt05];
   	           		data.addRow(dataRow);  
   	           		
   		          	//06 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month06;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt06, dbData.RESULT.resultStt4[0].yearCnt06];
   	           		data.addRow(dataRow);   
   	           		
   		          	//07 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month07;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt07, dbData.RESULT.resultStt4[0].yearCnt07];
   	           		data.addRow(dataRow);   
   	           		
   		          	//08 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month08;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt08, dbData.RESULT.resultStt4[0].yearCnt08];
   	           		data.addRow(dataRow);   
   	           		
   		          	//09 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month09;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt09, dbData.RESULT.resultStt4[0].yearCnt09];
   	           		data.addRow(dataRow);   
   	           		
   		          	//10 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month10;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt10, dbData.RESULT.resultStt4[0].yearCnt10];
   	           		data.addRow(dataRow);   
   	           		
   		          	//11 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month11;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt11, dbData.RESULT.resultStt4[0].yearCnt11];
   	           		data.addRow(dataRow);   
   	           		
   		          	//12 개월
   		          	varThisMonth00 = dbData.RESULT.resultStt4[0].month12;
   		         	varThisMonth00 = varThisMonth00.substring(0, 4)  + "년" + varThisMonth00.substring(4, 6)  + "월";
   	           		dataRow = [varThisMonth00, dbData.RESULT.resultStt4[0].freeCnt12, dbData.RESULT.resultStt4[0].yearCnt12];
   	           		data.addRow(dataRow);      	           		
   	           		
   		          	/* 
   	           		for(var i in dbData.RESULT.resultStt4) {
   	           			
   	           			//console.log(dbData.RESULT.resultStt4[i].joinDate);
   	           			
   	           			 
   	           			//dataRow = [dbData.RESULT.resultStt4[i].joinDate, dbData.RESULT.resultStt4[i].member0000, dbData.RESULT.resultStt4[i].member9001, dbData.RESULT.resultStt4[i].memberplus];
   	           			
   	           			//data.addRow(dataRow);
   	           			
   	           			//varmember0000All = varmember0000All + dbData.RESULT.resultStt4[i].member0000;
   	           			//varmember9001All = varmember9001All + dbData.RESULT.resultStt4[i].member9001;
   	           			//varmemberplusAll = varmemberplusAll + dbData.RESULT.resultStt4[i].memberplus;
   	           			
   	           			
   	           			dataRow = [dbData.RESULT.resultStt4[i].joinMm + "월", dbData.RESULT.resultStt4[i].freePrn, dbData.RESULT.resultStt4[i].yearPrn];
   	           			data.addRow(dataRow);
   	           			
   	           		}
   		          	*/
   	           		
   	           		/* 
   					$("#ul_chart_stt4").empty();
   					$("#ul_chart_stt4").append("<li class='d1'>" + "신규회원 " + varmember0000All + "명</li>");
   					$("#ul_chart_stt4").append("<li class='d2'>" + "탈퇴회원 " + varmember9001All + "명</li>");	          	
   		          	$("#ul_chart_stt4").append("<li class='d3'>" + "증감회원 " + varmemberplusAll + "명</li>");
   		          	*/
   		          	
   		          	/* 
   		         	$("#chart_stt4_overlay").empty();
   		         	$("#chart_stt4_overlay").append("최근 1년 신규 회원 증감 추이");
   		         	*/
   	           		
   		          	//console.log("Stt4 data = ");
   		          	//console.log(data);		          	
   		          	
   		          	/* 
   		            var options = {
   		                    title: ''
   		                    legend: {position: 'none'}
   		            };	
   		          	*/
   		         
	               var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'in',
	                       width: '100%',   		                	   
	                       height: '330',
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       legend: {
	                           position: 'bottom',
	                           alignment: 'center'
	                       },
	                       vAxis : {format: '#,##0명' 
	                       },	                       
	                       chartArea: { 
	                           left: 80, 
	                           top: 10, 
	                           width: '100%', 
	                           height: '270' 
	                       } 
	                       /* ,
	                       tooltip: {
	                           trigger:'none'
	                       } */
	                   };   		          	
   		          	
   		         /* var options = {     
	                       height: '100%',
	                       width: '100%',
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:12
	                       },
	                       legend: {
	                           position: 'bottom',
	                           alignment: 'center'
	                       },
	                       chartArea: { 
	                           left: 10, 
	                           top: 10, 
	                           width: '130%', 
	                           height: '65%'
	                       }

	                   }; */   		          	
   		          	
   		            var chart4 = new google.visualization.LineChart(document.getElementById('chart_stt4'));
   		            chart4.draw(data, options);				
   		            
   		        }
   				google.charts.setOnLoadCallback(drawDashboardStt4);
   			}
   		}     	
     	
    	var chartDrawStt5 = {
  			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt5 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt5() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '장소');
   		          	data.addColumn('number' , '입장객');
   		          		 
   		          	var dataRow = [];

   	           		for(var i in dbData.RESULT.resultStt5) {
   	           			
   	           			dataRow = [dbData.RESULT.resultStt5[i].grpCdNm, dbData.RESULT.resultStt5[i].ioQty];
   	           			
   	           			data.addRow(dataRow);
   	           		}
   		          	
   		          	//console.log("Stt5 data = ");
   		          	//console.log(data);		          	
   		          	
 	               var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'in',
	                       width: '100%',   		                	   
	                       height: '330',
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       legend: {
	                           position: 'none',
	                           alignment: 'center'
	                       },
	                       vAxis : {format: '#,##0명' 
	                       },	                       
	                       chartArea: { 
	                           left: 80, 
	                           top: 10, 
	                           width: '100%', 
	                           height: '270' 
	                       } 
	                       /* ,
	                       tooltip: {
	                           trigger:'none'
	                       } */
	                   };             
   		         	 	        
   		            var chart5 = new google.visualization.ColumnChart(document.getElementById('chart_stt5'));
   		            chart5.draw(data, options);
   		            
   		         	$("#chart_stt5_title_1").empty();
   		         	//$("#chart_stt5_title_1").append("${resultToday[0].yyyymmdd} 기준 게이트 위치 별 방문객 현황");
   		         	$("#chart_stt5_title_1").append("금일 기준 게이트 위치 별 방문객 현황");
   		            
   		        }
   				google.charts.setOnLoadCallback(drawDashboardStt5);
   			}
   		}     	
     	
    	var chartDrawStt52 = {
 			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt52 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt52() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '장소');
   		          	data.addColumn('number' , '관람객');
   		          		 
   		          	var dataRow = [];

   	           		for(var i in dbData.RESULT.resultStt52) {
   	           			
   	           			dataRow = [dbData.RESULT.resultStt52[i].partCdNm, dbData.RESULT.resultStt52[i].vistnmpr];
   	           			
   	           			data.addRow(dataRow);
   	           		}
   		          	
   		         	$("#chart_stt52_title_1").empty();
   		         	//$("#chart_stt52_title_1").append("${resultToday[0].yyyymmdd} 기준 관람객");
   		         	$("#chart_stt52_title_1").append("금일 기준 관람객");
   	           		
   		          	//console.log("Stt52 data = ");
   		          	//console.log(data);		          	
   		          	
	
 	               var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'left',
	                       width: '100%',   		                	   
	                       height: '330',
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       vAxis : {format: '#,##0명' 
	                       },
	                       legend: {
	                           position: 'none',
	                           alignment: 'center'
	                       },
	                       chartArea: { 
	                           left: 80, 
	                           top: 10, 
	                           width: '100%', 
	                           height: '270' 
	                       } 
	                   };
   		          	
 					/* 
   		            var options = {
   		                    //title: ''
   		                    legend: {position: 'none'}
   		                  }; */
   		         	 	        
   		            var chart52 = new google.visualization.ColumnChart(document.getElementById('chart_stt52'));
   		            chart52.draw(data, options);				
   		            
   		        }
   				google.charts.setOnLoadCallback(drawDashboardStt52);
   			}
   		}        	
     	
		var chartDrawStt6 = {
  			 
   		    chartDraw : function(dbData) {
   		    	
   		    	//console.log("call chartDrawStt6 : chartDraw : function(dbData)");
   		    	//console.log(dbData);
   		 
   		        function drawDashboardStt6() {
   		 
   		          	var data = new google.visualization.DataTable();
   		          
   		          	data.addColumn('string' , '월');
   		          	data.addColumn('number' , '관람');
   		          	data.addColumn('number' , '교육');
   		          	data.addColumn('number' , '강연/행사/영화');
   		          	data.addColumn('number' , '전체');
   		          		 
   		          	var dataRow = [];

   		          	var varYYYYMM = "";
   		         	var varConvertYYYYMM = "";
   		          	
   	           		for(var i in dbData.RESULT.resultStt6) {
   	           			
   	           			//console.log(dbData.RESULT.resultStt6[i].selngYymm);
   	           			
   	           			varYYYYMM = dbData.RESULT.resultStt6[i].selngYymm;
   	           			
   	           			varConvertYYYYMM = varYYYYMM.substring(0,4) + "년" + varYYYYMM.substring(4,7) + "월"; 
   	           			
   	           			dataRow = [varConvertYYYYMM, dbData.RESULT.resultStt6[i].salamtExbt, dbData.RESULT.resultStt6[i].salamtEdc, dbData.RESULT.resultStt6[i].salamtEvt, dbData.RESULT.resultStt6[i].allSum];
   	           			
   	           			data.addRow(dataRow);
   	           		}
   		          	
   		          	//console.log("Stt6 data = ");
   		          	//console.log(data);		   
   		          	
 	               var options = {     
	                	   //theme :'maximized',
	                	   titlePosition: 'in',
	                	   axisTitlesPosition: 'in',
	                       width: '100%',   		                	   
	                       height: '330',
	                       showLables: 'true',
	                       //pieSliceText: 'value',
	                       pieSliceTextStyle: {
	                           color: 'white',
	                           fontSize:16
	                       },
	                       legend: {
	                           position: 'bottom',
	                           alignment: 'center'
	                       },
	                       vAxis : {format: '#,##0원' 
	                       },	                       
	                       chartArea: { 
	                           left: 80, 
	                           top: 20, 
	                           width: '100%', 
	                           height: '270' 
	                       } 
	                       /* ,
	                       tooltip: {
	                           trigger:'none'
	                       } */
	                   };    		          	
   		          	
 	               
 	               	var varChartStt6 = document.getElementById('chart_stt6');
   		            var chart6 = new google.visualization.ColumnChart(varChartStt6);
   		            
   		         	chart6.clearChart();
   		            
   		            chart6.draw(data, options);			
   		            
   		         	window.addEventListener('ready', function() { chart6.draw(data, options); }, false);     		            
   		            
   		            var varPrevMonth = "${resultToday[0].yyyymmM6Sdate}";
   		            var varNextMonth = "${resultToday[0].yyyymmdd2}";
   		            
   		            varPrevMonth = varPrevMonth.substring(0, 4) + "년 " + varPrevMonth.substring(4, 6) + "월";
   		         	varNextMonth = varNextMonth.substring(0, 4) + "년 " + varNextMonth.substring(4, 6) + "월";
   		            
   		         	$("#chart_stt6_title_1").empty();
   		         	$("#chart_stt6_title_1").append(varPrevMonth + " ~ " + varNextMonth + " 매출 추이");   		            
   		            
   		        }
   				google.charts.setOnLoadCallback(drawDashboardStt6);
   			}
   		}     	
     	
        function fn_stt5_callback(data) {  //bar(for Stt5 - 관람 입장객)
        	
    		//console.log("call fn_stt5_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt5.chartDraw(data);
    		}
     	}
     	
        function fn_stt52_callback(data) {  //bar(for Stt55 - 관람 관람객)
        	
    		//console.log("call fn_stt52_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt52.chartDraw(data);
    		}
     	}     	
     	
        function fn_stt1_callback(data) {  //pie(for Member Stt)
        	
    		//console.log("call fn_stt1_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			
    			chartDrawStt1.chartDraw(data);
               	
    		}
    		
        }	              	              
	   
        function fn_stt2_callback(data) {  //pie(for 총 입장객 Stt)
        	
    		//console.log("call fn_stt2_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt2.chartDraw(data);
    		}
        }	         	
     	
        function fn_stt22_callback(data) {  //pie(for 총 관람객 Stt)
        	
    		//console.log("call fn_stt22_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt22.chartDraw(data);
    		}
        }	        
        
        function fn_stt3_callback(data) {  //pie(for 총 매출객)
        	
    		//console.log("call fn_stt3_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt3.chartDraw(data);
	   		}
        }            

        function fn_stt6_callback(data) {  //pie(for Stt6 - 매출 추이)
        	
    		//console.log("call fn_stt6_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			//console.log("매출 추이");
    			chartDrawStt6.chartDraw(data);
    		}
    		
    		////$('.bubble').fadeOut(300);
    		
        }        
        
        function fn_stt4_callback(data) {  //pie(for Stt4 - 회원 증감 추이)
        	
    		//console.log("call fn_stt4_callback");
    		//console.log(data);
    		
    		if (data.RESULT.SUCCESS_YN == 'Y') {
    			chartDrawStt4.chartDraw(data);
    		}
		}           
       
		$('.select_date2').change(function() {
			
			var varSelDate = $(this).val();

			varSelDate = varSelDate.replaceAll(".", "");
			
			$("#YYYYMMDD").val(varSelDate);
			
			fn_ajaxDashboardOnly("./selectDate", $("#dashboardForm"), fn_selectDate);  // 날짜 선택
    	
			$('.d_bubble').fadeOut(300);
			
		});        
       
		function fn_selectDate(data) {

			clearInterval(varInternal2Timer);
			
			gvarJobMode = "Selectday";
			
			$(".date_txt").empty();
			$(".date_txt").append("<dt>" + data.RESULT.resultDate[0].mm + "월 " + data.RESULT.resultDate[0].dd + "일, " + data.RESULT.resultDate[0].weekName + "</dt>");
			
			$("#dateTitle_stt4_01").empty();
			$("#dateTitle_stt4_01").append(data.RESULT.resultDate[0].yyyymmM1Sdate3 + "<br>~" + data.RESULT.resultDate[0].yyyymmdd);
			
			$("#dateTitle_stt2_01").empty();
			$("#dateTitle_stt2_01").append(data.RESULT.resultDate[0].yyyymmdd);
			
			$("#dateTitle_stt22_01").empty();
			$("#dateTitle_stt22_01").append(data.RESULT.resultDate[0].yyyymmdd);			
			
			$("#dateTitle_stt3_01").empty();
			$("#dateTitle_stt3_01").append(data.RESULT.resultDate[0].yyyymmdd);				
			
			$("#dateTitle_stt5_01").empty();
			$("#dateTitle_stt5_01").append(data.RESULT.resultDate[0].yyyymmdd);				
			
			$("#dateTitle_stt52_01").empty();
			$("#dateTitle_stt52_01").append(data.RESULT.resultDate[0].yyyymmdd);			
			
			$("#dateTitle_stt6_01").empty();
			$("#dateTitle_stt6_01").append(data.RESULT.resultDate[0].yyyymmM1Sdate3.substring(0, 7) + "~" + data.RESULT.resultDate[0].yyyymmdd.substring(0, 7));
			
			$("#HHMI").val(data.RESULT.resultDate[0].hhmi);
			$("#MM").val(data.RESULT.resultDate[0].mm);
			$("#DD").val(data.RESULT.resultDate[0].dd);
			$("#WEEK_NAME").val(data.RESULT.resultDate[0].weekName);
			$("#DAYNIGHT").val(data.RESULT.resultDate[0].daynight);
			$("#YYYYMMDD").val(data.RESULT.resultDate[0].yyyymmdd);
			$("#YYYYMMDD2").val(data.RESULT.resultDate[0].yyyymmdd2);
			$("#YYYYMMDD3").val(data.RESULT.resultDate[0].yyyymmdd3);
			$("#YYYYMMDD4").val(data.RESULT.resultDate[0].yyyymmdd4);
			$("#YYYYMM_M6_SDATE").val(data.RESULT.resultDate[0].yyyymmM6Sdate);
			$("#YYYYMM_M6_SDATE2").val(data.RESULT.resultDate[0].yyyymmM6Sdate2);
			$("#YYYYMM_M6_SDATE3").val(data.RESULT.resultDate[0].yyyymmM6Sdate3);
			$("#YYYYMM_M1_SDATE1").val(data.RESULT.resultDate[0].yyyymmM1Sdate1);
			$("#YYYYMM_M1_SDATE2").val(data.RESULT.resultDate[0].yyyymmM1Sdate2);
        	
			$("#YYYYMM_M12_SDATE").val(data.RESULT.resultDate[0].yyyymmM12Sdate);
			$("#YYYYMM_M12_SDATE2").val(data.RESULT.resultDate[0].yyyymmM12Sdate2);
			$("#YYYYMM_M12_SDATE3").val(data.RESULT.resultDate[0].yyyymmM12Sdate3);			
			
			$("#YYYYMM_M12").val(data.RESULT.resultDate[0].yyyymmM12);
			$("#YYYYMM_M11").val(data.RESULT.resultDate[0].yyyymmM11);
			$("#YYYYMM_M10").val(data.RESULT.resultDate[0].yyyymmM10);
			$("#YYYYMM_M09").val(data.RESULT.resultDate[0].yyyymmM09);
			$("#YYYYMM_M08").val(data.RESULT.resultDate[0].yyyymmM08);
			$("#YYYYMM_M07").val(data.RESULT.resultDate[0].yyyymmM07);
			$("#YYYYMM_M06").val(data.RESULT.resultDate[0].yyyymmM06);
			$("#YYYYMM_M05").val(data.RESULT.resultDate[0].yyyymmM05);
			$("#YYYYMM_M04").val(data.RESULT.resultDate[0].yyyymmM04);
			$("#YYYYMM_M03").val(data.RESULT.resultDate[0].yyyymmM03);
			$("#YYYYMM_M02").val(data.RESULT.resultDate[0].yyyymmM02);
			$("#YYYYMM_M01").val(data.RESULT.resultDate[0].yyyymmM01);
			
	    	fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
	    	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);   // 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
	    	fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback); // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
	    	fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
	    	fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
	    	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람 입장객
	    	fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관림 관람객
	    	fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이					
			
	    	/* JYS 
			$("#dispGroup1").show();
			$("#dispGroup2").show();
			$("#dispGroup3").show();
			$("#dispGroup4").show();	    	
	    	
	    	$('.bubble').fadeOut(300);
	    	*/
			
		}		
		
	    $('#retrieveToday').click(function() {
	    	gvarJobMode   = "Today";
	    	location.href = "./main";
	    });		
		
	    $('#retrieveYesterday').click(function() {
	    	
	    	$('.d_bubble').fadeOut(300);
	    	
	    	gvarJobMode = "Yesterday";
	    	fn_ajaxDashboardOnly("./selectYesterday", $("#dashboardForm"), fn_selectYesterday);  // 현 총 회원수
	    });	    	    
	   
	    function fn_selectToday(data) {
	    
	    	//console.log("call fn_selectToday");
	    	//console.log(data);
	    	
			if (data.RESULT.SUCCESS_YN == 'Y') {

				$(".date_txt").empty();
				//$(".date_txt").append("<dt>" + data.RESULT.resultToday[0].mm + "<em>월 " + data.RESULT.resultToday[0].dd + "일 현재, " + data.RESULT.resultToday[0].weekName + "</dt>");
				$(".date_txt").append("<dt>" + data.RESULT.resultToday[0].daynight + "<em>" + data.RESULT.resultToday[0].hhmi + "</em></dt><dd>" + data.RESULT.resultToday[0].mm + "월 " + data.RESULT.resultToday[0].dd + "일 현재, " + data.RESULT.resultToday[0].weekName + "</dd>");
				
				$("#dateTitle_stt4_01").empty();
				$("#dateTitle_stt4_01").append(data.RESULT.resultToday[0].yyyymmM1Sdate3 + "<br>~" + data.RESULT.resultToday[0].yyyymmdd);
				
				$("#dateTitle_stt2_01").empty();
				$("#dateTitle_stt2_01").append(data.RESULT.resultToday[0].yyyymmdd);
				
				$("#dateTitle_stt22_01").empty();
				$("#dateTitle_stt22_01").append(data.RESULT.resultToday[0].yyyymmdd);				
				
				$("#dateTitle_stt3_01").empty();
				$("#dateTitle_stt3_01").append(data.RESULT.resultToday[0].yyyymmdd);				
				
				$("#dateTitle_stt5_01").empty();
				$("#dateTitle_stt5_01").append(data.RESULT.resultToday[0].yyyymmdd);				
				
				$("#dateTitle_stt52_01").empty();
				$("#dateTitle_stt52_01").append(data.RESULT.resultToday[0].yyyymmdd);				
				
				$("#dateTitle_stt6_01").empty();
				$("#dateTitle_stt6_01").append(data.RESULT.resultToday[0].yyyymmM1Sdate3.substring(0, 7) + "~" + data.RESULT.resultToday[0].yyyymmdd.substring(0, 7));
				
				$("#HHMI").val(data.RESULT.resultToday[0].hhmi);
				$("#MM").val(data.RESULT.resultToday[0].mm);
				$("#DD").val(data.RESULT.resultToday[0].dd);
				$("#WEEK_NAME").val(data.RESULT.resultToday[0].weekName);
				$("#DAYNIGHT").val(data.RESULT.resultToday[0].daynight);
				$("#YYYYMMDD").val(data.RESULT.resultToday[0].yyyymmdd);
				$("#YYYYMMDD2").val(data.RESULT.resultToday[0].yyyymmdd2);
				$("#YYYYMMDD3").val(data.RESULT.resultToday[0].yyyymmdd3);
				$("#YYYYMMDD4").val(data.RESULT.resultToday[0].yyyymmdd4);
				$("#YYYYMM_M6_SDATE").val(data.RESULT.resultToday[0].yyyymmM6Sdate);
				$("#YYYYMM_M6_SDATE2").val(data.RESULT.resultToday[0].yyyymmM6Sdate2);
				$("#YYYYMM_M6_SDATE3").val(data.RESULT.resultToday[0].yyyymmM6Sdate3);
				$("#YYYYMM_M1_SDATE1").val(data.RESULT.resultToday[0].yyyymmM1Sdate1);
				$("#YYYYMM_M1_SDATE2").val(data.RESULT.resultToday[0].yyyymmM1Sdate2);
            	
				$("#YYYYMM_M12_SDATE").val(data.RESULT.resultToday[0].yyyymmM12Sdate);
				$("#YYYYMM_M12_SDATE2").val(data.RESULT.resultToday[0].yyyymmM12Sdate2);
				$("#YYYYMM_M12_SDATE3").val(data.RESULT.resultToday[0].yyyymmM12Sdate3);				
				
				$("#YYYYMM_M12").val(data.RESULT.resultToday[0].yyyymmM12);
				$("#YYYYMM_M11").val(data.RESULT.resultToday[0].yyyymmM11);
				$("#YYYYMM_M10").val(data.RESULT.resultToday[0].yyyymmM10);
				$("#YYYYMM_M09").val(data.RESULT.resultToday[0].yyyymmM09);
				$("#YYYYMM_M08").val(data.RESULT.resultToday[0].yyyymmM08);
				$("#YYYYMM_M07").val(data.RESULT.resultToday[0].yyyymmM07);
				$("#YYYYMM_M06").val(data.RESULT.resultToday[0].yyyymmM06);
				$("#YYYYMM_M05").val(data.RESULT.resultToday[0].yyyymmM05);
				$("#YYYYMM_M04").val(data.RESULT.resultToday[0].yyyymmM04);
				$("#YYYYMM_M03").val(data.RESULT.resultToday[0].yyyymmM03);
				$("#YYYYMM_M02").val(data.RESULT.resultToday[0].yyyymmM02);
				$("#YYYYMM_M01").val(data.RESULT.resultToday[0].yyyymmM01);
				
				console.log("gvarToggleValue = " + gvarToggleValue);
				
				if (gvarToggleValue === 'B') {

					/* 
					$("#dispGroup1").empty();
					$("#dispGroup2").empty();
					$("#dispGroup3").empty();
					$("#dispGroup4").empty();
					*/
					
					$("#dispGroup01").empty();
					$("#dispGroup02").empty();
					$("#dispGroup03").empty();
					$("#dispGroup04").empty();
					
					$("#dispGroup01").append(gvarGP1Contents);
					fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
					fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
					
					$("#dispGroup02").append(gvarGP2Contents); 
					fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
					fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이
					
					/* 
					$("#dispGroup1").empty();
					$("#dispGroup2").empty();					
					$("#dispGroup3").append("<li style='width:500px;height:400px;'>" + 
							" <h2 id='chart_stt2_title_1'>방문객</h2>" +
							" <div class='chart chart01'>" +
							" <div style='width:500px;height:310px;margin:0 auto'>" +
							" 	<ul id='chart_stt2'></ul>" +
							" 	<div class='chart_txt'> " +
							" 		<dl>" +
							" 			<dt>총</dt>" +
							" 			<dd id='chart_stt2_title_2'>(명)</dd>" +
							" 		</dl>" +
							" 	</div>" +
							" </div>" +
							" </div>" +
							" </li>" +
							" <li style='width:69%;height:400px;'>" +
							" <div class='right' style='width:100%;'>" +
							" <h2 class='left' id='chart_stt5_title_1'>방문객</h2>" +
							" <div class='chart chart02'>" +
							" 	<div style='width:100%;height:446px;margin:auto'>" +
							" 		<div id='chart_stt5'></div>" +
							" 	</div>" +
							" </div>" +
							" </div>" +
							" </li>");
					
					$("#dispGroup4").append("<li style='width:500px;height:400px;'>" + 
							" <h2 id='chart_stt22_title_1'>관람객</h2>" +
							" <div class='chart chart01'>" +
							" <div style='width:500px;height:310px;margin:0 auto'>" +
							" 	<ul id='chart_stt22'></ul>" +
							" 	<div class='chart_txt'> " +
							" 		<dl>" +
							" 			<dt>총</dt>" +
							" 			<dd id='chart_stt22_title_2'>(명)</dd>" +
							" 		</dl>" +
							" 	</div>" +
							" </div>" +
							" </div>" +
							" </li>" +
							" <li style='width:69%;height:400px;'>" +
							" <div class='right' style='width:100%;'>" +
							" <h2 class='left' id='chart_stt52_title_1'>관람객</h2>" +
							" <div class='chart chart02'>" +
							" 	<div style='width:100%;height:446px;margin:auto'>" +
							" 		<div id='chart_stt52'></div>" +
							" 	</div>" +
							" </div>" +
							" </div>" +
							" </li>");


			    	fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
			    	fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람 관람객
			    	fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이						
					*/
					
				} else {
					
					$("#dispGroup01").empty();
					$("#dispGroup02").empty();
					$("#dispGroup03").empty();
					$("#dispGroup04").empty();
					
					$("#dispGroup01").append(gvarGP4Contents);
					fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback);  // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
					fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람 관람객
					
					$("#dispGroup02").append(gvarGP3Contents);
	 			  	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);    // PIE :: 기준 총 방문객 (yyyymmdd2 -- yyyymmdd)
	 			  	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람 방문객					
					
					/* 
					$("#dispGroup1").append("<li style='width:500px;height:400px;'> " + 
							" <h2>총 회원수</h2> " + 
							" <div class='chart chart01'> " + 
							" <div style='width:500px;height:310px;margin:0 auto'> " + 
							" 	<ul class='chart chart01' id='chart_member'></ul> " + 
							" 	<div class='chart_txt'>  " + 
							" 		<dl> " + 
							" 			<dt>총</dt> " + 
							" 			<dd id='chart_member_title_1'>(명)</dd> " + 
							" 		</dl> " + 
							" 	</div> " + 
							" </div> " + 
							" </div> " + 
							" </li> " + 
							" <li style='width:69%;height:400px;'> " + 
							" <div class='right' style='width:100%;'> " + 
							" <h2 class='left'>최근 1년 신규 회원 증감 추이</h2> " + 
							" <div class='chart chart02'> " + 
							" 	<div style='width:100%;height:446px;margin:auto'> " + 
							" 		<div id='chart_stt4'></div> " + 
							" 	</div> " + 
							" </div> " + 
							" </div> " + 
							" </li>");
					
					$("#dispGroup2").append("<li style='width:500px;height:400px;'> " + 
							" <h2 id='chart_stt3_title_1'>매출</h2> " +
							" <div class='chart chart01'> " +
							" <div style='width:500px;height:310px;margin:0 auto'> " +
							" 	<ul id='chart_stt3'></ul> " +
							" 	<div class='chart_txt'>  " +
							" 		<dl> " +
							" 			<dt>총</dt> " +
							" 			<dd id='chart_stt3_title_2'>(명)</dd> " +
							" 		</dl> " +
							" 	</div> " +
							" </div> " +
							" </div> " +
							" </li> " +
							" <li style='width:69%;height:400px;'> " +
							" <div class='right' style='width:100%;'> " +
							" <h2 class='left' id='chart_stt6_title_1'>매출 추이</h2> " +
							" <div class='chart chart02'> " +
							" 	<div style='width:100%;height:446px;'> " +
							" 		<div id='chart_stt6'></div> " +
							" 	</div> " +
							" </div> " +
							" </div> " +
							" </li>"); 
					$("#dispGroup3").empty();
					$("#dispGroup4").empty();					

					fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
					fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
					fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);   // 기준 총 입장객 (yyyymmdd2 -- yyyymmdd)	
					fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람 입장객					
					*/
				}
				
			}		    	
	    	
	    }
	    
	    function fn_selectYesterday(data) {
	    	
	    	//console.log("call fn_selectYesterday");
	    	//console.log(data);
	    	
	    	clearInterval(varInternal2Timer);
	    	
			if (data.RESULT.SUCCESS_YN == 'Y') {

				$("#dispGroup01").empty();
				$("#dispGroup02").empty();
				$("#dispGroup03").empty();
				$("#dispGroup04").empty();
				
				$(".date_txt").empty();
				$(".date_txt").append("<dt>" + data.RESULT.resultYesterday[0].mm + "월 " + data.RESULT.resultYesterday[0].dd + "일 어제, " + data.RESULT.resultYesterday[0].weekName + "</dt>");
				
				$("#dateTitle_stt4_01").empty();
				$("#dateTitle_stt4_01").append(data.RESULT.resultYesterday[0].yyyymmM1Sdate3 + "<br>~" + data.RESULT.resultYesterday[0].yyyymmdd);
				
				$("#dateTitle_stt2_01").empty();
				$("#dateTitle_stt2_01").append(data.RESULT.resultYesterday[0].yyyymmdd);
				
				$("#dateTitle_stt22_01").empty();
				$("#dateTitle_stt22_01").append(data.RESULT.resultYesterday[0].yyyymmdd);				
				
				$("#dateTitle_stt3_01").empty();
				$("#dateTitle_stt3_01").append(data.RESULT.resultYesterday[0].yyyymmdd);				
				
				$("#dateTitle_stt5_01").empty();
				$("#dateTitle_stt5_01").append(data.RESULT.resultYesterday[0].yyyymmdd);				
				
				$("#dateTitle_stt52_01").empty();
				$("#dateTitle_stt52_01").append(data.RESULT.resultYesterday[0].yyyymmdd);				
				
				$("#dateTitle_stt6_01").empty();
				$("#dateTitle_stt6_01").append(data.RESULT.resultYesterday[0].yyyymmM1Sdate3.substring(0, 7) + "~" + data.RESULT.resultYesterday[0].yyyymmdd.substring(0, 7));
				
				$("#HHMI").val(data.RESULT.resultYesterday[0].hhmi);
				$("#MM").val(data.RESULT.resultYesterday[0].mm);
				$("#DD").val(data.RESULT.resultYesterday[0].dd);
				$("#WEEK_NAME").val(data.RESULT.resultYesterday[0].weekName);
				$("#DAYNIGHT").val(data.RESULT.resultYesterday[0].daynight);
				$("#YYYYMMDD").val(data.RESULT.resultYesterday[0].yyyymmdd);
				$("#YYYYMMDD2").val(data.RESULT.resultYesterday[0].yyyymmdd2);
				$("#YYYYMMDD3").val(data.RESULT.resultYesterday[0].yyyymmdd3);
				$("#YYYYMMDD4").val(data.RESULT.resultYesterday[0].yyyymmdd4);
				$("#YYYYMM_M6_SDATE").val(data.RESULT.resultYesterday[0].yyyymmM6Sdate);
				$("#YYYYMM_M6_SDATE2").val(data.RESULT.resultYesterday[0].yyyymmM6Sdate2);
				$("#YYYYMM_M6_SDATE3").val(data.RESULT.resultYesterday[0].yyyymmM6Sdate3);
				$("#YYYYMM_M1_SDATE1").val(data.RESULT.resultYesterday[0].yyyymmM1Sdate1);
				$("#YYYYMM_M1_SDATE2").val(data.RESULT.resultYesterday[0].yyyymmM1Sdate2);
            	
				$("#YYYYMM_M12_SDATE").val(data.RESULT.resultYesterday[0].yyyymmM12Sdate);
				$("#YYYYMM_M12_SDATE2").val(data.RESULT.resultYesterday[0].yyyymmM12Sdate2);
				$("#YYYYMM_M12_SDATE3").val(data.RESULT.resultYesterday[0].yyyymmM12Sdate3);				

				$("#YYYYMM_M12").val(data.RESULT.resultYesterday[0].yyyymmM12);
				$("#YYYYMM_M11").val(data.RESULT.resultYesterday[0].yyyymmM11);
				$("#YYYYMM_M10").val(data.RESULT.resultYesterday[0].yyyymmM10);
				$("#YYYYMM_M09").val(data.RESULT.resultYesterday[0].yyyymmM09);
				$("#YYYYMM_M08").val(data.RESULT.resultYesterday[0].yyyymmM08);
				$("#YYYYMM_M07").val(data.RESULT.resultYesterday[0].yyyymmM07);
				$("#YYYYMM_M06").val(data.RESULT.resultYesterday[0].yyyymmM06);
				$("#YYYYMM_M05").val(data.RESULT.resultYesterday[0].yyyymmM05);
				$("#YYYYMM_M04").val(data.RESULT.resultYesterday[0].yyyymmM04);
				$("#YYYYMM_M03").val(data.RESULT.resultYesterday[0].yyyymmM03);
				$("#YYYYMM_M02").val(data.RESULT.resultYesterday[0].yyyymmM02);
				$("#YYYYMM_M01").val(data.RESULT.resultYesterday[0].yyyymmM01);				
				
				/* 
		    	fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
		    	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);   // 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
		    	fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback); // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
		    	fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
		    	fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
		    	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람 입장객
		    	fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람 관람객
		    	fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이
		    	*/
		    	
				$("#dispGroup01").append(gvarGP4Contents);
				fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback);  // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
				fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람 관람객
				
				
				$("#dispGroup02").append(gvarGP3Contents);
 			  	fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);    // PIE :: 기준 총 방문객 (yyyymmdd2 -- yyyymmdd)
 			  	fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);   // 관람 방문객
				
				$("#dispGroup03").append(gvarGP1Contents);
				fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
				fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
				
				
				$("#dispGroup04").append(gvarGP2Contents); 
				fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
				fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이		    	
		    	
				
			}	    
	    }	    
	   
	    function fn_numberWithCommas(x) {
	        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    }

	    $(window).resize(function() {
	    	console.log("----");
	    });	    

	    //Refresh...
	    function fn_Refresh(data) {
	   
	 		$("#dispGroup01").empty();
	 		$("#dispGroup02").empty();
	 		$("#dispGroup03").empty();
	 		$("#dispGroup04").empty();
	 		
			$("#dispGroup01").append(gvarGP4Contents);
			fn_ajaxDashboardOnly("./selectStt22", $("#dashboardForm"), fn_stt22_callback);  // PIE :: 기준 총 관람객 (yyyymmdd2 -- yyyymmdd)
			fn_ajaxDashboardOnly("./selectStt52", $("#dashboardForm"), fn_stt52_callback); // 관람 관람객
			
			$("#dispGroup02").append(gvarGP3Contents);
			fn_ajaxDashboardOnly("./selectStt2", $("#dashboardForm"), fn_stt2_callback);    // PIE :: 기준 총 방문객 (yyyymmdd2 -- yyyymmdd)
			fn_ajaxDashboardOnly("./selectStt5", $("#dashboardForm"), fn_stt5_callback);    // 관람 방문객
			
			$("#dispGroup03").append(gvarGP1Contents);
			fn_ajaxDashboardOnly("./selectStt1", $("#dashboardForm"), fn_stt1_callback);   // 현 총 회원수
			fn_ajaxDashboardOnly("./selectStt4", $("#dashboardForm"), fn_stt4_callback);   // 회원 증감 추이
			
			
			$("#dispGroup04").append(gvarGP2Contents); 
			fn_ajaxDashboardOnly("./selectStt3", $("#dashboardForm"), fn_stt3_callback);   // 기준 총 매출   (yyyymmdd2 -- yyyymmdd)
			fn_ajaxDashboardOnly("./selectStt6", $("#dashboardForm"), fn_stt6_callback);   // 매출 추이			
			
	    }
	    
	</script>
	
</body>

</html>