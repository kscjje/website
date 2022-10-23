<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

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
                <li class="accredit-item on">
                  <p>02</p>
                  약관동의
                </li>
                <li class="accredit-item">
                  <p>03</p>
                  본인인증
                </li>

                <li class="accredit-item">
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
                  <td colspan="4" class="w-100">
                    <div class="input-group re-agree">
                      <input type="checkbox" name="all_check" id="all_check" value="Y" onclick="$(this).parents('form').find('input.agree_input').prop('checked', $(this).prop('checked'))" class="inpChk">
                      <label for="all_check">회원정보 보유 재동의 약관 전체 동의합니다.</label>
                    </div>
                  </td>
                  </tr>
                  <tr>
                    <td colspan="4" class="w-100">
                      <ul class="myinfo-list">
                        <li class="myinfo-item">
                          <div class=" input-group re-agree">
                            <input type="checkbox" id="inp09_03_02" class="inp">
                            <label for="inp09_03_02">이용약관</label>
                          </div>
                          <div class="myinfo-con">
                            <strong>제1장 총칙</strong>
                            <br>
                            제1조
                            <br>
                            목적본 약관은 남양주시청이 제공하는 웹메일 서비스의 이용조건 및 절차에 관한 사항과 기타 필요한 사항을 전기통신사업법 및 동법 시행령이 하는 대로 준수하고 규정함을
                            그
                            <br>
                            목적으로 한다.
                            <br>
                            <br>
                            제2조(이용약관의 효력 및 변경)
                            <br>
                            본 약관은 서비스 메뉴 및 남양주시청에 게시하여 공시함으로써 그 효력을 발생하고 남양주시청은 정당한 사유가 발생될 경우에는 약관을 수정, 변경할 수 있으며 약관이
                            변경된
                            <br>
                            경우에는 이를 공시의 절차를 거쳐 효력을 발생할 수 있습니다.
                            <br>
                            <br>
                            제3조 (약관 외 준칙)
                            <br>
                            본 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법 및 기타 관련법령의 규정을 따릅니다.
                            <br>
                            <br>
                            <strong>제2장 서비스 이용계약</strong>
                            <br>
                            제1조(이용 계약의 성립)
                            <br>
                            (1) 이용 계약은 이용자의 이용 신청에 대한 남양주시청의 이용 승낙과 이용자의 약관 내용에 대한 동의로 성립됩니다
                            <br>
                            (2) 이용계약은 회원의 이용신청에 대하여 남양주시청이 승낙함으로써 성립합니다.
                            <br>
                            (3) 회원에 가입하여 서비스를 이용하고자 하는 희망자는 남양주시청에서 요청하는 개인 신상정보를 제공해야 합니다.
                            <br>
                            (4) 이용자의 이용신청에 대하여 남양주시청이 이를 승낙한 경우, 남양주시청은 회원 ID와 기타 남양주시청이 필요하다고 인정하는 내용을 이용자에게 통지합니다.
                            <br>
                            (5) 남양주시청은 다음 각 호에 해당하는 이용계약 신청에 대하여는 이를 인정하지 아니 합니다.
                            <br>
                            가. 다른 사람의 명의를 사용하여 신청하였을 때(주민등록번호 도용 등)
                            <br>
                            나. 본인의 실명으로 신청하지 않았을 때
                            <br>
                            다. 가입 신청서의 내용을 허위로 기재하였을 때
                            <br>
                            라. 국가의 질서 혹은 미풍양속을 저해할 목적으로 신청하였다고 판단되어질 때
                            <br>
                            <br>
                            제2조 서비스 이용 및 제한
                            <br>
                            (1) 서비스 이용은 남양주시청의 업무상 또는 기술상 특별한 지장이 없는 한 연중무휴, 1일 24시간을 원칙으로 합니다.
                            <br>
                            (2) 남양주시청이 필요하다고 인정될 경우(시스템 정기점검 등) 회원에게 사전 통지한 후, 제한할 수 있습니다.
                            <br>
                            <br>
                            <strong>제3장 의무</strong>
                            <br>
                            제1조 남양주시청의 의무
                            <br>
                            (1) 남양주시청은 특별한 사정이 없는 한 회원이 신청한 서비스 제공 개시일에 서비스를 이용할 수 있도록 합니다.
                            <br>
                            (2) 남양주시청은 이 약관에서 정한 바에 따라 계속적, 안정적으로 서비스를 제공할 의무가 있습니다.
                            <br>
                            (3) 남양주시청은 회원으로부터 소정의 절차에 의해 제기되는 의견에 대해서는 적절한 절차를 거처 처리하며, 처리시 그 처리 내용과 결과 그리고 처리 일정을 알려주어야
                            <br>
                            합니다.
                            <br>
                            (4) 남양주시청은 회원의 정보를 철저히 보안 유지하며, 양질의 서비스를 운영하거나 개선하는 데에만 사용하고, 이외의 다른 목적으로 타 기관 및 개인에게 양도하지
                            않습니다.
                            <br>
                            <br>
                            제2조 회원의 의무
                            <br>
                            (1) ID와 비밀번호에 관한 모든 관리의 책임은 회원에게 있습니다. (관리 부실로 일어난 피해에 남양주시청은 책임을 지지 않습니다)
                            <br>
                            (2) 회원은 서비스의 일부로 보내지는 남양주시청의 전자우편을 받는 것에 동의합니다.
                            <br>
                            (3) 자신의 ID가 부정하게 사용된 경우나 주민등록번호가 도용당한 경우 회원은 반드시 남양주시청에 그 사실을 통보해야 합니다.
                            <br>
                            (4) 회원은 이 약관 및 관계 법령에서 규정한 사항을 준수하여야 합니다.
                            <br>
                            <br>
                            <strong>제4장 계약 해지 및 서비스 이용 제한</strong>
                            <br>
                            제1조 계약 해지 및 이용제한
                            <br>
                            (1) 회원이 이용 계약을 해지하고자 하는 때에는 회원 본인이 직접 로그인하여 마이페이지-개인정보수정-회원탈퇴 버튼을 이용하면 됩니다.
                            <br>
                            (2) 가입해지 여부는 기존의 ID, 비밀번호로 로그인이 되지 않으면 해지된 것입니다.
                            <br>
                            (3) 남양주시청은 회원이 다음 사항에 해당하는 행위를 하였을 경우, 사전 통지 없이 이용 계약을 해지하거나 또는 기간을 정하여 서비스 이용을 중지할 수 있습니다.
                            <br>
                            가. 공공 질서 및 미풍 양속에 반하는 경우
                            <br>
                            나. 범죄적 행위에 관련되는 경우
                            <br>
                            다. 국익 또는 사회적 공익을 저해할 목적으로 서비스 이용을 계획 또는 실행할 경우
                            <br>
                            라. 타인의 ID 및 비밀번호를 도용한 경우
                            <br>
                            마. 타인의 명예를 손상시키거나 불이익을 주는 경우
                            <br>
                            바. 같은 사용자가 다른 ID로 이중 등록을 한 경우
                            <br>
                            사. 서비스에 위해를 가하는 등 건전한 이용을 저해하는 경우
                            <br>
                            아. 기타 관련 법령이나 남양주시청이 정한 이용조건에 위배되는 경우
                            <br>
                            <br>
                            제2조 이용 제한의 해제 절차
                            <br>
                            (1) 남양주시청은 이용 제한을 하고자 하는 경우에는 그 사유, 일시 및 기간을 정하여 서면 또는 전화 등의 방법을 이용하여 해당 회원 또는 대리인에게 통지합니다.
                            <br>
                            (2) 다만, 남양주시청이 긴급하게 이용을 중지해야 할 필요가 있다고 인정하는 경우에는 전항의 과정없이 서비스 이용을 제한할 수 있습니다.
                            <br>
                            (3) 제4장 제2조 1항의 규정에 의하여 서비스 이용중지를 통지 받은 회원 또는 그 대리인은 이용 중지에 대하여 이의가 있을 경우 이의신청을 할 수 있습니다.
                            <br>
                            (4) 남양주시청은 이용중지 기간 중에 그 이용중지 사유가 해소된 것이 확인된 경우에 한하여 이용중지 조치를 즉시 해제합니다.
                            <br>
                            <br>
                            제3조 회원의 게시물 권리
                            <br>
                            남양주시청은 회원이 게시하거나 등록한 내용물이 다음 사항에 해당된다고 판단되는 경우에 사전 통지 없이 삭제할 수 있습니다.
                            <br>
                            (1) 타인을 비방하거나 중상모략으로 개인 및 단체의 명예를 손상시키는 내용인 경우
                            <br>
                            (2) 공공질서 및 미풍양속에 위반되는 내용인 경우
                            <br>
                            (3) 범죄적 행위에 부합된다고 인정되는 내용인 경우
                            <br>
                            (4) 타인의 저작권 등 기타의 권리를 침해하는 내용인 경우
                            <br>
                            (5) 기타 관계 법령이나 남양주시청에서 정한 규정에 위배되는 내용인 경우
                            <br>
                            <br>
                            -부칙-
                            <br>
                            본 약관은 2004년 9월 1일부터 시행합니다.
                          </div>
                        </li>
                        <li class="myinfo-item">
                          <div class=" input-group re-agree">
                            <input type="checkbox" id="inp09_03_03" class="inp">
                            <label for="inp09_03_03">개인정보 수집 및 이용안내</label>
                          </div>
                          <div class="myinfo-con">
                            남양주시는 회원님의 개인정보를 중요시하며, 남양주시가 취급하는 모든 개인정보는 관련법령에 근거할 뿐 아니라 정보주체의 동의에 의하여 수집·보유 및 처리되고 있습니다.
                            <br>
                            남양주시 대표 홈페이지는 회원가입, 상담, 서비스 신청 등을 위해 아래와 같은 개인정보를 수집하고 있습니다.
                            <br>
                            <br>
                            1. 개인정보의 수집·이용 목적
                            <br>
                            남양주시는 개인정보 수집 시 사용목적을 정확히 밝히고 있으며, 수집되는 개인정보는 아래와 같은 목적으로 사용합니다.
                            <br>
                            - 성명, 아이디, 비밀번호 : 서비스 이용에 따른 본인식별을 위한 목적
                            <br>
                            - 주소, 이메일, 휴대전화번호 : 고지사항 전달, 본인의사 확인, 불만처리 등 원활한 의사소통 경로를 확보하기 위한 목적
                            <br>
                            <br>
                            2. 수집하는 개인정보 항목
                            <br>
                            - 필수항목 : 성명, 아이디, 비밀번호, 이메일, 주소, 휴대전화번호, 실명인증정보(개인식별번호, 14세 미만은 법정대리인 개인식별번호)
                            <br>
                            - 컴퓨터에 의해 자동으로 수집되는 정보 : 접속 로그, 쿠키, 접속 IP 정보, 가입경로
                            <br>
                            <br>
                            3. 개인정보 수집방법
                            <br>
                            - 홈페이지 회원가입을 통한 수집
                            <br>
                            <br>
                            4. 개인정보의 보유·이용기간 및 파기
                            <br>
                            - 보존이유 : 정보통신망 이용촉진 및 정보보호 등에 관한 법률
                            <br>
                            - 보존기간 : 회원가입 ~ 회원탈퇴시까지
                            <br>
                            - 파기 : 회원 탈퇴를 요청하거나 수집/이용목적을 달성하거나 보유/ 이용기간이 종료한 경우, 사업 폐지 등의 사유발생시 당해 개인정보를 지체없이 파기합니다.
                            <br>
                            <br>
                            주의 : 홈페이지 게시판에 내용 게재시 주민등록번호, 계좌번호 등 민감한 개인정보가 포함되어 있는 경우 개인정보를 삭제 조치 후 게시하여야 합니다.
                            <br>
                            <br>
                            정보주체는 개인정보의 수집·이용목적에 대한 동의를 거부할 수 있으며, 동의 거부시 남양주시 홈페이지에 회원가입이 되지 않으며, 남양주시 홈페이지에서 제공하는 게시판
                            글작성
                            <br>
                            등의 서비스를 이용할 수 없습니다.
                          </div>
                        </li>
                      </ul>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div class="badge-btn">
          <a href="/" class="gray">취소</a>
          <a href="javascript:goApplyNextStep();" class="green">다음</a>
        </div>



      </div>

  </div>
  </main>
<form id="sendForm" name="sendForm" action="./joinStep3" method="post">
	<input type="hidden" name="termYn" id="termYn"/>
	<input type="hidden" name="useYn" id="useYn"/>
	<input type="hidden" name="type" value="<c:out value="${param.type}"/>"/>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>	
  
  
  <script>
  function goApplyNextStep(){
	  
	var form = document.sendForm;
	if(!$('#inp09_03_02').prop("checked"))
	{
		alert('"이용약관"에 동의해야합니다.');
		$('#inp09_03_02').focus();
		return false;
	}
	
	if(!$('#inp09_03_03').prop("checked"))
	{
		alert('"개인정보 수집 및 이용안내"에 동의해야합니다.');
		$('#inp09_03_03').focus();
		return false;
	}
	
	form.useYn.value='Y';
	form.termYn.value='Y';
	form.submit();
  }
  </script>