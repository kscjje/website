<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : detailAjax.jsp
  * @Description : 예약목록 상세모달
  * @Modification Information
  * @
  * @  수정일      수정자      수정내용
  * @ -------      --------    ---------------------------
  * @ 2020.10.06   전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.10.06
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>


		<div class="modal-body scroll2" tabindex="0">
			<div class="modal-txt">
				<p class="stit">예약 정보 확인</p>
					<!-- table -->
					<div class="table2 responsive-type mb50">
						<table>
							<caption>교육예약정보안내표입니다.</caption>
							<colgroup>
									<col style="width:25%">
									<col style="width:30%">
									<col style="width:18%">
									<col style="width:27%">
								</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="vtop">예약번호</th>
									<td class="b" colspan="3">
										<c:out value='${resultVO.edcRsvnNo}' />
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">프로그램명</th>
									<td class="b" colspan="3">
											<div class="title_wrap">
											<c:if test="${resultVO.edcOnlineyn eq 'Y' }">
												<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
											</c:if>
											<c:out value='${resultVO.edcPrgmnm}' />
											</div>
									</td>
								</tr>
							<c:choose>
							<c:when test="${resultVO.edcProgmType eq '1001' }">
								<tr>
									<th scope="row" class="vtop">교육 기간</th>
									<td  colspan="3">
										<fmt:parseDate var="dateStr" value="${resultVO.edcReqSdate}" pattern="yyyyMMdd" />
										<fmt:parseDate var="dateStr2" value="${resultVO.edcReqEdate}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">교육 요일</th>
									<td >
										<c:out value="${resultVO.edcDaygbnNm}"/>
									</td>
									<th scope="row" class="vtop">교육 시간</th>
									<td >
										${fn:substring(resultVO.edcReqStime,0,2)}:${fn:substring(resultVO.edcReqStime,2,4)}
										~ ${fn:substring(resultVO.edcReqEtime,0,2)}:${fn:substring(resultVO.edcReqEtime,2,4)}
									</td>
								</tr>
							</c:when>
							<c:when test="${resultVO.edcProgmType eq '3001' }">
								<tr>
									<th scope="row" class="vtop">교육 기간</th>
									<td  colspan="3">
										<fmt:parseDate var="dateStr" value="${resultVO.edcReqSdate}" pattern="yyyyMMdd" />
										<fmt:parseDate var="dateStr2" value="${resultVO.edcReqEdate}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">교육 요일</th>
									<td >
										<c:out value="${resultVO.edcDaygbnNm}"/>
									</td>
									<th scope="row" class="vtop">교육 시간</th>
									<td >
										${fn:substring(resultVO.edcReqStime,0,2)}:${fn:substring(resultVO.edcReqStime,2,4)}
										~ ${fn:substring(resultVO.edcReqEtime,0,2)}:${fn:substring(resultVO.edcReqEtime,2,4)}
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th scope="row" class="vtop">교육 기간</th>
							 		<td colspan="3">
							 			<fmt:parseDate var="dateStr" value="${resultVO.edcReqSdate}" pattern="yyyyMMdd" />
										<fmt:parseDate var="dateStr2" value="${resultVO.edcReqEdate}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" />
										${fn:substring(resultVO.edcReqStime,0,2)}:${fn:substring(resultVO.edcReqStime,2,4)}
										~
										<fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
										${fn:substring(resultVO.edcReqEtime,0,2)}:${fn:substring(resultVO.edcReqEtime,2,4)}
							 		</td>
							 	</tr>
							</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${resultVO.edcProgmType eq '1001' or resultVO.edcShtlbusYn ne 'Y'}">
								<tr>
									<th scope="row" class="vtop">교육장소</th>
									<td colspan="3">
										<c:out value="${resultVO.edcPlacenm}"/>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th scope="row" class="vtop">교육장소</th>
									<td >
										<c:out value="${resultVO.edcPlacenm}"/>
									</td>
									<th scope="row" class="vtop">셔틀버스</th>
									<td ><c:choose><c:when test="${resultVO.edcShtlbusYn eq 'Y' }">운행함</c:when><c:otherwise>운행안함</c:otherwise></c:choose></td>
								</tr>
							</c:otherwise>
						</c:choose>
						<c:if test="${resultVO.edcShtlbusYn eq 'Y' and !empty resultVO.edcShtlbusGuide }">
								<tr>
									<th scope="row" class="vtop">셔틀버스<br>탑승위치 안내</th>
									<td colspan="3">
										${fn:replace(resultVO.edcShtlbusGuide,crlf,'<br/>')}
									</td>
								</tr>
						</c:if>
						<c:if test="${resultVO.edcShtlbusYn eq 'Y' }">
								<tr>
									<th scope="row" class="vtop">셔틀버스 입소<br/>가능한 위치</th>
									<td colspan="3">
										<c:out value='${resultVO.edcInBusloct}' />
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">셔틀버스 퇴소<br/>가능한 위치</th>
									<td colspan="3">
										<c:out value='${resultVO.edcOutBusloct}' />
									</td>
								</tr>
						</c:if>
								<tr>
									<th scope="row" class="vtop">예약자 이름</th>
									<td>
										<c:out value='${resultVO.edcRsvnCustnm}' />
									</td>
									<th scope="row" class="vtop">휴대전화번호</th>
									<td >
										<tags:HpDecrypt param="${resultVO.edcRsvnMoblphon}"/>
									</td>
								</tr>
								<tr>
									<th scope="row" class="vtop">이메일</th>
									<td colspan="3">
										<tags:EmailDecrypt param="${resultVO.edcEmail}"/>
									</td>
								</tr>


						<c:if test="${resultVO.edcProgmType eq '3001'}">
									<!-- <tr>
										<th scope="row" class="vtop">단체명</th>
										<td colspan="3">
											<c:out value='${resultVO.edcRsvnOrgname}' />
										</td>
									</tr>
									
									<tr>
										<th scope="row" class="vtop">참가 학년</th>
										<td colspan="3">
											<c:out value="${resultVO.edcVistorGradenm }"/>
										</td>
									</tr>
									-->
									<tr>
										<th scope="row" class="vtop">예약인원</th>
										<td colspan="3">
											<fmt:formatNumber value="${resultVO.edcVistnmpr}"/>명
										</td>
									</tr>
									<%-- <tr>
										<th scope="row" class="vtop">방문 인솔자 명</th>
										<td colspan="3">
											<c:out value='${resultVO.edcLeadCustnm}' />
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">방문 인솔자 연락처</th>
										<td colspan="3">
											<tags:HpDecrypt param="${resultVO.edcLeadMoblphon}"/>
										</td>
									</tr> --%>
							</c:if>
							<c:forEach var="item" items="${itemList}" varStatus="status">
									<tr>
										<th scope="row" class="vtop">
											<c:out value='${item.edcMngitemnm}'/>
										</th>
										<td colspan="3">
											<c:out value='${item.itemValue}'/>
										</td>
									</tr>
							</c:forEach>
							<c:if test="${resultVO.edcAddrOpenyn eq 'Y'}">
								<tr>
									<th scope="row" class="vtop">주소</th>
									<td class="b"  colspan="3">
										<div class="w_box mt0">
											<div class="w100">
												<c:out value='${resultVO.edcHomeZipno}' /><br>
												<c:out value='${resultVO.edcHomeAddr1}' />&nbsp;<c:out value='${resultVO.edcHomeAddr2}' />
											</div>
										</div>
									</td>
								</tr>
							</c:if>
							</tbody>
						</table>
					</div>
					<!-- //예약자 정보 입력 -->
				<c:forEach var="item" items="${edcarsvnFamlyinfoList}" varStatus="status">
					<p class="stit">참가자</p>
					<!-- table -->
					<div class="table2 responsive-type mb50">
						<table>
							<colgroup>
								<col style="width:20%">
								<col style="width:80%">
							</colgroup>
							<tbody>
								<c:if test="${resultVO.edcProgmType eq '1001'}">
									<tr>
										<th scope="row" class="vtop">참여학생이름</th>
										<td>
											<c:out value='${item.edcRsvnfmName}' />
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">학년</th>
										<td>
											<c:out value='${item.edcRsvnfmEtc}' />
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">휴대전화번호</th>
										<td>
											<tags:Addr1Decrypt param='${item.edcRsvnfmTelnoDec}'/>
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">입금자명</th>
										<td>
											<c:out value='${item.edcRsvnfmDepositor}' />
										</td>
									</tr>
								</c:if>
								<c:if test="${resultVO.edcProgmType eq '3001'}">
									<tr>
										<th scope="row" class="vtop">입금자명</th>
										<td>
											<c:out value='${item.edcRsvnfmDepositor}' />
										</td>
									</tr>
									<c:if test="${item.edcRsvnfmEtc ne ''}">
										<tr>
											<th scope="row" class="vtop">기타</th>
											<td>
												<c:out value='${item.edcRsvnfmEtc}' />
											</td>
										</tr>
									</c:if>
									<tr>
										<th scope="row" class="vtop">참여 가족 총 인원 수(최대4명)</th>
										<td>
											<c:out value='${item.edcRsvnfmDesc}' />
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">보호자 이름</th>
										<td>
											<c:out value='${item.edcRsvnfmPname}' />
										</td>
									</tr>
									<c:if test="${item.edcRsvnfmPname2 ne ''}">
										<tr>
											<th scope="row" class="vtop">보호자 이름(보호자가 2명인 경우 기입)</th>
											<td>
												<c:out value='${item.edcRsvnfmPname2}' />
											</td>
										</tr>
									</c:if>
									<tr>
										<th scope="row" class="vtop">초등학생 이름 / 학년</th>
										<td>
											<c:out value='${item.edcRsvnfmName}' />
										</td>
									</tr>
									<c:if test="${item.edcRsvnfmName2 ne ''}">
										<tr>
											<th scope="row" class="vtop">초등학생 이름 / 학년(초등학생이 2명인 경우)</th>
											<td>
												<c:out value='${item.edcRsvnfmName2}' />
											</td>
										</tr>
									</c:if>
									<c:if test="${item.edcRsvnfmName2 ne ''}">
										<tr>
											<th scope="row" class="vtop">초등학생 이름 / 학년(초등학생이 3명인 경우)</th>
											<td>
												<c:out value='${item.edcRsvnfmName3}' />
											</td>
										</tr>
									</c:if>
									<tr>
										<th scope="row" class="vtop">연락처(연락가능한 번호)</th>
										<td>
											<tags:Addr1Decrypt param='${item.edcRsvnfmTelnoDec}'/>
										</td>
									</tr>
								</c:if>
								<%-- <tr>
									<th scope="row" class="vtop">성별</th>
									<td>
										<c:out value='${item.edcRsvnfmSexgbnNm}' />
									</td>
								</tr> --%>
							</tbody>
						</table>
					</div>
					<!-- //table -->
				</c:forEach>


			</div>

			<ul class="btn_area">
				<c:choose>
					<c:when test="${resultVO.edcStat eq '20'}">
						<li><a href="javascript:;" onclick="fn_pop_rsvnCerti('${resultVO.edcRsvnReqid}')" class="btn_ty_m3_c1">예약확인증 출력하기</a></li>
					</c:when>
				</c:choose>

				<c:if test="${resultVO.edcStat eq '11' or  resultVO.edcStat eq '20'}">
					<c:choose>
						<c:when test="${resultVO.edcProgmType eq '1001' and resultVO.edcAddrOpenyn ne 'Y'}"></c:when>
						<c:when test="${resultVO.editYn eq 'Y'}">
							<%-- <li><a href="./myRsvnEdcModify?edcRsvnReqid=${resultVO.edcRsvnReqid}" class="btn_ty_m3_c3">예약정보 수정</a></li> --%>
						</c:when>
						<c:otherwise>
							<!-- <li><a href="javascript:;" class="btn_ty_m3_c3" onclick="alert('교육 시작일 이후에는 수정이 불가능합니다.')">예약정보 수정</a></li> -->
						</c:otherwise>
					</c:choose>
				</c:if>
			</ul>

		</div>
		<!--//modal-body  -->
<script>

</script>
