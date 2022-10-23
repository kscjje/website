<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : evtrsvnList.jsp
  * @Description : 강연/행사/영화 목록
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.17    전영석                  최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.17
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
					<h3 class="tit">강연/행사/영화 예약</h3>
					<p class="stit">빠른 카테고리 찾기</p>
					<div class="cate_wrap">
						<div class="cate_list">
							<div>
								<span class="on">대분류</span>
								<ul class="cate_tab active">
								
									<li <c:if test="${evtGbn eq 'ALL'}">class="on"</c:if>><a href="./evtrsvnList" class="btn_ty_m5_c2">전체보기</a></li>
									<li <c:if test="${evtGbn eq '1001'}">class="on"</c:if>><a href="./evtrsvnList?evtGbn=1001" class="btn_ty_m5_c2">강연</a></li>
									<li <c:if test="${evtGbn eq '1002'}">class="on"</c:if>><a href="./evtrsvnList?evtGbn=1002" class="btn_ty_m5_c2">행사</a></li>
									<li <c:if test="${evtGbn eq '1003'}">class="on"</c:if>><a href="./evtrsvnList?evtGbn=1003" class="btn_ty_m5_c2">영화</a></li>
									
								</ul>
							</div>
						</div>
						<!-- //cate_list -->
					</div>
					<!-- //care_wrap -->
					
					<div class="search_wrap">
					
						<form:form commandName="eventProgramVO" id="searchForm" name="searchForm" action="./evtrsvnList?evtGbn=<c:out value='${evtGbn}'/>" method="get">
						
							<input type="hidden" id="comPrnctgcd" name="comPrnctgcd" value="<c:out value='${fCtg}'/>"/>
							<input type="hidden" id="comCtgcd"    name="comCtgcd"    value="<c:out value='${sCtg}'/>"/>
							<input type="hidden" id="comCtgLvl"   name="comCtgLvl"   value=""/>
							<input type="hidden" id="pageIndex"   name="pageIndex" />
							<input type="hidden" id="scrollVar"   name="scrollVar" />
							<input type="hidden"                  name="evtGbn" value="<c:out value='${evtGbn}'/>" />
							<input type="hidden"                  name="${_csrf.parameterName}" value="${_csrf.token}" />
							
							<fieldset>
								<legend>게시판 검색</legend>
								<span class="total"><strong>전체 목록</strong>총 <em id="lsize">0</em> 건의 게시물</span>
								<div class="search_box">
									<label for="orderField">정렬순서</label>
									<select id="orderField" class="ml0" name="orderField" title="목록 순서를 선택하세요" onchange="fn_order_select(this.value)">
										<option value="" <c:if test='${empty odr}'>selected</c:if> >정렬순서 선택 </option>
										<option value='default' <c:if test="${odr eq 'default'}">selected</c:if> >강연/행사/영화 시작일</option>
										<option value='regdate' <c:if test="${odr eq 'regdate'}">selected</c:if> >최신 등록일</option>
										<option value='reqStime' <c:if test="${odr eq 'reqStime'}">selected</c:if> >강연/행사/영화신청 시작일</option>
									</select>
									<label for="srchItem">검색어입력 </label>
									<input name="evtName" title="검색어를 입력하세요" id="srchItem" type="text" class="input_text" placeholder="검색어를 입력해주세요." value="<c:out value="${sKeyword}"/>">
									<button type="button" class="button" onclick="return fn_check_frm(this)"><span>검색</span></button>
								</div>
								<div id="paramString">

								</div>
							</fieldset>
						</form:form>					
					
					</div>
					<!-- //search_wrap -->
					
					<div id="list_box">
					</div>

				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
<script type="text/javascript">

	$(document).ready(function() {
		fn_set_param();

	});

	function fn_drt(eventNo) {
		var _scrollTop = window.scrollY || document.documentElement.scrollTop;
		$('#scrollVar').val(_scrollTop);

		location.hash = $('#searchForm').serialize();
		location.href = './evtrsvnDetail?evtNo='+eventNo;
	}

	var ctg1 = '';
	var ctg2 = '';


	function fn_set_param() {
		
		var hsh = location.hash;
		if (hsh != '') {
			
			var obj = fn_qs_json(location.hash);
			var idx = 1;
			
			$.each(obj, function(key, value) {
				
				if (key == 'pageIndex') {
					idx = value;
				}

				$('#'+key).val(value);
				$('.first_ctg').each(function() {
				if ($(this).data('ctgcd') == value) {
					$(this).addClass('on').attr("title" , "선택됨");
					$('#f_all').removeClass('on').removeAttr("title");
				}
				
			}) // first_ctg
			  
			$('.second_ctg').each(function() {
					if ($(this).data('prntcd') == value) {
						$(this).show();
					}
			});
				
			 $('.second_ctg').each(function() {
					if ($(this).data('ctgcd') == value) {
						$(this).addClass('on').attr("title" , "선택됨");
						$('#s_all').removeClass('on').removeAttr("title");
					}
				})//second_ctg
			})

			fn_get_list(idx,'1' , $('#scrollVar').val());
			
		} else {
			fn_set_ctg();
		}
	}

	function fn_ctg_dspy(clsId, dataId,obj) {
		$('.'+clsId).each(function() {
			if ($(this).data(dataId) == obj) {
				$(this).show();
			} else {
				$(this).removeClass('on').removeAttr("title");
				$(this).css('display','none');
			}
		})
	}

	function fn_set_ctg(obj, type) {
		switch (type) {
		case '1':
			$('#comPrnctgcd').val(obj);
			ctg1 = obj;
			$('#comCtgLvl').val('0')
			if ($('#comCtgcd').val() != '') {
				$('#comCtgcd').val('');
			}

			$('.first_ctg').each(function() {
				if ($(this).data('ctgcd') == obj) {
					$(this).addClass('on').attr("title" , "선택됨");
					$('#f_all').removeClass('on').removeAttr("title");
					$('#s_all').addClass('on').attr("title" , "선택됨");
					$('#t_all').addClass('on').attr("title" , "선택됨");
					$('#comCtgcd').val('');
				} else {
					$(this).removeClass('on').removeAttr("title");
				}
			})
			fn_ctg_dspy('second_ctg','prntcd',obj);
			fn_ctg_dspy('third_ctg','prntcd',obj);

			if (obj == 'all') {
				$('#f_all').addClass('on').attr("title" , "선택됨");
				$('#s_all').addClass('on').attr("title" , "선택됨");
				$('#comPrnctgcd').val('');
				ctg1 = '';
			}
			break;
		 case '2':
			if ($('#comPrnctgcd').val() == '') {
				 alert('대분류를 선택해주십시오');
				 return ;
			 }

			 $('.second_ctg').each(function() {
				if ($(this).data('ctgcd') == obj) {
					$(this).addClass('on')
					$('#s_all').removeClass('on').removeAttr("title");
				} else {
					$(this).removeClass('on').removeAttr("title");
				}
			})

			fn_ctg_dspy('third_ctg','prntcd',obj);

			$('#t_all').addClass('on').attr("title" , "선택됨");
			if (obj == 'all') {
				$('#s_all').addClass('on').attr("title" , "선택됨");
				$('#comCtgcd').val('');
				ctg2 = '';
				$('#comCtgLvl').val('0');
			} else {
				 $('#comCtgcd').val(obj);
				 ctg2 = obj;
				 $('#comCtgLvl').val('1');
			}
			break;
		 case '3':
			 	var scndVal = $('#comCtgcd').val();
				if ($('#comCtgcd').val() == '') {
					 alert('중분류를 선택해주십시오')
					 return ;
				 }

				 $('.third_ctg').each(function() {
					if ($(this).data('ctgcd') == obj) {
						$(this).addClass('on').attr("title" , "선택됨");
						$('#t_all').removeClass('on').removeAttr("title");
					} else {
						$(this).removeClass('on').removeAttr("title");
					}
				})

				if (obj == 'all') {
					$('#t_all').addClass('on').attr("title" , "선택됨");
					 $('#comPrnctgcd').val(ctg1);
					$('#comCtgcd').val(ctg2);
					$('#comCtgLvl').val('1');
				} else {
					 $('#comPrnctgcd').val(scndVal);
					 $('#comCtgcd').val(obj);
					 $('#comCtgLvl').val('2');
				}
				break;
		default:
			break;
		}

		fn_get_list(1,'1');
	}

	 function fn_check_frm(frm) {
		 var kword = $('#srchItem').val().trim()
		/*  if (kword == '') {
			 alert('검색어를 입력해주세요')
		 } else { */
			 $('#srchItem').val(kword)
			 $('#searchForm').submit();
		 /* } */

	 }

	function fn_order_select(obj) {
		fn_get_list(1,'1');
	}

	function fn_get_list(idx ,type , scrollVar) {
		
		////$('body').showLoading();
		
		var url = './evtrsvnListAjax';
		var pIdx = idx > 0 ? idx : 1;
		$('#pageIndex').val(pIdx);

		$.ajax({
			url : url,
			data : $('#searchForm').serialize(),
			method : 'GET',
			cache : false,
			dataType : 'html',
			success : function(data, textStatus, jqXHR) {
				$('#list_box').html(data);
				////$('body').hideLoading();
				if (type == '' || type == undefined) {
					var ofset= $('.list_middle').offset().top-30
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