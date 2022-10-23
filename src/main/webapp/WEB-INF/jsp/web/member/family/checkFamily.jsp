<%
 /**
  * @Class Name : checkFamily.jsp
  * @Description : 회원가입  성공
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.02   김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.03
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">

<form:form commandName="memberVO" name="memberVO" id="memberForm" action="./familyAction" method="post">
<form:hidden path="memNo" />
<input type="hidden" name="itemCd" value="${itemData.itemCd }" />
						<div class="inner">
							<h3 class="tit mb20">가족회원 등록</h3>
							<p class="desc mb70">국립중앙과학관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
							<div class="mypage_wrap bor">
								<!-- signup -->
								<div class="signup2">

									<h4 class="h4 mb5">가족회원으로 등록해 주신 분들을<br> 개별 유료회원으로 혜택을 드리려고 합니다.</h4>
									<!-- join_end -->
									<div class="join_end">
										<ul>
											<li>
												<dl>
													<dt>아이디</dt>
													<dd>${memberVO.id}</dd>
												</dl>
											</li>
											<li>
												<dl>
													<dt>이름</dt>
													<dd>${memberVO.memNm }</dd>
												</dl>
											</li>
										</ul>
									</div>
									<!-- //join_end -->


<c:choose>
	<c:when test="${memberVO.specialYn eq 'Y' }">
									<div class="ex_txt center">
										이미 특별회원으로 가입된 아이디로 별도의 가족회원 등록이 필요하지 않습니다.<br/>감사합니다.
									</div>
	</c:when>
	<c:when test="${memberVO.yearYn eq 'Y' }">
									<div class="ex_txt center">
										<fmt:parseDate value="${memberVO.anlmbEdate }" var="ymd" pattern="yyyyMMdd"/>
										이미 유료회원으로 가입된 아이디로 <em class="red"><fmt:formatDate value="${ymd }"  pattern="yyyy년 MM월 dd일"/></em>까지<br> 혜택을 받으실 수 있습니다. 감사합니다.
									</div>
	</c:when>

	<c:otherwise>
									<p class="desc mb40">가족회원 대표로 가입하신 분의 성함과 전화번호를 입력 해 주십시오.</p>
									<p class="stit">대표 가입자 정보 입력</p>
									<div class="table2 responsive-type mb50">
										<table>
											<caption>대표 가입자 정보 입력폼입니다.</caption>
											<colgroup>
												<col style="width:20%">
												<col style="width:80%">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row"><label for="name">이름</label></th>
													<td><input type="text" id="name" name="memNm" placeholder="이름을 입력해주세요."></td>
												</tr>
												<tr>
													<th scope="row"><label for="hp">휴대전화번호</label></th>
													<td><input type="tel" id="hp" name="hp" placeholder="‘-’ 을 빼고 입력 해 주십시오."></td>
													<p id="err_phone" class="red" style="display:none">연락처를 입력해주세요.</p>
												</tr>
											</tbody>
										</table>
										<!-- //table -->
									</div>
<c:choose>
	<c:when test="${empty itemData.itemCd }">
									<div>
										유료회원상품이 존재하지 않습니다. 관리자에게 문의해 주세요.
									</div>
	</c:when>
	<c:otherwise>
									<ul class="btn_area">
										<li><a href="javascript:fn_join_famYear()" class="btn_ty_m_c1" >개별회원 가입하기</a></li>
									</ul>
	</c:otherwise>
</c:choose>

</c:otherwise>
</c:choose>
								</div>
								<!-- //signup -->
							</div>

						</div>
</form:form>
					<!-- //inner -->
</div>
<!-- //sub_cont -->
<script>
	function fn_join_famYear() {
		var hpRule = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/g;
		var hphone = $('#hp').val()

		if ($('#name').val() == "") {
			alert("이름을 입력해 주세요.");
			$('#name').focus();
		} else if ($('#hp').val() == "") {
			alert("휴대폰번호를 입력해 주세요.");
			$('#hp').focus();
		} else if (isNaN(hphone)) {
			alert("휴대폰 번호는 숫자만 입력해 주세요.");
			$('#hp').focus();
		} else if (!hpRule.test($('#hp').val())) {
			$('#err_phone').text("연락처가 형식에 맞지 않습니다.").show();
		} else {
			$('#memberForm').submit()
		}

	}
</script>