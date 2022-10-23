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
<script>
$(document).ready(function() {
	$('body').addClass('w_pop')
});


window.addEventListener("beforeprint", function(event) {
	$('.btn_area').css('display','none')
	$('#close').css('display','none')
	$('.modal-title').css('display','none')
})

window.addEventListener("afterprint", function(event) {
	$('.btn_area').css('display','inline')
	$('#close').css('display','block')
	$('.modal-title').css('display','inline')
})

</script>
<div class="w_wrap">
	<!-- 팝업 -->
		<div class="modal-dialog" style="max-width:100%;padding: 0;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="modal-title">
							<h4>학생 수료증</h4>
							<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
						</div>
						<div class="modal-body certificate" style="overflow-y:auto;">

								<div class="certificate_cont" style="width:639px">
									<p class="title"><c:choose><c:when test="${resultVO.edcProgmType eq '4001'}">마을배움터</c:when><c:otherwise>노원평생교육</c:otherwise></c:choose> 수료증</p>
									<div class="name">
										<dl>
											<dt>수료자 :</dt>
											<dd><span class="certf-name"><c:out value="${resultVO.edcRsvnCustnm }"/></span>님</dd>
										</dl>
										<dl>
											<dt>강좌명 : </dt>
											<dd><span class="certf-title"><c:out value="${resultVO.edcPrgmnm }"/></span></dd>
										</dl>
									</div>
									<p class="center_txt">위 학생은 노원구에서 운영하는<br/>
										<c:choose><c:when test="${resultVO.edcProgmType eq '4001'}">마을배움터</c:when><c:otherwise>평생학습</c:otherwise></c:choose> 프로그램에 성실하게 참여하여<br>
										상기의 교육과정을 훌륭히 마쳣으므로<br>
										이 수여증을 부여합니다.
									</p>
									<div class="subject">
										<span span class="certf-date">수료일 ${fn:substring(resultVO.edcConfirmDate,0,4)}년 ${fn:substring(resultVO.edcConfirmDate,5,7)}월 ${fn:substring(resultVO.edcConfirmDate,8,10)}일</span>
									</div>


								</div>


						</div>
						<ul class="btn_area">
							<li><a href="javascript:window.close();" class="btn_s1_c0 modal1_close">닫기</a></li>
							<li><a href="javascript:;"  onclick="window.print()" class="btn_s1_c1 modal1_close">인쇄</a></li>
						</ul>
						<!-- //btn_area -->


					</div>
				</div>
			</div>
		<!-- //팝업 -->
</div>
<!-- //팝업 -->