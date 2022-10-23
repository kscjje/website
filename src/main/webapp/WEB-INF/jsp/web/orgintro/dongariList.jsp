<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script>
		$('div.sub_content').hide();
		$(document).ready(function () {
			$('.detail_tab').find('a').removeClass('on');
			$('.detail_tab').find('ul li:nth-child(1)').find('a').attr('href','../dongari');
			$('.detail_tab').find('ul li:nth-child(2)').find('a').addClass('on').attr('href','javascript:');
			$('#tab01').removeClass('on');

			$('#tab02').html($('.dongari-list').html()).addClass('on');

			$(".search-order").on( "change", function() {
				$('#articleForm').find('input[name=sortOrdr]').val($(this).val());
				$('#articleForm').submit();
			});


		});

		function fn_select_ctg(ctgId){
			$('#articleForm').find('input[name=searchCtg]').val(ctgId);
			$('#articleForm').submit();
		}


	</script>
</head>
<body>
	<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}"/>">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
				<div class="sub_top type01">
					<h3><c:out value="${SELECTED_MENU_OBJ.menuNm}" /></h3>
				</div>

				<c:set var="cnts" value="${fn:replace(contentsVO.cnts , '../../resources/' , '../resources/')}" />
				<c:out value="${cnts }" escapeXml="false"/>


				<div class="btn_area">
					<a href="<c:url value="${webDir}/mypage/dongari/list"/>" class="btn_s1_c2 long_txt">학습동아리 개설현황 확인 (바로가기)</a>
					<a href="./write" class="btn_s1_c1 long_txt">학습동아리 개설신청 (바로가기)</a>
				</div>
			</div>

			<form name="articleForm" id="articleForm" action="./list" method="get" >
				<input type="hidden" name="searchCtg" value="<c:out value="${searchVO.searchCtg }"/>"/>
				<input type="hidden" name="sortOrdr" value="<c:out value="${searchVO.sortOrdr }"/>"/>
			</form>


	<div class="dongari-list" style="display:none;">

		<div class="search_con">
			<div class="search_top">
				<ul class="search_tab">
					<li><a href="javascript:fn_select_ctg('')" class="<c:if test="${searchVO.searchCtg eq '' }">on</c:if>">전체</a></li>
	<c:if test="${!empty ctgList}">
		<c:forEach items="${ctgList}" var="item" varStatus="status">
			<c:if test="${item.ctgSort > 0}">
					<li><a href="javascript:fn_select_ctg('<c:out value="${item.ctgId}"/>')"  class="<c:if test="${searchVO.searchCtg eq item.ctgId }">on</c:if>"><c:out value="${item.ctgNm}"/></a></li>
			</c:if>
		</c:forEach>
	</c:if>
				</ul>
				<div class="select">
					<select class="search-order">
						<option value="0">최신 등록순</option>
						<% /* 퍼블은 있는데  SB에 접수 마감순은 없음
						<option value="1" <c:if test="${searchVO.sortOrdr eq '1' }">selected</c:if>>접수마감순</option>
						*/ %>
						<option value="2" <c:if test="${searchVO.sortOrdr eq '2' }">selected</c:if>>동아리명 순</option>
					</select>
				</div>
			</div>
			<!--// search_top  -->
		<c:if test="${fn:length(resultList) == 0}">
				<!--// search_sel  -->
				<div class="" style="padding-top:50px;">
					<table>
						<caption><c:out value="${boardMasterVO.bbsNm }"/> 표. - 등록된 데이타가 없음 안내</caption>

						<tbody>
							<tr>
								<td class="text-center">등록된 학습동아리가 없습니다.</td>
							</tr>
						</tbody>
					</table>
				</div>
		</c:if>
<c:if test="${fn:length(resultList) > 0}">
			<ul class="search_list invent">
			<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
				<li class="li">
					<span class="ask color0<c:out value="${resultInfo.ctgSort}"/>">
					<c:choose>
						<c:when test="${resultInfo.ctgNm eq '모집종료'}">
							모집<br>종료
						</c:when>
						<c:otherwise>
							<c:out value="${resultInfo.ctgNm}"/>
						</c:otherwise>
					</c:choose>
					</span>
					<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>" class="img_box">
						<img src="<c:url value="${webDir}"/>/common/file/download?atchFileId=<c:out value="${resultInfo.atchFileId}"/>&fileSn=<c:out value="${resultInfo.atchImg}"/>" alt="학습동아리 <c:out value="${resultInfo.nttSj}"/> 이미지 "  onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img02.jpg'"/>
					</a>
					<div class="txt_box">
						<div class="top_box">
							<p class="top_tit"><a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>"><c:out value='${resultInfo.nttSj}'/></a></p>

							<dl>
								<dt class="place">지역</dt>
								<dd>
									<c:if test="${!empty areaList}">
										<c:forEach items="${areaList}" var="item" varStatus="status">
											<c:if test="${item.areaCd eq resultInfo.item6}">
												<c:out value="${item.areaName }"/>
											</c:if>
										</c:forEach>
									</c:if>
								</dd>
							</dl>
							<dl>
								<dt class="teacher">조건</dt>
								<dd><c:out value='${resultInfo.item9}'/></dd>
							</dl>
							<dl>
								<dt class="time">시간</dt>
								<dd><c:out value='${resultInfo.item8}'/></dd>
							</dl>
							<dl>
								<dt class="price">주기</dt>
								<dd><c:out value='${resultInfo.item7}'/></dd>
							</dl>
						</div>
					</div>
				</li>
			</c:forEach>
			</ul>
</c:if>

					<div class="paginate">
						<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
				<!--// search_list  -->
		</div>
	</div>
</body>