<%
 /**
  * @Class Name : myRsvnEdcModify.jsp
  * @Description : 교육 예약 관련 정보 수정
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>

<script src="<%=strContext%>/js/common_nsm_edu.js"></script>

<%
	String strJusoKey = EgovProperties.getProperty("juso.go.kr.key");
%>

<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">예약 정보 수정</h3>
		<div class="mypage_wrap bor">
			<!-- signup2 -->
			<div class="signup2">
<form:form commandName="eventProgramVO" id="eduSaveForm" method="post">
<input type="hidden" id="comCd"         name="comCd"           value="${detailVO.comcd}"/>
<input type="hidden" id="edcPrgmid"     name="edcPrgmid"       value="${detailVO.edcPrgmid}" />
<input type="hidden" id="edcVisitfamilyCnt"  name="edcVisitfamilyCnt"    value="${detailVO.edcVisitfamilyCnt}" />
<input type="hidden" id="edcSdate"       name="edcSdate"     value="${detailVO.edcSdate}" />
<input type="hidden" id="edcStime"       name="edcStime"     value="${detailVO.edcStime}" />
<input type="hidden" id="edcEdate"       name="edcEdate"     value="${detailVO.edcEdate}" />
<input type="hidden" id="edcEtime"       name="edcEtime"     value="${detailVO.edcEtime}" />
<input type="hidden" id="edcVistnmpr"  name="edcVistnmpr"  value="<c:out value="${detailVO.edcVistnmpr }"/>" />
<input type="hidden" id="edcRsvnReqid"  name="edcRsvnReqid"   value="${detailVO.edcRsvnReqid}"  />
<input type="hidden" id="edcarsvnProgMItemCnt"  name="edcarsvnProgMItemCnt"  value="${edcarsvnProgMItemCnt}" />
<input type="hidden" id="memberEmail" name="memberEmail" value="<c:out value="${email }"/>"  />
<input type="hidden" id="oldEdcEmail" name="oldEdcEmail" value="<c:out value="${detailVO.edcEmail }"/>"  />
				<p class="stit">예약 정보</p>
				<div class="table2 responsive-type mb50" id="requireInfo">
					<table>
						<caption>예약자 정보 확인 및 배송주소 입력하는 표입니다.</caption>
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
									<c:out value='${detailVO.edcRsvnNo}' />
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">프로그램명</th>
								<td class="b" colspan="3">
									<c:if test="${detailVO.edcOnlineyn eq 'Y' }">
										<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
									</c:if>
									<c:out value='${detailVO.edcPrgmnm}' />
								</td>
							</tr>
<c:choose>
<c:when test="${detailVO.edcProgmType eq '1001' }">
							<tr>
								<th scope="row" class="vtop">교육 기간</th>
								<td class="b" colspan="3">
									<fmt:parseDate var="dateStr" value="${detailVO.edcReqSdate}" pattern="yyyyMMdd" />
									<fmt:parseDate var="dateStr2" value="${detailVO.edcReqEdate}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
									(<c:out value="${detailVO.edcOdr}"/> 차)
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">교육 요일</th>
								<td >
									<c:out value="${detailVO.edcDaygbnNm}"/>
								</td>
								<th scope="row" class="vtop">교육 시간</th>
								<td >
									${fn:substring(detailVO.edcReqStime,0,2)}:${fn:substring(detailVO.edcReqStime,2,4)}
									~ ${fn:substring(detailVO.edcReqEtime,0,2)}:${fn:substring(detailVO.edcReqEtime,2,4)}
								</td>
							</tr>
</c:when>
<c:when test="${detailVO.edcProgmType eq '3001' }">
							<tr>
								<th scope="row" class="vtop">교육 기간</th>
						 		<td class="b" colspan="3">
						 			<fmt:parseDate var="dateStr" value="${detailVO.edcReqSdate}" pattern="yyyyMMdd" />
									<fmt:parseDate var="dateStr2" value="${detailVO.edcReqEdate}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" />
									&nbsp;&nbsp;
									${fn:substring(detailVO.edcReqStime,0,2)}:${fn:substring(detailVO.edcReqStime,2,4)}
									~
									${fn:substring(detailVO.edcReqEtime,0,2)}:${fn:substring(detailVO.edcReqEtime,2,4)}
						 		</td>
						 	</tr>
</c:when>
<c:otherwise>
							<tr>
								<th scope="row" class="vtop">교육 기간</th>
						 		<td class="b" colspan="3">
						 			<fmt:parseDate var="dateStr" value="${detailVO.edcReqSdate}" pattern="yyyyMMdd" />
									<fmt:parseDate var="dateStr2" value="${detailVO.edcReqEdate}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" />
									${fn:substring(detailVO.edcReqStime,0,2)}:${fn:substring(detailVO.edcReqStime,2,4)}
									~
									<fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
									${fn:substring(detailVO.edcReqEtime,0,2)}:${fn:substring(detailVO.edcReqEtime,2,4)}
						 		</td>
						 	</tr>
</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${empty detailVO.edcRsvnMemno }">
		<c:set var="modifyYn" value="Y" />
							<tr>
								<th scope="row" class="vtop">이메일</th>
								<td class="b" colspan="3">
									<input type="text" id="edcEmail" name="edcEmail" value="<c:out value="${detailVO.edcEmail }"/>" placeholder="이메일주소를 입력 해 주십시오." title="이메일주소를 입력 해 주십시오." maxlength="50"/>
									<p class="red" id="edcEmail_msg" style="display:none"></p>
								</td>
							</tr>
	</c:when>
	<c:otherwise>
							<tr>
								<th scope="row" class="vtop">이메일</th>
								<td colspan="3">
									<div class="w_box" style="margin-top:0px">
										<div class="w100" style="vertical-align:middle">
											<label for="edcEmail" class="hidden">이메일</label>
											<input type="text" id="edcEmail" name="edcEmail" value="<c:out value="${detailVO.edcEmail }"/>" title="이메일주소를 입력 해 주십시오." />
										</div>
										<div>
											<button type="button" onclick="fn_email_modify()" class="btn_ty_m2_c5">수정하기</button>
										</div>
									</div>
									<p id="edcEmail_msg"></p>
									<p>※ 이메일 변경 후 수정하기 버튼을 클릭해 주세요.<br>※ 회원정보의 이메일 주소도 함께 변경됩니다.</p>
								</td>
							</tr>
	</c:otherwise>
</c:choose>
<!-- 단체 -->
<c:set var="modifyYn" value="N" />
<c:if test="${detailVO.edcProgmType eq '3001'}">
<c:set var="modifyYn" value="Y" />
							<tr>
								<th scope="row">
									<label for="edcRsvnOrgname">단체, 학교, 기관명</label>
								</th>
								<td colspan="3">
									<input type="text" id="edcRsvnOrgname" value="<c:out value="${detailVO.edcRsvnOrgname }"/>" name="edcRsvnOrgname" placeholder="단체, 학교, 기관명을 입력해주세요" title="단체, 학교, 기관명을 입력해주세요" maxlength="100"/>
									<p class="red" id="edcRsvnOrgname_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<label for="edcVistorGradenm">참가 학년</label>
								</th>
								<td colspan="3">
									<input type="text" id="edcVistorGradenm"  value="<c:out value="${detailVO.edcVistorGradenm }"/>" name="edcVistorGradenm" placeholder="참가 학년을 입력해 주세요." title="참가 학년을 입력해 주세요." maxlength="100"/>
									<p class="red" id="edcVistorGradenm_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row">
									<label for="edcRsvnCustnm">방문 인솔자 명</label>
								</th>
								<td colspan="3">
									<input type="text" id="edcReqCustnm" value="<c:out value="${detailVO.edcReqCustnm }"/>" name="edcReqCustnm" placeholder="방문 인솔자 명을 입력해주세요" title="방문 인솔자 명을 입력해주세요" maxlength="15"/>
									<p class="red" id="edcReqCustnm_msg" style="display:none"></p>
								</td>
							</tr>

							<tr>
								<th scope="row">
									<label for="edcReqMoblphon">방문 인솔자 연락처</label>
								</th>
								<td colspan="3">
									<input type="text" id="edcReqMoblphon" value="<tags:HpTelShow hpval="${detailVO.edcReqMoblphon}"/>" name="edcReqMoblphon" class="phoneCheck" placeholder="방문 인솔자 연락처를 입력해주세요" title="방문 인솔자 연락처를 입력해주세요" maxlength="15"/>
									<p class="red" id="edcReqMoblphon_msg" style="display:none"></p>
								</td>
							</tr>
	<c:if test="${detailVO.edcStat eq '20'}">
							<tr>
								<th  scope="row" class="vtop">
									<label for="edcRsvnNum">참여 인원</label>
								</th>
								<td colspan="3" class="b">
									<c:out value="${detailVO.edcVistnmpr }"/>명
								</td>
							</tr>
	</c:if>
</c:if>
<c:if test="${detailVO.edcShtlbusYn eq 'Y'}">
<!-- 셔틀버스 운행 -->
<c:set var="modifyYn" value="Y" />
<c:if test="${!empty detailVO.edcShtlbusGuide }">
							<tr>
								<th scope="row" class="vtop">셔틀버스<br>탑승위치 안내</th>
								<td colspan="3">
									${fn:replace(detailVO.edcShtlbusGuide, crlf , '<br/>')}
								</td>
							</tr>
</c:if>
							<tr>
								<th scope="row"><label for="edcInBusloct">셔틀버스 입소<br/>가능한 위치</label></th>
								<td colspan="3"><input type="text" id="edcInBusloct" value="<c:out value="${detailVO.edcInBusloct }"/>" name="edcInBusloct" placeholder="셔틀버스 입소 가능한 위치를 입력하세요" title="셔틀버스 입소 가능한 위치를 입력하세요" maxlength="25"/>
									<p class="red" id="edcInBusloct_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="edcOutBusloct">셔틀버스 퇴소<br/>가능한 위치</label></th>
								<td colspan="3"><input type="text" id="edcOutBusloct" name="edcOutBusloct" value="<c:out value="${detailVO.edcOutBusloct }"/>" placeholder="셔틀버스 퇴소 가능한 위치를 입력하세요" title="셔틀버스 퇴소 가능한 위치를 입력하세요" maxlength="25"/>
									<p class="red" id="edcOutBusloct_msg" style="display:none"></p>
								</td>
							</tr>
</c:if>

<c:forEach var="item" items="${itemList}" varStatus="status">
<c:set var="modifyYn" value="Y" />
							<tr>
								<th scope="row">
									<input type="hidden" id="edcMngitemid${status.index + 1}" name="edcMngitemid${status.index + 1}" value="${item.edcMngitemid}" />
									<label for="edcMngitemnm${status.index+1}">${item.edcMngitemnm}</label>
								</th>
								<td colspan="3">
									<input type="text" id="edcMngitemnm${status.index + 1}" name="edcMngitemnm${status.index + 1}" value="<c:out value="${item.itemValue }"/>" placeholder="${item.edcMngitemguide}" title="${item.edcMngitemnm}를 입력해주세요">
									<p class="red" id="edcMngitemnm${status.index + 1}_msg" style="display:none"></p>
								</td>
							</tr>
</c:forEach>
<c:if test="${detailVO.edcAddrOpenyn eq 'Y'}"> <!--  주소 수집 -->
<c:set var="modifyYn" value="Y" />
							<tr>
								<th scope="row" class="vtop">주소</th>
								<td colspan="3">
									<div class="w_box mb15">
										<div class="w50">
											<label for="edcHomeZipno" class="hidden">우편번호를 입력</label>
											<input type="text" id="edcHomeZipno" name="edcHomeZipno" value="<c:out value="${detailVO.edcHomeZipno }"/>" placeholder="우편번호를 입력해주세요." title="우편번호를 입력해주세요." readonly/>
										</div>
										<div>
											<button class="btn_ty_m2_c3" type="button" id="searchJusoPop" data-toggle="modal" data-target="#modalJusoSearch" title="주소찾기 레이어열림">주소 찾기</button>
										</div>
									</div>
									<label for="edcHomeAddr1" class="hidden">주소를 입력</label>
									<input type="text" id="edcHomeAddr1" name="edcHomeAddr1" class="mb15" placeholder="주소를 입력해주세요." title="주소를 입력해주세요." value="<c:out value="${detailVO.edcHomeAddr1 }"/>" readonly/>
									<label for="edcHomeAddr2" class="hidden">상세 주소를 입력</label>
									<input type="text" id="edcHomeAddr2" name="edcHomeAddr2" placeholder="상세 주소를 입력해주세요." title="상세 주소를 입력해주세요." value="<c:out value="${detailVO.edcHomeAddr2 }"/>" maxlength="100"/>
									<p class="red" id="edcAddr_msg" style="display:none"></p>
								</td>
							</tr>

</c:if>
						</tbody>
					</table>
					<!-- //table -->
				</div>
				<!-- //예약자 정보 입력  -->
<c:if test="${detailVO.edcProgmType eq '2001' or detailVO.edcProgmType eq '9001'}">
<!-- 캠프 또는 1회성 -->
	<c:set var="familyNo" value="1" />
	<c:set var="modifyYn" value="Y" />
	<c:forEach var="item" items="${edcarsvnFamlyinfoList}" varStatus="status">
				<p class="stit">참가자${status.index+1 } <c:if test="${status.index eq 0}"><span class="tb_txt">※ 참가자 본인도 참가하실 경우 작성하셔야 합니다.</span></c:if>
					<c:if test="${status.index eq 1}"><span class="tb_txt">※ 참가자 2명 까지 필수 입니다.</span></c:if>
					<c:if test="${status.index > 1}"><span class="tb_txt">※ 이름과 휴대전화번호를 입력하지 않으면 해당 참가자는 삭제됩니다.</span></c:if>
				</p>
				<div class="table2 responsive-type mb50">
					<table>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="edcRsvnfmName${familyNo}">이름</label></th>
								<td><input type="text" id="edcRsvnfmName${familyNo}" name="edcRsvnfmName${familyNo}"  value="<c:out value="${item.edcRsvnfmName }"/>" placeholder="이름을 입력해주세요." title="참가자 이름 입력"/>
									<p class="red" id="edcRsvnfmName${familyNo}_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="edcRsvnfmTelno${familyNo}">휴대전화번호</label></th>
								<td><input type="tel" id="edcRsvnfmTelno${familyNo}" name="edcRsvnfmTelno${familyNo}" value="<tags:HpTelShow hpval="${item.edcRsvnfmTelnoDec}"/>" class="phoneCheck" placeholder="‘-’ 을 빼고 입력 해 주십시오." title="참가자 연락처 입력">
									<p class="red" id="edcRsvnfmTelno${familyNo}_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row">성별</th>
								<td>
									<div class="radio_box">
										<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${familyNo}" id="edcRsvnfmSexgbn${familyNo}1" value="1001" <c:if test="${item.edcRsvnfmSexgbn eq '1001' }">checked</c:if>><em></em><span>남자</span></label>
										<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${familyNo}" id="edcRsvnfmSexgbn${familyNo}2" value="2001" <c:if test="${item.edcRsvnfmSexgbn eq '2001' }">checked</c:if>><em></em><span>여자</span></label>
										<p class="red" id="edcRsvnfmSexgbn${familyNo}_msg" style="display:none"></p>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>
		<c:set var="familyNo" value="${familyNo + 1 }" />
	</c:forEach>
	<c:forEach var="i" begin="${familyNo }" end="${detailVO.edcVisitfamilyCnt}" step="1">
				<p class="stit">참가자${i } <c:if test="${i eq 1}"><span class="tb_txt">※ 참가자 본인도 참가하실 경우 작성하셔야 합니다.</span></c:if>
					<c:if test="${i eq 2}"><span class="tb_txt">※ 참가자 2명 까지 필수 입니다.</span></c:if>
				</p>
				<div class="table2 responsive-type mb50">
					<table>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><label for="edcRsvnfmName${i}">이름</label></th>
								<td><input type="text" id="edcRsvnfmName${i}" name="edcRsvnfmName${i}" placeholder="이름을 입력해주세요." title="참가자 이름 입력"/>
									<p class="red" id="edcRsvnfmName${i}_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="edcRsvnfmTelno${i}">휴대전화번호</label></th>
								<td><input type="tel" id="edcRsvnfmTelno${i}" name="edcRsvnfmTelno${i}" class="phoneCheck" placeholder="‘-’ 을 빼고 입력 해 주십시오." title="참가자 연락처 입력">
									<p class="red" id="edcRsvnfmTelno${i}_msg" style="display:none"></p>
								</td>
							</tr>
							<tr>
								<th scope="row">성별</th>
								<td>
									<div class="radio_box">
										<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${i}" id="edcRsvnfmSexgbn${i}1" value="1001"><em></em><span>남자</span></label>
										<label><input type="radio" class="radio" name="edcRsvnfmSexgbn${i}" id="edcRsvnfmSexgbn${i}2" value="2001"><em></em><span>여자</span></label>

										<p class="red" id="edcRsvnfmSexgbn${i}_msg" style="display:none"></p>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>
</c:forEach>
</c:if>
</form:form>
<c:if test="${modifyYn eq 'Y' }">
				<!-- //join_wrap -->
				<ul class="btn_area mb50">
					<li><a href="javascript:fn_reserve_save()" class="btn_ty_m_c1">예약정보 저장</a></li>
				</ul>
</c:if>
							<!-- //btn_area -->
<c:if test="${detailVO.edcProgmType eq '3001' and detailVO.edcStat eq '11'}">
				<p class="stit">예약 인원 <span class="tb_txt">※ 예약하신 인원 이하로 수정하실 수 있습니다.</span></p>
				<!-- board_ty -->
				<div class="board_ty">
					<ul>
						<li>
							<div class="info">
								<!-- info_tit -->
								<div class="info_tit">
									<span>
										예약인원 입력
									</span>
								</div>
								<!-- //info_tit -->
								<!-- numcontrol -->
								<div class="numcontrol num01">
											<input type="button" class="btn_minus" onclick="fn_cnt_change('minus', 0,  -1, this)">
											<input id="personCnt" name="personCnt" value="<c:out value="${detailVO.edcVistnmpr }"/>" class="count" value="1" type="text" autocomplete="false" title="예약 인원수 입력">
											<input type="button" class="btn_plus" onclick="fn_cnt_change('plus', 0,  1, this)">
								</div>
								<!-- //numcontrol -->
							</div>
							<!-- //info -->

						</li>

					</ul>
				</div>
				<!-- //join_wrap -->
				<ul class="btn_area mb50">
					<li><a href="javascript:fn_person_save()" class="btn_ty_m_c1">예약인원 저장</a></li>
				</ul>
				<!-- //btn_area -->
</c:if>
			</div>
		</div>
					<!-- //inner -->
<ul id="cancel_area" class="btn_area long">
<c:choose>
	<c:when test="${detailVO.edcStat eq '11'}">
			<li><a href="./myRsvnList?gubun=EDC" class="btn_ty_m_c3">나의 예약 목록</a></li>
	</c:when>
	<c:otherwise>
			<li><a href="./myRsvnCompList?gubun=EDC&pageIndex=${param.pageIndex}" class="btn_ty_m3_c3">나의 예약 목록</a></li>
	</c:otherwise>
</c:choose>
</ul>

				</div>
			</div>
				<!-- //sub_cont -->

				<!-- //주소변경하기 -->
				<div class="modal" id="modalJusoSearch">
					<div class="modal-dialog" style="max-width:670px;">

						<div class="modal-content">
							<div class="modal-title">
								<h4>우편번호 찾기</h4>
								<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
							</div>
							<div class="modal-body">

								<div class="search_wrap w100">
<form>
										<fieldset>
											<legend>게시판 검색</legend>
											<div class="search_box">
												<label for="searchJusoWrd">검색어 : </label>
												<input id="searchJusoWrd" name="searchJusoWrd" title="검색어를 입력하세요" type="text" class="input_text" placeholder="예) 올림픽로 300">
												<button type="button" id="searchJuso" class="button"><span>검색</span></button>

												<inut id="jusoCurrentPage"  type="hidden">
												<inut id="jusoCountPerPage" type="hidden">

											</div>
										</fieldset>
</form>
								</div>

								<!-- //search_wrap -->
								<div class="table2 ty scroll" id="jusoSearchResult">
								</div>
								<div class="paginate" id="jusoPageApi">
								</div>
								<!-- //paginate -->
							</div>
						</div>

					</div>
				</div>
				<!-- //우편번호 찾기 -->
				<!-- //팝업 -->

	<script type="text/javascript">

		$(function() {
			$("input[type=text],input[type=tel]").keyup(function() {
				var id = $(this).attr("id");
				$('#'+id + "_msg").hide();

				if (id.indexOf("edcHome") >= 0) {
					$('#edcAddr_msg').hide();
				}
			});

			$("input[type=radio]").click(function() {
				var id = $(this).attr("name");
				$('#'+id + "_msg").hide();
			});

			$('.phoneCheck').keyup(function() {
				$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
			});

			$('.count').keyup(function() {
				if ($(this).val() == "") {
					$(this).val("0");
				}
				if ($(this).val().length > 1 && $(this).val().indexOf("0") == 0) {
					$(this).val(parseInt($(this).val()));
				}
				if ( isNaN($(this).val())) {
					alert("숫자만 입력해주세요.");
					$(this).val("0");
				}

				if (fn_cnt_change('cus',  0, 0, this)) {
					//
				}
			});
		});

		function fn_cnt_change(itemCd, index, cnt, obj) {
			var varMaxCnt     = Number("${detailVO.edcVistnmpr}");
			var varPernCnt = $("#personCnt").val();

			if (itemCd == 'plus') {
				varPernCnt ++;

				if (varMaxCnt < varPernCnt) {
					alert("신청하신 인원 " + varMaxCnt + "명 이하로 수정하실 수 있습니다.");
					return false;
				}

				$("#personCnt").val(varPernCnt);

			} else if (itemCd == 'minus') {
				varPernCnt --;
				if (varPernCnt <= 1) {
					alert("더는 줄일 수 없습니다");
					return false;
				}

				$("#personCnt").val(varPernCnt);

			} else if (varMaxCnt< varPernCnt) {
				alert("신청하신 인원 " + varMaxCnt + "명 이하로 수정하실 수 있습니다.");
				return false;
			}
			return false;
		}

		function fn_person_save() {
			var varMaxCnt     = Number("${detailVO.edcVistnmpr}");
			var varPernCnt = $("#personCnt").val();

			if (varPernCnt < ${detailVO.edcGrpMincnt}) {
				alert("단체 신청은 최소  ${detailVO.edcGrpMincnt}명 이상 신청하실 수 있습니다.");
				$("#personCnt").focus();
				return ;
			} else if (varMaxCnt< varPernCnt) {
				alert("신청하신 인원 " + varMaxCnt + "명 이하로 수정하실 수 있습니다.");
				$("#personCnt").focus();
				return;
			} else {
				$('#edcVistnmpr').val(varPernCnt);
				$('body').showLoading();
				fn_ajaxEduOnly("./myRsvnEdcNumSave", $("#eduSaveForm"), fn_rsvnsave_callback);

			}
		}

		function fn_email_modify() {
			var email = $('#edcEmail').val();
			var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식

			if (email =="") {
				alert("이메일 주소를 입력해 주세요.");
				$('#edcEmail').focus();
			} else if (!emailRule.test(email)) {
				alert("이메일 주소가 형식에 맞지 않습니다.");
				$('#edcEmail').focus();
			} else {
				$('body').showLoading();
				fn_ajaxEduOnly("../../edcarsvn/edcarsvnEmailSave", $("#eduSaveForm"), fn_email_callback);
			}
		}

		function fn_email_callback(data) {
			$('body').hideLoading();
			if (data.RESULT.code == "SUCCESS") {
				$('#edcEmail_msg').text("정상적으로 수정되었습니다.").addClass("green").removeClass("red").show();
				$('#edcEmail').val(data.RESULT.msg);
				$('#oldEdcEmail').val(data.RESULT.msg);
				$('#memberEmail').val(data.RESULT.msg);
			} else {
				$('#edcEmail_msg').text(data.RESULT.msg).addClass("red").removeClass("green").show();
			}
		}


		function fn_reserve_save() {
			var flag = true;
			var emailFlag = true;
			var firstId = "";

			$.each($('#requireInfo').find("input[type=text]") , function(index,item) {
				var val =  $.trim($(this).val());
				var id = $(this).attr("id");

				if ( val== "") {
					var text =  $(this).attr("placeHolder");
					if (text == "") {
						text = $(this).parent().parent().find("label").text() + "을(를) 입력해 주세요.";
					}
					$('#'+id + "_msg").html(text).show();
					if (firstId == "") firstId = id;
					flag = false;
				}
			});

			var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
			var hpRule = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
<c:choose>
	<c:when test="${empty detailVO.edcRsvnMemno}">
				if (!emailRule.test($('#edcEmail').val())) {
					if (firstId == "") firstId = "edcEmail";
					$('#edcEmail_msg').text("이메일 주소가 형식에 맞지 않습니다.").show();
					emailFlag = false;
				}
	</c:when>
	<c:otherwise>
				if ($('#edcEmail').val() != $('#memberEmail').val() && $('#edcEmail').val() != $('#oldEdcEmail').val()) {
					$('#edcEmail_msg').text("[수정하기] 버튼을 클릭하셔서 이메일 정보를 변경해 주세요.").addClass("red").removeClass("green").show();
					if (firstId == "") firstId = "edcEmail";
					emailFlag = false;
				}
	</c:otherwise>
</c:choose>
			<c:if test="${detailVO.edcAddrOpenyn eq 'Y'}">
				if ( $('#edcHomeAddr1').val() == "" || $('#edcHomeAddr2').val() == "") {
					if (firstId == "") firstId = "edcHomeAddr2";
					$('#edcAddr_msg').text("주소를 모두 입력해 주세요.").show();
					flag = false;
				}

			</c:if>

			if ($('#edcVisitfamilyCnt').val() != "" && parseInt($('#edcVisitfamilyCnt').val()) > 0) {
				for(var i = 1; i <= parseInt($('#edcVisitfamilyCnt').val()); i++) {
					if (i <= 2) {
						if ($.trim($('#edcRsvnfmName' + i).val()) == "") {
							$('#edcRsvnfmName' + i + "_msg").html("참가자 "+i + "의 이름을 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmName' + i;
							flag = false;
						}
						if ($.trim($('#edcRsvnfmTelno' + i).val()) == "") {
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처를 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							flag = false;
						} else if (!hpRule.test($('#edcRsvnfmTelno' + i).val())) {
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처가 형식에 맞지 않습니다.").show();
							flag = false;
						}
						if ($('input[name=edcRsvnfmSexgbn'+i+']:checked').length < 1) {
							$('#edcRsvnfmSexgbn' + i + "_msg").html("참가자 "+i + "의 성별을 선택해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmSexgbn' + i + "1";
							flag = false;
						}
					} else if ($.trim($('#edcRsvnfmName' + i).val()) != "" || $.trim($('#edcRsvnfmTelno' + i).val()) != "") {
						if ($.trim($('#edcRsvnfmName' + i).val()) == "") {
							$('#edcRsvnfmName' + i + "_msg").html("참가자 "+i + "의 이름을 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmName' + i;
							flag = false;
						}
						if ($.trim($('#edcRsvnfmTelno' + i).val()) == "") {
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처를 입력해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							flag = false;
						} else if (!hpRule.test($('#edcRsvnfmTelno' + i).val())) {
							if (firstId == "") firstId = 'edcRsvnfmTelno' + i;
							$('#edcRsvnfmTelno' + i + "_msg").html("참가자 "+i + "의 연락처가 형식에 맞지 않습니다.").show();
							flag = false;
						}

						if ($('input[name=edcRsvnfmSexgbn'+i+']:checked').length < 1) {
							$('#edcRsvnfmSexgbn' + i + "_msg").html("참가자 "+i + "의 성별을 선택해 주세요.").show();
							if (firstId == "") firstId = 'edcRsvnfmSexgbn' + i + "1";
							flag = false;
						}
					}
				}
			}

			if (flag && emailFlag) {
				$('body').showLoading();
				fn_ajaxEduOnly("./myRsvnEdcSave", $("#eduSaveForm"), fn_rsvnsave_callback);
			} else if (flag && !emailFlag) {
				$('#edcEmail').focus();
			} else {
				alert("신청정보를 모두 입력해 주세요.");
				$('#'+firstId).focus();
			}
		}

		function fn_rsvnsave_callback(data) {

			console.log("call fn_rsvnsave_callback");
			console.log(data);

			$('body').hideLoading();

			if (data.RESULT.EXE_YN == 'Y' && data.RESULT.RSVN_REQ_ID != 0) {
				// 완료 페이지 이동
				alert("수정되었습니다.");
				<c:choose>
					<c:when test="${detailVO.edcProgmType eq '3001'}">
						if (data.RESULT.NUM_SAVE) {
							window.location.replace("./myRsvnList?gubun=EDC");
						} else {
							window.location.reload();
						}
					</c:when>
					<c:when test="${detailVO.edcStat eq '11'}">
						window.location.replace("./myRsvnList?gubun=EDC");
					</c:when>
					<c:otherwise>
						window.location.replace("./myRsvnCompList?gubun=EDC&pageIndex=${param.pageIndex}");
					</c:otherwise>
				</c:choose>

				return false;

			} else {
				alert("오류! 수정에 실패했습니다.");
				return false;
			}
		}


		//----------------------------------------------------------------주소 연계 시작
		/*
		$("#searchJusoPop").click(function() {
			$('#modalJuso').modal();
		});
		*/

		$('#modalJuso').on('show.bs.modal', function (e) {
			$("#jusoSearchResult").hide();
		})

		$("#searchJuso").click(function() {

			//var varConfmKey     = "devU01TX0FVVEgyMDIwMDgyNDE3MTExNzExMDA5Mjk=";
			var varConfmKey     = "<%= strJusoKey %>";

			var varSearchWord   = $("#searchJusoWrd").val();
			var varResultType   = "json";

			var varCurrentPage  = $("#jusoCurrentPage").val();
			if ((varCurrentPage === undefined) || (varCurrentPage == null) || (varCurrentPage == '')) {
				varCurrentPage = "1";
				$("#jusoCurrentPage").val(varCurrentPage);
			}

			var varCountPerPage  = $("#jusoCountPerPage").val();
			if ((varCountPerPage === undefined) || (varCountPerPage == null) || (varCountPerPage == '')) {
				varCountPerPage = "5";
				$("#jusoCountPerPage").val(varCountPerPage);
			}

			if ((varSearchWord === undefined) || (varSearchWord == null) || (varSearchWord == '')) {
				alert("[도로명주소, 건물명 또는 지번입력] 정보를 입력하세요");
				return;
			}

			if (!fn_checkSearchedWord(varSearchWord)) {
				return ;
			}

			fn_ajaxIfOnly("/intrfc/adres/getAddrApi", {keyword : varSearchWord, confmKey : varConfmKey, currentPage : varCurrentPage, countPerPage : varCountPerPage, resultType : varResultType},  fn_callBack);

		});

		function fn_callBack(data) {

			var varCountPerPage = data.results.common.countPerPage;
			var varCurrentPage  = data.results.common.currentPage;
			var varErrorCode    = data.results.common.errorCode;
			var varErrorMessage = data.results.common.errorMessage;

			var varJusoRsltH = "<table>";
				varJusoRsltH += "	<caption>주소</caption>";
				varJusoRsltH += "<colgroup>";
				varJusoRsltH += "<col style='width:80%'>";
				varJusoRsltH += "<col style='width:20%'>";
				varJusoRsltH += "</colgroup>";
				varJusoRsltH += "<tbody>";

			var varJusoRsltB = "";

			var varJusoRsltT = "</tbody>";
			    varJusoRsltT += "</table>";

			if (varErrorCode == 0) {

				var varJuso = data.results.juso;

				var varZipNo     = "";
				var varJibunAddr = "";
				var varRoadAddr  = "";
				for(var i = 0; i < varJuso.length; i++) {

					varZipNo     = varJuso[i].zipNo;
					varJibunAddr = varJuso[i].jibunAddr;
					varRoadAddr  = varJuso[i].roadAddr;

					varJusoRsltB += "<tr>";
					varJusoRsltB += "	<td>";
					varJusoRsltB += "		<ul class='addr_list'>";
					varJusoRsltB += "			<li>";
					varJusoRsltB += "				<dl>";
					varJusoRsltB += "					<dt>도로명</dt>";
					varJusoRsltB += "					<dd class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varRoadAddr + "</a></dd>";
					varJusoRsltB += "				</dl>";
					varJusoRsltB += "			</li>";
					varJusoRsltB += "			<li>";
					varJusoRsltB += "				<dl>";
					varJusoRsltB += "					<dt>지번</dt>";
					varJusoRsltB += "					<dd><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varJibunAddr + "</a></dd>";
					varJusoRsltB += "				</dl>";
					varJusoRsltB += "			</li>";
					varJusoRsltB += "		</ul>";
					varJusoRsltB += "	</td>";
					varJusoRsltB += "	<td class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varZipNo + "</a></td>";
					varJusoRsltB += "</tr>";

					//varJusoRsltB += "<tr class='n_top'>";
					//varJusoRsltB += "	<td class='cat'>" + varZipNo + "</td>";
					//varJusoRsltB += "	<td class='title left'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>지번 ▶ " + varJibunAddr + "</br>도로명 ▶ " + varRoadAddr + "</a></td>";
					//varJusoRsltB += "</tr>";

				}

				if (varJuso.length >= 1) {
				} else {

					varJusoRsltB += "<tr>";
					varJusoRsltB += "	<td>";
					varJusoRsltB += "		주소 정보를 찾을 수 없습니다.";
					varJusoRsltB += "	</td>";
					varJusoRsltB += "	<td class='md'>" + varZipNo + "</td>";
					varJusoRsltB += "</tr>";

					//varJusoRsltB += "<tr class='n_top'>";
					//varJusoRsltB += "	<td class='cat' colspan='2'>주소 정보를 찾을 수 없습니다.</td>";
					//varJusoRsltB += "	</td>";
					//varJusoRsltB += "</tr>";

				}

				$("#jusoSearchResult").empty();
				$("#jusoPageApi").empty();
				$("#jusoSearchResult").show();
				$("#jusoSearchResult").append(varJusoRsltH + varJusoRsltB + varJusoRsltT);

				fn_PageMake(data);

			}
		}

		function fn_jusoFSelect(zipCode, readAddr) {

			console.log("최종 선택 도로명 주소******************************************************************>>>");
			console.log(zipCode);
			console.log(readAddr);

			$("#jusoSearchResult").empty();
			$("#jusoPageApi").empty();
			$("#searchJusoWrd").val("");

			$("#edcHomeZipno").val(zipCode);
			$("#edcHomeAddr1").val(readAddr);

			$("#edcHomeAddr2").focus();

			return false;

		}

		//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
		function fn_checkSearchedWord(objValue) {

			if (objValue.length > 0) {

				//특수문자 제거
				var expText = /[%=><]/ ;
				if (expText.test(objValue) == true) {
					alert("특수문자를 입력 할수 없습니다.") ;
					objValue = objValue.split(expText).join("");
					return false;
				}

				//특정문자열(sql예약어의 앞뒤공백포함) 제거
				var sqlArray = new Array(
					"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
		             		 "UNION",  "FETCH", "DECLARE", "TRUNCATE"
				);

				var regex;
				for(var i = 0; i < sqlArray.length; i++) {
					regex = new RegExp( sqlArray[i] ,"gi") ;

					if (regex.test(objValue) ) {
					    alert("\"" + sqlArray[i] + "\"와(과) 같은 특정문자로 검색할 수 없습니다.");
						objValue = objValue.replace(regex, "");
						return false;
					}
				}
			}
			return true ;
		}

		/*
		function enterSearch() {
			var evt_code = (window.netscape) ? ev.which : event.keyCode;
			if (evt_code == 13) {
				event.keyCode = 0;
				getAddr(); //jsonp사용시 enter검색
			}
		}
		*/

		$("#searchJusoWrd").keydown(function(event) {
			if (event.which == 13) {
				event.keyCode = 0;
				$('#searchJuso').trigger('click');
				return false;
			}

		});

		function fn_GoPage(pageNum) {

			$("#jusoCurrentPage").val(pageNum);
			$('#searchJuso').trigger('click');

		}

		function fn_PageMake(jsonStr) {

			var total = jsonStr.results.common.totalCount;

			var pageNum = Number($("#jusoCurrentPage").val());
			var paggingStr = "";

			if (total < 1) {
			} else {

				var PAGEBLOCK = Number($("#jusoCountPerPage").val());
				var pageSize  = Number($("#jusoCountPerPage").val());

				var totalPages = Math.floor((total-1)/pageSize) + 1;
				var firstPage = Math.floor((pageNum-1)/PAGEBLOCK) * PAGEBLOCK + 1;

				if ( firstPage <= 0 ) firstPage = 1;
				var lastPage = firstPage-1 + PAGEBLOCK;

				if ( lastPage > totalPages ) lastPage = totalPages;

				var nextPage = lastPage + 1 ;
				var prePage = firstPage - 5 ;

				if ( firstPage > PAGEBLOCK ) {
					paggingStr +=  "<a href='javascript:fn_GoPage("+prePage+");'>이전</a>  " ;
				}

				if (totalPages >= 2) {
					for(i = firstPage; i <= lastPage; i++) {
						if (pageNum == i) {
							paggingStr += "<a style='font-weight:bold;color:blue;font-size:15px;' href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
						} else {
							paggingStr += "<a href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
						}
					}
				}

				if (lastPage < totalPages) {
					paggingStr +=  "<a href='javascript:fn_GoPage(" + nextPage + ");'>다음</a>";
				}

				$("#jusoPageApi").html(paggingStr);

			}
		}
		//----------------------------------------------------------------주소 연계 끝

	</script>
