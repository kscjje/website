<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : detailAjax.jsp
  * @Description : 예약목록 상세모달
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



	<div class="modal-body scroll2" tabindex="0" style="height: 733px;">
		<div class="modal-txt">
			
			<!-- table -->
			<div class="table2 responsive-type mb50"> 
			
				<table>
					<caption>예약 정보 확인 표입니다.</caption>
					<colgroup>
						<col style="width:20%">
						<col style="width:80%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="vtop">예약 번호</th>
							<td class="b">
								<c:out value="${resultVO.rsvnNo}"/>
							</td>
						</tr>					
						<tr>
							<th scope="row" class="vtop">강좌명</th>
							<td class="b">
								<c:out value="${resultVO.mathName}"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">강좌 일자</th>
							<td class="b">
							
								<fmt:parseDate var="lecSdate" value="${resultVO.lecSdate}" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${lecSdate}" pattern="yyyy.MM.dd"/>
								/															
								<fmt:parseDate var="lecStime" value="${resultVO.lecStime}" pattern="HHmm"/>
								<fmt:formatDate value="${lecStime}" pattern="HH:mm"/>
								~
								<fmt:parseDate var="lecEtime" value="${resultVO.lecEtime}" pattern="HHmm"/>
								<fmt:formatDate value="${lecEtime}" pattern="HH:mm"/>							

							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">장소</th>
							<td class="b">
								<c:out value="${resultVO.lecPlace}"/>
							</td>
						</tr>						
						<tr>
							<th scope="row" class="vtop">예약자 이름</th>
							<td class="b">
								<c:out value="${resultVO.memNm}"/>
							</td>
						</tr>						
						<tr>
							<th scope="row" class="vtop">휴대전화번호</th>
							<td class="b">
								<tags:HpDecrypt param="${resultVO.hp}"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">이메일</th>
							<td class="b">
								<tags:EmailDecrypt param="${resultVO.email}"/>
							</td>
						</tr>						
						
					</tbody>
				</table>
			</div>
			<!-- //table -->
		</div>
		<!-- modal-txt -->

		<ul class="btn_area">
			<li><a href="#" class="btn_ty_m3_c1" data-dismiss="modal">확인</a></li>
		</ul>
		
	</div>
