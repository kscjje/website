<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduLectEdulogAjax.jsp
	 * @Description : 마울배움터 교육현황 수업기록 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.23
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script>
	$(document).ready(function(){
		jQuery("#archive-list").on("click", ".file-preview-link", function(e){
			if($(this).data('imgpath')) {
				jQuery("#img-preview").attr("src", $(this).data('imgpath'));
				jQuery("#modal1").modal("show");
			} else {
				alert("미리보기 이미지가 없습니다.");
			}
		});
	});

</script>
</head>
<body>
	<table class="table table-bordered table-hover text-center">
		<colgroup>
					<col width="6%">
					<col width="10%">
					<col width="20%">
					<col width="8%">
					<col width="*">
					<col width="10%">
				</colgroup>
		<thead class="thead-light">
			<tr>
				<th>회차</th>
				<th>날짜</th>
				<th>주제</th>
				<th>출석현황</th>
				<th>학습내용 일정/기록</th>
				<th>이미지 첨부</th>
			</tr>
		</thead>
		<tbody id="archive-list">
<c:forEach items="${logList}" var="item" varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td>
							${item.edcDate}
							<br/>(${item.weekName })
						</td>
						<td style="text-align:left">
							<c:out value="${item.edcTitle }"/>
						</td>
						<td>(${item.passCnt }/${item.totalMemCnt})</td>
						<td style="text-align:left">
							<c:out value="${fn:replace(item.edcCnts,crlf,'<br/>')}"/>
						</td>
						<td>
							<span class="file-preview-link" style="cursor:pointer" data-imgpath="<tags:UploadFileUrl filePath="${item.lectImgPath}" fileName="${item.lectImgFilenm }" originName="${item.lectImgOrigin }"/>">
								<em>${item.lectImgOrigin}</em>
							</span>
						</td>
					</tr>
</c:forEach>
				</tbody>
	</table>

	<div class="modal" id="modal1">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
		              <h4 class="modal-title">교육기록 사진</h4>
		              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">×</span>
		              </button>
	            </div>
				<div class="modal-body pb_0">
					<img id="img-preview" src="" alt="" style="width: 100%;">
				</div>
				<!-- //btn_area -->
			</div>
		</div>
	</div>
</body>