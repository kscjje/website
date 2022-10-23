<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : outMember.jsp
  * @Description : 탈퇴 페이지
  * @Modification Information
  * @
  * @  수정일           수정자                수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.12  전영석               최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.12
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

	function fn_out_member() {
		
		/* 
		if ($('#outReason').val() == "") {
			alert("탈퇴 이유를 적어 주세요");
			$('#outReason').focus();
		} else {
			document.memberVO.submit();
		}*/
		
		if (confirm("정말 탈퇴하시겠습니까?")) {
			document.memberVO.submit();
		} else {
			//top.location.href = "/web/main";
		}

	}

</script>
	
		<div class="sub_cont" id="container">
			<div class="inner">
			
				<form:form commandName="memberVO" name="memberVO" id="memberVO" action="./outMemberSave" method="post">
			
					<h3 class="tit">회원탈퇴</h3>
					<!-- mypage_wrap -->
					<div class="mypage_wrap pad">
						<h4 class="h4 mb5">회원 탈퇴를 하시겠습니까?</h4>
						<p class="desc mb40">회원 탈퇴 안내사항을 확인해주세요.</p>
						<!-- ex_txt -->
						<div class="ex_txt">
							<p class="ex_tit"><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘" /></i> 회원 탈퇴 안내</p>
							회원 탈퇴 시 노원수학문화관 홈페이지 내 예약/신청 이용이 불가합니다. <!-- <br />또한 탈퇴된 사용자의 ID는 재사용할 수 없습니다. -->
						</div>
						<!-- //ex_txt -->
					</div>
					<!-- //mypage_wrap -->
					
				</form:form>
				
				<ul class="btn_area">
					<!-- <li><a href="#" class="btn_ty_m_c4">취소</a></li> -->
					<li><a href="javascript:fn_out_member();" class="btn_ty_m_c1">탈퇴하기</a></li>
				</ul>
				<!-- //btn_area -->
			</div>
			<!-- //inner -->
		</div>
		<!-- //sub_cont -->
			
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>