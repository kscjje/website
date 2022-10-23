<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnGrpStep2.jsp
  * @Description : 예약 정보 입력 JSP(단체)
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.02    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.02
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

$(document).ready(function() {

	$('input[type=text]').keyup(function() {
		$('#err_'+$(this).attr("id")).hide();
	});

	$('.phoneCheck').keyup(function() {
		$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
	});

	setTimeout("fn_login_check()", 100); //로그인여부 체크
});


function fn_rsvn_send() {
	
	var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
	var hpRule = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	var hphone = $('#evtRsvnMoblphon').val().split("-").join('');
	var nullObj = "";

	if ($('#evtRsvnCustnm').val() == "") {
		alert("이름을 입력해 주세요.");
		$('#evtRsvnCustnm').focus();
	} else if ($('#evtRsvnMoblphon').val() == "") {
		alert("휴대폰번호를 입력해 주세요.");
		$('#evtRsvnMoblphon').focus();
	} else {

		
		
		<c:if test="${evtrsvnMstVO.evtPersnGbn eq '20'}">
			if ($('#evtRsvnGrpnm').val() == "") {
				if (nullObj == "") nullObj = "evtRsvnGrpnm";
				$('#err_evtRsvnGrpnm').text("단체명을 입력해 주세요.").show();
			}
			if ($('#evtVisitCustnm').val() == "") {
				if (nullObj == "") nullObj = "evtVisitCustnm";
				$('#err_evtVisitCustnm').text("방문 인솔자명을 입력해 주세요.").show();
			}
			if ($('#evtVisitMoblphon').val() == "") {
				if (nullObj == "") nullObj = "evtVisitMoblphon";
				$('#err_evtVisitMoblphon').text("방문 인솔자 연락처를 입력해 주세요.").show();
			} else if (!hpRule.test($('#evtVisitMoblphon').val())) {
				if (nullObj == "") nullObj = "evtVisitMoblphon";
				$('#err_evtVisitMoblphon').text("방문 인솔자 연락처가 형식에 맞지 않습니다.").show();
			}
			
		</c:if>
	
		/* 
		if ($('#evtEmail').val() == "") {
			if (nullObj == "") nullObj = "evtEmail";
			$('#err_evtEmail').text("이메일 주소를 입력해 주세요.").show();
		} else if (!emailRule.test($('#evtEmail').val())) {
			if (nullObj == "") nullObj = "evtEmail";
			$('#err_evtEmail').text("이메일 주소가 형식에 맞지 않습니다.").show();
		}
		*/

		if (nullObj != "") {
			
			console.log(nullObj);
			
			$('#' + nullObj).focus();
			
		} else {
			
			////$('body').showLoading();

			 $.ajax({
			        type : "POST",
			        url  : './evtrsvnAction',
			        data : $('#evtrsvnMstVO').serialize(),
			        dataType: "json",
			        beforeSend : function(xhr) {
			        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
			        },
			        success: function(data) {
			        	
			        	////$('body').hideLoading();
			        	
			        	if (data != null) {
			        		if ((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')) {
			        		} else {
			        			if (data.ERROR_CODE == -1) {
			            			alert(data.ERROR_MSG);
			            			return;
			        			} else if (data.ERROR_CODE == -3) {
			        				alert(data.ERROR_MSG);
									top.location.href = "/web/main";
			        				return;
			        			}
			        		}
			        	}
			        	
			        	fn_reserve_success(data);

			        }, error: function(jqXHR, exception) {
			        	$('body').hideLoading();
			        	var varStatus = jqXHR.status;

			        	if (varStatus == "403") {
			        		alert("접근 권한이 없습니다. 로그인을 다시 확인해 주세요.");
			        		top.location.replace("/web/main");
			        	} else {
			        		alert("시스템 오류 입니다 .");
			        	}
			        }
			    });

		}
	}
}

function fn_reserve_success(data) {
	 if (data.result.resultCd == "SUCCESS") {
		 $('#evtRsvnIdx').val( data.result.resultMsg );
   	 	 $('#evtrsvnMstVO').attr("action" , "./evtrsvnResult");
   		 $('#evtrsvnMstVO').submit();
   } else {
		alert(data.result.resultMsg);
   }
}

</script>

				<div class="sub_cont" id="container">
					<div class="inner">
						 
						<h3 class="tit mb100">강연/행사/영화 예약</h3>
						<div class="join_wrap bor">
						
							<ul class="step step3">
								<li>
									<dl>
										<dt>1</dt>
										<dd>강연/행사/영화 선택</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>2</dt>
										<dd>인원입력 및 동의</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
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
						
							<!-- step -->
							<!-- 
							<ul class="step step4">
								<li>
									<dl>
										<dt>1</dt>
										<dd>회차 선택</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>2</dt>
										<dd>안내 및 주문</dd>
									</dl>
								</li>
								<li class="on">
									<dl>
										<dt>3</dt>
										<dd>정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>예약완료</dd>
									</dl>
								</li>
							</ul>
							-->
							
							<!-- //step -->
							<!-- prgm detail -->
							<!-- signup2 -->
							<form:form commandName="evtrsvnMstVO" name="evtrsvnMstVO" id="evtrsvnMstVO" action="./evtrsvnAction" method="post">
								<form:hidden path="evtRsvnWebid"/>
								<form:hidden path="evtNo"/>
								<form:hidden path="comcd"/>
								<form:hidden path="evtPersnGbn"/>
								<form:hidden path="evtVeingdate"/>
								<form:hidden path="evtVeingnmpr"/>
								<form:hidden path="evtTimeseq"/>
								<form:hidden path="evtTimestdSeq"/>
								<form:hidden path="evtRsvnCustnm"/>
								<form:hidden path="evtRsvnMoblphon"/>
								<form:hidden path="evtRsvnMemno"/>
								<form:hidden path="evtNonmembCertno"/>
								<form:hidden path="evtRsvnIdx"/>
								<form:hidden path="evtRsvnMemtype"/>
								<form:hidden path="evtStime" value="${timeVO.sTime}"/>
								<form:hidden path="evtEtime" value="${timeVO.eTime}"/>
								<div class="signup2">
									<p class="stit">예약자 정보 입력</p>
									<div class="table2 responsive-type mb50">
										<table>
											<caption>예약자 정보를 입력하는 표입니다.</caption>
											<colgroup>
												<col style="width:30%">
												<col style="width:70%">
											</colgroup>
											<tbody>
												<tr style="display:none;">
													<th scope="row"><label for="name">아이디</label></th>
													<td><c:choose><c:when test="${evtrsvnMstVO.evtRsvnMemtype eq '1001'}"><c:out value="${evtrsvnMstVO.evtRsvnWebid }" /></c:when><c:otherwise>비회원</c:otherwise></c:choose></td>
												</tr>
												<tr>
													<th scope="row"><label for="custNm">예약자명</label></th>
													<td><c:out value="${userVO.name}"/></td>
												</tr>
												<c:choose>
													<c:when test="${evtrsvnMstVO.evtPersnGbn eq '20'}">
														<tr>
															<th scope="row"><label for="evtRsvnCustnm"><strong class="red">*</strong>단체, 학교, 기관 명</label></th>
															<td><form:input path="evtRsvnGrpnm"  placeholder="단체명을 입력해주세요." title="단체명을 입력해주세요." maxlength="50"/>
																<p id="err_evtRsvnGrpnm" class="red" style="display:none">단체명을 입력해주세요.</p>
															</td>
														</tr>
														<tr>
															<th scope="row"><label for="exbtHp">예약자  연락처</label></th>
															<td>
																<tags:HpDecrypt param="${userVO.ihidNum}"/>
															</td>
														</tr>														
														<tr>
															<th scope="row"><label for="evtRsvnCustnm"><strong class="red">*</strong>방문 인솔자명</label></th>
															<td><input type="text" id="evtVisitCustnm" name="evtVisitCustnm" value="${userVO.name}"  placeholder="이름을 입력해주세요." title="방문 인솔자명이름을 입력해주세요" maxlength="50">
																<p id="err_evtVisitCustnm" class="red" style="display:none">방문 인솔자명을 입력해주세요.</p>
															</td>
														</tr>
														<tr>
															<th scope="row"><label for="evtVisitMoblphon"><strong class="red">*</strong>방문 인솔자 연락처</label></th>
															<td>
																<input type="text" id="evtVisitMoblphon" name="evtVisitMoblphon" value="<tags:HpDecrypt param='${userVO.ihidNum}'/>" placeholder="‘-’ 을 빼고 입력 해 주십시오." title="방문 인솔자 연락처를 입력해주세요" class="phoneCheck" maxlength="13">
																<p id="err_evtVisitMoblphon" class="red" style="display:none">방문 인솔자 연락처를 입력해주세요.</p>
															</td>
														</tr>
													</c:when>
												</c:choose>
												
												<tr style="display:none;">
													<th scope="row"><label for="evtEmail">이메일</label></th>
													<td>
														<form:input path="evtEmail" placeholder="이메일주소를 입력 해 주십시오." maxlength="50" title="이메일 주소를 입력해 주세요."/>
														<p id="err_evtEmail" class="red" style="display:none">이메일을 입력해주세요.</p>
													</td>
												</tr>
											</tbody>
										</table>
										<!-- //table -->
									</div>
									<!-- //예약자 정보 입력 -->
									<p class="stit" style="display:none;">예약 정보 확인</p>
									<div class="table2 responsive-type mb50" style="display:none;">
										<table>
											<caption>예약 정보 확인표입니다.</caption>
											<colgroup>
												<col style="width:30%">
												<col style="width:80%">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row" class="vtop">제목</th>
													<td class="b"><c:out value="${detail.evtName}"/></td>
												</tr>
												<tr>
													<th scope="row" class="vtop">일시</th>
													<td class="b">
														<fmt:parseDate var="dateStr1" value="${evtrsvnMstVO.evtVeingdate}" pattern="yyyyMMdd" />
														<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" />
														&nbsp;&nbsp; <c:out value="${fn:substring(timeVO.sTime,0,2)}:${fn:substring(timeVO.sTime,2,4)}" /> ~ <c:out value="${fn:substring(timeVO.eTime,0,2)}:${fn:substring(timeVO.eTime,2,4)}" />
													</td>
												</tr>
												<tr>
													<th scope="row" class="vtop">인원수</th>
													<td class="b">
													<c:forEach items="${chrgList }" var="v" varStatus="s">
														<form:hidden path="chargeList[${s.index}].itemCd"/>
														<form:hidden path="chargeList[${s.index}].itemCnt"/>
														<form:hidden path="chargeList[${s.index}].itemPrice"/>
														<form:hidden path="chargeList[${s.index}].itemNm"/>
														<form:hidden path="chargeList[${s.index}].dcRate"/>
															<c:if test="${v.itemCnt > 0 }">
																<c:out value="${v.itemNm }"/> : <c:out value="${v.itemCnt }"/> 명&nbsp;&nbsp;&nbsp;
															</c:if>
													</c:forEach>
													</td>
												</tr>
												<tr>
													<th scope="row" class="vtop">총 금액</th>
													<td class="b"><fmt:formatNumber value="${totalAmount }" /> 원</td>
												</tr>
												
												<c:if test="${totalAmount > 0 and dcAmount > 0 }">
													<tr>
														<th scope="row" class="vtop">할인 예상 금액</th>
														<td class="b"><fmt:formatNumber value="${dcAmount}"/>원</td>
													</tr>
													<tr>
														<th scope="row" class="vtop">결제 예상 금액</th>
														<td class="b"><fmt:formatNumber value="${totalAmount-dcAmount}"/>원</td>
													</tr>
												</c:if>
												</tr>
											</tbody>
										</table>
										<!-- //table -->
									</div>
								</div>
							</form:form>
							<!-- //signup2 -->
						</div>
						<!-- //join_wrap -->
						<ul class="btn_area">
							<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지이동</a></li>
							<li><a href="javascript:fn_rsvn_send()" class="btn_ty_m_c1">예약하기</a></li>
						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->
				
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>