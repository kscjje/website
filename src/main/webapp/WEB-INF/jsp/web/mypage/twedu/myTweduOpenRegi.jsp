<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
 * @Class Name : myTweduOpenRegi.jsp
 * @Description :마을배움터 개설신청(수정)
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2021.12.14   이기태          최초 생성
 *
 *  @author 이기태
 *  @since 2021.12.14
 *  @version 1.0
 *  @see
 *
 */
%>
<head>
<style type="text/css">
.date_line label {position:relative;cursor:pointer;font-style:normal;width:100%;height:55px;text-align:center;line-height:54px;font-size:18px;background:#fff;box-sizing:border-box;margin-left:-1px;}
.date_line label:after {content:'';display:block;position:absolute;left:0;top:50%;width:1px;height:20px;margin-top:-10px;background:#e6e6e6;z-index:1;}
.date_line label:first-child:after {display:none;}
.date_line label.on {background:#004e9e;color:#fff;border-radius:10px;}
.date_line label.on:after {display:none;}
#edu-plan-list .datepicker{height:0px;border:0px}
</style>

<script type="text/javascript" src="<c:url value='/web/resources/js/twedu.js'/>"></script>
<script type="text/javascript">
var NAME_OF_WEEK = ["일","월","화","수","목","금","토"];
var CATE_LIST;
var LAST_DATE = '';
var ADD_DATE_CNT = 0;

var fn_getPlanDateText = function(ymd) {

	if(ymd.length == 10){
		ymd = ymd.substr(0,4) + ymd.substr(5,2) + ymd.substr(8,2);
	}
	var dt = new Date(Number(ymd.substr(0,4)), Number(ymd.substr(4,2)) -1, Number(ymd.substr(6,2)));
	var week = NAME_OF_WEEK[dt.getDay()];

	return ymd.replace(/(\d{4})(\d{2})(\d{2})/,"$1.$2.$3("+week+")");
};

var fn_getPlanDateYMD = function( ){
	var flag = true;
	var weekSelected = "";

	$.each($('input[name=edcDays]:checked') , function(index,item){
		weekSelected += $(this).attr("value");
	});

	while(flag){
		var dateFormat = new Date(Number(LAST_DATE.substr(0,4)), Number(LAST_DATE.substr(5,2)) -1, Number(LAST_DATE.substr(8,2)));
		dateFormat.setDate(dateFormat.getDate() + ADD_DATE_CNT);

		var year = dateFormat.getFullYear();
	    var month = ("0" + (1 + dateFormat.getMonth())).slice(-2);
	    var day = ("0" + dateFormat.getDate()).slice(-2);

	    //최종 날짜
	    LAST_DATE =  year + "-" + month + "-" + day;

	    ADD_DATE_CNT = 1;
		var week = dateFormat.getDay();
		if(week == 0) week = 7; //일요일인 경우 예외 처리
		//요일 체크
		if(weekSelected == "" || weekSelected.indexOf(week) >= 0){
			flag = false;
		}

	}


    return LAST_DATE;
}

var fn_addPlans = function(newCnt, oldCnt) {
	var $body = jQuery("#edu-plan-list");
	var end = newCnt + 1;
	var start = oldCnt + 1;

	//최초 시작 년월일
	var dateCnt = $('input[name=lectDate]').length;
	if(dateCnt < 1){
		LAST_DATE = $('#edcSdate').val();
		ADD_DATE_CNT = 0;
	}else{
		LAST_DATE = $('input[name=lectDate]').eq(dateCnt -1).val();
		ADD_DATE_CNT = 1;
	}

	var trs = [];
	for (var i = start; i < end; i++) {
		var lectDate = "";
		var lectDateTxt = "날짜를 선택하세요.";

		if($('#edcSdate').val() != ""){
			lectDate = fn_getPlanDateYMD(); // 시작일부터 요일에 맞는 날짜를 가져온다
			lectDateTxt = fn_getPlanDateText(lectDate);
		}

		trs.push(fn_dom(
			{ tagName : "tr", childs : [
				{ tagName : "td", attrs : { innerHTML : i } },
				{ tagName : "td", childs : [
					{ tagName : "p", attrs : { className:"plan-date-txt", innerHTML : lectDateTxt } },
					{ tagName : "div", attrs : { className : "btn_area small" }, childs : [
							{ tagName : "button", attrs : { type:"button" , className : "btn_s2_c1",  innerHTML : "날짜변경", rowIdx: i-1 } }
						]
					},
					{ tagName: "input", attrs: { type:"text", name: "lectDate" , className : "datepicker", value: lectDate , title:"수업계획날짜", required: true } }
				]},
				{ tagName : "td", childs : [ { tagName : "textarea", attrs : { name : "lectTitle", title:"수업계획제목", maxlength: 100, required: true }, styles : { minHeight : "100px" } } ]},
				{ tagName : "td", childs : [ { tagName : "textarea", attrs : { name : "lectContents", title:"수업계획내용", maxlength: 500, required: true }, styles : { minHeight : "100px" } } ]},
				{ tagName : "td", childs : [ { tagName : "textarea", attrs : { name : "lectEtc" , title : "준비물/유의사항" , maxlength : 500 }, styles : { minHeight : "100px" } } ]}
			]}
		));
	}

	$body.append(trs);
	jQuery(".datepicker").datepicker({
		dateFormat : 'yy-mm-dd',
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
	});

	$('#edu-plan-list').find("tr td button.btn_s2_c1").click(function(){
		//alert($(this).parent().parent().find(".datepicker").val());
		$(this).parent().parent().find(".datepicker").focus();
	});

	$('input[name=lectDate]').change(function(){
		var ymd = $(this).val().replace(/-/g,"");
		$(this).parent().find(".plan-date-txt").text(fn_getPlanDateText(ymd));
	});
};

var fn_delPlans = function(newCnt, oldCnt) {
	var $body = jQuery("#edu-plan-list");

	var loopCnt = oldCnt - newCnt;
	for (var i = 0; i < loopCnt; i++) {
		$body.find("tr:last-child").remove();
	}

	if (!jQuery("#edu-plan-list tr").length) {
		$body.append(fn_dom({ tagName : "tr", attrs : { className : "no-data-row" },
			childs : [ { tagName : "td", attrs : { colSpan : "5", innerHTML : "계획서가 비어있습니다." } } ]
		}));
	}
};

jQuery.fn.validateForm = function() {
	var chk = true;

	$(this).find("input:required, select:required, textarea:required, input[name='lectDate']").each(function(idx, elem){
		var val = elem.value;

		if($(this).hasClass("numberchk")) {
			if(val < 1) {
				alert("["+this.title+"] 항목은 0보다 커야 합니다.");
				chk = false;
				return false
			}
		} else {
			if(!val) {
				alert("["+this.title+"] 항목이 비어있습니다.");
				chk = false;
				return false;
			}
		}
	});

	if($('#edcLimitAgeyn').val() == "Y" && $('#edcTargetSage').val() == ""){
		alert("교육연령을 입력해 주세요.");
		$('#edcTargetSage').val().focus();
		return false;
	}
	if( isNaN($('#edcTargetSage').val())){
		alert("교육연령은 숫자만 입력해 주세요.");
		$('#edcTargetSage').val().focus();
		return false;
	}

	if($('#edcLimitAgeyn').val() == "Y" && $('#edcTargetEage').val() == ""){
		alert("교육연령을 입력해 주세요.");
		$('#edcTargetEage').val().focus();
		return false;
	}
	if( isNaN($('#edcTargetEage').val())){
		alert("교육연령은 숫자만 입력해 주세요.");
		$('#edcTargetEage').val().focus();
		return false;
	}

	if(!chk) return chk;

	if(jQuery("input[name='edcDays']:checked").length < 1) {
		alert("선택된 교육 요일이 없습니다.")
		chk = false;
	}

	return chk;
}

var fn_getToday = function() {
	var today = new Date();

	var yyyy = today.getFullYear();
	var mm = today.getMonth()+1;
	if(mm < 10) mm = "0" + mm;
	var dd = today.getDate();
	if(dd < 10) dd = "0" + dd;

	return yyyy+""+mm+""+dd;
};


var fn_setOpenDtail = function(data) {
	var $form = jQuery("#tweduVO");
	/* 기본정보 세팅 */
	$form.find("input[type='text'],input[type='hidden'],select,textarea").each(function(idx, elem){
		var key = elem.name;
		var val = data[key];

		if(val) elem.value = data[key];
	});

	/* 신청연령제한 */
	$form.find("#edcLimitAgeyn").trigger("change");
	if(data["edcLimitAgeyn"] === "Y") {
		var edcAgeList = data["edcAgeList"] && data["edcAgeList"][0];
		if(edcAgeList) {
			$form.find(".target-age-info input").each(function(idx, elem){
				var key = elem.name;
				var val = data[key];

				if(val !== undefined) elem.value = val;
			});
		}
	}

	/* 교육요일 */
	var edcDaysList = data["edcDaysList"];
	var edcDayMap = {};
	for(var i=0; i<edcDaysList.length ;i ++) {
		var dayGbn = edcDaysList[i].edcDaygbn;
		edcDayMap[dayGbn] = edcDaysList[i];
	}
	if(edcDaysList && edcDaysList.length) {

		$form.find(".edc-days-info input[type='checkbox']").each(function(idx, elem){
			var dayGbn = elem.value;
			if(edcDayMap[dayGbn].dayChk == dayGbn) {
				elem.checked = true;
				jQuery(elem).prev().addClass("on");
			} else {
				elem.checked = false;
				jQuery(elem).prev().removeClass("on");
			}
		});
	}

	/* 학습계획 */
	jQuery("#edcClcnt").trigger("change");
	var edcPlanList = data["edcPlanList"];
	if(edcPlanList && edcPlanList.length) {
		$form.find("#edu-plan-list tr").each(function(idx, elem){
			var $tr = jQuery(this);
			var rowData = edcPlanList[idx];
			$tr.find(".plan-date-txt").text(fn_getPlanDateText(rowData["lectDate"]));
			$tr.find("input,textarea").each(function(jdx,elem2){
				var key = elem2.name;
				var val = rowData[key] || "";

				if(val !== undefined) elem2.value = val;
			});
		});
	}
};

var fn_register = function(val){

	var url = "./openRegister.json";
	if($('#edcPrgmid').val() != "" && $('#edcPrgmid').val() != "0"){
		url = "./openModify.json";
	}

	$('#edcPrg').val(val);
	jQuery("#tweduVO").ajaxSubmit({
		method: "POST",
		enctype: 'multipart/form-data',
		url: url,
		success: function(json) {
			if(json.result.code == "SUCCESS") {
				if(val == '1001'){
					alert("제출이 완료되었습니다. 제출내역은 [마이페이지-마을배움터 개설현황]에서 확인하실 수 있습니다.");
				}else{
					alert(json.result.msg);
				}
				location.replace("./openList");
			}else{
				alert(json.result.msg);
			}
		},
		error: function(json) {
			alert("서버에러");
		}
	});
};

jQuery(document).ready(function() {

	jQuery("#edcProgmDate").val(fn_getToday());

	jQuery("input[name=lectDate]").datepicker({
		dateFormat : 'yy-mm-dd',
		showMonthAfterYear: true ,
		yearSuffix: "년" ,
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
	});

	jQuery("input[name=edcSdate] , input[name=edcEdate] ").datepicker({
		showOn : "button",
		dateFormat : 'yy-mm-dd',
		showMonthAfterYear: true ,
		yearSuffix: "년" ,
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
	});

	jQuery(".numberchk").on("change", function() {
		var val = this.value || "0";
		var cnt = Number(val.replace(/[^\d]/g, ""));

		this.value = cnt;
	});

	jQuery("#edcClcnt").on("change", function() {
		var newCnt = Number(this.value);

		jQuery("#edu-plan-list .no-data-row").remove();

		var oldCnt = jQuery("#edu-plan-list tr").length;

		if (newCnt > oldCnt) {
			/* ADD ROWS TO THE LAST */
			fn_addPlans(newCnt, oldCnt);
		} else if (newCnt < oldCnt) {
			/* DELETE ROWS FROM THE LAST */
			fn_delPlans(newCnt, oldCnt);
		}
	});

	$('#edu-plan-list').find("tr td button.btn_s2_c1").click(function(){
		//alert($(this).parent().parent().find(".datepicker").val());
		$(this).parent().parent().find(".datepicker").focus();
	});

	$('input[name=lectDate]').change(function(){
		var ymd = $(this).val().replace(/-/g,"");
		$(this).parent().find(".plan-date-txt").text(fn_getPlanDateText(ymd));
	});

	jQuery(".datepicker").on("click", function() {
		$(this).next().click();
	});

	jQuery(".date_line label").on("click", function(){
		$(this).toggleClass("on");
	});


	//임시저장
	jQuery("#temp_save_btn").on("click", function(){
		if(jQuery("#tweduVO").validateForm()){
			fn_register('0000');
		}
	});

	//신청서 제출
	jQuery("#save_btn").on("click", function(){
		if(jQuery("#tweduVO").validateForm()){
			if(confirm("마을배움터 신청서를 제출하시겠습니까?\n(취소 클릭 시 임시저장)")) {
				fn_register('1001');
			}else{
				fn_register('0000');
			}
		}
	});

	//신청서수정
	jQuery("#modify_btn").on("click", function(){
		if(jQuery("#tweduVO").validateForm()){
			fn_register('');
		}
	});

	//연령제한 여부
	jQuery("#edcLimitAgeyn").on("change", function(){
		if(this.value === "Y") {
			jQuery(".target-age-input").prop("readonly", false).prop("required", true);
		} else {
			jQuery(".target-age-input").prop("readonly", true).prop("required", false).val("");
		}
	});

	if($('#edcLimitAgeyn').val() == "Y"){
		jQuery(".target-age-input").prop("readonly", false).prop("required", true);
	}


	jQuery("#confrim_plan_ymd").on("click", function(){
		var rowIdx = jQuery("#plan_row").val();
		var ymd = jQuery("#plan_ymd").val().replace(/-/g,"");

		var $tr = jQuery("#edu-plan-list tr:eq("+rowIdx+")");

		$tr.find(".plan-date-txt").text(fn_getPlanDateText(ymd));
		$tr.find("input[name='lectDate']").val(ymd);
	});

	jQuery("#open-hist-btn").on("click", function(){
		fn_loadOpenHistory();
	});

	jQuery("#refresh_btn").on("click", function(){
		if(confirm("변경사항을 초기화 하시겠습니까?")) {
			location.reload();
		}
	});

	jQuery(".plan-date-txt").each(function(idx, elem){
		var ymd = elem.getAttribute("plan-date");
		this.innerHTML = fn_getPlanDateText(ymd);
	});

	//강좌유형 유형 가져오기
	$.ajax({
	      url: '<c:url value="./cateList.json"/>',
	      type : "GET",
	      cache : false,
	      async : false,
	      success: function(data) {
	    	  //목록을 담는다
	    	  CATE_LIST = data.cateList;
	      },
	      error : function(e){
	    	 // alert("기관유형 가져오기 오류\n" + e.responseText);
	      }
	});

	//$('#comCtgcd').html('');

	for(var i = 0 ; i<CATE_LIST.length ; i++){
		var item = CATE_LIST[i];
		var selected = "";

		if("${tweduVO.comTopctgcd}" == item.comCtgcd){
			selected = " selected";
		}else if(item.comCtgnm.indexOf("기타")>=0) {
			selected = " selected";

		}

		if(item.comCtgnm.indexOf("기타")>=0){
			$('#parentCtg').append("<option value='"+item.comCtgcd+"'"+selected+">"+item.comCtgnm+"</option>");
		}
	};

	fnSetCtgList($('#parentCtg').val() , "${tweduVO.comCtgcd}");

	$('#parentCtg').change(function(){
		fnSetCtgList($('#parentCtg').val() , "${tweduVO.comCtgcd}");
	});

});

function fnSetCtgList(parentCd , defaultVal){
	var cnt = 0;
	$('#comCtgcd').html("");
	$('#comCtgcd').append("<option value=''>::강좌분야선택::</option>");

	for(var i = 0 ; i<CATE_LIST.length ; i++){
		var item = CATE_LIST[i];
		if(item.comCtgcd == parentCd){
			var subList = item.subCtgrList;
			for(var j = 0 ; j<subList.length ; j++){
				var subItem = subList[j];

				if(subItem.comCtgcd != null){
					var selected = "";
					if(subItem.comCtgcd == defaultVal) selected = " selected";

					$('#comCtgcd').append("<option value='"+subItem.comCtgcd+"'"+selected+">"+subItem.comCtgnm+"</option>");
					cnt++;
				}

			}
		}
	}
}
</script>
</head>
<body>
	<div class="sub_visual mypage">
		<div class="inner">
			<h2>
				<c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" />
			</h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>
	<!--//sub_visual  -->
		<div id="content" class="sub_content">
			<div class="sub_top type02">
				<c:choose>
					<c:when test="${tweduVO.edcPrgmid > 0}">
						<h3>마을배움터 개설상세/수정</h3>
					</c:when>
					<c:otherwise>
						<h3>마을배움터 개설신청</h3>
					</c:otherwise>
				</c:choose>
			</div>
		<form:form commandName="tweduVO" name="tweduVO" action="./register" method="POST" enctype="multipart/form-data">
			<form:hidden path="edcPrgmid" />
			<form:hidden path="itemCd" />
			<form:hidden path="orgNo" />
			<form:hidden path="edcPrg" />
			<form:hidden path="edcFeeType" />
			<form:hidden path="salamt" />
			<form:hidden path="edcRsvnsetSeq" />
		 	<form:hidden path="edcRsvnSdate" />
		 	<form:hidden path="edcRsvnStimeHour"/>
			<form:hidden path="edcRsvnStimeMin"/>
			<form:hidden path="edcRsvnEdate" />
			<form:hidden path="edcRsvnEtimeHour" />
			<form:hidden path="edcRsvnEtimeMin"/>
			<form:hidden path="edcLimitAgeyn" value="Y" />

			<input type="hidden" id="edcProgmDate" name="edcProgmDate" value=""/>
			<!-- 강사신청 table 클래스 : t_table 추가 -->
			<div class="table t_table">
				<div class="title_box">
					<p class="t_title">01. 모집 정보</p>
					<span><em class="star">*</em>필수값으로, 모두 기재하세요. </span>
				</div>
				<table>
					<caption>모집정보 등록 표입니다. -강좌제목명, 대표사진, 연령제한, 교육연령, 모집대상 표기,
						접수방식 , 정원(수), 온라인 현장정원수</caption>
					<colgroup>
						<col style="width: 14.5%">
						<col style="width: 85.5%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span>강좌제목명<em class="star m_ab">*</em></span></th>
							<td>
								<div class="inputbox">
									<label for="inp01" class="hidden">강좌제목명</label>
									<form:input path="edcPrgmnm" title="강좌제목명" placeholder="한글기준 최대 30자 가능합니다." required="true" maxlength="30"/>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">강좌이미지</th>
							<td>
								<div class="w_box b_area">
									<div class="inputbox">
										<form:hidden path="edcImgFileid" />
										<input type="file" id="file" name="file_1" class="file">
										<label for="edcImgOrigin" class="hidden"></label>
										<input type="text" name="edcImgOrigin" id="edcImgOrigin" title="강좌이미지" placeholder="사이즈 크기는 492*619픽셀을 기준합니다." class="upload-name" readonly="true" />
									</div>

									<button type="button" class="btn_s3_c2 btn_file">첨부</button>
								</div>
								<c:if test="${!empty tweduVO.edcImgOrigin }">
									<div style="margin-top:10px">
										※ 현재 파일 : <a href="<tags:UploadFileUrl filePath='${tweduVO.edcImgPath}' fileName='${tweduVO.edcImgFilenm}' originName='${tweduVO.edcImgOrigin}' />" class="down_txt"><c:out value="${tweduVO.edcImgOrigin}"/></a> <a href="<tags:UploadFileUrl filePath='${tweduVO.edcImgPath}' fileName='${tweduVO.edcImgFilenm}' originName='${tweduVO.edcImgOrigin}' />" class="btn_down">다운로드</a>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>교육대상<em class="star">*</em></span></th>
							<td>
								<div class="w_box dblock">
									<label for="edcTargetAgegbn" class="hidden">교육대상</label>
									<form:select path="edcTargetAgegbn" required="true">
			                    	 	<option value="0">::::선택::::</option>
										<c:forEach items="${targetType}" var="item" varStatus="status">
				                    	 	<form:option value="${item.cd }" label="${item.cdNm }" />
										</c:forEach>
			                    	 </form:select>
			                   </div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>교육연령 제한 <em class="star">*</em></span></th>
							<td>
								<div class="w_box st3 taget-age-info">
									<div class="inputbox">
										<label for="edcTargetSage" class="hidden"></label>
										<form:input path="edcTargetSage" title="교육연령(시작)" placeholder="~세 부터" maxlength="2"  class="target-age-input" />

									</div>
									<span>~</span>
									<div class="inputbox">
										<label for="edcTargetEage" class="hidden"></label>
										<form:input path="edcTargetEage" title="교육연령(종료)" placeholder="~세 까지" maxlength="2"  class="target-age-input" />
									</div>
									<!-- <button class="btn_s3_c2">추가</button> -->
								</div>
								<p class="ex_p">※ 한국나이 기준</p>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>강의지역<em class="star">*</em></span></th>
							<td>
								<div class="w_box dblock">
									<form:select path="areaCd" class="form-control" title="강의지역" required="true">
										<option value="">선택하세요</option>
										<c:forEach items="${areaList}" var="item" varStatus="status">
											<form:option value="${item.areaCd }" label="${item.areaName }" />
										</c:forEach>
									</form:select>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>정원(수)<em class="star">*</em></span></th>
							<td>
								<div class="inputbox max100">
									<label for="edcPncpa" class="hidden">정원(수)</label>
									<form:input path="edcPncpa" title="정원(수)" placeholder="" cssClass="numberchk" required="true" maxlength="5"/>
								</div>
								<p class="ex_p">(온라인으로만 접수가능, 현장접수 불가)</p>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>문의/연락처<em class="star m_ab">*</em></span></th>
							<td>
								<div class="inputbox">
									<label for="edcGuideTelno" class="hidden">문의/연락처</label>
									<form:input path="edcGuideTelno" title="문의/연락처" placeholder="" required="true" maxlength="100"/>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--//table  -->
			<!-- 강사신청 table 클래스 : t_table 추가 -->
			<div class="table t_table t_flex">
				<div class="title_box">
					<p class="t_title">02.강좌정보</p>
				</div>
				<table>
					<caption>강좌정보 표입니다. -실제주소, 강좌분야,강좌소개, 재료비/유의사항, ,강의계획서,
						교육기간, 교육요일, 교육시간, 강좌횟수, 수료증 사용여부</caption>
					<colgroup>
						<col style="width: 14.5%">
						<col style="width: 35.5%">
						<col style="width: 14.5%">
						<col style="width: 35.5%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span>실제주소<em class="star">*</em></span></th>
							<td colspan="3">
								<div class="inputbox w100">
									<label for="edcPlacenm" class="hidden"></label>
									<form:input path="edcPlacenm" title="실제주소 입력" placeholder="구민들이 찾아가기 위한 실제 주소를 기입하세요" required="true" maxlength="200"/>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>강좌분야<em class="star">*</em></span></th>
							<td colspan="3" class="td_column">
								<div class="w_box dblock">
									<select name="parentCtg" id="parentCtg">
									</select>
									<form:select path="comCtgcd" title="강좌분야" required="true">
									</form:select>
								</div>
								<p class="ex_p">※ 마을배움터의 강좌는 통합검색에서 ‘기타’로 우선 분류됩니다.</p>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop"><span>강좌소개<em class="star">*</em></span></th>
							<td colspan="3">
								<form:textarea path="edcPrgmintrcn" title="강좌소개" placeholder="" required="true" maxlength="5000"></form:textarea>
							</td>
						</tr>
						<tr>
							<th scope="row">
								재료비/ <br class="br_m"> 유의사항
							</th>
							<td colspan="3">
								<div class="inputbox w100">
									<label for="inp05" class="hidden"></label>
									<form:input path="edcTchmtrGuide" title="재료비/유의사항" placeholder="" maxlength="1000" />
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">강의계획서</th>
							<td colspan="3">
								<div class="w_box b_area">
									<div class="inputbox w100">
										<form:hidden path="edcPlanFileid" />
										<input type="file" name="file_3" id="file02" class="file02">
										<label for="edcPlanOrigin" class="hidden"></label>
										<input type="text" name="edcPlanOrigin" id="edcPlanOrigin" title="강의계획서 입력" placeholder="강의계획서가 있을 경우 넣으세요" cssClass="upload-name02" readonly="true" />
									</div>
									<button type="button" class="btn_s3_c2 btn_file02">첨부</button>
								</div>
<c:if test="${!empty tweduVO.edcPlanPath }">
								<div style="margin-top:10px">
									※ 현재 파일 : <a href="<tags:UploadFileUrl filePath='${tweduVO.edcPlanPath}' fileName='${tweduVO.edcPlanFilenm}' originName='${tweduVO.edcPlanOrigin}' />" class="down_txt"><c:out value="${tweduVO.edcPlanOrigin}"/></a> <a href="<tags:UploadFileUrl filePath='${tweduVO.edcPlanPath}' fileName='${tweduVO.edcPlanFilenm}' originName='${tweduVO.edcPlanOrigin}' />" class="btn_down">다운로드</a>
								</div>
</c:if>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>교육기간<em class="star">*</em></span></th>
							<td colspan="3">
								<div class="w_box date">
									<div class="inputbox date">
										<label for="edcSdate" class="hidden"></label>
										<form:input path="edcSdate" title="교육기간(시작)" cssClass="datepicker" autocomplete="off" required="true" maxlength="10"/>
									</div>
									<span>~</span>
									<div class="inputbox date">
										<label for="edcEdate" class="hidden"></label>
										<form:input path="edcEdate" title="교육기간(종료)" cssClass="datepicker" autocomplete="off" required="true" maxlength="10"/>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>교육 요일<em class="star">*</em></span></th>
							<td colspan="3">
								<div class="date_line edc-days-info">
									<c:forEach items="${tweduVO.edcDaysList}" var="item2"
										varStatus="status">
										<label for="edcDays${status.index}" style="cursor:pointer" <c:if test="${item2.dayChk eq item2.edcDaygbn}">class="on"</c:if>>${item2.edcDaygbnNm}</label>
										<input type="checkbox" style="display:none" id="edcDays${status.index}" name="edcDays" value="${item2.edcDaygbn}" <c:if test="${item2.dayChk eq item2.edcDaygbn}">checked</c:if> />
										<span></span>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>교육 시간<em class="star">*</em></span></th>
							<td colspan="3">
								<!-- 교육시간 select 4개 일때 클래스 : time 추가 -->
								<div class="w_box time">
									<div>
										<form:select path="edcStimeHour" required="true">
											<c:forEach var="i" begin="0" end="23">
												<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>
											</c:forEach>
										</form:select>
										<form:select path="edcStimeMin" required="true">
											<c:forEach var="i" begin="0" end="50" step="10">
												<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>
											</c:forEach>
										</form:select>
									</div>
									<span>~</span>
									<div>
										<form:select path="edcEtimeHour" required="true">
											<c:forEach var="i" begin="0" end="23">
												<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>
											</c:forEach>
										</form:select>
										<form:select path="edcEtimeMin" required="true">
											<c:forEach var="i" begin="0" end="50" step="10">
												<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>강좌 횟수<em class="star">*</em></span></th>
							<td>
								<div class="w_box ">
									<div class="inputbox max100">
										<label for="inp15" class="hidden"></label>
										<form:input path="edcClcnt" title="강좌횟수" placeholder="" cssClass="numberchk" required="true" maxlength="5"/>
									</div>
									<span class="txt01">회</span>
								</div>
							</td>
							<th scope="row"><span>수료증 사용여부<em class="star">*</em></span></th>
							<td>
								<div class="w_box dblock">
									<form:select path="edcCtfhvyn" required="true">
										<option value="Y">수료증 사용</option>
										<option value="N">수료증 미사용</option>
									</form:select>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="table t_table">
				<div class="title_box">
					<p class="t_title">03.강사정보</p>
				</div>
				<table>
					<caption>강사정보 표입니다. -강사명, 강사연락처, 계좌정보, 계좌 예금주명</caption>
					<colgroup>
						<col style="width: 14.5%">
						<col style="width: 35.5%">
						<col style="width: 14.5%">
						<col style="width: 35.5%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span>강사명<em class="star">*</em></span></th>
							<td colspan="3">
								<form:hidden path="instrctrNo" />
								<div class="inputbox">
									<label for="instrctrName" class="hidden"></label>
									<form:input path="instrctrName" title="강사명 입력" placeholder="" required="true" maxlength="100"/>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>계좌 은행명<em class="star">*</em></span></th>
							<td>
								<div class="w_box">
									<div class="inputbox">
										<label for="edcFeeBnkNm" class="hidden"></label>
										<form:input path="edcFeeBnkNm" title="은행명" placeholder="은행명을 입력해주세요" required="true" maxlength="20"/>
									</div>
									<span class="txt01">&nbsp;</span>
								</div>

							</td>
							<th scope="row"><span>계좌번호<em class="star">*</em></span></th>
							<td>
									<div class="inputbox">
										<label for="edcFeeAccno" class="hidden"></label>
										<form:input path="edcFeeAccno" title="계좌번호" placeholder="계좌번호 숫자입력 (간편계좌번호 기재안됨)" required="true" maxlength="100"/>
									</div>
							</td>
						</tr>
						<tr>
							<th scope="row"><span>계좌 예금주명<em class="star">*</em></span></th>
							<td colspan="3">
								<div class="inputbox">
									<label for="edcFeeAccNm" class="hidden"></label>
									<form:input path="edcFeeAccNm" title="예금주명" placeholder="" required="true" maxlength="50"/>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--// table  -->
			<div class="table t_center">
				<div class="title_box">
					<p class="t_title">04.학습 계획서</p>
				</div>
				<table>
					<caption></caption>
					<colgroup>
						<col style="width: 5.5%">
						<col style="width: 16.5%">
						<col style="width: 14.5%">
						<col style="width: 47.5%">
						<col style="width: 16%">
					</colgroup>
					<thead>
						<tr>
							<th>회차</th>
							<th>날짜/시간</th>
							<th>주제</th>
							<th>내용</th>
							<th>준비물/유의사항</th>
						</tr>
					</thead>
					<tbody id="edu-plan-list">
					<c:choose>
						<c:when test="${empty tweduVO.edcPlanList}">
							<tr class="no-data-row">
								<td colSpan="5">계획서가 비어있습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
						<c:forEach items="${tweduVO.edcPlanList}" var="item" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>
									<p class="plan-date-txt" plan-date="${item.edcDate}"></p>
									<div class="btn_area small"><button type="button" class="btn_s2_c1">날짜변경</button></div>
									<input type="text" name="lectDate" value="${item.edcDate}" class="datepicker" title="수업계획날짜">
								</td>
								<td>
									<textarea name="lectTitle" title="수업계획제목" required="" style="min-height: 100px;"><c:out value="${item.edcTitle}"/></textarea>
								</td>
								<td>
									<textarea name="lectContents" title="수업계획내용" required="" style="min-height: 100px;"><c:out value="${item.edcCnts}"/></textarea>
								</td>
								<td>
									<textarea name="lectEtc" title="준비물/유의사항" style="min-height: 100px;"><c:out value="${item.edcEtccnts}"/></textarea>
								</td>
							</tr>
						</c:forEach>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
			</div>
			</form:form>
			<c:choose>
				<c:when test="${tweduVO.edcPrgmid > 0}">
					<div class="btn_area type02">
						<div>
							<a href="javascript:history.back()" class="btn_s1_c0">목록</a>
						</div>
						<div>
							<c:choose>
								<c:when test="${tweduVO.edcPrg eq '0000' }">
									<a href="javascript:;" class="btn_s1_c4" id="modify_btn">임시저장</a>
									<a href="javascript:;" class="btn_s1_c1" id="save_btn">신청서제출</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:;" class="btn_s1_c1" id="modify_btn">신청서수정</a>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="btn_area type02">
						<div>
							<a href="javascript:history.back()" class="btn_s1_c0">취소</a>
							<a href="javascript:;" class="btn_s1_c2" id="refresh_btn">초기화</a>
						</div>
						<div>
							<a href="javascript:;" class="btn_s1_c4" id="temp_save_btn">임시저장</a>
							<a href="javascript:;" class="btn_s1_c1" id="save_btn">신청서제출</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>

			<!--// btn_area  -->
		</div>
		<!--// sub_content  -->
	<div class="modal" id="modal2">

		<div class="modal-dialog s2 ">
			<div class="modal-content">
				<div class="modal-title">
					<h4>날짜변경</h4>
					<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div class="modal-body">
					<div class="modal-txt">
						<div class="w_box date">
							<div class="inputbox date">
								<label for="plan_ymd" class="hidden"></label>
								<input type="hidden" id="plan_row" value=""/>
								<input type="text" id="plan_ymd" class="datepicker" place-holder="YYYY-MM-DD" autocomplete="off" />
							</div>
						</div>

					</div>
					<ul class="btn_area">
						<li><a href="#" class="btn0_c2 modal2_close">취소</a></li>
						<li><a href="#" class="btn_s1_c1 modal2_close" id="confrim_plan_ymd">확인</a></li>
					</ul>
					<!-- //btn_area -->
				</div>
			</div>
		</div>
	</div>
	<!-- //팝업 -->
	<%@ include file="popMyTweduOpenHist.jsp" %>
</body>
