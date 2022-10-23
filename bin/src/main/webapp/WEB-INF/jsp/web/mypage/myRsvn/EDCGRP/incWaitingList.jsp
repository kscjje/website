<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : incWaitingList.jsp
  * @Description : 교육 예약 목록 페이지
  * @Modification Information
  * @
  * @  수정일       수정자      수정내용
  * @ -------       --------    ---------------------------
  * @ 2020.10.06    전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.10.06
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

	<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="./myRsvnPay" method="post">

		<form:hidden path="gubun" />
		<div class="m_table ty m_table2 mb20">

			<table>
				<caption>교육 예약 신청현황 게시판</caption>
				<thead>
					<tr>
						<th class="number">번호</th>
						<th class="numdt">예약번호/예약일자</th>
						<th class="">교육명/교육일자</th>
						<th class="expay">장소</th>
						<th class="stat">상태</th>
						<th class="expay">취소일자</th>
					</tr>
				</thead>
				<tbody>
					
					<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="6" class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
					</c:if>								
					
					<c:forEach items="${list}" var="resultVO" varStatus="status">
					
						<tr>
							<td>${status.index + 1}</td>
							<td class="numdt left">
							
								<a href='#' onclick="fn_get_modTable('${resultVO.edcRsvnReqid}'); return false;"><c:out value="${resultVO.edcRsvnno}"/></a>
								
								<br />
								
									<fmt:parseDate var="edcReqDate" value="${resultVO.edcReqDate}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${edcReqDate}" pattern="yyyy.MM.dd"/>																	
									<fmt:parseDate var="edcReqTime" value="${resultVO.edcReqTime}" pattern="HHmm"/>
									<fmt:formatDate value="${edcReqTime}" pattern=" HH:mm"/>																		
									
							</td>
							<td class="pro">
								<div class="pro_tt">

									<a href='#' onclick="fn_get_modTable('${resultVO.edcRsvnReqid}'); return false;">
										<ul>
											<li>
												<c:out value="${resultVO.edcPrgmnm}"/>
											</li>
										</ul>
									</a>
									
									<span>
										<fmt:parseDate var="edcReqSdate" value="${resultVO.edcReqSdate}" pattern="yyyyMMdd"/>
										<fmt:formatDate value="${edcReqSdate}" pattern="yyyy.MM.dd"/>&nbsp;
																									
										<fmt:parseDate var="edcReqStime" value="${resultVO.edcReqStime}" pattern="HHmm"/>
										<fmt:formatDate value="${edcReqStime}" pattern="HH:mm"/>
										
										~
										
										<fmt:parseDate var="edcReqEdate" value="${resultVO.edcReqEdate}" pattern="yyyyMMdd"/>
										<fmt:formatDate value="${edcReqEdate}" pattern="yyyy.MM.dd"/>&nbsp;
										
										<fmt:parseDate var="edcReqEtime" value="${resultVO.edcReqEtime}" pattern="HHmm"/>
										<fmt:formatDate value="${edcReqEtime}" pattern="HH:mm"/>										
									</span>
									
								</div>
							</td>
							<td>
								<!--  온라인 교육 -->
								<c:out value="${resultVO.edcPlacenm}"/>
							</td>
							<td class="stat cancelend">
				
								<c:choose>
									<c:when test="${resultVO.edcInterStatus eq 'ING'}">
									
										<c:if test="${resultVO.edcStat eq '20'}">
											<c:if test="${resultVO.cancelYn eq 'Y'}">
												<button type="button" class="btn_ty_s2_c2" onclick="javascript:fn_waiting_cancel('EDCGRP' , '<c:out value="${resultVO.edcRsvnReqid}"/>')">신청취소</button>
											</c:if>	
										</c:if>
										
										<c:if test="${resultVO.edcStat ne '20'}">
											<c:out value="${resultVO.edcApptypeNm}"/>
										</c:if>
										
									</c:when>
									<c:otherwise>
										<c:out value="${resultVO.edcApptypeNm}"/>
									</c:otherwise>
								</c:choose>													
								
							</td>
							<td class="expay red">
								<fmt:parseDate var="edcCanceldate" value="${resultVO.edcCanceldate}" pattern="yyyy-MM-dd"/>
								<fmt:formatDate value="${edcCanceldate}" pattern="yyyy.MM.dd"/>																		
							</td>
						</tr>									
					
					</c:forEach>
					
				</tbody>
			</table>		
			
		</form:form>
		</div>

		<!-- 팝업 -->
		<div class="modal" id="modal1">
			<div class="modal-dialog" style="max-width:820px;">
				<div class="modal-content">
					<div class="modal-title">
						<h4>상세 예약정보</h4>
						<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
					</div>
					<div id="modal_table">

					</div>
				</div>
			</div>
		</div>
		
		<!-- //팝업 -->
		<form:form commandName="edcRsvnMstVO" name="edcRsvnMstVO" id="popFrm" action="./EDCGRP/edcPop" target="rsvnPop" method="post">
			<input type="hidden" name="edcRsvnReqid" id="rsvId"/>
		</form:form>
		
<script>

	function fn_get_modTable(val) {
		var idx = val
		var url = './detailAjax'
		$.ajax({
			url : url,
			data : {
				edcRsvnReqid : idx , gubun : 'EDCGRP'
			},
			method : 'GET',
			dataType : 'html',
			error : function(request, error, jqXHR) {
				alert("불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				$('#modal_table').children().remove();
				$('#modal_table').html(data);
				$('#modal1').modal();
			}
		});
	}
	
	function fn_pop_rsvnCerti(idx) {
		$('#rsvId').val(idx)
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100')
		$('#popFrm').submit()
	}
	
	function fn_waiting_cancel(gubun, rsvnIdx) {
		
		if (confirm("해당 강좌를 정말 취소하시겠습니까?")) {
			
			$.ajax({
			    type : "POST",
			    url  : './reserveCancel',
			    data : {'gubun':gubun , 'rsvnIdxOne' : rsvnIdx},
			    dataType: "json",
			    beforeSend : function(xhr) {
			    	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
			    },
			    success: function(data) {
			    	
			      	if (data != null) {
			        		if ((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')) {
			        		} else {
			        			if (data.ERROR_CODE == -1) {
			            			alert(data.ERROR_MSG);
			            			return;
			        			}
			        		}
			        	}
			        	if (data.result.success) {
			        		alert(data.result.msg);
			        		window.location.reload();
			        	} else {
			        		alert(data.result.msg);
			        	}

			    }, error: function() {
			        	alert("Server Error.");
			    }
		    });
		}			
	}
	
</script>

