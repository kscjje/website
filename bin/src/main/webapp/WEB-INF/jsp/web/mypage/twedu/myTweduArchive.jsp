<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduArchive.jsp
  * @Description :마을배움터 교육기록
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
<style type="text/css">
.file-preview-link,.file-add-btn { cursor:pointer }
#archive-list .datepicker{height:0px;border:0px}
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

var fn_saveData = function(edc_prgmid) {
	jQuery("#archive_form").ajaxSubmit({
		url: "./lect/"+edc_prgmid+"/edulog"+JSON_REQ_SUFFIX,
		type: "POST",
		dataType: "json",
		success: function(json) {
			alert(json.result.msg);

			if (json.result.code == "SUCCESS") {
				window.location.reload();
	        }
		},
		error: function(){
			alert("서버 에러");
		}
	});
};

jQuery(document).ready(function(){
	//fn_loadData(jQuery("#edcPrgmid").val());

	jQuery("#archive-list").on("click", ".file-preview-link", function(e){
		if($(this).data('imgpath')) {
			jQuery("#img-preview").attr("src", $(this).data('imgpath'));
			jQuery("#modal1").popup("show");
		} else {
			alert("미리보기 이미지가 없습니다.");
		}
	}).on("click", ".file-del-btn", function(e){
 		var $parent = jQuery(this).parent().parent();
		$parent.find(".lectFileid ,input[type='file']").val("");
		$parent.find(".file_list em").text("");
		$parent.find(".file-preview-link").prop("imgPath", "");
		$parent.find(".file_list,.file-del-ui").hide();
		$parent.find(".file-add-ui").show();

	}).on("change","input[type='file']", function(e){
		if(this.value) {
	 		var $parent = jQuery(this).parent().parent();
	 		$parent.find(".file_list em").text(this.files[0].name);
			$parent.find(".file_list,.file-del-ui").show();
			$parent.find(".file-add-ui").hide();

			var reader = new FileReader();
			reader.onload = function(event) {
				$parent.find(".file_list span").data('imgpath' , event.target.result);
			};
			reader.readAsDataURL(event.target.files[0]);
		}
	});

	jQuery(".lectDate").datepicker({
		dateFormat : 'yy-mm-dd',
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
	});

	$('#archive-list').find("tr td button.btn_s2_c1").click(function(){
		//alert($(this).parent().parent().find(".datepicker").val());
		$(this).parent().parent().find(".datepicker").focus();
	});


	$('.lectDate').change(function(){
		var ymd = $(this).val().replace(/-/g,"");
		$(this).parent().find(".plan-date-txt").text(fn_getPlanDateText(ymd));
	});

	jQuery(".plan-date-txt").each(function(idx, elem){
		var ymd = elem.getAttribute("plan-date");
		this.innerHTML = fn_getPlanDateText(ymd);
	});


	jQuery("#save-btn").on("click", function(){
		fn_saveData(jQuery("#edcPrgmid").val());
	});
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
		<div class="sub_top type02">
			<a href="javascript:history.back()">뒤로가기 버튼</a>
			<h3>마을배움터 교육기록</h3>
		</div>
		<!--//top -->
<form id="archive_form" name="archive_form" action="./lectArchiveSave" enctype="multipart/form-data">
<input type="hidden" id="edcPrgmid" name="edcPrgmid" value="${edcPrgmid}" />
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="table t_fix scroll1000">
			<table>
				<caption>마을배움터 교육기록 표입니다. -</caption>
				<colgroup>
					<col width="6%">
					<col width="9%">
					<col width="16.5%">
					<col width="9.5%">
					<col width="*">
					<col width="19.5%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>주제</th>
						<th>출석현황</th>
						<th>학습내용 일정 / 기록</th>
						<th>이미지 첨부</th>
					</tr>
				</thead>
				<tbody id="archive-list">
<c:forEach items="${logList}" var="item" varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td>
							<p class="plan-date-txt" plan-date="${item.edcDate}"></p>
							<c:if test="${item.passCnt < 1 }">
								<!-- 출석체크전에만 바꿀 수 있음 -->
								<div class="btn_area small"><button type="button" class="btn_s2_c1">날짜변경</button></div>
								<input type="text" class="lectDate datepicker" name="lectDate_${status.index}" value="${item.edcDate}"  title="수업계획날짜">
							</c:if>
						</td>
						<td>
							<input type="hidden" name="seq" value="${status.index}" />
							<input type="hidden" class="lectFileid" name="lectFileid_${status.index}" value="${item.lectFileid}" />
							<c:choose>
								<c:when test="${item.passYn eq 'Y' }">
									<textarea name="lectTitle_${status.index}" class="r_textarea" style="overflow: hidden; overflow-wrap: break-word; height: 110px;"><c:out value="${item.edcTitle }"/></textarea>
								</c:when>
								<c:otherwise>
									<textarea name="lectTitle_${status.index}" class="r_textarea" style="overflow: hidden; overflow-wrap: break-word; height: 110px;"><c:out value="${item.edcTitle }"/></textarea>
								</c:otherwise>
							</c:choose>
						</td>
						<td>(${item.passCnt }/${item.totalMemCnt})</td>
						<td>
							<c:choose>
								<c:when test="${item.passYn eq 'Y' }">
									<textarea name="lectContent_${status.index}" class="h2"><c:out value="${item.edcCnts }"/></textarea>
								</c:when>
								<c:otherwise>
									<textarea name="lectContent" class="h2" disabled>학습일 이후 등록하실 수 있습니다.</textarea>
								</c:otherwise>
							</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.passYn eq 'Y' }">
										<div class="file_list">
											<span class="file-preview-link" data-imgpath="<tags:UploadFileUrl filePath="${item.lectImgPath}" fileName="${item.lectImgFilenm }" originName="${item.lectImgOrigin }"/>">
												<em>${item.lectImgOrigin}</em>
											</span>
										</div>
										<div class=" txtleft mt_10 file-del-ui" <c:if test="${empty item.lectImgOrigin}">style="display:none"</c:if>>
											<a href="javascript:;" class="btn_s2_c2 mg0 file-del-btn">삭제</a>
										</div>
										<div class=" txtleft mt_10 file-add-ui" <c:if test="${!empty item.lectImgOrigin}">style="display:none"</c:if>>
											<label class="btn_s2_c1 mg0 file-add-btn" for="log_file_${status.index}">추가</label>
											<input type="file" name="log_file_${status.index}" id="log_file_${status.index}" style="display:none" accept="image/*"/>
										</div>
									</c:when>
								</c:choose>
							</td>
						</tr>
</c:forEach>
				</tbody>
			</table>
		</div>
</form>
		<!--// table  -->

		<div class="btn_area">
			<a href="javascript:history.back()" class="btn_s1_c0">이전</a>
			<a href="javascript:void(0)" class="btn_s1_c1" id="save-btn">저장</a>
		</div>
		<!--// btn_area  -->
	</div>
	<!--// sub_content  -->
	<!-- 팝업 -->
	<div class="modal" id="modal1">
		<div class="modal-dialog big">
			<div class="modal-content">
				<div class="modal-title">
					<h4>교육기록 사진</h4>
					<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div class="modal-body pb_0">
					<img id="img-preview" src="" alt="">
				</div>
				<ul class="btn_area">
					<li><a href="#" class="btn_s1_c0 modal1_close">닫기</a></li>
				</ul>
				<!-- //btn_area -->
			</div>
		</div>
	</div>
</body>