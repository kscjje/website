<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnStep1.jsp
  * @Description : 단체교육 예약 step1
  * @Modification Information
  * @
  * @  수정일             수정자            수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.18   전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.18.
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">단체 교육 예약</h3>
						<div class="join_wrap bor">
							<!-- step -->
							
							<c:if test="${paramMap.evtPersnGbn eq '10'}">
							
								<ul class="step step3">
									<li>
										<dl>
											<dt>1</dt>
											<dd>단체교육 선택</dd>
										</dl>
									</li>
									<li class="on">
										<!-- 현재 on -->
										<dl>
											<dt>2</dt>
											<dd>인원입력 및 동의</dd>
										</dl>
									</li>
									<li>
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
															
							</c:if>
							
							<c:if test="${paramMap.evtPersnGbn eq '20'}">
								
								<ul class="step step3">
									<li>
										<dl>
											<dt>1</dt>
											<dd>단체교육 선택</dd>
										</dl>
									</li>
									<li class="on">
										<!-- 현재 on -->
										<dl>
											<dt>2</dt>
											<dd>인원입력 및 동의</dd>
										</dl>
									</li>
									<li>
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
								
							</c:if>
							
							<!-- 
							<ul class="step step4">
								<li>
									<dl>
										<dt>1</dt>
										<dd>회차 선택</dd>
									</dl>
								</li>
								<li class="on">
									
									<dl>
										<dt>2</dt>
										<dd>안내 및 주문</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>정보 입력</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd><c:choose><c:when test="${evtData.evtFeeType eq '2001'}">예약완료</c:when><c:otherwise>결제 대기</c:otherwise></c:choose></dd>
									</dl>
								</li>

							</ul>
							-->
							<!-- //step -->
							
							<!-- signup2 -->
								<div class="signup2">
								
									<c:if test="${paramMap.evtPersnGbn eq '10'}">
									
										<p class="stit">선택된 단체교육 예약을 확인해 주십시오.</p>
										<div class="table2 responsive-type mb50">
										
											<table>
												<caption>선택된 단체교육 예약 안내표입니다.</caption>
												<colgroup>
													<col style="width:20%">
													<col style="width:30%">
													<col style="width:20%">
													<col style="width:30%">
												</colgroup>
												<tbody>
													
													<tr>
														<th scope="row" class="vtop">교육명</th>
														<td class="b" colspan="3">
															<c:out value="${evtData.evtName}"></c:out>
														</td>
													</tr>
													<tr>
														<th scope="row" class="vtop">이용일자</th>
														<td class="b">
															<fmt:parseDate var="extYmd" value="${param.evtTime}" pattern="yyyyMMdd"/> 
	 														<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/> 
	 													</td>
														<th scope="row" class="vtop">이용시간/회차</th>
														<td class="b">
															${fn:substring(evtTime.sTime,0,2)}:${fn:substring(evtTime.sTime,2,4)} ~ ${fn:substring(evtTime.eTime,0,2)}:${fn:substring(evtTime.eTime,2,4)} / <c:out value="${evtTime.timeName}"></c:out>
	 													</td>	 													
													</tr>													
													<tr>
														<th scope="row" class="vtop">장소</th>
														<td class="b" colspan="3">
															<c:out value="${evtData.evtPlacenm}"></c:out>
														</td>
													</tr>

													<tr>
														<th scope="row" class="vtop">취소기간</th>
														<td class="b">
															<c:if test="${evtData.cancelYn eq 'Y'}">
																취소 가능
															</c:if>
															<c:if test="${evtData.cancelYn eq 'N'}">
																취소 불가능
															</c:if>
														</td>
														<th scope="row" class="vtop">문의전화</th>
														<td class="b">
															<c:out value="${evtData.evtGuideTelno}"/>										
														</td>
													</tr>
													<tr style="display:none;">
														<th scope="row" class="vtop">러닝타임</th>
														<td class="b">
															<!-- 60분 -->
															<c:out value="${evtData.evtRuningTime}">분</c:out>
														</td>
														<th scope="row" class="vtop">관람비용</th>
														<td class="b">무료</td>
													</tr>												
	
												</tbody>
											</table>									
	
										</div>
									</c:if>
									
									<c:if test="${paramMap.evtPersnGbn eq '20'}">
										
										<p class="stit">선택된 단체교육 예약을 확인해 주십시오.</p>
										<div class="table2 responsive-type mb50">
										
											<table>
												<caption>선택된 단체교육 예약 안내표입니다.</caption>
												<colgroup>
													<col style="width:20%">
													<col style="width:30%">
													<col style="width:20%">
													<col style="width:30%">
												</colgroup>
												<tbody>
													
													<tr>
														<th scope="row" class="vtop">교육명</th>
														<td class="b" colspan="3">
															<c:out value="${evtData.evtName}"></c:out>
														</td>
													</tr>
													<tr>
														<th scope="row" class="vtop">이용일자</th>
														<td class="b">
															<fmt:parseDate var="extYmd" value="${param.evtTime}" pattern="yyyyMMdd"/> 
	 														<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/> 
	 													</td>
														<th scope="row" class="vtop">이용시간/회차</th>
														<td class="b">
															${fn:substring(evtTime.sTime,0,2)}:${fn:substring(evtTime.sTime,2,4)} ~ ${fn:substring(evtTime.eTime,0,2)}:${fn:substring(evtTime.eTime,2,4)} / <c:out value="${evtTime.timeName}"></c:out>
	 													</td>	 													
													</tr>													
													<tr>
														<th scope="row" class="vtop">장소</th>
														<td class="b" colspan="3">
															<c:out value="${evtData.evtPlacenm}"></c:out>
														</td>
													</tr>

													<tr>
														<th scope="row" class="vtop">취소기간</th>
														<td class="b">
															<c:if test="${evtData.cancelYn eq 'Y'}">
																취소 가능
															</c:if>
															<c:if test="${evtData.cancelYn eq 'N'}">
																취소 불가능
															</c:if>
														</td>
														<th scope="row" class="vtop">문의전화</th>
														<td class="b">
															<c:out value="${evtData.evtGuideTelno}"/>										
														</td>
													</tr>
													<tr style="display:none;">
														<th scope="row" class="vtop">러닝타임</th>
														<td class="b">
															<!-- 60분 -->
															<c:out value="${evtData.evtRuningTime}">분</c:out>
														</td>
														<th scope="row" class="vtop">관람비용</th>
														<td class="b">무료</td>
													</tr>											
	
												</tbody>
											</table>									
	
										</div>										
										
									</c:if>										
								
									<!-- board_ty -->
									<div class="board_ty">
										<ul>
											<li>
												<div class="info">
													<!-- info_tit -->
													<div class="info_tit">
														<span>인원(명) <em>관람에 참가할 입장 인원을 입력해주세요</em></span>
													</div>
													<!-- //info_tit -->
													<!-- numcontrol -->
													<div class="numcontrol">
													
														<!-- 
														<input value="-" id="m" type="button" class="btn_minus" onclick="fn_cnt_change('', 0,  -1 , this, 'min')" />
														<input type="text" class="count" value="1" title="인원" />
														<input value="+" id="p" type="button" class="btn_plus" onclick="fn_cnt_change('', 0,  1 , this, 'plu')" />
														-->
														
														<c:if test="${paramMap.evtPersnGbn eq '10'}">
															<input type="button" class="btn_minus" onclick="fn_cnt_change('minus', 0,  -1, this)">
															<input id="personCnt" name="personCnt" class="count" value="1" type="text" autocomplete="false" title="참여 인원수 입력">
															<input type="button" class="btn_plus" onclick="fn_cnt_change('plus', 0,  1, this)">																
														</c:if>
														
														<c:if test="${paramMap.evtPersnGbn eq '20'}">
															<select id="personCnt" class="ml0" name="personCnt" title="인원을 선택하세요">
																<c:forEach var="item" begin="15" end="30" step="1" varStatus="status">
																	<option value="${item}">${item} 명</option>
																</c:forEach>														
															</select>																									
														</c:if>														
														
													</div>
													<!-- //numcontrol -->
												</div>
												<!-- //info -->
											</li>
										</ul>
									</div>
									<!-- //board_ty -->
									<!-- calc -->
									<div class="calc mb70">
										<div class="cal2">
											<dl>
												<dt>결제 금액</dt>
												<dd class="pink">무료</dd>
											</dl>
										</div>
									</div>
									<!-- //calc -->								
								
									<!-- agree_list -->
									<p class="stit">단체교육 예약 신청 시 유의사항에 동의해 주시기 바랍니다.</p>
									<ul class="agree_list ty">
										<li>
											<input type="checkbox" id="total_chk">
											<label for="total_chk">(필수) 예약유의사항</label>
											<div class="agree_cont">
												<p>* 주말 및 공휴일 혼잡 안내</p>
												<p class="sub_p">- 주말 및 공휴일 12시 이후부터는 입장 대기시간이 1시간 이상 소요 될 수 있으니 쾌적한 관람을 원하시는 분들께서는 평일에 방문해 주시길 바랍니다.</p>
											</div>
										</li>
									</ul>
									<!-- //agree_list -->								
								
									<!-- 
									<ul class="agree_choice ty">
										<li>
											<input type="checkbox" id="total_chk">
											<label for="total_chk">
											<c:choose>
													<c:when test="${evtData.evtFeeType eq '2001'}">예약 유의사항, 개인정보 이용동의에 모두 동의합니다.</c:when>
													<c:otherwise>예약 유의사항, 환불 유의사항, 개인정보 이용동의에 모두 동의합니다.</c:otherwise>
												</c:choose>
										</label>
										</li>
									</ul>
									-->
									
									
									
									
				<form:form commandName="evtEdcrsvnMstVO" name="evtEdcrsvnMstVO" id="evtEdcrsvnMstVO" action="./evtEdcrsvnStep2" method="post">
				
					<input type="hidden" name="evtNo"         id="evtNo"     value="${evtTime.evtNo}"/>
					<input type="hidden" name="evtPartcd"     id="evtPartcd" value="${evtTime.evtPartcd}"/>
					<input type="hidden" name="evtVeingdate"  id="evtTime"   value="<c:out value='${param.evtTime}'/>"/> 
					<input type="hidden" name="evtTime"       value="<c:out value="${param.evtTime}"/>"/>
					<input type="hidden" name="evtPersnGbn"   id="evtPersnGbn" value="<c:out value="${target}"/>"/>
					<input type="hidden" name="evtTimeseq"    id="evtTimeseq" value="${evtTime.evtTimeseq}"/>
					<input type="hidden" name="evtTimestdSeq" id="evtTimestdSeq" value="${evtTime.evtTimestdSeq}"/>

					<input type="hidden" name="evtRsvnMemno"      id="evtRsvnMemno" value="${loginVO.uniqId}"/>
					<input type="hidden" name="evtRsvnMemtype"    id="evtRsvnMemtype" value="${loginVO.memGbn}"/>
					<input type="hidden" name="evtVeingnmpr"      id="evtVeingnmpr"   value=""/>
					

					<form:hidden path="dcAnnualLimit" />
					<form:hidden path="evtRsvnIdx"/>
					
					<!-- agree_list -->
					<ul class="agree_list ty mb70">
					<c:forEach items="${termList }" var="item" varStatus="s">
						<c:choose>
							<c:when test="${evtData.evtFeeType eq '2001' && item.stplatId eq '4002'}">
							</c:when>
							<c:otherwise>
								<li>
									<div class="agree_tit">
										<input type="checkbox" id="c${s.index+1 }"/>
										<label for="c${s.index+1 }" class="on"><c:out value="${item.stplatName }"/></label>
										
										<!-- 원본 JYS -->
										<!-- <i class="on">내용보기</i> -->
										<a href="#" onclick="fnAgreeOnClick(this); return false;"><i class="on">내용보기</i></a>
										
									</div>
									<div class="agree_cont">
										<c:out value="${fn:replace(item.stplatCn, crlf , '<br>')}" escapeXml="false" />
									</div>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					</ul>
					<!-- //agree_list -->
	
					<!-- 
					<p class="stit mb5">예약 인원 선택 <span class="tb_txt">※ 쿠폰 또는 기타 할인은 결제 화면에서 변경하실 수 있습니다.</span></p>
					<c:if test="${evtEdcrsvnMstVO.dcAnnualLimit > 0}">
						<p class="desc mb5" style="text-align:left">유료회원 할인은 <span class="pink">최대 ${evtEdcrsvnMstVO.dcAnnualLimit }명</span> 까지 가능합니다.</p>
					</c:if>
					-->
					
					<!-- board_ty -->
					<div class="board_ty">
						<ul>
						<c:forEach items="${chrgList}" var="item" varStatus="s">
							<form:hidden path="chargeList[${s.index}].itemCd"    value="${item.itemCd}"/>
							<form:hidden path="chargeList[${s.index}].itemPrice" value="${item.itemPrice}"/>
							<form:hidden path="chargeList[${s.index}].itemNm"    value="${item.itemNm}"/>
							<form:hidden path="chargeList[${s.index}].dcRate"    value="${item.dcRate}"/>
							<form:hidden path="chargeList[${s.index}].dcAnnualRate" value="${item.dcAnnualRate}"/>
							<input type="hidden" id="prevCnt" name="prev"/>
							
							<li>
								<div class="info"> 
									<!-- info_tit -->
									<div class="info_tit">
										<span><c:out value="${item.itemNm }" />  <em><c:if test="${evtData.evtFeeType eq '1001'}"> <fmt:formatNumber value="${item.itemPrice}"/>원 </c:if></em></span>
										<!-- <button type="button" class="btn_ty_s2_c2" data-toggle="modal" data-target="#modal1">할인 적용</button> -->
									</div>
									<!-- //info_tit -->
									<!-- numcontrol -->
									<div class="numcontrol num01">
									
										<input type="button" class="btn_minus" onclick="fn_cnt_change('<c:out value="${item.itemCd }"/>' , ${s.index} ,  -1 , this, 'min')"/>
										<form:input path="chargeList[${s.index}].itemCnt" class="count" value="0" data-idx="${s.index}" data-item="${item.itemCd}" autocomplete="false" title="예약인원수 입력"/>
										<input type="button" class="btn_plus" onclick="fn_cnt_change('<c:out value="${item.itemCd }"/>' , ${s.index} ,  1 , this, 'plu')"/>
										
									</div>
									<!-- //numcontrol -->
								</div>
								<!-- //info -->
								<c:choose>
								<c:when test="${evtData.evtFeeType eq '2001'}">
									<ul class="info_price" id="DIV_PRICE_<c:out value="${item.itemCd}" />">
										<li class="ex_totalp">
											<dl >
												<dt>총 금액</dt>
												<dd>무료</dd>
											</dl>
										</li>
									</ul>
								</c:when>
								<c:otherwise>
									<!-- info_price -->
								<ul class="info_price" id="DIV_PRICE_<c:out value="${item.itemCd}" />">
									<li class="ex_totalp">
										<dl >
											<dt>총 금액</dt>
											<dd>무료</dd>
										</dl>
									</li>
									<c:if test="${item.dcAnnualRate > 0 and item.itemPrice>0}">
										<li class="ex_disc_2">
											<dl>
												<dt>유료회원 (<c:out value="${item.dcAnnualRate}"/>% 할인)<em id="DIV_AQTY_${item.itemCd }">0명 적용</em></dt>
												<dd>0 원</dd>
											</dl>
										</li>
									</c:if>
									<c:if test="${item.dcRate > 0 and item.itemPrice>0}">
										<li class="ex_disc">
											<dl>
												<dt><c:out value="${item.dcName}"/> (<c:out value="${item.dcRate}"/>% 할인)<em id="DIV_DQTY_${item.itemCd }">0명 적용</em></dt>
												<dd>무료</dd>
											</dl>
										</li>
									</c:if>
									<li class="ex_total">
										<dl>
											<dt>결제 예상 금액</dt>
											<dd>0 원</dd>
										</dl>
									</li>
								</ul>
								<!-- //info_price -->
								</c:otherwise>
							</c:choose>
							</li>
							</c:forEach>
								</ul>
							</div>
							
				</form:form>
						
							<!-- //board_ty -->
							<c:if test="${evtData.evtFeeType eq '1001'}">
								<!-- calc -->
								<div class="calc">
									<div class="cal1">
										<dl>
											<dt>총 금액</dt>
											<dd id="priceSum">무료</dd>
										</dl>
										<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
										<dl>
											<dt>할인 예상 금액</dt>
											<dd id="priceDiscount">0 원</dd>
										</dl>
									</div>
									<div class="cal2">
										<span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span>
										<dl>
											<dt>결제 예정 금액</dt>
											<dd class="pink" id="priceTotal">0 원</dd>
										</dl>
									</div>
								</div>
								<!-- //calc -->
								</c:if>
							</div>
							<!-- //signup2 -->
						</div>
						<!-- //join_wrap -->
						<ul class="btn_area">
						
							<c:if test="${paramMap.evtPersnGbn eq '10'}">
						
								<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지이동</a></li>
								<!-- <li><a href="javascript:fn_rsvn_send()" class="btn_ty_m_c1">예약하기</a></li> -->
								<li><a href="javascript:fn_next()" class="btn_ty_m_c1">다음단계</a></li>
								
							</c:if>
							
							<c:if test="${paramMap.evtPersnGbn eq '20'}">
						
								<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지이동</a></li>
								<li><a href="javascript:fn_next()" class="btn_ty_m_c1">다음단계</a></li>
								
							</c:if>							
							
						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
		<!-- //sub_cont -->
	<script type="text/javascript">

	function fn_next(obj) {
		
		 var notCheckCnt = 0;
			$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
				if (!$(item).prop("checked")) {
					notCheckCnt++;
				}
			});

		if (notCheckCnt > 0) {
			alert("약관을 확인하시고 모두 동의해주세요.");
			$('#total_chk').focus();
			return false;
		}
		
		$("#evtVeingnmpr").val($("#personCnt").val());		
		
		var totalCnt = Number($("#personCnt").val());
		
		var notCheckCnt = 0;
		var maxCnt = ${evtTime.totCapa-evtTime.rsvCnt};
		
		var feetype = '${evtData.evtFeeType}'
		var grpMinCnt = ${evtData.evtOrgMincnt};
		var grpMaxCnt = ${evtData.evtOrgMaxcnt};

		var target = "<c:out value='${target}'/>";

		console.log(totalCnt);
		console.log(grpMinCnt);
		console.log(grpMaxCnt);
		
		/*
		
		$.each($('.count') , function(idx , item) {
			var cnt = Number(document.getElementById("chargeList"+idx+".itemCnt").value);
			totalCnt += cnt;
		});
		
		*/

		$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
			if (!$(item).prop("checked")) {
				notCheckCnt++;
			}
		});

		if (totalCnt < 1) {
			
			alert("예약 인원을 선택해 주세요.");
			
			
		/* } else if (totalCnt > maxCnt) {
			
			 console.log(totalCnt);
			 console.log(maxCnt);
			
			 if(maxCnt == 0) {
				numFlag = true;				 
			 } else {
				alert("현재 잔여정원은 " + maxCnt + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
				numFlag = false;				 
			 }

		} else if (grpMaxCnt > 0 && totalCnt > grpMaxCnt) {
			alert("예약가능 최대인원은  " + grpMaxCnt + "명입니다.\n"+grpMaxCnt+"명 이하로 인원을 선택해 주세요.");
			numFlag = false;
		} else if (notCheckCnt > 0) {
			var msg = '예약유의사항 , 환불유의사항 , 개인정보 이용에 모두 동의후 예약하실 수 있습니다.';
			msg = feetype == '2001' ? '예약유의사항 , 개인정보 이용에 모두 동의후 예약하실 수 있습니다.' :
				'예약유의사항 , 환불유의사항 , 개인정보 이용에 모두 동의후 예약하실 수 있습니다.';
			alert(msg);
			$('.agree_list').find('input[type=checkbox]').each(function() {
				if ($(this).is('checked') == false) {
					$(this).focus()
					return false;
				}
			})
		} else if (target == "20" && grpMinCnt > 0 && totalCnt < grpMinCnt) {
			alert("단체 신청 최소 인원수 [" + grpMinCnt + "명] 미만으로 예약하실 수 없습니다.");
		} else if (target == "20" && grpMaxCnt > 0 && totalCnt > grpMaxCnt) {
			alert("단체 신청 최대  인원수를 초과하여 예약하실 수 없습니다.");
		
		} else if (target == "10" && grpMinCnt > 0 && totalCnt >= grpMinCnt) {
			if (confirm("${evtData.evtOrgMincnt} 명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
				window.location.replace("./evtEdcrsvnStep1?type=20&evtTimeseq=<c:out value="${param.evtTimeseq}"/>&evtTimestdseq=<c:out value="${param.evtTimestdseq}"/>&evtNo=<c:out value="${param.evtNo}"/>&evtTime=<c:out value="${param.evtTime}"/>");
			} */
			
		} else {
			$('#evtEdcrsvnMstVO').submit();
		}
		
	}

	function fn_cnt_change(itemCd, index, cnt, obj) {
		
		var target        = "<c:out value='${target}'/>";
		var varMaxCnt     = Number("${evtData.evtOrgMaxcnt}");
		var varAllRscvRow = Number("0");
		var varPernCnt    = $("#personCnt").val();
		
		var grpMinCnt = Number("${evtData.evtOrgMincnt}");
		var grpMaxCnt = Number("${evtData.evtOrgMaxcnt}");		

		console.log(target);
		console.log(varMaxCnt);
		console.log(varAllRscvRow);
		console.log(varPernCnt);
		
		if (itemCd == 'plus') {
			
			varPernCnt ++;

			/* 
			if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
				alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
				return false;
			}
			*/

			$("#personCnt").val(varPernCnt);

		} else if (itemCd == 'minus') {

			varPernCnt --;

			if (varPernCnt <= 0) {
				alert("더는 줄일 수 없습니다");
				return false;
			}

			$("#personCnt").val(varPernCnt);

		} else if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
			
			////alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			///fn_total_price_set();
			return false;
		}

		//fn_total_price_set();
		return false;

	}
	

	/* 
	function fn_cnt_change(itemCd , index, cnt , obj, type) {
		
		var target = "<c:out value="${target}"/>";
		var grpMinCnt = ${evtData.evtOrgMincnt};
		var grpMaxCnt = ${evtData.evtOrgMaxcnt};
		var numFlag = true;
		var numObj =  $(obj).parent().find(".count");
		var txt = numObj.val();
		var newTxt = parseInt(txt) + cnt;
		var prevCnt = document.getElementById("chargeList"+index+".itemCnt").value;
		var price = document.getElementById("chargeList"+index+".itemPrice").value;

		var maxCnt = ${evtTime.totCapa-evtTime.rsvCnt};

		if (type == "cus") {
			numObj = $(obj);
		}

		//총 인원 체크
		var totalCnt  = 0;
		$.each($('.count') , function(idx , item) {
			var myCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
			if (myCnt == "") myCnt = 0;
			totalCnt += parseInt(myCnt);
		});

		totalCnt = totalCnt+cnt;

		if (newTxt < 0) {
			//0보다 작을순 없음
		} else if (target == "10" && totalCnt >= grpMinCnt && grpMinCnt > 0 && cnt > 0) {
			if (confirm(grpMinCnt +"명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
				window.location.replace("./evtEdcrsvnStep1?type=20&evtTimeseq=<c:out value="${param.evtTimeseq}"/>&evtTimestdseq=<c:out value="${param.evtTimestdseq}"/>&evtNo=<c:out value="${param.evtNo}"/>&evtTime=<c:out value="${param.evtTime}"/>");
			}
			numFlag = false;
		} else if (totalCnt > maxCnt) {
			alert("현재 잔여정원은 " + maxCnt + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			numFlag = false;
		} else if (grpMaxCnt >0 && totalCnt > grpMaxCnt) {
			alert("예약가능 최대인원은  " + grpMaxCnt + "명입니다.\n"+grpMaxCnt+"명 이하로 인원을 선택해 주세요.");
			numFlag = false;
		} else {
			numObj.val(newTxt);
		}

		//총금액 셋팅
		if (!numFlag ) {
			numObj.val(prevCnt);
		}

		fn_total_price_set();
		
	}
	*/

	function numberWithCommas(x) {
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function fn_total_price_set() {
		var totalPrice = 0;
		var totalDisc = 0;

		var updownUnit = "${optData.payUpdownUnit}";
		var updownType =  "${optData.payUpdown}";

		var remainCnt = 0;
		var limitQty = 0;
		if ($('#dcAnnualLimit').val() != "") {
			limitQty = parseInt($('#dcAnnualLimit').val()) ;
		}

		$.each($('.count') , function(idx , item) {
			
			/* 
			var price = document.getElementById("chargeList"+idx+".itemPrice").value;
			var orignCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
			var discRate = document.getElementById("chargeList"+idx+".dcRate").value;
			var discAnnual = document.getElementById("chargeList"+idx+".dcAnnualRate").value;
			*/
			
			var price = 0;
			var orignCnt = 0;
			var discRate = 0;
			var discAnnual = 0;			

			var discPrice = 0;
			var discPrice2 = 0;
			var itemCd = $(this).data("item");
			var cnt = orignCnt;

			if ( discAnnual != "" && parseInt(discAnnual ) > 0 && limitQty == 0) {
				limitQty = 1000;
			}

			var newPrice = orignCnt * parseInt(price);
			var annualCnt = limitQty - remainCnt;

			if (annualCnt > 0 && discAnnual != "" && parseInt(discAnnual ) > 0) {
				//유료회원할인을 먹인다
				if (annualCnt >= orignCnt) {
					annualCnt = orignCnt;
					cnt = 0;
				} else {
					cnt = orignCnt - annualCnt;
				}

				discPrice2 = fn_pay_updown(updownUnit,updownType,discAnnual , price,annualCnt);
				$('#DIV_PRICE_'+itemCd).find('.ex_disc_2 > dl > dd').text(numberWithCommas(discPrice2) + " 원");
				$('#DIV_AQTY_'+itemCd).text(annualCnt + "명 적용");

				totalDisc += discPrice2;
			} else if (discAnnual != "" && parseInt(discAnnual ) > 0) {
				$('#DIV_PRICE_'+itemCd).find('.ex_disc_2 > dl > dd').text("0 원");
				$('#DIV_AQTY_'+itemCd).text("0명 적용");
			}


			if (discRate != "" && parseInt(discRate) > 0) {
				discPrice = fn_pay_updown(updownUnit,updownType,discRate , price,cnt);
				$('#DIV_PRICE_'+itemCd).find('.ex_disc > dl > dd').text(numberWithCommas(discPrice) + " 원");
				$('#DIV_DQTY_'+itemCd).text(cnt + "명 적용");

				totalDisc += discPrice;
			}

			totalPrice += newPrice;
			remainCnt  += parseInt(orignCnt);

			$('#DIV_PRICE_'+itemCd).find('.ex_total > dl > dd').text(numberWithCommas(newPrice-discPrice-discPrice2) + " 원");
			$('#DIV_PRICE_'+itemCd).find('.ex_totalp > dl > dd').text(numberWithCommas(newPrice) + " 원");


		});

		$('#priceSum').text(numberWithCommas(totalPrice) + " 원");
		$('#priceTotal').text(numberWithCommas(totalPrice-totalDisc) + " 원");
		$('#priceDiscount').text(numberWithCommas(totalDisc) + " 원");


	}

	$('#total_chk').click(function() {
		var chkBox = $(this);
		if (chkBox.is(':checked')) {
			$(".agree_list input[type='checkbox']").prop("checked", true);
		} else {
			$(".agree_list input[type='checkbox']").prop("checked", false);
		}
	}); //전체 약관 동의
	$('.agree_list.ty input[type="checkbox"] + label').click(function() {
		$(this).toggleClass('on');
	}); //제목클릭이벤트

	$('.agree_list.ty li i').click(function() {
		$(this).toggleClass('on');
		$(this).parent().siblings('.agree_cont').stop().slideToggle();
	});//화살표

	$('.count').on('keyup',function() {
		var idx = $(this).data("idx");
		var itemCd = $(this).data("item");

		if ($(this).val() == "") {
			$(this).val("0");
		}

		if ( isNaN($(this).val())) {
			alert("숫자만 입력해주세요.");
			$(this).val("0");
		}

		fn_cnt_change(itemCd,  idx , 0, this, 'cus');
	});



	setTimeout("fn_login_check()", 100); //로그인여부 체크
	setTimeout("fn_total_price_set()", 100); //historyback 시 금액 계산


	function fnAgreeOnClick(parmThis) {
		$(parmThis).parent().siblings('.agree_cont').stop().slideToggle();		
		return false;	
	}	
	
	function fn_rsvn_send() {

		var notCheckCnt = 0;
		$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
			if (!$(item).prop("checked")) {
				notCheckCnt++;
			}
		});

		if (notCheckCnt > 0) {
			alert("약관을 확인하시고 모두 동의해주세요.");
			$('#total_chk').focus();
			return false;
		}
		
		$("#evtVeingnmpr").val($("#personCnt").val());
		
		if (confirm("단체교육을 예약합니다")) {
			
			 $.ajax({
				 
			        type : "POST",
			        url  : './evtEdcrsvnAction',
			        data : $('#evtEdcrsvnMstVO').serialize(),
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
	
	function fn_reserve_success(data) {
		
		console.log(data);
		
		 if (data.result.resultCd == "SUCCESS") {
			 $('#evtRsvnIdx').val( data.result.resultMsg );
	   	 	 $('#evtEdcrsvnMstVO').attr("action" , "./evtEdcrsvnIndiResult");
	   		 $('#evtEdcrsvnMstVO').submit();
	   } else {
			alert(data.result.resultMsg);
	   }
		 
	}	
	
</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>