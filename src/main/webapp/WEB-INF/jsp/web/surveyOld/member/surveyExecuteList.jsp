<%@ page contentType="text/html; charset=utf-8"%>

<%
	 /**
	  * @Class Name : edcarsvnindList.jsp
	  * @Description : 교육 예약 개인 목록 리스트
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

<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="tags"      tagdir="/WEB-INF/tags" %> 

	<c:forEach items="${edcarsvnindList}" var="item">
		${item.edcPrgmnm}<br/> 
	</c:forEach>
	
	<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit">교육 프로그램 예약(개인)</h3>
			<div class="cate_wrap">
				<div class="cate_head">
					<dl>
						<dt>빠르게<br> 카테고리 찾기</dt>
						<dd>원하는 강연/행사/영화을 빠르게!</dd>
					</dl>
				</div>
				<!-- //cate_head -->
				<div class="cate_list">
					<div>
						<span >대분류</span>
						<ul class="cate_tab active">
						<li <c:if test="${empty fCtg}">class="on"</c:if>><a href="javascript:fn_set_ctg('','1')" class="btn_ty_m5_c2">전체보기</a></li>
						<c:forEach items="${cList }" var="f" varStatus="st">
							<li <c:if test="${f.comCtgcd eq fCtg }">class="on"</c:if>><a href="javascript:fn_set_ctg('${f.comCtgcd }', '1')" class="btn_ty_m5_c2"><c:out value="${f.comCtgnm }" /></a></li><!-- 현재위치 class: on -->
						</c:forEach>
						</ul>
					</div>
					<div>								
						<span>소분류</span>
						<ul class="cate_tab active">
							<li <c:if test="${empty sCtg}">class="on"</c:if>><a href="javascript:fn_set_ctg('','1')" class="btn_ty_m5_c2">전체보기</a></li>
							<c:forEach items="${sList }" var="s" varStatus="st">
								<li <c:if test="${s.comCtgcd eq sCtg }">class="on"</c:if>><a href="javascript:fn_set_ctg('${s.comCtgcd }','2')" class="btn_ty_m5_c2"><c:out value="${s.comCtgnm }" /></a></li><!-- 현재위치 class: on -->
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- //cate_list -->
			</div>
			<!-- //care_wrap -->
			
			<div class="search_wrap">
				<form:form commandName="eventProgramVO" id="searchForm" action="./evtrsvnList" method="get">
					<input type="hidden" id="comPrnctgcd" name="comPrnctgcd" value="${fCtg }"/>
					<input type="hidden" id="comCtgcd" name="comCtgcd" value="${sCtg }"/>								
					<fieldset>
						<legend>게시판 검색</legend>
						<span class="total"><strong>전체 목록</strong>총 <em><c:out value="${listSize }"></c:out></em> 건의 게시물</span>
						<div class="search_box">
							<label for="ss01">목록순서</label>
							<select id="odr" class="ml0" name="orderField" title="목록 순서를 선택하세요" onchange="fn_order_select(this.value)">
								<option value="" <c:if test='${empty odr }'>selected</c:if> >전체 </option>
								<option value='regdate' <c:if test="${odr eq 'regdate' }">selected</c:if> >최신 등록순 </option>
								<option value='reqStime' <c:if test="${odr eq 'reqStime' }">selected</c:if> >강연/행사/영화신청 임박순</option>
							</select>																				
							<label for="ss02">검색어입력 </label>
							<input id="ss02" name="evtName" title="검색어를 입력하세요" id="srchItem" type="text" class="input_text" placeholder="검색어를 입력해주세요.">
							<button type="submit" class="button"><span>검색</span></button>
						</div>
					</fieldset>
				</form:form>
			</div>
			<!-- //search_wrap -->
			
			<ul class="event_list">
				<c:forEach items="${rList }" var="v" varStatus="st"> 
					<li>
						<a href='<c:url value="./evtrsvnDetail?evtNo=${ v.evtNo }"/>'>
							<div class="e_img"><img src="<c:url value="/web/common/file/view?atchFileId=${v.evtThmbImgFinnb }&fileSn=0"/>" alt="이벤트이미지"></div>
							<!-- //e_img -->
							<div class="e_cont">
								<div class="e_st">
									<span class="st s2">${v.comPrnctgcdnm }</span>
									<span class="st s3">${v.comCtgnm }</span>
								</div>
								<div class="e_tit"><c:out value="${v.evtName }"/></div>
								<ul class="e_dt">
									<li>
										<dl>
											<dt>기간</dt>
											<fmt:parseDate var="dateStr1" value="${v.evtUseSdate }" pattern="yyyyMMdd" />
											<fmt:parseDate var="dateStr2" value="${v.evtUseEdate }" pattern="yyyyMMdd" />																									
											<dd><fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" />~ <fmt:formatDate value="${dateStr2}" pattern="yyyy.MM.dd" /></dd>														
										</dl>
									</li>
									<li>
										<dl>
											<dt>시간</dt>
											<dd><c:out value="${v.evtTimeinfo }"/></dd>
										</dl>
									</li>
									<li>
										<dl>
											<dt>추천</dt>
											<dd><c:out value="${v.evtTargetage }"/></dd>
										</dl>
									</li>
								</ul>
								<div class="e_price">
									<span class="p_tit">관람비용</span>
									<c:choose>
										<c:when test="${v.evtFeeType == '2001'}">
											<span class="p_price"><em> ${v.evtFeeTypenm }</em></span>
										</c:when>
										<c:otherwise>
											<span class="p_price"><em>${v.evtFeeTypenm }</em> 원</span>
										</c:otherwise>
									</c:choose>												
								</div>
							</div>
							<!-- //e_cont -->
						</a>
					</li>	
				</c:forEach>
			</ul>
			<!-- //event_list -->
			
			<div class="paginate">
				<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./evtrsvnList?pageIndex="/>
			</div>
			<!-- //paginate -->
		</div>
		<!-- //inner -->
	</div>
	<!-- //sub_cont -->
				
	<script type="text/javascript">
		function fn_set_ctg(obj, type) {
			var prnCd = '${fCtg}'
			switch (type) {
			case '1':
				$('#comPrnctgcd').val(obj)
				if ($('#comCtgcd').val() != '') {
					$('#comCtgcd').val('')
				}
				break;
			 case '2':			 
				if ($('#comPrnctgcd').val() == '') {
					 alert('대분류를 선택해주십시오')
					 return false;
				 }
				 $('#comCtgcd').val(obj)
				break;
			default:
				break;
			}
	
			$('#searchForm').submit();
		}
		
		
		function fn_order_select(obj) {		
			$('#searchForm').submit();
		}
	</script>
	