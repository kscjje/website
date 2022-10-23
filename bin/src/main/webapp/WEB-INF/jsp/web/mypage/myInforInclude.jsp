<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="bySector my_p">
          <ul class="bySector-list nav nav-pills" role="tablist">
            <li class="bySector-item nav-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myinfor/myInforRegist')">
              <button <c:out value='${splitStr[fn:length(splitStr)-1] eq "myInforRegist.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true">회원정보수정</button>
            </li>
            <li class="bySector-item nav-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myinfor/changePasswd')">
              <button <c:out value='${splitStr[fn:length(splitStr)-1] eq "changePasswd.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">비밀번호 변경</button>
            </li>
            <li class="bySector-item nav-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myinfor/reAgree')">
              <button <c:out value='${splitStr[fn:length(splitStr)-1] eq "reAgree.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">회원정보 재동의</button>
            </li>
            <li class="bySector-item nav-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myinfor/outMember')">
              <button <c:out value='${splitStr[fn:length(splitStr)-1] eq "outMember.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">회원탈퇴</button>
            </li>
          </ul>
        </div>