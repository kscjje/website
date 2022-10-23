<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<c:choose>
		<c:when test="${paramMap.CST_PLATFORM eq 'test'}">
			<script language="javascript" src="https://pretest.uplus.co.kr:9443/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
		</c:when>
		<c:otherwise>
			<script language="javascript" src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
		</c:otherwise>
	</c:choose>


	<script>
		$(document).ready(function() {
			$("input[name=paymethod]").click(function() {
				var val = $(this).val();
				if (val == "SC0040") {
					$("#refundLi").show();
				} else {
					$("#refundLi").hide();
				}
			});

			$("input[name=chkDc]").click(function() {
				var chkDiscountCd;
				if ($(this).prop("checked")) {
					chkDiscountCd = $(this).data("dccd");
				}

				$("input[name=chkDc]").each(function() {
					if ($(this).data("dccd") != chkDiscountCd) {
						$(this).prop("checked", false);
					}
				});
			})

			$("#accountNum").keyup(function() {
				var val = $(this).val();
				$(this).val(val.replace(/[^0-9-]/g, ''));
			});

			$("#nwpayDcAmtSpan").text(comma(${rsvnInfo.edcProgmCost} * (20/100)));
		});

		function fnDcApply(gubun) {
			var saleAmt = $("#saleAmt").val();

			if (gubun == "dc") {
				var dcRate = $("input[name='chkDc']:checked").data("dcrate");
				var dcNm = $("input[name='chkDc']:checked").data("dcnm");
				$("#dcCd").val($("input[name='chkDc']:checked").data("dccd"));
				$("#dcRate").val($("input[name='chkDc']:checked").data("dcrate"));

				if (dcRate) {
					var dcAmt = saleAmt * (dcRate/100);
					dcAmt = Math.round(dcAmt/10)*10;
					var payAmt = saleAmt - dcAmt;

					$("#dcAmt").val(dcAmt);
					$("#payAmt").val(payAmt);

					$("#dcAmtEm").text(comma(-1*dcAmt) + "원");
					$("#payAmtEm").text(comma(payAmt) + "원");

					$("#dcDesc").text(dcNm + "(" + dcRate + "%감면)").show();

					if (payAmt > 0) {
						$("input[name=paymethod]").prop("disabled", false);
						$("#payBtn").text("결제하기");
						$("#paymethodLi").show();
					} else {
						$("input[name=paymethod]").prop("checked", false);
						$("input[name=paymethod]").prop("disabled", true);
						$("#payBtn").text("신청하기");
						$("#paymethodLi").hide();
					}

					$("#nwpayAmt").val(0);
				}
			} else {
				var nwpayAmt = $("#nwpayAmt").val();

				$("#dcCd").val("NWPY");
				$("#dcRate").val(Math.round((nwpayAmt/saleAmt)*100));

				if (nwpayAmt >= 0) {
					var dcAmt = nwpayAmt;
					var payAmt = saleAmt - dcAmt;

					$("#dcAmt").val(dcAmt);
					$("#payAmt").val(payAmt);

					$("#dcAmtEm").text(comma(-1*nwpayAmt) + "원");
					$("#payAmtEm").text(comma(payAmt) + "원");

					if (nwpayAmt == 0) {
						$("#dcDesc").text("").hide();
					} else {
						$("#dcDesc").text("노원PAY(" + comma(nwpayAmt) + "감면)").show();
					}

					if (payAmt > 0) {
						$("input[name=paymethod]").prop("disabled", false);
						$("#payBtn").text("결제하기");
						$("#paymethodLi").show();
					} else {
						$("input[name=paymethod]").prop("checked", false);
						$("input[name=paymethod]").prop("disabled", true);
						$("#payBtn").text("신청하기");
						$("#paymethodLi").hide();
					}

				}
			}
			pop_close();
		}

		function fnPay() {
			var payAmt = $("#payAmt").val();
			if (payAmt > 0) {
				var paymethod = $("input[name=paymethod]:checked").val();
				if (!paymethod) {
					alert("결제방식을 선택해 주세요.");
					return;
				}

				if (paymethod == "SC0040") {
					if (!$.trim($("#bankCd").val())) {
						alert("환불 받을 계좌은행을 선택해 주세요.");
						$("#bankCd").focus();
						return;
					}

					if (!$.trim($("#accountNum").val())) {
						alert("환불 받을 계좌번호를 입력해 주세요.");
						$("#accountNum").focus();
						return;
					}

					if (!$.trim($("#dpstrNm").val())) {
						alert("환불 받을 예금주명을 입력해 주세요.");
						$("#dpstrNm").focus();
						return;
					}
				}
			}

			//ajax 호출하여 oid 채번. 더불어 pd_ord_msg, pd_ord_det도 입력을 마친다.
			if (confirm("결제하시겠습니까?")) {
				var payAmt = $("#payAmt").val();
				if (payAmt == 0) {//할인율100%결제
					$.post({
						url : "<c:url value='/web/edc/rsvn/pay.json'/>",
						data : $("#frmPay").serialize(),
						success : function (data) {
							if (data.result.code == "SUCCESS") {
								location.replace("<c:url value='/web/edc/rsvn/complete/${rsvnInfo.edcRsvnNo}'/>");
							} else { //"FAIL"
								alert(data.result.msg);
							}
						},
						error : function (result) {
							console.log(result);
							alert("일시적인 오류가 발생하였습니다. 잠시후 다시 신청해 주세요.");
						}
					});
				} else {//PG결제
					var data = {};
					data.CST_MID = $('#CST_MID').val();
					data.LGD_MID = $('#LGD_MID').val();
					data.LGD_OID = $('#LGD_OID').val();
					data.LGD_AMOUNT = payAmt;
					data.rsvnNo = $("#rsvnNo").val();
					data.dcCd = $("#dcCd").val();
					data.dcRate = $("#dcRate").val();
					data.dcAmt = $("#dcAmt").val();
					data.nwpayAmt = $("#nwpayAmt").val();
					data.nwpayId =  $("#nwpayId").val();
					data.payMethod = $("input[name=paymethod]:checked").val();
					data.bankCd = $("#bankCd").val();
					data.bankNm = $("#bankCd option:checked").text();
					data.accountNum = $("#accountNum").val();
					data.dpstrNm = $("#dpstrNm").val();
					data.orgNo = ${rsvnInfo.orgNo};

					$.post({
						url: "<c:url value='/web/edc/rsvn/pay/toss/prepare.json'/>",
						data: data,
						success: function (data) {
							if (data.result.code == "SUCCESS") {
								$("#LGD_AMOUNT").val(data.paramMap.LGD_AMOUNT);
								$("#LGD_OID").val(data.paramMap.LGD_OID);
								$("#LGD_TIMESTAMP").val(data.paramMap.LGD_TIMESTAMP);
								$("#LGD_HASHDATA").val(data.paramMap.LGD_HASHDATA);
								fn_launchCrossPlatform()
							} else { //"FAIL"
								alert(data.result.msg);
							}
						},
						error: function (result) {
							alert("일시적인 오류가 발생하였습니다. 잠시후 다시 시도해 주세요.");
						}
					});
				}
			}
		}
		
		function fnDcLimitNoti() {
			alert("노원평생교육원, 공릉평생교육원, 장미실습장은 분기 내 1번만 할인감면 받을 수 있습니다. 고객님께서는 이미 할인감면 사용했기에, 감면받을 수 없습니다.");
		}

		var LGD_window_type = '${paramMap.CST_WINDOW_TYPE}';
		var lgdwin;

		function fn_launchCrossPlatform() {
			<%-- if (LGD_window_type == "iframe") {
				// LGD_window_type=SUBMIT 인 경우 기본 창 사이즈(650, 650)를 변경하고 싶은 경우 openXpay(a, b, c, d, e, f) 의 매개변수 e:가로, f:세로 값을 정수로 전달하여 주세요
				var domain = null;
				var width = "";
				var height = "";
				lgdwin = openXpay(document.getElementById('LGD_PAYINFO'), '${CST_PLATFORM}', LGD_window_type, domain, width, height);
			} else {
				lgdwin = open_paymentwindow(document.getElementById('LGD_PAYINFO'), '${CST_PLATFORM}', LGD_window_type);
			} --%>

			var paymethod = $("input[name=paymethod]:checked").val();
			if (!paymethod) {
				alert("결제방식을 선택해 주세요.");
				return;
			}

			if ($('#LGD_AMOUNT').val() == "0") {
				alert("잘못된 호출입니다. 관리자에게 문의하세요.");
				return;
			}

			var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
			if ( varUA.indexOf("iphone") > -1||varUA.indexOf("ipad") > -1||varUA.indexOf("ipod") > -1 ) {
				$('#LGD_KVPMISPAUTOAPPYN').val("N");
				$('#LGD_MTRANSFERAUTOAPPYN').val("N");
			}

			$('#LGD_CUSTOM_FIRSTPAY').val(paymethod);
			$('#LGD_CUSTOM_USABLEPAY').val(paymethod);

			lgdwin = openXpay(document.getElementById("frmPay"), $("#CST_PLATFORM").val(), LGD_window_type, null, "", "");
		}

		/*
		 * 인증결과 처리
		 */
		function payment_return() {
			var fDoc;
			if (LGD_window_type == "iframe") {
				fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
			} else {
				fDoc = lgdwin;
			}

			$("#LGD_RESPCODE").val(fDoc.document.getElementById('LGD_RESPCODE').value);
			$("#LGD_RESPMSG").val(fDoc.document.getElementById('LGD_RESPMSG').value);
			$("#LGD_PAYKEY").val(fDoc.document.getElementById('LGD_PAYKEY').value);
			$("#LGD_PARAMS").val(fDoc.document.getElementById('LGD_PARAMS').value);

			if ($("#LGD_RESPCODE").val() == "0000") {
				//if ($("#LGD_CUSTOM_PROCESSTYPE").val() == "TWOTR") {
				closeIframe();

				$.post({
					url: "<c:url value='/web/edc/rsvn/pay/toss/complete.json'/>",
					data: $("#frmPay").serialize(),
					success: function (data) {
						if (data.result.code == "SUCCESS") {
							location.replace("<c:url value='/web/edc/rsvn/complete/${rsvnInfo.edcRsvnNo}'/>");
						} else { //"FAIL"
							alert(data.result.msg);
						}
					},
					error: function (result) {
						console.log(result);
						alert("일시적인 오류가 발생하였습니다. 잠시후 다시 신청해 주세요.");
					}
				});
			} else {
				alert(document.getElementById('LGD_RESPMSG').value);
				closeIframe();
			}
		}

	</script>
	
	
    <main class="content" id="content">
<form name="frmPay" id="frmPay">
	<input type="hidden" name="comcd" id="comcd" value="${rsvnInfo.comcd}"/>
	<input type="hidden" name="rsvnNo" id="rsvnNo" value="${rsvnInfo.edcRsvnNo}"/>
	<input type="hidden" name="dcCd" id="dcCd" value=""/>
	<input type="hidden" name="dcRate" id="dcRate" value="0"/>
	<input type="hidden" name="saleAmt" id="saleAmt" value="${rsvnInfo.edcProgmCost}"/>
	<input type="hidden" name="dcAmt" id="dcAmt" value="0"/>
	<input type="hidden" name="payAmt" id="payAmt" value="${rsvnInfo.edcProgmCost}"/>
	<input type="hidden" name="nwpayBalance" id="nwpayBalance" value="0">
	<input type="hidden" name="nwpayAmt" id="nwpayAmt" value="0">

	<!-- TOSS PAYMENT PARAMETER START -->
	<input type='hidden' name='CST_MID' id='CST_MID' value='${paramMap.CST_MID}'>
	<input type='hidden' name='LGD_MID' id='LGD_MID' value='${paramMap.LGD_MID}'>
	<input type='hidden' name='LGD_OID' id='LGD_OID' value='${paramMap.LGD_OID}'>

	<input type="hidden" name="CST_PLATFORM" id="CST_PLATFORM" value="${paramMap.CST_PLATFORM}"/>
	<input type="hidden" name="LGD_RETURNURL" id="LGD_RETURNURL" value="${paramMap.LGD_RETURNURL}"/>
	<input type="hidden" name="LGD_CASNOTEURL" id="LGD_CASNOTEURL" value="${paramMap.LGD_CASNOTEURL}"/>

	<input type="hidden" name="LGD_ENCODING" id="LGD_ENCODING" value="UTF-8"/>
	<input type="hidden" name="LGD_ENCODING_RETURNURL" id="LGD_ENCODING_RETURNURL" value="UTF-8"/>
	<input type="hidden" name="LGD_ENCODING_NOTEURL" id="LGD_ENCODING_NOTEURL" value="UTF-8"/>

	<input type="hidden" name="LGD_CUSTOM_PROCESSTYPE" id="LGD_CUSTOM_PROCESSTYPE" value="TWOTR"/>
	<input type="hidden" name="LGD_CUSTOM_SKIN" id="LGD_CUSTOM_SKIN" value="red"/>
	<input type="hidden" name="CST_WINDOW_TYPE" id="CST_WINDOW_TYPE" value="${paramMap.CST_WINDOW_TYPE}"/>
	<input type="hidden" name="LGD_VERSION" id="LGD_VERSION" value="JSP_Non-ActiveX_SmartXPay"/>
	<input type="hidden" name="LGD_CUSTOM_SWITCHINGTYPE" id="LGD_CUSTOM_SWITCHINGTYPE" value="${paramMap.LGD_CUSTOM_SWITCHINGTYPE}"/>

	<input type="hidden" name="LGD_MPILOTTEAPPCARDWAPURL" id="LGD_MPILOTTEAPPCARDWAPURL" value=""/><!-- iOS 연동시 필수 -->
	<input type="hidden" name="LGD_KVPMISPWAPURL" id="LGD_KVPMISPWAPURL" value=""/><!-- ISP 카드결제 연동을 위한 파라미터(필수) -->
	<input type="hidden" name="LGD_KVPMISPCANCELURL" id="LGD_KVPMISPCANCELURL" value=""/><!-- ISP 카드결제 연동을 위한 파라미터(필수) -->
	<input type="hidden" name="LGD_KVPMISPAUTOAPPYN" id="LGD_KVPMISPAUTOAPPYN" value="A"/><!-- iOS: N -->
	<input type="hidden" name="LGD_MTRANSFERWAPURL" id="LGD_MTRANSFERWAPURL" value=""/><!-- 계좌이체 연동을 위한 파라미터(필수) -->
	<input type="hidden" name="LGD_MTRANSFERCANCELURL" id="LGD_MTRANSFERCANCELURL" value=""/><!-- 계좌이체 연동을 위한 파라미터(필수) -->
	<input type="hidden" name="LGD_MTRANSFERAUTOAPPYN" id="LGD_MTRANSFERAUTOAPPYN" value="A"/><!-- android여부(A:android, N:ios) -->
	<input type="hidden" name="LGD_ESCROW_USEYN" id="LGD_ESCROW_USEYN" value="N"/>

	<!-- REQUEST -->
	<input type="hidden" name="LGD_BUYER" id="LGD_BUYER" value="${userInfo.name}"/>
	<input type="hidden" name="LGD_BUYERID" id="LGD_BUYERID" value="${userInfo.id}"/>
	<input type="hidden" name="LGD_BUYEREMAIL" id="LGD_BUYEREMAIL" value="${userInfo.email}"/>
	<input type="hidden" name="LGD_PRODUCTINFO" id="LGD_PRODUCTINFO" value="${rsvnInfo.edcPrgmnm}"/>
	<input type="hidden" name="LGD_AMOUNT" id="LGD_AMOUNT" value=""/>
	<input type="hidden" name="LGD_TIMESTAMP" id="LGD_TIMESTAMP" value=""/>
	<input type="hidden" name="LGD_HASHDATA" id="LGD_HASHDATA" value=""/>
	<!-- 무통장입금 입금기한:yyyyMMddHHmmss -->
	<input type="hidden" name="LGD_CLOSEDATE" id="LGD_CLOSEDATE" value="<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "yyyyMMddHHmmss"/>"/>

	<!-- 결제결과(RETURN URL을 통해 전달되는 값) -->
	<input type="hidden" name="LGD_RESPCODE" id="LGD_RESPCODE" value=""/>
	<input type="hidden" name="LGD_PAYKEY" id="LGD_PAYKEY" value=""/>
	<input type="hidden" name="LGD_RESPMSG" id="LGD_RESPMSG" value=""/>
	<input type="hidden" name="LGD_PARAMS" id="LGD_PARAMS" value=""/>
	<!-- 아래 키값들은 returnUrl에서 session에 설정하고 payResponse에서 session에서 추출하여 사용 -->
	<!-- <input type="hidden" name="LGD_PAYTYPE" id="LGD_PAYTYPE" value=""/>
	<input type="hidden" name="LGD_BUYERIP" id="LGD_BUYERIP" value=""/>
	<input type="hidden" name="LGD_CARDNUM" id="LGD_CARDNUM" value=""/>
	<input type="hidden" name="LGD_FINANCECODE" id="LGD_FINANCECODE" value=""/>
	<input type="hidden" name="LGD_FINANCENAME" id="LGD_FINANCENAME" value=""/>
	<input type="hidden" name="LGD_AUTHTYPE" id="LGD_AUTHTYPE" value=""/> -->

	<input type="hidden" name="LGD_CUSTOM_FIRSTPAY" id="LGD_CUSTOM_FIRSTPAY" value="SC0010"/>
	<input type="hidden" name="LGD_CUSTOM_USABLEPAY" id="LGD_CUSTOM_USABLEPAY" value="SC0010"/>
	<input type='hidden' name='LGD_WINDOW_TYPE' id='LGD_WINDOW_TYPE' value='iframe'>
	<input type='hidden' name='LGD_WINDOW_VER' id='LGD_WINDOW_VER' value='2.5'>
	<!-- TOSS PAYMENT PARAMETER END -->

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../../resources/images/sub/img_subVisual_03.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">교육신청</h3>
        </div>
      </div>
      <div class="sub">
        <div class="pageTit">
          <div class="pageTit-inner">
            <div class="pageTit-wrap align-center">
              <h3 class="pageTit-title">결제하기</h3>
            </div>
          </div>
        </div>
        <div class="inform">
          <div class="inform-inner">
            <div class="inform-wrap">
              <figure class="inform-figure">
                <img src="../../../resources/images/sub/ico_basice_01.png" alt="아이콘">
              </figure>
              <div class="inform-body">
                <h3 class="inform-tit">수강신청 <span>완료</span>
                </h3>
                <p class="inform-txt">
              	    수강신청을 해 주셔서 감사합니다. 
              	  <span>
					
					<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "yyyy"  />년
					<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "MM"  />월
					<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "dd"  />일
					<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "HH"  />시
					<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "mm"  />분까지 결제가 필요합니다.

				  </span>까지 결제완료바랍니다.
                  <br/>
                  	미 결제 시 자동 취소됩니다. 결제완료 후에 해당 수강신청 등록이 완료됩니다.
                  <br>
                </p>
              </div>
            </div>
          </div>
        </div>

        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">강좌정보</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">강좌명</th>
                    <td colspan="3">${rsvnInfo.edcPrgmnm}</td>
                  </tr>
                  <tr>
                    <th scope="row">강사명</th>
                    <td> ${rsvnInfo.instrctrName} </td>
                    <th scope="row">교육장소</th>
                    <td> ${rsvnInfo.edcPlacenm} </td>
                  </tr>
                  <tr>
                    <th scope="row">교육기간</th>
                    <td>
                      <strong><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy-MM-dd"/>
                      </strong>
                    </td>
                    <th scope="row">교육기간</th>
                    <td>
                      <strong>
						<c:choose>
							<c:when test="${empty rsvnInfo.edcTimeGuide }">
								${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/>
							</c:when>
							<c:otherwise>
								${rsvnInfo.edcTimeGuide}
							</c:otherwise>
						</c:choose>                      
					  </strong>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row">교육비</th>
                    <td colspan="3">
                      <strong><fmt:formatNumber value='${rsvnInfo.edcProgmCost}'/>원</strong>
                    </td>
                    <!-- 
                    <th scope="row">추가비용</th>
                    <td>
                      <strong>교재비</strong>
                    </td>
                     -->
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">신청자정보</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="34%">
                  <col width="10%">
                  <col width="40%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">이름</th>
                    <td>${rsvnInfo.edcRsvnCustnm}</td>
                    <th scope="row">생년월일</th>
                    <td>${rsvnInfo.edcRsvnBirthdate}</td>
                  </tr>
                  <tr>
                    <th scope="row">휴대폰번호</th>
                    <td colspan="3">
                     ${rsvnInfo.edcRsvnMoblphon}
                          <!-- 

                      <div class="w_box w_460 justify-content-between">
                        <div class="input-group">
                          <div class="searchBar-item">
                         
                            <button class="btn">010</button>
                            <ul class="searchBar-list">
                              <li>
                                <button>010</button>
                              </li>
                              <li>
                                <button>011</button>
                              </li>
                              <li>
                                <button>016</button>
                              </li>
                              <li>
                                <button>017</button>
                              </li>
                              <li>
                                <button>018</button>
                              </li>
                              <li>
                                <button>019</button>
                              </li>
                            </ul>
                          </div>

                        </div>
                        <div class="input-group">
                          <label for="inp04_03_01" class="visually-hidden"></label>
                          <input type="text" title="휴대폰번호" value="1234" id="inp04_03_01">
                        </div>
                        <div class="input-group">
                          <label for="inp04_03_02" class="visually-hidden"></label>
                          <input type="text" title="휴대폰번호" value="5678" id="inp04_03_02">
                        </div>
                      </div>
                      -->
                      
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap multiple">
              <h3 class="myTable-title">할인/할증내역</h3>
              <table class="align-center">
                <colgroup>
                  <col style="width: 15%;">
                  <col style="width: 25%;">
                  <col style="width: 10%;">
                  <col style="width: 20%;">
                  <col style="width: 20%;">
                  <col style="width: 20%;">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="row">센터</th>
                    <th scope="row">강좌</th>
                    <th scope="row">수강자</th>
                    <th scope="row">할인/할증사유</th>
                    <th scope="row">할인/할증율</th>
                    <th scope="row">할이/할증 금액(원)</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td colspan="6">할인 내역이 없습니다.</td>
                  </tr>
<!--
                  <tr>
                    <td colspan="6">
				                      ※ 장애인, 기초생활수급자, 국가유공자할인 대상자 분들은 <p>아래 할인 내용 확인하기 (행정감면서비스)</strong>를 이용하여 즉시 할인 받으실 수 있습니다.
                    </td>
                  </tr>
                  <tr>
                    <td>별내동주민자치센터</td>
                    <td>(단기_온라인)커피이론전문가(자격증)-야간)</td>
                    <td>김별내</td>
                    <td>국가유공자</td>
                    <td>50%</td>
                    <td>50,000</td>
                  </tr>
                  <tr class="total">
                    <th colspan="5">합계</th>
                    <th>50,000</th>
                  </tr>
 -->
  
                </tbody>
              </table>
            </div>
            <div class="text-end">
              <!-- 조회후  green 클래스 추가 -->
              <button type="button" class="btn exemption-btn black" data-bs-toggle="modal" data-bs-target="#exemption-service">할인내용
                확인하기(행정감면서비스)</button>
            </div>
          </div>
        </div>
        <div class="myTable">
          <div class="myTable-inner">
            <div class="myTable-wrap">
              <h3 class="myTable-title">결제정보</h3>
              <table>
                <colgroup>
                  <col width="10%">
                  <col width="90%">
                </colgroup>
                <tbody>
                  <tr>
                    <th scope="row">결제상태</th>
                    <td>
                      <div class="radio">
                      
                      
					<c:if test="${fn:contains(paymentMethods, '1')}">
                        <span>
                          <input name="paymethod" id="paymethod1" type="radio" value="SC0010" title="카드결제">
                          <label for="paymethod1" tabindex="0">카드결제</label>
                        </span>
					</c:if>
					<c:if test="${fn:contains(paymentMethods, '2')}">
						<span>
							<input name="paymethod" id="paymethod2" type="radio" value="SC0030"  title="실시간 계좌이체">
							<label for="paymethod2">실시간 계좌이체</label>
						</span>
					</c:if>
					<c:if test="${fn:contains(paymentMethods, '3')}">
						<span>
							<input name="paymethod" id="paymethod3" type="radio" value="SC0040"  title="가상계좌(무통장)">
							<label for="paymethod3">가상계좌(무통장)</label>
						</span>
					</c:if>
                       
                        
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="badge-btn">
        	<a href="javascript:history.back()" class="gray">이전</a>
  			<a href="javascript:;" onclick="fnPay()" class="green" id="payBtn">결제하기</a>
        </div>
      </div>
  </div>
  </form>
  </main>

	<script>
		$(document).ready(function() {
			$('#nwpayId').bind('keyup', function(e){
				if (e.keyCode == 13) {
					fnGetNwPayBalance();
					e.preventDefault();
				}
			});

			$('#modalNwpayAmt').bind('blur', function(e){
				fnRefreshNwpay();
			});
		});

		function fnRefreshNwpay(obj) {
			var amount = parseInt(uncomma($("#modalNwpayAmt").val()));
			var balance = parseInt($("#nwpayBalance").val());
			var ableNwpayAmt = parseInt(uncomma($("#nwpayDcAmtSpan").text()));

			if (amount < 0) {
				amount = 0;
			}

			if (amount > balance) {
				amount = balance;
			}

			if (amount > ableNwpayAmt) {
				amount = ableNwpayAmt;
			}

			$("#modalNwpayAmt").val(comma(Math.floor(amount/100)*100));
		}

		function fnGetNwPayBalance() {
			if (!$.trim($("#nwpayId").val())) {
				$("#nwpayId").focus();
				alert("지역화폐 아이디를 입력하세요.");
				return;
			}

			/* if (!$.trim($("#nwpayPassword").val())) {
				alert("지역화폐 결제비밀번호를 입력하세요.");
				return;
			} */

			$.post({
				url : "<c:url value='/web/edc/rsvn/nwpay/balance.json'/>",
				data : {"nwpayId": $.trim($("#nwpayId").val()), "orgNo":${rsvnInfo.orgNo}},
				success : function (data) {
					if (data.result.code == "SUCCESS") {
						if (data.nwPay.status == "200") {
							alert(comma(data.nwPay.amount) + " NW를 보유하고 있으며 100원단위로 사용가능합니다.");
							var balance = parseInt(uncomma(data.nwPay.amount));
							var ableNwpayAmt = parseInt(uncomma($("#nwpayDcAmtSpan").text()));

							if (balance >= ableNwpayAmt) {
								$("#modalNwpayAmt").val(comma(ableNwpayAmt));
							} else {
								$("#modalNwpayAmt").val(comma(Math.floor(balance/100)*100));
							}
							$("#nwpayBalance").val(balance);
							$("#modalNwpayAmt").prop("readonly", false);
						} else {
							alert(data.nwPay.statusDesc);
						}
					} else { //"FAIL"
						alert(data.result.msg);
					}
				}
			});
		}

		function fnNwpayApply() {
			fnRefreshNwpay();
			$("#nwpayAmt").val(uncomma($("#modalNwpayAmt").val()));
			fnDcApply('nwpay');
		}
	</script>

	