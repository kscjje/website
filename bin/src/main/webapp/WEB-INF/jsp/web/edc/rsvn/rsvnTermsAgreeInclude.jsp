<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="edcRsvnInfoVO" name="edcRsvnInfoVO">
	<input type="hidden" name="edcPrgmNo" value="${detailVO.edcPrgmNo}"/>
	<input type="hidden" name="edcRsvnsetSeq" value="${detailVO.edcRsvnsetSeq}"/>
	<div class="table t_flex text-left">
		<table>
			<caption>강좌신청 정보 표입니다. -강좌명, 실주소, 주관기관, 접수마감, 교육기간 , 요일시간, 정원수, 교육장소, 강좌분야, 교육대상, 강좌회차, 수강료</caption>
			<colgroup class="pc">
				<col style="width:10.7%">
				<col style="width:39.3%">
				<col style="width:10.7%">
				<col style="width:39.3%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">강좌명</th>
					<td colspan="3">${detailVO.edcPrgmnm}</td>
				</tr>
				<tr>
					<th scope="row">교육기간</th>
					<td>${detailVO.edcPeriod}</td>
					<th scope="row">일시</th>
					<td>${detailVO.edcDaygbnNm} / ${detailVO.edcTime}</td>
				</tr>
				<tr>
					<th scope="row">정원수</th>
					<td>${detailVO.edcPncpa}명 </td>
					<th scope="row">수강료</th>
					<td id="fee">
						<c:choose>
							<c:when test="${detailVO.salamt > 0}">
								<input type="hidden" id="saleAmt" name="saleAmt" value="${detailVO.salamt}" />
								<fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/>원
							</c:when>
							<c:otherwise>무료</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${empty userInfo or !userInfo.isMember}">
				<tr>
					<th scope="row">신청자성명</th>
					<td colspan="3"><input type="text" id="edcRsvnCustnm" name="edcRsvnCustnm" maxlength="20" placeholder="이름을 입력하세요" validate="required;"></td>
				</tr>
				<tr>
					<th scope="row">성별/생년월일</th>
					<td colspan="3">
						<div class="w_box dblock">
							<div class="radio">
								<span>
									<input id="sel1" name="edcRsvnGender" type="radio" title="" value="1">
									<label for="sel1">남자</label>
								</span>
								<span>
									<input id="sel2" name="edcRsvnGender" type="radio" title="" value="2">
									<label for="sel2">여자</label>
								</span>
							</div>
							<div class="inputbox">
								<input type="text" id="edcRsvnBirthdate" name="edcRsvnBirthdate" maxlength="8" placeholder="생년월일 8자리를 입력하세요 (ex.19910212)" validate="required;numeric;minlength:8">
							</div>

						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">휴대폰번호</th>
					<td colspan="3"><input type="tel" id="edcRsvnMoblphon" name="edcRsvnMoblphon" maxlength="11" placeholder="(-) 없이 숫자로 입력하세요." validate="required;label:휴대폰번호를;numeric;minlength:10"></td>
				</tr>
				</c:if>
				<c:if test="${ detailVO.edcRsvnRectype == 2001 and detailVO.exclDcyn eq 'N'}">
					<tr>
						<th scope="row">할인정보</th>
						<td>
							<c:choose>
								<c:when test="${dcLimitYn eq 'N'}">
									<input type="hidden" id="dcCd" name="edcReasondc" />
									<input type="hidden" id="dcAmt" name="edcDcamt" value = "0" />
									<a href="javascript:;" onclick="fnDcLimitPop(${detailVO.edcPrgmid}, ${detailVO.edcRsvnsetSeq},'${detailVO.rsvnNonmebyn}')" class="btn_s2_c2">감면선택</a>
									<span id="dcDesc" style="display:none">선택감면정보</span>
								</c:when>
								<c:otherwise>
									노원평생교육원, 공릉평생교육원, 장미실습장은 분기 내 1번만 할인감면 받을 수 있습니다. 고객님께서는 이미 할인감면 사용했기에, 감면받을 수 없습니다.
								</c:otherwise>
							</c:choose>
						</td>
						<th scope="row">감면금액</th>
						<td id="dcAmtEm" class="red02" >0원</td>
					</tr>
					<tr>
						<th scope="row">총 결제금액</th>
						<td colspan="3" id="payAmtEm">
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
								<fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/>원
								</c:when>
								<c:otherwise>무료</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<!--// table  -->
	
	<div class="agree">
		<div class="t_check agree_allcheck">
			<div class="w_box">
				<div class="chk_box">
					<input type="checkbox" id="c1" name="chked" class="all_check">
					<label for="c1">아래 [예약 유의시항 /개인정보 이용 동의]에 모두 동의합니다.</label>
				</div>
			</div>
		</div>
		<div class="table">
			<table>
				<caption>약관동의 표입니다.</caption>
				<tbody>
					<tr>
						<td class="t_check">
							<div class="w_box">
								<div class="chk_box">
									<input type="checkbox" id="c2" name="agree3001" class="chked">
									<label for="c2">교육 프로그램 예약 유의사항  </label>
								</div>
								<a href="#" class="agree_btn">약관 노출버튼</a>
							</div>
							<div class="desc">
								<div class="a_box_con">
								<pre><c:forEach items="${termsList}" var="item"><c:if test="${item.stplatId eq '3001'}">${item.stplatCn}</c:if></c:forEach></pre>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="t_check">
							<div class="w_box">
								<div class="chk_box">
									<input type="checkbox" id="c3" name="agree1008" class="chked">
									<label for="c3">개인정보 수집 이용 관한 동의(필수)</label>
								</div>
								<a href="javascript:;" class="agree_btn">약관 노출버튼</a>
							</div>
							<div class="desc">
								<div class="a_box_con">
								<pre><c:forEach items="${termsList}" var="item"><c:if test="${item.stplatId eq '1008'}">${item.stplatCn}</c:if></c:forEach></pre>
								</div>
							</div>
						</td>
					</tr>

				</tbody>
			</table>
		</div>
	</div>
</form>


<script>
	function fnApply() {
		var salamt = ${detailVO.salamt};
		<c:if test="${empty userInfo or !userInfo.isMember}">
		if (!edcRsvnInfoVO.validateForm())
			return;

		if($("input[name=edcRsvnGender]:checked").length < 1){
			alert("성별을 선택해 주세요.");
			$("input[name=edcRsvnGender]").eq(0).focus();
			return;
		}

		var date = $('#edcRsvnBirthdate').val();
		var regex = RegExp(/^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/);
		if(regex.test(date) == false){
			alert("생년월일이 날짜 형식에 맞지 않습니다.");
			$('#edcRsvnBirthdate').focus();
			return;
		}

		</c:if>


		if (!$(".all_check").prop("checked")) {
			alert("[예약 유의시항 /개인정보 이용 동의] 모든 동의가 필요합니다.");
			return;
		}

		$.ajax({
			url : "<c:url value='/web/edc/rsvn/apply.json'/>",
			data : edcRsvnInfoVO.ajaxSubmit(),
			type : 'POST',
			success : function (data) {
				if (data.result.code == "SUCCESS") {
					if (data.PAY_YN == "Y") { //유료.결제페이지로이동
						location.replace("<c:url value='/web/edc/rsvn/pay'/>" + "/" + data.EDC_RSVN_NO);
					} else { //무료
						location.replace("<c:url value='/web/edc/rsvn/complete'/>" + "/" + data.EDC_RSVN_NO);
					}
				} else { //"FAIL"
					alert(data.result.msg);
					if (data.EDC_RSVN_NO) {
						if(confirm("수강신청 현황 페이지로 이동하시겠습니까?")){
							var url = "<c:url value='/web/mypage/myRsvn/myRsvnList'/>"; //마아페이지>수강신청현황
							location.replace(url);
						}
					}
				}
			},
			error : function (result) {
				console.log(result);
				alert("일시적인 오류가 발생하였습니다. 잠시후 다시 신청해 주세요.");
			}
		});
	}
	$('.agree_btn').click(function(e) {
		if ($(this).parent().hasClass('on')) {
			$(this).parent().removeClass('on');
			$(this).parent().siblings('.desc').stop().slideUp(300);
		} else {
			$(this).parent().addClass('on').closest('tr').siblings()
					.children().children().removeClass('on');
			$(this).parent().addClass('on').closest('tr').siblings()
					.children().children('.desc').stop().slideUp(300);
			$(this).parent().siblings('.desc').stop().slideDown(300); //1
		}
		e.preventDefault();
	});

	
	$(function() {
		$(".all_check").click(function() {
			if ($(".all_check").prop("checked")) {
				$("input.chked").prop("checked", true);
			} else {
				$("input.chked").prop("checked", false);
			}
		})

		$("input.chked").click(function() {
			var allAgree = true;
			$.each($("input.chked"), function(idx, item) {
				if (!$(item).prop("checked")) allAgree = false;
			});
			if (allAgree) $(".all_check").prop("checked", true);
			else $(".all_check").prop("checked", false);
		});
		
		<c:if test="${not empty userInfo and userInfo.isMember}">
		window.setupValidateForm(edcRsvnInfoVO);
		</c:if>
	});
	
	function fnDcLimitPop(edcPrgmid , edcRsvnsetSeq , nonCheck){
		<c:if test="${empty userInfo or !userInfo.isMember}">
		if (!edcRsvnInfoVO.validateForm())
			return;

		if($("input[name=edcRsvnGender]:checked").length < 1){
			alert("성별을 선택해 주세요.");
			$("input[name=edcRsvnGender]").eq(0).focus();
			return;
		}

		var date = $('#edcRsvnBirthdate').val();
		var regex = RegExp(/^\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/);
		if(regex.test(date) == false){
			alert("생년월일이 날짜 형식에 맞지 않습니다.");
			$('#edcRsvnBirthdate').focus();
			return;
		}

		</c:if>
		
		
		var data = new Object();
		data.edcPrgmid = edcPrgmid;
		data.edcRsvnsetSeq = edcRsvnsetSeq;
		data.dccd = $("#dcCd").val();
			

		var userId = "${userInfo.id}";
		
		console.log($("#dcCd").val());
		console.log(edcPrgmid);
		

		if( userId == "" && nonCheck == "Y" ){
			$.ajax({
				url : "../../termsDcAjax/"+edcPrgmid+"/"+edcRsvnsetSeq, //신청 팝업
				data : edcRsvnInfoVO.ajaxSubmit(),
				dataType : 'html',
				success : function(data) {
					if(data.indexOf("ERR") == 0){
						alert(data.substring(4,data.length));
					}else{
						$('#modal1').popup('hide');
						$('#discountModal').html(data);
						$('#discountModal').popup('show');
					}
				},
				error : function(error) {
					alert("오류");
					console.log(error);
				}
			});
			
		} else if ( userId == "" && nonCheck != "Y" ) {
			//비로그인 신청화면으로
			alert("로그인 후 신청하실 수 있습니다.");
			

		} else {
			$.ajax({
				url : "<c:url value='../rsvn/termsDcAjax'/>/"+edcPrgmid+"/"+edcRsvnsetSeq, //신청 팝업
				data : data,
				dataType : 'html',
				success : function(data) {
					if(data.indexOf("ERR") == 0){
						alert(data.substring(4,data.length));
					}else{
						$('#modal1').popup('hide');
						$('#discountModal').html(data);
						$('#discountModal').popup('show');
					}
				},
				error : function(error) {
					alert("오류");
					console.log(error);
				}
			});
		}
	}
	
	function fnDcLimitPopHide(){
		$('#discountModal').popup('hide');
		$('#modal1').popup('show');
	}
	
	//감면확인 클릭 시 요금에 반영
	function fnDcApply(gubun) {
		var saleAmt = $("#saleAmt").val();

		var dcRate = $("input[name='chkDc']:checked").data("dcrate");
		var dcNm = $("input[name='chkDc']:checked").data("dcnm");
		$("#dcCd").val($("input[name='chkDc']:checked").data("dccd"));
		$("#dcRate").val($("input[name='chkDc']:checked").data("dcrate"));

		if (dcRate) {
			var dcAmt = saleAmt * (dcRate/100);
			dcAmt = Math.round(dcAmt/10)*10;
			var payAmt = saleAmt - dcAmt;

			$("#dcAmt").val(dcAmt);
			$("#payAmt").val(payAmt);

			$("#dcAmtEm").text(comma(-1*dcAmt) + "원");
			$("#payAmtEm").text(comma(payAmt) + "원");

			$("#dcDesc").text(dcNm + "(" + dcRate + "%감면)").show();
		}
		fnDcLimitPopHide();
	}
</script>