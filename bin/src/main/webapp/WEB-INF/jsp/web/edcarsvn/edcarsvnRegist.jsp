<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : edcarsvnRegist.jsp
  * @Description : 교육 예약 등록
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.09.16      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.09.16
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script src="<%=strContext%>/js/common_web_edu.js"></script>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">교육예약</h3>
					<div class="join_wrap bor">
						<!-- step -->
						<c:if test="${detailVO.edcProgmType eq '1001'}"> <!--  개인 -->
							<ul class="step step3">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>2</dt>
										<dd>인원입력 및 동의</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>
						
						<c:if test="${detailVO.edcProgmType eq '3001'}"> <!--  단체 -->
							<ul class="step step4">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>2</dt>
										<dd>인원입력 및 동의</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>						
						
						<c:if test="${detailVO.edcProgmType eq '4001' and param.eduUserBtn eq '1'}"> <!--  개인 -->
							<ul class="step step3">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>2</dt>
										<dd>인원입력 및 동의</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>						
						
						<c:if test="${detailVO.edcProgmType eq '4001' and param.eduUserBtn eq '2'}"> <!--  단체 -->
							<ul class="step step4">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>2</dt>
										<dd>인원입력 및 동의</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>	
												
						<!-- //step -->
						
						<form:form commandName="eventProgramVO" id="eduSaveForm" method="post">
						
							<input type="hidden" id="edcPrgmid"     name="edcPrgmid"       value="${detailVO.edcPrgmid}" />
							<input type="hidden" id="edcRsvnReqid"  name="edcRsvnReqid"   />
							<input type="hidden" id="memberEmail"   name="memberEmail"     value="${email}"  />						
							<input type="hidden" id="eduUserBtn"    name="eduUserBtn"      value="${param.eduUserBtn}"  />
							
							<!-- signup2 -->
							<div class="signup2">
								<p class="stit">선택된 예약 프로그램을 확인해 주십시오.</p>
								<!-- table -->
								<div class="table2 responsive-type mb50">
									<table>
										<caption>교육예약정보안내표입니다.</caption>
										<colgroup>
											<col style="width:20%">
											<col style="width:30%">
											<col style="width:20%">
											<col style="width:30%">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row" class="vtop">프로그램명</th>
												<td class="b" colspan="3"><c:out value='${detailVO.edcPrgmnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">신청 기간</th>
												<td class="b" colspan="3">
													<c:out value="${detailVO.edcRsvnSdatetime}"/> ~ <c:out value="${detailVO.edcRsvnEdatetime}"/>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">교육 기간</th>
												<td class="b" colspan="3">
													
													<fmt:parseDate var="dateStr" value="${detailVO.edcSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${detailVO.edcEdate}" pattern="yyyyMMdd" />
													<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
																										
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">교육 요일</th>
												<td class="b">
													<c:out value="${detailVO.edcDaygbnNm}"/>
												</td>
												<th scope="row" class="vtop">교육 시간</th>
												<td class="b">
													${fn:substring(detailVO.edcStime,0,2)}:${fn:substring(detailVO.edcStime,2,4)}
													~ ${fn:substring(detailVO.edcEtime,0,2)}:${fn:substring(detailVO.edcEtime,2,4)}												
												</td>
											</tr>
											
											<c:if test="${empty detailVO.edcOdr or detailVO.edcOdr eq '0'}">
												<tr>
													<th scope="row" class="vtop">대상</th>
													<td class="b" colspan="3">
														<c:out value="${detailVO.edcTargetinfo}"/>
													</td>
												</tr>												
											</c:if>
											
											<c:if test="${!empty detailVO.edcOdr and detailVO.edcOdr ne '0'}">
												<tr>
													<th scope="row" class="vtop">교육 차수</th>
													<td class="b">
														<c:out value="${detailVO.edcOdr}"/> 회
													</td>
													<th scope="row" class="vtop">대상</th>
													<td class="b">
														<c:out value="${detailVO.edcTargetinfo}"/>
													</td>
												</tr>												
											</c:if>

											<tr>
												<th scope="row" class="vtop">비용</th>
												<td class="b" colspan="3">
													<!-- 0원 -->
													무료
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">재료비</th>
												<td class="b" colspan="3"><em>${detailVO.edcTchmtrGuide}</em></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">교육장소</th>
												<td class="b">
													<c:out value="${detailVO.edcPlacenm}"/>
												</td>
												<th scope="row" class="vtop">문의전화</th>
												<td class="b">
													<c:out value="${detailVO.edcGuideTelno}"/>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">특이사항</th>
												<td class="b" colspan="3">
													<c:out value="${detailVO.edcGuideComment}"/> 
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<!--// table -->
								<!-- board_ty -->
								<div class="board_ty">
									<ul>
										<li>
											<div class="info">
												<!-- info_tit -->
												<div class="info_tit">
													<span>참가인원(명) <em>교육에 참가할 인원을 입력해주세요</em></span>
												</div>
												<!-- //info_tit -->
												
												<!-- numcontrol -->
												<div class="numcontrol">
													<!-- 
													<input value="-" id="m" type="button" class="btn_minus" />
													<input type="text" class="count" value="1"  title="인원" />
													<input value="+" id="p" type="button" class="btn_plus" />
													-->
													
													<!-- <input type="button" class="btn_minus" onclick="fn_cnt_change('minus', 0,  -1, this)"> -->
													<input id="personCnt" name="edcVistnmpr" class="count" value="1" type="text" autocomplete="false" title="참여 인원수 입력" readonly>
													<!-- <input type="button" class="btn_plus" onclick="fn_cnt_change('plus', 0,  1, this)"> -->													
													
												</div>
												<!-- //numcontrol -->
												
											</div>
											<!-- //info -->
										</li>
									</ul>
								</div>
								<!-- //board_ty -->
								<!-- calc -->
								<div class="calc mb70">
									<div class="cal2">
										<dl>
											<dt>결제 금액</dt>
											<dd class="pink">
												<!-- 0원 -->
												무료
											</dd>
										</dl>
									</div>
								</div>
								<!-- //calc -->
								
								<!-- agree_list -->
								<p class="stit">교육 프로그램 신청 시 유의사항에 동의해 주시기 바랍니다.</p>
								<ul class="agree_list ty">
									<li>
										<input type="checkbox" id="total_chk">
										<label for="total_chk">(필수) 예약유의사항</label>
										<div class="agree_cont">
											<p>* 주말 및 공휴일 혼잡 안내</p>
											<p class="sub_p">- 주말 및 공휴일 12시 이후부터는 입장 대기시간이 1시간 이상 소요 될 수 있으니 쾌적한 관람을 원하시는 분들께서는 평일에 방문해 주시길 바랍니다.</p>
											<p>* 프라이빗 전시 도슨트 투어 (소요시간 1시간 내외)</p>
											<p class="sub_p">- 요금: 1인 20,000원</p>
										</div>
									</li>
								</ul>
								<!-- //agree_list -->
							</div>
							<!-- //signup2 -->
							
						</form:form>
						
					</div>
					<!-- //join_wrap -->
					<ul class="btn_area">
						<li><a href="javascript:history.go(-1);" class="btn_ty_m_c4">이전페이지 이동</a></li>
						
						<c:if test="${detailVO.edcProgmType eq '1001'}">
							<li><a href="javascript:fn_next();" class="btn_ty_m_c1">다음단계</a></li>	
						</c:if>
						<c:if test="${detailVO.edcProgmType eq '3001'}">
							<li><a href="javascript:fn_next();" class="btn_ty_m_c1">다음단계</a></li>	
						</c:if>						
						<c:if test="${detailVO.edcProgmType eq '4001' and param.eduUserBtn eq '1'}">
							<li><a href="javascript:fn_reserve_save();" class="btn_ty_m_c1">예약</a></li>	
						</c:if>
						<c:if test="${detailVO.edcProgmType eq '4001' and param.eduUserBtn eq '2'}">
							<li><a href="javascript:fn_next();" class="btn_ty_m_c1">다음단계</a></li>
						</c:if>
												
					</ul>
					<!-- //btn_area -->
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
<script type="text/javascript">
	$(function() {
		setTimeout("fn_login_check()", 100); //로그인여부 체크

		$('#total_chk').click(function() {
			var chkBox = $(this);
			if (chkBox.is(':checked')) {
				$(".agree_list input[type='checkbox']").prop("checked", true);
			} else {
				$(".agree_list input[type='checkbox']").prop("checked", false);
			}
		}); //전체 약관 동의
		$('.agree_list.ty input[type="checkbox"] + label').click(function() {
			$(this).toggleClass('on');
		}); //제목클릭이벤트

		$('.agree_list.ty li i').click(function() {
			$(this).toggleClass('on');
			$(this).parent().siblings('.agree_cont').stop().slideToggle();
		});//화살표

		$('.count').keyup(function() {
			if ($(this).val() == "") {
				$(this).val("0");
			}
			if ($(this).val().length > 1 && $(this).val().indexOf("0") == 0) {
				$(this).val(parseInt($(this).val()));
			}
			if ( isNaN($(this).val())) {
				alert("숫자만 입력해주세요.");
				$(this).val("0");
			}

			if (fn_cnt_change('cus',  0, 0, this)) {
				//
			}
		});

		$('#edcEmail').on('keyup',function() {
			$('#divEmailResult').html("");
		});

		//setTimeout("fn_total_price_set()", 100); //historyback 시 금액 계산
	});

	function fn_reserve_save() {
		
		if (fn_personcnt_check()) {
			
			/* 
			if ($('#edcEmail').val() != $('#memberEmail').val()) {
				$('#divEmailResult').text("[수정하기] 버튼을 클릭하셔서 이메일 정보를 변경해 주세요.").addClass("red").removeClass("green");
				$('#edcEmail').focus();
			} else */ 
			
			if (confirm("교육을 예약하시겠습니까?")) {
				
				////$('body').showLoading();
				
				////console.log($("#eduSaveForm"));
				
				fn_ajaxEduOnly("./edcarsvnRegSave", $("#eduSaveForm"), fn_rsvnsave_callback);
			}
			
		}

	}
	
	function fn_next() {
		if (fn_personcnt_check()) {
			$('#eduSaveForm').attr("action" , "./edcarsvnRegInput");
			$("#eduSaveForm").submit();
		}
	}
	
	function fn_email_modify() {
		var email = $('#edcEmail').val();
		var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식

		if (email == "") {
			alert("이메일 주소를 입력해 주세요.");
			$('#edcEmail').focus();
		} else if (!emailRule.test(email)) {
			alert("이메일 주소가 형식에 맞지 않습니다.");
			$('#edcEmail').focus();
		} else if (email == $('#memberEmail').val()) { 
			$('#divEmailResult').text("정상적으로 수정되었습니다.").addClass("green").removeClass("red");
		} else {
			$('body').showLoading();
			fn_ajaxEduOnly("./edcarsvnEmailSave", $("#eduSaveForm"), fn_email_callback);
		}
	}

	function fn_email_callback(data) {
		
		////JYS 2021.05.13 $('body').hideLoading();
		
		if (data.RESULT.code == "SUCCESS") {
			$('#divEmailResult').text("정상적으로 수정되었습니다.").addClass("green").removeClass("red");
			$('#edcEmail').val(data.RESULT.msg);
			$('#memberEmail').val(data.RESULT.msg);
		} else {
			$('#divEmailResult').text(data.RESULT.msg).addClass("red").removeClass("green");
		}
	}

	function fn_numWithCommas(x) {
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function fn_rsvnsave_callback(data) {

		console.log("call fn_rsvnsave_callback");
		console.log(data);

		////JYS 2021.05.13 $('body').hideLoading();

		if (data.RESULT.EXE_YN == 'Y') {

			// 완료 페이지 이동
			$("#edcRsvnReqid").val(data.RESULT.RSVN_REQ_ID);
			$('#eduSaveForm').attr("action" , "./edcarsvnRegEnd");
			$("#eduSaveForm").submit();

			return false;

		} else {
			alert("오류! 교육 예약에 실패했습니다.");
			return false;
		}

	}

	function fn_cnt_change(itemCd, index, cnt, obj) {
		var varMaxCnt     = Number("${detailVO.edcPncpa}");
		var varAllRscvRow = Number("${detailVO.appCnt}");
		var varPernCnt = $("#personCnt").val();

		if (itemCd == 'plus') {
			varPernCnt ++;

			if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
				alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
				return false;
			}

			$("#personCnt").val(varPernCnt);

		} else if (itemCd == 'minus') {

			varPernCnt --;

			if (varPernCnt <= 0) {
				alert("더는 줄일 수 없습니다");
				return false;
			}

			$("#personCnt").val(varPernCnt);

		} else if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
			alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			//fn_total_price_set();
			return false;
		}


		//fn_total_price_set();
		return false;

	}

	function fn_total_price_set() {
		var varPernCnt = 0;
		var varPrice   = Number("${detailVO.salamt}");

		if ($("#personCnt").length) {
			varPernCnt =  Number($("#personCnt").val());
		} else {
			varPernCnt = 1;
		}



		var varTotalAmt      = varPrice;
		var varFinalTotalAmt = 0;

		varFinalTotalAmt = varPrice * varPernCnt;

		$("#totalAmt").text(fn_numWithCommas(varFinalTotalAmt) + "원");
		$("#dcChargeAmt").text("0");
		$("#totalChargeAmt").text(fn_numWithCommas(varFinalTotalAmt) + "원");

		$("#finalTotalAmt").text($("#totalAmt").text());
		$("#finalDcChargeAmt").text("0");
		$("#finalTotalChargeAmt").text($("#totalChargeAmt").text());

	}

	function fn_personcnt_check() {
		var varMaxCnt     = Number("${detailVO.edcPncpa}");
		var varAllRscvRow = Number("${detailVO.allRscvRow}");

		var varPernCnt = $("#personCnt").val();

		 if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
				alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
				return false;
			}


		<c:if test="${detailVO.edcProgmType eq '3001' and detailVO.edcGrpMincnt > 0}"> 
			if (varPernCnt < ${detailVO.edcGrpMincnt} || varPernCnt > ${detailVO.edcGrpMaxcnt}) {
				alert("단체 신청은 최소  ${detailVO.edcGrpMincnt}명 이상 최대 ${detailVO.edcGrpMaxcnt}명 이하로 입력해 주세요.");
				$("#personCnt").focus();
				return false;
			}
		</c:if>

		 var notCheckCnt = 0;
			$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
				if (!$(item).prop("checked")) {
					notCheckCnt++;
				}
			});

		if (notCheckCnt > 0) {
			alert("약관을 확인하시고 모두 동의해주세요.");
			$('#total_chk').focus();
			return false;
		} else {
			return true;
		}

	}
		
	function fnAgreeOnClick(parmThis) {
		$(parmThis).parent().siblings('.agree_cont').stop().slideToggle();		
		return false;	
	}

</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>