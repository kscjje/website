<%
 /**
  * @Class Name : changeMycar.jsp
  * @Description : 차량정보 변경 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.19    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.19
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<script>
	function fn_member_submit() {
		/* if ($('#carNo1').val() == "") {
			alert("차량 앞자리를 입력해 주세요.");
			$('#carNo1').focus();
		} else if (!/^[0-9]{2,3}$/.test($('#carNo1').val())) {
			alert("차량 앞자리는 숫자 2~3자리로 입력해 주세요.");
			$('#carNo1').focus();
		} else if ($('#carNo2').val() == "") {
			alert("차량 가운데 자리를 입력해 주세요.");
			$('#carNo2').focus();
		} else if (!/^[가-힣]{1}$/.test($('#carNo2').val())) {
			alert("차량 가운데자리는 한글 1자리로 입력해 주세요.");
			$('#carNo2').focus();
		} else if ($('#carNo3').val() == "") {
			alert("차량 마지막 자리를 입력해 주세요.");
			$('#carNo3').focus();
		} else if (!/^[0-9]{4}$/.test($('#carNo3').val())) {
			alert("차량 마지막 자리는 숫자 4자리로 입력해 주세요.");
			$('#carNo3').focus();
		} else {} */
		
			var carNo = $('#carNo').val();

			if (carNo == "") {
				alert("변경할 차량 번호를 입력해 주세요.");
				$('#carNo').focus();
			} else if (carNo == $('#carno_origin').val()) {
				alert("차량정보가 현재 차량정보와 같습니다.");
			} else {
				
				carNo = carNo.replaceAll(' ', '');
				
				document.carVO.submit();
			}

	}

</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">회원정보 수정</h3>
		<ul class="tab tab3">
			<li ><a href="./myInforRegist">회원정보 수정</a></li><!-- 현재위치 class: on 추가 -->
			<li ><a href="./changePasswd">비밀번호 변경</a></li>
			<li class="on"><a >내 차량정보</a></li>
			<li><a href="./outMember">회원탈퇴</a></li>
		</ul>
<form:form commandName="carVO" name="carVO" id="carVO" action="./changeMycarSave" method="post">
<input type="hidden" name="carno_origin" id="carno_origin" value="<c:out value="${carData.carNo}"/>" />
		<!-- mypage_wrap -->
		<div class="mypage_wrap bor">
			<!-- signup -->
			<div class="signup2">
				<p class="stit">내 차량정보</p>
				<div class="table2 responsive-type mb50">
					<table>
						<caption>내 차량정보 수정 입력폼입니다.</caption>
						<colgroup>
							<col style="width:25%">
							<col style="width:75%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">현재 차량정보</th>
								<td class="b">
									<c:choose>
										<c:when test="${empty carData }">등록된 차량정보가 없습니다.</c:when>
										<c:otherwise>
											<c:out value="${carData.carNo}"/>
											<p style="font-weight:normal">※ 등록일 : <fmt:formatDate value="${carData.regdate}" pattern="yyyy-MM-dd HH:mm"/></p>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">변경할 차량정보</th>
								<td>
									<div class="w100">
										<label for="carNo" class="hidden">차량번호</label>
										<input type="text" id="carNo" maxlength="15" name="carNo" placeholder=" 예시) 000가0000">
									</div>
										<c:if test="${!empty carData}">
											<!--  <p>※ 차량정보는 변경한 날짜 다음날 부터 할인 적용 됩니다.</p>  -->
											<p>※ 차량 번호 변경 등록 시 <b>다음 일</b>에 자동주차정산시스템에 반영됩니다.</p>
										</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>
			</div>
			<!-- //signup -->
		</div>
		<!-- //mypage_wrap -->
		<ul class="btn_area">
			<li><a href="javascript:fn_member_submit()" class="btn_ty_m_c1">등록 변경</a></li>
		</ul>
		<!-- //btn_area -->
</form:form>
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->