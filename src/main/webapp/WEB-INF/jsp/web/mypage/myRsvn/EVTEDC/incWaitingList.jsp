<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : incWaitingList.jsp
  * @Description : 단체교육 예약 목록 페이지
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

<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="./myRsvnPay" method="post">
	<form:hidden path="gubun" />
		<div class="m_table m_table2 mb20 ty">
	
						<!-- m_table -->
						<div class="m_table m_table2 ty mb20">
							<table>
								<caption>단체교육 예약 신청현황 게시판</caption>
								<thead>
									<tr>
										<th class="number">번호</th>
										<th class="numdt">예약번호/예약일자</th>
										<th class="">단체교육 예약/교육일자</th>
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
									
									<c:forEach items="${list}" var="var" varStatus="s">
										
										<tr>
											<td>
												${s.index + 1}
											</td>
											<td class="numdt left">
												<!-- <a href="#" data-toggle="modal" data-target="#modal1">E1234 5678 1234</a> -->
												<a href='#' onclick="fn_get_modTable('${var.evtRsvnIdx}'); return false;"><c:out value="${var.evtRsvnno}"/></a>
											
												<br />
												
												<c:out value="${var.inputDate}"/>
												
												<%-- <fmt:formatDate value="${var.regdate}" pattern="yyyy-MM-dd HH:mm"/> --%>
													
												<!-- 2020.09.30&nbsp;&nbsp;23:11:59 -->
											</td>
											<td class="pro">
												<div class="pro_tt">
												
													<!-- <a href="#" data-toggle="modal" data-target="#modal1"><ul><li>이미테이션게임</li></ul></a> -->
													<a href='#' onclick="fn_get_modTable('${var.evtRsvnIdx}'); return false;"><c:out value="${var.evtName}"/></a>
													
													<span>
														<fmt:parseDate var="evtYmd" value="${var.evtVeingdate}" pattern="yyyyMMdd"/>
													<fmt:formatDate value="${evtYmd}" pattern="yyyy.MM.dd"/>
													/ ${fn:substring(var.evtStime,0,2)}:${fn:substring(var.evtStime,2,4)} ~ ${fn:substring(var.evtEtime,0,2)}:${fn:substring(var.evtEtime,2,4)}													
														<!-- 2021.06.10 / 10:00 ~ 12:00 -->
													</span>
												</div>
											</td>
											<td>
												<c:out value="${var.evtPlacenm}"/>
											</td>
											<td class="stat cancelend">
												<c:choose>
													<c:when test="${var.interStatus eq 'ING'}">
													
														<c:if test="${var.evtRsvnApptype eq '20'}">
														
															<c:if test="${var.cancelYn eq 'Y'}">
																<button type="button" class="btn_ty_s2_c2" onclick="javascript:fn_waiting_cancel('EVTEDC' , '<c:out value="${var.evtRsvnIdx}"/>')">신청취소</button>
															</c:if>
																
														</c:if>
														
														<c:if test="${var.evtRsvnApptype ne '20'}">
															<c:out value="${var.evtRsvnApptypeNm}"/>
														</c:if>
														
													</c:when>
													<c:otherwise>
														<c:out value="${var.evtRsvnApptypeNm}"/>
													</c:otherwise>
												</c:choose>													
												<!-- 취소완료 -->
											</td>
											<td class="expay red">
											
												<fmt:parseDate var="cancelDate" value="${var.cancelDate}" pattern="yyyy-MM-dd"/>
												<fmt:formatDate value="${cancelDate}" pattern="yyyy.MM.dd"/>
																								
												<!-- 2020.09.16<em>11:59:59</em> -->
											</td>
										</tr>
										
									</c:forEach>
									
									<!-- 
									<tr>
										<td>5</td>
										<td class="numdt left"><a href="#" data-toggle="modal" data-target="#modal1">E1234 5678 1234</a><br />2020.09.30&nbsp;&nbsp;23:11:59</td>
										<td class="pro">
											<div class="pro_tt">
												<a href="#" data-toggle="modal" data-target="#modal1">
													<ul>
														<li>이미테이션게임</li>
													</ul>
												</a>
												<span>2021.06.10 / 10:00 ~ 12:00</span>
											</div>
										</td>
										<td>노원수학문화관</td>
										<td class="stat cancelend">취소완료</td>
										<td class="expay red">2020.09.16<em>11:59:59</em></td>
									</tr>
									<tr>
										<td>4</td>
										<td class="numdt left"><a href="#" data-toggle="modal" data-target="#modal1">E1234 5678 1234</a><br />2020.09.30&nbsp;&nbsp;23:11:59</td>
										<td class="pro">
											<div class="pro_tt">
												<a href="#" data-toggle="modal" data-target="#modal1">
													<ul>
														<li>이미테이션게임</li>
													</ul>
												</a>
												<span>2021.06.10 / 10:00 ~ 12:00</span>
											</div>
										</td>
										<td>노원수학문화관</td>
										<td class="stat payend">종료</td>
										<td class="expay red">2020.09.16<em>11:59:59</em></td>
									</tr>
									<tr>
										<td>3</td>
										<td class="numdt left"><a href="#" data-toggle="modal" data-target="#modal1">E1234 5678 1234</a><br />2020.09.30&nbsp;&nbsp;23:11:59</td>
										<td class="pro">
											<div class="pro_tt">
												<a href="#" data-toggle="modal" data-target="#modal1">
													<ul>
														<li>이미테이션게임</li>
													</ul>
												</a>
												<span>2021.06.10 / 10:00 ~ 12:00</span>
											</div>
										</td>
										<td>노원수학문화관</td>
										<td class="stat using"><a href="#" onclick="alert('해당 강좌를 정말 취소하시겠습니까?');">신청취소</a></td>
										<td class="expay red">2020.09.16<em>11:59:59</em></td>
									</tr>
									-->
									
								</tbody>
							</table>
						</div>
						<!-- //m_table -->
						
						<!--
						<table>
							<caption>결제 대기 목록</caption>
							<thead>
								<tr>
									<th class="chek">
										<input type="checkbox" id="total_chk">
										<label for="total_chk">전체선택</label>
									</th>
									<th class="numdt">예약번호/예약일자</th>
									<th class="pro">단체교육  예약/교육일자</th>
									
									<th class="pay">결제금액
										<i><img src="<%=strContext%>/images/sub/ico_info2.png" alt="결제금액안내아이콘"></i>
										<div class="bubble">
											<div class="tri"><img src="<%=strContext%>/images/sub/tri.png" alt="삼각형"></div>
											<p>할인 사유가 모두 적용된 최종 금액 입니다.</p>
										</div>
									</th>
									
									<th class="stat">장소</th>
									<th class="stat">상태</th>
									<th class="expay">취소일자</th>
									<th class="extra">예약취소</th>
									
								</tr>
							</thead>
							<tbody>
							<c:if test="${fn:length(list) == 0}">
								<tr>
									<td colspan="7" class="text-center">등록된 데이타가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${list }" var="var" varStatus="s">
								<tr>
									<td>
										<input type="checkbox" name="rsvnIdx" value="${var.evtRsvnIdx}" data-amt="${var.evtRsvnPayamt }" id="rsvnIdx${s.index}"/>
										<label for="rsvnIdx${s.index}">선택</label>
									</td>
									<td class="numdt left">
										<a href="javascript:fn_get_modTable(${var.evtRsvnIdx})"><c:out value="${var.evtRsvnno }"/></a>
										<br><fmt:formatDate value="${var.regdate}" pattern="yyyy.MM.dd HH:mm"/>
									</td>
									<td class="pro">
										<div class="pro_tt">
											<ul>
												<li><c:out value="${var.evtName }"/></li>
											</ul>
											<span>
											<fmt:parseDate var="extYmd" value="${var.evtVeingdate}" pattern="yyyyMMdd"/>
											<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
											&nbsp;&nbsp; ${fn:substring(var.evtStime,0,2) }:${fn:substring(var.evtStime,2,4) } ~ ${fn:substring(var.evtEtime,0,2) }:${fn:substring(var.evtEtime,2,4) }
											</span>
										</div>
										<c:if test="${var.evtPersnGbn eq '2001' and (var.evtRsvnApptype eq '11' or var.evtRsvnApptype eq '20')}">
											<div class="btn_pro">
											<c:choose>
												<c:when test="${var.editYn eq 'Y'}">
														<button type="button" class="btn_ty_s3_cty" onclick="location.href='./myRsvnEvtModify?evtRsvnIdx=${var.evtRsvnIdx}'">예약수정</button>
												</c:when>
												<c:otherwise>
														<button type="button" class="btn_ty_s3_c2" onclick="alert('교육일 이후에는 수정이 불가능합니다.')">예약수정</button>
												</c:otherwise>
											</c:choose>
											</div>
										</c:if>
									</td>
									<td class="pay"><fmt:formatNumber value="${var.evtRsvnPayamt }"/></td>
									<td class="stat"><c:out value="${var.evtApptypeNm }"/></td>
									<td class="expay red">
										<fmt:formatDate value="${var.evtPaywaitEnddatetime}" pattern="yyyy.MM.dd"/>
										<em><fmt:formatDate value="${var.evtPaywaitEnddatetime}" pattern="HH:mm"/></em>
									</td>
									<td class="cancel"><button type="button" class="btn_ty_s2_c2" onclick="javascript:fn_waiting_cancel('EVTEDC' , '<c:out value="${var.evtRsvnIdx}"/>')">예약취소</button></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>						
						 
						 -->
						
			</form:form>
		</div>

		<form:form commandName="evtrsvnMstVO" name="evtrsvnMstVO" id="popFrm" action="/web/evtedcrsvn/evtEdcrsvnPop" target="rsvnPop" method="post">
			<input type="hidden" name="evtRsvnIdx" id="rsvId" />
		</form:form>
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
<script>
	function fn_get_modTable(val) {
		var idx = val
		var url = './detailAjax'
		$.ajax({
			url : url,
			data : {
				evtRsvnIdx : idx , gubun : 'EVTEDC'
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
		
		if (confirm("해당 단체교육 예약을 정말 취소하시겠습니까?")) {
			
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