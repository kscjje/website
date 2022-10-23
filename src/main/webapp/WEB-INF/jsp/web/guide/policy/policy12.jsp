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
<!-- 제1조 -->
							<h4 class="tit2">제1조(개인정보 처리목적)</h4>
							<ol class="mb30">
								<li>
									<p>1. 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전 동의를 구할 예정입니다.</p>
									<p class="dot">노원수학문화관 홈페이지 회원관리, 과학교육 자원봉사등 접수 및 이력관리</p>
								</li>
								<li>
									<p>2. 개인정보파일 현황<br> 노원수학문화관이 개인정보보호법 제32조(개인정보파일의 등록 및 공개)에 따라 등록공개하는 개인정보파일은 다음과 같습니다.</p>
								</li>
							</ol>
							<!-- table -->
							<div class="table3 mb55">
								<table>
									<caption>개인정보보호법 제32조(개인정보파일의 등록 및 공개)에 따라 등록공개하는 개인정보파일 현황표입니다.-순번, 파일명, 처리목적</caption>
									<colgroup>
										<col style="width:10%">
										<col style="*">
										<col style="width:30%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">파일명</th>
											<th scope="col">처리목적</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">전국과학전람회수상자목록</td>
											<td>수상자 실적 관리</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">전국학생과학별명품경진대회수상자목록</td>
											<td>수상자 실적 관리</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">과학교실자원봉사자명부</td>
											<td>자원봉사자 관리</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">과학교실강사출근명부</td>
											<td>강사 관리</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">전국학생과학발명품경진대회심사위원</td>
											<td>경진대회 운영</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">회원목록DB</td>
											<td>회원 관리</td>
										</tr>
										<tr>
											<td>7</td>
											<td class="txt_left">자원봉사자관리</td>
											<td>자원봉사자 관리</td>
										</tr>
										<tr>
											<td>8</td>
											<td class="txt_left">전국과학전람회심사위원명단</td>
											<td>경진대회 운영</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //제1조 -->
							<!-- 제2조 -->
							<h4 class="tit2">제2조(개인정보 처리 및 보유기간)</h4>
							<ol class="mb30">
								<li>
									<p>1. 노원수학문화관은 법령에 따라 개인정보보호 또는 정보주체로부터 동의받은 보유기간내에서 개인정보를 처리합니다.</p>
								</li>
								<li>
									<p>2. 각각의 개인정보보유기간은 아래 개인정보파일현황을 참고하시기바랍니다.</p>
								</li>
							</ol>
							<!-- table -->
							<div class="table3 mb55">
								<table>
									<caption>개인정보보유기간이 포함된 개인정보파일현황표입니다.-순번, 파일명, 보유기간</caption>
									<colgroup>
										<col style="width:10%">
										<col style="*">
										<col style="width:30%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">파일명</th>
											<th scope="col">보유기간</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">전국과학전람회수상자목록</td>
											<td>30년</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">전국학생과학별명품경진대회수상자목록</td>
											<td>30년</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">과학교실자원봉사자명부</td>
											<td>3년</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">과학교실강사출근명부</td>
											<td>10년</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">전국학생과학발명품경진대회심사위원</td>
											<td>5년</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">회원목록DB</td>
											<td>3년</td>
										</tr>
										<tr>
											<td>7</td>
											<td class="txt_left">자원봉사자관리</td>
											<td>3년</td>
										</tr>
										<tr>
											<td>8</td>
											<td class="txt_left">전국과학전람회심사위원명단</td>
											<td>5년</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //제2조 -->
							<!-- 제3조 -->
							<h4 class="tit2">제3조(개인정보 제3자 제공에 관한 사항)</h4>
							<ol class="mb30">
								<li>
									<p>1. 다음과 같은 기관 및 자에게 개인정보를 제공하고 있으며 제공 목적 및 범위는 다음과 같습니다.</p>
								</li>
								<li>
									<p>2. 노원수학문화관에서 [http://www.hisco.co.kr/web/main] 에서만 제공됩니다.</p>
								</li>
								<li>
									<p>3. 개인정보 제공 목적 : 대덕특구 연구기관 출입 및 보안, 위레카 체험 이용 등에 관한 사항</p>
								</li>
							</ol>
							<div class="table3 mb55">
								<table>
									<caption> 개인정보 제3자제공  제공 목적 및 범위에 관한 표입니다.- 연번, 제공기관, 개인정보 항목(이름, 생년월일, 주민번호, 기관명(소속), 직위(급)(학년, 반), 주소, 이메일, 일반전화번호, 휴대전화번호), 개인정보 보유기간, 비고 안내 </caption>
									<colgroup>
										<col style="width:5%">
										<col style="width:10%">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
										<col style="*">
									</colgroup>
									<thead>
										<tr>
											<th rowspan="2">연번</th>
											<th rowspan="2">제공기관</th>
											<th colspan="9">개인정보 항목</th>
											<th rowspan="2">개인정보<br> 보유기간</th>
											<th rowspan="2">비고</th>
										</tr>
										<tr>
											<th scope="col">이름</th>
											<th scope="col">생년월일</th>
											<th scope="col">주민번호</th>
											<th scope="col">기관명<br> (소속)</th>
											<th scope="col">직위(급)<br> (학년, 반)</th>
											<th scope="col">주소</th>
											<th scope="col">이메일</th>
											<th scope="col">일반<br> 전화번호</th>
											<th scope="col">휴대<br> 전화번호</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">국가핵융합연구소(NFRI)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>3년</td>
											<td></td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">한국항공우주연구원(KARI)</td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>5년</td>
											<td></td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">한국생명공학연구원(KRIBB)</td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>5년</td>
											<td>○는 신청자만 해당</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">한국화학연구원(KRICT)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>3년</td>
											<td>○는 신청자만 해당</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">한국과학기술정보연구원(KISTI)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>5년</td>
											<td>○는 신청자만 해당</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">한국지질자원연구원(KIGAM)</td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td>1년</td>
											<td></td>
										</tr>
										<tr>
											<td>7</td>
											<td class="txt_left">한국에너지기술연구원(KIER)</td>
											<td>인솔자</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>인솔자</td>
											<td>인솔자</td>
											<td>인솔자</td>
											<td>2년</td>
											<td></td>
										</tr>
										<tr>
											<td>8</td>
											<td class="txt_left">한국표준과학연구원(KRISS)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td>○</td>
											<td>3년</td>
											<td>신청 대표자 정보만 필요</td>
										</tr>
										<tr>
											<td>9</td>
											<td class="txt_left">한국원자력연구원(KAERI)</td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td colspan="2">택1</td>
											<td>5년</td>
											<td></td>
										</tr>
										<tr>
											<td>10</td>
											<td class="txt_left">한국기초과학지원연구원(KBSI)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>5년</td>
											<td></td>
										</tr>
										<tr>
											<td>11</td>
											<td class="txt_left">한국한의학연구원(KIOM)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>영구적</td>
											<td></td>
										</tr>
										<tr>
											<td>12</td>
											<td class="txt_left">한국천문연구원(KASI)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>○</td>
											<td>3년</td>
											<td></td>
										</tr>
										<tr>
											<td>13</td>
											<td class="txt_left">한국기계연구원(KIMM)</td>
											<td>○</td>
											<td></td>
											<td></td>
											<td>○</td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td></td>
											<td>○</td>
											<td>-</td>
											<td>인솔자 정보만 열람</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //제3조 -->
							<!-- 제4조 -->
							<h4 class="tit2">제4조(개인정보처리 위탁에 관한 사항)</h4>
							<ol class="mb55">
								<li>
									<p class="mb30">1. 개인정보 처리를 위탁하는 사항은 다음과 같습니다.</p>
									<!-- table -->
									<div class="table3 mb15">
										<table>
											<caption>개인정보처리의 위탁을 설명하는 표입니다.-순번, 관리부서, 업체명, 위탁기간, 위탁명, 위탁내용</caption>
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
													<th scope="col">관리부서</th>
													<th scope="col">업체명</th>
													<th scope="col">위탁기간</th>
													<th scope="col">위탁명</th>
													<th scope="col">위탁내용</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td>스마트정보화팀</td>
													<td>(주)연무종합기술</td>
													<td>2014. 1. 1~12. 31.</td>
													<td>홈페이지 유지보수</td>
													<td>대표 홈페이지 관리</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- //table -->
								</li>
								<li>
									<p>2. 위탁계약 시 개인정보보호 관련 법규의 준수, 개인정보에 관한 제3자 제공 금지 및 책임부담 등을 명확히 규정하고, 당해 계약내용을 서면 및 전자 보관하고 있습니다. 업체 변경시 공지사항 및 개인정보처리방침을 통해 고지하겠습니다.</p>
								</li>
							</ol>
							<!-- //제4조 -->
							<!-- 제5조 -->
							<h4 class="tit2">제5조(정보주체 권리·의무 및 그 행사방법에 관한 사항)</h4>
							<p class="txt">이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</p>
							<ol class="mb55">
								<li>
									<p>1. 정보주체는 노원수학문화관에 대해 언제든지 다음과 같은 개인정보보호 관련 권리를 행사할 수 있습니다.</p>
									<ul class="sub_p_list">
										<li class="sub_p">- 개인정보 열람 요구</li>
										<li class="sub_p">- 개인정보에 오류가 있을 경우 정정 요구</li>
										<li class="sub_p">- 개인정보 삭제 요구</li>
										<li class="sub_p">- 개인정보 처리정지 요구</li>
										<li class="sub_p">- 탈퇴(해지) : 회원이 이용계약을 종료시키는 행위</li>
									</ul>
								</li>
								<li>
									<p>2. 정보주체의 권리 행사는 개인정보보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통해 할 수 있으며 노원수학문화관은 이에 대해 지체 없이 조치하겠습니다. [별지 제8호 서식 다운로드] <a href="#" class="btn_ty_s_c2 down">다운로드</a></p>
								<li>
									<p>3. 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 노원수학문화관은 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</p>
								</li>
								<li>
									<p>4. 권리 행사는 정보주체의 법정 대리인이나 위임을 받은 자 등을 통하여 할 수 있습니다. 이 경우 개인정보보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출해야 합니다. [별지 제11호 서식 다운로드] <a href="#" class="btn_ty_s_c2 down">다운로드</a></p>
								</li>
								<li>
									<p>5. 정보주체는 개인정보보호법 제35조(개인정보의 열람)에 따른 개인정보의 열람청구를 개인정보보호 (분야별)책임자 및 담당자 연락처(하단, 제10조 참조)로 할 수 있습니다. 노원수학문화관은 정보주체의 개인정보 열람청구가 신속하게 처리되도록 하겠습니다.</p>
								</li>
								<li>
									<p>6. 정보주체는 노원수학문화관의 열람청구 접수·처리부서(하단, 제10조 개인정보보호 (분야별)책임자 및 담당자 연락처 참조) 이외에, 안전행정부의 ‘개인정보보호 종합지원포털(<a href="http://www.privacy.go.kr" target="_blank">http://www.privacy.go.kr</a>)’을 통해 개인정보 열람청구를 하실 수 있습니다.</p>
								</li>
							</ol>
							<!-- //제5조 -->
							<!-- 제6조 -->
							<h4 class="tit2">제6조(처리하는 개인정보 항목)</h4>
							<!-- table -->
							<div class="table3 mb55">
								<table>
									<caption>처리하는 개인정보 항목을 포함한 표입니다.-순번, 파일명, 개인정보의 항목, 필수항목, 선택항목</caption>
									<colgroup>
										<col style="width:5%">
										<col style="width:25%">
										<col style="width:25%">
										<col style="*">
										<col style="width:15%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">파일명</th>
											<th scope="col">개인정보의 항목	</th>
											<th scope="col">필수항목</th>
											<th scope="col">선택항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td class="txt_left">전국과학전람회수상자목록</td>
											<td class="txt_left">성명, 소속, 수상등급</td>
											<td class="txt_left">성명, 소속</td>
											<td class="txt_left">수상등급</td>
										</tr>
										<tr>
											<td>2</td>
											<td class="txt_left">전국학생과학별명품 경진대회수상자목록</td>
											<td class="txt_left">성명, 소속, 수상등급</td>
											<td class="txt_left">성명, 소속</td>
											<td class="txt_left">수상등급</td>
										</tr>
										<tr>
											<td>3</td>
											<td class="txt_left">과학교실자원봉사자명부</td>
											<td class="txt_left">성명, 주민번호, 계좌번호, 전화번호</td>
											<td class="txt_left">성명, 주민번호, 계좌번호	</td>
											<td class="txt_left">전화번호</td>
										</tr>
										<tr>
											<td>4</td>
											<td class="txt_left">과학교실강사출근명부</td>
											<td class="txt_left">성명, 주민번호, 계좌번호, 전화번호</td>
											<td class="txt_left">성명, 주민번호, 계좌번호</td>
											<td class="txt_left">전화번호</td>
										</tr>
										<tr>
											<td>5</td>
											<td class="txt_left">전국학생과학발명품 경진대회심사위원</td>
											<td class="txt_left">성명, 소속, 전공</td>
											<td class="txt_left">성명, 소속</td>
											<td class="txt_left">전공</td>
										</tr>
										<tr>
											<td>6</td>
											<td class="txt_left">회원목록DB	</td>
											<td class="txt_left">아이디, 성명, 생년월일, 주소, 전화번호, 이메일</td>
											<td class="txt_left">아이디, 성명, 생년월일</td>
											<td class="txt_left">주소, 전화번호, 이메일</td>
										</tr>
										<tr>
											<td>7</td>
											<td class="txt_left">자원봉사자관리</td>
											<td class="txt_left">성명, 주민번호, 주소, 전화번호, 이메일</td>
											<td class="txt_left">성명, 주민번호, 전화번호</td>
											<td class="txt_left">주소, 이메일</td>
										</tr>
										<tr>
											<td>8</td>
											<td class="txt_left">전국과학전람회심사위원명단</td>
											<td class="txt_left">성명, 소속, 전공</td>
											<td class="txt_left">성명, 소속	</td>
											<td class="txt_left">전공</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //table -->
							<!-- //제6조 -->
							<!-- 제7조 -->
							<h4 class="tit2">제7조(개인정보 파기 절차 및 방법)</h4>
							<p class="txt mb30">개인정보 처리 목적이 달성된 경우에는 원칙적으로 지체 없이 해당 개인정보를 파기합니다. 파기 기한 및 방법은 다음과 같습니다.</p>
							<ul class="dot_list mb55">
								<li class="dot">파기기한 : 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</li>
								<li class="dot">파기방법 : 전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하고, 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</li>
							</ul>

							<!-- //제7조 -->
							<!-- 제8조 -->
							<h4 class="tit2">제8조(개인정보 안전성 확보 조치)</h4>
							<p class="txt">노원수학문화관은 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적, 관리적, 물리적 조치를 하고 있습니다.</p>
							<ul class="dot_list2 ty mb30">
								<li class="dot">내부관리계획 수립·시행<br>개인정보의 안전한 처리를 위해 내부관리계획을 수립하여 시행하고 있습니다.</li>
								<li class="dot">개인정보의 암호화<br>개인정보는 암호화되어 저장 및 관리되고 있습니다.</li>
								<li class="dot">해킹 등에 대비한 기술적 대책<br>방화벽, 침입방지시스템 등 정보보호시스템을 이용하여 해킹 등에 의한 개인정보 유출 및 훼손을 방지하고 있습니다.</li>
								<li class="dot">개인정보에 대한 접근 제한<br>개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여, 변경, 말소를 통해 개인정보에 대한 접근통제를 하고 있습니다.</li>
								<li class="dot">데이터베이스시스템 접속기록 보관<br>데이터베이스시스템에 접속한 기록을 최소 6개월 이상 보관하여 관리하고 있습니다.</li>
								<li class="dot">문서보안을 위한 잠금장치 사용<br>개인정보가 포함된 서류는 잠금장치가 있는 캐비닛에 보관하여 관리하고 있습니다.</li>
								<li class="dot">비인가자에 대한 출입 통제<br>개인정보를 보관하고 있는 물리적 장소를 별도로 두고 이에 대해 출입통제 절차를 수립하여 운영하고 있습니다.</li>
							</ul>
							<div class="table3 mb55">
								<table>
									<caption>파일별 안전성 확보조치 내용에 관한 표입니다. -순번, 파일명, 안전성 확보조치</caption>
									<colgroup>
										<col style="width:10%">
										<col style="*">
										<col style="width:30%">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">순번</th>
											<th scope="col">파일명</th>
											<th scope="col">안전성 확보조치</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>전국과학전람회수상자목록</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>2</td>
											<td>전국학생과학별명품경진대회수상자목록</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>3</td>
											<td>과학교실자원봉사자명부</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>4</td>
											<td>과학교실강사출근명부</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>5</td>
											<td>전국학생과학발명품경진대회심사위원</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>6</td>
											<td>회원목록DB</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>7</td>
											<td>자원봉사자관리</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
										<tr>
											<td>8</td>
											<td>전국과학전람회심사위원명단</td>
											<td class="txt_left">파일암호화, 해킹 대비 기술적 대책, 접근제한, 문서보안을 위한 잠금장치 사용, 비인가자 출입통계</td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- //제8조 -->
							<!-- 제9조 -->
							<h4 class="tit2">제9조(권익침해 구제방법)</h4>
							<div class="ex_txt mb15">
								<p><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.</p>
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
							<!-- //제9조 -->
							<!-- 제 10조 -->
							<h4 class="tit2">제10조(개인정보보호 (분야별)책임자 및 담당자 연락처)</h4>
							<ul class="dot_list2 ty mb30">
								<li class="dot">개인정보보호 책임자: 김기영 ☎ 042-601-7865 / mig40@msip.go.kr</li>
								<li class="dot">개인정보보호 담당자: 김정희 ☎ 042-601-7869 / kate@msip.go.kr</li>
							</ul>
							<div class="table3 mb20">
								<table>
									<caption>개인정보보호 (분야별)책임자 및 담당자 연락처 확인 표입니다.-순번, 파일명, 분야별책임관, 담당자정보</caption>
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
											<th rowspan="2" scope="col">파일명</th>
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
											<td>전국과학전람회수상자목록</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>전영필</td>
											<td>042-601-7942</td>
											<td>phil7145@msip.go.kr</td>
										</tr>
										<tr>
											<td>2</td>
											<td>전국학생과학별명품경진대회수상자목록</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>전영필</td>
											<td>042-601-7942</td>
											<td>phil7145@msip.go.kr</td>
										</tr>
										<tr>
											<td>3</td>
											<td>과학교실자원봉사자명부</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>진대성</td>
											<td>042-601-7736</td>
											<td>fullcourse@msip.go.kr</td>
										</tr>
										<tr>
											<td>4</td>
											<td>과학교실강사명부</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>진대성</td>
											<td>042-601-7736</td>
											<td>fullcourse@msip.go.kr</td>
										</tr>
										<tr>
											<td>5</td>
											<td>전국학생과학별명품경진대회심사위원</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>전영필</td>
											<td>042-601-7942</td>
											<td>phil7145@msip.go.kr</td>
										</tr>
										<tr>
											<td>6</td>
											<td>회원목록DB</td>
											<td>스마트정보화팀</td>
											<td>김기영</td>
											<td>김정희</td>
											<td>042-601-7869</td>
											<td>kate@msip.go.kr</td>
										</tr>
										<tr>
											<td>7</td>
											<td>자원봉사자관리</td>
											<td>관람기획과</td>
											<td>오재협</td>
											<td>김순희</td>
											<td>042-601-7910</td>
											<td>tnsgml@msip.go.kr</td>
										</tr>
										<tr>
											<td>8</td>
											<td>전국과학전람회심사위원명단</td>
											<td>교육문화과</td>
											<td>윤용현</td>
											<td>김정호</td>
											<td>042-601-7739</td>
											<td>elecdream@msip.go.kr</td>
										</tr>
									</tbody>
								</table>
							</div>
							<p class="txt mb55">정보주체는 노원수학문화관의 서비스를 이용하면서 개인정보보호관련문의, 불만처리, 피해구제등에 관한 사항을 개인정보보호 책임자 및 담당부서로 문의할 수 있습니다. 노원수학문화관은 정보주체의 문의에 대해 지체없이 답변 및 처리해 드리겠습니다.</p>
							<!-- //제 10조 -->
							<!-- 제 11조 -->
							<h4 class="tit2">제11조(개인정보처리방침 변경)</h4>
							<p>이 개인정보처리방침은 2014. 4. 23.부터 적용됩니다.</p>
							<!-- //제 11조 -->
							<input type="hidden" id="dt" value="2014. 4. 23" />