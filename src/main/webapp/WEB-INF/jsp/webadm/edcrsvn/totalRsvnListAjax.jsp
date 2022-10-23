<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		$(document).ready(function(){
			$("#chkAll").click(function() {
				$(":checkbox").prop("checked", $(this).prop("checked") ? true : false);
			});
			$("tbody").find(":checkbox").click(function() {
				$("#chkAll").prop("checked", false);
			});

			var sort_column = $('#searchVO #searchOrder').val();
			var sort_order = $('#searchVO #searchOrderDir').val();

			$('#searchVO select[name=recordCountPerPage] ,#searchVO select[name=edcStat]').change(function(){
				$.ajax({
				    url : './totalRsvnListAjax',
				    data : $('#searchVO').serialize(),
					dataType : 'html',
					success: function(data) {
				    	$("#divSubCard").html(data);
					}
			    });
			});

			//상단 타이틀
			$.each( $('#dataTable2').find("thead tr th") , function(){
				if($(this).data("column")){
					var text = $('input[name=selectItem][value='+$(this).data("column")+']').eq(0).parent().text();

					if(text.indexOf("(") > 1){
						//정렬이 있는 경우
						text = text.substring(0,text.indexOf("(")) + " <span id=\""+ $(this).data("column") +"\" class=\"sort\">▼</span>";
					}
					$(this).html(text);
				}

			});

			//showItem 디폴트 체크
			var itemList = $('#showColumnList').val().split("|");
			$('#showItem').html("<li><label><input type=\"checkbox\" checked disabled/> 예약번호 (필수)</li>");

			$.each( itemList , function(index,item){
				var obj = $('input[name=selectItem][value='+item+']').eq(0);

				var html = "<li>";
				html += "<label><input type=\"checkbox\" name=\"setItem\" value=\""+ obj.val() +"\"/> "+ obj.parent().text()+"</label>";
				html += "</li>";
				$('#showItem').append(html);

				obj.parent().hide();

			});

			$('#showItem').sortable({
				items: "li",
				placeholder: "ui-state-highlight"
			});

			//정렬 클릭
			$('#dataTable2 .sort').click(function(){
				var id = $(this).attr("id");
				if(id == sort_column){
					if(sort_order == "" || sort_order == "desc"){
						sort_order = "asc";
					}else{
						sort_order = "desc";
					}
				}else{
					sort_order = "asc";
				}
				$('#searchVO #searchOrder').val(id);
				$('#searchVO #searchOrderDir').val(sort_order);
				fn_load_student_list(1);

			});

			if(sort_column == "") sort_column = "edcRsvnNo";
			if(sort_order == "") sort_order = "desc";
			$.each($('#dataTable2 .sort') , function(){
				if($(this).attr("id") == sort_column){
					$(this).addClass("active");
					if(sort_order == "asc"){
						$(this).text("▲");
					}
				}
			});

		});

		function fn_complete_change(){
			if ($(".sms-target-table :checkbox").filter(":checked").length < 1) {
				alert("수료처리할 대상을 선택해 주세요.");
				return;
			}

			var cnt = $(".sms-target-table :checkbox").filter(":checked").length;

			if(confirm("선택한 "+cnt +"건의 수강등록 회원 현황을 수료 처리 하시겠습니까?")){
				$.ajax({
				    url : './studentMemCompUpdate.json',
				    data : $('#listForm').serialize(),
					dataType : 'json',
					type : 'POST',
					success: function(data) {
						alert(data.result.msg);

						if (data.result.code == "ERROR") {

						} else {
							fn_load_student_list(${commandMap.pagingInfo.currentPageNo});
						}
					}
			    });
			}
		}
	</script>



<form id="listForm" name="listForm" >
			<div class="card-body table-responsive p-0">

				<table class="table table-bordered table-hover text-nowrap sms-target-table" style="text-align:center" id="dataTable2${exceldown}">
		            <thead>
		                <tr>
		                   	<th><input type="checkbox" name="all" id="chkAll"/></th>
		                   	<th>예약번호 <span id="edcRsvnNo" class="sort">▼</span></th>
							<c:if test="${empty exceldown}">
								<c:forEach items="${fn:split(showColumnList,'|')}" var="item" varStatus="status">
			                   		<th data-column="${item }">${item }</th>
								</c:forEach>
							</c:if>
							<c:if test="${not empty exceldown}">
								<c:forEach items="${thList}" var="item" varStatus="status">
			                   		<th>${item}</th>
								</c:forEach>
							</c:if>
							<th></th>
		               </tr>
		            </thead>
		            <tbody>
<c:if test="${fn:length(newList) == 0}">
		                 <tr>
		                   	<td colspan="16" align="center">
		                   		<c:choose>
		                   			<c:when test="${empty searchVO.searchOrgNo }">기관을 먼저 선택해 주세요.</c:when>
		                   			<c:otherwise>데이타가 없습니다</c:otherwise>
		                   		</c:choose>
		                   	</td>
		                 </tr>
</c:if>
<c:forEach items="${newList}" var="item" varStatus="status">
						 <tr <c:if test="${fn:contains(item.edcStatNm, '취소')}">style="color:red"</c:if>>
						 	<c:if test="${status.index eq 0}"><input type="hidden" id="orgTel" value="${item.orgTel}"/></c:if>
		                   	<td><input type="checkbox" name="rsvnReqid" data-memno="${item.edcMemNo}" data-memnm="${item.edcRsvnCustnm}" data-memhp="<hisco:HpPrint hp="${item.edcRsvnMoblphon}"/>" data-memsmsyn="${item.smsYn}" value="${item.edcRsvnReqid}"/></td>
		                   	<td>
		                   		<c:if test="${empty exceldown}"><a href="./edcRsvnInfoDetail?edcRsvnNo=${item.edcRsvnNo}"></c:if>
		                   		${item.edcRsvnNo}
		                   		<c:if test="${empty exceldown}"></a></c:if>
		                   	</td>

<c:forEach items="${fn:split(showColumnList,'|')}" var="item2" varStatus="status2">
			               <td>
				               <c:choose>
				               	<c:when test="${item2 eq 'edcProgmCost' or item2 eq 'edcDcamt' or item2 eq 'cancelAmt' }">
				               		<fmt:formatNumber value='${item[item2]}'/>
				               	</c:when>
				               	<c:when test="${item2 eq 'payAmt' }">
									<c:choose>
										<c:when test="${item.edcStat eq '3004'}"><fmt:formatNumber value='${item.payAmt}'/></c:when>
										<c:when test="${item.payAmt > 0}"><fmt:formatNumber value='${item.payAmt}'/></c:when>
										<c:otherwise>
											<c:if test="${item.edcStat eq '2001'}">
												<button type="button" class="btn btn-success btn-xs" style="margin-top:-18px;margin-bottom:-15px;" onclick="fn_pay_in_cash(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">입금확인</button>
											</c:if>
										</c:otherwise>
									</c:choose>
				               	</c:when>
				               	<c:otherwise>
				               		${item[item2]}
				               	</c:otherwise>
				               </c:choose>
			               </td>
</c:forEach>

							<td style="min-width:100px">
								<c:if test="${fn:substring(item.edcStat, 0, 1) ne '3'}">
									<c:choose>
										<c:when test="${item.edcStat eq '4001'}">
											<c:if test="${item.payAmount > 0}">
												<button type="button" class="btn btn-danger btn-xs"  style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;" onclick="fn_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}', '${item.edcStat}', '${item.payCancelYn}', '${item.tid}', '${item.editYn}')">
													환불취소
												</button>
											</c:if>
											<c:if test="${item.payAmount == 0}">
												<button type="button" class="btn btn-danger btn-xs"  style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;" onclick="fn_all_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">
													신청취소
												</button>
											</c:if>
										</c:when>
										<c:otherwise><button type="button" class="btn btn-danger btn-xs"  style="margin-top:-18px;margin-bottom:-15px;" onclick="fn_all_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">신청취소</button></c:otherwise>
									</c:choose>
								</c:if>
							</td>
		                 </tr>
</c:forEach>
		            </tbody>
		        </table>

			</div>
</form>
			<div class="card-footer p-0">
				<div class="pagination_2">
					<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="javascript:fn_load_student_list"/>
				</div>
			</div>