<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<script type="text/javascript">
		var DISPLAY_TYPE = 'THUMB';//thumb 썸네일, list : 리스트
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
				if (!$("#orgLtype").val() && $("#orgMtype").val()) {
					alert("기관유형1을 선택해 주세요.");
					$("#orgLtype").focus();
					return;
				}
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
		    			}
		    			else{
		    				$('#totalRecordCount').html(0);
		    			}
		    			drawDisplay(obj.data);
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

		function drawDisplay(data){
			var itemsArray = [];

			if(DISPLAY_TYPE == 'THUMB'){
				//debugger;
				var items = data.contents;
				for(var i=0; i<items.length; i++){
					var item = items[i];
					var filePath = '/web/resources/images/common/no_img.png';
					if(item.edc_img_filenm != undefined && item.edc_img_filenm != null && item.edc_img_filenm.length>0){
						filePath = 'http://211.188.68.230/web/common/file/view/' + item.edc_img_path + item.edc_img_filenm + '?originName='+item.edc_img_origin;
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
								      <p>`+item.edc_rsvn_sdate+`~`+item.edc_rsvn_edate+`</p>
								    </li>
								    <li>
								      <img src="../../resources/images/sub/ico_article_06.png" alt="비용">비용 :
								      <p>`+item.sale_amt+`원</p>
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
			else if(DISPLAY_TYPE == 'MAGINE'){
				
			}
			else if(DISPLAY_TYPE == 'LIST'){
				
			}
			else{}
			
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
            <img src="../../resources/images/sub/img_subVisual_03.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle"><c:out value="${SELECTED_MENU_OBJ.menuNm}" /></h3>
        </div>
      </div>

      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">교육신청</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">교육신청</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">문화강좌</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">

	<form:form commandName="searchVO" name="searchVO" action="javascript:;" method="get">
		<form:hidden path="CtgCd"/>
		<form:hidden path="pageIndex"/>
		<form:hidden path="searchTab"/><!-- 강좌명,지역별,,, -->
		<form:hidden path="searchOnOff"/><!-- 온라인, 온라인+방문 -->
		<input type="hidden" name="scrollVar" id="scrollVar" /><!-- 스크롤 위치 저장 -->
		<input type="hidden" name="usePagingYn" id="usePagingYn" value="Y"/>


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
    background: #fff url(/web/resources/images/common/ico_sel.png) 95% center no-repeat;
    border: 1px solid #ddd;
    border-radius: 14px;
    background-color: #fff;
}
    option:hover {
      background-color: yellow;
    }
    
   
</style>

<div class="searchBar">
          <div class="searchBar-inner">
            <div class="searchBar-wrap">
              <div class="searchBar-basicBox">
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
                <div class="searchBar-group input-group">
                  <label for="inp04_01_01_01" class="visually-hidden"></label>
                  <input type="text" id="inp04_01_01_01" class="form-control w400" placeholder="검색어를 입력하세요!">
                </div>
                <div class="searchBar-group input-group">
                  <button class="btn searchBar-search" type="button">
                    <img src="../../resources/images/layout/icon_search_w.png" alt="아이콘">검색
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="search-result">
          <div class="search-result-inner mt-3 mb-3">
            <div class="search-result-title">
              <img src="../../resources/images/sub/ico_search-result.png" alt="아이콘">
              <div class="search-result-txt"> 검색 결과 <span>총 <span id="totalRecordCount"></span>개</span>의
                <br class="mobile"><span id="totalRecordMessage">결과가 조회되었습니다.</span>
              </div>
            </div>
          </div>
        </div>

        <div class="article">
          <div class="article-inner">
            <div class="article-wrap">
              <div class="article-top mt-5">
                <ul class="article-list list-01 nav nav-fill" id="pills-tab" role="tablist">
                  <li class="nav-item" role="presentation">
                    <button class="nav-link active" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="true">전체</button>
                  </li>
                  <li class="nav-item" role="presentation">
                    <button class="nav-link" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="false">온라인접수</button>
                  </li>
                  <li class="nav-item" role="presentation">
                    <button class="nav-link" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="false">방문접수</button>
                  </li>
                </ul>
                <div class="article-right">
                  <ul class="right-list list-02 nav nav-fill" id="pills-tab" role="tablist">
                    <li class="nav-item" role="presentation">
                      <button class="nav-link" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="true" title="썸네일"></button>
                    </li>
                    <li class="nav-item" role="presentation">
                      <button class="nav-link active" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="false" title="매거진"></button>
                    </li>
                    <li class="nav-item" role="presentation">
                      <button class="nav-link" data-bs-toggle="pill" type="button" role="tab" aria-controls="pills-home" aria-selected="false" title="리스트"></button>
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
                
                
                <div class="article-magazine magazine" style="display:none">
                  <ul class="magazine-list" id="displayMagazineList">
                    <li class="magazine-item">
                      <a href="javascript:void(0)" class="magazine-link">
                        <figure class="magazine-figure">
                          <img src="../../resources/images/sub/img_article_01.png" alt="이미지">
                          <figcaption class="magazine-ask blue position-absolute">접수전</figcaption>
                        </figure>
                        <div class="magazine-content">
                          <ul class="magazine-hash hash">
                            <li>성인</li>
                            <li>다산1동</li>
                            <li>온라인교육</li>
                          </ul>
                          <h4 class="magazine-title truncate-1">에어로빅과 신나는 줌배댄스 수업으로 Go ! Go !</h4>
                          <p class="magazine-txt truncate-2">레그익스텐션, 버티컬플라이, 라잉레그컬, 하이랫 플 등 보유 ! 레그익스텐션, 버티컬플라이, 라잉레그컬, 하이랫
                            플 등 보유 !</p>
                          <ul class="magazine-desc">
                            <li>
                              <img src="../../resources/images/sub/ico_article_04.png" alt="아이콘">기관 :
                              <p>와부읍주민자치센터</p>
                            </li>
                            <li>
                              <img src="../../resources/images/sub/ico_article_05.png" alt="아이콘">일정 :
                              <p>22.4.6~22.4.19</p>
                            </li>
                            <li>
                              <img src="../../resources/images/sub/ico_article_06.png" alt="아이콘">비용 :
                              <p>80,000원</p>
                            </li>
                          </ul>
                          <p class="magazine-view">
                            <img src="../../resources/images/sub/ico_article_07.png" alt="아이콘">조회 :
                            <span>2136</span>
                          </p>
                        </div>
                      </a>
                    </li>
                  </ul>
                </div>
                
                
                
                
			<div class="article-lsit list">
                  <table class="list-table table" id="displayList" style="display:none">
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
                      <tr>
                        <td class="number">1</td>
                        <td class="title">
                          <a href="#" class="truncate-1">한글서예 전문가과정-한경희</a>
                        </td>
                        <td class="member">0/15</td>
                        <td class="fee">유료</td>
                        <td class="area">다산2동주민자치센터</td>
                        <td class="prace">2층 2강의실</td>
                        <td class="date">2022.04.01~2022.06.30
                          <br>화 / 18:30 ~ 20:20
                        </td>
                        <td class="state">
                          <p class="gray">접수마감</p>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>                
                
                
                
                <div id="displayPageing"></div>
                
              </div>
            </div>
          </div>
        </div>

 
      
  	</form:form>    
      
      
      </div>
    </main>

    