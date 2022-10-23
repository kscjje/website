<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>

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

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script src="<%=strContext%>/js/common_web_edu.js"></script>

<%
	String strJusoKey = EgovProperties.getProperty("juso.go.kr.key");
%>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">교육예약</h3>
					<div class="join_wrap bor">
						<!-- step -->
						<ul class="step step4">
							<li>
								<dl>
									<dt>1</dt>
									<dd>교육 선택</dd>
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
							<input type="hidden" id="edcVistnmpr"    name="edcVistnmpr"  value="<c:out value="${param.edcVistnmpr}"/>" />
							<input type="hidden" id="edcRsvnReqid"   name="edcRsvnReqid"   />
							<input type="hidden" id="memberEmail"    name="memberEmail"  value="<c:out value='${email}'/>"  />					
							<input type="hidden" id="eduUserBtn"     name="eduUserBtn"   value="${param.eduUserBtn}"  />	
						
							<!-- signup2 -->
							<div class="signup2">
								<p class="stit">신청자 정보<span class="tb_txt"><strong class="red"></strong></span></p>
								<div id="requireInfo" class="table2 responsive-type mb50">
									<table>
										<caption>예약자 정보를 확인 해 주십시오</caption>
										<colgroup>
											<col style="width:25%">
											<col style="width:75%">
										</colgroup>
										<tbody>
											<%-- <tr>
												<th scope="row"><strong class="red">* </strong>예약자명</th>
												<td><c:out value="${loginVO.name}"/></td>
											</tr>
											<tr>
												<th scope="row"><strong class="red">* </strong><label for="edcRsvnOrgname">단체, 학교, 기관 명</label></th>
												<td><input type="text" id="edcRsvnOrgname" name="edcRsvnOrgname" placeholder="방문하시려는 단체의 단체명을 정확하게 적어주십시오." /></td>
											</tr>
											<tr>
												<th scope="row"><strong class="red">* </strong>예약자 연락처</th>
												<td>
													<tags:HpDecrypt param="${loginVO.ihidNum}"/>
												</td>
											</tr>
											<tr>
												<th scope="row"><strong class="red">* </strong><label for="edcLeadCustnm">방문 인솔자명</label></th>
												<td><input type="text" id="edcLeadCustnm" name="edcLeadCustnm" placeholder="실제 방문하시는 단체의 인솔자의 성함을 적어주십시오." /></td>
											</tr>
											<tr>
												<th scope="row"><strong class="red">* </strong><label for="edcLeadMoblphon">방문 인솔자 연락처</label></th>
												<td><input type="text" id="edcLeadMoblphon" name="edcLeadMoblphon" placeholder="실제 방문하시는 단체의 인솔자의 연락처를 적어주십시오." /></td>
											</tr> --%>
											<tr>
												<th scope="row">이름</th>
												<td><c:out value="${loginVO.name}"/></td>
											</tr>
											<tr>
												<th scope="row">연락처</th>
												<td>
													<tags:HpDecrypt param="${loginVO.ihidNum}"/>
												</td>
											</tr>
										</tbody>
									</table>
									<!-- //table -->
								</div>
								<p class="stit">이용 정보<span class="tb_txt"><strong class="red">*필수 입력항목</strong></span></p>
								<div id="requireInfo" class="table2 responsive-type mb50">
									<table>
										<caption>예약자 정보를 확인 해 주십시오</caption>
										<colgroup>
											<col style="width:25%">
											<col style="width:75%">
										</colgroup>
										<input type="hidden" id="edcProgmType" value="${detailVO.edcProgmType }">
										<tbody>
											<c:if test="${detailVO.edcProgmType eq '1001'}">
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmName">참여학생이름</label></th>
													<td>
														<input type="text" id="edcRsvnfmName" name="edcRsvnfmName"/>
														<p id="err_edcRsvnfmName" class="red" style="display:none">참여학생이름을 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmEtc">기타(학년)</label></th>
													<td>
														<input type="text" id="edcRsvnfmEtc" name="edcRsvnfmEtc"/>
														<p id="err_edcRsvnfmEtc" class="red" style="display:none">기타(학년)을 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmTelno">연락가능한번호</label></th>
													<td>
														<input type="text" id="edcRsvnfmTelno" name="edcRsvnfmTelno"/>
														<p id="err_edcRsvnfmTelno" class="red" style="display:none">연락가능한번호를 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmDepositor">입금자명</label></th>
													<td>
														<input type="text" id="edcRsvnfmDepositor" name="edcRsvnfmDepositor"/>
														<p id="err_edcRsvnfmDepositor" class="red" style="display:none">입금자명을 입력해주세요.</p>
													</td>
												</tr>
											</c:if>
											<c:if test="${detailVO.edcProgmType eq '3001'}">
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmDepositor">입금자명</label></th>
													<td>
														<input type="text" id="edcRsvnfmDepositor" name="edcRsvnfmDepositor"/>
														<p id="err_edcRsvnfmDepositor" class="red" style="display:none">입금자명을 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><label for="edcRsvnfmEtc">기타</label></th>
													<td>
														<input type="text" id="edcRsvnfmEtc" name="edcRsvnfmEtc"/>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmDesc">필수) 참여 가족 <br>총 인원 수(최대4명)</label></th>
													<td>
														<input type="text" id="edcRsvnfmDesc" name="edcRsvnfmDesc"/>
														<p id="err_edcRsvnfmDesc" class="red" style="display:none">참여 가족 총 인원 수를 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmPname">필수) 보호자 이름</label></th>
													<td>
														<input type="text" id="edcRsvnfmPname" name="edcRsvnfmPname"/>
														<p id="err_edcRsvnfmPname" class="red" style="display:none">보호자 이름을 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><label for="edcRsvnfmPname2">선택) 보호자 이름(보호자가 2명인 경우 기입)</label></th>
													<td>
														<input type="text" id="edcRsvnfmPname2" name="edcRsvnfmPname2"/>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmName">필수) 초등학생 이름 / 학년</label></th>
													<td>
														<input type="text" id="edcRsvnfmName" name="edcRsvnfmName"/>
														<p id="err_edcRsvnfmName" class="red" style="display:none">초등학생 이름 / 학년을 입력해주세요.</p>
													</td>
												</tr>
												<tr>
													<th scope="row"><label for="edcRsvnfmName2">선택) 초등학생 이름 / 학년(초등학생이 2명인 경우)</label></th>
													<td>
														<input type="text" id="edcRsvnfmName2" name="edcRsvnfmName2"/>
													</td>
												</tr>
												<tr>
													<th scope="row"><label for="edcRsvnfmName3">선택) 초등학생 이름 / 학년(초등학생이 3명인 경우)</label></th>
													<td>
														<input type="text" id="edcRsvnfmName3" name="edcRsvnfmName3"/>
													</td>
												</tr>
												<tr>
													<th scope="row"><strong class="red">* </strong><label for="edcRsvnfmTelno">필수) 연락처(연락가능한 번호)</label></th>
													<td>
														<input type="text" id="edcRsvnfmTelno" name="edcRsvnfmTelno"/>
														<p id="err_edcRsvnfmTelno" class="red" style="display:none">연락처(연락가능한 번호)를 입력해주세요.</p>
													</td>
												</tr>
											</c:if>
										</tbody>
									</table>
								</div>
								
								<!-- //예약자 정보 -->
							</div>
							<!-- //signup2 -->
							
						</form:form>
						
					</div>
					<!-- //join_wrap -->
					<ul class="btn_area long">
						<li><a href="javascript:history.back()" class="btn_ty_m3_c1">이전페이지 이동</a></li>
						<li><a href="javascript:fn_reserve_save()" class="btn_ty_m3_c5">예약하기</a></li>
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
			
			if ($('#edcProgmType').val() =='1001') {
				if ($('#edcRsvnfmName').val() == "") {
					$('#err_edcRsvnfmName').text("참여학생이름을 입력해 주세요.").show();
					$('#edcRsvnfmName').focus();
					return;
				}else if ($('#edcRsvnfmEtc').val() == "") {
					$('#err_edcRsvnfmName').text("참여학생이름을 입력해 주세요.").hide();
					$('#err_edcRsvnfmEtc').text("기타(학년)을 입력해 주세요.").show();
					$('#edcRsvnfmEtc').focus();
					return;
				}else if ($('#edcRsvnfmTelno').val() == "") {
					$('#err_edcRsvnfmEtc').text("기타(학년)을 입력해 주세요.").hide();
					$('#err_edcRsvnfmTelno').text("연락가능한 번호를 입력해 주세요.").show();
					$('#edcRsvnfmTelno').focus();
					return;
				}else if ($('#edcRsvnfmDepositor').val() == "") {
					$('#err_edcRsvnfmTelno').text("연락가능한 번호를 입력해 주세요.").hide();
					$('#err_edcRsvnfmDepositor').text("입금자명을 입력해 주세요.").show();
					$('#edcRsvnfmDepositor').focus();
					return;
				}
			} else if ($('#edcProgmType').val() =='3001') {
				if ($('#edcRsvnfmDepositor').val() == "") {
					$('#err_edcRsvnfmDepositor').text("입금자명을 입력해 주세요.").show();
					$('#edcRsvnfmDepositor').focus();
					return;
				}else if ($('#edcRsvnfmDesc').val() == "") {
					$('#err_edcRsvnfmDepositor').text("입금자명을 입력해 주세요.").hide();
					$('#err_edcRsvnfmDesc').text("참여 가족 총 인원 수를 입력해 주세요.").show();
					$('#edcRsvnfmDesc').focus();
					return;
				}else if ($('#edcRsvnfmPname').val() == "") {
					$('#err_edcRsvnfmDesc').text("참여 가족 총 인원 수를 입력해 주세요.").hide();
					$('#err_edcRsvnfmPname').text("보호자 이름을 입력해 주세요.").show();
					$('#edcRsvnfmPname').focus();
					return;
				}else if ($('#edcRsvnfmName').val() == "") {
					$('#err_edcRsvnfmPname').text("보호자 이름을 입력해 주세요.").hide();
					$('#err_edcRsvnfmName').text("초등학생 이름/학년을 입력해 주세요.").show();
					$('#edcRsvnfmName').focus();
					return;
				}else if ($('#edcRsvnfmTelno').val() == "") {
					$('#err_edcRsvnfmName').text("초등학생 이름/학년을 입력해 주세요.").hide();
					$('#err_edcRsvnfmTelno').text("연락처를 입력해 주세요.").show();
					$('#edcRsvnfmTelno').focus();
					return;
				}
				
			}
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

			if ($('#edcVisitfamilyCnt').val() != "" && parseInt($('#edcVisitfamilyCnt').val()) > 0) {
				for(var i = 1; i <= parseInt($('#edcVisitfamilyCnt').val()); i++) {
					if (i <=2) {
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
				////$('body').showLoading();
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
				////$('body').showLoading();
				fn_ajaxEduOnly("./edcarsvnEmailSave", $("#eduSaveForm"), fn_email_callback);
			}
		}

		function fn_email_callback(data) {
			
			////$('body').hideLoading();
			
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

			////$('body').hideLoading();

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

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
	