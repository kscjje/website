<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
	<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}"/>">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
<c:choose>
	<c:when test="${orgType eq '2001' or  orgType eq '4001' or orgType eq '5001' or orgType eq 'dongari'}">
				<div class="sub_top type01">
					<h3><c:out value="${SELECTED_MENU_OBJ.menuNm}" /></h3>
				</div>
	</c:when>
	<c:when test="${orgType eq '9001'}">
				<div class="sub_top type03">
					<div class="pc_link">
						<ul class="pc_list">
							<c:forEach items="${orgTypeList}" var="item" varStatus="status">
								<li><a href="?orgMtype=${item.cd }" <c:if test="${codeDetail.cd eq item.cd }">class="on"</c:if>><c:out value="${item.cdNm }" /></a></li>
							</c:forEach>
						</ul>
					</div>

					<div class="mobile_link">
						<a href="#" class="allbtn">
							<h3>${codeDetail.cdNm }</h3><i></i>
						</a>
						<div class="link">
							<ul class="link_list">
							<c:forEach items="${orgTypeList}" var="item" varStatus="status">
								<li><a href="?orgMtype=${item.cd }"><c:out value="${item.cdNm }" /></a></li>
							</c:forEach>
							</ul>
						</div>
					</div>
				</div>
	</c:when>
	<c:otherwise>
				<div class="sub_top type03">
					<div class="pc_link">
						<ul class="pc_list">
							<c:if test="${orgType eq '3001'}"><li ><a href="./3001" <c:if test="${ empty orgDetail}">class="on"</c:if>>평생시민대학이란?</a></li></c:if>
							<c:forEach items="${orgList}" var="item" varStatus="status">
								<li><a href="?orgNo=${item.orgNo }" <c:if test="${orgDetail.orgNo eq item.orgNo}">class="on"</c:if>><c:out value="${item.comnm }" /></a></li>
							</c:forEach>
						</ul>
					</div>

					<div class="mobile_link">
						<a href="#" class="allbtn">
							<c:choose>
								<c:when test="${orgType eq '3001' and empty orgDetail}"><h3>평생시민대학이란?</h3><i></i></c:when>
								<c:otherwise><h3>${orgDetail.comnm }</h3><i></i></c:otherwise>
							</c:choose>
						</a>
						<div class="link">
							<ul class="link_list">
							<c:if test="${orgType eq '3001'}"><li><a href="./3001">평생시민대학이란?</a></li></c:if>
							<c:forEach items="${orgList}" var="item" varStatus="status">
								<li><a href="?orgNo=${item.orgNo }"><c:out value="${item.comnm }" /></a></li>
							</c:forEach>
							</ul>
						</div>
					</div>
				</div>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${orgType eq '9001' }"><!-- 기타 유관기관 -->
				<div class="ex_box t_left">
					<c:out value="${fn:replace(codeDetail.remark,crlf , '<br/>')}" escapeXml="false"/>
				</div>
				<div class="detail_tab st1">
					<ul>
						<li>
							<a href="#tab01" class="on btn">소개</a>
						</li>
						<li>
							<a href="<c:url value="${webDir}/edc/program/list#searchTab=ORGAN&orgLtype=${orgType}&orgMtype=${codeDetail.cd}"/>" target="_blank">강좌신청</a>
						</li>
					</ul>
					<div class="tab_box">
						<div class="tab_con on" id="tab01">
							<div class="tab_list">
								<ul>
									<li>
										<div class="table t_center">
											<table>
												<caption></caption>
												<colgroup>
													<col style="width:6.5%">
													<col style="width:13%">
													<col style="width:15.5%">
													<col style="width:48%">
													<col style="width:17%">
												</colgroup>
												<thead>
													<tr>
														<th>번호</th>
														<th>기관유형</th>
														<th>기관명</th>
														<th>기관 주소</th>
														<th>연락처</th>
													</tr>
												</thead>
												<tbody>
<c:forEach items="${orgList}" var="item" varStatus="status">
													<tr>
														<td>${status.index+1 }</td>
														<td>${codeDetail.cdNm}</td>
														<td>
															<c:choose>
																<c:when test="${empty item.orgUrl}">
																	${item.comnm }
																</c:when>
																<c:otherwise>
																	<a href="${item.orgUrl}" class="under" target="_blank">${item.comnm }</a>
																</c:otherwise>
															</c:choose>
														</td>
														<td>${item.addr }</td>
														<td>${item.orgTel }</td>
													</tr>
</c:forEach>
												</tbody>
											</table>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
	</c:when>
	<c:when test="${empty contentsVO }">
				<!-- // sub_top -->
				<div class="ex_box t_left">
					<c:out value="${fn:replace(orgContentsVO.orgGuide,crlf , '<br/>')}" escapeXml="false"/>
				</div>
				<div class="detail_tab st1">
					<ul>
						<li>
							<a href="#tab01" class="on btn">소개</a>
						</li>
						<li>
							<a href="#tab02" class="btn">강좌신청 안내</a>
						</li>
						<li>
							<a href="<c:url value="${webDir}/edc/program/list#searchTab=ORGAN&orgLtype=${orgType}&orgMtype=${orgDetail.orgNo}"/>" target="_blank">강좌신청</a>
						</li>
					</ul>
					<div class="tab_box">
						<div class="tab_con on" id="tab01">

							<div class="img_con">
								<c:if test="${!empty orgContentsVO.orgFileName}">
									<img src="<tags:UploadFileUrl filePath="${orgContentsVO.orgFilePath}" fileName="${orgContentsVO.orgFileName }" originName="${orgContentsVO.orgFileOrigin }"/>"/>
								</c:if>
							</div>
							<div class="tab_list">
								<ul>
									<li>
										<div class="table">
											<table>
												<caption>기관 소개 표입니다. -주소, 전화번호, 지하철 편, 버스 편, 컨텐츠 관리부서</caption>
												<colgroup>
													<col style="width:18.75%">
													<col style="width:81.25%">
												</colgroup>
												<tbody>
													<tr>
														<th scope="row">주소</th>
														<td>${orgDetail.addr }
															<c:if test="${!empty orgContentsVO.orgPlaceurl}">
															&nbsp;<a href="${orgContentsVO.orgPlaceurl}" class="btn_s2_c2 btn_pop" title="새창열기" target="_blank">지도 바로가기</a>
															</c:if>
														</td>
													</tr>
													<c:if test="${!empty orgDetail.orgUrl}">
													<tr>
														<th>홈페이지</th>
														<td> <a href="${orgDetail.orgUrl }" target="blank" class="a_link" title="새창열기">${orgDetail.orgUrl }</a></td>
													</tr>
													</c:if>
													<tr>
														<th>전화번호</th>
														<td>${orgDetail.orgTel }</td>
													</tr>
													<c:if test="${!empty orgContentsVO.orgSubwayGuide}">
													<tr>
														<th>지하철 편</th>
														<td>${fn:replace(orgContentsVO.orgSubwayGuide , crlf , '<br/>') }</td>
													</tr>
													</c:if>
													<c:if test="${!empty orgContentsVO.orgBusGuide}">
													<tr>
														<th>버스 편</th>
														<td>${fn:replace(orgContentsVO.orgBusGuide , crlf , '<br/>')}</td>
													</tr>
													</c:if>
													<tr>
														<th>컨텐츠 관리부서</th>
														<td>${orgDetail.charger } &nbsp; ${orgDetail.chargertel }</td>
													</tr>
												</tbody>
											</table>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div class="tab_con" id="tab02">
							${orgContentsVO.orgClassmngGuide }
						</div>
					</div>
				</div>
	</c:when>
	<c:otherwise>
				<c:set var="cnts" value="${fn:replace(contentsVO.cnts , '../../resources/' , '../resources/')}" />
				<c:out value="${cnts }" escapeXml="false"/>


				<c:if test="${editAuthYn eq true }">
					<div class="btn_area">
						<a href="<c:url value="${webDir}/contents/${contentsVO.contentsSeq}/regist"/>" class="btn_s1_c1 long_txt">소개/안내 컨텐츠 수정(관리자전용)</a>
					</div>
				</c:if>

				<c:if test="${!empty fileList2 }">
					<script>
						var html = "";

						<c:forEach items="${fileList2}" var="item" varStatus="status">
						html += "<img src=\"<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>\"/>";
						</c:forEach>

						$('#tab02').append(html);
					</script>
				</c:if>
	</c:otherwise>
</c:choose>
<c:if test="${orgType eq '4001' }">
				<div class="btn_area">
					<a href="<c:url value="${webDir}"/>/mypage/myTwedu/openRegi" class="btn_s1_c2 long_txt">마을배움터 개설신청 (바로가기)</a>
					<a href="<c:url value="${webDir}"/>/mypage/myTwedu/openList" class="btn_s1_c1 long_txt">마을배움터 개설현황 확인 (바로가기)</a>
				</div>
</c:if>
<c:if test="${orgType eq 'dongari' }">
				<div class="btn_area">
					<a href="<c:url value="${webDir}/mypage/dongari/list"/>" class="btn_s1_c2 long_txt">학습동아리 개설현황 확인 (바로가기)</a>
					<a href="./dongari/write" class="btn_s1_c1 long_txt">학습동아리 개설신청 (바로가기)</a>
				</div>
</c:if>
			</div>

</body>