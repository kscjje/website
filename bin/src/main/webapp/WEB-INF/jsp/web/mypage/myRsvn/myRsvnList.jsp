<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function fn_reserve_cancel(edcRsvnReqid, edcRsvnNo) {
		if (confirm("해당강좌의 신청을 취소하시겠습니까?")) {
			 $.post({
		        url  : './rsvnCancel.json',
		        data : {'edcRsvnReqid':edcRsvnReqid, 'edcRsvnNo':edcRsvnNo},
		        success: function(data) {
		        	alert(data.result.msg);
		        	if (data.result.success) {
		        		window.location.reload();
		        	}
		        }, error: function() {
		        	alert("일시적인 문제가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
		        }
		    });
		}
	}

	function printCert(idx){
		$('#edcRsvnReqid').val(idx);
		$('#edcRsvnMstVO').attr("action" , "../myRsvn/edcCompPopup");
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');

		$('#edcRsvnMstVO').submit();

	}

	$(document).ready(function(){
		$('#appStatus').change(function(){
			$('#listForm').submit();
		});
	})
</script>

    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">마이페이지</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">내예약관리</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">수강신청 현황</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">
      
      
    	<%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myRsvnInclude.jsp"%>
		      

        <div class="article-bottom">
          <div class="article-lsit list">
            <table class="list-table table bor-black">
              <colgroup>
                <col style="width: 5%">
                <col style="width: 25%">
                <col style="width: 15%">
                <col style="width: 20%">
                <col style="width: 10%">
                <col style="width: 10%">
                <col style="width: 15%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col" class="number">번호</th>
                  <th scope="col" class="title">강좌명</th>
                  <th scope="col" class="area">센터명</th>
                  <th scope="col" class="data">교육기간</th>
                  <th scope="col" class="fee">수강료</th>
                  <th scope="col" class="reception">접수일자</th>
                  <th scope="col" class="state">접수상태</th>
                </tr>
              </thead>
              <tbody>
              
<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="7" class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
</c:if>              
<c:forEach items="${list}" var="resultVO" varStatus="status">
                <tr>
                  <td class="number"><c:out value="${paginationInfo.totalRecordCount - (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage - status.index}"/></td>
                  <td class="title">
                    <a class="under" href="<c:url value='/web/edc/program/${resultVO.edcPrgmNo}'/>"><c:out value="${resultVO.edcPrgmnm}"/></a>
                  </td>
                  <td class="area">
                    <a class="under" href="<c:url value='/web/edc/program/${resultVO.edcPrgmNo}'/>"><c:out value="${resultVO.orgNm}"/></a>
                  </td>
                  <td class="data"><hisco:DateUtil datestr="${resultVO.edcReqSdate}" format="yy.MM.dd"/>~<hisco:DateUtil datestr="${resultVO.edcReqEdate}" format="yy.MM.dd"/>
                    <br> ${resultVO.edcDaygbnNm} / 18:30 ~ 20:20
                  </td>
                  <td class="fee">
						<c:choose>
							<c:when test="${resultVO.edcProgmCost < 1}">무료</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${resultVO.edcTotamt}" pattern="#,###"/>원
							</c:otherwise>
						</c:choose>                  
				  </td>
                  <td class="reception">
					<fmt:parseDate var="edcReqDate" value="${resultVO.edcReqDate}" pattern="yyyyMMdd"/>
					<fmt:formatDate value="${edcReqDate}" pattern="yyyy.MM.dd"/>
					<fmt:parseDate var="edcReqTime" value="${resultVO.edcReqTime}" pattern="HHmmss"/>
					<fmt:formatDate value="${edcReqTime}" pattern=" HH:mm"/>
				  </td>
                  <td class="state">
					<a href="<c:url value='./myRsvnDetail?edcRsvnReqid=${resultVO.edcRsvnReqid}'/><c:out value="${commandMap.query}"/>" class="flag flag-green" style="margin-top:2px;margin-bottom:2px;">상세보기</a>
					<c:if test="${resultVO.edcStat eq '2001' and !empty resultVO.webPaymentMethod and resultVO.webPaymentMethod ne '0' and resultVO.vbankSeq < 1}">
						<a href="<c:url value='../../edc/rsvn/pay'/>/${resultVO.edcRsvnNo}" class="blue" style="margin-top:2px;margin-bottom:2px;">결제하기</a>
					</c:if>
					<c:if test="${(resultVO.edcStat eq '4001' and resultVO.editYn eq 'Y') or fn:indexOf(resultVO.edcStat,'10')==0 or resultVO.edcStat eq '2001'}">
						<a href="javascript:fn_reserve_cancel(${resultVO.edcRsvnReqid}, '${resultVO.edcRsvnNo}')" class="red" style="margin-top:2px;margin-bottom:2px;">${(!empty resultVO.oid and resultVO.edcTotamt >0)?'결제취소':'신청취소'}</a>
					</c:if>
					<!--                   
                    <p class="flag flag-green">결제대기</p>
                    -->
                  </td>
                </tr>
</c:forEach>              
              
              </tbody>
            </table>
          </div>
        </div>
        <div class="paginate pc">
 				<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./myRsvnList?pageIndex="/>
        </div>

      </div>
    </main>
