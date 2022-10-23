<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnListAjax.jsp
  * @Description : 단체예약 목록
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.26    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.26
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

	<ul class="event_list">
	
		<c:choose>
			<c:when test="${empty rList}">
				<li>
					<a>
						<div class="e_cont guide">
							<dl>
								<dt><img src="<%=strContext%>/images/sub/ico_info_b.png" alt="안내아이콘"></dt>
								<dd>조회된 프로그램이 없습니다</dd>
							</dl>
						</div>
						<!-- //e_cont -->
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<c:forEach items="${rList}" var="v" varStatus="st">
					<li>
						<a href="#" onclick="fn_drt('${v.evtNo}'); return false;">
						
							<div class="e_img">
							
								<c:set var="onlyOrginExtNm" value=".${v.evtIntrimgFinnbOnlyOrginExtrn}" />
								<img src="<tags:UploadFileUrl filePath='${v.evtIntrimgFinnbFilePath}' fileName='${v.evtIntrimgFinnbFileName}' originName='${v.evtIntrimgFinnbOrginFileName}' />" alt="${fn:replace(v.evtIntrimgFinnbOrginFileName, onlyOrginExtNm, '')}" />
								
							</div>
							
							<!-- //e_img -->
							<div class="e_cont">
								<div class="e_tit">
									<c:out value="${v.evtName}"/>
								</div>
								<ul class="e_dt">
									<li>
										<dl>
											<dt>장소</dt>
											<dd>
												<c:out value="${v.evtPlacenm}"/>												
											</dd>
										</dl>
									</li>
									<li>
										<dl>
											<dt>기간</dt>
											<dd>

												<fmt:parseDate var="dateStr1" value="${v.evtUseSdate}" pattern="yyyyMMdd" />
												<fmt:parseDate var="dateStr2" value="${v.evtUseEdate}" pattern="yyyyMMdd" />	
												
												<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" />~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />												
																							
											</dd>
										</dl>
									</li>
									<li>
										<dl>
											<dt>접수</dt>
											<dd>

												<c:if test="${v.evtAppPeriodGbn eq '1001'}">
													<fmt:parseDate var="dateOpenStr1" value="${v.evtOpendate}" pattern="yyyyMMdd" />
													
													<fmt:formatDate value="${dateOpenStr1}" pattern="yyyy.MM.dd" />&nbsp;<c:out value="${v.evtOpentimeHh}"/>:<c:out value="${v.evtOpentimeMm}"/> 이후 ~
												</c:if>
												<c:if test="${v.evtAppPeriodGbn eq '2001'}">
													<fmt:parseDate var="dateStr1" value="${v.evtReqSdate}" pattern="yyyyMMdd" />
													<fmt:parseDate var="dateStr2" value="${v.evtReqEdate}" pattern="yyyyMMdd" />	
												
													<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" />&nbsp;<c:out value="${v.evtReqStimeHh}"/>:<c:out value="${v.evtReqStimeMm}"/>~<fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" />&nbsp;<c:out value="${v.evtReqEtimeHh}"/>:<c:out value="${v.evtReqEtimeMm}"/>													
												</c:if>												
																							
											</dd>
										</dl>
									</li>									
									<%-- <li>
										<dl>
											<dt>시간</dt>
											<dd>
												<c:out value="${v.evtTimeinfo}"/>
											</dd>
										</dl>
									</li> --%>									
									<%-- <li>
										<dl>
											<dt>분류</dt>
											<dd>
												<c:out value="${v.evtGbnNm}"/>
											</dd>
										</dl>
									</li> --%>
									
									<li>
										<dl>
											<dt>대상</dt>
											<dd>
												<c:out value="${v.evtTargetage}"/>
											</dd>
										</dl>
									</li>
									
									
									<%-- <li>
										<dl>
											<dt>러닝타임</dt>
											<dd>
												<c:out value="${v.evtRuningTime}"/>
											</dd>
										</dl>
									</li> --%>
									
								</ul>
								<div class="e_price">
									<span class="p_tit">관람비용</span>
									<span class="p_price">
										<em>무료</em> 
									</span>
								</div>
							</div>
							<!-- //e_cont -->
						</a>
					</li>				
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
	</ul>	
	
	<!-- //event_list -->
	<div class="paginate">
		<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_get_list"/>
	</div>
	<!-- //paginate -->
	
<script>
	$(document).ready(function() {
		var temp = '${listSize}';
		if (temp > 0) {
			$('#lsize').text(temp)
		} else {
			$('#lsize').text('0')
		}
	
	});
</script>