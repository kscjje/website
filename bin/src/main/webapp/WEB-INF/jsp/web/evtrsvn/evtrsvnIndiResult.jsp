<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnIndiResult.jsp
  * @Description : 예약 결과 JSP
  * @Modification Information
  * @
  * @  수정일              수정자           수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.18    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.18
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
	
	
	});
	
	function fn_pop_rsvnCerti() {
		window.open('','rsvnPop','width=818, height=1177, resizable=yes, scrollbars=yes, status=0, titlebar=0, toolbar=0, left=100, top=50')
		$('#popFrm').submit()
	}
	function fn_direct_pay() {
		$('#evtrsvnMstVO').submit();
	}
	
</script>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">강연/행사/영화 예약</h3>
					<div class="join_wrap bor">
						<!-- step -->
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
									<dd>안내사항 동의 및 여부</dd>
								</dl>
							</li>
							<li class="on">
								<!-- 현재 on -->
								<dl>
									<dt>3</dt>
									<dd>예약 완료 안내</dd>
								</dl>
							</li>
						</ul>
						<!-- //step -->
						<!-- signup2 -->
						<div class="signup2">
							<p class="desc desc3 mb35">강연/행사/영화 예약이 완료 되었습니다.</p>
							<!-- table -->
							<div class="table2 responsive-type">
								<table>
									<caption>예약 정보 확인표입니다.</caption>
									<colgroup>
										<col style="width:25%">
										<col style="width:75%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="vtop">예약번호</th>
											<td class="b">
												<c:out value="${resultVO.evtRsvnno}"/>
												
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">강연/행사/영화 제목</th>
											<td class="b">
												<c:out value="${resultVO.evtName}"/>
												
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">예약자 성명</th>
											<td class="b">
												<c:out value="${resultVO.evtRsvnCustnm}"/>
												
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">참여 인원</th>
											<td class="b">
												<c:out value="${resultVO.evtVeingnmpr}"/> 명
												
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">휴대폰</th>
											<td class="b">
												<tags:HpDecrypt param="${resultVO.evtRsvnMoblphon}"/>
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">이메일</th>
											<td class="b">
												<tags:EmailDecrypt param="${resultVO.evtEmail}"/>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //예약자 정보 입력 -->
						</div>
						<!-- //signup2 -->
					</div>
					<!-- //join_wrap -->
					<ul class="btn_area">
						<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EVT" class="btn_ty_m3_c1">예약현황보기</a></li>
						<li><a href="./evtrsvnList" class="btn_ty_m3_c5">메인화면으로</a></li>
					</ul>
					<!-- //btn_area -->
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>