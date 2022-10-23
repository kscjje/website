<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
 /**
  * @Class Name : parentDetailAjax.jsp
  * @Description : 공통코드그룹 상세(ajax)
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27 이윤호              최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.27
  *  @version 1.0
  *  @see
  *
  */
%>
<script>
$('.btn-delete').hide();
$('.btn-save').hide();
$('.btn-save-group').hide();
$('.btn-copy').show();
<c:if test="${fn:length(yearList)<=0}">
	$('.btn-copy').hide();
</c:if>
</script>

<form id="copyForm" class="form-horizontal" >
	<div class="row">
		<p class="text-red">*기준 월력에서 복사할 기간을 선택하세요.</p>
		<table id="modaltable" class="table table-bordered">
			<colgroup>
				<col width="25%">
				<col width="*">
			</colgroup>
			<tr>
				<th>기관명</th>
				<td><c:out value="${orgInfoVO.orgNm }"/> [<c:out value="${orgInfoVO.orgNo }"/>]</td>
			</tr>
			<tr>
				<th>연도(*)</th>
				<td>
			<c:choose>
				<c:when test="${fn:length(yearList)>0}">
					<select id="yyyyCopy" class="form-control">
					<c:forEach items="${yearList}" var="yyyy" varStatus="status">
						<option value="<c:out value="${yyyy }"/>" <c:if test="${yyyy eq today}">selected</c:if> ><c:out value="${yyyy }"/></option>
					</c:forEach>
					</select>
				</c:when>
				<c:otherwise>
					입력된 데이터가 없습니다.
				</c:otherwise>
			</c:choose>

				</td>
			</tr>
		</table>
	</div>
 </form>

