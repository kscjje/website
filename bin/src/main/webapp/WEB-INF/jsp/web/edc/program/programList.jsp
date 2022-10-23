<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<script type="text/javascript">
		var DISPLAY_TYPE = 'THUMB';//THUMB 썸네일, MAGAZINE : 매거진, LIST : 리스트
		var CURRENT_DATA = [];
		var pageSize = 10;
		var TotalRecordCount = -1;
		
		
		
		var orgTypeList; // 기관 유형 담는 객체
		var searchOrgNm; // 기관명
		var searchComCtgnm; // 카테고리 명
		var areaList = []; //지역 목록 담는 객체
		$(document).ready(function() {
			$(".searchTab a").click(function() {
				//$("#searchKeyword").attr("placeholder", $(this).attr("alt") + " 입력해주세요.");
				$("#searchTab").val($(this).data("cd"));
				$(".search_sel").hide();
				$(".search_sel." + $(this).data("cd")).show();
			});

			$(".searchOnOff a").click(function() {
				if($(this).data("cd") == undefined){

				}else{
					$("#searchOnOff").val($(this).data("cd"));
					$(".searchOnOff a").removeClass("on");
					$(this).addClass("on");
					fnGetProgramList();
				}

			});

			$("#searchOrderBy").change(function() {
				fnGetProgramList();
			});

			//기관 유형 가져오기
			$.ajax({
			      url: '<c:url value="../../contents/codelist/SM_ORG_MTYPE/list.json"/>',
			      type : "GET",
			      cache : false,
			      async : false,
			      success: function(data) {
			    	  //목록을 담는다
			    	  orgTypeList = data.list;
			      },
			      error : function(e){
			    	 // alert("기관유형 가져오기 오류\n" + e.responseText);
			      }
			});

			$('#orgLtype').change(function(){
				fnPrintOrgtype($(this).val() , '');
			});

			$.each($('select[name=areaCd]').find("option"), function(index,item){
				areaList[index] = item;
			});

			$('#areaName').change(function(){

				fnPrintAreaList($(this).val());
			})

		 	fnSetParams();

		});

		function fnSetParams() {
			var hsh = location.hash;
			if(hsh != ''){
				var obj = fn_qs_json(location.hash);

				var idx = 1;
				var orgMtype = '';

				$.each(obj, function(key, value){
					switch (key) {
						case 'pageIndex' :
							idx = value;
							break;
						case 'searchTab' :
							jQuery(".searchTab a[data-cd='"+value+"']").click();
							break;
						case 'searchOnOff':
							$("#searchOnOff").val(value);
							$(".searchOnOff a").removeClass("on");
							jQuery(".searchOnOff a[data-cd='"+value+"']").addClass("on");
							break;
						case 'orgMtype' :
							$('#'+key).val(value);
							orgMtype = value;
							break;
						case 'comCtgnm':
							searchComCtgnm = value;
							break;
						case 'orgName':
							searchOrgNm = value;
							break;
						case 'areaName' :
							$('#'+key).val(value);
							fnPrintAreaList(value);
							break;
						default :
							$('#'+key).val(value);
					}
				  });//end of each

				  if($("#searchTab").val() == "ORGAN"){
					  fnPrintOrgtype( $('#orgLtype').val() , orgMtype);
				  }

				  if(searchComCtgnm){
				 		$.each($('#comCtgcd').find("option") , function(index,item){
				 			if($(this).text() == searchComCtgnm){
				 				$('#comCtgcd').val($(this).val());
				 			}
				 		})
				  }

				  fnSetPage(idx,'1' , $('#scrollVar').val())	;
			}else{
				fnSetPage(1 , '1' );
			}
		}

		function fnSetPage(page , type , scrollVar) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);

			fnGetProgramList(type,scrollVar);
		}

		function fnPrintAreaList(areaNm){
			$('select[name=areaCd]').html("");
			$('select[name=areaCd]').append("<option value='0'>세부지역(전체)</option>");

			$.each(areaList , function(index,item){
				if($(item).prop("value") == "0"){

				}else if(areaNm == "" || $(item).text().indexOf(areaNm)==0){
					$('select[name=areaCd]').append("<option value='"+$(item).prop("value")+"'>"+$(item).text()+"</option>");
				}

			});
		}

		function fnGetProgramList(type , scrollVar) {
			if ($("#searchTab").val() == "ORGAN") {
				/*
				if (!$("#orgLtype").val() && $("#orgMtype").val()) {
					alert("기관유형1을 선택해 주세요.");
					$("#orgLtype").focus();
					return;
				}
				*/
			} else if ($("#searchTab").val() == "TIME") {
				if (!$("#edcStime").val() && $("#edcEtime").val()) {
					alert("시작시간대를 선택해 주세요.");
					$("#edcStime").focus();
					return;
				}
				if ($("#edcStime").val() && !$("#edcEtime").val()) {
					alert("종료시간대를 선택해 주세요.");
					$("#edcEtime").focus();
					return;
				}
				if ($("#edcStime").val() > $("#edcEtime").val()) {
					alert("종료시간대는 시작시간대보다 커야합니다.");
					$("#edcStime").focus();
					return;
				}
			}

			
			$.ajax({
				//url : '${ajaxUrl}',
				url : './list2Ajax',
				data : $('#searchVO').serialize(),
				type : "GET",
				dataType : 'json',
				cache : false,				
				success : function(data, textStatus, jqXHR) {
		    		const obj = JSON.parse(data);
		    		if(obj != undefined &&  obj != null && obj.result == true){
		    			if(obj.data.pagination.TotalRecordCount != undefined && obj.data.pagination.TotalRecordCount >0){
		    				TotalRecordCount = obj.data.pagination.TotalRecordCount;
		    				$('#totalRecordCount').html(TotalRecordCount);
		    				$('.pagelTab-num').html(TotalRecordCount);
		    			}
		    			else{
		    				TotalRecordCount = 0;
		    				$('#totalRecordCount').html(0);
		    				$('.pagelTab-num').html(0);
		    			}
		    			
		    			CURRENT_DATA = obj.data;
		    			drawDisplay();
		    			drawPaging();
		    		}
				},
				error : function(request, error, jqXHR) {
					console.log("목록조회 실패 " + error);
				}
			});
		}
		
/*
			$.ajax({
				url : '${ajaxUrl}',
				data : $('#searchVO').serialize(),
				dataType : 'html',
				success : function(data, textStatus, jqXHR) {
					debugger;
					$('#resultList').html(data);

					if(type == '' || type == undefined){
						//alert(1);
						var ofset= $('.search_txt').offset().top-100;
						$('html').animate({scrollTop : ofset},100);
					}else if(scrollVar){
						//alert(2);
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
					console.log("목록조회 실패 " + error);
				}
			});
		}
*/

		function drawDisplay(){
			var itemsArray = [];

			/*노출페이지 정보 초기화*/
			$('#displayThumbList').html('');	
			$('#displayMagazineList').html('');	
			$('#displayList').html('');				
			
			if(DISPLAY_TYPE == 'THUMB'){
				var items = CURRENT_DATA.contents;
				for(var i=0; i<items.length; i++){
					var item = items[i];
					var filePath = '/web/resources/images/common/no_img.png';
					if(item.edc_img_filenm != undefined && item.edc_img_filenm != null && item.edc_img_filenm.length>0){
						filePath = 'http://211.188.68.145/web/common/file/view/' + item.edc_img_path + item.edc_img_filenm + '?originName='+item.edc_img_origin;
					}
					
					itemsArray.push(`
							   <li class="thumb-item">
							      <a href="javascript:fnGoDetail(`+item.edc_prgm_no+`)" class="thumb-link">
								<figure class="thumb-figure">
								  <img src="` + filePath + `" alt="`+item.edc_prgm_nm+`">
								  <figcaption class="thumb-ask blue position-absolute">`+item.edc_status+`</figcaption>
								</figure>
								<div class="thumb-content">
								  <ul class="thumb-hash hash">
								    <li>`+item.target_name+`</li>
								    <li>`+item.area_nm+`</li>
								    <li>`+item.ctg_nm+`</li>
								  </ul>
								  <h4 class="thumb-title truncate-2">`+item.edc_prgm_nm+`
								  </h4>
								  <ul class="thumb-desc">
								    <li>
								      <img src="../../resources/images/sub/ico_article_04.png" alt="기관">기관 :
								      <p>`+item.org_name+`</p>
								    </li>
								    <li>
								      <img src="../../resources/images/sub/ico_article_05.png" alt="일정">일정 :
								      <p>`+getDateInfo(item.edc_rsvn_sdate,'.')+`~`+getDateInfo(item.edc_rsvn_edate,'.')+`</p>
								    </li>
								    <li>
								      <img src="../../resources/images/sub/ico_article_06.png" alt="비용">비용 :
								      <p>`+item.sale_amt.toLocaleString()+`원</p>
								    </li>
								  </ul>
								  <p class="thumb-view">
								    <img src="../../resources/images/sub/ico_article_07.png" alt="조회수">0
								  </p>
								</div>
							      </a>
							    </li>
							`);
					
					$('#displayThumbList').html(itemsArray.join(''));
				}
			}
			else if(DISPLAY_TYPE == 'MAGAZINE'){
				var items = CURRENT_DATA.contents;
				for(var i=0; i<items.length; i++){
					var item = items[i];
					var filePath = '/web/resources/images/common/no_img.png';
					if(item.edc_img_filenm != undefined && item.edc_img_filenm != null && item.edc_img_filenm.length>0){
						filePath = 'http://211.188.68.230/web/common/file/view/' + item.edc_img_path + item.edc_img_filenm + '?originName='+item.edc_img_origin;
					}
					
					itemsArray.push(`
							<li class="magazine-item">
							<a href="javascript:fnGoDetail(`+item.edc_prgm_no+`)" class="magazine-link">
			                  <figure class="magazine-figure">
			                  	<img src="` + filePath + `" alt="`+item.edc_prgm_nm+`">
			                    <figcaption class="magazine-ask blue position-absolute">`+item.edc_status+`</figcaption>
			                  </figure>
			                  <div class="magazine-content">
			                    <ul class="magazine-hash hash">
			                      <li>`+item.target_name+`</li>
			                      <li>`+item.area_nm+`</li>
			                      <li>`+item.ctg_nm+`</li>
			                    </ul>
			                    <h4 class="magazine-title truncate-1">`+item.edc_prgm_nm+`</h4>
			                    <p class="magazine-txt truncate-2">레그익스텐션, 버티컬플라이, 라잉레그컬, 하이랫 플 등 보유 ! 레그익스텐션, 버티컬플라이, 라잉레그컬, 하이랫 플 등 보유 !</p>
			                    <ul class="magazine-desc">
			                      <li>
			                        <img src="../../resources/images/sub/ico_article_04.png" alt="아이콘">기관 :&nbsp; 
			                        <p> `+item.org_name+`</p>
			                      </li>
			                      <li>
			                        <img src="../../resources/images/sub/ico_article_05.png" alt="아이콘">일정 :&nbsp; 
			                        <p> `+getDateInfo(item.edc_rsvn_sdate,'.')+`~`+getDateInfo(item.edc_rsvn_edate,'.')+`</p>
			                      </li>
			                      <li>
			                        <img src="../../resources/images/sub/ico_article_06.png" alt="아이콘">비용 :&nbsp; 
			                        <p> `+item.sale_amt.toLocaleString()+`원</p>
			                      </li>
			                    </ul>
			                    <p class="magazine-view">
			                      <img src="../../resources/images/sub/ico_article_07.png" alt="아이콘">조회 :&nbsp; 
			                      <span>2136</span>
			                    </p>
			                  </div>
			                </a>
			              </li>
						`);    					
				}
				$('#displayMagazineList').html(itemsArray.join(''));

			}
			else if(DISPLAY_TYPE == 'LIST'){
				var items = CURRENT_DATA.contents;
				
				itemsArray.push(`
						<table class="list-table table">
						<colgroup>
							<col style="width: 5%">
							<col style="width: 20%">
							<col style="width: 10%">
							<col style="width: 10%">
							<col style="width: 15%">
							<col style="width: 10%">
							<col style="width: 20%">
							<col style="width: 10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" class="number">번호</th>
								<th scope="col" class="title">강좌명</th>
								<th scope="col" class="member">인원/정원</th>
								<th scope="col" class="fee">수강료</th>
								<th scope="col" class="area">교육기관</th>
								<th scope="col" class="prace">교육장소</th>
								<th scope="col" class="date">교육기간</th>
								<th scope="col" class="state">상태</th>
							</tr>
						</thead>
						<tbody>
				`);
				
				for(var i=0; i<items.length; i++){
					var item = items[i];
					var filePath = '/web/resources/images/common/no_img.png';
					if(item.edc_img_filenm != undefined && item.edc_img_filenm != null && item.edc_img_filenm.length>0){
						filePath = 'http://211.188.68.230/web/common/file/view/' + item.edc_img_path + item.edc_img_filenm + '?originName='+item.edc_img_origin;
					}
					
					itemsArray.push(`
							<tr>
							<td class="number">1</td>
							<td class="title"><a href="javascript:fnGoDetail(`+item.edc_prgm_no+`)" class="truncate-1">`+item.edc_prgm_nm+`</a></td>
							<td class="member">0/15</td>
							<td class="fee">`+item.sale_amt.toLocaleString()+`원</td>
							<td class="area">`+item.org_name+`</td>
							<td class="prace">`+item.edc_place_nm+`</td>
							<td class="date">`+getDateInfo(item.edc_rsvn_sdate,'.')+`~`+getDateInfo(item.edc_rsvn_edate,'.')+` <br>`+item.edc_day_gbn_nm+` /
							`+item.edc_stime+` ~ `+item.edc_etime+`
							</td>
							<td class="state">
								<p class="gray">`+item.edc_status+`</p>
							</td>
						</tr>
						`);    					
				}
				//색상값
				//접수전 :blue, 접수중 :red, 접수중:green, 접수마감:gray
				itemsArray.push(`
						</tbody>
					</table>						
				`);
				
				$('#displayList').html(itemsArray.join(''));				
			}
			
		}
		//페이징처리 함수
		function drawPaging(){
			var page = $('#pageIndex').val();
			$('#displayPageing').html('');
			if(TotalRecordCount > 0){
				var dividePageSize	= 10;
				var totalPage		= parseInt(TotalRecordCount / pageSize) + (TotalRecordCount % pageSize > 0 ? 1 : 0);
				var pageStart		= parseInt((page - 1) / dividePageSize, 10) * dividePageSize + 1;
				var pageEnd			= totalPage <= pageStart + dividePageSize - 1 ? totalPage : pageStart + dividePageSize - 1;
				var PAGE_HTML		= '';

				PAGE_HTML +='<div class="paginate pc">';
				if(page > 1) PAGE_HTML += '<a href="javascript:goPage(1)" class="prev02">처음</a>';
				if(page > dividePageSize) PAGE_HTML += '<a href="javascript:goPage('+ (pageStart - 1) +')" class="prev">이전</a>';
				
				for(var i=pageStart; i<=pageEnd; i++){
					if(i == page){
						PAGE_HTML += '<a href="#" class="on" title="선택">'+ i +'</a>';
					}
					else{
						PAGE_HTML += '<a href="javascript:goPage('+ i +')">'+ i +'</a>';
					}
				}
				if(pageEnd + 1 <= totalPage) PAGE_HTML += '<a href="javascript:goPage('+ (pageEnd + 1) +')" class="next">다음</a>';
				if(page < totalPage) PAGE_HTML += '<a href="javascript:goPage('+ totalPage +')" class="next02>마지막</a>';
				PAGE_HTML +='</div>';
			}
			$('#displayPageing').html(PAGE_HTML);
		}
		
		function goPage(val){
			$('#pageIndex').val(val);
			fnGetProgramList();
		}
		//약관동의로이동
		function fnApply(edcPrgmid, edcRsvnsetSeq, edcRsvnRectype, edcRsvnLinkurl, rsvnNonmebyn) {
			var userId = $.trim("${userInfo.id}");

			if (edcRsvnRectype == "5001") {//1.타기관링크
				$("#linkForm").attr("action", linkUrl).submit();
				return;
			}

			//2.내부
			var data = new Object();
			data.edcPrgmid = edcPrgmid;
			data.edcRsvnsetSeq = edcRsvnsetSeq;

			if (rsvnNonmebyn == "Y" && userId == "") {
				if(confirm("해당강좌를 비회원으로 신청하시겠습니까?\n(회원가입자 경우, 로그인하여 신청하세요)")){
					location.href = "<c:url value='../termsAgree/${detailVO.edcPrgmid}/${detailVO.edcRsvnsetSeq}'/>";
				}
			} else if (userId == "") {
				if(confirm("해당 강좌의 수강신청을 위해서 로그인 해야 합니다. 로그인하시겠습니까?")){
					location.href = "<c:url value='/web/member/login'/>";
				}
			} else {
				$.ajax({
					url : "<c:url value='./applyCheck.json'/>", //제약조건체크
					data : data,
					success : function(data) {
						if (data.result == "OK") {
							//수강신청페이지로 이동
						} else {
							alert(data.message);
						}
					},
					error : function(error) {
						console.log(error);
					}
				});
			}

			location.href = '<c:url value="../rsvn/termsAgree"/>' + '/' + edcPrgmid + '/' + edcRsvnsetSeq;
		}

		//상세페이지로이동
		function fnGoDetail(edcPrgmid) {
			var _scrollTop = window.scrollY || document.documentElement.scrollTop;
			$('#scrollVar').val(_scrollTop);

			location.hash = $('#searchVO').serialize();
			location.href = '<c:url value="../program"/>' + '/' + edcPrgmid;
		}
		//상세페이지로이동
		function fnMove(url) {
			var _scrollTop = window.scrollY || document.documentElement.scrollTop;
			$('#scrollVar').val(_scrollTop);

			location.hash = $('#searchVO').serialize();
			location.href = url;
		}
		//기관 유형 option 표시
		function fnPrintOrgtype(parentCd , defaultVal){
			$('#orgMtype').html('');

			if(parentCd == "9001"){
				var cnt=0;
				$('#orgMtype').append("<option value=''>전체(기관 유형2)</option>");
				for(var i = 0 ; i<orgTypeList.length ; i++){
					var item = orgTypeList[i];
					if(item.item1 == parentCd){
						var selected = "";
						if(item.cd == defaultVal) selected = " selected";

						$('#orgMtype').append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
						cnt++;
					}
				}
			}else{
				//기관목록
				$('#orgMtype').append("<option value=''>전체(기관명)</option>");
				$.ajax({
				      url: '../../orgintro/orgList/'+parentCd+'.json',
				      type : "GET",
				      cache : false,
				      async : false,
				      success: function(data) {
				    	  for(var i = 0 ; i<data.list.length ; i++){
								var item = data.list[i];
								var selected = "";
								if(item.orgNo == defaultVal){
									selected = " selected";
								}else if(searchOrgNm  == item.comnm){
									selected = " selected";
								}

								$('#orgMtype').append("<option value='"+item.orgNo+"'"+selected+">"+item.comnm+"</option>");
								cnt++;
							}
				      },
				      error : function(e){
				    	 // alert("기관목록 가져오기 오류\n" + e.responseText);
				      }
				});

			}
			if(cnt < 1){
				$('#orgMtype').append("<option value=''>없음</option>");
			}
		}
	</script>

<main class="content" id="content">

	<div class="subVisual">
		<div class="subVisual-inner">
			<figure class="subVisual-figure">
				<img src="../../resources/images/sub/img_subVisual_03.png"
					alt="서브 비주얼">
			</figure>
			<h3
				class="subVisual-title position-absolute start-50 top-50 translate-middle">
				<c:out value="${SELECTED_MENU_OBJ.menuNm}" />
			</h3>
		</div>
	</div>

	<div class="pageTit">
		<div class="pageTit-inner">
			<div class="pageTit-wrap">
				<h3 class="pageTit-title">강좌검색</h3>
				<ul class="pageTit-list">
					<li class="pageTit-item"><a href="javascript:void(0)"
						title="홈 이동" class="pageTit-link"> <img
							src="../../resources/images/sub/ico_snb_home.png" alt="홈">
					</a></li>
					<li class="pageTit-item"><a href="javascript:void(0)"
						title="이동" class="pageTit-link">교육신청</a></li>
					<li class="pageTit-item"><a href="javascript:void(0)"
						title="이동" class="pageTit-link">강좌검색</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="sub">

		<form:form commandName="searchVO" name="searchVO" action="javascript:;" method="get">
			<!-- 분야코드 -->
			<form:hidden path="CtgCd" />
			<form:hidden path="pageIndex" />
			<!-- 강좌명,지역별,,, 
			EDCANM 교육명,강사명 검색(searchKeyword)  
			AREA:지역코드(areaCd),지역명(areaName) 
			TARGET:교육대상(edcTargetinfo)
			CATE : 교육분야(CtgCd)
			ORGAN:기관유형1(orgLtype), 기관유형2(orgMtype)
			TIME:평일:1001, 주말:2001 (dayGbn), hourbandGbn(오전:1001, 오후:2001, 야간:3001 COT_GRPCD->CM_HOURBAND_GBN)
			-->
			<form:hidden path="searchTab" />
			<!-- 온라인, 온라인+방문 -->
			<form:hidden path="searchOnOff" />
			<!-- 임박예약 교육(예약 7일 이내) : Y -->
			<input type="hidden" name="searchHurry" id="searchHurry" />
			<!-- 스크롤 위치 저장 -->
			<input type="hidden" name="scrollVar" id="scrollVar" />
			<!-- 페이지여부 -->
			<input type="hidden" name="usePagingYn" id="usePagingYn" value="Y" />

			<!-- 기관코드 세팅 orgltype값은 세팅하지 않고, orgMtype에 기관코드 세팅-->
			<input type="hidden" name="orgltype" id="orgltype"/>
			<form:hidden path="orgMtype"/>

			<!-- 대상별 코드정보 -->
			<form:hidden path="edcTargetinfo"/>

			<!-- 시간대별 오전:1001, 오후:2001, 야간:3001-->
			<form:hidden path="hourbandGbn"/>
			
			<!-- 시간대별 평일:1001, 주말:2001-->
			<form:hidden path="dayGbn"/>
			

			<!--강좌 정렬순서
				1. 기본은 프로그램 등록순 DESC
				2. BY_RECENT_UP	프로그램 등록순 DESC
				3. BY_APPLY_CLOSE	EDC_STATUS_CLASS ASC 교육상태, 예약종료일 DESC   예약마감
				4. BY_APPLY_CLOSE_DESC	EDC_STATUS_CLASS ASC 교육상태, 예약종료일 ASC   예약마감
				5. BY_PROGRAM_NM_ASC	EDC_PRGM_NM ASC, EDC_PRGM_NO ASC 교육명 ASC 정렬
				6. BY_PROGRAM_NM_DESC	EDC_PRGM_NM DESC, EDC_PRGM_NO DESC 교육명 DESC 정렬
				7. BY_RSVN_TYPE_DESC	RSVN_SORT DESC, EDC_RSVN_RECTYPE DESC
				8. BY_RSVN_TYPE_ASC	RSVN_SORT ASC, EDC_RSVN_RECTYPE ASC
				9. BY_CATE_DESC	CTG_TOPSORT DESC, CTG_LVL DESC, SORT_ORDER DESC
				10. BY_CATE_ASC	CTG_TOPSORT ASC, CTG_LVL ASC , SORT_ORDER ASC
			-->
			<input type="hidden" name="searchOrderBy" id="searchOrderBy"/>

			<div class="smart">
				<div class="smart-inner">
					<div class="smart-wrap">
						<button class="smart-title">스마트 검색</button>
						<div class="smart-category">
							<ul class="smart-list">
								<li class="smart-item">
									<div class="smart-dh">분야</div>
									<div class="smart-dd">
										<div class="input-group">
											<input type="radio" id="inp03_01_01" name="sub03_01" checked>
											<label for="inp03_01_01">전체</label>
										</div>

										<c:forEach items="${cateList}" var="resultInfo"
											varStatus="status">
											<c:forEach items="${resultInfo.subCtgrList}"
												var="resultInfo2" varStatus="status1">
												<div class="input-group">
													<input type="radio"
														id="inp03_<c:out value="${status.index}" />_<c:out value="${status1.index}" />"
														name="sub03_01"> <label
														for="inp03_<c:out value="${status.index}" />_<c:out value="${status1.index}" />">${resultInfo2.ctgNm }</label>
												</div>
											</c:forEach>
										</c:forEach>
									</div>
								</li>

								<li class="smart-item">
									<div class="smart-dh">교육대상</div>
									<div class="smart-dd">
										<div class="input-group">
											<input type="radio" id="inp03_01_09" name="sub03_02" checked>
											<label for="inp03_01_09">전체</label>
										</div>
										<c:forEach items="${targetType}" var="resultInfo"
											varStatus="status">
											<div class="input-group">
												<input type="radio"
													id="inp03_01_<c:out value="${status.index}" />"
													name="sub03_02" value="${resultInfo.cd }"> <label
													for="inp03_01_<c:out value="${status.index}" />">${resultInfo.cdNm }</label>
											</div>
										</c:forEach>

									</div>
								</li>
								<li class="smart-item smart-week">
									<div class="smart-dh">요일선택</div>
									<div class="smart-dd">
										<div class="smart-group">
											<div class="input-group">
												<input type="checkbox" id="inp03_01_15" name="sub03_03"
													checked> <label for="inp03_01_15">월</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_16" name="sub03_03">
												<label for="inp03_01_16">화</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_17" name="sub03_03">
												<label for="inp03_01_17">수</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_18" name="sub03_03">
												<label for="inp03_01_18">목</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_19" name="sub03_03">
												<label for="inp03_01_19">금</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_20" name="sub03_03">
												<label for="inp03_01_20">토</label>
											</div>
											<div class="input-group">
												<input type="checkbox" id="inp03_01_21" name="sub03_03">
												<label for="inp03_01_21">일</label>
											</div>
										</div>
									</div>
								</li>
								<li class="smart-item">
									<div class="smart-dh">신청상태</div>
									<div class="smart-dd">
										<div class="input-group">
											<input type="radio" id="inp03_01_22" name="sub03_04" checked>
											<label for="inp03_01_22">접수전</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_23" name="sub03_04">
											<label for="inp03_01_23">접수중</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_24" name="sub03_04">
											<label for="inp03_01_24">접수마감</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_25" name="sub03_04">
											<label for="inp03_01_25">준비중</label>
										</div>
									</div>
								</li>
								<li class="smart-item">
									<div class="smart-dh">시간별</div>
									<div class="smart-dd">
										<div class="input-group">
											<input type="radio" id="inp03_01_26" name="sub03_05" checked>
											<label for="inp03_01_26">오전</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_27" name="sub03_05">
											<label for="inp03_01_27">오후</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_28" name="sub03_05">
											<label for="inp03_01_28">야간</label>
										</div>
									</div>
								</li>
								<li class="smart-item">
									<div class="smart-dh">수강료</div>
									<div class="smart-dd">
										<div class="input-group">
											<input type="radio" id="inp03_01_29" name="sub03_06" checked>
											<label for="inp03_01_29">무료</label>
										</div>
										<div class="input-group">
											<input type="radio" id="inp03_01_30" name="sub03_06">
											<label for="inp03_01_30">유료</label>
										</div>
									</div>
								</li>
							</ul>
							<div class="smart-btn searchBar-group input-group">
								<button class="btn searchBar-search" type="button" onclick="goSearch();">
									<img src="../../resources/images/layout/icon_search_w.png"
										alt="아이콘">검색
								</button>
								<button class="btn searchBar-reset" type="button" onclick="goReset();">
									<img src="../../resources/images/layout/ico_reset.png"
										alt="아이콘">초기화
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="pagelTab">
				<div class="pagelTab-inner">
					<div class="pagelTab-wrap mt-5">
						<ul class="nav nav-tabs pagelTab-list" role="tablist">
							<li class="nav-item pagelTab-item" role="presentation"><a
								href="#none" onclick="changTab(1)"
								class="nav-link pagelTab-link active" data-bs-toggle="tab"
								role="tab" aria-controls="home" aria-selected="true">
									<figure class="pagelTab-figure">
										<img src="../../resources/images/sub/ico_tab_01.png" alt="">
									</figure>
									<p class="pagelTab-txt">분야별</p> <span class="pagelTab-num"></span>
							</a></li>
							<li class="nav-item pagelTab-item" role="presentation"><a
								href="#none" onclick="changTab(2)"
								class="nav-link pagelTab-link" data-bs-toggle="tab" role="tab"
								aria-controls="profile" aria-selected="false">
									<figure class="pagelTab-figure">
										<img src="../../resources/images/sub/ico_tab_02.png" alt="">
									</figure>
									<p class="pagelTab-txt">기관별</p> <span class="pagelTab-num"></span>
							</a></li>
							<li class="nav-item pagelTab-item" role="presentation"><a
								href="#none" onclick="changTab(3)"
								class="nav-link pagelTab-link" data-bs-toggle="tab" role="tab"
								aria-controls="contact" aria-selected="false">
									<figure class="pagelTab-figure">
										<img src="../../resources/images/sub/ico_tab_03.png" alt="">
									</figure>
									<p class="pagelTab-txt">지역별</p> <span class="pagelTab-num"></span>
							</a></li>
							<li class="nav-item pagelTab-item" role="presentation"><a
								href="none" onclick="changTab(4)" class="nav-link pagelTab-link"
								data-bs-toggle="tab" role="tab" aria-controls="contact"
								aria-selected="false">
									<figure class="pagelTab-figure">
										<img src="../../resources/images/sub/ico_tab_04.png" alt="">
									</figure>
									<p class="pagelTab-txt">대상별</p> <span class="pagelTab-num"></span>
							</a></li>
							<li class="nav-item pagelTab-item" role="presentation"><a
								href="none" onclick="changTab(5)" class="nav-link pagelTab-link"
								data-bs-toggle="tab" role="tab" aria-controls="contact"
								aria-selected="false">
									<figure class="pagelTab-figure">
										<img src="../../resources/images/sub/ico_tab_05.png" alt="">
									</figure>
									<p class="pagelTab-txt">시간별</p> <span class="pagelTab-num"></span>
							</a></li>
						</ul>
					</div>
				</div>
			</div>

			<!-- 분야별 검색조건 -->
			<div class="bySector" style="display: block;" id="searchTab-1">
				<div class="bySector-inner mt-5 mb-5">
					<ul class="bySector-list nav nav-pills" role="tablist">
						<c:forEach items="${cateList}" var="resultInfo" varStatus="status">
							<c:forEach items="${resultInfo.subCtgrList}" var="resultInfo2" varStatus="status1">	
								<li class="bySector-item nav-item" role="presentation" style="width: 240px;">							
									<button class="nav-link" data-bs-toggle="pill"
										data-bs-target="#pills-home" type="button" role="tab"
										aria-controls="pills-home" aria-selected="true"
										onclick="onClickCate('${resultInfo2.ctgCd }')">${resultInfo2.ctgNm }</button>
								</li>									
							</c:forEach>						
						</c:forEach>					
					</ul>
				</div>
			</div>


			<!-- 기관별 검색조건 -->
			<div style="display: none; padding-top: 30px;" id="searchTab-2">
				<div class="byOrganization">
					<div class="byOrganization-inner">
						<div class="byOrganization-wrap">
							<div>
								<h4 class="byOrganization-title">원하시는 기관을 선택하세요</h4>
								<p class="byOrganization-txt">해당 주민자치센터를 선택하여 강좌를 볼 수 있습니다.
									자치센터를 다중 선택하시면 여러 자치센터의 강좌도 한번에 볼 수 있습니다.</p>
							</div>
							<a href="#" class="byOrganization-link">선택기관 강좌보기</a>
						</div>
					</div>
				</div>
				<div class="facility">
					<div class="facility-inner">
						<div class="facility-wrap">
							<ul class="facility-list">
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_01" checked=""> <label
											for="sub03_5_01" tabindex="0">선택
											<div class="facility-content">
												<p>와부읍주민자치센터</p>
												<span>T.031-521-6801</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_02"> <label
											for="sub03_5_02" tabindex="0">선택
											<div class="facility-content">
												<p>진접읍주민자치센터</p>
												<span>T.031-590-2612</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_03"> <label
											for="sub03_5_03" tabindex="0">선택
											<div class="facility-content">
												<p>화도읍주민자치센터</p>
												<span>T.031-590-2603</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_04"> <label
											for="sub03_5_04" tabindex="0">선택
											<div class="facility-content">
												<p>진건읍주민자치센터</p>
												<span>T.031-574-4746</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_05"> <label
											for="sub03_5_05" tabindex="0">선택
											<div class="facility-content">
												<p>오남읍주민자치센터</p>
												<span>T.031-590-2605</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_06"> <label
											for="sub03_5_06" tabindex="0">선택
											<div class="facility-content">
												<p>퇴계원읍주민자치센터</p>
												<span>T.031-590-2617</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_07" checked=""> <label
											for="sub03_5_07" tabindex="0">선택
											<div class="facility-content">
												<p>별내면주민자치센터</p>
												<span>T.031-527-8556</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_08"> <label
											for="sub03_5_08" tabindex="0">선택
											<div class="facility-content">
												<p>수동면주민자치센터</p>
												<span>T.031-593-9876</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_09"> <label
											for="sub03_5_09" tabindex="0">선택
											<div class="facility-content">
												<p>조안면주민자치센터</p>
												<span>T.031-576-8598</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_10"> <label
											for="sub03_5_10" tabindex="0">선택
											<div class="facility-content">
												<p>호평동주민자치센터</p>
												<span>T.031-511-7674</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_11"> <label
											for="sub03_5_11" tabindex="0">선택
											<div class="facility-content">
												<p>평내동주민자치센터</p>
												<span>T.031-591-4600</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_12"> <label
											for="sub03_5_12" tabindex="0">선택
											<div class="facility-content">
												<p>금곡동주민자치센터</p>
												<span>T.031-228-6648</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_13"> <label
											for="sub03_5_13" tabindex="0">선택
											<div class="facility-content">
												<p>양정동주민자치센터</p>
												<span>T.031-590-2664</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_14"> <label
											for="sub03_5_14" tabindex="0">선택
											<div class="facility-content">
												<p>다산1동주민자치센터</p>
												<span>T.031-590-1937</span>
											</div>
										</label>
									</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_15"> <label
											for="sub03_5_15" tabindex="0">선택
											<div class="facility-content">
												<p>다산2동주민자치센터</p>
												<span>T.031-590-2655</span>
											</div>
										</label>
									</div>
									<div class="flag green">접수중</div>
								</li>
								<li class="facility-item">
									<div class="facility-input input-group">
										<input type="checkbox" id="sub03_5_16"> <label
											for="sub03_5_16" tabindex="0">선택
											<div class="facility-content">
												<p>별내동주민자치센터</p>
												<span>T.031-590-8491</span>
											</div>
										</label>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<!-- 지역별 검색조건 -->
			<div class="region zone" style="display: block;" id="searchTab-3">
				<div class="region-inner zone-inner">
		            <div class="region-wrap zone-wrap">
		              <div class="region-map zone-map">
		                <div class="map">
		                  <img src="../../resources/images/layout/map_img.png" class="mapImg" usemap="#image-map">
		                  <map name="image-map">
		                    <area alt="와부읍" title="와부읍" href="javascript:void(0)" coords="172,278,166,297,181,306,161,324,180,346,192,366,204,392,234,406,244,390,236,368,225,360,230,337,241,315,254,310,270,288,253,245,237,248,227,240,210,245,196,253,187,262,175,271" shape="poly">
		                    <area alt="진접읍" title="진접읍" href="javascript:void(0)" coords="86,56,92,75,104,86,105,101,89,111,85,137,83,179,92,192,106,167,127,164,128,153,130,141,135,121,155,111,179,124,209,117,222,76,216,73,228,25,206,30,187,47,164,28,141,34,129,30,115,55,107,58,89,45" shape="poly">
		                    <area alt="화도읍" title="화도읍" href="javascript:void(0)" coords="253,145,250,169,247,178,238,181,233,196,243,212,232,236,239,244,256,240,265,271,274,286,292,290,295,284,321,279,329,283,334,280,352,240,360,237,383,180,366,156,315,194,302,193,297,178,305,165,304,151,291,144,281,135,268,142" shape="poly">
		                    <area alt="진건읍" title="진건읍" href="javascript:void(0)" coords="94,201,97,228,88,244,114,252,142,246,144,234,156,234,165,227,168,212,181,202,183,193,198,179,187,173,173,183,156,174,154,162,145,158,131,159,135,171,112,171,103,187,97,196" shape="poly">
		                    <area alt="오남읍" title="오남읍" href="javascript:void(0)" coords="135,139,143,153,157,159,159,168,168,176,187,169,199,177,242,168,247,147,244,139,246,131,242,122,213,124,184,130,173,129,160,119,140,123" shape="poly">
		                    <area alt="퇴계원읍" title="퇴계원읍" href="javascript:void(0)" coords="67,227,82,204,82,192,91,197,88,208,92,230,87,236,80,224" shape="poly">
		                    <area alt="별내면" title="별내면" href="javascript:void(0)" coords="2,139,20,153,36,141,53,146,62,160,62,179,78,177,78,145,74,134,78,104,99,97,98,88,84,82,85,68,78,62,66,59,60,71,57,100,40,112,38,122" shape="poly">
		                    <area alt="수동면" title="수동면" href="javascript:void(0)" coords="238,12,222,67,229,74,216,116,245,115,254,134,263,136,278,130,294,141,310,151,310,164,303,180,310,190,333,174,372,148,353,146,346,130,346,99,335,88,309,32,274,14,246,14" shape="poly">
		                    <area alt="조안면" title="조안면" href="javascript:void(0)" coords="244,316,242,334,231,345,231,359,241,369,248,389,235,410,257,422,266,454,291,442,287,413,326,334,333,287,322,289,302,286,295,297,273,292,259,315" shape="poly">
		                    <area alt="호평동" title="호평동" href="javascript:void(0)" coords="202,182,187,198,195,201,204,211,218,214,230,227,237,212,228,198,235,178,240,172" shape="poly">
		                    <area alt="평내동" title="평내동" href="javascript:void(0)" coords="185,206,172,214,172,225,182,226,185,233,193,237,201,244,214,236,226,235,222,223,203,216,194,207" shape="poly">
		                    <area alt="금곡동" title="금곡동" href="javascript:void(0)" coords="165,231,160,240,148,239,148,245,154,250,153,265,173,267,196,246,189,240,180,235,176,231" shape="poly">
		                    <area alt="양정동" title="양정동" href="javascript:void(0)" coords="118,257,122,275,117,282,128,295,135,297,133,312,152,316,157,322,174,306,160,299,168,271,151,268,149,252" shape="poly">
		                    <area alt="다산1동" title="다산1동" href="javascript:void(0)" coords="86,252,90,292,96,294,117,275,114,256" shape="poly">
		                    <area alt="다산2동" title="다산2동" href="javascript:void(0)" coords="90,297,93,302,110,310,112,318,105,321,107,328,114,332,130,316,128,309,129,301,123,296,113,284,102,297" shape="poly">
		                    <area alt="별내동" title="별내동" href="javascript:void(0)" coords="23,156,35,147,58,161,57,179,79,183,78,203,61,228,50,232,25,224,23,192" shape="poly">
		                  </map>
		                </div>
		              </div>
            </div>
          </div>
			</div>

			<!-- 대상별 -->
			<div style="display: none;" id="searchTab-4">
				<div class="bySector-inner mt-5 mb-5">
					<ul class="bySector-list nav nav-pills" role="tablist">
						<c:forEach items="${targetType}" var="resultInfo" varStatus="status">
								<li class="bySector-item nav-item" role="presentation"
									style="width: 16%;">							
									<button class="nav-link" data-bs-toggle="pill"
										data-bs-target="#pills-home" type="button" role="tab"
										aria-controls="pills-home" aria-selected="true"
										onclick="onClickAge('${resultInfo.cd }')">${resultInfo.cdNm }</button>
								</li>									
						</c:forEach>					
					</ul>
				</div>			
			
			</div>
			<!-- 시간대별 -->
			<div style="display: none;" id="searchTab-5">
				<div class="bySector-inner mt-5 mb-5">
					<ul class="bySector-list nav nav-pills" role="tablist">
						<li class="bySector-item nav-item" role="presentation"
							style="width: 16%;">							
							<button class="nav-link" data-bs-toggle="pill"
								data-bs-target="#pills-home" type="button" role="tab"
								aria-controls="pills-home" aria-selected="true"
								onclick="onClickTime('1001')">오전</button>
						</li>	
						<li class="bySector-item nav-item" role="presentation"
							style="width: 16%;">							
							<button class="nav-link" data-bs-toggle="pill"
								data-bs-target="#pills-home" type="button" role="tab"
								aria-controls="pills-home" aria-selected="true"
								onclick="onClickTime('2001')">오후</button>
						</li>	
						<li class="bySector-item nav-item" role="presentation"
							style="width: 16%;">							
							<button class="nav-link" data-bs-toggle="pill"
								data-bs-target="#pills-home" type="button" role="tab"
								aria-controls="pills-home" aria-selected="true"
								onclick="onClickTime('3001')">야간</button>
						</li>																
					</ul>
				</div>				
			
			</div>
			<style>
.search_sel select {
	margin-right: 10px;
	/*width: 250px;*/
}

textarea, input, select {
	font-size: 16px;
	color: #222;
	font-weight: normal;
	text-decoration: none;
	border: 0;
	border-radius: 0;
	resize: none;
	box-shadow: none;
	-webkit-appearance: none;
	appearance: none;
}

select {
	width: 180px;
	height: 5rem;
	font-size: 1.8rem;
	color: #666;
	padding: 0 20px;
	letter-spacing: -1px;
	box-sizing: border-box;
	background: #fff url(/web/resources/images/common/ico_sel.png) 95%
		center no-repeat;
	border: 1px solid #ddd;
	border-radius: 14px;
	background-color: #fff;
}

option:hover {
	background-color: yellow;
}
</style>
			<div class="searchBar" style="display: none">
				<div class="searchBar-inner">
					<div class="searchBar-wrap moMt">
						<div class="searchBar-flexbox column">
							<div class="search_sel">

								<select id="dayGbn" name="dayGbn" title="시작시간대">
									<option value="" selected="selected">전체(평일/주말)</option>
									<option value="1001">평일</option>
									<option value="2001">주말</option>
								</select>
							</div>



							<!-- 
	                <div class="searchBar-item1 w200">
                
                  <button class="btn">1차 강좌분류</button>
                  <ul class="searchBar-list">
                    <li>
                      <button>전체</button>
                    </li>
                    <li>
                      <button>문화교양</button>
                    </li>
                    <li>
                      <button>생활체육</button>
                    </li>
                    <li>
                      <button>어학</button>
                    </li>
                    <li>
                      <button>정보화</button>
                    </li>
                    <li>
                      <button>유아, 아동</button>
                    </li>
                    <li>
                      <button>헬스</button>
                    </li>
                  </ul>
                </div>
                   -->
							<div class="searchBar-item w200">
								<button class="btn">2차 강좌분류</button>
								<ul class="searchBar-list">
									<li>
										<button>전체</button>
									</li>
									<li>
										<button>직업능력</button>
									</li>
									<li>
										<button>어학</button>
									</li>
									<li>
										<button>문학예술스포츠</button>
									</li>
									<li>
										<button>인무교양</button>
									</li>
									<li>
										<button>시민공공역량</button>
									</li>
								</ul>

							</div>
							<div class="searchBar-item w200">
								<button class="btn">기관 선택</button>
								<ul class="searchBar-list">
									<li>
										<button>전체</button>
									</li>
									<li>
										<button>와부읍 주민자치센터</button>
									</li>
									<li>
										<button>진접읍 주민자치센터</button>
									</li>
									<li>
										<button>화도읍 주민자치센터</button>
									</li>
									<li>
										<button>진건읍 주민자치센터</button>
									</li>
									<li>
										<button>오남읍 주민자치센터</button>
									</li>
									<li>
										<button>퇴계원읍 주민자치센터</button>
									</li>
									<li>
										<button>별내면 주민자치센터</button>
									</li>
									<li>
										<button>수동면 주민자치센터</button>
									</li>
									<li>
										<button>조안면 주민자치센터</button>
									</li>
									<li>
										<button>호평동 주민자치센터</button>
									</li>
									<li>
										<button>평내동 주민자치센터</button>
									</li>
									<li>
										<button>금곡동 주민자치센터</button>
									</li>
									<li>
										<button>양정동 주민자치센터</button>
									</li>
									<li>
										<button>다산1동 주민자치센터</button>
									</li>
									<li>
										<button>다산2동 주민자치센터</button>
									</li>
								</ul>
							</div>
							<div class="searchBar-item w200">
								<button class="btn">강좌명</button>
								<ul class="searchBar-list">
									<li>
										<button>전체</button>
									</li>
									<li>
										<button>강좌명</button>
									</li>
									<li>
										<button>기관명</button>
									</li>
								</ul>
							</div>
						</div>
						<div class="searchBar-flexbox">
							<div class="searchBar-group input-group">
								<label for="inp03_01_31" class="visually-hidden"></label> <input
									type="text" id="inp03_01_31" class="form-control"
									placeholder="검색어를 입력하세요">
							</div>
							<div class="searchBar-group input-group btn-group">
								<button class="btn searchBar-search" type="button">
									<img src="../../resources/images/layout/icon_search_w.png"
										alt="아이콘">검색
								</button>
								<button class="btn searchBar-reset" type="button">
									<img src="../../resources/images/layout/ico_reset.png"
										alt="아이콘">초기화
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="search-result">
				<div class="search-result-inner mt-3 mb-3">
					<div class="search-result-title">
						<img src="../../resources/images/sub/ico_search-result.png"
							alt="아이콘">
						<div class="search-result-txt">
							검색 결과 <span>총 <span id="totalRecordCount"></span>개
							</span>의 <br class="mobile">
							<span id="totalRecordMessage">결과가 조회되었습니다.</span>
						</div>
					</div>
				</div>
			</div>

			<div class="article">
				<div class="article-inner">
					<div class="article-wrap">
						<div class="article-top mt-5">
							<ul class="article-list list-01 nav nav-fill" id="pills-tab"
								role="tablist">
								<li class="nav-item" role="presentation">
									<button class="nav-link active" data-bs-toggle="pill"
										type="button" role="tab" aria-controls="pills-home"
										aria-selected="true" onclick="onReceptMethod('')">전체</button>
								</li>
								<li class="nav-item" role="presentation">
									<button class="nav-link" data-bs-toggle="pill" type="button"
										role="tab" aria-controls="pills-home" aria-selected="false" onclick="onReceptMethod('ON')">온라인접수</button>
								</li>
								<li class="nav-item" role="presentation">
									<button class="nav-link" data-bs-toggle="pill" type="button"
										role="tab" aria-controls="pills-home" aria-selected="false" onclick="onReceptMethod('OFF')">방문접수</button>
								</li>
							</ul>
							<div class="article-right">
								<ul class="right-list list-02 nav nav-fill" id="pills-tab"
									role="tablist">
									<li class="nav-item" role="presentation">
										<button class="nav-link active" data-bs-toggle="pill" type="button"
											role="tab" aria-controls="pills-home" aria-selected="true"
											onclick="changeTheme('THUMB');" title="썸네일"></button>
									</li>
									<li class="nav-item" role="presentation">
										<button class="nav-link" data-bs-toggle="pill"
											type="button" role="tab" aria-controls="pills-home"
											aria-selected="false" onclick="changeTheme('MAGAZINE');" title="매거진"></button>
									</li>
									<li class="nav-item" role="presentation">
										<button class="nav-link" data-bs-toggle="pill" type="button"
											role="tab" aria-controls="pills-home" aria-selected="false"
											onclick="changeTheme('LIST');" title="리스트"></button>
									</li>
								</ul>
								<div class="article-select">
									<button class="btn">전체</button>
									<ul class="select-list">
										<li>
											<button>전체</button>
										</li>
										<li>
											<button>접수 마감순</button>
										</li>
										<li>
											<button>최신 등록순</button>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="article-bottom">
							<div class="article-thumb thumb">
								<ul class="thumb-list" id="displayThumbList">
								</ul>
							</div>


							<div class="article-magazine magazine">
								<ul class="magazine-list" id="displayMagazineList">
								</ul>
							</div>


							<div class="article-lsit list" id="displayList">
							</div>



							<div id="displayPageing"></div>

						</div>
					</div>
				</div>
			</div>



		</form:form>


	</div>
</main>

<script>
    /*검색tab처리*/
    function changTab(data){
    	console.log('changTab==' + data);
    	for(var i=1; i<6;i++){
    		$('#searchTab-'+i).hide();
    	}
    	$('#searchTab-'+data).show();
    }
    
    /*화면 스킨 변경*/
    function changeTheme(data){
    	console.log("changeThem===" + data);
    	DISPLAY_TYPE = data;
    	drawDisplay();
    }
    /*날자변환함수*/
    function getDateInfo(data, gubun){
    	//console.log('getDateInfo');
    	if(gubun == ''){
    		gubun = '';
    	}
    	if(data == null || data == '' || data.length != 8){
    		return data;
    	}
    	else{
    		return data.substring(0,4) + gubun + data.substring(4,6) + gubun + data.substring(6,8);
    	}
    }
    /*시간변환함수*/
    function getTimeInfo(data){
    	if(data == null || data == '' || data.length != 4){
    		return data;
    	}
    	else{
    		return data.substring(0,2) + ':' + data.substring(2,4);
    	}
    }
    /*분야선택*/
    function onClickCate(data){
    	console.log('onClickCate');
		var form = document.searchVO;
    	form.searchTab.value = 'CATE';
    	form.CtgCd.value = data;
    	goSearch();    	
    }

    
    /*교육대상선택(나이)*/
    function onClickAge(data){
    	console.log('onClickAge');
    	var form = document.searchVO;
    	form.searchTab.value = 'TARGET';
    	form.edcTargetinfo.value = data;
    	goSearch();  
    }
    
    /*시간대별*/
    function onClickTime(data){
    	console.log('onClickTime');
    	var form = document.searchVO;
    	form.searchTab.value = 'TIME';
    	form.hourbandGbn.value = data;
    	goSearch();  
    	
    }
    /*접수방식*/
    function onReceptMethod(data){
    	console.log('onReceptMethod');
    	var form = document.searchVO;
    	form.searchOnOff.value = data;
    	goSearch();  
    }
    $('#searchTab-3').hide();

    
    function goSearch(){
    	console.log('goSearch');
		var form = document.searchVO;
		form.pageIndex.value = 1;
    	
    	
    	$.ajax({
			//url : '${ajaxUrl}',
			url : './list2Ajax',
			data : $('#searchVO').serialize(),
			type : "GET",
			dataType : 'json',
			cache : false,				
			success : function(data, textStatus, jqXHR) {
	    		const obj = JSON.parse(data);
	    		if(obj != undefined &&  obj != null && obj.result == true){
	    			if(obj.data.pagination.TotalRecordCount != undefined && obj.data.pagination.TotalRecordCount >0){
	    				TotalRecordCount = obj.data.pagination.TotalRecordCount;
	    				$('#totalRecordCount').html(TotalRecordCount);
	    				$('.pagelTab-num').html(TotalRecordCount);
	    			}
	    			else{
	    				TotalRecordCount = 0;
	    				$('#totalRecordCount').html(0);
	    				$('.pagelTab-num').html(0);
	    			}
	    			
	    			CURRENT_DATA = obj.data;
	    			drawDisplay();
	    			drawPaging();
	    		}
			},
			error : function(request, error, jqXHR) {
				console.log("목록조회 실패 " + error);
			}
		});
    }
    function goReset(){
    	
    }
    </script>