<%
 /**
  * @Class Name : policy4.jsp
  * @Description : 개인정보 처리방침 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.10.27    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.10.27
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
	<h4 class="tit2">제1조(개인정보 처리목적)</h4>
							<ol class="mb30">
								<li>
									<p>1. 노원수학문화관은 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 「개인정보보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.</p>
									<ol>
										<li class="num_p">(1) 홈페이지 회원 가입 및 관리<br> 회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 만 14세 미만 아동의 개인정보 처리시 법정대리인의 동의여부 확인, 각종 고지·통지, 고충처리 등을 목적으로 개인정보를 처리합니다.</li>
										<li class="num_p">(2) 민원사무 처리<br> 민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등의 목적으로 개인정보를 처리합니다.</li>
									</ol>
								</li>
								<li>
									<p>2. 노원수학문화관이「개인정보보호법」제32조에 따라 등록·공개하는 개인정보파일의 처리목적은 다음과 같습니다.</p>
								</li>
							</ol>
							<!-- table -->
							<div class="table3 mb15">
								<table>
									<caption>개인정보처리방침 설명하는 표입니다.-순번, 개인정보파일명칭, 운영근거처리목적, 개인정보항목, 보유기간</caption>
									<colgroup>
										<col style="width:5%">
										<col style="width:25%">
										<col style="width:30%">
										<col style="*">
										<col style="width:15%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">개인정보파일 명칭</th>
											<th scope="col">운영근거/처리목적</th>
											<th scope="col">개인정보파일에 기록되는<br> 개인정보의 항목</th>
											<th scope="col">보유기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">노원수학문화관 홈페이지 회원명부</td>
											<td class="txt_left">정보주체동의(2년주기)/홈페이지 회원 관리</td>
											<td class="txt_left">이름:필수, 집주소, E-Mail:필수, 핸드폰(연락처):필수, 생년월일:필수</td>
											<td>회원탈퇴시까지(2년)</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">노원수학문화관 특별회원 명부 </td>
											<td class="txt_left">노원수학문화관 유료회원규정 제6조/노원수학문화관 회원제운영</td>
											<td class="txt_left">이름:필수, 집주소:필수, E-Mail:필수, 집연락처, 핸드폰(연락처):필수, 생년월일:필수, 기타 가족 성명, 생년월일</td>
											<td>탈퇴시까지</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">노원수학문화관 유료회원 명부 </td>
											<td class="txt_left">노원수학문화관 회원제운영관리규정/노원수학문화관 회원제를 운영하기 위함</td>
											<td class="txt_left">이름:필수, 집주소:필수, E-Mail:필수, 집연락처, 핸드폰(연락처):필수, 생년월일:필수</td>
											<td>1년</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">전국학생과학별명품경진대회수상자목록</td>
											<td class="txt_left">전국학생발명품경진대회 수상자 목록/수상자를 수상하고 입시에서 사용될 수 있는 학교제공자료로 사용하기 위함</td>
											<td class="txt_left">이름, E-Mail, 생년월일, 기타 수상등급</td>
											<td>영구</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">전국과학전람회수상자목록 </td>
											<td class="txt_left">전국과학전람회 규칙/과학전람회에서 입상한 수상자에게 상장을 부여하고 입시에서 사용토록 하기위함</td>
											<td class="txt_left">이름, E-Mail, 생년월일 성명, 소속, 수상등급</td>
											<td>영구</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">노원수학문화관 자원봉사자 지원서</td>
											<td class="txt_left">자원봉사자 운영규정</td>
											<td class="txt_left">이름, 집주소, 핸드폰(연락처), 생년월일</td>
											<td>1년</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<p class="tb_txt fn mb55">※ 기타 노원수학문화관의 개인정보파일 등록사항 공개는 행정자치부 개인정보보호 종합지원포털(www.privacy.go.kr) → 개인정보민원 → 개인정보 열람 등 요구 → 개인정보파일 목록검색 메뉴를 활용해 주시기 바랍니다.</p>
							<!-- //제1조 -->
							<!-- 제2조 -->
							<h4 class="tit2">제2조(개인정보 처리 및 보유기간)</h4>
							<ol class="mb30">
								<li>
									<p>1. 노원수학문화관은 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.</p>
								</li>
								<li>
									<p>2. 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</p>
									<ul class="mb55">
										<li>
											<p class="dot">홈페이지 회원 가입 및 관리 : 홈페이지 탈퇴시까지(2년이내 미로그인 회원은 공고 후계정 삭제)다만, 다음의 사유에 해당하는 경우에는 해당 사유 종료시까지</p>
											<ul class="sub_p_list">
												<li class="sub_p">- 관계 법령 위반에 따른 수사·조사 등이 진행중인 경우에는 해당 수사·조사 종료시까지</li>
												<li class="sub_p">- 홈페이지 이용에 다른 채권·채무관계 잔존시에는 해당 채권·채무관계 정산까지</li>
												<li class="sub_p">- 예외사유 시에는 보유기간 까지</li>
											</ul>
											<p class="dot">「정보통신망 이용촉진 및 정보보호 등에 관한 법률」시행령 제29조에 따른 본인확인정보 보관 : 게시판에 정보 게시가 종료된 후 6개월</p>
										</li>
									</ul>
								</li>
							</ol>
							<!-- //제2조 -->
							<!-- 제3조 -->
							<h4 class="tit2">제3조(개인정보의 제3자 제공)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 정보주체의 동의, 법률의 특별한 규정 등 개인정보보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p>
								</li>
								<li>
									<p>2. 노원수학문화관은 개인정보 열람, 정정·삭제, 처리정지 요구 민원을 처리하기 위하여 민원 신청인의 개인정보를 개인정보파일 보유기관이 열람 할 수 있도록 하고 있습니다.<br>
										[개인정보 열람, 정정·삭제, 처리정지 요구 사용자 정보]<br>
										민원인이 신청한 공공기관에 개인정보를 제공하고 있으며, 접수된 민원이 처리완료되는 시점까지 보유 및 이용하고 있습니다.<br>
										제공하는 개인정보 항목: 신청인 성명, 생년월일, 전화번호, 주소</p>
								</li>
							</ol>
							<!-- //제3조 -->
							<!-- 제4조 -->
							<h4 class="tit2">제4조(개인정보처리 위탁)</h4>
							<ol class="mb55">
								<li>
									<p class="mb30">1. 노원수학문화관은 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</p>
									<!-- table -->
									<div class="table3 mb15">
										<table>
											<caption>개인정보처리의 위탁을 설명하는 표입니다.-순번, 부서명, 업무명, 업체명, 위탁기간, 업무내용</caption>
											<colgroup>
												<col style="width:5%">
												<col style="*">
												<col style="*">
												<col style="*">
												<col style="*">
											</colgroup>
											<thead>
												<tr>
													<th scope="col">순번</th>
													<th scope="col">부서명</th>
													<th scope="col">업무명</th>
													<th scope="col">업체명</th>
													<th scope="col">위탁기간</th>
													<th scope="col">업무내용</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>운영지원과</td>
													<td>홈페이지 위탁사업	</td>
													<td>(주)연무기술 </td>
													<td>2018. 1. 1.~12. 31.</td>
													<td>노원수학문화관 정보시스템 운영</td>
												</tr>
												<tr>
													<td>2</td>
													<td>시설공간과 </td>
													<td>CCTV 시스템 위탁사업	</td>
													<td>(주)제이에스엔텍</td>
													<td>2018. 1. 1.~12. 31.</td>
													<td>CCTV 운영</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- //table -->
								</li>
								<li>
									<p>2. 노원수학문화관은 위탁계약 체결시 「개인정보보호법」 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</p>
									<p>3. 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체 없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</p>
								</li>
							</ol>
							<!-- //제4조 -->
							<!-- 제5조 -->
							<h4 class="tit2">제5조(정보주체 권리·의무 및 그 행사방법에 관한 사항)</h4>
							<p class="txt">정보주체(만 14세 미만인 경우에는 법정대리인을 말함)는 언제든지 다음 각 호의 개인정보보호 관련 권리를 행사할 수 있습니다.</p>
							<ol class="mb55">
								<li><p>1. 개인정보 열람 요구</p></li>
								<li><p>2. 오류 등이 있을 경우 정정 요구</p></li>
								<li><p>3. 삭제 요구</p></li>
								<li><p>4. 처리 정지요구<br>※ 노원수학문화관 회원의 개인정보 정정·삭제·탈퇴는 '로그인 – 마이페이지'에서 가능합니다.</p></li>
								<li>
									<p>5. 제1항에 따른 권리 행사는 노원수학문화관에 대해 개인정보보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며, 노원수학문화관은 이에 대해 지체 없이 조치하겠습니다.</p>
								</li>
								<li>
									<p>6. 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 노원수학문화관은 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</p>
								</li>
								<li>
									<p>7. 제1항에 따른 권리 행사는 정보주체의 법정대리인의 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보보호법 시행규칙 별지 제11호 서식에 따라 위임장을 제출하셔야 합니다.</p>
								</li>
								<li>
									<p>8. 개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제5항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.</p>
								</li>
								<li>
									<p>9. 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</p>
								</li>
								<li>
									<p>10. 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.</p>
									<ul class="dot_list2">
										<li class="dot">[개인정보보호법 시행규칙 별지 제8호] 개인정보(열람, 정정·삭제, 처리정지) 요구서 <a href="<%=strContext%>/download/%5B%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EB%B3%B4%ED%98%B8%EB%B2%95%20%EC%8B%9C%ED%96%89%EA%B7%9C%EC%B9%99%20%EB%B3%84%EC%A7%80%20%EC%A0%9C8%ED%98%B8%5D%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4(%EC%97%B4%EB%9E%8C,%20%EC%A0%95%EC%A0%95%C2%B7%EC%82%AD%EC%A0%9C,%20%EC%B2%98%EB%A6%AC%EC%A0%95%EC%A7%80)%20%EC%9A%94%EA%B5%AC%EC%84%9C.hwp" class="btn_ty_s_c2 down">다운로드</a></li>
										<li class="dot">[개인정보보호법 시행규칙 별지 제11호] 위임장 <a href="<%=strContext%>/download/%5B%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EB%B3%B4%ED%98%B8%EB%B2%95%20%EC%8B%9C%ED%96%89%EA%B7%9C%EC%B9%99%20%EB%B3%84%EC%A7%80%20%EC%A0%9C11%ED%98%B8%5D%20%EC%9C%84%EC%9E%84%EC%9E%A5.hwp" class="btn_ty_s_c2 down">다운로드</a></li>
									</ul>
								</li>
								<li><p>11. 정보주체는 노원수학문화관의 열람청구 접수·처리부서(하단, 제9조 개인정보보호(분야별)책임자 및 담당자 연락처 참조) 이외에 행정자치부의 ‘개인정보보호 종합지원포털(<a href="http://www.privacy.go.kr" target="_blank">http://www.privacy.go.kr</a>)’을 통해 개인정보 열람청구를 하실 수 있습니다.</p></li>
							</ol>
							<!-- //제5조 -->
							<!-- 제6조 -->
							<h4 class="tit2">제6조(처리하는 개인정보 항목)</h4>
							<p class="txt mb30">노원수학문화관은 다음의 개인정보 항목을 처리하고 있습니다.</p>
							<ol class="mb55">
								<li>
									<p>1. 홈페이지 회원가입 및 관리</p>
									<ul class="dot_list2">
										<li class="dot">유료회원: 성명, 생년월일, 아이디, 비밀번호, 이메일, 핸드폰, 집주소</li>
										<li class="dot">무료회원: 성명, 생년월일, 아이디, 비밀번호, 이메일, 핸드폰</li>
									</ul>
								</li>
								<li>
									<p>2. 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다.(자동으로 생성·수집되는 개인정보 항목이 있는 경우)</p>
									<ul class="dot_list2">
										<li class="dot">IP주소, 쿠키, MAC주소, 서비스 이용기록, 방문기록, 불량 이용기록 등</li>
									</ul>
								</li>
							</ol>
							<!-- //제6조 -->
							<!-- 제7조 -->
							<h4 class="tit2">제7조(개인정보 파기 절차 및 방법)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체 없이 해당 개인정보를 파기합니다.</p>
								</li>
								<li>
									<p>2. 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는 해당 개인정보(또는 개인정보파일)을 별도의 데이터베이스로 옮기거나 보관장소를 달리하여 보존합니다.</p>
								</li>
								<li>
									<p class="num_p">(1) 파기절차</p>
									<ul class="dot_list">
										<li>노원수학문화관은 파기하여야 하는 개인정보(또는 개인정보파일)에 대해 개인정보 파기계획을 수립하여 파기합니다. 노원수학문화관은 파기사유가 발생한 개인정보(또는 개인정보파일)을 선정하고, 노원수학문화관의 개인정보보호책임자의 승인을 받아 개인정보(또는 개인정보파일)를 파기합니다.</li>
										<li class="dot">개인정보 파기 : 보유기간이 경과한 개인정보는 종료일로부터 지체없이 파기합니다.</li>
										<li class="dot">개인정보파일 파기 : 개인정보파일의 처리목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보파일이 불필요하게 되었을때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 지체없이 그 개인정보파일을 파기합니다.</li>
									</ul>
								</li>
								<li>
									<p class="num_p">(2) 파기방법</p>
									<ul class="dot_list">
										<li>노원수학문화관은 전자적 파일 형태로 기록·저장된 개인정보는 기록을 재생할 수 없도록 파기하며, 종이 문서에 기록·저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.</li>
									</ul>
								</li>
							</ol>
							<!-- //제7조 -->
							<!-- 제8조 -->
							<h4 class="tit2">제8조(개인정보의 안전성 확보조치)</h4>
							<p class="txt">노원수학문화관은 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적, 관리적, 물리적 조치를 하고 있습니다.</p>
							<ul class="dot_list2 ty mb55">
								<li class="dot">내부관리계획 수립·시행<br>노원수학문화관의 내부관리계획 수립 및 시행은 행정자치부의 내부관리 지침을 준수하여 시행합니다.</li>
								<li class="dot">개인정보 취급 담당자의 최소화 및 교육<br>개인정보를 취급하는 담당자를 지정하고 최소화하여 전직원 및 개인정보 위탁직원에 대한 정기적 교육을 시행하고 있습니다.</li>
								<li class="dot">개인정보에 대한 접근 제한<br>개인정보를 처리하는 데이터베이스 시스템에 대한 접근권한의 부여, 변경, 말소를 통하여 개인정보에 대한 접근통제를위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</li>
								<li class="dot">접속기록의 보관 및 위변조 방지<br>개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안기능을 사용하고 있습니다.</li>
								<li class="dot">개인정보의 암호화<br>이용자의 개인정보는 암호화 되어 저장 및 관리되고 있습니다. 또한 중요한 데이터는 저장 및 전송시 암호화하여 사용하는 등의 별도 보안기능을 사용하고 있습니다.</li>
								<li class="dot">해킹 등에 대비한 기술적 대책<br>노원수학문화관은 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신 점검을 하고 있습니다.</li>
								<li class="dot">문서보안<br>개인정보가 포함된 서류는 잠금장치가 있는 캐비닛에 보관하여 관리하고 있습니다.</li>
								<li class="dot">비인가자에 대한 출입 통제<br>개인정보를 보관하고 있는 개인정보시스템을 외부로부터 접근이 통제된 구역에 장소를 별도로 두고 이에 대해 출입통제 절차를 수립·운영하고 있습니다.</li>
							</ul>
							<!-- //제8조 -->
							<!-- 제9조 -->
							<h4 class="tit2">제9조(개인정보보호 책임자)</h4>
							<p class="txt">노원수학문화관은 개인정보 처리에 관한 업무를 총괄해서 책임지고 개인정보처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자 및 실무담당자를 지정하고 있습니다. 노원수학문화관은 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.</p>
							<ul class="dot_list2 ty mb30">
								<li class="dot">개인정보보호 책임자: 관람총괄과 이상명 042-601-7905, smlee0988@korea.kr</li>
								<li class="dot">개인정보보호 담당자: 관람총괄과 이인영 042-601-7975, scharffe@korea.kr</li>
								<li class="dot">개인정보파일 열람청구 담당부서 및 담당자</li>
							</ul>
									<div class="table3 mb55">
										<table>
											<caption>개인정보열람청구를 설명하는 표입니다.-순번, 개인정보파일명칭, 분야별책임관, 담당자정보</caption>
											<colgroup>
												<col style="width:5%">
												<col style="width:30%">
												<col style="width:12%">
												<col style="*">
												<col style="*">
												<col style="width:14%">
												<col style="width:20%">
											</colgroup>
											<thead>
												<tr>
													<th rowspan="2" scope="col">순번</th>
													<th rowspan="2" scope="col">개인정보파일 명칭</th>
													<th colspan="2" scope="col">분야별 책임관</th>
													<th colspan="3" scope="col">담당자</th>
												</tr>
												<tr>
													<th scope="col">부서명</th>
													<th scope="col">이름</th>
													<th scope="col">이름</th>
													<th scope="col">연락처</th>
													<th scope="col">이메일</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>노원수학문화관 홈페이지 회원명부</td>
													<td>관람총괄과</td>
													<td>이상명</td>
													<td>엄주용</td>
													<td>042-601-7867</td>
													<td>eomjooy@korea.kr</td>
												</tr>
												<tr>
													<td>2</td>
													<td>노원수학문화관 특별회원 명부</td>
													<td>관람총괄과</td>
													<td>이상명</td>
													<td>엄주용</td>
													<td>042-601-7867</td>
													<td>eomjooy@korea.kr</td>
												</tr>
												<tr>
													<td>3</td>
													<td>노원수학문화관 유료회원 명부</td>
													<td>관람총괄과</td>
													<td>이상명</td>
													<td>엄주용</td>
													<td>042-601-7867</td>
													<td>eomjooy@korea.kr</td>
												</tr>
												<tr>
													<td>4</td>
													<td>전국학생과학별명품경진대회수상자목록</td>
													<td>과학교육과</td>
													<td>정기상</td>
													<td>국향희</td>
													<td>042-601-7736</td>
													<td>incense@korea.kr</td>
												</tr>
												<tr>
													<td>5</td>
													<td>전국과학전람회수상자목록</td>
													<td>과학교육과</td>
													<td>정기상</td>
													<td>정동균</td>
													<td>042-601-7737</td>
													<td>kimds21@korea.kr</td>
												</tr>
												<tr>
													<td>6</td>
													<td>노원수학문화관 자원봉사자 지원서</td>
													<td>관람총괄과</td>
													<td>이상명</td>
													<td>유인지</td>
													<td>042-601-7912</td>
													<td>inji1212@korea.kr</td>
												</tr>
											</tbody>
										</table>
									</div>
							<!-- //제9조 -->
							<!-- 제10조 -->
							<h4 class="tit2">제10조(권익침해 구제방법)</h4>
							<div class="ex_txt mb15">
								<p><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.<br> 아래의 기관은 노원수학문화관과는 별개의 기관으로서 노원수학문화관의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다.</p>
							</div>
							<ul class="mb30">
								<li>
									<p class="dot">개인정보 침해신고센터(한국인터넷진흥원 운영)</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 소관업무 : 개인정보 침해사실 신고, 상담신청</li>
										<li class="sub_p">- 홈페이지 : <a href="http://privacy.kisa.or.kr" target="_blank">http://privacy.kisa.or.kr</a></li>
										<li class="sub_p">- 전화 : (국번 없이) 118</li>
										<li class="sub_p">- 주소 : (138-950) 서울시 송파구 중대로 135 한국인터넷진흥원 개인정보침해신고센터</li>
									</ul>
								</li>
								<li>
									<p class="dot">개인정보 분쟁조정위원회(한국인터넷진흥원 운영)</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정(민사적 해결)</li>
										<li class="sub_p">- 홈페이지 : <a href="http://privacy.kisa.or.kr" target="_blank">http://privacy.kisa.or.kr</a></li>
										<li class="sub_p">- 전화 : (국번없이) 118</li>
										<li class="sub_p">- 주소 : (138-950) 서울시 송파구 중대로 135 한국인터넷진흥원 개인정보침해신고센터</li>
									</ul>
								</li>
								<li>
									<p class="dot">대검찰청 사이버범죄수사단</p>
									<ul class="dot_list2">
										<li>02-3480-3571(<a href="http://www.spo.go.kr" target="_blank">http://www.spo.go.kr</a>, <a href="mailto:cybercid@spo.go.kr" target="_blank">cybercid@spo.go.kr)</a></li>
									</ul>
								</li>
								<li>
									<p class="dot">경찰청 사이버테러대응센터</p>
									<ul class="dot_list2">
										<li>1566-0112(<a href="http://cyberbureau.police.go.kr/index.do" target="_blank">http://cyberbureau.police.go.kr</a>)</li>
									</ul>
								</li>
							</ul>
							<p class="txt mb55">
								개인정보의 열람, 정정·삭제, 처리정지 등에 대한 정보주체자의 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익을 침해 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br>☞ 중앙행정심판위원회(<a href="https://www.simpan.go.kr/nsph/index.do" target="_blank">www.simpan.go.kr</a>)의 전화번호 안내 참조
							</p>
							<!-- //제10조 -->
							<!-- 제 11조 -->
							<h4 class="tit2">제11조(개인정보 처리방침 변경)</h4>
							<p>이 개인정보처리방침은 2018. 2. 12.부터 적용됩니다.</p>
							<!-- //제 11조 -->
							<input type="hidden" id="dt" value="2018. 2. 12"/>