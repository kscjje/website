<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
var COM_TOP_CTGR = [];
var COM_CTGR = [];


<c:forEach items="${cateList}" var="resultInfo" varStatus="status">
		COM_TOP_CTGR.push({
			'ctgCd':'${resultInfo.ctgCd}',
			'ctgNm':'${resultInfo.ctgNm}'		
			});
	<c:forEach items="${resultInfo.subCtgrList}" var="resultInfo2" varStatus="status1">
		COM_CTGR.push({
			'ctgCd':'${resultInfo2.ctgCd}',
			'parentCtgCd':'${resultInfo.ctgCd}',
			'ctgNm':'${resultInfo2.ctgNm}'		
			});
	</c:forEach>
</c:forEach>


</script>

<main class="content" id="content">
      <div class="section-1">
        <div class="section-title">
          <h2 class="section-h2">당신의 꿈과 열정을 응원합니다.</h2>
          <p class="section-p title">주민의 문화욕구 해소와 정서함양에
            <br class="mobile">기여하고자 다양한 체험의 장을 마련하고 있습니다.
          </p>
        </div>

        <div class="tab">
        
        <form id="form_search" name="form_search" method="post" onsubmit="return false" action="/web/edc/program/list">
        <!-- 강좌명 -->
        <input type="hidden" id="searchKeyword" name="searchKeyword"/>
        <!-- 분야코드 -->
        <input type="hidden" id="CtgCd" name="CtgCd"/>
        <!-- 센터(기관) -->
        <input type="hidden" id="orgMtype" name="orgMtype"/>
        <!-- 대상 -->
        <input type="hidden" id="edcTargetinfo" name="edcTargetinfo"/>
        
          <div class="inner">
            <div class="tab-wrap">
              <nav>
                <div class="nav nav-tabs nav-tabTitle" id="nav-tab1" role="tablist">
                  <button class="nav-link active" id="nav-culture-tab" data-bs-toggle="tab" data-bs-target="#nav-culture" type="button" value="0" onclick="changeFeildTab(0);">문화강좌</button>
                  <button class="nav-link" id="nav-class-tab" data-bs-toggle="tab" data-bs-target="#nav-class" type="button" value="0" onclick="changeFeildTab(1);">생활체육강좌</button>
                </div>
              </nav>
              <div class="tab-content" id="nav-tabContent1">
                <div class="tab-pane fade show active" id="nav-culture" role="tabpanel">
                  <div class="type">
                    <div class="type-wrap">
                      <button>분야</button>
                      <ul class="type-list" id="divLectureFielld1">
                      </ul>
                    </div>
                    <div class="type-wrap">
                      <button>센터</button>
                      <ul class="type-list">
                        <li>
                          <button type="button">전체</button>
                        </li>
<c:forEach items="${orgInfoList}" var="resultInfo" varStatus="status">
	                    <li>
	                      <button tyle="button" value="${resultInfo.orgNo}">${resultInfo.orgNm}</button>
	                    </li>                    
</c:forEach>
                      </ul>
                    </div>
                    
                    <div class="type-wrap">
                      <button>대상</button>
                      <ul class="type-list">
                        <li>
                          <button type="button">전체</button>
                        </li>
<c:forEach items="${targetList}" var="resultInfo" varStatus="status">
                        <li>
                          <button tyle="button" value="${resultInfo.cd}">${resultInfo.cdNm}</button>
                        </li>
</c:forEach>                        
                      </ul>
                    </div>
                    <div class="type-wrap">
                      <div class="input-wrap">
                        <label for="inputclass1" class="visually-hidden">강좌명</label>
                        <input type="text" id="inputclass1" placeholder="강좌명을 입력하세요.">
                      </div>
                    </div>
                  </div>
                  <div class="search-btn">
                    <button>
                      <img src="${requestScope.resourceContext}/images/main/icon_search_w.png" alt="검색아이콘">검색
                    </button>
                  </div>
                </div>
                <div class="tab-pane fade" id="nav-class" role="tabpanel">
                  <div class="type">
                    <div class="type-wrap">
                      <button>분야</button>
                      <ul class="type-list" id="divLectureFielld2">
                      </ul>
                    </div>
                    <div class="type-wrap">
                      <button>센터</button>
                      <ul class="type-list">
                        <li>
                          <button type="button">전체</button>
                        </li>
<c:forEach items="${targetList}" var="resultInfo" varStatus="status">
                        <li>
                          <button tyle="button" value="${resultInfo.cd}">${resultInfo.cdNm}</button>
                        </li>
</c:forEach>
                      </ul>
                    </div>
                    <div class="type-wrap">
                      <button>대상</button>
                      <ul class="type-list">
 <c:forEach items="${targetList}" var="resultInfo" varStatus="status">
                        <li>
                          <button tyle="button" value="${resultInfo.cd}">${resultInfo.cdNm}</button>
                        </li>
</c:forEach> 
                      </ul>
                    </div>
                    <div class="type-wrap">
                      <div class="input-wrap">
                        <label for="inputclass2" class="visually-hidden">강좌명</label>
                        <input type="text" id="inputclass2" placeholder="강좌명을 입력하세요.">
                      </div>
                    </div>
                  </div>
                  <div class="search-btn">
                    <button>
                      <img src="${requestScope.resourceContext}/images/main/icon_search_w.png" alt="검색아이콘">검색
                    </button>
                  </div>
                </div>
              </div>
            </div>
            
            </form>
            
            
            
            <div class="tab-wrap right pc">
              <nav>
                <div class="nav nav-tabs nav-tabTitle" id="nav-tab2" role="tablist">
                  <button class="nav-link active" id="nav-area-tab" data-bs-toggle="tab" data-bs-target="#nav-area" type="button" data-text="분야별" data-after="분야별">분야별</button>
                  <button class="nav-link" id="nav-engine-tab" data-bs-toggle="tab" data-bs-target="#nav-engine" type="button" data-text="기관별 강좌">기관별 강좌</button>
                  <button class="nav-link" id="nav-zone-tab" data-bs-toggle="tab" data-bs-target="#nav-zone" type="button" data-text="지역별 강좌">지역별 강좌</button>
                  <button class="nav-link" id="nav-object-tab" data-bs-toggle="tab" data-bs-target="#nav-object" type="button" data-text="대상별 강좌">대상별 강좌</button>
                  <button class="nav-link" id="nav-time-tab" data-bs-toggle="tab" data-bs-target="#nav-time" type="button" data-text="시간별 강좌">시간별 강좌</button>
                </div>
              </nav>
              <div class="tab-content" id="nav-tabContent2">
<!-- PC버전 분야별 -->              
                <div class="tab-pane fade show active " id="nav-area" role="tabpanel">
                  <ul class="field-list" id="ulLectureField"></ul>
                </div>
<!-- PC버전 기관별강좌 -->                
                <div class="tab-pane fade pc" id="nav-engine" role="tabpanel">
                  <ul class="field-list field-tile w144">
<c:forEach items="${orgInfoList}" var="resultInfo" varStatus="status">
                    <li class="field-item">
                      <a href="javascript:goTotalSearch('ORGAN','${resultInfo.orgNo }','${resultInfo.orgNm }');">${resultInfo.orgNm }</a>
                    </li>
</c:forEach>
                  </ul>
                </div>
                <div class="tab-pane fade" id="nav-zone" role="tabpanel">
                  <ul class="field-list field-tile w144">
<!-- PC버전 지역별 -->
<c:forEach items="${areaList}" var="resultInfo" varStatus="status">
                    <li class="field-item">
                      <a href="javascript:goTotalSearch('ORGAN','${resultInfo.areaCd }','${resultInfo.areaNm }');">${resultInfo.areaNm }</a>
                    </li>
</c:forEach>       
                  </ul>
                  
                </div>
                <div class="tab-pane fade" id="nav-object" role="tabpanel">
                  <ul class="field-list" id="ulLectureTarget">
<!-- PC버전 대상별 -->                  
<c:forEach items="${targetList}" var="resultInfo" varStatus="status">
                    <li class="field-item">
                     <a href="javascript:goTotalSearch('TARGET','${resultInfo.cd }','${resultInfo.cdNm }');">	
                        <img src="${requestScope.resourceContext}/images/main/ico_target_01.png" alt="${resultInfo.cdNm }">${resultInfo.cdNm }
                      </a>
                    </li>
</c:forEach> 
                  </ul>
                </div>
<!-- PC버전 시간별 -->                
                <div class="tab-pane fade" id="nav-time" role="tabpanel">
                  <ul class="field-list">
                    <li class="field-item">
                      <a href="javascript:goTotalSearch('TIME','1001','오전');">
                        <img src="${requestScope.resourceContext}/images/main/ico_target_06.png" alt="오전">오전
                      </a>
                    </li>
                    <li class="field-item">
                      <a href="javascript:goTotalSearch('TIME','2001','오후');">
                        <img src="${requestScope.resourceContext}/images/main/ico_target_07.png" alt="오후">오후
                      </a>
                    </li>
                    <li class="field-item">
					  <a href="javascript:goTotalSearch('TIME','3001','야간');">                      
                        <img src="${requestScope.resourceContext}/images/main/ico_target_08.png" alt="야간">야간
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div id="visual">
          <div class="main_visual">
            <div class="inner">
              <div class="vis-top">
                <h3>강좌목록</h3>
                <div class="vis-notice">
                  <div class="notice-before">접수전 <span>34</span>
                  </div>
                  <div class="notice-ing">접수중 <span>134</span>
                  </div>
                </div>
              </div>
              <div class="vis_slider">
                <div class="swiper-container">
                  <div class="swiper-progress">
                    <span></span>
                  </div>
                  <div class="swiper-wrapper" id="DIV_MAIN_LECTURE_LIST"></div>
                </div>
                <!-- //swiper-container -->
                <div class="swiper-controls">
                  <button class="prev">이전</button>
                  <div class="swiper-pagination"></div>
                  <button class="next">다음</button>
                  <button class="play-stop">메인배너 자동플레이/일시정지</button>
                </div>
                <!-- //swiper-controls -->
              </div>
              <!-- //vis_slider-->
            </div>
          </div>
        </div>
      </div>




      </div>

      <div class="section-2">
        <div class="section-title">
          <h2 class="section-h2 sub-h2">운영하는 강좌 한눈에 보기 <a href="/web/edc/program/list" class="add" title="더보기"></a>
          </h2>
          <p class="section-p">원하시는 지역을 선택하시면
            <br class="mobile"> 해당 지역의 강좌를 한눈에 보실 수 있습니다.
          </p>
        </div>
        <div class="region">
          <div class="inner">
            <div class="region-map">
              <div class="map">
                <img src="${requestScope.resourceContext}/images/layout/map_img.png" class="mapImg" usemap="#image-map">
                <map name="image-map">
                  <area alt="와부읍" title="와부읍" href="javascript:onClickArea(9)" coords="172,277,166,297,181,305,161,324,180,346,192,365,204,392,234,405,244,389,236,368,225,359,230,336,241,315,254,310,270,288,253,245,237,247,227,239,210,245,196,253,187,262,175,271" shape="poly">
                  <area alt="진접읍" title="진접읍" href="javascript:onClickArea(12)" coords="86,56,92,75,104,85,105,101,89,110,85,137,83,178,92,192,106,167,127,163,128,152,130,141,135,120,155,110,179,123,209,116,222,75,216,73,228,25,206,29,187,47,164,28,141,34,129,29,115,55,107,57,89,44" shape="poly">
                  <area alt="화도읍" title="화도읍" href="javascript:onClickArea(16)" coords="253,145,250,169,247,177,238,181,233,196,242,212,232,236,239,244,256,239,265,271,274,285,292,290,295,284,321,279,329,283,334,280,352,239,360,236,383,179,366,155,315,194,302,193,297,177,305,165,304,150,291,144,281,135,268,142" shape="poly">
                  <area alt="진건읍" title="진건읍" href="javascript:onClickArea(11)" coords="94,200,97,228,88,244,114,251,142,246,144,233,156,233,165,227,168,212,181,202,183,193,198,178,187,173,173,182,156,173,154,162,145,158,131,159,135,171,112,171,103,186,97,196" shape="poly">
                  <area alt="오남읍" title="오남읍" href="javascript:onClickArea(8)" coords="135,138,143,152,157,159,159,167,168,175,187,169,199,177,242,167,247,146,244,138,246,131,242,122,213,123,184,130,173,128,160,118,140,123" shape="poly">
                  <area alt="퇴계원읍" title="퇴계원읍" href="javascript:onClickArea(13)" coords="67,227,82,204,82,192,91,197,88,208,92,229,87,236,80,224" shape="poly">
                  <area alt="별내면" title="별내면" href="javascript:onClickArea(5)" coords="2,138,20,152,36,141,53,146,62,159,62,178,78,177,78,145,74,134,78,104,99,97,98,87,84,81,85,68,78,61,66,59,60,71,57,100,40,112,38,122" shape="poly">
                  <area alt="수동면" title="수동면" href="javascript:onClickArea(6)" coords="238,12,222,67,229,73,216,115,245,114,254,134,263,136,278,130,294,141,310,150,310,163,303,179,310,189,333,173,372,147,353,146,346,130,346,99,335,87,309,31,274,14,246,14" shape="poly">
                  <area alt="조안면" title="조안면" href="javascript:onClickArea(10)" coords="244,316,242,334,230,345,230,358,241,369,248,388,235,409,257,421,266,454,291,441,287,412,326,334,333,286,322,289,302,285,295,297,273,292,259,315" shape="poly">
                  <area alt="호평동" title="호평동" href="javascript:onClickArea(15)" coords="202,181,187,198,195,200,204,210,218,213,230,227,237,212,228,198,235,177,240,171" shape="poly">
                  <area alt="평내동" title="평내동" href="javascript:onClickArea(14)" coords="185,206,172,213,172,224,182,225,185,232,193,236,201,244,214,236,226,235,222,223,203,216,194,206" shape="poly">
                  <area alt="금곡동" title="금곡동" href="javascript:onClickArea(1)" coords="165,231,160,239,148,239,148,245,154,249,153,265,173,266,196,246,189,239,180,235,176,231" shape="poly">
                  <area alt="양정동" title="양정동" href="javascript:onClickArea(7)" coords="118,257,122,274,117,282,128,294,135,297,133,311,152,316,157,322,174,305,160,299,168,271,151,268,149,251" shape="poly">
                  <area alt="다산1동" title="다산1동" href="javascript:onClickArea(2)" coords="86,251,90,292,96,293,117,274,114,255" shape="poly">
                  <area alt="다산2동" title="다산2동" href="javascript:onClickArea(3)" coords="90,297,93,301,110,310,112,318,105,320,107,327,114,332,130,316,128,309,129,301,123,295,113,284,102,297" shape="poly">
                  <area alt="별내동" title="별내동" href="javascript:onClickArea(4)" coords="23,155,35,146,58,161,57,178,79,182,78,202,61,228,50,231,25,224,23,192" shape="poly">
                </map>
              </div>
            </div>
            <div class="region-con">
              <ul class="region-list" id="UL_MAIL_AREA_LECTURE_LIST">
<!--               
                <li class="region-item">
                  <p class="receive-after">접수전</p>
                  <div class="tit">
                    <a href="#" class="truncate-1">품격있는 삶을 위한 예술 강의 미학 수업</a>
                    <span class="data">2022.04.27</span>
                  </div>
                </li>
                <li class="region-item">
                  <p class="receive-ing">접수중</p>
                  <div class="tit">
                    <a href="#" class="truncate-1">와부읍 52기 수강생 모집</a>
                    <span class="data">2022.04.27</span>
                  </div>
                </li>
                <li class="region-item">
                  <p class="prepare">준비중</p>
                  <div class="tit">
                    <a href="#" class="truncate-1">에어로빅과 신나는 줌바댄스 수업으로 Go ! Go !</a>
                    <span class="data">2022.04.27</span>
                  </div>
                </li>
                <li class="region-item">
                  <p class="receive-after">접수전</p>
                  <div class="tit">
                    <a href="#" class="truncate-1">통기타를 배워 즐겁게 공연 !</a>
                    <span class="data">2022.04.27</span>
                  </div>
                </li>
                <li class="region-item">
                  <p class="receive-finish">접수마감</p>
                  <div class="tit">
                    <a href="#" class="truncate-1">말하기, 듣기, 독해 수업으로 다양한 주제와 상황에서 말하기 연습 해보세요</a>
                    <span class="data">2022.04.27</span>
                  </div>
                </li>
 -->                
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div class="section-3 contentT-skin_a-02">
        <div class="contentT">
          <div class="contentT-inner max-inner inner">
            <div class="section-title">
              <h2 class="section-h2 sub-h2">우리 동네 소식 확인하기</h2>
              <p class="section-p">남양주시 주민자치센터(읍면동)에서 알려드립니다.</p>
            </div>
            <div class="contentT-wrapper">
              <div class="contentT-left">
                <ul class="contentT-tab nav" id="contentTSkinA02-tab" role="tablist">
                  <li class="nav-item" role="presentation">
                    <button class="nav-link btn active" id="contentTSkinA02-1-tab" data-bs-toggle="pill" data-bs-target="#contentTSkinA02-1" type="button" role="tab" aria-controls="contentTSkinA02-1" aria-selected="true">공지사항</button>
                  </li>
                  <li class="nav-item" role="presentation">
                    <button class="nav-link btn" id="contentTSkinA02-2-tab" data-bs-toggle="pill" data-bs-target="#contentTSkinA02-2" type="button" role="tab" aria-controls="contentTSkinA02-2" aria-selected="false">기관소식</button>
                  </li>
                </ul>
                <div class="swiper-control">
                  <button class="swiper-prev btn" tabindex="0" aria-label="Previous slide" aria-controls="swiper-wrapper-99b34459c91082336" aria-disabled="false">이전</button>
                  <button class="swiper-next btn" tabindex="0" aria-label="Next slide" aria-controls="swiper-wrapper-99b34459c91082336" aria-disabled="false">다음</button>
                </div>
                <a href="#" class="add" title="더보기"></a>
                <div class="contentT-tab-content tab-content" id="contentTSkinA02-tabContent">
                  <div class="tab-pane fade show active" id="contentTSkinA02-1" role="tabpanel" aria-labelledby="contentTSkinA02-1-tab">
                    <div class="contentT-slider swiper pc swiper-initialized swiper-horizontal swiper-pointer-events swiper-backface-hidden">
                      <div class="swiper-wrapper" id="swiper-wrapper-638afa15495b03ec" aria-live="polite" style="cursor: grab;">
                        <div class="swiper-slide swiper-slide-visible swiper-slide-active" role="group" aria-label="1 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <!-- 새 글일 경우 i태그 new 아이콘 추가 -->
                              <!-- 글자수 38페이지 넘어갈 경우 스크립트로 i태그 추가 -->
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide swiper-slide-visible swiper-slide-next" role="group" aria-label="2 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide swiper-slide-visible" role="group" aria-label="3 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide" role="group" aria-label="4 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide" role="group" aria-label="5 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">타이실무역량키움 마케팅 현직자 직무스터디 캠프 캠프  ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide" role="group" aria-label="6 / 6" style="width: 292.667px; margin-right: 24px;">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                      </div>
                    <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
                    <div class="contentT-slider swiper mobile swiper-initialized swiper-horizontal swiper-pointer-events">
                      <div class="swiper-wrapper" id="swiper-wrapper-0d8462e8165977a6" aria-live="polite" style="cursor: grab; transition-duration: 0ms;">
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                      </div>
                    <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
                  </div>
                  <div class="tab-pane fade" id="contentTSkinA02-2" role="tabpanel" aria-labelledby="contentTSkinA02-2-tab">
                    <div class="contentT-slider swiper pc swiper-initialized swiper-horizontal swiper-pointer-events">
                      <div class="swiper-wrapper" id="swiper-wrapper-d70176010ad21ab4" aria-live="polite" style="cursor: grab; transition-duration: 0ms;">
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">타이실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">타이실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">
                                2022년 평생학습동아리 학습 나눔사업 공고(2차)<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                      </div>
                    <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
                    <div class="contentT-slider swiper mobile swiper-initialized swiper-horizontal swiper-pointer-events">
                      <div class="swiper-wrapper" id="swiper-wrapper-99b34459c91082336" aria-live="polite" style="cursor: grab; transition-duration: 0ms;">
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                        <div class="swiper-slide">
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">진저읍 주민자치센터</p>
                              <h4 class="slide-title truncate-2">남양주시 교육플랫폼 홈페이지 발표<i class="new"></i>
                              </h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                          <a class="slide-link" href="#">
                            <div class="slide-text">
                              <p class="tit">다산1동 주민자치센터</p>
                              <h4 class="slide-title truncate-2">실무역량키움 마케팅 현직자 직무스터디 캠프 참여자 모 ... <i class="new"></i></h4>
                              <div class="slide-desc truncate-2">평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라 평생교육법 제5조 및 남양주시평생
                                교육진흥조례 제3조 규정에 따라</div>
                            </div>
                            <div class="slide-info">
                              <p class="slide-date">
                                <span>2022.04.26</span>
                              </p>
                              <p class="slide-count">
                                <span>100</span>
                              </p>
                            </div>
                          </a>
                        </div>
                      </div>
                    <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
                    <div class="swiper-control">
                      <button class="swiper-prev btn" tabindex="0" aria-label="Previous slide" aria-controls="swiper-wrapper-99b34459c91082336" aria-disabled="false">이전</button>
                      <button class="swiper-next btn" tabindex="0" aria-label="Next slide" aria-controls="swiper-wrapper-99b34459c91082336" aria-disabled="false">다음</button>
                    </div>
                  </div>
                </div>
              </div>
              <div class="contentT-right">
                <a class="contentT-banner" href="#">
                  <img src="${requestScope.resourceContext}/images/main/img_noti.png" class="pc" alt="이미지">
                  <img src="${requestScope.resourceContext}/images/main/m_img_noti.png" class="mobile" alt="이미지">
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="section-4 contentT-skin_a-03">
        <div class="contentT">
          <div class="contentT-inner max-inner inner">
            <div class="section-title">
              <h2 class="section-h2 sub-h2">자주 찾는 메뉴 바로가기</h2>
              <p class="section-p">원하는 페이지로 빠른 이동을 도와드립니다.</p>
            </div>
            <div class="contentT-card">
              <ul class="card-list row">
                <li class="card-item col-3">
                  <a class="card-link" href="/web/edc/program/list">
                    <figure class="card-figure">
                      <img class="card-image" src="${requestScope.resourceContext}/images/main/img_register.png" alt="수강신청">
                    </figure>
                    <h4 class="card-title truncate-1">수강신청</h4>
                  </a>
                </li>
                <li class="card-item col-3">
                  <a class="card-link" href="/web/teacher/poolList">
                    <figure class="card-figure">
                      <img class="card-image" src="${requestScope.resourceContext}/images/main/img_lecturer.png" alt="강사소개">
                    </figure>
                    <h4 class="card-title truncate-1">강사소개</h4>
                  </a>
                </li>
                <li class="card-item col-3">
                  <a class="card-link" href="/web/board/qna/list">
                    <figure class="card-figure">
                      <img class="card-image" src="${requestScope.resourceContext}/images/main/img_qna.png" alt="묻고 닫하기">
                    </figure>
                    <h4 class="card-title truncate-1">묻고 답하기</h4>
                  </a>
                </li>
                <li class="card-item col-3">
                  <a class="card-link" href="#">
                    <figure class="card-figure">
                      <img class="card-image" src="${requestScope.resourceContext}/images/main/img_video.png" alt="온라인 동영상 강좌">
                    </figure>
                    <h4 class="card-title truncate-1">온라인 동영상 강좌</h4>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

    </main>
    
<script>
var G_LECTURE_FIELD = 0;
//COM_TOP_CTGR
if(COM_TOP_CTGR != undefined && COM_TOP_CTGR.length>0){
	for(var i=0; i<COM_TOP_CTGR.length; i++){
		var item = COM_TOP_CTGR[i];
		if(i==0){
			$('#nav-culture-tab').html(item.ctgNm);
		}
		else if(i==1){
			$('#nav-class-tab').html(item.ctgNm);
		}
	}
}


//COM_CTGR
var drawHtml = [];
$('#ulLectureField').html('');
if(COM_CTGR !=undefined && COM_CTGR.length>0){
	for(var i=0; i<COM_CTGR.length; i++){
		var item = 	COM_CTGR[i];
		drawHtml.push('<li class="field-item"><a href="javascript:goTotalSearch(\'CATE\', \'' + item.ctgCd + '\', \'' + item.ctgNm + '\');"><img src="${requestScope.resourceContext}/images/main/icon_all.png" alt="' + item.ctgNm + '">' + item.ctgNm + '</a></li>');
	}
	$('#ulLectureField').html(drawHtml.join(''));
}


function changeFeildTab(data){
	console.log('changeFeildTab');
	G_LECTURE_FIELD = data;
	var entity = COM_TOP_CTGR[G_LECTURE_FIELD];
	var drawField = [];

	drawField.push('<li><button type="button" onclick=changeLectureField("");>전체</button></li>');		
	if(data == 0){
		var rows = COM_CTGR.filter(function(item){
			if(item.parentCtgCd == entity.ctgCd){
				return item
			}
		});
		if(rows != null && rows.length>0){
			for(var i=0; i<rows.length; i++){
				var item = rows[i];
				drawField.push('<li><button type="button" onclick=changeLectureField("' + item.ctgCd + '");>' + item.ctgNm +'</button></li>');
			}
		}
		$('#divLectureFielld1').html(drawField.join(''));
		$('#divLectureFielld2').html('');
	
	} 
	else if(data == 1){
		var rows = COM_CTGR.filter(function(item){
			if(item.parentCtgCd == entity.ctgCd){
				return item
			}
		});
		if(rows != null && rows.length>0){
			for(var i=0; i<rows.length; i++){
				var item = rows[i];
				drawField.push('<li><button type="button" onclick=changeLectureField("' + item.ctgCd + '");>' + item.ctgNm +'</button></li>');
			}
		}
		$('#divLectureFielld1').html('');
		$('#divLectureFielld2').html(drawField.join(''));
	}
}
/*지역선택시 해당 지역에 강습정보 조회처리 함수*/
function onClickArea(data){
	console.log('onClickArea=='+data);
	var searchTab = 'AREA';
	areaCd = data;
	if(data = undefined || data == null || data == ''){
		searchTab = null;
		areaCd = null;
	}
	$('#UL_MAIL_AREA_LECTURE_LIST').html('');
	$.ajax({
		url : '/web/edc/program/listMain2Ajax',
		data : {recordCountPerPage:5, firstIndex:0, usePagingYn:'Y', searchTab:searchTab, areaCd:areaCd},
		type : "GET",
		dataType : 'json',
		cache : false,				
		success : function(data, textStatus, jqXHR) {
    		const obj = JSON.parse(data);
    		if(obj != undefined &&  obj != null && obj.result == true){
    			var items = obj.data.contents;
    			var itemsArray = [];
    			if(items.length>0){
        			for(var i=0; i<items.length; i++){
        				var item = items[i];
        				var org_name = '[' + item.org_name +'] ';
        				
        				if(areaCd != -1){
        					org_name = '';
        				}
        				
        				itemsArray.push(`
        				         <li class="region-item">
        				           <p class="receive-after">`+item.edc_status+`</p>
        				           <div class="tit">
        				             <a href="/web/edc/program/`+item.edc_prgm_no+`" class="truncate-1">` + org_name + item.edc_prgm_nm+`</a>
        				             <span class="data">`+getDateInfo(item.edc_rsvn_sdate,'.')+`~`+getDateInfo(item.edc_rsvn_edate,'.')+`</span>
        				           </div>
        				         </li>							
        				`);		
        			}
    			}
    			else{
    				itemsArray.push(`
    				         <li class="region-item">
    				           		등록된 정보가 없습니다.
    				         </li>							
    				`);	
    			}
    		}
    		else{
    		}
			$('#UL_MAIL_AREA_LECTURE_LIST').html(itemsArray.join(''));    		
		},
		error : function(request, error, jqXHR) {
			console.log("목록조회 실패 " + error);
		}
	});
}
onClickArea(-1);

changeFeildTab(0);

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

function goTotalSearch(SearchType, Code, CodeNm){
	console.log('goTotalSearch');
	var params = 'searchTab='+SearchType;
	/*분야선택*/
	if(SearchType == 'CATE'){
		params = params + '&CtgCd='+Code;
	}
	/*교육대상선택(나이)*/
	else if(SearchType == 'TARGET'){
		params = params + '&edcTargetinfo='+Code;
	}
	/*시간대별*/
	else if(SearchType == 'TIME'){
		params = params + '&hourbandGbn='+Code;
	}
	/*기관별*/
	else if(SearchType == 'ORGAN'){
		params = params + '&orgLtype=&orgMtype='+Code;
	}
	document.location.href='/web/edc/program/list?'+params;
}
/*메인 강좌리스트*/
function mainLectureList(){
	$('#DIV_MAIN_LECTURE_LIST').html('');
	$.ajax({
		url : '/web/edc/program/listMain2Ajax',
		data : {recordCountPerPage:8, firstIndex:0, usePagingYn:'Y'},
		type : "GET",
		dataType : 'json',
		cache : false,				
		success : function(data, textStatus, jqXHR) {
    		const obj = JSON.parse(data);
    		if(obj != undefined &&  obj != null && obj.result == true){
    			var items = obj.data.contents;
    			drawMainList(items)    			
    		}
		},
		error : function(request, error, jqXHR) {
			console.log("목록조회 실패 " + error);
		}
	});
	
	
}
function drawMainList(items){
	var itemsArray = [];
	for(var i=0; i<items.length; i++){
		var item = items[i];
		var filePath = '/web/resources/images/common/no_img.png';
		var no_img = '/web/resources/images/common/no_img.png';
		if(item.edc_img_filenm != undefined && item.edc_img_filenm != null && item.edc_img_filenm.length>0){
			filePath = 'http://211.188.68.145/web/common/file/view/' + item.edc_img_path + item.edc_img_filenm + '?originName='+item.edc_img_origin;
		}
		itemsArray.push(`
                <div class="swiper-slide">
                <a href="/web/edc/program/`+item.edc_prgm_no+`" class="slide">
                  <p class="img">
                  <img src="` + filePath + `" alt="`+item.edc_prgm_nm+`" onerror="/web/resources/images/common/no_img.png"/>
                  </p>
                  <h4>`+item.edc_prgm_nm+`</h4>
                </a>
              </div> 							
		`);		
	}
	$('#DIV_MAIN_LECTURE_LIST').html(itemsArray.join(''));
	mainLectureListSwiper();
}
function mainLectureListSwiper(){
    var interleaveOffset = 0.5;
    var mainVisual = new Swiper(".vis_slider .swiper-container", {
      slidesPerView: "auto",
      loop: true,
      // loopedSlides: 3,
      speed: 1000,
      spaceBetween: 24,
      grabCursor: true,
      watchSlidesProgress: true,
      mousewheelControl: true,
      keyboardControl: true,
      autoplay: {
        delay: 5000,
      },
      pagination: {
        el: ".vis_slider .swiper-pagination" /*12.17 jhj 수정*/,
        type: "fraction",
      },
      navigation: {
        nextEl: ".vis_slider .next",
        prevEl: ".vis_slider .prev",
      },
      breakpoints: {
        500: {
          slidesPerView: 2,
          spaceBetween: 24,
        },
        1240: {
          slidesPerView: 5,
          spaceBetween: 24,
        },
      },
      on: {
        progress: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            var slideProgress = swiper.slides[i].progress;
            var innerOffset = swiper.width * interleaveOffset;
            var innerTranslate = slideProgress * innerOffset;
            /*swiper.slides[i].querySelector(".slide").style.transform =
						"translate3d(" + innerTranslate + "px, 0, 0)";*/
          }
        },
        slideChangeTransitionStart: function (slider) {
          $(".swiper-progress").removeClass("full");
        },
        slideChangeTransitionEnd: function (slider) {
          $(".swiper-progress").addClass("full");
        },
        touchStart: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = "";
          }
        },
        setTransition: function (speed) {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = speed + "ms";
            swiper.slides[i].querySelector(".slide").style.transition =
              speed + "ms";
          }
        },
      },
    });

    $(".vis_slider .swiper-slide a").focusin(function () {
      mainVisual.slideTo($(this).parent().index(), 1000);
      return false;
    });

    $(".vis_slider .play-stop").click(function () {
      $(this).toggleClass("play");
      if ($(this).hasClass("play")) {
        mainVisual.autoplay.stop();
      } else {
        mainVisual.autoplay.start();
      }
    });
}

$(document).ready(function(){
	mainLectureList();	
});
	


</script>    