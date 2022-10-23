<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${!empty errorMsg }">${errorMsg }</c:when>
	<c:otherwise>
		<style type="text/css">
			pre { white-space:pre-wrap; word-break:break-all;}
		</style>
		<script src="${requestScope.resourceContext}/js/XE_validate.js"></script>
		
		<div class="modal-dialog big" >
			<div class="modal-content">
				<div class="modal-title">
					<h4>할인감면 선택</h4>
					<a href="javascript:;" onclick="pop_close()" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div style="padding-top:40px;padding-left:40px;text-align:left">
				* 노원구청 해당기관에 감면서류제출이 확정될 경우, 자동으로 감면이 선택 됩니다.<br/>
				* 감면사유 증빙서류가 제출되지 않는 감면은 현장방문시 증빙서류를 제출해야합니다.<br/>
				&nbsp;&nbsp;&nbsp;(자격요건을 허위로 선택하거나 증빙서류 미제출 시, 신청취소 될 수 있습니다)
				</div>
				<div class="modal-body pb_0" style="overflow:auto">
					<div class="table scroll500">
						<table>
							<caption></caption>
							<colgroup>
								<col width="7%">
								<col width="93%">
							</colgroup>
							<thead>
								<tr>
									<th>체크</th>
									<th>자격요건 대상자 <br>할인율/서류증빙</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(dcList) == 0}">
									<tr><td colspan="2">감면설정 정보가 없습니다.</td></tr>
		   							</c:if>
		   							<c:set var="applyYn" value=""/>
		   							<c:forEach items="${dcList}" var="item" varStatus="status">
									<tr>
										<td>
											<div class="chk_box" style="margin-top:-10px">
											<c:choose>
												<c:when test="${item.dcconfirmYn eq 'Y'}">
													<input type="checkbox" id="cc${status.index}" name="chkDc"  <c:if test="${dccd eq item.dcReasonCd}">checked="checked"</c:if> data-dccd="${item.dcReasonCd}" data-dcrate="${item.dcRate}" data-dcnm="${item.dcNm}">
												</c:when>
												<c:otherwise>
													<input type="checkbox" id="cc${status.index}" name="chkDc" <c:if test="${dccd eq item.dcReasonCd}">checked="checked"</c:if> data-dccd="${item.dcReasonCd}" data-dcrate="${item.dcRate}" data-dcnm="${item.dcNm}">
												</c:otherwise>
											</c:choose>
											<label for="cc${status.index}"></label>
											</div>
										</td>
										<td>
											<p class="t_bold">${item.dcNm} </p>
											<span class="red">${item.dcRate}%</span>
											<c:if test="${item.dcconfirmYn eq 'Y'}"><span>등록완료</span></c:if>
										</td>
									</tr>
									<c:if test="${item.dcconfirmYn eq 'Y' and applyYn ne 'Y'}">
		   									<c:set var="applyYn" value="Y"/>
		   								</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<ul class="btn_area">
					<li><a href="javascript:;" onclick="fnDcLimitPopHide()" class="btn_s1_c0" >취소</a></li>
					<li><a href="javascript:;" onclick="fnDcApply('dc')" class="btn_s1_c1">확인</a></li>
				</ul>
				<!-- //btn_area -->
			</div>
		</div>
	</c:otherwise>
</c:choose>

<script>
		$(document).ready(function() {
			$("input[name=chkDc]").click(function() {
				var chkDiscountCd;
				if ($(this).prop("checked")) {
					chkDiscountCd = $(this).data("dccd");
				}

				$("input[name=chkDc]").each(function() {
					if ($(this).data("dccd") != chkDiscountCd) {
						$(this).prop("checked", false);
					}
				});
			})
		});
</script>


