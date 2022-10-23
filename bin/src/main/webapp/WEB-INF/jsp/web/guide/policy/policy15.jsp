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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<h4 class="tit2">개인정보 처리목적</h4>
							<p class="txt">1. 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전 동의를 구할 예정입니다.</p>
							<ul class="dot_list mb30">
								<li class="dot">노원수학문화관 홈페이지 회원 관리</li>
								<li class="dot">과학교육(캠프), 체험활동, 수상실적증명, 자원봉사 등 접수 및 이력 관리</li>
							</ul>
							<h4 class="tit2">개인정보파일 현황</h4>
							<p class="txt mb30">노원수학문화관이 개인정보보호법 제32조(개인정보파일의 등록 및 공개)에 따라 등록·공개하는 개인정보파일은 다음과 같습니다.</p>
							<div class="table3 mb15">
								<table>
									<caption>노원수학문화관이 개인정보보호법 제32조(개인정보파일의 등록 및 공개)에 따라 등록·공개하는 개인정보파일현황표입니다. -파일명, 보유근거, 수집방법, 보유목적, 기록항목, 보유기간</caption>
									<colgroup>
										<col style="width:10%">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="width:15%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">파일명</th>
											<th scope="col">보유근거</th>
											<th scope="col">수집방법</th>
											<th scope="col">보유목적</th>
											<th scope="col">기록항목</th>
											<th scope="col">보유기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>홈페이지 회원목록DB</td>
											<td class="txt_left">노원수학문화관 회원제 운영규정</td>
											<td class="txt_left">노원수학문화관 홈페이지 회원가입</td>
											<td class="txt_left">노원수학문화관 회원제 운영</td>
											<td class="txt_left">기본정보 : 이름, 아이디, 서브아이디, 휴대전화, 이메일, SMS수신동의, 뉴스레터수신동의, 가입일<br>부가정보 : 생년월일, 회원구분, 유료가입일, 유료만기일, 주소, 소속, 학교명, 학년(10개), 교육⋅체험활동 등 이력</td>
											<td>회원탈퇴 시 까지</td>
										</tr>
										<tr>
											<td>자원봉사자</td>
											<td class="txt_left">노원수학문화관 자원봉사자 운영규정</td>
											<td class="txt_left">서면, 전화접수</td>
											<td class="txt_left">노원수학문화관 업무에 필요한 자원봉사자 관리</td>
											<td class="txt_left">이름, 주민번호, 주소, 연락처, 이메일, 계좌번호</td>
											<td>3년</td>
										</tr>
										<tr>
											<td>과학 꿈나무 멘토링 신청자 명단</td>
											<td class="txt_left">국가 과학기술경쟁력 강화를 위한 이공계지원 특별법 제4조</td>
											<td class="txt_left">서면</td>
											<td class="txt_left">과학꿈나무멘토링에 참여하는 학생들을 관리</td>
											<td class="txt_left">이름, 연락처</td>
											<td>1년</td>
										</tr>
										<tr>
											<td>천체관측대회수상자</td>
											<td class="txt_left">노원수학문화관 기본운영규정</td>
											<td class="txt_left">서면</td>
											<td class="txt_left">천체관측대회에서 우승한 수상자의 실적증명 발급</td>
											<td class="txt_left">이름, 주민번호</td>
											<td>1년</td>
										</tr>
										<tr>
											<td>교육 및 캠프 수료자 명부</td>
											<td class="txt_left">노원수학문화관 과학교육운영규정</td>
											<td class="txt_left">전자접수, 회원가입</td>
											<td class="txt_left">교육·캠프에 참여하는 교육생 관리 및 수료증 발급</td>
											<td class="txt_left">성명, 생년월일, 학교, 전화번호</td>
											<td>회원 탈퇴 시까지<br> 또는 15년</td>
										</tr>
										<tr>
											<td>과학경진대회 수상자 명단</td>
											<td class="txt_left">전국과학전람회 규칙, 전국학생과학발명품경진대회 규정</td>
											<td class="txt_left">서면</td>
											<td class="txt_left">수상자 관리 및 수상자 실적 증명 발급</td>
											<td class="txt_left">성명,소속, 수상등급</td>
											<td>30년</td>
										</tr>
									</tbody>
								</table>
							</div>

							<h4 class="tit2">개인정보 보유기간</h4>
							<p class="txt mb55">노원수학문화관은 법령에 따른 개인정보 보유기간 또는 정보주체로부터 동의 받은 보유기간 내에서 개인정보를 처리합니다. 각각의 개인정보 보유기간은 상기 “개인정보파일 현황”을 참고하시기 바랍니다.</p>

							<h4 class="tit2">정보주체 권리, 의무 및 그 행사방법</h4>
							<p class="txt">이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</p>
							<ol class="mb55">
								<li>
									<p>1. 정보주체는 노원수학문화관에 대해 언제든지 다음과 같은 개인정보보호 관련 권리를 행사할 수 있습니다.</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 개인정보 열람 요구</li>
										<li class="sub_p">- 개인정보에 오류가 있을 경우 정정 요구</li>
										<li class="sub_p">- 개인정보 삭제 요구</li>
										<li class="sub_p">- 개인정보 처리정지 요구</li>
									</ul>
								</li>
								<li>
									<p>2. 정보주체의 권리 행사는 개인정보보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통해 할 수 있으며 노원수학문화관은 이에 대해 지체 없이 조치하겠습니다. [별지 제8호 서식 다운로드] <a href="#" class="btn_ty_s_c2 down">다운로드</a></p>
								</li>
								<li>
									<p>3. 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 노원수학문화관은 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</p>
								</li>
								<li>
									<p>4. 권리 행사는 정보주체의 법정 대리인이나 위임을 받은 자 등을 통하여 할 수 있습니다. 이 경우 개인정보보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출해야 합니다. [별지 제11호 서식 다운로드] <a href="#" class="btn_ty_s_c2 down">다운로드</a></p>
								</li>
							</ol>

							<h4 class="tit2">개인정보 파기</h4>
							<p class="txt mb30">개인정보 처리 목적이 달성된 경우에는 원칙적으로 지체 없이 해당 개인정보를 파기합니다. 파기 기한 및 방법은 다음과 같습니다.</p>
							<ul class="dot_list mb55">
								<li class="dot">파기기한 : 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</li>
								<li class="dot">파기방법 : 전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하고, 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</li>
							</ul>

							<h4 class="tit2">개인정보 안전성 확보 조치</h4>
							<p class="txt">노원수학문화관은 개인정보보호법 제29조(안전조치의무)에 따라 다음과 같이 안전성 확보에 필요한 기술적, 관리적 및 물리적 조치를 하고 있습니다.</p>
							<ul class="dot_list2 ty mb30">
								<li class="dot">내부관리계획의 수립 및 시행 : 개인정보의 안전한 처리를 위해 내부관리계획을 수립하여 시행하고 있습니다.</li>
								<li class="dot">개인정보의 암호화 : 개인정보는 암호화되어 저장 및 관리되고 있습니다.</li>
								<li class="dot">해킹 등에 대비한 기술적 대책 : 방화벽, 침입방지시스템 등 정보보호시스템을 이용하여 해킹 등에 의한 개인정보 유출 및 훼손을 방지하고 있습니다.</li>
								<li class="dot">개인정보에 대한 접근 제한 : 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여, 변경, 말소를 통해 개인정보에 대한 접근통제를 하고 있습니다.</li>
								<li class="dot">데이터베이스시스템 접속기록 보관 : 데이터베이스시스템에 접속한 기록을 최소 6개월 이상 보관하여 관리하고 있습니다.</li>
								<li class="dot">문서보안을 위한 잠금장치 사용 : 개인정보가 포함된 서류는 잠금장치가 있는 캐비닛에 보관하여 관리하고 있습니다.</li>
								<li class="dot">비인가자에 대한 출입 통제 : 개인정보를 보관하고 있는 물리적 장소를 별도로 두고 이에 대해 출입통제 절차를 수립하여 운영하고 있습니다.</li>
							</ul>

							<h4 class="tit2">개인정보보호 책임자 지정</h4>
							<p class="txt mb30">노원수학문화관은 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위해 아래와 같이 개인정보보호 책임자를 지정하고 있습니다.</p>
							<div class="table3 mb55">
								<table>
									<caption>개인정보보호 책임자 및 담당자 연락처 확인 표입니다.-구분, 소속, 성명, 연락처, 이메일</caption>
									<colgroup>
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="width:20%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col" colspan="2">구분</th>
											<th scope="col">소속</th>
											<th scope="col">성명</th>
											<th scope="col">연락처</th>
											<th scope="col">이메일</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="2">개인정보관리책임관</td>
											<td>스마트 노원수학문화관팀</td>
											<td>오석균</td>
											<td>042-601-7865</td>
											<td rowspan="9">webadmin@hisco.co.kr</td>
										</tr>
										<tr>
											<td rowspan="8">분야별 책임자/담당자</td>
											<td>홈페이지(유·무료회원)</td>
											<td>스마트 노원수학문화관팀</td>
											<td>오석균<br> 송은희</td>
											<td>042-601-7865<br> 042-601-7869</td>
										</tr>
										<tr>
											<td>CCTV</td>
											<td>운영지원과</td>
											<td>김승주<br> 정상일</td>
											<td>042-601-7852<br> 042-601-7857</td>
										</tr>
										<tr>
											<td>교육·캠프</td>
											<td>창의교육<br> 진흥과</td>
											<td>김해식<br> 이형한</td>
											<td>042-601-7939<br> 042-601-7971</td>
										</tr>
										<tr>
											<td>과학경진대회</td>
											<td>창의교육<br> 진흥과</td>
											<td>민병철<br> 손홍락</td>
											<td>042-601-7937<br> 042-601-7942</td>
										</tr>
										<tr>
											<td>자연탐험단<br> 과학문화재탐방</td>
											<td>종합연구<br> 기획실</td>
											<td>백운기<br> 김동희</td>
											<td>042-601-7982<br> 042-601-7990</td>
										</tr>
										<tr>
											<td>전통과학대학</td>
											<td>종합연구<br> 기획실</td>
											<td>윤용현<br> 윤대식</td>
											<td>042-601-7984<br> 042-601-7986</td>
										</tr>
										<tr>
											<td>자원봉사자</td>
											<td>관람운영과</td>
											<td>조원근<br> 신영순</td>
											<td>042-601-7906<br> 042-601-7910</td>
										</tr>
										<tr>
											<td>천체관</td>
											<td>관람운영과</td>
											<td>김해용<br> 조성헌</td>
											<td>042-601-7909<br> 042-601-7908</td>
										</tr>
									</tbody>
								</table>
							</div>

							<h4 class="tit2">개인정보 열람청구</h4>
							<ol class="mb30">
								<li>
									<p>1. 정보주체는 개인정보보호법 제35조(개인정보의 열람)에 따른 개인정보의 열람청구를 상기의 부서에서 할 수 있습니다. 노원수학문화관은 정보주체의 개인정보 열람청구가 신속하게 처리되도록 하겠습니다.</p>
								</li>
								<li>
									<p>
										2. 정보주체는 노원수학문화관의 열람청구 접수·처리부서 이외에, 행정안전부의 ‘개인정보보호 종합지원포털(http://www.privacy.go.kr)’을 통해 개인정보 열람청구를 할 수 있습니다.<br>
										※ 정보주체는 노원수학문화관의 열람청구 접수·처리부서(하단, 제9조 개인정보보호(분야별)책임자 및 담당자 연락처 참조) 이외에 행정자치부의 ‘개인정보보호 종합지원포털(<a href="http://www.privacy.go.kr" target="_blank">http://www.privacy.go.kr</a>)’을 통해 개인정보 열람청구를 하실 수 있습니다.
									</p>
								</li>
							</ol>
							<ul class="mb55">
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

							<h4 class="tit2">개인정보처리방침의 변경</h4>
							<p>이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 가능한 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</p>
							<input type="hidden" id="dt" value="2012. 6. 1" />
