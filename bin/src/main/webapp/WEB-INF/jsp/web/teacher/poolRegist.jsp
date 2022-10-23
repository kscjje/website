<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$(document).ready(function(){

	});
	function fn_save(){
		var f = document.instrPoolVO;

		if(f.targetArea.value == ""){
			alert("강의 가능지역을 입력해 주세요.");
			f.targetArea.focus();
			return;
		}
		if(f.lectrField.value == ""){
			alert("강의 과목을 입력해 주세요.");
			f.lectrField.focus();
			return;
		}
		if(f.selfintrcn.value == ""){
			alert("강의 설명을 입력해 주세요.");
			f.selfintrcn.focus();
			return;
		}
		if(f.licenseEtc.value == ""){
			alert("자격증/이력사항을 입력해 주세요.");
			f.licenseEtc.focus();
			return;
		}

		var msg = "강사신청을 등록하시겠습니까?";
		<c:if test="${mode eq 'modify'}">
		 	msg = "강사신청을 수정하시겠습니까?";
		</c:if>

		if(confirm(msg)){
			f.submit();
		}

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
            <h3 class="pageTit-title">강사신청</h3>
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
                <a href="javascript:void(0)" title="이동" class="pageTit-link">강사신청</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="sub">
      <form:form commandName="instrPoolVO" name="instrPoolVO" method="post" action="./${actionPage}" enctype="multipart/form-data">
      <form:hidden path="proflImageid" />

	        <div class="instructor-group">
	          <div class="myTable">
	            <div class="myTable-inner">
	              <div class="myTable-wrap">
	                <h3 class="myTable-title">01.기본정보</span>
	                </h3>
	                <table>
	                  <colgroup>
	                    <col width="10%">
	                    <col width="40%">
	                    <col width="10%">
	                    <col width="40%">
	                  </colgroup>
	                  <tbody>
	                    <tr>
	                      <th scope="row">강사명(아이디)</th>
	                      <td colspan="3"><c:out value="${instrPoolVO.memNm }" /></td>
	                    </tr>
	                    <tr>
	                      <th scope="row">성별</th>
	                      <td colspan="3">
							<c:choose>
								<c:when test="${reqPoolUserInfo.gender eq '1' }">
								남성
								</c:when>
								<c:when test="${reqPoolUserInfo.gender eq '2' }">
								여성
								</c:when>
								<c:otherwise>
								-
								</c:otherwise>
							</c:choose>
							</td>
	                    </tr>
	                    <tr>
	                      <th scope="row">연락처</th>
	                      <td colspan="3">
							<div class="radio">
	                          <span>
	                            <form:radiobutton path="eduinfoOpenyn" value="Y"/>
	                            <label for="eduinfoOpenyn1">공개</label>
	                          </span>
	                          <span>
	                            <form:radiobutton path="eduinfoOpenyn" value="N"/>
	                            <label for="eduinfoOpenyn2">비공개</label>
	                          </span>
	                          
								<c:choose>
									<c:when test="${empty instrPoolVO.hp}">등록된 휴대폰 번호 없음</c:when>
									<c:otherwise><hisco:HpPrint hp="${instrPoolVO.hp}"/></c:otherwise>
								</c:choose>	                          
	                        </div>	                      
							
							<br/>※ 휴대폰번호 변경시 회원정보를 수정해 주세요.
						  </td>
	                    </tr>
	                    <tr>
	                      <th scope="row">E-mail</th>
	                      <td colspan="3">
							<c:choose>
								<c:when test="${empty instrPoolVO.email}">등록된 이메일 없음</c:when>
								<c:otherwise><c:out value="${instrPoolVO.email }" /></c:otherwise>
							</c:choose>	 
							<br/>※ 이메일 변경시 회원정보를 수정해 주세요.  	                      
						  </td>
	                    </tr>
	                    <tr>
	                      <th scope="row">개인홈피/블로그</th>
	                      <td colspan="3">
		                      <div class="searchBar-group input-group w100">
								<label for="proflEtc" class="inp7_4_20 visually-hidden"></label>
	                            <input type="text" id="proflEtc" name="proflEtc" style="width:100%;" class="form-control" placeholder="개인홈피/블로그"  maxlength="100"/>                      
		                      </div>
						  </td>
	                    </tr>
	                  </tbody>
	                </table>
	              </div>
	            </div>
	          </div>
	        </div>

	        <div class="instructor-group">
	          <div class="myTable">
	            <div class="myTable-inner">
	              <div class="myTable-wrap">
	                <h3 class="myTable-title">02.강의정보</span>
	                </h3>
	                <table>
	                  <colgroup>
	                    <col width="10%">
	                    <col width="40%">
	                    <col width="10%">
	                    <col width="40%">
	                  </colgroup>
	                  <tbody>
	                  <!-- 
	                    <tr>
	                      <th scope="row">강사구분<i class="star">*</i>
	                      </th>
	                      <td colspan="3">
	                        <div class="radio">
	                          <span>
	                            <input name="sub07_4_1" id="inp7_4_1" type="radio" checked>
	                            <label for="inp7_4_1">재능나눔강사</label>
	                          </span>
	                          <span>
	                            <input name="sub07_4_1" id="inp7_4_2" type="radio">
	                            <label for="inp7_4_2">전문강사</label>
	                          </span>
	                        </div>
	                      </td>
	                    </tr>
	                     -->
	                    <tr>
	                      <th scope="row">활동 가능지역<i class="star">*</i>
	                      </th>
	                      <td colspan="3">
	                      
	                        <label for="comment_01" class="visually-hidden"></label>
							 <div class="searchBar-group input-group w100">
							 	<label for="targetArea" class="visually-hidden"></label>
							  	<form:input path="targetArea" style="width:100%;" title="강의 가능지역" placeholder="강의 가능지역을 입력해 주세요." maxlength="100"/>
	                          </div>
	                          <br/>※ 실제 교육가능한 남양주시에 00동을 나열하세요.
	                          <br/> 남양주시전체,금곡동,다산1동,다산2동,별내동,양정동,평내동,호평동,별내면,수동면,조안면,퇴계원면,오남읍,와부읍,진건읍,진접읍,화도읍
	                      </td>
	                    </tr>

	                    <tr>
	                      <th scope="row">강의과목<i class="star">*</i>
	                      </th>
	                      <td colspan="3">
	                        <label for="comment_01" class="visually-hidden"></label>
							 <div class="searchBar-group input-group w100">
							 	<label for="lectrField" class="visually-hidden"></label>
							  	<form:input path="lectrField" title="강의과목" placeholder="강의과목을 적어주세요." class="form-control" style="width:100%;" maxlength="100"/>
	                          </div>
	                          <br/>※강좌과목이 2개이상 경우 “/“ 구분하여 연이어 기재하세요. (예 : 힐링 요가/필라테스)
	                      </td>
	                    </tr>
	                    <tr>
	                      <th scope="row">강의설명  <i class="star">*</i>
	                      </th>
	                      <td colspan="3">
	                        <label for="selfintrcn" class="visually-hidden"></label>
	                        <form:textarea path="selfintrcn" title="강의설명" placeholder="위의 과목에 대한 구체적 내용을 적어주세요. 과목이 여러개인 경우도 여기서 모두 적어주세요" maxlength="2000"/>
	                      </td>
	                    </tr>
	                    
	                    <tr>
	                      <th scope="row">강의 계획서</th>
						<td colspan="3">
	                      <div class="myTable-file w_box w100">
	                        <input type="text" id="inp01_3"  style="max-width:100%;" placeholder="강의계획서가 있을 경우 첨부해 주세요." readonly class="inp02_box">
	                        <input type="file" id="file_1" id="file_1" style="display: none">
	                        <label for="file_1" tabindex="0" class="label02_5_3 black" style="padding: 0 22px;max-width: 200px;width: 160px;text-align: center;">계획서 첨부</label>
	                      </div>
	                      
							<c:if test="${!empty planFile}">
			                      <div class="myTable-name">
			                        <button class="btn delete_btn" title="삭제"></button>
									<a href="<tags:UploadFileUrl filePath="${planFile.filePath}" fileName="${planFile.fileName }" originName="${planFile.orginFileName }"/>" class="down_txt">		                        
			                          <em class="file_ico"></em><c:out value="${planFile.orginFileName }"/>
			                        </a>
			                      </div>
			                      <p>※ 첨부 가능 확장자 : docx, xlsx, pptx, hwp, txt, pdf, jpg, png, gif, bmp, tif / 첨부 가능 총 용량 : 50MB / 첨부 가능 개별 용량 : 5 MB</p>
							</c:if>
	                    </td>
	                    </tr>	  
	                    <tr>
	                      <th scope="row">강좌 대표사진</th>
						<td colspan="3">

	                      <div class="myTable-file w_box w100">
	                        <input type="text" id="inp01_4" style="max-width:100%;" placeholder="대표사진을 첨부해 주세요." readonly class="inp02_box">
	                        <input type="file" name="file_2" id="file_2" class="file02" accept="image/*"  style="display: none" >
	                        
	                        <label for="file_1" tabindex="0" class="label02_5_3 black" style="padding: 0 22px;max-width: 200px;width: 160px;text-align: center;">사진첨부</label>
	                      </div>
						                      ※ 최적화 사이즈 : 가로 492 픽셀 , 가로 619 픽셀
	                      
							<c:if test="${!empty planFile}">
			                      <div class="myTable-name">
			                        <button class="btn delete_btn" title="삭제"></button>
									<a href="<tags:UploadFileUrl filePath="${planFile.filePath}" fileName="${planFile.fileName }" originName="${planFile.orginFileName }"/>" class="down_txt">		                        
			                          <em class="file_ico"></em><c:out value="${planFile.orginFileName }"/>
			                        </a>
			                      </div>
			                      <p>※ 첨부 가능 확장자 : docx, xlsx, pptx, hwp, txt, pdf, jpg, png, gif, bmp, tif / 첨부 가능 총 용량 : 50MB / 첨부 가능 개별 용량 : 5 MB</p>
							</c:if>
	                    </td>
	                    </tr>
						<tr>
	                      <th scope="row">자격증/이력사항 <i class="star">*</i>
	                      </th>
	                      <td colspan="3">
	                        <label for="licenseEtc" class="visually-hidden"></label>
	                        <form:textarea path="licenseEtc" title="자격증/이력사항" placeholder="자격증이나 이력사항 및 활동내역을 자유롭게 기재하세요. (년도 별 활동내용도 좋습니다)"  maxlength="2000"/>
	                      </td>
	                    </tr>
	                  </tbody>
	                </table>
	              </div>
	            </div>
	          </div>
	        </div>

	        <div class="badge-btn">
	          <a href="/web/teacher/poolList" class="black">목록</a>
	          <a href="javascript:fn_save()" class="green">신청</a>
	        </div>
	        
        </form:form>
        
      </div>
    </main>
