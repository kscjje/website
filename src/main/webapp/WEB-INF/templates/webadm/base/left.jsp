<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	//adminRoot is declared in "/WEB-INF/templates/webadm/base/header.jsp"

	String strContextPath = request.getContextPath() + adminRoot;
	com.hisco.cmm.object.UserSession adminVO = (com.hisco.cmm.object.UserSession)session.getAttribute(com.hisco.cmm.util.Config.USER_SESSION);
	java.util.List<egovframework.com.sym.mnu.mpm.service.MenuManageVO> menuList = adminVO.getAdminMenuList();

	String selectedMenuNo = (String)request.getAttribute("SELECTED_MENU_NO");
	//System.out.println("WEBADM selectedMenuNo = " + selectedMenuNo);

	egovframework.com.cmm.LoginVO user = (egovframework.com.cmm.LoginVO) egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser();

	if (!request.getRequestURI().endsWith(adminRoot)) {
%>

<c:set var="adminVO" value="<%=user%>" />

<!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <!--
      <li class="nav-item d-none d-sm-inline-block">
        <a href="#" class="nav-link">Home</a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="#" class="nav-link">Contact</a>
      </li>
       -->
    </ul>

    <!-- SEARCH FORM -->
    <!--
    <form class="form-inline ml-3">
      <div class="input-group input-group-sm">
        <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
        <div class="input-group-append">
          <button class="btn btn-navbar" type="submit">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>
	 -->
    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
    	<li class="nav-item  d-sm-inline-block pr-2">
        	<a href="<%=strContextPath %>/myinfo/detail"><i class="fa fa-fw fa-pen "></i> <c:out value="${adminVO.name}" /> (<small><c:out value="${adminVO.userSe}" /></small>) </a>
         </li>

		<li class="nav-item ">
			<form id="logoutForm" name="logoutForm"  method="post" action="<%=strContextPath %>/egov_security_logout">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			    <button type="submit"  ><i class="fa fa-fw fa-sign-out-alt"></i> 로그아웃</button>
			</form>
        </li>
     </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-info">
    <!-- Brand Logo -->
    <div class="header navbar-light">
    	<h1 class="logo"><a href="javascript:menuToggle()">남양주시청 주민자치센터 평생교육포털</a></h3>
	</div>
    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar Menu -->
      <nav>
        <ul class="nav nav-pills nav-sidebar flex-column nav-flat" data-widget="treeview" role="menu" data-accordion="false">

          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->

<c:set var="upperMenuNo" value="-1" />
<c:set var="prevDepth"   value="0" />
<c:set var="closeTag"    value="false" />

<%
	int i = 0;
	for (egovframework.com.sym.mnu.mpm.service.MenuManageVO menu : menuList ) {
		String menuUrl = menu.getMenuUrl();
		if(menuUrl != null && !menuUrl.startsWith("http")) {
		    String newMenuUrl = configUtil.getAdminDynamicPath(request, menuUrl);
		    menuUrl = newMenuUrl;
		}
%>
	<c:set var="menu" value="<%=menu %>" />
	<c:set var="menuUrl" value="<%=menuUrl %>" />
	<c:choose>
		<c:when test="${menu.childCnt < 1}">
				<li class="nav-item">
			<c:choose>
				<c:when test="${fn:startsWith(menuUrl,'http')}">
	                <a href="<c:out value="${menuUrl}" />" class="pb-2 pt-2 nav-link <%if ( Long.toString(menu.getMenuNo()).equals(selectedMenuNo)) {%>active2<%}%>">
				</c:when>
				<c:otherwise>
	                <a href="<c:url value="${menuUrl}" />" class="pb-2 pt-2 nav-link <%if ( Long.toString(menu.getMenuNo()).equals(selectedMenuNo)) {%>active2<%}%>">
				</c:otherwise>
			</c:choose>
	                  <i class="fa fa-fw fa-caret-right fa-sm ml-2"></i>
	                  <p><c:out value="${menu.menuNm}" /></p>
	                </a>
	            </li>
		</c:when>
		<c:otherwise>
			<c:if test="${upperMenuNo >= 0 and upperMenuNo != menu.upperMenuNo}">
				<c:forEach begin="${menu.menuDepth}" end="${prevDepth}" step="1" var="i">
				</ul></li><!-- test ${prevDepth} ${menu.menuDepth} ${i}-->
				</c:forEach>
				<c:set var="closeTag" value="false" />
			</c:if>

			<c:set var="closeTag" value="true" />

			<li class="nav-item <c:if test="${menu.menuDepth == 1}"> menu-tree</c:if>">
	            <a href="#" class="pb-2 pt-2 nav-link parent-link">
	              <i class="fa fa-fw fa-bookmark"></i>
	              <p>
	              	<c:out value="${menu.menuNm}" />
	                <i class="right fas fa-angle-left"></i>
	              </p>
	            </a>

	            <ul class="nav nav-treeview" style="display: none;">

	            <c:set var="prevDepth" value="${menu.menuDepth}" />

		</c:otherwise>
	</c:choose>
	<c:set var="upperMenuNo" value="${menu.upperMenuNo}" />
<%
	}
%>

<c:if test="${closeTag}">
				</ul></li>
</c:if>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>

  <script>
  	 if ($('.nav-link.active2').parents("ul").parents("li").hasClass("menu-tree")) {
  		$('.nav-link.active2').parents("ul").parents("li").find(".parent-link").eq(0).addClass("active");
  		$('.nav-link.active2').parents("ul").parents("li").addClass("menu-open");

  		$('.menu-open').find("a.active").next().show();
  		$('.active2').parent().parent().show();

  	 };

  	 //$('.active2').parent().parent().show();
  	 $('.active2').css("color" , "#ffc107");

  	 var menuToggleFlag = false;

  	 function menuToggle(){
  		 if(menuToggleFlag){
  			$('.menu-tree').removeClass('menu-open');
  			$('.nav-item').removeClass('menu-open');
  			$('.menu-tree').find("ul").hide();

  			menuToggleFlag = false;
  		 }else{
  			 $('.menu-tree').addClass('menu-open');
  			 $('.nav-item').addClass('menu-open');
  			 $('.menu-tree').find("ul").show();

  			menuToggleFlag = true;

  		 }
  	 }

  </script>
<%
	}
%>