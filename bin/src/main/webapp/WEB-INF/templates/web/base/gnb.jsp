<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <!-- accessibility -->
  <script>
  
  	var menuArray = [
  		<c:forEach items="${menuList}" var="item" varStatus="status">
			{
				"menuNo":'${item.menuNo}',
				"menuOrdr":'${item.menuOrdr}',
				"menuNm":'${item.menuNm}',
				"menuUrl":'${item.menuUrl}',
				"upperMenuId":'${item.upperMenuId}',
				"upperMenuNo":'${item.upperMenuNo}',
				"useYn":'${item.useYn}',
				"mainYn":'${item.mainYn}',
				"frontGnbmenuyn":'${item.frontGnbmenuyn}',
				"menuDepth":'${item.menuDepth}',
			}
	           <c:if test="${!status.last}">    
	           ,    
	          </c:if>  		
		</c:forEach>    
  	];
  
  </script>
  
  
  <div id="accessibility">
    <h6 class="visually-hidden">건너뛰기 메뉴</h6>
    <ul>
      <li>
        <a class="visually-hidden-focusable" href="#gnb">주메뉴 바로가기</a>
      </li>
      <li>
        <a class="visually-hidden-focusable" href="#content">본문 바로가기</a>
      </li>
    </ul>
  </div>
  <!-- //accessibility -->

  <!-- wrap -->
  <div class="wrap">
    <!-- header -->
    <header class="header">
      <div class="header-wrap">
        <div class="top">
          <div class="inner">
            <h1 class="logo" title="로고">
              <a href="/" title="남양주시 주민자치센터">
                <img src="${requestScope.resourceContext}/images/layout/namyang_logo.png" alt="남양주시 주민자치센터 로고">
              </a>
            </h1>
            <form method="post">
            <div class="search-box">
	              <div class="search-list">
	                <button type="button" class="text-truncate">전체</button>
	                <ul class="search-item">
	                  <li>
	                    <button type="button">전체</button>
	                  </li>
	                  <li>
	                    <button type="button">문화교양</button>
	                  </li>
	                  <li>
	                    <button type="button">생활체육</button>
	                  </li>
	                  <li>
	                    <button type="button">어학</button>
	                  </li>
	                  <li>
	                    <button type="button">정보화</button>
	                  </li>
	                  <li>
	                    <button type="button">유아/아동</button>
	                  </li>
	                  <li>
	                    <button type="button">헬스</button>
	                  </li>
	                </ul>
	              </div>
	              <div class="input-wrap">
	                <label for="gnbSearchText" class="visually-hidden">검색</label>
	                <input type="text" id="gnbSearchText" class="srch-inp" placeholder="강좌 /강사명을 입력하세요.">
	              </div>
	              <button  type="button" class="btn-srch gnb-search-btn">검색</button>
            </div>
            </form>
            <!-- side -->
            <div class="utility">
              <div class="utility-menu">
                <!-- 로그인 -->


	<c:choose>
		<c:when test="${isLogin eq 'Y'}">
		
                <!-- 로그아웃 -->
                <ul class="utility-list">
                  		<span style="padding-top: 5px;padding-right: 7px;">${loginUserNm }님</span>                	
                  	<li class="utility-item">
                		<a href="javascript:doLogout()" class="loginOut" title="로그아웃">로그아웃</a>
              		</li>
              	</ul>		
		</c:when>
		<c:otherwise>
                <ul class="utility-list">
                  <li class="utility-item">
                    <a href="javascript:doLogin()" class="login" title="로그인">로그인</a>
                  </li>
                  <li class="utility-item">
                    <a href="/web/member/join/joinStep1" class="joinMember" title="회원가입">회원가입</a>
                  </li>
                  <!-- 
                  <li class="utility-item">
						<a href="<c:url value="/web/member/nonLogin"/>">비회원수강 현황</a>
                  </li>
 					-->
                </ul>
		</c:otherwise>
	</c:choose>              
              
              
                  <li class="utility-item">
                    <a href="#" class="alarm" title="알람">
                      <span>3+</span>
                    </a>
                    <!-- 알람설정 -->
                    <div class="preview">
                      <div class="preview-inner">
                        <div class="preview-wrap">
                          <div class="preview-top">
                            <p class="preview-title">알림</p>
                            <button class="preview-close">닫기</button>
                          </div>
                          <div class="preview-bottom">
                            <ul class="preview-list">
                              <li class="preview-item">
                                <a href="#" class="preview-link">
                                  <div class="preview-name">시스템 점검 안내</div>
                                  <p class="preview-desc">안정적인 서비스 제공을 위한 시스템 정기 점검을 실시합니다.</p>
                                  <span class="preview-data">2022-06-07 13:33</span>
                                </a>
                              </li>
                              <li class="preview-item">
                                <a href="#" class="preview-link">
                                  <div class="preview-name">상담문의 답변 완료</div>
                                  <p class="preview-desc">신청하신 강좌의 결제방법은 계좌이체입니다. 계좌이체로 결제를 한경우, 결제취소를 하셨다면, 환불요청은 별도로 필요하지
                                    않습니다.</p>
                                  <span class="preview-data">2022-06-07 13:33</span>
                                </a>
                              </li>
                              <li class="preview-item">
                                <a href="#" class="preview-link">
                                  <div class="preview-name">직무스터디 캠프 참여자 모집</div>
                                  <p class="preview-desc">커피 관련 직업군이 바리스타, 로스터, 테이스터 등으로 분화하고 다양해지면서 현장에서는 원리를 이해하고 응용하는 자질을
                                    갖추는 것이 중요해졌다.</p>
                                  <span class="preview-data">2022-06-07 13:33</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- //알람설정 -->
                  </li>
                </ul>
                <button class="btn-all pc" data-bs-toggle="modal" data-bs-target="#modalAllmenu" title="전체메뉴"></button>
                <button class="btn-menu mobile" title="메뉴"></button>
              </div>
            </div>
          </div>
        </div>
        <!-- // top -->
        
         <div id="gnb" class="gnb">
          <nav>
            <div class="gnb-inner">
              <ul class="gnb-list" id="divTopMenuList">
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </header>
    <!-- //header -->
 <script>
	var MENU_LIST = [];
	var MN_STEP01 = [];
	var MN_STEP02 = [];
	var MN_STEP03 = [];
 	if(menuArray != undefined && menuArray.length>0){
 		var depthOne = menuArray.filter(item => item.menuDepth == 1 && item.frontGnbmenuyn == 'Y');
 		//1메뉴처리
 		if(depthOne != undefined && depthOne.length>0){
 			for(var i=0; i<depthOne.length; i++){
 				var item1 = depthOne[i];
 				MN_STEP01 = [];
 				MN_STEP01.push('<li class="gnb-item">');
 				MN_STEP01.push('<a href="'+item1.menuUrl+'#">' + item1.menuNm + '</a>');
 				MN_STEP01.push('	<ul class="sitemap-list">');
 				//2차메뉴
 				var depthSecond = menuArray.filter(item => item.upperMenuNo == item1.menuNo && item.frontGnbmenuyn == 'Y');
 				MN_STEP02 = [];
				if(depthSecond != undefined && depthSecond.length>0){
					//주민자치센터 메뉴일경우
					if(i==0){
						MN_STEP02.push('<li class="sitemap-item">');
						var idx=0;
						for(var j=0; j<depthSecond.length; j++){
							var item2 = depthSecond[j];
							if(j==0){
								MN_STEP02.push('<a href="'+item2.menuUrl+'#" class="addition">' + item2.menuNm + '<i class="more"></i></a>');
								MN_STEP02.push('<ul class="item-list area">');
								MN_STEP02.push('<li id="aDepth-' + idx + '"><a href="'+item2.menuUrl+'#">' + item2.menuNm + '</a></li>');
							}
							else{
								MN_STEP02.push('<li id="aDepth-' + idx + '"><a href="'+item2.menuUrl+'#">' + item2.menuNm + '</a></li>');
							}
							
							var depthThird = menuArray.filter(item => item.upperMenuNo == item2.menuNo && item.frontGnbmenuyn == 'Y');
							if(depthThird != undefined && depthThird.length>0){
								var cssStyle = 'display:none';
								if(idx==0){
									cssStyle = 'display:block';
								}
								MN_STEP03.push(' <ul class="item-list" id="aDepthShow-' + idx + '" style="' + cssStyle + '">');
								for(k=0; k<depthThird.length; k++){
									var item3 = depthThird[k];
									MN_STEP03.push(' <li><a href="'+item3.menuUrl+'#">' + item3.menuNm + '</a></li>');
								}
								MN_STEP03.push(' </ul>');
							}
							idx++;
						}
						MN_STEP02.push('</ul>');
						MN_STEP02.push(MN_STEP03.join('')+'</li>');
					}
					else{
						for(var j=0; j<depthSecond.length; j++){
							var item2 = depthSecond[j];
							var depthThird = menuArray.filter(item => item.upperMenuNo == item2.menuNo && item.frontGnbmenuyn == 'Y');
							MN_STEP02.push('<li class="sitemap-item">');
							MN_STEP03 = [];
							if(depthThird != undefined && depthThird.length>0){
								if(i==0){
									
									MN_STEP02.push('<a href="'+item2.menuUrl+'#" class="addition">' + item2.menuNm + '<i class="more"></i></a>');
									MN_STEP02.push('<ul class="item-list area">');
								}
								else{
									MN_STEP02.push('<a href="'+item2.menuUrl+'#">' + item2.menuNm + '</a>');
									MN_STEP02.push('<ul class="item-list">');
								}
								for(var k=0; k<depthThird.length; k++){
									var item3 = depthThird[k];
									MN_STEP03.push('<li><a href="'+item3.menuUrl+'#">' + item3.menuNm + '</a></li>')
								}
								MN_STEP02.push(MN_STEP03.join('') + '</ul>');
							}
							else{
								MN_STEP02.push('<a href="'+item2.menuUrl+'#">' + item2.menuNm + '</a>');
							}
							MN_STEP02.push('</li>');
						}
					}
					
					
					

					
					
					
					
					
					
				}
				MN_STEP01.push(MN_STEP02.join(''));
 				MN_STEP01.push('	</ul>');
 				MN_STEP01.push('</li>');
 				
 				MENU_LIST.push(MN_STEP01.join(''));
 			}
 		}
 		
 		$('#divTopMenuList').html(MENU_LIST.join(''));	
 		
 		
 		function changeAgency(idx, menuNo){
 			alert('changeAgency');
 		}
 	}
 </script>