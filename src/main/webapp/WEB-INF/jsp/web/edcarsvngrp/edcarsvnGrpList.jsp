<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
	 /**
	  * @Class Name : edcarsvnList.jsp
	  * @Description : 교육 예약 목록 리스트
	  * @Modification Information
	  * @
	  * @  수정일       수정자      수정내용
	  * @ -------       --------    ---------------------------
	  * @ 2020.09.08    전영석      최초 생성
	  *
	  *  @author 전영석
	  *  @since 2020.09.08
	  *  @version 1.0
	  * 
	  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit">교육예약</h3>
					<div class="cate_wrap">
						<div class="cate_list">
							<div>
								<span class="on">분류</span>
								
								<ul class="cate_tab active">
								
									<%-- <li id="prg_all" <c:if test="${paramMap.edcProgmType eq 'ALL'}">class="on"</c:if>><a href="#none" class="btn_ty_m5_c2" onclick="fn_sel_item(1, 'ALL');">전체보기</a></li> --%>
									<li class="lv1 on" data-cd="3001"><a href="#none" class="btn_ty_m5_c2" onclick="fn_sel_item(1, '3001');">가족</a></li>

								</ul>
								
							</div>
							<div>
								<span>상태</span>
								<ul class="cate_tab">
								
									<li id="stat_all" data-status="<c:out value="${paramMap.eduAccStus}"/>" <c:if test="${paramMap.eduAccStus eq 'ALL'}">class="on"</c:if>>
										<a href="javascript:;" class="btn_ty_m5_c2" onclick="fn_sel_item(4, 'ALL');">전체보기</a>
									</li>
									<li class="sta" data-status="ING">
										<a href="javascript:;" class="btn_ty_m5_c2" onclick="fn_sel_item(4, 'ING');">
											접수중
										</a>
									</li>
									<li class="sta" data-status="ACCT">
										<a href="javascript:;" class="btn_ty_m5_c2" onclick="fn_sel_item(4, 'ACCT');">
											접수준비
										</a>
									</li>
									<li class="sta" data-status="END">
										<a href="javascript:;" class="btn_ty_m5_c2" onclick="fn_sel_item(4, 'END');">
											접수종료
										</a>
									</li>								

								</ul>
							</div>
							<div>
								<span>요일</span>
								<ul class="cate_tab">
								<!-- 
									<ul class="cate_tab">
								 -->
										<li id="week_all" <c:if test="${paramMap.edcWeekNm eq ''}">class="on"</c:if>>
											<a href="#none" class="btn_ty_m5_c2" onclick="fn_sel_item(6, '');">전체보기</a>
										</li>
										<c:forEach items="${edcarsvnWeekList}" var="item" varStatus="status">
											<li class="week" data-status="${item.edcDaygbn}">
												<a href="#none" class="btn_ty_m5_c2" onclick="fn_sel_item(6, '${item.edcDaygbn}');"><c:out value="${item.edcDaygbnNm}" />요일</a>
											</li>
										</c:forEach>
									<!-- 
									</ul>								
 									-->
								</ul>
							</div>
						</div>
						<!-- //cate_list -->
					</div>
					<!-- //care_wrap -->

					<div class="search_wrap">
						<form:form commandName="eventProgramVO" id="searchForm" action="./edcarsvnGrpList" method="get">
						
							<input type="hidden" id="eduFreeYN"     name="eduFreeYN"     value="<c:out value="${paramMap.eduFreeYN}"/>" />
							<input type="hidden" id="eduAccStus"    name="eduAccStus"    value="<c:out value="${paramMap.eduAccStus}"/>" />
							<input type="hidden" id="edcPrgmid"     name="edcPrgmid"     value="<c:out value="${paramMap.edcPrgmid}"/>" />
							<input type="hidden" id="edcPrgmidSec"  name="edcPrgmidSec"  value="<c:out value="${paramMap.edcPrgmidSec}"/>" />
							<input type="hidden" id="edcPrgmidThr"  name="edcPrgmidThr"  value="<c:out value="${paramMap.edcPrgmidThr}"/>" />
							<input type="hidden" id="edcWeekNm"     name="edcWeekNm"     value="<c:out value="${paramMap.edcWeekNm}"/>" />
							<input type="hidden" id="edcProgmType"  name="edcProgmType"  value="<c:out value="${paramMap.edcProgmType}"/>" />
							
							<input type="hidden" id="pageIndex"     name="pageIndex" />
							<input type="hidden" id="ctgLvl"        name="ctgLvl" />
							<input type="hidden" id="scrollVar"     name="scrollVar" />						
						
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						
							<fieldset>
								<legend>게시판 검색</legend>
								<div class="search_box">

									<label for="srchItem">검색어입력 </label>
									<input id="srchItem" name="edcPrgmNm" title="검색어를 입력하세요" type="text" value="<c:out value='${paramMap.edcPrgmNm}'/>" class="input_text" placeholder="검색어를 입력해주세요.">
									<button type="submit" class="button"><span>검색</span></button>								
								
								</div>
							</fieldset>
							
						</form:form>
					</div>
					<!-- //search_wrap -->

					<div id="edu_list">
					</div>

				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->

<script type="text/javascript">

	$(document).ready(function() {
		fn_set_params();
	});

	function fn_drt(edcno) {
		var _scrollTop = window.scrollY || document.documentElement.scrollTop;
		$('#scrollVar').val(_scrollTop);

		location.hash = $('#searchForm').serialize();
		location.href = './edcarsvnGrpDetail?edcPrgmid='+edcno;
	}

	function fn_set_params() {
		
		var hsh = location.hash;

		console.log(hsh);
		
		if (hsh != '') {
			
			var obj = fn_qs_json(location.hash);
			var idx = 1;

			$.each(obj, function(key, value) {
				var gubun = '';
				switch (key) {
				case 'edcProgmType':
					gubun = '1';
					break;
				case 'edcPrgmidSec':
					gubun = '2';
					break;
				case 'edcPrgmidThr':
					gubun = '3';
					break;
				case 'eduAccStus':
					gubun = '4';
					break;
				case 'eduFreeYN':
					gubun = '5';
					break;
				case 'edcWeekNm':
					gubun = '6';
					break;
				case 'pageIndex' :
					idx = value;
					break;
				case 'scrollVar' :
					$('#scrollVar').val(value);
					break;
				}
				
				fn_set_item(gubun, value);
				
			  });//end of each
			  
			  fn_set_list(idx,'1' , $('#scrollVar').val())	;
			  
		} else {
			
			fn_set_list(1,'1');
			
		}
		
	}
	function fn_order_select(obj) {
		fn_set_list(1,'1');
	}

	function fn_ctg_dspy(clsId, dataId, obj) {
		$('.'+clsId).each(function() {
			if ($(this).data(dataId) == obj) {
				$(this).show();
			} else {
				$(this).removeClass('on');
				$(this).css('display','none');
			}
		});
	}

	function fn_each_func(clsNm, dataNm, allNm, arguValue, argId) {
		$('.'+clsNm).each(function() {
			if ($(this).data(dataNm) == arguValue) {
				$(this).addClass('on').attr("title" , "선택됨");
				$('#'+allNm).removeClass('on').removeAttr("title");
			} else {
				$(this).removeClass('on').removeAttr("title");;
			}
		})
		if (arguValue == 'ALL' || arguValue =='') {
			$('#'+allNm).addClass('on').attr("title" , "선택됨");
			$('#'+argId).val('');
		}
	}

	function fn_set_item(jobGubun, arguValue) {
		if (jobGubun == 1) {
			$("#edcProgmType").val(arguValue);
			fn_each_func('lv1', 'cd', 'prg_all', arguValue);
			fn_each_func('lv2', 'cd', 'sprg_all', arguValue);
			// low level
			$('#sprg_all').addClass('on').attr("title" , "선택됨");
			$('#tprg_all').addClass('on').attr("title" , "선택됨");
			fn_ctg_dspy('lv2', 'prncd',arguValue);
			fn_ctg_dspy('lv3', 'prncd',arguValue);

			if (arguValue == 'ALL') {
				$("#ctgLvl").val("0");
				$('#prg_all').addClass('on').attr("title" , "선택됨");
				$("#edcProgmType").val('');
				$("#edcPrgmidSec").val('');
				$("#edcPrgmidThr").val('');
			} else {
				$("#ctgLvl").val("0");
			}
		} else if (jobGubun == 2) {
			$("#edcPrgmidSec").val(arguValue);
			fn_each_func('lv2', 'cd', 'sprg_all', arguValue);
			fn_ctg_dspy('lv3', 'prncd',arguValue);

			//depth-3 조정
			fn_each_func('lv3', 'cd', 'tprg_all', arguValue);
			$("#edcPrgmidThr").val('');
			$('#tprg_all').addClass('on').attr("title" , "선택됨");
			if (arguValue == 'ALL') {
				$('#sprg_all').addClass('on').attr("title" , "선택됨");
				$("#ctgLvl").val("0");
				$("#edcPrgmidSec").val('');
			} else {
				$("#ctgLvl").val("1");
			}

		} else if (jobGubun == 3) {
			$("#edcPrgmidThr").val(arguValue);
			fn_each_func('lv3', 'cd', 'tprg_all', arguValue);

			if (arguValue == 'ALL' || arguValue == '') {
				$('#tprg_all').addClass('on').attr("title" , "선택됨");
				$("#edcPrgmidThr").val('');
				$("#ctgLvl").val("1");
			} else {
				$("#ctgLvl").val("2");
			}
		} else if (jobGubun == 4) {
			$("#eduAccStus").val(arguValue);
			fn_each_func('sta', 'status', 'stat_all', arguValue, 'eduAccStus' );
		} else if (jobGubun == 5) {
			$("#eduFreeYN").val(arguValue);
			fn_each_func('free', 'status', 'free_all', arguValue, 'eduFreeYN');
			if (arguValue == '9') {
				$('#free_all').addClass('on').attr("title" , "선택됨");
			}
		} else if (jobGubun == 6) {
			$("#edcWeekNm").val(arguValue);
			fn_each_func('week', 'status', 'week_all', arguValue, 'edcWeekNm');
		}
	}

	function fn_sel_item(jobGubun, arguValue) {
		fn_set_item(jobGubun, arguValue);
		fn_set_list(1,'1');
	}


	function fn_reserve_url(programId , eduType, eduGender) {
		
		var _scrollTop = window.scrollY || document.documentElement.scrollTop;
		
		$('#scrollVar').val(_scrollTop);

		var url =  "/web/edcarsvngrp/edcarsvnGrpRegist?edcPrgmid=" + programId;
		
		location.hash = $('#searchForm').serialize();
		
		var msg = "";
		var paramAdd = "";
		<c:choose>
			<c:when test="${!empty userInfo}">
				// 예약가능
				var myGender = "<c:out value="${userInfo.gender}"/>";

				if ((eduType == "1001" || eduType == "2001" )  && "${userInfo.isMember}" == "true") {
					if ( eduGender == "2001" && myGender != "2" ) {
						alert("여성만 신청가능한 교육입니다.");
					} else if ( eduGender == "1001" && myGender != "1") {
						alert("남성만 신청가능한 교육입니다.");
					} else {
						window.location.href =url;
					}
				} else if ((eduType == "1001" || eduType == "2001" ) && "${userInfo.isMember}" == "false") {
					alert("비회원은 신청이 불가능한 교육입니다. 회원으로 로그인 하신 후 다시 신청해 주시기 바랍니다.");
				} else {
					window.location.href =url;
				}
			</c:when>
			<c:otherwise>
				if (eduType == "3001" || eduType == "9001") {
					msg = "회원 로그인 또는 비회원 본인인증 후 예약하실 수 있습니다.";
				} else {
					msg = "회원 로그인 후 예약하실 수 있습니다.";
					paramAdd = "&member_yn=Y";
				}

				if (msg != "") {
					if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
						window.location.href = "/web/member/login?returnURL=" + encodeURIComponent(url) + paramAdd;
					}
				}
			</c:otherwise>
		</c:choose>
	}

	//개인/단체 전용
	function fn_reserve_url_4001(programId , eduType, eduGender, eduUserBtn) {
		
		var _scrollTop = window.scrollY || document.documentElement.scrollTop;
		
		$('#scrollVar').val(_scrollTop);

		var url =  "/web/edcarsvngrp/edcarsvnGrpRegist?edcPrgmid=" + programId + "&eduUserBtn=" + eduUserBtn;
		
		location.hash = $('#searchForm').serialize();
		
		var msg = "";
		var paramAdd = "";
		<c:choose>
			<c:when test="${!empty userInfo}">
				// 예약가능
				var myGender = "<c:out value='${userInfo.gender}'/>";

				if ((eduType == "4001")  && "${userInfo.isMember}" == "true") {
					window.location.href =url;
				} else if ((eduType == "4001") && "${userInfo.isMember}" == "false") {
					alert("비회원은 신청이 불가능한 교육입니다. 회원으로 로그인 하신 후 다시 신청해 주시기 바랍니다.");
				} else {
					window.location.href=url;
				}
			</c:when>
			<c:otherwise>

				msg = "회원 로그인 후 예약하실 수 있습니다.";
				paramAdd = "&member_yn=Y";

				if (msg != "") {
					if (confirm(msg + "\n로그인 페이지로 이동하시겠습니까?")) {
						window.location.href = "/web/member/login?returnURL=" + encodeURIComponent(url) + paramAdd;
					}
				}
			</c:otherwise>
		</c:choose>
	}
	
	function fn_set_list(idx, type, scrollVar) {
		
		////$('body').showLoading(); 풀어야함
		
		var url = './edcarsvnGrpListAjax';
		var pIdx = idx != '' ? idx : '1';
		$('#pageIndex').val(pIdx);

		$.ajax({
			url : url,
			data : $('#searchForm').serialize(),
			method : 'GET',
			dataType : 'html',
			cache : false,
			success : function(data, textStatus, jqXHR) {
				
				$('#edu_list').html(data);
				////$('body').hideLoading();

				if (type == '' || type == undefined) {
					var ofset= $('.list_middle').offset().top-50;
					$('html').animate({scrollTop : ofset},100);
				} else if (scrollVar) {
					window.scrollTo(0, scrollVar);
				}
				//hash 없애기
				var uri = window.location.toString();
			    if (uri.indexOf("#") > 0) {
			        var clean_uri = uri.substring(0, uri.indexOf("#"));
			        window.history.replaceState({}, document.title, clean_uri);
			    }
			},
			error : function(request, error, jqXHR) {
				//alert("일정 불러오기 실패");
			}
		});

	}
</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
