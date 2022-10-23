<%
 /**
  * @Class Name : loginSleep.jsp
  * @Description : 휴면계정 전환 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.22    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.22
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script>
	function fn_member_update() {
		if (confirm("휴면상태를 해제 하시겠습니까?")) {
			$('body').showLoading();

			 $.ajax({
			        type : "POST",
			        url  : './sleepAction',
			        data : $('#loginVO').serialize(),
			        dataType: "json",
			        beforeSend : function(xhr) {
			        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
			        },
			        success: function(data) {
			        	$('body').hideLoading();
			        	if (data != null) {
			        		if ((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')) {
			        		} else {
			        			if (data.ERROR_CODE == -1) {
			            			alert(data.ERROR_MSG);
			            			return;
			        			}
			        		}
			        	}
			        	fn_success(data);

			        }, error: function() {
			        	$('body').hideLoading();
			        	alert("Server Error.");
			        }
			    });
		}
	}
	function fn_success(data) {
		if (data.result.success) {
			$('#loginVO').attr("action" , "./loginSleepResult");
			$('#loginVO').submit();
        } else {
			alert(data.result.msg);
        }
	}
</script>
<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">휴면계정 안내</h3>
			<p class="desc mb70">회원님 반갑습니다! 국립중앙과학관입니다.</p>

			<!-- mypage_wrap -->
			<div class="mypage_wrap">
				<!-- signup -->
<form:form commandName="loginVO" name="loginVO" id="loginVO"  method="post">
<form:hidden path="uniqId" />
<form:hidden path="id" />
				<div class="signup3">
					<ul>
						<li>
							<p class="img"><img src="<%=strContext%>/images/sub/img_sleep.png" alt="휴면계정해지전이미지"></p>
							<dl>
								<dt>1년동안 로그인 기록이 없어 <br/><fmt:formatDate value="${loginVO.outDate}" pattern="yyyy년 MM월 dd"/> 일 부로 휴면계정으로 전환되었습니다.<br/></dt>
								<dd>고객님의 소중한 정보를 보호하고자 관련 법령에 따라 사용 이력이 1년 이상 경과한 계정에 대하여<br> 개인정보를 별도로 분리 보관하여 관리하고 있습니다.<br>휴면계정 해제를 원하시는 경우 "휴면상태 해제" 버튼을 클릭하시면 <br> 개인정보가 복구되어  계정을 다시 사용하실 수 있습니다.</dd>
							</dl>
						</li>
					</ul>


				</div>

				<!-- //signup -->

</form:form>
			</div>
			<!-- //mypage_wrap -->
			<!-- signup -->
			<div>
				<ul class="btn_area long">
					<li><a href="javascript:fn_member_update()" class="btn_ty_m3_c1">휴면상태 해제</a></li>
				</ul>
				<!-- //btn_area -->
			</div>

			<!-- //signup2 -->
		</div>
		<!-- //join_wrap -->

	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->