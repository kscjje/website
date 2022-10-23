<%
 /**
  * @Class Name : joinYearRegist.jsp
  * @Description : 연간회원  가입 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.08    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.08
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<c:choose>
	<c:when test="${memberYearVO.CST_PLATFORM eq 'test'}"><script language="javascript" src="https://pretest.uplus.co.kr:9443/xpay/js/xpay_crossplatform.js" type="text/javascript"></script></c:when>
	<c:otherwise><script src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js"></script></c:otherwise>
</c:choose>
<script type="text/javascript">
/*
* 수정불가.
*/
var LGD_window_type = '${CST_WINDOW_TYPE }';
/*
* FORM 명만  수정 가능
*/
function getFormObject() {
	return document.getElementById("memberYearVO");
}

/*
* 수정불가
*/
function fn_launchCrossPlatform(payMethod) {
	if (payMethod == "000") {
		getFormObject().target = "_self";
		getFormObject().method = "post";
		getFormObject().action = "./joinYearSave";
		getFormObject().submit();
	} else {
		document.charset = "euc-kr";

		var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기

		if ( varUA.indexOf("iphone") > -1||varUA.indexOf("ipad") > -1||varUA.indexOf("ipod") > -1 ) {
			$('#LGD_KVPMISPAUTOAPPYN').val("N");
			$('#LGD_MTRANSFERAUTOAPPYN').val("N");
		}
		$('#LGD_CUSTOM_FIRSTPAY').val(payMethod);
		$('#LGD_CUSTOM_USABLEPAY').val(payMethod);
		lgdwin = openXpay( getFormObject(), '<c:out value="${memberYearVO.CST_PLATFORM}"/>', LGD_window_type, null, "", "");
	}

}


/*
 * 인증결과 처리
 */
function payment_return() {

	var fDoc;

	fDoc = lgdwin.contentWindow || lgdwin.contentDocument;

	if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {

		document.getElementById("LGD_PAYKEY").value = fDoc.document.getElementById('LGD_PAYKEY').value;
		document.getElementById("LGD_RESPCODE").value = fDoc.document.getElementById('LGD_RESPCODE').value;
		getFormObject().target = "_self";
		getFormObject().method = "post";
		getFormObject().action = "./joinYearSave";
		getFormObject().submit();

	} else {

		var varReturnCd  = fDoc.document.getElementById('LGD_RESPCODE').value;
		var varReturnMsg = fDoc.document.getElementById('LGD_RESPMSG').value;


		alert(varReturnCd + "\n" + varReturnMsg);
		closeIframe();

	}
}
</script>
<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">유료회원 등록</h3>
			<p class="desc mb100">국립중앙과학관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
			<div class="join_wrap bor pt70">
			<!-- signup -->

			<!-- signup2 -->
<c:choose>
	<c:when test="${empty userInfo or empty memberYearVO.itemCd }">
			<div class="signup2">
			<h4 class="h4">유료회원 등록을 위한 정보 확인</h4>
			<div class="table2 responsive-type">
				<table>
					<caption>유료회원 정보를 입력하는 표입니다.</caption>
					<tbody>
						<tr>
							<td class="b">
								<c:if test="${empty userInfo }">회원 정보가 없습니다.</c:if>
								<c:if test="${empty memberYearVO.itemCd }">상품 정보가 없습니다.</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	</c:when>
	<c:otherwise>
<form:form commandName="memberYearVO" name="memberYearVO" id="memberYearVO" action="./joinYearSave" method="post" accept-charset="EUC-KR">
<form:hidden path="memNo" />
<form:hidden path="itemCd" />
<form:hidden path="LGD_AMOUNT" />
<form:hidden path="CST_MID" />
<form:hidden path="LGD_MID" />
<form:hidden path="LGD_BUYER" />
<form:hidden path="LGD_BUYERID" />
<form:hidden path="LGD_PRODUCTINFO" />
<form:hidden path="LGD_PRODUCTCODE" />
<form:hidden path="LGD_BUYEREMAIL" />
<form:hidden path="LGD_OID" />
<form:hidden path="LGD_TIMESTAMP" />
<form:hidden path="LGD_HASHDATA" />
<form:hidden path="LGD_RETURNURL" />
<form:hidden path="LGD_CASNOTEURL" /><!-- 무통장 -->
<input type="hidden" name="LGD_CUSTOM_PROCESSTYPE" id="LGD_CUSTOM_PROCESSTYPE" value="TWOTR"/>
<input type="hidden" name="LGD_CUSTOM_SKIN" id="LGD_CUSTOM_SKIN" value="red"/>
<input type="hidden" name="CST_WINDOW_TYPE" id="CST_WINDOW_TYPE" value="${CST_WINDOW_TYPE }"/>
<input type="hidden" name="LGD_VERSION" id="LGD_VERSION" value="JSP_Non-ActiveX_SmartXPay"/>
<input type="hidden" name="LGD_CUSTOM_SWITCHINGTYPE" id="LGD_CUSTOM_SWITCHINGTYPE" value="${LGD_CUSTOM_SWITCHINGTYPE }"/>
<input type="hidden" name="LGD_MPILOTTEAPPCARDWAPURL" id="LGD_MPILOTTEAPPCARDWAPURL" value=""/>
<input type="hidden" name="LGD_KVPMISPWAPURL" id="LGD_KVPMISPWAPURL" value=""/>
<input type="hidden" name="LGD_KVPMISPCANCELURL" id="LGD_KVPMISPCANCELURL" value=""/>
<input type="hidden" name="LGD_KVPMISPAUTOAPPYN" id="LGD_KVPMISPAUTOAPPYN" value="A"/><!-- iOS: N -->
<input type="hidden" name="LGD_MTRANSFERWAPURL" id="LGD_MTRANSFERWAPURL" value=""/>
<input type="hidden" name="LGD_MTRANSFERCANCELURL" id="LGD_MTRANSFERCANCELURL" value=""/>
<input type="hidden" name="LGD_MTRANSFERAUTOAPPYN" id="LGD_MTRANSFERAUTOAPPYN" value="A"/>
<input type="hidden" name="LGD_RESPCODE" id="LGD_RESPCODE" value=""/>
<input type="hidden" name="LGD_PAYKEY" id="LGD_PAYKEY" value=""/>
<input type="hidden" name="LGD_RESPMSG" id="LGD_RESPMSG" value=""/>
<input type="hidden" name="LGD_CUSTOM_FIRSTPAY" id="LGD_CUSTOM_FIRSTPAY" value="SC0010"/>
<input type="hidden" name="LGD_CUSTOM_USABLEPAY" id="LGD_CUSTOM_USABLEPAY" value="SC0010-SC0030"/>
<form:hidden path="CST_PLATFORM" />
			<div class="signup2">
				<h4 class="h4">유료회원 등록을 위한 정보 확인</h4>
				<div class="table2 responsive-type">
					<table>
						<caption>유료회원 정보를 입력하는 표입니다.</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop"><label for="id">아이디</label></th>
								<td><c:out value="${memberYearVO.id }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="memNm">이름</label></th>
								<td><c:out value="${memberYearVO.memNm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">유료회원 기간</th>
								<td>
									<fmt:parseDate var="startYmd" value="${memberYearVO.startYmd}" pattern="yyyyMMdd"/>
									<fmt:parseDate var="endYmd" value="${memberYearVO.endYmd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${startYmd}" pattern="yyyy년 MM월 dd일"/> ~  <fmt:formatDate value="${endYmd}" pattern="yyyy년 MM월 dd일"/>
									(<c:out value="${memberYearVO.monthCnt }"/> 개월)
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">결제 금액</th>
								<td><fmt:formatNumber value="${memberYearVO.saleamt}"/>원</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>

			</div>
<c:choose>
		<c:when test="${orderVO.retCd eq 'OK' }">
			<div>
				<ul class="btn_area long">
					<c:choose>
						<c:when test="${memberYearVO.saleamt == 0 }">
							<li><a href="javascript:fn_launchCrossPlatform('000')" class="btn_ty_m3_c1">가입하기</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="javascript:fn_launchCrossPlatform('SC0010')" class="btn_ty_m3_c1" title="토스 전자결제 레이어열림">신용카드 결제하기</a></li>
							<li><a href="javascript:fn_launchCrossPlatform('SC0030')" class="btn_ty_m3_c3" title="토스 전자결제 레이어열림">계좌이체 결제하기</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
				<!-- //btn_area -->
			</div>

		</c:when>
		<c:otherwise>
			<div>
				${orderVO.retCd} : ${orderVO.regMsg }
			</div>
		</c:otherwise>
</c:choose>
</form:form>
	</c:otherwise>

</c:choose>
			<!-- //signup2 -->
		</div>
		<!-- //join_wrap -->

	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->