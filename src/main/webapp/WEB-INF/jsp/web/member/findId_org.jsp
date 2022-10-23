<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : findId.jsp
  * @Description : 아이디 찾기 페이지
  * @Modification Information
  * @
  * @  수정일              수정자                   수정내용
  * @ -------      --------    ---------------------------
  * @ 2021.05.03    전영석                 최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.03
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<!-- %@ include file="/WEB-INF/templates/web/base/top.jsp"%> -->
<!-- %@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%> -->

<script>

	function fn_member_submit() {
		
		if ($('#name').val() == "") {
			alert("이름을 입력해 주세요.");
			$('#name').focus();
		} else if ($('input[name=memGbn]:checked').val()=="email" && $('#email').val() == "") {
			alert("이메일 주소를 입력해 주세요.");
			$('#email').focus();
		} else if ($('input[name=memGbn]:checked').val()=="hphone" && $('#ihidNum').val() == "") {
			alert("휴대폰 번호를 입력해 주세요.");
			$('#ihidNum').focus();
			
		} else if (($('select[name=birthYYYY] option:selected').val()=="") || ($('select[name=birthYYYY] option:selected').val()=="연도")) {
			alert("생년월일 년도를 선택하세요");
			$('radio[name=birthYYYY]').focus();			
			
		} else if (($('select[name=birthMM] option:selected').val()=="") || ($('select[name=birthMM] option:selected').val()=="월")) {
			alert("생년월일 월을 선택하세요");
			$('radio[name=birthMM]').focus();			
			
		} else if (($('select[name=birthDD] option:selected').val()=="") || ($('select[name=birthDD] option:selected').val()=="일")) {
			alert("생년월일 일을 선택하세요");
			$('radio[name=birthDD]').focus();			
			
		} else if ($('#ihidNum').val() == "") {
			alert("휴대전화 번호를 입력하세요");
			$('#ihidNum').focus();			
			
		} else {
			//fn_ajaxSendData('./findIdResult', $('#loginVO').serialize(), callBack , "${_csrf.headerName}", "${_csrf.token}");
			$('#loginVO').submit();
		}
		
	}

	function callBack(data) {

		if (data.result.success) {
    		$('#errPass').html(data.result.msg).removeClass("red").addClass("green");
        } else {
        	$('#errPass').html(data.result.msg).removeClass("green").addClass("red");
        }
		$('#findResultDiv').show();
	}

	$(document).ready(function() {
		$('input[name=memGbn]').click(function() {
			if ($(this).val() == "email") {
				$('#ROW_email').show();
				$('#ROW_hphone').hide();
			} else {
				$('#ROW_email').hide();
				$('#ROW_hphone').show();
			}
		});

		setTimeout("$('input[name=memGbn]').eq(0).prop('checked' , true)", 100); //historyback 대비

		$('.phoneCheck').keyup(function() {
			$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
		});

	});
</script>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit mb70">아이디 찾기</h3>
					
					<form:form commandName="loginVO" id="loginVO" action="./findIdResult" method="post">
						
						<ul class="tab tab5 mb35">
							<li class="on"><a href="./findId">가입정보로 찾기</a></li>
							<li><a href="./findIdByCerti">본인인증으로 찾기</a></li>
						</ul>
						<div class="mypage_wrap">
							<p class="desc mb35">※ 가입 시 등록된 정보로 아이디를 찾을 수 있습니다.</p>
							<div class="signup2">
								<p class="stit">개인정보 입력 (<strong class="red">*</strong> 필수입력)</p>
								<div class="table2 responsive-type mb50">
									<table>
										<caption>필수 입력 정보를 입력하는 표입니다.</caption>
										<colgroup>
											<col style="width:30%">
											<col style="width:70%">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row"><label for="name">이름<strong class="red">*</strong></label></th>
												<td><input type="text" id="name" name="name" placeholder="이름을 입력해주세요." /></td>
											</tr>
											<tr>
												<th scope="row">생년월일<strong class="red">*</strong></th>
												<td>
													<div class="w_box">
														<div class="w33">
															<label for="birthYYYY" class="hidden">연도</label>
															<select id="birthYYYY" name="birthYYYY">
																<option>연도</option>
																
																<c:forEach var="item" begin="1930" end="2022" step="1" varStatus="status">
																	<option value="${item}">${item}</option>	
																</c:forEach>	
																
															</select>
														</div>
														<div class="w33">
															<label for="birthMM" class="hidden">월</label>
															<select id="birthMM" name="birthMM">
																<option>월</option>
																
																<c:forEach var="item" begin="1" end="12" step="1" varStatus="status">
																	<fmt:formatNumber var="itemVal" minIntegerDigits="2" value="${status.count}" type="number"/>
																	<option value="${itemVal}">${item}</option>
																</c:forEach>																	
																
															</select>
														</div>
														<div class="w33">
															<label for="birthDD" class="hidden">일</label>
															<select id="birthDD" name="birthDD">
																<option>일</option>

																<c:forEach var="item" begin="1" end="31" step="1" varStatus="status">
																	<fmt:formatNumber var="itemVal" minIntegerDigits="2" value="${status.count}" type="number"/>
																	<option value="${itemVal}">${item}</option>
																</c:forEach>

															</select>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th scope="row"><label for="ihidNum">휴대전화번호<strong class="red">*</strong></label></th>
												<td><input type="tel" id="ihidNum" name="ihidNum" placeholder="‘-’ 을 빼고 입력 해 주십시오." />
													<p class="green">※ 회원가입 시 등록한 휴대폰 번호를 입력합니다</p>
												</td>
											</tr>
										</tbody>
									</table>
									<!-- //table -->
								</div>
								<!-- 필수 입력 정보 -->
							</div>
						</div>
						
					</form:form>	
						
					<ul class="btn_area">
						<li><a href="#" onclick="fn_member_submit(); return false;" class="btn_ty_m_c1">확인</a></li>
					</ul>
					
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->

