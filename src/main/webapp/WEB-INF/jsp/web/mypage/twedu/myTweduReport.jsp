<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduReport.jsp
  * @Description :마을배움터 보고서
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.12.21   이기태          최초 생성
  *
  *  @author 이기태
  *  @since 2021.12.21
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script type="text/javascript" src="<c:url value='/web/resources/js/twedu.js'/>"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	//fn_loadReport(jQuery("#edcPrgmid").val());

	$('.file-preview-link').click(function(){
		if($(this).data('imgpath')) {
			jQuery("#img-preview").attr("src", $(this).data('imgpath'));
			jQuery("#modal1").popup("show");
		} else {
			alert("미리보기 이미지가 없습니다.");
		}
	});
});
</script>
</head>
<body>
	<div class="sub_visual mypage no-print">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>
	<div id="content" class="sub_content">
		<div class="sub_top type04 no-print">
			<a href="javascript:history.back()">뒤로가기 버튼</a>
			<h3>마을배움터 보고서</h3>
			<div class="right_box">
				<button type="button" onclick="print()" class="btn_s1_c1">인쇄</button>
			</div>
		</div>
		<!--//top -->

		<div class="report">
			<h4 class="report-title"><c:out value="${data.edcPrgmnm}"/></h4>
			<div class="table t_flex data-bind-header">
				<table>
					<caption>마을배움터 보고서 표입니다. -</caption>
					<colgroup>
						<col width="13.3333%">
						<col width="20%">
						<col width="13.3333%">
						<col width="20%">
						<col width="13.3333%">
						<col width="20%">
					</colgroup>
					<tbody>
						<tr>
							<th>보고일</th>
							<td>
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
							</td>
							<th>보고시점 실행 회차</th>
							<td><span class="data-binder" data-id="edcClcnt">${passCnt}</span>회차</td>
							<th>보고시점 실행시간</th>
							<td>(<span class="data-binder" data-id="edcRtime">${eduMin}</span>분x<span class="data-binder" data-id="edcClcnt">${passCnt}</span>회차) = <span id="tot_edu_time"><fmt:formatNumber value="${passCnt*eduMin }"/></span>분 </td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--// table  -->
			<div class="table t_flex data-bind-header">
				<div class="t_title">01.운영개요</div>
				<table>
					<caption>마을배움터 교육기록 표입니다. -</caption>
					<colgroup>
						<col width="11.3333%">
						<col width="22%">
						<col width="11.3333%">
						<col width="22%">
						<col width="11.3333%">
						<col width="22%">
					</colgroup>
					<tbody>
						<tr>
							<th>선생님</th>
							<td><span class="data-binder" data-id="instrctrName">${data.instrctrName}</span></td>
							<th>시작일</th>
							<td><span class="data-binder" data-id="edcSdate">${data.edcSdate}</span></td>
							<th>종료일</th>
							<td><span class="data-binder" data-id="edcEdate">${data.edcEdate}</span></td>
						</tr>
						<tr>
							<th>교육 회차</th>
							<td><span class="data-binder" data-id="edcClcnt">${data.edcClcnt}</span>회</td>
							<th>교육참여자</th>
							<td><span class="data-binder" data-id="stdCnt">${fn:length(studentList) }</span>명</td>
							<th>교육대상</th>
							<td><span class="data-binder" data-id="edcTargetAge">${data.targetName}</span> </td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--// table  -->
			<div class="table">
				<div class="t_title">02.운영내용 : 출석부</div>
				<table class="data-bind-attendList" data-list-id="attendList">
					<caption>출석부 표입니다. -번호, 성명, 출석일, 결석일, 출석률, 교육상태, </caption>
					<colgroup>
						<col width="6%">
						<col width="13%">
						<col width="15.5%">
						<col width="20%">
						<col width="13%">
						<col width="13%">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th class="data-binder" data-id="memNm">성명</th>
							<th class="data-binder" data-id="passCnt">출석일</th>
							<th class="data-binder" data-id="nopassCnt">결석일</th>
							<th class="data-binder" data-id="passRate" data-suffix="%">출석률</th>
							<th class="data-binder" data-id="edcComplstat">교육상태</th>
						</tr>
					</thead>
					<tbody>
<c:forEach items="${studentList}" var="item" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${item.memNm }</td>
								<td>${item.attendCnt }</td>
								<td>${passCnt-item.attendCnt }</td>
								<td><fmt:formatNumber type="percent" value="${item.attendCnt/passCnt}"  pattern="0%"/></td>
								<td>${item.edcStat }</td>
							</tr>
</c:forEach>

					</tbody>
				</table>
			</div>
			<!--// table  -->

			<div class="table">
				<div class="t_title">03.운영내용 : 학습기록</div>
				<table class="data-bind-logList" data-list-id="logList">
					<caption>학습기록 표입니다. -회차, 날짜/시간, 출석/정원, 주제, 학습내용 일정/기록, 사진 </caption>
					<colgroup>
						<col width="6%">
						<col width="11.5%">
						<col width="8.5%">
						<col width="13%">
						<col width="47%">
						<col width="14%">
					</colgroup>
					<thead>
						<tr>
							<th>회차</th>
							<th>날짜</th>
							<th>출석/정원</th>
							<th>주제</th>
							<th>학습내용 일정/기록</th>
							<th>사진보기</th>
						</tr>
					</thead>
					<tbody>
<c:forEach items="${logList}" var="item" varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td>
							${item.edcDate}
						</td>
						<td>${item.passCnt}/${item.totalMemCnt}</td>
						<td style="text-align:left">
							<c:out value="${item.edcTitle }"/>
						</td>
						<td  style="text-align:left">
							${fn:replace(item.edcCnts,crlf , '<br/>')}
						</td>
						<td>
							<div class="file_list">
								<span style="cursor:pointer" class="file-preview-link" data-imgpath="<tags:UploadFileUrl filePath="${item.lectImgPath}" fileName="${item.lectImgFilenm }" originName="${item.lectImgOrigin }"/>">
									<em style="border-bottom:0px">${item.lectImgOrigin}</em>
								</span>
							</div>
						</td>
					</tr>
</c:forEach>

					</tbody>
				</table>
			</div>
			<!--// table  -->
			<div class="last_txt data-bind-header">
				<p>위의 같은 [마을배움터]의 운영결과를 보고합니다. </p>
				<p>보고일 : <span class="data-binder" data-id="reportDate"><fmt:formatDate value="${now}" pattern="yyyy.MM.dd" /></span><br>	보고자 : <span class="reportUserNm">${reportUserNm}</span></p>
			</div>
	</div>
	<!--// report  -->
		<div class="btn_area no-print">
			<a href="./lectList" class="btn_s1_c0">목록</a>
		</div>
		<!--// btn_area  -->
	</div>
	<!--// sub_content  -->
	<!-- 팝업 -->
	<div class="modal" id="modal1">
		<div class="modal-dialog big">
			<div class="modal-content">
				<div class="modal-title">
					<h4>학습보고 사진</h4>
					<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div class="modal-body pb_0">
					<img id="img-preview" src="" alt="">
				</div>
				<ul class="btn_area">
					<li><a href="#" class="btn_s1_c0 modal1_close">닫기</a></li>
				</ul>
				<!-- //btn_area -->
			</div>
		</div>
	</div>
</body>