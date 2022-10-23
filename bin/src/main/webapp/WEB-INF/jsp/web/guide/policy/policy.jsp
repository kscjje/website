<%
 /**
  * @Class Name : policy.jsp
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
			<!-- 제1조 -->
							<h4 class="tit2">제1조(개인정보의 처리목적)</h4>
							<ol class="mb30">
								<li>
									<p>1. 노원수학문화관은 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는「개인정보보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.</p>
									<ol>
										<li class="num_p">(1) 대표 누리집 회원 가입 및 관리 회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 제한적 본인확인제 시행에 따른 본인확인,<br class="pc"> 서비스 부정이용 방지, 만 14세 미만 아동의 개인정보 처리시 법정대리인의 동의여부 확인, 각종 고지·통지, 고충처리 등을 목적으로 개인정보를 처리합니다.</li>
										<li class="num_p">(2) 민원사무 처리 민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등의 목적으로 개인정보를 처리합니다.</li>
									</ol>
								</li>
								<li>
									<p>2. 노원수학문화관이「개인정보보호법」제32조에 따라 등록·공개하는 개인정보파일의 처리목적은 다음과 같습니다.</p>
								</li>
							</ol>
							<!-- table -->
							<div class="table3 mb15">
								<table>
									<caption>개인정보처리방침 설명하는 표입니다.-순번, 개인정보파일명칭, 운영근거처리목적, 개인정보항목</caption>
									<colgroup>
										<col style="width:5%">
										<col style="width:27%">
										<col style="*">
										<col style="width:30%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">개인정보파일 명칭</th>
											<th scope="col">운영근거/처리목적</th>
											<th scope="col">개인정보파일에 기록되는 개인정보의 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">전국학생과학발명품 경진대회 수상자 목록</td>
											<td class="txt_left">전국학생발명품경진대회규정/수상자에게 상장을 부여하고 입시자료로 사용하기 위함</td>
											<td class="txt_left">이름, 생년월일, E-mail, 전화번호, 수상등급</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">전국과학전람회 수상자 목록</td>
											<td class="txt_left">전국과학전람회규칙/수상자에게 상장을 부여하고 입시자료로 사용하기 위함</td>
											<td class="txt_left">이름, 생년월일, E-mail, 전화번호, 수상등급</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">노원수학문화관 대표 누리집 회원명부</td>
											<td class="txt_left">정보주체동의(1년주기)/대표 누리집 회원관리</td>
											<td class="txt_left">이름, 생년월일, E-mail, 주소, 전화번호</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">노원수학문화관 특별회원 명부</td>
											<td class="txt_left">노원수학문화관 회원제운영규정 제5조/특별회원 운영</td>
											<td class="txt_left">이름, 생년월일, E-mail, 주소, 전화번호, 가족성명, 가족생년월일</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">노원수학문화관 유료회원 명부</td>
											<td class="txt_left">노원수학문화관 회원제운영규정/유료회원 운영</td>
											<td class="txt_left">이름, 생년월일, E-mail, 주소, 전화번호, 가족성명, 가족생년월일</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">노원수학문화관 자원봉사자 지원서</td>
											<td class="txt_left">자원봉사자 운영규정</td>
											<td class="txt_left">이름, 생년월일, 주소, 전화번호</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<p class="tb_txt fn mb55">※ 기타 노원수학문화관의 개인정보파일 등록사항 공개는 행정안전부 개인정보보호 종합지원 포털(www.privacy.go.kr) → 개인정보민원 → 개인정보열람등 요구 → 개인정보파일 목록검색 메뉴를 활용해주시기 바랍니다.</p>
							<!-- //제1조 -->
							<!-- 제2조 -->
							<h4 class="tit2">제2조(개인정보의 처리 및 보유기간)</h4>
							<ol class="mb30">
								<li>
									<p>1. 노원수학문화관은 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.</p>
									<p>2. 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</p>
								</li>
							</ol>
							<!-- table -->
							<div class="table3 mb55">
								<table>
									<caption>개인정보처리방침 설명하는 표입니다.-순번, 개인정보파일명칭, 운영근거처리목적, 개인정보항목</caption>
									<colgroup>
										<col style="width:5%">
										<col style="width:27%">
										<col style="*">
										<col style="width:15%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">개인정보파일 명칭</th>
											<th scope="col">운영근거/처리목적</th>
											<th scope="col">보유기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">전국학생과학발명품 경진대회 수상자 목록</td>
											<td class="txt_left">전국학생발명품경진대회규정/수상자에게 상장을 부여하고 입시자료로 사용하기 위함</td>
											<td>영구</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">전국과학전람회 수상자 목록</td>
											<td class="txt_left">전국과학전람회규칙/수상자에게 상장을 부여하고 입시자료로 사용하기 위함</td>
											<td>영구</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">노원수학문화관 회원명부</td>
											<td class="txt_left">정보주체동의(1년주기)/통합예약 누리집 회원관리</td>
											<td>탈퇴시까지(1년)</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">노원수학문화관 특별회원 명부</td>
											<td class="txt_left">노원수학문화관 회원제운영규정 제5조/특별회원 운영</td>
											<td>탈퇴시까지</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">노원수학문화관 유료회원 명부</td>
											<td class="txt_left">노원수학문화관 회원제운영규정/유료회원 운영</td>
											<td>1년</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">노원수학문화관 자원봉사자 지원서</td>
											<td class="txt_left">자원봉사자 운영규정</td>
											<td>1년</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //제2조 -->
							<!-- 제3조 -->
							<h4 class="tit2">제3조(개인정보의 제3자 제공)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 「개인정보보호법」 제17조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p>
								</li>
								<li>
									<p>2. 노원수학문화관은 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</p>
									<ol>
										<li>
											<p class="num_p">(1) 전국학생과학전람회</p>
											<ul class="dot_list">
												<li class="dot">제3자 제공 목적 및 내용: 상장발급을 위한 최소한의 개인정보</li>
												<li class="dot">제공기관(상장발급 및 상금지급): 교육부, 농림축산식품부, 산업통상자원부, 환경부, 해양수산부 등 38개 기관 중 해당기관 및 상금지급을 처리할 은행</li>
												<li class="dot">개인정보 항목: 해당 기관 상장 수여 대상자의 성명, 생년월일, 소속, 주소, 계좌정보 등</li>
												<li class="dot">보유 및 이용기간: 수상자 정보 보존을 위해 최소한의 개인정보 지속 보유</li>
												<li class="dot">동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익의 내용</li>
											</ul>
										</li>
										<li>
											<p class="num_p">(2) 전국학생과학발명품경진대회
											</p>
											<ul class="dot_list">
												<li class="dot">제3자 제공 목적 및 내용: 상장발급을 위한 최소한의 개인정보</li>
												<li class="dot">제공기관(상장발급 및 상금지급): 교육부, 농림축산식품부, 해양수산부, 산업통상자원부, 환경부, 중소벤처기업부, 특허청, 한국연구재단, 한국과학창의재단, 동아일보사, 한국야쿠르트 총 11개 기관 중 해당기관 및 상금지급을 처리할 은행</li>
												<li class="dot">개인정보 항목: 해당 기관 상장 수여 대상자의 성명, 생년월일, 소속, 주소 등</li>
												<li class="dot">보유 및 이용기간: 수상자 정보 보존을 위해 최소한의 개인정보 지속 보유</li>
												<li class="dot">동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익의 내용</li>
											</ul>
										</li>
									</ol>
								</li>
							</ol>
							<!-- //제3조 -->
							<!-- 제4조 -->
							<h4 class="tit2">제4조(개인정보처리의 위탁)</h4>
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
													<td>과학교육과</td>
													<td>방학 STEAM 캠프</td>
													<td>㈜이엠씨플랜</td>
													<td>2019. 11. 15. ~ 2020. 8. 31.</td>
													<td>캠프 참가자 관리</td>
												</tr>
												<tr>
													<td>2</td>
													<td>과학수학체험행사<br>사이언스데이</td>
													<td>마이스피플</td>
													<td>㈜이엠씨플랜</td>
													<td>2020. 4. 2. ~ 11. 20.</td>
													<td>과학수학체험행사<br>사이언스데이</td>
												</tr>
												<tr>
													<td>3</td>
													<td>경영기획과</td>
													<td>노원수학문화관유지보수</td>
													<td>(주)연무기술 </td>
													<td>2020. 1. 1. ~ 2022. 12. 31.</td>
													<td>정보시스템 유지보수</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- //table -->
									<p class="tb_txt fn mb15">※ 과학수학체험행사와 사이언스데이는 같은 사업으로 운영</p>
								</li>
								<li>
									<p>2. 노원수학문화관은 위탁계약 체결시 「개인정보보호법」 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</p>
									<p>3. 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체 없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</p>
								</li>
							</ol>
							<!-- //제4조 -->
							<!-- 제5조 -->
							<h4 class="tit2">제5조(정보주체와 법정대리인의 권리· 의무 및 행사방법)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 「개인정보보호법」 제17조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p>
								</li>
								<li>
									<p>2. 제1항에 따른 권리 행사는 노원수학문화관에 대해 「개인정보보호법」 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며, 노원수학문화관은 이에 대해 지체 없이 조치하겠습니다.</p>
								</li>
								<li>
									<p>3. 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 「개인정보보호법」 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.</p>
								</li>
								<li>
									<p>4. 개인정보 열람 및 처리정지 요구는 「개인정보보호법」 제35조 제5항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.</p>
								</li>
								<li>
									<p>5. 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</p>
								</li>
								<li>
									<p>6. 노원수학문화관은 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인 인지를 확인합니다.</p>
									<ul class="dot_list2">
										<li class="dot">[개인정보보호법 시행규칙 별지 제8호] 개인정보(열람, 정정·삭제, 처리정지) 요구서 <a href="<%=strContext%>/download/%5B%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EB%B3%B4%ED%98%B8%EB%B2%95%20%EC%8B%9C%ED%96%89%EA%B7%9C%EC%B9%99%20%EB%B3%84%EC%A7%80%20%EC%A0%9C8%ED%98%B8%5D%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4(%EC%97%B4%EB%9E%8C,%20%EC%A0%95%EC%A0%95%C2%B7%EC%82%AD%EC%A0%9C,%20%EC%B2%98%EB%A6%AC%EC%A0%95%EC%A7%80)%20%EC%9A%94%EA%B5%AC%EC%84%9C.hwp" title="개인정보(열람, 정정·삭제, 처리정지) 요구서 한글파일" class="btn_ty_s_c2 down">다운로드</a></li>
										<li class="dot">[개인정보보호법 시행규칙 별지 제11호] 위임장 <a href="<%=strContext%>/download/%5B%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EB%B3%B4%ED%98%B8%EB%B2%95%20%EC%8B%9C%ED%96%89%EA%B7%9C%EC%B9%99%20%EB%B3%84%EC%A7%80%20%EC%A0%9C11%ED%98%B8%5D%20%EC%9C%84%EC%9E%84%EC%9E%A5.hwp" title="위임장 한글파일" class="btn_ty_s_c2 down">다운로드</a></li>
										<li>정보주체는 노원수학문화관의 열람청구 접수·처리부서 이외에 행정안전부의 ‘개인정보보호 종합포털(www.privacy.go.kr)’을 통해 개인정보 열람청구를 하실 수 있습니다.</li>
									</ul>
								</li>
							</ol>
							<!-- //제5조 -->
							<!-- 제6조 -->
							<h4 class="tit2">제6조(처리하는 개인정보 항목)</h4>
							<p class="txt mb30">노원수학문화관은 다음의 개인정보 항목을 처리하고 있습니다.</p>
							<ol class="mb55">
								<li>
									<p class="mb30">1. 개인정보 처리항목</p>
									<!-- table -->
									<div class="table3 mb30">
										<table>
											<caption>개인정보처리항목을 설명하는 표입니다.-순번, 개인정보파일명칭, 개인정보항목</caption>
											<colgroup>
												<col style="width:5%">
												<col style="*">
												<col style="*">
											</colgroup>
											<thead>
												<tr>
													<th scope="col">순번</th>
													<th scope="col">개인정보파일 명칭</th>
													<th scope="col">개인정보파일에 기록되는 개인정보의 항목</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>전국학생과학발명품 경진대회 수상자 목록</td>
													<td>이름, 생년월일, E-mail, 전화번호, 수상등급</td>
												</tr>
												<tr>
													<td>2</td>
													<td>전국과학전람회 수상자 목록</td>
													<td>이름, 생년월일, E-mail, 전화번호, 수상등급</td>
												</tr>
												<tr>
													<td>3</td>
													<td>노원수학문화관 회원명부 </td>
													<td>이름, 생년월일, E-mail, 지역, 전화번호</td>
												</tr>
												<tr>
													<td>4</td>
													<td>노원수학문화관 특별회원 명부</td>
													<td>이름, 생년월일, E-mail, 지역, 전화번호, 가족성명, 가족생년월일</td>
												</tr>
												<tr>
													<td>5</td>
													<td>노원수학문화관 유료회원 명부</td>
													<td>이름, 생년월일, E-mail, 지역, 전화번호, 가족성명, 가족생년월일</td>
												</tr>
												<tr>
													<td>6</td>
													<td>노원수학문화관 자원봉사자 지원서</td>
													<td>이름, 생년월일, 주소, 전화번호</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- //table -->
								</li>
								<li>
									<p>2. 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다. - IP주소, 쿠키, MAC주소, 서비스 이용기록, 방문기록, 불량 이용기록 등</p>
								</li>
							</ol>
							<!-- //제6조 -->
							<!-- 제7조 -->
							<h4 class="tit2">제7조(개인정보의 파기)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 「개인정보보호법」 제17조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p>
								</li>
								<li>
									<p>2. 노원수학문화관은 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</p>
									<ol>
										<li>
											<p class="num_p">(1) 파기절차</p>
											<ul class="dot_list">
												<li>노원수학문화관은 파기하여야 하는 개인정보(또는 개인정보파일)에 대해 개인정보 파기계획을 수립하여 파기합니다. 노원수학문화관은 파기 사유가 발생한 개인정보(또는 개인정보파일)에 대하여 개인정보파일 파기요청서를 개인정보 보호책임자에게 제출하여 승인을 받아 파기하며, 개인정보파일 파기 결과를 개인정보 보호책임자에게 보고합니다.</li>
												<li class="dot">개인정보 파기 : 보유기간이 경과한 개인정보는 종료일로부터 5일이내 파기합니다.</li>
												<li class="dot">개인정보파일 파기 : 개인정보파일의 처리목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보파일이 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일이내 그 개인정보파일을 파기합니다.</li>
											</ul>
										</li>
										<li>
											<p class="num_p">(2) 파기방법</p>
											<ul class="dot_list">
												<li>노원수학문화관은 전자적 파일 형태로 기록·저장된 개인정보는 기록을 재생할 수 없도록 파기하며, 종이 문서에 기록·저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.</li>
											</ul>
										</li>
									</ol>
								</li>
							</ol>
							<!-- //제7조 -->
							<!-- 제8조 -->
							<h4 class="tit2">제8조(개인정보의 안전성 확보조치)</h4>
							<p class="txt">노원수학문화관은 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.</p>
							<ul class="dot_list2 ty mb55">
								<li class="dot">관리적 조치 : 내부관리계획 수립·시행, 정기적 직원 교육 및 개인정보처리 위탁업체교육 실시 등</li>
								<li class="dot">기술적 조치 : 개인정보처리시스템 접근권한 관리 및 접속기록(최소 6개월) 보관, 개인정보 암호화 저장, 접근통제시스템 및 보안프로그램 설치</li>
								<li class="dot">물리적 조치 : 전산실, 자료보관실 등의 접근통제</li>
							</ul>
							<!-- //제8조 -->
							<!-- 제9조 -->
							<h4 class="tit2">제9조(개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용합니다.</p>
								</li>
								<li>
									<p>2. 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 쿠키의 사용목적 : 노원수학문화관 대표 누리집의 소셜 로그인 상태를 유지하기 위해 사용합니다.</li>
										<li class="sub_p">- 쿠키의 설치·운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부 할 수 있습니다.</li>
										<li class="sub_p">- 쿠키 저장을 거부할 경우 소셜 로그인 서비스 이용에 어려움이 발생할 수 있습니다.</li>
									</ul>
								</li>
							</ol>
							<!-- //제9조 -->
							<!-- 제10조 -->
							<h4 class="tit2">제10조(개인정보보호 책임자)</h4>
							<ol class="mb55">
								<li>
									<p>1. 노원수학문화관은 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 개인정보보호 책임자 : 경영기획과 김진형, 042-601-7805 jinhyunk2@korea.kr</li>
										<li class="sub_p">- 개인정보보호 담당자 : 경영기획과 장미경, 042-601-7867 wkdalrud@korea.kr</li>
									</ul>
								</li>
								<li>
									<p>2. 정보주체는 노원수학문화관의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보보호 책임자 및 담당부서로 문의하실 수 있습니다. 노원수학문화관은 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.</p>
								</li>
							</ol>
							<!-- //제10조 -->
							<!-- 제11조 -->
							<h4 class="tit2">제11조(개인정보 열람청구)</h4>
							<ol class="mb55">
								<li>
									<p class="mb30">1. 정보주체는 「개인정보보호법」 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다. 노원수학문화관은 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.</p>
									<p>개인정보파일 열람청구 접수·처리 부서</p>
									<div class="table3 mb30">
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
													<td>전국학생과학발명품 경진대회 수상자 목록</td>
													<td>과학교육과</td>
													<td>허준행</td>
													<td>탁현수</td>
													<td>042-601-7739</td>
													<td>tahkhs@korea.kr</td>
												</tr>
												<tr>
													<td>2</td>
													<td>전국과학전람회 수상자목록</td>
													<td>과학교육과</td>
													<td>허준행</td>
													<td>조정현</td>
													<td>042-601-7737</td>
													<td>cjhjcy@korea.kr</td>
												</tr>
												<tr>
													<td>3</td>
													<td>노원수학문화관 대표 누리집 회원명부</td>
													<td>경영기획과</td>
													<td>김진형</td>
													<td>장미경</td>
													<td>042-601-7867</td>
													<td>wkdalrud@korea.kr</td>
												</tr>
												<tr>
													<td>4</td>
													<td>노원수학문화관 특별회원 명부</td>
													<td>경영기획과</td>
													<td>김진형</td>
													<td>장미경</td>
													<td>042-601-7867</td>
													<td>wkdalrud@korea.kr</td>
												</tr>
												<tr>
													<td>5</td>
													<td>노원수학문화관 유료회원 명부</td>
													<td>경영기획과</td>
													<td>김진형</td>
													<td>장미경</td>
													<td>042-601-7867</td>
													<td>wkdalrud@korea.kr</td>
												</tr>
												<tr>
													<td>6</td>
													<td>노원수학문화관 자원봉사자 지원서</td>
													<td>관람총괄과</td>
													<td>윤용현</td>
													<td>김민정</td>
													<td>042-601-7908</td>
													<td>7907kmj@korea.kr</td>
												</tr>
											</tbody>
										</table>
									</div>
								</li>
								<li>
									<p>2. 정보주체께서는 제1항의 열람청구 접수·처리부서 이외에, 행정안전부의 ‘개인정보보호 종합지원 포털’ 웹사이트(www.privacy.go.kr)를 통하여서도 개인정보 열람청구를 하실 수 있습니다.</p>
								</li>
							</ol>
							<!-- //제11조 -->
							<!-- 제12조 -->
							<h4 class="tit2">제12조(권익침해 구제방법)</h4>
							<p class="txt mb15">정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.</p>
							<div class="ex_txt mb15">
								<p><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 아래의 기관은 노원수학문화관과는 별개의 기관으로서, 노원수학문화관의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다.</p>
							</div>
							<ul class="mb55">
								<li>
									<p class="dot">개인정보 침해신고센터(한국인터넷진흥원 운영)</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 소관업무 : 개인정보 침해사실 신고, 상담신청</li>
										<li class="sub_p">- 홈페이지 : <a href="http://privacy.kisa.or.kr" target="_blank">http://privacy.kisa.or.kr</a></li>
										<li class="sub_p">- 전화 : (국번 없이) 118</li>
										<li class="sub_p">- 주소 : (58324) 전남 나주시 진흥길 9(빛가람동 301-2) 3층 개인정보침해신고센터</li>
									</ul>
								</li>
								<li>
									<p class="dot">개인정보 분쟁조정위원회</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정 (민사적 해결)</li>
										<li class="sub_p">- 홈페이지 : <a href="www.kopico.go.kr" target="_blank">www.kopico.go.kr</a></li>
										<li class="sub_p">- 전화 : (국번없이) 1833-6972</li>
										<li class="sub_p">- 주소 : (03171)서울특별시 종로구 세종대로 209 정부서울청사 4층</li>
									</ul>
								</li>
								<li>
									<p class="dot">대검찰청 사이버범죄수사단</p>
									<ul class="dot_list2">
										<li>02-3480-3573(<a href="http://www.spo.go.kr" target="_blank" title="새창열림">http://www.spo.go.kr</a>, <a href="mailto:cybercid@spo.go.kr" target="_blank">cybercid@spo.go.kr)</a></li>
									</ul>
								</li>
								<li>
									<p class="dot">경찰청 사이버안전국</p>
									<ul class="dot_list2">
										<li>182(<a href="http://cyberbureau.police.go.kr/index.do" target="_blank" title="새창열림">http://cyberbureau.police.go.kr</a>)</li>
									</ul>
								</li>
							</ul>
							<!-- //제12조 -->
							<!-- 제 13조 -->
							<h4 class="tit2">제13조(개인정보 처리방침 변경)</h4>
							<ol class="mb55">
								<li><p>1. 이 개인정보 처리방침은 2019. 6. 5.부터 적용됩니다.</p></li>
								<li><p>2. 이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다.</p></li>
							</ol>
							<!-- //제 13조 -->
							<input type="hidden" id="dt" value="2019. 6. 5" />