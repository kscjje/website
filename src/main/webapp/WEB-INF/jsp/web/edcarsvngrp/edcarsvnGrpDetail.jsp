<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : edcarsvnDetail.jsp
  * @Description : 교육 개인 예약 상세
  * @Modification Information
  * @
  * @  수정일                   수정자             수정내용
  * @ ------------   --------    ---------------------------
  * @ 2021.05.13      전영석             최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.13
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<%
	////String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

			<form:form commandName="eventProgramVO" id="eduForm" method="post">

				<input type="hidden" id="comCd"      name="comCd"        value="<c:out value="${detailVO.comcd}"/>"/>
				<input type="hidden" id="edcPrgmid"  name="edcPrgmid"    value="<c:out value="${detailVO.edcPrgmid}"/>" />
				<input type="hidden" id="eduUserBtn" name="eduUserBtn"   value="<c:out value="${detailVO.eduUserBtn}"/>" />
				<input type="hidden" id="memYn"      name="memYn" />

			</form:form>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">교육예약</h3>
					<div class="edu_wrap mb45">
						<div class="edu_top">
							<div class="edu_tit">
								<div class="tit_wrap">
									<div class="title_wrap"><div class="title"><c:out value="${detailVO.edcPrgmnm}"/></div></div>
									<!-- 
									<div class="tag_wrap">
										<span class="tag">#캠프</span><span class="tag">#창의적인</span><span class="tag">#실험</span><span class="tag">#창의력반짝</span>
									</div>
									-->
								</div>
								<span class="b_stat">
									<c:if test="${detailVO.eduInterStatus eq 'ING'}">
										<c:choose>
											<c:when test="${detailVO.edcPncpa-detailVO.appCnt > 0 }">접수<br/>가능</c:when>
											<c:otherwise>정원<br>마감</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${detailVO.eduInterStatus eq 'ACCT'}">
										접수<br/>준비
									</c:if>
									<c:if test="${detailVO.eduInterStatus eq 'END'}">
										접수<br/>종료
									</c:if>
									<c:if test="${detailVO.eduInterStatus eq 'OUT'}">
										접수<br/>불가
									</c:if>
								</span>
							</div>
							<!-- //edu_tit -->
							<div class="date_wrap">
								<div class="date_list">
									<dl>
										<dt><i class="select_date"></i>신청 기간</dt>
										<dd><c:choose><c:when test="${detailVO.reqDday > 0 }">D-${detailVO.reqDday }</c:when><c:when test="${detailVO.reqDday == 0 }">D-DAY</c:when></c:choose></dd></dd>
									</dl>
									<span><c:out value="${detailVO.edcRsvnSdatetime}"/> ~ <c:out value="${detailVO.edcRsvnEdatetime}"/></span>
								</div>
								<div class="date_list">
									<dl>
										<dt><i class="select_date"></i>교육기간</dt>
										<dd>
										
											<c:choose>
												<c:when test="${detailVO.eduDday > 0}">
													D-${detailVO.eduDday}
												</c:when>
												<c:when test="${detailVO.eduDday == 0}">
													D-DAY
												</c:when>
											</c:choose>
											
										</dd>
									</dl>
									
									<fmt:parseDate var="dateStr1" value="${detailVO.edcSdate}" pattern="yyyyMMdd" />
									<fmt:parseDate var="dateStr2" value="${detailVO.edcEdate}" pattern="yyyyMMdd" />
									
									<%-- <span><fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" /></span> --%>
									<span>${detailVO.eduDateStr}</span>
									
									
								</div>
							</div>
						</div>
						<!-- //edu_top -->
						<div class="edu_cont">
							<div class="edu_reserv">
								<div class="seat">
								
									<c:if test="${detailVO.eduInterStatus eq 'ING'}">
										<span class="st_num">
											<em>
												<c:out value="${(detailVO.edcPncpa - detailVO.appCnt) < 0 ? 0 :(detailVO.edcPncpa - detailVO.appCnt)}"/>
											</em>
												<i>
													명 신청가능
												</i> <b>/</b> 총 정원<c:out value="${detailVO.edcPncpa}"/>명
										</span>									
									</c:if>
									<!-- <span class="st_num"><em>000</em> <i>명 신청가능</i> <b>/</b> 총 정원 000명</span> -->
									
								</div>
								<!-- //seat -->
								<ul class="btn_area long">
									<c:if test="${detailVO.eduInterStatus eq 'ING' and detailVO.edcPncpa-detailVO.appCnt > 0}">
										<!-- 
										<li>
											<a href="#" class="btn_ty_m3_c5">바로예약하기</a>
										</li>
										-->
										
											<c:choose>
												<c:when test="${detailVO.edcProgmType eq '1001' }">
													<li><a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y')"  class="btn_ty_m3_c5">바로 예약하기</a></li>												
												</c:when>
												<c:when test="${detailVO.edcProgmType eq '3001' }">
													<li><a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y')"  class="btn_ty_m3_c5">바로 예약하기</a></li>
												</c:when>
												<c:when test="${detailVO.edcProgmType eq '4001' }">
													<li><a href="javascript:fn_reserve_url_4001('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y', '1')"  class="btn_ty_m3_c5">개인 예약하기</a></li>
													<li><a href="javascript:fn_reserve_url_4001('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y', '2')"  class="btn_ty_m3_c5">단체 예약하기</a></li>
												</c:when>												
												<c:otherwise>
												</c:otherwise>
											</c:choose>											
											
										<!-- 
										<c:if test="${detailVO.edcProgmType eq '3001' or etailVO.edcProgmType eq '9001'}">   단체 
											<li>
												<a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'N')" class="btn_ty_m3_c1">비회원 예약하기</a>
											</li>
										</c:if>
										-->
																				
									</c:if>
								</ul>
								<!-- //btn_area -->
							</div>
							<div class="table2 responsive-type">
								<table>
									<caption>교육상세정보를 제공하는 표입니다(교육요일, 교육시간, 교육차수, 대상, 비용, 재료비, 교육장소, 셔틀버스여부, 문의사항, 특이사항 포함)</caption>
									<colgroup>
										<col style="width:11.5%">
										<col style="width:38.5%">
										<col style="width:11.5%">
										<col style="width:38.5%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="vtop">교육요일</th>
											<td>
												${detailVO.edcDaygbnNm}
											</td>
											
											<c:if test="${detailVO.edcProgmType eq '1001' }">
												<th scope="row" class="vtop">교육시간</th>
												<td>
												
													${fn:substring(detailVO.edcStime,0,2)}:${fn:substring(detailVO.edcStime,2,4)}
														~ ${fn:substring(detailVO.edcEtime,0,2)}:${fn:substring(detailVO.edcEtime,2,4)}
																									
												</td>											
											</c:if>
											<c:if test="${detailVO.edcProgmType ne '1001' }">
												<th scope="row" class="vtop"></th>
												<td></td>											
											</c:if>											

										</tr>
										
										<tr>
											<c:if test="${empty detailVO.edcOdr or detailVO.edcOdr eq '0'}">
											
												<th scope="row" class="vtop">대상</th>
												<td colspan="3">
													<c:out value="${detailVO.edcTargetinfo}"/>	
												</td>														
												
											</c:if>
											
											<c:if test="${!empty detailVO.edcOdr and detailVO.edcOdr ne '0'}">

												<th scope="row" class="vtop">교육차수</th>
												<td>
													<c:out value="${detailVO.edcOdr}"/> 차
												</td>
												<th scope="row" class="vtop">대상</th>
												<td>
													<c:out value="${detailVO.edcTargetinfo}"/>	
												</td>
												
											</c:if>													
											

										</tr>
											
											
										<tr>
											<th scope="row" class="vtop">비용</th>
											<td>무료</td>
											<th scope="row" class="vtop">재료비</th>
											<td>
												${detailVO.edcTchmtrGuide}
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop">교육장소</th>
											<td>
												${detailVO.edcPlacenm}
											</td>
											<th scope="row" class="vtop">문의전화</th>
											<td>
												${detailVO.edcGuideTelno}
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop red">특이사항</th>
											<td class="b" colspan="3">
												${detailVO.edcGuideComment} 
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- //edu_wrap -->
					<div class="edu_wrap">
						<p class="stit">교육 안내</p>
						<div class="edu_info mb40">
						
							<c:set var="edcPrgmintrcn" value="${fn:replace(detailVO.edcPrgmintrcn, '<' , '&lt;')}" />
							<c:out value="${fn:replace(edcPrgmintrcn, crlf , '<br/>')}" escapeXml="false" />

							<p>
								<c:forEach var="item" items="${edcPrgmintrcnFileList}" varStatus="status">
									<c:set var="onlyOrginExtNm" value=".${item.fileExtsn}" />
								   <img src="<tags:UploadFileUrl filePath='${item.filePath}' fileName='${item.fileName}' originName='${item.orginFileName}' />" alt="${fn:replace(item.orginFileName, onlyOrginExtNm, '')}" />
								</c:forEach>
							</p>  						
						
							<!-- 
							<p>※ 방학기간은 진로특강을 운영하지 않으며, 12월은 주 3회(수,목,금) 확대 운영</p>
							<p><strong>[강사진]</strong> 교수·출연(연) 연구원 등 사회적 인지도가 높은 인사 초빙</p>
							<p>- 4차산업혁명 관련 유망한 미래기술 소개 및 진로강연</p>
							<p>- 사이언스홀(12월) 강연의 경우 과학공연과 테드식 강연을 동시 진행</p>
							<p>- 인공지능(AI), 사물인터넷(IOT), 가상현실(VR)·증강현실(AR), 자율주행차, 로보틱스, 정밀의료, 빅데이터, 블록체인, 스마트시티, 에너지프로슈머 등</p>
							<p><strong>[교육비]</strong> 무료</p>
							<p>
								<img src="../..<%=strContext%>/images/sub/img_edu01.png" alt="교육예약안내이미지">
							</p>
							-->
						</div>
						<p class="stit">교육계획서</p>
						<div class="table2 responsive-type">
						
								<table>
									<caption>교육기획서 제공.</caption>
									<colgroup>
										<col style="width:11.6%"/>
										<col style="*"/>
									</colgroup>
									<tbody>
										<c:forEach var="item" items="${edcPlanFileList}" varStatus="status">
											<tr>
												<th scope="row" class="vtop">첨부파일 <c:out value="${status.index + 1}"/></th>
												<td>
													<div class="file-list bb0">
														<ul>
															<li>
																<a href="javascript:fn_file_download('<c:out value="${item.fileGrpinnb}"/>','<c:out value="${item.fileSn}"/>');" class="file">
																	<c:out value="${item.orginFileName}"/><span>(<tags:FileSize fileSize="${item.fileSize}"/>)</span>
																</a>

																<a class="btn_ty_s_c2 btn" href="javascript:fn_file_download('<c:out value="${item.fileGrpinnb}"/>','<c:out value="${item.fileSn}"/>');">다운로드</a>
															</li>
														</ul>
													</div>

												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
														
						</div>
					</div>
					<!-- //edu_wrap -->
					<ul class="btn_area long">
						<!-- <li><a href="#" class="btn_ty_m3_c5">바로 예약하기</a></li> -->
						
						<c:if test="${detailVO.eduInterStatus eq 'ING' and detailVO.edcPncpa-detailVO.appCnt > 0}">
						
							<c:choose>
								<c:when test="${detailVO.edcProgmType eq '1001' }">
									<li><a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y')"  class="btn_ty_m3_c5">바로 예약하기</a></li>												
								</c:when>
								<c:when test="${detailVO.edcProgmType eq '3001' }">
									<li><a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y')"  class="btn_ty_m3_c5">바로 예약하기</a></li>
								</c:when>
								<c:when test="${detailVO.edcProgmType eq '4001' }">
									<li><a href="javascript:fn_reserve_url_4001('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y', '1')"  class="btn_ty_m3_c5">개인 예약하기</a></li>
									<li><a href="javascript:fn_reserve_url_4001('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y', '2')"  class="btn_ty_m3_c5">단체 예약하기</a></li>
								</c:when>												
								<c:otherwise>
								</c:otherwise>
							</c:choose>							
						
							<%-- <li>
								<a href="javascript:fn_reserve_url('<c:out value="${detailVO.edcPrgmid}"/>','<c:out value="${detailVO.edcProgmType }"/>', 'Y')"  class="btn_ty_m3_c5">바로 예약하기</a>
							</li> --%>
							
						</c:if>						
						
					</ul>
					<!-- //btn_area -->

				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->


	<script>

	 
	function fn_reserve_url(programId, eduType, memType) {
		
		var url =  "/web/edcarsvngrp/edcarsvnGrpRegist?edcPrgmid=" + programId;
		var msg = "";
		var paramAdd = "";
		var eduGender = "<c:out value='${detailVO.eduReqGender}'/>";
		
		<c:choose>
			<c:when test="${!empty userInfo and userInfo.isMember}">
				// 예약가능
				if (memType == "N") {
					alert("현재 회원 로그인 상태입니다.\n로그아웃 후 비회원 인증 절차를 거쳐 비회원 예약을 하실 수 있습니다. ");
				} else {
					var myGender = "<c:out value="${userInfo.gender}"/>";
					if (eduType== "1001" && eduGender == "2001" && myGender != "2" ) {
						alert("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					} else if (eduType== "1001" && eduGender == "1001" && myGender != "1") {
						alert("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					} else {
						window.location.href = url;
					}
				}
			</c:when>
			<c:when test="${!empty userInfo and !userInfo.isMember}">
				// 예약가능
				if (memType == "Y") {
					alert("현재 비회원 인증 상태입니다.\n로그아웃 후 일반 로그인을 하신 후  예약을 하실 수 있습니다.");
				} else {
					window.location.href = url;
				}
			</c:when>
			<c:otherwise>
				if (memType == "N") {
					msg = "비회원 본인인증 후 예약하실 수 있습니다.";
				} else {
					msg = "회원 로그인 후 예약하실 수 있습니다.";
					paramAdd = "&member_yn=Y";
				}

				if (msg != "") {
					if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
						window.location.href = "/web/member/login?returnURL=" + encodeURIComponent(url) + paramAdd;
					}
				}

			</c:otherwise>
		</c:choose>
	}

	function fn_reserve_url_4001(programId, eduType, memType, eduUserBtn) {
		
		var url =  "/web/edcarsvn/edcarsvnRegist?edcPrgmid=" + programId + "&eduUserBtn=" + eduUserBtn;
		var msg = "";
		var paramAdd = "";
		var eduGender = "<c:out value='${detailVO.eduReqGender}'/>";
		
		<c:choose>
			<c:when test="${!empty userInfo and userInfo.isMember}">
				// 예약가능
				if (memType == "N") {
					alert("현재 회원 로그인 상태입니다.\n로그아웃 후 비회원 인증 절차를 거쳐 비회원 예약을 하실 수 있습니다. ");
				} else {
					var myGender = "<c:out value="${userInfo.gender}"/>";
					if (eduType== "1001" && eduGender == "2001" && myGender != "2" ) {
						alert("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					} else if (eduType== "1001" && eduGender == "1001" && myGender != "1") {
						alert("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
					} else {
						window.location.href = url;
					}
				}
			</c:when>
			<c:when test="${!empty userInfo and !userInfo.isMember}">
				// 예약가능
				if (memType == "Y") {
					alert("현재 비회원 인증 상태입니다.\n로그아웃 후 일반 로그인을 하신 후  예약을 하실 수 있습니다.");
				} else {
					window.location.href = url;
				}
			</c:when>
			<c:otherwise>
				if (memType == "N") {
					msg = "비회원 본인인증 후 예약하실 수 있습니다.";
				} else {
					msg = "회원 로그인 후 예약하실 수 있습니다.";
					paramAdd = "&member_yn=Y";
				}

				if (msg != "") {
					if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
						window.location.href = "/web/member/login?returnURL=" + encodeURIComponent(url) + paramAdd;
					}
				}

			</c:otherwise>
		</c:choose>
	}
	
	$(function() {

		$(".select_date").mouseout(function(event) {
			$(".pop_calendar").removeClass("show");
		});

		$(".select_date").mouseover(function(event) {
			$(".pop_calendar").toggleClass("show");
		});

	});


	function fn_file_download(atchFileId , fileSn) {
	    window.location.href="/web/common/file/download?atchFileId="+atchFileId+"&fileSn=" + fileSn;
	}

	</script>

	
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
