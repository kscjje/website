<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="com.hisco.cmm.util.Config"%>

<%
 /**
  * @Class Name : sysUserIpUpdt.jsp
  * @Description : IP 접속 차단 설정 관리
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------      --------    ---------------------------
  * @ 2021.06.09    전영석                  최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.09
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/webadm/base/header.jsp"%>

<script type="text/javascript" src="<c:url value="/webadm/validator"/>"></script>
<validator:javascript formName="sysUserVO" staticJavascript="false" xhtml="true" cdata="false"/>

<script type="text/javascript">

	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {

		var form = document.sysUserVO;

		var varIp = $("#ip").val().trim();
		$("#ip").val(varIp);

		if ((varIp === undefined) || (varIp == null) || (varIp == '')) {
			alert("IP 정보는 비울 수 없습니다.");
			$("#ip").focus();
			return false;
		}

		var msg = "등록하시겠습니까?";
		<c:if test="${mode eq 'edit'}">
			msg = "IP를 추가하시겠습니까?";
		</c:if>

		if (confirm(msg)) {
			form.submit();
		}

	}

	function fnDeleteIp(seq) {

		if (confirm("선택하신 IP 정보를 삭제할까요?")) {

			var form = document.sysUserVO;

			$("#mode").val("delete");
			$("#delSeq").val(seq);

			form.submit();
		}

	}

	$(document).ready(function() {



	})

</script>

<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>IP 접속 허용 설정 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

				<form:form commandName="sysUserVO" name="sysUserVO" action="./sysUserIpSave" class="form-horizontal" method="post">

				<input type="hidden" name="searchQuery"     value="<c:out value="${searchQuery}"/>"/>
				<input type="hidden" name="mode"    id="mode"    value="<c:out value='${mode}'/>">
				<input type="hidden" name="delSeq"  id="delSeq"  value="">

                <div class="card-body">

                  <!--
                  <div class="form-group row">
                    <label for="roleCd" class="col-sm-2 col-form-label">* 권한</label>
                    <div class="col-sm-10">
                      	<form:select path="groupId" title="권한" cssClass="form-control">
                      		<form:option value="0">슈퍼관리자</form:option>
							<form:options items="${authorList}" itemValue="groupId" itemLabel="groupNm"/>
						</form:select>
						<div><form:errors path="groupId" cssClass="error text-red" /></div>
                    </div>
                  </div>
                  -->

                  <div class="form-group row">
                    <label for="userId" class="col-sm-1 col-form-label">* 아이디</label>
                    <div class="col-sm-2">
						 <c:choose>
						 	<c:when test="${mode eq 'edit'}">
	 							<form:input path="userId" title="아이디" class="form-control" maxlength="50" readonly="true"/>
						 	</c:when>
						 	<c:otherwise>
			 					<div class="input-group">
			 						<form:input path="userId" title="아이디" class="form-control" maxlength="50" />
			 						<span class="input-group-append">
			                    		<button type="button" class="btn btn-info btn-flat" onclick="fn_id_check()">중복체크</button>
			                  		</span>
			                  	</div>
						 	</c:otherwise>
						 </c:choose>

	   					<div id="divModalResult"><form:errors path="userId" cssClass="error text-red" /></div>

                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="korName" class="col-sm-1 col-form-label">* 이름</label>
                    <div class="col-sm-2">
                      	<form:input path="korName" title="이름" class="form-control" maxlength="70" readonly="true" />
   						<div><form:errors path="korName" cssClass="error text-red" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="ip" class="col-sm-1 col-form-label">* IP</label>
                    <div class="col-sm-2">
                      	<form:input path="ip" title="IP" class="form-control" maxlength="70" />
   						<div><form:errors path="ip" cssClass="error text-red" /></div>
                    </div>
                    <div class="col-sm-2">
                    	<button type="button" class="btn btn-info" onclick="fn_save()">추가</button>
                    </div>
                  </div>

                  <div class="form-group row">

                  	<label for="thisIp" class="col-sm-1 col-form-label"></label>
                   	<c:forEach items="${sysUserIpList}" var="item" varStatus="status">
                   		<div class="col-sm-2">
                      		<input name="thisIp" title="IP 주소" class="form-control" maxlength="70" value="<c:out value='${item.ipInfo}' />" readonly="true" />
                      	</div>
	                    <div class="col-sm-2">
	                    	<button type="button" class="btn btn-info" onclick="fnDeleteIp('<c:out value="${item.ipSeq}" />'); return false;">삭제</button>
	                    </div>
                     </c:forEach>

                  </div>

                </div>

                <!--
                <div class="card-body">

					<c:forEach items="${sysUserIpList}" var="item" varStatus="status">

						<input name="ip" title="IP 주소" maxlength="50" readonly="true" value="<c:out value='${item.ipInfo}' />" />
						<a href="#" onclick="fnDeleteIp('<c:out value="${item.ipSeq}" />'); return false;">&nbsp;<font color='red'>X</font></a><br/><br/>

					</c:forEach>

                </div>
                -->

                <!-- /.card-body -->
                <div class="card-footer">

	                <!-- <button type="button" class="btn btn-default " onclick="./sysUserIpList">취소</button> -->
	                <a href="./sysUserIpList" class="btn btn-default ">취소</a>

	                <!--
				    <c:choose>
				    	<c:when test="${ mode eq 'edit' and commandMap.selectedMenu.updYn ne 'Y'}"><button type="button" class="btn btn-info float-right disabled">저장</button></c:when>
				    	<c:when test="${ commandMap.selectedMenu.insYn ne 'Y'}"><button type="button" class="btn btn-info float-right disabled">저장</button></c:when>
				    	<c:otherwise><button type="button" class="btn btn-info float-right" onclick="fn_save()">저장</button></c:otherwise>
				    </c:choose>
				    -->

                </div>
                <!-- /.card-footer -->
</form:form>

              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
        </div>
</section>


<!-- ****************************************************************************************************************************** -->

		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>