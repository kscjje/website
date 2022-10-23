<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>

<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<%
 /**
  * @Class Name : myInforRegist.jsp
  * @Description : 회원 정보  수정 페이지
  * @Modification Information
  * @
  * @  수정일              수정자           수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.12    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.12
  *  @version 1.0
  *  @see 
  *
  */
%>



<%
	String strJusoKey = EgovProperties.getProperty("juso.go.kr.key");
%>

<script>
	
		var element;
		
		$(function() {
			
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
			
		});	
		
		function fn_passwd_check() {
			var newPassword = $('#new_password').val();
			var msg = passwdValidationNew(newPassword);
			if (msg == '사용가능한 비밀번호 입니다.') {
				$('#errPass').removeClass('red').addClass('green').html('사용가능한 비밀번호 입니다.');
				return true;
			} else {
				$('#errPass').removeClass('green').addClass('red').html(msg);
		        $('#new_password').focus();
		        return false;
			}
		}
		
		function fn_passwd_confirm() {
			if ($('#new_password').val()  != $('#new_password2').val() ) {
				$('#errPass2').html("비밀번호와 비밀번호 확인값이 일치하지 않습니다.");
				$('#new_password2').focus();
				return false;
			} else {
				$('#errPass2').html("");
				return true;
			}
		}
		
		function fn_address() {
			
			$('#addressResultDiv').text("").addClass("red").removeClass("green");
			
			var varPostNum = $("#postNum").val();
			var varAddr1   = $("#addr1").val();
			var varAddr2   = $("#addr2").val();
			
			if ((varPostNum === undefined) || (varPostNum == null) || (varPostNum == '')) {
				$('#addressResultDiv').text("주소를 검색해주세요").addClass("red").removeClass("green");
				return false;
			}				
			
			if ((varAddr1 === undefined) || (varAddr1 == null) || (varAddr1 == '')) {
				$('#addressResultDiv').text("주소를 검색해주세요").addClass("red").removeClass("green");
				return false;
			}
			
			if ((varAddr2 === undefined) || (varAddr2 == null) || (varAddr2 == '')) {
				$('#addressResultDiv').text("상세주소를 입력해주세요").addClass("red").removeClass("green");
				return false;					
			}				
			
			return true;		
			
		}
		
		function fn_email() {
			
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
			
		}
		
		function fn_save() {
	
			var varEmail = $("#email").val().trim();
			$("#email").val(varEmail);		
			
			var varEmail2 = $("#mail2").val().trim();
			$("#mail2").val(varEmail2);				
			
			var varAddr2 = $("#addr2").val().trim();
			$("#addr2").val(varAddr2);
			
			var flag = true;
			
			$('#errHp').html("");
			$('#errPass').html("");
			$('#errPass2').html("");
			
			var f = document.memberVO;
	
			if (!fn_passwd_confirm()) {
				flag = false;
			}
		
			if (!fn_passwd_check()) {
				flag = false;
			}
			
			var varHp = $("#hp").val();
			
			if ((varHp === undefined) || (varHp == null) || (varHp == '')) {
				$('#errHp').html("휴대폰 번호를 입력하세요");
				return false;
			}		
			
			if (!fn_email()) {
				flag = false;
			}				
			
			if (!fn_address()) {
				flag = false;
			}		
	
			var varSmsYn = $("#smsYn").is(":checked");
			if (varSmsYn) {
				$("#smsYn").val("Y");
			} else {
				$("#smsYn").val("N");
			}
			
			if (flag) {
				
				if (confirm("회원 정보를 수정합니다.")) {
					document.memberVO.submit();	
				}
				
			}
			
		}
	
		function fn_hphone_change() {
			var prsonCertiWin = window.open('/web/common/prsoncerti/prsonCertiPop', 'prsonCertiWin', 'width=650, height=650, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=100, top=50');
			if (!prsonCertiWin)
			{
				alert('** 휴대전화 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
				return false;
			}
		}
	
		function fn_hphone_complete(tel) {
			
			$('#hp').val(tel);

			$('#btn_hphone').text("변경취소").addClass("btn_ty_m2_c4").removeClass("btn_ty_m2_c3");

		}
		
		function fn_sns_alert() {
			var existInfo =  document.getElementById('snsVO.snsRegistkind');
			if (existInfo.value != "") {
				alert("간편 로그인은 1개만 설정이 가능합니다.\n연결된 간편 로그인을  해제하신 후 연결하기 버튼을 클릭해 주시기 바랍니다.");
			}
		}
	  
	  	function fn_sns_cancel(snsKind) {
	  		
		  $.ajax({
		      url: './snsJoinCancel',
		      data : { snsKind : snsKind },
		      type : "POST",
		      dataType : "json",
		      beforeSend : function(xhr) {
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
		    success: function(data) {
		    	  if ( data.result.code == 'SUCCESS' ) {
		    		  	var obj = $('#SNS_' + snsKind + '_DIV');

		    		  	obj.find("li").eq(1).find("input").remove();
		    		  	obj.find("li").eq(1).find("button").show();
		    		  	obj.find("li").eq(2).find("button").remove();

		    		  	var existInfo =  document.getElementById('snsVO.snsRegistkind');
		    		  	existInfo.value = "";

						alert("연결 해제 되었습니다.");

					} else {
						alert(data.result.msg);
					}
		      },
		      error: function(data) {
		    	  alert("Server Error");
		      }
		    });
	  	}
	  	
	  	function fn_email_check() {
	  		
			var type = "email";
			var email = $('#email').val();

			var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식

			if (email =="") {
				alert("이메일 주소를 입력해 주세요.");
				$('#email').focus();
			} else if (!emailRule.test(email)) {
				alert("이메일 주소가 형식에 맞지 않습니다.");
				$('#email').focus();
			} else if (email == $('#email_dup_check').val()) {
				$('#divModalResult').text("사용 하실 수 있는 이메일 입니다.").addClass("green").removeClass("red");
			} else if (email == $('#email_origin').val()) {
				$('#divModalResult').text("사용 하실 수 있는 이메일 입니다.").addClass("green").removeClass("red");
			} else {
				$.ajax({
				      url: '../../member/join/memDupliCheckAjax',
				      data : {'email' : email,'type' : type ,'piPAuthkey':$('#piPAuthkey').val()  },
				      type : "POST",
				      dataType : "json",
				      beforeSend : function(xhr) {
			                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			            },
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	$('#divModalResult').text(data.result.msg).removeClass("green").addClass("red");
					        } else {
					        	$('#divModalResult').text(data.result.msg).addClass("green").removeClass("red");
					        	$('#email_dup_check').val( email );
					         }
				      },
				      error : function(data) {
				    	  alert("Server Error.");
				      }
				 });
			}
		}
		
	  	$(document).ready(function() {
	  		
			<c:if test="${!empty changePhoneNum}">
				//fn_hphone_complete("<c:out value='${changePhoneNum}'/>");
				fn_hphone_complete("<tags:HpTelShow hpval='${changePhoneNum}'/>");
			</c:if>
			
	  	});

		function fn_cancel() {
			
			if (confirm("취소하시겠습니까?")) {
				top.location.href="/web/main";
			}
			
		}
		
		function fn_mailserver_save(obj) {
			
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
		
		function fn_juso_layer_pop() {
			$('#modalJusoSearch').modal();
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
		
		function fn_checkSearchedWord(obj) {
			return true ;
		}		
		
		function fn_hpNice_Change() {
			
			var varBtnHpone = $("#btn_hphone").text();
			
			if (varBtnHpone == "변경하기") {
				
				var varMyPiPAuthType = "<c:out value='${memberVO.piPAuthtype}' />";
				if (varMyPiPAuthType == "3001") {
					alert("어린이 회원은 휴대폰 변경을 할 수 없습니다.");
					return;
				}
				
				$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click();
				
			} else if (varBtnHpone == "변경취소") {
			
				var varOrgHpNum = $("#orgMyHpNum").val();
				$("#hp").val(varOrgHpNum);
				$('#btn_hphone').text("변경하기").removeClass("btn_ty_m2_c4").addClass("btn_ty_m2_c3");
				
			}

		}
		
</script>

			<c:url var="namefact_url" value="/web/namefact/memChangePhone">
				<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
				<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
				<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
				<c:param name="add_css" value="${add_css_url}"/>
				<c:param name="mode" value="button"/>
				<c:param name="inputVal" value=""/>
			</c:url>
			
			<iframe id="iframe_namefact" src="<c:out value='${namefact_url}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>

			<form:form commandName="memberVO" name="memberVO" id="memberVO" action="./myInforSave" method="post">
	
				<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit mb20">개인정보 수정</h3>
							
							<form:hidden path="snsVO.snsRegistkind" />
							<form:hidden path="piPAuthkey"/>						
							
							<input type="hidden" name="email_dup_check" id="email_dup_check" value="<c:out value="${memberVO.email}" />"/>
							<input type="hidden" name="email_origin"    id="email_origin"    value="<c:out value="${memberVO.email}" />"/>
							<input type="hidden" name="cert_type"       id="cert_type"       value="hpchange"/>
							<input type="hidden" name="birthDate"       id="birthDate"       value="<c:out value="${memberVO.birthDate}" />"/>
							<input type="hidden" name="id"              id="id"              value="<c:out value="${memberVO.id}" />"/>
	
							<input type="hidden" id="orgMyHpNum"        value="<tags:HpDecrypt param='${memberVO.hp}'/>"/>
											
							<div class="mypage_wrap bor">
								<!-- signup -->
								<div class="signup2">
									<p class="stit">개인정보 입력 (<strong class="red">*</strong> 필수입력)</p>
									<div class="table2 responsive-type mb50">
										<table>
											<caption>필수 입력 정보를 입력하는 표입니다.</caption>
											<colgroup>
												<col style="width:20%">
												<col style="width:80%">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row">이름</th>
													<td><c:out value="${memberVO.memNm}"/></td>
												</tr>
												<tr>
													<th scope="row">아이디 <strong class="red">*</strong></th>
													<td><c:out value="${memberVO.id}"/></td>
												</tr>
												<tr>
													<th scope="row" class="vtop"><label for="new_password">비밀번호<strong class="red">*</strong></label></th>
													<td>
														<input type="password" id="new_password" name="new_password" placeholder="비밀번호를 입력해주세요." />
														
														<p class="green" id="errPass"></p>
														
													</td>
												</tr>
												<tr>
													<th scope="row" class="vtop"><label for="new_password2">비밀번호 확인<strong class="red">*</strong></label></th>
													<td>
														<input type="password" id="new_password2" name="new_password2" placeholder="비밀번호를 재입력해주세요." />
														
														<p id="errPass2" class="errMsg red"></p>
													</td>
												</tr>
												
												<tr>
													<th scope="row">휴대전화번호<strong class="red">*</strong></th>
													<td>
														
														<input type="text" style="width:220px;" id="hp" name="hp" readonly="true" value="<tags:HpDecrypt param='${memberVO.hp}'/>" />
														<button type="button" onclick="fn_hpNice_Change(); return false;" id="btn_hphone" class="btn_ty_m2_c3" title="새창 열림">변경하기</button>
														<p id="errHp" class="errMsg red"></p>
														
													</td>
												</tr>											
												
												<tr>
													<th scope="row">이메일<strong class="red">*</strong></th>
													<td>
														<div class="w_box">
															<div class="w33d">
															
																<label for=email class="hidden">이메일 주소<strong class="red">*</strong></label>
																<input type="text" id="email" name="email" title="이메일" maxlength="1000" placeholder="이메일 주소" value="<tags:EmailDivideDecrypt param1='1' param2='${memberVO.email}'/>" />
																
															</div>
															<div class="w33d">
																<label for="mail1" class="hidden">도메인 선택</label>
																<select id="mail1" name="mail1" onchange="fn_mailserver_save(this); return false;">
																	<option value=''>선택</option>
																	
																	<c:forEach items="${mailServerGrpCdList}" var="item" varStatus="status">
																		<option value="<c:out value='${item.cd}'/>"><c:out value='${item.cdNm}'/></option>
																	</c:forEach>
																	
																</select>
															</div>
															<div class="w33d">
																<label for="mail2" class="hidden">도메인 직접입력</label>
																<input type="text" id="mail2" name="mail2" placeholder="직접입력" value="<tags:EmailDivideDecrypt param1='2' param2='${memberVO.email}'/>" />
															</div>
																												
														</div>
														
															<p id="emailResultDiv"></p>
															<form:errors path="email" cssClass="red" />												
														
													</td>
												</tr>
												
												<tr>
													<th scope="row"><label for="postNum">주소<strong class="red">*</strong></label></th>
													<td>
														<div class="w_box">
															<div class="w150">
																<input type="text" id="postNum" name="postNum" placeholder="" readonly value="<c:out value='${memberVO.postNum}'/>" />
															</div>
															<div>
																<a href="#" class="btn_ty_m2_c3" onclick="fn_juso_layer_pop(); return false;">주소 찾기</a>
															</div>
														</div>
														<div class="w_box mt10">
															<div class="w50">
																<label for="addr1" class="hidden">주소 검색</label>
																<input type="text" id="addr1" name="addr1" placeholder="주소 검색해주세요" value="<tags:Addr1Decrypt param='${memberVO.addr1}'/>" />
															</div>
															<div class="w50">
																<label for="addr2" class="hidden">상세주소</label>
																<input type="text" id="addr2" name="addr2" placeholder="상세주소를 입력해주세요." value="<tags:Addr2Decrypt param='${memberVO.addr2}'/>" />
															</div>
															
														</div>
														
														<p id="addressResultDiv"></p>
														
													</td>
												</tr>											
												
												<tr>
													<th scope="row">SMS 수신동의<strong class="red">*</strong></th>
													<td>
														
														<input type="checkbox" id="smsYn" name="smsYn" <c:if test="${empty memberVO.smsYn or memberVO.smsYn eq 'Y'}"> checked='true' </c:if> />
														<label for="smsYn">노원수학문화관에서 제공하는 알림서비스 이용에 동의합니다.</label>												
														<p id="smsYnDiv"></p>
														
													</td>
												</tr>												
												
											</tbody>
										</table>
										<!-- //table -->
									</div>
									<!-- 필수 입력 정보 -->
								</div>
								<!-- //mypage_wrap -->
							</div>
						
						
						<!-- //join_wrap -->
						<ul class="btn_area long">
							<li><a href="javascript:fn_save();" class="btn_ty_m_c1">회원정보 수정</a></li>
							<li><a href="javascript:fn_cancel();" class="btn_ty_m3_c3">취소</a></li>
						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->
			
			</form:form>
			
			<div class="modal" id="modalJusoSearch">
				<div class="modal-dialog" style="max-width:760px;">

					<div class="modal-content">
						<div class="modal-title">
							<h4>우편번호 찾기</h4>
							<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
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

							<!-- //search_wrap -->
							<div class="table2 ty scroll" id="jusoSearchResult">
							</div>
							<div class="paginate" id="jusoPageApi">
							</div>
							<!-- //paginate -->
						</div>
					</div>

				</div>
			</div>
			<!-- //팝업 -->
						
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>			
