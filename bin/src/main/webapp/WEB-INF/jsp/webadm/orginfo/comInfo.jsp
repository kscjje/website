<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : comInfo.jsp
  * @Description : 운영사업자 정보 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ include file="/WEB-INF/templates/webadm/base/header.jsp"%>

<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>운영사업자 정보 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
<form:form commandName="comInfoVO" name="comInfoVO" action="./comInfoSave" class="form-horizontal" method="post">
       <div class="row">
          <div class="col-md-6">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">기본 정보</h3>
              </div>

              <!-- /.card-header -->
               <div class="card-body ">
                	<div class="form-group row">
                    <label for="nttSj" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 고유번호</label>
                    <div class="col-sm-10">
                    	<form:input path="comcd" title="고유번호" class="form-control " maxlength="10" readonly="true"/>
   						<div><form:errors path="comcd" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 사업자명</label>
                    <div class="col-sm-10">
                    	<form:input path="comnm" title="사업자명" class="form-control " maxlength="30" />
   						<div><form:errors path="comnm" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">전화 1</label>
                    <div class="col-sm-10">
                    	<form:input path="tel1" title="전화 1" class="form-control " maxlength="14" />
   						<div><form:errors path="tel1" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">전화 2</label>
                    <div class="col-sm-10">
                    	<form:input path="tel2" title="전화 2" class="form-control " maxlength="14" />
   						<div><form:errors path="tel2" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">팩스번호</label>
                    <div class="col-sm-10">
                    	<form:input path="fax" title="팩스번호" class="form-control " maxlength="14" />
   						<div><form:errors path="fax" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">주소</label>
                    <div class="col-sm-10">
                    	<form:input path="addr" title="주소" class="form-control " maxlength="250" />
   						<div><form:errors path="addr" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">E-MAIL</label>
                    <div class="col-sm-10">
                    	<form:input path="email" title="이메일" class="form-control " maxlength="250" />
   						<div><form:errors path="email" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">대표자명</label>
                    <div class="col-sm-10">
                    	<form:input path="bossNm" title="대표자명" class="form-control " maxlength="15" />
   						<div><form:errors path="bossNm" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">사업자번호</label>
                    <div class="col-sm-10">
                    	<form:input path="bizNo" title="사업자번호" class="form-control " maxlength="20" />
   						<div><form:errors path="bizNo" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">담당자명</label>
                    <div class="col-sm-10">
                    	<form:input path="charger" title="담당자명" class="form-control " maxlength="20" />
   						<div><form:errors path="charger" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">담당자전화</label>
                    <div class="col-sm-10">
                    	<form:input path="chargertel" title="담당자전화" class="form-control " maxlength="14" />
   						<div><form:errors path="chargertel" cssClass="error" /></div>
                    </div>
                  </div>

                </div>
                <!-- /.card-body -->

            <!-- /.card -->
          </div>
        </div>
         <div class="col-md-6">
         	<div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">운영 환경 설정</h3>
              </div>

              <!-- /.card-header -->
              <div class="card-body ">
                	<div class="form-group row">
	                    <label for="smsUseyn" class="col-sm-2 col-form-label"> SMS연동</label>
	                    <div class="col-sm-10" style="padding-top:5px">
							<div class="form-check" style="display:inline-block">
								<form:radiobutton path="smsUseyn" cssClass="form-check-input" value="Y"/>
	                           <label class="form-check-label" for="smsUseyn1">사용함</label>
	                        </div>
	                        <div class="form-check"  style="display:inline-block">
	                          	<form:radiobutton path="smsUseyn" cssClass="form-check-input" value="N"/>
	                           	<label class="form-check-label" for="smsUseyn2">사용안함</label>
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="smsProvider" class="col-sm-2 col-form-label"> SMS연동업체</label>
	                    <div class="col-sm-10" >
	                    	 <form:select path="smsProvider" class="form-control">
	                    	 	<option value=""> ----선택---- </option>
    							<form:options items="${smsProviderList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	 </form:select>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="smsUseyn" class="col-sm-2 col-form-label"> LMS서비스</label>
	                    <div class="col-sm-10" style="padding-top:5px">
							<div class="form-check" style="display:inline-block">
								<form:radiobutton path="lmsServiceyn" cssClass="form-check-input" value="Y"/>
	                           <label class="form-check-label" for="lmsServiceyn">사용함</label>
	                        </div>
	                        <div class="form-check"  style="display:inline-block">
	                          	<form:radiobutton path="lmsServiceyn" cssClass="form-check-input" value="N"/>
	                           	<label class="form-check-label" for="lmsServiceyn2">사용안함</label>
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="mmsUseyn" class="col-sm-2 col-form-label"> MMS서비스</label>
	                    <div class="col-sm-10" style="padding-top:5px">
							<div class="form-check" style="display:inline-block">
								<form:radiobutton path="mmsServiceyn" cssClass="form-check-input" value="Y"/>
	                           <label class="form-check-label" for="mmsServiceyn">사용함</label>
	                        </div>
	                        <div class="form-check"  style="display:inline-block">
	                          	<form:radiobutton path="mmsServiceyn" cssClass="form-check-input" value="N"/>
	                           	<label class="form-check-label" for="mmsServiceyn2">사용안함</label>
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="smsId" class="col-sm-2 col-form-label">SMS ID</label>
	                    <div class="col-sm-10">
	                    	<form:input path="smsId" title="SMS ID" class="form-control" maxlength="30" />
	   						<div><form:errors path="smsId" cssClass="error" /></div>
	                    </div>
	                  </div>
	                <div class="form-group row">
	                    <label for="smsPw" class="col-sm-2 col-form-label">SMS Password</label>
	                    <div class="col-sm-10">
	                    	<form:input path="smsPw" title="SMS PW" class="form-control" maxlength="30" />
	   						<div><form:errors path="smsPw" cssClass="error" /></div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="mailServer" class="col-sm-2 col-form-label">메일 서버 IP</label>
	                    <div class="col-sm-10">
	                    	<form:input path="mailServer" title="메일 서버 IP" class="form-control" maxlength="30" />
	   						<div><form:errors path="mailServer" cssClass="error" /></div>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="pcompany" class="col-sm-2 col-form-label">POS 결제업체</label>
	                    <div class="col-sm-10" >
	                    	 <form:select path="pcompany"  class="form-control">
	                    	 	<form:option value="" label="없음" />
	                    	 	<form:options items="${paymentList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	 </form:select>
	                    </div>
	                </div>
	                <div class="form-group row">
	                    <label for="pcompany" class="col-sm-2 col-form-label">온라인 결제업체</label>
	                    <div class="col-sm-10" >
	                    	 <form:select path="onlinePaymentcorp"  class="form-control">
	                    	 	<form:option value="" label="없음" />
	                    	 	<form:options items="${paymentList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	 </form:select>
	                    </div>
	                </div>

	                <div class="form-group row">
	                    <label for="mbPwdcycle" class="col-sm-2 col-form-label">비밀번호 변경주기</label>
	                    <div class="col-sm-10" >
	                    	 <form:select path="mbPwdcycle" items="${mbPwdCycleList}" itemLabel="cdNm" itemValue="cd" class="form-control" />
	                    </div>
	                </div>
					<div class="form-group row">
	                    <label for="smsProvider" class="col-sm-2 col-form-label">변경주기 값</label>
	                    <div class="col-sm-10" >
							<div class="input-group mb-3">
			                  <form:input path="mbPwdcycleValue" title="비밀번호 변경주기 값" class="form-control" maxlength="2" />
			                  <div class="input-group-append">
			                    <span class="input-group-text">개월</span>
			                  </div>
			                </div>
			             </div>
			         </div>
              </div>
               <div class="card-footer">
                	<c:choose>
                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
                			 <button type="submit"  class="btn btn-info float-right">저장</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-info float-right disabled">저장</button>
                		</c:otherwise>
                	</c:choose>
               </div>
                <!-- /.card-footer -->
           </div>
        </div>
      </div>
</form:form>
     </div>
</section>


		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>