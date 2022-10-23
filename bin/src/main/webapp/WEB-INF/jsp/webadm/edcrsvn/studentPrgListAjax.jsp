<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		$(document).ready(function(){
			var sort_column = $('#prgmVO #searchOrder').val();
			var sort_order = $('#prgmVO #searchOrderDir').val();

			$('#dataTable .sort').click(function(){
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
				$('#prgmVO #searchOrder').val(id);
				$('#prgmVO #searchOrderDir').val(sort_order);
				fn_get_program_list();

			});

			//if(sort_column == "") sort_column = "createdate";
			//if(sort_order == "") sort_order = "desc";
			$.each($('.sort') , function(){
				if($(this).attr("id") == sort_column){
					$(this).addClass("active");
					if(sort_order == "asc"){
						$(this).text("▲");
					}
				}
			});
		});

		function fn_student_book(prgmNo, seq) {
			document.studentBookForm.edcPrgmNo.value = prgmNo;
			document.studentBookForm.edcRsvnsetSeq.value = seq;

		}
	</script>


<div class="card-body table-responsive p-0">
	<table class="table table-bordered table-hover text-nowrap" id="dataTable" style="text-align:center">
		<thead>
			<tr>
				<th>번호</th>
				<th>분야 <span id="cateSort" class="sort">▼</span></th>
				<th>프로그램명 <span id="programNm" class="sort">▼</span></th>
				<th>모집차수</th>
				<th>접수기간</th>
				<th>교육대상</th>
				<th>수업시작일</th>
				<th>수업종료일</th>
				<th>정원</th>
				<th>등록인원</th>
				<th>환불인원</th>
				<th>잔여정원</th>
				<th>대기인원</th>
				<th>출석부</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(programList) == 0}">
			<tr>
				<td colspan="14" align="center">
					<c:choose>
						<c:when test="${empty searchVO.searchOrgNo }">기관을 먼저 선택 하시고 검색해 주세요.</c:when>
						<c:otherwise>
							검색된 데이타가 없습니다.
						</c:otherwise>
					</c:choose>

				</td>
			</tr>
			</c:if>
			<c:forEach items="${programList}" var="item" varStatus="status">
			<tr style="cursor:pointer" data-prgmNo="${item.edcPrgmNo}" data-prgmSet="${item.edcRsvnsetSeq }">
				<td>
					${status.index+1}
				</td>
				<td>${item.ctgNm}</td>
				<td>${item.edcPrgmnm}</td>
				<td>${item.edcRsvnsetNm} (${item.edcRsvnsetSeq})</td>
				<td><hisco:DateUtil datestr="${item.edcRsvnSdate}" format="yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${item.edcRsvnEdate}" format="yyyy-MM-dd"/></td>
				<td>${item.targetName}</td>
				<td><hisco:DateUtil datestr="${item.edcSdate}" format="yyyy-MM-dd"/></td>
				<td><hisco:DateUtil datestr="${item.edcEdate}" format="yyyy-MM-dd"/></td>
				<td>${item.edcPncpa}</td>
				<td><fmt:formatNumber value="${item.statPaydoneCnt}"/></td>
				<td><fmt:formatNumber value="${item.statCancelCnt}"/></td>
				<td><fmt:formatNumber value="${item.statRemainCnt}"/></td>
				<td>
					<c:if test="${item.edcRsvnRectype eq '1002'}"><!-- 선착대기 -->
						<a href="./edcRsvnInfoAssignWaitList?edcPrgmNo=${item.edcPrgmNo}&edcRsvnsetSeq=${item.edcRsvnsetSeq}"><fmt:formatNumber value="${item.statAssignWaitCnt}"/></a>
					</c:if>
				</td>
				<td><button type="button" class="btn  btn-success btn-xs btn-flat" style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;"  data-toggle="modal" data-target="#modal-book" onclick="fn_student_book(${item.edcPrgmNo},${item.edcRsvnsetSeq})">EXCEL</button></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
 </div>
 <div class="card-footer" >
 	검색결과 : ${fn:length(programList)} 건
 </div>