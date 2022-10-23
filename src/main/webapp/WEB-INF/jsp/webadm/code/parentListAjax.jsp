<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  parentListExcel.jsp
  * @Description : 공통코드그룹목록 엑셀 저장 용JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27  이윤호            최초 생성
  *
  *  @author 이윤호
  *  @since 2021.11.10
  *  @version 1.0
  *  @see
  *
  */
%>

	               <table id="excelTable" class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 9%;">
						<col style="width: 30%;">
						<col style="width: *;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th class="text-center">번호</th><!-- 번호 -->
							<th class=" board_th_link">그룹코드</th><!-- 코드ID -->
							<th class="">그룹명</th><!-- 코드ID -->
							<th class="text-center">기관별 코드관리</th>
							<th class="text-center">수정가능</th>
							<th class="text-center">삭제가능</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="6"  class="text-center">데이타가 없습니다</td>
					</tr>
</c:if>

<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	                    <tr>
							<td  class="text-center"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y' && commandMap.selectedMenu.updYn eq 'Y' }">
										<a href="javascript:" onclick="fn_data_load('<c:out value="${resultInfo.grpCd}"/>' )" data-toggle="modal" data-target="#modal-default"><c:out value='${fn:substring(resultInfo.grpCd, 0, 40)}'/></a>
	                         		</c:when>
	                         		<c:otherwise>
										<c:out value='${fn:substring(resultInfo.grpCd, 0, 40)}'/>
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y' && commandMap.selectedMenu.updYn eq 'Y' }">
										<a href="javascript:" onclick="fn_data_load('<c:out value="${resultInfo.grpCd}"/>' )" data-toggle="modal" data-target="#modal-default"><c:out value='${fn:substring(resultInfo.grpNm, 0, 40)}'/></a>
	                         		</c:when>
	                         		<c:otherwise>
										<c:out value='${fn:substring(resultInfo.grpCd, 0, 40)}'/>
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.orgGrpcdyn eq 'Y'}">
										기관별관리
	                         		</c:when>
	                         		<c:otherwise>
										공통관리
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y'}">
										O
	                         		</c:when>
	                         		<c:otherwise>
										X
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.delYn eq 'Y'}">
										O
	                         		</c:when>
	                         		<c:otherwise>
										X
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
						</tr>
</c:forEach>
	                  </tbody>
	                </table>
