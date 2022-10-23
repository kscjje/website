<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : incWaitingList.jsp
  * @Description : 매스휴먼북 예약 목록 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.16    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.16
  *  @version 1.0
  *  @see
  *
  */
%>

<c:if test="${subGubun eq 'rsvn'}">

	<c:if test="${fn:length(rsvnlist) != 0}">
		
		<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="./myRsvnPay" method="post">
		
			<div class="m_table m_table2 ty mb20">		
		
				<table>
					<caption>결제 대기 목록</caption>
					<colgroup>
						<col style="width:5%">
						<col style="width:20%">
						<col style="width:*">
						<col style="width:9%">
						<col style="width:9%">
						<col style="width:9%">
					</colgroup> 					
					<thead>
						<tr>
							<th>번호</th>
							<th>예약번호/예약일자</th>
							<th>강좌명/강좌일자</th>
							<th>장소</th>
							<th>상태</th>
							<th>취소일자</th>
						</tr>
					</thead>
					
					<tbody>
					
					<c:if test="${fn:length(rsvnlist) == 0}">
						<tr>
							<td colspan="6" class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:forEach items="${rsvnlist}" var="resultVO" varStatus="status">
					
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td class="numdt left">
								<a href='#' onclick="fn_get_modTable('${resultVO.rsvnIdx}'); return false;"><c:out value="${resultVO.rsvnNo}" /></a><br/>
								<c:out value="${resultVO.inputDate}"/>
							</td>							
							<td class="pro">
								<div class="pro_tt">
									
									<a href='#' onclick="fn_get_modTable('${resultVO.rsvnIdx}'); return false;"><c:out value="${resultVO.mathName}"/></a>
										<fmt:parseDate var="lecSdate" value="${resultVO.lecSdate}" pattern="yyyyMMdd"/>
										<fmt:formatDate value="${lecSdate}" pattern="yyyy.MM.dd"/>&nbsp;
																									
										<fmt:parseDate var="lecStime" value="${resultVO.lecStime}" pattern="HHmm"/>
										<fmt:formatDate value="${lecStime}" pattern="HH:mm"/>
										~
										<fmt:parseDate var="lecEtime" value="${resultVO.lecEtime}" pattern="HHmm"/>
										<fmt:formatDate value="${lecEtime}" pattern="HH:mm"/>
									
								</div>
							</td>
							<td>
								<c:out value="${resultVO.lecPlace}"/>
							</td>
							<td>
					
								<c:choose>
									<c:when test="${resultVO.mathInterStatus eq 'ACCT'}">
									
										<c:if test="${resultVO.apptype eq '20'}">
											<button type="button" class="btn_ty_s2_c2" onclick="javascript:fn_waiting_cancel('MATH' , '<c:out value="${resultVO.rsvnIdx}"/>')">신청취소</button>	
										</c:if>
										
										<c:if test="${resultVO.apptype ne '20'}">
											<c:out value="${resultVO.mathApptypeNm}"/>
										</c:if>
										
									</c:when>
									
									<c:when test="${resultVO.mathInterStatus eq 'ING'}">
									
										<c:if test="${resultVO.apptype eq '20'}">
											<button type="button" class="btn_ty_s2_c2" onclick="javascript:fn_waiting_cancel('MATH' , '<c:out value="${resultVO.rsvnIdx}"/>')">신청취소</button>	
										</c:if>
										
										<c:if test="${resultVO.apptype ne '20'}">
											<c:out value="${resultVO.mathApptypeNm}"/>
										</c:if>
										
									</c:when>									
									
									<c:otherwise>
										<c:out value="${resultVO.mathApptypeNm}"/>
									</c:otherwise>
								</c:choose>							
		
							</td>
							<td class="expay red">
								<fmt:parseDate var="mathCanceldate" value="${resultVO.mathCanceldate}" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${mathCanceldate}" pattern="yyyy.MM.dd"/>													
							</td>
							
						</tr>
						
					</c:forEach>
				
					</tbody>
				</table>	
				
			</div>
			
		</form:form>
		
	</c:if>
	
</c:if>

<c:if test="${subGubun eq 'lec'}">

	<c:if test="${fn:length(list) == 0}">
		
		<div class="mypage_wrap">
			<div class="signup3">
				<ul>
					<li>매스휴먼북 강사 전용 메뉴입니다.</li>
				</ul>
			</div>
		</div>
		<ul class="btn_area">
			<li><a href="/web/autocontents/contents/main/65" class="btn_ty_m_c1">강사등록</a></li>
		</ul>
		
	</c:if>

	<c:if test="${fn:length(list) != 0}">
	
		<font color='red'>※ 심사중/미승인 강좌만 내용을 수정하실 수 있습니다.</font>

		<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="./myRsvnPay" method="post">
		
			<div class="m_table m_table2 ty mb20">
			
				<table>
					<caption>결제 대기 목록</caption>
					<colgroup>
						<col style="width:5%">
						<col style="width:*">
						<col style="width:9%">
						<col style="width:9%">
						<col style="width:9%">
						<col style="width:9%">
						<col style="width:9%">
						<col style="width:9%">
					</colgroup>					
					<thead>
						<tr>
							<th>번호</th>
							<th>강좌명</th>
							<th>신청일자</th>
							<th>강사심사</th>
							<th>강사심사사유</th>
							<th>강좌심사</th>
							<th>강좌심사사유</th>
							<th>강좌장소</th>
						</tr>
					</thead>
					
					<tbody>
					
					<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="8" class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
					</c:if>
					
					<c:forEach items="${list}" var="resultVO" varStatus="status">
					
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td class="pro">
								<div class="pro_tt">
									<ul>
										<li><a href='/web/mathhubook/lectureUpd?lecSeq=${resultVO.lecSeq}'><c:out value="${resultVO.lecSubject}"/></a></li>
									</ul>
								</div>
							</td>							
							<td>
								<fmt:parseDate var="extYmd" value="${resultVO.regdate}" pattern="yyyy-MM-dd"/>
								<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
							</td>						
							<td class="stat using">
								<c:out value="${resultVO.lecrStatusNm}"/>
							</td>
							<td>
								<c:out value="${resultVO.lecrNocause}"/>
							</td>							
							<td>
								<c:out value="${resultVO.lecStatusNm}"/>
							</td>
							<td>
								<c:out value="${resultVO.lecNocause}"/>
							</td>							
							<td>
								<c:out value="${resultVO.lecPlace}"/>
							</td>
						</tr>
						
					</c:forEach>
				
					</tbody>
				</table>	
			</div>
			
		</form:form>
		
	</c:if>
	
</c:if>



<form:form commandName="rsvnMasterVO" name="rsvnMasterVO" id="popFrm" action="./WEB/mathPop" target="rsvnPop" method="post">
	<input type="hidden" name="rsvnIdx" id="rsvId"/>
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



<script>

	function fn_get_modTable(val) {
		
		console.log("call fn_get_modTable()");
		console.log(val);
		
		var idx = val
		var url = './detailAjax'
		
		$.ajax({
			url : url,
			data : {
				rsvnIdx : idx , gubun : 'MATH'
			},
			method : 'GET',
			dataType : 'html',
			error : function(request, error, jqXHR) {
				alert("불러오기 실패");
			},
			success : function(data, textStatus, jqXHR) {
				$('#modal_table').html(data);
				$('#modal1').modal();
			}
		});

	}

	function fn_pop_rsvnCerti(idx) {
		
		$('#rsvId').val(idx);
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');
		$('#popFrm').submit();
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
