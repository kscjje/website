<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : orgInfoDcAjax.jsp
  * @Description : 기관 할인 감면 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script>
	$(document).ready(function(){
		$('.dcReason').click(function(){
			var checked = $(this).prop("checked");
			$(this).parent().parent().find(".dcRate").prop("disabled" , !checked);
		})
	})
</script>
<form:form commandName="orgDcVO" name="orgDcVO" action="./orgInfoRegistSave" method="post" >
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
		                <table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="50%">
			                  				<col width="25%">
			                  				<col width="25%">
			                  			</colgroup>
									<thead>
										<tr>
											<th>사유</th>
											<th>할인/할증</th>
											<th>할인/할증률(%)</th>
										</tr>
									</thead>
									<tbody>
<c:forEach items="${orgDcVO.orgDcList}" var="item" varStatus="status">
							         <tr>
											<td>
												<form:checkbox path="orgDcList[${status.index }].dcYn" value="${item.dcReasonCd }" class="dcReason"/>
												<label for="orgDcList${status.index }.dcYn1" class="form-check-label">&nbsp;<c:out value="${item.dcNm }"/></label>
											</td>
											<td>
												<c:out value="${item.dcType eq '10'?'할인':'할증' }"/>
											</td>
											<td>
												<c:set var="flag" value="${empty item.dcYn?true:false}" />
												<form:input path="orgDcList[${status.index }].dcRate" style="width:70px" disabled="${flag }" class="dcRate"/> %
											</td>
									</tr>
</c:forEach>

		                        	</tbody>
		                        </table>

	               </div>
	                <div>
						<a id="orgInfoBtn" class="btn btn-primary btn-sm form-control <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_dcmng_save()">수강료 감면기준 저장</a>
	                </div>
</form:form>
 </body>