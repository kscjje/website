<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduAttend.jsp
  * @Description :마을배움터 출석부
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.12.21   이기태          최초 생성
  *
  *  @author 이기태
  *  @since 2021.12.21
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script type="text/javascript" src="<c:url value='/web/resources/js/twedu.js'/>"></script>
<script type="text/javascript">

var fn_loadAttendances = function(edc_prgmid){
	var queryStr = "?edcPrgmid="+edc_prgmid;

	jQuery.ajax({
		url : "./lectAttendList"+JSON_REQ_SUFFIX+queryStr,
		type: "GET",
		cache : false,
		success: function(json) {
			if (json.result.code == "ERROR") {
				alert(json.result.msg);
			} else {
				for(var i=0;i<json.data.length;i++){
					var attData = json.data[i];

					$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).find("span").removeClass("pick");

					if(attData.edcAtendGbn == "1001"){
						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).find("span").addClass('miss').text("결석");

						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).find("span").removeClass("attend-updatable").css("cursor" , "default");

					}else if(attData.edcAtendGbn == "2001"){
						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).find("span").addClass("att").text("출석");

						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).find("span").removeClass("attend-updatable").css("cursor" , "default");
					}else{

					}
				}
			}

			$.each($('.attendCnt') , function(index,item){
				var tr = $(this).parent().parent().parent();
				var totalCnt = tr.find("td span.att").length;

				$(this).text(totalCnt);
			});

			$('.attend-updatable').click(function(){
				var gbn = $(this).data('edcAtendGbn');
				var old_gbn = gbn;
				var new_gbn = "";

				switch(gbn) {
					case "2001": new_gbn = "1001";	break;
					case "1001": new_gbn = "";		break;
					default : new_gbn = "2001";
				}

				$(this).data('edcAtendGbn' , new_gbn);

				var $this = jQuery(this);
				$this.removeClass("pass").removeClass("miss").removeClass("pick");

				switch(new_gbn) {
					case "2001": $this.addClass("pass").text("출석");		break;
					case "1001": $this.addClass("miss").text("결석");		break;
					default : $this.addClass("pick").text("선택");
				}

				if($(this).hasClass("attend-chagned") == false){
					$(this).addClass("attend-chagned");
				}

			});
		},
		error:function(data) {
			alert("server error");
		}
	});
};

var fn_saveAttendances = function(edc_prgmid, data) {
	jQuery.ajax({
		url : "./lect/"+edc_prgmid+"/attendance"+JSON_REQ_SUFFIX,
		type: "POST",
		contentType:"application/json",
		data: JSON.stringify(data),
		success: function(json) {
			alert(json.result.msg);

			if (json.result.code == "SUCCESS") {
				fn_loadAttendances(jQuery("#edcPrgmid").val());
			}
		},
		error:function(data) {
			alert("server error");
		}
	});
};

jQuery(document).ready(function(){

	jQuery("#save-attend").on("click", function(){
		var edc_prgm_id = jQuery("#edcPrgmid").val();
		var postData = [];

		jQuery(".attend-chagned").each(function(idx, elem){

			var _attd_data_ = { edcRsvnReqid: $(this).data("reqid"), edcAtendDate:$(this).data("ymd"), edcAtendGbn: $(this).data("edcAtendGbn") || "" };

			postData.push(_attd_data_);
		});
		if(postData.length) {
			fn_saveAttendances(edc_prgm_id, postData);
		} else {
			alert("변경된 데이터가 없습니다.");
		}
	});

	$.each($('.totalCnt') , function(index,item){
		var tr = $(this).parent().parent().parent();
		var totalCnt = tr.find("td span.attend-updatable").length;

		$(this).text(totalCnt);
	});

	var leftPosition = 0;

	$.each($('.right_tbl').find("table thead tr th") ,function(index,item){
		if($(this).data("passyn") == "Y"){
			//스크롤 이동
			leftPosition += $(this).width()+8;
		};
	});


	if(leftPosition > 0){
		leftPosition = leftPosition + $('.right_tbl').find("table thead tr th").eq(0).width() + $('.right_tbl').find("table thead tr th").eq(1).width()  ;

		//leftPosition = leftPosition - $('.right_tbl').width();
	}
	if(leftPosition > $('.right_tbl').width()){
		leftPosition = leftPosition - $('.right_tbl').width();
	}else{
		leftPosition = 0;
	}


	$('.right_tbl').scrollLeft(leftPosition);



	fn_loadAttendances(jQuery("#edcPrgmid").val());
});

</script>
</head>
<body>
	<div class="sub_visual mypage">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>
	<div id="content" class="sub_content">
<input type="hidden" id="edcPrgmid" name="edcPrgmid" value="${edcPrgmid}" />
<input type="hidden" id="edcSdate" name="edcSdate" value="${edcSdate}" />
<input type="hidden" id="edcEdate" name="edcEdate" value="${edcEdate}" />
		<!--// tab_link  -->
		<div class="sub_top type02">
			<a href="javascript:history.back()">뒤로가기 버튼</a>
			<h3>마을배움터 출석부</h3>
		</div>
		<!--//top -->
		<div class="at_table">
					<div class="title_box">
						<p class="t_title">* 모바일화면에서 날짜를 가로로 드레그하여 볼 수 있습니다.</p>
						 </div>
				<div class="table left_tbl">
					<table>
						<caption>마을배움터 출석부 표입니다. -</caption>
						<colgroup>
							<col width="24%">
							<col width="76%">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>이름/연락처</th>
							</tr>
						</thead>
						<tbody>
<c:forEach items="${studentList}" var="item" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${item.memNm } <br/><hisco:HpPrint hp="${item.memHp}"/></td>
							</tr>
</c:forEach>

						</tbody>
					</table>
					</div>
					<div class="table right_tbl">
					<table>
						<caption>마을배움터 출석부 표입니다. -</caption>
							<thead>
								<tr>
									<th>상태</th>
<c:forEach items="${logList}" var="item" varStatus="status">
									<th data-passyn="${item.passYn }">${fn:substring(item.edcDate,5,10)} <br>(${item.weekName })</th>
</c:forEach>
								</tr>
							</thead>
							<tbody>
							<tbody>
<c:forEach items="${studentList}" var="item" varStatus="status">
	<c:set var="edcEdate" value="${item.edcEdate }"/><!-- 중도탈퇴일 경우체크 -->
							<tr>
								<td>${item.edcStat}<div><c:if test="${item.edcStat ne '교육전'}">(<span class="attendCnt">-</span>/<span class="totalCnt">-</span>)</c:if></div></td>
	<c:forEach items="${logList}" var="logItem" varStatus="status2">
		<c:set var="edcToday" value="${fn:replace(logItem.edcDate,'-','')}"/>
								<td id="DTCHK_${item.edcRsvnReqid}_${edcToday}">
									<c:choose>
										<c:when test="${logItem.passYn eq 'Y' and edcEdate >= edcToday}">
											<span class="rollbook pick attend-updatable" data-reqid="${item.edcRsvnReqid }" data-ymd="${edcToday }" style="cursor:pointer">선택</span>
										</c:when>
										<c:otherwise>
											<span class="rollbook empty">빈열</span>
										</c:otherwise>
									</c:choose>
								</td>
	</c:forEach>
							</tr>
</c:forEach>
							</tbody>

					</table>
				</div>

				<c:set var="rowCnt" value="${fn:length(logList)+3 }" />

		</div>
		<!--// table  -->
<c:if test="${fn:length(studentList) < 1}">
					<div style="margin-top:40px;text-align:center;width:100%">등록된 학생이 없습니다.</div>
</c:if>
		<div class="btn_area">
			<a href="javascript:history.back()" class="btn_s1_c0">이전</a>
			<a href="javascript:void(0)" class="btn_s1_c1" id="save-attend">저장</a>
		</div>
		<!--// btn_area  -->
	</div>
</body>