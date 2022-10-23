<%
 /**
  * @Class Name : joinUpdate.jsp
  * @Description 기조 ㄴ회원 본인인증 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.12    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.13
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<script>
function fn_auth_pop(type) {
	//type : normal / parent

	if ($('#identy').prop("checked")) {
		var prsonCertiWin = window.open('/web/common/prsoncerti/prsonCertiPop', 'prsonCertiWin', 'width=650, height=650, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=100, top=50');

		if (prsonCertiWin == null)
		{
			alert('** 휴대전화 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
			return false;
		}
	} else {
		alert("등록된 정보와 본인의 정보 일치 여부 체크 후  인증하기 버튼을 클릭해 주세요.");
		$('#identy').focus();
	}



}
function fn_auth_complete(mid,onlykey,paytype) {
	$('#LGD_MID').val(mid);
	$('#LGD_AUTHONLYKEY').val(onlykey);
	$('#LGD_PAYTYPE').val(paytype);
	$('#memberVO').attr("action" , "./joinUpdateCertCheck");
	$('#memberVO').submit();
}

$(document).ready(function() {


});
</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit mb20">노원수학문화관 통합예약<br/>누리집 최초 본인인증</h3>
		<p class="desc mb100">새로운 보안체계 적용으로 본인인증을 통해 비밀번호를 재설정하고 있습니다.<br>통합예약을 안전하게 이용하기 위한 꼭 필요한 절차 입니다!</p>
		<div class="join_wrap bor">
			<!-- step -->
			<ul class="step">
				<li class="on"><!-- 현재 on -->
					<dl>
						<dt>1</dt>
						<dd>최초 본인인증</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>정보입력</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>3</dt>
						<dd>수정완료</dd>
					</dl>
				</li>
			</ul>
			<!-- //step -->
			<!-- signup -->
<form:form path="memberVO" name="memberVO" id="memberVO" method="post" action="./joinUpdateCertCheck">
<input type="hidden" id="LGD_MID" name="LGD_MID" value="" />
<input type="hidden" id="LGD_AUTHONLYKEY" name="LGD_AUTHONLYKEY" value="" />
<input type="hidden" id="LGD_PAYTYPE" name="LGD_PAYTYPE" value="" />
<input type="hidden" id="name" name="name" value="<c:out value="${memberVO.memNm }" />" />
<input type="hidden" id="memNo" name="memNo" value="<c:out value="${memberVO.memNo }" />" />
<input type="hidden" id="birthYmd" name="birthYmd" value="<c:out value="${memberVO.birthDate }" />" />
<input type="hidden" id="gender" name="gender" value="<c:out value="${memberVO.gender }" />" />
<input type="hidden" id="snsId" name="snsId" value="<c:out value="${param.snsId}" />" />
<input type="hidden" id="type" name="type" value="<c:out value="${memberVO.type }" />" />
	<div class="signup2 mb20">
		<p class="stit">본인정보 확인</p>
		<div class="table2 responsive-type mb50">
			<table>
				<caption>본인정보 확인.</caption>
				<colgroup>
					<col style="width:25%">
					<col style="width:75%">
				</colgroup>
				<tbody>
				<!--
					<tr>
						<th scope="row" class="vtop">회원번호</th>
						<td>
							<c:out value="${memberVO.memNo }" />
						</td>
					</tr>
				 -->
				<c:if test="${!empty memberVO.id}">
					<tr>
						<th scope="row" class="vtop">아이디</th>
						<td class="b">
							<c:out value="${memberVO.id }" />
						</td>
					</tr>
				</c:if>
					<tr>
						<th scope="row" class="vtop">이름</th>
						<td class="b">
							<c:out value="${memberVO.memNm}" />
						</td>
					</tr>
					<tr>
						<th scope="row" class="vtop">생년월일</th>
						<td class="b">
							<fmt:parseDate var="dateStr1" value="${memberVO.birthDate}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateStr1}" pattern="yyyy년 MM월 dd일" />
						</td>
					</tr>

				</tbody>
			</table>
			<!-- //table -->
		</div>



				<ul class="dl_wrap">
					<li>
						<p class="img"><img src="<c:url value="<%=strContext%>/images/sub/img_idt.png"/>" alt="휴대폰본인인증이미지"></p>
						<dl>
							<dt>휴대전화 본인인증 </dt>
							<c:choose>
								<c:when test="${memberVO.type eq 'normal' }">
									<dd>노원수학문화관에 등록된 본인 정보와 일치하면 본인인증 후<br> 새롭게 사용하실 비밀번호를 설정하시기 바랍니다.</dd>
								</c:when>
								<c:when test="${memberVO.type eq 'child' }">
									<dd>노원수학문화관에 등록된 본인 정보와 일치하면 보호자의 휴대폰 정보로 <br>본인인증후 새로운 비밀번호를 설정해 주세요.
								</dd>
								</c:when>
							</c:choose>

						</dl>

					</li>
				</ul>
				<c:if test="${memberVO.type eq 'child' }">
					<div class="ex_txt mb40">
						<p class="ex_tit"><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 개인정보 보호법  제39조의3(개인정보의 수집ㆍ이용 동의 등에 대한 특례)</p>
						정보통신서비스 제공자는 만 14세 미만의 아동으로부터 개인정보 수집ㆍ이용ㆍ제공 등의 동의를 받으려면 그 법정대리인의 동의를 받아야 하고, 대통령령으로 정하는 바에 따라 법정대리인이 동의하였는지를 확인하여야 한다.
					</div>
				</c:if>
				<ul class="agree_choice ty center mb40">
					<li>
						<input type="checkbox" id="identy" name="identy" value="Y"/>
						<label for="identy">등록된 정보와 본인의 정보가 일치합니다.</label>
					</li>
				</ul>
				<p class="center"><a href="javascript:fn_auth_pop('<c:out value="${memberVO.type}"/>')" class="btn_ty_m_c3" title="새창 열림">인증하기</a></p>
	</div>
</form:form>
		</div>
		<!-- //join_wrap -->

	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->