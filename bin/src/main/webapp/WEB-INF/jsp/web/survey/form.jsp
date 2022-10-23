<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : index.jsp
  * @Description : 설문 JSP
  * @Modification Information
  * @
  * @  수정일           수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2022.01.06  이윤호                 최초 생성
  *
  *  @author 이윤호
  *  @since 2022.01.06
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<style>
		/*radio*/
		input[type="radio"] {position:absolute;left:-9999px;width:0;height:0;border:0;overflow:hidden;}
		input[type="radio"] + label {display:inline-block;position:relative;line-height:25px;vertical-align:top;cursor:pointer; padding-left: 34px;padding-right:8px;color: #222;}
		input[type="radio"] + label:before {position:absolute;left:0;top:0;width:25px;height:25px;background: url(<c:url value="${webDir }"/>/resources/images/common/ico_check_w.png) no-repeat center;border-radius:4px;box-sizing:border-box;content:"";background-color: #ddd; }
		input[type="radio"]:checked + label:before {width:25px;height:25px; background-color: #004e9e; }
		input[type="radio"]:focus {border:1px solid #222;}
		input[type="radio"]:focus  + label:before{border:1px solid #222;}
		input[type="radio"] + label em { color: #e61b3c; }		
	</style>
	<script>
		var qList = [];
		
		$(document).ready(function () {
			console.log('qList:',qList);
		});
		
		
		function fn_save() {
			var chk = true;
			$.each(qList, function (k, v) {
				if(chk){
					if(v.type=='3001'){
						if(!($('#surveyResultVO').find('[name='+v.name+']').val())){
							//alert(v.no+'번 문항의 답변을 입력해주세요.');
							alert('질문에 대한 답변을 모두 채워주시기 바랍니다.');
							chk = false;
						}
					}
					else if(v.type=='0000'){
						console.log('0000:','pass');
					}
					else{
						if($('#surveyResultVO').find('[name='+v.name+']:checked').length<1){
							//alert(v.no+'번 문항의 답변을 선택해주세요.');
							alert('질문에 대한 답변을 모두 채워주시기 바랍니다.');
							chk = false;
						}
					}
				}
			});
			
			if(!chk) return ;
			
			if(confirm("설문 내용을 제출하시겠습니까?")){
				$('#surveyResultVO').submit();
			}
			
		}		
	</script>
</head>

<body>
			<div class="sub_visual">
				<div class="inner">
					<h2>알림마당</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			
			<div id="content" class="sub_content">
				<div class="sub_top">
					<a href="javascript:history.back();">뒤로가기 버튼</a>
					<h3>설문조사 작성</h3>
				</div>
				<!--//sub_top -->
<form:form commandName="surveyResultVO" name="surveyResultVO" action="./save" method="post" >
	<form:hidden path="qestnarId" value="${survey.qestnarId }"/>
	<form:hidden path="qestnarStdno" value="${stdrmng.qestnarStdno }"/>

				<div class="inform_area">
					<div class="img_con">
	<c:choose>
	 	<c:when test="${survey.qestnarImgfilnb!=null  && fn:length(survey.qestnarImgfilnb)>0}">
				 		<img src="<c:url value="${webDir}"/>/common/file/download?atchFileId=<c:out value="${survey.qestnarImgfilnb}"/>&fileSn=0" alt="첨부파일 이미지"  onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img.jpg'">
		</c:when>
		<c:otherwise>
						<img src="<c:url value="${webDir }"/>/resources/images/data/img_data11.jpg" alt="알림마당 설문조사 메인 이미지">
		</c:otherwise>
	</c:choose>
						
					</div>
					<h4><c:out value="${survey.qestnarName }"/></h4>
				<c:if test="${fn:length(survey.qestnarGuide)>0 }">
					<div class="ex_box">
						<c:out value="${fn:replace(survey.qestnarGuide , crlf , '<br/>')}" escapeXml="false" />
					</div>
				</c:if>
					<ul class="question_list">
	<c:set var="qIdx" value="0" />
	<c:choose>
	 	<c:when test="${questionList!=null  && fn:length(questionList)>0}">
			<c:forEach items="${questionList}" var="q" varStatus="status">
						<li>
							<input type="hidden" name="qestnsSeq" value="<c:out value="${q.qestnsSeq}" />"/> 
				<c:choose>
				 	<c:when test="${q.qestnsType eq '0000'}">
 						<% /* 요약 */ %>
				 		<c:if test="${fn:length(q.qestnsName) > 0  }">
				 			<p class="title"><c:out value="${q.qestnsName}" escapeXml="false" /></p>
				 		</c:if>
						<c:if test="${fn:length(q.itemList)>0 && fn:length(q.itemList[0].qestnarItemnm)>0}">
							<div class="ex_box">
								<i class="mark"></i><c:out value="${q.itemList[0].qestnarItemnm}" escapeXml="false" />
							</div>
			 			</c:if>
				 	</c:when>
				 	<c:when test="${q.qestnsType eq '3001'}">
				 		<% /* 주관식 */ %>
				 		<c:set var="qIdx" value="${qIdx+1 }" />
				 		<p class="title">${qIdx }. <c:out value="${q.qestnsName}" escapeXml="false" /></p>
				 		<textarea name="q<c:out value="${q.qestnsSeq}" />" placeholder="(내용을 작성해 주세요)"></textarea>
			 			<script>qList.push({no:'${qIdx }', name:'q<c:out value="${q.qestnsSeq}" />', type:'${q.qestnsType}'});</script>
				 	</c:when>
				 	<c:otherwise>
				 		<% /* 객관식(선택) */ %>
				 		<c:set var="qIdx" value="${qIdx+1 }" />
				 		<p class="title">${qIdx }. <c:out value="${q.qestnsName}" escapeXml="false" /></p>
				 		<div class="flexbox">
				 		<c:if test="${fn:length(q.itemList)>0}">
			 				<c:forEach items="${q.itemList}" var="qi" varStatus="qstatus">
			 				<div class="chk_box">
			 					<input type="radio" name="q<c:out value="${q.qestnsSeq}" />" id="c${qIdx }_${qstatus.index}"  value="${qi.qestnarItemseq }" >
			 					<c:choose>
				 					<c:when test="${q.qestnsType eq '2001'}">
			 					<label for="c${qIdx }_${qstatus.index}"><c:out value="${qi.qestnarItemnm }"/></label>
			 						</c:when>
			 						<c:otherwise>
			 					<label for="c${qIdx }_${qstatus.index}"><c:out value="${qi.qestnarItemnm }"/>(${qi.qestnarScore })</label>
			 						</c:otherwise>
			 					</c:choose>
			 				</div>
			 				</c:forEach>
				 			<script>qList.push({no:'${qIdx }', name:'q<c:out value="${q.qestnsSeq}" />', type:'${q.qestnsType}'});</script>
			 			</c:if>
			 			</div>
				 	</c:otherwise>
				 </c:choose>
				 		</li>
			</c:forEach>
	 	</c:when>
	 	<c:otherwise>
	 					<li>
							<div class="ex_box">
								<i class="mark"></i>등록된 문항이 없습니다.
							</div>
						</li>	 	
	 	</c:otherwise>
	 </c:choose>					
					</ul>
					<p class="last_ask">위의 내용을 기반으로 설문을 마치겠습니까? </p>
				</div>
				<!--// inform_area  -->
</form:form>
			
				<div class="btn_area">
					<a href="javascript:history.back();" class="btn_s1_c0">취소</a>
					<a href="javascript:fn_save()" class="btn_s1_c1">설문 제출</a>
				</div>
				<!--// btn_area  -->
			</div>
			<!--// sub_content  -->			

</body>