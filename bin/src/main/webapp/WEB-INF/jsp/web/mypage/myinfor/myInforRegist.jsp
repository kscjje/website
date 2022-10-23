<%@page import="com.hisco.user.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>

<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>


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
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원정보수정</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">
      
      <%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myInforInclude.jsp"%>
		
<form name="memberVO" id="memberVO" method="post" action="./myInforSave">
<input type="hidden" id="returnURL" name="returnURL" value="<c:url value="${loginReturnURL}" />" />
<input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <p class="myTable-text">
                <span class="star">*</span>표시는 필수 입력사항입니다.
              </p>
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
                    <td colspan="3">
                      <div class="w_box flex-column align-items-start">
                        <div class="input-group">
                          <label for="inp08_01_01" class="visually-hidden"></label>
                          <input type="text" id="inp08_01_01" class="inp02_box w280" name="memNm" value="${memberVO.memNm}">
                        </div>
                        <p class="myTable-note">
                          <span class="star-green">*</span>이름이 부정확할경우 강좌 신청등에 제약이 있습니다. 정확히 입력해주세요.
                        </p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">아이디<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box">
                        <div class="input-group">
                          <label for="inp08_01_02" class="visually-hidden"></label>
                          <input type="text" id="inp08_01_02" class="inp02_box" name="id" value="${memberVO.id}" readonly="readonly">
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">생년월일<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box dong ex">
                        <div class="d-flex">
                          <div class="searchBar-item01 me-3">
                            <div class="dates">
                              <label for="datapiker_08_01_01" class="visually-hidden"></label>
                              <input type="text" autocomplete="off" id="datapiker_08_01_01" class="form-control datepicker birth" name="birthDate" value="${memberVO.birthDate}">
                            </div>
                          </div>
                          <button class="btn black btn-badge" data-bs-target="#ageUnder" data-bs-toggle="modal">인증완료</button>
                          <!-- 인증완료 클릭시 생년월일 확인버튼으로 변경 -->
                          <!-- <button class="btn black btn-badge">생년월일확인</button> -->
                        </div>
                      </div>
                      <p class="myTable-note">
                        <span class="star-green">*</span>20200815 형식(생년월일이 실재와 다른 경우, 강좌 신청,홈페이지 이용등에
                        많은 제약 있습니다. 정확히 입력해주세요.)
                      </p>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">보호자인증<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box align-items-start justify-content-start certified">
                        <div class="input-group wAuto me-3">
                          <label for="inp08_01_03" class="visually-hidden"></label>
                          <input type="text" id="inp08_01_03" class="inp02_box w280" value="김남양">
                        </div>
                        <div class="button-group w-100">
                          <button class="btn black btn-badge me-3">본인인증</button>
                          <button class="btn black btn-badge">IPIN인증</button>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">보호자관계<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box align-items-start justify-content-start">
                        <div class="input-group me-3">
                          <label for="inp08_01_04" class="visually-hidden"></label>
                          <input type="text" id="inp08_01_04" class="inp02_box w280" placeholder="예)모, 부, 삼촌">
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">성별<i class="star">*</i>
                    </th>
                    <td colspan="3">
                    <input type="hidden" name="gender" value="${memberVO.gender}">
                      <p class="txt"><c:out value="${memberVO.gender eq 1? '남자':'여자'}"/></p>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">국적<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="radio">
                        <span>
                          <input name="sub08_01_01" id="inp08_01_05" type="radio" checked>
                          <label for="inp08_01_05">내국인</label>
                        </span>
                        <span>
                          <input name="sub08_01_01" id="inp08_01_06" type="radio">
                          <label for="inp08_01_06">외국인</label>
                        </span>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">주소<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box dong">
                        <div class="d-flex">
                          <div class="input-group wAuto me-3">
                            <label for="inp08_01_07" visually-hidden"></label>
                            <input type="text" id="postNum"  name="postNum" value="${memberVO.postNum }">
                          </div>
                          <button type="button"  class="btn black btn-badge me-3"  onclick="sample6_execDaumPostcode();">우편번호검색</button>
                        </div>
                      </div>
                      <div class="w_box">
                        <div class="input-group mt-3">
                          <label for="inp08_01_08" visually-hidden"></label>
                          <input type="text" id="addr1"  name="addr1" value="${memberVO.addr1 }">
                        </div>
                      </div>
                      <div class="w_box">
                        <div class="input-group mt-3">
                          <label for="inp08_01_09" visually-hidden"></label>
                          <input type="text" id="addr2"  name="addr2" value="${memberVO.addr2 }">
                        </div>
                      </div>
                      <p class="myTable-note">
                        <span class="star-green">*</span>강의 신청시 지역(동)제한이 될수 있으므로 주민등록상 주소를 정확히 입력하셔야
                        합니다.
                      </p>
                      <div class="w_box mt-3 dong">
                        <div class="d-flex">
                          <div class="input-group wAuto me-3">
                            <label for="inp08_01_10" class=" class=" visually-hidden"></label>
                            <input type="text" id="inp08_01_10" value="행정동없음" disabled>
                          </div>
                          <button class="btn black btn-badge me-3" data-bs-target="#referWait" data-bs-toggle="modal">행정동검색</button>
                        </div>
                        <div class="button-group">
                          <button class="btn green btn-badge me-3" data-bs-target="#searchMethod" data-bs-toggle="modal">행정동
                            검색방법보기</button>
                        </div>
                      </div>
                      <p class="myTable-note">
                        <span class="star-green">*</span>조회시 행정안전부 연계작업으로 5~10초정도 시간이 소요될 수 있습니다.
                      </p>
                      <p class="myTable-note">
                        <span class="star-green">*</span>남양주시 거주자가 아닌 경우 행정동 검색후 행정동 없음으로 입력됩니다.
                      </p>
                    </td>
                  </tr>
                  
                  <tr>
                    <th scope="row">전화번호</th>
                    <td colspan="3">
                      <div class="w_box w_460 justify-content-between">
                        <div class="input-group">
                          <div class="searchBar-item">
                            <button class="btn">선택</button>
                            <ul class="searchBar-list">
                              <li>
                                <button>02</button>
                              </li>
                              <li>
                                <button>031</button>
                              </li>
                              <li>
                                <button>032</button>
                              </li>
                              <li>
                                <button>033</button>
                              </li>
                              <li>
                                <button>041</button>
                              </li>
                              <li>
                                <button>042</button>
                              </li>
                              <li>
                                <button>043</button>
                              </li>
                              <li>
                                <button>051</button>
                              </li>
                              <li>
                                <button>052</button>
                              </li>
                              <li>
                                <button>053</button>
                              </li>
                              <li>
                                <button>054</button>
                              </li>
                              <li>
                                <button>055</button>
                              </li>
                              <li>
                                <button>061</button>
                              </li>
                              <li>
                                <button>062</button>
                              </li>
                              <li>
                                <button>063</button>
                              </li>
                              <li>
                                <button>064</button>
                              </li>
                              <li>
                                <button>070</button>
                              </li>
                            </ul>
                          </div>
                        </div>
                        <div class="input-group">
                          <label for="inp08_01_11" class=" class=" visually-hidden"></label>
                          <input type="text" title="전화번호" value="" id="inp08_01_11">
                        </div>
                        <div class="input-group">
                          <label for="inp08_01_12" class=" class=" visually-hidden"></label>
                          <input type="text" title="전화번호" value="" id="inp08_01_12">
                        </div>
                      </div>
                    </td>
                  </tr>
                  
                  
                  
                  <tr>
                    <th scope="row">휴대폰번호<span class="star">*</span>
                    </th>
                    <td colspan="3">
                      <div class="w_box w_460 justify-content-between">
                        <div class="input-group">
                          <div class="searchBar-item">
                            <button type="button" class="btn" id="hp1">${fn:substring(memberVO.hp,0,3) }</button>
                            <ul class="searchBar-list">
                              <li>
                                <button type="button">010</button>
                              </li>
                              <li>
                                <button type="button">011</button>
                              </li>
                              <li>
                                <button type="button">016</button>
                              </li>
                              <li>
                                <button type="button">017</button>
                              </li>
                              <li>
                                <button type="button">018</button>
                              </li>
                              <li>
                                <button type="button">019</button>
                              </li>
                            </ul>
                          </div>
                        </div>
                        <div class="input-group">
                          <label for="inp08_01_13" class=" class=" visually-hidden"></label>
                          <input type="text" title="전화번호" value="${fn:substring(memberVO.hp,3,7) }" id="hp2"  class="hp2"  name="hp2">
                        </div>
                        <div class="input-group">
                          <label for="inp08_01_114" class=" class=" visually-hidden"></label>
                          <input type="text" title="전화번호" value="${fn:substring(memberVO.hp,7,11) }"  id="hp3"  class="hp3"  name="hp3">
                          <input type="hidden" id="hp" class="hp" name="hp" value="${memberVO.hp}">
                        </div>
                      </div>
                    </td>
                  </tr>
                  
                  
                  <%
                  
                  MemberVO member = (MemberVO)request.getAttribute("memberVO");
                  String email = member.getEmail();
                  System.out.println("email : " + email);
                  
                  String [] emailSpl = email.split("@");
                  
                  if(emailSpl != null && emailSpl.length > 1){
                	  request.setAttribute("email1" , emailSpl[0]);
                	  request.setAttribute("email2" , emailSpl[1]);
                  }
                  
                  %>
                  <tr>
                    <th scope="row">이메일</th>
                    <td colspan="3">
                      <div class="w_box e_mail">
                        <div class="d-flex align-items-center">
                          <div class="input-group wAuto">
                            <label for="inp08_01_15" class=" class=" visually-hidden"></label>
                            <input type="hidden" id="email" class="email" name="email" value="${email1 }">
                            <input type="text" id="email1" class="w280" name="email1" value="${email1 }">
                          </div>
                          <span>@</span>
                          <div class="input-group wAuto">
                            <label for="inp08_01_16" class=" class=" visually-hidden"></label>
                            <input type="text" id="email2" class="w280" name="email2" value="${email2 }">
                          </div>
                        </div>
                        <div class="myTable-email d-flex w-100 ms-3">
                          <div class="searchBar-item">
                            <button  type="button" class="btn">직접입력</button>
                            <ul class="searchBar-list">
                              <li>
                                <button type="button">naver.com</button>
                              </li>
                              <li>
                                <button type="button">daum.net</button>
                              </li>
                              <li>
                                <button type="button">google.com</button>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                      <p class="ms-3">※ 이메일 최대길이는 50자이내입니다.</p>
                      <p class="myTable-note">
                        <span class="star-green">*</span>미입력시 'noEmail'로 입력됩니다.
                      </p>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">수신동의</th>
                    <td colspan="3">
                      <div class="radio">
                        <span>
                          <input name="smsYn" id="inp08_01_17" type="checkbox"    ${memberVO.smsYn eq "Y"? "checked" : ""} value="Y">
                          <label for="inp08_01_17">알림정보 수신동의</label>
                        </span>
                      </div>
                      <p class="myTable-note">
                        <span class="star-green">*</span> 강좌, 행사 등 안내 문자수신 동의함.
                      </p>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        
        
        </form>       

        <div class="badge-btn">
          <a href="#none" class="gray" data-bs-target="#membership-revise" data-bs-toggle="modal">취소</a>
          <a href="#none" class="green" data-bs-target="#revise-once" data-bs-toggle="modal">수정</a>
        </div>


      </div>

  </div>
  </main>
  
  
 <script type="text/javascript">
  function memberInfoSave(){
	  //alert("memberInfoSave()");
	  
	  var hp1 = $("#hp1").text();
	  var hp2 = $("#hp2").val();
	  var hp3 = $("#hp3").val();
	  
	  $("#hp").val(hp1 + hp2 + hp3);
	  
	  var email1 = $("#email1").val();
	  var email2 = $("#email2").val();
	  
	  $("#email").val(email1 + '@' + email2);
	  
	  $('#memberVO').submit();
	  
  }
 </script >
 
 
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


 <script type="text/javascript">
function sample6_execDaumPostcode() {
      new daum.Postcode({
          oncomplete: function(data) {
              // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

              // 각 주소의 노출 규칙에 따라 주소를 조합한다.
              // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
              var addr = ''; // 주소 변수
              var extraAddr = ''; // 참고항목 변수

              //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
              if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                  addr = data.roadAddress;
              } else { // 사용자가 지번 주소를 선택했을 경우(J)
                  addr = data.jibunAddress;
              }

              // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
              if(data.userSelectedType === 'R'){
                  // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                  // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                  if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                      extraAddr += data.bname;
                  }
                  // 건물명이 있고, 공동주택일 경우 추가한다.
                  if(data.buildingName !== '' && data.apartment === 'Y'){
                      extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                  }
                  // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                  if(extraAddr !== ''){
                      extraAddr = ' (' + extraAddr + ')';
                  }
                  // 조합된 참고항목을 해당 필드에 넣는다.
                  //document.getElementById("sample6_extraAddress").value = extraAddr;
              
              } else {
                  //document.getElementById("sample6_extraAddress").value = '';
              }

              // 우편번호와 주소 정보를 해당 필드에 넣는다.
              document.getElementById('postNum').value = data.zonecode;
              document.getElementById("addr1").value = addr;
              // 커서를 상세주소 필드로 이동한다.
              document.getElementById("addr2").focus();
          }
      }).open();
  }
  </script>