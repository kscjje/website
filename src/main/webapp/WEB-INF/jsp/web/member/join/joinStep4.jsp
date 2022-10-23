<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
	String strJusoKey = EgovProperties.getProperty("juso.go.kr.key");
%>
<style>
.text-red{
	color:red
}
.text-green{
	color:green
}
</style>


<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
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


<form:form commandName="memberVO" name="memberVO" id="memberForm" action="./joinAction" method="post">

						<form:hidden path="piAuthkey"/>
						<form:hidden path="piAuthtype"/>
						<form:hidden path="memNm"/>
						<form:hidden path="gender"/>
						
						<form:hidden path="birthDate"/>
						<form:hidden path="piPAuthkey"/>
						<form:hidden path="piPAuthtype"/>
						
						<input type="hidden" id="snsid" name="snsVO.snsid" />
						<input type="hidden" id="snskind" name="snsVO.snsRegistkind"/>
						<input type="hidden" id="type" name="type" value="<c:out value="${type}"/>"  />
						<input type="hidden" id="dupCheckId" name="dupCheckId" />
						<input type="hidden" id="dupCheckEmail" name="dupCheckEmail" />
						<input type="hidden" id="payYn" name="payYn" />	



    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">회원가입</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">회원가입</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원가입</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원유형</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

     <div class="sub">
        <div class="accredit pc">
          <div class="accredit-inner">
            <div class="accredit-wrap">
              <ul class="accredit-list">
                <li class="accredit-item">
                  <p>01</p>
           회원선택
                </li>
                <li class="accredit-item">
                  <p>02</p>
                  약관동의
                </li>
                <li class="accredit-item">
                  <p>03</p>
                  본인인증
                </li>

                <li class="accredit-item on">
                  <p>04</p>
                  회원정보입력
                </li>
                <li class="accredit-item">
                  <p>05</p>
                  회원가입 완료
                </li>
              </ul>
            </div>
          </div>
        </div>

        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap pt-0">
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
                    <th scope="row">이름<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box flex-column align-items-start">
                        <div class="input-group">
                          <label for="inp09_04_01" class=" visually-hidden"></label>
                          
							<c:if test="${type eq 'child'}">
	                          <input type="text" id="inp09_04_01" class="inp02_box w280" value="김별내">
							
								<p id="childName"></p>
							</c:if>
							
							<c:if test="${type ne 'child'}">
	                          <input type="text" id="inp09_04_01" class="inp02_box w280" readonly value="<c:out value="${memberVO.memNm}" />">
							
								
								<form:errors path="memNm" cssClass="text-red" />													
							</c:if>	                          
                          
                          
                          
                          
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
                      <div class="w_box dong ex">
                        <div class="d-flex">
                          <div class="input-group w280 me-3">
                            <label for="id" class=" visually-hidden"></label>
                            <form:input path="id"  placeholder="아이디를 입력해주세요." title="아이디" maxlength="20" class="inp02_box" value=""/>
	                          
	                          <p style="padding-top:7px;padding-left:14px;" id="idResultDiv"></p>
							  <form:errors path="id" cssClass="text-red" />
                            
                          </div>
                          <button type="button" class="btn black btn-badge" onclick="dupliCheck('id')" data-bs-target="#joinMember-02">중복확인</button>
                        </div>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">비밀번호<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box column">
                        <div class="input-group w280 me-3">
                          <label for="pw" class=" visually-hidden"></label>
	                        <input name="pw" type="password" id="pw" class="inp02_box" placeholder="비밀번호를 입력해주세요." onkeyup="validMember.passCheck(); return false;" maxlength="16" title="비밀번호">
							<p style="padding-top:7px;padding-left:14px;" id="errPass" class="errMsg" ></p>
							<form:errors path="pw" cssClass="text-red" />                          
                        </div>
                        <p>8~16자 영문 대소문자, 특수문자, 숫자를 사용하세요.</p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">비밀번호 확인<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box column">
                        <div class="input-group w280 me-3">
                          <label for="pw2" class=" visually-hidden"></label>
						  <input type="password" id="pw2" name="pw2" class="inp02_box"  placeholder="비밀번호를 재입력해주세요." onkeyup="validMember.passDiff(); return false;" maxlength="16" title="비밀번호 확인">
						  <p style="padding-top:7px;padding-left:14px;" id="errPass2" class="errMsg" ></p>	                          
                        </div>
                        <p>8~16자 영문 대소문자, 특수문자, 숫자를 사용하세요.</p>
                      </div>
                    </td>
                  </tr>
                  <!-- 
                  <tr>
                    <th scope="row">생년월일<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box dong ex">
                        <div class="d-flex">
                          <div class="searchBar-item01 me-3">
                            <div class="dates">
                              <label for="datapiker_09_04_01" class=" visually-hidden"></label>
                              <input type="text" autocomplete="off" id="datapiker_09_04_01" class="form-control datepicker birth" value="20090205">
                            </div>
                          </div>
                          	생년월일확인 클릭시 인증완료로 글짜 변경
                          <button class="btn black btn-badge" data-bs-target="#brirth-chk" data-bs-toggle="modal">생년월일확인</button>
                        </div>
                      </div>
                      <p class="myTable-note">
                        <span class="star-green">*</span>인증완료 상태에서 수정이 필요한 경우 생년월일확인버튼을 눌러주세요.
                      </p>
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
                          <label for="inp09_04_05" class=" visually-hidden"></label>
                          <input type="text" id="inp09_04_05" class="inp02_box w280" value="김남양">
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
                          <label for="inp09_04_06" class=" visually-hidden"></label>
                          <input type="text" id="inp09_04_06" class="inp02_box w280" placeholder="예)모, 부, 삼촌">
                        </div>
                      </div>
                    </td>
                  </tr>

                  <tr>
                    <th scope="row">성별<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="radio">
                        <span>
                          <input name="sub09_04_01" id="inp09_04_07" type="radio" checked>
                          <label for="inp09_04_07">남자</label>
                        </span>
                        <span>
                          <input name="sub09_04_01" id="inp09_04_08" type="radio">
                          <label for="inp09_04_08">여자</label>
                        </span>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">국적<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="radio">
                        <span>
                          <input name="sub09_04_02" id="inp09_04_09" type="radio" checked>
                          <label for="inp09_04_09">내국인</label>
                        </span>
                        <span>
                          <input name="sub09_04_02" id="inp09_04_10" type="radio">
                          <label for="inp09_04_10">외국인</label>
                        </span>
                      </div>
                    </td>
                  </tr>
					-->
                  <tr>
                    <th scope="row">주소<i class="star">*</i>
                    </th>
                    <td colspan="3">
                      <div class="w_box dong">
                        <div class="d-flex">
                          <div class="input-group wAuto me-3">
                            <label for="postNum" class="visually-hidden"></label>
                            <input type="text" id="postNum" name="postNum" placeholder="" readonly />
                          </div>
                          <!-- 
                          <button type="button" onclick="fn_juso_layer_pop(); return false;" class="btn black btn-badge me-3">주소찾기</button>
                           -->
                          <button type="button" id="btnSearchAddress" class="btn black btn-badge me-3" onclick="sample6_execDaumPostcode();">주소찾기</button>
                          
                        </div>
                      </div>
                      <div class="w_box">
                        <div class="input-group mt-3">
                          <label for="addr1" class="visually-hidden"></label>
                          <input type="text" id="addr1" name="addr1" readonly placeholder="주소 검색해주세요" />
                        </div>
                      </div>
                      <div class="w_box">
                        <div class="input-group mt-3">
                          <label for="addr2" class="visually-hidden"></label>
                          <input type="text" id="addr2" name="addr2" placeholder="상세주소를 입력해주세요." />
                        </div>
                      </div>
                      <p class="myTable-note">
                      	<p id="addressResultDiv"></p>
                        <span class="star-green">*</span>강의 신청시 지역(동)제한이 될수 있으므로 주민등록상 주소를 정확히 입력하셔야  합니다.
                      </p>
                      <!-- 
                      <div class="w_box mt-3 dong">
                        <div class="d-flex">
                          <div class="input-group wAuto me-3">
                            <label for="inp09_04_14" class="visually-hidden"></label>
                            <input type="text" id="inp09_04_14" value="행정동없음" disabled>
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
                       -->
                    </td>
                  </tr>
                  <!-- 
                  <tr>
                    <th scope="row">전화번호</th>
                    <td colspan="3">
                      <div class="w_box w_460 justify-content-between">
                        <div class="input-group">
                          <div class="searchBar-item">
                            <button type="button" class="btn">선택</button>
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
                          <label for="inp09_04_15" class="visually-hidden"></label>
                          <input type="text" title="전화번호" value="" id="inp09_04_15">
                        </div>
                        <div class="input-group">
                          <label for="inp09_04_16" class="visually-hidden"></label>
                          <input type="text" title="전화번호" value="" id="inp09_04_16">
                        </div>
                      </div>
                    </td>
                  </tr>
                   -->
                  <tr>
                    <th scope="row">휴대폰번호<span class="star">*</span>
                    </th>
                    <td colspan="3">
                      <div class="w_box w_460 justify-content-between">
                      <c:choose>
                      	<c:when test="${empty memberVO.hp }">
                      		<input type="text" id="hp" name="hp"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
                      	</c:when>
                      	<c:otherwise>
                      		<tags:HpTelShow hpval="${memberVO.hp}"/>
							<form:hidden path="hp"/>
							<form:errors path="hp" cssClass="text-red" />
                      	</c:otherwise>
                      </c:choose>
                      
                      </div>
                      
                      <c:if test="${empty memberVO.hp  }">
                      	<p class="myTable-note" id="hpResultDiv">
	                        <span class="star-green">*</span>휴대폰번호는 반드시 숫자로 입력해주세요
	                        <p style="color:red;" id="hpResultDivWan"></p>
	                    </p>
                      </c:if>
                      
                      
                    </td>
                  </tr>
                  
                  <tr>
                    <th scope="row">이메일<span class="star">*</span></th>
                    <td colspan="3">
                      <div class="w_box e_mail">
                        <div class="d-flex align-items-center">
                          <div class="input-group wAuto">
                            <label for=email class="visually-hidden">이메일 주소<strong class="red">*</strong></label>
							<!--<form:input path="email" title="이메일" class="w280" maxlength="100" placeholder="이메일 주소" />-->
							<input id="email" title="이메일" class="w280" maxlength="100" placeholder="이메일 주소" />
							<input type="hidden" name="email" title="최종이메일값" class="w280" maxlength="100" placeholder="이메일 주소" />
                          </div>
                          <span>@</span>
                          <div class="input-group wAuto">
								<label for="mail2" class="visually-hidden">도메인 직접입력</label>
								<input type="text" id="mail2" name="mail2"  class="w280" placeholder="직접입력" />                          
                          </div>
                        </div>
                        <div class="myTable-email d-flex w-100 ms-3">
                          <div class="searchBar-item">
                            <button type="button" class="btn">선택</button>
                            <ul class="searchBar-list">
									<li>
									  <button onclick="fn_mailserver_save(this); return false;" type="button" value="">선택</button>
									</li>
								<c:forEach items="${mailServerGrpCdList}" var="item" varStatus="status">
									<li>
									  <button type="button"  onclick="fn_mailserver_save(this); return false;" value="${item.cdNm}"><c:out value='${item.cdNm}'/></button>
									</li>											
								</c:forEach>
                            </ul>
                          </div>
                        </div>
                      </div>
					  <form:errors path="email" cssClass="text-red" />	
                      <p class="">※ 이메일 최대길이는 50자이내입니다.</p>
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
                        	<input type="checkbox" id="smsYn" name="smsYn" checked="true" />
                          <label for="smsYn">알림정보 수신동의</label>
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

        <div class="badge-btn">
          <a href="#none" onclick="fn_submit('N'); return false;" class="green">회원가입</a>
          <a href="/" class="gray">취소</a>
        </div>



      </div>

  </div>
  </main>
</form:form>
  
  
  		<script type="text/javascript">
		
		$(function() {

			var varChildName     = "";
			var varChildBirthDay = "";			
			
			if ("${type}" == "child") {
				
				varChildName     = sessionStorage.getItem("childName");
				varChildBirthDay = sessionStorage.getItem("childBirthDay");
				
				$("#memNm").val(varChildName);
				$("#childName").text(varChildName);
				$("#birthDate").val(varChildBirthDay); 
				$("#gender").val("");			
				
			}
			
			//// JYS naverLogin.init();

			$('#email').on('keyup',function() {
				$('#emailResultDiv').html("");
			});

			$('#id').on('keyup',function() {
				$('#idResultDiv').html("");
			});
			
			$('#keyword').on('keydown',function() {

				var evt_code = (window.netscape) ? ev.which : event.keyCode;
				
				if (evt_code == 8) {
					event.keyCode = 0;
					return true;
					
				} else if (evt_code == 13) {
					event.keyCode = 0;  
					fn_getAddr(); 			
					return false;
				}				
				
			});
			
			$("#memberForm")[0].reset();
			
		})
	
		var element;

		var validMember = {
			  
			passDiff: function() {
				
				var pass = $('#pw').val();
				
				if (pass != $('#pw2').val()) {
					$('#errPass2').addClass('text-red').text("비밀번호와 비밀번호 확인값이 일치하지 않습니다.");
					$('#pw2').focus();
					return false;
				} else {
					$('#errPass2').addClass('text-red').text('');
					return true;
				}
			},
			
			passCheck : function() {
				var newPassword = $('#pw').val();
				var msg = passwdValidationNew(newPassword);
				if (msg == '사용가능한 비밀번호 입니다.') {
					$('#errPass').removeClass('text-red').addClass('text-green').html('사용가능한 비밀번호 입니다.');
					return true;
				} else {
					$('#errPass').removeClass('text-green').addClass('text-red').html(msg);
		            $('#pw').focus();
		            return false;
				}
			},
			
			userid:function() {
				var userid = $('#id').val();
				if (userid =="") {
					$('#idResultDiv').text("아이디를 입력해 주세요.").addClass("text-red").removeClass("text-green");
					$('#id').focus();
					return false;
				} else if (!/^[a-z0-9]{4,20}$/.test(userid)) {
					$('#idResultDiv').text("아이디가 형식에 맞지 않습니다.").addClass("text-red").removeClass("text-green");
					$('#id').focus();
					return false;
				} else {
					return true;
				}
			},
			
			idCheck:function() {
				if ($('#id').val() != $('#dupCheckId').val()) {
					$('#idResultDiv').text("아이디 중복확인이 필요합니다.").addClass("text-red").removeClass("text-green");
					$('#id').focus();
					return false;
				} else {
					return true;
				}
			},
			
			email:function() {
				
				$('#emailResultDiv').text("");
				
				var varAllEMail = "";
				
				var varEMailId  = $('#email').val();
				var varSelVal   = $("select[name='mail1'] option:selected").val();
				var varSelText  = $("select[name='mail1'] option:selected").text();
				var varSerInput = $('#mail2').val();
				
				if ((varSelVal === undefined) || (varSelVal == null) || (varSelVal == '')) {
					varAllEMail = varEMailId + "@" + varSerInput; 					
				} else {
					varAllEMail = varEMailId + "@" + varSelText;
				}
				
				var email = varAllEMail;
				
				console.log(email);
				
				var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;//이메일 정규식

				if (email =="") {
					$('#emailResultDiv').addClass('text-red').text("이메일 주소를 입력해 주세요.")
					$('#email').focus();
					return false;
				} else if (!emailRule.test(email)) {
					$('#emailResultDiv').addClass('text-red').text("이메일 주소가 형식에 맞지 않습니다.")
					$('#email').focus();
					return false;
				} else {
					$("input[name='email']").val(email);
					return true;
				}

			},
			
			emailCheck:function() {

				return true;
				
			},
			
			carNo: function() {
				
				return true;
				
				var info1 = $('#carNo1').val();

			},
			
			resdncGbn : function() {
				return true;
			},
			
			address : function() {
				
				$('#addressResultDiv').text("").addClass("text-red").removeClass("text-green");
				
				var varPostNum = $("#postNum").val();
				var varAddr1   = $("#addr1").val();
				var varAddr2   = $("#addr2").val();
				
				console.log(varPostNum);
				console.log(varAddr1);
				console.log(varAddr2);
				
				if ((varPostNum === undefined) || (varPostNum == null) || (varPostNum == '')) {
					$('#addressResultDiv').text("주소를 검색해주세요").addClass("text-red").removeClass("text-green");
					return false;
				}				
				
				if ((varAddr1 === undefined) || (varAddr1 == null) || (varAddr1 == '')) {
					$('#addressResultDiv').text("주소를 검색해주세요").addClass("text-red").removeClass("text-green");
					return false;
				}
				
				if ((varAddr2 === undefined) || (varAddr2 == null) || (varAddr2 == '')) {
					$('#addressResultDiv').text("상세주소를 입력해주세요").addClass("text-red").removeClass("text-green");
					return false;					
				}				
				
				return true;
				
			},	
			
			//idCheck:function() {
			hpCheck:function() {
				var varHp = $('#hp').val();
				if (   (varHp === undefined) || (varHp == null) || (varHp == '')   ) {
					$('#hpResultDiv').css("color","red").text("");
					$('#hpResultDivWan').css("color","red").text("휴대폰번호는 반드시 숫자로 입력해주세요");
					$('#hp').focus();
					return false;
				} else {
					return true;
				}
			},
			
			
			smsYn : function() {
			
				$('#smsYnDiv').text("").addClass("text-red").removeClass("text-green");
				
				var varSmsYn = $("#smsYn").is(":checked");
				if (varSmsYn) {
					$("#smsYn").val("Y");
				} else {
					//$('#smsYnDiv').text("노원수학문화관에서 제공하는 알림서비스 이용에 동의해야 합니다.").addClass("red").removeClass("green");
					$("#smsYn").val("N");
					//return false;					
				}	
				return true;
				
			},
			
			chkAll : function() {
				var chkList = [validMember.resdncGbn(), validMember.passDiff(), validMember.passCheck(), validMember.email(), validMember.userid(), validMember.address(), validMember.smsYn(), validMember.hpCheck()];
				var flag = true;
				var idFlag = false;
				var emailFlag = false;
				$.each(chkList,function() {
					if (this == false) {
						flag = false;
					}
				});
				if (validMember.userid()) {
					idFlag = validMember.idCheck();
				}
				if (validMember.email()) {
					emailFlag = validMember.emailCheck();
				}
				if (flag) {
					flag = idFlag;
				}
				if (flag) {
					flag = emailFlag;
				}
				return flag;
			},
			
			chkSubmit : function() {
				
				var flag = validMember.chkAll();

				if (!flag) {
					return false;
				} else {
					return true;
				}
			}
			
		}// end of validMember

		var setParam = {
			  
			name : function(val) {
				var regex = /[^가-힣]/gi
				val = val.replace(regex, '')
				$('#name').val(val);
			},
			
			carNoSet : function(val , type) {
				switch (type) {
				case '1st':
					var regex = /[^0-9]/gi;
					val = val.replace(regex, '')
					$('#carNo1').val(val);
					break;
				case '2nd':
					var regex = /[^가-힣]/gi;
					val = val.replace(regex, '')
					$('#carNo2').val(val);
					break;
				case '3rd':
					var regex = /[^0-9]/gi;
					val = val.replace(regex, '')
					$('#carNo3').val(val);
					break;
				default:
					break;
				}
			}
			
	  }//end of setParam


	function dupliCheck(type) {
		  
		var id;
		var email;
		switch (type) {
			case 'id':
				id = $('#id').val();
				if (!validMember.userid()) {
					return false;
				}
			break;
			case 'email':
				id = '';
				email = $('#email').val();
				if (!validMember.email()) {
					return false;
				}
			break;
		}
		
		$.ajax({
			
		      url: './memDupliCheckAjax',
		      data : {'id' :  id, 'email' : email, 'type' : type , 'piPAuthkey':$('#piPAuthkey').val()},
		      type : "POST",
		      dataType : "json",
		      beforeSend : function(xhr) {
	                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	            },
		      success: function(data) {
		    	  console.log(data)
		    	   if (data.result.code == "ERROR") {
		    		   $('#'+type+'ResultDiv').text(data.result.msg).addClass("text-red").removeClass("text-green");
			        } else {
			        	 $('#'+type+'ResultDiv').text(data.result.msg).addClass("text-green").removeClass("text-red");
			        	 if (type == "id") {
			        		 $('#dupCheckId').val(id);
			        	 } else {
			        		 $('#dupCheckEmail').val(email);
			        	 }
			        }
		      },
		      error : function(data) {
		    	  alert("Server Error.");
		      }
		 });
		
	}//end of dupliCheck

	function fn_sns_join(id, snsEmail, snsKind) {
		
		$.ajax({
			
		      url: './findSnsCheck',
		      data : {'snsId' :  id, 'snsKind' : snsKind , 'snsEmail': snsEmail},
		      type : "POST",
		      dataType : "json",
		      beforeSend : function(xhr) {
	                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	            },
		      success: function(data) {
		    	  if (data.result.success) {
			    	  	fn_sns_cancel("1001");
				  		fn_sns_cancel("2001");
				  		fn_sns_cancel("3001");

				  		var obj = $('#SNS_' + snsKind + '_DIV');
				  		var date = new Date();
				  		var year = date.getFullYear();
				  		var month = new String(date.getMonth()+1);
				  		var day = new String(date.getDate());

				  		if (month.length == 1) {
				  		  month = "0" + month;
				  		}
				  		if (day.length == 1) {
				  		  day = "0" + day;
				  		}
				  	  	var connDate = year + "-" + month + "-" + day;
				  	  	obj.find("li").eq(1).find("input").remove();
				  	  	obj.find("li").eq(1).find("button").hide();

				  	  	obj.find("li").eq(1).append("<input type=\"text\" name=\"link_date\" readonly value=\"연결일자 "+connDate+"\">");
				  	  	obj.find("li").eq(2).html("<button type=\"button\" class=\"btn_ty_m2_c4\" onclick=\"fn_sns_cancel('"+snsKind+"')\">연결해제</button>");
						if (snsEmail != '') {
							$('#snsid').val(snsEmail);
						} else {
							$('#snsid').val(id);
						}

			         	$('#snskind').val(snsKind);

			  			alert("연결 되었습니다.");
		    	  } else {
		    		  alert("다른 아이디와 이미 연결되어 있어 등록할 수 없습니다.");
		    	  }
		      },
		      error : function(data) {
		    	  alert("Server Error.");
		      }
		 });

	}//end of snsjoin

	function fn_sns_cancel(type) {
		$('#snsid').val('');
        $('#snskind').val('');

        var obj = $('#SNS_' + type + '_DIV');

        obj.find("li").eq(1).find("input").remove();
	  	obj.find("li").eq(1).find("button").show();

	  	obj.find("li").eq(2).html("");

	}//end of fn_sns_cancel
	
	var saveFlag = false;
	function fn_submit(data) {
		if (saveFlag == false) {
			
			var form = $('#memberForm');
			$('#payYn').val(data);
			
			//validation check
			if (!validMember.chkSubmit()) {
				return;
			} else if (confirm('가입하시겠습니까?')) {
				saveFlag = true;
				
				sessionStorage.clear();
				
				form.submit();
			}
			
		} else {
			alert("처리중입니다.\n잠시만 기다려 주시기 바랍니다.");
		}

	}// end of senddata

	$(document).ready(function() {
		$(document).attr("title", "정보입력 - 회원가입 | 노원수학문화관");
	});
	
	function fn_mailserver_save(obj){
		$("#mail2").val(obj.value);
	}
	function fn_mailserver_save1(obj) {
		
		var varSelVal  = $("select[name='mail1'] option:selected").val();
		var varSelText = $("select[name='mail1'] option:selected").text();
		
		var varObj = $(obj).val();
		if ((varObj === undefined) || (varObj == null) || (varObj == '')) {
			$("#mail2").val("");
			$("#mail2").focus();
		} else {
			$("#mail2").val(varSelText);
		}

	}
	
	function fn_jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
	
		$("#postNum").val(zipNo);
		$("#addr1").val(roadAddrPart1 + " " + bdNm);
		$("#addr2").val(addrDetail);
		
	}
		
	$("#searchJuso.old").click(function() {

		var varConfmKey     = "<%= strJusoKey %>";

		var varSearchWord   = $("#searchJusoWrd").val();
		var varResultType   = "json";

		var varCurrentPage  = $("#jusoCurrentPage").val();
		if ((varCurrentPage === undefined) || (varCurrentPage == null) || (varCurrentPage == '')) {
			varCurrentPage = "1";
			$("#jusoCurrentPage").val(varCurrentPage);
		}

		var varCountPerPage  = $("#jusoCountPerPage").val();
		if ((varCountPerPage === undefined) || (varCountPerPage == null) || (varCountPerPage == '')) {
			varCountPerPage = "5";
			$("#jusoCountPerPage").val(varCountPerPage);
		}

		if ((varSearchWord === undefined) || (varSearchWord == null) || (varSearchWord == '')) {
			alert("[도로명주소, 건물명 또는 지번입력] 정보를 입력하세요");
			return;
		}

		if (!fn_checkSearchedWord(varSearchWord)) {
			return ;
		}

		fn_ajaxIfOnly("/intrfc/adres/getAddrApi", {keyword : varSearchWord, confmKey : varConfmKey, currentPage : varCurrentPage, countPerPage : varCountPerPage, resultType : varResultType},  fn_callBack);

	});	
	
	

	
	$("#searchJuso.old2").click(function() {

		if (!fn_checkSearchedWord(document.form.keyword)) {
			return ;
		}

		$.ajax({
			
			 url :"https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
			,type:"post"
			,data:$("#jusoForm").serialize()
			,dataType:"jsonp"
			,crossDomain:true
			,success:function(jsonStr) {
				
				$("#list").html("");
				var errCode = jsonStr.results.common.errorCode;
				var errDesc = jsonStr.results.common.errorMessage;
				if (errCode != "0") {
					alert(errCode+"="+errDesc);
				} else {
					if (jsonStr != null) {
						makeListJson(jsonStr);
					}
				}
			}
		    ,error: function(xhr,status, error) {
		    	alert("에러발생");
		    }
		    
		});			
		
	});
	
	function makeListJson(jsonStr) {
		
		var htmlStr = "";
		
		htmlStr += "<table>";
		
		$(jsonStr.results.juso).each(function() {
			
			htmlStr += "<tr>";
			htmlStr += "<td>"+this.roadAddr+"</td>";
			htmlStr += "<td>"+this.roadAddrPart1+"</td>";
			htmlStr += "<td>"+this.roadAddrPart2+"</td>";
			htmlStr += "<td>"+this.jibunAddr+"</td>";
			htmlStr += "<td>"+this.engAddr+"</td>";
			htmlStr += "<td>"+this.zipNo+"</td>";
			htmlStr += "<td>"+this.admCd+"</td>";
			htmlStr += "<td>"+this.rnMgtSn+"</td>";
			htmlStr += "<td>"+this.bdMgtSn+"</td>";
			htmlStr += "<td>"+this.detBdNmList+"</td>";
			
			htmlStr += "<td>"+this.bdNm+"</td>";
			htmlStr += "<td>"+this.bdKdcd+"</td>";
			htmlStr += "<td>"+this.siNm+"</td>";
			htmlStr += "<td>"+this.sggNm+"</td>";
			htmlStr += "<td>"+this.emdNm+"</td>";
			htmlStr += "<td>"+this.liNm+"</td>";
			htmlStr += "<td>"+this.rn+"</td>";
			htmlStr += "<td>"+this.udrtYn+"</td>";
			htmlStr += "<td>"+this.buldMnnm+"</td>";
			htmlStr += "<td>"+this.buldSlno+"</td>";
			htmlStr += "<td>"+this.mtYn+"</td>";
			htmlStr += "<td>"+this.lnbrMnnm+"</td>";
			htmlStr += "<td>"+this.lnbrSlno+"</td>";
			htmlStr += "<td>"+this.emdNo+"</td>";
			htmlStr += "</tr>";
		});
		
		htmlStr += "</table>";
		
		$("#list").html(htmlStr);
	}
	
	function fn_checkSearchedWord(obj) {
		return true ;
	}

	function fn_enterSearch() {
		
		console.log("call fn_enterSearch");
		
		var evt_code = (window.netscape) ? ev.which : event.keyCode;
		
		console.log(evt_code);
		
		if (evt_code == 8) {
			event.keyCode = 0;
		} else if (evt_code == 13) {
			event.keyCode = 0;  
			fn_getAddr(); 			
			
			return false;
		}
		
		return false;
		
	}	
	
	function fn_getAddr() {
		
		if (!fn_checkSearchedWord(document.formLayerPop.keyword)) {
			return;
		}

		var varKeyword = $("#keyword").val();
		
		var varCurrentPage  = $("#jusoCurrentPage").val();
		if ((varCurrentPage === undefined) || (varCurrentPage == null) || (varCurrentPage == '')) {
			varCurrentPage = "1";
			$("#jusoCurrentPage").val(varCurrentPage);
		}		
		
		var varCountPerPage  = $("#jusoCountPerPage").val();
		if ((varCountPerPage === undefined) || (varCountPerPage == null) || (varCountPerPage == '')) {
			varCountPerPage = "5";
			$("#jusoCountPerPage").val(varCountPerPage);
		}		
		
		var varJusoFormVal = $("#formLayerPop").serialize();  
		
		$.ajax({
			 url :"https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
			,type:"post"
			,data:varJusoFormVal
			,dataType:"jsonp"
			,crossDomain:true
			,success:function(data) {
				
				console.log(data);
				
				$("#list").html("");
				var errCode = data.results.common.errorCode;
				var errDesc = data.results.common.errorMessage;
				if (errCode != "0") {
					alert(errCode+"="+errDesc);
				} else {
					if (data != null) {
						fn_JusoCallBack(data);
					}
				}
				
			}
		    ,error: function(xhr,status, error) {
		    	alert("주소 정보를 가져올 수 없습니다.");
		    }
		});
	}
	
	$('#modalJusoSearch').on('show.bs.modal', function (e) {
		$("#jusoSearchResult").hide();
	})	

	function fn_closeJusoModal(){
		
		$("#modal-backdrop").hide();
		$("#modalJusoSearch").hide();
	}
	function fn_JusoCallBack(data) {

		var varCountPerPage = data.results.common.countPerPage;
		var varCurrentPage  = data.results.common.currentPage;
		var varErrorCode    = data.results.common.errorCode;
		var varErrorMessage = data.results.common.errorMessage;

		var varJusoRsltH = "<table>";
			varJusoRsltH += "	<caption>주소</caption>";
			varJusoRsltH += "<colgroup>";
			varJusoRsltH += "<col style='width:80%'>";
			varJusoRsltH += "<col style='width:20%'>";
			varJusoRsltH += "</colgroup>";
			varJusoRsltH += "<tbody>";

		var varJusoRsltB = "";

		var varJusoRsltT = "</tbody>";
		    varJusoRsltT += "</table>";

		if (varErrorCode == 0) {

			var varJuso = data.results.juso;

			var varZipNo     = "";
			var varJibunAddr = "";
			var varRoadAddr  = "";
			for(var i = 0; i < varJuso.length; i++) {

				varZipNo     = varJuso[i].zipNo;
				varJibunAddr = varJuso[i].jibunAddr;
				varRoadAddr  = varJuso[i].roadAddr;

				varJusoRsltB += "<tr>";
				varJusoRsltB += "	<td>";
				varJusoRsltB += "		<ul class='addr_list'>";
				varJusoRsltB += "			<li>";
				varJusoRsltB += "				<dl>";
				varJusoRsltB += "					<dt>도로명</dt>";
				varJusoRsltB += "					<dd class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varRoadAddr + "</a></dd>";
				varJusoRsltB += "				</dl>";
				varJusoRsltB += "			</li>";
				varJusoRsltB += "			<li>";
				varJusoRsltB += "				<dl>";
				varJusoRsltB += "					<dt>지번</dt>";
				varJusoRsltB += "					<dd><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varJibunAddr + "</a></dd>";
				varJusoRsltB += "				</dl>";
				varJusoRsltB += "			</li>";
				varJusoRsltB += "		</ul>";
				varJusoRsltB += "	</td>";
				varJusoRsltB += "	<td class='md'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "'); return false;\" data-dismiss='modal'>" + varZipNo + "</a></td>";
				varJusoRsltB += "</tr>";

			}

			if (varJuso.length >= 1) {
			} else {

				varJusoRsltB += "<tr>";
				varJusoRsltB += "	<td>";
				varJusoRsltB += "		주소 정보를 찾을 수 없습니다.";
				varJusoRsltB += "	</td>";
				varJusoRsltB += "	<td class='md'>" + varZipNo + "</td>";
				varJusoRsltB += "</tr>";

			}

			$("#jusoSearchResult").empty();
			$("#jusoPageApi").empty();
			$("#jusoSearchResult").show();
			$("#jusoSearchResult").append(varJusoRsltH + varJusoRsltB + varJusoRsltT);

			fn_PageMake(data);

		}
	}	
	
	function fn_PageMake(jsonStr) {

		var total = jsonStr.results.common.totalCount;

		var pageNum = Number($("#jusoCurrentPage").val());
		
		console.log(total);
		console.log(pageNum);
		
		var paggingStr = "";

		if (total < 1) {
		} else {

			var PAGEBLOCK = Number($("#jusoCountPerPage").val());
			var pageSize  = Number($("#jusoCountPerPage").val());

			var totalPages = Math.floor((total-1)/pageSize) + 1;
			var firstPage = Math.floor((pageNum-1)/PAGEBLOCK) * PAGEBLOCK + 1;

			if ( firstPage <= 0 ) firstPage = 1;
			var lastPage = firstPage-1 + PAGEBLOCK;

			if ( lastPage > totalPages ) lastPage = totalPages;

			var nextPage = lastPage + 1 ;
			var prePage = firstPage - 5 ;

			if ( firstPage > PAGEBLOCK ) {
				paggingStr +=  "<a href='javascript:fn_GoPage("+prePage+");'>이전</a>  " ;
			}

			if (totalPages >= 2) {
				for(i = firstPage; i <= lastPage; i++) {
					if (pageNum == i) {
						paggingStr += "<a style='font-weight:bold;color:blue;font-size:15px;' href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
					} else {
						paggingStr += "<a href='javascript:fn_GoPage("+i+");'>" + i + "</a>  ";
					}
				}
			}

			if (lastPage < totalPages) {
				paggingStr +=  "<a href='javascript:fn_GoPage(" + nextPage + ");'>다음</a>";
			}

			$("#jusoPageApi").html(paggingStr);

		}
	}	
	
	function fn_juso_layer_pop() {
		$('#modalJusoSearch').modal();
		$("#modal-backdrop").show();
		$('#modalJusoSearch').show();
	}
	
	function fn_jusoFSelect(zipCode, readAddr) {

		$("#postNum").empty();
		$("#addr1").empty();
		
		$("#jusoSearchResult").empty();
		$("#jusoPageApi").empty();
		$("#keyword").val("");

		$("#postNum").val(zipCode);
		$("#addr1").val(readAddr);

		$("#addr2").focus();

		return false;

	}	
	
	function fn_GoPage(pageNum) {

		$("#jusoCurrentPage").val(pageNum);
		$("#currentPage").val(pageNum);
		$('#searchJuso').trigger('click');

	}	
	
</script>

  <div class="modal fade show" id="modalJusoSearch" tabindex="-1" aria-labelledby="modalAllmenuLabel" aria-hidden="true">
    <div class="modal-dialog searchMethod-inner">
      <div class="modal-content searchMethod-wrap">
        <div class="modal-header searchMethod-header">
          <h5 class="modal-title">우편번호 찾기</h5>
          <button type="button" class="modal-close btn-close" onclick="fn_closeJusoModal()"  data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
		<div class="search_wrap w100">
				<form name="formLayerPop" id="formLayerPop" method="post">

					<input type="hidden" name="currentPage"  id="currentPage"  value="1"/> 
					<input type="hidden" name="countPerPage" id="countPerPage" value="5"/>
					<input type="hidden" name="resultType"   value="json"/>  
					<input type="hidden" name="confmKey"     value="<%=strJusoKey%>"/>
					<div id="list"></div>				
				
					<fieldset>
						<legend>주소 검색</legend>
						<div class="search_box">
							<label for="keyword">검색어 : </label>
							<input id="keyword" name="keyword" title="검색어를 입력하세요" type="text" class="input_text" placeholder="예) 올림픽로 300">
							<button type="button" id="searchJuso" onClick="fn_getAddr(); return false;" class="button"><span>검색</span></button>

							<inut id="jusoCurrentPage"  type="hidden">
							<inut id="jusoCountPerPage" type="hidden">

						</div>
					</fieldset>
				</form>
			</div>        
        
        <div class="modal-body searchMethod-body">
          <div class="searchMethod-content" style="height:450px !important">
				<div class="table2 ty scroll" id="jusoSearchResult"></div>
				<div class="paginate" id="jusoPageApi"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
<div class="modal fade show" id="modalJusoSearch2" tabindex="-1" aria-labelledby="modalAllmenuLabel" style="display: none;" aria-modal="true" role="dialog">
    <div class="modal-dialog" style="max-width:760px;">
      <div class="modal-content pb50">
        <div class="modal-header nbB">
        	<h5 class="modal-title">우편번호 찾기</h5>
        	
          <button type="button" onclick="fn_closeJusoModal()" class="modal-close btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body searchMethod-body">
          					<div class="searchMethod-content">

						<div class="search_wrap w100">
								<form name="formLayerPop" id="formLayerPop" method="post">

									<input type="hidden" name="currentPage"  id="currentPage"  value="1"/> 
									<input type="hidden" name="countPerPage" id="countPerPage" value="5"/>
									<input type="hidden" name="resultType"   value="json"/>  
									<input type="hidden" name="confmKey"     value="<%=strJusoKey%>"/>
									<div id="list"></div>				
								
									<fieldset>
										<legend>주소 검색</legend>
										<div class="search_box">
											<label for="keyword">검색어 : </label>
											<input id="keyword" name="keyword" title="검색어를 입력하세요" type="text" class="input_text" placeholder="예) 올림픽로 300">
											<button type="button" id="searchJuso" onClick="fn_getAddr(); return false;" class="button"><span>검색</span></button>

											<inut id="jusoCurrentPage"  type="hidden">
											<inut id="jusoCountPerPage" type="hidden">

										</div>
									</fieldset>
								</form>
							</div>

								<div class="table2 ty scroll" id="jusoSearchResult"></div>
								<div class="paginate" id="jusoPageApi"></div>
							</div>

        </div>
      </div>
    </div>
  </div>


		<div class="modal fade show" id="modalJusoSearch11" tabindex="-1" aria-labelledby="modalAllmenuLabel" style="display: none;" aria-modal="true" role="dialog">

				<div class="modal-dialog" style="max-width:760px;">

					<div class="modal-content">
						<div class="modal-title">
							<h4>우편번호 찾기</h4>
							<a href="javascript:fn_closeJusoModal();" class="pop_close" data-dismiss="modal">닫기</a>
						</div>
						<div class="modal-body">

							<div class="search_wrap w100">
								<form name="formLayerPop" id="formLayerPop" method="post">

									<input type="hidden" name="currentPage"  id="currentPage"  value="1"/> 
									<input type="hidden" name="countPerPage" id="countPerPage" value="5"/>
									<input type="hidden" name="resultType"   value="json"/>  
									<input type="hidden" name="confmKey"     value="<%=strJusoKey%>"/>
									<div id="list"></div>				
								
									<fieldset>
										<legend>주소 검색</legend>
										<div class="search_box">
											<label for="keyword">검색어 : </label>
											<input id="keyword" name="keyword" title="검색어를 입력하세요" type="text" class="input_text" placeholder="예) 올림픽로 300">
											<button type="button" id="searchJuso" onClick="fn_getAddr(); return false;" class="button"><span>검색</span></button>

											<inut id="jusoCurrentPage"  type="hidden">
											<inut id="jusoCountPerPage" type="hidden">

										</div>
									</fieldset>
								</form>
							</div>

							<div class="table2 ty scroll" id="jusoSearchResult">
							</div>
							<div class="paginate" id="jusoPageApi">
							</div>

						</div>
					</div>

				</div>
			</div>
			<!-- //우편번호 찾기 -->
			<!-- //팝업 -->

  <div id="modal-backdrop" class="modal-backdrop fade show" style="display:none;"></div>
  
  