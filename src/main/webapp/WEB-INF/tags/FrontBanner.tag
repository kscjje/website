<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="comCd" rtexprvalue="true" %>
<%@ attribute name="bannerGbn" rtexprvalue="true" %>
<%@ attribute name="bannerType" rtexprvalue="true" %>
<%@ attribute name="webroot" rtexprvalue="true" %>
<%@ tag import="java.util.List,
	org.springframework.context.ApplicationContext,
	org.springframework.web.context.WebApplicationContext,
	org.springframework.web.context.support.WebApplicationContextUtils,
	org.springframework.web.servlet.FrameworkServlet,
	org.springframework.web.servlet.support.RequestContextUtils
"%>
<%@ tag import="egovframework.com.uss.ion.bnr.service.EgovBannerService"%>
<%@ tag import="egovframework.com.uss.ion.bnr.service.BannerVO"%>
<%@ tag import="javax.servlet.ServletContext"%>


<%
List<BannerVO> list = null;
if(bannerType==null || "".equals(bannerType)) bannerType="default";
try
{
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());


	EgovBannerService egovBannerService = null;
	//if(wac != null && wac.containsBean("EgovBannerService")){
	if(wac != null ){
	    egovBannerService = (EgovBannerService) wac.getBean("egovBannerService");
	}

	BannerVO bannerVO = new egovframework.com.uss.ion.bnr.service.BannerVO();
	bannerVO.setComCd(comCd);
	bannerVO.setBannerGbn(bannerGbn);
	list = egovBannerService.selectBannerListCurrent(bannerVO);
	if(list.size()==0){
	    list = null;
	}
}
catch(Exception e)
{
    list = null;
    //out.print("오류가 발생하였습니다.");
    //System.out.println("오류가 발생하였습니다.");
}
%>


<c:set var="webroot" value="<%=webroot %>" />
<c:set var="bannerType" value="<%=bannerType %>" />
<c:set var="list" value="<%=list %>" />

<c:choose>
	<c:when test="${bannerType eq 'default'}">
		<c:choose>
			<c:when test="${list!=null }">
				<c:forEach items="${list}" var="b" varStatus="status">
					<div class="swiper-slide">
					<c:choose>
						<c:when test="${b.linkUrl eq ''}">
							<a href="javascript:" class="slide">
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${b.linkType!=null && b.linkType eq '2'}">
							                <a href="<c:out value="${webroot }"/>/web/banner/view?bannerId=${b.bannerId}" class="slide" target="_self">
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${fn:startsWith(b.linkUrl,'http')}">
							                <a href="<c:out value='${b.linkUrl}'/>" class="slide" target="<c:out value='${b.linkTarget}'/>">
										</c:when>
										<c:otherwise>
							                <a href="<c:out value='${webroot }${b.linkUrl}'/>" class="slide" target="<c:out value='${b.linkTarget}'/>">
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
<c:url var="imageUrl" value="/web/common/file/view/${b.filePath}${b.fileName}" >
  <c:param name="originName" value="${b.orginFileName }" />
</c:url>
							<p class="img">
								<img src="${imageUrl}" alt="<c:out value="${b.bannerImage}"/>"  onError="this.src='<c:out value="${webroot }"/>/web/resources/images/data/no_img02.jpg'"/>
							</p>
							<div>
								<strong><c:out value="${b.bannerNm}"/></strong>
								<span><c:out value="${b.bannerDc}"/></span>
							</div>
						</a>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
					<div class="swiper-slide">
						<a href="javascript:" class="slide">
							<p class="img">
								<img src="<c:out value="${webroot }"/>/web/resources/images/data/no_img02.jpg" alt="임시이미지"/>
							</p>
							<div>
								<strong>메인배너</strong>
								<span>메인배너를 등록해주세요.</span>
							</div>
						</a>
					</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${bannerType eq 'noti'}">
		<c:if test="${!empty list}">
				<c:forEach items="${list}" var="b" varStatus="status">
					<c:if test="${status.index == 0 }">
						<c:choose>
						<c:when test="${b.linkUrl eq ''}">
							<a href="javascript:">
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${b.linkType!=null && b.linkType eq '2'}">
							                <a href="<c:out value="${webroot }"/>/web/banner/view?bannerId=${b.bannerId}" target="_self">
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${fn:startsWith(b.linkUrl,'http')}">
							                <a href="<c:out value='${b.linkUrl}'/>" target="<c:out value='${b.linkTarget}'/>">
										</c:when>
										<c:otherwise>
							                <a href="<c:out value='${webroot }${b.linkUrl}'/>" target="<c:out value='${b.linkTarget}'/>">
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:url var="imageUrl" value="/web/common/file/view/${b.filePath}${b.fileName}" >
					  <c:param name="originName" value="${b.orginFileName }" />
					</c:url>
							<img src="${imageUrl}" alt="<c:out value="${b.bannerImage}"/>"  onError="this.src='<c:out value="${webroot }"/>/web/resources/images/data/no_img02.jpg'"/>
							<div class="hidden">
								<ul>
									<li><c:out value="${b.bannerNm}"/></li>
									<li><c:out value="${b.bannerDc}"/></li>
									<li>자세히보기버튼</li>
								</ul>
							</div>
						</a>
					</c:if>
				</c:forEach>
		</c:if>
	</c:when>
	<c:otherwise>
		<script>alert("FrontBanner.tag 에 등록되지 않은 타입입니다. 등록후 이용해주세요.");</script>
	</c:otherwise>
</c:choose>
