<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

        <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">마이페이지</h3>
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
                <a href="javascript:void(0)" title="이동" class="pageTit-link">마이페이지</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">내정보관리</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원정보 재동의</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="sub">
        
      <%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myInforInclude.jsp"%>

        <div class="myTable keep">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <p class="myTable-"></p>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">회원정보 보유
                      <br>재동의 안내
                    </th>
                    <td colspan="3">
                      개인정보보호법 21조 및 표준 개인정보보호 보호지침 제60조에 의거하여, 회원정보 보유 동의 후 2년 만기가 되는 회원이
                      <br class="pc">
                      [회원정보 보유기간 연장(보유 재동의)]를 하지 않으면 2년 만기일에 회원님의 개인정보를 삭제하고, 향후 자동탈퇴 처리됩니다.
                      <br class="pc">
                      자동탈퇴를 원하지 않을 경우, 재동의 기간 내에 <strong>‘[로그인] > [마이페이지] > [내정보관리] > [회원정보 재동의] ’</strong>를 진행해주시면 기존과 동일한
                      서비스를
                      받을 수 있습니다.
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="myTable mt-5">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <p class="myTable-"></p>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">아이디</th>
                    <td colspan="3">${loginVO.id}</td>
                  </tr>
                  <tr>
                    <th scope="row">가입일시</th>
                    <td colspan="3">${fn:substring(loginVO.regdate,0,4)}년 ${fn:substring(loginVO.regdate,5,7)}월 ${fn:substring(loginVO.regdate,8,10)}일</td>
                  </tr>
                  <tr>
                    <th scope="row">최근 재동의 일시</th>
                    <td colspan="3">${fn:substring(loginVO.agreeLastDate,0,4)}년 ${fn:substring(loginVO.agreeLastDate,4,6)}월 ${fn:substring(loginVO.agreeLastDate,6,8)}일</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">회원정보 재동의</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">재동의 기간</th>
                    <td colspan="3">${fn:substring(agreePeriod,0,4)}년 ${fn:substring(agreePeriod,4,6)}월 ${fn:substring(agreePeriod,6,8)}일까지</td>
                  </tr>
                  <tr>
                    <th scope="row">개인정보 파기 및 탈퇴시행일자</th>
                    <td colspan="3">${fn:substring(agreePeriod,0,4)}년 ${fn:substring(agreePeriod,4,6)}월 ${fn:substring(agreePeriod,6,8)}일까지</td>
                  </tr>
                  <tr>
                    <th scope="row">주요 시행 내용</th>
                    <td colspan="3">${fn:substring(agreePeriod,0,4)}년 ${fn:substring(agreePeriod,4,6)}월 ${fn:substring(agreePeriod,6,8)}일까지 회원정보 보유 재동의를 하지 않을 경우, 회원정보 삭제 및 자동탈퇴 처리</td>
                  </tr>
                  <tr>
                    <th scope="row">파기 대상
                      <br>개인정보 항목
                    </th>
                    <td colspan="3">회원가입 시 입력한 정보 및 서비스 이용내역 등의 모든 정보가 삭제되며 삭제된 아이디는 재사용 및 복구 불가능 합니다.</td>
                  </tr>
                  <tr>
                    <th scope="row">관련법령</th>
                    <td colspan="3">
                      표준개인정보보호지침 제60조(개인정보파일 보유기간의 산정)
                      <br>
                      <strong>제 ③항</strong> 정책고객, 홈페이지회원 등의 홍보 및 대국민서비스 목적의 외부고객 명부는 특별한 경우를 제외하고는
                      <br>
                      2년을 주기로 정보주체의 재동의 절차를 거쳐 동의한 경우에만 계속하여 보유할 수 있다.
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        
        <form name="memberVO" id="memberVO" method="post" action="./reAgreeProc">
		<input type="hidden" id="returnURL" name="returnURL" value="<c:url value="${loginReturnURL}" />" />
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		</form>
        
        
        <div class="badge-btn">
          <button type="button" class="green" data-bs-target="#agreeAgain" data-bs-toggle="modal">재동의</button>
          <button type="button" class="gray" data-bs-toggle="modal" data-bs-target="#agreeAgain-cancel">취소</button>
        </div>
      </div>
  </div>
  </main>
  
<script type="text/javascript">
function reAgreeProc() {
	//alert("reAgreeProc()");
	$('#memberVO').submit();
}


</script>