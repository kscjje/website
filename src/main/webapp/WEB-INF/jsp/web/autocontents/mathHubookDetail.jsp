<%
 /**
  * @Class Name : edcarsvnDtlList.jsp
  * @Description : 교육 개인 예약 상세
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.09.15      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.09.15
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<script src="<%=strContext%>/js/common_nsm_human.js"></script>

				<form:form commandName="eventProgramVO" id="eduForm" method="post">

					<input type="hidden" id="comCd"      name="comCd"        value="<c:out value="${detailVO.comcd}"/>"/>
					<input type="hidden" id="edcPrgmid"  name="edcPrgmid"    value="<c:out value="${detailVO.edcPrgmid}"/>" />
					<input type="hidden" id="memYn"      name="memYn" />

				</form:form>

				<div class="sub_cont" id="container">
					<div class="inner">
						<!-- <h3 class="tit">교육예약</h3> -->
						<div class="edu_wrap mb45">
							<div class="edu_top">
								<div class="edu_tit">
									<div class="tit_wrap">
										<div class="tag_wrap">

											<c:set var="HTMLTag"     value="${detailVO.edcTagnm.replaceAll('_SSPAN','<span class=\"tag\">')}" />
											<c:out value='${HTMLTag.replaceAll("_ESPAN","</span>")}' escapeXml="false" />

											<c:if test="${HTMLTag != null}">
												</span>
											</c:if>

										</div>
										<div class="title_wrap">
										<p class="title">
											<c:if test="${detailVO.edcOnlineyn eq 'Y' }">
												<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
											</c:if>
											<c:out value="${detailVO.edcPrgmnm}"/>
										</p>

										</div>
									</div>
									<span class="b_stat">
										<c:if test="${detailVO.eduInterStatus eq 'ING'}">
											<c:choose>
												<c:when test="${detailVO.edcPncpa-detailVO.appCnt > 0 }">접수<br>가능</c:when>
												<c:otherwise>정원<br>마감</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${detailVO.eduInterStatus eq 'ACCT'}">
											접수<br>준비
										</c:if>
										<c:if test="${detailVO.eduInterStatus eq 'END'}">
											접수<br>종료
										</c:if>
										<c:if test="${detailVO.eduInterStatus eq 'OUT'}">
											접수<br>불가
										</c:if>
									</span>
								</div>
								<!-- //edu_tit -->
								<div class="date_wrap">
									<div class="date_list">
										<dl>
											<!-- <dt><button type="button" class="select_date" id="applyDate"></button> <label for="applyDate">신청 기간</label></dt> 원존 JYS -->
											<dt><img class="select_date" src="" border="0" id="applyDate" style="cursor:default;" alt="" /><label for="applyDate">신청 기간</label></dt>
											<dd><c:choose><c:when test="${detailVO.reqDday > 0 }">D-${detailVO.reqDday }</c:when><c:when test="${detailVO.reqDday == 0 }">D-DAY</c:when></c:choose></dd>
										</dl>
										<span>
											<c:out value="${detailVO.edcRsvnSdatetime}"/> ~ <c:out value="${detailVO.edcRsvnEdatetime}"/>
										</span>


									</div>
									<div class="date_list">
										<dl>
											<!-- <dt><button type="button" class="select_date" id="eduDate"></button> <label for="eduDate">교육 기간</label></dt> 원본 JYS -->
											<dt><img class="select_date" src="" border="0" id="eduDate" style="cursor:default;" alt="" /><label for="eduDate">교육 기간</label></dt>
											<dd><c:choose><c:when test="${detailVO.eduDday > 0 }">D-${detailVO.eduDday }</c:when><c:when test="${detailVO.eduDday == 0 }">D-DAY</c:when></c:choose></dd>
										</dl>
										<span>
											${detailVO.eduDateStr }
										</span>
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
													<c:out value="${(detailVO.edcPncpa - detailVO.appCnt) < 0? 0 :(detailVO.edcPncpa - detailVO.appCnt)}"/>
												</em>
													<i>
														명 신청가능
													</i> <b>/</b> 총 <c:out value="${detailVO.edcPncpa}"/>석
											</span>
										</c:if>
									</div>
									<!-- //seat -->
									<ul class="btn_area long">
<c:if test="${detailVO.eduInterStatus eq 'ING' and detailVO.edcPncpa-detailVO.appCnt > 0}">
										<li>
											<a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'Y')"  class="btn_ty_m3_c5">회원 예약하기</a>
										</li>
										<c:if test="${detailVO.edcProgmType eq '3001' or etailVO.edcProgmType eq '9001'}"> <!--  단체 -->
											<li>
												<a href="javascript:fn_reserve_url('${detailVO.edcPrgmid}','${detailVO.edcProgmType }', 'N')" class="btn_ty_m3_c1">비회원 예약하기</a>
											</li>
										</c:if>
</c:if>
									</ul>
									<!-- //btn_area -->
								</div>
								<div class="table2 responsive-type">
									<table>
										<caption>비용, 교육대상, 교육장소, 문의사항, 특이사항등 교육상세정보 제공.</caption>
										<colgroup>
											<col style="width:11.5%">
											<col style="width:38.5%">
											<col style="width:11.5%">
											<col style="width:38.5%">
										</colgroup>
										<tbody>
<c:if test="${detailVO.edcProgmType eq '1001' }">
											<tr>
												<th scope="row" class="vtop">교육시간</th>
												<td>
													${fn:substring(detailVO.edcStime,0,2)}:${fn:substring(detailVO.edcStime,2,4)}
														~ ${fn:substring(detailVO.edcEtime,0,2)}:${fn:substring(detailVO.edcEtime,2,4)}
													(<c:out value="${detailVO.edcDaygbnNm}"/>요일)
												</td>
												<th scope="row" class="vtop">
													교육차수
												</th>
												<td><c:out value="${detailVO.edcOdr}"/> 차</td>
											</tr>
</c:if>

											<tr>
												<th scope="row" class="vtop">비용</th>
												<td>
													<c:choose>
														<c:when test="${detailVO.salamt >0 }"><fmt:formatNumber value="${detailVO.salamt}" pattern="#,###" />원</c:when>
														<c:otherwise>무료</c:otherwise>
													</c:choose>
													<c:if test="${detailVO.edcTchmtrGuideyn eq 'Y'}">
													<em>※ <c:out value="${detailVO.edcTchmtrGuide}"/></em>
													</c:if>
												</td>
												<th scope="row" class="vtop">교육대상</th>
												<td>
													<c:out value="${detailVO.edcTargetinfo}"/>
												</td>
											</tr>
											<tr>

<c:choose>
	<c:when test="${detailVO.edcProgmType eq '1001' or detailVO.edcShtlbusYn ne 'Y'}">
												<th scope="row" class="vtop">교육장소</th>
												<td colspan="3"><c:out value="${detailVO.edcPlacenm}"/></td>
	</c:when>
	<c:otherwise>
												<th scope="row" class="vtop">교육장소</th>
												<td ><c:out value="${detailVO.edcPlacenm}"/></td>
												<th scope="row" class="vtop">셔틀버스</th>
												<td>
													<c:if test="${detailVO.edcShtlbusYn eq 'Y'}">운행함</c:if>
													<c:if test="${detailVO.edcShtlbusYn ne 'Y'}">운행안함</c:if>
												</td>
	</c:otherwise>
</c:choose>

											</tr>
<c:if test="${detailVO.edcShtlbusYn eq 'Y' and !empty detailVO.edcShtlbusGuide }">
											<tr>
												<th scope="row" class="vtop">셔틀버스<br>탑승위치 안내</th>
												<td colspan="3">
													${fn:replace(detailVO.edcShtlbusGuide, crlf , '<br/>')}
												</td>
											</tr>
</c:if>
											<tr>
												<th scope="row" class="vtop">문의사항</th>
												<td>
													<c:out value="${detailVO.edcGuideTelno}"/>
												</td>
												<th scope="row" class="vtop red">특이사항</th>
												<td class="b">
													<c:out value="${detailVO.edcGuideComment}"/>
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
									
									   <img src="<tags:UploadFileUrl filePath='${item.filePath}' fileName='${item.fileName}' originName='${item.orginFileName}' />" alt="${fn:replace(item.orginFileName, onlyOrginExtNm, '')}"/>
									</c:forEach>
								</p>
							</div>

							<p class="stit">교육 기획서</p>
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
<c:if test="${detailVO.eduInterStatus eq 'ING' and detailVO.edcPncpa-detailVO.appCnt > 0}">
							<li>
								<a href="javascript:fn_reserve_url('<c:out value="${detailVO.edcPrgmid}"/>','<c:out value="${detailVO.edcProgmType }"/>', 'Y')"  class="btn_ty_m3_c5">회원 예약하기</a>
							</li>
							<c:if test="${detailVO.edcProgmType eq '3001' or etailVO.edcProgmType eq '9001'}"> <!--  단체 -->
								<li>
									<a href="javascript:fn_reserve_url('<c:out value="${detailVO.edcPrgmid}"/>','<c:out value="${detailVO.edcProgmType }"/>', 'N')" class="btn_ty_m3_c1">비회원 예약하기</a>
								</li>
							</c:if>
</c:if>
						</ul>
						<!-- //btn_area -->

					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->


	<script>

	function fn_reserve_url(programId , eduType , memType) {
		var url =  "/web/edcarsvn/edcarsvnRegist?edcPrgmid="+programId;
		var msg = "";
		var paramAdd = "";
		var eduGender = "<c:out value="${detailVO.eduReqGender}"/>";
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
						alert("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.")
					} else {
						window.location.href =url;
					}
				}
			</c:when>
			<c:when test="${!empty userInfo and !userInfo.isMember}">
				// 예약가능
				if (memType == "Y") {
					alert("현재 비회원 인증 상태입니다.\n로그아웃 후 일반 로그인을 하신 후  예약을 하실 수 있습니다. ");
				} else {
					window.location.href =url;
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
