<%
 /**
  * @Class Name : pInfo.jsp
  * @Description : 개인정보 처리방침 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.10.27    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.10.27
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
			<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit mb15">개인정보 처리방침 </h3>
						<p class="desc mb70">국립중앙과학관은 「개인정보보호법」 제30조에 따라 정보주체의 개인정보를 보호하고<br class="pc"> 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다.</p>
						<!-- sel_box -->
						<div class="sel_box">
							<span>이 개인정보 처리방침은 <em id="boxDt">2019. 6. 5.</em>부터 적용됩니다.</span>
							<!-- sel -->
							<div class="sel w320" style="width:320px;">
								<label for="policy" class="hidden">개인정보처리방침 연도별 선택</label>
								<select id="policy" onchange="fn_select_policy(this.value)" title="시행일 선택">
									<option value="" selected="selected">현행 시행일자 : 2019년 6월 5일</option>
									<option value="2">2018. 3. 15 ~ 2019. 6. 4</option>
									<option value="3">2018. 3. 13 ~ 2018. 3. 14</option>
									<option value="4">2018. 2. 12 ~ 2018. 3. 13</option>
									<option value="5">2018. 1. 18 ~ 2018. 2. 11</option>
									<option value="6">2017. 6. 27 ~ 2018. 1. 17</option>
									<option value="7">2017. 4. 14 ~ 2017. 6. 27</option>
									<option value="8">2016. 12. 1 ~ 2017. 4. 13</option>
									<option value="9">2015. 7. 24 ~ 2016. 11. 30</option>
									<option value="10">2015. 6. 19 ~ 2015. 7. 23</option>
									<option value="11">2015. 1. 1 ~ 2015. 6. 18</option>
									<option value="12">2014. 4. 23 ~ 2014. 12. 31</option>
									<option value="13">2014. 2. 12 ~ 2014. 4. 22</option>
									<option value="14">2013. 7. 29 ~ 2014. 2. 11</option>
									<option value="15">2012. 6. 1 ~ 2013. 7. 28</option>
								</select>
							</div>
							<!-- //sel -->
						</div>
						<!-- //sel_box -->
						<div class="policy_wrap">
						</div>
						<!-- //policy_wrap-->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->
<script>

$(document).ready(function() {
	fn_select_policy('')
});

function fn_select_policy(val) {
	var number = val != '' ? val : '';
	$.ajax({
	    url: './policy',
	    data : { num : number },
	    type : "GET",
	    dataType : "html",
	  success: function(data) {
		  $('.policy_wrap').children().remove()
		  $('.policy_wrap').html(data)
		  $('#boxDt').text($('#dt').val())
	    },
	    error: function(data) {
	  	  alert("Server Error");
	    }
	  });
}
</script>