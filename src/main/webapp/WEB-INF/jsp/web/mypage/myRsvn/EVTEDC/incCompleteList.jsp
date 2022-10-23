<%
 /**
  * @Class Name : incCompleteList.jsp
  * @Description : 단체교육 예약 완료  목록 페이지
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------       --------    ---------------------------
  * @ 2021.07.15    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.07.15
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

<div class="m_table ty m_table2">
			<table>
				<caption>단체교육 예약 목록</caption>
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
						<td colspan="7" class="text-center">등록된 데이타가 없습니다.</td>
					</tr>
</c:if>
<form:form commandName="evtrsvnMstVO" name="evtrsvnMstVO" id="popFrm" action="/web/evtrsvn/evtrsvnPop" target="rsvnPop" method="post">
	<input type="hidden" name="evtRsvnIdx" id="rsvId" value="${var.evtRsvnIdx }"/>
</form:form>
<c:forEach items="${list}" var="var" varStatus="status">

					<tr>
						<td class="number"><c:out value="${paginationInfo.totalRecordCount - (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage - status.index}"/></td>
						<td class="numdt left">
							<a href="javascript:fn_get_modTable(${var.evtRsvnIdx}, 1)" id="modal1<c:out value='${var.evtRsvnIdx}'/>"><c:out value="${var.evtRsvnno}"/></a><br>
							<fmt:formatDate value="${var.regdate}" pattern="yyyy.MM.dd HH:mm"/>
						</td>
						<td class="pro">
							<div class="pro_tt">
							<a href="javascript:fn_get_modTable(${var.evtRsvnIdx}, 2)" class="w100" id="modal2<c:out value='${var.evtRsvnIdx}'/>">
								<ul>
									<li><c:out value="${var.evtName}"/></li>
								</ul>
							</a>
							<span>
								<fmt:parseDate var="extYmd" value="${var.evtVeingdate}" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
								/ ${fn:substring(var.evtStime,0,2) }:${fn:substring(var.evtStime,2,4) } ~ ${fn:substring(var.evtEtime,0,2) }:${fn:substring(var.evtEtime,2,4) }
							</span>
							</div>
							<div class="btn_pro">
								<c:if test="${var.evtPersnGbn eq '2001' and (var.evtRsvnApptype eq '11' or var.evtRsvnApptype eq '20')}">
									<c:choose>
										<c:when test="${var.editYn eq 'Y'}">
												<button type="button" class="btn_ty_s3_cty" onclick="location.href='./myRsvnEvtModify?evtRsvnIdx=${var.evtRsvnIdx}'">예약수정</button>
										</c:when>
										<c:otherwise>
												<button type="button" class="btn_ty_s3_c2" onclick="alert('관람일 이후에는 수정이 불가능합니다.')">예약수정</button>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:choose>
									 	<c:when test="${!empty var.oid and var.evtRsvnSaleamt >0}">
											<button class="btn_ty_s3_c9" type="button" onclick="fn_move_detail('<c:out value="${var.oid }" />','EVT' , '<c:out value="${myRsvnVO.pageIndex}" />')">결제상세/취소</button>
										</c:when>
										<c:when test="${var.evtRsvnApptype eq '20' and (var.rfndSeq > 0  or var.cancelAbleYn eq 'Y')}">
											<button class="btn_ty_s3_c2" type="button" onclick="fn_reserve_cancel('<c:out value="${var.evtRsvnIdx }" />','EVT', '<c:out value="${var.evtPartcd }" />')">예약취소</button>
										</c:when>
								</c:choose>
							</div>
						</td>
						<td class="pay">
							<c:choose>
								<c:when test="${var.evtRsvnSaleamt < 1}">무료</c:when>
								<c:otherwise><fmt:formatNumber value="${var.evtRsvnPayamt}"/>원</c:otherwise>
							</c:choose>
						</td>
						<td class="stat">
							<c:choose>
								<c:when test="${var.evtRsvnApptype eq '12'}"><span class="stat"><c:out value="${var.evtApptypeNm }"/></span></c:when>
								<%-- <c:when test="${var.evtRsvnApptype eq '20' and var.evtRsvnSaleamt>0}"><span class="payend"><c:out value="${var.evtApptypeNm }"/></span></c:when> --%>
								<c:when test="${var.evtRsvnApptype eq '20'}"><span class="useend"><c:out value="${var.evtApptypeNm }"/></span></c:when>
								<c:when test="${var.evtRsvnApptype eq '31'}"><span class="cancelend"><c:out value="${var.evtApptypeNm }"/></span></c:when>
								<c:when test="${var.evtRsvnApptype eq '32'}"><span class="stat"><c:out value="${var.evtApptypeNm }"/></span></c:when>
							</c:choose>
						</td>
						<td class="expay red">
							<c:choose>
								<c:when test="${var.evtRsvnApptype eq '12' or var.evtRsvnApptype eq '31'}">
									<fmt:formatDate value="${var.moddate}" pattern="yyyy.MM.dd"/><em><fmt:formatDate value="${var.moddate}" pattern="HH:mm"/></em>
								</c:when>
								<c:when test="${var.evtRsvnApptype eq '32'}">
									&nbsp;
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${var.payregdate}" pattern="yyyy.MM.dd"/><em><fmt:formatDate value="${var.payregdate}" pattern="HH:mm"/></em>
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
				evtRsvnIdx : idx , gubun : 'EVT'
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
		$('#rsvId').val(idx)
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100')
		$('#popFrm').submit()
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