<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="/web/resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">마이페이지</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap align-center">
            <h3 class="pageTit-title">결제완료</h3>
          </div>
        </div>
      </div>
      <div class="sub">
        <div class="inform">
          <div class="inform-inner">
            <div class="inform-wrap">
              <figure class="inform-figure">
                <img src="/web/resources/images/sub/ico_basice_01.png" alt="아이콘">
              </figure>
              <div class="inform-body">
              
<c:choose>
	<c:when test="${rsvnInfo.edcStat eq '1000' }">
		<h3 class="inform-tit">수강 신청<span>을 대기접수하였습니다.</span></h3>		
		<h3 class="inform-tit"><span>정원결원 배정 시, 알림 또는 MY페이지에서 확인 가능합니다.</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '1002' }">
		<h3 class="inform-tit">수강 신청<span>을 접수하였습니다.</span></h3>		
		<h3 class="inform-tit"><span>추첨당첨 시,  알림 또는 MY페이지에서 확인 가능합니다.</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '2001' }">
		<h3 class="inform-tit">수강 신청<span>을 접수 하였습니다.</span></h3>		
		<h3 class="inform-tit">
			<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "yyyy"  />년
			<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "MM"  />월
			<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "dd"  />일
			<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "HH"  />시
			<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "mm"  />분까지
			<span>분까지 미입금 시, [자동취소] 됩니다.</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '3001' or rsvnInfo.edcStat eq '3004' }">
		<h3 class="inform-tit">수강 신청<span>을 취소하셨습니다.</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '3002' }">
		<h3 class="inform-tit"><span>신청하신 교육프로그램에</span>강좌 신청불가(낙점처리) <span>되었습니다</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '3003' }">
		<h3 class="inform-tit">결제기한이 만료<span>되어 자동취소 되었습니다.</span></h3>		
	</c:when>
	<c:when test="${rsvnInfo.edcStat eq '4001' }">
		<h3 class="inform-tit">수강 등록<span>이 완료 되었습니다.</span></h3>
	</c:when>
</c:choose>
              
              
                
              </div>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">강좌정보</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">예약번호</th>
                    <td>${rsvnInfo.edcRsvnNo}</td>
                    <th scope="row">주관기관</th>
                    <td>${rsvnInfo.orgNm}</td>
                  </tr>                
                  <tr>
                    <th scope="row">강좌명</th>
                    <td colspan="3">${rsvnInfo.edcPrgmnm}</td>
                  </tr>
                  <tr>
                    <th scope="row">강사명</th>
                    <td>${rsvnInfo.instrctrName}</td>
                    <th scope="row">교육장소</th>
                    <td>${rsvnInfo.edcPlacenm}</td>
                  </tr>
                  <tr>
                    <th scope="row">교육기간</th>
                    <td>
                      <strong><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy.MM.dd"/>
                      </strong>
                    </td>
                    <th scope="row">교육시간</th>
                    <td>
                      <strong>${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/></strong>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">교육비</th>
                    <td>
                      <strong>50,000원</strong>
                    </td>
                    <th scope="row">문의전화</th>
                    <td>
                      <strong>${empty detailVO.edcGuideTelno?'-':detailVO.edcGuideTelno}</strong>
                    </td>
                  </tr>
                  
                  <!-- 추가정보 -->
<c:if test="${rsvnInfo.edcStat eq '1000'}">
                  <tr>
                    <th scope="row">대기순번</th>
                    <td colspan="3">${rsvnInfo.waitNo } 순위 대기</td>
                  </tr>
</c:if>
                  
<c:if test="${rsvnInfo.edcRsvnRectype eq '2001'}">
                  <tr>
                    <th scope="row">추첨발표일</th>
                    <td colspan="3">
						<fmt:parseDate var="drwtDate" value="${resultVO.drwtNtcedate}" pattern="yyyyMMdd"/>
						<fmt:formatDate value="${drwtDate}" pattern="yyyy.MM.dd"/>                    
					</td>
                  </tr>
</c:if>                  
                  
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">신청자정보</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름</th>
                    <td>${rsvnInfo.edcRsvnCustnm}</td>
                    <th scope="row">생년월일</th>
                    <td>${rsvnInfo.edcRsvnBirthdate}</td>
                  </tr>
                  <tr>
                    <th scope="row">휴대폰번호</th>
                    <td colspan="3">
                    	${rsvnInfo.edcRsvnMoblphon}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap multiple">
              <h3 class="myTable-title">할인/할증내역</h3>
              <table class="align-center">
                <colgroup>
                  <col style="width: 15%;">
                  <col style="width: 20%;">
                  <col style="width: 15%;">
                  <col style="width: 15%;">
                  <col style="width: 15%;">
                  <col style="width: 20%;">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="row">센터</th>
                    <th scope="row">강좌</th>
                    <th scope="row">수강자</th>
                    <th scope="row">할인/할증사유</th>
                    <th scope="row">할인/할증율</th>
                    <th scope="row">할이/할증 금액(원)</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td colspan="6">할인/할증 내역이 없습니다.</td>
                  </tr>
<!--  
                  <tr>
                    <td colspan="6">
                      ※ 장애인, 기초생활수급자, 국가유공자할인 대상자 분들은 <p>아래 할인 내용 확인하기 (행정감면서비스)</strong>를 이용하여 즉시 할인 받으실 수 있습니다.
                    </td>
                  </tr>
                 
                  <tr>
                    <td>별내동주민자치센터</td>
                    <td>(단기_온라인)커피이론전문가(자격증)-야간)</td>
                    <td>김별내</td>
                    <td>국가유공자</td>
                    <td>50%</td>
                    <td>50,000</td>
                  </tr>
                  <tr class="total">
                    <th colspan="5">합계</th>
                    <th>50,000</th>
                  </tr>
 -->                  
                </tbody>
              </table>
            </div>
            <div class="text-end" style="display:none;">
              <!-- 조회후  green 클래스 추가 -->
              <button class="btn exemption-btn black" data-bs-toggle="modal" data-bs-target="#exemption-service">할인내용  확인하기(행정감면서비스)</button>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap multiple">
<!-- 무통장 입금 -->              
<c:if test="${rsvnInfo.edcStat eq '2001' and rsvnInfo.edcProgmCost > 0 and (orgOptinfoVO.webPaymentMethod eq '0' or rsvnInfo.vbankSeq >0) }">
              <h3 class="myTable-title">무통장 입금 정보</h3>
             
              <table class="align-center">
                <thead>
                  <tr>
                    <th scope="row">입금 하실 계좌</th>
                    <th scope="row">입금 하실 금액</th>
                    <th scope="row">입금 하실 계좌</th>
                    <th scope="row">입금 하실 금액</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td> 
                    	${orgOptinfoVO.orgPayBanknm } ${orgOptinfoVO.orgPayAccno } (${orgOptinfoVO.orgPayAccname}) 
                    </td>
                    <td>
                    	<fmt:formatNumber value="${rsvnInfo.edcProgmCost}" pattern="#,###"/>원
                    </td>
                    <td>
                    	${rsvnInfo.vbankName}은행 / ${rsvnInfo.vbankAccountNo} <%-- (${rsvnInfo.vbankPname})는 가상계좌신청시 입력한 회원이름 --%>
					</td>
                    <td>
						<fmt:formatNumber value="${rsvnInfo.vbankAmount}" pattern="#,###"/>원
					</td>
                  </tr>
                </tbody>
              </table>
</c:if>

<!-- 
${rsvnInfo }
${paySummary }
 -->
<!-- 신용카드 입금 -->
<c:if test="${!empty paySummary }">
              <h3 class="myTable-title">결제정보</h3>
             
              <table class="align-center">
                <thead>
                  <tr>
                    <th scope="row">결제상태</th>
                    <th scope="row">승인일시</th>
                    <th scope="row">승인번호</th>
                    <th scope="row">지불수단</th>
                    <th scope="row">구분</th>
                    <th scope="row">결제금액(원)</th>
                    <th scope="row">영수증</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td> ${rsvnInfo.edcStatNm} </td>
                    <td><hisco:DateUtil datestr="${paySummary.payDate}${paySummary.payTime}" format="yyyy.MM.dd HH:mm"/>
                    </td>
                    <td>${paySummary.appNo }</td>
                    <td>
						<c:if test="${rsvnInfo.edcProgmCost > 0}">
							${paySummary.payMethodNm} (${paySummary.financeNm})
						</c:if>
					</td>
                    <td> ${paySummary.payMethodNm} </td>
                    <td>
						<c:if test="${rsvnInfo.edcProgmCost > 0}">
							<fmt:formatNumber value="${paySummary.payAmt}" pattern="#,###"/>원
						</c:if>  
						<c:if test="${rsvnInfo.edcProgmCost == 0}">
							무료
						</c:if>  						
					</td>
                    <td>
                    <c:if test="${paySummary.cardNo != null}">
                     	${paySummary.receiptNo }<br/>
                        <button type="button" class="badge black">영수증출력</button>
                    </c:if>
                    </td>
                  </tr>
                </tbody>
              </table>

</c:if>              
 
            </div>
          </div>
        </div>
        <div class="badge-btn">
	        <a href="<c:url value='/web/mypage/myRsvn/myRsvnList'/>${queryString}" class="black">${mypage eq 'Y'?'목록' : '마이페이지 목록이동'}</a>
			<c:if test="${rsvnInfo.edcStat eq '2001' and orgOptinfoVO.webPaymentMethod ne '0' and fn:length(orgOptinfoVO.webPaymentMethod)>0 and empty rsvnInfo.vbankStatus}">
				<a href="<c:url value='/web/edc/rsvn/pay'/>/${rsvnInfo.edcRsvnNo}" class="gray">결제 하기</a>
			</c:if>
        </div>
      </div>
  </div>
  </main>
