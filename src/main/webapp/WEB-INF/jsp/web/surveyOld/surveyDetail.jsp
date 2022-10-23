<%
	 /**
	  * @Class Name : surveyDetail.jsp
	  * @Description : 설문조사 페이지
	  * @Modification Information
	  * @
	  * @  수정일       수정자      수정내용
	  * @ -------       --------    ---------------------------
	  * @ 2020.10.08    김희택      최초 생성
	  *
	  *  @author 김희택
	  *  @since 2020.10.08
	  *  @version 1.0
	  *
	  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
	<section class="feedback_ui">
		<div class="top_box">
			<div class="inner">
				<div class="top_head">
					<h1><a href="#">노원수학문화관 로고</a></h1>
					<a class="btn_close">닫기</a>
				</div>
				<!-- //top_head -->
				<form:form commandName="surveyResultVO" id="srvForm" action="/usurvey/surveyAction" method="post">
				<input type="hidden" name="qestnarId" id="qestnarId" value="${vo.qestnarId }"/>
				<input type="hidden" name="qestnarStdno" id="qestnarStdno" value="${sendVO.qestnarStdno }"/>
				<input type="hidden" name="qenstarSendseq" id="qenstarSendseq" value="${sendVO.qenstarSendseq }"/>
				<input type="hidden" name="qestnarMembgbn" id="qestnarMembgbn" value="${rsvnVO.rsvnMembgbn }"/>
				<input type="hidden" name="qestnarMemno" id="qestnarMemno" value="${rsvnVO.memNo }"/>
				<input type="hidden" name="qestnarMemWebid" id="qestnarMemWebid" value="${rsvnVO.webId }"/>
				<input type="hidden" name="qestnarUsername" id="qestnarUsername" value="${rsvnVO.rsvnCustNm }"/>
				<input type="hidden" name="qestnarUsertelno" id="qestnarUsertelno" value="${rsvnVO.rsvnTelno }"/>
				<input type="hidden" name="qestnarUserpartcd" id="qestnarUserpartcd" value="${rsvnVO.rsvnPartcd }"/>
				<input type="hidden" name="qestnarProgrmid" id="qestnarProgrmid" value="${rsvnVO.programId }"/>
				<input type="hidden" name="qestnarTerminalType" id="qestnarTerminalType" />
				<input type="hidden" name="reguser" id="reguser" value="${rsvnVO.webId }"/>
				<div class="top_info">
					<dl>
						<dt><span><c:out value="${sendVO.qestnarUsername}"/></span> 님</dt>
						<dd>오늘 방문해 주셔서 감사합니다.</dd>
					</dl>
					<span class="exp"><em>방문일</em><c:out value="${ rsvnVO.regdate}"/></span>
				</div>
				<!-- //top_info -->
			</div>
			<!-- //inner-->
		</div>
		<!-- top_box -->
		<div class="txt_box">
			<div class="inner">
				<!-- 
				안녕하세요. 노원수학문화관 입니다.<br>
				오늘 노원수학문화관 방문 즐거우셨나요? 여러분들의 방문에<br>
				대해 만족도를 여쭈려고 합니다. 더 나은 관람, 교육을<br>
				위하여 언제나 노력하는 노원수학문화관이 되도록<br>
				하겠습니다. 감사합니다.
				-->
				
				<c:out value="${sendVO.qestnarGuide}"/>
				
			</div>
		</div>
		<!-- //txt_box -->
		<div class="fb_cont">
			<c:forEach items="${vo.qstList }" var="qst" varStatus="qs">
			<input type="hidden" name="dtlList[${qs.index }].qestnsSeq" value="${qst.qestnsSeq }"/>
			<input type="hidden" name="dtlList[${qs.index }].qestnarId" value="${qst.qestnarId }"/>
			<input type="hidden" name="dtlList[${qs.index }].qestnarStdno" value="${sendVO.qestnarStdno }"/>
			<input type="hidden" name="dtlList[${qs.index }].reguser" value="${sendVO.qestnarMembWebid }"/>
			
					<c:choose>
						<c:when test="${qst.qestnsType eq '0000' }">
							<div class="check_list">					
								<h2><c:out value="${qst.qestnsName }"/></h2>
							</div>
							<!-- //check_list -->
						</c:when>
						<c:when test="${qst.qestnsType eq '1001' }">
							<div class="check_list">					
								<h2><c:out value="${qst.qestnsName }"/></h2>
								<input type="hidden" name="dtlList[${qs.index }].resltItemseq" class="answer" />
								<input type="hidden" name="dtlList[${qs.index }].resltItemscore" class="answer" />
									<ul>
										<c:forEach items="${qst.itemList }" var="item" varStatus="s">
											<c:if test="${qst.qestnsSeq eq item.qestnsSeq }">
												<li class="checked">
													<dl>
														<dt>${item.qestnarItemseq }</dt>
														<dd>${item.qestnarItemnm }</dd>
													</dl>
												</li>
											</c:if>
										</c:forEach>
									</ul>
										
							</div>
							<!-- //check_list -->
						</c:when>
						<c:when test="${qst.qestnsType eq '2001' }">
							<div class="check_list">					
								<h2><c:out value="${qst.qestnsName }"/></h2>
								<input type="hidden" name="dtlList[${qs.index }].resltItemseq" class="answer" />
									<ul>
										<c:forEach items="${qst.itemList }" var="item" varStatus="s">
											<c:if test="${qst.qestnsSeq eq item.qestnsSeq }">
												<li class="checked">
													<dl>
														<dt>${item.qestnarItemseq }</dt>
														<dd>${item.qestnarItemnm }</dd>
													</dl>
												</li>
											</c:if>
										</c:forEach>
									</ul>
										
							</div>
							<!-- //check_list -->
						</c:when>
						<c:when test="${qst.qestnsType eq '3001' }">
							<div class="txt_list">
								<h2><c:out value="${qst.qestnsName }"/></h2>
									<label for="txt2" class="hidden">이유 입력</label>
									<textarea class="txtAnswer" id="txt${qs.count }" name="dtlList[${qs.index }].resltSbjct" style="height: 175px" placeholder="200자이내로 작성 가능합니다." maxlength="200"></textarea>
							</div>
						</c:when>
					</c:choose>
			</c:forEach>
		</div>
		<!-- //fb_cont -->
		<ul class="btn_area">
			<li><a href="javascript:window.close()" class="btn_ty_m_c2">취소</a></li>
			<li><a href="javascript:fn_send_data()" class="btn_ty_m_c1">완료 하기</a></li>
		</ul>
		<!-- //btn_area -->		
		</form:form>
	</section>
	<!-- //feeback_ui -->
	<script>
	var agent;
	$(document).ready(function() {
		agent = fn_getAgent()
		if (agent == 'mb') {
			$('#qestnarTerminalType').val('2001')
		} else {
			$('#qestnarTerminalType').val('1001')
		}
	});
	
	
	$('.check_list li.checked').bind("click", function() {	
		if ($(this).hasClass('chk') == false) {				
			$(this).closest('div.check_list').find('.answer').val($(this).find('dt').text())
			$(this).parent().find('.checked').removeClass('chk');
			$(this).addClass('chk');
		}
	});
	
	function fn_send_data() {
		var flag = true
		$('.check_list, .txt_list').each(function() {
			$this = $(this)
			if ($this.find('.answer').val() == '') {
				alert('질문에 대한 답변을 선택해주십시오.')
				$(this).find('.answer').focus()
				flag = false;
				return false;
			}
			if ($this.find('.txtAnswer').val() == '') {
				alert('질문에 대한 답변을 작성해주십시오.')
				$(this).find('.txtAnswer').focus()
				flag = false;
				return false;
			}
		})
		
		if (flag) {			
			$('#srvForm').submit()
		}
	}
	</script>