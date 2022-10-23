<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="webroot" rtexprvalue="true" %>
<%@ tag import="java.util.List,
	org.springframework.context.ApplicationContext,
	org.springframework.web.context.WebApplicationContext,
	org.springframework.web.context.support.WebApplicationContextUtils,
	org.springframework.web.servlet.FrameworkServlet,
	org.springframework.web.servlet.support.RequestContextUtils
"%>
<%@ tag import="egovframework.com.uss.ion.pwm.service.EgovPopupManageService"%>
<%@ tag import="egovframework.com.uss.ion.pwm.service.PopupManageVO"%>
<%@ tag import="javax.servlet.ServletContext"%>


<%
List<?> list = null;

try
{
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());

	EgovPopupManageService egovPopupManageService = null;
	//if(wac != null && wac.containsBean("EgovBannerService")){
	if(wac != null ){
	    egovPopupManageService = (EgovPopupManageService) wac.getBean("egovPopupManageService");
	}

	PopupManageVO popupManageVO = new egovframework.com.uss.ion.pwm.service.PopupManageVO();
	list = egovPopupManageService.selectPopupMainList(popupManageVO);
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
<c:set var="list" value="<%=list %>" />
<c:choose>
	<c:when test="${list!=null }">
		<div class="modal notice" id="mainPopup" >

			<div class="modal-dialog middle" style="max-width:928px !important;">
				<div class="modal-content" style="border-radius: 0;">
					<div class="swiper-controls">
						<button class="prev"></button>
						<div class="paged">
						<span>팝업공지</span>
						<div class="swiper-pagination"></div>
					</div>
						<button class="next"></button>
					</div>
					<!-- //swiper-controls -->
					<div class="swiper-container">
						<div class="swiper-wrapper main-pop-wrapper">
		<c:forEach items="${list}" var="p" varStatus="status">
							<div class="modal-body swiper-slide pop-<c:out value="${p.popupId }"/>" data-id="<c:out value="${p.popupId }"/>">
					<c:if test="${p.fileUrl ne ''}">
						<c:choose>
							<c:when test="${fn:startsWith(p.fileUrl,'http')}">
				                <a href="<c:out value='${p.fileUrl}'/>" class="slide" target="<c:out value='${p.linkTarget}'/>">
							</c:when>
							<c:otherwise>
								<a href="<c:out value='${webroot }${p.fileUrl}'/>" class="slide" target="<c:out value='${p.linkTarget}'/>">
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:url var="imageUrl" value="/web/common/file/view/${p.filePath}${p.fileName}" >
					  <c:param name="originName" value="${p.orginFileName }" />
					</c:url>
									<img src="${imageUrl}" alt="<c:out value="${p.popupTitleNm}"/>"  onError="this.src='<c:out value="${webroot }"/>/web/resources/images/data/no_img.jpg'"/>
					<c:if test="${p.fileUrl ne ''}">
								</a>
					</c:if>
								<div class="hidden">
									<c:out value="${p.popupTitleNm}"/>
								</div>
							</div>
		</c:forEach>
						</div>
					</div>
					<!-- swiper-container -->
					<div class="modal-bottom">
						<div class="chk_box">
							<input type="checkbox" id="todayChkPop" class="check_agree">
							<label for="todayChkPop">오늘 하루 안보기</label>
						</div>
						<a href="#none" class="mainPopup_close">닫기</a>
					</div>
				</div>
			</div>
		</div>
		<!-- //팝업 -->

		<script>


			$(function() {
				var cookieName = "en_not_day";
				if (getCookie(cookieName)!=="Y") {
					$('#mainPopup').popup('show');
				}

				// 오늘 하루 안보기
				$( "#todayChkPop" ).on( "click", function() {
					var closeCookieName = "en_not_day";
					setCookieAt00(closeCookieName, "Y", 1);
					$('#mainPopup').popup('hide');
				});
			});

			var layerpop;
			$(function () {
				layerpop = new Swiper('.modal .swiper-container', {
					slidesPerView: 'auto',
					loop: true,
					loopedSlides: <c:out value='${fn:length(list)}'/>,
					speed: 1000,
					spaceBetween: 0,
					grabCursor: true,
					watchSlidesProgress: true,
					mousewheelControl: true,
					keyboardControl: true,
					autoplay: false,
					pagination: {
						el: '.modal .swiper-pagination',
						type: 'fraction',
					},
					navigation: {
						nextEl: ".modal .next",
						prevEl: ".modal .prev"
					},
				});
			});
		</script>
	</c:when>
	<c:otherwise>

	</c:otherwise>
</c:choose>
