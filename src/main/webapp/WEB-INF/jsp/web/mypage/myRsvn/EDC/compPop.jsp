<%
 /**
  * @Class Name : compPop.jsp
  * @Description : 수료증 팝업 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.13    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.13
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/top.jsp"%>

<script>
$(document).ready(function() {
	$('body').addClass('w_pop')
});


window.addEventListener("beforeprint", function(event) {
	$('#printBtn').css('display','none')
	$('#close').css('display','none')
	$('.modal-title').css('display','none')
})

window.addEventListener("afterprint", function(event) {
	$('#printBtn').css('display','inline')
	$('#close').css('display','block')
	$('.modal-title').css('display','inline')
})

</script>
<div class="w_wrap">
	<!-- 팝업 -->
		<div class="modal-dialog" style="max-width:100%;padding: 0;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="modal-txt" style="color:#222;">
							<div class="certi_wrap">
								<div class="img_certi">
									<img src="<%=strContext%>/images/sub/img_certi.jpg" alt="수료증이미지">
								</div> 
								<div class="certi_data certi_data1">
									<p><img src="<%=strContext%>/images/sub/txt_cert01.png" alt="수료번호"><span>${resultVO.edcRsvnNo }</span></p>
								</div>
								<!-- certi_data -->
								<div class="certi_data certi_data2">
									<p><img src="<%=strContext%>/images/sub/txt_certi02_1.png" alt="성명"><span><c:out value="${resultVO.edcReqCustnm }"/></span></p>
								<p>
									<img src="<%=strContext%>/images/sub/txt_certi02_2.png"
										alt="생년월일"><span><fmt:parseDate var="birth"
											value="${resultVO.birthDate }" pattern="yyyyMMdd" /> 
											<fmt:formatDate value="${birth}" pattern="yyyy-MM-dd" /></span>
								</p>
							<p><img src="<%=strContext%>/images/sub/txt_certi02_3.png" alt="교육과정"><span><c:out value="${resultVO.edcPrgmnm }"/></span></p>
									<p>
										<img src="<%=strContext%>/images/sub/txt_certi02_4.png" alt="교육기간"><span><fmt:parseDate
												var="dateStr" value="${resultVO.edcReqSdate}"
												pattern="yyyyMMdd" /> <fmt:parseDate var="dateStr2"
												value="${resultVO.edcReqEdate}" pattern="yyyyMMdd" /> <fmt:formatDate
												value="${dateStr}" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate
												value="${dateStr2}" pattern="yyyy.MM.dd" /></span>
									</p>
								</div>
								<div class="certi_data certi_data3">
									<p><img src="<%=strContext%>/images/sub/txt_certi03.png" alt="수료증내용"></p>
								</div>
								<div class="certi_data certi_data4">
									<p><fmt:formatDate value="${resultVO.edcConfirmDate}" pattern="yyyy년 MM월 dd일" /></p>
								</div>
								<!-- //certi_data -->
							</div>
						</div>
						<!-- //modal-txt -->
						<ul class="btn_area">
							<li><a href="javascript:window.close();" class="btn_ty_m_c4" id="close">닫기</a></li>
							<li><button class="btn_ty_m_c1" id="printBtn" onclick="window.print()">예약증 출력</button></li>
						</ul>
						<!-- //btn_area -->
					</div>
				</div>
			</div>
		<!-- //팝업 -->
</div>
<!-- //팝업 -->