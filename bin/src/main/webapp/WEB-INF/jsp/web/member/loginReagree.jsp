<%
 /**
  * @Class Name : loginReagree.jsp
  * @Description : 약관 재동의 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.12    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.12
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
	<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">개인정보 재동의</h3>
			<p class="desc mb100">개인정보 동의후 1년 이 경과 하여 재동의 후 이용해 주세요.</p>
			<div class="join_wrap bor">
				<!-- signup2 -->
				<div class="signup2">
					<!-- agree_list -->
					<ul class="agree_list">
<c:forEach items="${termsList }" var="var" varStatus="stat">
	<c:if test="${var.stplatId ne '1005'  or (var.stplatId eq '1005' and param.type eq 'child') }">
						<li>
							<input type="checkbox" id="c${stat.count }" name="ness" class="agr">
							<label for="c${stat.count }"><c:out value="${var.stplatName }"/> <em>(필수)</em></label>
							<span class="view"><a href="javascript:showTerms('<c:out value="${var.stplatId}"/>')">내용보기</a></span>
						</li>
	</c:if>
</c:forEach>
					</ul>
					<!-- //agree_list -->
					<ul class="agree_choice">
						<li class="w100">
							<input type="checkbox" id="total_chk">
							<label for="total_chk">전체 약관 동의</label>
						</li>
					</ul>
				</div>
				<!-- //signup2 -->
			</div>
			<!-- //join_wrap -->
			<ul class="btn_area">
				<li><a href="javascript:fn_agree()" class="btn_ty_m_c1">확인</a></li>
			</ul>
			<!-- //btn_area -->
		</div>
		<!-- //inner -->
	</div>
	<!-- modal_base -->
<c:forEach items="${result }" var="var2" varStatus="stat2">
<div id="modal_<c:out value="${var2.stplatId }"/>" style="display:none">
	<div class="modal-dialog"  style="max-width:600px;min-height: 600px;">
		<div class="modal-content">
			<div class="modal-title">
				<h4><c:out value="${var2.stplatName }"/></h4>
				<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
			</div>
			<div class="modal-body">
				<p></p>
				<div class="modal-txt">
						<c:out value="${fn:replace(var2.stplatCn, crlf , '<br>')}" escapeXml="false" />
				</div>
			</div>
			<ul class="btn_area">
				<li><a href="javascript:;" data-dismiss="modal" class="btn_ty_m_c1">확인</a></li>
			</ul>
			<!-- //btn_area -->
		</div>
	</div>
</div>
</c:forEach>
	<!-- //sub_cont -->
<script type="text/javascript">
		//전체 약관 동의
		$( '#total_chk').click( function() {
			var chkBox = $(this);
			if (chkBox.is(':checked')) {
				$(".agree_list input[type='checkbox']").prop("checked",true);
			} else {
				$(".agree_list input[type='checkbox']").prop("checked",false);
			}
		});


		function fn_agree() {
			var isChecked = true;
			$.each($(".agree_list input[type='checkbox']") ,function(index,item) {
				if (!$(item).is(':checked')) {
					isChecked = false;
				}
			});

			if (!isChecked) {
				alert('약관에 모두 동의하여야 합니다.');
			} else {
				$('body').showLoading();
				 $.ajax({
				        type : "POST",
				        url  : './loginReagreeSave',
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
				        	if (data.result.success) {
				        		alert("정상적으로 처리 되었습니다.");
				        		var returnURL = "<c:out value="${param.returnURL}"/>";
				        		if (returnURL == "") returnURL = "/web/main";
				        		window.location.replace(returnURL);
				        	} else {
				        		alert(data.result.msg);
				        	}
				        }, error: function() {
				        	$('body').hideLoading();
				        	alert("Server Error.");
				        }
				    });
			}
		}

		function showTerms(no) {
			var html = $('#modal_'+no).html();
			$('#modalCommon').html(html);
			$('#modalCommon').modal();

		}
</script>