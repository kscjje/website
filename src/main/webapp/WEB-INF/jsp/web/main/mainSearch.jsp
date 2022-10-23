<%
 /**
  * @Class Name : mainSeach.jsp
  * @Description : 메인 화면 검색결고ㅏ JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.25    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.25
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script type="text/javascript">

$(document).ready(function() {

	/* 	
	$("#title").on('keydown', function(e) {
		
		if (e.keycode == 13) {
			console.log("--- enter");
		}
		
		
	});
	*/
	
});

// function fn_chk_frm(obj) {
// 	var sKeywrd = $('#title').val();
// 	var flag = true;
// 	if (sKeywrd == '' ) {
// 		alert('검색어를 입력해주십시오.');
// 		$('#title').focus();
// 		return false;
// 	}
// 	return flag;
// }

function fn_send_page(type, idx, partcd, title, prnno) {
	var url = '';
	switch (type) {
	case 'EVT':
		url = './evtrsvn/evtrsvnDetail?evtNo='+idx;
		break;
	case 'EDC':
		url = '/web/edcarsvn/edcarsvnDetail?edcPrgmid='+idx;
		break;
	case 'EXBT':
		url = './exbtrsvn/dspy/'+partcd+'?exbtSeq='+idx;
		break;
	case 'faq':
		url = './rsvguidcntr/'+type+'/list?searchCnd=3&searchWrd='+title;
		break;
	default:
		url = './rsvguidcntr/'+type+'/detail?nttId='+idx;
		break;
	}

	location.href= url;
}

// function fn_odr_list(odr) {
// 	$('#orderType').val(odr);
// 	$('#mainSearchVO').submit();
// }

// $("#mainSearchForm").submit(function(e) {
// 	alert("--");
//     e.preventDefault();
// });

</script>

<div class="sub_cont pt0" id="container">
	<div class="full_bg">
			<div class="inner">

<form:form commandName="mainSearchVO" method="get" id="mainSearchForm">
<form:hidden path="searchType" />
<form:hidden path="orderType" />

					<div class="search_wrap_ty mb15">
					
						<label for="searchTitle">검색어</label>
						
						<form:input id="searchTitle" name="title" path="title" class="searchbar" placeholder="검색어를 입력해 주세요." onclick="return false;" />
						
						 <!-- 
						<button type="button" class="btn_del" onclick="fnSearchReset(); return false;">
							<img src="<%=strContext%>/images/sub/ico_del.png" alt="삭제버튼">
						</button>
						<button type="button" class="btn_search" onclik="fnSearchExe(); return false;"> 
							<img src="<%=strContext%>/images/sub/ico_search.png" alt="검색버튼">
						</button>
						-->
						
					</div>

						<a href="#" class="btn_del"    id="btnDelete" onclick="fnSearchReset(this); return false;" ><img src="<%=strContext%>/images/sub/ico_del.png" alt="삭제버튼" /></a>
						<a href="#" class="btn_search" id="btnSearch" onclick="fnSearchSubmit(this); return false;"><img src="<%=strContext%>/images/sub/ico_search.png" alt="검색버튼" /></a>
					
					<!-- //search_wrap_ty-->
					<div class="tag_list">
						<%-- <span class="tag">#과학캠프</span>
						<span class="tag">#캠프</span>
						<span class="tag">#여름방학특강</span>
						<span class="tag">#카이스트</span>
						<span class="tag">#과학캠프</span>
						<span class="tag">#캠프</span>
						<span class="tag">#여름방학특강</span> --%>
					</div>
					
					</form:form>
				</div>
				<!-- //inner -->
			</div>
			
	<div class="inner pt70">
	
<c:if test="${!empty searchVO.title }">
		<p class="ser_txt"><em><c:out value="${searchVO.title }"/></em> 에 대한 검색 결과는 총
		<c:choose>
			<c:when test="${searchVO.searchType eq 'rsv' }">
				<em><c:out value="${listsize1 }"/></em>
			</c:when>
			<c:when test="${searchVO.searchType eq 'board' }">
				<em><c:out value="${listsize2 }"/></em>
			</c:when>
		</c:choose>
		 건 입니다.</p>
		<!-- //search_wrap -->
		<ul class="tab tab5 mb50">
		
			<li <c:if test="${searchVO.searchType eq 'rsv' }">class="on"</c:if>><a href="#" onclick="fnChangeType('rsv'); return false;" id="btnChangeTypeRsv">예약상품 및 프로그램<em><c:out value="${listsize1 }"/></em></a></li>
			<li <c:if test="${searchVO.searchType eq 'board' }">class="on"</c:if>><a href="#" onclick="fnChangeType('board'); return false;" id="btnChangeTypeBoard">게시판<em><c:out value="${listsize2 }"/></em></a></li>
			
		</ul>
</c:if>

		<div class="radio_box radio_box2 txr mb20">
		
			<!-- JYS 웹접근성 위배 2021.03.02 -->
			<label><input type="radio" class="radio" name="orderType" id="thisOrderType1" value="des" <c:if test="${searchVO.orderType eq 'des' or empty searchVO.orderType }">checked</c:if>><em></em><span>최신순</span></label>
			<label><input type="radio" class="radio" name="orderType" id="thisOrderType2" value="asc" <c:if test="${searchVO.orderType eq 'asc'}">checked</c:if>><em></em><span>오래된순</span></label>			
		
			<label><a href="#" id="searchKeySubmit"><input type="button" class="btn_ty_s3_c9" value="   검색   "/></a></label>
				
		</div>
		
		
		
		<div class="table2 type responsive-type">
			<table>
				<caption>게시판 - 번호, 제목, 담당부서, 작성일, 조회</caption>
				<!--               <colgroup>
                                        <col style="width:11%">
                                        <col style="">
                                        <col style="width:11%">
                                        <col style="width:11%">
                                        <col style="width:10%">
                                    </colgroup>-->
				<tbody>
				<!-- 본문 -->
				<c:choose>
					<c:when test="${empty list }">
					<tr>
						<td style="padding: 26px 30px !important;text-align: center;color: #222;">검색된 데이터가 없습니다.</td>
					</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${list }" var="v" varStatus="s">
							<tr>
								<td>
									<c:choose>
									<c:when test="${searchVO.searchType eq 'rsv' }">
									<a href="javascript:fn_send_page('<c:out value="${v.ctgr }"/>','<c:out value="${v.idx }"/>', '<c:out value="${v.partcd }"/>', '<c:out value="${ v.title}"/>')">
										<div class="e_cont">
										<div class="e_tit">
										<strong>
											<c:choose>
												<c:when test="${v.ctgr eq 'EVT' }">[강연/행사/영화]</c:when>
												<c:when test="${v.ctgr eq 'EXBT' }">[관람]</c:when>
												<c:when test="${v.ctgr eq 'EDC' }">[교육]</c:when>
											</c:choose>
										</strong>
									 &nbsp;<c:out value="${v.title }"/>
										</div>
										<ul class="e_dt">
										<c:if test="${not empty v.dateinfo }">
											<li>
												<dl>
													<dt>기간</dt>
													<dd><c:out value="${v.dateinfo }"/></dd>
												</dl>
											</li>
										</c:if>
										<c:if test="${not empty v.timeinfo }">
											<li>
												<dl>
													<dt>시간</dt>
													<dd><c:out value="${v.timeinfo }"/></dd>
												</dl>
											</li>
										</c:if>
										<c:if test="${not empty v.feetype }">
											<li>
												<dl>
													<dt>관람비용</dt>
													<dd>
													<c:choose>
													<c:when test="${v.feetype == '1001' }">
														<strong>유료</strong>
													</c:when>
													<c:when test="${v.feetype == '2001' }">
														<strong>무료</strong>
													</c:when>
													</c:choose>
													</dd>
												</dl>
											</li>
										</c:if>
										</ul>
									</div>
									</a>
									</c:when>
									<c:when test="${searchVO.searchType eq 'board' }">
										<a href="javascript:fn_send_page('<c:out value="${v.ctgr }"/>','<c:out value="${v.idx }"/>', '<c:out value="${v.partcd }"/>', '<c:out value="${ v.title}"/>','<c:out value="${ v.prnno}"/>')">
											<div class="e_cont">
												<div class="e_tit">
													<c:out value="${v.title }"/>
												</div>
												<div class="e_desc">
													<c:out value="${v.cn }"/>
												</div>
												<div class="e_date">${v.regdate }</div>
											</div>
										</a>
									</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
		
		<!-- //m_table -->
		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./mainSearch?pageIndex="/>
		</div>
		
		<!-- //paginate -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<script>

	function fnSearchReset(thisObj) {
		
    	if ("${searchVO.searchType}" == "rsv") {
    		
    	} else if ("${searchVO.searchType}" == "board") {
    		
    	}		
		
		console.log("call fnSearchReset()");
		
		top.location.href="/web/mainSearch?searchType=${searchVO.searchType}&title=";
		
	}
	
	function fnSearchSubmit(thisObj) {
		console.log("call fnSearchSubmit()");
		
    	$('#searchType').val("${searchVO.searchType}");
    	$('#mainSearchForm').submit();		
	}	
	
	$("#searchTitle").keydown(function(e) {
		
		console.log("call searchTitle");
    	console.log(e.keyCode);
    	
    	/* 
    	if (e.keycode == 13) {
    		
    		alert("--11");
    	
    		e.preventDefault();	
    		
    		return false;
    		
    	} else if (e.keycode == 32) {
    		
    		e.preventDefault();
    		
    	} else {

    	}	
    	*/
    	
	});
	
    $("#btnDelete").keydown(function(e) {
    	
    	console.log(e.keyCode);
    	
    	if (e.keycode == 32) {
    		
    		top.location.href="/web/mainSearch?searchType=${searchVO.searchType}&title=";
    		
    		return false;
    	}
        
    });
    
    $("#btnSearch").keydown(function(e) {
        console.log(e.keyCode);
        
    	if (e.keycode == 32) {
    		    		
        	$('#searchType').val("${searchVO.searchType}");
        	$('#mainSearchForm').submit();
        	
    		return false;
    	}       
    });    
	
    $("#btnChangeTypeRsv").keydown(function(e) {
        console.log(e.keyCode);
        
    	if (e.keycode == 32) {
    		return false;
    	}        
    }); 
    
    $("#btnChangeTypeBoard").keydown(function(e) {
        console.log(e.keyCode);
        
    	if (e.keycode == 32) {
    		
        	$('#searchType').val("${searchVO.searchType}");
        	$('#mainSearchForm').submit();    		
    		
    		return false;
    	}        
    });     
    
    $("#searchKeySubmit").keydown(function(e) {
        console.log(e.keyCode);
        
    	if (e.keycode == 13) {
    		$('#orderType').val("${searchVO.orderType}");
        	$('#searchType').val("${searchVO.searchType}");
        	$('#mainSearchForm').submit();    		    			
    		return false;
    	}        
    });     
    
    function fnChangeType(type) {

    	console.log("call fnChangeType");
    	console.log(type);
    	
    	if ("${searchVO.searchType}" == "rsv") {
    		
    	} else if ("${searchVO.searchType}" == "board") {
    		
    	}
   	
    	$('#searchType').val(type);
    	$('#mainSearchForm').submit();
    	
    }

//     $(document).on('keydown', function(e) {

//     	console.log(e.keycode);
    	
//     	//return false;
//     	//e.preventDefault();
    	
//     	/* 
//     	if (e.keycode == 13) {
    		
//     		console.log("111");
    		
//     		//return true;
//     	}
//     	*/
    	
//     	/* else if (e.keycode == 23) {
//     		return true;
//     	} else {
//     		return false;	
//     	}
//     	*/
    	
//     });
	
	$('#searchKeySubmit').click(function() {

		var varThisOrderTypeVal = "";
		
		var varThisOrderType1Yn = $("#thisOrderType1").prop('checked');
		var varThisOrderType2Yn = $("#thisOrderType2").prop('checked');
		
		if (varThisOrderType1Yn) {
			varThisOrderTypeVal = 'des';
		} else if (varThisOrderType2Yn) {
			varThisOrderTypeVal = 'asc';
		}
		
		$('#orderType').val(varThisOrderTypeVal);
    	$('#searchType').val("${searchVO.searchType}");
    	$('#mainSearchForm').submit();    		    
    	
	});
	
</script>
