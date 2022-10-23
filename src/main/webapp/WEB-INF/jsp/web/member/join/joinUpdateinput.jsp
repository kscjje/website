<%
 /**
  * @Class Name : joinUpdateinput.jsp
  * @Description : 회원가입 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.14    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.14
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
	<div class="sub_cont" id="container">
			<div class="inner">
				<h3 class="tit mb20">노원수학문화관 통합예약<br/>누리집 최초 본인인증</h3>
				<p class="desc mb100">새로운 보안체계 적용으로 본인인증을 통해 비밀번호를 재설정하고 있습니다.<br>통합예약을 안전하게 이용하기 위한 꼭 필요한 절차 입니다!</p>
				<div class="join_wrap bor">
					<!-- step -->
					<ul class="step">
						<li ><!-- 현재 on -->
							<dl>
								<dt>1</dt>
								<dd>최초 본인인증</dd>
							</dl>
						</li>
						<li class="on">
							<dl>
								<dt>2</dt>
								<dd>정보입력</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>3</dt>
								<dd>수정완료</dd>
							</dl>
						</li>
					</ul>
					<!-- //step -->
					<!-- signup -->
<form:form commandName="memberVO" name="memberVO" id="memberForm" action="./joinUpdateSave" method="post">
<form:hidden path="piAuthkey"/>
<form:hidden path="piAuthtype"/>
<form:hidden path="memNo"/>
<form:hidden path="memNm"/>
<form:hidden path="gender"/>
<form:hidden path="hp"/>
<form:hidden path="birthDate"/>
<form:hidden path="piPAuthkey"/>
<form:hidden path="piPAuthtype"/>
<input type="hidden" id="type" name="type" value="<c:out value="${type}"/>"  />
<input type="hidden" id="dupCheckId" name="dupCheckId" value="<c:out value="${memberVO.id}"/>"/>
<input type="hidden" id="dupCheckEmail" name="dupCheckEmail" value="<c:out value="${memberVO.email}"/>"/>
<input type="hidden" name="email_origin" id="email_origin" value="<c:out value="${memberVO.email }" />"/>
					<div class="signup2">
						<p class="stit">필수 입력 정보</p>
						<div class="table2 responsive-type mb50">
							<table>
								<caption>필수 입력 정보를 입력하는 표입니다.</caption>
								<colgroup>
									<col style="width:20%">
									<col style="width:80%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="vtop">이름</th>
										<td class="b">
											<c:out value="${memberVO.memNm }" />
											<form:errors path="memNm" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="id">아이디</label></th>
										<td>
<c:choose>
	<c:when test="${empty memberVO.id}">
											<div class="w_box">
												<div class="w384">
													<form:input path="id"  placeholder="아이디를 입력해주세요." title="아이디" maxlength="20"/>
												</div>
												<div>
													<button type="button" onclick="dupliCheck('id')" class="btn_ty_m2_c3">중복확인</button>
												</div>
											</div>
											<p id="idResultDiv"></p>
											<p>※ 아이디는 4 ~ 20 자리로 알파벳 소문자 와 숫자만 사용하실 수 있습니다.</p>
	</c:when>
	<c:otherwise>
												<c:out value="${memberVO.id }" />
												<form:hidden path="id" />

	</c:otherwise>
</c:choose>
											<form:errors path="id" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row" ><label for="pass1">비밀번호</label></th>
										<td>
											<input name="pw" type="password" id="pw" placeholder="비밀번호를 입력해주세요." onkeyup="validMember.passCheck()" maxlength="20" title="비밀번호">
											<p id="errPass" class="errMsg" ></p>
											<p>※ 영문, 숫자, 특수문자 중 2종류 이상 8 ~ 20자 이내로 입력해 주세요.</p>
											<p>※ 사용하실 수 없는 비밀번호
											<p>- 아이디와 3자리 이상이 포함된 경우</p>
											<p>- 핸드폰의  마지막 4자리 또는 8자리가 포함된 경우</p>
											<p>- 생일의 년도 또는 생일의 월일 4자리가 포함된 경우</p>
											<p>- 3자 이상의 연속 또는 반복 문자 및 숫자가 포함된 경우</p>
											<form:errors path="pw" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row" ><label for="pass2">비밀번호 확인</label></th>
										<td>
											<input type="password" id="pw2" name="pw2" placeholder="비밀번호를 재입력해주세요." onkeyup="validMember.passDiff()" maxlength="20" title="비밀번호 확인">
											<p id="errPass2" class="errMsg" ></p>
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">성별</th>
										<td>
											<c:choose><c:when test="${ memberVO.gender eq 'F' or memberVO.gender eq '2'}">여자</c:when><c:when test="${ memberVO.gender eq 'M' or memberVO.gender eq '1'}">남자</c:when></c:choose>
											<form:errors path="gender" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">생년월일</th>
										<td>
											<div class="mb15 b"><c:out value="${memberVO.birthDate }" /></div>
											<form:errors path="birthDate" cssClass="red" />
											<div class="radio_box">
												<label><input type="radio" class="radio" name="birthSec" value="S" checked><em></em><span>양력</span></label>
												<label><input type="radio" class="radio" name="birthSec" value="M"><em></em><span>음력</span></label>
											</div>
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop"><label for="phone">휴대전화번호</label></th>
										<td class="b"> <tags:HpTelShow hpval="${memberVO.hp}"/>
											<form:errors path="hp" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row">이메일</th>
										<td>
											<div class="w_box">
												<div class="w100">
													<label for="email" class="hidden">이메일</label>
													<form:input path="email" title="이메일" maxlength="1000"/>
												</div>
												<div>
													<button type="button" onclick="dupliCheck('email')" class="btn_ty_m2_c3">중복확인</button>
												</div>
											</div>
											<p id="emailResultDiv"></p>
											<form:errors path="email" cssClass="red" />
										</td>
									</tr>
									<tr>
										<th scope="row">거주지역</th>
										<td>
											<label for="resdncGbn" class="hidden">거주지역 선택</label>
											<form:select path="resdncGbn" class="w384" onchange="validMember.resdncGbn()">
												<option value="">선택</option>
												<form:options items="${gbnList }" itemLabel="cdNm" itemValue="cd"></form:options>
											</form:select>
											<form:errors path="resdncGbn" cssClass="red" />
											<p id="resMsg"></p>
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">정보수신</th>
										<td class="pd30">
											<div class="radio_box mb30">
												<span class="r_tit">SMS</span>
												<label><form:radiobutton path="smsYn" class="radio" value="Y"/><em></em><span>받음</span></label>
												<label><form:radiobutton path="smsYn" class="radio" value="N"/><em></em><span>받지 않음</span></label>
											</div>
											<div class="radio_box">
												<span class="r_tit">이메일</span>
												<label><form:radiobutton path="emailYn" class="radio" value="Y"/><em></em><span>받음</span></label>
												<label><form:radiobutton path="emailYn" class="radio" value="N"/><em></em><span>받지 않음</span></label>
											</div>
											<p>※ 예약 내용 수신 등의 필수 내용은 수신여부와 관계없이 발송됩니다.</p>
										</td>
									</tr>

								</tbody>
							</table>
							<!-- //table -->
						</div>
						<!-- 필수 입력 정보 -->

						<!-- 추가 입력 정보 -->
						<p class="stit">추가 입력 정보</p>
						<div class="table2 responsive-type">
							<table>
								<caption>추가 입력 정보를 입력하는 표입니다.</caption>
								<colgroup>
									<col style="width:20%">
									<col style="width:80%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="vtop">기관(학교)명</th>
										<td>
											<div class="w100">
												<label for="comp" class="hidden">기관명</label>
												<input type="text" name="resdncOrgname" id="comp" maxlength="50" value="<c:out value="${memberVO.resdncOrgname }"/>" title="기관명"/>
											</div>
										</td>
									</tr>
									<tr>
										<th scope="row" class="vtop">관심정보</th>
										<td class="pd30">
											<div class="chk_box">
<c:forEach var="item" items="${intrList}" varStatus="status">
										<input type="checkbox" id="intr_${status.index }" name="instVO.intrstKind" value="<c:out value="${item.intrstKind }"/>"/>
										<label for="intr_${status.index }"><c:out value="${item.intrstKindNm }"/></label>
</c:forEach>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							<!-- //table -->
						</div>
						<!-- //추가 입력정보 -->
					</div>
</form:form >
					<!-- //signup -->
				</div>
				<!-- //join_wrap -->
				<ul class="btn_area long">
					<li><a href="javascript:void(0)" onclick="fn_submit('N')"  class="btn_ty_m_c1">저장</a></li>
				</ul>
				<!-- //btn_area -->
			</div>

			<!-- //inner -->
		</div>
		<!-- //sub_cont -->

		<script type="text/javascript">
		$(function() {

			$('#email').on('keyup',function() {
				$('#emailResultDiv').html("");
			});

			$('#id').on('keyup',function() {
				$('#idResultDiv').html("");
			});
		})


		var validMember = {
			passDiff: function() {
				var pass = $('#pw').val();
				if (pass != $('#pw2').val()) {
					$('#errPass2').addClass('red').text("비밀번호와 비밀번호 확인값이 일치하지 않습니다.");
					$('#pw2').focus();
					return false;
				} else {
					$('#errPass2').addClass('red').text('');
					return true;
				}
			},
			passCheck : function() {
				var newPassword = $('#pw').val();
				var msg = passwdValidation(newPassword);
				if (msg == '사용가능한 비밀번호 입니다.') {
					$('#errPass').removeClass('red').addClass('green').html('사용가능한 비밀번호 입니다.');
					return true;
				} else {
					$('#errPass').removeClass('green').addClass('red').html(msg);
		            $('#pw').focus();
		            return false;
				}
			},
			userid:function() {
				var userid = $('#id').val();
				if (userid =="") {
					$('#idResultDiv').text("아이디를 입력해 주세요.").addClass("red").removeClass("green");
					$('#id').focus();
					return false;
				} else if (!/^[a-z0-9]{4,20}$/.test(userid)) {
					$('#idResultDiv').text("아이디가 형식에 맞지 않습니다.").addClass("red").removeClass("green");
					$('#id').focus();
					return false;
				} else {
					return true;
				}
			},
			idCheck:function() {
				if ($('#id').val() != $('#dupCheckId').val()) {
					$('#idResultDiv').text("아이디 중복확인이 필요합니다.").addClass("red").removeClass("green");
					$('#id').focus();
					return false;
				} else {
					return true;
				}
			},
			email:function() {
				var email = $('#email').val();
				var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;//이메일 정규식

				if (email =="") {
					$('#emailResultDiv').addClass('red').text("이메일 주소를 입력해 주세요.")
					$('#email').focus();
					return false;
				} else if (!emailRule.test(email)) {
					$('#emailResultDiv').addClass('red').text("이메일 주소가 형식에 맞지 않습니다.")
					$('#email').focus();
					return false;
				} else {
					return true;
				}
			},
			emailCheck:function() {
				if ($('#email').val() != $('#dupCheckEmail').val()) {
					$('#emailResultDiv').text("이메일 중복확인이 필요합니다.").addClass("red").removeClass("green");
					$('#email').focus();
					return false;
				} else {
					return true;
				}
			},
			carNo: function() {
				var info1 = $('#carNo1').val();
			},
			resdncGbn : function() {
				var val = $('#resdncGbn').val();
				if (val == '') {
					$('#resMsg').addClass('red').text("거주지역을 선택해 주세요.");
					$('#resdncGbn').focus();
					return false;
				} else {
					$('#resMsg').addClass('red').text("");
					$('#resdncGbn').focus();
					return true;
				}
			},
			chkAll : function() {
				var chkList = [validMember.resdncGbn(),validMember.passDiff(), validMember.passCheck(), validMember.email() ,validMember.userid()];
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
		if (type == "email" && $('#email').val() == $('#email_origin').val()) {
			 $('#emailResultDiv').text("사용가능한 이메일입니다.").addClass("green").removeClass("red");
		} else {
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
			    		   $('#'+type+'ResultDiv').text(data.result.msg).addClass("red").removeClass("green");
				        } else {
				        	 $('#'+type+'ResultDiv').text(data.result.msg).addClass("green").removeClass("red");
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
		}

	}//end of dupliCheck

	function fn_submit(data) {
		var form = $('#memberForm');
		//validation check
		if (!validMember.chkSubmit()) {
			//return
		} else if (confirm('수정 하시겠습니까?')) {
			form.submit();
		}
	}// end of senddata

</script>