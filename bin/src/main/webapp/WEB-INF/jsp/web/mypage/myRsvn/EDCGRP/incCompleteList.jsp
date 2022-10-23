<%
 /**
  * @Class Name : incCompleteList.jsp
  * @Description : 관람 예약 완료  목록 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.10    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.10
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

<div class="m_table ty m_table2 mb20">
			<table>
				<caption>교육 예약내역 게시판 </caption>
				<thead>
					<tr>
						<th class="number">번호</th>
						<th class="numdt">예약번호/예약일자</th>
						<th class="pro">프로그램명</th>
						<th class="pay">결제금액
							<i><img src="<%=strContext%>/images/sub/ico_info2.png" alt="결제금액안내아이콘"></i>
							<div class="bubble">
								<div class="tri"><img src="<%=strContext%>/images/sub/tri.png" alt="삼각형"></div>
								<p>할인 사유가 모두 적용된 최종 금액 입니다.</p>
							</div>
						</th>
						<th class="stat">상태</th>
						<th class="expay">일자</th>
					</tr>
				</thead>
				<tbody>

<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="6" class="text-center">예약 및 결제완료 내역이 없습니다.</td>
					</tr>
</c:if>
<form:form commandName="edcRsvnMstVO" name="edcRsvnMstVO"  action="./EDC/edcPop" target="rsvnPop" method="post">
	<input type="hidden" name="edcRsvnReqid" id="edcRsvnReqid"/>
</form:form>
<c:forEach items="${list}" var="resultVO" varStatus="status">
					<tr>
						<td class="number"><c:out value="${paginationInfo.totalRecordCount - (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage - status.index}"/></td>
						<td class="numdt left">
							<a href="javascript:fn_get_modTable(${resultVO.edcRsvnReqid}, 1)" id="modal1<c:out value='${resultVO.edcRsvnReqid}'/>"><c:out value="${resultVO.edcRsvnno}"/></a><br>
							<fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/>
						</td>
						<td class="pro">
							<div class="pro_tt">
								<a href="javascript:fn_get_modTable(${resultVO.edcRsvnReqid}, 2)" class="w100" id="modal2<c:out value='${resultVO.edcRsvnReqid}'/>">
									<ul>
										<li><c:out value="${resultVO.edcPartCdNm}"/></li>
										<li><c:out value="${resultVO.edcPrgmnm}"/>
										</li>
									</ul>
								</a>
							 	<span><c:out value="${resultVO.eduPeriod }" /></span>
							</div>
							<div class="btn_pro">
								<c:if test="${resultVO.edcStat eq '11' or  resultVO.edcStat eq '20'}">
									<c:choose>
										<c:when test="${resultVO.edcComplyn eq 'Y'}">
												<button type="button" class="btn_ty_s3_c8" onclick="fn_comp_print('${resultVO.edcRsvnReqid}')">수료증</button>
										</c:when>
										<c:when test="${resultVO.editYn eq 'Y'}">
												<button type="button" class="btn_ty_s3_cty" onclick="location.href='./myRsvnEdcModify?edcRsvnReqid=${resultVO.edcRsvnReqid}'">예약수정</button>
										</c:when>
										<c:otherwise>
												<button type="button" class="btn_ty_s3_c2" onclick="alert('교육 시작일 이후에는 수정이 불가능합니다.')">예약수정</button>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:choose>
									 	<c:when test="${!empty resultVO.oid and resultVO.edcTotamt >0}">
									 		<c:if test="${resultVO.passYn ne 'Y'}">
												<button class="btn_ty_s3_c9" type="button" onclick="fn_move_detail('<c:out value="${resultVO.oid }" />','EDC' , '<c:out value="${myRsvnVO.pageIndex}" />')">결제상세/취소</button>
											</c:if>
										</c:when>
										<c:when test="${resultVO.edcStat eq '20' and (resultVO.rfndSeq > 0 or resultVO.cancelAbleYn eq 'Y')}">
											<c:if test="${resultVO.passYn ne 'Y'}">
												<button class="btn_ty_s3_c2" type="button" onclick="fn_reserve_cancel('<c:out value="${resultVO.edcRsvnReqid}" />','EDC')">예약취소</button>
											</c:if>
										</c:when>
								</c:choose>
							</div>
						</td>
						<td class="pay">
							<c:choose>
								<c:when test="${resultVO.edcTotamt < 1}">무료</c:when>
								<c:otherwise><fmt:formatNumber value="${resultVO.edcTotamt}"/>원</c:otherwise>
							</c:choose>
						</td>
						<td class="stat">
							<c:choose>
								<c:when test="${resultVO.edcStat eq '12'}"><span class="cancelend">${resultVO.edcApptypeNm }</span></c:when>
								<%-- <c:when test="${resultVO.edcStat eq '20' and resultVO.edcTotamt >0}"><span class="payend">${resultVO.edcApptypeNm }</span></c:when> --%>
								<c:when test="${resultVO.edcStat eq '20'}"><span class="useend">${resultVO.edcApptypeNm }</span></c:when>
								<c:when test="${resultVO.edcStat eq '31'}"><span class="cancelend">${resultVO.edcApptypeNm }</span></c:when>
								<c:when test="${resultVO.edcStat eq '32'}"><span class="stat">${resultVO.edcApptypeNm }</span></c:when>
							</c:choose>

						</td>
						<td class="expay red">
						<c:choose>
							<c:when test="${resultVO.edcStat eq '12' or resultVO.edcStat eq '31'}">
								<fmt:formatDate value="${resultVO.moddate}" pattern="yyyy.MM.dd"/><em><fmt:formatDate value="${resultVO.moddate}" pattern="HH:mm"/></em>
							</c:when>
							<c:when test="${resultVO.edcStat eq '32'}">
								&nbsp;
							</c:when>
							<c:when test="${!empty resultVO.payRegdate}">
								<fmt:formatDate value="${resultVO.payRegdate}" pattern="yyyy.MM.dd"/><em><fmt:formatDate value="${resultVO.payRegdate}" pattern="HH:mm"/></em>
							</c:when>
							<c:otherwise>
								<fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd"/><em><fmt:formatDate value="${resultVO.regdate}" pattern="HH:mm"/></em>
							</c:otherwise>
						</c:choose>
						</td>

					</tr>
</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./${(myRsvnVO.appStatus eq '20')?'myRsvnCompList':'myRsvnCancelList'}?pageIndex="/>
		</div>
		<!-- 팝업 -->
		<div class="modal" id="modal1">
			<div class="modal-dialog" style="max-width:820px;">
				<div class="modal-content">
					<div class="modal-title">
						<h4>상세 예약정보</h4>
						<a href="#" class="pop_close" data-dismiss="modal" data-focus-next="">닫기</a>
					</div>
					<div id="modal_table">
					</div>
				</div>
			</div>
		</div>
		<!-- //팝업 -->

<script>
	function fn_get_modTable(val, colGubun) {
		
		var idx = val
		var url = './detailAjax'
		$.ajax({
			url : url,
			data : {
				edcRsvnReqid : idx , gubun : 'EDC'
			},
			method : 'GET',
			dataType : 'html',
			error : function(request, error, jqXHR) {
				alert("불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				$('#modal_table').children().remove();
				$('#modal_table').html(data);
				
				$(".pop_close").attr("data-focus-next", "modal" + colGubun + val);
				
				$('#modal1').modal();
			}
		});

	}

	function fn_pop_rsvnCerti(idx) {
		$('#edcRsvnReqid').val(idx);
		$('#edcRsvnMstVO').attr("action" , "./EDC/edcPop");
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');

		$('#edcRsvnMstVO').submit();
	}
	function fn_comp_print(idx) {
		$('#edcRsvnReqid').val(idx);
		$('#edcRsvnMstVO').attr("action" , "./EDC/compPop");
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');

		$('#edcRsvnMstVO').submit();
	}
	
	function fnAccessibilityFocus() {
		
		$(document).on('keydown', '[data-focus-prev], [data-focus-next]',
		
			function(e) {
			
				var next = $(e.target).attr('data-focus-next'), 
					prev = $(e.target).attr('data-focus-prev'), 
					target = next || prev || false; 
				
				if (!target || e.keyCode != 13) { 
					return; 
				} 
				
				if ( (!e.shiftKey && !!next) || (e.shiftKey && !!prev) ) { 
					setTimeout(function() { 
						
						//$('[data-focus="' + target + '"]').focus();
						
						$('#' + target).focus();
						
					}, 1); 
				} 
			}); 
	}

	
	$(document).ready(function() { 
		fnAccessibilityFocus(); 
	});	
	
</script>
