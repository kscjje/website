<%
 /**
  * @Class Name : myRsvnPayAjax.jsp
  * @Description : 예약 결제 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.10    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.10
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
<input type="hidden" name="LGD_AMOUNT" id="LGD_AMOUNT" value="<c:out value="${myRsvnVO.LGD_AMOUNT}"/>"/>
<input type="hidden" name="LGD_MID" id="LGD_MID" value="<c:out value="${myRsvnVO.LGD_MID}"/>"/>
<input type="hidden" name="LGD_BUYER" id="LGD_BUYER" value="<c:out value="${myRsvnVO.LGD_BUYER}"/>"/>
<input type="hidden" name="LGD_BUYERID" id="LGD_BUYERID" value="<c:out value="${myRsvnVO.LGD_BUYERID}"/>"/>
<input type="hidden" name="LGD_PRODUCTINFO" id="LGD_PRODUCTINFO" value="<c:out value="${myRsvnVO.LGD_PRODUCTINFO}"/>"/>
<input type="hidden" name="LGD_PRODUCTCODE" id="LGD_PRODUCTCODE" value="<c:out value="${myRsvnVO.LGD_PRODUCTCODE}"/>"/>
<input type="hidden" name="LGD_BUYEREMAIL" id="LGD_BUYEREMAIL" value="<c:out value="${myRsvnVO.LGD_BUYEREMAIL}"/>"/>
<input type="hidden" name="LGD_OID" id="LGD_OID" value="<c:out value="${myRsvnVO.LGD_OID}"/>" />
<input type="hidden" name="LGD_TIMESTAMP" id="LGD_TIMESTAMP" value="<c:out value="${myRsvnVO.LGD_TIMESTAMP}"/>"/>
<input type="hidden" name="LGD_HASHDATA" id="LGD_HASHDATA" value="<c:out value="${myRsvnVO.LGD_HASHDATA}"/>"/>
		<h4 class="h4">결제 할 예약 정보</h4>
		<p class="stit">총 <em class="pink">${fn:length(list)}</em>건 예약정보</p>
<c:if test="${fn:length(list) == 0}">
		<div>	등록된 데이타가 없습니다.</div>
</c:if>
<c:set var="prevItemCd" value="" />
<c:choose>
	<c:when test="${myRsvnVO.gubun eq 'EXBT'}">
		<!--  관람 결제 목록 -->
		<c:forEach items="${list}" var="resultVO" varStatus="status">
			<!-- his_list -->
			<ul class="his_list mb20">
				<li>
					<!-- 결제 가격 안내 -->
					<div class="his_tit">
						<span><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></span>
						<span><c:out value="${resultVO.rsvnNo}"/></span>
						<dl>
							<dt><c:out value="${resultVO.partNm}"/></dt>
							<dd><c:out value="${resultVO.exbtTypeNm}"/></dd>
							<dd><c:out value="${resultVO.exbtName}"/><fmt:parseDate var="extYmd" value="${resultVO.ymd}" pattern="yyyyMMdd"/>
								(<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;${fn:substring(resultVO.exbtStime,0,2)}:${fn:substring(resultVO.exbtStime,2,4)} ~ ${fn:substring(resultVO.exbtEtime,0,2)}:${fn:substring(resultVO.exbtEtime,2,4)})
							</dd>
						</dl>
					</div>
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">결제 가격 안내</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
<c:set var="prevItemCd" value="" />
<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
	<c:if test="${item.itemCd ne prevItemCd}">
		<c:set var="prevItemCd" value="${item.itemCd}" />
							<li>
								<div class="c_l"><c:out value="${item.itemNm}"/>(<fmt:formatNumber value="${item.price}"/>원)&nbsp;&nbsp;<c:out value="${item.itemCnt}"/>명</div>
								<div class="c_r">
									<span><fmt:formatNumber value="${item.amount}"/>원</span>
									<c:choose>
										<c:when test="${item.price <=0}"><button type="button" onclick="fn_none_msg()" class="btn_ty_s2_c2">할인 선택</button></c:when>
										<c:when test="${resultVO.target eq '2001'}"><button type="button" onclick="fn_group_msg()" class="btn_ty_s2_c2">할인 선택</button></c:when>
										<c:otherwise><button type="button" onclick="javascript:fn_discount('EXBT','<c:out value="${resultVO.rsvnIdx}"/>','<c:out value="${item.itemCd}"/>','<c:out value="${item.itemCnt}"/>')" class="btn_ty_s2_c6">할인 선택</button></c:otherwise>
									</c:choose>
								</div>
							</li>
	</c:if>
</c:forEach>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //결제 가격 안내 -->
					<!-- 쿠폰 적용 내역 -->
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">할인 적용 내역</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
<c:set var="discCnt" value="0" />
<c:forEach items="${resultVO.chargeList}" var="item" varStatus="s">
	<c:if test="${!empty item.dcType}">
		<li>
			<div class="c_l">
				<c:choose>
				 	<c:when test="${!empty item.cpnName}"><c:out value="${item.cpnName}"/></c:when>
				 	<c:when test="${!empty item.dcName}"> <c:out value="${item.dcName}"/></c:when>
				 	<c:when test="${!empty item.dcTypeNm}"><c:out value="${item.dcTypeNm}"/></c:when>
				</c:choose>
				(${item.dcRate}%)&nbsp;&nbsp;<c:out value="${item.itemNm}"/> : <c:out value="${item.itemSubCnt}"/>명<c:set var="discCnt" value="${discCnt+1}" />
			</div>
			<div class="c_r">
				<span>- <fmt:formatNumber value="${item.dcAmount}"/>원</span>
				<c:choose>
					<c:when test="${item.dcKindCd eq '6001'}"><button type="button" onclick="fn_group_msg()" class="btn_ty_s2_c2">삭제</button></c:when>
					<c:when test="${item.dcKindCd eq '4010'}"><button type="button" onclick="fn_disc_msg()" class="btn_ty_s2_c2 del">삭제</button></c:when>
					<c:otherwise>
						<button type="button"  onclick="javascript:fn_discount_cancel('EXBT','<c:out value="${resultVO.rsvnIdx}"/>','<c:out value="${item.saleSeq}"/>','<c:out value="${item.itemCd}"/>')" class="btn_ty_s2_c6 del">삭제</button>
					</c:otherwise>
				</c:choose>
			</div>
		</li>
	</c:if>
</c:forEach>
<c:if test="${discCnt == 0}">
	<li>
		<div class="c_l">할인 적용 내역이 없습니다.</div>
	</li>
</c:if>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //쿠폰 적용 내역 -->
					<div class="calc his_calc">
						<div class="cal1">
							<dl>
								<dt>총 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.saleamt}"/>원</dd>
							</dl>
							<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
							<dl>
								<dt>할인 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.saleamt-resultVO.payamt}"/>원</dd>
							</dl>
						</div>
						<div class="cal2">
							<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
							<dl>
								<dt>최종 결제 금액</dt>
								<dd class="blu"><fmt:formatNumber value="${resultVO.payamt}"/>원</dd>
							</dl>
						</div>
					</div>
				</li>
			</ul>
			</c:forEach>
	</c:when>
	<c:when test="${myRsvnVO.gubun eq 'EVT'}">
		<c:forEach items="${list}" var="resultVO" varStatus="s">
			<ul class="his_list mb20">
				<li>
					<!-- 결제 가격 안내 -->
					<div class="his_tit">
						<span><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></span>
						<span><c:out value="${resultVO.evtRsvnno}"/></span>
						<dl>
							<dt><c:out value="${resultVO.evtName}"/></dt>
							<dd><fmt:parseDate var="extYmd" value="${resultVO.evtVeingdate}" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp; ${fn:substring(resultVO.evtStime,0,2)}:${fn:substring(resultVO.evtStime,2,4)} ~ ${fn:substring(resultVO.evtEtime,0,2)}:${fn:substring(resultVO.evtEtime,2,4)}</dd>
						</dl>
					</div>
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">결제 가격 안내</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
<c:set var="prevItemCd" value="" />
<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
	<c:if test="${item.evtRsvnItemcd ne prevItemCd}">
		<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
							<li>
								<div class="c_l"><c:out value="${item.evtItemNm}"/>(<fmt:formatNumber value="${item.evtCost}"/>원)&nbsp;&nbsp;<c:out value="${item.evtRsvnItemcnt}"/>명</div>
								<div class="c_r">
									<span><fmt:formatNumber value="${item.evtSalamt}"/>원</span>
									<c:choose>
										<c:when test="${item.evtCost <=0}"><button type="button" onclick="fn_none_msg()" class="btn_ty_s2_c2">할인 선택</button></c:when>
										<c:when test="${resultVO.evtPersnGbn eq '2001'}"><button type="button" onclick="fn_group_msg()" class="btn_ty_s2_c2">할인 선택</button></c:when>
										<c:otherwise>
											<button type="button"  onclick="javascript:fn_discount('EVT','<c:out value="${resultVO.evtRsvnIdx}"/>','<c:out value="${item.evtRsvnItemcd}"/>','<c:out value="${item.evtRsvnItemcnt}"/>')" class="btn_ty_s2_c6">할인 선택</button>
										</c:otherwise>
									</c:choose>
								</div>
							</li>
	</c:if>
</c:forEach>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //결제 가격 안내 -->
					<!-- 쿠폰 적용 내역 -->
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">할인 적용 내역</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
<c:set var="discCnt" value="0" />
<c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
	<c:if test="${!empty item.evtDcType}">
		<li>
			<div class="c_l">
				<c:choose>
				 	<c:when test="${!empty item.cpnName}"><c:out value="${item.cpnName}"/></c:when>
				 	<c:when test="${!empty item.dcName}"><c:out value="${item.dcName}"/></c:when>
				 	<c:when test="${!empty item.dcTypeNm}"><c:out value="${item.dcTypeNm}"/></c:when>
				</c:choose>
				(${item.evtDcRate}%)&nbsp;&nbsp;<c:out value="${item.evtItemNm}"/> : <c:out value="${item.itemSubCnt}"/>명<c:set var="discCnt" value="${discCnt+1}" />
			</div>
			<div class="c_r">
				<span>- <fmt:formatNumber value="${item.evtDcamt}"/>원</span>
				<c:choose>
					<c:when test="${item.dcKindCd eq '6001'}"><button type="button" onclick="fn_group_msg()" class="btn_ty_s2_c2 del">삭제</button></c:when>
					<c:when test="${item.dcKindCd eq '4010'}"><button type="button" onclick="fn_disc_msg()" class="btn_ty_s2_c2 del">삭제</button></c:when>
					<c:otherwise>
						<button type="button"  onclick="javascript:fn_discount_cancel('EVT','<c:out value="${resultVO.evtRsvnIdx}"/>','<c:out value="${item.evtSaleSeq}"/>','<c:out value="${item.evtRsvnItemcd}"/>')" class="btn_ty_s2_c6 del">삭제</button>
					</c:otherwise>
				</c:choose>
			</div>
		</li>
	</c:if>
</c:forEach>
<c:if test="${discCnt == 0}">
	<li>
		<div class="c_l">할인 적용 내역이 없습니다.</div>
	</li>
</c:if>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //쿠폰 적용 내역 -->
					<div class="calc his_calc">
						<div class="cal1">
							<dl>
								<dt>총 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.evtRsvnSaleamt}"/>원</dd>
							</dl>
							<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
							<dl>
								<dt>할인 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.evtRsvnSaleamt-resultVO.evtRsvnPayamt}"/>원</dd>
							</dl>
						</div>
						<div class="cal2">
							<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
							<dl>
								<dt>최종 결제 금액</dt>
								<dd class="blu"><fmt:formatNumber value="${resultVO.evtRsvnPayamt}"/>원</dd>
							</dl>
						</div>
					</div>
				</li>
			</ul>
		</c:forEach>
	</c:when>
	<c:when test="${myRsvnVO.gubun eq 'EDC'}">
		<c:forEach items="${list}" var="resultVO" varStatus="s">
			<ul class="his_list mb20">
				<li>
					<!-- 결제 가격 안내 -->
					<div class="his_tit">
						<span><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></span>
						<span><c:out value="${resultVO.edcRsvnno}"/></span>
						<dl>
							<dt>
								<c:out value="${resultVO.edcPrgmnm}"/>
							</dt>
							<dd>
								${resultVO.eduPeriod }
							</dd>
						</dl>
					</div>
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">결제 가격 안내</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
							<li>
								<div class="c_l">
								<c:if test="${resultVO.edcOnlineyn eq 'Y' }">
									<span class="st s3"><i><img src="<%=strContext%>/images/sub/ico_online.png" alt=""></i>온라인교육</span>
								</c:if>

								<c:out value="${resultVO.edcPrgmnm}"/>
								</div>
								<div class="c_r">
									<span><fmt:formatNumber value="${resultVO.edcProgmCost}"/>원</span>
<c:if test="${resultVO.exclDcyn ne 'Y' }">
									<button type="button" onclick="javascript:fn_discount('EDC','<c:out value="${resultVO.edcRsvnReqid}"/>','<c:out value="${resultVO.edcReqItemCd}"/>','1')" class="btn_ty_s2_c6">할인 선택</button>
</c:if>
								</div>
							</li>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //결제 가격 안내 -->
<c:if test="${resultVO.exclDcyn ne 'Y' }">
					<!-- 쿠폰 적용 내역 -->
					<a href="#" onclick="fnHisStitOnClick(this); return false;"><div class="his_stit on">할인 적용 내역</div></a>
					<!-- his_cont -->
					<div class="his_cont active">
						<ul>
<c:choose>
	<c:when test="${resultVO.edcDcamt > 0 }">
								<li>
									<div class="c_l">
									<c:choose>
										 	<c:when test="${!empty resultVO.cpnName}"><c:out value="${resultVO.cpnName}"/></c:when>
										 	<c:when test="${!empty resultVO.dcName}"><c:out value="${resultVO.dcName}"/></c:when>
										 	<c:when test="${!empty resultVO.dcTypeNm}"><c:out value="${resultVO.dcTypeNm}"/></c:when>
										</c:choose>
										<c:choose><c:when test="${resultVO.cpnDcrate > 0}">(${resultVO.cpnDcrate}%)</c:when><c:when test="${resultVO.dcRate>0 }">(${resultVO.dcRate}%)</c:when></c:choose><c:set var="discCnt" value="${discCnt+1}" />
									</div>
									<div class="c_r">
										<span>- <fmt:formatNumber value="${resultVO.edcDcamt}"/>원</span>
										<c:choose>
											<c:when test="${resultVO.dcKindCd eq '4010'}"><button type="button" onclick="fn_disc_msg()" class="btn_ty_s2_c2 del">삭제</button></c:when>
											<c:otherwise><button type="button"  onclick="javascript:fn_discount_cancel('EDC','<c:out value="${resultVO.edcRsvnReqid}"/>','<c:out value="${resultVO.edcRsvnReqid}"/>')" class="btn_ty_s2_c6 del">삭제</button></c:otherwise>
										</c:choose>
									</div>

								</li>
	</c:when>
	<c:otherwise>
								<li>
									<div class="c_l">할인 적용 내역이 없습니다.</div>
								</li>
	</c:otherwise>
</c:choose>
						</ul>
					</div>
					<!-- //his_cont -->
					<!-- //쿠폰 적용 내역 -->
</c:if>
					<div class="calc his_calc">
						<div class="cal1">
							<dl>
								<dt>총 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.edcProgmCost}"/>원</dd>
							</dl>
							<span><img src="<%=strContext%>/images/sub/ico_minus2.png" alt="빼기아이콘"></span>
							<dl>
								<dt>할인 금액</dt>
								<dd><fmt:formatNumber value="${resultVO.edcDcamt}"/>원</dd>
							</dl>
						</div>
						<div class="cal2">
							<span><img src="<%=strContext%>/images/sub/ico_total2.png" alt="전체금액아이콘"></span>
							<dl>
								<dt>최종 결제 금액</dt>
								<dd class="blu"><fmt:formatNumber value="${resultVO.edcTotamt}"/>원</dd>
							</dl>
						</div>
					</div>
				</li>
			</ul>
		</c:forEach>
	</c:when>

</c:choose>
<!-- calc -->
<div class="calc">
	<div class="cal1">
		<dl>
			<dt>총 금액</dt>
			<dd><fmt:formatNumber value="${payAmount}"/>원</dd>
		</dl>
		<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
		<dl>
			<dt>총 할인 금액</dt>
			<dd><fmt:formatNumber value="${payAmount-myRsvnVO.LGD_AMOUNT}"/>원</dd>
		</dl>
	</div>
	<div class="cal2">
		<span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span>
		<dl>
			<dt>최종 결제 금액</dt>
			<dd class="pink"><fmt:formatNumber value="${myRsvnVO.LGD_AMOUNT}"/>원</dd>
		</dl>
	</div>
</div>
<!-- //calc -->

<script>

	$('.his_stit').click(function() {
		$(this).toggleClass('on');
		$(this).next('.his_cont').stop().slideToggle();
	}); //제목클릭이벤트
	
	function fnHisStitOnClick(parmThis) {
		$(parmThis).next('.his_cont').stop().slideToggle();
		return false;	
	}

</script>