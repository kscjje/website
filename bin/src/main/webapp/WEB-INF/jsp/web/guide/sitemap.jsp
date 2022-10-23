<%
 /**
  * @Class Name : sitemap.jsp
  * @Description : 사이트맵 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.22    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.22
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
	<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">사이트맵</h3>
						<ul class="link_area">
							<li><a href="http://www.hisco.co.kr/web/main" class="btn_ty_m3_c1" title="새창열림" target="_blank">노원수학문화관 누리집 이동<i><img src="<%=strContext%>/images/sub/ico_link.png" alt="링크아이콘"></i></a></li>
						</ul>

						<div class="sitemap_wrap">
							<ul>
							<c:forEach items="${ mList }" var="m" varStatus="s">
								<c:choose>
									<c:when test="${m.upperMenuId == 0 && m.mainYn eq 'Y'}">
										<li>
											<div class="site_tit"><c:out value="${m.menuNm }"/></div>
											<ul class="site_list">
												<c:forEach items="${ mList }" var="sub" varStatus="ss">
													<c:if test="${ sub.upperMenuId eq m.menuNo }">
														<li><a href="${sub.menuUrl }"><c:out value="${sub.menuNm }"/></a></li>
													</c:if>
													<c:if test="${ sub.menuNo eq m.menuNo && not empty sub.menuUrl }">
														<li><a href="${sub.menuUrl }"><c:out value="${sub.menuNm }"/></a></li>
													</c:if>
												</c:forEach>
											</ul>
										</li>
									</c:when>
									<c:when test="${m.upperMenuId == 0 && empty m.menuUrl }">
										<li>
											<div class="site_tit"><c:out value="${m.menuNm }"/></div>
											<ul class="site_list">
												<c:forEach items="${ mList }" var="sub" varStatus="ss">
													<c:if test="${ sub.upperMenuId eq m.menuNo }">
														<li><a href="${sub.menuUrl }"><c:out value="${sub.menuNm }"/></a></li>
													</c:if>
												</c:forEach>
											</ul>
										</li>
									</c:when>
								</c:choose>
							</c:forEach>
							</ul>
						</div>
						<!-- //sitemap -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->
