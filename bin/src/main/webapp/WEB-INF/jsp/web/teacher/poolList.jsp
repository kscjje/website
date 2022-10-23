<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

	$(document).ready(function(){
		fn_set_params();
	});

	function fn_go_detail(detailNo) {
		var _scrollTop = window.scrollY || document.documentElement.scrollTop;
		$('#scrollVar').val(_scrollTop);

		location.hash = $('#searchForm').serialize();
		location.href = './poolDetail?detailNo=' + detailNo;
	}

	function fn_set_params() {
		var hsh = location.hash;
		if(hsh != ''){
			var obj = fn_qs_json(location.hash);

			var idx = 1;

			$.each(obj, function(key, value){
				switch (key) {
					case 'pageIndex' :
						idx = value;
						break;
					case 'scrollVar' :
						$('#scrollVar').val(value);
						break;
					case 'searchKeyword':
						$('#searchKeyword').val(value);
						break;
				}
			  });//end of each

			  fn_set_page(idx,'1' , $('#scrollVar').val())	;

		}else{

			fn_set_page(1,'1');

		}
	}


	function fn_set_page(pageNo ,type , scrollVar ){
		location.hash = $('#searchForm').serialize();
		var pIdx = pageNo != '' ? pageNo : '1';
		$('#pageIndex').val(pIdx);

		$.ajax({
		      url: './poolListAjax',
		      data : $('#searchForm').serialize(),
		      type : "GET",
		      cache : false,
		      dataType : "html",
		      success: function(data) {
		         $('.search_con').html(data);

		         if(type == '' || type == undefined){
					//var ofset= $('.search_sel').offset().top-50;
					$('html').animate({scrollTop : ofset},100);
				}else if(scrollVar){
					window.scrollTo(0, scrollVar);
				}

	       		//hash 없애기
				var uri = window.location.toString();
			    if (uri.indexOf("#") > 0) {
			        var clean_uri = uri.substring(0, uri.indexOf("#"));
			        window.history.replaceState({}, document.title, clean_uri);
			    }
		      },
		      error : function(){
		    	  alert("불러 오기 오류");
		      }
		    });
	}


</script>

    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="/web/resources/images/sub/img_subVisual_03.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h3>
        </div>
      </div>

      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">강사현황</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">교육신청</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">강사뱅크</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">강사현황</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">
        <div class="mb-5">
          <div class="instructor-group">
            <a href="<c:url value="./poolRegist"/>" class="instructor-link">
              <div>강사신청바로가기
                <img src="/web/resources/images/sub/ico_shortcut.png" alt="아이콘">
              </div>
              <p>전문적인 지식을 겸비하고 적절한
                <br class="mobile">전달력을 가진 분
              </p>
            </a>
          </div>
        </div>
        
<form name="searchForm" id="searchForm">
<input type="hidden" id="pageIndex"  	name="pageIndex" value="" />
<input type="hidden" id="scrollVar"     name="scrollVar" />
        
        <div class="searchBar">
          <div class="searchBar-inner">
            <div class="searchBar-wrap">
              <div class="searchBar-basicBox">
                <div class="searchBar-group input-group">
                  <label for="searchKeyword" class="visually-hidden"></label>
                  <input type="text" id="searchKeyword" name="searchKeyword" class="form-control w500" title="강사은행 검색" placeholder="강의분야/강사명을 입력해주세요.">
                </div>
                <div class="searchBar-group input-group">
                  <button class="btn searchBar-search" type="button" onclick="fn_set_page(1)">
                    <img src="/web/resources/images/layout/icon_search_w.png" alt="검색버튼">검색
                  </button>
                </div>
              </div>
            </div>
          </div>
</form>          
        </div>
        <div class="search-result">
          <div class="search-result-inner mt-3 mb-3">
            <div class="search-result-title">
              <img src="/web/resources/images/sub/ico_search-result.png" alt="아이콘">
              <div class="search-result-txt"> 검색 결과 <span>총 317개</span>의
                <br class="mobile">결과가 조회되었습니다.
              </div>
            </div>
          </div>
        </div>
        <div class="article-bottom">
          <div class="article-lsit list">
            <table class="list-table table">
              <colgroup>
                <col style="width: 5%">
                <col style="width: 10%">
                <col style="width: 10%">
                <col style="width: 60%">
                <col style="width: 10%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col" class="number">번호</th>
                  <th scope="col" class="member">강사명</th>
                  <th scope="col" class="area">교육분야</th>
                  <th scope="col" class="title">강의가능분야</th>
                  <th scope="col" class="date">등록일</th>
                </tr>
              </thead>
              <tbody class="search_con">
             
              </tbody>
            </table>
          </div>
        </div>

        <div class="paginate"></div>
      </div>
    </main>
