<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  userTabDiscAjax.jsp
  * @Description : 회원감면정보 이력 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.11.17  진수진            최초 생성
  *
  *  @author 진수진
  *  @since 2021.11.17
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script>
	function fn_load_dc_delete(orgNo , seq){
		if(confirm("할인 사유를 삭제하시겠습니까?")){
			$.ajax({
			      url: './userTabDcDelete.json',
			      type : "POST",
			      dataType : "json",
			      data : {'memNo': $('#memNo').val(), 'orgNo':orgNo ,'discountSeq':seq},
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_load_dclist();
				        	 alert(data.result.msg);
				        }
			      }
			});
		}
	}
</script>

	<table class="table table-hover table-sm">
        <thead>
          <tr>
            <th style="width: 70px">번호</th>
            <th>기관명</th>
            <th>할인종류</th>
            <th>할인율(%)</th>
            <th>유효기간적용</th>
            <th>유효시작일</th>
            <th>유효종료일</th>
            <th>수정/삭제</th>
          </tr>
        </thead>
        <tbody>
<c:if test="${empty list }">
			<tr>
				<td colspan="8">등록된 데이타가 없습니다.</td>
			</tr>
</c:if>
<c:forEach items="${list}" var="item" varStatus="status">
		<tr>
			<td><c:out value="${status.index+1}"/></td>
			<td>${item.orgNm }</td>
			<td>${item.dcNm }</td>
			<td>${item.dcRate }</td>
			<td><c:choose><c:when test="${item.dcconfirmYn eq 'Y' }">적용</c:when><c:otherwise>미적용</c:otherwise></c:choose></td>
			<td><c:if test="${item.dcconfirmYn eq 'Y' }"><hisco:DateUtil datestr="${item.dcconfSdate}" format="yyyy-MM-dd"/></c:if></td>
			<td><c:if test="${item.dcconfirmYn eq 'Y' }"><hisco:DateUtil datestr="${item.dcconfEdate}" format="yyyy-MM-dd"/></c:if></td>
			<td>
				<c:choose>
					<c:when test="${commandMap.selectedMenu.updYn eq 'Y'}">
						<button type="button" onclick="fn_load_dc_detail('${item.orgNo}','${item.discountSeq}')" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modal-default">수정</button>
						&nbsp;
						<button type="button" onclick="fn_load_dc_delete('${item.orgNo}','${item.discountSeq}')" class="btn btn-secondary btn-xs">삭제</button>
					</c:when>
				</c:choose>
			</td>
		</tr>
</c:forEach>
        </tbody>
      </table>


</body>