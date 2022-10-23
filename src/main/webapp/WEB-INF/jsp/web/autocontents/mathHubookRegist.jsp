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

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<script src="<%=strContext%>/js/common_nsm_human.js"></script>
					<div class="sub_cont" id="container">
						<div class="inner">
							<h3 class="tit">예약정보</h3>
							<div class="join_wrap bor">
								<!-- step -->
								<c:if test="${detailVO.edcProgmType eq '1001'}"> <!--  개인 -->
									<c:choose>
										<c:when test="${detailVO.detailVO.eduAddrOpenyn eq 'Y'}"> <!--  주소 수집 -->
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
														<dd>안내 및 주문</dd>
													</dl>
												</li>
												<li>
													<dl>
														<dt>3</dt>
														<dd>정보 입력</dd>
													</dl>
												</li>
												<li>
													<dl>
														<dt>4</dt>
														<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
													</dl>
												</li>
											</ul>
										</c:when>
										<c:otherwise>
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
														<dd>안내 및 주문</dd>
													</dl>
												</li>
												<li>
													<dl>
														<dt>3</dt>
														<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
													</dl>
												</li>
											</ul>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${detailVO.edcProgmType eq '3001'}">
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
												<dd>인원 입력 및 동의</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>4</dt>
												<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
											</dl>
										</li>
									</ul>
								</c:if>
								<c:if test="${detailVO.edcProgmType eq '2001'}">
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
												<dd>안내 및 주문</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>4</dt>
												<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
											</dl>
										</li>
									</ul>
								</c:if>
								<c:if test="${detailVO.edcProgmType eq '9001'}">
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
												<dd>안내 및 주문</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>4</dt>
												<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
											</dl>
										</li>
									</ul>
								</c:if>
								<!-- //step -->

<form:form commandName="eventProgramVO" id="eduSaveForm" method="post">
<input type="hidden" id="edcPrgmid"     name="edcPrgmid"       value="${detailVO.edcPrgmid}" />
<input type="hidden" id="edcRsvnReqid"  name="edcRsvnReqid"   />
<input type="hidden" id="memberEmail" name="memberEmail" value="${email}"  />
								<!-- signup2 -->
								<div class="signup2">
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
													<td class="b" colspan="3">
														<div class="title_wrap">
														<c:if test="${detailVO.edcOnlineyn eq 'Y' }">
															<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
														</c:if>
														<c:out value='${detailVO.edcPrgmnm}' />
														</div>
													</td>
												</tr>
<c:choose>
	<c:when test="${detailVO.edcProgmType eq '1001' }">
												<tr>
													<th scope="row" class="vtop">교육 기간</th>
													<td class="b" colspan="3">
														<fmt:parseDate var="dateStr" value="${detailVO.edcSdate}" pattern="yyyyMMdd" />
														<fmt:parseDate var="dateStr2" value="${detailVO.edcEdate}" pattern="yyyyMMdd" />
														<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
														(<c:out value="${detailVO.edcOdr}"/> 차)
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
	</c:when>
	<c:when test="${detailVO.edcProgmType eq '2001' or detailVO.edcProgmType eq '9001' }">
												<tr>
													<th scope="row" class="vtop">교육 기간</th>
											 		<td class="b" colspan="3"><c:out value="${detailVO.edcSdatetime}"/> ~ <c:out value="${detailVO.edcEdatetime}"/></td>
											 	</tr>
	</c:when>
	<c:when test="${detailVO.edcProgmType eq '3001' }">
												<tr>
													<th scope="row" class="vtop">교육 기간</th>
											 		<td class="b" colspan="3"><c:out value="${detailVO.edcSdatetime}"/> ~ ${fn:substring(detailVO.edcEtime,0,2)}:${fn:substring(detailVO.edcEtime,2,4)} </td>
											 	</tr>
	</c:when>
	<c:otherwise>

	</c:otherwise>
</c:choose>

												<tr>
													<th scope="row" class="vtop">교육장소</th>
													<td class="b">
														<c:out value="${detailVO.edcPlacenm}"/>
													</td>
													<th scope="row" class="vtop">교육 대상</th>
													<td class="b" colspan="3">
														<c:out value="${detailVO.edcTargetinfo}"/>
													</td>
												</tr>
												<tr>

												</tr>
												<tr>
													<th scope="row" class="vtop">비용</th>
													<td class="b" colspan="3">
														<c:choose>
															<c:when test="${detailVO.salamt >0 }"><fmt:formatNumber value="${detailVO.salamt}" pattern="#,###" />원</c:when>
															<c:otherwise>무료</c:otherwise>
														</c:choose>
<c:choose>
														<c:when test="${detailVO.edcTchmtrGuideyn eq 'Y'}">&nbsp;<em>※ ${detailVO.edcTchmtrGuide}</em></c:when>
</c:choose>
													</td>
												</tr>
<c:if test="${detailVO.edcShtlbusYn eq 'Y' }">
												<tr>
													<th scope="row" class="vtop">셔틀버스</th>
													<td colspan="3" class="b">운행함</td>
												</tr>
</c:if>
<c:if test="${detailVO.edcShtlbusYn eq 'Y' and !empty detailVO.edcShtlbusGuide }">
											<tr>
												<th scope="row" class="vtop">셔틀버스<br>탑승위치</th>
												<td colspan="3">
													${fn:replace(detailVO.edcShtlbusGuide, crlf , '<br/>')}
												</td>
											</tr>
</c:if>
												<tr>
<c:choose>
	<c:when test="${detailVO.edcProgmType eq '3001' and detailVO.edcGrpMincnt > 0}">
													<th scope="row" class="vtop">잔여 정원</th>
													<td class="b">
														<fmt:formatNumber value="${detailVO.edcPncpa-detailVO.appCnt}"/>명
													</td>
													<th scope="row" class="vtop">단체 기준</th>
													<td class="b"> 최소 <fmt:formatNumber value="${detailVO.edcGrpMincnt}"/>명 ~ 최대  <fmt:formatNumber value="${detailVO.edcGrpMaxcnt}"/>명</td>

	</c:when>
	<c:otherwise>
													<th scope="row" class="vtop">잔여 정원</th>
													<td class="b" colspan="3">
														<fmt:formatNumber value="${detailVO.edcPncpa-detailVO.appCnt}"/>명
													</td>

	</c:otherwise>
</c:choose>
												</tr>
<c:choose>
	<c:when test="${detailVO.edcProgmType eq '1001' and detailVO.edcAddrOpenyn ne 'Y'}">
												<tr>
													<th scope="row" class="vtop">이메일</th>
													<td colspan="3">
														<div class="w_box" style="margin-top:0px">
															<div class="w100" style="vertical-align:middle">
																<label for="edcEmail" class="hidden">이메일</label>
																<input type="text" id="edcEmail" name="edcEmail" value="${email}" title="이메일 입력"/>
															</div>
															<div>
																<button type="button" onclick="fn_email_modify()" class="btn_ty_m2_c3">수정하기</button>
															</div>
														</div>
														<p id="divEmailResult"></p>
														<p>※ 이메일 변경 후 수정하기 버튼을 클릭해 주세요.<br>※ 회원정보의 이메일 주소도 함께 변경됩니다.</p>
													</td>
												</tr>
	</c:when>
</c:choose>
											</tbody>
										</table>
									</div>
									<!--// table -->
<c:if test="${detailVO.edcProgmType eq '3001'}">
									<h4 class="h4 mb5">예약 인원 선택</h4>
									<p class="desc mb40">쿠폰 또는 기타 할인은 결제 화면에서 변경하실 수 있습니다.</p>
									<!-- board_ty -->
									<div class="board_ty">
										<ul>
											<li>
												<div class="info">
													<!-- info_tit -->
													<div class="info_tit">
														<span>
															참여인원 입력
														</span>
													</div>
													<!-- //info_tit -->
													<!-- numcontrol -->
													<div class="numcontrol num01">
																<input type="button" class="btn_minus" onclick="fn_cnt_change('minus', 0,  -1, this)">
																<input id="personCnt" name="edcVistnmpr" class="count" value="1" type="text" autocomplete="false" title="참여 인원수 입력">
																<input type="button" class="btn_plus" onclick="fn_cnt_change('plus', 0,  1, this)">
													</div>
													<!-- //numcontrol -->
												</div>
												<!-- //info -->

											</li>

										</ul>
									</div>
</c:if>
									<!-- //board_ty -->
									<!-- calc -->
<c:if test="${detailVO.edufreeYn ne 'Y'}">
	<c:choose>
		<c:when test="${dcAmount > 0 }">
									<p class="desc mb15" style="text-align:left">
									※ ${discInfoVO.eventDcname } (${discInfoVO.eventDcRate }% 할인)
									</p>
									<div class="calc mb70">
										<div class="cal1">
											<dl>
												<dt>총 금액</dt>
												<dd id="priceSum"><fmt:formatNumber value="${detailVO.salamt}" pattern="#,###" />원</dd>
											</dl>
											<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
											<dl>
												<dt>할인 예상 금액</dt>
												<dd id="priceDiscount"><fmt:formatNumber value="${dcAmount}" pattern="#,###" />원</dd>
											</dl>
										</div>
										<div class="cal2">
											<span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span>
											<dl>
												<dt>결제 예정 금액</dt>
												<dd class="pink" id="priceTotal"><fmt:formatNumber value="${detailVO.salamt-dcAmount}" pattern="#,###" />원</dd>
											</dl>
										</div>
									</div>
		</c:when>
		<c:otherwise>
									<div class="calc mb70">
										<div class="cal1">
											<dl>
												<dt>결제 금액</dt>
												<dd class="pink"><fmt:formatNumber value="${detailVO.salamt}" pattern="#,###" />원</dd>
											</dl>
										</div>
									</div>
		</c:otherwise>
	</c:choose>
</c:if>
									<!-- //calc -->
									<ul class="agree_choice ty">
										<li>
											<input type="checkbox" id="total_chk">
											<label for="total_chk">예약 유의사항, <c:if test="${detailVO.edufreeYn ne 'Y'}">환불 유의사항,</c:if> 개인정보 이용 동의에 모두 동의합니다.</label>
										</li>
									</ul>
									<!-- agree_list -->
									<ul class="agree_list ty">
<c:forEach items="${cmStplatInfoList}" var="item" varStatus="status">
	<c:if test="${(detailVO.edufreeYn ne 'Y' and item.stplatId eq '3002') or item.stplatId ne '3002'}">
	<li>
		<div class="agree_tit">
			<input type="hidden" id="stplatId1${item.stplatId}" name="stplatId1${item.stplatId}" value="${item.stplatId}" />
			<input type="checkbox" id="c${status.index+1 }"/>
            <label for="c${status.index+1 }" class="on"><c:out value="${item.stplatName }"/></label>
            
 			<!-- <i class="on">내용보기</i> 원본 JYS --> 
			<a href="#" onclick="fnAgreeOnClick(this); return false;"><i class="on">내용보기</i></a>
			
		</div>
		<div class="agree_cont">
			<c:out value="${fn:replace(item.stplatCn, crlf , '<br>')}" escapeXml="false" />
		</div>
	</li>
	</c:if>
</c:forEach>

									</ul>
									<!-- //agree_list -->
								</div>
</form:form>
								<!-- //signup2 -->
							</div>
							<!-- //join_wrap -->
							<ul class="btn_area">
								<li>
									<a href="javascript:history.back()" class="btn_ty_m_c4">이전</a>
								</li>
								<li>
<c:choose>
	<c:when test="${detailVO.edcProgmType eq '1001' and detailVO.edcAddrOpenyn ne 'Y'}">
		<a href="javascript:fn_reserve_save()" class="btn_ty_m_c1">예약</a>
	</c:when>
	<c:otherwise>
		<a href="javascript:fn_next()" class="btn_ty_m_c1">다음</a>
	</c:otherwise>
</c:choose>
								</li>
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
			if ($('#edcEmail').val() != $('#memberEmail').val()) {
				$('#divEmailResult').text("[수정하기] 버튼을 클릭하셔서 이메일 정보를 변경해 주세요.").addClass("red").removeClass("green");
				$('#edcEmail').focus();
			} else if (confirm("교육을 예약하시겠습니까?")) {
				$('body').showLoading();
				
				console.log($("#eduSaveForm"));
				
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

		if (email =="") {
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
		$('body').hideLoading();
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

				$('body').hideLoading();

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
