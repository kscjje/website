<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/includeTop.jsp"%>

<%
/**
 * @Class Name : edcarsvnListAjax.jsp
 * @Description : 교육 예약 개인 목록 리스트
 * @Modification Information
 * @
 * @  수정일       수정자      수정내용
 * @ -------       --------    ---------------------------
 * @ 2020.09.08    전영석      최초 생성
 *
 *  @author 전영석
 *  @since 2020.09.08
 *  @version 1.0
 *
 */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

	<c:choose>
		<c:when test="${empty edcarsvnMainList}">
		<ul class="event_list one">
			<li>
				<a>
					<div class="e_cont guide"><!-- 목록없을 경우, class: guide 추가 -->
						<dl>
							<dt><img src="<%=strContext%>/images/sub/ico_info_b.png" alt="안내아이콘"></dt>
							<dd>조회된 프로그램이 없습니다</dd>
						</dl>
					</div>
					<!-- //e_cont -->
				</a>
			</li>
		</ul>
		</c:when>
		<c:otherwise>
		<!-- edu_list -->
		
			<ul class="edu_list">
				<c:forEach items="${edcarsvnMainList}" var="item" varStatus="status">
						<c:choose>
							<c:when test="${item.eduInterStatus eq 'ING' and item.edcPncpa <= item.appCnt}">
								<c:set var="statusClass" value="imps" />
							</c:when>
							<c:when test="${item.eduInterStatus eq 'ING'}">
								<c:set var="statusClass" value="" />
							</c:when>
							<c:when test="${item.eduInterStatus eq 'ACCT'}">
								<c:set var="statusClass" value="prep" />
							</c:when>
							<c:when test="${item.eduInterStatus == 'END'}">
								<c:set var="statusClass" value="end" />
							</c:when>
							<c:when test="${item.eduInterStatus == 'OUT'}">
								<c:set var="statusClass" value="imps" />
							</c:when>
						</c:choose>
						<li>
							<div class="edu_info ${statusClass}">
							
								<c:set var="HTMLTag"     value="${item.edcTagnm.replaceAll('_SSPAN','<span class=\"tag\">')}" />
								<c:out value='${HTMLTag.replaceAll("_ESPAN","</span>")}' escapeXml="false" />

								<c:if test="${HTMLTag != null}">
									</span>
								</c:if>
								
								<div class="title_wrap">
								
									<c:if test="${item.edcOnlineyn eq 'Y' }">
										<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt="온라인교육입니다."></i>온라인교육</span>
									</c:if>
									
									<a href="javascript:fn_drt('${item.edcPrgmid}')" class="title"><c:out value="${item.edcPrgmnm}"/></a>
									
								</div>
								
								<div class="info_cont">
									<dl>
										<dt>분류</dt>
										<dd>
											<c:if test="${item.edcProgmType eq '1001'}">
												개인
											</c:if>		
											<c:if test="${item.edcProgmType eq '3001'}">
												가족
											</c:if>
											<c:if test="${item.edcProgmType eq '4001'}">
												개인/단체
											</c:if>																								
										</dd>
									</dl>								
									<dl>
										<dt>대상</dt>
										<dd><c:out value="${item.edcTargetinfo}"/></dd>
									</dl>
									<!-- 
									<dl>
										<dt>비용</dt>
										<dd>
											<c:choose>
												<c:when test="${item.salamt >0 }"><fmt:formatNumber value="${item.salamt}" pattern="#,###" />원</c:when>
												<c:otherwise>무료</c:otherwise>
											</c:choose>
										</dd>
									</dl>
									-->
									<dl>
										<dt>신청기간</dt>
										<dd>
											<c:out value="${item.edcRsvnSdatetime}"/> ~ <c:out value="${item.edcRsvnEdatetime}"/>
										</dd>
									</dl>
									<dl>
										<dt>교육기간</dt>
										<dd>
										
											<c:choose>
												<c:when test="${item.edcProgmType eq '1001' }">
												
													<fmt:parseDate var="dateStr" value="${item.edcSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${item.edcEdate}" pattern="yyyyMMdd" />
													<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />
													<br>${fn:substring(item.edcStime,0,2)}:${fn:substring(item.edcStime,2,4)}
													~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}
													(<c:out value="${item.edcDaygbnNm}"/>)												
												
													<%-- <fmt:parseDate var="dateStr" value="${item.edcSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${item.edcEdate}" pattern="yyyyMMdd" />
													<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />&nbsp;(<c:out value="${item.edcDaygbnNm}"/>) --%>
												</c:when>
												<c:when test="${item.edcProgmType eq '3001' }">
													<%-- <c:out value="${item.edcSdatetime}"/> ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}
													<fmt:parseDate var="dateStr" value="${item.edcSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${item.edcEdate}" pattern="yyyyMMdd" />													
													<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" /> --%>
													
													<c:out value="${item.edcSdatetime}"/> ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}													
													
												</c:when>
												<c:when test="${item.edcProgmType eq '4001' }">
													<%-- <c:out value="${item.edcSdatetime}"/> ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}
													<fmt:parseDate var="dateStr" value="${item.edcSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${item.edcEdate}" pattern="yyyyMMdd" />													
													<fmt:formatDate value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" /> --%>
													
													<c:out value="${item.edcSdatetime}"/> ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}													
													
												</c:when>												
												<c:otherwise>
													<c:out value="${item.edcSdatetime}"/> ~ <c:out value="${item.edcEdatetime}"/>
												</c:otherwise>
											</c:choose>
											
										</dd>
									</dl>
								</div>
							</div>
							<div class="edu_reserv">
							<c:choose>
								<c:when test="${item.eduInterStatus eq 'ING'}">
									<div class="seat">
										<span class="st_num"><em><fmt:formatNumber value="${(item.edcPncpa - item.appCnt) > 0 ? (item.edcPncpa - item.appCnt) : 0 }" pattern="#,###" /></em> <i>명 신청가능</i> <b>/</b> 총 <fmt:formatNumber value="${item.edcPncpa}" pattern="#,###" />석</span>
									</div>
									<ul class="p_btn">
										<li>
											<a href="javascript:fn_drt('${item.edcPrgmid}')" class="btn_ty_c6">내용 더보기</a>
										</li>
										
										<c:choose>
											<c:when test="${item.edcPncpa > item.appCnt}">
											
												<c:choose>
													<c:when test="${item.edcProgmType eq '1001' }">
														<li><a href="javascript:fn_reserve_url('<c:out value="${item.edcPrgmid}"/>','<c:out value="${item.edcProgmType}"/>','<c:out value="${item.edcReqGender}"/>')" class="btn_ty_c5">바로 예약하기</a></li>
													</c:when>
													<c:when test="${item.edcProgmType eq '3001' }">
														<li><a href="javascript:fn_reserve_url('<c:out value="${item.edcPrgmid}"/>','<c:out value="${item.edcProgmType}"/>','<c:out value="${item.edcReqGender}"/>')" class="btn_ty_c5">바로 예약하기</a></li>
													</c:when>
													<c:when test="${item.edcProgmType eq '4001' }">
														<li><a href="javascript:fn_reserve_url_4001('<c:out value="${item.edcPrgmid}"/>','<c:out value="${item.edcProgmType}"/>','<c:out value="${item.edcReqGender}"/>', '1')" class="btn_ty_c5" style="width:90px;">개인 예약</a></li>
														<li><a href="javascript:fn_reserve_url_4001('<c:out value="${item.edcPrgmid}"/>','<c:out value="${item.edcProgmType}"/>','<c:out value="${item.edcReqGender}"/>', '2')" class="btn_ty_c5" style="width:90px;">단체 예약</a></li>
													</c:when>												
													<c:otherwise>
													</c:otherwise>
												</c:choose>												
												
											</c:when>
											<c:otherwise>
												<li><a class="btn_ty_c4" onclick="return false;">정원 마감</a></li>
											</c:otherwise>
										</c:choose>

									</ul>
									
									<!-- 
									<div class="seat">
										<span class="st_num"><em><fmt:formatNumber value="${(item.edcPncpa - item.appCnt)>0?(item.edcPncpa - item.appCnt):0 }" pattern="#,###" /></em> <i>명 신청가능</i> <b>/</b> 총 <fmt:formatNumber value="${item.edcPncpa}" pattern="#,###" />석</span>
									</div>
									<ul class="p_btn">
										<li><a href="javascript:fn_drt('${item.edcPrgmid}')"class="btn_ty_c6">내용 더보기</a></li>
										<li>
											<c:choose>
												<c:when test="${item.edcPncpa > item.appCnt }"><a href="javascript:fn_reserve_url('<c:out value="${item.edcPrgmid}"/>','<c:out value="${item.edcProgmType }"/>','<c:out value="${item.edcReqGender }"/>')" class="btn_ty_c5">바로 예약하기</a></c:when>
												<c:otherwise><a  class="btn_ty_c4" onclick="return false;">정원 마감</a></c:otherwise>
											</c:choose>
										</li>
									</ul>
									-->									
									
								</c:when>
								<c:when test="${item.eduInterStatus eq 'ACCT'}">
									<div class="seat">
										<span class="st_num"><em><c:choose><c:when test="${item.reqDday > 0 }">D-<c:out value="${item.reqDday }"/></c:when><c:when test="${item.reqDday == 0 }">${fn:substring(item.edcRsvnStime,0,2) }:${fn:substring(item.edcRsvnStime,2,4) } 부터 접수가능</c:when></c:choose></em></span>
									</div>
									<ul class="p_btn">
										<li><a href="javascript:fn_drt('${item.edcPrgmid}')" class="btn_ty_c6" >내용 더보기</a></li>
										<li><a  class="btn_ty_c10" onclick="return false;">접수 준비</a></li>
									</ul>
								</c:when>
								<c:when test="${item.eduInterStatus == 'END'}">
									<div class="seat">
										<span class="st_num">접수기간 종료</span>
									</div>
									<ul class="p_btn">
										<li><a href="javascript:fn_drt('${item.edcPrgmid}')" class="btn_ty_c6" >내용 더보기</a></li>
										<li><a class="btn_ty_c1" onclick="return false;">접수 종료</a></li>
									</ul>
								</c:when>
								<c:when test="${item.eduInterStatus == 'OUT'}">
									<div class="seat">
										<span class="st_num">-</span>
									</div>
									<ul class="p_btn">
										<li><a href="javascript:fn_drt('${item.edcPrgmid}')" class="btn_ty_c6" >내용 더보기</a></li>
										<li><a class="btn_ty_c7" onclick="return false;">접수 불가</a></li>
									</ul>
								</c:when>
							</c:choose>

							</div>
						</li>
				</c:forEach>

			</ul>
			
			<!-- //edu_list -->
			<div class="paginate">
				<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_set_list"/>
			</div>
			<!-- //paginate -->
			
		</c:otherwise>
	</c:choose>


<script>
$(document).ready(function() {

	fn_set_param();
	$('#lsize').html("&nbsp;${paginationInfo.totalRecordCount}");
});

function fn_set_param() {
	var temp = '${edcarsvnMainList[0].tbAllCount}';
	var status = '${paramMap.eduAccStus}';
		if (temp > 0) {
			$('#lsize').text(temp);
		} else {
			$('#lsize').text('0');
		}

	$('.sta').each(function() {
		if ($(this).data('status') == status) {
			$(this).addClass('on');
			$('#stat_all').removeClass('on');
		} else {
			$(this).removeClass('on');
		}
	})
	
	var ctg = new Array();
	var ctg2 = new Array();
	
	<c:forEach items="${edcarsvnSecPrmList}" var="item" varStatus="status">
		ctg[${status.index}] = '${item.comCtgcd }';
	</c:forEach>

	<c:forEach items="${edcarsvnThrPrmList}" var="item" varStatus="status">
		ctg2[${status.index}] = '${item.comCtgcd }';
	</c:forEach>
		
	$('.lv2').each(function() {
		var dataCd = $(this).data('cd');
		for (var i = 0; i < ctg.length; i++) {
			var val = ctg[i];
			if (dataCd == val) {
				$(this).fadeIn(150);
			}
		}
	})
	$('.lv3').each(function() {
		var dataCd = $(this).data('cd');
		for (var i = 0; i < ctg2.length; i++) {
			var val = ctg2[i];
			if (dataCd == val) {
				$(this).fadeIn(150);
			}
		}
	})
}
</script>