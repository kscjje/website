<%
 /**
  * @Class Name : myCouponList.jsp
  * @Description : 쿠폰리스트 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.09    김희택          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.09
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strCouponNum = request.getParameter("couponNum");
	if (strCouponNum == null) strCouponNum = "";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">
	<div class="inner">
	<h3 class="tit">쿠폰함</h3>
	<jsp:useBean id="today" class="java.util.Date" />
	<span style="display:none;"><fmt:formatDate value='${today}' pattern='yyyyMMddHHmm' var="now"/></span>

		<!-- search_wrap-->
		<div class="search_wrap mb15">
				<fieldset>
					<legend>전체목록 건수, 선택</legend>
					<span class="total"><strong>전체 목록</strong>총 <em><c:out value="${listSize }"/></em> 건의 게시물</span>
				</fieldset>
		</div>
		<!-- //search_wrap-->
		<form:form commandName="memberCouponVO" name="memberCouponVO" id="searchForm" action="./myCouponList" method="get">
		<!-- radio_box -->

		<div class="radio_box radio_box2 mb20">
		
			<!-- <label><input type="radio" class="radio" name="searchKey" value="all"   tabindex="0" onKeyDown="fnCheckEnter(this.form, this)" onclick="this.form.submit()" <c:if test="${sKey eq 'all' || empty sKey }">checked</c:if>><em></em><span>전체</span></label> -->
			
			<!-- JYS -->
			<!-- 웹접근성 위배하여 막음 2021.03.02
			<label><input type="radio" class="radio" name="searchKey" value="all"   onclick="this.form.submit(); return false;" <c:if test="${sKey eq 'all' || empty sKey }">checked</c:if>><em></em><span>전체</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="eddt"  onclick="this.form.submit(); return false;" <c:if test="${sKey eq 'eddt' }">checked</c:if>><em></em><span>사용기한 임박순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="rcnt"  onclick="this.form.submit(); return false;" <c:if test="${sKey eq 'rcnt' }">checked</c:if>><em></em><span>최신등록순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="dscnt" onclick="this.form.submit(); return false;" <c:if test="${sKey eq 'dscnt' }">checked</c:if>><em></em><span>할인율 높은순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="avail" onclick="this.form.submit(); return false;" <c:if test="${sKey eq 'avail' }">checked</c:if>><em></em><span>사용가능한 쿠폰만 보기</span></label>
			-->

			<label><input type="radio" class="radio" name="searchKey" value="all"   <c:if test="${sKey eq 'all' || empty sKey}">checked</c:if>><em></em><span>전체</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="eddt"  <c:if test="${sKey eq 'eddt'}">checked</c:if>><em></em><span>사용기한 임박순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="rcnt"  <c:if test="${sKey eq 'rcnt'}">checked</c:if>><em></em><span>최신등록순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="dscnt" <c:if test="${sKey eq 'dscnt'}">checked</c:if>><em></em><span>할인율 높은순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="avail" <c:if test="${sKey eq 'avail'}">checked</c:if>><em></em><span>사용가능한 쿠폰만 보기</span></label>
			
			<label><input type="button" class="btn_ty_s3_c9" id="searchKeySubmit" value="   검색   "/></label>

			<!-- 
			<label><input type="radio" class="radio" name="searchKey" value="all"   onKeyDown="fnCheckEnter(this.form, this); return false;" <c:if test="${sKey eq 'all' || empty sKey }">checked</c:if>><em></em><span>전체</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="eddt"  onKeyDown="fnCheckEnter(this.form, this); return false;" <c:if test="${sKey eq 'eddt' }">checked</c:if>><em></em><span>사용기한 임박순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="rcnt"  onKeyDown="fnCheckEnter(this.form, this); return false;" <c:if test="${sKey eq 'rcnt' }">checked</c:if>><em></em><span>최신등록순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="dscnt" onKeyDown="fnCheckEnter(this.form, this); return false;" <c:if test="${sKey eq 'dscnt' }">checked</c:if>><em></em><span>할인율 높은순</span></label>
			<label><input type="radio" class="radio" name="searchKey" value="avail" onKeyDown="fnCheckEnter(this.form, this); return false;" <c:if test="${sKey eq 'avail' }">checked</c:if>><em></em><span>사용가능한 쿠폰만 보기</span></label>
			-->
			
			<!-- <label><a href="#" onclick="fn_member_submit(); return false;" class="btn_ty_m_c1">확인</a></label> -->

			<!-- 			
			<div class="search_box">
				<button type="button" class="button"><span>검색</span></button>
			</div>
			-->
				
		</div>
		
		<!-- //radio_box -->
		</form:form>
		<!-- //search_wrap -->
		<div class="m_table m_table2">
			<table>
				<caption>전체쿠폰목록 - 번호, 혜택, 쿠폰명, 쿠폰사용조건, 사용기한, 사용여부</caption>
				<thead>
					<tr>
						<th class="number">번호</th>
						<th class="number">혜택</th>
						<th class="name">쿠폰명</th>
						<th class="cond">쿠폰사용조건</th>
						<th class="expay">사용기한</th>
						<th class="stat">사용여부</th>
					</tr>
				</thead>
				<tbody>
				<!-- 본문 -->
				<c:choose>
					<c:when test="${empty cList }">
					<tr>
						<td colspan="6">등록된 데이터가 없습니다.</td>
					</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${cList }" var="v" varStatus="s">
							<tr>
								<td class="number"><c:out value="${paginationInfo.totalRecordCount - (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage - s.index}"/></td>
								<td class="bene"><c:out value="${v.cpnDcrate }"/>% 할인</td>
								<td class="name"><a href="javascript:fn_get_couponInfo('<c:out value="${v.cpnId}"/>','<c:out value="${v.cpnUid}"/>')" id="modal<c:out value='${v.cpnUid}'/>" title="레이어팝업 열림"><c:out value="${v.cpnName}"/></a></td>
								<td class="cond"><c:out value="${v.cpnGuide}"/></td>
								<td class="expay red">
									<fmt:parseDate var="extYmd" value="${fn:substring(v.endtime,0,8)}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd }" pattern="yyyy.MM.dd"/> <br class="pc"/>&nbsp;
									${fn:substring(v.endtime,8,10) }:${fn:substring(v.endtime,10,12) }
								</td>
								<c:choose>
									<c:when test="${v.cpnUseyn eq 'N' and v.endtime > now }">
										<td class="stat">사용가능</td>
									</c:when>
									<c:when test="${v.cpnUseyn eq 'Y'}">
										<td class="stat useend">사용완료</td>
									</c:when>
									<c:when test="${v.endtime < now }">
										<td class="stat payend">기한만료</td>
									</c:when>
								</c:choose>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
		<!-- //m_table -->
		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./myCouponList?pageIndex="/>
		</div>
		<!-- //paginate -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<!-- 팝업 -->
		<div class="modal" id="modal1">
			<div class="modal-dialog" style="max-width:500px;">
			<div class="modal-content">
				<div class="modal-title">
					<h4>쿠폰 상세 정보</h4>
					<a href="#" class="pop_close" data-dismiss="modal" data-focus-next="">닫기</a>
				</div>
				<div class="m-body">
				</div>
				<!-- //m_txt -->
				<ul class="btn_area long">
					<li><a href="#" class="btn_ty_m3_c4" data-dismiss="modal" data-focus-next="">닫기</a></li>
					<!-- <li><a href="#" class="btn_ty_m3_c1">사용처리 (관리자용)</a></li> -->
				</ul>
				<!-- //btn_area -->
			</div>
			<!-- //modal-content -->
			</div>
		</div>
		<!-- //팝업 -->
<script>

function fn_get_couponInfo(id, uid) {
	var url = './myCouponDetailAjax'
	$.ajax({
		url : url,
		data : {
			cpnId : id,
			cpnUid : uid
		},
		method : 'GET',
		cache: false,
		dataType : 'html',
		error : function(request, error, jqXHR) {
			alert("불러오기 실패");
		},
		success : function(data, textStatus, jqXHR) {
			
			console.log("modal");
			
			$(".pop_close").attr("data-focus-next", "modal" + uid);
			$(".btn_ty_m3_c4").attr("data-focus-next", "modal" + uid);
			
			$('.m-body').html(data)
			$('#modal1').modal();
		}
	});

}

function fn_get_couponInfoMobile(id, uid) {
	var url = './myCouponDetailMobileAjax'
	$.ajax({
		url : url,
		data : {
			cpnId : id,
			cpnUid : uid
		},
		method : 'GET',
		cache: false,
		dataType : 'html',
		error : function(request, error, jqXHR) {
			alert("불러오기 실패");
		},
		success : function(data, textStatus, jqXHR) {
			
			$(".pop_close").attr("data-focus-next", "modal" + uid);
			$(".btn_ty_m3_c4").attr("data-focus-next", "modal" + uid);			
			
			$('.m-body').html(data)
			$('#modal1').modal();
		}
	});

}

var varCouponNum = "<c:out value="<%= strCouponNum %>"/>";

console.log("strCouponNum = ");
console.log(varCouponNum);

if ((varCouponNum == "") || (varCouponNum === undefined) || (varCouponNum == null)) {
} else {
	fn_get_couponInfoMobile(varCouponNum, varCouponNum);
}

function fnAccessibilityFocus() {
	
	$(document).on('keydown', '[data-focus-prev], [data-focus-next]',
	
		function(e) {
		
			console.log("===");
		
			var next = $(e.target).attr('data-focus-next'), 
				prev = $(e.target).attr('data-focus-prev'), 
				target = next || prev || false; 
			
			if (!target || e.keyCode != 13) { 
				return; 
			} 
			
			if ( (!e.shiftKey && !!next) || (e.shiftKey && !!prev) ) { 
				setTimeout(function() { 
					
					//$('[data-focus="' + target + '"]').focus();
					
					$('#' + target).focus();
					
				}, 1); 
			} 
		}); 
}


$(document).ready(function() { 
	fnAccessibilityFocus(); 
});	


$('#searchKeySubmit').click(function() {
	$('#searchForm').submit();
});
	


</script>