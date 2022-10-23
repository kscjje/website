<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<script type="text/javascript">
		function fnDetailApply() {
			var rsvnRectype = "${detailVO.edcRsvnRectype}"; //예약신청접수방법
			var linkUrl = "${detailVO.edcRsvnLinkurl}"; //타기간접수경료
			var nonMemberYn = "${detailVO.rsvnNonmebyn}"; // 비회원 신청 가능여부
			var userId = "${userInfo.id}";

			if (rsvnRectype == "5001") {//1.타기관링크
				window.open(linkUrl);
				return;
			}

			//2.내부
			var data = new Object();
			data.edcPrgmNo = ${detailVO.edcPrgmNo};
			data.edcRsvnsetSeq = ${detailVO.edcRsvnsetSeq};

			if (nonMemberYn == "Y" && userId == "" ) {
				if(confirm("해당강좌를 비회원으로 신청하시겠습니까?\n(회원가입자 경우, 로그인하여 신청하세요)")){
					location.href = "<c:url value='../rsvn/termsAgree/${detailVO.edcPrgmNo}/${detailVO.edcRsvnsetSeq}'/>";
					//TODO: 일단 약관선택 페이지로 이동
					return;
				}else{
					return;
				}
			} else if ( userId == "") {
				if(confirm("로그인 후 신청하실 수 있습니다.\n로그인 페이지로 이동하시겠습니까?")){
					doLogin();
				}
				return;
			}

			$.ajax({
				url : "<c:url value='../rsvn/termsAgreeAjax/${detailVO.edcPrgmNo}/${detailVO.edcRsvnsetSeq}'/>", //신청 팝업
				data : data,
				dataType : 'html',
				success : function(data) {
					if(data.indexOf("ERR") == 0){
						alert(data.substring(4,data.length));
					}else{
						$('#modal1').html(data);
						//$('#modal1').popup('show');
						$('#modal1').show();
					}
				},
				error : function(error) {
					alert("오류");
					console.log(error);
				}
			});
		}
		
		

		/*날자변환함수*/
		function getDateInfo(data, gubun){
			//console.log('getDateInfo');
			if(gubun == ''){
				gubun = '';
			}
			if(data == null || data == '' || data.length != 8){
				return data;
			}
			else{
				return data.substring(0,4) + gubun + data.substring(4,6) + gubun + data.substring(6,8);
			}
		}
		/*시간변환함수*/
		function getTimeInfo(data){
			if(data == null || data == '' || data.length != 4){
				return data;
			}
			else{
				return data.substring(0,2) + ':' + data.substring(2,4);
			}
		}		
	</script>
	
	<form name="linkForm" id="linkForm" method="get" target="_blank">
	</form>
	
<main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_03.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">교육신청</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">문화강좌</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">교육신청</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">문화강좌</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      
      
      <div class="sub">
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap pt-0">
              <h3 class="myTable-title">
                <p>${detailVO.edcPrgmnm}</p>
                <span class="bedge red">${detailVO.edcStatus}</span>
              </h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="40%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">교육기관</th>
                    <td colspan="3">${detailVO.orgName}</td>
                  </tr>
                  <tr>
                    <th scope="row">접수방법</th>
                    <td colspan="3">${detailVO.edcRsvnAccssrdNm}/${detailVO.edcRsvnRectypeNm}
                      <div class="tooltip">
                        <span>?</span>
                        <div class="tooltip-pop">
                          <span>
                            ·접수 방법은 온라인과 방문 접수, 두 가지 방법이 있으며,
                            수강생 선정은 선착순과 추첨식 방법으로 구분되며,
                            선착순의 경우 접수 기간 동안 우선 신청한 회원이 접수되고
                            정원이 초과할 경우 일정 인원을 대기인원으로 접수하며
                            이미 접수된 인원 중 취소자가 발생하면 대기인원이 보충
                            접수됩니다.
                          </span>

                          <span>
                            ·기타 궁금한 사항은 하단의 담당자 연락처로 문의하여
                            주시기 바랍니다.
                          </span>
                        </div>
                      </div>
                    </td>
                  </tr>
<!--                   
                  <tr>
                    <th scope="row">면제/감면신청</th>
                    <td colspan="3">
                      <div class="radio">
                        <div class="radio-wrap">
                          <span class="input-group">
                            <input name="sub04_02" id="inp04_02_01" type="radio">
                            <label for="inp04_02_01" tabindex="0" data-bs-toggle="modal" data-bs-target="#inp_01">국민 기초생활수급권자</label>
                          </span>
                          <span class="input-group">
                            <input name="sub04_02" id="inp04_02_01" type="radio">
                            <label for="inp04_02_02" tabindex="0" data-bs-toggle="modal" data-bs-target="#inp_02">국가 유공자 가족</label>
                          </span>
                          <span class="input-group">
                            <input name="sub04_02" id="inp04_02_01" type="radio">
                            <label for="inp04_02_03" tabindex="0" data-bs-toggle="modal" data-bs-target="#inp_02">국가 유공자(본인)</label>
                          </span>
                          <span class="input-group">
                            <input name="sub04_02" id="inp04_02_01" type="radio">
                            <label for="inp04_02_04" tabindex="0" data-bs-toggle="modal" data-bs-target="#inp_03">등록 장애인</label>
                          </span>
                          <span class="input-group">
                            <input name="sub04_02" id="inp04_02_01" type="radio">
                            <label for="inp04_02_05" tabindex="0" data-bs-toggle="modal" data-bs-target="#inp_04">다자녀</label>
                            <div class="tooltip ms-3">
                              <span data-bs-toggle="modal" data-bs-target="#exempt">?</span>
                            </div>
                          </span>
                        </div>

                      </div>
                      <button class="btn exemption-btn black" data-bs-toggle="modal" data-bs-target="#exemption-service">할인내용 확인하기(행정감면서비스)</button>
                    </td>
                  </tr>
 -->                  
                  <tr>
                    <th scope="row">모집인원</th>
                    <td>${detailVO.edcPncpa}명</td>
                    <th scope="row">신청/대기인원</th>
                    <td>${detailVO.statPaydoneCnt} 명/ ${detailVO.statAssignWaitCnt} 명</td>
                  </tr>
                  <tr>
                    <th scope="row">신청기간</th>
                    <td>
                    	<hisco:DateUtil datestr="${detailVO.edcRsvnSdate}${detailVO.edcRsvnStime}" format="yy.MM.dd_HH:mm"/> 
	                    <br class="mobile"> ~ <hisco:DateUtil datestr="${detailVO.edcRsvnEdate}${detailVO.edcRsvnEtime}" format="yy.MM.dd_HH:mm"/>
                    </td>
                    <th scope="row">교육기간</th>
                    <td>
	                    <hisco:DateUtil datestr="${detailVO.edcSdate}" format="yy.MM.dd"/> 
	                    <br class="mobile">~ <hisco:DateUtil datestr="${detailVO.edcEdate}" format="yy.MM.dd"/>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">강사명</th>
                    <td colspan="3">${detailVO.instrctrNm }</td>
                  </tr>
                  <tr>
                    <th scope="row">교육장소</th>
                    <td colspan="3">${detailVO.edcPlacenm}</td>
                  </tr>
                  <tr>
                    <th scope="row">교육시간</th>
                    <td colspan="3">
                      <p>${detailVO.edcDaygbnNm } / ${detailVO.edcTime} (${detailVO.edcOdrNm})</p>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">교육비</th>
                    <td>
           
				           <c:choose>
					        	<c:when test="${detailVO.edcFeeType eq '1001'}">
					        	유료
					        	${detailVO.costAmt }원
					        	</c:when>
					        	<c:otherwise>
					        	무료
					        	</c:otherwise>
					        </c:choose>
					        
                      <div class="tooltip" tabindex="0">
                        <span>?</span>
                        <div class="tooltip-pop">
                          <span>·교육비는 강좌의 성격에 따라 무료 또는 유료로 구분됩니다.</span>
                          <span>·유료 강좌의 경우 강좌 수강을 위해 강좌접수가 마감되기 전까지 모두 결제하여야 합니다.</span>
                          <span>·수강중인 강좌를 취소 하시면 환불 정책에 따라 결제하신 금액에서 일정금액을 공제하고 잔여금액이 환불됩니다.</span>
                          <span>·환불금액은 강좌 또는 기관의 정책에 의거하여 차등적으로 지불될 수 있습니다.</span>
                          <span>·카드 결제 후 수강중 취소에 대한 환불은 카드사를 통해 지급되지 않으므로 해당 기관 또는 담당자에 문의하여 주시기 바랍니다.</span>
                        </div>
                      </div>
                    </td>
                    <th scope="row">추가비용</th>
                    <td>교재비
                      <div class="tooltip" tabindex="0">
                        <span>?</span>
                        <div class="tooltip-pop">
                          <span>·추가비용은 강좌 수강 시 필요한 재료비 등입니다.</span>
                          <span>·추가비용은 강좌접수 신청 시 결제 금액에 포함되지 않으며 별도로 결제하여야 합니다. (인터넷 결제 불가)</span>
                          <span>·강좌 수강 시 재료비 등으로 사용된 비용은 수강중 취소시에 환불되지 않으므로 유의하시기 바랍니다.</span>
                          <span>·기타 궁금한 사항은 각 강좌의 담당자 연락처로 문의하여 주시기 바랍니다.</span>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">강의계획서</th>
                    <td colspan="3">
         				<c:if test="${not empty detailVO.edcPlanOrigin}">
	                    	<a href="<tags:UploadFileUrl filePath='${detailVO.edcPlanPath}' fileName='${detailVO.edcPlanFilenm}' originName='${detailVO.edcPlanOrigin}'/>" class="down_txt">
	                    	<img src="../../resources/images/sub/ico_file.png" alt=""><c:out value="${detailVO.edcPlanOrigin}"/>
	                    	</a>
		                  </c:if>
	                      							
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">문의전화</th>
                    <td colspan="3">
                    	${detailVO.edcGuideTelno}
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">강좌소개</th>
                    <td colspan="3">
                      <div class="myTable-content">
                      ${fn:replace(detailVO.edcPrgmIntroCnts,crlf , '<br/>')}
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">특이사항
                      <br>(준비물,
                      <br class="pc">기타사항 등)
                    </th>
                    <td colspan="3">
						<c:if test="${detailVO.edcTchmtrGuideyn eq 'Y'}">
							${fn:replace(detailVO.edcTchmtrGuide,crlf , '<br/>')}
						</c:if>                    
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">교육대상</th>
                    <td colspan="3">
                    ${detailVO.targetName}
                      <div class="tooltip" tabindex="0">
                        <span>?</span>
                        <div class="tooltip-pop">
                          <span>·회원 정보에 설정된 성별 기준으로 남성, 여성 교육대상 이 확인됩니다.</span>
                          <span>·회원 정보에 설정된 생년월일 기준으로 영유아(0~7세), 어린이(8~13세), 청소년(14~19세), 성인(20세이상), 어르신(65세이상)</span>
                          <span>·교육대상 적용됩니다.</span>
                          <span>·기타 대상은 안내정보 제공히되며 해당 시 하단 담당자에게 문의 바랍니다.</span>
                          <span>·기타 궁금한 사항은 하단의 담당자 연락처로 문의하여 주시기 바랍니다. </span>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">주소</th>
                    <td colspan="3">${detailVO.orgAddr }&nbsp; ${detailVO.orgName }</td>
                  </tr>
                </tbody>
              </table>
              <div class="myTable-desc">
                <span>· 강좌에 대한 상세정보를 확인하시고 수강 신청하여 주시기 바랍니다.</span>
                <span>· 모집인원이 초과될 경우 일정인원이 대기자로 등록되며, 수강신청자가 신청을 취소한 경우, 대기인원이 수강신청자로 변경될 수 있습니다.</span>
                <span>· 유료강좌의 경우, 수강료를 모두 납부하여야 수강신청이 완료되며, 수강료 납부 후 취소 시 전액 또는 일부 금액이 환불됩니다.</span>
                <span>· (환불금액은 강좌마다 다를 수 있습니다.)</span>
                
                
                
                <span>· ${detailVO.orgName } 전화번호 : ${detailVO.orgTel } 이메일 : ${detailVO.orgEmail }</span>
              </div>
              <div class="d-flex justify-content-center align-items-center myTable-classApp">
                <img src="../../resources/images/sub/ico_mark.png" alt="아이콘">
                <div> 수강신청 &nbsp;<strong>${detailVO.edcStatus }</strong> 입니다.</div>
              </div>
            </div>
          </div>
        </div>
        

        <div class="badge-btn">
			<a href="javascript:history.back();" class="black">목록</a>
			
	        <c:choose>
	        	<c:when test="${detailVO.edcRsvnRectype eq '5001' and empty detailVO.edcRsvnLinkurl}">
	        		<!--외부 링크 바로가기 없음 -->
	        	</c:when>
				<c:when test="${detailVO.edcRsvnRectype eq '5001' }">
		            <a href="javascript:fnDetailApply();" class="green">강좌 URL연결</a>
				</c:when>
				<c:when test="${detailVO.alreadyApplyYn eq 'Y'}">
		            <a href="#none" class="gray">이미 신청완료</a>
				</c:when>
				<c:when test="${detailVO.edcStatus eq '신청'}">
					<c:choose>
						<c:when test="${detailVO.edcRsvnAccssrd eq '2001' }">
				            <a href="#none" class="red">현장에서 접수하세요!</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:fnDetailApply();" class="green">강좌 신청하기</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${detailVO.edcStatus eq '대기접수'}">
						<a href="javascript:fnDetailApply();" class="green">대기 접수하기</a>
				</c:when>
				<c:when test="${detailVO.edcStatus eq '준비'}">
					<a href="#none" class="blue">접수준비</a>
				</c:when>
				<c:when test="${detailVO.edcStatus eq '종료'}">
					<a href="#none" class="gray">접수종료</a>
				</c:when>
				<c:otherwise>
					<a href="#none" class="gray">${detailVO.edcStatus }</a>
				</c:otherwise>        
			</c:choose>        

        </div>
      </div>
    </main>
    
	<div class="modal" id="discountModal">
	<!-- 감면혜택 모달 팝업 -->
	</div>
	<div class="modal" id="modal1">
	<!-- 강좌 신청 모달 팝업 -->
	</div>    
    