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

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" import="egovframework.com.cmm.service.EgovProperties" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script src="<%=strContext%>/js/common_nsm_human.js"></script>
<script>
function fn_direct_pay() {
	$('#myRsvnVO').submit();
}
</script>
				<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">예약정보</h3>
						<div class="join_wrap bor">
							<!-- step -->
								<c:if test="${detailVO.edcProgmType eq '1001'}"> <!--  개인 -->
									<c:choose>
										<c:when test="${detailVO.edcOnlineyn eq 'Y'}"> <!--  온라인 'Y' -->
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
												<li>
													<dl>
														<dt>3</dt>
														<dd>정보 입력</dd>
													</dl>
												</li>
												<li class="on">
													<dl>
														<dt>4</dt>
														<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
													</dl>
												</li>
											</ul>
										</c:when>
										<c:otherwise>
											<ul class="step step3">
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
														<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
													</dl>
												</li>
											</ul>
										</c:otherwise>
									</c:choose>
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
												<dd>인원 입력 및 동의</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li class="on">
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
										<li>
											<!-- 현재 on -->
											<dl>
												<dt>2</dt>
												<dd>안내 및 주문</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li  class="on">
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
										<li >
											<!-- 현재 on -->
											<dl>
												<dt>2</dt>
												<dd>안내 및 주문</dd>
											</dl>
										</li>
										<li>
											<dl>
												<dt>3</dt>
												<dd>정보 입력</dd>
											</dl>
										</li>
										<li class="on">
											<dl>
												<dt>4</dt>
												<dd><c:choose><c:when test="${detailVO.edufreeYn ne 'Y'}">결제 대기</c:when><c:otherwise>예약완료</c:otherwise></c:choose></dd>
											</dl>
										</li>
									</ul>
								</c:if>
								<!-- //step -->
							<!-- //step -->
							<!-- signup2 -->
							<div class="signup2">
								<c:choose>
								<c:when test="${detailVO.edufreeYn ne 'Y'}">
									<h4 class="h4 mb40"><span class="red"><c:out value='${edcarsvnInfo.edcPaywaitEnddatetime}' /></span> 까지 결제 하셔야 <br/><span class="red">예약이 완료</span>됩니다.</h4>
								</c:when>
								<c:otherwise>
									<h4 class="h4 mb40">예약이 완료 되었습니다.</h4>
								</c:otherwise>
								</c:choose>

								<c:if test="${detailVO.edufreeYn ne 'Y'}">
									<div class="calc">

										<div class="cal2">
											<!-- <span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span> -->
											<dl>
												<dt>결제 예정 금액</dt>
												<dd class="pink">
													<fmt:formatNumber value="${edcarsvnInfo.edcTotamt}" pattern="#,###" />원
													<input type="hidden" name="LGD_AMOUNT" id="LGD_AMOUNT" value="${edcarsvnInfo.edcTotamt}" />
												</dd>
											</dl>
										</div>
									</div>
									<!-- //calc -->
								</c:if>

								<c:if test="${detailVO.edufreeYn ne 'Y'}">
									<!-- btn_area -->
<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="/web/mypage/myRsvn/myRsvnPay" method="post">
<input type="hidden" name="rsvnIdx" value="${edcarsvnInfo.edcRsvnReqid}"/>
<input type="hidden" name="gubun" value="EDC"/>
				<!-- btn_area -->
				<ul class="btn_area mb70">
					<li><a href="javascript:fn_direct_pay()" class="btn_ty_m_c1">바로 결제</a></li>
				</ul>
</form:form>
									<!-- //btn_area -->
								</c:if>
								<p class="stit">예약 정보</p>
								<!-- table -->
								<div class="table2 responsive-type mb50">
									<table>
										<caption>예약 정보 확인표입니다.</caption>
										<colgroup>
											<col style="width:30%">
											<col style="width:70%">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row" class="vtop">예약 프로그램</th>
												<td class="b">
														<div class="title_wrap">
														<c:if test="${detailVO.edcOnlineyn eq 'Y' }">
															<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
														</c:if>
														<c:out value='${detailVO.edcPrgmnm}' />
														</div>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약번호</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcRsvnNo}' />
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">예약자 이름</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcRsvnCustnm}' />
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop">휴대전화번호</th>
												<td class="b"><tags:HpTelShow hpval="${edcarsvnInfo.edcRsvnMoblphon}"/></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">이메일</th>
												<td class="b">
													<c:out value='${edcarsvnInfo.edcEmail}' />
												</td>
											</tr>

<c:if test="${detailVO.edcProgmType eq '3001'}">
												<tr>
													<th scope="row" class="vtop">단체, 학교, 기관 명</th>
													<td>
														<c:out value='${edcarsvnInfo.edcRsvnOrgname}' />
													</td>
												</tr>
												<tr>
												<th scope="row" class="vtop">참가 학년</th>
												<td>
													<c:out value='${edcarsvnInfo.edcVistorGradenm}' />
												</td>
											</tr>
												<tr>
													<th scope="row" class="vtop">방문 인솔자 명</th>
													<td >
														<c:out value='${edcarsvnInfo.edcReqCustnm}' />
													</td>
												</tr>
												<tr>
													<th scope="row" class="vtop">방문 인솔자 연락처</th>
													<td><tags:HpTelShow hpval="${edcarsvnInfo.edcReqMoblphon}"/></td>
												</tr>
</c:if>
<c:if test="${detailVO.edcShtlbusYn eq 'Y'}">
<!-- 셔틀버스 운행 -->
											<tr>
												<th scope="row" class="vtop">셔틀버스 입소<br/>가능한 위치</th>
												<td><c:out value='${edcarsvnInfo.edcInBusloct}' /></td>
											</tr>
											<tr>
												<th scope="row" class="vtop">셔틀버스 퇴소<br/>가능한 위치</th>
												<td><c:out value='${edcarsvnInfo.edcOutBusloct}' /></td>
											</tr>
</c:if>
<c:forEach var="item" items="${itemList}" varStatus="status">
												<tr>
													<th scope="row" class="vtop">
														<c:out value='${item.edcMngitemnm}'/>
													</th>
													<td>
														<c:out value='${item.itemValue}'/>
													</td>
												</tr>
</c:forEach>
<c:if test="${detailVO.edcAddrOpenyn eq 'Y'}">
											<tr>
												<th scope="row" class="vtop">주소</th>
												<td>
													<div class="w_box mt0">
														<div class="w100">
															<c:out value='${edcarsvnInfo.edcHomeZipno}' /><br>
															<c:out value='${edcarsvnInfo.edcHomeAddr1}' />&nbsp;<c:out value='${edcarsvnInfo.edcHomeAddr2}' />
														</div>
													</div>
												</td>
											</tr>
</c:if>
										</tbody>
									</table>
								</div>
								<!-- //table -->
								<!-- //예약자 정보 입력 -->
<c:forEach var="item" items="${edcarsvnFamlyinfoList}" varStatus="status">
									<p class="stit">참가자 ${status.index + 1}</p>
									<!-- table -->
									<div class="table2 responsive-type mb50">
										<table>
											<colgroup>
												<col style="width:20%">
												<col style="width:80%">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row" class="vtop">예약자 이름</th>
													<td>
														<c:out value='${item.edcRsvnfmName}' />
													</td>
												</tr>
												<tr>
													<th scope="row" class="vtop">휴대전화번호</th>
													<td><tags:HpTelShow hpval="${item.edcRsvnfmTelnoDec}"/></td>
												</tr>
												<tr>
													<th scope="row" class="vtop">성별</th>
													<td>
														<c:out value='${item.edcRsvnfmSexgbnNm}' />
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- //table -->
</c:forEach>

							</div>
							<!-- //signup2 -->
						</div>
						<!-- //join_wrap -->
						<ul class="btn_area long">
							<li><a href="./edcarsvnList" class="btn_ty_m_c1">교육 목록</a></li>
							<c:choose>
								<c:when test="${detailVO.edufreeYn ne 'Y'}">
									<li><a href="/web/mypage/myRsvn/myRsvnList?gubun=EDC" class="btn_ty_m3_c5">나의 예약내역</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="/web/mypage/myRsvn/myRsvnCompList?gubun=EDC" class="btn_ty_m3_c5">나의 예약내역</a></li>
								</c:otherwise>
							</c:choose>
<c:if test="${detailVO.edcAddrOpenyn eq 'Y' or detailVO.edcProgmType ne '1001'}">
							<li><a href="/web/mypage/myRsvn/myRsvnEdcModify?edcRsvnReqid=${edcarsvnInfo.edcRsvnReqid}" class="btn_ty_m3_c3">예약정보 수정</a></li>
</c:if>
						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->