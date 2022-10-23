<%
 /**
  * @Class Name : edcarsvnRegInput.jsp
  * @Description : 교육 예약 관련 정보 등록
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
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>

<%@ include file="../../include/top.jsp"%>

<script src="<%=strContext%>/js/common_nsm_human.js"></script>

<%
	String strJusoKey = EgovProperties.getProperty("juso.go.kr.key");
%>
				<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">예약정보</h3>
						<div class="join_wrap bor">
							<!-- step -->
								<c:if test="${detailVO.edcProgmType eq '1001'}"> <!--  개인 -->
											<ul class="step step4">
												<li>
													<dl>
														<dt>1</dt>
														<dd>교육 선택</dd>
													</dl>
												</li>
												<li >
													<!-- 현재 on -->
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
														<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
													</dl>
												</li>
											</ul>
								</c:if>
								<c:if test="${detailVO.edcProgmType eq '3001'}">
									<ul class="step step4">
										<li>
											<dl>
												<dt>1</dt>
												<dd>교육 선택</dd>
											</dl>
										</li>
										<li >
											<!-- 현재 on -->
											<dl>
												<dt>2</dt>
												<dd>인원입력 및 동의</dd>
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
										<li >
											<!-- 현재 on -->
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
										<li>
											<!-- 현재 on -->
											<dl>
												<dt>2</dt>
												<dd>안내 및 주문</dd>
											</dl>
										</li>
										<li  class="on">
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
<input type="hidden" id="comCd"         name="comCd"           value="${detailVO.comcd}"/>
<input type="hidden" id="edcPrgmid"     name="edcPrgmid"       value="${detailVO.edcPrgmid}" />
<input type="hidden" id="edcVisitfamilyCnt"  name="edcVisitfamilyCnt"    value="${detailVO.edcVisitfamilyCnt}" />
<input type="hidden" id="edcRsvnSdatetime"   name="edcRsvnSdatetime"     value="${paramMap.edcRsvnSdatetime}" />
<input type="hidden" id="edcRsvnEdatetime"   name="edcRsvnEdatetime"     value="${paramMap.edcRsvnEdatetime}" />
<input type="hidden" id="edcSdate"       name="edcSdate"     value="${detailVO.edcSdate}" />
<input type="hidden" id="edcStime"       name="edcStime"     value="${detailVO.edcStime}" />
<input type="hidden" id="edcEdate"       name="edcEdate"     value="${detailVO.edcEdate}" />
<input type="hidden" id="edcEtime"       name="edcEtime"     value="${detailVO.edcEtime}" />
<input type="hidden" id="edcarsvnProgMItemCnt"  name="edcarsvnProgMItemCnt"  value="${edcarsvnProgMItemCnt}" />
<input type="hidden" id="edcVistnmpr"  name="edcVistnmpr"  value="<c:out value="${param.edcVistnmpr }"/>" />
<input type="hidden" id="edcRsvnReqid"  name="edcRsvnReqid"   />
<input type="hidden" id="memberEmail" name="memberEmail" value="<c:out value="${email }"/>"  />
							<!-- signup2 -->
							<div class="signup2">
								<p class="stit">예약자 정보 입력</p>
								<div id="requireInfo" class="table2 responsive-type mb50">

									<table>
										<caption>예약자 정보 확인 및 배송주소 입력하는 표입니다.</caption>
										<colgroup>
											<col style="width:25%">
											<col style="width:75%">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row" class="vtop">예약자 이름</th>
												<td class="b">
													<c:out value='${userName}' />
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">휴대전화번호</th>
												<td class="b"><tags:HpTelShow hpval="${ihidNum}"/></td>
											</tr>
<c:choose>
	<c:when test="${!empty email}">
											<tr>
												<th scope="row" class="vtop">이메일</th>
												<td colspan="3">
													<div class="w_box" style="margin-top:0px">
														<div class="w100" style="vertical-align:middle">
															<label for="edcEmail" class="hidden">이메일</label>
															<input type="text" id="edcEmail" name="edcEmail" value="<c:out value="${email}"/>" maxlength="50"  title="이메일 입력"/>
														</div>
														<div>
															<button type="button" onclick="fn_email_modify()" class="btn_ty_m2_c3">수정하기</button>
														</div>
													</div>
													<p id="edcEmail_msg"></p>
													<p>※ 이메일 변경 후 수정하기 버튼을 클릭해 주세요.<br>※ 회원정보의 이메일 주소도 함께 변경됩니다.</p>
												</td>
											</tr>
	</c:when>
	<c:otherwise>
											<tr>
												<th scope="row" class="vtop">이메일</th>
												<td class="b">
													<input type="text" id="edcEmail" name="edcEmail" value="${email}" placeholder="이메일주소를 입력 해 주십시오." maxlength="50" title="이메일 입력"/>
													<p class="red" id="edcEmail_msg" style="display:none"></p>
												</td>
											</tr>
	</c:otherwise>
</c:choose>

<!-- 단체 -->
<c:if test="${detailVO.edcProgmType eq '3001'}">
											<tr>
												<th scope="row">
													<label for="edcRsvnOrgname">단체, 학교, 기관명</label>
												</th>
												<td>
													<input type="text" id="edcRsvnOrgname" name="edcRsvnOrgname" value="<c:out value="${orgName}"/>" placeholder="단체, 학교, 기관명을 입력해주세요" title="단체, 학교, 기관명을 입력해주세요" maxlength="100"/>
													<p class="red" id="edcRsvnOrgname_msg" style="display:none"></p>
												</td>
											</tr>
											<tr>
												<th scope="row">
													<label for="edcVistorGradenm">참가 학년</label>
												</th>
												<td>
													<input type="text" id="edcVistorGradenm" name="edcVistorGradenm" placeholder="참가 학년을 입력해 주세요." title="참가 학년을 입력해 주세요." maxlength="100"/>
													<p class="red" id="edcVistorGradenm_msg" style="display:none"></p>
												</td>
											</tr>
											<tr>
												<th scope="row">
													<label for="edcReqCustnm">방문 인솔자 명</label>
												</th>
												<td>
													<input type="text" id="edcReqCustnm" name="edcReqCustnm" placeholder="방문 인솔자 명을 입력해주세요" title="방문 인솔자 명을 입력해주세요" maxlength="15"/>
													<p class="red" id="edcRsvnCustnm_msg" style="display:none"></p>
												</td>
											</tr>

											<tr>
												<th scope="row">
													<label for="edcReqMoblphon">방문 인솔자 연락처</label>
												</th>
												<td>
													<input type="text" id="edcReqMoblphon" name="edcReqMoblphon" value="<tags:HpTelShow hpval="${ihidNum}"/>" class="phoneCheck" placeholder="방문 인솔자 연락처를 입력해주세요" title="방문 인솔자 연락처를 입력해주세요" maxlength="13"/>
													<p class="red" id="edcReqMoblphon_msg" style="display:none"></p>
												</td>
											</tr>
</c:if>
<c:if test="${detailVO.edcShtlbusYn eq 'Y'}">
<!-- 셔틀버스 운행 -->
<c:if test="${!empty detailVO.edcShtlbusGuide }">
											<tr>
												<th scope="row" class="vtop">셔틀버스<br>탑승위치 안내</th>
												<td colspan="3">
													${fn:replace(detailVO.edcShtlbusGuide, crlf , '<br/>')}
												</td>
											</tr>
</c:if>
											<tr>
												<th scope="row"><label for="edcInBusloct">셔틀버스 입소<br/>가능한 위치</label></th>
												<td><input type="text" id="edcInBusloct" name="edcInBusloct" placeholder="셔틀버스 입소 가능한 위치를 입력하세요" title="셔틀버스 입소 가능한 위치를 입력하세요" maxlength="25"/>
													<p class="red" id="edcInBusloct_msg" style="display:none"></p>
												</td>
											</tr>
											<tr>
												<th scope="row"><label for="edcOutBusloct">셔틀버스 퇴소<br/>가능한 위치</label></th>
												<td><input type="text" id="edcOutBusloct" name="edcOutBusloct" placeholder="셔틀버스 퇴소 가능한 위치를 입력하세요"  title="셔틀버스 퇴소 가능한 위치를 입력하세요" maxlength="25"/>
													<p class="red" id="edcOutBusloct_msg" style="display:none"></p>
												</td>
											</tr>
</c:if>

<c:forEach var="item" items="${edcarsvnProgMItemList}" varStatus="status">
												<tr>
													<th scope="row">
														<input type="hidden" id="edcMngitemid${status.index + 1}" name="edcMngitemid${status.index + 1}" value="${item.edcMngitemid}" />
														<label for="edcMngitemnm${status.index+1}">${item.edcMngitemnm}</label>
													</th>
													<td>
														<input type="text" id="edcMngitemnm${status.index + 1}" name="edcMngitemnm${status.index + 1}" placeholder="${item.edcMngitemguide}">
														<p class="red" id="edcMngitemnm${status.index + 1}_msg" style="display:none"></p>
													</td>
												</tr>
</c:forEach>
<c:if test="${detailVO.edcAddrOpenyn eq 'Y'}"> <!--  주소 수집 -->
											<tr>
												<th scope="row" class="vtop">주소</th>
												<td>
													<div class="w_box mb15">
														<div class="w50">
															<label for="edcHomeZipno" class="hidden">우편번호를 입력</label>
															<input type="text" id="edcHomeZipno" name="edcHomeZipno" placeholder="우편번호를 입력해주세요." title="우편번호 입력" readonly/>
														</div>
														<div>
															<button class="btn_ty_m2_c3" type="button" id="searchJusoPop" data-toggle="modal" data-target="#modalJusoSearch" title="주소찾기 레이어열림">주소 찾기</button>
														</div>
													</div>
													<label for="edcHomeAddr1" class="hidden">주소를 입력</label>
													<input type="text" id="edcHomeAddr1" name="edcHomeAddr1" class="mb15" placeholder="주소를 입력해주세요." title="주소입력" readonly/>
													<label for="edcHomeAddr2" class="hidden">상세 주소를 입력</label>
													<input type="text" id="edcHomeAddr2" name="edcHomeAddr2" placeholder="상세 주소를 입력해주세요." title="상세주소 입력" maxlength="100"/>
													<p class="red" id="edcAddr_msg" style="display:none"></p>
												</td>
											</tr>

</c:if>
										</tbody>
									</table>
									<!-- //table -->
								</div>
								<!-- //예약자 정보 입력  -->
<c:if test="${detailVO.edcProgmType eq '2001' or detailVO.edcProgmType eq '9001'}">
<!-- 캠프 또는 1회성 -->
	<c:forEach var="i" begin="1" end="${detailVO.edcVisitfamilyCnt}" step="1">
									<p class="stit">참가자${i } <c:if test="${i eq 1}"><span class="tb_txt">※ 참가자 본인도 참가하실 경우 작성하셔야 합니다.</span></c:if>
										<c:if test="${i eq 2}"><span class="tb_txt">※ 참가자 2명 까지 필수 입니다.</span></c:if>
									</p>

									<div class="table2 responsive-type mb50">
										<table>
											<colgroup>
												<col style="width:20%">
												<col style="width:80%">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row"><label for="edcRsvnfmName${i}">이름</label></th>
													<td><input type="text" id="edcRsvnfmName${i}" name="edcRsvnfmName${i}" placeholder="이름을 입력해주세요." title="참가자 이름 입력"/>
														<p class="red" id="edcRsvnfmName${i}_msg" style="display:none"></p>
													</td>
												</tr>
												<tr>
													<th scope="row"><label for="edcRsvnfmTelno${i}">휴대전화번호</label></th>
													<td><input type="tel" id="edcRsvnfmTelno${i}" name="edcRsvnfmTelno${i}" class="phoneCheck" placeholder="‘-’ 을 빼고 입력 해 주십시오." title="참가자 휴대전화 입력">
														<p class="red" id="edcRsvnfmTelno${i}_msg" style="display:none"></p>
													</td>
												</tr>
												<tr>
													<th scope="row">성별</th>
													<td>
														<div class="radio_box">
															<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${i}" id="edcRsvnfmSexgbn${i}1" value="1001"><em></em><span>남자</span></label>
															<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${i}" id="edcRsvnfmSexgbn${i}2" value="2001"><em></em><span>여자</span></label>

															<p class="red" id="edcRsvnfmSexgbn${i}_msg" style="display:none"></p>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
										<!-- //table -->
									</div>
</c:forEach>
</c:if>
							</div>
							<!-- //signup2 -->
</form:form>

						</div>
						<!-- //join_wrap -->
						<ul class="btn_area">
							<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전</a></li>
							<li><a href="javascript:fn_reserve_save()" class="btn_ty_m_c1">예약</a></li>
						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->

				<!-- //주소변경하기 -->
				<div class="modal" id="modalJusoSearch">
					<div class="modal-dialog" style="max-width:670px;">

						<div class="modal-content">
							<div class="modal-title">
								<h4>우편번호 찾기</h4>
								<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
							</div>
							<div class="modal-body">

								<div class="search_wrap w100">
<form>
										<fieldset>
											<legend>게시판 검색</legend>
											<div class="search_box">
												<label for="searchJusoWrd">검색어 : </label>
												<input id="searchJusoWrd" name="searchJusoWrd" title="검색어를 입력하세요" type="text" class="input_text" placeholder="예) 올림픽로 300">
												<button type="button" id="searchJuso" class="button"><span>검색</span></button>

												<inut id="jusoCurrentPage"  type="hidden">
												<inut id="jusoCountPerPage" type="hidden">

											</div>
										</fieldset>
</form>
								</div>

								<!-- //search_wrap -->
								<div class="table2 ty scroll" id="jusoSearchResult">
								</div>
								<div class="paginate" id="jusoPageApi">
								</div>
								<!-- //paginate -->
							</div>
						</div>

					</div>
				</div>
				<!-- //우편번호 찾기 -->
				<!-- //팝업 -->

		<form:form commandName="eventProgramVO" id="eduFinalForm" method="post" action="./edcarsvnRegEnd">
			<input type="hidden" name="comCd"         value="${detailVO.comcd}"/>
			<input type="hidden" name="edcRsvnReqid"  id="edcRsvnReqid" />
			<input type="hidden" name="edcPrgmid"     value="${detailVO.edcPrgmid}" />
		</form:form>

	<script type="text/javascript">

		$(function() {
			$("input[type=text],input[type=tel]").keyup(function() {
					var id = $(this).attr("id");
					$('#'+id + "_msg").hide();

					if (id.indexOf("edcHome") >= 0) {
						$('#edcAddr_msg').hide();
					}
			});

			$("input[type=radio]").click(function() {
				var id = $(this).attr("name");
				$('#'+id + "_msg").hide();
			});

			$('.phoneCheck').keyup(function() {
				$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
			});

			setTimeout("fn_login_check()", 100); //로그인여부 체크
		});

		function fn_reserve_save() {
			var flag = true;
			var emailFlag = true;
			var firstId = "";

			$.each($('#requireInfo').find("input[type=text]") , function(index,item) {
				var val =  $.trim($(this).val());
				var id = $(this).attr("id");

				if ( val== "") {
					var text =  $(this).attr("placeHolder");
					if (text == "") {
						text = $(this).parent().parent().find("label").text() + "을(를) 입력해 주세요.";
					}
					$('#'+id + "_msg").html(text).show();

					if (firstId == "") firstId = id;

					flag = false;
				}
			});

			var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
			var hpRule = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

<c:choose>
			<c:when test="${empty email}">
				if (!emailRule.test($('#edcEmail').val())) {
					if (firstId == "") firstId = "edcEmail";
					$('#edcEmail_msg').text("이메일 주소가 형식에 맞지 않습니다.").show();
					emailFlag = false;
				}
			</c:when>
			<c:otherwise>
				if ($('#edcEmail').val() != $('#memberEmail').val()) {
					$('#edcEmail_msg').text("[수정하기] 버튼을 클릭하셔서 이메일 정보를 변경해 주세요.").addClass("red").removeClass("green").show();
					if (firstId == "") firstId = "edcEmail";
					emailFlag = false;
				}
			</c:otherwise>
</c:choose>

			<c:if test="${detailVO.edcAddrOpenyn eq 'Y'}">
				if ( $('#edcHomeAddr1').val() == "" || $('#edcHomeAddr2').val() == "") {
					if (firstId == "") firstId = "edcHomeAddr2";
					$('#edcAddr_msg').text("주소를 모두 입력해 주세요.").show();
					flag = false;
				}

			</c:if>


			if ($('#edcVisitfamilyCnt').val() != "" && parseInt($('#edcVisitfamilyCnt').val()) > 0) {
				for(var i = 1 ; i <= parseInt($('#edcVisitfamilyCnt').val()); i++) {
					if (i <= 2) {
						if ($.trim($('#edcRsvnfmName' + i).val()) == "") {
							$('#edcRsvnfmName' + i + "_msg").html("참가자 "+i + "의 이름을 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmName' + i;
							flag = false;
						}
						if ($.trim($('#edcRsvnfmTelno' + i).val()) == "") {
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처를 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							flag = false;
						} else if (!hpRule.test($('#edcRsvnfmTelno' + i).val())) {
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처가 형식에 맞지 않습니다.").show();
							flag = false;
						}

						if ($('input[name=edcRsvnfmSexgbn'+i+']:checked').length < 1) {
							$('#edcRsvnfmSexgbn' + i + "_msg").html("참가자 "+i + "의 성별을 선택해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmSexgbn' + i + "1";
							flag = false;
						}
					} else if ($.trim($('#edcRsvnfmName' + i).val()) != "" || $.trim($('#edcRsvnfmTelno' + i).val()) != "") {
						if ($.trim($('#edcRsvnfmName' + i).val()) == "") {
							$('#edcRsvnfmName' + i + "_msg").html("참가자 "+i + "의 이름을 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmName' + i;
							flag = false;
						}
						if ($.trim($('#edcRsvnfmTelno' + i).val()) == "") {
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처를 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							flag = false;
						} else if (!hpRule.test($('#edcRsvnfmTelno' + i).val())) {
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처가 형식에 맞지 않습니다.").show();
							flag = false;
						}

						if ($('input[name=edcRsvnfmSexgbn'+i+']:checked').length < 1) {
							$('#edcRsvnfmSexgbn' + i + "_msg").html("참가자 "+i + "의 성별을 선택해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmSexgbn' + i + "1";
							flag = false;
						}
					}
				}
			}

			if (flag && emailFlag) {
				$('body').showLoading();
				fn_ajaxEduOnly("./edcarsvnRegSave", $("#eduSaveForm"), fn_rsvnsave_callback);
			} else if (flag && !emailFlag) {
				$('#edcEmail').focus();
			} else {
				alert("신청정보를 모두 입력해 주세요.");
				$('#'+firstId).focus();
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
			} else {
				$('body').showLoading();
				fn_ajaxEduOnly("./edcarsvnEmailSave", $("#eduSaveForm"), fn_email_callback);
			}
		}

		function fn_email_callback(data) {
			$('body').hideLoading();
			if (data.RESULT.code == "SUCCESS") {
				$('#edcEmail_msg').text("정상적으로 수정되었습니다.").addClass("green").removeClass("red").show();
				$('#edcEmail').val(data.RESULT.msg);
				$('#memberEmail').val(data.RESULT.msg);
			} else {
				$('#edcEmail_msg').text(data.RESULT.msg).addClass("red").removeClass("green").show();
			}
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


		//----------------------------------------------------------------주소 연계 시작
		/*
		$("#searchJusoPop").click(function() {
			$('#modalJuso').modal();
		});
		*/

		$('#modalJuso').on('show.bs.modal', function (e) {
			$("#jusoSearchResult").hide();
		})

		$("#searchJuso").click(function() {

			//var varConfmKey     = "devU01TX0FVVEgyMDIwMDgyNDE3MTExNzExMDA5Mjk=";
			var varConfmKey     = "<%= strJusoKey %>";

			console.log(varConfmKey);

			var varSearchWord   = $("#searchJusoWrd").val();
			var varResultType   = "json";

			var varCurrentPage  = $("#jusoCurrentPage").val();
			if ((varCurrentPage === undefined) || (varCurrentPage == null) || (varCurrentPage == '')) {
				varCurrentPage = "1";
				$("#jusoCurrentPage").val(varCurrentPage);
			}

			var varCountPerPage  = $("#jusoCountPerPage").val();
			if ((varCountPerPage === undefined) || (varCountPerPage == null) || (varCountPerPage == '')) {
				varCountPerPage = "5";
				$("#jusoCountPerPage").val(varCountPerPage);
			}

			if ((varSearchWord === undefined) || (varSearchWord == null) || (varSearchWord == '')) {
				alert("[도로명주소, 건물명 또는 지번입력] 정보를 입력하세요");
				return;
			}

			if (!fn_checkSearchedWord(varSearchWord)) {
				return ;
			}

			fn_ajaxIfOnly("/intrfc/adres/getAddrApi", {keyword : varSearchWord, confmKey : varConfmKey, currentPage : varCurrentPage, countPerPage : varCountPerPage, resultType : varResultType},  fn_callBack);

		});

		function fn_callBack(data) {

			var varCountPerPage = data.results.common.countPerPage;
			var varCurrentPage  = data.results.common.currentPage;
			var varErrorCode    = data.results.common.errorCode;
			var varErrorMessage = data.results.common.errorMessage;

			var varJusoRsltH = "<table>";
				varJusoRsltH += "	<caption>주소</caption>";
				varJusoRsltH += "<colgroup>";
				varJusoRsltH += "<col style='width:80%'>";
				varJusoRsltH += "<col style='width:20%'>";
				varJusoRsltH += "</colgroup>";
				varJusoRsltH += "<tbody>";

			var varJusoRsltB = "";

			var varJusoRsltT = "</tbody>";
			    varJusoRsltT += "</table>";

			if (varErrorCode == 0) {

				var varJuso = data.results.juso;

				var varZipNo     = "";
				var varJibunAddr = "";
				var varRoadAddr  = "";
				for(var i = 0; i < varJuso.length; i++) {

					varZipNo     = varJuso[i].zipNo;
					varJibunAddr = varJuso[i].jibunAddr;
					varRoadAddr  = varJuso[i].roadAddr;

					varJusoRsltB += "<tr>";
					varJusoRsltB += "	<td>";
					varJusoRsltB += "		<ul class='addr_list'>";
					varJusoRsltB += "			<li>";
					varJusoRsltB += "				<dl>";
					varJusoRsltB += "					<dt>도로명</dt>";
					varJusoRsltB += "					<dd class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varRoadAddr + "</a></dd>";
					varJusoRsltB += "				</dl>";
					varJusoRsltB += "			</li>";
					varJusoRsltB += "			<li>";
					varJusoRsltB += "				<dl>";
					varJusoRsltB += "					<dt>지번</dt>";
					varJusoRsltB += "					<dd><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varJibunAddr + "</a></dd>";
					varJusoRsltB += "				</dl>";
					varJusoRsltB += "			</li>";
					varJusoRsltB += "		</ul>";
					varJusoRsltB += "	</td>";
					varJusoRsltB += "	<td class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varZipNo + "</a></td>";
					varJusoRsltB += "</tr>";

					//varJusoRsltB += "<tr class='n_top'>";
					//varJusoRsltB += "	<td class='cat'>" + varZipNo + "</td>";
					//varJusoRsltB += "	<td class='title left'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>지번 ▶ " + varJibunAddr + "</br>도로명 ▶ " + varRoadAddr + "</a></td>";
					//varJusoRsltB += "</tr>";

				}

				if (varJuso.length >= 1) {
				} else {

					varJusoRsltB += "<tr>";
					varJusoRsltB += "	<td>";
					varJusoRsltB += "		주소 정보를 찾을 수 없습니다.";
					varJusoRsltB += "	</td>";
					varJusoRsltB += "	<td class='md'>" + varZipNo + "</td>";
					varJusoRsltB += "</tr>";

					//varJusoRsltB += "<tr class='n_top'>";
					//varJusoRsltB += "	<td class='cat' colspan='2'>주소 정보를 찾을 수 없습니다.</td>";
					//varJusoRsltB += "	</td>";
					//varJusoRsltB += "</tr>";

				}

				$("#jusoSearchResult").empty();
				$("#jusoPageApi").empty();
				$("#jusoSearchResult").show();
				$("#jusoSearchResult").append(varJusoRsltH + varJusoRsltB + varJusoRsltT);

				fn_PageMake(data);

			}
		}

		function fn_jusoFSelect(zipCode, readAddr) {

			console.log("최종 선택 도로명 주소******************************************************************>>>");
			console.log(zipCode);
			console.log(readAddr);

			$("#jusoSearchResult").empty();
			$("#jusoPageApi").empty();
			$("#searchJusoWrd").val("");

			$("#edcHomeZipno").val(zipCode);
			$("#edcHomeAddr1").val(readAddr);

			$("#edcHomeAddr2").focus();

			return false;

		}

		//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
		function fn_checkSearchedWord(objValue) {

			if (objValue.length > 0) {

				//특수문자 제거
				var expText = /[%=><]/ ;
				if (expText.test(objValue) == true) {
					alert("특수문자를 입력 할수 없습니다.") ;
					objValue = objValue.split(expText).join("");
					return false;
				}

				//특정문자열(sql예약어의 앞뒤공백포함) 제거
				var sqlArray = new Array(
					"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
		             		 "UNION",  "FETCH", "DECLARE", "TRUNCATE"
				);

				var regex;
				for(var i = 0; i < sqlArray.length; i++) {
					regex = new RegExp( sqlArray[i] ,"gi") ;

					if (regex.test(objValue) ) {
					    alert("\"" + sqlArray[i] + "\"와(과) 같은 특정문자로 검색할 수 없습니다.");
						objValue = objValue.replace(regex, "");
						return false;
					}
				}
			}
			return true ;
		}

		/*
		function enterSearch() {
			var evt_code = (window.netscape) ? ev.which : event.keyCode;
			if (evt_code == 13) {
				event.keyCode = 0;
				getAddr(); //jsonp사용시 enter검색
			}
		}
		*/

		$("#searchJusoWrd").keydown(function(event) {
			if (event.which == 13) {
				event.keyCode = 0;
				$('#searchJuso').trigger('click');
				return false;
			}

		});

		function fn_GoPage(pageNum) {

			$("#jusoCurrentPage").val(pageNum);
			$('#searchJuso').trigger('click');

		}

		function fn_PageMake(jsonStr) {

			var total = jsonStr.results.common.totalCount;

			var pageNum = Number($("#jusoCurrentPage").val());
			var paggingStr = "";

			if (total < 1) {
			} else {

				var PAGEBLOCK = Number($("#jusoCountPerPage").val());
				var pageSize  = Number($("#jusoCountPerPage").val());

				var totalPages = Math.floor((total-1)/pageSize) + 1;
				var firstPage = Math.floor((pageNum-1)/PAGEBLOCK) * PAGEBLOCK + 1;

				if ( firstPage <= 0 ) firstPage = 1;
				var lastPage = firstPage-1 + PAGEBLOCK;

				if ( lastPage > totalPages ) lastPage = totalPages;

				var nextPage = lastPage + 1 ;
				var prePage = firstPage - 5 ;

				if ( firstPage > PAGEBLOCK ) {
					paggingStr +=  "<a href='javascript:fn_GoPage("+prePage+");'>이전</a>  " ;
				}

				if (totalPages >= 2) {
					for(i = firstPage; i <= lastPage; i++) {

						if (pageNum == i) {
							paggingStr += "<a style='font-weight:bold;color:blue;font-size:15px;' href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
						} else {
							paggingStr += "<a href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
						}
					}
				}

				if (lastPage < totalPages) {
					paggingStr +=  "<a href='javascript:fn_GoPage(" + nextPage + ");'>다음</a>";
				}

				$("#jusoPageApi").html(paggingStr);

			}
		}
		//----------------------------------------------------------------주소 연계 끝

	</script>
