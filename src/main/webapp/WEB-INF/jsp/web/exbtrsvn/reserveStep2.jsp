<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : reserveStep2.jsp
  * @Description : 예약 정보 입력 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.19    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.19
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

$(document).ready(function() {
	$('input[type=text]').keyup(function() {
		$('#err_'+$(this).attr("id")).hide();
	});

	$('.phoneCheck').keyup(function() {
		$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
	});
	
	setTimeout("fn_login_check()", 100); //로그인여부 체크
	
	$(document).attr("title", "정보 입력 - 관람예약 | 노원수학문화관");
	
});

function fn_next() {
	
	var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
	var hpRule = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

	var nullObj = "";

	/* 
	if ($('#custNm').val() == "") {
		alert("이름을 입력해 주세요.");
		$('#custNm').focus();
	} else */
	
	if ($('#exbtHp').val() == "") {
		alert("휴대폰번호를 입력해 주세요.");
		$('#exbtHp').focus();
	} else {

		<c:if test="${rsvnMasterVO.target eq '20'}">
		 	if ($('#grpnm').val() == "") {
				if (nullObj == "") nullObj = "grpnm";
				$('#err_grpnm').text("단체명을 입력해 주세요.").show();
		 	}
			if ($('#visitcustNm').val() == "") {
				if (nullObj == "") nullObj = "visitcustNm";
				$('#err_visitcustNm').text("방문 인솔자명을 입력해 주세요.").show();
			}
			if ($('#visitcustHpno').val() == "") {
				if (nullObj == "") nullObj = "visitcustHpno";
				$('#err_visitcustHpno').text("방문 인솔자 연락처를 입력해 주세요.").show();
			} else if (!hpRule.test($('#visitcustHpno').val())) {
				if (nullObj == "") nullObj = "visitcustHpno";
				$('#err_visitcustHpno').text("방문 인솔자 연락처가 형식에 맞지 않습니다.").show();
			}
		</c:if>
		
		/* 
		if ($('#exbtEmail').val() == "") {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_exbtEmail').text("이메일 주소를 입력해 주세요.").show();
		} else if (!emailRule.test($('#exbtEmail').val())) {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_exbtEmail').text("이메일 주소가 형식에 맞지 않습니다.").show();
		}
		*/

		if (nullObj != "") {
			$('#' + nullObj).focus();
		} else {
			////$('body').showLoading();

			if (confirm("관람 정보를 예약합니다.")) {
			
				 $.ajax({
				        type : "POST",
				        url  : './reserveAction',
				        data : $('#rsvnMasterVO').serialize(),
				        dataType: "json",
				        beforeSend : function(xhr) {
				        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
				        },
				        success: function(data) {
				        	
				        	////$('body').hideLoading();
				        	
				        	if (data != null) {
				        		if ((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')) {
				        		} else {
				        			if (data.ERROR_CODE == -1) {
				            			alert(data.ERROR_MSG);
				            			return;
				        			} else if (data.ERROR_CODE == -3) {
				        				alert(data.ERROR_MSG);
										top.location.href = "/web/main";
				        				return;
				        			}
				        		}
				        	}
				        	fn_reserve_success(data);
	
				        }, error: function(jqXHR, exception) {
				        	
				        	////$('body').hideLoading();
				        	
				        	var varStatus = jqXHR.status;
	
				        	if (varStatus == "403") {
				        		alert("접근 권한이 없습니다. 로그인을 다시 확인해 주세요.");
				        		top.location.replace("/web/main");
				        	} else {
				        		alert("시스템 오류 입니다 .");
				        	}
				        }
				    });
			}

		}


	}
}

function fn_reserve_success(data) {

	 if (data.result.resultCd == "SUCCESS") {
		 $('#rsvnIdx').val( data.result.resultMsg );
    	 $('#rsvnMasterVO').attr("action" , "./reserveResult");
    	 $('#rsvnMasterVO').submit();
    } else {
		alert(data.result.resultMsg);
    }
}
</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		<div class="join_wrap bor">
			<!-- step -->
			
			<ul class="step step3">
				<li>
					<dl>
						<dt>1</dt>
						<dd>일자 선택</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>인원입력 및 동의</dd>
					</dl>
				</li>
				<li class="on">
					<!-- 현재 on -->
					<dl>
						<dt>3</dt>
						<dd>예약자 정보 입력</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>4</dt>
						<dd>예약 완료 안내</dd>
					</dl>
				</li>
			</ul>			
			
			<!-- 
			<ul class="step step4">
				<li>
					<dl>
						<dt>1</dt>
						<dd>회차 선택</dd>
					</dl>
				</li>
				<li >
					<dl>
						<dt>2</dt>
						<dd>안내 및 주문</dd>
					</dl>
				</li>
				<li class="on">
					<dl>
						<dt>3</dt>
						<dd>정보 입력</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>4</dt>
						<dd><c:choose><c:when test="${baseDataVO.feeType eq '1001' }">결제 대기</c:when><c:otherwise>예약 완료</c:otherwise></c:choose></dd>
					</dl>
				</li>
			</ul>
			-->
			
			<!-- //step -->
			<!-- signup2 -->
<form:form commandName="rsvnMasterVO" name="rsvnMasterVO" id="rsvnMasterVO" action="./reserveAction" method="post">

<form:hidden path="target" />
<form:hidden path="exbtSeq" />
<form:hidden path="timeseq" />
<form:hidden path="stdseq" />
<form:hidden path="ymd" />
<form:hidden path="webId" />
<form:hidden path="memNo" />
<form:hidden path="hpcertno" />
<form:hidden path="memType" />
<form:hidden path="rsvnIdx" />

<form:hidden path="exbtVistnmpr" />
<form:hidden path="visitnum" />

<form:hidden path="startTime" value="${timeVO.sTime}"/>

			<div class="signup2">
				<!--  <p class="stit">예약자 정보 입력</p> -->
				<p class="stit">예약자 정보를 확인해 주십시오<span class="tb_txt"><strong class="red">*</strong>는 필수 입력 정보 입니다.</span></p>
				<div class="table2 responsive-type mb50">
					<table>
						<caption>예약자 정보를 입력하는 표입니다.</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<tr style="display:none;">
								<th scope="row"><label for="name">아이디</label></th>
								<td><c:choose><c:when test="${rsvnMasterVO.memType eq '1001'}"><c:out value="${rsvnMasterVO.webId }" /></c:when><c:otherwise>비회원</c:otherwise></c:choose></td>
							</tr>
							<tr>
								<th scope="row"><label for="custNm">예약자명</label></th>
								<td><c:out value="${userVO.name }"/><form:hidden path="custNm" value="${userVO.name}"/></td>
							</tr>
							
							<c:choose>
								<c:when test="${rsvnMasterVO.target eq '20'}">
									<tr>
										<th scope="row"><strong class="red">*</strong><label for="name">단체,학교, 기관명</label></th>
										<td><form:input path="grpnm"  placeholder="단체명을 입력해주세요." title="단체명을 입력해주세요." maxlength="50"/>
											<p id="err_grpnm" class="red" style="display:none">단체명을 입력해주세요.</p>
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="exbtHp">예약자  연락처</label></th>
										<td>
											<tags:HpDecrypt param="${userVO.ihidNum}"/>
											<form:hidden path="exbtHp" value="${userVO.ihidNum}"/>
										</td>
									</tr>									
									<tr>
										<th scope="row"><strong class="red">*</strong><label for="visitcustNm">방문 인솔자명</label></th>
										<td><form:input path="visitcustNm"  placeholder="방문 인솔자 이름을 입력해주세요." title="방문 인솔자 이름을 입력해주세요." value="${userVO.name}" maxlength="50"/>
											<p id="err_visitcustNm" class="red" style="display:none">방문 인솔자명을 입력해주세요.</p>
										</td>
									</tr>
									<tr>
										<th scope="row"><strong class="red">*</strong><label for="visitcustHpno">방문 인솔자 연락처</label></th>
										<td>
											<input type="text" name="visitcustHpno" id="visitcustHpno"  placeholder="‘-’ 을 빼고 입력 해 주십시오." title="방문 인솔자 연락처를 입력해주세요." value="<tags:HpDecrypt param='${userVO.ihidNum}'/>" maxlength="13" class="phoneCheck"/>
											<p id="err_visitcustHpno" class="red" style="display:none">방문 인솔자 연락처를 입력해주세요.</p>
										</td>
									</tr>
								</c:when>
							</c:choose>

							<tr style="display:none;">
								<th scope="row">이메일</th>
								<td>
									<form:input path="exbtEmail"  placeholder="이메일주소를 입력 해 주십시오." title="이메일주소를 입력 해 주십시오." maxlength="50"/>
									<p id="err_exbtEmail" class="red" style="display:none">이메일주소를 입력해주세요.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>
				<!-- //예약자 정보 입력 -->
				<!-- <p class="stit">예약 정보 확인</p> -->
				<div class="table2 responsive-type mb50" style="display:none;">
					<table>
						<caption>예약 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">관람 제목</th>
								<td class="b">
									<c:choose>
										<c:when test="${baseDataVO.exbtType eq '1001' }"><c:out value="${baseDataVO.exbtPartNm }"/> 관람</c:when>
										<c:otherwise><c:out value="${baseDataVO.exbtName }"/></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">일시</th>
								<td class="b"><fmt:parseDate var="extYmd" value="${param.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(timeVO.sTime,0,2) }:${fn:substring(timeVO.sTime,2,4) } ~ ${fn:substring(timeVO.eTime,0,2) }:${fn:substring(timeVO.eTime,2,4) }
									<!-- (<c:out value="${timeVO.timeName }"/>) -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인원수</th>
								<td class="b">
<c:forEach items="${rsvnMasterVO.chargeList}" var="item" varStatus="s">
<form:hidden path="chargeList[${s.index}].itemCd"/>
<form:hidden path="chargeList[${s.index}].itemCnt"/>
<form:hidden path="chargeList[${s.index}].price"/>
<form:hidden path="chargeList[${s.index}].itemNm"/>
<form:hidden path="chargeList[${s.index}].dcKindCd" value="${item.dcKindCd }"/>
<form:hidden path="chargeList[${s.index}].dcType" value="${item.dcType}"/>
<form:hidden path="chargeList[${s.index}].dcRate" value="${item.dcRate}"/>
	<c:if test="${item.itemCnt > 0 }"> <c:out value="${item.itemNm }"/> : <c:out value="${item.itemCnt }"/>명&nbsp;&nbsp;&nbsp;</c:if>
</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">총 금액</th>
								<td class="b"><fmt:formatNumber value="${totalAmount}"/>원</td>
							</tr>
<c:if test="${totalAmount > 0 and dcAmount>0}">
							<tr>
								<th scope="row" class="vtop">할인 예상 금액</th>
								<td class="b"><fmt:formatNumber value="${dcAmount}"/>원</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">결제 예상 금액</th>
								<td class="b"><fmt:formatNumber value="${totalAmount-dcAmount}"/>원</td>
							</tr>
</c:if>
						</tbody>
					</table>
					<!-- //table -->
				</div>
			</div>
			<!-- //signup2 -->
</form:form>
		</div>
		<!-- //join_wrap -->
		<ul class="btn_area">
		
			<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지 이동</a></li>
			<li><a href="javascript:fn_next()" class="btn_ty_m_c1">예약하기</a></li>		
		
			<!-- 
			<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전</a></li>
			<li><a href="javascript:fn_next()" class="btn_ty_m_c1">예약</a></li>
			-->
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>