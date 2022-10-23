<%@ page language="java" import="egovframework.com.cmm.service.EgovProperties" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : edcarsvnRegEnd.jsp
  * @Description : 교육 예약 완료 조회
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

<script>
function fn_direct_pay() {
	$('#myRsvnVO').submit();
}
</script>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">교육예약</h3>
					<div class="join_wrap bor">
					
						<!-- step -->
						<c:if test="${detailVO.edcProgmType eq '1001'}">
							<ul class="step step3">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
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
						</c:if>
						<!-- //step -->

						<!-- step -->
						<c:if test="${detailVO.edcProgmType eq '3001'}">
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
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>
						
						<c:if test="${detailVO.edcProgmType eq '4001' and edcarsvnInfo.edcPersnGbn eq '1001'}">
							<ul class="step step3">
								<li>
									<dl>
										<dt>1</dt>
										<dd>교육 선택</dd>
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
						</c:if>
												
						<c:if test="${detailVO.edcProgmType eq '4001' and edcarsvnInfo.edcPersnGbn eq '2001'}">
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
								<li>
									<dl>
										<dt>3</dt>
										<dd>예약자 정보 입력</dd>
									</dl>
								</li>
								<li class="on">
									<!-- 현재 on -->
									<dl>
										<dt>4</dt>
										<dd>예약 완료 안내</dd>
									</dl>
								</li>
							</ul>
						</c:if>
												
						<!-- //step -->						
						
						<!-- signup2 -->
						<div class="signup2">
							<p class="desc desc3 mb35">예약이 완료 되었습니다.</p>
							<!-- table -->
							<div class="table2 responsive-type">
								<table>
									<caption>예약 정보 확인표입니다.</caption>
									<colgroup>
										<col style="width:25%">
										<col style="width:75%">
									</colgroup>
									<tbody>
										<c:if test="${detailVO.edcProgmType eq '1001'}">
											<tr>
												<th scope="row" class="vtop">예약번호</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnNo}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약프로그램</th>
												<td class="b"><c:out value='${detailVO.edcPrgmnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자 성명</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnCustnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">참가인원</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcVistnmpr}' /> 명
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">휴대폰</th>
												<td class="b">
													<tags:HpDecrypt param="${edcarsvnInfo.edcRsvnMoblphon}"/>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">이메일</th>
												<td class="b">
													<tags:EmailDecrypt param="${edcarsvnInfo.edcEmail}"/>
												</td>
											</tr>
										</c:if>
										
										<c:if test="${detailVO.edcProgmType eq '3001'}">
										
											<tr>
												<th scope="row" class="vtop">예약프로그램</th>
												<td class="b"><c:out value='${detailVO.edcPrgmnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약번호</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnNo}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnCustnm}' /></td>
											</tr>
											<%-- <tr>
												<th scope="row" class="vtop">단체, 학교, 기관 명</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcRsvnOrgname}' />
												</td>
											</tr> --%>
											<tr>
												<th scope="row" class="vtop">예약자 연락처</th>
												<td class="b">
													<tags:HpDecrypt param="${edcarsvnInfo.edcRsvnMoblphon}"/>
												</td>
											</tr>
											<%-- <tr>
												<th scope="row" class="vtop">방문 인솔자명</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcLeadCustnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">방문 인솔자 연락처</th>
												<td class="b">
													<tags:HpDecrypt param="${edcarsvnInfo.edcLeadMoblphon}"/>
												</td>
											</tr> --%>
											<tr>
												<th scope="row" class="vtop">참가인원</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcVistnmpr}' />명</td>
											</tr>
											
										</c:if>
												
										<c:if test="${detailVO.edcProgmType eq '4001' and edcarsvnInfo.edcPersnGbn eq '1001'}">
											<tr>
												<th scope="row" class="vtop">예약번호</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnNo}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약프로그램</th>
												<td class="b"><c:out value='${detailVO.edcPrgmnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자 성명</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnCustnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">참가인원</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcVistnmpr}' /> 명
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">휴대폰</th>
												<td class="b">
													<tags:HpDecrypt param="${edcarsvnInfo.edcRsvnMoblphon}"/>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">이메일</th>
												<td class="b">
													<tags:EmailDecrypt param="${edcarsvnInfo.edcEmail}"/>
												</td>
											</tr>
										</c:if>												
												
										<c:if test="${detailVO.edcProgmType eq '4001' and edcarsvnInfo.edcPersnGbn eq '2001'}">
										
											<tr>
												<th scope="row" class="vtop">예약프로그램</th>
												<td class="b"><c:out value='${detailVO.edcPrgmnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약번호</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnNo}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcRsvnCustnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">단체, 학교, 기관 명</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcRsvnOrgname}' />
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자 연락처</th>
												<td class="b">
													<tags:HpTelShow hpval="${edcarsvnInfo.edcRsvnMoblphon}"/>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">방문 인솔자명</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcLeadCustnm}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">방문 인솔자 연락처</th>
												<td class="b"><tags:HpTelShow hpval="${edcarsvnInfo.edcLeadMoblphon}"/></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">참가인원</th>
												<td class="b"><c:out value='${edcarsvnInfo.edcVistnmpr}' />명</td>
											</tr>
											
										</c:if>												
																				
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //예약자 정보 입력 -->
							
						</div>
						<!-- //signup2 -->
					</div>
					<!-- //join_wrap -->
					<ul class="btn_area long">
						<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EDCGRP" class="btn_ty_m3_c1">예약현황보기</a></li>
						<li><a href="./edcarsvnGrpList" class="btn_ty_m3_c5">메인화면으로</a></li>
					</ul>
					<!-- //btn_area -->
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>