<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduOpenDetail.jsp
	 * @Description : 마울배움터 개설현황 상세 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.19
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
var NAME_OF_WEEK = ["일","월","화","수","목","금","토"];

var fn_weekText = function(ymd) {

	if(ymd.length == 10){
		ymd = ymd.substr(0,4) + ymd.substr(5,2) + ymd.substr(8,2);
	}
	var dt = new Date(Number(ymd.substr(0,4)), Number(ymd.substr(4,2)) -1, Number(ymd.substr(6,2)));
	var week = NAME_OF_WEEK[dt.getDay()];

	return week;
};
//분류 선택 후
var fn_select_category = function(comcd,ctgCd , obj){
	var nowCtgnm = $(obj).text();

	if(ctgCd.lastIndexOf("0000000") == "3"){
		//1depth
	}else if(ctgCd.lastIndexOf("0000") == "6"){
		//2depth
		var upper1 = ctgCd.substring(0,3) + "0000000";
		var upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text();

		nowCtgnm = upperCtgnm +" > " + nowCtgnm;
	}else{
		//3depth
		var upper2 = ctgCd.substring(0,6) + "0000";
		var upperCtgnm = $('input[name=listCtgCd][value='+upper2+']').parent().find("a").text();

		nowCtgnm = upperCtgnm +" > " + nowCtgnm;


		var upper1 = ctgCd.substring(0,3) + "0000000";
		upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text();
		nowCtgnm = upperCtgnm +" > " + nowCtgnm;

	}

	$('#comCtgNm').val(nowCtgnm);
	$('#CtgCd').val(ctgCd);

	$('#modal-category').modal("hide");

};

var fn_find_category = function(){

	$.ajax({
	      url: "../../comctgr/comCtgrListAjax.json",
	      type : "GET",
	      dataType : "json",
	      cache : false,
	      success: function(data) {
	        console.log(data);

	        var LIST = data.result;
	        $('#CTG_TABLE').find("tbody").html("");

	        if(LIST){
	        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
	        }
	      }
	});
};



var fn_save = function() {
	var form = jQuery("#tweduVO")[0];

	var msg = "수정하시겠습니까?";
	if(confirm(msg)){
		$.ajax({
		      url:  form.action +".json" ,
		      type : "POST",
		      data : $('#tweduVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}
};

var fn_delRow = function(obj){
  		var row = $(obj).parent().parent();

  		row.next().remove();
  		row.remove();

  		jQuery("#edu-plan-list tr td div.lect-planseq").each(function(idx, elem){
  			var clsNo = idx +1;
  			$(this).text(clsNo);
  		});
}

jQuery(document).ready(function(){
	$('.datetype').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

	jQuery(".datepicker ,.datetype").datepicker({
		"dateFormat":'yy-mm-dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});


	jQuery("#modal-category").on("show.bs.modal", function(){
		fn_find_category();
	});


   	jQuery("input[name=edcLimitAgeyn]").on("click",function(){
   		jQuery("input[name='edcTargetSage'], input[name='edcTargetEage']").prop("disabled", this.value=="Y"?false:true);
   	});

   	jQuery("#add-edu-plan-btn").on("click", function(){
   		var no = 1 ;
   		jQuery("#edu-plan-list tr td div.lect-planseq").each(function(idx, elem){
   			no++;
   		});

   		var html = "<tr><td style=\"vertical-align: middle;\" rowspan=\"2\">";
   		html += "<div class=\"lect-planseq\" style=\"text-align:center;font-size:14px;font-weight:bold;color:black\">"+no+"</div>";
   		html += "<button type=\"button\" onclick='fn_delRow(this)' class=\"btn btn-secondary del-edu-plan-btn btn-xs mt-1\"><i class=\"fa fa-trash\"> 삭제</i></button>";
   		html += "</td>";

   		html += "<td  style=\"background-color:#f4f6f9\">";
   		html += "<div class=\"input-group\" style=\"width:160px\" >";
   		html += "<input type=\"text\" name=\"lectDate\" class=\"form-control datepicker\" style=\"width:120px\" placeholder=\"YYYY-MM-DD\" value=''>";
   		html += "<span style=\"padding-left:10px\">&nbsp;&nbsp;</span>";
   		html += "</div>";
   		html += "</td>";

   		html += "<td  style=\"background-color:#f4f6f9\" colspan=\"4\">";
   		html += "<input type=\"text\" name=\"lectTitle\" class=\"form-control\" placeholder=\"수업주제\" maxlength=\"50\" value=''>";
   		html += "</td>";

   		html += "</tr>";


   		html += "<tr>";
   		html += "<td colspan=\"3\"><div>수업내용</div>";
   		html += "<textarea rows=\"2\" name=\"lectContents\" class=\"form-control\" placeholder=\"수업내용\" maxlength=\"500\"></textarea>";
   		html += "</td>";
   		html += "<td colspan=\"2\"><div>준비물/유의사항</div>";
   		html += "<textarea rows=\"2\" name=\"lectEtc\" class=\"form-control\" placeholder=\"준비물\" maxlength=\"500\"></textarea>";
   		html += "</td>";
   		html += "</tr>";

   		jQuery("#edu-plan-list").append(html);

   		$('input[name=lectDate]').change(function(){
   			var ymd = $(this).val().replace(/-/g,"");
   			$(this).next().text("(" + fn_weekText(ymd) + ")");
   		});

   		jQuery("input[name=lectDate]").datepicker({
   			dateFormat : 'yy-mm-dd',
   			prevText : '이전 달',
   			nextText : '다음 달',
   			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
   			monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
   			dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
   			dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
   			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ]
   		});

   	});


   	jQuery("#submit-btn").on("click", fn_save);

   	$('input[name=lectDate]').change(function(){
		var ymd = $(this).val().replace(/-/g,"");
		$(this).next().text("(" + fn_weekText(ymd) + ")");
	});
});

</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-12">
            <h1>마을배움터 개설현황</h1>
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<form:form commandName="tweduVO" name="tweduVO" action="./${tweduVO.edcPrgmid}" class="form-horizontal" method="POST" enctype="multipart/form-data">
<form:hidden path="edcPrgmid"/>
<form:hidden path="comcd"/>
<form:hidden path="edcRsvnsetSeq"/>
<form:hidden path="orgNo"/>
<form:hidden path="edcFeeType"/>
<form:hidden path="salamt"/>

<section class="content">
      <div class="container-fluid">
      	<div class="row">
      		<div class="col-md-12">
      			<div class="row mb-2">
					 <div class="col-2">
					 </div>
					 <div class="col-2 pt-1">
					 </div>
					 <div class="col-8" style="text-align:right">
						<button type="button" onclick="window.print();" class="btn  btn-primary btn-sm btn-flat">인쇄</button>
					</div>
		       </div>
		 		<div class="card card-primary card-outline">
		 			<div class="card-header">
		 				<h3 class="card-title">기본정보</h3>
		 			</div>
			     	<div class="card-body">
		     			<table class="table table-bordered text-nowrap">
			               	<colgroup>
								<col style="width:5%;">
								<col style="width:45%;">
								<col style="width:5%;">
								<col style="width:45%;">
							</colgroup>

							<tbody>
								<tr>
									<th>신청 회원명(ID)</th>
									<td class="left">${tweduVO.memNm}(${tweduVO.id})</td>
									<th>휴대폰번호</th>
									<td class="left">
										<hisco:HpPrint hp="${tweduVO.memHp}"/>
									</td>
								</tr>
								<tr>
									<th>입금계좌정보</th>
									<td class="left" colspan="3">
										<div class="input-group col-sm-3" style="float:left">
											<div class="input-group-prepend"><span class="input-group-text">예금주</span></div>
				                    		<form:input path="edcFeeAccNm" title="예금주" class="form-control" maxlength="10" />
				                    	</div>
										<div class="input-group col-sm-3" style="float:left">
											<div class="input-group-prepend"><span class="input-group-text">은행명</span></div>
				                    		<form:input path="edcFeeBnkNm" title="은행명" class="form-control" maxlength="20" />
				                    	</div>
				                    	<div class="input-group col-sm-6" style="float:left">
											<div class="input-group-prepend"><span class="input-group-text">계좌번호</span></div>
				                    		<form:input path="edcFeeAccno" title="계좌번호" class="form-control" maxlength="20" />
				                    	</div>
									</td>
								</tr>
								<tr>
									<th>프로그램명</th>
									<td class="left" colspan="3">
										<form:input path="edcPrgmnm" title="프로그램명" class="form-control " maxlength="100" />
		   								<div><form:errors path="edcPrgmnm" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th>대표이미지</th>
									<td class="left">
<c:choose>
	<c:when test="${tweduVO.edcPrgmid >0 }">
						                <div class="input-group mb-3">
				                    		<form:hidden path="edcImgFileid" />
						                  	<input type="text" class="form-control" readonly id="edcImgOrigin" value="<c:out value="${tweduVO.edcImgOrigin}"/>">
						                  	<div class="input-group-append">
						                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="edcImgOrigin" data-inputid="edcImgFileid">파일변경</span>
						                  	</div>
						                </div>
						                <div id="edcImgFileidPreview" class="preview">
						                	<c:if test="${!empty tweduVO.edcImgOrigin}"><img src="<tags:UploadFileUrl filePath="${tweduVO.edcImgPath}" fileName="${tweduVO.edcImgFilenm }" originName="${tweduVO.edcImgOrigin }"/>" style="height:50px;cursor:pointer"/></c:if>
						                </div>
	</c:when>
	<c:otherwise>
										<input type="file" name="file_1" id="egovfile_0" title="대표이미지" class="form-control"/>
	</c:otherwise>
</c:choose>
									</td>
									<th>강의계획서</th>
									<td class="left">
<c:choose>
	<c:when test="${tweduVO.edcPrgmid >0 }">
						                <div class="input-group mb-3">
				                    		<form:hidden path="edcPlanFileid" />
						                  	<input type="text" class="form-control" readonly id="edcPlanOrigin" value="<c:out value="${tweduVO.edcPlanOrigin}"/>">
						                  	<div class="input-group-append">
						                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="edcPlanOrigin" data-inputid="edcPlanFileid">파일변경</span>
						                  	</div>
						                </div>
						                <div id="edcPlanFileidPreview" class="preview"><c:if test="${!empty tweduVO.edcPlanOrigin}"><a href="<tags:UploadFileUrl filePath="${tweduVO.edcPlanPath}" fileName="${tweduVO.edcPlanFilenm }" originName="${tweduVO.edcPlanOrigin }"/>"><i class="fas fa-download"></i> 다운로드</a></c:if></div>
	</c:when>
	<c:otherwise>
		                    			<input type="file" name="file_3" id="egovfile_2" title="강의계획서" class="form-control"/>
	</c:otherwise>
</c:choose>
									</td>
								</tr>
								<tr>
									<th>프로그램 소개</th>
									<td class="left" colspan="3">
										<form:textarea path="edcPrgmintrcn" title="프로그램소개" class="form-control" maxlength="5000" rows="5"/>
									</td>
								</tr>
								<tr>
									<th>교육대상</th>
									<td class="left">
										<form:select path="edcTargetAgegbn" class="form-control">
			                    	 		<option value="0">::::선택::::</option>
			<c:forEach items="${targetType}" var="item" varStatus="status">
			                    	 		<form:option value="${item.cd }" label="${item.cdNm }" />
			</c:forEach>
			                    	 	</form:select>
			   							<div><form:errors path="edcTargetAgegbn" cssClass="error" /></div>
									</td>
									<th>프로그램 분야</th>
									<td class="left">
										<div class="input-group">
											<div class="input-group-prepend">
							                    <button class="btn btn-secondary" type="button" data-toggle="modal" data-target="#modal-category">
									              <i class="fas fa-search fa-fw"></i> 찾기
									            </button>
						           			</div>
						           			<form:input path="comCtgNm" title="분야" class="form-control" readonly="true"/>
						           		</div>
					           			<form:hidden path="CtgCd" />
				   						<div><form:errors path="CtgCd" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th>강사명</th>
									<td class="left">
										<form:input path="instrctrName" title="강사명" class="form-control"/>
			   							<div><form:errors path="instrctrName" cssClass="error" /></div>
									</td>
									<th>강의지역</th>
									<td class="left">
										<form:select path="areaCd" class="form-control">
				                    	 	<option value="0">::::선택::::</option>
				<c:forEach items="${areaList}" var="item" varStatus="status">
				                    	 	<form:option value="${item.areaCd }" label="${item.areaName }" />
				</c:forEach>
				                    	 </form:select>
				   						<div><form:errors path="areaCd" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th>강의장소</th>
									<td class="left" colspan="3">
										<form:input path="edcPlacenm" title="교육장소" class="form-control " maxlength="100" />
		   								<div><form:errors path="edcPlacenm" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th>강의정원</th>
									<td class="left" >
										<div class="input-group">
											<form:input path="edcPncpa" title="모집정원" class="form-control numberchk" maxlength="5" />
						                    <div class="input-group-append">
						                      <span class="input-group-text">명</span>
						                    </div>
						                 </div>
				   						<div><form:errors path="edcPncpa" cssClass="error" /></div>
									</td>
									<th>수업횟수</th>
									<td class="left" >
										<div class="input-group">
											<form:input path="edcClcnt" title="수업횟수" class="form-control numberchk" maxlength="5" />
						                    <div class="input-group-append">
						                      <span class="input-group-text">회</span>
						                    </div>
						                 </div>
									</td>
								</tr>
								<tr>
									<th>연령제한여부</th>
									<td class="left" >
										<div class="input-group">
											<div class="form-check col-sm-3">
						     					<form:radiobutton path="edcLimitAgeyn" cssClass="form-check-input" value="Y"/>
						     					<label for="edcLimitAgeyn1" class="form-check-label">제한함</label>
					     					</div>
					     					<div class="form-check col-sm-3">
					     						<form:radiobutton path="edcLimitAgeyn" cssClass="form-check-input" value="N"/>
					     						<label for="edcLimitAgeyn2" class="form-check-label">제한안함</label>
					     					</div>
					     				</div>
									</td>
									<th>신청제한연령</th>
									<td class="left" >
										<div class="input-group">
											<div class="col-sm-5 input-group">
												<input type="text" name="edcTargetSage" value="${tweduVO.edcTargetSage}" title="제한시작연령" class="form-control numberchk" maxlength="5" <c:if test="${tweduVO.edcLimitAgeyn ne 'Y' }">disabled</c:if> />
							                    <div class="input-group-append">
							                      <span class="input-group-text">세</span>
							                    </div>
						                    </div>
						                    <div class="col-sm-2 text-center" style="line-height:1.5;">~</div>
					     					<div class="col-sm-5 input-group">
												<input type="text" name="edcTargetEage" value="${tweduVO.edcTargetEage}" title="제한종료연령" class="form-control numberchk" maxlength="5" <c:if test="${tweduVO.edcLimitAgeyn ne 'Y' }">disabled</c:if> />
							                    <div class="input-group-append">
							                      <span class="input-group-text">세</span>
							                    </div>
						                    </div>
						                </div>

									</td>
								</tr>
								<tr>
									<th>교육기간</th>
									<td class="left" >
										<div class="input-group">
											<div class="col-sm-5">
								                    <form:input path="edcSdate"  title="시작일" maxlength="10" cssClass="form-control datetype"/>
						                    </div>
						                    <div class="col-sm-1">
													부터
						                    </div>
						                     <div class="col-sm-5">
								                    <form:input path="edcEdate"  title="종료일" maxlength="10" cssClass="form-control datetype"/>
						                    </div>
						                     <div class="col-sm-1">
													까지
						                    </div>
										</div>
									</td>
									<th>접수 오픈일시</th>
									<td class="left" >
										<div class="input-group">
											 <div class="col-sm-6">
												<form:input path="edcRsvnSdate"  title="시작일" maxlength="10" cssClass="form-control datetype"/>
											</div>
											<div class="col-sm-4">
						                    		<form:select path="edcRsvnStimeHour" cssClass="form-control">
						 <c:forEach var="i" begin="0" end="23">
						                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

						 </c:forEach>
						 								</form:select>
						 							<form:hidden path="edcRsvnStimeMin"	 value="00"/>
						 							<form:hidden path="edcRsvnEdate" />
						 							<form:hidden path="edcRsvnEtimeHour" />
						 							<form:hidden path="edcRsvnEtimeMin"	 value="00"/>
								            </div>
										</div>
									</td>
								</tr>
								<tr>
									<th>교육요일</th>
									<td class="left" >
										<c:forEach items="${tweduVO.edcDaysList}" var="item2" varStatus="status">
				     						<div class="form-check mr-3" style="display:inline-block">
				     						  <input type="checkbox" class="form-check-input edcDaygbn" id="edcDays${status.index}" name="edcDays" value="${item2.edcDaygbn}" <c:if test="${item2.dayChk eq item2.edcDaygbn}">checked</c:if> />
					                          <label for="edcDays${status.index}" class="form-check-label">${item2.edcDaygbnNm}</label>
					                        </div>
	     								</c:forEach>
									</td>
									<th>교육시간</th>
									<td class="left" >
										<div class="input-group">
											<div class="col-sm-2">

					                    		<form:select path="edcStimeHour" cssClass="form-control">
					 <c:forEach var="i" begin="0" end="23">
					                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

					 </c:forEach>
					 								</form:select>
							                </div>
					                    	 <div class="col-sm-2">
					                    		<form:select path="edcStimeMin" cssClass="form-control">
					 <c:forEach var="i" begin="0" end="50" step="10">
					                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

					 </c:forEach>
					 								</form:select>
							                </div>
					                    <div class="col-sm-1">부터</div>
					                     <div class="col-sm-2">
					                    		<form:select path="edcEtimeHour" cssClass="form-control">
					 <c:forEach var="i" begin="0" end="23">
					                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

					 </c:forEach>
					 								</form:select>
					                     </div>
					                     <div class="col-sm-2">
					                    		<form:select path="edcEtimeMin" cssClass="form-control">
					 <c:forEach var="i" begin="0" end="50" step="10">
					                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

					 </c:forEach>
					 								</form:select>
							                </div>

					                     <div class="col-sm-1">까지</div>
					                     </div>
									</td>
								</tr>
								<tr>
									<th>재료비/유의사항</th>
									<td class="left" colspan="3">
										<form:textarea path="edcTchmtrGuide" title="재료비/유의사항" class="form-control" maxlength="5000" rows="5"/>
									</td>
								</tr>
								<tr>
									<th>교육문의연락처</th>
									<td class="left" >
										<form:input path="edcGuideTelno" title="교육문의연락처" class="form-control " maxlength="20" />
		   								<div><form:errors path="edcGuideTelno" cssClass="error" /></div>
									</td>
									<th>승인상태</th>
									<td class="left" >
										<form:select path="edcPrg" class="form-control">
			<c:forEach items="${apprvType}" var="item" varStatus="status">
			                    	 		<form:option value="${item.cd }" label="${item.cdNm }" />
			</c:forEach>
			                    	 	</form:select>
			   							<div><form:errors path="edcPrg" cssClass="error" /></div>
									</td>
								</tr>
								<tr>
									<th>노출여부</th>
									<td class="left" colspan="3">
										<div class="input-group">
											<div class="form-check col-sm-1">
						     					<form:radiobutton path="edcOpenyn" cssClass="form-check-input" value="Y"/>
						     					<label for="edcOpenyn1" class="form-check-label">공개</label>
					     					</div>
					     					<div class="form-check col-sm-1">
					     						<form:radiobutton path="edcOpenyn" cssClass="form-check-input" value="N"/>
					     						<label for="edcOpenyn2" class="form-check-label">비공개</label>
					     					</div>
					     				</div>
									</td>
								</tr>
								<tr>
									<th>등록일</th>
									<td class="left">${tweduVO.reguser} | <fmt:formatDate value="${tweduVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
									<th>수정일</th>
									<td class="left">${tweduVO.moduser} | <fmt:formatDate value="${tweduVO.moddate}" pattern="yyyy.MM.dd HH:mm"/></td>
								</tr>
						   </tbody>
						  </table>
			     	</div>

			      </div>
      		</div>
      		<div class="col-md-12">
		 		<div class="card card-primary card-outline">
		 			<div class="card-header">
		 				<h3 class="card-title">학습계획서</h3>
		 			</div>
			     	<div class="card-body">
		     			<div class="row">
		     				<div class="col-12">
		     					<table style="width:100%" class="table table-bordered">
									<colgroup>
										<col style="width:7%;" />
										<col style="width:20%;" />
										<col style="width:*;" />
										<col style="width:20%;" />
										<col style="width:20%;" />
										<col style="width:10%;" />
									</colgroup>
									<thead>
										<tr>
											<th>회차</th>
											<th>수업일자</th>
											<th colspan="3">수업주제</th>
											<th><button type="button" class="btn btn-primary btn-sm" id="add-edu-plan-btn"><i class="fa fa-plus"> 추가</i></button></th>
										</tr>
									</thead>


									<tbody id="edu-plan-list">
<c:forEach items="${tweduVO.edcPlanList}" var="item" varStatus="status">
										<tr>
											<td style="vertical-align: middle;" rowspan="2">
												<div class="lect-planseq" style="text-align:center;font-size:14px;font-weight:bold;color:black">${item.edcClassNo}</div>
												<button type="button"  onclick='fn_delRow(this)' class="btn btn-secondary del-edu-plan-btn btn-xs mt-1"><i class="fa fa-trash"> 삭제</i></button>
											</td>

											<td style="background-color:#f4f6f9">
												<div class="input-group" style="width:160px" >
													<input type="text" name="lectDate" class="form-control datepicker" style="width:120px" placeholder="YYYY-MM-DD" value="${item.edcDate}">
													<span style="padding-left:10px">(${item.weekName })</span>
												</div>
											</td>

											<td style="background-color:#f4f6f9" colspan="4">
												<input type="text" name="lectTitle" class="form-control" placeholder="" maxlength="50" value="${item.edcTitle}">
											</td>
										</tr>
										<tr>

											<td style="vertical-align: middle;" colspan="3">
												<div>수업내용</div>
												<textarea rows="2" name="lectContents" class="form-control" placeholder="" maxlength="500">${item.edcCnts}</textarea>
											</td>
											<td style="vertical-align: middle;" colspan="2">
												<div>준비물/유의사항</div>
												<textarea rows="2" name="lectEtc" class="form-control" placeholder="" maxlength="500">${item.edcEtccnts}</textarea>
											</td>
										</tr>
</c:forEach>

									</tbody>
								</table>
		     				</div>
		     			</div>
		     		</div>
		     		<div class="card-footer">
			     		<div class="form-group row">
		     				<div class="col-md-6">
		     					<a href="./list${searchQuery}" class="btn  form-control btn-secondary">목록</a>
		     				</div>
		     				<div class="col-md-6">
		     					<button type="button" class="btn btn-info form-control" id="submit-btn" <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }">disabled</c:if>>저장</button>
		     				</div>
		     			</div>
		     		</div>
		     	</div>
      		</div>
      	</div>
      </div>
</section>
</form:form>
     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-category" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">분야 선택하기</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <div class="modal fade" id="modal-teacher" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">강사 선택</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <div style="padding-bottom:10px;">
              		강사명 검색  : <input type="text" name="searchTeacher" id="searchTeacher" value="" />
              </div>

              <table class="table table-sm table-bordered" id="TCR_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
<c:if test="${tweduVO.edcPrgmid >0 }">
<script type="text/javascript">
	//업로드 가능 확장자
	var UPLOAD_EXT = 'JPG, JPEG, PNG, GIF, HWP, DOC, DOCX, XLS, XLSX, PDF, TXT, PPT, PPTX, ZIP';
	var UPLOAD_EXTS = UPLOAD_EXT.split(',');

 //클릭 이벤트
  $('.preview').find("img").click(function(){
	  window.open($(this).attr("src"));
  })
//파일 업로드 처리
	$('.fileupload_process').click(function(e){
		$('#image-file-upload').remove();

		var last_flag = 0;
		var form = $('<form id="image-file-upload" style="display: block; width: 1px; height: 0px; overflow: hidden;" method="post" enctype="multipart/form-data"><input type="file" name="file" value="" /><input type="submit" value="업로드" /></form>');
		var inputnm = $(this).data("inputnm");
		var inputid = $(this).data("inputid");
		var fileid = $('#'+inputid).val();

		$(document.body).append(form);

		// 파일 업로드 버튼 클릭 처리
		form.find('[type="file"]').change(function(e){
			var val = $(this).val();
			if(val == ''){
				alert('파일을 선택해 주세요.');
				return;
			}

			// 확장자 체크
			var ext = $.trim(val.indexOf('.') > -1 ? val.substring(val.lastIndexOf('.') + 1).toUpperCase() : '');
			if(ext == ''){
				alert('확장자가 없는 파일은 선택하실 수 없습니다.');
				return false;
			}else{
				var check = false;
				for(var i=0; i<UPLOAD_EXTS.length; i++)
				{
					if($.trim(UPLOAD_EXTS[i]) == ext)
					{
						check = true;
						break;
					}
				}
				if(!check){
					alert(ext + ' 확장자는 첨부하실 수 없습니다.\n* 첨부 가능 확장자 : '+ UPLOAD_EXT);
					return false;
				}
			}

			$('body').showLoading();

			// 폼 전송 처리
			form.ajaxSubmit({
				type : 'POST',
				url : "./edcFileUpload.json",
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'edcPrgmid' : "${tweduVO.edcPrgmid}",
							'comcd': "${tweduVO.comcd}"
						},
				dataType : 'json',
				headers : {
					"${_csrf.headerName}": "${_csrf.token}"
				},
				error : function()
				{
					alert('업로드 처리 중 오류가 발생하였습니다.');
					$('body').hideLoading();
				},
				success: function(data, statusText, xhr, $form)
				{
					form.empty().remove();
					console.log(data);

					$('body').hideLoading();

					if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {


			        	$('#'+inputnm).val(data.originFileName);
			        	$('#'+inputid).val(data.atchFileId);

			        	if(inputid == "edcPlanFileid"){
			        		//강의계획서
			        		alert("강의계획서가 변경되었습니다.");
			        		$('#'+inputid+'Preview').html("<a href='"+data.realFilePath+"'><i class=\"fas fa-download\"></i> 다운로드</a>");
			        	}else{
			        		alert("이미지가 변경되었습니다.");
			        		var img = document.createElement("img");
							img.setAttribute("src", data.realFilePath);
							img.style.height = "50px";
							img.style.cursor = "pointer";

							img.onclick = function(){
								window.open(data.realFilePath);
							}

							$('#'+inputid+'Preview').html("");
							$('#'+inputid+'Preview').append(img);
			        	}
			        }
				}
			});
		}).click();
	});
 </script>
</c:if>
</body>

